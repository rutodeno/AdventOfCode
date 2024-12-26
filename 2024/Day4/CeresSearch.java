import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CeresSearch {

    public CeresSearch() {}

    // load data from file
    public char[][] loadData(String fileName) {
    
        if (fileName.isBlank() || fileName.isEmpty() || fileName == null)
            throw new IllegalArgumentException("Invalid file provided");
    
        List<String> xmasList = new ArrayList<>();
    
        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(fileName));

            String line;
            while ((line = fileInput.readLine()) != null) {
                xmasList.add(line);
            }

        } catch (IOException e) {
            System.out.println("Exception "+e);
        }
        

        char[][] data = new char[xmasList.size()][];

        for (int i = 0; i < xmasList.size(); i++) {
            data[i] = xmasList.get(i).toCharArray();
        }


        return data;
    }

    /*
     * Takes in a string and utilizes the dictionary to aggregate the letters to determine the number of
     * XMAS words in the string
     */
    public int findNumberOfXMAS(char[][] data) {
        
        if (data.length == 0 || data == null) throw new IllegalArgumentException("Array is empty");

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (i == 0 && (data[i][j] == 'M' || data[i][j] == 'A')) {

                }
            }
        }
        
        return 0;
    }

    public static void main(String[] args) {
        String fileName = args[0];
        CeresSearch findXMAS = new CeresSearch();
        char[][] data = findXMAS.loadData(fileName);
        System.out.println("Num XMAS: "+findXMAS.findNumberOfXMAS(data));
    }
}