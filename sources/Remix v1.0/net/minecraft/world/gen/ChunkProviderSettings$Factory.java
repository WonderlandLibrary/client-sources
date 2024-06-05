package net.minecraft.world.gen;

import com.google.gson.*;
import java.lang.reflect.*;

public static class Factory
{
    static final Gson field_177901_a;
    public float field_177899_b;
    public float field_177900_c;
    public float field_177896_d;
    public float field_177898_e;
    public float field_177893_f;
    public float field_177894_g;
    public float field_177915_h;
    public float field_177917_i;
    public float field_177911_j;
    public float field_177913_k;
    public float field_177907_l;
    public float field_177909_m;
    public float field_177903_n;
    public float field_177905_o;
    public float field_177933_p;
    public float field_177931_q;
    public int field_177929_r;
    public boolean field_177927_s;
    public boolean field_177925_t;
    public int field_177923_u;
    public boolean field_177921_v;
    public boolean field_177919_w;
    public boolean field_177944_x;
    public boolean field_177942_y;
    public boolean field_177940_z;
    public boolean field_177870_A;
    public boolean field_177871_B;
    public int field_177872_C;
    public boolean field_177866_D;
    public int field_177867_E;
    public boolean field_177868_F;
    public int field_177869_G;
    public int field_177877_H;
    public int field_177878_I;
    public int field_177879_J;
    public int field_177880_K;
    public int field_177873_L;
    public int field_177874_M;
    public int field_177875_N;
    public int field_177876_O;
    public int field_177886_P;
    public int field_177885_Q;
    public int field_177888_R;
    public int field_177887_S;
    public int field_177882_T;
    public int field_177881_U;
    public int field_177884_V;
    public int field_177883_W;
    public int field_177891_X;
    public int field_177890_Y;
    public int field_177892_Z;
    public int field_177936_aa;
    public int field_177937_ab;
    public int field_177934_ac;
    public int field_177935_ad;
    public int field_177941_ae;
    public int field_177943_af;
    public int field_177938_ag;
    public int field_177939_ah;
    public int field_177922_ai;
    public int field_177924_aj;
    public int field_177918_ak;
    public int field_177920_al;
    public int field_177930_am;
    public int field_177932_an;
    public int field_177926_ao;
    public int field_177928_ap;
    public int field_177908_aq;
    public int field_177906_ar;
    public int field_177904_as;
    public int field_177902_at;
    public int field_177916_au;
    public int field_177914_av;
    public int field_177912_aw;
    public int field_177910_ax;
    public int field_177897_ay;
    public int field_177895_az;
    public int field_177889_aA;
    
    public Factory() {
        this.field_177899_b = 684.412f;
        this.field_177900_c = 684.412f;
        this.field_177896_d = 512.0f;
        this.field_177898_e = 512.0f;
        this.field_177893_f = 200.0f;
        this.field_177894_g = 200.0f;
        this.field_177915_h = 0.5f;
        this.field_177917_i = 80.0f;
        this.field_177911_j = 160.0f;
        this.field_177913_k = 80.0f;
        this.field_177907_l = 8.5f;
        this.field_177909_m = 12.0f;
        this.field_177903_n = 1.0f;
        this.field_177905_o = 0.0f;
        this.field_177933_p = 1.0f;
        this.field_177931_q = 0.0f;
        this.field_177929_r = 63;
        this.field_177927_s = true;
        this.field_177925_t = true;
        this.field_177923_u = 8;
        this.field_177921_v = true;
        this.field_177919_w = true;
        this.field_177944_x = true;
        this.field_177942_y = true;
        this.field_177940_z = true;
        this.field_177870_A = true;
        this.field_177871_B = true;
        this.field_177872_C = 4;
        this.field_177866_D = true;
        this.field_177867_E = 80;
        this.field_177868_F = false;
        this.field_177869_G = -1;
        this.field_177877_H = 4;
        this.field_177878_I = 4;
        this.field_177879_J = 33;
        this.field_177880_K = 10;
        this.field_177873_L = 0;
        this.field_177874_M = 256;
        this.field_177875_N = 33;
        this.field_177876_O = 8;
        this.field_177886_P = 0;
        this.field_177885_Q = 256;
        this.field_177888_R = 33;
        this.field_177887_S = 10;
        this.field_177882_T = 0;
        this.field_177881_U = 80;
        this.field_177884_V = 33;
        this.field_177883_W = 10;
        this.field_177891_X = 0;
        this.field_177890_Y = 80;
        this.field_177892_Z = 33;
        this.field_177936_aa = 10;
        this.field_177937_ab = 0;
        this.field_177934_ac = 80;
        this.field_177935_ad = 17;
        this.field_177941_ae = 20;
        this.field_177943_af = 0;
        this.field_177938_ag = 128;
        this.field_177939_ah = 9;
        this.field_177922_ai = 20;
        this.field_177924_aj = 0;
        this.field_177918_ak = 64;
        this.field_177920_al = 9;
        this.field_177930_am = 2;
        this.field_177932_an = 0;
        this.field_177926_ao = 32;
        this.field_177928_ap = 8;
        this.field_177908_aq = 8;
        this.field_177906_ar = 0;
        this.field_177904_as = 16;
        this.field_177902_at = 8;
        this.field_177916_au = 1;
        this.field_177914_av = 0;
        this.field_177912_aw = 16;
        this.field_177910_ax = 7;
        this.field_177897_ay = 1;
        this.field_177895_az = 16;
        this.field_177889_aA = 16;
        this.func_177863_a();
    }
    
    public static Factory func_177865_a(final String p_177865_0_) {
        if (p_177865_0_.length() == 0) {
            return new Factory();
        }
        try {
            return (Factory)Factory.field_177901_a.fromJson(p_177865_0_, (Class)Factory.class);
        }
        catch (Exception var2) {
            return new Factory();
        }
    }
    
    @Override
    public String toString() {
        return Factory.field_177901_a.toJson((Object)this);
    }
    
    public void func_177863_a() {
        this.field_177899_b = 684.412f;
        this.field_177900_c = 684.412f;
        this.field_177896_d = 512.0f;
        this.field_177898_e = 512.0f;
        this.field_177893_f = 200.0f;
        this.field_177894_g = 200.0f;
        this.field_177915_h = 0.5f;
        this.field_177917_i = 80.0f;
        this.field_177911_j = 160.0f;
        this.field_177913_k = 80.0f;
        this.field_177907_l = 8.5f;
        this.field_177909_m = 12.0f;
        this.field_177903_n = 1.0f;
        this.field_177905_o = 0.0f;
        this.field_177933_p = 1.0f;
        this.field_177931_q = 0.0f;
        this.field_177929_r = 63;
        this.field_177927_s = true;
        this.field_177925_t = true;
        this.field_177923_u = 8;
        this.field_177921_v = true;
        this.field_177919_w = true;
        this.field_177944_x = true;
        this.field_177942_y = true;
        this.field_177940_z = true;
        this.field_177870_A = true;
        this.field_177871_B = true;
        this.field_177872_C = 4;
        this.field_177866_D = true;
        this.field_177867_E = 80;
        this.field_177868_F = false;
        this.field_177869_G = -1;
        this.field_177877_H = 4;
        this.field_177878_I = 4;
        this.field_177879_J = 33;
        this.field_177880_K = 10;
        this.field_177873_L = 0;
        this.field_177874_M = 256;
        this.field_177875_N = 33;
        this.field_177876_O = 8;
        this.field_177886_P = 0;
        this.field_177885_Q = 256;
        this.field_177888_R = 33;
        this.field_177887_S = 10;
        this.field_177882_T = 0;
        this.field_177881_U = 80;
        this.field_177884_V = 33;
        this.field_177883_W = 10;
        this.field_177891_X = 0;
        this.field_177890_Y = 80;
        this.field_177892_Z = 33;
        this.field_177936_aa = 10;
        this.field_177937_ab = 0;
        this.field_177934_ac = 80;
        this.field_177935_ad = 17;
        this.field_177941_ae = 20;
        this.field_177943_af = 0;
        this.field_177938_ag = 128;
        this.field_177939_ah = 9;
        this.field_177922_ai = 20;
        this.field_177924_aj = 0;
        this.field_177918_ak = 64;
        this.field_177920_al = 9;
        this.field_177930_am = 2;
        this.field_177932_an = 0;
        this.field_177926_ao = 32;
        this.field_177928_ap = 8;
        this.field_177908_aq = 8;
        this.field_177906_ar = 0;
        this.field_177904_as = 16;
        this.field_177902_at = 8;
        this.field_177916_au = 1;
        this.field_177914_av = 0;
        this.field_177912_aw = 16;
        this.field_177910_ax = 7;
        this.field_177897_ay = 1;
        this.field_177895_az = 16;
        this.field_177889_aA = 16;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            final Factory var2 = (Factory)p_equals_1_;
            return this.field_177936_aa == var2.field_177936_aa && this.field_177934_ac == var2.field_177934_ac && this.field_177937_ab == var2.field_177937_ab && this.field_177892_Z == var2.field_177892_Z && Float.compare(var2.field_177907_l, this.field_177907_l) == 0 && Float.compare(var2.field_177905_o, this.field_177905_o) == 0 && Float.compare(var2.field_177903_n, this.field_177903_n) == 0 && Float.compare(var2.field_177931_q, this.field_177931_q) == 0 && Float.compare(var2.field_177933_p, this.field_177933_p) == 0 && this.field_177877_H == var2.field_177877_H && this.field_177941_ae == var2.field_177941_ae && this.field_177938_ag == var2.field_177938_ag && this.field_177943_af == var2.field_177943_af && this.field_177935_ad == var2.field_177935_ad && Float.compare(var2.field_177899_b, this.field_177899_b) == 0 && Float.compare(var2.field_177915_h, this.field_177915_h) == 0 && Float.compare(var2.field_177893_f, this.field_177893_f) == 0 && Float.compare(var2.field_177894_g, this.field_177894_g) == 0 && this.field_177916_au == var2.field_177916_au && this.field_177912_aw == var2.field_177912_aw && this.field_177914_av == var2.field_177914_av && this.field_177902_at == var2.field_177902_at && this.field_177883_W == var2.field_177883_W && this.field_177890_Y == var2.field_177890_Y && this.field_177891_X == var2.field_177891_X && this.field_177884_V == var2.field_177884_V && this.field_177880_K == var2.field_177880_K && this.field_177874_M == var2.field_177874_M && this.field_177873_L == var2.field_177873_L && this.field_177879_J == var2.field_177879_J && this.field_177923_u == var2.field_177923_u && this.field_177869_G == var2.field_177869_G && this.field_177930_am == var2.field_177930_am && this.field_177926_ao == var2.field_177926_ao && this.field_177932_an == var2.field_177932_an && this.field_177920_al == var2.field_177920_al && this.field_177887_S == var2.field_177887_S && this.field_177881_U == var2.field_177881_U && this.field_177882_T == var2.field_177882_T && this.field_177888_R == var2.field_177888_R && this.field_177876_O == var2.field_177876_O && this.field_177885_Q == var2.field_177885_Q && this.field_177886_P == var2.field_177886_P && this.field_177875_N == var2.field_177875_N && Float.compare(var2.field_177900_c, this.field_177900_c) == 0 && this.field_177922_ai == var2.field_177922_ai && this.field_177918_ak == var2.field_177918_ak && this.field_177924_aj == var2.field_177924_aj && this.field_177939_ah == var2.field_177939_ah && this.field_177895_az == var2.field_177895_az && this.field_177897_ay == var2.field_177897_ay && this.field_177910_ax == var2.field_177910_ax && this.field_177889_aA == var2.field_177889_aA && this.field_177867_E == var2.field_177867_E && Float.compare(var2.field_177898_e, this.field_177898_e) == 0 && Float.compare(var2.field_177917_i, this.field_177917_i) == 0 && Float.compare(var2.field_177911_j, this.field_177911_j) == 0 && Float.compare(var2.field_177913_k, this.field_177913_k) == 0 && this.field_177908_aq == var2.field_177908_aq && this.field_177904_as == var2.field_177904_as && this.field_177906_ar == var2.field_177906_ar && this.field_177928_ap == var2.field_177928_ap && this.field_177878_I == var2.field_177878_I && this.field_177929_r == var2.field_177929_r && Float.compare(var2.field_177909_m, this.field_177909_m) == 0 && Float.compare(var2.field_177896_d, this.field_177896_d) == 0 && this.field_177927_s == var2.field_177927_s && this.field_177925_t == var2.field_177925_t && this.field_177866_D == var2.field_177866_D && this.field_177868_F == var2.field_177868_F && this.field_177944_x == var2.field_177944_x && this.field_177870_A == var2.field_177870_A && this.field_177921_v == var2.field_177921_v && this.field_177942_y == var2.field_177942_y && this.field_177940_z == var2.field_177940_z && this.field_177919_w == var2.field_177919_w && this.field_177871_B == var2.field_177871_B && this.field_177872_C == var2.field_177872_C;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int var1 = (this.field_177899_b != 0.0f) ? Float.floatToIntBits(this.field_177899_b) : 0;
        var1 = 31 * var1 + ((this.field_177900_c != 0.0f) ? Float.floatToIntBits(this.field_177900_c) : 0);
        var1 = 31 * var1 + ((this.field_177896_d != 0.0f) ? Float.floatToIntBits(this.field_177896_d) : 0);
        var1 = 31 * var1 + ((this.field_177898_e != 0.0f) ? Float.floatToIntBits(this.field_177898_e) : 0);
        var1 = 31 * var1 + ((this.field_177893_f != 0.0f) ? Float.floatToIntBits(this.field_177893_f) : 0);
        var1 = 31 * var1 + ((this.field_177894_g != 0.0f) ? Float.floatToIntBits(this.field_177894_g) : 0);
        var1 = 31 * var1 + ((this.field_177915_h != 0.0f) ? Float.floatToIntBits(this.field_177915_h) : 0);
        var1 = 31 * var1 + ((this.field_177917_i != 0.0f) ? Float.floatToIntBits(this.field_177917_i) : 0);
        var1 = 31 * var1 + ((this.field_177911_j != 0.0f) ? Float.floatToIntBits(this.field_177911_j) : 0);
        var1 = 31 * var1 + ((this.field_177913_k != 0.0f) ? Float.floatToIntBits(this.field_177913_k) : 0);
        var1 = 31 * var1 + ((this.field_177907_l != 0.0f) ? Float.floatToIntBits(this.field_177907_l) : 0);
        var1 = 31 * var1 + ((this.field_177909_m != 0.0f) ? Float.floatToIntBits(this.field_177909_m) : 0);
        var1 = 31 * var1 + ((this.field_177903_n != 0.0f) ? Float.floatToIntBits(this.field_177903_n) : 0);
        var1 = 31 * var1 + ((this.field_177905_o != 0.0f) ? Float.floatToIntBits(this.field_177905_o) : 0);
        var1 = 31 * var1 + ((this.field_177933_p != 0.0f) ? Float.floatToIntBits(this.field_177933_p) : 0);
        var1 = 31 * var1 + ((this.field_177931_q != 0.0f) ? Float.floatToIntBits(this.field_177931_q) : 0);
        var1 = 31 * var1 + this.field_177929_r;
        var1 = 31 * var1 + (this.field_177927_s ? 1 : 0);
        var1 = 31 * var1 + (this.field_177925_t ? 1 : 0);
        var1 = 31 * var1 + this.field_177923_u;
        var1 = 31 * var1 + (this.field_177921_v ? 1 : 0);
        var1 = 31 * var1 + (this.field_177919_w ? 1 : 0);
        var1 = 31 * var1 + (this.field_177944_x ? 1 : 0);
        var1 = 31 * var1 + (this.field_177942_y ? 1 : 0);
        var1 = 31 * var1 + (this.field_177940_z ? 1 : 0);
        var1 = 31 * var1 + (this.field_177870_A ? 1 : 0);
        var1 = 31 * var1 + (this.field_177871_B ? 1 : 0);
        var1 = 31 * var1 + this.field_177872_C;
        var1 = 31 * var1 + (this.field_177866_D ? 1 : 0);
        var1 = 31 * var1 + this.field_177867_E;
        var1 = 31 * var1 + (this.field_177868_F ? 1 : 0);
        var1 = 31 * var1 + this.field_177869_G;
        var1 = 31 * var1 + this.field_177877_H;
        var1 = 31 * var1 + this.field_177878_I;
        var1 = 31 * var1 + this.field_177879_J;
        var1 = 31 * var1 + this.field_177880_K;
        var1 = 31 * var1 + this.field_177873_L;
        var1 = 31 * var1 + this.field_177874_M;
        var1 = 31 * var1 + this.field_177875_N;
        var1 = 31 * var1 + this.field_177876_O;
        var1 = 31 * var1 + this.field_177886_P;
        var1 = 31 * var1 + this.field_177885_Q;
        var1 = 31 * var1 + this.field_177888_R;
        var1 = 31 * var1 + this.field_177887_S;
        var1 = 31 * var1 + this.field_177882_T;
        var1 = 31 * var1 + this.field_177881_U;
        var1 = 31 * var1 + this.field_177884_V;
        var1 = 31 * var1 + this.field_177883_W;
        var1 = 31 * var1 + this.field_177891_X;
        var1 = 31 * var1 + this.field_177890_Y;
        var1 = 31 * var1 + this.field_177892_Z;
        var1 = 31 * var1 + this.field_177936_aa;
        var1 = 31 * var1 + this.field_177937_ab;
        var1 = 31 * var1 + this.field_177934_ac;
        var1 = 31 * var1 + this.field_177935_ad;
        var1 = 31 * var1 + this.field_177941_ae;
        var1 = 31 * var1 + this.field_177943_af;
        var1 = 31 * var1 + this.field_177938_ag;
        var1 = 31 * var1 + this.field_177939_ah;
        var1 = 31 * var1 + this.field_177922_ai;
        var1 = 31 * var1 + this.field_177924_aj;
        var1 = 31 * var1 + this.field_177918_ak;
        var1 = 31 * var1 + this.field_177920_al;
        var1 = 31 * var1 + this.field_177930_am;
        var1 = 31 * var1 + this.field_177932_an;
        var1 = 31 * var1 + this.field_177926_ao;
        var1 = 31 * var1 + this.field_177928_ap;
        var1 = 31 * var1 + this.field_177908_aq;
        var1 = 31 * var1 + this.field_177906_ar;
        var1 = 31 * var1 + this.field_177904_as;
        var1 = 31 * var1 + this.field_177902_at;
        var1 = 31 * var1 + this.field_177916_au;
        var1 = 31 * var1 + this.field_177914_av;
        var1 = 31 * var1 + this.field_177912_aw;
        var1 = 31 * var1 + this.field_177910_ax;
        var1 = 31 * var1 + this.field_177897_ay;
        var1 = 31 * var1 + this.field_177895_az;
        var1 = 31 * var1 + this.field_177889_aA;
        return var1;
    }
    
    public ChunkProviderSettings func_177864_b() {
        return new ChunkProviderSettings(this, null);
    }
    
    static {
        field_177901_a = new GsonBuilder().registerTypeAdapter((Type)Factory.class, (Object)new Serializer()).create();
    }
}
