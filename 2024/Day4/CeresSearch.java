import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CeresSearch {

    private char[][] data;
    private final static int N  = 0;
    private final static int NE = 1;
    private final static int E  = 2;
    private final static int SE = 3;
    private final static int S  = 4;
    private final static int SW = 5;
    private final static int W  = 6;
    private final static int NW = 7;


    public CeresSearch(String fileName) {

        // load data from file

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
        

        data = new char[xmasList.size()][];

        for (int i = 0; i < xmasList.size(); i++) {
            data[i] = xmasList.get(i).toCharArray();
        }
    }

    /*
     * Takes in a string and utilizes the dictionary to aggregate the letters to determine the number of
     * XMAS words in the string
     */
    public int findNumberOfXMAS() {
        
        if (data.length == 0 || data == null) throw new IllegalArgumentException("Array is empty");

        int numXMAS = 0;

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
               List<int[]> pList = possibleIndex(data, i, j);
               
               for (int[] indicies : pList) {
                    if (extractString(indicies).equals("XMAS")) numXMAS++;
               }

            }
        }
        
        return numXMAS;
    }

    /*
     * Get's all the possible indicies from 2D character array
     */
    private List<int[]> possibleIndex(char[][] data, int x, int y) {

        List<int[]> possibleDirections = new ArrayList<int[]>();

        int offset = charOffset(data[x][y]);
        int[][] directions = {
            {0, -offset, 0, -offset + 3, N},                   // North
            {-offset, -offset, -offset + 3, -offset + 3, NE},  // North East
            {-offset, 0, -offset + 3, 0, E},                   // East
            {-offset, -offset, -offset + 3, -offset - 3, SE},  // South East
            {0, -offset, 0, -offset - 3, S},                   // South
            {-offset, -offset, -offset - 3, -offset - 3, SW},  // South West
            {-offset, 0, -offset - 3, 0, W},                   // West
            {-offset, -offset, -offset - 3, -offset + 3, NW}   // North West            
        };

        for (int[] dir : directions) {

            int x1 = x + dir[0]; 
            int y1 = y + dir[1];
            int x2 = x + dir[2];
            int y2 = y + dir[3];
            int position = dir[4];

            if (isValidPoint(x1, y1) && isValidPoint(x2, y2)) possibleDirections.add(new int[] {x1, y1, x2, y2, position});
        }

        return possibleDirections;
    }


    private String extractString(int[] indexArray) {

        int position = indexArray[4];
        int x1 = indexArray[0];
        int y1 = indexArray[1];
        int x2 = indexArray[2];
        int y2 = indexArray[3];

        StringBuilder xmasString = new StringBuilder();

        switch (position) {
            case N:
                for (int y = y1; y <= y2; y++)
                    xmasString.append(data[x1][y]);
                break;
            case NE:
                for (int y = y1, x = x1; y <= y2 ; x++, y++)
                    xmasString.append(data[x][y]);
                break;
            case E:
                for (int x = x1; x <= x2; x++)
                    xmasString.append(data[x][y1]);
                break;
            case SE:
                for (int y = y1, x = x1; y <= y2 ; x++, y--)
                    xmasString.append(data[x][y]);            
                break;
            case S:
                for (int y = y1; y >= y2; y--)
                    xmasString.append(data[x1][y]);
                break;
            case SW:
                for (int y = y1, x = x1; y >= y2 ; x--, y--)
                    xmasString.append(data[x][y]);            
                break;
            case W:
                for (int x = x1; x >= x2; x--)
                    xmasString.append(data[x][y1]);
            break;
            case NW:
                for (int y = y1, x = x1; y <= y2 ; x--, y++)
                    xmasString.append(data[x][y]);            
            break;
            default:
                break;

        }
        return xmasString.toString();
    }

    private boolean isValidPoint(int x, int y) {
        return x >= 0 && x <= data.length-1 && y >= 0 && y <= data.length-1;
    }

    private int charOffset(char c) {
        if (c == 'X')      return 0;
        else if (c == 'M') return 1;
        else if (c == 'A') return 2;
        else               return 3;
    }


    public static void main(String[] args) {
        String fileName = args[0];  
        CeresSearch findXMAS = new CeresSearch(fileName);
        System.out.println("Num XMAS: "+findXMAS.findNumberOfXMAS());
    }
}