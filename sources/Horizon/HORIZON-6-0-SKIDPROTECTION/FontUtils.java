package HORIZON-6-0-SKIDPROTECTION;

public class FontUtils
{
    public static void HorizonCode_Horizon_È(final Font font, final String s, final int x, final int y) {
        HorizonCode_Horizon_È(font, s, 1, x, y, 0, Color.Ý);
    }
    
    public static void HorizonCode_Horizon_È(final Font font, final String s, final int x, final int y, final int width) {
        HorizonCode_Horizon_È(font, s, 2, x, y, width, Color.Ý);
    }
    
    public static void HorizonCode_Horizon_È(final Font font, final String s, final int x, final int y, final int width, final Color color) {
        HorizonCode_Horizon_È(font, s, 2, x, y, width, color);
    }
    
    public static void Â(final Font font, final String s, final int x, final int y, final int width) {
        HorizonCode_Horizon_È(font, s, 3, x, y, width, Color.Ý);
    }
    
    public static void Â(final Font font, final String s, final int x, final int y, final int width, final Color color) {
        HorizonCode_Horizon_È(font, s, 3, x, y, width, color);
    }
    
    public static final int HorizonCode_Horizon_È(final Font font, final String s, final int alignment, final int x, final int y, final int width, final Color color) {
        final int resultingXCoordinate = 0;
        if (alignment == 1) {
            font.HorizonCode_Horizon_È(x, y, s, color);
        }
        else if (alignment == 2) {
            font.HorizonCode_Horizon_È(x + width / 2 - font.Ý(s) / 2, y, s, color);
        }
        else if (alignment == 3) {
            font.HorizonCode_Horizon_È(x + width - font.Ý(s), y, s, color);
        }
        else if (alignment == 4) {
            final int leftWidth = width - font.Ý(s);
            if (leftWidth <= 0) {
                font.HorizonCode_Horizon_È(x, y, s, color);
            }
            return Ý(font, s, x, y, HorizonCode_Horizon_È(font, s, leftWidth));
        }
        return resultingXCoordinate;
    }
    
    private static int HorizonCode_Horizon_È(final Font font, final String s, final int leftWidth) {
        int space = 0;
        int curpos = 0;
        while (curpos < s.length()) {
            if (s.charAt(curpos++) == ' ') {
                ++space;
            }
        }
        if (space > 0) {
            space = (leftWidth + font.Ý(" ") * space) / space;
        }
        return space;
    }
    
    private static int Ý(final Font font, final String s, final int x, final int y, final int justifiedSpaceWidth) {
        int curpos = 0;
        int endpos = 0;
        int resultingXCoordinate = x;
        while (curpos < s.length()) {
            endpos = s.indexOf(32, curpos);
            if (endpos == -1) {
                endpos = s.length();
            }
            final String substring = s.substring(curpos, endpos);
            font.HorizonCode_Horizon_È(resultingXCoordinate, y, substring);
            resultingXCoordinate += font.Ý(substring) + justifiedSpaceWidth;
            curpos = endpos + 1;
        }
        return resultingXCoordinate;
    }
    
    public class HorizonCode_Horizon_È
    {
        public static final int HorizonCode_Horizon_È = 1;
        public static final int Â = 2;
        public static final int Ý = 3;
        public static final int Ø­áŒŠá = 4;
    }
}
