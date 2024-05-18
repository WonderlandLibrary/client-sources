package paulscode.sound;

import java.net.*;

public class FilenameURL
{
    private SoundSystemLogger logger;
    private String filename;
    private URL url;
    
    public FilenameURL(final URL url, final String filename) {
        this.filename = null;
        this.url = null;
        this.logger = SoundSystemConfig.getLogger();
        this.filename = filename;
        this.url = url;
    }
    
    public FilenameURL(final String filename) {
        this.filename = null;
        this.url = null;
        this.logger = SoundSystemConfig.getLogger();
        this.filename = filename;
        this.url = null;
    }
    
    public String getFilename() {
        return this.filename;
    }
    
    public URL getURL() {
        if (this.url == null) {
            if (this.filename.matches(SoundSystemConfig.PREFIX_URL)) {
                try {
                    this.url = new URL(this.filename);
                    return this.url;
                }
                catch (Exception ex) {
                    this.errorMessage("Unable to access online URL in method 'getURL'");
                    this.printStackTrace(ex);
                    return null;
                }
            }
            this.url = this.getClass().getClassLoader().getResource(SoundSystemConfig.getSoundFilesPackage() + this.filename);
        }
        return this.url;
    }
    
    private void errorMessage(final String s) {
        this.logger.errorMessage("MidiChannel", s, 0);
    }
    
    private void printStackTrace(final Exception ex) {
        this.logger.printStackTrace(ex, 1);
    }
}
