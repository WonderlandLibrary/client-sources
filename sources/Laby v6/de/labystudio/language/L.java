package de.labystudio.language;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;

public class L
{
    private static Map<String, L.Language> lang = new ConcurrentHashMap();
    public static L.Language language;
    public static String lastMCCode = "";

    public static void load()
    {
        try
        {
            String s = "/assets/minecraft/lang/";
            load("en", L.class.getResourceAsStream(s + "labymod_en.properties"));
            load("de", L.class.getResourceAsStream(s + "labymod_de.properties"));
            load("pt", L.class.getResourceAsStream(s + "labymod_pt.properties"));
            load("es", L.class.getResourceAsStream(s + "labymod_es.properties"));
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }

        language = (L.Language)lang.get("en");
        updateLang();
    }

    public static void updateLang()
    {
        String s = "en";

        if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().getLanguageManager() != null && Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage() != null && Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode() != null)
        {
            s = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
        }

        if (s.startsWith("en"))
        {
            setLang("en");
        }

        if (s.startsWith("de"))
        {
            setLang("de");
        }

        if (s.startsWith("pt"))
        {
            setLang("pt");
        }

        if (s.startsWith("es"))
        {
            setLang("es");
        }

        lastMCCode = s;
    }

    public static void setLang(String s)
    {
        language = (L.Language)lang.get(s);
    }

    public static void load(String name, InputStream input) throws IOException
    {
        try
        {
            Properties properties = new Properties();
            properties.load(input);
            L.Language l$language = new L.Language(name);

            for (Entry<Object, Object> entry : properties.entrySet())
            {
                l$language.translations.put(entry.getKey().toString(), entry.getValue().toString());
            }

            lang.put(name, l$language);
        }
        catch (Exception var6)
        {
            System.out.println("Can\'t load language file " + name);
        }
    }

    public static String f(String key, Object... args)
    {
        return translate(key, true, args);
    }

    public static String translate(String key, boolean format, Object... args)
    {
        String s = "en";

        if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().getLanguageManager() != null && Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage() != null && Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode() != null && lastMCCode != Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode())
        {
            updateLang();
        }

        if (key != null && language != null)
        {
            String s1 = language.get(key);

            if (s1 == null)
            {
                return "unknown";
            }
            else
            {
                if (format)
                {
                    s1 = String.format(s1, args);
                }

                return s1;
            }
        }
        else
        {
            return "unknown";
        }
    }

    public static String translate(String key, Object... args)
    {
        return translate(key, true, args);
    }

    public static void registerLanguage(String lang1, L.Language l)
    {
        lang.put(lang1.toLowerCase(), l);
    }

    public static L.Language getLanguage()
    {
        return language;
    }

    public static L.Language getLanguage(String l)
    {
        if (l.indexOf("_") == -1)
        {
            return null;
        }
        else
        {
            String s = l.split("_")[0];
            return lang.containsKey(s) ? (L.Language)lang.get(s) : (L.Language)lang.get("en");
        }
    }

    public static class Language
    {
        public Map<String, String> translations = new ConcurrentHashMap();
        private String name;

        public Language(String name)
        {
            this.name = name;
        }

        public void add(String key, String translation)
        {
            this.translations.put(key.toLowerCase(), translation);
        }

        public String get(String key)
        {
            return (String)this.translations.get(key.toLowerCase());
        }

        public String getName()
        {
            return this.name;
        }
    }
}
