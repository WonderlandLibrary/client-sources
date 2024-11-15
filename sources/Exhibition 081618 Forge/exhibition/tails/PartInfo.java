package exhibition.tails;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class PartInfo implements Cloneable {
   @Expose
   public final boolean hasPart;
   @Expose
   public final int typeid;
   @Expose
   public final int subid;
   @Expose
   public final int[] tints;
   @Expose
   public final int textureID;
   @Expose
   public final float scale;
   private ResourceLocation texture;
   public boolean needsTextureCompile;

   public PartInfo(boolean hasPart, int type, int subtype, int textureID, int[] tints, float scale, ResourceLocation texture) {
      this.needsTextureCompile = true;
      this.hasPart = hasPart;
      this.typeid = type;
      this.subid = subtype;
      this.textureID = textureID;
      this.tints = tints;
      this.scale = scale;
      this.texture = texture;
   }

   public PartInfo(boolean hasPart, int type, int subtype, int textureID, int tint1, int tint2, int tint3, float scale, ResourceLocation texture) {
      this(hasPart, type, subtype, textureID, new int[]{tint1, tint2, tint3}, scale, texture);
   }

   public ResourceLocation getTexture() {
      return this.texture;
   }

   public void setTexture(ResourceLocation texture) {
      if (texture != null && (this.texture == null || this.texture.equals(texture))) {
         this.needsTextureCompile = false;
      } else {
         try {
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
         } catch (Exception var3) {
            ;
         }

         this.needsTextureCompile = true;
      }

      this.texture = texture;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         PartInfo partInfo = (PartInfo)o;
         if (this.hasPart != partInfo.hasPart) {
            return false;
         } else if (this.subid != partInfo.subid) {
            return false;
         } else if (this.textureID != partInfo.textureID) {
            return false;
         } else if (this.typeid != partInfo.typeid) {
            return false;
         } else {
            return Arrays.equals(this.tints, partInfo.tints);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.hasPart ? 1 : 0;
      result = 31 * result + this.typeid;
      result = 31 * result + this.subid;
      result = 31 * result + Arrays.hashCode(this.tints);
      result = 31 * result + this.textureID;
      return result;
   }

   public String toString() {
      return "PartInfo{hasPart=" + this.hasPart + ", typeid=" + this.typeid + ", subid=" + this.subid + ", tints=" + Arrays.toString(this.tints) + ", textureID=" + this.textureID + ", texture=" + this.texture + '}';
   }

   public PartInfo deepCopy() {
      Gson gson = new Gson();
      return (PartInfo)gson.fromJson(gson.toJson(this), PartInfo.class);
   }
}
