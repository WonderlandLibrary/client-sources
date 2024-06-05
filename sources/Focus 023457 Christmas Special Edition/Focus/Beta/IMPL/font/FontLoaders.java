package Focus.Beta.IMPL.font;

import java.awt.Font;
import java.io.InputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public abstract class FontLoaders {

    public static CFontRenderer roboto10 = new CFontRenderer(FontLoaders.getRoboto(10), true, true);
    public static CFontRenderer roboto14 = new CFontRenderer(FontLoaders.getRoboto(14), true, true);


    public static CFontRenderer Tahoma12 = new CFontRenderer(FontLoaders.getTahoma(12), true, true);
    public static CFontRenderer Tahoma9 = new CFontRenderer(FontLoaders.getTahoma(9), false, false);
    public static CFontRenderer Tahoma14 = new CFontRenderer(FontLoaders.getTahoma(14), true, true);

    public static CFontRenderer arial16 = new CFontRenderer(FontLoaders.getArial(16), true, true);
    public static CFontRenderer arial18 = new CFontRenderer(FontLoaders.getArial(18), true, true);
    public static CFontRenderer arial20 = new CFontRenderer(FontLoaders.getArial(20), true, true);
    public static CFontRenderer arial22 = new CFontRenderer(FontLoaders.getArial(22), true, true);
    public static CFontRenderer arial24 = new CFontRenderer(FontLoaders.getArial(24), true, true);
    public static CFontRenderer arial26 = new CFontRenderer(FontLoaders.getArial(26), true, true);
    public static CFontRenderer arial28 = new CFontRenderer(FontLoaders.getArial(28), true, true);

    public static CFontRenderer F14 = new CFontRenderer(FontLoaders.getTahoma(14), true, true);
    public static CFontRenderer F23 = new CFontRenderer(FontLoaders.getTahoma(23), true, true);
    public static CFontRenderer F16 = new CFontRenderer(FontLoaders.getTahoma(16), true, true);
    public static CFontRenderer F20 = new CFontRenderer(FontLoaders.getTahoma(20), true, true);

    public static CFontRenderer Comfortaa16 = new CFontRenderer(FontLoaders.getComfortaa(16), true, true);
    public static CFontRenderer Comfortaa18 = new CFontRenderer(FontLoaders.getComfortaa(18), true, true);
    public static CFontRenderer Comfortaa20 = new CFontRenderer(FontLoaders.getComfortaa(20), true, true);
    public static CFontRenderer Comfortaa22 = new CFontRenderer(FontLoaders.getComfortaa(22), true, true);
    public static CFontRenderer Comfortaa24 = new CFontRenderer(FontLoaders.getComfortaa(24), true, true);
    public static CFontRenderer Comfortaa26 = new CFontRenderer(FontLoaders.getComfortaa(26), true, true);
    public static CFontRenderer Comfortaa28 = new CFontRenderer(FontLoaders.getComfortaa(28), true, true);
    public static CFontRenderer Comfortaa80 = new CFontRenderer(FontLoaders.getComfortaa(50), true, true);


    public static CFontRenderer GoogleSans10 = new CFontRenderer(FontLoaders.getGoogleSans(10), true, true);
    public static CFontRenderer GoogleSans12 = new CFontRenderer(FontLoaders.getGoogleSans(12), true, true);
    public static CFontRenderer GoogleSans14 = new CFontRenderer(FontLoaders.getGoogleSans(14), true, true);
    public static CFontRenderer GoogleSans16 = new CFontRenderer(FontLoaders.getGoogleSans(16), true, true);
    public static CFontRenderer GoogleSans18 = new CFontRenderer(FontLoaders.getGoogleSans(18), true, true);
    public static CFontRenderer GoogleSans20 = new CFontRenderer(FontLoaders.getGoogleSans(20), true, true);
    public static CFontRenderer GoogleSans22 = new CFontRenderer(FontLoaders.getGoogleSans(22), true, true);
    public static CFontRenderer GoogleSans24 = new CFontRenderer(FontLoaders.getGoogleSans(24), true, true);
    public static CFontRenderer GoogleSans26 = new CFontRenderer(FontLoaders.getGoogleSans(26), true, true);
    public static CFontRenderer GoogleSans28 = new CFontRenderer(FontLoaders.getGoogleSans(28), true, true);

    public static CFontRenderer SfUiArray = new CFontRenderer(FontLoaders.getSfUi(20), true, true);

    public static CFontRenderer NovICON16 = new CFontRenderer(FontLoaders.getNovICON(16), true, true);
    public static CFontRenderer NovICON18 = new CFontRenderer(FontLoaders.getNovICON(18), true, true);
    public static CFontRenderer NovICON20 = new CFontRenderer(FontLoaders.getNovICON(20), true, true);
    public static CFontRenderer NovICON22 = new CFontRenderer(FontLoaders.getNovICON(22), true, true);
    public static CFontRenderer NovICON24 = new CFontRenderer(FontLoaders.getNovICON(24), true, true);
    public static CFontRenderer NovICON26 = new CFontRenderer(FontLoaders.getNovICON(26), true, true);
    public static CFontRenderer NovICON28 = new CFontRenderer(FontLoaders.getNovICON(28), true, true);
    public static CFontRenderer NovICON30 = new CFontRenderer(FontLoaders.getNovICON(30), true, true);
    public static CFontRenderer NovICON32 = new CFontRenderer(FontLoaders.getNovICON(32), true, true);
    public static CFontRenderer NovICON34 = new CFontRenderer(FontLoaders.getNovICON(34), true, true);
    public static CFontRenderer NovICON36 = new CFontRenderer(FontLoaders.getNovICON(36), true, true);
    public static CFontRenderer NovICON38 = new CFontRenderer(FontLoaders.getNovICON(38), true, true);
    public static CFontRenderer NovICON40 = new CFontRenderer(FontLoaders.getNovICON(40), true, true);
    public static CFontRenderer NovICON42 = new CFontRenderer(FontLoaders.getNovICON(42), true, true);
    public static CFontRenderer NovICON44 = new CFontRenderer(FontLoaders.getNovICON(44), true, true);
    public static CFontRenderer NovICON80 = new CFontRenderer(FontLoaders.getNovICON(80), true, true);

    private static Font getComfortaa(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation("focus/Comfortaa.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    private static Font getTahoma(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation("focus/TAHOMA_0.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    private static Font getRoboto(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation("focus/Roboto-Regular.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    private static Font getDefault(int size) {
        Font font;
        font = new Font("default", 0, size);
        return font;
    }

    private static Font getNovICON(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation("focus/NovICON.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    private static Font getGoogleSans(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation("focus/GoogleSans.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    private static Font getSfUi(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation("focus/SF-UI-Display-Regular.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    private static Font getArial(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation("focus/Arial.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }
}
