package ClassSub;

import java.util.*;
import java.io.*;

public class Class258
{
    private int fontSize;
    private boolean bold;
    private boolean italic;
    private int paddingTop;
    private int paddingLeft;
    private int paddingBottom;
    private int paddingRight;
    private int paddingAdvanceX;
    private int paddingAdvanceY;
    private int glyphPageWidth;
    private int glyphPageHeight;
    private final List effects;
    
    
    public Class258() {
        this.fontSize = 12;
        this.bold = false;
        this.italic = false;
        this.glyphPageWidth = 512;
        this.glyphPageHeight = 512;
        this.effects = new ArrayList();
    }
    
    public Class258(final String s) throws Class341 {
        this(Class337.getResourceAsStream(s));
    }
    
    public Class258(final InputStream inputStream) throws Class341 {
        this.fontSize = 12;
        this.bold = false;
        this.italic = false;
        this.glyphPageWidth = 512;
        this.glyphPageHeight = 512;
        this.effects = new ArrayList();
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                final String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                final String trim = line.trim();
                if (trim.length() == 0) {
                    continue;
                }
                final String[] array = trim.split("=", 2);
                final String trim2 = array[0].trim();
                final String string = array[1];
                if (trim2.equals("font.size")) {
                    this.fontSize = Integer.parseInt(string);
                }
                else if (trim2.equals("font.bold")) {
                    this.bold = Boolean.valueOf(string);
                }
                else if (trim2.equals("font.italic")) {
                    this.italic = Boolean.valueOf(string);
                }
                else if (trim2.equals("pad.top")) {
                    this.paddingTop = Integer.parseInt(string);
                }
                else if (trim2.equals("pad.right")) {
                    this.paddingRight = Integer.parseInt(string);
                }
                else if (trim2.equals("pad.bottom")) {
                    this.paddingBottom = Integer.parseInt(string);
                }
                else if (trim2.equals("pad.left")) {
                    this.paddingLeft = Integer.parseInt(string);
                }
                else if (trim2.equals("pad.advance.x")) {
                    this.paddingAdvanceX = Integer.parseInt(string);
                }
                else if (trim2.equals("pad.advance.y")) {
                    this.paddingAdvanceY = Integer.parseInt(string);
                }
                else if (trim2.equals("glyph.page.width")) {
                    this.glyphPageWidth = Integer.parseInt(string);
                }
                else if (trim2.equals("glyph.page.height")) {
                    this.glyphPageHeight = Integer.parseInt(string);
                }
                else {
                    if (trim2.equals("effect.class")) {
                        try {
                            this.effects.add(Class.forName(string).newInstance());
                            continue;
                        }
                        catch (Exception ex) {
                            throw new Class341("Unable to create effect instance: " + string, ex);
                        }
                    }
                    if (!trim2.startsWith("effect.")) {
                        continue;
                    }
                    final String substring = trim2.substring(7);
                    final Class193 class193 = this.effects.get(this.effects.size() - 1);
                    final List values = class193.getValues();
                    for (final Class193.Class111 class194 : values) {
                        if (class194.getName().equals(substring)) {
                            class194.setString(string);
                            break;
                        }
                    }
                    class193.setValues(values);
                }
            }
            bufferedReader.close();
        }
        catch (Exception ex2) {
            throw new Class341("Unable to load Hiero font file", ex2);
        }
    }
    
    public int getPaddingTop() {
        return this.paddingTop;
    }
    
    public void setPaddingTop(final int paddingTop) {
        this.paddingTop = paddingTop;
    }
    
    public int getPaddingLeft() {
        return this.paddingLeft;
    }
    
    public void setPaddingLeft(final int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }
    
    public int getPaddingBottom() {
        return this.paddingBottom;
    }
    
    public void setPaddingBottom(final int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }
    
    public int getPaddingRight() {
        return this.paddingRight;
    }
    
    public void setPaddingRight(final int paddingRight) {
        this.paddingRight = paddingRight;
    }
    
    public int getPaddingAdvanceX() {
        return this.paddingAdvanceX;
    }
    
    public void setPaddingAdvanceX(final int paddingAdvanceX) {
        this.paddingAdvanceX = paddingAdvanceX;
    }
    
    public int getPaddingAdvanceY() {
        return this.paddingAdvanceY;
    }
    
    public void setPaddingAdvanceY(final int paddingAdvanceY) {
        this.paddingAdvanceY = paddingAdvanceY;
    }
    
    public int getGlyphPageWidth() {
        return this.glyphPageWidth;
    }
    
    public void setGlyphPageWidth(final int glyphPageWidth) {
        this.glyphPageWidth = glyphPageWidth;
    }
    
    public int getGlyphPageHeight() {
        return this.glyphPageHeight;
    }
    
    public void setGlyphPageHeight(final int glyphPageHeight) {
        this.glyphPageHeight = glyphPageHeight;
    }
    
    public int getFontSize() {
        return this.fontSize;
    }
    
    public void setFontSize(final int fontSize) {
        this.fontSize = fontSize;
    }
    
    public boolean isBold() {
        return this.bold;
    }
    
    public void setBold(final boolean bold) {
        this.bold = bold;
    }
    
    public boolean isItalic() {
        return this.italic;
    }
    
    public void setItalic(final boolean italic) {
        this.italic = italic;
    }
    
    public List getEffects() {
        return this.effects;
    }
    
    public void save(final File file) throws IOException {
        final PrintStream printStream = new PrintStream(new FileOutputStream(file));
        printStream.println("font.size=" + this.fontSize);
        printStream.println("font.bold=" + this.bold);
        printStream.println("font.italic=" + this.italic);
        printStream.println();
        printStream.println("pad.top=" + this.paddingTop);
        printStream.println("pad.right=" + this.paddingRight);
        printStream.println("pad.bottom=" + this.paddingBottom);
        printStream.println("pad.left=" + this.paddingLeft);
        printStream.println("pad.advance.x=" + this.paddingAdvanceX);
        printStream.println("pad.advance.y=" + this.paddingAdvanceY);
        printStream.println();
        printStream.println("glyph.page.width=" + this.glyphPageWidth);
        printStream.println("glyph.page.height=" + this.glyphPageHeight);
        printStream.println();
        for (final Class193 class193 : this.effects) {
            printStream.println("effect.class=" + class193.getClass().getName());
            for (final Class193.Class111 class194 : class193.getValues()) {
                printStream.println("effect." + class194.getName() + "=" + class194.getString());
            }
            printStream.println();
        }
        printStream.close();
    }
}
