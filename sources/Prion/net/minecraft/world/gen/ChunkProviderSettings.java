package net.minecraft.world.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;
import net.minecraft.world.biome.BiomeGenBase;

public class ChunkProviderSettings
{
  public final float field_177811_a;
  public final float field_177809_b;
  public final float field_177810_c;
  public final float field_177806_d;
  public final float field_177808_e;
  public final float field_177803_f;
  public final float field_177804_g;
  public final float field_177825_h;
  public final float field_177827_i;
  public final float field_177821_j;
  public final float field_177823_k;
  public final float field_177817_l;
  public final float field_177819_m;
  public final float field_177813_n;
  public final float field_177815_o;
  public final float field_177843_p;
  public final int field_177841_q;
  public final boolean field_177839_r;
  public final boolean field_177837_s;
  public final int field_177835_t;
  public final boolean field_177833_u;
  public final boolean field_177831_v;
  public final boolean field_177829_w;
  public final boolean field_177854_x;
  public final boolean field_177852_y;
  public final boolean field_177850_z;
  public final boolean field_177781_A;
  public final int field_177782_B;
  public final boolean field_177783_C;
  public final int field_177777_D;
  public final boolean field_177778_E;
  public final int field_177779_F;
  public final int field_177780_G;
  public final int field_177788_H;
  public final int field_177789_I;
  public final int field_177790_J;
  public final int field_177791_K;
  public final int field_177784_L;
  public final int field_177785_M;
  public final int field_177786_N;
  public final int field_177787_O;
  public final int field_177797_P;
  public final int field_177796_Q;
  public final int field_177799_R;
  public final int field_177798_S;
  public final int field_177793_T;
  public final int field_177792_U;
  public final int field_177795_V;
  public final int field_177794_W;
  public final int field_177801_X;
  public final int field_177800_Y;
  public final int field_177802_Z;
  public final int field_177846_aa;
  public final int field_177847_ab;
  public final int field_177844_ac;
  public final int field_177845_ad;
  public final int field_177851_ae;
  public final int field_177853_af;
  public final int field_177848_ag;
  public final int field_177849_ah;
  public final int field_177832_ai;
  public final int field_177834_aj;
  public final int field_177828_ak;
  public final int field_177830_al;
  public final int field_177840_am;
  public final int field_177842_an;
  public final int field_177836_ao;
  public final int field_177838_ap;
  public final int field_177818_aq;
  public final int field_177816_ar;
  public final int field_177814_as;
  public final int field_177812_at;
  public final int field_177826_au;
  public final int field_177824_av;
  public final int field_177822_aw;
  public final int field_177820_ax;
  public final int field_177807_ay;
  public final int field_177805_az;
  private static final String __OBFID = "CL_00002006";
  
  private ChunkProviderSettings(Factory p_i45639_1_)
  {
    field_177811_a = field_177899_b;
    field_177809_b = field_177900_c;
    field_177810_c = field_177896_d;
    field_177806_d = field_177898_e;
    field_177808_e = field_177893_f;
    field_177803_f = field_177894_g;
    field_177804_g = field_177915_h;
    field_177825_h = field_177917_i;
    field_177827_i = field_177911_j;
    field_177821_j = field_177913_k;
    field_177823_k = field_177907_l;
    field_177817_l = field_177909_m;
    field_177819_m = field_177903_n;
    field_177813_n = field_177905_o;
    field_177815_o = field_177933_p;
    field_177843_p = field_177931_q;
    field_177841_q = field_177929_r;
    field_177839_r = field_177927_s;
    field_177837_s = field_177925_t;
    field_177835_t = field_177923_u;
    field_177833_u = field_177921_v;
    field_177831_v = field_177919_w;
    field_177829_w = field_177944_x;
    field_177854_x = field_177942_y;
    field_177852_y = field_177940_z;
    field_177850_z = field_177870_A;
    field_177781_A = field_177871_B;
    field_177782_B = field_177872_C;
    field_177783_C = field_177866_D;
    field_177777_D = field_177867_E;
    field_177778_E = field_177868_F;
    field_177779_F = field_177869_G;
    field_177780_G = field_177877_H;
    field_177788_H = field_177878_I;
    field_177789_I = field_177879_J;
    field_177790_J = field_177880_K;
    field_177791_K = field_177873_L;
    field_177784_L = field_177874_M;
    field_177785_M = field_177875_N;
    field_177786_N = field_177876_O;
    field_177787_O = field_177886_P;
    field_177797_P = field_177885_Q;
    field_177796_Q = field_177888_R;
    field_177799_R = field_177887_S;
    field_177798_S = field_177882_T;
    field_177793_T = field_177881_U;
    field_177792_U = field_177884_V;
    field_177795_V = field_177883_W;
    field_177794_W = field_177891_X;
    field_177801_X = field_177890_Y;
    field_177800_Y = field_177892_Z;
    field_177802_Z = field_177936_aa;
    field_177846_aa = field_177937_ab;
    field_177847_ab = field_177934_ac;
    field_177844_ac = field_177935_ad;
    field_177845_ad = field_177941_ae;
    field_177851_ae = field_177943_af;
    field_177853_af = field_177938_ag;
    field_177848_ag = field_177939_ah;
    field_177849_ah = field_177922_ai;
    field_177832_ai = field_177924_aj;
    field_177834_aj = field_177918_ak;
    field_177828_ak = field_177920_al;
    field_177830_al = field_177930_am;
    field_177840_am = field_177932_an;
    field_177842_an = field_177926_ao;
    field_177836_ao = field_177928_ap;
    field_177838_ap = field_177908_aq;
    field_177818_aq = field_177906_ar;
    field_177816_ar = field_177904_as;
    field_177814_as = field_177902_at;
    field_177812_at = field_177916_au;
    field_177826_au = field_177914_av;
    field_177824_av = field_177912_aw;
    field_177822_aw = field_177910_ax;
    field_177820_ax = field_177897_ay;
    field_177807_ay = field_177895_az;
    field_177805_az = field_177889_aA;
  }
  
  ChunkProviderSettings(Factory p_i45640_1_, Object p_i45640_2_)
  {
    this(p_i45640_1_);
  }
  
  public static class Factory
  {
    static final Gson field_177901_a = new GsonBuilder().registerTypeAdapter(Factory.class, new ChunkProviderSettings.Serializer()).create();
    public float field_177899_b = 684.412F;
    public float field_177900_c = 684.412F;
    public float field_177896_d = 512.0F;
    public float field_177898_e = 512.0F;
    public float field_177893_f = 200.0F;
    public float field_177894_g = 200.0F;
    public float field_177915_h = 0.5F;
    public float field_177917_i = 80.0F;
    public float field_177911_j = 160.0F;
    public float field_177913_k = 80.0F;
    public float field_177907_l = 8.5F;
    public float field_177909_m = 12.0F;
    public float field_177903_n = 1.0F;
    public float field_177905_o = 0.0F;
    public float field_177933_p = 1.0F;
    public float field_177931_q = 0.0F;
    public int field_177929_r = 63;
    public boolean field_177927_s = true;
    public boolean field_177925_t = true;
    public int field_177923_u = 8;
    public boolean field_177921_v = true;
    public boolean field_177919_w = true;
    public boolean field_177944_x = true;
    public boolean field_177942_y = true;
    public boolean field_177940_z = true;
    public boolean field_177870_A = true;
    public boolean field_177871_B = true;
    public int field_177872_C = 4;
    public boolean field_177866_D = true;
    public int field_177867_E = 80;
    public boolean field_177868_F = false;
    public int field_177869_G = -1;
    public int field_177877_H = 4;
    public int field_177878_I = 4;
    public int field_177879_J = 33;
    public int field_177880_K = 10;
    public int field_177873_L = 0;
    public int field_177874_M = 256;
    public int field_177875_N = 33;
    public int field_177876_O = 8;
    public int field_177886_P = 0;
    public int field_177885_Q = 256;
    public int field_177888_R = 33;
    public int field_177887_S = 10;
    public int field_177882_T = 0;
    public int field_177881_U = 80;
    public int field_177884_V = 33;
    public int field_177883_W = 10;
    public int field_177891_X = 0;
    public int field_177890_Y = 80;
    public int field_177892_Z = 33;
    public int field_177936_aa = 10;
    public int field_177937_ab = 0;
    public int field_177934_ac = 80;
    public int field_177935_ad = 17;
    public int field_177941_ae = 20;
    public int field_177943_af = 0;
    public int field_177938_ag = 128;
    public int field_177939_ah = 9;
    public int field_177922_ai = 20;
    public int field_177924_aj = 0;
    public int field_177918_ak = 64;
    public int field_177920_al = 9;
    public int field_177930_am = 2;
    public int field_177932_an = 0;
    public int field_177926_ao = 32;
    public int field_177928_ap = 8;
    public int field_177908_aq = 8;
    public int field_177906_ar = 0;
    public int field_177904_as = 16;
    public int field_177902_at = 8;
    public int field_177916_au = 1;
    public int field_177914_av = 0;
    public int field_177912_aw = 16;
    public int field_177910_ax = 7;
    public int field_177897_ay = 1;
    public int field_177895_az = 16;
    public int field_177889_aA = 16;
    private static final String __OBFID = "CL_00002004";
    
    public static Factory func_177865_a(String p_177865_0_)
    {
      if (p_177865_0_.length() == 0)
      {
        return new Factory();
      }
      

      try
      {
        return (Factory)field_177901_a.fromJson(p_177865_0_, Factory.class);
      }
      catch (Exception var2) {}
      
      return new Factory();
    }
    


    public String toString()
    {
      return field_177901_a.toJson(this);
    }
    
    public Factory()
    {
      func_177863_a();
    }
    
    public void func_177863_a()
    {
      field_177899_b = 684.412F;
      field_177900_c = 684.412F;
      field_177896_d = 512.0F;
      field_177898_e = 512.0F;
      field_177893_f = 200.0F;
      field_177894_g = 200.0F;
      field_177915_h = 0.5F;
      field_177917_i = 80.0F;
      field_177911_j = 160.0F;
      field_177913_k = 80.0F;
      field_177907_l = 8.5F;
      field_177909_m = 12.0F;
      field_177903_n = 1.0F;
      field_177905_o = 0.0F;
      field_177933_p = 1.0F;
      field_177931_q = 0.0F;
      field_177929_r = 63;
      field_177927_s = true;
      field_177925_t = true;
      field_177923_u = 8;
      field_177921_v = true;
      field_177919_w = true;
      field_177944_x = true;
      field_177942_y = true;
      field_177940_z = true;
      field_177870_A = true;
      field_177871_B = true;
      field_177872_C = 4;
      field_177866_D = true;
      field_177867_E = 80;
      field_177868_F = false;
      field_177869_G = -1;
      field_177877_H = 4;
      field_177878_I = 4;
      field_177879_J = 33;
      field_177880_K = 10;
      field_177873_L = 0;
      field_177874_M = 256;
      field_177875_N = 33;
      field_177876_O = 8;
      field_177886_P = 0;
      field_177885_Q = 256;
      field_177888_R = 33;
      field_177887_S = 10;
      field_177882_T = 0;
      field_177881_U = 80;
      field_177884_V = 33;
      field_177883_W = 10;
      field_177891_X = 0;
      field_177890_Y = 80;
      field_177892_Z = 33;
      field_177936_aa = 10;
      field_177937_ab = 0;
      field_177934_ac = 80;
      field_177935_ad = 17;
      field_177941_ae = 20;
      field_177943_af = 0;
      field_177938_ag = 128;
      field_177939_ah = 9;
      field_177922_ai = 20;
      field_177924_aj = 0;
      field_177918_ak = 64;
      field_177920_al = 9;
      field_177930_am = 2;
      field_177932_an = 0;
      field_177926_ao = 32;
      field_177928_ap = 8;
      field_177908_aq = 8;
      field_177906_ar = 0;
      field_177904_as = 16;
      field_177902_at = 8;
      field_177916_au = 1;
      field_177914_av = 0;
      field_177912_aw = 16;
      field_177910_ax = 7;
      field_177897_ay = 1;
      field_177895_az = 16;
      field_177889_aA = 16;
    }
    
    public boolean equals(Object p_equals_1_)
    {
      if (this == p_equals_1_)
      {
        return true;
      }
      if ((p_equals_1_ != null) && (getClass() == p_equals_1_.getClass()))
      {
        Factory var2 = (Factory)p_equals_1_;
        return field_177936_aa == field_177936_aa;
      }
      

      return false;
    }
    

    public int hashCode()
    {
      int var1 = field_177899_b != 0.0F ? Float.floatToIntBits(field_177899_b) : 0;
      var1 = 31 * var1 + (field_177900_c != 0.0F ? Float.floatToIntBits(field_177900_c) : 0);
      var1 = 31 * var1 + (field_177896_d != 0.0F ? Float.floatToIntBits(field_177896_d) : 0);
      var1 = 31 * var1 + (field_177898_e != 0.0F ? Float.floatToIntBits(field_177898_e) : 0);
      var1 = 31 * var1 + (field_177893_f != 0.0F ? Float.floatToIntBits(field_177893_f) : 0);
      var1 = 31 * var1 + (field_177894_g != 0.0F ? Float.floatToIntBits(field_177894_g) : 0);
      var1 = 31 * var1 + (field_177915_h != 0.0F ? Float.floatToIntBits(field_177915_h) : 0);
      var1 = 31 * var1 + (field_177917_i != 0.0F ? Float.floatToIntBits(field_177917_i) : 0);
      var1 = 31 * var1 + (field_177911_j != 0.0F ? Float.floatToIntBits(field_177911_j) : 0);
      var1 = 31 * var1 + (field_177913_k != 0.0F ? Float.floatToIntBits(field_177913_k) : 0);
      var1 = 31 * var1 + (field_177907_l != 0.0F ? Float.floatToIntBits(field_177907_l) : 0);
      var1 = 31 * var1 + (field_177909_m != 0.0F ? Float.floatToIntBits(field_177909_m) : 0);
      var1 = 31 * var1 + (field_177903_n != 0.0F ? Float.floatToIntBits(field_177903_n) : 0);
      var1 = 31 * var1 + (field_177905_o != 0.0F ? Float.floatToIntBits(field_177905_o) : 0);
      var1 = 31 * var1 + (field_177933_p != 0.0F ? Float.floatToIntBits(field_177933_p) : 0);
      var1 = 31 * var1 + (field_177931_q != 0.0F ? Float.floatToIntBits(field_177931_q) : 0);
      var1 = 31 * var1 + field_177929_r;
      var1 = 31 * var1 + (field_177927_s ? 1 : 0);
      var1 = 31 * var1 + (field_177925_t ? 1 : 0);
      var1 = 31 * var1 + field_177923_u;
      var1 = 31 * var1 + (field_177921_v ? 1 : 0);
      var1 = 31 * var1 + (field_177919_w ? 1 : 0);
      var1 = 31 * var1 + (field_177944_x ? 1 : 0);
      var1 = 31 * var1 + (field_177942_y ? 1 : 0);
      var1 = 31 * var1 + (field_177940_z ? 1 : 0);
      var1 = 31 * var1 + (field_177870_A ? 1 : 0);
      var1 = 31 * var1 + (field_177871_B ? 1 : 0);
      var1 = 31 * var1 + field_177872_C;
      var1 = 31 * var1 + (field_177866_D ? 1 : 0);
      var1 = 31 * var1 + field_177867_E;
      var1 = 31 * var1 + (field_177868_F ? 1 : 0);
      var1 = 31 * var1 + field_177869_G;
      var1 = 31 * var1 + field_177877_H;
      var1 = 31 * var1 + field_177878_I;
      var1 = 31 * var1 + field_177879_J;
      var1 = 31 * var1 + field_177880_K;
      var1 = 31 * var1 + field_177873_L;
      var1 = 31 * var1 + field_177874_M;
      var1 = 31 * var1 + field_177875_N;
      var1 = 31 * var1 + field_177876_O;
      var1 = 31 * var1 + field_177886_P;
      var1 = 31 * var1 + field_177885_Q;
      var1 = 31 * var1 + field_177888_R;
      var1 = 31 * var1 + field_177887_S;
      var1 = 31 * var1 + field_177882_T;
      var1 = 31 * var1 + field_177881_U;
      var1 = 31 * var1 + field_177884_V;
      var1 = 31 * var1 + field_177883_W;
      var1 = 31 * var1 + field_177891_X;
      var1 = 31 * var1 + field_177890_Y;
      var1 = 31 * var1 + field_177892_Z;
      var1 = 31 * var1 + field_177936_aa;
      var1 = 31 * var1 + field_177937_ab;
      var1 = 31 * var1 + field_177934_ac;
      var1 = 31 * var1 + field_177935_ad;
      var1 = 31 * var1 + field_177941_ae;
      var1 = 31 * var1 + field_177943_af;
      var1 = 31 * var1 + field_177938_ag;
      var1 = 31 * var1 + field_177939_ah;
      var1 = 31 * var1 + field_177922_ai;
      var1 = 31 * var1 + field_177924_aj;
      var1 = 31 * var1 + field_177918_ak;
      var1 = 31 * var1 + field_177920_al;
      var1 = 31 * var1 + field_177930_am;
      var1 = 31 * var1 + field_177932_an;
      var1 = 31 * var1 + field_177926_ao;
      var1 = 31 * var1 + field_177928_ap;
      var1 = 31 * var1 + field_177908_aq;
      var1 = 31 * var1 + field_177906_ar;
      var1 = 31 * var1 + field_177904_as;
      var1 = 31 * var1 + field_177902_at;
      var1 = 31 * var1 + field_177916_au;
      var1 = 31 * var1 + field_177914_av;
      var1 = 31 * var1 + field_177912_aw;
      var1 = 31 * var1 + field_177910_ax;
      var1 = 31 * var1 + field_177897_ay;
      var1 = 31 * var1 + field_177895_az;
      var1 = 31 * var1 + field_177889_aA;
      return var1;
    }
    
    public ChunkProviderSettings func_177864_b()
    {
      return new ChunkProviderSettings(this, null);
    }
  }
  
  public static class Serializer implements JsonDeserializer, JsonSerializer {
    private static final String __OBFID = "CL_00002003";
    
    public Serializer() {}
    
    public ChunkProviderSettings.Factory func_177861_a(JsonElement p_177861_1_, Type p_177861_2_, JsonDeserializationContext p_177861_3_) {
      JsonObject var4 = p_177861_1_.getAsJsonObject();
      ChunkProviderSettings.Factory var5 = new ChunkProviderSettings.Factory();
      
      try
      {
        field_177899_b = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "coordinateScale", field_177899_b);
        field_177900_c = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "heightScale", field_177900_c);
        field_177898_e = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "lowerLimitScale", field_177898_e);
        field_177896_d = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "upperLimitScale", field_177896_d);
        field_177893_f = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "depthNoiseScaleX", field_177893_f);
        field_177894_g = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "depthNoiseScaleZ", field_177894_g);
        field_177915_h = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "depthNoiseScaleExponent", field_177915_h);
        field_177917_i = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "mainNoiseScaleX", field_177917_i);
        field_177911_j = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "mainNoiseScaleY", field_177911_j);
        field_177913_k = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "mainNoiseScaleZ", field_177913_k);
        field_177907_l = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "baseSize", field_177907_l);
        field_177909_m = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "stretchY", field_177909_m);
        field_177903_n = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "biomeDepthWeight", field_177903_n);
        field_177905_o = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "biomeDepthOffset", field_177905_o);
        field_177933_p = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "biomeScaleWeight", field_177933_p);
        field_177931_q = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "biomeScaleOffset", field_177931_q);
        field_177929_r = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "seaLevel", field_177929_r);
        field_177927_s = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useCaves", field_177927_s);
        field_177925_t = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useDungeons", field_177925_t);
        field_177923_u = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dungeonChance", field_177923_u);
        field_177921_v = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useStrongholds", field_177921_v);
        field_177919_w = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useVillages", field_177919_w);
        field_177944_x = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useMineShafts", field_177944_x);
        field_177942_y = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useTemples", field_177942_y);
        field_177940_z = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useMonuments", field_177940_z);
        field_177870_A = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useRavines", field_177870_A);
        field_177871_B = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useWaterLakes", field_177871_B);
        field_177872_C = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "waterLakeChance", field_177872_C);
        field_177866_D = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useLavaLakes", field_177866_D);
        field_177867_E = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "lavaLakeChance", field_177867_E);
        field_177868_F = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useLavaOceans", field_177868_F);
        field_177869_G = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "fixedBiome", field_177869_G);
        
        if ((field_177869_G < 38) && (field_177869_G >= -1))
        {
          if (field_177869_G >= hellbiomeID)
          {
            field_177869_G += 2;
          }
          
        }
        else {
          field_177869_G = -1;
        }
        
        field_177877_H = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "biomeSize", field_177877_H);
        field_177878_I = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "riverSize", field_177878_I);
        field_177879_J = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dirtSize", field_177879_J);
        field_177880_K = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dirtCount", field_177880_K);
        field_177873_L = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dirtMinHeight", field_177873_L);
        field_177874_M = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dirtMaxHeight", field_177874_M);
        field_177875_N = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "gravelSize", field_177875_N);
        field_177876_O = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "gravelCount", field_177876_O);
        field_177886_P = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "gravelMinHeight", field_177886_P);
        field_177885_Q = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "gravelMaxHeight", field_177885_Q);
        field_177888_R = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "graniteSize", field_177888_R);
        field_177887_S = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "graniteCount", field_177887_S);
        field_177882_T = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "graniteMinHeight", field_177882_T);
        field_177881_U = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "graniteMaxHeight", field_177881_U);
        field_177884_V = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dioriteSize", field_177884_V);
        field_177883_W = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dioriteCount", field_177883_W);
        field_177891_X = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dioriteMinHeight", field_177891_X);
        field_177890_Y = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dioriteMaxHeight", field_177890_Y);
        field_177892_Z = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "andesiteSize", field_177892_Z);
        field_177936_aa = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "andesiteCount", field_177936_aa);
        field_177937_ab = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "andesiteMinHeight", field_177937_ab);
        field_177934_ac = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "andesiteMaxHeight", field_177934_ac);
        field_177935_ad = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "coalSize", field_177935_ad);
        field_177941_ae = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "coalCount", field_177941_ae);
        field_177943_af = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "coalMinHeight", field_177943_af);
        field_177938_ag = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "coalMaxHeight", field_177938_ag);
        field_177939_ah = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "ironSize", field_177939_ah);
        field_177922_ai = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "ironCount", field_177922_ai);
        field_177924_aj = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "ironMinHeight", field_177924_aj);
        field_177918_ak = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "ironMaxHeight", field_177918_ak);
        field_177920_al = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "goldSize", field_177920_al);
        field_177930_am = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "goldCount", field_177930_am);
        field_177932_an = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "goldMinHeight", field_177932_an);
        field_177926_ao = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "goldMaxHeight", field_177926_ao);
        field_177928_ap = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "redstoneSize", field_177928_ap);
        field_177908_aq = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "redstoneCount", field_177908_aq);
        field_177906_ar = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "redstoneMinHeight", field_177906_ar);
        field_177904_as = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "redstoneMaxHeight", field_177904_as);
        field_177902_at = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "diamondSize", field_177902_at);
        field_177916_au = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "diamondCount", field_177916_au);
        field_177914_av = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "diamondMinHeight", field_177914_av);
        field_177912_aw = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "diamondMaxHeight", field_177912_aw);
        field_177910_ax = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "lapisSize", field_177910_ax);
        field_177897_ay = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "lapisCount", field_177897_ay);
        field_177895_az = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "lapisCenterHeight", field_177895_az);
        field_177889_aA = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "lapisSpread", field_177889_aA);
      }
      catch (Exception localException) {}
      



      return var5;
    }
    
    public JsonElement func_177862_a(ChunkProviderSettings.Factory p_177862_1_, Type p_177862_2_, JsonSerializationContext p_177862_3_)
    {
      JsonObject var4 = new JsonObject();
      var4.addProperty("coordinateScale", Float.valueOf(field_177899_b));
      var4.addProperty("heightScale", Float.valueOf(field_177900_c));
      var4.addProperty("lowerLimitScale", Float.valueOf(field_177898_e));
      var4.addProperty("upperLimitScale", Float.valueOf(field_177896_d));
      var4.addProperty("depthNoiseScaleX", Float.valueOf(field_177893_f));
      var4.addProperty("depthNoiseScaleZ", Float.valueOf(field_177894_g));
      var4.addProperty("depthNoiseScaleExponent", Float.valueOf(field_177915_h));
      var4.addProperty("mainNoiseScaleX", Float.valueOf(field_177917_i));
      var4.addProperty("mainNoiseScaleY", Float.valueOf(field_177911_j));
      var4.addProperty("mainNoiseScaleZ", Float.valueOf(field_177913_k));
      var4.addProperty("baseSize", Float.valueOf(field_177907_l));
      var4.addProperty("stretchY", Float.valueOf(field_177909_m));
      var4.addProperty("biomeDepthWeight", Float.valueOf(field_177903_n));
      var4.addProperty("biomeDepthOffset", Float.valueOf(field_177905_o));
      var4.addProperty("biomeScaleWeight", Float.valueOf(field_177933_p));
      var4.addProperty("biomeScaleOffset", Float.valueOf(field_177931_q));
      var4.addProperty("seaLevel", Integer.valueOf(field_177929_r));
      var4.addProperty("useCaves", Boolean.valueOf(field_177927_s));
      var4.addProperty("useDungeons", Boolean.valueOf(field_177925_t));
      var4.addProperty("dungeonChance", Integer.valueOf(field_177923_u));
      var4.addProperty("useStrongholds", Boolean.valueOf(field_177921_v));
      var4.addProperty("useVillages", Boolean.valueOf(field_177919_w));
      var4.addProperty("useMineShafts", Boolean.valueOf(field_177944_x));
      var4.addProperty("useTemples", Boolean.valueOf(field_177942_y));
      var4.addProperty("useMonuments", Boolean.valueOf(field_177940_z));
      var4.addProperty("useRavines", Boolean.valueOf(field_177870_A));
      var4.addProperty("useWaterLakes", Boolean.valueOf(field_177871_B));
      var4.addProperty("waterLakeChance", Integer.valueOf(field_177872_C));
      var4.addProperty("useLavaLakes", Boolean.valueOf(field_177866_D));
      var4.addProperty("lavaLakeChance", Integer.valueOf(field_177867_E));
      var4.addProperty("useLavaOceans", Boolean.valueOf(field_177868_F));
      var4.addProperty("fixedBiome", Integer.valueOf(field_177869_G));
      var4.addProperty("biomeSize", Integer.valueOf(field_177877_H));
      var4.addProperty("riverSize", Integer.valueOf(field_177878_I));
      var4.addProperty("dirtSize", Integer.valueOf(field_177879_J));
      var4.addProperty("dirtCount", Integer.valueOf(field_177880_K));
      var4.addProperty("dirtMinHeight", Integer.valueOf(field_177873_L));
      var4.addProperty("dirtMaxHeight", Integer.valueOf(field_177874_M));
      var4.addProperty("gravelSize", Integer.valueOf(field_177875_N));
      var4.addProperty("gravelCount", Integer.valueOf(field_177876_O));
      var4.addProperty("gravelMinHeight", Integer.valueOf(field_177886_P));
      var4.addProperty("gravelMaxHeight", Integer.valueOf(field_177885_Q));
      var4.addProperty("graniteSize", Integer.valueOf(field_177888_R));
      var4.addProperty("graniteCount", Integer.valueOf(field_177887_S));
      var4.addProperty("graniteMinHeight", Integer.valueOf(field_177882_T));
      var4.addProperty("graniteMaxHeight", Integer.valueOf(field_177881_U));
      var4.addProperty("dioriteSize", Integer.valueOf(field_177884_V));
      var4.addProperty("dioriteCount", Integer.valueOf(field_177883_W));
      var4.addProperty("dioriteMinHeight", Integer.valueOf(field_177891_X));
      var4.addProperty("dioriteMaxHeight", Integer.valueOf(field_177890_Y));
      var4.addProperty("andesiteSize", Integer.valueOf(field_177892_Z));
      var4.addProperty("andesiteCount", Integer.valueOf(field_177936_aa));
      var4.addProperty("andesiteMinHeight", Integer.valueOf(field_177937_ab));
      var4.addProperty("andesiteMaxHeight", Integer.valueOf(field_177934_ac));
      var4.addProperty("coalSize", Integer.valueOf(field_177935_ad));
      var4.addProperty("coalCount", Integer.valueOf(field_177941_ae));
      var4.addProperty("coalMinHeight", Integer.valueOf(field_177943_af));
      var4.addProperty("coalMaxHeight", Integer.valueOf(field_177938_ag));
      var4.addProperty("ironSize", Integer.valueOf(field_177939_ah));
      var4.addProperty("ironCount", Integer.valueOf(field_177922_ai));
      var4.addProperty("ironMinHeight", Integer.valueOf(field_177924_aj));
      var4.addProperty("ironMaxHeight", Integer.valueOf(field_177918_ak));
      var4.addProperty("goldSize", Integer.valueOf(field_177920_al));
      var4.addProperty("goldCount", Integer.valueOf(field_177930_am));
      var4.addProperty("goldMinHeight", Integer.valueOf(field_177932_an));
      var4.addProperty("goldMaxHeight", Integer.valueOf(field_177926_ao));
      var4.addProperty("redstoneSize", Integer.valueOf(field_177928_ap));
      var4.addProperty("redstoneCount", Integer.valueOf(field_177908_aq));
      var4.addProperty("redstoneMinHeight", Integer.valueOf(field_177906_ar));
      var4.addProperty("redstoneMaxHeight", Integer.valueOf(field_177904_as));
      var4.addProperty("diamondSize", Integer.valueOf(field_177902_at));
      var4.addProperty("diamondCount", Integer.valueOf(field_177916_au));
      var4.addProperty("diamondMinHeight", Integer.valueOf(field_177914_av));
      var4.addProperty("diamondMaxHeight", Integer.valueOf(field_177912_aw));
      var4.addProperty("lapisSize", Integer.valueOf(field_177910_ax));
      var4.addProperty("lapisCount", Integer.valueOf(field_177897_ay));
      var4.addProperty("lapisCenterHeight", Integer.valueOf(field_177895_az));
      var4.addProperty("lapisSpread", Integer.valueOf(field_177889_aA));
      return var4;
    }
    
    public Object deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_)
    {
      return func_177861_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
    }
    
    public JsonElement serialize(Object p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_)
    {
      return func_177862_a((ChunkProviderSettings.Factory)p_serialize_1_, p_serialize_2_, p_serialize_3_);
    }
  }
}
