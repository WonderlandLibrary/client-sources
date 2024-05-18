/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.gui.GuiScreenResourcePacks;
/*    */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*    */ import net.minecraft.client.resources.data.PackMetadataSection;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class ResourcePackListEntryDefault
/*    */   extends ResourcePackListEntry {
/* 16 */   private static final Logger logger = LogManager.getLogger();
/*    */   
/*    */   private final IResourcePack field_148320_d;
/*    */   private final ResourceLocation resourcePackIcon;
/*    */   
/*    */   public ResourcePackListEntryDefault(GuiScreenResourcePacks resourcePacksGUIIn) {
/* 22 */     super(resourcePacksGUIIn); DynamicTexture dynamictexture;
/* 23 */     this.field_148320_d = (this.mc.getResourcePackRepository()).rprDefaultResourcePack;
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 28 */       dynamictexture = new DynamicTexture(this.field_148320_d.getPackImage());
/*    */     }
/* 30 */     catch (IOException var4) {
/*    */       
/* 32 */       dynamictexture = TextureUtil.missingTexture;
/*    */     } 
/*    */     
/* 35 */     this.resourcePackIcon = this.mc.getTextureManager().getDynamicTextureLocation("texturepackicon", dynamictexture);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int func_183019_a() {
/* 40 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String func_148311_a() {
/*    */     try {
/* 47 */       PackMetadataSection packmetadatasection = this.field_148320_d.<PackMetadataSection>getPackMetadata((this.mc.getResourcePackRepository()).rprMetadataSerializer, "pack");
/*    */       
/* 49 */       if (packmetadatasection != null)
/*    */       {
/* 51 */         return packmetadatasection.getPackDescription().getFormattedText();
/*    */       }
/*    */     }
/* 54 */     catch (JsonParseException jsonparseexception) {
/*    */       
/* 56 */       logger.error("Couldn't load metadata info", (Throwable)jsonparseexception);
/*    */     }
/* 58 */     catch (IOException ioexception) {
/*    */       
/* 60 */       logger.error("Couldn't load metadata info", ioexception);
/*    */     } 
/*    */     
/* 63 */     return EnumChatFormatting.RED + "Missing " + "pack.mcmeta" + " :(";
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean func_148309_e() {
/* 68 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean func_148308_f() {
/* 73 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean func_148314_g() {
/* 78 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean func_148307_h() {
/* 83 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String func_148312_b() {
/* 88 */     return "Default";
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_148313_c() {
/* 93 */     this.mc.getTextureManager().bindTexture(this.resourcePackIcon);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean func_148310_d() {
/* 98 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\resources\ResourcePackListEntryDefault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */