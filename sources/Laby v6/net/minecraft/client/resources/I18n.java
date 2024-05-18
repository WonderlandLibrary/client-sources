package net.minecraft.client.resources;

import com.minimap.XaeroMinimap;
import java.util.Map;
import net.minecraft.client.Minecraft;

public class I18n
{
    private static Locale i18nLocale;

    static void setLocale(Locale i18nLocaleIn)
    {
        i18nLocale = i18nLocaleIn;
    }

    /**
     * format(a, b) is equivalent to String.format(translate(a), b). Args: translationKey, params...
     */
    public static String format(String translateKey, Object... parameters)
    {
        String s = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();

        if (XaeroMinimap.languages.containsKey(s))
        {
            Map<String, String> map = (Map)XaeroMinimap.languages.get(s);

            if (map.containsKey(translateKey))
            {
                return (String)map.get(translateKey);
            }

            if (((Map)XaeroMinimap.languages.get("en_US")).containsKey(translateKey))
            {
                return (String)((Map)XaeroMinimap.languages.get("en_US")).get(translateKey);
            }
        }

        return i18nLocale.formatMessage(translateKey, parameters);
    }
}
