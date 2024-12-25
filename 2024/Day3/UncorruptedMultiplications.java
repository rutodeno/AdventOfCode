import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class UncorruptedMultiplications {

    public UncorruptedMultiplications() {

    }

    public String loadData(String fileName) {
        if (fileName.isBlank() || fileName.isEmpty() || fileName == null)
            throw new IllegalArgumentException("Invalid file provided");
        
        StringBuilder data = new StringBuilder();
        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(fileName));

            String line;
            while ((line = fileInput.readLine()) != null) {
                data.append(line);
            }

        } catch (IOException e) {
            System.out.println("Exception "+e);
        }
        
        return data.toString();
    }

    public int[] getTotalResult(String data) {
        Pattern pattern = Pattern.compile("(do\\(\\))|(don\\'t\\(\\))|mul\\((\\d{1,3})\\,(\\d{1,3})\\)");
        Matcher matcher = pattern.matcher(data);

        int totalResut = 0;
        int conditionalResult = 0;
        boolean enabledCondition = true;

        while (matcher.find()) {

            String enableString = matcher.group(1);
            String disableString = matcher.group(2);

            int x = 0;
            int y = 0;
            
            if (matcher.group(3) != null) x = Integer.parseInt(matcher.group(3));
            if (matcher.group(4) != null) y = Integer.parseInt(matcher.group(4));

            totalResut += x*y;

            if (disableString != null && disableString.equals("don't()")) enabledCondition = false;
            if (enableString != null && enableString.equals("do()")) enabledCondition = true;
            
            if (enabledCondition) conditionalResult += x*y;
        }

        return new int[] {totalResut, conditionalResult};
    }

    public static void main(String[] args) {
        UncorruptedMultiplications validation = new UncorruptedMultiplications();

        String data = validation.loadData(args[0]);
        int[] result = validation.getTotalResult(data);
        System.out.println("TotalSum: "+ result[0]);
        System.out.println("PartialSum: "+ result[1]);
    }
}