package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonParseException;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializer;
import java.util.List;
import java.util.Iterator;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.io.Reader;
import java.lang.reflect.Type;
import com.google.gson.GsonBuilder;
import java.util.Map;
import com.google.gson.Gson;

public class ModelBlockDefinition
{
    static final Gson HorizonCode_Horizon_È;
    private final Map Â;
    private static final String Ý = "CL_00002498";
    
    static {
        HorizonCode_Horizon_È = new GsonBuilder().registerTypeAdapter((Type)ModelBlockDefinition.class, (Object)new HorizonCode_Horizon_È()).registerTypeAdapter((Type)Ý.class, (Object)new Ý.HorizonCode_Horizon_È()).create();
    }
    
    public static ModelBlockDefinition HorizonCode_Horizon_È(final Reader p_178331_0_) {
        return (ModelBlockDefinition)ModelBlockDefinition.HorizonCode_Horizon_È.fromJson(p_178331_0_, (Class)ModelBlockDefinition.class);
    }
    
    public ModelBlockDefinition(final Collection p_i46221_1_) {
        this.Â = Maps.newHashMap();
        for (final Ø­áŒŠá var3 : p_i46221_1_) {
            this.Â.put(var3.HorizonCode_Horizon_È, var3);
        }
    }
    
    public ModelBlockDefinition(final List p_i46222_1_) {
        this.Â = Maps.newHashMap();
        for (final ModelBlockDefinition var3 : p_i46222_1_) {
            this.Â.putAll(var3.Â);
        }
    }
    
    public Ø­áŒŠá HorizonCode_Horizon_È(final String p_178330_1_) {
        final Ø­áŒŠá var2 = this.Â.get(p_178330_1_);
        if (var2 == null) {
            throw new Â();
        }
        return var2;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (p_equals_1_ instanceof ModelBlockDefinition) {
            final ModelBlockDefinition var2 = (ModelBlockDefinition)p_equals_1_;
            return this.Â.equals(var2.Â);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.Â.hashCode();
    }
    
    public static class HorizonCode_Horizon_È implements JsonDeserializer
    {
        private static final String HorizonCode_Horizon_È = "CL_00002497";
        
        public ModelBlockDefinition HorizonCode_Horizon_È(final JsonElement p_178336_1_, final Type p_178336_2_, final JsonDeserializationContext p_178336_3_) {
            final JsonObject var4 = p_178336_1_.getAsJsonObject();
            final List var5 = this.HorizonCode_Horizon_È(p_178336_3_, var4);
            return new ModelBlockDefinition((Collection)var5);
        }
        
        protected List HorizonCode_Horizon_È(final JsonDeserializationContext p_178334_1_, final JsonObject p_178334_2_) {
            final JsonObject var3 = JsonUtils.áˆºÑ¢Õ(p_178334_2_, "variants");
            final ArrayList var4 = Lists.newArrayList();
            for (final Map.Entry var6 : var3.entrySet()) {
                var4.add(this.HorizonCode_Horizon_È(p_178334_1_, var6));
            }
            return var4;
        }
        
        protected Ø­áŒŠá HorizonCode_Horizon_È(final JsonDeserializationContext p_178335_1_, final Map.Entry p_178335_2_) {
            final String var3 = p_178335_2_.getKey();
            final ArrayList var4 = Lists.newArrayList();
            final JsonElement var5 = (JsonElement)p_178335_2_.getValue();
            if (var5.isJsonArray()) {
                for (final JsonElement var7 : var5.getAsJsonArray()) {
                    var4.add(p_178335_1_.deserialize(var7, (Type)Ý.class));
                }
            }
            else {
                var4.add(p_178335_1_.deserialize(var5, (Type)Ý.class));
            }
            return new Ø­áŒŠá(var3, var4);
        }
        
        public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            return this.HorizonCode_Horizon_È(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
        }
    }
    
    public class Â extends RuntimeException
    {
        private static final String Â = "CL_00002496";
    }
    
    public static class Ý
    {
        private final ResourceLocation_1975012498 HorizonCode_Horizon_È;
        private final ModelRotation Â;
        private final boolean Ý;
        private final int Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002495";
        
        public Ý(final ResourceLocation_1975012498 p_i46219_1_, final ModelRotation p_i46219_2_, final boolean p_i46219_3_, final int p_i46219_4_) {
            this.HorizonCode_Horizon_È = p_i46219_1_;
            this.Â = p_i46219_2_;
            this.Ý = p_i46219_3_;
            this.Ø­áŒŠá = p_i46219_4_;
        }
        
        public ResourceLocation_1975012498 HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
        
        public ModelRotation Â() {
            return this.Â;
        }
        
        public boolean Ý() {
            return this.Ý;
        }
        
        public int Ø­áŒŠá() {
            return this.Ø­áŒŠá;
        }
        
        @Override
        public boolean equals(final Object p_equals_1_) {
            if (this == p_equals_1_) {
                return true;
            }
            if (!(p_equals_1_ instanceof Ý)) {
                return false;
            }
            final Ý var2 = (Ý)p_equals_1_;
            return this.HorizonCode_Horizon_È.equals(var2.HorizonCode_Horizon_È) && this.Â == var2.Â && this.Ý == var2.Ý;
        }
        
        @Override
        public int hashCode() {
            int var1 = this.HorizonCode_Horizon_È.hashCode();
            var1 = 31 * var1 + ((this.Â != null) ? this.Â.hashCode() : 0);
            var1 = 31 * var1 + (this.Ý ? 1 : 0);
            return var1;
        }
        
        public static class HorizonCode_Horizon_È implements JsonDeserializer
        {
            private static final String HorizonCode_Horizon_È = "CL_00002494";
            
            public Ý HorizonCode_Horizon_È(final JsonElement p_178425_1_, final Type p_178425_2_, final JsonDeserializationContext p_178425_3_) {
                final JsonObject var4 = p_178425_1_.getAsJsonObject();
                final String var5 = this.Â(var4);
                final ModelRotation var6 = this.HorizonCode_Horizon_È(var4);
                final boolean var7 = this.Ø­áŒŠá(var4);
                final int var8 = this.Ý(var4);
                return new Ý(this.HorizonCode_Horizon_È(var5), var6, var7, var8);
            }
            
            private ResourceLocation_1975012498 HorizonCode_Horizon_È(final String p_178426_1_) {
                ResourceLocation_1975012498 var2 = new ResourceLocation_1975012498(p_178426_1_);
                var2 = new ResourceLocation_1975012498(var2.Ý(), "block/" + var2.Â());
                return var2;
            }
            
            private boolean Ø­áŒŠá(final JsonObject p_178429_1_) {
                return JsonUtils.HorizonCode_Horizon_È(p_178429_1_, "uvlock", false);
            }
            
            protected ModelRotation HorizonCode_Horizon_È(final JsonObject p_178428_1_) {
                final int var2 = JsonUtils.HorizonCode_Horizon_È(p_178428_1_, "x", 0);
                final int var3 = JsonUtils.HorizonCode_Horizon_È(p_178428_1_, "y", 0);
                final ModelRotation var4 = ModelRotation.HorizonCode_Horizon_È(var2, var3);
                if (var4 == null) {
                    throw new JsonParseException("Invalid BlockModelRotation x: " + var2 + ", y: " + var3);
                }
                return var4;
            }
            
            protected String Â(final JsonObject p_178424_1_) {
                return JsonUtils.Ó(p_178424_1_, "model");
            }
            
            protected int Ý(final JsonObject p_178427_1_) {
                return JsonUtils.HorizonCode_Horizon_È(p_178427_1_, "weight", 1);
            }
            
            public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
                return this.HorizonCode_Horizon_È(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
            }
        }
    }
    
    public static class Ø­áŒŠá
    {
        private final String HorizonCode_Horizon_È;
        private final List Â;
        private static final String Ý = "CL_00002493";
        
        public Ø­áŒŠá(final String p_i46218_1_, final List p_i46218_2_) {
            this.HorizonCode_Horizon_È = p_i46218_1_;
            this.Â = p_i46218_2_;
        }
        
        public List HorizonCode_Horizon_È() {
            return this.Â;
        }
        
        @Override
        public boolean equals(final Object p_equals_1_) {
            if (this == p_equals_1_) {
                return true;
            }
            if (!(p_equals_1_ instanceof Ø­áŒŠá)) {
                return false;
            }
            final Ø­áŒŠá var2 = (Ø­áŒŠá)p_equals_1_;
            return this.HorizonCode_Horizon_È.equals(var2.HorizonCode_Horizon_È) && this.Â.equals(var2.Â);
        }
        
        @Override
        public int hashCode() {
            int var1 = this.HorizonCode_Horizon_È.hashCode();
            var1 = 31 * var1 + this.Â.hashCode();
            return var1;
        }
    }
}
