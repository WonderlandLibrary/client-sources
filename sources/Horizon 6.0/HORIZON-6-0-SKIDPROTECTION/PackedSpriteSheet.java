package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class PackedSpriteSheet
{
    private Image HorizonCode_Horizon_È;
    private String Â;
    private HashMap Ý;
    private int Ø­áŒŠá;
    
    public PackedSpriteSheet(final String def) throws SlickException {
        this(def, null);
    }
    
    public PackedSpriteSheet(String def, final Color trans) throws SlickException {
        this.Ý = new HashMap();
        this.Ø­áŒŠá = 2;
        def = def.replace('\\', '/');
        this.Â = def.substring(0, def.lastIndexOf("/") + 1);
        this.HorizonCode_Horizon_È(def, trans);
    }
    
    public PackedSpriteSheet(final String def, final int filter) throws SlickException {
        this(def, filter, null);
    }
    
    public PackedSpriteSheet(String def, final int filter, final Color trans) throws SlickException {
        this.Ý = new HashMap();
        this.Ø­áŒŠá = 2;
        this.Ø­áŒŠá = filter;
        def = def.replace('\\', '/');
        this.Â = def.substring(0, def.lastIndexOf("/") + 1);
        this.HorizonCode_Horizon_È(def, trans);
    }
    
    public Image HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public Image HorizonCode_Horizon_È(final String name) {
        final HorizonCode_Horizon_È section = this.Ý.get(name);
        if (section == null) {
            throw new RuntimeException("Unknown sprite from packed sheet: " + name);
        }
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(section.HorizonCode_Horizon_È, section.Â, section.Ý, section.Ø­áŒŠá);
    }
    
    public SpriteSheet Â(final String name) {
        final Image image = this.HorizonCode_Horizon_È(name);
        final HorizonCode_Horizon_È section = this.Ý.get(name);
        return new SpriteSheet(image, section.Ý / section.Âµá€, section.Ø­áŒŠá / section.Ó);
    }
    
    private void HorizonCode_Horizon_È(final String def, final Color trans) throws SlickException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(ResourceLoader.HorizonCode_Horizon_È(def)));
        try {
            this.HorizonCode_Horizon_È = new Image(String.valueOf(this.Â) + reader.readLine(), false, this.Ø­áŒŠá, trans);
            while (reader.ready()) {
                if (reader.readLine() == null) {
                    break;
                }
                final HorizonCode_Horizon_È sect = new HorizonCode_Horizon_È(reader);
                this.Ý.put(sect.à, sect);
                if (reader.readLine() == null) {
                    break;
                }
            }
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È(e);
            throw new SlickException("Failed to process definitions file - invalid format?", e);
        }
    }
    
    private class HorizonCode_Horizon_È
    {
        public int HorizonCode_Horizon_È;
        public int Â;
        public int Ý;
        public int Ø­áŒŠá;
        public int Âµá€;
        public int Ó;
        public String à;
        
        public HorizonCode_Horizon_È(final BufferedReader reader) throws IOException {
            this.à = reader.readLine().trim();
            this.HorizonCode_Horizon_È = Integer.parseInt(reader.readLine().trim());
            this.Â = Integer.parseInt(reader.readLine().trim());
            this.Ý = Integer.parseInt(reader.readLine().trim());
            this.Ø­áŒŠá = Integer.parseInt(reader.readLine().trim());
            this.Âµá€ = Integer.parseInt(reader.readLine().trim());
            this.Ó = Integer.parseInt(reader.readLine().trim());
            reader.readLine().trim();
            reader.readLine().trim();
            this.Âµá€ = Math.max(1, this.Âµá€);
            this.Ó = Math.max(1, this.Ó);
        }
    }
}
