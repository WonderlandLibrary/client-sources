package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.net.URL;

public class Downloader
{
    public static void HorizonCode_Horizon_È(final String link, final String downloadname, final String location, final String format) {
        try {
            final URL url = new URL(link);
            final File download = new File(Minecraft.áŒŠà().ŒÏ + "/" + location, String.valueOf(downloadname) + "." + format);
            if (download.exists()) {
                System.out.println(String.valueOf(downloadname) + "." + format + " allready exists");
                return;
            }
            FileUtils.copyURLToFile(url, download);
            System.out.println("Successfully Downloaded " + downloadname + "." + format);
        }
        catch (IOException e) {
            System.out.println("Failed to Download " + downloadname + "." + format);
        }
    }
    
    public static void Â(final String link, final String downloadname, final String location, final String format) {
        try {
            final URL url = new URL(link);
            final File download = new File(Minecraft.áŒŠà().ŒÏ + "/" + location, String.valueOf(downloadname) + "." + format);
            FileUtils.copyURLToFile(url, download);
            System.out.println("Successfully Downloaded " + downloadname + "." + format);
        }
        catch (IOException e) {
            System.out.println("Failed to Download " + downloadname + "." + format);
        }
    }
    
    public static void HorizonCode_Horizon_È(final String file) {
    }
}
