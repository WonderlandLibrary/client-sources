package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Iterator;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HieroSettings
{
    private int HorizonCode_Horizon_È;
    private boolean Â;
    private boolean Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private int à;
    private int Ø;
    private int áŒŠÆ;
    private int áˆºÑ¢Õ;
    private int ÂµÈ;
    private final List á;
    
    public HieroSettings() {
        this.HorizonCode_Horizon_È = 12;
        this.Â = false;
        this.Ý = false;
        this.áˆºÑ¢Õ = 512;
        this.ÂµÈ = 512;
        this.á = new ArrayList();
    }
    
    public HieroSettings(final String hieroFileRef) throws SlickException {
        this(ResourceLoader.HorizonCode_Horizon_È(hieroFileRef));
    }
    
    public HieroSettings(final InputStream in) throws SlickException {
        this.HorizonCode_Horizon_È = 12;
        this.Â = false;
        this.Ý = false;
        this.áˆºÑ¢Õ = 512;
        this.ÂµÈ = 512;
        this.á = new ArrayList();
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                line = line.trim();
                if (line.length() == 0) {
                    continue;
                }
                final String[] pieces = line.split("=", 2);
                String name = pieces[0].trim();
                final String value = pieces[1];
                if (name.equals("font.size")) {
                    this.HorizonCode_Horizon_È = Integer.parseInt(value);
                }
                else if (name.equals("font.bold")) {
                    this.Â = Boolean.valueOf(value);
                }
                else if (name.equals("font.italic")) {
                    this.Ý = Boolean.valueOf(value);
                }
                else if (name.equals("pad.top")) {
                    this.Ø­áŒŠá = Integer.parseInt(value);
                }
                else if (name.equals("pad.right")) {
                    this.à = Integer.parseInt(value);
                }
                else if (name.equals("pad.bottom")) {
                    this.Ó = Integer.parseInt(value);
                }
                else if (name.equals("pad.left")) {
                    this.Âµá€ = Integer.parseInt(value);
                }
                else if (name.equals("pad.advance.x")) {
                    this.Ø = Integer.parseInt(value);
                }
                else if (name.equals("pad.advance.y")) {
                    this.áŒŠÆ = Integer.parseInt(value);
                }
                else if (name.equals("glyph.page.width")) {
                    this.áˆºÑ¢Õ = Integer.parseInt(value);
                }
                else if (name.equals("glyph.page.height")) {
                    this.ÂµÈ = Integer.parseInt(value);
                }
                else {
                    if (name.equals("effect.class")) {
                        try {
                            this.á.add(Class.forName(value).newInstance());
                            continue;
                        }
                        catch (Exception ex) {
                            throw new SlickException("Unable to create effect instance: " + value, ex);
                        }
                    }
                    if (!name.startsWith("effect.")) {
                        continue;
                    }
                    name = name.substring(7);
                    final ConfigurableEffect effect = this.á.get(this.á.size() - 1);
                    final List values = effect.Â();
                    for (final ConfigurableEffect.HorizonCode_Horizon_È effectValue : values) {
                        if (effectValue.HorizonCode_Horizon_È().equals(name)) {
                            effectValue.HorizonCode_Horizon_È(value);
                            break;
                        }
                    }
                    effect.HorizonCode_Horizon_È(values);
                }
            }
            reader.close();
        }
        catch (Exception ex2) {
            throw new SlickException("Unable to load Hiero font file", ex2);
        }
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final int paddingTop) {
        this.Ø­áŒŠá = paddingTop;
    }
    
    public int Â() {
        return this.Âµá€;
    }
    
    public void Â(final int paddingLeft) {
        this.Âµá€ = paddingLeft;
    }
    
    public int Ý() {
        return this.Ó;
    }
    
    public void Ý(final int paddingBottom) {
        this.Ó = paddingBottom;
    }
    
    public int Ø­áŒŠá() {
        return this.à;
    }
    
    public void Ø­áŒŠá(final int paddingRight) {
        this.à = paddingRight;
    }
    
    public int Âµá€() {
        return this.Ø;
    }
    
    public void Âµá€(final int paddingAdvanceX) {
        this.Ø = paddingAdvanceX;
    }
    
    public int Ó() {
        return this.áŒŠÆ;
    }
    
    public void Ó(final int paddingAdvanceY) {
        this.áŒŠÆ = paddingAdvanceY;
    }
    
    public int à() {
        return this.áˆºÑ¢Õ;
    }
    
    public void à(final int glyphPageWidth) {
        this.áˆºÑ¢Õ = glyphPageWidth;
    }
    
    public int Ø() {
        return this.ÂµÈ;
    }
    
    public void Ø(final int glyphPageHeight) {
        this.ÂµÈ = glyphPageHeight;
    }
    
    public int áŒŠÆ() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void áŒŠÆ(final int fontSize) {
        this.HorizonCode_Horizon_È = fontSize;
    }
    
    public boolean áˆºÑ¢Õ() {
        return this.Â;
    }
    
    public void HorizonCode_Horizon_È(final boolean bold) {
        this.Â = bold;
    }
    
    public boolean ÂµÈ() {
        return this.Ý;
    }
    
    public void Â(final boolean italic) {
        this.Ý = italic;
    }
    
    public List á() {
        return this.á;
    }
    
    public void HorizonCode_Horizon_È(final File file) throws IOException {
        final PrintStream out = new PrintStream(new FileOutputStream(file));
        out.println("font.size=" + this.HorizonCode_Horizon_È);
        out.println("font.bold=" + this.Â);
        out.println("font.italic=" + this.Ý);
        out.println();
        out.println("pad.top=" + this.Ø­áŒŠá);
        out.println("pad.right=" + this.à);
        out.println("pad.bottom=" + this.Ó);
        out.println("pad.left=" + this.Âµá€);
        out.println("pad.advance.x=" + this.Ø);
        out.println("pad.advance.y=" + this.áŒŠÆ);
        out.println();
        out.println("glyph.page.width=" + this.áˆºÑ¢Õ);
        out.println("glyph.page.height=" + this.ÂµÈ);
        out.println();
        for (final ConfigurableEffect effect : this.á) {
            out.println("effect.class=" + effect.getClass().getName());
            for (final ConfigurableEffect.HorizonCode_Horizon_È value : effect.Â()) {
                out.println("effect." + value.HorizonCode_Horizon_È() + "=" + value.Â());
            }
            out.println();
        }
        out.close();
    }
}
