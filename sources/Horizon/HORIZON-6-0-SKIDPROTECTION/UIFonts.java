package HORIZON-6-0-SKIDPROTECTION;

import java.util.Date;
import java.io.File;
import java.awt.Font;

public class UIFonts
{
    public static UnicodeFontRenderer HorizonCode_Horizon_È;
    public static UnicodeFontRenderer Â;
    public static UnicodeFontRenderer Ý;
    public static UnicodeFontRenderer Ø­áŒŠá;
    public static UnicodeFontRenderer Âµá€;
    public static UnicodeFontRenderer Ó;
    public static UnicodeFontRenderer à;
    public static UnicodeFontRenderer_1956725890 Ø;
    public static UnicodeFontRenderer_1956725890 áŒŠÆ;
    public static UnicodeFontRenderer_1956725890 áˆºÑ¢Õ;
    public static UnicodeFontRenderer_1956725890 ÂµÈ;
    public static UnicodeFontRenderer_1956725890 á;
    public static UnicodeFontRenderer_1956725890 ˆÏ­;
    public static UnicodeFontRenderer_1956725890 £á;
    public static UnicodeFontRenderer_1956725890 Å;
    public static UnicodeFontRenderer_1956725890 £à;
    public static UnicodeFontRenderer_1956725890 µà;
    public static UnicodeFontRenderer_1956725890 ˆà;
    public static UnicodeFontRenderer_1956725890 ¥Æ;
    public static UnicodeFontRenderer_1956725890 Ø­à;
    public static UnicodeFontRenderer_1956725890 µÕ;
    public static UnicodeFontRenderer_1956725890 Æ;
    public static UnicodeFontRenderer_1956725890 Šáƒ;
    public static UnicodeFontRenderer_1956725890 Ï­Ðƒà;
    public static UnicodeFontRenderer_1956725890 áŒŠà;
    
    public static void HorizonCode_Horizon_È() {
        UIFonts.HorizonCode_Horizon_È = new UnicodeFontRenderer(new Font("Arial", 0, 72));
        UIFonts.Ó = new UnicodeFontRenderer(new Font("Arial", 0, 51));
        UIFonts.Â = new UnicodeFontRenderer(new Font("Arial", 0, 32));
        UIFonts.Âµá€ = new UnicodeFontRenderer(new Font("Arial", 0, 20));
        UIFonts.Ý = new UnicodeFontRenderer(new Font("Arial", 0, 22));
        UIFonts.Ø­áŒŠá = new UnicodeFontRenderer(new Font("Arial", 0, 12));
        UIFonts.à = new UnicodeFontRenderer(new Font("Arial", 0, 3));
        final File mcdir = new File(Minecraft.áŒŠà().ŒÏ, "Horizon");
        final File fontPath = new File(mcdir, "fonts");
        final File Comfortaa = new File(fontPath, "Horizonfont.ttf");
        final File HelveticaNeue = new File(fontPath, "Horizonfont2.ttf");
        final File SegoeUI = new File(fontPath, "Horizonfont3.ttf");
        final File Vibes = new File(fontPath, "Horizonfont4.ttf");
        UIFonts.Ø = new UnicodeFontRenderer_1956725890(Comfortaa.getAbsolutePath(), 45);
        UIFonts.áŒŠÆ = new UnicodeFontRenderer_1956725890(Comfortaa.getAbsolutePath(), 28);
        UIFonts.ÂµÈ = new UnicodeFontRenderer_1956725890(Comfortaa.getAbsolutePath(), 72);
        UIFonts.áˆºÑ¢Õ = new UnicodeFontRenderer_1956725890(Comfortaa.getAbsolutePath(), 23);
        UIFonts.á = new UnicodeFontRenderer_1956725890(Comfortaa.getAbsolutePath(), 20);
        UIFonts.ˆÏ­ = new UnicodeFontRenderer_1956725890(Comfortaa.getAbsolutePath(), 18);
        UIFonts.£á = new UnicodeFontRenderer_1956725890(HelveticaNeue.getAbsolutePath(), 23);
        UIFonts.Å = new UnicodeFontRenderer_1956725890(HelveticaNeue.getAbsolutePath(), 30);
        UIFonts.Ø­à = new UnicodeFontRenderer_1956725890(HelveticaNeue.getAbsolutePath(), 22);
        UIFonts.µà = new UnicodeFontRenderer_1956725890(SegoeUI.getAbsolutePath(), 22);
        UIFonts.¥Æ = new UnicodeFontRenderer_1956725890(SegoeUI.getAbsolutePath(), 92);
        UIFonts.ˆà = new UnicodeFontRenderer_1956725890(SegoeUI.getAbsolutePath(), 62);
        UIFonts.µÕ = new UnicodeFontRenderer_1956725890(Comfortaa.getAbsolutePath(), 22);
        UIFonts.Æ = new UnicodeFontRenderer_1956725890(Vibes.getAbsolutePath(), 22);
        UIFonts.Šáƒ = new UnicodeFontRenderer_1956725890(Vibes.getAbsolutePath(), 45);
        UIFonts.Ï­Ðƒà = new UnicodeFontRenderer_1956725890(Vibes.getAbsolutePath(), 28);
        UIFonts.áŒŠà = new UnicodeFontRenderer_1956725890(Vibes.getAbsolutePath(), 23);
    }
    
    public static String HorizonCode_Horizon_È(final String input) {
        String daySync = "";
        String monthSync = "";
        final Date d = new Date();
        if (Minecraft.áŒŠà().ŠÄ.Ï­Ó.equalsIgnoreCase("de_DE")) {
            switch (d.getDay()) {
                case 0: {
                    daySync = "Sontag";
                    break;
                }
                case 1: {
                    daySync = "Montag";
                    break;
                }
                case 2: {
                    daySync = "Dienstag";
                    break;
                }
                case 3: {
                    daySync = "Mittwoch";
                    break;
                }
                case 4: {
                    daySync = "Donnerstag";
                    break;
                }
                case 5: {
                    daySync = "Freitag";
                    break;
                }
                case 6: {
                    daySync = "Samstag";
                    break;
                }
                case 7: {
                    daySync = "Sontag";
                    break;
                }
            }
        }
        else {
            switch (d.getDay()) {
                case 0: {
                    daySync = "Sunday";
                    break;
                }
                case 1: {
                    daySync = "Monday";
                    break;
                }
                case 2: {
                    daySync = "Tuesday";
                    break;
                }
                case 3: {
                    daySync = "Wednesday";
                    break;
                }
                case 4: {
                    daySync = "Thursday";
                    break;
                }
                case 5: {
                    daySync = "Friday";
                    break;
                }
                case 6: {
                    daySync = "Saturday";
                    break;
                }
                case 7: {
                    daySync = "Sunday";
                    break;
                }
            }
        }
        switch (d.getMonth()) {
            case 0: {
                monthSync = "January";
                break;
            }
            case 1: {
                monthSync = "February";
                break;
            }
            case 2: {
                monthSync = "March";
                break;
            }
            case 3: {
                monthSync = "April";
                break;
            }
            case 4: {
                monthSync = "May";
                break;
            }
            case 5: {
                monthSync = "June";
                break;
            }
            case 6: {
                monthSync = "July";
                break;
            }
            case 7: {
                monthSync = "August";
                break;
            }
            case 8: {
                monthSync = "September";
                break;
            }
            case 9: {
                monthSync = "October";
                break;
            }
            case 10: {
                monthSync = "November";
                break;
            }
            case 11: {
                monthSync = "December";
                break;
            }
        }
        return String.valueOf(daySync) + ", " + monthSync + " " + d.getDate();
    }
}
