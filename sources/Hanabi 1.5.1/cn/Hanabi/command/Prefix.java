package cn.Hanabi.command;

import org.jetbrains.annotations.*;
import ClassSub.*;
import cn.Hanabi.modules.Player.*;
import java.util.*;
import java.io.*;
import net.minecraft.client.*;

public class Prefix extends Command
{
    private static String fileDir;
    
    
    public Prefix() {
        super("prefix", new String[0]);
    }
    
    @Override
    public void run(final String s, @NotNull final String[] array) {
        if (array.length != 1) {
            Class120.sendClientMessage("Failed", Class84.Class307.WARNING);
        }
        else {
            Spammer.prefix = array[0];
            AutoAbuse.prefix = array[0];
            saveText();
            Class120.sendClientMessage("Changed to " + array[0], Class84.Class307.SUCCESS);
        }
    }
    
    @NotNull
    @Override
    public List<String> autocomplete(final int n, final String[] array) {
        return new ArrayList<String>();
    }
    
    public static void saveText() {
        final File file = new File(Prefix.fileDir + "/prefix.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            final PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(Spammer.prefix);
            printWriter.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void loadText() throws IOException {
        final File file = new File(Prefix.fileDir + "/prefix.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        else {
            String line;
            while ((line = new BufferedReader(new FileReader(file)).readLine()) != null) {
                try {
                    AutoAbuse.prefix = (Spammer.prefix = String.valueOf(line));
                }
                catch (Exception ex) {}
            }
        }
    }
    
    static {
        Prefix.fileDir = Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/HanabiAzureWare";
    }
}
