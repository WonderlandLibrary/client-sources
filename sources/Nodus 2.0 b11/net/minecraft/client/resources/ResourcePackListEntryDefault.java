/*  1:   */ package net.minecraft.client.resources;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.client.Minecraft;
/*  5:   */ import net.minecraft.client.gui.GuiScreenResourcePacks;
/*  6:   */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*  7:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  8:   */ import net.minecraft.client.renderer.texture.TextureUtil;
/*  9:   */ import net.minecraft.client.resources.data.PackMetadataSection;
/* 10:   */ import net.minecraft.util.EnumChatFormatting;
/* 11:   */ import net.minecraft.util.ResourceLocation;
/* 12:   */ import org.apache.logging.log4j.LogManager;
/* 13:   */ import org.apache.logging.log4j.Logger;
/* 14:   */ 
/* 15:   */ public class ResourcePackListEntryDefault
/* 16:   */   extends ResourcePackListEntry
/* 17:   */ {
/* 18:15 */   private static final Logger logger = ;
/* 19:23 */   private final IResourcePack field_148320_d = this.field_148317_a.getResourcePackRepository().rprDefaultResourcePack;
/* 20:   */   private final ResourceLocation field_148321_e;
/* 21:   */   private static final String __OBFID = "CL_00000822";
/* 22:   */   
/* 23:   */   public ResourcePackListEntryDefault(GuiScreenResourcePacks p_i45052_1_)
/* 24:   */   {
/* 25:22 */     super(p_i45052_1_);
/* 26:   */     DynamicTexture var2;
/* 27:   */     try
/* 28:   */     {
/* 29:28 */       var2 = new DynamicTexture(this.field_148320_d.getPackImage());
/* 30:   */     }
/* 31:   */     catch (IOException var4)
/* 32:   */     {
/* 33:   */       DynamicTexture var2;
/* 34:32 */       var2 = TextureUtil.missingTexture;
/* 35:   */     }
/* 36:35 */     this.field_148321_e = this.field_148317_a.getTextureManager().getDynamicTextureLocation("texturepackicon", var2);
/* 37:   */   }
/* 38:   */   
/* 39:   */   protected String func_148311_a()
/* 40:   */   {
/* 41:   */     try
/* 42:   */     {
/* 43:42 */       PackMetadataSection var1 = (PackMetadataSection)this.field_148320_d.getPackMetadata(this.field_148317_a.getResourcePackRepository().rprMetadataSerializer, "pack");
/* 44:44 */       if (var1 != null) {
/* 45:46 */         return var1.getPackDescription();
/* 46:   */       }
/* 47:   */     }
/* 48:   */     catch (IOException var2)
/* 49:   */     {
/* 50:51 */       logger.error("Couldn't load metadata info", var2);
/* 51:   */     }
/* 52:54 */     return EnumChatFormatting.RED + "Missing " + "pack.mcmeta" + " :(";
/* 53:   */   }
/* 54:   */   
/* 55:   */   protected boolean func_148309_e()
/* 56:   */   {
/* 57:59 */     return false;
/* 58:   */   }
/* 59:   */   
/* 60:   */   protected boolean func_148308_f()
/* 61:   */   {
/* 62:64 */     return false;
/* 63:   */   }
/* 64:   */   
/* 65:   */   protected boolean func_148314_g()
/* 66:   */   {
/* 67:69 */     return false;
/* 68:   */   }
/* 69:   */   
/* 70:   */   protected boolean func_148307_h()
/* 71:   */   {
/* 72:74 */     return false;
/* 73:   */   }
/* 74:   */   
/* 75:   */   protected String func_148312_b()
/* 76:   */   {
/* 77:79 */     return "Default";
/* 78:   */   }
/* 79:   */   
/* 80:   */   protected void func_148313_c()
/* 81:   */   {
/* 82:84 */     this.field_148317_a.getTextureManager().bindTexture(this.field_148321_e);
/* 83:   */   }
/* 84:   */   
/* 85:   */   protected boolean func_148310_d()
/* 86:   */   {
/* 87:89 */     return false;
/* 88:   */   }
/* 89:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.ResourcePackListEntryDefault
 * JD-Core Version:    0.7.0.1
 */