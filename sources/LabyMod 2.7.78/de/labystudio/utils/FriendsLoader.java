package de.labystudio.utils;

import de.labystudio.labymod.Source;
import de.labystudio.labymod.Timings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;

public class FriendsLoader
{
    public static Map<String, String> friends = new HashMap();

    public static void loadFriends()
    {
        Timings.start("Load Friends Config");

        if (friends.isEmpty())
        {
            friends.clear();
            String s = "";
            create();

            try
            {
                s = IOUtils.toString((InputStream)(new FileInputStream(Source.file_friendTags)), (Charset)Charset.forName("UTF-8"));
            }
            catch (FileNotFoundException var2)
            {
                ;
            }
            catch (IOException var3)
            {
                ;
            }

            friends = (Map)Utils.ConvertJsonToObject.getFromJSON(s, Map.class);

            if (friends == null)
            {
                friends = new HashMap();
            }

            Timings.stop("Load Friends Config");
        }
    }

    public static void create()
    {
        if (!(new File(Source.file_friendTags)).exists())
        {
            try
            {
                if (!(new File(Source.file_friendTags)).getParentFile().exists())
                {
                    (new File(Source.file_friendTags)).getParentFile().mkdirs();
                }

                (new File(Source.file_friendTags)).createNewFile();
            }
            catch (IOException var1)
            {
                ;
            }
        }
    }

    public static String getNick(String name, String blank)
    {
        return !Allowed.nick() ? name : (friends.containsKey(blank) && !((String)friends.get(blank)).replace(" ", "").isEmpty() ? ((String)friends.get(blank)).replace("&", Color.c) : name);
    }

    public static void saveFriends()
    {
        create();
        String s = Utils.ConvertJsonToObject.toJSON(friends);

        try
        {
            PrintWriter printwriter = new PrintWriter(new FileOutputStream(Source.file_friendTags));
            printwriter.print(s);
            printwriter.flush();
            printwriter.close();
        }
        catch (FileNotFoundException var2)
        {
            ;
        }
    }
}
