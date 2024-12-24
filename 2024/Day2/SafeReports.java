import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SafeReports {

    public static void main(String args[]) {

        if (args.length == 0 || args == null) throw new IllegalArgumentException("Please provide file name");

        String fileName = args[0];
        ArrayList<ArrayList<Integer>> report = new ArrayList<ArrayList<Integer>>();
        int numberOfLines = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values =  line.split("\\s+");
                report.add(new ArrayList<Integer>());
                for (int i = 0; i < values.length; i++) {
                    report.get(numberOfLines).add(Integer.parseInt(values[i]));
                }
                numberOfLines++;
            }
        } catch (IOException e) {
            System.out.println("Unable to read "+ fileName);
        }

        int numSafeReports = 0;
        int numTolerantSafeReport = 0;

        for (int i = 0; i < report.size(); i++) {
            boolean result = isSafe(report.get(i));
            if (result) numSafeReports++;
            else {
                // we go through the unsafe list and remove each element and test whether it's safe;
                for (int l = 0; l < report.get(i).size(); l++) {

                    ArrayList<Integer> copyReport = new ArrayList<>();
                    for (int j = 0; j < report.get(i).size(); j++) {
                        if (j != l) copyReport.add(report.get(i).get(j));
                    }

                    if (isSafe(copyReport)) {
                        numTolerantSafeReport++;
                        break;
                    }
                }
            }
        }

        System.out.println("numSafeFiles: " +numSafeReports);
        System.out.println("tolerant safe: " +numTolerantSafeReport);
        System.out.println("safe + tolerantsafe: " + (numSafeReports + numTolerantSafeReport));
    }

    private static boolean isSafe(ArrayList<Integer> line) {

        int maxThreshold = 3;
        int minThreshold = 1;

        if (line.size() < 2) return true; // edge case where list has only 2 element

        boolean previousPairSign = (line.get(0) > line.get(1));

        for (int j = 1; j < line.size(); j++) {
    
            int difference = Math.abs(line.get(j-1) - line.get(j));
            
            boolean pairSign = false;            
            if (line.get(j-1) > line.get(j)) pairSign = true;

            if (difference < minThreshold || difference > maxThreshold  ||  pairSign != previousPairSign)  return false;

            previousPairSign = pairSign;
        }

        return true;
    }
}