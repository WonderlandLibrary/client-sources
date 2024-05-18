package optfine;

import java.io.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;

public class NaturalTextures
{
    private static NaturalProperties[] propertiesByIndex;
    private static final String[] I;
    
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x59 ^ 0x24])["".length()] = I("(2\u0013\u000b/.,\u0002M'&6\u0012\u0010(+l\u0017\u0010&7'\u0015\u0016 \"1", "GBgbI");
        NaturalTextures.I[" ".length()] = I("\u00063\u0002->)>\"=4<'\u0004=?rr\u00157\".;\u0011->)&\u001f7\"hp", "HRvXL");
        NaturalTextures.I["  ".length()] = I("Kg-\u0006!I!,\u001c;\r", "iGCiU");
        NaturalTextures.I["   ".length()] = I("Y]", "SPRpe");
        NaturalTextures.I[0x5B ^ 0x5F] = I("(\u000f:%1\u0007\u0002n\u0004&\u001e\u001a;\"&\u0015Tn\u0000\"\u0014\u001d'>$F\r!>%\u000f\t;\"\"\u0012\u0007!>cD", "fnNPC");
        NaturalTextures.I[0x65 ^ 0x60] = I("{", "YHygt");
        NaturalTextures.I[0x67 ^ 0x61] = I("`", "CYKZV");
        NaturalTextures.I[0xA3 ^ 0xA4] = I("{", "FKpFm");
        NaturalTextures.I[0x62 ^ 0x6A] = I("8%&\"3\u0017(r\u0003$\u000e0'%$\u0005~r\u001e/\u0000%>>%Vf", "vDRWA");
        NaturalTextures.I[0x5D ^ 0x54] = I("{e\u0007\u0011\f<\u007fK", "YEkxb");
        NaturalTextures.I[0x37 ^ 0x3D] = I("\u000e\u0002\t$\u001a\u0011\n\u00015C\u0001\u0007\b\"\u0012\u0010D", "ckgAy");
        NaturalTextures.I[0x6B ^ 0x60] = I("\u00149\u001e\u0003!;4J\"6\",\u001f\u00046)bJ\"6\",\u001f\u00046z6\u0005\u0002s<7\u001f\u00187`xH", "ZXjvS");
        NaturalTextures.I[0x49 ^ 0x45] = I("LZ/;\"\u000b@c", "nzCRL");
        NaturalTextures.I[0x7 ^ 0xA] = I("\r\u000b$1\u001e\"\u0006p\u0010\t;\u001e%6\t0Pp\r\u00025\u000b<-\bcH", "CjPDl");
        NaturalTextures.I[0xBB ^ 0xB5] = I("Ue\u000b\b6\u0012\u007fG", "wEgaX");
        NaturalTextures.I[0x90 ^ 0x9F] = I("$%\u0015?\u000b\u000b(5/\u0001\u001e1\u0013/\nPd", "jDaJy");
        NaturalTextures.I[0x92 ^ 0x82] = I("YrA", "yOaQn");
        NaturalTextures.I[0x55 ^ 0x44] = I("+#\u000e1\b\u0004..!\u0002\u00117\b!\t_b\u0019+\u0014\u0003+\u001d1\b\u00046\u0013+\u0014E`", "eBzDz");
        NaturalTextures.I[0xB2 ^ 0xA0] = I("Pc\u001f\n\u0000R%\u001e\u0010\u001a\u0016", "rCqet");
        NaturalTextures.I[0xB2 ^ 0xA1] = I("\u001e\u0011\u001d\u0013\"1\u001c=\u0003($\u0005\u001b\u0003#jP*\u001451\u0004\u0000\b7p\u0014\f\u00001%\u001c\u001dF3?\u001e\u000f\u000f7%\u0002\b\u00129?\u001eG", "PpifP");
        NaturalTextures.I[0x12 ^ 0x6] = I("-\u0017\u000e\u001c*+'\u000b\u0007+:", "NxonY");
        NaturalTextures.I[0x5E ^ 0x4B] = I("C\u0012", "wTHGo");
        NaturalTextures.I[0x34 ^ 0x22] = I("?\u000b\u000f:\"\u0007\n\u0007-4", "XynIQ");
        NaturalTextures.I[0x99 ^ 0x8E] = I("\u0010", "VvrKw");
        NaturalTextures.I[0x97 ^ 0x8F] = I("\u001d8,+#%9$<5%%;=\"\u0016+4", "zJMXP");
        NaturalTextures.I[0x35 ^ 0x2C] = I("\u001e", "XCqhU");
        NaturalTextures.I[0x84 ^ 0x9E] = I("*\u0018\u0003)=\u0006\u001f\u0000&:\u0006\u0018\u00037", "YllGX");
        NaturalTextures.I[0x22 ^ 0x39] = I("\u0016", "PmdJW");
        NaturalTextures.I[0x2E ^ 0x32] = I("\u001e\n\u00158\u0017\u0015", "yxtNr");
        NaturalTextures.I[0xB9 ^ 0xA4] = I("X", "jdkei");
        NaturalTextures.I[0x65 ^ 0x7B] = I("\u0000\u001f.\u0007\u001b\r\u001b", "lpIXt");
        NaturalTextures.I[0x23 ^ 0x3C] = I("{0", "IvlHi");
        NaturalTextures.I[0xE0 ^ 0xC0] = I("/:\u0006\u0007\u001e3'\u0014;\b", "CUaXm");
        NaturalTextures.I[0x45 ^ 0x64] = I("\\\u0002", "nDSuZ");
        NaturalTextures.I[0x38 ^ 0x1A] = I("\u0001\u001a7\u0019(\u0004\u00073.", "muPFJ");
        NaturalTextures.I[0x56 ^ 0x75] = I("'", "aFNZT");
        NaturalTextures.I[0x40 ^ 0x64] = I("\b\u0007\u001e>,\u0011\u0006\u001e\r#", "dhyaF");
        NaturalTextures.I[0xD ^ 0x28] = I("a\r", "SKdVx");
        NaturalTextures.I[0x1F ^ 0x39] = I("\u0019$\"\u0014\"\u0016*&\"\"", "uKEKC");
        NaturalTextures.I[0xE1 ^ 0xC6] = I("H?", "zyWeS");
        NaturalTextures.I[0xA4 ^ 0x8C] = I("6*=\u001d'3\"\u0005-$1", "ZEZBE");
        NaturalTextures.I[0x63 ^ 0x4A] = I("x6", "Jpbrw");
        NaturalTextures.I[0x5A ^ 0x70] = I(".!7'5#%\u000f\f52", "BNPxZ");
        NaturalTextures.I[0x8 ^ 0x23] = I("^6", "jpKwn");
        NaturalTextures.I[0x35 ^ 0x19] = I("\r\u0006!\u001a\t\u0011\u001b3&\u001f>\u001d)5", "aiFEz");
        NaturalTextures.I[0x51 ^ 0x7C] = I("\u007f ", "KfEyL");
        NaturalTextures.I[0x10 ^ 0x3E] = I("*&\u000b\u00072/;\u000f0\u000f2&\u001c", "FIlXP");
        NaturalTextures.I[0x73 ^ 0x5C] = I("E\u0011", "qWFje");
        NaturalTextures.I[0x54 ^ 0x64] = I("\u0007\u0016\u000b2!\u001e\u0017\u000b\u0001.4\r\u0003\u001d", "kylmK");
        NaturalTextures.I[0x8B ^ 0xBA] = I("n+", "ZmLaH");
        NaturalTextures.I[0x14 ^ 0x26] = I("\u0002\"\u000e\u001d.\r,\n+.19\u00062", "nMiBO");
        NaturalTextures.I[0x7A ^ 0x49] = I("Y ", "mfQxb");
        NaturalTextures.I[0x9B ^ 0xAF] = I("\r?\u000b<\u0001\b73\f\u0002\n\u000f\u0018\f\u0013", "aPlcc");
        NaturalTextures.I[0x18 ^ 0x2D] = I("a\u0002", "UDGEk");
        NaturalTextures.I[0x1A ^ 0x2C] = I("\u0000,7\u000e\u0007\u001f\u00169\u0019\t", "lIVxb");
        NaturalTextures.I[0x72 ^ 0x45] = I("@$", "rbBUM");
        NaturalTextures.I[0x9E ^ 0xA6] = I("53\u0011& *\t\u0003 7,5\u0015", "YVpPE");
        NaturalTextures.I[0x82 ^ 0xBB] = I("W/", "eiJkt");
        NaturalTextures.I[0x7F ^ 0x45] = I("&!\r ,9\u001b\u000e?;),", "JDlVI");
        NaturalTextures.I[0x67 ^ 0x5C] = I("q!", "CgkxH");
        NaturalTextures.I[0x3C ^ 0x0] = I("%\u00070$,:=;''.\u000e4", "IbQRI");
        NaturalTextures.I[0xF9 ^ 0xC4] = I("\u007f", "MKbZL");
        NaturalTextures.I[0x86 ^ 0xB8] = I("\u001c<\u0011</\u0003\u0006\u0012#-/6\u0011!", "pYpJJ");
        NaturalTextures.I[0x59 ^ 0x66] = I("`\"", "RdjpE");
        NaturalTextures.I[0xF6 ^ 0xB6] = I("\u001e-1#\u001f\u0001\u001716\u001b\u0011!1", "rHPUz");
        NaturalTextures.I[0x9 ^ 0x48] = I("u(", "GnGLb");
        NaturalTextures.I[0x82 ^ 0xC0] = I(",\u0019(\b\u0012$\u0004!", "KvDlM");
        NaturalTextures.I[0x6 ^ 0x45] = I("{<", "Izmba");
        NaturalTextures.I[0xEA ^ 0xAE] = I(">?\r'\u00188?\u0007", "WMbIG");
        NaturalTextures.I[0x21 ^ 0x64] = I("h)", "ZoLln");
        NaturalTextures.I[0xD5 ^ 0x93] = I("\t!\u0014\n\r\u0005<\u0010", "jNufR");
        NaturalTextures.I[0x54 ^ 0x13] = I("G'", "uaKRr");
        NaturalTextures.I[0x4 ^ 0x4C] = I("=\u0000/\u0000\u00187\r\u0011\u0002\u0005<", "YiNmw");
        NaturalTextures.I[0x45 ^ 0xC] = I("W\f", "eJjYt");
        NaturalTextures.I[0x30 ^ 0x7A] = I(";\u0012\n$\u0003&\u0019\u000b\b\u0018;\u0012", "IwnWw");
        NaturalTextures.I[0xCE ^ 0x85] = I("v\u000b", "DMewu");
        NaturalTextures.I[0x2A ^ 0x66] = I("8\u0006\u0017\"\u001d\u000b\b\u0015.", "TggKn");
        NaturalTextures.I[0xCF ^ 0x82] = I("J7", "xqusQ");
        NaturalTextures.I[0x5A ^ 0x14] = I("6)\u00101\n0*\r", "YKcXn");
        NaturalTextures.I[0x8B ^ 0xC4] = I("Q*", "elofA");
        NaturalTextures.I[0xE2 ^ 0xB2] = I("\u00037\b0", "pYgGz");
        NaturalTextures.I[0x4D ^ 0x1C] = I("\\/", "hiTGB");
        NaturalTextures.I[0x3B ^ 0x69] = I("\u0014!04\u001c, 8#\n, ?(\u0018\u00167", "sSQGo");
        NaturalTextures.I[0x11 ^ 0x42] = I("\u0017", "QtFzb");
        NaturalTextures.I[0xD3 ^ 0x87] = I("\u0016\u0012'<\u0019\u0006,7!\b\u0010", "usDHl");
        NaturalTextures.I[0xC1 ^ 0x94] = I("z\u0012", "HTxPp");
        NaturalTextures.I[0xDF ^ 0x89] = I("4\u000f3>", "WcRGI");
        NaturalTextures.I[0x3E ^ 0x69] = I("V\u001f", "bYsqe");
        NaturalTextures.I[0x25 ^ 0x7D] = I(",8%/\u001f(4+\u0015\u0000(%#", "AAFJs");
        NaturalTextures.I[0xF8 ^ 0xA1] = I("7", "qfDkQ");
        NaturalTextures.I[0x55 ^ 0xF] = I("\u0014+\r\u0017\"\u0010'\u0003-:\u0016\"", "yRnrN");
        NaturalTextures.I[0xE4 ^ 0xBF] = I("v>", "BxrWe");
        NaturalTextures.I[0x76 ^ 0x2A] = I("03<\u0000:7<*2!3&", "VRNmV");
        NaturalTextures.I[0xE7 ^ 0xBA] = I("F4", "trLLU");
        NaturalTextures.I[0x6B ^ 0x35] = I("\t\u0015=\u0006\u0001\u000e\u001a+4\t\u001d\r", "otOkm");
        NaturalTextures.I[0x79 ^ 0x26] = I("`3", "RukYq");
        NaturalTextures.I[0x49 ^ 0x29] = I("8\u0012\u000e-<$\u0005\u001b&2", "VwzEY");
        NaturalTextures.I[0x2 ^ 0x63] = I("~\u0007", "JASeh");
        NaturalTextures.I[0x3F ^ 0x5D] = I("\u0002\u0002\u001e\u0018\b\u0002\f\u0005\u0010", "qmktW");
        NaturalTextures.I[0xD3 ^ 0xB0] = I("S$", "gbwyv");
        NaturalTextures.I[0xF5 ^ 0x91] = I("#\u0018\u0000\u0014\u00100\u001b\u0001\u0006", "Dtocc");
        NaturalTextures.I[0xC5 ^ 0xA0] = I("s", "Gwhrn");
        NaturalTextures.I[0x56 ^ 0x30] = I("'\u0014'*&6\u0015-\u0010", "BzCuU");
        NaturalTextures.I[0xF8 ^ 0x9F] = I("z", "NxZyg");
        NaturalTextures.I[0xCF ^ 0xA7] = I("$\u001b!\u000e6#\u0015!\u000f\u001a#\u0015?", "WzOjE");
        NaturalTextures.I[0x47 ^ 0x2E] = I("S", "gnkIL");
        NaturalTextures.I[0x35 ^ 0x5F] = I("\u0004\u0004\u0002\r\u0016\u0003\n\u0002\f:\u0015\n\u0018\u001d\n\u001a", "welie");
        NaturalTextures.I[0xF1 ^ 0x9A] = I("U0", "avcuZ");
        NaturalTextures.I[0x5 ^ 0x69] = I("\u0004\u0016\u000f\u0014\u0000\u0019\u001d\u000e8\u0018\u0017\u001e\u001b8\u001b\u0018", "vskgt");
        NaturalTextures.I[0x4C ^ 0x21] = I("N\u000f", "zInGL");
        NaturalTextures.I[0xC3 ^ 0xAD] = I(" \u0002!\t\u0006=\t %\u001e3\n5%\u001d4\u0001", "RgEzr");
        NaturalTextures.I[0xFB ^ 0x94] = I("p\u0004", "DBXSX");
        NaturalTextures.I[0x69 ^ 0x19] = I("\u0005\f\u001f4\u0017\u001a\u0004\u0017%N\n\t\u001e2\u001f\u001bJ", "heqQt");
        NaturalTextures.I[0x69 ^ 0x18] = I("nBpH\u000b%\u001c/\u001a$(8(\u00075!\u001a.\u0001 7Rz!&+\u0006z\u0006*0H<\u00070*\f`H", "DhZhE");
        NaturalTextures.I[0x5F ^ 0x2D] = I("G_dS", "guNyj");
        NaturalTextures.I[0xC ^ 0x7F] = I("O{he\u0019\u0004%776\t\u00010*'\u0000#6,2\u0016kb\f4\n?b,$E?-1w,2-+\u0004\u001186&?\u00005xe", "eQBEW");
        NaturalTextures.I[0x56 ^ 0x22] = I("Sr", "iRbJU");
        NaturalTextures.I[0x68 ^ 0x1D] = I("xrFG", "XXlmV");
        NaturalTextures.I[0x32 ^ 0x44] = I("FxRH\u0005\r&\r\u001a*\u0000\u0002\n\u0007;\t \f\u0001.\u001fhX!%\u001a3\u0014\u0001/L;\u0016\f.\u0014r\u001e\u00079L;\u001b\u0007%Vr", "lRxhK");
        NaturalTextures.I[0x51 ^ 0x26] = I("RY", "hypzf");
        NaturalTextures.I[0x62 ^ 0x1A] = I("ek@n", "EAjDi");
        NaturalTextures.I[0x71 ^ 0x8] = I("3\u0002\u0017&\u00055\u0002\u001c}\u0012+\b\f9\u0003h", "GgoRp");
        NaturalTextures.I[0x77 ^ 0xD] = I("O\u001e\u001a\b", "antop");
        NaturalTextures.I[0x3B ^ 0x40] = I("*-\u0018\u00009\u0005 8\u00103\u00109\u001e\u00108^l", "dLluK");
        NaturalTextures.I[0xDF ^ 0xA3] = I("iqz", "ILZJo");
    }
    
    public static void update() {
        NaturalTextures.propertiesByIndex = new NaturalProperties["".length()];
        if (Config.isNaturalTextures()) {
            final String s = NaturalTextures.I["".length()];
            try {
                final ResourceLocation resourceLocation = new ResourceLocation(s);
                if (!Config.hasResource(resourceLocation)) {
                    Config.dbg(NaturalTextures.I[" ".length()] + s + NaturalTextures.I["  ".length()]);
                    NaturalTextures.propertiesByIndex = makeDefaultProperties();
                    return;
                }
                final InputStream resourceStream = Config.getResourceStream(resourceLocation);
                final ArrayList<NaturalProperties> list = new ArrayList<NaturalProperties>(79 + 252 - 223 + 148);
                final String inputStream = Config.readInputStream(resourceStream);
                resourceStream.close();
                final String[] tokenize = Config.tokenize(inputStream, NaturalTextures.I["   ".length()]);
                Config.dbg(NaturalTextures.I[0x70 ^ 0x74] + s + NaturalTextures.I[0xB1 ^ 0xB4]);
                final TextureMap textureMapBlocks = TextureUtils.getTextureMapBlocks();
                int i = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (i < tokenize.length) {
                    final String trim = tokenize[i].trim();
                    if (!trim.startsWith(NaturalTextures.I[0x92 ^ 0x94])) {
                        final String[] tokenize2 = Config.tokenize(trim, NaturalTextures.I[0x28 ^ 0x2F]);
                        if (tokenize2.length != "  ".length()) {
                            Config.warn(NaturalTextures.I[0x7F ^ 0x77] + s + NaturalTextures.I[0xCE ^ 0xC7] + trim);
                            "".length();
                            if (1 == 3) {
                                throw null;
                            }
                        }
                        else {
                            final String trim2 = tokenize2["".length()].trim();
                            final String trim3 = tokenize2[" ".length()].trim();
                            final TextureAtlasSprite spriteSafe = textureMapBlocks.getSpriteSafe(NaturalTextures.I[0x68 ^ 0x62] + trim2);
                            if (spriteSafe == null) {
                                Config.warn(NaturalTextures.I[0x0 ^ 0xB] + s + NaturalTextures.I[0x54 ^ 0x58] + trim);
                                "".length();
                                if (1 < -1) {
                                    throw null;
                                }
                            }
                            else {
                                final int indexInMap = spriteSafe.getIndexInMap();
                                if (indexInMap < 0) {
                                    Config.warn(NaturalTextures.I[0x2C ^ 0x21] + s + NaturalTextures.I[0x9C ^ 0x92] + trim);
                                    "".length();
                                    if (3 >= 4) {
                                        throw null;
                                    }
                                }
                                else {
                                    final NaturalProperties naturalProperties = new NaturalProperties(trim3);
                                    if (naturalProperties.isValid()) {
                                        "".length();
                                        if (3 < 0) {
                                            throw null;
                                        }
                                        while (list.size() <= indexInMap) {
                                            list.add(null);
                                        }
                                        list.set(indexInMap, naturalProperties);
                                        Config.dbg(NaturalTextures.I[0x83 ^ 0x8C] + trim2 + NaturalTextures.I[0xA3 ^ 0xB3] + trim3);
                                    }
                                }
                            }
                        }
                    }
                    ++i;
                }
                NaturalTextures.propertiesByIndex = list.toArray(new NaturalProperties[list.size()]);
                "".length();
                if (4 == 3) {
                    throw null;
                }
            }
            catch (FileNotFoundException ex2) {
                Config.warn(NaturalTextures.I[0x17 ^ 0x6] + s + NaturalTextures.I[0xB ^ 0x19]);
                NaturalTextures.propertiesByIndex = makeDefaultProperties();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static NaturalProperties getNaturalProperties(final TextureAtlasSprite textureAtlasSprite) {
        if (!(textureAtlasSprite instanceof TextureAtlasSprite)) {
            return null;
        }
        final int indexInMap = textureAtlasSprite.getIndexInMap();
        if (indexInMap >= 0 && indexInMap < NaturalTextures.propertiesByIndex.length) {
            return NaturalTextures.propertiesByIndex[indexInMap];
        }
        return null;
    }
    
    private static void setIconProperties(final List list, final String s, final String s2) {
        final TextureAtlasSprite spriteSafe = TextureUtils.getTextureMapBlocks().getSpriteSafe(NaturalTextures.I[0x16 ^ 0x66] + s);
        if (spriteSafe == null) {
            Config.warn(NaturalTextures.I[0xFE ^ 0x8F] + s + NaturalTextures.I[0x4 ^ 0x76]);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else if (!(spriteSafe instanceof TextureAtlasSprite)) {
            Config.warn(NaturalTextures.I[0x5D ^ 0x2E] + s + NaturalTextures.I[0xB7 ^ 0xC3] + spriteSafe.getClass().getName() + NaturalTextures.I[0x40 ^ 0x35]);
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            final int i = spriteSafe.getIndexInMap();
            if (i < 0) {
                Config.warn(NaturalTextures.I[0xEA ^ 0x9C] + s + NaturalTextures.I[0xE8 ^ 0x9F] + i + NaturalTextures.I[0x6A ^ 0x12]);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else if (Config.isFromDefaultResourcePack(new ResourceLocation(NaturalTextures.I[0xF5 ^ 0x8C] + s + NaturalTextures.I[0xE6 ^ 0x9C]))) {
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (i >= list.size()) {
                    list.add(null);
                }
                list.set(i, new NaturalProperties(s2));
                Config.dbg(NaturalTextures.I[0xCF ^ 0xB4] + s + NaturalTextures.I[0x2 ^ 0x7E] + s2);
            }
        }
    }
    
    private static NaturalProperties[] makeDefaultProperties() {
        Config.dbg(NaturalTextures.I[0xA1 ^ 0xB2]);
        final ArrayList list = new ArrayList();
        setIconProperties(list, NaturalTextures.I[0x80 ^ 0x94], NaturalTextures.I[0xBB ^ 0xAE]);
        setIconProperties(list, NaturalTextures.I[0x28 ^ 0x3E], NaturalTextures.I[0xB1 ^ 0xA6]);
        setIconProperties(list, NaturalTextures.I[0x97 ^ 0x8F], NaturalTextures.I[0x17 ^ 0xE]);
        setIconProperties(list, NaturalTextures.I[0x95 ^ 0x8F], NaturalTextures.I[0x4F ^ 0x54]);
        setIconProperties(list, NaturalTextures.I[0xAC ^ 0xB0], NaturalTextures.I[0x1 ^ 0x1C]);
        setIconProperties(list, NaturalTextures.I[0x6C ^ 0x72], NaturalTextures.I[0x94 ^ 0x8B]);
        setIconProperties(list, NaturalTextures.I[0x6 ^ 0x26], NaturalTextures.I[0x64 ^ 0x45]);
        setIconProperties(list, NaturalTextures.I[0xA8 ^ 0x8A], NaturalTextures.I[0x41 ^ 0x62]);
        setIconProperties(list, NaturalTextures.I[0x45 ^ 0x61], NaturalTextures.I[0x27 ^ 0x2]);
        setIconProperties(list, NaturalTextures.I[0x62 ^ 0x44], NaturalTextures.I[0x18 ^ 0x3F]);
        setIconProperties(list, NaturalTextures.I[0x7F ^ 0x57], NaturalTextures.I[0x69 ^ 0x40]);
        setIconProperties(list, NaturalTextures.I[0xA7 ^ 0x8D], NaturalTextures.I[0xA2 ^ 0x89]);
        setIconProperties(list, NaturalTextures.I[0x3A ^ 0x16], NaturalTextures.I[0x9C ^ 0xB1]);
        setIconProperties(list, NaturalTextures.I[0x44 ^ 0x6A], NaturalTextures.I[0xE8 ^ 0xC7]);
        setIconProperties(list, NaturalTextures.I[0x7D ^ 0x4D], NaturalTextures.I[0x44 ^ 0x75]);
        setIconProperties(list, NaturalTextures.I[0x5E ^ 0x6C], NaturalTextures.I[0x52 ^ 0x61]);
        setIconProperties(list, NaturalTextures.I[0x13 ^ 0x27], NaturalTextures.I[0xBE ^ 0x8B]);
        setIconProperties(list, NaturalTextures.I[0x18 ^ 0x2E], NaturalTextures.I[0x63 ^ 0x54]);
        setIconProperties(list, NaturalTextures.I[0x1C ^ 0x24], NaturalTextures.I[0x39 ^ 0x0]);
        setIconProperties(list, NaturalTextures.I[0x1E ^ 0x24], NaturalTextures.I[0xB7 ^ 0x8C]);
        setIconProperties(list, NaturalTextures.I[0x51 ^ 0x6D], NaturalTextures.I[0x6E ^ 0x53]);
        setIconProperties(list, NaturalTextures.I[0x14 ^ 0x2A], NaturalTextures.I[0x56 ^ 0x69]);
        setIconProperties(list, NaturalTextures.I[0x4 ^ 0x44], NaturalTextures.I[0x4E ^ 0xF]);
        setIconProperties(list, NaturalTextures.I[0xDC ^ 0x9E], NaturalTextures.I[0x2F ^ 0x6C]);
        setIconProperties(list, NaturalTextures.I[0x72 ^ 0x36], NaturalTextures.I[0x32 ^ 0x77]);
        setIconProperties(list, NaturalTextures.I[0x15 ^ 0x53], NaturalTextures.I[0x14 ^ 0x53]);
        setIconProperties(list, NaturalTextures.I[0xC6 ^ 0x8E], NaturalTextures.I[0x40 ^ 0x9]);
        setIconProperties(list, NaturalTextures.I[0x51 ^ 0x1B], NaturalTextures.I[0x3 ^ 0x48]);
        setIconProperties(list, NaturalTextures.I[0xF4 ^ 0xB8], NaturalTextures.I[0x6C ^ 0x21]);
        setIconProperties(list, NaturalTextures.I[0x89 ^ 0xC7], NaturalTextures.I[0x67 ^ 0x28]);
        setIconProperties(list, NaturalTextures.I[0x96 ^ 0xC6], NaturalTextures.I[0x4F ^ 0x1E]);
        setIconProperties(list, NaturalTextures.I[0x7C ^ 0x2E], NaturalTextures.I[0xD4 ^ 0x87]);
        setIconProperties(list, NaturalTextures.I[0x5E ^ 0xA], NaturalTextures.I[0xE4 ^ 0xB1]);
        setIconProperties(list, NaturalTextures.I[0x6F ^ 0x39], NaturalTextures.I[0x4F ^ 0x18]);
        setIconProperties(list, NaturalTextures.I[0xD9 ^ 0x81], NaturalTextures.I[0x0 ^ 0x59]);
        setIconProperties(list, NaturalTextures.I[0x2D ^ 0x77], NaturalTextures.I[0x9 ^ 0x52]);
        setIconProperties(list, NaturalTextures.I[0x64 ^ 0x38], NaturalTextures.I[0xD9 ^ 0x84]);
        setIconProperties(list, NaturalTextures.I[0x1C ^ 0x42], NaturalTextures.I[0xE0 ^ 0xBF]);
        setIconProperties(list, NaturalTextures.I[0xE ^ 0x6E], NaturalTextures.I[0x49 ^ 0x28]);
        setIconProperties(list, NaturalTextures.I[0x26 ^ 0x44], NaturalTextures.I[0xE5 ^ 0x86]);
        setIconProperties(list, NaturalTextures.I[0x42 ^ 0x26], NaturalTextures.I[0xDD ^ 0xB8]);
        setIconProperties(list, NaturalTextures.I[0x71 ^ 0x17], NaturalTextures.I[0x57 ^ 0x30]);
        setIconProperties(list, NaturalTextures.I[0x2F ^ 0x47], NaturalTextures.I[0xAB ^ 0xC2]);
        setIconProperties(list, NaturalTextures.I[0x6A ^ 0x0], NaturalTextures.I[0xD4 ^ 0xBF]);
        setIconProperties(list, NaturalTextures.I[0x75 ^ 0x19], NaturalTextures.I[0x60 ^ 0xD]);
        setIconProperties(list, NaturalTextures.I[0x71 ^ 0x1F], NaturalTextures.I[0x54 ^ 0x3B]);
        return (NaturalProperties[])list.toArray(new NaturalProperties[list.size()]);
    }
    
    public static BakedQuad getNaturalTexture(final BlockPos blockPos, final BakedQuad bakedQuad) {
        final TextureAtlasSprite sprite = bakedQuad.getSprite();
        if (sprite == null) {
            return bakedQuad;
        }
        final NaturalProperties naturalProperties = getNaturalProperties(sprite);
        if (naturalProperties == null) {
            return bakedQuad;
        }
        final int random = Config.getRandom(blockPos, ConnectedTextures.getSide(bakedQuad.getFace()));
        int length = "".length();
        int length2 = "".length();
        if (naturalProperties.rotation > " ".length()) {
            length = (random & "   ".length());
        }
        if (naturalProperties.rotation == "  ".length()) {
            length = length / "  ".length() * "  ".length();
        }
        if (naturalProperties.flip) {
            int n;
            if ((random & (0x16 ^ 0x12)) != 0x0) {
                n = " ".length();
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            length2 = n;
        }
        return naturalProperties.getQuad(bakedQuad, length, length2 != 0);
    }
    
    static {
        I();
        NaturalTextures.propertiesByIndex = new NaturalProperties["".length()];
    }
}
