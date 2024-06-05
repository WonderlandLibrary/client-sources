package zamorozka.ui;

public enum ColorsUtil {

    BLACK(-16711423),
    BLUE(-12028161),
    DARKBLUE(-12621684),
    GREEN(-9830551),
    DARKGREEN(-9320847),
    WHITE(-65794),
    AQUA(-7820064),
    DARKAQUA(-12621684),
    GREY(-9868951),
    DARKGREY(-14342875),
    RED(-65536),
    DARKRED(-8388608),
    ORANGE(35840),
    DARKORANGE(-2263808),
    YELLOW(65280),
    DARKYELLOW(-2702025),
    MAGENTA(46785),
    DARKMAGENTA(-2252579);

    public static int getBlue = 0x114FFD;
    public static int getGreen = 0x2FFF11;
    public static int getRed = 0xFE0301;
    public static int getGrey = 0xD3D3D3;
    public static int getWhite = 0xffffff;
    public int c;

    ColorsUtil(int co) {
        c = co;
    }

    public static int getRed() {
        return getRed;
    }

    public static int getGreen() {
        return getBlue;
    }

    public static int getBlue() {
        return getBlue;
    }

    public static int getGrey() {
        return getGrey;
    }

    public static int getWhite() {
        return getWhite;
    }

    public static class IntColors {
        public static int getBlack() {
            return -16711423;
        }

        public static int getBlue() {
            return -12028161;
        }

        public static int getDarkBlue() {
            return -12621684;
        }

        public static int getGreen() {
            return -9830551;
        }

        public static int getDarkGreen() {
            return -9320847;
        }

        public static int getWhite() {
            return -65794;
        }

        public static int getAqua() {
            return -7820064;
        }

        public static int getDarkAqua() {
            return -12621684;
        }

        public static int getGrey() {
            return -9868951;
        }

        public static int getDarkGrey() {
            return -14342875;
        }

        public static int getRed() {
            return -65536;
        }

        public static int getDarkRed() {
            return -8388608;
        }

        public static int getOrange() {
            return 35840;
        }

        public static int getDarkOrange() {
            return -2263808;
        }

        public static int getYellow() {
            return 65280;
        }

        public static int getDarkYellow() {
            return -2702025;
        }

        public static int getMagenta() {
            return 46785;
        }

        public static int getDarkMagenta() {
            return -2252579;
        }

        public static int getCO() {
            return -830000;
        }

    }
}
