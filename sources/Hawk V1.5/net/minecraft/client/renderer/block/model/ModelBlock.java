package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBlock {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String __OBFID = "CL_00002503";
   static final Gson SERIALIZER = (new GsonBuilder()).registerTypeAdapter(ModelBlock.class, new ModelBlock.Deserializer()).registerTypeAdapter(BlockPart.class, new BlockPart.Deserializer()).registerTypeAdapter(BlockPartFace.class, new BlockPartFace.Deserializer()).registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer()).registerTypeAdapter(ItemTransformVec3f.class, new ItemTransformVec3f.Deserializer()).registerTypeAdapter(ItemCameraTransforms.class, new ItemCameraTransforms.Deserializer()).create();
   protected ModelBlock parent;
   public String field_178317_b;
   private ItemCameraTransforms itemCameraTransforms;
   private final boolean ambientOcclusion;
   private final List elements;
   private final boolean field_178322_i;
   protected ResourceLocation parentLocation;
   protected final Map textures;

   public boolean func_178309_b() {
      return this.hasParent() ? this.parent.func_178309_b() : this.field_178322_i;
   }

   private boolean hasParent() {
      return this.parent != null;
   }

   public ItemTransformVec3f getInGuiTransform() {
      return this.parent != null && this.itemCameraTransforms.field_178354_e == ItemTransformVec3f.field_178366_a ? this.parent.getInGuiTransform() : this.itemCameraTransforms.field_178354_e;
   }

   public ItemTransformVec3f getFirstPersonTransform() {
      return this.parent != null && this.itemCameraTransforms.field_178356_c == ItemTransformVec3f.field_178366_a ? this.parent.getFirstPersonTransform() : this.itemCameraTransforms.field_178356_c;
   }

   protected ModelBlock(ResourceLocation var1, Map var2, boolean var3, boolean var4, ItemCameraTransforms var5) {
      this(var1, Collections.emptyList(), var2, var3, var4, var5);
   }

   public void getParentFromMap(Map var1) {
      if (this.parentLocation != null) {
         this.parent = (ModelBlock)var1.get(this.parentLocation);
      }

   }

   public boolean isResolved() {
      return this.parentLocation == null || this.parent != null && this.parent.isResolved();
   }

   public ModelBlock getRootModel() {
      return this.hasParent() ? this.parent.getRootModel() : this;
   }

   public String resolveTextureName(String var1) {
      if (!this.isTextureName(var1)) {
         var1 = String.valueOf((new StringBuilder(String.valueOf('#'))).append(var1));
      }

      return this.resolveTextureName(var1, new ModelBlock.Bookkeep(this, (Object)null));
   }

   public ItemTransformVec3f getThirdPersonTransform() {
      return this.parent != null && this.itemCameraTransforms.field_178355_b == ItemTransformVec3f.field_178366_a ? this.parent.getThirdPersonTransform() : this.itemCameraTransforms.field_178355_b;
   }

   public List getElements() {
      return this.hasParent() ? this.parent.getElements() : this.elements;
   }

   public static ModelBlock deserialize(Reader var0) {
      return (ModelBlock)SERIALIZER.fromJson(var0, ModelBlock.class);
   }

   private boolean isTextureName(String var1) {
      return var1.charAt(0) == '#';
   }

   private ModelBlock(ResourceLocation var1, List var2, Map var3, boolean var4, boolean var5, ItemCameraTransforms var6) {
      this.field_178317_b = "";
      this.elements = var2;
      this.field_178322_i = var4;
      this.ambientOcclusion = var5;
      this.textures = var3;
      this.parentLocation = var1;
      this.itemCameraTransforms = var6;
   }

   public ItemTransformVec3f getHeadTransform() {
      return this.parent != null && this.itemCameraTransforms.field_178353_d == ItemTransformVec3f.field_178366_a ? this.parent.getHeadTransform() : this.itemCameraTransforms.field_178353_d;
   }

   public static void func_178312_b(Map var0) {
      Iterator var1 = var0.values().iterator();

      while(var1.hasNext()) {
         ModelBlock var2 = (ModelBlock)var1.next();

         try {
            ModelBlock var3 = var2.parent;

            for(ModelBlock var4 = var3.parent; var3 != var4; var4 = var4.parent.parent) {
               var3 = var3.parent;
            }

            throw new ModelBlock.LoopException();
         } catch (NullPointerException var5) {
         }
      }

   }

   public boolean isAmbientOcclusionEnabled() {
      return this.ambientOcclusion;
   }

   public ResourceLocation getParentLocation() {
      return this.parentLocation;
   }

   public static ModelBlock deserialize(String var0) {
      return deserialize((Reader)(new StringReader(var0)));
   }

   private String resolveTextureName(String var1, ModelBlock.Bookkeep var2) {
      if (this.isTextureName(var1)) {
         if (this == var2.field_178323_b) {
            LOGGER.warn(String.valueOf((new StringBuilder("Unable to resolve texture due to upward reference: ")).append(var1).append(" in ").append(this.field_178317_b)));
            return "missingno";
         } else {
            String var3 = (String)this.textures.get(var1.substring(1));
            if (var3 == null && this.hasParent()) {
               var3 = this.parent.resolveTextureName(var1, var2);
            }

            var2.field_178323_b = this;
            if (var3 != null && this.isTextureName(var3)) {
               var3 = var2.model.resolveTextureName(var3, var2);
            }

            return var3 != null && !this.isTextureName(var3) ? var3 : "missingno";
         }
      } else {
         return var1;
      }
   }

   public boolean isTexturePresent(String var1) {
      return !"missingno".equals(this.resolveTextureName(var1));
   }

   protected ModelBlock(List var1, Map var2, boolean var3, boolean var4, ItemCameraTransforms var5) {
      this((ResourceLocation)null, var1, var2, var3, var4, var5);
   }

   public static class LoopException extends RuntimeException {
      private static final String __OBFID = "CL_00002499";
   }

   final class Bookkeep {
      public final ModelBlock model;
      private static final String __OBFID = "CL_00002501";
      public ModelBlock field_178323_b;
      final ModelBlock this$0;

      private Bookkeep(ModelBlock var1) {
         this.this$0 = var1;
         this.model = var1;
      }

      Bookkeep(ModelBlock var1, Object var2) {
         this(var1);
      }
   }

   public static class Deserializer implements JsonDeserializer {
      private static final String __OBFID = "CL_00002500";

      public ModelBlock func_178327_a(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         JsonObject var4 = var1.getAsJsonObject();
         List var5 = this.getModelElements(var3, var4);
         String var6 = this.getParent(var4);
         boolean var7 = StringUtils.isEmpty(var6);
         boolean var8 = var5.isEmpty();
         if (var8 && var7) {
            throw new JsonParseException("BlockModel requires either elements or parent, found neither");
         } else if (!var7 && !var8) {
            throw new JsonParseException("BlockModel requires either elements or parent, found both");
         } else {
            Map var9 = this.getTextures(var4);
            boolean var10 = this.getAmbientOcclusionEnabled(var4);
            ItemCameraTransforms var11 = ItemCameraTransforms.field_178357_a;
            if (var4.has("display")) {
               JsonObject var12 = JsonUtils.getJsonObject(var4, "display");
               var11 = (ItemCameraTransforms)var3.deserialize(var12, ItemCameraTransforms.class);
            }

            return var8 ? new ModelBlock(new ResourceLocation(var6), var9, var10, true, var11) : new ModelBlock(var5, var9, var10, true, var11);
         }
      }

      protected List getModelElements(JsonDeserializationContext var1, JsonObject var2) {
         ArrayList var3 = Lists.newArrayList();
         if (var2.has("elements")) {
            Iterator var4 = JsonUtils.getJsonObjectJsonArrayField(var2, "elements").iterator();

            while(var4.hasNext()) {
               JsonElement var5 = (JsonElement)var4.next();
               var3.add((BlockPart)var1.deserialize(var5, BlockPart.class));
            }
         }

         return var3;
      }

      public Object deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         return this.func_178327_a(var1, var2, var3);
      }

      protected boolean getAmbientOcclusionEnabled(JsonObject var1) {
         return JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var1, "ambientocclusion", true);
      }

      private Map getTextures(JsonObject var1) {
         HashMap var2 = Maps.newHashMap();
         if (var1.has("textures")) {
            JsonObject var3 = var1.getAsJsonObject("textures");
            Iterator var4 = var3.entrySet().iterator();

            while(var4.hasNext()) {
               Entry var5 = (Entry)var4.next();
               var2.put(var5.getKey(), ((JsonElement)var5.getValue()).getAsString());
            }
         }

         return var2;
      }

      private String getParent(JsonObject var1) {
         return JsonUtils.getJsonObjectStringFieldValueOrDefault(var1, "parent", "");
      }
   }
}
