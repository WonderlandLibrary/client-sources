package ClassSub;

import java.io.*;
import java.util.*;

public class Class182 implements Class297
{
    private static Class311 GL;
    private static final int DISPLAY_LIST_CACHE_SIZE = 200;
    private static final int MAX_CHAR = 255;
    private boolean displayListCaching;
    private Class220 fontImage;
    private Class270[] chars;
    private int lineHeight;
    private int baseDisplayListID;
    private int eldestDisplayListID;
    private Class66 eldestDisplayList;
    private final LinkedHashMap displayLists;
    
    
    public Class182(final String s, final Class220 fontImage) throws Class341 {
        this.displayListCaching = true;
        this.baseDisplayListID = -1;
        this.displayLists = new Class69(this, 200, 1.0f, true);
        this.fontImage = fontImage;
        this.parseFnt(Class337.getResourceAsStream(s));
    }
    
    public Class182(final String s, final String s2) throws Class341 {
        this.displayListCaching = true;
        this.baseDisplayListID = -1;
        this.displayLists = new Class69(this, 200, 1.0f, true);
        this.fontImage = new Class220(s2);
        this.parseFnt(Class337.getResourceAsStream(s));
    }
    
    public Class182(final String s, final Class220 fontImage, final boolean displayListCaching) throws Class341 {
        this.displayListCaching = true;
        this.baseDisplayListID = -1;
        this.displayLists = new Class69(this, 200, 1.0f, true);
        this.fontImage = fontImage;
        this.displayListCaching = displayListCaching;
        this.parseFnt(Class337.getResourceAsStream(s));
    }
    
    public Class182(final String s, final String s2, final boolean displayListCaching) throws Class341 {
        this.displayListCaching = true;
        this.baseDisplayListID = -1;
        this.displayLists = new Class69(this, 200, 1.0f, true);
        this.fontImage = new Class220(s2);
        this.displayListCaching = displayListCaching;
        this.parseFnt(Class337.getResourceAsStream(s));
    }
    
    public Class182(final String s, final InputStream inputStream, final InputStream inputStream2) throws Class341 {
        this.displayListCaching = true;
        this.baseDisplayListID = -1;
        this.displayLists = new Class69(this, 200, 1.0f, true);
        this.fontImage = new Class220(inputStream2, s, false);
        this.parseFnt(inputStream);
    }
    
    public Class182(final String s, final InputStream inputStream, final InputStream inputStream2, final boolean displayListCaching) throws Class341 {
        this.displayListCaching = true;
        this.baseDisplayListID = -1;
        this.displayLists = new Class69(this, 200, 1.0f, true);
        this.fontImage = new Class220(inputStream2, s, false);
        this.displayListCaching = displayListCaching;
        this.parseFnt(inputStream);
    }
    
    private void parseFnt(final InputStream inputStream) throws Class341 {
        if (this.displayListCaching) {
            this.baseDisplayListID = Class182.GL.glGenLists(200);
            if (this.baseDisplayListID == 0) {
                this.displayListCaching = false;
            }
        }
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            bufferedReader.readLine();
            bufferedReader.readLine();
            bufferedReader.readLine();
            final HashMap<Object, Object> hashMap = new HashMap<Object, Object>(64);
            final ArrayList<Class270> list = new ArrayList<Class270>(255);
            int max = 0;
            int i = 0;
            while (i == 0) {
                final String line = bufferedReader.readLine();
                if (line == null) {
                    i = 1;
                }
                else {
                    if (!line.startsWith("chars c")) {
                        if (line.startsWith("char")) {
                            final Class270 char1 = this.parseChar(line);
                            if (char1 != null) {
                                max = Math.max(max, char1.id);
                                list.add(char1);
                            }
                        }
                    }
                    if (line.startsWith("kernings c")) {
                        continue;
                    }
                    if (!line.startsWith("kerning")) {
                        continue;
                    }
                    final StringTokenizer stringTokenizer = new StringTokenizer(line, " =");
                    stringTokenizer.nextToken();
                    stringTokenizer.nextToken();
                    final short short1 = Short.parseShort(stringTokenizer.nextToken());
                    stringTokenizer.nextToken();
                    final int int1 = Integer.parseInt(stringTokenizer.nextToken());
                    stringTokenizer.nextToken();
                    final int int2 = Integer.parseInt(stringTokenizer.nextToken());
                    List<?> list2 = hashMap.get(new Short(short1));
                    if (list2 == null) {
                        list2 = new ArrayList<Object>();
                        hashMap.put(new Short(short1), list2);
                    }
                    list2.add(new Short((short)(int2 << 8 | int1)));
                }
            }
            this.chars = new Class270[max + 1];
            for (final Class270 class270 : list) {
                this.chars[class270.id] = class270;
            }
            for (final Map.Entry<Short, List<Short>> entry : hashMap.entrySet()) {
                final short shortValue = entry.getKey();
                final List<Short> list3 = entry.getValue();
                final short[] kerning = new short[list3.size()];
                int n = 0;
                final Iterator<Short> iterator3 = list3.iterator();
                while (iterator3.hasNext()) {
                    kerning[n] = iterator3.next();
                    ++n;
                }
                this.chars[shortValue].kerning = kerning;
            }
        }
        catch (IOException ex) {
            Class301.error(ex);
            throw new Class341("Failed to parse font file: " + inputStream);
        }
    }
    
    private Class270 parseChar(final String s) throws Class341 {
        final Class270 class270 = new Class270(null);
        final StringTokenizer stringTokenizer = new StringTokenizer(s, " =");
        stringTokenizer.nextToken();
        stringTokenizer.nextToken();
        class270.id = Short.parseShort(stringTokenizer.nextToken());
        if (class270.id < 0) {
            return null;
        }
        if (class270.id > 255) {
            throw new Class341("Invalid character '" + class270.id + "': AngelCodeFont does not support characters above " + 255);
        }
        stringTokenizer.nextToken();
        class270.x = Short.parseShort(stringTokenizer.nextToken());
        stringTokenizer.nextToken();
        class270.y = Short.parseShort(stringTokenizer.nextToken());
        stringTokenizer.nextToken();
        class270.width = Short.parseShort(stringTokenizer.nextToken());
        stringTokenizer.nextToken();
        class270.height = Short.parseShort(stringTokenizer.nextToken());
        stringTokenizer.nextToken();
        class270.xoffset = Short.parseShort(stringTokenizer.nextToken());
        stringTokenizer.nextToken();
        class270.yoffset = Short.parseShort(stringTokenizer.nextToken());
        stringTokenizer.nextToken();
        class270.xadvance = Short.parseShort(stringTokenizer.nextToken());
        class270.init();
        if (class270.id != 32) {
            this.lineHeight = Math.max(class270.height + class270.yoffset, this.lineHeight);
        }
        return class270;
    }
    
    @Override
    public void drawString(final float n, final float n2, final String s) {
        this.drawString(n, n2, s, Class26.white);
    }
    
    @Override
    public void drawString(final float n, final float n2, final String s, final Class26 class26) {
        this.drawString(n, n2, s, class26, 0, s.length() - 1);
    }
    
    @Override
    public void drawString(final float n, final float n2, final String text, final Class26 class26, final int n3, final int n4) {
        this.fontImage.bind();
        class26.bind();
        Class182.GL.glTranslatef(n, n2, 0.0f);
        if (this.displayListCaching && n3 == 0 && n4 == text.length() - 1) {
            final Class66 class27 = this.displayLists.get(text);
            if (class27 != null) {
                Class182.GL.glCallList(class27.id);
            }
            else {
                final Class66 class28 = new Class66(null);
                class28.text = text;
                final int size = this.displayLists.size();
                if (size < 200) {
                    class28.id = this.baseDisplayListID + size;
                }
                else {
                    class28.id = this.eldestDisplayListID;
                    this.displayLists.remove(this.eldestDisplayList.text);
                }
                this.displayLists.put(text, class28);
                Class182.GL.glNewList(class28.id, 4865);
                this.render(text, n3, n4);
                Class182.GL.glEndList();
            }
        }
        else {
            this.render(text, n3, n4);
        }
        Class182.GL.glTranslatef(-n, -n2, 0.0f);
    }
    
    private void render(final String s, final int n, final int n2) {
        Class182.GL.glBegin(7);
        int n3 = 0;
        int n4 = 0;
        Class270 class270 = null;
        final char[] array = s.toCharArray();
        for (int i = 0; i < array.length; ++i) {
            final char c = array[i];
            if (c == '\n') {
                n3 = 0;
                n4 += this.getLineHeight();
            }
            else if (c < this.chars.length) {
                final Class270 class271 = this.chars[c];
                if (class271 != null) {
                    if (class270 != null) {
                        n3 += class270.getKerning(c);
                    }
                    class270 = class271;
                    if (i >= n && i <= n2) {
                        class271.draw(n3, n4);
                    }
                    n3 += class271.xadvance;
                }
            }
        }
        Class182.GL.glEnd();
    }
    
    public int getYOffset(final String s) {
        Class66 class66 = null;
        if (this.displayListCaching) {
            class66 = this.displayLists.get(s);
            if (class66 != null && class66.yOffset != null) {
                return class66.yOffset;
            }
        }
        int n = s.indexOf(10);
        if (n == -1) {
            n = s.length();
        }
        int min = 10000;
        for (int i = 0; i < n; ++i) {
            final Class270 class67 = this.chars[s.charAt(i)];
            if (class67 != null) {
                min = Math.min(class67.yoffset, min);
            }
        }
        if (class66 != null) {
            class66.yOffset = new Short((short)min);
        }
        return min;
    }
    
    @Override
    public int getHeight(final String s) {
        Class66 class66 = null;
        if (this.displayListCaching) {
            class66 = this.displayLists.get(s);
            if (class66 != null && class66.height != null) {
                return class66.height;
            }
        }
        int n = 0;
        int max = 0;
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 == '\n') {
                ++n;
                max = 0;
            }
            else if (char1 != ' ') {
                final Class270 class67 = this.chars[char1];
                if (class67 != null) {
                    max = Math.max(class67.height + class67.yoffset, max);
                }
            }
        }
        final int n2 = max + n * this.getLineHeight();
        if (class66 != null) {
            class66.height = new Short((short)n2);
        }
        return n2;
    }
    
    @Override
    public int getWidth(final String s) {
        Class66 class66 = null;
        if (this.displayListCaching) {
            class66 = this.displayLists.get(s);
            if (class66 != null && class66.width != null) {
                return class66.width;
            }
        }
        int max = 0;
        int n = 0;
        Class270 class67 = null;
        for (int i = 0, length = s.length(); i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 == '\n') {
                n = 0;
            }
            else if (char1 < this.chars.length) {
                final Class270 class68 = this.chars[char1];
                if (class68 != null) {
                    if (class67 != null) {
                        n += class67.getKerning(char1);
                    }
                    class67 = class68;
                    if (i < length - 1) {
                        n += class68.xadvance;
                    }
                    else {
                        n += class68.width;
                    }
                    max = Math.max(max, n);
                }
            }
        }
        if (class66 != null) {
            class66.width = new Short((short)max);
        }
        return max;
    }
    
    @Override
    public int getLineHeight() {
        return this.lineHeight;
    }
    
    static Class66 access$002(final Class182 class182, final Class66 eldestDisplayList) {
        return class182.eldestDisplayList = eldestDisplayList;
    }
    
    static int access$102(final Class182 class182, final int eldestDisplayListID) {
        return class182.eldestDisplayListID = eldestDisplayListID;
    }
    
    static Class66 access$000(final Class182 class182) {
        return class182.eldestDisplayList;
    }
    
    static Class220 access$400(final Class182 class182) {
        return class182.fontImage;
    }
    
    static {
        Class182.GL = Class197.get();
    }
    
    private class Class270
    {
        public short id;
        public short x;
        public short y;
        public short width;
        public short height;
        public short xoffset;
        public short yoffset;
        public short xadvance;
        public Class220 image;
        public short dlIndex;
        public short[] kerning;
        final Class182 this$0;
        
        
        private Class270(final Class182 this$0) {
            this.this$0 = this$0;
        }
        
        public void init() {
            this.image = Class182.access$400(this.this$0).getSubImage(this.x, this.y, this.width, this.height);
        }
        
        @Override
        public String toString() {
            return "[CharDef id=" + this.id + " x=" + this.x + " y=" + this.y + "]";
        }
        
        public void draw(final float n, final float n2) {
            this.image.drawEmbedded(n + this.xoffset, n2 + this.yoffset, this.width, this.height);
        }
        
        public int getKerning(final int n) {
            if (this.kerning == null) {
                return 0;
            }
            int i = 0;
            int n2 = this.kerning.length - 1;
            while (i <= n2) {
                final int n3 = i + n2 >>> 1;
                final short n4 = this.kerning[n3];
                final short n5 = (short)(n4 & 0xFF);
                if (n5 < n) {
                    i = n3 + 1;
                }
                else {
                    if (n5 <= n) {
                        return n4 >> 8;
                    }
                    n2 = n3 - 1;
                }
            }
            return 0;
        }
        
        Class270(final Class182 class182, final Class69 class183) {
            this(class182);
        }
    }
    
    private static class Class66
    {
        int id;
        Short yOffset;
        Short width;
        Short height;
        String text;
        
        
        private Class66() {
        }
        
        Class66(final Class69 class69) {
            this();
        }
    }
}
