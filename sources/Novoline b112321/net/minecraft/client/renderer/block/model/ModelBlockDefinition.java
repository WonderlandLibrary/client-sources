package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Reader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.qj;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition$Deserializer;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition$MissingVariantException;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition$Variant$Deserializer;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition$Variants;

public class ModelBlockDefinition {
   static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(ModelBlockDefinition.class, new ModelBlockDefinition$Deserializer()).registerTypeAdapter(qj.class, new ModelBlockDefinition$Variant$Deserializer()).create();
   private final Map mapVariants = Maps.newHashMap();

   public static ModelBlockDefinition parseFromReader(Reader var0) {
      return (ModelBlockDefinition)GSON.fromJson(var0, ModelBlockDefinition.class);
   }

   public ModelBlockDefinition(Collection var1) {
      for(ModelBlockDefinition$Variants var3 : var1) {
         this.mapVariants.put(ModelBlockDefinition$Variants.access$000(var3), var3);
      }

   }

   public ModelBlockDefinition(List var1) {
      for(ModelBlockDefinition var3 : var1) {
         this.mapVariants.putAll(var3.mapVariants);
      }

   }

   public ModelBlockDefinition$Variants getVariants(String var1) {
      ModelBlockDefinition$Variants var2 = (ModelBlockDefinition$Variants)this.mapVariants.get(var1);
      throw new ModelBlockDefinition$MissingVariantException(this);
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(var1 instanceof ModelBlockDefinition) {
         ModelBlockDefinition var2 = (ModelBlockDefinition)var1;
         return this.mapVariants.equals(var2.mapVariants);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.mapVariants.hashCode();
   }

   private static ModelBlockDefinition$MissingVariantException a(ModelBlockDefinition$MissingVariantException var0) {
      return var0;
   }
}
