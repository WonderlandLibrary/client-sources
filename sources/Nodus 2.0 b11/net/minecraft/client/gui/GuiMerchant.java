/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import io.netty.buffer.ByteBuf;
/*   4:    */ import io.netty.buffer.Unpooled;
/*   5:    */ import java.util.List;
/*   6:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   7:    */ import net.minecraft.client.Minecraft;
/*   8:    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*   9:    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*  10:    */ import net.minecraft.client.renderer.RenderHelper;
/*  11:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*  12:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  13:    */ import net.minecraft.client.resources.I18n;
/*  14:    */ import net.minecraft.entity.IMerchant;
/*  15:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  16:    */ import net.minecraft.inventory.ContainerMerchant;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*  19:    */ import net.minecraft.util.ResourceLocation;
/*  20:    */ import net.minecraft.village.MerchantRecipe;
/*  21:    */ import net.minecraft.village.MerchantRecipeList;
/*  22:    */ import net.minecraft.world.World;
/*  23:    */ import org.apache.logging.log4j.LogManager;
/*  24:    */ import org.apache.logging.log4j.Logger;
/*  25:    */ import org.lwjgl.opengl.GL11;
/*  26:    */ 
/*  27:    */ public class GuiMerchant
/*  28:    */   extends GuiContainer
/*  29:    */ {
/*  30: 27 */   private static final Logger logger = ;
/*  31: 28 */   private static final ResourceLocation field_147038_v = new ResourceLocation("textures/gui/container/villager.png");
/*  32:    */   private IMerchant field_147037_w;
/*  33:    */   private MerchantButton field_147043_x;
/*  34:    */   private MerchantButton field_147042_y;
/*  35:    */   private int field_147041_z;
/*  36:    */   private String field_147040_A;
/*  37:    */   private static final String __OBFID = "CL_00000762";
/*  38:    */   
/*  39:    */   public GuiMerchant(InventoryPlayer par1InventoryPlayer, IMerchant par2IMerchant, World par3World, String par4Str)
/*  40:    */   {
/*  41: 38 */     super(new ContainerMerchant(par1InventoryPlayer, par2IMerchant, par3World));
/*  42: 39 */     this.field_147037_w = par2IMerchant;
/*  43: 40 */     this.field_147040_A = ((par4Str != null) && (par4Str.length() >= 1) ? par4Str : I18n.format("entity.Villager.name", new Object[0]));
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void initGui()
/*  47:    */   {
/*  48: 48 */     super.initGui();
/*  49: 49 */     int var1 = (width - this.field_146999_f) / 2;
/*  50: 50 */     int var2 = (height - this.field_147000_g) / 2;
/*  51: 51 */     this.buttonList.add(this.field_147043_x = new MerchantButton(1, var1 + 120 + 27, var2 + 24 - 1, true));
/*  52: 52 */     this.buttonList.add(this.field_147042_y = new MerchantButton(2, var1 + 36 - 19, var2 + 24 - 1, false));
/*  53: 53 */     this.field_147043_x.enabled = false;
/*  54: 54 */     this.field_147042_y.enabled = false;
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected void func_146979_b(int p_146979_1_, int p_146979_2_)
/*  58:    */   {
/*  59: 59 */     this.fontRendererObj.drawString(this.field_147040_A, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(this.field_147040_A) / 2, 6, 4210752);
/*  60: 60 */     this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void updateScreen()
/*  64:    */   {
/*  65: 68 */     super.updateScreen();
/*  66: 69 */     MerchantRecipeList var1 = this.field_147037_w.getRecipes(this.mc.thePlayer);
/*  67: 71 */     if (var1 != null)
/*  68:    */     {
/*  69: 73 */       this.field_147043_x.enabled = (this.field_147041_z < var1.size() - 1);
/*  70: 74 */       this.field_147042_y.enabled = (this.field_147041_z > 0);
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  75:    */   {
/*  76: 80 */     boolean var2 = false;
/*  77: 82 */     if (p_146284_1_ == this.field_147043_x)
/*  78:    */     {
/*  79: 84 */       this.field_147041_z += 1;
/*  80: 85 */       var2 = true;
/*  81:    */     }
/*  82: 87 */     else if (p_146284_1_ == this.field_147042_y)
/*  83:    */     {
/*  84: 89 */       this.field_147041_z -= 1;
/*  85: 90 */       var2 = true;
/*  86:    */     }
/*  87: 93 */     if (var2)
/*  88:    */     {
/*  89: 95 */       ((ContainerMerchant)this.field_147002_h).setCurrentRecipeIndex(this.field_147041_z);
/*  90: 96 */       ByteBuf var3 = Unpooled.buffer();
/*  91:    */       try
/*  92:    */       {
/*  93:100 */         var3.writeInt(this.field_147041_z);
/*  94:101 */         this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|TrSel", var3));
/*  95:    */       }
/*  96:    */       catch (Exception var8)
/*  97:    */       {
/*  98:105 */         logger.error("Couldn't send trade info", var8);
/*  99:    */       }
/* 100:    */       finally
/* 101:    */       {
/* 102:109 */         var3.release();
/* 103:    */       }
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_)
/* 108:    */   {
/* 109:116 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 110:117 */     this.mc.getTextureManager().bindTexture(field_147038_v);
/* 111:118 */     int var4 = (width - this.field_146999_f) / 2;
/* 112:119 */     int var5 = (height - this.field_147000_g) / 2;
/* 113:120 */     drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
/* 114:121 */     MerchantRecipeList var6 = this.field_147037_w.getRecipes(this.mc.thePlayer);
/* 115:123 */     if ((var6 != null) && (!var6.isEmpty()))
/* 116:    */     {
/* 117:125 */       int var7 = this.field_147041_z;
/* 118:126 */       MerchantRecipe var8 = (MerchantRecipe)var6.get(var7);
/* 119:128 */       if (var8.isRecipeDisabled())
/* 120:    */       {
/* 121:130 */         this.mc.getTextureManager().bindTexture(field_147038_v);
/* 122:131 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 123:132 */         GL11.glDisable(2896);
/* 124:133 */         drawTexturedModalRect(this.field_147003_i + 83, this.field_147009_r + 21, 212, 0, 28, 21);
/* 125:134 */         drawTexturedModalRect(this.field_147003_i + 83, this.field_147009_r + 51, 212, 0, 28, 21);
/* 126:    */       }
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void drawScreen(int par1, int par2, float par3)
/* 131:    */   {
/* 132:144 */     super.drawScreen(par1, par2, par3);
/* 133:145 */     MerchantRecipeList var4 = this.field_147037_w.getRecipes(this.mc.thePlayer);
/* 134:147 */     if ((var4 != null) && (!var4.isEmpty()))
/* 135:    */     {
/* 136:149 */       int var5 = (width - this.field_146999_f) / 2;
/* 137:150 */       int var6 = (height - this.field_147000_g) / 2;
/* 138:151 */       int var7 = this.field_147041_z;
/* 139:152 */       MerchantRecipe var8 = (MerchantRecipe)var4.get(var7);
/* 140:153 */       GL11.glPushMatrix();
/* 141:154 */       ItemStack var9 = var8.getItemToBuy();
/* 142:155 */       ItemStack var10 = var8.getSecondItemToBuy();
/* 143:156 */       ItemStack var11 = var8.getItemToSell();
/* 144:157 */       RenderHelper.enableGUIStandardItemLighting();
/* 145:158 */       GL11.glDisable(2896);
/* 146:159 */       GL11.glEnable(32826);
/* 147:160 */       GL11.glEnable(2903);
/* 148:161 */       GL11.glEnable(2896);
/* 149:162 */       itemRender.zLevel = 100.0F;
/* 150:163 */       itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var9, var5 + 36, var6 + 24);
/* 151:164 */       itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var9, var5 + 36, var6 + 24);
/* 152:166 */       if (var10 != null)
/* 153:    */       {
/* 154:168 */         itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var10, var5 + 62, var6 + 24);
/* 155:169 */         itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var10, var5 + 62, var6 + 24);
/* 156:    */       }
/* 157:172 */       itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var11, var5 + 120, var6 + 24);
/* 158:173 */       itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var11, var5 + 120, var6 + 24);
/* 159:174 */       itemRender.zLevel = 0.0F;
/* 160:175 */       GL11.glDisable(2896);
/* 161:177 */       if (func_146978_c(36, 24, 16, 16, par1, par2)) {
/* 162:179 */         func_146285_a(var9, par1, par2);
/* 163:181 */       } else if ((var10 != null) && (func_146978_c(62, 24, 16, 16, par1, par2))) {
/* 164:183 */         func_146285_a(var10, par1, par2);
/* 165:185 */       } else if (func_146978_c(120, 24, 16, 16, par1, par2)) {
/* 166:187 */         func_146285_a(var11, par1, par2);
/* 167:    */       }
/* 168:190 */       GL11.glPopMatrix();
/* 169:191 */       GL11.glEnable(2896);
/* 170:192 */       GL11.glEnable(2929);
/* 171:193 */       RenderHelper.enableStandardItemLighting();
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public IMerchant func_147035_g()
/* 176:    */   {
/* 177:199 */     return this.field_147037_w;
/* 178:    */   }
/* 179:    */   
/* 180:    */   static class MerchantButton
/* 181:    */     extends NodusGuiButton
/* 182:    */   {
/* 183:    */     private final boolean field_146157_o;
/* 184:    */     private static final String __OBFID = "CL_00000763";
/* 185:    */     
/* 186:    */     public MerchantButton(int par1, int par2, int par3, boolean par4)
/* 187:    */     {
/* 188:209 */       super(par2, par3, 12, 19, "");
/* 189:210 */       this.field_146157_o = par4;
/* 190:    */     }
/* 191:    */     
/* 192:    */     public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
/* 193:    */     {
/* 194:215 */       if (this.field_146125_m)
/* 195:    */       {
/* 196:217 */         p_146112_1_.getTextureManager().bindTexture(GuiMerchant.field_147038_v);
/* 197:218 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 198:219 */         boolean var4 = (p_146112_2_ >= this.xPosition) && (p_146112_3_ >= this.yPosition) && (p_146112_2_ < this.xPosition + this.width) && (p_146112_3_ < this.yPosition + this.height);
/* 199:220 */         int var5 = 0;
/* 200:221 */         int var6 = 176;
/* 201:223 */         if (!this.enabled) {
/* 202:225 */           var6 += this.width * 2;
/* 203:227 */         } else if (var4) {
/* 204:229 */           var6 += this.width;
/* 205:    */         }
/* 206:232 */         if (!this.field_146157_o) {
/* 207:234 */           var5 += this.height;
/* 208:    */         }
/* 209:237 */         drawTexturedModalRect(this.xPosition, this.yPosition, var6, var5, this.width, this.height);
/* 210:    */       }
/* 211:    */     }
/* 212:    */   }
/* 213:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiMerchant
 * JD-Core Version:    0.7.0.1
 */