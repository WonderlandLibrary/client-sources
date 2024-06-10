/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.InputStreamReader;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Random;
/*   8:    */ import net.minecraft.client.Minecraft;
/*   9:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  10:    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*  11:    */ import net.minecraft.client.renderer.Tessellator;
/*  12:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  13:    */ import net.minecraft.client.resources.IResource;
/*  14:    */ import net.minecraft.client.resources.IResourceManager;
/*  15:    */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*  16:    */ import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
/*  17:    */ import net.minecraft.util.EnumChatFormatting;
/*  18:    */ import net.minecraft.util.ResourceLocation;
/*  19:    */ import net.minecraft.util.Session;
/*  20:    */ import org.apache.commons.io.Charsets;
/*  21:    */ import org.apache.logging.log4j.LogManager;
/*  22:    */ import org.apache.logging.log4j.Logger;
/*  23:    */ import org.lwjgl.opengl.GL11;
/*  24:    */ 
/*  25:    */ public class GuiWinGame
/*  26:    */   extends GuiScreen
/*  27:    */ {
/*  28: 19 */   private static final Logger logger = ;
/*  29: 20 */   private static final ResourceLocation field_146576_f = new ResourceLocation("textures/gui/title/minecraft.png");
/*  30: 21 */   private static final ResourceLocation field_146577_g = new ResourceLocation("textures/misc/vignette.png");
/*  31:    */   private int field_146581_h;
/*  32:    */   private List field_146582_i;
/*  33:    */   private int field_146579_r;
/*  34: 25 */   private float field_146578_s = 0.5F;
/*  35:    */   private static final String __OBFID = "CL_00000719";
/*  36:    */   
/*  37:    */   public void updateScreen()
/*  38:    */   {
/*  39: 33 */     this.field_146581_h += 1;
/*  40: 34 */     float var1 = (this.field_146579_r + height + height + 24) / this.field_146578_s;
/*  41: 36 */     if (this.field_146581_h > var1) {
/*  42: 38 */       func_146574_g();
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected void keyTyped(char par1, int par2)
/*  47:    */   {
/*  48: 47 */     if (par2 == 1) {
/*  49: 49 */       func_146574_g();
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   private void func_146574_g()
/*  54:    */   {
/*  55: 55 */     this.mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
/*  56: 56 */     this.mc.displayGuiScreen(null);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean doesGuiPauseGame()
/*  60:    */   {
/*  61: 64 */     return true;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void initGui()
/*  65:    */   {
/*  66: 72 */     if (this.field_146582_i == null)
/*  67:    */     {
/*  68: 74 */       this.field_146582_i = new ArrayList();
/*  69:    */       try
/*  70:    */       {
/*  71: 78 */         String var1 = "";
/*  72: 79 */         String var2 = EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + EnumChatFormatting.GREEN + EnumChatFormatting.AQUA;
/*  73: 80 */         short var3 = 274;
/*  74: 81 */         BufferedReader var4 = new BufferedReader(new InputStreamReader(this.mc.getResourceManager().getResource(new ResourceLocation("texts/end.txt")).getInputStream(), Charsets.UTF_8));
/*  75: 82 */         Random var5 = new Random(8124371L);
/*  76: 85 */         while ((var1 = var4.readLine()) != null)
/*  77:    */         {
/*  78:    */           String var7;
/*  79:    */           String var8;
/*  80: 90 */           for (var1 = var1.replaceAll("PLAYERNAME", this.mc.getSession().getUsername()); var1.contains(var2); var1 = var7 + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, var5.nextInt(4) + 3) + var8)
/*  81:    */           {
/*  82: 92 */             int var6 = var1.indexOf(var2);
/*  83: 93 */             var7 = var1.substring(0, var6);
/*  84: 94 */             var8 = var1.substring(var6 + var2.length());
/*  85:    */           }
/*  86: 97 */           this.field_146582_i.addAll(this.mc.fontRenderer.listFormattedStringToWidth(var1, var3));
/*  87: 98 */           this.field_146582_i.add("");
/*  88:    */         }
/*  89:101 */         for (int var6 = 0; var6 < 8; var6++) {
/*  90:103 */           this.field_146582_i.add("");
/*  91:    */         }
/*  92:106 */         var4 = new BufferedReader(new InputStreamReader(this.mc.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream(), Charsets.UTF_8));
/*  93:108 */         while ((var1 = var4.readLine()) != null)
/*  94:    */         {
/*  95:110 */           var1 = var1.replaceAll("PLAYERNAME", this.mc.getSession().getUsername());
/*  96:111 */           var1 = var1.replaceAll("\t", "    ");
/*  97:112 */           this.field_146582_i.addAll(this.mc.fontRenderer.listFormattedStringToWidth(var1, var3));
/*  98:113 */           this.field_146582_i.add("");
/*  99:    */         }
/* 100:116 */         this.field_146579_r = (this.field_146582_i.size() * 12);
/* 101:    */       }
/* 102:    */       catch (Exception var9)
/* 103:    */       {
/* 104:120 */         logger.error("Couldn't load credits", var9);
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   private void func_146575_b(int p_146575_1_, int p_146575_2_, float p_146575_3_)
/* 110:    */   {
/* 111:127 */     Tessellator var4 = Tessellator.instance;
/* 112:128 */     this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
/* 113:129 */     var4.startDrawingQuads();
/* 114:130 */     var4.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
/* 115:131 */     int var5 = width;
/* 116:132 */     float var6 = 0.0F - (this.field_146581_h + p_146575_3_) * 0.5F * this.field_146578_s;
/* 117:133 */     float var7 = height - (this.field_146581_h + p_146575_3_) * 0.5F * this.field_146578_s;
/* 118:134 */     float var8 = 0.015625F;
/* 119:135 */     float var9 = (this.field_146581_h + p_146575_3_ - 0.0F) * 0.02F;
/* 120:136 */     float var10 = (this.field_146579_r + height + height + 24) / this.field_146578_s;
/* 121:137 */     float var11 = (var10 - 20.0F - (this.field_146581_h + p_146575_3_)) * 0.005F;
/* 122:139 */     if (var11 < var9) {
/* 123:141 */       var9 = var11;
/* 124:    */     }
/* 125:144 */     if (var9 > 1.0F) {
/* 126:146 */       var9 = 1.0F;
/* 127:    */     }
/* 128:149 */     var9 *= var9;
/* 129:150 */     var9 = var9 * 96.0F / 255.0F;
/* 130:151 */     var4.setColorOpaque_F(var9, var9, var9);
/* 131:152 */     var4.addVertexWithUV(0.0D, height, zLevel, 0.0D, var6 * var8);
/* 132:153 */     var4.addVertexWithUV(var5, height, zLevel, var5 * var8, var6 * var8);
/* 133:154 */     var4.addVertexWithUV(var5, 0.0D, zLevel, var5 * var8, var7 * var8);
/* 134:155 */     var4.addVertexWithUV(0.0D, 0.0D, zLevel, 0.0D, var7 * var8);
/* 135:156 */     var4.draw();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void drawScreen(int par1, int par2, float par3)
/* 139:    */   {
/* 140:164 */     func_146575_b(par1, par2, par3);
/* 141:165 */     Tessellator var4 = Tessellator.instance;
/* 142:166 */     short var5 = 274;
/* 143:167 */     int var6 = width / 2 - var5 / 2;
/* 144:168 */     int var7 = height + 50;
/* 145:169 */     float var8 = -(this.field_146581_h + par3) * this.field_146578_s;
/* 146:170 */     GL11.glPushMatrix();
/* 147:171 */     GL11.glTranslatef(0.0F, var8, 0.0F);
/* 148:172 */     this.mc.getTextureManager().bindTexture(field_146576_f);
/* 149:173 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 150:174 */     drawTexturedModalRect(var6, var7, 0, 0, 155, 44);
/* 151:175 */     drawTexturedModalRect(var6 + 155, var7, 0, 45, 155, 44);
/* 152:176 */     var4.setColorOpaque_I(16777215);
/* 153:177 */     int var9 = var7 + 200;
/* 154:180 */     for (int var10 = 0; var10 < this.field_146582_i.size(); var10++)
/* 155:    */     {
/* 156:182 */       if (var10 == this.field_146582_i.size() - 1)
/* 157:    */       {
/* 158:184 */         float var11 = var9 + var8 - (height / 2 - 6);
/* 159:186 */         if (var11 < 0.0F) {
/* 160:188 */           GL11.glTranslatef(0.0F, -var11, 0.0F);
/* 161:    */         }
/* 162:    */       }
/* 163:192 */       if ((var9 + var8 + 12.0F + 8.0F > 0.0F) && (var9 + var8 < height))
/* 164:    */       {
/* 165:194 */         String var12 = (String)this.field_146582_i.get(var10);
/* 166:196 */         if (var12.startsWith("[C]"))
/* 167:    */         {
/* 168:198 */           this.fontRendererObj.drawStringWithShadow(var12.substring(3), var6 + (var5 - this.fontRendererObj.getStringWidth(var12.substring(3))) / 2, var9, 16777215);
/* 169:    */         }
/* 170:    */         else
/* 171:    */         {
/* 172:202 */           this.fontRendererObj.fontRandom.setSeed(var10 * 4238972211L + this.field_146581_h / 4);
/* 173:203 */           this.fontRendererObj.drawStringWithShadow(var12, var6, var9, 16777215);
/* 174:    */         }
/* 175:    */       }
/* 176:207 */       var9 += 12;
/* 177:    */     }
/* 178:210 */     GL11.glPopMatrix();
/* 179:211 */     this.mc.getTextureManager().bindTexture(field_146577_g);
/* 180:212 */     GL11.glEnable(3042);
/* 181:213 */     GL11.glBlendFunc(0, 769);
/* 182:214 */     var4.startDrawingQuads();
/* 183:215 */     var4.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
/* 184:216 */     var10 = width;
/* 185:217 */     int var13 = height;
/* 186:218 */     var4.addVertexWithUV(0.0D, var13, zLevel, 0.0D, 1.0D);
/* 187:219 */     var4.addVertexWithUV(var10, var13, zLevel, 1.0D, 1.0D);
/* 188:220 */     var4.addVertexWithUV(var10, 0.0D, zLevel, 1.0D, 0.0D);
/* 189:221 */     var4.addVertexWithUV(0.0D, 0.0D, zLevel, 0.0D, 0.0D);
/* 190:222 */     var4.draw();
/* 191:223 */     GL11.glDisable(3042);
/* 192:224 */     super.drawScreen(par1, par2, par3);
/* 193:    */   }
/* 194:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiWinGame
 * JD-Core Version:    0.7.0.1
 */