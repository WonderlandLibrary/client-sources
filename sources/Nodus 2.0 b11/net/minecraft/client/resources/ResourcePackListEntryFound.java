/*  1:   */ package net.minecraft.client.resources;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.Minecraft;
/*  4:   */ import net.minecraft.client.gui.GuiScreenResourcePacks;
/*  5:   */ 
/*  6:   */ public class ResourcePackListEntryFound
/*  7:   */   extends ResourcePackListEntry
/*  8:   */ {
/*  9:   */   private final ResourcePackRepository.Entry field_148319_c;
/* 10:   */   private static final String __OBFID = "CL_00000823";
/* 11:   */   
/* 12:   */   public ResourcePackListEntryFound(GuiScreenResourcePacks p_i45053_1_, ResourcePackRepository.Entry p_i45053_2_)
/* 13:   */   {
/* 14:12 */     super(p_i45053_1_);
/* 15:13 */     this.field_148319_c = p_i45053_2_;
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected void func_148313_c()
/* 19:   */   {
/* 20:18 */     this.field_148319_c.bindTexturePackIcon(this.field_148317_a.getTextureManager());
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected String func_148311_a()
/* 24:   */   {
/* 25:23 */     return this.field_148319_c.getTexturePackDescription();
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected String func_148312_b()
/* 29:   */   {
/* 30:28 */     return this.field_148319_c.getResourcePackName();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public ResourcePackRepository.Entry func_148318_i()
/* 34:   */   {
/* 35:33 */     return this.field_148319_c;
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.ResourcePackListEntryFound
 * JD-Core Version:    0.7.0.1
 */