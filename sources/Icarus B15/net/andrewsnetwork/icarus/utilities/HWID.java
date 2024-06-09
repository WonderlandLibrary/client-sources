// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.utilities;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class HWID
{
    public static String getSerialNumber(final String drive) {
        OutputStreamWriter request = new OutputStreamWriter(System.out);
        Label_0031: {
            try {
                request.flush();
            }
            catch (IOException ex) {
                break Label_0031;
            }
            finally {
                request = null;
            }
            request = null;
        }
        String result = "";
        try {
            final File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            final FileWriter fw = new FileWriter(file);
            final String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"" + drive + "\")\n" + "Wscript.Echo objDrive.SerialNumber";
            fw.write(vbs);
            fw.close();
            final Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            final BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result = String.valueOf(result) + line;
            }
            input.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }
}
