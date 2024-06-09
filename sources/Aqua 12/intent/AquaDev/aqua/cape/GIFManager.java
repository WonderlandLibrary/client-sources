// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.cape;

import java.util.ArrayList;

public class GIFManager
{
    ArrayList<GIF> gifs;
    
    public GIFManager() {
        this.gifs = new ArrayList<GIF>();
    }
    
    public void addGif(final GIF gif) {
        this.gifs.add(gif);
    }
    
    public GIF getGIFByName(final String name) {
        return this.gifs.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
