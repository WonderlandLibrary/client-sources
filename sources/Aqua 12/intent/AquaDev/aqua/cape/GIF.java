// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.cape;

import intent.AquaDev.aqua.Aqua;
import java.net.URL;
import intent.AquaDev.aqua.utils.TimeUtil;

public class GIF
{
    private String name;
    private ImageFrame[] frames;
    TimeUtil timer;
    int current;
    
    public GIF(final String name, final URL URL) {
        this.timer = new TimeUtil();
        this.current = 0;
        this.name = name;
        this.frames = Aqua.INSTANCE.GIFLoader.readGifFromURL(URL, name);
    }
    
    public GIF(final String name, final String file) {
        this.timer = new TimeUtil();
        this.current = 0;
        this.name = name;
        this.frames = Aqua.INSTANCE.GIFLoader.readGifFromAssets(file, name);
    }
    
    public ImageFrame getNext() {
        if (this.frames != null) {
            if (this.timer.hasReached(100L)) {
                this.timer.reset();
                ++this.current;
                if (this.current >= this.frames.length) {
                    this.current = 0;
                }
            }
            return this.frames[this.current];
        }
        return null;
    }
    
    public String getName() {
        return this.name;
    }
    
    public ImageFrame get() {
        return this.frames[1];
    }
}
