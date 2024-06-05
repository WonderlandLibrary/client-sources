package cn.Hanabi.command;

import org.jetbrains.annotations.*;
import cn.Hanabi.*;
import ClassSub.*;
import net.minecraft.client.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Config extends Command
{
    
    
    public Config() {
        super("config", new String[0]);
    }
    
    @Override
    public void run(final String s, @NotNull final String[] array) {
        if (array.length == 1) {
            if (!get("http://hanabi.alphaantileak.cn:893/hanabi/config/" + array[0].toLowerCase() + "/values.txt").equals("")) {
                writeValue(get("http://hanabi.alphaantileak.cn:893/hanabi/config/" + array[0].toLowerCase() + "/values.txt"));
                writeMods(get("http://hanabi.alphaantileak.cn:893/hanabi/config/" + array[0].toLowerCase() + "/mods.txt"));
                Hanabi.INSTANCE.fileManager.load();
            }
            else {
                Class120.sendClientMessage("We do not have this config!", Class84.Class307.INFO);
            }
        }
        else {
            Class120.sendClientMessage(".config [server]", Class84.Class307.INFO);
        }
    }
    
    public static void writeValue(final String s) {
        final File file = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/Hanabi" + "/values.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            final PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(s);
            printWriter.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void writeMods(final String s) {
        final File file = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/Hanabi" + "/mods.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            final PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(s);
            printWriter.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static String get(final String s) {
        String string = "";
        BufferedReader bufferedReader = null;
        try {
            final URLConnection openConnection = new URL(s).openConnection();
            openConnection.setDoOutput(true);
            openConnection.setReadTimeout(99781);
            openConnection.setRequestProperty("accept", "*/*");
            openConnection.setRequestProperty("connection", "Keep-Alive");
            openConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            openConnection.connect();
            for (String s2 : openConnection.getHeaderFields().keySet()) {}
            bufferedReader = new BufferedReader(new InputStreamReader(openConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                string = string + line + "\n";
            }
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            catch (Exception ex) {}
        }
        catch (Exception ex2) {}
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            catch (Exception ex3) {}
        }
        return string;
    }
    
    @Override
    public List<String> autocomplete(final int n, final String[] array) {
        return new ArrayList<String>();
    }
}
