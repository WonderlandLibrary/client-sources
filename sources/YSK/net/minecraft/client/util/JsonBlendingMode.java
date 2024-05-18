package net.minecraft.client.util;

import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import com.google.gson.*;
import net.minecraft.util.*;

public class JsonBlendingMode
{
    private static final String[] I;
    private final boolean field_148113_g;
    private final boolean field_148119_h;
    private final int field_148112_f;
    private final int field_148114_d;
    private final int field_148116_b;
    private final int field_148115_e;
    private final int field_148117_c;
    private static JsonBlendingMode field_148118_a;
    
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
            if (3 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        final int n = (0x6E ^ 0x71) * ((0x15 ^ 0xA) * ((0xBE ^ 0xA1) * ((0x15 ^ 0xA) * ((0x5F ^ 0x40) * this.field_148116_b + this.field_148117_c) + this.field_148114_d) + this.field_148115_e) + this.field_148112_f);
        int n2;
        if (this.field_148113_g) {
            n2 = " ".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = (0x8D ^ 0x92) * (n + n2);
        int n4;
        if (this.field_148119_h) {
            n4 = " ".length();
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        return n3 + n4;
    }
    
    public boolean func_148111_b() {
        return this.field_148119_h;
    }
    
    private static void I() {
        (I = new String[0x32 ^ 0x10])["".length()] = I("\u001c7' ", "zBICI");
        JsonBlendingMode.I[" ".length()] = I(">1*0", "XDDSk");
        JsonBlendingMode.I["  ".length()] = I("\u000b5&\u0007\n\u001a", "xGEum");
        JsonBlendingMode.I["   ".length()] = I("\u001f;\"8\u0016\u000e", "lIAJq");
        JsonBlendingMode.I[0x9 ^ 0xD] = I("\u000b\u00190\u001c\u001f\r", "ojDnx");
        JsonBlendingMode.I[0x6 ^ 0x3] = I("\t\u0006?\u001d$\u000f", "muKoC");
        JsonBlendingMode.I[0x90 ^ 0x96] = I("\u0019\u0000'5\r\u001a\u001a%", "jrDTa");
        JsonBlendingMode.I[0xAE ^ 0xA9] = I(":\n\u000e\u0013\u000b9\u0010\f", "Ixmrg");
        JsonBlendingMode.I[0x9A ^ 0x92] = I("\u000f\",\u000f!\u001b99", "kQXnM");
        JsonBlendingMode.I[0x1C ^ 0x15] = I("\u0011\n;\u0011/\u0005\u0011.", "uyOpC");
        JsonBlendingMode.I[0xCE ^ 0xC4] = I("\u000b\t.", "jmJBm");
        JsonBlendingMode.I[0x81 ^ 0x8A] = I("\"<6\u0013#0* ", "QITgQ");
        JsonBlendingMode.I[0x65 ^ 0x69] = I(" !\u001b)\u001b!!\u001e9\u000b&6\f/\u001d", "RDmLi");
        JsonBlendingMode.I[0x48 ^ 0x45] = I("\u001f\u0000/\r\b\u001e\u0000\u0006\u001b\u000f\u000f\u0011+\t\u0019\u0019", "meYhz");
        JsonBlendingMode.I[0x0 ^ 0xE] = I(":\u001d\u001a", "WttZz");
        JsonBlendingMode.I[0xA9 ^ 0xA6] = I("\u000e\u0012\"", "csZQA");
        JsonBlendingMode.I[0x8E ^ 0x9E] = I("\u001c", "CWdLM");
        JsonBlendingMode.I[0x1B ^ 0xA] = I("", "LlnUk");
        JsonBlendingMode.I[0x51 ^ 0x43] = I("&'\t", "IIldO");
        JsonBlendingMode.I[0x42 ^ 0x51] = I("r", "CrJvq");
        JsonBlendingMode.I[0x26 ^ 0x32] = I("\u00182*\u0015", "bWXza");
        JsonBlendingMode.I[0x3D ^ 0x28] = I("d", "THsgD");
        JsonBlendingMode.I[0x89 ^ 0x9F] = I("\u001b\u0019;$?", "vpUQL");
        JsonBlendingMode.I[0x12 ^ 0x5] = I("[", "vElNt");
        JsonBlendingMode.I[0xB ^ 0x13] = I("Y", "iIghC");
        JsonBlendingMode.I[0x82 ^ 0x9B] = I("Y", "hTozn");
        JsonBlendingMode.I[0x9D ^ 0x87] = I("\n\u00135.$\u0015\u000e$", "yaVMK");
        JsonBlendingMode.I[0x21 ^ 0x3A] = I("fj  ,4(?==", "WGSRO");
        JsonBlendingMode.I[0x2E ^ 0x32] = I("=%%\u0015+59#", "YVQvD");
        JsonBlendingMode.I[0x6B ^ 0x76] = I("xA!\u001c\u0016*\u0003)\u0000\u0010", "IlEob");
        JsonBlendingMode.I[0x7E ^ 0x60] = I("\u001c\n1\u0018'\u001f\u00103", "oxRyK");
        JsonBlendingMode.I[0x9C ^ 0x83] = I("eL\u00039\u00195\r\u0000#\u001b", "TapKz");
        JsonBlendingMode.I[0xE0 ^ 0xC0] = I("*\u0006\u000e8?>\u001d\u001b", "NuzYS");
        JsonBlendingMode.I[0x7C ^ 0x5D] = I("E^\u00000>\u0015\u001f\u0014++", "tsdCJ");
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (!(o instanceof JsonBlendingMode)) {
            return "".length() != 0;
        }
        final JsonBlendingMode jsonBlendingMode = (JsonBlendingMode)o;
        int n;
        if (this.field_148112_f != jsonBlendingMode.field_148112_f) {
            n = "".length();
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        else if (this.field_148115_e != jsonBlendingMode.field_148115_e) {
            n = "".length();
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else if (this.field_148114_d != jsonBlendingMode.field_148114_d) {
            n = "".length();
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else if (this.field_148119_h != jsonBlendingMode.field_148119_h) {
            n = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (this.field_148113_g != jsonBlendingMode.field_148113_g) {
            n = "".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else if (this.field_148117_c != jsonBlendingMode.field_148117_c) {
            n = "".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else if (this.field_148116_b == jsonBlendingMode.field_148116_b) {
            n = " ".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public JsonBlendingMode(final int n, final int n2, final int n3) {
        this("".length() != 0, "".length() != 0, n, n2, n, n2, n3);
    }
    
    public void func_148109_a() {
        if (!this.equals(JsonBlendingMode.field_148118_a)) {
            if (JsonBlendingMode.field_148118_a == null || this.field_148119_h != JsonBlendingMode.field_148118_a.func_148111_b()) {
                JsonBlendingMode.field_148118_a = this;
                if (this.field_148119_h) {
                    GlStateManager.disableBlend();
                    return;
                }
                GlStateManager.enableBlend();
            }
            GL14.glBlendEquation(this.field_148112_f);
            if (this.field_148113_g) {
                GlStateManager.tryBlendFuncSeparate(this.field_148116_b, this.field_148114_d, this.field_148117_c, this.field_148115_e);
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                GlStateManager.blendFunc(this.field_148116_b, this.field_148114_d);
            }
        }
    }
    
    public static JsonBlendingMode func_148110_a(final JsonObject jsonObject) {
        if (jsonObject == null) {
            return new JsonBlendingMode();
        }
        int func_148108_a = 31840 + 822 - 25787 + 25899;
        int n = " ".length();
        int n2 = "".length();
        int n3 = " ".length();
        int n4 = "".length();
        int n5 = " ".length();
        int n6 = "".length();
        if (JsonUtils.isString(jsonObject, JsonBlendingMode.I["".length()])) {
            func_148108_a = func_148108_a(jsonObject.get(JsonBlendingMode.I[" ".length()]).getAsString());
            if (func_148108_a != 3466 + 19644 + 4326 + 5338) {
                n5 = "".length();
            }
        }
        if (JsonUtils.isString(jsonObject, JsonBlendingMode.I["  ".length()])) {
            n = func_148107_b(jsonObject.get(JsonBlendingMode.I["   ".length()]).getAsString());
            if (n != " ".length()) {
                n5 = "".length();
            }
        }
        if (JsonUtils.isString(jsonObject, JsonBlendingMode.I[0xB3 ^ 0xB7])) {
            n2 = func_148107_b(jsonObject.get(JsonBlendingMode.I[0x67 ^ 0x62]).getAsString());
            if (n2 != 0) {
                n5 = "".length();
            }
        }
        if (JsonUtils.isString(jsonObject, JsonBlendingMode.I[0x55 ^ 0x53])) {
            n3 = func_148107_b(jsonObject.get(JsonBlendingMode.I[0xF ^ 0x8]).getAsString());
            if (n3 != " ".length()) {
                n5 = "".length();
            }
            n6 = " ".length();
        }
        if (JsonUtils.isString(jsonObject, JsonBlendingMode.I[0x7C ^ 0x74])) {
            n4 = func_148107_b(jsonObject.get(JsonBlendingMode.I[0x1B ^ 0x12]).getAsString());
            if (n4 != 0) {
                n5 = "".length();
            }
            n6 = " ".length();
        }
        JsonBlendingMode jsonBlendingMode;
        if (n5 != 0) {
            jsonBlendingMode = new JsonBlendingMode();
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else if (n6 != 0) {
            jsonBlendingMode = new JsonBlendingMode(n, n2, n3, n4, func_148108_a);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            jsonBlendingMode = new JsonBlendingMode(n, n2, func_148108_a);
        }
        return jsonBlendingMode;
    }
    
    static {
        I();
        JsonBlendingMode.field_148118_a = null;
    }
    
    private static int func_148108_a(final String s) {
        final String lowerCase = s.trim().toLowerCase();
        int n;
        if (lowerCase.equals(JsonBlendingMode.I[0x56 ^ 0x5C])) {
            n = 27207 + 3749 - 10587 + 12405;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else if (lowerCase.equals(JsonBlendingMode.I[0xB7 ^ 0xBC])) {
            n = 12277 + 2965 + 8351 + 9185;
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else if (lowerCase.equals(JsonBlendingMode.I[0xB1 ^ 0xBD])) {
            n = 22006 + 10299 - 3084 + 3558;
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else if (lowerCase.equals(JsonBlendingMode.I[0x80 ^ 0x8D])) {
            n = 14817 + 3000 + 3709 + 11253;
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else if (lowerCase.equals(JsonBlendingMode.I[0xA0 ^ 0xAE])) {
            n = 11515 + 15647 - 1235 + 6848;
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else if (lowerCase.equals(JsonBlendingMode.I[0xB1 ^ 0xBE])) {
            n = 1282 + 615 + 18192 + 12687;
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            n = 13999 + 13076 - 12784 + 18483;
        }
        return n;
    }
    
    private JsonBlendingMode(final boolean field_148113_g, final boolean field_148119_h, final int field_148116_b, final int field_148114_d, final int field_148117_c, final int field_148115_e, final int field_148112_f) {
        this.field_148113_g = field_148113_g;
        this.field_148116_b = field_148116_b;
        this.field_148114_d = field_148114_d;
        this.field_148117_c = field_148117_c;
        this.field_148115_e = field_148115_e;
        this.field_148119_h = field_148119_h;
        this.field_148112_f = field_148112_f;
    }
    
    public JsonBlendingMode(final int n, final int n2, final int n3, final int n4, final int n5) {
        this(" ".length() != 0, "".length() != 0, n, n2, n3, n4, n5);
    }
    
    private static int func_148107_b(final String s) {
        final String replaceAll = s.trim().toLowerCase().replaceAll(JsonBlendingMode.I[0x2A ^ 0x3A], JsonBlendingMode.I[0x3 ^ 0x12]).replaceAll(JsonBlendingMode.I[0x8A ^ 0x98], JsonBlendingMode.I[0x5F ^ 0x4C]).replaceAll(JsonBlendingMode.I[0xA9 ^ 0xBD], JsonBlendingMode.I[0x31 ^ 0x24]).replaceAll(JsonBlendingMode.I[0x55 ^ 0x43], JsonBlendingMode.I[0x8F ^ 0x98]);
        int n;
        if (replaceAll.equals(JsonBlendingMode.I[0x91 ^ 0x89])) {
            n = "".length();
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else if (replaceAll.equals(JsonBlendingMode.I[0x7C ^ 0x65])) {
            n = " ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (replaceAll.equals(JsonBlendingMode.I[0x37 ^ 0x2D])) {
            n = 158 + 215 + 347 + 48;
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else if (replaceAll.equals(JsonBlendingMode.I[0x73 ^ 0x68])) {
            n = 475 + 262 - 105 + 137;
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (replaceAll.equals(JsonBlendingMode.I[0x85 ^ 0x99])) {
            n = 395 + 235 - 607 + 751;
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else if (replaceAll.equals(JsonBlendingMode.I[0xAE ^ 0xB3])) {
            n = 703 + 205 - 648 + 515;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (replaceAll.equals(JsonBlendingMode.I[0x71 ^ 0x6F])) {
            n = 297 + 474 - 202 + 201;
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else if (replaceAll.equals(JsonBlendingMode.I[0x18 ^ 0x7])) {
            n = 162 + 85 + 220 + 304;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else if (replaceAll.equals(JsonBlendingMode.I[0xB2 ^ 0x92])) {
            n = 704 + 608 - 1065 + 525;
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else if (replaceAll.equals(JsonBlendingMode.I[0x48 ^ 0x69])) {
            n = 519 + 450 - 469 + 273;
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else {
            n = -" ".length();
        }
        return n;
    }
    
    public JsonBlendingMode() {
        this("".length() != 0, " ".length() != 0, " ".length(), "".length(), " ".length(), "".length(), 8907 + 11944 - 3550 + 15473);
    }
}
