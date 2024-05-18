package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Map;
import java.util.LinkedHashMap;

public class AngelCodeFont implements Font
{
    private static SGL HorizonCode_Horizon_È;
    private static final int Â = 200;
    private static final int Ý = 255;
    private boolean Ø­áŒŠá;
    private Image Âµá€;
    private HorizonCode_Horizon_È[] Ó;
    private int à;
    private int Ø;
    private int áŒŠÆ;
    private Â áˆºÑ¢Õ;
    private final LinkedHashMap ÂµÈ;
    
    static {
        AngelCodeFont.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
    }
    
    public AngelCodeFont(final String fntFile, final Image image) throws SlickException {
        this.Ø­áŒŠá = true;
        this.Ø = -1;
        this.ÂµÈ = new LinkedHashMap(200, 1.0f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry eldest) {
                AngelCodeFont.HorizonCode_Horizon_È(AngelCodeFont.this, eldest.getValue());
                AngelCodeFont.HorizonCode_Horizon_È(AngelCodeFont.this, AngelCodeFont.this.áˆºÑ¢Õ.HorizonCode_Horizon_È);
                return false;
            }
        };
        this.Âµá€ = image;
        this.HorizonCode_Horizon_È(ResourceLoader.HorizonCode_Horizon_È(fntFile));
    }
    
    public AngelCodeFont(final String fntFile, final String imgFile) throws SlickException {
        this.Ø­áŒŠá = true;
        this.Ø = -1;
        this.ÂµÈ = new LinkedHashMap(200, 1.0f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry eldest) {
                AngelCodeFont.HorizonCode_Horizon_È(AngelCodeFont.this, eldest.getValue());
                AngelCodeFont.HorizonCode_Horizon_È(AngelCodeFont.this, AngelCodeFont.this.áˆºÑ¢Õ.HorizonCode_Horizon_È);
                return false;
            }
        };
        this.Âµá€ = new Image(imgFile);
        this.HorizonCode_Horizon_È(ResourceLoader.HorizonCode_Horizon_È(fntFile));
    }
    
    public AngelCodeFont(final String fntFile, final Image image, final boolean caching) throws SlickException {
        this.Ø­áŒŠá = true;
        this.Ø = -1;
        this.ÂµÈ = new LinkedHashMap(200, 1.0f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry eldest) {
                AngelCodeFont.HorizonCode_Horizon_È(AngelCodeFont.this, eldest.getValue());
                AngelCodeFont.HorizonCode_Horizon_È(AngelCodeFont.this, AngelCodeFont.this.áˆºÑ¢Õ.HorizonCode_Horizon_È);
                return false;
            }
        };
        this.Âµá€ = image;
        this.Ø­áŒŠá = caching;
        this.HorizonCode_Horizon_È(ResourceLoader.HorizonCode_Horizon_È(fntFile));
    }
    
    public AngelCodeFont(final String fntFile, final String imgFile, final boolean caching) throws SlickException {
        this.Ø­áŒŠá = true;
        this.Ø = -1;
        this.ÂµÈ = new LinkedHashMap(200, 1.0f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry eldest) {
                AngelCodeFont.HorizonCode_Horizon_È(AngelCodeFont.this, eldest.getValue());
                AngelCodeFont.HorizonCode_Horizon_È(AngelCodeFont.this, AngelCodeFont.this.áˆºÑ¢Õ.HorizonCode_Horizon_È);
                return false;
            }
        };
        this.Âµá€ = new Image(imgFile);
        this.Ø­áŒŠá = caching;
        this.HorizonCode_Horizon_È(ResourceLoader.HorizonCode_Horizon_È(fntFile));
    }
    
    public AngelCodeFont(final String name, final InputStream fntFile, final InputStream imgFile) throws SlickException {
        this.Ø­áŒŠá = true;
        this.Ø = -1;
        this.ÂµÈ = new LinkedHashMap(200, 1.0f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry eldest) {
                AngelCodeFont.HorizonCode_Horizon_È(AngelCodeFont.this, eldest.getValue());
                AngelCodeFont.HorizonCode_Horizon_È(AngelCodeFont.this, AngelCodeFont.this.áˆºÑ¢Õ.HorizonCode_Horizon_È);
                return false;
            }
        };
        this.Âµá€ = new Image(imgFile, name, false);
        this.HorizonCode_Horizon_È(fntFile);
    }
    
    public AngelCodeFont(final String name, final InputStream fntFile, final InputStream imgFile, final boolean caching) throws SlickException {
        this.Ø­áŒŠá = true;
        this.Ø = -1;
        this.ÂµÈ = new LinkedHashMap(200, 1.0f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry eldest) {
                AngelCodeFont.HorizonCode_Horizon_È(AngelCodeFont.this, eldest.getValue());
                AngelCodeFont.HorizonCode_Horizon_È(AngelCodeFont.this, AngelCodeFont.this.áˆºÑ¢Õ.HorizonCode_Horizon_È);
                return false;
            }
        };
        this.Âµá€ = new Image(imgFile, name, false);
        this.Ø­áŒŠá = caching;
        this.HorizonCode_Horizon_È(fntFile);
    }
    
    private void HorizonCode_Horizon_È(final InputStream fntFile) throws SlickException {
        if (this.Ø­áŒŠá) {
            this.Ø = AngelCodeFont.HorizonCode_Horizon_È.Ó(200);
            if (this.Ø == 0) {
                this.Ø­áŒŠá = false;
            }
        }
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(fntFile));
            final String info = in.readLine();
            final String common = in.readLine();
            final String page = in.readLine();
            final Map kerning = new HashMap(64);
            final List charDefs = new ArrayList(255);
            int maxChar = 0;
            boolean done = false;
            while (!done) {
                final String line = in.readLine();
                if (line == null) {
                    done = true;
                }
                else {
                    if (!line.startsWith("chars c") && line.startsWith("char")) {
                        final HorizonCode_Horizon_È def = this.Ø­áŒŠá(line);
                        if (def != null) {
                            maxChar = Math.max(maxChar, def.HorizonCode_Horizon_È);
                            charDefs.add(def);
                        }
                    }
                    if (line.startsWith("kernings c") || !line.startsWith("kerning")) {
                        continue;
                    }
                    final StringTokenizer tokens = new StringTokenizer(line, " =");
                    tokens.nextToken();
                    tokens.nextToken();
                    final short first = Short.parseShort(tokens.nextToken());
                    tokens.nextToken();
                    final int second = Integer.parseInt(tokens.nextToken());
                    tokens.nextToken();
                    final int offset = Integer.parseInt(tokens.nextToken());
                    List values = kerning.get(new Short(first));
                    if (values == null) {
                        values = new ArrayList();
                        kerning.put(new Short(first), values);
                    }
                    values.add(new Short((short)(offset << 8 | second)));
                }
            }
            this.Ó = new HorizonCode_Horizon_È[maxChar + 1];
            Iterator iter = charDefs.iterator();
            while (iter.hasNext()) {
                final HorizonCode_Horizon_È def = iter.next();
                this.Ó[def.HorizonCode_Horizon_È] = def;
            }
            iter = kerning.entrySet().iterator();
            while (iter.hasNext()) {
                final Map.Entry entry = iter.next();
                final short first = entry.getKey();
                final List valueList = entry.getValue();
                final short[] valueArray = new short[valueList.size()];
                int i = 0;
                final Iterator valueIter = valueList.iterator();
                while (valueIter.hasNext()) {
                    valueArray[i] = valueIter.next();
                    ++i;
                }
                this.Ó[first].ÂµÈ = valueArray;
            }
        }
        catch (IOException e) {
            Log.HorizonCode_Horizon_È(e);
            throw new SlickException("Failed to parse font file: " + fntFile);
        }
    }
    
    private HorizonCode_Horizon_È Ø­áŒŠá(final String line) throws SlickException {
        final HorizonCode_Horizon_È def = new HorizonCode_Horizon_È((HorizonCode_Horizon_È)null);
        final StringTokenizer tokens = new StringTokenizer(line, " =");
        tokens.nextToken();
        tokens.nextToken();
        def.HorizonCode_Horizon_È = Short.parseShort(tokens.nextToken());
        if (def.HorizonCode_Horizon_È < 0) {
            return null;
        }
        if (def.HorizonCode_Horizon_È > 255) {
            throw new SlickException("Invalid character '" + def.HorizonCode_Horizon_È + "': AngelCodeFont does not support characters above " + 255);
        }
        tokens.nextToken();
        def.Â = Short.parseShort(tokens.nextToken());
        tokens.nextToken();
        def.Ý = Short.parseShort(tokens.nextToken());
        tokens.nextToken();
        def.Ø­áŒŠá = Short.parseShort(tokens.nextToken());
        tokens.nextToken();
        def.Âµá€ = Short.parseShort(tokens.nextToken());
        tokens.nextToken();
        def.Ó = Short.parseShort(tokens.nextToken());
        tokens.nextToken();
        def.à = Short.parseShort(tokens.nextToken());
        tokens.nextToken();
        def.Ø = Short.parseShort(tokens.nextToken());
        def.HorizonCode_Horizon_È();
        if (def.HorizonCode_Horizon_È != 32) {
            this.à = Math.max(def.Âµá€ + def.à, this.à);
        }
        return def;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final String text) {
        this.HorizonCode_Horizon_È(x, y, text, Color.Ý);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final String text, final Color col) {
        this.HorizonCode_Horizon_È(x, y, text, col, 0, text.length() - 1);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final String text, final Color col, final int startIndex, final int endIndex) {
        this.Âµá€.Â();
        col.HorizonCode_Horizon_È();
        AngelCodeFont.HorizonCode_Horizon_È.Â(x, y, 0.0f);
        if (this.Ø­áŒŠá && startIndex == 0 && endIndex == text.length() - 1) {
            Â displayList = this.ÂµÈ.get(text);
            if (displayList != null) {
                AngelCodeFont.HorizonCode_Horizon_È.Â(displayList.HorizonCode_Horizon_È);
            }
            else {
                displayList = new Â(null);
                displayList.Âµá€ = text;
                final int displayListCount = this.ÂµÈ.size();
                if (displayListCount < 200) {
                    displayList.HorizonCode_Horizon_È = this.Ø + displayListCount;
                }
                else {
                    displayList.HorizonCode_Horizon_È = this.áŒŠÆ;
                    this.ÂµÈ.remove(this.áˆºÑ¢Õ.Âµá€);
                }
                this.ÂµÈ.put(text, displayList);
                AngelCodeFont.HorizonCode_Horizon_È.Âµá€(displayList.HorizonCode_Horizon_È, 4865);
                this.HorizonCode_Horizon_È(text, startIndex, endIndex);
                AngelCodeFont.HorizonCode_Horizon_È.Â();
            }
        }
        else {
            this.HorizonCode_Horizon_È(text, startIndex, endIndex);
        }
        AngelCodeFont.HorizonCode_Horizon_È.Â(-x, -y, 0.0f);
    }
    
    private void HorizonCode_Horizon_È(final String text, final int start, final int end) {
        AngelCodeFont.HorizonCode_Horizon_È.HorizonCode_Horizon_È(7);
        int x = 0;
        int y = 0;
        HorizonCode_Horizon_È lastCharDef = null;
        final char[] data = text.toCharArray();
        for (int i = 0; i < data.length; ++i) {
            final int id = data[i];
            if (id == 10) {
                x = 0;
                y += this.HorizonCode_Horizon_È();
            }
            else if (id < this.Ó.length) {
                final HorizonCode_Horizon_È charDef = this.Ó[id];
                if (charDef != null) {
                    if (lastCharDef != null) {
                        x += lastCharDef.HorizonCode_Horizon_È(id);
                    }
                    lastCharDef = charDef;
                    if (i >= start && i <= end) {
                        charDef.HorizonCode_Horizon_È(x, y);
                    }
                    x += charDef.Ø;
                }
            }
        }
        AngelCodeFont.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
    
    public int HorizonCode_Horizon_È(final String text) {
        Â displayList = null;
        if (this.Ø­áŒŠá) {
            displayList = this.ÂµÈ.get(text);
            if (displayList != null && displayList.Â != null) {
                return displayList.Â;
            }
        }
        int stopIndex = text.indexOf(10);
        if (stopIndex == -1) {
            stopIndex = text.length();
        }
        int minYOffset = 10000;
        for (int i = 0; i < stopIndex; ++i) {
            final int id = text.charAt(i);
            final HorizonCode_Horizon_È charDef = this.Ó[id];
            if (charDef != null) {
                minYOffset = Math.min(charDef.à, minYOffset);
            }
        }
        if (displayList != null) {
            displayList.Â = new Short((short)minYOffset);
        }
        return minYOffset;
    }
    
    @Override
    public int Â(final String text) {
        Â displayList = null;
        if (this.Ø­áŒŠá) {
            displayList = this.ÂµÈ.get(text);
            if (displayList != null && displayList.Ø­áŒŠá != null) {
                return displayList.Ø­áŒŠá;
            }
        }
        int lines = 0;
        int maxHeight = 0;
        for (int i = 0; i < text.length(); ++i) {
            final int id = text.charAt(i);
            if (id == 10) {
                ++lines;
                maxHeight = 0;
            }
            else if (id != 32) {
                final HorizonCode_Horizon_È charDef = this.Ó[id];
                if (charDef != null) {
                    maxHeight = Math.max(charDef.Âµá€ + charDef.à, maxHeight);
                }
            }
        }
        maxHeight += lines * this.HorizonCode_Horizon_È();
        if (displayList != null) {
            displayList.Ø­áŒŠá = new Short((short)maxHeight);
        }
        return maxHeight;
    }
    
    @Override
    public int Ý(final String text) {
        Â displayList = null;
        if (this.Ø­áŒŠá) {
            displayList = this.ÂµÈ.get(text);
            if (displayList != null && displayList.Ý != null) {
                return displayList.Ý;
            }
        }
        int maxWidth = 0;
        int width = 0;
        HorizonCode_Horizon_È lastCharDef = null;
        for (int i = 0, n = text.length(); i < n; ++i) {
            final int id = text.charAt(i);
            if (id == 10) {
                width = 0;
            }
            else if (id < this.Ó.length) {
                final HorizonCode_Horizon_È charDef = this.Ó[id];
                if (charDef != null) {
                    if (lastCharDef != null) {
                        width += lastCharDef.HorizonCode_Horizon_È(id);
                    }
                    lastCharDef = charDef;
                    if (i < n - 1) {
                        width += charDef.Ø;
                    }
                    else {
                        width += charDef.Ø­áŒŠá;
                    }
                    maxWidth = Math.max(maxWidth, width);
                }
            }
        }
        if (displayList != null) {
            displayList.Ý = new Short((short)maxWidth);
        }
        return maxWidth;
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return this.à;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final AngelCodeFont angelCodeFont, final Â áˆºÑ¢Õ) {
        angelCodeFont.áˆºÑ¢Õ = áˆºÑ¢Õ;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final AngelCodeFont angelCodeFont, final int áœšæ) {
        angelCodeFont.áŒŠÆ = áœšæ;
    }
    
    private class HorizonCode_Horizon_È
    {
        public short HorizonCode_Horizon_È;
        public short Â;
        public short Ý;
        public short Ø­áŒŠá;
        public short Âµá€;
        public short Ó;
        public short à;
        public short Ø;
        public Image áŒŠÆ;
        public short áˆºÑ¢Õ;
        public short[] ÂµÈ;
        
        public void HorizonCode_Horizon_È() {
            this.áŒŠÆ = AngelCodeFont.this.Âµá€.HorizonCode_Horizon_È(this.Â, this.Ý, this.Ø­áŒŠá, this.Âµá€);
        }
        
        @Override
        public String toString() {
            return "[CharDef id=" + this.HorizonCode_Horizon_È + " x=" + this.Â + " y=" + this.Ý + "]";
        }
        
        public void HorizonCode_Horizon_È(final float x, final float y) {
            this.áŒŠÆ.Â(x + this.Ó, y + this.à, this.Ø­áŒŠá, this.Âµá€);
        }
        
        public int HorizonCode_Horizon_È(final int otherCodePoint) {
            if (this.ÂµÈ == null) {
                return 0;
            }
            int low = 0;
            int high = this.ÂµÈ.length - 1;
            while (low <= high) {
                final int midIndex = low + high >>> 1;
                final int value = this.ÂµÈ[midIndex];
                final int foundCodePoint = value & 0xFF;
                if (foundCodePoint < otherCodePoint) {
                    low = midIndex + 1;
                }
                else {
                    if (foundCodePoint <= otherCodePoint) {
                        return value >> 8;
                    }
                    high = midIndex - 1;
                }
            }
            return 0;
        }
    }
    
    private static class Â
    {
        int HorizonCode_Horizon_È;
        Short Â;
        Short Ý;
        Short Ø­áŒŠá;
        String Âµá€;
    }
}
