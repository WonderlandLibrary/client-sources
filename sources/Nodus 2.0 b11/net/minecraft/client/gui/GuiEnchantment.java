/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.client.Minecraft;
/*   5:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*   6:    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*   7:    */ import net.minecraft.client.model.ModelBook;
/*   8:    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*   9:    */ import net.minecraft.client.renderer.RenderHelper;
/*  10:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  11:    */ import net.minecraft.client.resources.I18n;
/*  12:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  13:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  14:    */ import net.minecraft.inventory.Container;
/*  15:    */ import net.minecraft.inventory.ContainerEnchantment;
/*  16:    */ import net.minecraft.inventory.Slot;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ import net.minecraft.util.EnchantmentNameParts;
/*  19:    */ import net.minecraft.util.MathHelper;
/*  20:    */ import net.minecraft.util.ResourceLocation;
/*  21:    */ import net.minecraft.world.World;
/*  22:    */ import org.lwjgl.opengl.GL11;
/*  23:    */ import org.lwjgl.util.glu.Project;
/*  24:    */ 
/*  25:    */ public class GuiEnchantment
/*  26:    */   extends GuiContainer
/*  27:    */ {
/*  28: 22 */   private static final ResourceLocation field_147078_C = new ResourceLocation("textures/gui/container/enchanting_table.png");
/*  29: 23 */   private static final ResourceLocation field_147070_D = new ResourceLocation("textures/entity/enchanting_table_book.png");
/*  30: 24 */   private static final ModelBook field_147072_E = new ModelBook();
/*  31: 25 */   private Random field_147074_F = new Random();
/*  32:    */   private ContainerEnchantment field_147075_G;
/*  33:    */   public int field_147073_u;
/*  34:    */   public float field_147071_v;
/*  35:    */   public float field_147069_w;
/*  36:    */   public float field_147082_x;
/*  37:    */   public float field_147081_y;
/*  38:    */   public float field_147080_z;
/*  39:    */   public float field_147076_A;
/*  40:    */   ItemStack field_147077_B;
/*  41:    */   private String field_147079_H;
/*  42:    */   private static final String __OBFID = "CL_00000757";
/*  43:    */   
/*  44:    */   public GuiEnchantment(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5, String par6Str)
/*  45:    */   {
/*  46: 40 */     super(new ContainerEnchantment(par1InventoryPlayer, par2World, par3, par4, par5));
/*  47: 41 */     this.field_147075_G = ((ContainerEnchantment)this.field_147002_h);
/*  48: 42 */     this.field_147079_H = par6Str;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void func_146979_b(int p_146979_1_, int p_146979_2_)
/*  52:    */   {
/*  53: 47 */     this.fontRendererObj.drawString(this.field_147079_H == null ? I18n.format("container.enchant", new Object[0]) : this.field_147079_H, 12, 5, 4210752);
/*  54: 48 */     this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void updateScreen()
/*  58:    */   {
/*  59: 56 */     super.updateScreen();
/*  60: 57 */     func_147068_g();
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void mouseClicked(int par1, int par2, int par3)
/*  64:    */   {
/*  65: 65 */     super.mouseClicked(par1, par2, par3);
/*  66: 66 */     int var4 = (width - this.field_146999_f) / 2;
/*  67: 67 */     int var5 = (height - this.field_147000_g) / 2;
/*  68: 69 */     for (int var6 = 0; var6 < 3; var6++)
/*  69:    */     {
/*  70: 71 */       int var7 = par1 - (var4 + 60);
/*  71: 72 */       int var8 = par2 - (var5 + 14 + 19 * var6);
/*  72: 74 */       if ((var7 >= 0) && (var8 >= 0) && (var7 < 108) && (var8 < 19) && (this.field_147075_G.enchantItem(this.mc.thePlayer, var6))) {
/*  73: 76 */         this.mc.playerController.sendEnchantPacket(this.field_147075_G.windowId, var6);
/*  74:    */       }
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_)
/*  79:    */   {
/*  80: 83 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  81: 84 */     this.mc.getTextureManager().bindTexture(field_147078_C);
/*  82: 85 */     int var4 = (width - this.field_146999_f) / 2;
/*  83: 86 */     int var5 = (height - this.field_147000_g) / 2;
/*  84: 87 */     drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
/*  85: 88 */     GL11.glPushMatrix();
/*  86: 89 */     GL11.glMatrixMode(5889);
/*  87: 90 */     GL11.glPushMatrix();
/*  88: 91 */     GL11.glLoadIdentity();
/*  89: 92 */     ScaledResolution var6 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
/*  90: 93 */     GL11.glViewport((var6.getScaledWidth() - 320) / 2 * var6.getScaleFactor(), (var6.getScaledHeight() - 240) / 2 * var6.getScaleFactor(), 320 * var6.getScaleFactor(), 240 * var6.getScaleFactor());
/*  91: 94 */     GL11.glTranslatef(-0.34F, 0.23F, 0.0F);
/*  92: 95 */     Project.gluPerspective(90.0F, 1.333333F, 9.0F, 80.0F);
/*  93: 96 */     float var7 = 1.0F;
/*  94: 97 */     GL11.glMatrixMode(5888);
/*  95: 98 */     GL11.glLoadIdentity();
/*  96: 99 */     RenderHelper.enableStandardItemLighting();
/*  97:100 */     GL11.glTranslatef(0.0F, 3.3F, -16.0F);
/*  98:101 */     GL11.glScalef(var7, var7, var7);
/*  99:102 */     float var8 = 5.0F;
/* 100:103 */     GL11.glScalef(var8, var8, var8);
/* 101:104 */     GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/* 102:105 */     this.mc.getTextureManager().bindTexture(field_147070_D);
/* 103:106 */     GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
/* 104:107 */     float var9 = this.field_147076_A + (this.field_147080_z - this.field_147076_A) * p_146976_1_;
/* 105:108 */     GL11.glTranslatef((1.0F - var9) * 0.2F, (1.0F - var9) * 0.1F, (1.0F - var9) * 0.25F);
/* 106:109 */     GL11.glRotatef(-(1.0F - var9) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
/* 107:110 */     GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
/* 108:111 */     float var10 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * p_146976_1_ + 0.25F;
/* 109:112 */     float var11 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * p_146976_1_ + 0.75F;
/* 110:113 */     var10 = (var10 - MathHelper.truncateDoubleToInt(var10)) * 1.6F - 0.3F;
/* 111:114 */     var11 = (var11 - MathHelper.truncateDoubleToInt(var11)) * 1.6F - 0.3F;
/* 112:116 */     if (var10 < 0.0F) {
/* 113:118 */       var10 = 0.0F;
/* 114:    */     }
/* 115:121 */     if (var11 < 0.0F) {
/* 116:123 */       var11 = 0.0F;
/* 117:    */     }
/* 118:126 */     if (var10 > 1.0F) {
/* 119:128 */       var10 = 1.0F;
/* 120:    */     }
/* 121:131 */     if (var11 > 1.0F) {
/* 122:133 */       var11 = 1.0F;
/* 123:    */     }
/* 124:136 */     GL11.glEnable(32826);
/* 125:137 */     field_147072_E.render(null, 0.0F, var10, var11, var9, 0.0F, 0.0625F);
/* 126:138 */     GL11.glDisable(32826);
/* 127:139 */     RenderHelper.disableStandardItemLighting();
/* 128:140 */     GL11.glMatrixMode(5889);
/* 129:141 */     GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 130:142 */     GL11.glPopMatrix();
/* 131:143 */     GL11.glMatrixMode(5888);
/* 132:144 */     GL11.glPopMatrix();
/* 133:145 */     RenderHelper.disableStandardItemLighting();
/* 134:146 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 135:147 */     EnchantmentNameParts.instance.reseedRandomGenerator(this.field_147075_G.nameSeed);
/* 136:149 */     for (int var12 = 0; var12 < 3; var12++)
/* 137:    */     {
/* 138:151 */       String var13 = EnchantmentNameParts.instance.generateNewRandomName();
/* 139:152 */       zLevel = 0.0F;
/* 140:153 */       this.mc.getTextureManager().bindTexture(field_147078_C);
/* 141:154 */       int var14 = this.field_147075_G.enchantLevels[var12];
/* 142:155 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 143:157 */       if (var14 == 0)
/* 144:    */       {
/* 145:159 */         drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 185, 108, 19);
/* 146:    */       }
/* 147:    */       else
/* 148:    */       {
/* 149:163 */         String var15 = var14;
/* 150:164 */         FontRenderer var16 = this.mc.standardGalacticFontRenderer;
/* 151:165 */         int var17 = 6839882;
/* 152:167 */         if ((this.mc.thePlayer.experienceLevel < var14) && (!this.mc.thePlayer.capabilities.isCreativeMode))
/* 153:    */         {
/* 154:169 */           drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 185, 108, 19);
/* 155:170 */           var16.drawSplitString(var13, var4 + 62, var5 + 16 + 19 * var12, 104, (var17 & 0xFEFEFE) >> 1);
/* 156:171 */           var16 = this.mc.fontRenderer;
/* 157:172 */           var17 = 4226832;
/* 158:173 */           var16.drawStringWithShadow(var15, var4 + 62 + 104 - var16.getStringWidth(var15), var5 + 16 + 19 * var12 + 7, var17);
/* 159:    */         }
/* 160:    */         else
/* 161:    */         {
/* 162:177 */           int var18 = p_146976_2_ - (var4 + 60);
/* 163:178 */           int var19 = p_146976_3_ - (var5 + 14 + 19 * var12);
/* 164:180 */           if ((var18 >= 0) && (var19 >= 0) && (var18 < 108) && (var19 < 19))
/* 165:    */           {
/* 166:182 */             drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 204, 108, 19);
/* 167:183 */             var17 = 16777088;
/* 168:    */           }
/* 169:    */           else
/* 170:    */           {
/* 171:187 */             drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 166, 108, 19);
/* 172:    */           }
/* 173:190 */           var16.drawSplitString(var13, var4 + 62, var5 + 16 + 19 * var12, 104, var17);
/* 174:191 */           var16 = this.mc.fontRenderer;
/* 175:192 */           var17 = 8453920;
/* 176:193 */           var16.drawStringWithShadow(var15, var4 + 62 + 104 - var16.getStringWidth(var15), var5 + 16 + 19 * var12 + 7, var17);
/* 177:    */         }
/* 178:    */       }
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void func_147068_g()
/* 183:    */   {
/* 184:201 */     ItemStack var1 = this.field_147002_h.getSlot(0).getStack();
/* 185:203 */     if (!ItemStack.areItemStacksEqual(var1, this.field_147077_B))
/* 186:    */     {
/* 187:205 */       this.field_147077_B = var1;
/* 188:    */       do
/* 189:    */       {
/* 190:209 */         this.field_147082_x += this.field_147074_F.nextInt(4) - this.field_147074_F.nextInt(4);
/* 191:211 */       } while ((this.field_147071_v <= this.field_147082_x + 1.0F) && (this.field_147071_v >= this.field_147082_x - 1.0F));
/* 192:    */     }
/* 193:214 */     this.field_147073_u += 1;
/* 194:215 */     this.field_147069_w = this.field_147071_v;
/* 195:216 */     this.field_147076_A = this.field_147080_z;
/* 196:217 */     boolean var2 = false;
/* 197:219 */     for (int var3 = 0; var3 < 3; var3++) {
/* 198:221 */       if (this.field_147075_G.enchantLevels[var3] != 0) {
/* 199:223 */         var2 = true;
/* 200:    */       }
/* 201:    */     }
/* 202:227 */     if (var2) {
/* 203:229 */       this.field_147080_z += 0.2F;
/* 204:    */     } else {
/* 205:233 */       this.field_147080_z -= 0.2F;
/* 206:    */     }
/* 207:236 */     if (this.field_147080_z < 0.0F) {
/* 208:238 */       this.field_147080_z = 0.0F;
/* 209:    */     }
/* 210:241 */     if (this.field_147080_z > 1.0F) {
/* 211:243 */       this.field_147080_z = 1.0F;
/* 212:    */     }
/* 213:246 */     float var5 = (this.field_147082_x - this.field_147071_v) * 0.4F;
/* 214:247 */     float var4 = 0.2F;
/* 215:249 */     if (var5 < -var4) {
/* 216:251 */       var5 = -var4;
/* 217:    */     }
/* 218:254 */     if (var5 > var4) {
/* 219:256 */       var5 = var4;
/* 220:    */     }
/* 221:259 */     this.field_147081_y += (var5 - this.field_147081_y) * 0.9F;
/* 222:260 */     this.field_147071_v += this.field_147081_y;
/* 223:    */   }
/* 224:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiEnchantment
 * JD-Core Version:    0.7.0.1
 */