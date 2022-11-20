//declares the package the class is in
package gamePackage;

//imports the following libraries for class to use
import java.io.*;
import java.lang.*;
import java.util.*;

//Starts the FileFunctions class that contains methods for modifying files
public class FileFunctions {

    // =======================================================================
    // modifyingFile method
    // Modify a specific line of a file
    // String filePath, oldString, newString parameter
    // Returns void
    // =======================================================================
    public void modifyFile(String filePath, String oldString, String newString){

        File fileToBeModified = new File(filePath);

        String oldContent = "";

        BufferedReader reader = null;

        FileWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(fileToBeModified));

            String line = reader.readLine();

            while (line != null)
            {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();
            }


            String newContent = oldContent.replaceAll(oldString, newString);


            writer = new FileWriter(fileToBeModified);

            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {

                reader.close();

                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


}

