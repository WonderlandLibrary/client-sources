package HORIZON-6-0-SKIDPROTECTION;

import java.io.Writer;
import java.io.PrintWriter;
import java.io.File;
import java.util.Iterator;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class FileUtils
{
    public static List<String> HorizonCode_Horizon_È(final String file) {
        try {
            return Files.readAllLines(Paths.get(file, new String[0]));
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }
    
    public static void HorizonCode_Horizon_È(final String file, final List<String> newcontent) {
        try {
            final FileWriter fw = new FileWriter(file);
            for (final String s : newcontent) {
                fw.append((CharSequence)(String.valueOf(s) + "\r\n"));
            }
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void Â(final String name) {
        try {
            final File file = new File(Minecraft.áŒŠà().ŒÏ + "\\Horizon", String.valueOf(name) + ".ini");
            if (!file.exists()) {
                final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                printWriter.println();
                printWriter.close();
            }
            System.out.println(Minecraft.áŒŠà().ŒÏ + "\\Horizon");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
