package me.superskidder.lune.utils.client;

import me.superskidder.lune.Lune;
import net.minecraft.client.Minecraft;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 国际化的道路
 * @author: QianXia
 * @create: 2020/10/31 18:15
 **/
public class I18n {
    public static Map<String, String> languageTranslationCn = new HashMap<>();
    public static boolean isChinese = false;

    public I18n() {
        I18n.load();
    }

    public static void load(){
        String contextCh = FileUtils.readFile(Minecraft.getMinecraft().mcDataDir.getPath() + "/" + Lune.CLIENT_NAME + "/zh_CN.lang");
        String[] chStrs = contextCh.split("\n");

        for (String str : chStrs) {
            String[] strings = str.split("=");
            languageTranslationCn.put(strings[0], strings[1]);
        }
    }

    public static String format(final String key) {
        //boolean isChinese = "zh_CN".equals(Minecraft.getMinecraft().gameSettings.language);
        Map<String, String> fuckU = new HashMap<>();
        fuckU.put(key, key);
        if(isChinese){
            languageTranslationCn.forEach((keye, wow) -> {
                String[] textSplit = key.split(" ");
                for (String s : textSplit) {
                    if (keye.equalsIgnoreCase(s)) {
                        String old = fuckU.get(key);
                        fuckU.put(key, old.replace(keye, wow));
                    }
                }
                fuckU.put(key, fuckU.get(key).trim());
            });
        }
        return fuckU.get(key);
    }
}
