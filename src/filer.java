import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class filer
{
    static FileWriter fileWriter;
    static FileReader fileReader;
    public static void write(String toBeWritten, File fileLocation)
    {
        try
        {
            console.pln("Init new FileWriter ...");
            fileWriter = new FileWriter(fileLocation);
            console.pln("Writing ...");
            fileWriter.write(toBeWritten);
            console.pln("Flushing ...");
            fileWriter.flush();
            console.pln("Closing FileWriter ...");
            fileWriter.close();
            console.pln("... Done!");
        }
        catch (Exception e)
        {
            console.pln("Error Occurred ...");
            console.pln("StackTrace : \n\n");
            e.printStackTrace();
        }
    }

    public static String read(File fileLocation)
    {
        try
        {
            console.pln("Starting new FileReader ...");
            fileReader = new FileReader(fileLocation);
            console.pln("Reading ...");
            String toBeReturned = "";
            while(true)
            {
                int d = fileReader.read();
                if(d==-1)
                    break;
                else
                    toBeReturned += Character.toString((char) d);
            }

            return toBeReturned;
        }
        catch (Exception e)
        {
            console.pln("Error Occurred ...");
            console.pln("StackTrace : \n\n");
            e.printStackTrace();
            return null;
        }
    }
}