package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.ArrayList;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

public class PanelManager
{
    public void HorizonCode_Horizon_È() {
        if (Horizon.à¢.Ä == null) {
            return;
        }
        try {
            final File f = new File(Horizon.à¢.áŒŠ, "panel.ini");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            final PrintWriter output = new PrintWriter(new FileWriter(f, true));
            for (final Panel panel : Horizon.à¢.Ä.Âµá€()) {
                output.println(String.valueOf(panel.Âµá€()) + ":" + panel.Ø­áŒŠá() + "-" + panel.Ó() + "-" + panel.áŒŠÆ() + "-" + panel.Â());
            }
            output.close();
        }
        catch (Exception ex) {}
    }
    
    public void Â() {
        try {
            for (final Panel m : Horizon.à¢.Ä.Âµá€()) {
                String[] ý;
                for (int length = (ý = this.Ý()).length, i = 0; i < length; ++i) {
                    final String bind = ý[i];
                    final String[] splitted = bind.split(":");
                    if (m.Âµá€().equalsIgnoreCase(splitted[0])) {
                        final String[] bound = splitted[1].split("-");
                        m.HorizonCode_Horizon_È(Integer.valueOf(bound[0]));
                        m.Â(Integer.valueOf(bound[1]));
                        m.Â(Boolean.valueOf(bound[2]));
                        m.HorizonCode_Horizon_È(Boolean.valueOf(bound[3]));
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public String[] Ý() {
        try {
            final File f = new File(Horizon.à¢.áŒŠ, "panel.ini");
            if (!f.exists()) {
                f.createNewFile();
            }
            final FileReader fileReader = new FileReader(f);
            final BufferedReader bufferedReader = new BufferedReader(fileReader);
            final List<String> lines = new ArrayList<String>();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
            return lines.toArray(new String[lines.size()]);
        }
        catch (Exception ex) {
            return new String[] { "kevin" };
        }
    }
}
