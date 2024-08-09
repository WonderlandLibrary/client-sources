package wtf.shiyeno.util.font;

import lombok.SneakyThrows;
import wtf.shiyeno.util.font.common.Lang;
import wtf.shiyeno.util.font.styled.StyledFont;


public class Fonts {
    public static final String FONT_DIR = "/assets/minecraft/shiyeno/font/";

    public static volatile StyledFont[] minecraft = new StyledFont[24];
    public static volatile StyledFont[] verdana = new StyledFont[24];
    public static volatile StyledFont[] gilroyBold = new StyledFont[24];
    public static volatile StyledFont[] msBold = new StyledFont[24];
    public static volatile StyledFont[] msMedium = new StyledFont[24];
    public static volatile StyledFont[] msLight = new StyledFont[24];
    public static volatile StyledFont[] msRegular = new StyledFont[24];
    public static volatile StyledFont[] msSemiBold = new StyledFont[24];
    public static volatile StyledFont[] sfbold = new StyledFont[24];
    public static volatile StyledFont[] logo = new StyledFont[131];

    public static volatile StyledFont[] gilroy = new StyledFont[24];
    public static volatile StyledFont[] sora = new StyledFont[24];
    public static volatile StyledFont[] woveline = new StyledFont[24];
    public static volatile StyledFont[] icons = new StyledFont[24];
    public static volatile StyledFont[] configIcon = new StyledFont[24];

    public static volatile StyledFont[] icons1 = new StyledFont[131];
    public static volatile StyledFont[] icons2 = new StyledFont[131];
    public static volatile StyledFont[] icons3 = new StyledFont[131];
    public static volatile StyledFont[] mainMenuIcons = new StyledFont[131];

    public static volatile StyledFont[] graycliff = new StyledFont[80];

    @SneakyThrows
    public static void init() {
        long time = System.currentTimeMillis();

        minecraft[8] = new StyledFont("mc.ttf", 8, 0.0f, 0.0f, 0.0f, false, Lang.ENG_RU);
        icons[16] = new StyledFont("penus.ttf", 16, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        icons[12] = new StyledFont("penus.ttf", 12, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        woveline[19] = new StyledFont("woveline.otf", 19, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        icons1[130] = new StyledFont("icons.ttf", 130, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        logo[130] = new StyledFont("logo.ttf", 130, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        icons2[130] = new StyledFont("icons2.ttf", 130, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        icons3[130] = new StyledFont("penisfonts.ttf", 130, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        mainMenuIcons[130] = new StyledFont("rofl.ttf", 130, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        for (int i = 8; i < 24;i++) {
            icons1[i] = new StyledFont("icons.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        for (int i = 8; i < 24;i++) {
            icons2[i] = new StyledFont("icons2.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        for (int i = 8; i < 32;i++) {
            icons3[i] = new StyledFont("penisfonts.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        for (int i = 8; i < 32;i++) {
            mainMenuIcons[i] = new StyledFont("rofl.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        for (int i = 8; i < 16;i++) {
            verdana[i] = new StyledFont("verdana.ttf", i, 0.0f, 0.0f, 0.0f, false, Lang.ENG_RU);
        }
        for (int i = 10; i < 23;i++) {
            sora[i] = new StyledFont("sora.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        for (int i = 10; i < 23;i++) {
            configIcon[i] = new StyledFont("Glyphter.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        for (int i = 10; i < 23;i++) {
            gilroyBold[i] = new StyledFont("gilroy-bold.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        for (int i = 10; i < 24;i++) {
            gilroy[i] = new StyledFont("gilroy.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        for (int i = 8; i < 24;i++) {
            msBold[i] = new StyledFont("Inter-Bold.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        for (int i = 8; i < 24;i++) {
            msLight[i] = new StyledFont("Inter-Light.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        for (int i = 8; i < 24;i++) {
            msMedium[i] = new StyledFont("Inter-Medium.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        for (int i = 8; i < 24;i++) {
            msRegular[i] = new StyledFont("Inter-Regular.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        for (int i = 8; i < 24;i++) {
            msSemiBold[i] = new StyledFont("Inter-SemiBold.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        for (int i = 8; i < 24;i++) {
            sfbold[i] = new StyledFont("sfbold.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        for (int i = 8; i < 24;i++) {
            logo[i] = new StyledFont("logo.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        for (int i = 1; i < 80;i++) {
            graycliff[i] = new StyledFont("semibold.ttf", i, 0.0f, 0.0f, 0.0f, true, Lang.ENG_RU);
        }
        System.out.println("Ўрифты загрузились за: " + (System.currentTimeMillis() - time) + " миллисекунды!");
        //fontThread.shutdown();
    }
}