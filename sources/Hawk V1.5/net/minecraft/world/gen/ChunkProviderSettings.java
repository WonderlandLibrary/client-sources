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

public class ChunkProviderSettings {
   public final int field_177814_as;
   public final int field_177794_W;
   public final float field_177815_o;
   public final int field_177812_at;
   public final int field_177849_ah;
   public final int field_177847_ab;
   public final boolean field_177837_s;
   public final int field_177798_S;
   public final int field_177830_al;
   public final float field_177804_g;
   public final int field_177828_ak;
   public final int field_177797_P;
   public final int field_177805_az;
   public final int field_177790_J;
   public final float field_177811_a;
   public final int field_177846_aa;
   public final int field_177836_ao;
   public final int field_177841_q;
   public final float field_177809_b;
   public final int field_177835_t;
   public final int field_177802_Z;
   public final int field_177842_an;
   public final boolean field_177850_z;
   public final int field_177822_aw;
   public final boolean field_177833_u;
   public final int field_177782_B;
   public final float field_177813_n;
   public final int field_177784_L;
   public final int field_177848_ag;
   public final float field_177823_k;
   public final int field_177796_Q;
   public final int field_177816_ar;
   public final int field_177834_aj;
   public final float field_177825_h;
   public final int field_177824_av;
   public final int field_177807_ay;
   public final float field_177808_e;
   public final int field_177853_af;
   public final float field_177810_c;
   public final int field_177799_R;
   public final int field_177800_Y;
   public final int field_177788_H;
   public final int field_177792_U;
   public final boolean field_177852_y;
   public final int field_177840_am;
   public final int field_177844_ac;
   public final int field_177791_K;
   public final int field_177832_ai;
   private static final String __OBFID = "CL_00002006";
   public final int field_177786_N;
   public final float field_177817_l;
   public final boolean field_177783_C;
   public final boolean field_177778_E;
   public final int field_177801_X;
   public final boolean field_177831_v;
   public final int field_177826_au;
   public final float field_177821_j;
   public final float field_177827_i;
   public final int field_177787_O;
   public final boolean field_177829_w;
   public final int field_177779_F;
   public final int field_177785_M;
   public final float field_177819_m;
   public final int field_177845_ad;
   public final int field_177780_G;
   public final boolean field_177854_x;
   public final boolean field_177781_A;
   public final float field_177806_d;
   public final int field_177795_V;
   public final int field_177820_ax;
   public final float field_177843_p;
   public final int field_177789_I;
   public final int field_177777_D;
   public final int field_177838_ap;
   public final int field_177851_ae;
   public final int field_177818_aq;
   public final int field_177793_T;
   public final boolean field_177839_r;
   public final float field_177803_f;

   ChunkProviderSettings(ChunkProviderSettings.Factory var1, Object var2) {
      this(var1);
   }

   private ChunkProviderSettings(ChunkProviderSettings.Factory var1) {
      this.field_177811_a = var1.field_177899_b;
      this.field_177809_b = var1.field_177900_c;
      this.field_177810_c = var1.field_177896_d;
      this.field_177806_d = var1.field_177898_e;
      this.field_177808_e = var1.field_177893_f;
      this.field_177803_f = var1.field_177894_g;
      this.field_177804_g = var1.field_177915_h;
      this.field_177825_h = var1.field_177917_i;
      this.field_177827_i = var1.field_177911_j;
      this.field_177821_j = var1.field_177913_k;
      this.field_177823_k = var1.field_177907_l;
      this.field_177817_l = var1.field_177909_m;
      this.field_177819_m = var1.field_177903_n;
      this.field_177813_n = var1.field_177905_o;
      this.field_177815_o = var1.field_177933_p;
      this.field_177843_p = var1.field_177931_q;
      this.field_177841_q = var1.field_177929_r;
      this.field_177839_r = var1.field_177927_s;
      this.field_177837_s = var1.field_177925_t;
      this.field_177835_t = var1.field_177923_u;
      this.field_177833_u = var1.field_177921_v;
      this.field_177831_v = var1.field_177919_w;
      this.field_177829_w = var1.field_177944_x;
      this.field_177854_x = var1.field_177942_y;
      this.field_177852_y = var1.field_177940_z;
      this.field_177850_z = var1.field_177870_A;
      this.field_177781_A = var1.field_177871_B;
      this.field_177782_B = var1.field_177872_C;
      this.field_177783_C = var1.field_177866_D;
      this.field_177777_D = var1.field_177867_E;
      this.field_177778_E = var1.field_177868_F;
      this.field_177779_F = var1.field_177869_G;
      this.field_177780_G = var1.field_177877_H;
      this.field_177788_H = var1.field_177878_I;
      this.field_177789_I = var1.field_177879_J;
      this.field_177790_J = var1.field_177880_K;
      this.field_177791_K = var1.field_177873_L;
      this.field_177784_L = var1.field_177874_M;
      this.field_177785_M = var1.field_177875_N;
      this.field_177786_N = var1.field_177876_O;
      this.field_177787_O = var1.field_177886_P;
      this.field_177797_P = var1.field_177885_Q;
      this.field_177796_Q = var1.field_177888_R;
      this.field_177799_R = var1.field_177887_S;
      this.field_177798_S = var1.field_177882_T;
      this.field_177793_T = var1.field_177881_U;
      this.field_177792_U = var1.field_177884_V;
      this.field_177795_V = var1.field_177883_W;
      this.field_177794_W = var1.field_177891_X;
      this.field_177801_X = var1.field_177890_Y;
      this.field_177800_Y = var1.field_177892_Z;
      this.field_177802_Z = var1.field_177936_aa;
      this.field_177846_aa = var1.field_177937_ab;
      this.field_177847_ab = var1.field_177934_ac;
      this.field_177844_ac = var1.field_177935_ad;
      this.field_177845_ad = var1.field_177941_ae;
      this.field_177851_ae = var1.field_177943_af;
      this.field_177853_af = var1.field_177938_ag;
      this.field_177848_ag = var1.field_177939_ah;
      this.field_177849_ah = var1.field_177922_ai;
      this.field_177832_ai = var1.field_177924_aj;
      this.field_177834_aj = var1.field_177918_ak;
      this.field_177828_ak = var1.field_177920_al;
      this.field_177830_al = var1.field_177930_am;
      this.field_177840_am = var1.field_177932_an;
      this.field_177842_an = var1.field_177926_ao;
      this.field_177836_ao = var1.field_177928_ap;
      this.field_177838_ap = var1.field_177908_aq;
      this.field_177818_aq = var1.field_177906_ar;
      this.field_177816_ar = var1.field_177904_as;
      this.field_177814_as = var1.field_177902_at;
      this.field_177812_at = var1.field_177916_au;
      this.field_177826_au = var1.field_177914_av;
      this.field_177824_av = var1.field_177912_aw;
      this.field_177822_aw = var1.field_177910_ax;
      this.field_177820_ax = var1.field_177897_ay;
      this.field_177807_ay = var1.field_177895_az;
      this.field_177805_az = var1.field_177889_aA;
   }

   public static class Serializer implements JsonSerializer, JsonDeserializer {
      private static final String __OBFID = "CL_00002003";

      public JsonElement serialize(Object var1, Type var2, JsonSerializationContext var3) {
         return this.func_177862_a((ChunkProviderSettings.Factory)var1, var2, var3);
      }

      public Object deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         return this.func_177861_a(var1, var2, var3);
      }

      public ChunkProviderSettings.Factory func_177861_a(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         JsonObject var4 = var1.getAsJsonObject();
         ChunkProviderSettings.Factory var5 = new ChunkProviderSettings.Factory();

         try {
            var5.field_177899_b = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "coordinateScale", var5.field_177899_b);
            var5.field_177900_c = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "heightScale", var5.field_177900_c);
            var5.field_177898_e = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "lowerLimitScale", var5.field_177898_e);
            var5.field_177896_d = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "upperLimitScale", var5.field_177896_d);
            var5.field_177893_f = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "depthNoiseScaleX", var5.field_177893_f);
            var5.field_177894_g = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "depthNoiseScaleZ", var5.field_177894_g);
            var5.field_177915_h = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "depthNoiseScaleExponent", var5.field_177915_h);
            var5.field_177917_i = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "mainNoiseScaleX", var5.field_177917_i);
            var5.field_177911_j = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "mainNoiseScaleY", var5.field_177911_j);
            var5.field_177913_k = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "mainNoiseScaleZ", var5.field_177913_k);
            var5.field_177907_l = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "baseSize", var5.field_177907_l);
            var5.field_177909_m = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "stretchY", var5.field_177909_m);
            var5.field_177903_n = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "biomeDepthWeight", var5.field_177903_n);
            var5.field_177905_o = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "biomeDepthOffset", var5.field_177905_o);
            var5.field_177933_p = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "biomeScaleWeight", var5.field_177933_p);
            var5.field_177931_q = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "biomeScaleOffset", var5.field_177931_q);
            var5.field_177929_r = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "seaLevel", var5.field_177929_r);
            var5.field_177927_s = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useCaves", var5.field_177927_s);
            var5.field_177925_t = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useDungeons", var5.field_177925_t);
            var5.field_177923_u = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dungeonChance", var5.field_177923_u);
            var5.field_177921_v = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useStrongholds", var5.field_177921_v);
            var5.field_177919_w = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useVillages", var5.field_177919_w);
            var5.field_177944_x = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useMineShafts", var5.field_177944_x);
            var5.field_177942_y = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useTemples", var5.field_177942_y);
            var5.field_177940_z = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useMonuments", var5.field_177940_z);
            var5.field_177870_A = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useRavines", var5.field_177870_A);
            var5.field_177871_B = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useWaterLakes", var5.field_177871_B);
            var5.field_177872_C = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "waterLakeChance", var5.field_177872_C);
            var5.field_177866_D = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useLavaLakes", var5.field_177866_D);
            var5.field_177867_E = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "lavaLakeChance", var5.field_177867_E);
            var5.field_177868_F = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useLavaOceans", var5.field_177868_F);
            var5.field_177869_G = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "fixedBiome", var5.field_177869_G);
            if (var5.field_177869_G < 38 && var5.field_177869_G >= -1) {
               if (var5.field_177869_G >= BiomeGenBase.hell.biomeID) {
                  var5.field_177869_G += 2;
               }
            } else {
               var5.field_177869_G = -1;
            }

            var5.field_177877_H = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "biomeSize", var5.field_177877_H);
            var5.field_177878_I = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "riverSize", var5.field_177878_I);
            var5.field_177879_J = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dirtSize", var5.field_177879_J);
            var5.field_177880_K = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dirtCount", var5.field_177880_K);
            var5.field_177873_L = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dirtMinHeight", var5.field_177873_L);
            var5.field_177874_M = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dirtMaxHeight", var5.field_177874_M);
            var5.field_177875_N = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "gravelSize", var5.field_177875_N);
            var5.field_177876_O = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "gravelCount", var5.field_177876_O);
            var5.field_177886_P = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "gravelMinHeight", var5.field_177886_P);
            var5.field_177885_Q = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "gravelMaxHeight", var5.field_177885_Q);
            var5.field_177888_R = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "graniteSize", var5.field_177888_R);
            var5.field_177887_S = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "graniteCount", var5.field_177887_S);
            var5.field_177882_T = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "graniteMinHeight", var5.field_177882_T);
            var5.field_177881_U = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "graniteMaxHeight", var5.field_177881_U);
            var5.field_177884_V = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dioriteSize", var5.field_177884_V);
            var5.field_177883_W = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dioriteCount", var5.field_177883_W);
            var5.field_177891_X = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dioriteMinHeight", var5.field_177891_X);
            var5.field_177890_Y = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dioriteMaxHeight", var5.field_177890_Y);
            var5.field_177892_Z = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "andesiteSize", var5.field_177892_Z);
            var5.field_177936_aa = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "andesiteCount", var5.field_177936_aa);
            var5.field_177937_ab = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "andesiteMinHeight", var5.field_177937_ab);
            var5.field_177934_ac = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "andesiteMaxHeight", var5.field_177934_ac);
            var5.field_177935_ad = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "coalSize", var5.field_177935_ad);
            var5.field_177941_ae = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "coalCount", var5.field_177941_ae);
            var5.field_177943_af = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "coalMinHeight", var5.field_177943_af);
            var5.field_177938_ag = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "coalMaxHeight", var5.field_177938_ag);
            var5.field_177939_ah = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "ironSize", var5.field_177939_ah);
            var5.field_177922_ai = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "ironCount", var5.field_177922_ai);
            var5.field_177924_aj = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "ironMinHeight", var5.field_177924_aj);
            var5.field_177918_ak = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "ironMaxHeight", var5.field_177918_ak);
            var5.field_177920_al = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "goldSize", var5.field_177920_al);
            var5.field_177930_am = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "goldCount", var5.field_177930_am);
            var5.field_177932_an = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "goldMinHeight", var5.field_177932_an);
            var5.field_177926_ao = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "goldMaxHeight", var5.field_177926_ao);
            var5.field_177928_ap = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "redstoneSize", var5.field_177928_ap);
            var5.field_177908_aq = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "redstoneCount", var5.field_177908_aq);
            var5.field_177906_ar = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "redstoneMinHeight", var5.field_177906_ar);
            var5.field_177904_as = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "redstoneMaxHeight", var5.field_177904_as);
            var5.field_177902_at = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "diamondSize", var5.field_177902_at);
            var5.field_177916_au = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "diamondCount", var5.field_177916_au);
            var5.field_177914_av = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "diamondMinHeight", var5.field_177914_av);
            var5.field_177912_aw = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "diamondMaxHeight", var5.field_177912_aw);
            var5.field_177910_ax = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "lapisSize", var5.field_177910_ax);
            var5.field_177897_ay = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "lapisCount", var5.field_177897_ay);
            var5.field_177895_az = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "lapisCenterHeight", var5.field_177895_az);
            var5.field_177889_aA = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "lapisSpread", var5.field_177889_aA);
         } catch (Exception var7) {
         }

         return var5;
      }

      public JsonElement func_177862_a(ChunkProviderSettings.Factory var1, Type var2, JsonSerializationContext var3) {
         JsonObject var4 = new JsonObject();
         var4.addProperty("coordinateScale", var1.field_177899_b);
         var4.addProperty("heightScale", var1.field_177900_c);
         var4.addProperty("lowerLimitScale", var1.field_177898_e);
         var4.addProperty("upperLimitScale", var1.field_177896_d);
         var4.addProperty("depthNoiseScaleX", var1.field_177893_f);
         var4.addProperty("depthNoiseScaleZ", var1.field_177894_g);
         var4.addProperty("depthNoiseScaleExponent", var1.field_177915_h);
         var4.addProperty("mainNoiseScaleX", var1.field_177917_i);
         var4.addProperty("mainNoiseScaleY", var1.field_177911_j);
         var4.addProperty("mainNoiseScaleZ", var1.field_177913_k);
         var4.addProperty("baseSize", var1.field_177907_l);
         var4.addProperty("stretchY", var1.field_177909_m);
         var4.addProperty("biomeDepthWeight", var1.field_177903_n);
         var4.addProperty("biomeDepthOffset", var1.field_177905_o);
         var4.addProperty("biomeScaleWeight", var1.field_177933_p);
         var4.addProperty("biomeScaleOffset", var1.field_177931_q);
         var4.addProperty("seaLevel", var1.field_177929_r);
         var4.addProperty("useCaves", var1.field_177927_s);
         var4.addProperty("useDungeons", var1.field_177925_t);
         var4.addProperty("dungeonChance", var1.field_177923_u);
         var4.addProperty("useStrongholds", var1.field_177921_v);
         var4.addProperty("useVillages", var1.field_177919_w);
         var4.addProperty("useMineShafts", var1.field_177944_x);
         var4.addProperty("useTemples", var1.field_177942_y);
         var4.addProperty("useMonuments", var1.field_177940_z);
         var4.addProperty("useRavines", var1.field_177870_A);
         var4.addProperty("useWaterLakes", var1.field_177871_B);
         var4.addProperty("waterLakeChance", var1.field_177872_C);
         var4.addProperty("useLavaLakes", var1.field_177866_D);
         var4.addProperty("lavaLakeChance", var1.field_177867_E);
         var4.addProperty("useLavaOceans", var1.field_177868_F);
         var4.addProperty("fixedBiome", var1.field_177869_G);
         var4.addProperty("biomeSize", var1.field_177877_H);
         var4.addProperty("riverSize", var1.field_177878_I);
         var4.addProperty("dirtSize", var1.field_177879_J);
         var4.addProperty("dirtCount", var1.field_177880_K);
         var4.addProperty("dirtMinHeight", var1.field_177873_L);
         var4.addProperty("dirtMaxHeight", var1.field_177874_M);
         var4.addProperty("gravelSize", var1.field_177875_N);
         var4.addProperty("gravelCount", var1.field_177876_O);
         var4.addProperty("gravelMinHeight", var1.field_177886_P);
         var4.addProperty("gravelMaxHeight", var1.field_177885_Q);
         var4.addProperty("graniteSize", var1.field_177888_R);
         var4.addProperty("graniteCount", var1.field_177887_S);
         var4.addProperty("graniteMinHeight", var1.field_177882_T);
         var4.addProperty("graniteMaxHeight", var1.field_177881_U);
         var4.addProperty("dioriteSize", var1.field_177884_V);
         var4.addProperty("dioriteCount", var1.field_177883_W);
         var4.addProperty("dioriteMinHeight", var1.field_177891_X);
         var4.addProperty("dioriteMaxHeight", var1.field_177890_Y);
         var4.addProperty("andesiteSize", var1.field_177892_Z);
         var4.addProperty("andesiteCount", var1.field_177936_aa);
         var4.addProperty("andesiteMinHeight", var1.field_177937_ab);
         var4.addProperty("andesiteMaxHeight", var1.field_177934_ac);
         var4.addProperty("coalSize", var1.field_177935_ad);
         var4.addProperty("coalCount", var1.field_177941_ae);
         var4.addProperty("coalMinHeight", var1.field_177943_af);
         var4.addProperty("coalMaxHeight", var1.field_177938_ag);
         var4.addProperty("ironSize", var1.field_177939_ah);
         var4.addProperty("ironCount", var1.field_177922_ai);
         var4.addProperty("ironMinHeight", var1.field_177924_aj);
         var4.addProperty("ironMaxHeight", var1.field_177918_ak);
         var4.addProperty("goldSize", var1.field_177920_al);
         var4.addProperty("goldCount", var1.field_177930_am);
         var4.addProperty("goldMinHeight", var1.field_177932_an);
         var4.addProperty("goldMaxHeight", var1.field_177926_ao);
         var4.addProperty("redstoneSize", var1.field_177928_ap);
         var4.addProperty("redstoneCount", var1.field_177908_aq);
         var4.addProperty("redstoneMinHeight", var1.field_177906_ar);
         var4.addProperty("redstoneMaxHeight", var1.field_177904_as);
         var4.addProperty("diamondSize", var1.field_177902_at);
         var4.addProperty("diamondCount", var1.field_177916_au);
         var4.addProperty("diamondMinHeight", var1.field_177914_av);
         var4.addProperty("diamondMaxHeight", var1.field_177912_aw);
         var4.addProperty("lapisSize", var1.field_177910_ax);
         var4.addProperty("lapisCount", var1.field_177897_ay);
         var4.addProperty("lapisCenterHeight", var1.field_177895_az);
         var4.addProperty("lapisSpread", var1.field_177889_aA);
         return var4;
      }
   }

   public static class Factory {
      public boolean field_177927_s = true;
      public boolean field_177942_y = true;
      public float field_177898_e = 512.0F;
      public float field_177907_l = 8.5F;
      public int field_177897_ay = 1;
      public int field_177884_V = 33;
      public int field_177929_r = 63;
      public int field_177875_N = 33;
      public boolean field_177925_t = true;
      public int field_177881_U = 80;
      public float field_177909_m = 12.0F;
      public boolean field_177944_x = true;
      public int field_177941_ae = 20;
      public int field_177885_Q = 256;
      public int field_177878_I = 4;
      public float field_177915_h = 0.5F;
      public int field_177890_Y = 80;
      public int field_177876_O = 8;
      public int field_177889_aA = 16;
      public float field_177911_j = 160.0F;
      public float field_177899_b = 684.412F;
      public int field_177914_av = 0;
      public int field_177882_T = 0;
      public boolean field_177866_D = true;
      public float field_177893_f = 200.0F;
      public int field_177880_K = 10;
      public int field_177928_ap = 8;
      public int field_177938_ag = 128;
      public int field_177924_aj = 0;
      public int field_177943_af = 0;
      public int field_177923_u = 8;
      public int field_177906_ar = 0;
      public float field_177896_d = 512.0F;
      public float field_177903_n = 1.0F;
      public int field_177867_E = 80;
      static final Gson field_177901_a = (new GsonBuilder()).registerTypeAdapter(ChunkProviderSettings.Factory.class, new ChunkProviderSettings.Serializer()).create();
      public int field_177912_aw = 16;
      public int field_177922_ai = 20;
      public int field_177902_at = 8;
      public boolean field_177870_A = true;
      public int field_177908_aq = 8;
      private static final String __OBFID = "CL_00002004";
      public int field_177934_ac = 80;
      public boolean field_177868_F = false;
      public int field_177879_J = 33;
      public int field_177916_au = 1;
      public float field_177900_c = 684.412F;
      public float field_177905_o = 0.0F;
      public int field_177886_P = 0;
      public int field_177936_aa = 10;
      public int field_177872_C = 4;
      public boolean field_177871_B = true;
      public int field_177869_G = -1;
      public int field_177939_ah = 9;
      public int field_177877_H = 4;
      public int field_177920_al = 9;
      public int field_177892_Z = 33;
      public float field_177913_k = 80.0F;
      public int field_177910_ax = 7;
      public float field_177894_g = 200.0F;
      public boolean field_177940_z = true;
      public int field_177883_W = 10;
      public int field_177887_S = 10;
      public float field_177931_q = 0.0F;
      public int field_177874_M = 256;
      public int field_177926_ao = 32;
      public int field_177873_L = 0;
      public float field_177933_p = 1.0F;
      public int field_177888_R = 33;
      public int field_177937_ab = 0;
      public int field_177935_ad = 17;
      public int field_177930_am = 2;
      public int field_177891_X = 0;
      public boolean field_177919_w = true;
      public int field_177918_ak = 64;
      public int field_177932_an = 0;
      public int field_177904_as = 16;
      public int field_177895_az = 16;
      public float field_177917_i = 80.0F;
      public boolean field_177921_v = true;

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (var1 != null && this.getClass() == var1.getClass()) {
            ChunkProviderSettings.Factory var2 = (ChunkProviderSettings.Factory)var1;
            return this.field_177936_aa != var2.field_177936_aa ? false : (this.field_177934_ac != var2.field_177934_ac ? false : (this.field_177937_ab != var2.field_177937_ab ? false : (this.field_177892_Z != var2.field_177892_Z ? false : (Float.compare(var2.field_177907_l, this.field_177907_l) != 0 ? false : (Float.compare(var2.field_177905_o, this.field_177905_o) != 0 ? false : (Float.compare(var2.field_177903_n, this.field_177903_n) != 0 ? false : (Float.compare(var2.field_177931_q, this.field_177931_q) != 0 ? false : (Float.compare(var2.field_177933_p, this.field_177933_p) != 0 ? false : (this.field_177877_H != var2.field_177877_H ? false : (this.field_177941_ae != var2.field_177941_ae ? false : (this.field_177938_ag != var2.field_177938_ag ? false : (this.field_177943_af != var2.field_177943_af ? false : (this.field_177935_ad != var2.field_177935_ad ? false : (Float.compare(var2.field_177899_b, this.field_177899_b) != 0 ? false : (Float.compare(var2.field_177915_h, this.field_177915_h) != 0 ? false : (Float.compare(var2.field_177893_f, this.field_177893_f) != 0 ? false : (Float.compare(var2.field_177894_g, this.field_177894_g) != 0 ? false : (this.field_177916_au != var2.field_177916_au ? false : (this.field_177912_aw != var2.field_177912_aw ? false : (this.field_177914_av != var2.field_177914_av ? false : (this.field_177902_at != var2.field_177902_at ? false : (this.field_177883_W != var2.field_177883_W ? false : (this.field_177890_Y != var2.field_177890_Y ? false : (this.field_177891_X != var2.field_177891_X ? false : (this.field_177884_V != var2.field_177884_V ? false : (this.field_177880_K != var2.field_177880_K ? false : (this.field_177874_M != var2.field_177874_M ? false : (this.field_177873_L != var2.field_177873_L ? false : (this.field_177879_J != var2.field_177879_J ? false : (this.field_177923_u != var2.field_177923_u ? false : (this.field_177869_G != var2.field_177869_G ? false : (this.field_177930_am != var2.field_177930_am ? false : (this.field_177926_ao != var2.field_177926_ao ? false : (this.field_177932_an != var2.field_177932_an ? false : (this.field_177920_al != var2.field_177920_al ? false : (this.field_177887_S != var2.field_177887_S ? false : (this.field_177881_U != var2.field_177881_U ? false : (this.field_177882_T != var2.field_177882_T ? false : (this.field_177888_R != var2.field_177888_R ? false : (this.field_177876_O != var2.field_177876_O ? false : (this.field_177885_Q != var2.field_177885_Q ? false : (this.field_177886_P != var2.field_177886_P ? false : (this.field_177875_N != var2.field_177875_N ? false : (Float.compare(var2.field_177900_c, this.field_177900_c) != 0 ? false : (this.field_177922_ai != var2.field_177922_ai ? false : (this.field_177918_ak != var2.field_177918_ak ? false : (this.field_177924_aj != var2.field_177924_aj ? false : (this.field_177939_ah != var2.field_177939_ah ? false : (this.field_177895_az != var2.field_177895_az ? false : (this.field_177897_ay != var2.field_177897_ay ? false : (this.field_177910_ax != var2.field_177910_ax ? false : (this.field_177889_aA != var2.field_177889_aA ? false : (this.field_177867_E != var2.field_177867_E ? false : (Float.compare(var2.field_177898_e, this.field_177898_e) != 0 ? false : (Float.compare(var2.field_177917_i, this.field_177917_i) != 0 ? false : (Float.compare(var2.field_177911_j, this.field_177911_j) != 0 ? false : (Float.compare(var2.field_177913_k, this.field_177913_k) != 0 ? false : (this.field_177908_aq != var2.field_177908_aq ? false : (this.field_177904_as != var2.field_177904_as ? false : (this.field_177906_ar != var2.field_177906_ar ? false : (this.field_177928_ap != var2.field_177928_ap ? false : (this.field_177878_I != var2.field_177878_I ? false : (this.field_177929_r != var2.field_177929_r ? false : (Float.compare(var2.field_177909_m, this.field_177909_m) != 0 ? false : (Float.compare(var2.field_177896_d, this.field_177896_d) != 0 ? false : (this.field_177927_s != var2.field_177927_s ? false : (this.field_177925_t != var2.field_177925_t ? false : (this.field_177866_D != var2.field_177866_D ? false : (this.field_177868_F != var2.field_177868_F ? false : (this.field_177944_x != var2.field_177944_x ? false : (this.field_177870_A != var2.field_177870_A ? false : (this.field_177921_v != var2.field_177921_v ? false : (this.field_177942_y != var2.field_177942_y ? false : (this.field_177940_z != var2.field_177940_z ? false : (this.field_177919_w != var2.field_177919_w ? false : (this.field_177871_B != var2.field_177871_B ? false : this.field_177872_C == var2.field_177872_C))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))));
         } else {
            return false;
         }
      }

      public String toString() {
         return field_177901_a.toJson(this);
      }

      public Factory() {
         this.func_177863_a();
      }

      public static ChunkProviderSettings.Factory func_177865_a(String var0) {
         if (var0.length() == 0) {
            return new ChunkProviderSettings.Factory();
         } else {
            try {
               return (ChunkProviderSettings.Factory)field_177901_a.fromJson(var0, ChunkProviderSettings.Factory.class);
            } catch (Exception var2) {
               return new ChunkProviderSettings.Factory();
            }
         }
      }

      public ChunkProviderSettings func_177864_b() {
         return new ChunkProviderSettings(this, (Object)null);
      }

      public void func_177863_a() {
         this.field_177899_b = 684.412F;
         this.field_177900_c = 684.412F;
         this.field_177896_d = 512.0F;
         this.field_177898_e = 512.0F;
         this.field_177893_f = 200.0F;
         this.field_177894_g = 200.0F;
         this.field_177915_h = 0.5F;
         this.field_177917_i = 80.0F;
         this.field_177911_j = 160.0F;
         this.field_177913_k = 80.0F;
         this.field_177907_l = 8.5F;
         this.field_177909_m = 12.0F;
         this.field_177903_n = 1.0F;
         this.field_177905_o = 0.0F;
         this.field_177933_p = 1.0F;
         this.field_177931_q = 0.0F;
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

      public int hashCode() {
         int var1 = this.field_177899_b != 0.0F ? Float.floatToIntBits(this.field_177899_b) : 0;
         var1 = 31 * var1 + (this.field_177900_c != 0.0F ? Float.floatToIntBits(this.field_177900_c) : 0);
         var1 = 31 * var1 + (this.field_177896_d != 0.0F ? Float.floatToIntBits(this.field_177896_d) : 0);
         var1 = 31 * var1 + (this.field_177898_e != 0.0F ? Float.floatToIntBits(this.field_177898_e) : 0);
         var1 = 31 * var1 + (this.field_177893_f != 0.0F ? Float.floatToIntBits(this.field_177893_f) : 0);
         var1 = 31 * var1 + (this.field_177894_g != 0.0F ? Float.floatToIntBits(this.field_177894_g) : 0);
         var1 = 31 * var1 + (this.field_177915_h != 0.0F ? Float.floatToIntBits(this.field_177915_h) : 0);
         var1 = 31 * var1 + (this.field_177917_i != 0.0F ? Float.floatToIntBits(this.field_177917_i) : 0);
         var1 = 31 * var1 + (this.field_177911_j != 0.0F ? Float.floatToIntBits(this.field_177911_j) : 0);
         var1 = 31 * var1 + (this.field_177913_k != 0.0F ? Float.floatToIntBits(this.field_177913_k) : 0);
         var1 = 31 * var1 + (this.field_177907_l != 0.0F ? Float.floatToIntBits(this.field_177907_l) : 0);
         var1 = 31 * var1 + (this.field_177909_m != 0.0F ? Float.floatToIntBits(this.field_177909_m) : 0);
         var1 = 31 * var1 + (this.field_177903_n != 0.0F ? Float.floatToIntBits(this.field_177903_n) : 0);
         var1 = 31 * var1 + (this.field_177905_o != 0.0F ? Float.floatToIntBits(this.field_177905_o) : 0);
         var1 = 31 * var1 + (this.field_177933_p != 0.0F ? Float.floatToIntBits(this.field_177933_p) : 0);
         var1 = 31 * var1 + (this.field_177931_q != 0.0F ? Float.floatToIntBits(this.field_177931_q) : 0);
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
   }
}
