package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonPrimitive;
import org.apache.commons.lang3.StringUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtils
{
    private static final String HorizonCode_Horizon_È = "CL_00001484";
    
    public static boolean HorizonCode_Horizon_È(final JsonObject p_151205_0_, final String p_151205_1_) {
        return Ø­áŒŠá(p_151205_0_, p_151205_1_) && p_151205_0_.getAsJsonPrimitive(p_151205_1_).isString();
    }
    
    public static boolean HorizonCode_Horizon_È(final JsonElement p_151211_0_) {
        return p_151211_0_.isJsonPrimitive() && p_151211_0_.getAsJsonPrimitive().isString();
    }
    
    public static boolean Â(final JsonObject p_180199_0_, final String p_180199_1_) {
        return Ø­áŒŠá(p_180199_0_, p_180199_1_) && p_180199_0_.getAsJsonPrimitive(p_180199_1_).isBoolean();
    }
    
    public static boolean Ý(final JsonObject p_151202_0_, final String p_151202_1_) {
        return Âµá€(p_151202_0_, p_151202_1_) && p_151202_0_.get(p_151202_1_).isJsonArray();
    }
    
    public static boolean Ø­áŒŠá(final JsonObject p_151201_0_, final String p_151201_1_) {
        return Âµá€(p_151201_0_, p_151201_1_) && p_151201_0_.get(p_151201_1_).isJsonPrimitive();
    }
    
    public static boolean Âµá€(final JsonObject p_151204_0_, final String p_151204_1_) {
        return p_151204_0_ != null && p_151204_0_.get(p_151204_1_) != null;
    }
    
    public static String HorizonCode_Horizon_È(final JsonElement p_151206_0_, final String p_151206_1_) {
        if (p_151206_0_.isJsonPrimitive()) {
            return p_151206_0_.getAsString();
        }
        throw new JsonSyntaxException("Expected " + p_151206_1_ + " to be a string, was " + Â(p_151206_0_));
    }
    
    public static String Ó(final JsonObject p_151200_0_, final String p_151200_1_) {
        if (p_151200_0_.has(p_151200_1_)) {
            return HorizonCode_Horizon_È(p_151200_0_.get(p_151200_1_), p_151200_1_);
        }
        throw new JsonSyntaxException("Missing " + p_151200_1_ + ", expected to find a string");
    }
    
    public static String HorizonCode_Horizon_È(final JsonObject p_151219_0_, final String p_151219_1_, final String p_151219_2_) {
        return p_151219_0_.has(p_151219_1_) ? HorizonCode_Horizon_È(p_151219_0_.get(p_151219_1_), p_151219_1_) : p_151219_2_;
    }
    
    public static boolean Â(final JsonElement p_151216_0_, final String p_151216_1_) {
        if (p_151216_0_.isJsonPrimitive()) {
            return p_151216_0_.getAsBoolean();
        }
        throw new JsonSyntaxException("Expected " + p_151216_1_ + " to be a Boolean, was " + Â(p_151216_0_));
    }
    
    public static boolean à(final JsonObject p_151212_0_, final String p_151212_1_) {
        if (p_151212_0_.has(p_151212_1_)) {
            return Â(p_151212_0_.get(p_151212_1_), p_151212_1_);
        }
        throw new JsonSyntaxException("Missing " + p_151212_1_ + ", expected to find a Boolean");
    }
    
    public static boolean HorizonCode_Horizon_È(final JsonObject p_151209_0_, final String p_151209_1_, final boolean p_151209_2_) {
        return p_151209_0_.has(p_151209_1_) ? Â(p_151209_0_.get(p_151209_1_), p_151209_1_) : p_151209_2_;
    }
    
    public static float Ý(final JsonElement p_151220_0_, final String p_151220_1_) {
        if (p_151220_0_.isJsonPrimitive() && p_151220_0_.getAsJsonPrimitive().isNumber()) {
            return p_151220_0_.getAsFloat();
        }
        throw new JsonSyntaxException("Expected " + p_151220_1_ + " to be a Float, was " + Â(p_151220_0_));
    }
    
    public static float Ø(final JsonObject p_151217_0_, final String p_151217_1_) {
        if (p_151217_0_.has(p_151217_1_)) {
            return Ý(p_151217_0_.get(p_151217_1_), p_151217_1_);
        }
        throw new JsonSyntaxException("Missing " + p_151217_1_ + ", expected to find a Float");
    }
    
    public static float HorizonCode_Horizon_È(final JsonObject p_151221_0_, final String p_151221_1_, final float p_151221_2_) {
        return p_151221_0_.has(p_151221_1_) ? Ý(p_151221_0_.get(p_151221_1_), p_151221_1_) : p_151221_2_;
    }
    
    public static int Ø­áŒŠá(final JsonElement p_151215_0_, final String p_151215_1_) {
        if (p_151215_0_.isJsonPrimitive() && p_151215_0_.getAsJsonPrimitive().isNumber()) {
            return p_151215_0_.getAsInt();
        }
        throw new JsonSyntaxException("Expected " + p_151215_1_ + " to be a Int, was " + Â(p_151215_0_));
    }
    
    public static int áŒŠÆ(final JsonObject p_151203_0_, final String p_151203_1_) {
        if (p_151203_0_.has(p_151203_1_)) {
            return Ø­áŒŠá(p_151203_0_.get(p_151203_1_), p_151203_1_);
        }
        throw new JsonSyntaxException("Missing " + p_151203_1_ + ", expected to find a Int");
    }
    
    public static int HorizonCode_Horizon_È(final JsonObject p_151208_0_, final String p_151208_1_, final int p_151208_2_) {
        return p_151208_0_.has(p_151208_1_) ? Ø­áŒŠá(p_151208_0_.get(p_151208_1_), p_151208_1_) : p_151208_2_;
    }
    
    public static JsonObject Âµá€(final JsonElement p_151210_0_, final String p_151210_1_) {
        if (p_151210_0_.isJsonObject()) {
            return p_151210_0_.getAsJsonObject();
        }
        throw new JsonSyntaxException("Expected " + p_151210_1_ + " to be a JsonObject, was " + Â(p_151210_0_));
    }
    
    public static JsonObject áˆºÑ¢Õ(final JsonObject base, final String key) {
        if (base.has(key)) {
            return Âµá€(base.get(key), key);
        }
        throw new JsonSyntaxException("Missing " + key + ", expected to find a JsonObject");
    }
    
    public static JsonObject HorizonCode_Horizon_È(final JsonObject p_151218_0_, final String p_151218_1_, final JsonObject p_151218_2_) {
        return p_151218_0_.has(p_151218_1_) ? Âµá€(p_151218_0_.get(p_151218_1_), p_151218_1_) : p_151218_2_;
    }
    
    public static JsonArray Ó(final JsonElement p_151207_0_, final String p_151207_1_) {
        if (p_151207_0_.isJsonArray()) {
            return p_151207_0_.getAsJsonArray();
        }
        throw new JsonSyntaxException("Expected " + p_151207_1_ + " to be a JsonArray, was " + Â(p_151207_0_));
    }
    
    public static JsonArray ÂµÈ(final JsonObject p_151214_0_, final String p_151214_1_) {
        if (p_151214_0_.has(p_151214_1_)) {
            return Ó(p_151214_0_.get(p_151214_1_), p_151214_1_);
        }
        throw new JsonSyntaxException("Missing " + p_151214_1_ + ", expected to find a JsonArray");
    }
    
    public static JsonArray HorizonCode_Horizon_È(final JsonObject p_151213_0_, final String p_151213_1_, final JsonArray p_151213_2_) {
        return p_151213_0_.has(p_151213_1_) ? Ó(p_151213_0_.get(p_151213_1_), p_151213_1_) : p_151213_2_;
    }
    
    public static String Â(final JsonElement p_151222_0_) {
        final String var1 = StringUtils.abbreviateMiddle(String.valueOf(p_151222_0_), "...", 10);
        if (p_151222_0_ == null) {
            return "null (missing)";
        }
        if (p_151222_0_.isJsonNull()) {
            return "null (json)";
        }
        if (p_151222_0_.isJsonArray()) {
            return "an array (" + var1 + ")";
        }
        if (p_151222_0_.isJsonObject()) {
            return "an object (" + var1 + ")";
        }
        if (p_151222_0_.isJsonPrimitive()) {
            final JsonPrimitive var2 = p_151222_0_.getAsJsonPrimitive();
            if (var2.isNumber()) {
                return "a number (" + var1 + ")";
            }
            if (var2.isBoolean()) {
                return "a boolean (" + var1 + ")";
            }
        }
        return var1;
    }
}
