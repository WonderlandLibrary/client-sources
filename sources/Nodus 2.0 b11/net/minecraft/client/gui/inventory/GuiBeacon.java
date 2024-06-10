/*   1:    */ package net.minecraft.client.gui.inventory;
/*   2:    */ 
/*   3:    */ import io.netty.buffer.ByteBuf;
/*   4:    */ import io.netty.buffer.Unpooled;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   8:    */ import net.minecraft.client.Minecraft;
/*   9:    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*  10:    */ import net.minecraft.client.renderer.RenderHelper;
/*  11:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*  12:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  13:    */ import net.minecraft.client.resources.I18n;
/*  14:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  15:    */ import net.minecraft.init.Items;
/*  16:    */ import net.minecraft.inventory.ContainerBeacon;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*  19:    */ import net.minecraft.potion.Potion;
/*  20:    */ import net.minecraft.tileentity.TileEntityBeacon;
/*  21:    */ import net.minecraft.util.ResourceLocation;
/*  22:    */ import org.apache.logging.log4j.LogManager;
/*  23:    */ import org.apache.logging.log4j.Logger;
/*  24:    */ import org.lwjgl.opengl.GL11;
/*  25:    */ 
/*  26:    */ public class GuiBeacon
/*  27:    */   extends GuiContainer
/*  28:    */ {
/*  29: 28 */   private static final Logger logger = ;
/*  30: 29 */   private static final ResourceLocation field_147025_v = new ResourceLocation("textures/gui/container/beacon.png");
/*  31:    */   private TileEntityBeacon field_147024_w;
/*  32:    */   private ConfirmButton field_147028_x;
/*  33:    */   private boolean field_147027_y;
/*  34:    */   private static final String __OBFID = "CL_00000739";
/*  35:    */   
/*  36:    */   public GuiBeacon(InventoryPlayer par1InventoryPlayer, TileEntityBeacon par2TileEntityBeacon)
/*  37:    */   {
/*  38: 37 */     super(new ContainerBeacon(par1InventoryPlayer, par2TileEntityBeacon));
/*  39: 38 */     this.field_147024_w = par2TileEntityBeacon;
/*  40: 39 */     this.field_146999_f = 230;
/*  41: 40 */     this.field_147000_g = 219;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void initGui()
/*  45:    */   {
/*  46: 48 */     super.initGui();
/*  47: 49 */     this.buttonList.add(this.field_147028_x = new ConfirmButton(-1, this.field_147003_i + 164, this.field_147009_r + 107));
/*  48: 50 */     this.buttonList.add(new CancelButton(-2, this.field_147003_i + 190, this.field_147009_r + 107));
/*  49: 51 */     this.field_147027_y = true;
/*  50: 52 */     this.field_147028_x.enabled = false;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void updateScreen()
/*  54:    */   {
/*  55: 60 */     super.updateScreen();
/*  56: 62 */     if ((this.field_147027_y) && (this.field_147024_w.func_145998_l() >= 0))
/*  57:    */     {
/*  58: 64 */       this.field_147027_y = false;
/*  59: 71 */       for (int var1 = 0; var1 <= 2; var1++)
/*  60:    */       {
/*  61: 73 */         int var2 = TileEntityBeacon.field_146009_a[var1].length;
/*  62: 74 */         int var3 = var2 * 22 + (var2 - 1) * 2;
/*  63: 76 */         for (int var4 = 0; var4 < var2; var4++)
/*  64:    */         {
/*  65: 78 */           int var5 = TileEntityBeacon.field_146009_a[var1][var4].id;
/*  66: 79 */           PowerButton var6 = new PowerButton(var1 << 8 | var5, this.field_147003_i + 76 + var4 * 24 - var3 / 2, this.field_147009_r + 22 + var1 * 25, var5, var1);
/*  67: 80 */           this.buttonList.add(var6);
/*  68: 82 */           if (var1 >= this.field_147024_w.func_145998_l()) {
/*  69: 84 */             var6.enabled = false;
/*  70: 86 */           } else if (var5 == this.field_147024_w.func_146007_j()) {
/*  71: 88 */             var6.func_146140_b(true);
/*  72:    */           }
/*  73:    */         }
/*  74:    */       }
/*  75: 93 */       byte var7 = 3;
/*  76: 94 */       int var2 = TileEntityBeacon.field_146009_a[var7].length + 1;
/*  77: 95 */       int var3 = var2 * 22 + (var2 - 1) * 2;
/*  78: 97 */       for (int var4 = 0; var4 < var2 - 1; var4++)
/*  79:    */       {
/*  80: 99 */         int var5 = TileEntityBeacon.field_146009_a[var7][var4].id;
/*  81:100 */         PowerButton var6 = new PowerButton(var7 << 8 | var5, this.field_147003_i + 167 + var4 * 24 - var3 / 2, this.field_147009_r + 47, var5, var7);
/*  82:101 */         this.buttonList.add(var6);
/*  83:103 */         if (var7 >= this.field_147024_w.func_145998_l()) {
/*  84:105 */           var6.enabled = false;
/*  85:107 */         } else if (var5 == this.field_147024_w.func_146006_k()) {
/*  86:109 */           var6.func_146140_b(true);
/*  87:    */         }
/*  88:    */       }
/*  89:113 */       if (this.field_147024_w.func_146007_j() > 0)
/*  90:    */       {
/*  91:115 */         PowerButton var8 = new PowerButton(var7 << 8 | this.field_147024_w.func_146007_j(), this.field_147003_i + 167 + (var2 - 1) * 24 - var3 / 2, this.field_147009_r + 47, this.field_147024_w.func_146007_j(), var7);
/*  92:116 */         this.buttonList.add(var8);
/*  93:118 */         if (var7 >= this.field_147024_w.func_145998_l()) {
/*  94:120 */           var8.enabled = false;
/*  95:122 */         } else if (this.field_147024_w.func_146007_j() == this.field_147024_w.func_146006_k()) {
/*  96:124 */           var8.func_146140_b(true);
/*  97:    */         }
/*  98:    */       }
/*  99:    */     }
/* 100:129 */     this.field_147028_x.enabled = ((this.field_147024_w.getStackInSlot(0) != null) && (this.field_147024_w.func_146007_j() > 0));
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 104:    */   {
/* 105:134 */     if (p_146284_1_.id == -2)
/* 106:    */     {
/* 107:136 */       this.mc.displayGuiScreen(null);
/* 108:    */     }
/* 109:138 */     else if (p_146284_1_.id == -1)
/* 110:    */     {
/* 111:140 */       String var2 = "MC|Beacon";
/* 112:141 */       ByteBuf var3 = Unpooled.buffer();
/* 113:    */       try
/* 114:    */       {
/* 115:145 */         var3.writeInt(this.field_147024_w.func_146007_j());
/* 116:146 */         var3.writeInt(this.field_147024_w.func_146006_k());
/* 117:147 */         this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(var2, var3));
/* 118:    */       }
/* 119:    */       catch (Exception var8)
/* 120:    */       {
/* 121:151 */         logger.error("Couldn't send beacon info", var8);
/* 122:    */       }
/* 123:    */       finally
/* 124:    */       {
/* 125:155 */         var3.release();
/* 126:    */       }
/* 127:158 */       this.mc.displayGuiScreen(null);
/* 128:    */     }
/* 129:160 */     else if ((p_146284_1_ instanceof PowerButton))
/* 130:    */     {
/* 131:162 */       if (((PowerButton)p_146284_1_).func_146141_c()) {
/* 132:164 */         return;
/* 133:    */       }
/* 134:167 */       int var10 = p_146284_1_.id;
/* 135:168 */       int var11 = var10 & 0xFF;
/* 136:169 */       int var4 = var10 >> 8;
/* 137:171 */       if (var4 < 3) {
/* 138:173 */         this.field_147024_w.func_146001_d(var11);
/* 139:    */       } else {
/* 140:177 */         this.field_147024_w.func_146004_e(var11);
/* 141:    */       }
/* 142:180 */       this.buttonList.clear();
/* 143:181 */       initGui();
/* 144:182 */       updateScreen();
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   protected void func_146979_b(int p_146979_1_, int p_146979_2_)
/* 149:    */   {
/* 150:188 */     RenderHelper.disableStandardItemLighting();
/* 151:189 */     drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.primary", new Object[0]), 62, 10, 14737632);
/* 152:190 */     drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.secondary", new Object[0]), 169, 10, 14737632);
/* 153:191 */     Iterator var3 = this.buttonList.iterator();
/* 154:193 */     while (var3.hasNext())
/* 155:    */     {
/* 156:195 */       NodusGuiButton var4 = (NodusGuiButton)var3.next();
/* 157:197 */       if (var4.func_146115_a())
/* 158:    */       {
/* 159:199 */         var4.func_146111_b(p_146979_1_ - this.field_147003_i, p_146979_2_ - this.field_147009_r);
/* 160:200 */         break;
/* 161:    */       }
/* 162:    */     }
/* 163:204 */     RenderHelper.enableGUIStandardItemLighting();
/* 164:    */   }
/* 165:    */   
/* 166:    */   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_)
/* 167:    */   {
/* 168:209 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 169:210 */     this.mc.getTextureManager().bindTexture(field_147025_v);
/* 170:211 */     int var4 = (width - this.field_146999_f) / 2;
/* 171:212 */     int var5 = (height - this.field_147000_g) / 2;
/* 172:213 */     drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
/* 173:214 */     itemRender.zLevel = 100.0F;
/* 174:215 */     itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), new ItemStack(Items.emerald), var4 + 42, var5 + 109);
/* 175:216 */     itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), new ItemStack(Items.diamond), var4 + 42 + 22, var5 + 109);
/* 176:217 */     itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), new ItemStack(Items.gold_ingot), var4 + 42 + 44, var5 + 109);
/* 177:218 */     itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), new ItemStack(Items.iron_ingot), var4 + 42 + 66, var5 + 109);
/* 178:219 */     itemRender.zLevel = 0.0F;
/* 179:    */   }
/* 180:    */   
/* 181:    */   static class Button
/* 182:    */     extends NodusGuiButton
/* 183:    */   {
/* 184:    */     private final ResourceLocation field_146145_o;
/* 185:    */     private final int field_146144_p;
/* 186:    */     private final int field_146143_q;
/* 187:    */     private boolean field_146142_r;
/* 188:    */     private static final String __OBFID = "CL_00000743";
/* 189:    */     
/* 190:    */     protected Button(int par1, int par2, int par3, ResourceLocation par4ResourceLocation, int par5, int par6)
/* 191:    */     {
/* 192:232 */       super(par2, par3, 22, 22, "");
/* 193:233 */       this.field_146145_o = par4ResourceLocation;
/* 194:234 */       this.field_146144_p = par5;
/* 195:235 */       this.field_146143_q = par6;
/* 196:    */     }
/* 197:    */     
/* 198:    */     public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
/* 199:    */     {
/* 200:240 */       if (this.field_146125_m)
/* 201:    */       {
/* 202:242 */         p_146112_1_.getTextureManager().bindTexture(GuiBeacon.field_147025_v);
/* 203:243 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 204:244 */         this.field_146123_n = ((p_146112_2_ >= this.xPosition) && (p_146112_3_ >= this.yPosition) && (p_146112_2_ < this.xPosition + this.width) && (p_146112_3_ < this.yPosition + this.height));
/* 205:245 */         short var4 = 219;
/* 206:246 */         int var5 = 0;
/* 207:248 */         if (!this.enabled) {
/* 208:250 */           var5 += this.width * 2;
/* 209:252 */         } else if (this.field_146142_r) {
/* 210:254 */           var5 += this.width * 1;
/* 211:256 */         } else if (this.field_146123_n) {
/* 212:258 */           var5 += this.width * 3;
/* 213:    */         }
/* 214:261 */         drawTexturedModalRect(this.xPosition, this.yPosition, var5, var4, this.width, this.height);
/* 215:263 */         if (!GuiBeacon.field_147025_v.equals(this.field_146145_o)) {
/* 216:265 */           p_146112_1_.getTextureManager().bindTexture(this.field_146145_o);
/* 217:    */         }
/* 218:268 */         drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.field_146144_p, this.field_146143_q, 18, 18);
/* 219:    */       }
/* 220:    */     }
/* 221:    */     
/* 222:    */     public boolean func_146141_c()
/* 223:    */     {
/* 224:274 */       return this.field_146142_r;
/* 225:    */     }
/* 226:    */     
/* 227:    */     public void func_146140_b(boolean p_146140_1_)
/* 228:    */     {
/* 229:279 */       this.field_146142_r = p_146140_1_;
/* 230:    */     }
/* 231:    */   }
/* 232:    */   
/* 233:    */   class CancelButton
/* 234:    */     extends GuiBeacon.Button
/* 235:    */   {
/* 236:    */     private static final String __OBFID = "CL_00000740";
/* 237:    */     
/* 238:    */     public CancelButton(int par2, int par3, int par4)
/* 239:    */     {
/* 240:289 */       super(par3, par4, GuiBeacon.field_147025_v, 112, 220);
/* 241:    */     }
/* 242:    */     
/* 243:    */     public void func_146111_b(int p_146111_1_, int p_146111_2_)
/* 244:    */     {
/* 245:294 */       GuiBeacon.this.func_146279_a(I18n.format("gui.cancel", new Object[0]), p_146111_1_, p_146111_2_);
/* 246:    */     }
/* 247:    */   }
/* 248:    */   
/* 249:    */   class PowerButton
/* 250:    */     extends GuiBeacon.Button
/* 251:    */   {
/* 252:    */     private final int field_146149_p;
/* 253:    */     private final int field_146148_q;
/* 254:    */     private static final String __OBFID = "CL_00000742";
/* 255:    */     
/* 256:    */     public PowerButton(int par2, int par3, int par4, int par5, int par6)
/* 257:    */     {
/* 258:306 */       super(par3, par4, GuiContainer.field_147001_a, 0 + Potion.potionTypes[par5].getStatusIconIndex() % 8 * 18, 198 + Potion.potionTypes[par5].getStatusIconIndex() / 8 * 18);
/* 259:307 */       this.field_146149_p = par5;
/* 260:308 */       this.field_146148_q = par6;
/* 261:    */     }
/* 262:    */     
/* 263:    */     public void func_146111_b(int p_146111_1_, int p_146111_2_)
/* 264:    */     {
/* 265:313 */       String var3 = I18n.format(Potion.potionTypes[this.field_146149_p].getName(), new Object[0]);
/* 266:315 */       if ((this.field_146148_q >= 3) && (this.field_146149_p != Potion.regeneration.id)) {
/* 267:317 */         var3 = var3 + " II";
/* 268:    */       }
/* 269:320 */       GuiBeacon.this.func_146279_a(var3, p_146111_1_, p_146111_2_);
/* 270:    */     }
/* 271:    */   }
/* 272:    */   
/* 273:    */   class ConfirmButton
/* 274:    */     extends GuiBeacon.Button
/* 275:    */   {
/* 276:    */     private static final String __OBFID = "CL_00000741";
/* 277:    */     
/* 278:    */     public ConfirmButton(int par2, int par3, int par4)
/* 279:    */     {
/* 280:330 */       super(par3, par4, GuiBeacon.field_147025_v, 90, 220);
/* 281:    */     }
/* 282:    */     
/* 283:    */     public void func_146111_b(int p_146111_1_, int p_146111_2_)
/* 284:    */     {
/* 285:335 */       GuiBeacon.this.func_146279_a(I18n.format("gui.done", new Object[0]), p_146111_1_, p_146111_2_);
/* 286:    */     }
/* 287:    */   }
/* 288:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.inventory.GuiBeacon
 * JD-Core Version:    0.7.0.1
 */