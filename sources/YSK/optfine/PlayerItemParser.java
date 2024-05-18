package optfine;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import java.awt.*;
import com.google.gson.*;
import java.util.*;

public class PlayerItemParser
{
    public static final String MODEL_SPRITES;
    public static final String MODEL_TRANSLATE;
    public static final String MODEL_BASE_ID;
    public static final String MODEL_MIRROR_TEXTURE;
    public static final String ITEM_TEXTURE_SIZE;
    public static final String ITEM_USE_PLAYER_TEXTURE;
    public static final String BOX_SIZE_ADD;
    public static final String MODEL_BOXES;
    public static final String MODEL_ATTACH_TO;
    public static final String ITEM_TYPE_MODEL;
    private static final String[] I;
    public static final String MODEL_SUBMODELS;
    public static final String MODEL_SUBMODEL;
    public static final String MODEL_ROTATE;
    public static final String MODEL_INVERT_AXIS;
    public static final String MODEL_ID;
    public static final String MODEL_TYPE_BOX;
    public static final String BOX_TEXTURE_OFFSET;
    public static final String ITEM_TYPE;
    public static final String MODEL_SCALE;
    public static final String ITEM_MODELS;
    private static JsonParser jsonParser;
    public static final String BOX_COORDINATES;
    public static final String MODEL_TYPE;
    
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
            if (4 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static ResourceLocation makeResourceLocation(final String s) {
        final int index = s.indexOf(0x9A ^ 0xA0);
        if (index < 0) {
            return new ResourceLocation(s);
        }
        return new ResourceLocation(s.substring("".length(), index), s.substring(index + " ".length()));
    }
    
    private static ModelRenderer parseModelRenderer(final JsonObject jsonObject, final ModelBase modelBase) {
        final ModelRenderer modelRenderer = new ModelRenderer(modelBase);
        final String lowerCase = Json.getString(jsonObject, PlayerItemParser.I[0x53 ^ 0x7C], PlayerItemParser.I[0x82 ^ 0xB2]).toLowerCase();
        final boolean contains = lowerCase.contains(PlayerItemParser.I[0x7D ^ 0x4C]);
        final boolean contains2 = lowerCase.contains(PlayerItemParser.I[0x4D ^ 0x7F]);
        final boolean contains3 = lowerCase.contains(PlayerItemParser.I[0xF ^ 0x3C]);
        final float[] floatArray = Json.parseFloatArray(jsonObject.get(PlayerItemParser.I[0x64 ^ 0x50]), "   ".length(), new float["   ".length()]);
        if (contains) {
            floatArray["".length()] = -floatArray["".length()];
        }
        if (contains2) {
            floatArray[" ".length()] = -floatArray[" ".length()];
        }
        if (contains3) {
            floatArray["  ".length()] = -floatArray["  ".length()];
        }
        final float[] floatArray2 = Json.parseFloatArray(jsonObject.get(PlayerItemParser.I[0x2D ^ 0x18]), "   ".length(), new float["   ".length()]);
        int i = "".length();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (i < floatArray2.length) {
            floatArray2[i] = floatArray2[i] / 180.0f * 3.1415927f;
            ++i;
        }
        if (contains) {
            floatArray2["".length()] = -floatArray2["".length()];
        }
        if (contains2) {
            floatArray2[" ".length()] = -floatArray2[" ".length()];
        }
        if (contains3) {
            floatArray2["  ".length()] = -floatArray2["  ".length()];
        }
        modelRenderer.setRotationPoint(floatArray["".length()], floatArray[" ".length()], floatArray["  ".length()]);
        modelRenderer.rotateAngleX = floatArray2["".length()];
        modelRenderer.rotateAngleY = floatArray2[" ".length()];
        modelRenderer.rotateAngleZ = floatArray2["  ".length()];
        final String lowerCase2 = Json.getString(jsonObject, PlayerItemParser.I[0x78 ^ 0x4E], PlayerItemParser.I[0xD ^ 0x3A]).toLowerCase();
        final boolean contains4 = lowerCase2.contains(PlayerItemParser.I[0x4 ^ 0x3C]);
        final boolean contains5 = lowerCase2.contains(PlayerItemParser.I[0x73 ^ 0x4A]);
        if (contains4) {
            modelRenderer.mirror = (" ".length() != 0);
        }
        if (contains5) {
            modelRenderer.mirrorV = (" ".length() != 0);
        }
        final JsonArray asJsonArray = jsonObject.getAsJsonArray(PlayerItemParser.I[0x8F ^ 0xB5]);
        if (asJsonArray != null) {
            int j = "".length();
            "".length();
            if (1 == 4) {
                throw null;
            }
            while (j < asJsonArray.size()) {
                final JsonObject asJsonObject = asJsonArray.get(j).getAsJsonObject();
                final int[] intArray = Json.parseIntArray(asJsonObject.get(PlayerItemParser.I[0xC ^ 0x37]), "  ".length());
                if (intArray == null) {
                    throw new JsonParseException(PlayerItemParser.I[0xAC ^ 0x90]);
                }
                final float[] floatArray3 = Json.parseFloatArray(asJsonObject.get(PlayerItemParser.I[0x13 ^ 0x2E]), 0x8E ^ 0x88);
                if (floatArray3 == null) {
                    throw new JsonParseException(PlayerItemParser.I[0x78 ^ 0x46]);
                }
                if (contains) {
                    floatArray3["".length()] = -floatArray3["".length()] - floatArray3["   ".length()];
                }
                if (contains2) {
                    floatArray3[" ".length()] = -floatArray3[" ".length()] - floatArray3[0xC6 ^ 0xC2];
                }
                if (contains3) {
                    floatArray3["  ".length()] = -floatArray3["  ".length()] - floatArray3[0x15 ^ 0x10];
                }
                final float float1 = Json.getFloat(asJsonObject, PlayerItemParser.I[0x9F ^ 0xA0], 0.0f);
                modelRenderer.setTextureOffset(intArray["".length()], intArray[" ".length()]);
                modelRenderer.addBox(floatArray3["".length()], floatArray3[" ".length()], floatArray3["  ".length()], (int)floatArray3["   ".length()], (int)floatArray3[0x8F ^ 0x8B], (int)floatArray3[0x73 ^ 0x76], float1);
                ++j;
            }
        }
        final JsonArray asJsonArray2 = jsonObject.getAsJsonArray(PlayerItemParser.I[0x7F ^ 0x3F]);
        if (asJsonArray2 != null) {
            int k = "".length();
            "".length();
            if (4 < 3) {
                throw null;
            }
            while (k < asJsonArray2.size()) {
                final JsonObject asJsonObject2 = asJsonArray2.get(k).getAsJsonObject();
                final int[] intArray2 = Json.parseIntArray(asJsonObject2.get(PlayerItemParser.I[0x5 ^ 0x44]), "  ".length());
                if (intArray2 == null) {
                    throw new JsonParseException(PlayerItemParser.I[0xFB ^ 0xB9]);
                }
                final float[] floatArray4 = Json.parseFloatArray(asJsonObject2.get(PlayerItemParser.I[0x4C ^ 0xF]), 0x44 ^ 0x42);
                if (floatArray4 == null) {
                    throw new JsonParseException(PlayerItemParser.I[0x6F ^ 0x2B]);
                }
                if (contains) {
                    floatArray4["".length()] = -floatArray4["".length()] - floatArray4["   ".length()];
                }
                if (contains2) {
                    floatArray4[" ".length()] = -floatArray4[" ".length()] - floatArray4[0x82 ^ 0x86];
                }
                if (contains3) {
                    floatArray4["  ".length()] = -floatArray4["  ".length()] - floatArray4[0x3F ^ 0x3A];
                }
                final float float2 = Json.getFloat(asJsonObject2, PlayerItemParser.I[0x7B ^ 0x3E], 0.0f);
                modelRenderer.setTextureOffset(intArray2["".length()], intArray2[" ".length()]);
                modelRenderer.addSprite(floatArray4["".length()], floatArray4[" ".length()], floatArray4["  ".length()], (int)floatArray4["   ".length()], (int)floatArray4[0xB8 ^ 0xBC], (int)floatArray4[0x50 ^ 0x55], float2);
                ++k;
            }
        }
        final JsonObject jsonObject2 = (JsonObject)jsonObject.get(PlayerItemParser.I[0x25 ^ 0x63]);
        if (jsonObject2 != null) {
            modelRenderer.addChild(parseModelRenderer(jsonObject2, modelBase));
        }
        final JsonArray jsonArray = (JsonArray)jsonObject.get(PlayerItemParser.I[0x3E ^ 0x79]);
        if (jsonArray != null) {
            int l = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (l < jsonArray.size()) {
                modelRenderer.addChild(parseModelRenderer((JsonObject)jsonArray.get(l), modelBase));
                ++l;
            }
        }
        return modelRenderer;
    }
    
    private static PlayerItemRenderer parseItemRenderer(final JsonObject jsonObject, final Dimension dimension) {
        final String string = Json.getString(jsonObject, PlayerItemParser.I[0x1C ^ 0x36]);
        if (!Config.equals(string, PlayerItemParser.I[0x54 ^ 0x7F])) {
            Config.warn(PlayerItemParser.I[0x70 ^ 0x5C] + string);
            return null;
        }
        final int attachModel = parseAttachModel(Json.getString(jsonObject, PlayerItemParser.I[0x58 ^ 0x75]));
        final float float1 = Json.getFloat(jsonObject, PlayerItemParser.I[0x84 ^ 0xAA], 1.0f);
        final ModelPlayerItem modelPlayerItem = new ModelPlayerItem();
        modelPlayerItem.textureWidth = dimension.width;
        modelPlayerItem.textureHeight = dimension.height;
        return new PlayerItemRenderer(attachModel, float1, parseModelRenderer(jsonObject, modelPlayerItem));
    }
    
    public static PlayerItemModel parseItemModel(final JsonObject jsonObject) {
        final String string = Json.getString(jsonObject, PlayerItemParser.I[0x7E ^ 0x68]);
        if (!Config.equals(string, PlayerItemParser.I[0xA ^ 0x1D])) {
            throw new JsonParseException(PlayerItemParser.I[0xE ^ 0x16] + string);
        }
        final int[] intArray = Json.parseIntArray(jsonObject.get(PlayerItemParser.I[0x64 ^ 0x7D]), "  ".length());
        checkNull(intArray, PlayerItemParser.I[0xAD ^ 0xB7]);
        final Dimension dimension = new Dimension(intArray["".length()], intArray[" ".length()]);
        final boolean boolean1 = Json.getBoolean(jsonObject, PlayerItemParser.I[0x90 ^ 0x8B], "".length() != 0);
        final JsonArray jsonArray = (JsonArray)jsonObject.get(PlayerItemParser.I[0xB8 ^ 0xA4]);
        checkNull(jsonArray, PlayerItemParser.I[0x54 ^ 0x49]);
        final HashMap<String, JsonObject> hashMap = (HashMap<String, JsonObject>)new HashMap<Object, JsonObject>();
        final ArrayList<PlayerItemRenderer> list = new ArrayList<PlayerItemRenderer>();
        new ArrayList();
        int i = "".length();
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (i < jsonArray.size()) {
            final JsonObject jsonObject2 = (JsonObject)jsonArray.get(i);
            final String string2 = Json.getString(jsonObject2, PlayerItemParser.I[0x28 ^ 0x36]);
            Label_0511: {
                if (string2 != null) {
                    final JsonObject jsonObject3 = hashMap.get(string2);
                    if (jsonObject3 == null) {
                        Config.warn(PlayerItemParser.I[0xA1 ^ 0xBE] + string2);
                        "".length();
                        if (3 == -1) {
                            throw null;
                        }
                        break Label_0511;
                    }
                    else {
                        final Iterator iterator = jsonObject3.entrySet().iterator();
                        "".length();
                        if (2 == 1) {
                            throw null;
                        }
                        while (iterator.hasNext()) {
                            final Map.Entry<String, V> entry = iterator.next();
                            if (!jsonObject2.has((String)entry.getKey())) {
                                jsonObject2.add((String)entry.getKey(), (JsonElement)entry.getValue());
                            }
                        }
                    }
                }
                final String string3 = Json.getString(jsonObject2, PlayerItemParser.I[0x22 ^ 0x2]);
                if (string3 != null) {
                    if (!hashMap.containsKey(string3)) {
                        hashMap.put(string3, jsonObject2);
                        "".length();
                        if (1 < 1) {
                            throw null;
                        }
                    }
                    else {
                        Config.warn(PlayerItemParser.I[0x16 ^ 0x37] + string3);
                    }
                }
                final PlayerItemRenderer itemRenderer = parseItemRenderer(jsonObject2, dimension);
                if (itemRenderer != null) {
                    list.add(itemRenderer);
                }
            }
            ++i;
        }
        return new PlayerItemModel(dimension, boolean1, list.toArray(new PlayerItemRenderer[list.size()]));
    }
    
    private static void checkNull(final Object o, final String s) {
        if (o == null) {
            throw new JsonParseException(s);
        }
    }
    
    static {
        I();
        ITEM_TEXTURE_SIZE = PlayerItemParser.I["".length()];
        MODEL_SPRITES = PlayerItemParser.I[" ".length()];
        MODEL_SUBMODELS = PlayerItemParser.I["  ".length()];
        BOX_TEXTURE_OFFSET = PlayerItemParser.I["   ".length()];
        BOX_SIZE_ADD = PlayerItemParser.I[0xBB ^ 0xBF];
        MODEL_TYPE_BOX = PlayerItemParser.I[0xB9 ^ 0xBC];
        MODEL_INVERT_AXIS = PlayerItemParser.I[0xA6 ^ 0xA0];
        MODEL_BOXES = PlayerItemParser.I[0x28 ^ 0x2F];
        BOX_COORDINATES = PlayerItemParser.I[0x73 ^ 0x7B];
        MODEL_SUBMODEL = PlayerItemParser.I[0xBC ^ 0xB5];
        MODEL_ATTACH_TO = PlayerItemParser.I[0xAC ^ 0xA6];
        MODEL_MIRROR_TEXTURE = PlayerItemParser.I[0xB5 ^ 0xBE];
        MODEL_BASE_ID = PlayerItemParser.I[0x22 ^ 0x2E];
        MODEL_TRANSLATE = PlayerItemParser.I[0x36 ^ 0x3B];
        ITEM_TYPE = PlayerItemParser.I[0xB ^ 0x5];
        MODEL_SCALE = PlayerItemParser.I[0x62 ^ 0x6D];
        ITEM_USE_PLAYER_TEXTURE = PlayerItemParser.I[0x46 ^ 0x56];
        ITEM_MODELS = PlayerItemParser.I[0x7B ^ 0x6A];
        MODEL_ROTATE = PlayerItemParser.I[0x90 ^ 0x82];
        ITEM_TYPE_MODEL = PlayerItemParser.I[0x2E ^ 0x3D];
        MODEL_ID = PlayerItemParser.I[0xB5 ^ 0xA1];
        MODEL_TYPE = PlayerItemParser.I[0x8 ^ 0x1D];
        PlayerItemParser.jsonParser = new JsonParser();
    }
    
    private static int parseAttachModel(final String s) {
        if (s == null) {
            return "".length();
        }
        if (s.equals(PlayerItemParser.I[0x1B ^ 0x39])) {
            return "".length();
        }
        if (s.equals(PlayerItemParser.I[0x75 ^ 0x56])) {
            return " ".length();
        }
        if (s.equals(PlayerItemParser.I[0x80 ^ 0xA4])) {
            return "  ".length();
        }
        if (s.equals(PlayerItemParser.I[0xF ^ 0x2A])) {
            return "   ".length();
        }
        if (s.equals(PlayerItemParser.I[0xAA ^ 0x8C])) {
            return 0xBD ^ 0xB9;
        }
        if (s.equals(PlayerItemParser.I[0xA1 ^ 0x86])) {
            return 0x79 ^ 0x7C;
        }
        if (s.equals(PlayerItemParser.I[0x2D ^ 0x5])) {
            return 0x49 ^ 0x4F;
        }
        Config.warn(PlayerItemParser.I[0xEA ^ 0xC3] + s);
        return "".length();
    }
    
    private static void I() {
        (I = new String[0x3D ^ 0x75])["".length()] = I("\u0013\u0012?\u0011\"\u0015\u0012\u0014\f-\u0002", "gwGeW");
        PlayerItemParser.I[" ".length()] = I("\u0002#\u0006\u0002\u0004\u0014 ", "qStkp");
        PlayerItemParser.I["  ".length()] = I("8\u0000\n5?/\u0010\u0004+", "KuhXP");
        PlayerItemParser.I["   ".length()] = I("=+/\u0018';+\u0018\n4:+#", "INWlR");
        PlayerItemParser.I[0x9C ^ 0x98] = I("\t \n3\u000e\u001e-", "zIpVO");
        PlayerItemParser.I[0x4 ^ 0x1] = I("\u001c\u0019!6\u0018\u0013\u0019=", "QvESt");
        PlayerItemParser.I[0x18 ^ 0x1E] = I("\u0006,\u0013(;\u001b\u0003\u001d$:", "oBeMI");
        PlayerItemParser.I[0xA0 ^ 0xA7] = I("\u000f\u0017;\r\u0011", "mxChb");
        PlayerItemParser.I[0x4F ^ 0x47] = I("4\u001b\u00061>>\u001a\b7?$", "WtiCZ");
        PlayerItemParser.I[0x1D ^ 0x14] = I(";8()\u0003,(&", "HMJDl");
        PlayerItemParser.I[0x47 ^ 0x4D] = I("\u0006\u000e>6;\u000f.%", "gzJWX");
        PlayerItemParser.I[0xC8 ^ 0xC3] = I("\u001c*#\u0007\t\u0003\u00174\r\u0012\u000414", "qCQuf");
        PlayerItemParser.I[0xBD ^ 0xB1] = I("\f\u000e\u0003\u0011\u001b\n", "noptR");
        PlayerItemParser.I[0x42 ^ 0x4F] = I("\"79-\u0005:$,&", "VEXCv");
        PlayerItemParser.I[0x90 ^ 0x9E] = I("\u0005\u000e\b\u0010", "qwxuu");
        PlayerItemParser.I[0xC9 ^ 0xC6] = I("\u001c:\"8\u001d", "oYCTx");
        PlayerItemParser.I[0x6E ^ 0x7E] = I("\u000f\u0015\u000f4)\u001b\u001f\u000f\u0016\u0011\u001f\u001e\u001e\u00117\u001f", "zfjdE");
        PlayerItemParser.I[0x89 ^ 0x98] = I("\u0004\u001c,\t<\u001a", "isHlP");
        PlayerItemParser.I[0x10 ^ 0x2] = I("#\u001a\u0001\u001b\u00164", "Quuzb");
        PlayerItemParser.I[0x7C ^ 0x6F] = I("\u0016%\u0005\u001f\u00144\u0000\u0010\u0003\u001c", "FIdfq");
        PlayerItemParser.I[0x44 ^ 0x50] = I("%=", "LYMCx");
        PlayerItemParser.I[0x88 ^ 0x9D] = I("!\u0000>\u0012", "UyNwi");
        PlayerItemParser.I[0x29 ^ 0x3F] = I("%\u0017\t\u000b", "Qnynf");
        PlayerItemParser.I[0xA6 ^ 0xB1] = I("\u0013$\t +1\u0001\u001c<#", "CHhYN");
        PlayerItemParser.I[0xC ^ 0x14] = I("\u0010\u001e\u0011-\u00152\u001eZ.\u0015!\u0015\u0016c\u000e<\u0000\u001fyZ", "EpzCz");
        PlayerItemParser.I[0xB1 ^ 0xA8] = I("\u0000\u0001\u0016%\f\u0006\u0001=8\u0003\u0011", "tdnQy");
        PlayerItemParser.I[0xC ^ 0x16] = I("*.\u00198!\t J?-\u001f3\u001f9-G4\u00031-", "gGjKH");
        PlayerItemParser.I[0x58 ^ 0x43] = I("\u0003\u00151<\u0004\u0017\u001f1\u001e<\u0013\u001e \u0019\u001a\u0013", "vfTlh");
        PlayerItemParser.I[0xAC ^ 0xB0] = I("\u000b\u0004\u0016#5\u0015", "fkrFY");
        PlayerItemParser.I[0x5E ^ 0x43] = I(" \u001f\u001d6\u000b\u0003\u0011N \u000e\b\u001b\u000b+\u0016\u001e", "mvnEb");
        PlayerItemParser.I[0xC ^ 0x12] = I(":\u0004\n\u0003\u0005<", "XeyfL");
        PlayerItemParser.I[0x2 ^ 0x1D] = I("3\u0018+\u0013,5Y6\u0019\u0011Q\u001f7\u0003\u000b\u0015Cx", "qyXve");
        PlayerItemParser.I[0x8A ^ 0xAA] = I("$<", "MXdoR");
        PlayerItemParser.I[0x8E ^ 0xAF] = I("<\u000f7$\u001a\u001b\u001b3-S\u0015\u0015#-\u001fX3\u0003rS", "xzGHs");
        PlayerItemParser.I[0x9D ^ 0xBF] = I("7\u0001<\u0012", "UnXkZ");
        PlayerItemParser.I[0xBD ^ 0x9E] = I("'41\u0017", "OQPsz");
        PlayerItemParser.I[0x6 ^ 0x22] = I("\"\n,,\u0019<\u0002", "NoJXX");
        PlayerItemParser.I[0x3 ^ 0x26] = I("+\b\u0011\u000e\u0019\u0018\u0013\u001b", "Yavfm");
        PlayerItemParser.I[0x42 ^ 0x64] = I("\u001c\"\u0016\u0010\u0014\u0015 ", "pGpdX");
        PlayerItemParser.I[0x47 ^ 0x60] = I("\u0011\u0013\u001f\u000b?/\u001f\u001f", "czxcK");
        PlayerItemParser.I[0x85 ^ 0xAD] = I("\u0011\u0014\u0016)", "rufLe");
        PlayerItemParser.I[0x8 ^ 0x21] = I("\u001b$,!(9$g.3:+$'\n!.\"#}n", "NJGOG");
        PlayerItemParser.I[0x84 ^ 0xAE] = I("?1\u00174", "KHgQT");
        PlayerItemParser.I[0x66 ^ 0x4D] = I("\f\f\u0013\u0011\u001b\u0003\f\u000f", "Acwtw");
        PlayerItemParser.I[0x94 ^ 0xB8] = I(",;\t\u0000)\u000e;B\u0003)\u001d0\u000eN2\u0000%\u0007Tf", "yUbnF");
        PlayerItemParser.I[0xBE ^ 0x93] = I("&:\u000491/\u001a\u001f", "GNpXR");
        PlayerItemParser.I[0x7D ^ 0x53] = I("\u0001\u001b\r'\u0015", "rxlKp");
        PlayerItemParser.I[0x14 ^ 0x3B] = I(":485\u0016'\u001b69\u0017", "SZNPd");
        PlayerItemParser.I[0x6D ^ 0x5D] = I("", "OjEKb");
        PlayerItemParser.I[0x4D ^ 0x7C] = I("\r", "uPVFY");
        PlayerItemParser.I[0x2A ^ 0x18] = I("=", "DiKtK");
        PlayerItemParser.I[0x51 ^ 0x62] = I("\n", "pfMtj");
        PlayerItemParser.I[0x59 ^ 0x6D] = I("#\b\u0000-\u0017;\u001b\u0015&", "WzaCd");
        PlayerItemParser.I[0x9C ^ 0xA9] = I("5?-4\u001d\"", "GPYUi");
        PlayerItemParser.I[0x81 ^ 0xB7] = I("\u001b=\u0006\u0017;\u0004\u0000\u0011\u001d \u0003&\u0011", "vTteT");
        PlayerItemParser.I[0xA8 ^ 0x9F] = I("", "VjrTL");
        PlayerItemParser.I[0xAA ^ 0x92] = I(" ", "UGWEh");
        PlayerItemParser.I[0x70 ^ 0x49] = I("\u0018", "ncwcm");
        PlayerItemParser.I[0x3 ^ 0x39] = I("77\u000f\u0001\u0011", "UXwdb");
        PlayerItemParser.I[0xAA ^ 0x91] = I(".&\u001f\u001f\u0006(&(\r\u0015)&\u0013", "ZCgks");
        PlayerItemParser.I[0x6B ^ 0x57] = I("\u0004\u0001\u0010\u0013\u0017\"\u0001H\b\u00046\u0017\r\u0013B>\u000b\u001cG\u0011 \u0001\u000b\u000e\u00049\u0001\f", "Pdhgb");
        PlayerItemParser.I[0x56 ^ 0x6B] = I("\u0010\u0005\u0019\u001d<\u001a\u0004\u0017\u001b=\u0000", "sjvoX");
        PlayerItemParser.I[0xFA ^ 0xC4] = I("\f.?$\u000e&/1\"\u000f<a>9\u001eo2 3\t&'93\u000e", "OAPVj");
        PlayerItemParser.I[0x77 ^ 0x48] = I("\u0011\u0001\u0011\u0017;\u0006\f", "bhkrz");
        PlayerItemParser.I[0x7F ^ 0x3F] = I("\u001e\"\u0016?>\b!", "mRdVJ");
        PlayerItemParser.I[0x1A ^ 0x5B] = I("%#\u0000=\u0012##7/\u0001\"#\f", "QFxIg");
        PlayerItemParser.I[0x6B ^ 0x29] = I("=\u0004\t\u00187\u001b\u0004Q\u0003$\u000f\u0012\u0014\u0018b\u0007\u000e\u0005L1\u0019\u0004\u0012\u0005$\u0000\u0004\u0015", "iaqlB");
        PlayerItemParser.I[0x7E ^ 0x3D] = I("\f\u001e\u0017\u0014.\u0006\u001f\u0019\u0012/\u001c", "oqxfJ");
        PlayerItemParser.I[0xCB ^ 0x8F] = I(":,&0\u0011\u0010-(6\u0010\nc'-\u0001Y09'\u0016\u0010% '\u0011", "yCIBu");
        PlayerItemParser.I[0xD6 ^ 0x93] = I("\u0002\u0004\u0017<5\u0015\t", "qmmYt");
        PlayerItemParser.I[0x3A ^ 0x7C] = I("\u001e\u0006\u001645\t\u0016\u0018", "mstYZ");
        PlayerItemParser.I[0xD2 ^ 0x95] = I("\u0010/64>\u0007?8*", "cZTYQ");
    }
}
