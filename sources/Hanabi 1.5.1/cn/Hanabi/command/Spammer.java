package cn.Hanabi.command;

import java.io.*;
import ClassSub.*;
import java.util.*;
import net.minecraft.client.*;

public class Spammer extends Command
{
    private static String fileDir;
    
    
    public Spammer() {
        super("spammer", new String[0]);
    }
    
    public static void saveText() {
        final File file = new File(Spammer.fileDir + "/spammer.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            final PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(cn.Hanabi.modules.Player.Spammer.text);
            printWriter.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void loadText() throws IOException {
        final File file = new File(Spammer.fileDir + "/spammer.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        else {
            String line;
            while ((line = new BufferedReader(new FileReader(file)).readLine()) != null) {
                try {
                    cn.Hanabi.modules.Player.Spammer.text = String.valueOf(line);
                }
                catch (Exception ex) {}
            }
        }
    }
    
    @Override
    public void run(final String s, final String[] array) {
        if (array.length == 0) {
            Class120.sendClientMessage("Failed", Class84.Class307.WARNING);
        }
        else {
            String string = "";
            for (int i = 0; i < array.length; ++i) {
                string = string + array[i] + " ";
            }
            cn.Hanabi.modules.Player.Spammer.text = string;
            saveText();
            Class120.sendClientMessage("Changed to " + string, Class84.Class307.SUCCESS);
        }
    }
    
    @Override
    public List<String> autocomplete(final int n, final String[] array) {
        return new ArrayList<String>();
    }
    
    static {
        Spammer.fileDir = Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/Hanabi";
    }
}
