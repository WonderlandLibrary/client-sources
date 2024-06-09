// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.alt.design;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

public class AltsSaver
{
    public static ArrayList<AltTypes> AltTypeList;
    public static final File altFile;
    
    public static void saveAltsToFile() {
        try {
            if (!AltsSaver.altFile.exists()) {
                try {
                    AltsSaver.altFile.createNewFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            final PrintWriter writer = new PrintWriter(AltsSaver.altFile);
            for (final AltTypes slot : AltsSaver.AltTypeList) {
                AltManager.i += 40;
                writer.write(String.valueOf(slot.getEmail()) + ":" + slot.getPassword() + "\n");
            }
            writer.close();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    public static void altsReader() {
        try {
            if (!AltsSaver.altFile.exists()) {
                System.out.println("Alt not found. Error 22");
            }
            final BufferedReader reader = new BufferedReader(new FileReader(AltsSaver.altFile));
            final AltTypes altTypes = new AltTypes("", "");
            String line;
            while ((line = reader.readLine()) != null) {
                final String[] arguments = line.split(":");
                altTypes.setPassword(arguments[0]);
                altTypes.setEmail(arguments[1]);
                AltsSaver.AltTypeList.add(new AltTypes(arguments[0], arguments[1]));
            }
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static {
        altFile = null;
        AltsSaver.AltTypeList = new ArrayList<AltTypes>();
    }
}
