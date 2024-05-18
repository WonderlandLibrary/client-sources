package optfine;

import com.google.gson.*;
import net.minecraft.util.*;
import java.awt.image.*;
import java.net.*;
import javax.imageio.*;
import java.io.*;

public class PlayerConfigurationParser
{
    private static final String[] I;
    public static final String CONFIG_ITEMS;
    public static final String ITEM_ACTIVE;
    private String player;
    public static final String ITEM_TYPE;
    
    private PlayerItemModel downloadModel(final String s) {
        final String string = PlayerConfigurationParser.I[0x22 ^ 0x33] + s;
        try {
            final JsonObject jsonObject = (JsonObject)new JsonParser().parse(new String(HttpUtils.get(string), PlayerConfigurationParser.I[0x7D ^ 0x6F]));
            final PlayerItemParser playerItemParser = new PlayerItemParser();
            return PlayerItemParser.parseItemModel(jsonObject);
        }
        catch (Exception ex) {
            Config.warn(PlayerConfigurationParser.I[0x37 ^ 0x24] + s + PlayerConfigurationParser.I[0x47 ^ 0x53] + ex.getClass().getName() + PlayerConfigurationParser.I[0x7D ^ 0x68] + ex.getMessage());
            return null;
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        ITEM_ACTIVE = PlayerConfigurationParser.I[0x3F ^ 0x29];
        ITEM_TYPE = PlayerConfigurationParser.I[0x53 ^ 0x44];
        CONFIG_ITEMS = PlayerConfigurationParser.I[0x84 ^ 0x9C];
    }
    
    public PlayerConfiguration parsePlayerConfiguration(final JsonElement jsonElement) {
        if (jsonElement == null) {
            throw new JsonParseException(PlayerConfigurationParser.I["".length()] + this.player);
        }
        final JsonObject jsonObject = (JsonObject)jsonElement;
        final PlayerConfiguration playerConfiguration = new PlayerConfiguration();
        final JsonArray jsonArray = (JsonArray)jsonObject.get(PlayerConfigurationParser.I[" ".length()]);
        if (jsonArray != null) {
            int i = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (i < jsonArray.size()) {
                final JsonObject jsonObject2 = (JsonObject)jsonArray.get(i);
                Label_0421: {
                    if (Json.getBoolean(jsonObject2, PlayerConfigurationParser.I["  ".length()], " ".length() != 0)) {
                        final String string = Json.getString(jsonObject2, PlayerConfigurationParser.I["   ".length()]);
                        if (string == null) {
                            Config.warn(PlayerConfigurationParser.I[0x1B ^ 0x1F] + this.player);
                            "".length();
                            if (3 < 0) {
                                throw null;
                            }
                        }
                        else {
                            String s = Json.getString(jsonObject2, PlayerConfigurationParser.I[0x37 ^ 0x32]);
                            if (s == null) {
                                s = PlayerConfigurationParser.I[0x93 ^ 0x95] + string + PlayerConfigurationParser.I[0x24 ^ 0x23];
                            }
                            final PlayerItemModel downloadModel = this.downloadModel(s);
                            if (downloadModel != null) {
                                if (!downloadModel.isUsePlayerTexture()) {
                                    String s2 = Json.getString(jsonObject2, PlayerConfigurationParser.I[0x3B ^ 0x33]);
                                    if (s2 == null) {
                                        s2 = PlayerConfigurationParser.I[0x32 ^ 0x3B] + string + PlayerConfigurationParser.I[0x7E ^ 0x74] + this.player + PlayerConfigurationParser.I[0x6F ^ 0x64];
                                    }
                                    final BufferedImage downloadTextureImage = this.downloadTextureImage(s2);
                                    if (downloadTextureImage == null) {
                                        "".length();
                                        if (3 < 2) {
                                            throw null;
                                        }
                                        break Label_0421;
                                    }
                                    else {
                                        downloadModel.setTextureImage(downloadTextureImage);
                                        downloadModel.setTextureLocation(new ResourceLocation(PlayerConfigurationParser.I[0x6F ^ 0x63], s2));
                                    }
                                }
                                playerConfiguration.addPlayerItemModel(downloadModel);
                            }
                        }
                    }
                }
                ++i;
            }
        }
        return playerConfiguration;
    }
    
    public PlayerConfigurationParser(final String player) {
        this.player = null;
        this.player = player;
    }
    
    private BufferedImage downloadTextureImage(final String s) {
        final String string = PlayerConfigurationParser.I[0x7C ^ 0x71] + s;
        try {
            return ImageIO.read(new URL(string));
        }
        catch (IOException ex) {
            Config.warn(PlayerConfigurationParser.I[0x6F ^ 0x61] + s + PlayerConfigurationParser.I[0x5E ^ 0x51] + ex.getClass().getName() + PlayerConfigurationParser.I[0xA3 ^ 0xB3] + ex.getMessage());
            return null;
        }
    }
    
    private static void I() {
        (I = new String[0x7D ^ 0x64])["".length()] = I("\u001a&\u0002<Y?\u0017'\u0017\u001a$U$\u0001Y>\u0000!\u001eUp\u0005!\u0013\u00005\u0007wR", "PuMry");
        PlayerConfigurationParser.I[" ".length()] = I("0\u0004$\u0018\u001e", "YpAum");
        PlayerConfigurationParser.I["  ".length()] = I("5\u0007$\u0005\u00051", "TdPls");
        PlayerConfigurationParser.I["   ".length()] = I("6?)\u000f", "BFYjj");
        PlayerConfigurationParser.I[0xBF ^ 0xBB] = I("8\u0017&\tS\u0005\u001a3\u0001S\u0018\u0010c\n\u0006\u001d\u000foD\u0003\u001d\u0002:\u0001\u0001KC", "qcCds");
        PlayerConfigurationParser.I[0x62 ^ 0x67] = I("\"\u0016\u000f3!", "OykVM");
        PlayerConfigurationParser.I[0x18 ^ 0x1E] = I("/8, 5i", "FLIMF");
        PlayerConfigurationParser.I[0x8A ^ 0x8D] = I("J\u0015(\u0017\"\tV$\u0015 ", "exGsG");
        PlayerConfigurationParser.I[0x54 ^ 0x5C] = I("8\u0011\b3;>\u0011", "LtpGN");
        PlayerConfigurationParser.I[0x5F ^ 0x56] = I("\u0010\u0010/\u001b\u0007V", "ydJvt");
        PlayerConfigurationParser.I[0x6A ^ 0x60] = I("k=9#87g", "DHJFJ");
        PlayerConfigurationParser.I[0x7 ^ 0xC] = I("F7\u0014+", "hGzLO");
        PlayerConfigurationParser.I[0x4B ^ 0x47] = I("\"88\u000e\u0001$&)I\t(<", "MHLgg");
        PlayerConfigurationParser.I[0xBD ^ 0xB0] = I(" 55\u001ekgn2@>85(\b8&$o\u00004<n", "HAAnQ");
        PlayerConfigurationParser.I[0x0 ^ 0xE] = I("5\u0003\u0013\u0005\u001fP\u001d\u000e\u000b\t\u0019\u001f\u0006J\u0004\u0004\u0014\fJ\u0019\u0015\t\u0015\u001f\u001f\u0015Q", "pqajm");
        PlayerConfigurationParser.I[0x41 ^ 0x4E] = I("ct", "YTQkL");
        PlayerConfigurationParser.I[0x3C ^ 0x2C] = I("IN", "snasB");
        PlayerConfigurationParser.I[0x67 ^ 0x76] = I("\u0000\u0017\u0019!BGL\u001e\u007f\u0017\u0018\u0017\u00047\u0011\u0006\u0006C?\u001d\u001cL", "hcmQx");
        PlayerConfigurationParser.I[0xBA ^ 0xA8] = I("9\u0004\u0005%\u0003", "xWFlJ");
        PlayerConfigurationParser.I[0x49 ^ 0x5A] = I("\u000b\u0006\u0000\u0004\u0003n\u0018\u001d\n\u0015'\u001a\u0015K\u0018:\u0011\u001fK\u001c!\u0010\u0017\u0007Q", "Ntrkq");
        PlayerConfigurationParser.I[0x7C ^ 0x68] = I("OQ", "uqnEQ");
        PlayerConfigurationParser.I[0x33 ^ 0x26] = I("Kj", "qJbov");
        PlayerConfigurationParser.I[0x4B ^ 0x5D] = I("\u0017\u0007\r!\u0011\u0013", "vdyHg");
        PlayerConfigurationParser.I[0x5A ^ 0x4D] = I(" 6\u00055", "TOuPC");
        PlayerConfigurationParser.I[0x76 ^ 0x6E] = I("\u001b%\f46", "rQiYE");
    }
}
