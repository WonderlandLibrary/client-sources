package zamorozka.ui;

public class ColoredChar {
    private final char character;
    private int r = 255, g = 255, b = 255;

    public ColoredChar(char character, int r, int g, int b) {
        this.character = character;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public ColoredChar(char character) {
        this.character = character;
    }

    public static ColoredChar[] getFromString(String str) {
        ColoredChar[] ch = new ColoredChar[str.length()];
        char[] s = str.toCharArray();

        for (int i = 0; i < ch.length; i++) {
            ch[i] = new ColoredChar(s[i]);
        }

        return ch;
    }

    public void setColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public char getChar() {
        return character;
    }

    public float getR() {
        return r / 255F;
    }

    public float getG() {
        return g / 255F;
    }

    public float getB() {
        return b / 255F;
    }
}
