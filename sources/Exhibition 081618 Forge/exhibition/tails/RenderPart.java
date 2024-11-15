package exhibition.tails;

import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;

public class RenderPart {
   private static final HashMap renderHelpers = new HashMap();
   protected final String name;
   protected final String[] textureNames;
   protected final int subTypes;
   protected final String[][] authors;
   protected final String modelAuthor;
   public final ModelPartBase modelPart;

   public RenderPart(String name, int subTypes, ModelPartBase modelPart, String modelAuthor, String... textureNames) {
      this.name = name;
      this.subTypes = subTypes;
      this.modelAuthor = modelAuthor;
      this.modelPart = modelPart;
      this.textureNames = textureNames;
      this.authors = new String[subTypes + 1][textureNames.length];
   }

   public void render(EntityLivingBase entity, PartInfo info, double x, double y, double z, float partialTicks) {
      if (info.needsTextureCompile || info.getTexture() == null) {
         info.setTexture(TextureHelper.generateTexture(entity.getUniqueID(), info));
         info.needsTextureCompile = false;
      }

      GlStateManager.pushMatrix();
      if (entity.isSneaking()) {
         GlStateManager.translate(0.0F, 0.82F, 0.0F);
      } else {
         GlStateManager.translate(0.0F, 0.65F, 0.1F);
      }

      GlStateManager.scale(0.8F, 0.8F, 0.8F);
      this.doRender(entity, info, partialTicks);
      GlStateManager.popMatrix();
   }

   protected void doRender(EntityLivingBase entity, PartInfo info, float partialTicks) {
      Minecraft.getMinecraft().renderEngine.bindTexture(info.getTexture());
      this.modelPart.render(entity, info.subid, partialTicks);
   }

   public String[] getTextureNames(int subid) {
      return this.textureNames;
   }

   public int getAvailableSubTypes() {
      return this.subTypes;
   }

   public String getUnlocalisedName(int subType) {
      return this.name + "." + subType + ".name";
   }

   public RenderPart setAuthor(String author, int subID, int textureID) {
      this.authors[subID][textureID] = author;
      return this;
   }

   public RenderPart setAuthor(String author, int subID) {
      for(int textureID = 0; textureID < this.getTextureNames(subID).length; ++textureID) {
         this.setAuthor(author, subID, textureID);
      }

      return this;
   }

   public RenderPart setAuthor(String author) {
      for(int subID = 0; subID <= this.subTypes; ++subID) {
         this.setAuthor(author, subID);
      }

      return this;
   }

   public String getModelAuthor() {
      return this.modelAuthor;
   }

   public String getAuthor(int subID, int textureID) {
      return this.authors[subID][textureID];
   }

   public boolean hasAuthor(int subID, int textureID) {
      return this.getAuthor(subID, textureID) != null;
   }

   public static void registerRenderHelper(Class clazz, IRenderHelper helper) {
      if (!renderHelpers.containsKey(clazz) && helper != null) {
         renderHelpers.put(clazz, helper);
      } else {
         throw new IllegalArgumentException("An invalid RenderHelper was registered!");
      }
   }

   public static IRenderHelper getRenderHelper(Class clazz) {
      return (IRenderHelper)renderHelpers.getOrDefault(clazz, (Object)null);
   }
}
