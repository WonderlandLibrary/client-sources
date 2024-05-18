package HORIZON-6-0-SKIDPROTECTION;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.io.File;

public class FileSystemLocation implements ResourceLocation
{
    private File HorizonCode_Horizon_È;
    
    public FileSystemLocation(final File root) {
        this.HorizonCode_Horizon_È = root;
    }
    
    @Override
    public URL HorizonCode_Horizon_È(final String ref) {
        try {
            File file = new File(this.HorizonCode_Horizon_È, ref);
            if (!file.exists()) {
                file = new File(ref);
            }
            if (!file.exists()) {
                return null;
            }
            return file.toURI().toURL();
        }
        catch (IOException e) {
            return null;
        }
    }
    
    @Override
    public InputStream Â(final String ref) {
        try {
            File file = new File(this.HorizonCode_Horizon_È, ref);
            if (!file.exists()) {
                file = new File(ref);
            }
            return new FileInputStream(file);
        }
        catch (IOException e) {
            return null;
        }
    }
}
