import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class TotalDistance {
    public static void main(String[] args) {
        if (args.length == 0 || args == null)
            throw new IllegalArgumentException("Please provide file name");

        String fileName = args[0];
        ArrayList<Integer> firstList = new ArrayList<Integer>();
        ArrayList<Integer> secondList = new ArrayList<Integer>();
        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(fileName));
            String line;
            
            while ((line = fileInput.readLine()) != null) {
                String[] values = line.split("\\s+");
                firstList.add(Integer.parseInt(values[0]));
                secondList.add(Integer.parseInt(values[1]));
            }

        } catch (IOException e) {
            System.out.println("Exception "+e);
        }

        // part 1

        firstList.sort(Comparator.naturalOrder());
        secondList.sort(Comparator.naturalOrder());

        int distance = 0;
        for (int i = 0; i < firstList.size(); i++) {
            distance += Math.abs(firstList.get(i) - secondList.get(i));
        }

        System.out.println("TotalDistance: "+distance);

        // part 2

        HashMap<Integer, Integer> duplicateNumber = new HashMap<Integer, Integer>();
        for (int i = 0; i < secondList.size(); i++) {
            Integer currentKey = secondList.get(i);
            if (duplicateNumber.containsKey(currentKey)) {
                duplicateNumber.put(currentKey, duplicateNumber.get(currentKey)+1);
            } else {
                duplicateNumber.put(currentKey, 1);
            }
        }

        int totalSimilarity = 0;
        for (int i = 0; i < firstList.size(); i++) {
            Integer currentKey = firstList.get(i);
            if (duplicateNumber.containsKey(currentKey)) {
                totalSimilarity += currentKey * duplicateNumber.get(currentKey);
            }
        }

        System.out.println("Total dups: "+totalSimilarity);
    }
}
