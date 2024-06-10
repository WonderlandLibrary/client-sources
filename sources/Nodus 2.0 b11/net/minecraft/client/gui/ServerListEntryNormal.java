/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Charsets;
/*   4:    */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*   5:    */ import io.netty.buffer.ByteBuf;
/*   6:    */ import io.netty.buffer.ByteBufInputStream;
/*   7:    */ import io.netty.buffer.Unpooled;
/*   8:    */ import io.netty.handler.codec.base64.Base64;
/*   9:    */ import java.awt.image.BufferedImage;
/*  10:    */ import java.net.UnknownHostException;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*  13:    */ import java.util.concurrent.ThreadPoolExecutor;
/*  14:    */ import javax.imageio.ImageIO;
/*  15:    */ import net.minecraft.client.Minecraft;
/*  16:    */ import net.minecraft.client.multiplayer.ServerData;
/*  17:    */ import net.minecraft.client.multiplayer.ServerList;
/*  18:    */ import net.minecraft.client.network.OldServerPinger;
/*  19:    */ import net.minecraft.client.renderer.Tessellator;
/*  20:    */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*  21:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  22:    */ import net.minecraft.util.EnumChatFormatting;
/*  23:    */ import net.minecraft.util.ResourceLocation;
/*  24:    */ import org.apache.commons.lang3.Validate;
/*  25:    */ import org.apache.logging.log4j.LogManager;
/*  26:    */ import org.apache.logging.log4j.Logger;
/*  27:    */ import org.lwjgl.opengl.GL11;
/*  28:    */ 
/*  29:    */ public class ServerListEntryNormal
/*  30:    */   implements GuiListExtended.IGuiListEntry
/*  31:    */ {
/*  32: 28 */   private static final Logger logger = ;
/*  33: 29 */   private static final ThreadPoolExecutor field_148302_b = new ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).build());
/*  34:    */   private final GuiMultiplayer field_148303_c;
/*  35:    */   private final Minecraft field_148300_d;
/*  36:    */   private final ServerData field_148301_e;
/*  37:    */   private long field_148298_f;
/*  38:    */   private String field_148299_g;
/*  39:    */   private DynamicTexture field_148305_h;
/*  40:    */   private ResourceLocation field_148306_i;
/*  41:    */   private static final String __OBFID = "CL_00000817";
/*  42:    */   
/*  43:    */   protected ServerListEntryNormal(GuiMultiplayer p_i45048_1_, ServerData p_i45048_2_)
/*  44:    */   {
/*  45: 41 */     this.field_148303_c = p_i45048_1_;
/*  46: 42 */     this.field_148301_e = p_i45048_2_;
/*  47: 43 */     this.field_148300_d = Minecraft.getMinecraft();
/*  48: 44 */     this.field_148306_i = new ResourceLocation("servers/" + p_i45048_2_.serverIP + "/icon");
/*  49: 45 */     this.field_148305_h = ((DynamicTexture)this.field_148300_d.getTextureManager().getTexture(this.field_148306_i));
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void func_148279_a(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_)
/*  53:    */   {
/*  54: 50 */     if (!this.field_148301_e.field_78841_f)
/*  55:    */     {
/*  56: 52 */       this.field_148301_e.field_78841_f = true;
/*  57: 53 */       this.field_148301_e.pingToServer = -2L;
/*  58: 54 */       this.field_148301_e.serverMOTD = "";
/*  59: 55 */       this.field_148301_e.populationInfo = "";
/*  60: 56 */       field_148302_b.submit(new Runnable()
/*  61:    */       {
/*  62:    */         private static final String __OBFID = "CL_00000818";
/*  63:    */         
/*  64:    */         public void run()
/*  65:    */         {
/*  66:    */           try
/*  67:    */           {
/*  68: 63 */             ServerListEntryNormal.this.field_148303_c.func_146789_i().func_147224_a(ServerListEntryNormal.this.field_148301_e);
/*  69:    */           }
/*  70:    */           catch (UnknownHostException var2)
/*  71:    */           {
/*  72: 67 */             ServerListEntryNormal.this.field_148301_e.pingToServer = -1L;
/*  73: 68 */             ServerListEntryNormal.this.field_148301_e.serverMOTD = (EnumChatFormatting.DARK_RED + "Can't resolve hostname");
/*  74:    */           }
/*  75:    */           catch (Exception var3)
/*  76:    */           {
/*  77: 72 */             ServerListEntryNormal.this.field_148301_e.pingToServer = -1L;
/*  78: 73 */             ServerListEntryNormal.this.field_148301_e.serverMOTD = (EnumChatFormatting.DARK_RED + "Can't connect to server.");
/*  79:    */           }
/*  80:    */         }
/*  81:    */       });
/*  82:    */     }
/*  83: 79 */     boolean var10 = this.field_148301_e.field_82821_f > 4;
/*  84: 80 */     boolean var11 = this.field_148301_e.field_82821_f < 4;
/*  85: 81 */     boolean var12 = (var10) || (var11);
/*  86: 82 */     this.field_148300_d.fontRenderer.drawString(this.field_148301_e.serverName, p_148279_2_ + 32 + 3, p_148279_3_ + 1, 16777215);
/*  87: 83 */     List var13 = this.field_148300_d.fontRenderer.listFormattedStringToWidth(this.field_148301_e.serverMOTD, p_148279_4_ - 32 - 2);
/*  88: 85 */     for (int var14 = 0; var14 < Math.min(var13.size(), 2); var14++) {
/*  89: 87 */       this.field_148300_d.fontRenderer.drawString((String)var13.get(var14), p_148279_2_ + 32 + 3, p_148279_3_ + 12 + this.field_148300_d.fontRenderer.FONT_HEIGHT * var14, 8421504);
/*  90:    */     }
/*  91: 90 */     String var22 = var12 ? EnumChatFormatting.DARK_RED + this.field_148301_e.gameVersion : this.field_148301_e.populationInfo;
/*  92: 91 */     int var15 = this.field_148300_d.fontRenderer.getStringWidth(var22);
/*  93: 92 */     this.field_148300_d.fontRenderer.drawString(var22, p_148279_2_ + p_148279_4_ - var15 - 15 - 2, p_148279_3_ + 1, 8421504);
/*  94: 93 */     byte var16 = 0;
/*  95: 94 */     String var18 = null;
/*  96:    */     int var17;
/*  97:    */     String var19;
/*  98: 98 */     if (var12)
/*  99:    */     {
/* 100:100 */       int var17 = 5;
/* 101:101 */       String var19 = var10 ? "Client out of date!" : "Server out of date!";
/* 102:102 */       var18 = this.field_148301_e.field_147412_i;
/* 103:    */     }
/* 104:104 */     else if ((this.field_148301_e.field_78841_f) && (this.field_148301_e.pingToServer != -2L))
/* 105:    */     {
/* 106:    */       int var17;
/* 107:    */       int var17;
/* 108:106 */       if (this.field_148301_e.pingToServer < 0L)
/* 109:    */       {
/* 110:108 */         var17 = 5;
/* 111:    */       }
/* 112:    */       else
/* 113:    */       {
/* 114:    */         int var17;
/* 115:110 */         if (this.field_148301_e.pingToServer < 150L)
/* 116:    */         {
/* 117:112 */           var17 = 0;
/* 118:    */         }
/* 119:    */         else
/* 120:    */         {
/* 121:    */           int var17;
/* 122:114 */           if (this.field_148301_e.pingToServer < 300L)
/* 123:    */           {
/* 124:116 */             var17 = 1;
/* 125:    */           }
/* 126:    */           else
/* 127:    */           {
/* 128:    */             int var17;
/* 129:118 */             if (this.field_148301_e.pingToServer < 600L)
/* 130:    */             {
/* 131:120 */               var17 = 2;
/* 132:    */             }
/* 133:    */             else
/* 134:    */             {
/* 135:    */               int var17;
/* 136:122 */               if (this.field_148301_e.pingToServer < 1000L) {
/* 137:124 */                 var17 = 3;
/* 138:    */               } else {
/* 139:128 */                 var17 = 4;
/* 140:    */               }
/* 141:    */             }
/* 142:    */           }
/* 143:    */         }
/* 144:    */       }
/* 145:    */       String var19;
/* 146:131 */       if (this.field_148301_e.pingToServer < 0L)
/* 147:    */       {
/* 148:133 */         var19 = "(no connection)";
/* 149:    */       }
/* 150:    */       else
/* 151:    */       {
/* 152:137 */         String var19 = this.field_148301_e.pingToServer + "ms";
/* 153:138 */         var18 = this.field_148301_e.field_147412_i;
/* 154:    */       }
/* 155:    */     }
/* 156:    */     else
/* 157:    */     {
/* 158:143 */       var16 = 1;
/* 159:144 */       var17 = (int)(Minecraft.getSystemTime() / 100L + p_148279_1_ * 2 & 0x7);
/* 160:146 */       if (var17 > 4) {
/* 161:148 */         var17 = 8 - var17;
/* 162:    */       }
/* 163:151 */       var19 = "Pinging...";
/* 164:    */     }
/* 165:154 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 166:155 */     this.field_148300_d.getTextureManager().bindTexture(Gui.icons);
/* 167:156 */     Gui.func_146110_a(p_148279_2_ + p_148279_4_ - 15, p_148279_3_, var16 * 10, 176 + var17 * 8, 10, 8, 256.0F, 256.0F);
/* 168:158 */     if ((this.field_148301_e.func_147409_e() != null) && (!this.field_148301_e.func_147409_e().equals(this.field_148299_g)))
/* 169:    */     {
/* 170:160 */       this.field_148299_g = this.field_148301_e.func_147409_e();
/* 171:161 */       func_148297_b();
/* 172:162 */       this.field_148303_c.func_146795_p().saveServerList();
/* 173:    */     }
/* 174:165 */     if (this.field_148305_h != null)
/* 175:    */     {
/* 176:167 */       this.field_148300_d.getTextureManager().bindTexture(this.field_148306_i);
/* 177:168 */       Gui.func_146110_a(p_148279_2_, p_148279_3_, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
/* 178:    */     }
/* 179:171 */     int var20 = p_148279_7_ - p_148279_2_;
/* 180:172 */     int var21 = p_148279_8_ - p_148279_3_;
/* 181:174 */     if ((var20 >= p_148279_4_ - 15) && (var20 <= p_148279_4_ - 5) && (var21 >= 0) && (var21 <= 8)) {
/* 182:176 */       this.field_148303_c.func_146793_a(var19);
/* 183:178 */     } else if ((var20 >= p_148279_4_ - var15 - 15 - 2) && (var20 <= p_148279_4_ - 15 - 2) && (var21 >= 0) && (var21 <= 8)) {
/* 184:180 */       this.field_148303_c.func_146793_a(var18);
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   private void func_148297_b()
/* 189:    */   {
/* 190:186 */     if (this.field_148301_e.func_147409_e() == null)
/* 191:    */     {
/* 192:188 */       this.field_148300_d.getTextureManager().func_147645_c(this.field_148306_i);
/* 193:189 */       this.field_148305_h = null;
/* 194:    */     }
/* 195:    */     else
/* 196:    */     {
/* 197:193 */       ByteBuf var2 = Unpooled.copiedBuffer(this.field_148301_e.func_147409_e(), Charsets.UTF_8);
/* 198:194 */       ByteBuf var3 = Base64.decode(var2);
/* 199:    */       BufferedImage var1;
/* 200:    */       try
/* 201:    */       {
/* 202:200 */         BufferedImage var1 = ImageIO.read(new ByteBufInputStream(var3));
/* 203:201 */         Validate.validState(var1.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
/* 204:202 */         Validate.validState(var1.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
/* 205:    */         
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:212 */         var2.release();
/* 215:213 */         var3.release();
/* 216:    */       }
/* 217:    */       catch (Exception var8)
/* 218:    */       {
/* 219:207 */         logger.error("Invalid icon for server " + this.field_148301_e.serverName + " (" + this.field_148301_e.serverIP + ")", var8);
/* 220:208 */         this.field_148301_e.func_147407_a(null);
/* 221:    */         
/* 222:    */ 
/* 223:    */ 
/* 224:212 */         var2.release();
/* 225:213 */         var3.release();
/* 226:    */       }
/* 227:    */       finally
/* 228:    */       {
/* 229:212 */         var2.release();
/* 230:213 */         var3.release();
/* 231:    */       }
/* 232:221 */       this.field_148305_h = new DynamicTexture(var1.getWidth(), var1.getHeight());
/* 233:222 */       this.field_148300_d.getTextureManager().loadTexture(this.field_148306_i, this.field_148305_h);
/* 234:    */       
/* 235:    */ 
/* 236:225 */       var1.getRGB(0, 0, var1.getWidth(), var1.getHeight(), this.field_148305_h.getTextureData(), 0, var1.getWidth());
/* 237:226 */       this.field_148305_h.updateDynamicTexture();
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
/* 242:    */   {
/* 243:232 */     this.field_148303_c.func_146790_a(p_148278_1_);
/* 244:234 */     if (Minecraft.getSystemTime() - this.field_148298_f < 250L) {
/* 245:236 */       this.field_148303_c.func_146796_h();
/* 246:    */     }
/* 247:239 */     this.field_148298_f = Minecraft.getSystemTime();
/* 248:240 */     return false;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {}
/* 252:    */   
/* 253:    */   public ServerData func_148296_a()
/* 254:    */   {
/* 255:247 */     return this.field_148301_e;
/* 256:    */   }
/* 257:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.ServerListEntryNormal
 * JD-Core Version:    0.7.0.1
 */