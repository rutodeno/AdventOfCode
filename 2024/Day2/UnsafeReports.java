import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UnsafeReports {

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


        int numUnsafeReports = 0;
        int maxThreshold = 3;
        int minThreshold = 1;

        for (int i = 0; i < report.size(); i++) {
            boolean previousValue = false;

            for (int j = 1; j < report.get(i).size(); j++) {
                boolean isMonotonicallyIncreasing = false;
                if (report.get(i).get(j-1) > report.get(i).get(j))
                    isMonotonicallyIncreasing = true;

                int difference = Math.abs(report.get(i).get(j-1) - report.get(i).get(j));

                if (difference < minThreshold || difference > maxThreshold || ((j > 1) && isMonotonicallyIncreasing != previousValue)) {
                    numUnsafeReports++;
                    System.out.println(report.get(i));
                    break;
                }

                previousValue = isMonotonicallyIncreasing;
            }
        }

        System.out.println((report.size() +" "+numUnsafeReports));

    }


    

}