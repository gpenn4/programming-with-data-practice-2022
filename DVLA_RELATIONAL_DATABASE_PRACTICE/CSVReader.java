import java.io.*;
import java.util.*;

//class to read CSV data files
public class CSVReader {

    public static List<List<String>> read(String fileName, boolean hasHeader, boolean keepHeader) throws IOException {
       
        List<List<String>> result = new ArrayList<>();

        //uses BufferedReader to loop through the file and creat a list of words in a list of lines
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));) {

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    
                    String[] words = line.split(", ");
                    ArrayList<String> wordList = new ArrayList<>();

                    for(String word: words){
                        wordList.add(word);
                    }
                    result.add(wordList);
                }
            }

            //removes headers of the files from the list
            if (hasHeader && ! keepHeader){
                result.remove(0);
            }
        }

        return result;
    }

}
