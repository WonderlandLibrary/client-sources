package net.minecraft.world.gen;

import java.lang.reflect.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.*;
import com.google.gson.*;

public static class Serializer implements JsonDeserializer, JsonSerializer
{
    public Factory func_177861_a(final JsonElement p_177861_1_, final Type p_177861_2_, final JsonDeserializationContext p_177861_3_) {
        final JsonObject var4 = p_177861_1_.getAsJsonObject();
        final Factory var5 = new Factory();
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
                    final Factory factory = var5;
                    factory.field_177869_G += 2;
                }
            }
            else {
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
        }
        catch (Exception ex) {}
        return var5;
    }
    
    public JsonElement func_177862_a(final Factory p_177862_1_, final Type p_177862_2_, final JsonSerializationContext p_177862_3_) {
        final JsonObject var4 = new JsonObject();
        var4.addProperty("coordinateScale", (Number)p_177862_1_.field_177899_b);
        var4.addProperty("heightScale", (Number)p_177862_1_.field_177900_c);
        var4.addProperty("lowerLimitScale", (Number)p_177862_1_.field_177898_e);
        var4.addProperty("upperLimitScale", (Number)p_177862_1_.field_177896_d);
        var4.addProperty("depthNoiseScaleX", (Number)p_177862_1_.field_177893_f);
        var4.addProperty("depthNoiseScaleZ", (Number)p_177862_1_.field_177894_g);
        var4.addProperty("depthNoiseScaleExponent", (Number)p_177862_1_.field_177915_h);
        var4.addProperty("mainNoiseScaleX", (Number)p_177862_1_.field_177917_i);
        var4.addProperty("mainNoiseScaleY", (Number)p_177862_1_.field_177911_j);
        var4.addProperty("mainNoiseScaleZ", (Number)p_177862_1_.field_177913_k);
        var4.addProperty("baseSize", (Number)p_177862_1_.field_177907_l);
        var4.addProperty("stretchY", (Number)p_177862_1_.field_177909_m);
        var4.addProperty("biomeDepthWeight", (Number)p_177862_1_.field_177903_n);
        var4.addProperty("biomeDepthOffset", (Number)p_177862_1_.field_177905_o);
        var4.addProperty("biomeScaleWeight", (Number)p_177862_1_.field_177933_p);
        var4.addProperty("biomeScaleOffset", (Number)p_177862_1_.field_177931_q);
        var4.addProperty("seaLevel", (Number)p_177862_1_.field_177929_r);
        var4.addProperty("useCaves", Boolean.valueOf(p_177862_1_.field_177927_s));
        var4.addProperty("useDungeons", Boolean.valueOf(p_177862_1_.field_177925_t));
        var4.addProperty("dungeonChance", (Number)p_177862_1_.field_177923_u);
        var4.addProperty("useStrongholds", Boolean.valueOf(p_177862_1_.field_177921_v));
        var4.addProperty("useVillages", Boolean.valueOf(p_177862_1_.field_177919_w));
        var4.addProperty("useMineShafts", Boolean.valueOf(p_177862_1_.field_177944_x));
        var4.addProperty("useTemples", Boolean.valueOf(p_177862_1_.field_177942_y));
        var4.addProperty("useMonuments", Boolean.valueOf(p_177862_1_.field_177940_z));
        var4.addProperty("useRavines", Boolean.valueOf(p_177862_1_.field_177870_A));
        var4.addProperty("useWaterLakes", Boolean.valueOf(p_177862_1_.field_177871_B));
        var4.addProperty("waterLakeChance", (Number)p_177862_1_.field_177872_C);
        var4.addProperty("useLavaLakes", Boolean.valueOf(p_177862_1_.field_177866_D));
        var4.addProperty("lavaLakeChance", (Number)p_177862_1_.field_177867_E);
        var4.addProperty("useLavaOceans", Boolean.valueOf(p_177862_1_.field_177868_F));
        var4.addProperty("fixedBiome", (Number)p_177862_1_.field_177869_G);
        var4.addProperty("biomeSize", (Number)p_177862_1_.field_177877_H);
        var4.addProperty("riverSize", (Number)p_177862_1_.field_177878_I);
        var4.addProperty("dirtSize", (Number)p_177862_1_.field_177879_J);
        var4.addProperty("dirtCount", (Number)p_177862_1_.field_177880_K);
        var4.addProperty("dirtMinHeight", (Number)p_177862_1_.field_177873_L);
        var4.addProperty("dirtMaxHeight", (Number)p_177862_1_.field_177874_M);
        var4.addProperty("gravelSize", (Number)p_177862_1_.field_177875_N);
        var4.addProperty("gravelCount", (Number)p_177862_1_.field_177876_O);
        var4.addProperty("gravelMinHeight", (Number)p_177862_1_.field_177886_P);
        var4.addProperty("gravelMaxHeight", (Number)p_177862_1_.field_177885_Q);
        var4.addProperty("graniteSize", (Number)p_177862_1_.field_177888_R);
        var4.addProperty("graniteCount", (Number)p_177862_1_.field_177887_S);
        var4.addProperty("graniteMinHeight", (Number)p_177862_1_.field_177882_T);
        var4.addProperty("graniteMaxHeight", (Number)p_177862_1_.field_177881_U);
        var4.addProperty("dioriteSize", (Number)p_177862_1_.field_177884_V);
        var4.addProperty("dioriteCount", (Number)p_177862_1_.field_177883_W);
        var4.addProperty("dioriteMinHeight", (Number)p_177862_1_.field_177891_X);
        var4.addProperty("dioriteMaxHeight", (Number)p_177862_1_.field_177890_Y);
        var4.addProperty("andesiteSize", (Number)p_177862_1_.field_177892_Z);
        var4.addProperty("andesiteCount", (Number)p_177862_1_.field_177936_aa);
        var4.addProperty("andesiteMinHeight", (Number)p_177862_1_.field_177937_ab);
        var4.addProperty("andesiteMaxHeight", (Number)p_177862_1_.field_177934_ac);
        var4.addProperty("coalSize", (Number)p_177862_1_.field_177935_ad);
        var4.addProperty("coalCount", (Number)p_177862_1_.field_177941_ae);
        var4.addProperty("coalMinHeight", (Number)p_177862_1_.field_177943_af);
        var4.addProperty("coalMaxHeight", (Number)p_177862_1_.field_177938_ag);
        var4.addProperty("ironSize", (Number)p_177862_1_.field_177939_ah);
        var4.addProperty("ironCount", (Number)p_177862_1_.field_177922_ai);
        var4.addProperty("ironMinHeight", (Number)p_177862_1_.field_177924_aj);
        var4.addProperty("ironMaxHeight", (Number)p_177862_1_.field_177918_ak);
        var4.addProperty("goldSize", (Number)p_177862_1_.field_177920_al);
        var4.addProperty("goldCount", (Number)p_177862_1_.field_177930_am);
        var4.addProperty("goldMinHeight", (Number)p_177862_1_.field_177932_an);
        var4.addProperty("goldMaxHeight", (Number)p_177862_1_.field_177926_ao);
        var4.addProperty("redstoneSize", (Number)p_177862_1_.field_177928_ap);
        var4.addProperty("redstoneCount", (Number)p_177862_1_.field_177908_aq);
        var4.addProperty("redstoneMinHeight", (Number)p_177862_1_.field_177906_ar);
        var4.addProperty("redstoneMaxHeight", (Number)p_177862_1_.field_177904_as);
        var4.addProperty("diamondSize", (Number)p_177862_1_.field_177902_at);
        var4.addProperty("diamondCount", (Number)p_177862_1_.field_177916_au);
        var4.addProperty("diamondMinHeight", (Number)p_177862_1_.field_177914_av);
        var4.addProperty("diamondMaxHeight", (Number)p_177862_1_.field_177912_aw);
        var4.addProperty("lapisSize", (Number)p_177862_1_.field_177910_ax);
        var4.addProperty("lapisCount", (Number)p_177862_1_.field_177897_ay);
        var4.addProperty("lapisCenterHeight", (Number)p_177862_1_.field_177895_az);
        var4.addProperty("lapisSpread", (Number)p_177862_1_.field_177889_aA);
        return (JsonElement)var4;
    }
    
    public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        return this.func_177861_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
    }
    
    public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
        return this.func_177862_a((Factory)p_serialize_1_, p_serialize_2_, p_serialize_3_);
    }
}
