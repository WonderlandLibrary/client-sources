/*   1:    */ package net.minecraft.client.resources;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.client.Minecraft;
/*   5:    */ import net.minecraft.client.gui.FontRenderer;
/*   6:    */ import net.minecraft.client.gui.Gui;
/*   7:    */ import net.minecraft.client.gui.GuiListExtended.IGuiListEntry;
/*   8:    */ import net.minecraft.client.gui.GuiScreenResourcePacks;
/*   9:    */ import net.minecraft.client.renderer.Tessellator;
/*  10:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  11:    */ import net.minecraft.client.settings.GameSettings;
/*  12:    */ import net.minecraft.util.ResourceLocation;
/*  13:    */ import org.lwjgl.opengl.GL11;
/*  14:    */ 
/*  15:    */ public abstract class ResourcePackListEntry
/*  16:    */   implements GuiListExtended.IGuiListEntry
/*  17:    */ {
/*  18: 14 */   private static final ResourceLocation field_148316_c = new ResourceLocation("textures/gui/resource_packs.png");
/*  19:    */   protected final Minecraft field_148317_a;
/*  20:    */   protected final GuiScreenResourcePacks field_148315_b;
/*  21:    */   private static final String __OBFID = "CL_00000821";
/*  22:    */   
/*  23:    */   public ResourcePackListEntry(GuiScreenResourcePacks p_i45051_1_)
/*  24:    */   {
/*  25: 21 */     this.field_148315_b = p_i45051_1_;
/*  26: 22 */     this.field_148317_a = Minecraft.getMinecraft();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void func_148279_a(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_)
/*  30:    */   {
/*  31: 27 */     func_148313_c();
/*  32: 28 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  33: 29 */     Gui.func_146110_a(p_148279_2_, p_148279_3_, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
/*  34: 32 */     if (((this.field_148317_a.gameSettings.touchscreen) || (p_148279_9_)) && (func_148310_d()))
/*  35:    */     {
/*  36: 34 */       this.field_148317_a.getTextureManager().bindTexture(field_148316_c);
/*  37: 35 */       Gui.drawRect(p_148279_2_, p_148279_3_, p_148279_2_ + 32, p_148279_3_ + 32, -1601138544);
/*  38: 36 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  39: 37 */       int var10 = p_148279_7_ - p_148279_2_;
/*  40: 38 */       int var11 = p_148279_8_ - p_148279_3_;
/*  41: 40 */       if (func_148309_e())
/*  42:    */       {
/*  43: 42 */         if (var10 < 32) {
/*  44: 44 */           Gui.func_146110_a(p_148279_2_, p_148279_3_, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*  45:    */         } else {
/*  46: 48 */           Gui.func_146110_a(p_148279_2_, p_148279_3_, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*  47:    */         }
/*  48:    */       }
/*  49:    */       else
/*  50:    */       {
/*  51: 53 */         if (func_148308_f()) {
/*  52: 55 */           if (var10 < 16) {
/*  53: 57 */             Gui.func_146110_a(p_148279_2_, p_148279_3_, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*  54:    */           } else {
/*  55: 61 */             Gui.func_146110_a(p_148279_2_, p_148279_3_, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*  56:    */           }
/*  57:    */         }
/*  58: 65 */         if (func_148314_g()) {
/*  59: 67 */           if ((var10 < 32) && (var10 > 16) && (var11 < 16)) {
/*  60: 69 */             Gui.func_146110_a(p_148279_2_, p_148279_3_, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*  61:    */           } else {
/*  62: 73 */             Gui.func_146110_a(p_148279_2_, p_148279_3_, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*  63:    */           }
/*  64:    */         }
/*  65: 77 */         if (func_148307_h()) {
/*  66: 79 */           if ((var10 < 32) && (var10 > 16) && (var11 > 16)) {
/*  67: 81 */             Gui.func_146110_a(p_148279_2_, p_148279_3_, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*  68:    */           } else {
/*  69: 85 */             Gui.func_146110_a(p_148279_2_, p_148279_3_, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*  70:    */           }
/*  71:    */         }
/*  72:    */       }
/*  73:    */     }
/*  74: 91 */     String var14 = func_148312_b();
/*  75: 92 */     int var11 = this.field_148317_a.fontRenderer.getStringWidth(var14);
/*  76: 94 */     if (var11 > 157) {
/*  77: 96 */       var14 = this.field_148317_a.fontRenderer.trimStringToWidth(var14, 157 - this.field_148317_a.fontRenderer.getStringWidth("...")) + "...";
/*  78:    */     }
/*  79: 99 */     this.field_148317_a.fontRenderer.drawStringWithShadow(var14, p_148279_2_ + 32 + 2, p_148279_3_ + 1, 16777215);
/*  80:100 */     List var12 = this.field_148317_a.fontRenderer.listFormattedStringToWidth(func_148311_a(), 157);
/*  81:102 */     for (int var13 = 0; (var13 < 2) && (var13 < var12.size()); var13++) {
/*  82:104 */       this.field_148317_a.fontRenderer.drawStringWithShadow((String)var12.get(var13), p_148279_2_ + 32 + 2, p_148279_3_ + 12 + 10 * var13, 8421504);
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected abstract String func_148311_a();
/*  87:    */   
/*  88:    */   protected abstract String func_148312_b();
/*  89:    */   
/*  90:    */   protected abstract void func_148313_c();
/*  91:    */   
/*  92:    */   protected boolean func_148310_d()
/*  93:    */   {
/*  94:116 */     return true;
/*  95:    */   }
/*  96:    */   
/*  97:    */   protected boolean func_148309_e()
/*  98:    */   {
/*  99:121 */     return !this.field_148315_b.func_146961_a(this);
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected boolean func_148308_f()
/* 103:    */   {
/* 104:126 */     return this.field_148315_b.func_146961_a(this);
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected boolean func_148314_g()
/* 108:    */   {
/* 109:131 */     List var1 = this.field_148315_b.func_146962_b(this);
/* 110:132 */     int var2 = var1.indexOf(this);
/* 111:133 */     return (var2 > 0) && (((ResourcePackListEntry)var1.get(var2 - 1)).func_148310_d());
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected boolean func_148307_h()
/* 115:    */   {
/* 116:138 */     List var1 = this.field_148315_b.func_146962_b(this);
/* 117:139 */     int var2 = var1.indexOf(this);
/* 118:140 */     return (var2 >= 0) && (var2 < var1.size() - 1) && (((ResourcePackListEntry)var1.get(var2 + 1)).func_148310_d());
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
/* 122:    */   {
/* 123:145 */     if ((func_148310_d()) && (p_148278_5_ <= 32))
/* 124:    */     {
/* 125:147 */       if (func_148309_e())
/* 126:    */       {
/* 127:149 */         this.field_148315_b.func_146962_b(this).remove(this);
/* 128:150 */         this.field_148315_b.func_146963_h().add(0, this);
/* 129:151 */         return true;
/* 130:    */       }
/* 131:154 */       if ((p_148278_5_ < 16) && (func_148308_f()))
/* 132:    */       {
/* 133:156 */         this.field_148315_b.func_146962_b(this).remove(this);
/* 134:157 */         this.field_148315_b.func_146964_g().add(0, this);
/* 135:158 */         return true;
/* 136:    */       }
/* 137:164 */       if ((p_148278_5_ > 16) && (p_148278_6_ < 16) && (func_148314_g()))
/* 138:    */       {
/* 139:166 */         List var7 = this.field_148315_b.func_146962_b(this);
/* 140:167 */         int var8 = var7.indexOf(this);
/* 141:168 */         var7.remove(this);
/* 142:169 */         var7.add(var8 - 1, this);
/* 143:170 */         return true;
/* 144:    */       }
/* 145:173 */       if ((p_148278_5_ > 16) && (p_148278_6_ > 16) && (func_148307_h()))
/* 146:    */       {
/* 147:175 */         List var7 = this.field_148315_b.func_146962_b(this);
/* 148:176 */         int var8 = var7.indexOf(this);
/* 149:177 */         var7.remove(this);
/* 150:178 */         var7.add(var8 + 1, this);
/* 151:179 */         return true;
/* 152:    */       }
/* 153:    */     }
/* 154:183 */     return false;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {}
/* 158:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.ResourcePackListEntry
 * JD-Core Version:    0.7.0.1
 */