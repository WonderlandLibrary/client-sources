package xyz.vergoclient.ui.fonts;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;


public class JelloFontRenderer {

    public static JelloFontRenderer createFontRenderer(Font font) {
        return new MinecraftFontRenderer(font, true, true);
    }

    public int drawString(String text, double d, float y2, int color) {
        text = text.replaceAll("\u00c3\u201a", "");
        return 0;
    }
    
    public int drawPassword(String text, double d, float y2, int color) {
        text = text.replaceAll("\u00c3\u201a", "");
        return 0;
    }
    
    public int drawNoBSString(String text, double d, float y2, int color) {
        text = text.replaceAll("\u00c3\u201a", "");
        return 0;
    }
    
    public int drawSmoothString(String text, double d, float y2, int color) {
        text = text.replaceAll("\u00c3\u201a", "");
        return 0;
    }

    public int drawStringWithShadow(String text, double d, float y2, int color) {
        text = text.replaceAll("\u00c3\u201a", "");
        return 0;
    }

    public float drawNoBSCenteredString(String text, float x2, float y2, int color) {
        text = text.replaceAll("\u00c3\u201a", "");
        return 0;
    }
    public float drawCenteredString(String text, float x2, float y2, int color) {
        text = text.replaceAll("\u00c3\u201a", "");
        return 0;
    }
    public float drawCenteredStringWithShadow(String text, float x2, float y2, int color) {
        text = text.replaceAll("\u00c3\u201a", "");
        return 0;
    }
    public double getStringWidth(String text) {
        text = text.replaceAll("\u00c3\u201a", "");
        return 0;
    }
    
    public double getPasswordWidth(String text) {
        text = text.replaceAll("\u00c3\u201a", "");
        return 0;
    }

    public int getHeight() {
        return 0;
    }

    public static Font createFontFromFile(String name, int size) {
        Font f2;
        try {
            f2 = Font.createFont(0, new Object().getClass().getResourceAsStream("/" + name + ".ttf"));
        }
        catch (Exception e2) {
            return null;
        }
        f2 = f2.deriveFont(0, size);
        return f2;
    }


    public String trimStringToWidth(String p_78262_1_, int p_78262_2_, boolean p_78262_3_) {
    	p_78262_1_ = p_78262_1_.replaceAll("\u00c3\u201a", "");
        return "";
    }
    
    public String trimStringToWidth(String p_78262_1_, int p_78262_2_) {
    	p_78262_1_ = p_78262_1_.replaceAll("\u00c3\u201a", "");
        return "";
    }
    
    public String trimStringToWidthPassword(String p_78262_1_, int p_78262_2_, boolean custom) {
    	p_78262_1_ = p_78262_1_.replaceAll("\u00c3\u201a", "");
        return "";
    }

    public List<String> wrapWords(String text, double width) {
        ArrayList<String> finalWords = new ArrayList<>();

        if (FontUtil.comfortaaSmall.getStringWidth(text) > width) {
            String[] words = text.split(" ");
            StringBuilder currentWord = new StringBuilder();
            char lastColorCode = 65535;

            for (String word : words) {
                for (int innerIndex = 0; innerIndex < word.toCharArray().length; innerIndex++) {
                    char c = word.toCharArray()[innerIndex];

                    if (c == '\u00a7' && innerIndex < word.toCharArray().length - 1) {
                        lastColorCode = word.toCharArray()[innerIndex + 1];
                    }
                }

                if (FontUtil.comfortaaSmall.getStringWidth(currentWord + word + " ") < width) {
                    currentWord.append(word).append(" ");
                } else {
                    finalWords.add(currentWord.toString());
                    currentWord = new StringBuilder("\u00a7" + lastColorCode + word + " ");
                }
            }

            if (currentWord.length() > 0) {
                if (FontUtil.comfortaaSmall.getStringWidth(currentWord.toString()) < width) {
                    finalWords.add("\u00a7" + lastColorCode + currentWord + " ");
                    currentWord = new StringBuilder();
                } else {
                    finalWords.addAll(formatString(currentWord.toString(), width));
                }
            }
        } else {
            finalWords.add(text);
        }

        return finalWords;
    }

    public List<String> formatString(String string, double width) {
        ArrayList<String> finalWords = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();
        char lastColorCode = 65535;
        char[] chars = string.toCharArray();

        for (int index = 0; index < chars.length; index++) {
            char c = chars[index];

            if (c == '\u00a7' && index < chars.length - 1) {
                lastColorCode = chars[index + 1];
            }

            if (FontUtil.comfortaaSmall.getStringWidth(currentWord.toString() + c) < width) {
                currentWord.append(c);
            } else {
                finalWords.add(currentWord.toString());
                currentWord = new StringBuilder("\u00a7" + lastColorCode + c);
            }
        }

        if (currentWord.length() > 0) {
            finalWords.add(currentWord.toString());
        }

        return finalWords;
    }
    
    public int FONT_HEIGHT = 9;
    
}

