package directionhud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Configuration
{
    private Map<String, Property> configValues = new HashMap();
    private File configFile;

    public Configuration(File configFile)
    {
        File file1 = new File("config/" + configFile.getName());

        if (!file1.getParentFile().exists())
        {
            file1.getParentFile().mkdir();
        }

        if (!file1.exists())
        {
            try
            {
                file1.createNewFile();
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        }

        this.configFile = file1;
    }

    public void load()
    {
        try
        {
            BufferedReader bufferedreader = new BufferedReader(new FileReader(this.configFile));
            String s = null;

            while ((s = bufferedreader.readLine()) != null)
            {
                if (!s.equals(""))
                {
                    String[] astring = s.split(":");
                    this.configValues.put(astring[0], new Property(astring[1]));
                }
            }

            bufferedreader.close();
        }
        catch (FileNotFoundException filenotfoundexception)
        {
            filenotfoundexception.printStackTrace();
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public Property get(String key, Object defaultValue)
    {
        if (!this.configValues.containsKey(key))
        {
            this.configValues.put(key, new Property(defaultValue.toString()));
        }

        return (Property)this.configValues.get(key);
    }

    public void save()
    {
        this.configFile.delete();

        try
        {
            this.configFile.createNewFile();
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }

        try
        {
            PrintWriter printwriter = new PrintWriter(this.configFile);

            for (Entry<String, Property> entry : this.configValues.entrySet())
            {
                printwriter.println((String)entry.getKey() + ":" + ((Property)entry.getValue()).getValue());
            }

            printwriter.close();
        }
        catch (IOException ioexception1)
        {
            ioexception1.printStackTrace();
        }
    }
}
