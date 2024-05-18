package de.labystudio.utils;

import de.labystudio.labymod.ConfigManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class FriendsLoader
{
    public static Map<String, String> friends = new HashMap();

    public static void loadFriends()
    {
        if (friends.isEmpty())
        {
            friends.clear();
            String s = "";

            try
            {
                create();
                BufferedReader bufferedreader = new BufferedReader(new FileReader("LabyMod/friend_tags.json"));

                try
                {
                    StringBuilder stringbuilder = new StringBuilder();

                    for (String s1 = bufferedreader.readLine(); s1 != null; s1 = bufferedreader.readLine())
                    {
                        stringbuilder.append(s1);
                        stringbuilder.append("\n");
                    }

                    s = stringbuilder.toString();
                }
                finally
                {
                    bufferedreader.close();
                }
            }
            catch (Exception exception1)
            {
                exception1.printStackTrace();
            }

            try
            {
                friends = (Map)Utils.ConvertJsonToObject.getFromJSON(s, Map.class);
            }
            catch (Exception exception)
            {
                (new File("LabyMod/friend_tags.json")).delete();
                exception.printStackTrace();
            }

            if (friends == null)
            {
                friends = new HashMap();
            }
        }
    }

    public static void create()
    {
        if (!(new File("LabyMod/friend_tags.json")).exists())
        {
            try
            {
                if (!(new File("LabyMod/friend_tags.json")).getParentFile().exists())
                {
                    (new File("LabyMod/friend_tags.json")).getParentFile().mkdirs();
                }

                (new File("LabyMod/friend_tags.json")).createNewFile();
            }
            catch (IOException var1)
            {
                ;
            }
        }
    }

    public static String getNick(String name, String blank)
    {
        if (Allowed.nick() && ConfigManager.settings.tags && !friends.isEmpty())
        {
            if (friends.containsKey(blank) && !((String)friends.get(blank)).replace(" ", "").isEmpty())
            {
                return ((String)friends.get(blank)).replace("&", Color.c);
            }
            else
            {
                for (String s : friends.keySet())
                {
                    if (s.startsWith("@"))
                    {
                        s = s.replace("@", "");

                        if (blank.contains(s))
                        {
                            return name.replace(s, ((String)friends.get("@" + s)).replace("&", Color.c));
                        }
                    }
                }

                return name;
            }
        }
        else
        {
            return name;
        }
    }

    public static void saveFriends()
    {
        create();
        String s = Utils.ConvertJsonToObject.toJSON(friends);

        try
        {
            PrintWriter printwriter = new PrintWriter(new FileOutputStream("LabyMod/friend_tags.json"));
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
