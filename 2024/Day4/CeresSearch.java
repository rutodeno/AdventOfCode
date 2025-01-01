import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CeresSearch {

    private char[][] data;
    private static final int N  = 0;
    private static final int NE = 1;
    private static final int E  = 2;
    private static final int SE = 3;
    private static final int S  = 4;
    private static final int SW = 5;
    private static final int W  = 6;
    private static final int NW = 7;


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

        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
               List<int[]> pList = possibleIndex(data, row, col);
               
               for (int[] indicies : pList) {
                    String s = extractString(indicies);
                    if (s.equals("XMAS")) {
                        // System.out.println(s);
                        numXMAS++;
                    }
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
            {0, 0, 0, 3, N},    // North
            {0, 0, 3, 3, NE},   // North East
            {0, 0, 3, 0, E},    // East
            {0, 0, 3, -3, SE},  // South East
            {0, 0, 0 ,-3, S},   // South
            {0, 0, -3, -3, SW}, // South West
            {0, 0, -3, 0, W},   // West
            {0, 0, -3, 3, NW}   // North West            
        };

        for (int[] dir : directions) {

            int x1 = x + dir[0]; 
            int y1 = y + dir[1];
            int x2 = x + dir[2];
            int y2 = y + dir[3];
            int position = dir[4];

            // System.out.printf("(%d, %d) (%d, %d) %d \n", x1, y1, x2, y2, position);

            if (isValidPoint(x1, y1) && isValidPoint(x2, y2)) {
                int[] indexArray = new int[] {x1, y1, x2, y2, position};
                possibleDirections.add(indexArray);
                // System.out.printf("(%d, %d) (%d, %d) %d %s\n", x1, y1, x2, y2, position, extractString(indexArray));
            }
        }
        // System.out.println("");

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
                for (int y = y1, x = x1; y >= y2 ; x++, y--)
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
        return (x >= 0 && x <= data.length-1) && (y >= 0 && y <= data.length-1);
    }

    public int findDiagonalXMAS() {
        int numDiagonals = 0;

        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
                // first diagonal
                // (row, col) --> (x2, y2) position SE
                int x2 = row + 2;
                int y2 = col - 2;

                // second diagonal
                // (x1,y1) --> (x3, y3) position SW
                int x1 = row + 2;
                int y1 = col;

                int x3 = row;
                int y3 = col - 2;



                if (isValidPoint(x3, y3) && isValidPoint(x2, y2) && isValidPoint(x1, y1)) {

                    String firstDiagonal = extractString(new int[]{row, col, x2, y2, SE});
                    String secondDiagonal = extractString(new int[]{x1, y1, x3, y3, SW});

                    if ((firstDiagonal.equals("MAS") || firstDiagonal.equals("SAM")) && (secondDiagonal.equals("MAS") || secondDiagonal.equals("SAM"))) {
                        numDiagonals++;
                        // System.out.println(firstDiagonal + " " + secondDiagonal);
                    }
                }
            }
        }

        return numDiagonals;
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
        System.out.println("Num diagonal: "+findXMAS.findDiagonalXMAS());
    }
}