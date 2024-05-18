package mods.itemphysic.config;

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
    private Map<String, Object> defaultValues;

    public Configuration(String configName, Map<String, Object> defaultValues)
    {
        this.defaultValues = defaultValues;
        File file1 = new File("config/" + configName);

        if (!file1.getParentFile().exists())
        {
            file1.getParentFile().mkdir();
        }

        this.configFile = file1;

        if (!file1.exists())
        {
            this.createConfig();
        }

        this.load();
    }

    public void load()
    {
        try
        {
            for (Entry<String, Object> entry : this.defaultValues.entrySet())
            {
                this.configValues.put(entry.getKey(), new Property((String)entry.getValue()));
            }

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
        catch (Exception var6)
        {
            this.createConfig();
        }
    }

    private void createConfig()
    {
        this.configValues.clear();

        for (Entry<String, Object> entry : this.defaultValues.entrySet())
        {
            this.configValues.put(entry.getKey(), new Property(String.valueOf(entry.getValue())));
        }

        this.save();
    }

    public Property get(String key)
    {
        return (Property)this.configValues.get(key);
    }

    public void set(String key, Property property)
    {
        this.configValues.put(key, property);
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
