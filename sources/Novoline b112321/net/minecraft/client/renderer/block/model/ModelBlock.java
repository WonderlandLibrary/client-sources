package net.minecraft.client.renderer.block.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.aAp;
import net.ayZ;
import net.j6;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPart$Deserializer;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.BlockPartFace$Deserializer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f$Deserializer;
import net.minecraft.client.renderer.block.model.ModelBlock$Bookkeep;
import net.minecraft.client.renderer.block.model.ModelBlock$LoopException;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBlock {
   private static final Logger LOGGER = LogManager.getLogger();
   static final Gson SERIALIZER = (new GsonBuilder()).registerTypeAdapter(ModelBlock.class, new aAp()).registerTypeAdapter(BlockPart.class, new BlockPart$Deserializer()).registerTypeAdapter(BlockPartFace.class, new BlockPartFace$Deserializer()).registerTypeAdapter(BlockFaceUV.class, new ayZ()).registerTypeAdapter(ItemTransformVec3f.class, new ItemTransformVec3f$Deserializer()).registerTypeAdapter(ItemCameraTransforms.class, new j6()).create();
   private final List elements;
   private final boolean gui3d;
   private final boolean ambientOcclusion;
   private final ItemCameraTransforms cameraTransforms;
   public String name;
   protected final Map textures;
   protected ModelBlock parent;
   protected ResourceLocation parentLocation;

   public static ModelBlock deserialize(Reader var0) {
      return (ModelBlock)SERIALIZER.fromJson(var0, ModelBlock.class);
   }

   public static ModelBlock deserialize(String var0) {
      return deserialize((Reader)(new StringReader(var0)));
   }

   protected ModelBlock(List var1, Map var2, boolean var3, boolean var4, ItemCameraTransforms var5) {
      this((ResourceLocation)null, var1, var2, var3, var4, var5);
   }

   protected ModelBlock(ResourceLocation var1, Map var2, boolean var3, boolean var4, ItemCameraTransforms var5) {
      this(var1, Collections.emptyList(), var2, var3, var4, var5);
   }

   private ModelBlock(ResourceLocation var1, List var2, Map var3, boolean var4, boolean var5, ItemCameraTransforms var6) {
      this.name = "";
      this.elements = var2;
      this.ambientOcclusion = var4;
      this.gui3d = var5;
      this.textures = var3;
      this.parentLocation = var1;
      this.cameraTransforms = var6;
   }

   public List getElements() {
      return this.hasParent()?this.parent.getElements():this.elements;
   }

   private boolean hasParent() {
      return this.parent != null;
   }

   public boolean isAmbientOcclusion() {
      return this.hasParent()?this.parent.isAmbientOcclusion():this.ambientOcclusion;
   }

   public boolean isGui3d() {
      return this.gui3d;
   }

   public boolean isResolved() {
      return this.parentLocation == null || this.parent != null && this.parent.isResolved();
   }

   public void getParentFromMap(Map var1) {
      if(this.parentLocation != null) {
         this.parent = (ModelBlock)var1.get(this.parentLocation);
      }

   }

   public boolean isTexturePresent(String var1) {
      return !"missingno".equals(this.resolveTextureName(var1));
   }

   public String resolveTextureName(String var1) {
      if(!this.startsWithHash(var1)) {
         var1 = '#' + var1;
      }

      return this.resolveTextureName(var1, new ModelBlock$Bookkeep(this));
   }

   private String resolveTextureName(String var1, ModelBlock$Bookkeep var2) {
      if(this.startsWithHash(var1)) {
         if(this == var2.modelExt) {
            LOGGER.warn("Unable to resolve texture due to upward reference: " + var1 + " in " + this.name);
            return "missingno";
         } else {
            String var3 = (String)this.textures.get(var1.substring(1));
            if(this.hasParent()) {
               var3 = this.parent.resolveTextureName(var1, var2);
            }

            var2.modelExt = this;
            if(this.startsWithHash(var3)) {
               var3 = var2.model.resolveTextureName(var3, var2);
            }

            return !this.startsWithHash(var3)?var3:"missingno";
         }
      } else {
         return var1;
      }
   }

   private boolean startsWithHash(String var1) {
      return var1.charAt(0) == 35;
   }

   public ResourceLocation getParentLocation() {
      return this.parentLocation;
   }

   public ModelBlock getRootModel() {
      return this.hasParent()?this.parent.getRootModel():this;
   }

   public ItemCameraTransforms func_181682_g() {
      ItemTransformVec3f var1 = this.func_181681_a(ItemCameraTransforms$TransformType.THIRD_PERSON);
      ItemTransformVec3f var2 = this.func_181681_a(ItemCameraTransforms$TransformType.FIRST_PERSON);
      ItemTransformVec3f var3 = this.func_181681_a(ItemCameraTransforms$TransformType.HEAD);
      ItemTransformVec3f var4 = this.func_181681_a(ItemCameraTransforms$TransformType.GUI);
      ItemTransformVec3f var5 = this.func_181681_a(ItemCameraTransforms$TransformType.GROUND);
      ItemTransformVec3f var6 = this.func_181681_a(ItemCameraTransforms$TransformType.FIXED);
      return new ItemCameraTransforms(var1, var2, var3, var4, var5, var6);
   }

   private ItemTransformVec3f func_181681_a(ItemCameraTransforms$TransformType var1) {
      return this.parent != null && !this.cameraTransforms.func_181687_c(var1)?this.parent.func_181681_a(var1):this.cameraTransforms.getTransform(var1);
   }

   public static void checkModelHierarchy(Map var0) {
      for(ModelBlock var2 : var0.values()) {
         try {
            ModelBlock var3 = var2.parent;

            for(ModelBlock var4 = var3.parent; var3 != var4; var4 = var4.parent.parent) {
               var3 = var3.parent;
            }

            throw new ModelBlock$LoopException();
         } catch (NullPointerException var5) {
            ;
         }
      }

   }

   private static ModelBlock$LoopException a(ModelBlock$LoopException var0) {
      return var0;
   }
}
