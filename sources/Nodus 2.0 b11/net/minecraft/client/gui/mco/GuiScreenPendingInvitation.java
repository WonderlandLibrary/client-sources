/*   1:    */ package net.minecraft.client.gui.mco;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.concurrent.atomic.AtomicInteger;
/*   6:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   7:    */ import net.minecraft.client.Minecraft;
/*   8:    */ import net.minecraft.client.gui.GuiScreen;
/*   9:    */ import net.minecraft.client.gui.GuiScreenOnlineServers;
/*  10:    */ import net.minecraft.client.gui.GuiScreenSelectLocation;
/*  11:    */ import net.minecraft.client.mco.ExceptionMcoService;
/*  12:    */ import net.minecraft.client.mco.McoClient;
/*  13:    */ import net.minecraft.client.mco.PendingInvite;
/*  14:    */ import net.minecraft.client.mco.PendingInvitesList;
/*  15:    */ import net.minecraft.client.renderer.Tessellator;
/*  16:    */ import net.minecraft.client.resources.I18n;
/*  17:    */ import net.minecraft.util.Session;
/*  18:    */ import org.apache.logging.log4j.LogManager;
/*  19:    */ import org.apache.logging.log4j.Logger;
/*  20:    */ import org.lwjgl.input.Keyboard;
/*  21:    */ 
/*  22:    */ public class GuiScreenPendingInvitation
/*  23:    */   extends GuiScreen
/*  24:    */ {
/*  25: 25 */   private static final AtomicInteger field_146732_a = new AtomicInteger(0);
/*  26: 26 */   private static final Logger logger = LogManager.getLogger();
/*  27:    */   private final GuiScreen field_146730_g;
/*  28:    */   private List field_146733_h;
/*  29: 29 */   private List field_146734_i = Lists.newArrayList();
/*  30: 30 */   private int field_146731_r = -1;
/*  31:    */   private static final String __OBFID = "CL_00000797";
/*  32:    */   
/*  33:    */   public GuiScreenPendingInvitation(GuiScreen par1GuiScreen)
/*  34:    */   {
/*  35: 35 */     this.field_146730_g = par1GuiScreen;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void initGui()
/*  39:    */   {
/*  40: 43 */     Keyboard.enableRepeatEvents(true);
/*  41: 44 */     this.buttonList.clear();
/*  42: 45 */     this.field_146733_h = new List();
/*  43: 46 */     new Thread("MCO List Invites #" + field_146732_a.incrementAndGet())
/*  44:    */     {
/*  45:    */       private static final String __OBFID = "CL_00000798";
/*  46:    */       
/*  47:    */       public void run()
/*  48:    */       {
/*  49: 51 */         Session var1 = GuiScreenPendingInvitation.this.mc.getSession();
/*  50: 52 */         McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/*  51:    */         try
/*  52:    */         {
/*  53: 56 */           GuiScreenPendingInvitation.this.field_146734_i = var2.func_148710_g().field_148768_a;
/*  54:    */         }
/*  55:    */         catch (ExceptionMcoService var4)
/*  56:    */         {
/*  57: 60 */           GuiScreenPendingInvitation.logger.error("Couldn't list invites");
/*  58:    */         }
/*  59:    */       }
/*  60: 63 */     }.start();
/*  61: 64 */     func_146728_h();
/*  62:    */   }
/*  63:    */   
/*  64:    */   private void func_146728_h()
/*  65:    */   {
/*  66: 69 */     this.buttonList.add(new NodusGuiButton(1, width / 2 - 154, height - 52, 153, 20, I18n.format("mco.invites.button.accept", new Object[0])));
/*  67: 70 */     this.buttonList.add(new NodusGuiButton(2, width / 2 + 6, height - 52, 153, 20, I18n.format("mco.invites.button.reject", new Object[0])));
/*  68: 71 */     this.buttonList.add(new NodusGuiButton(0, width / 2 - 75, height - 28, 153, 20, I18n.format("gui.back", new Object[0])));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void updateScreen()
/*  72:    */   {
/*  73: 79 */     super.updateScreen();
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  77:    */   {
/*  78: 84 */     if (p_146284_1_.enabled) {
/*  79: 86 */       if (p_146284_1_.id == 1) {
/*  80: 88 */         func_146723_p();
/*  81: 90 */       } else if (p_146284_1_.id == 0) {
/*  82: 92 */         this.mc.displayGuiScreen(new GuiScreenOnlineServers(this.field_146730_g));
/*  83: 94 */       } else if (p_146284_1_.id == 2) {
/*  84: 96 */         func_146724_i();
/*  85:    */       } else {
/*  86:100 */         this.field_146733_h.func_148357_a(p_146284_1_);
/*  87:    */       }
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   private void func_146724_i()
/*  92:    */   {
/*  93:107 */     if ((this.field_146731_r >= 0) && (this.field_146731_r < this.field_146734_i.size())) {
/*  94:126 */       new Thread("MCO Reject Invite #" + field_146732_a.incrementAndGet())
/*  95:    */       {
/*  96:    */         private static final String __OBFID = "CL_00000800";
/*  97:    */         
/*  98:    */         public void run()
/*  99:    */         {
/* 100:    */           try
/* 101:    */           {
/* 102:116 */             Session var1 = GuiScreenPendingInvitation.this.mc.getSession();
/* 103:117 */             McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/* 104:118 */             var2.func_148706_b(((PendingInvite)GuiScreenPendingInvitation.this.field_146734_i.get(GuiScreenPendingInvitation.this.field_146731_r)).field_148776_a);
/* 105:119 */             GuiScreenPendingInvitation.this.func_146718_q();
/* 106:    */           }
/* 107:    */           catch (ExceptionMcoService var3)
/* 108:    */           {
/* 109:123 */             GuiScreenPendingInvitation.logger.error("Couldn't reject invite");
/* 110:    */           }
/* 111:    */         }
/* 112:    */       }.start();
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   private void func_146723_p()
/* 117:    */   {
/* 118:132 */     if ((this.field_146731_r >= 0) && (this.field_146731_r < this.field_146734_i.size())) {
/* 119:151 */       new Thread("MCO Accept Invite #" + field_146732_a.incrementAndGet())
/* 120:    */       {
/* 121:    */         private static final String __OBFID = "CL_00000801";
/* 122:    */         
/* 123:    */         public void run()
/* 124:    */         {
/* 125:    */           try
/* 126:    */           {
/* 127:141 */             Session var1 = GuiScreenPendingInvitation.this.mc.getSession();
/* 128:142 */             McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/* 129:143 */             var2.func_148691_a(((PendingInvite)GuiScreenPendingInvitation.this.field_146734_i.get(GuiScreenPendingInvitation.this.field_146731_r)).field_148776_a);
/* 130:144 */             GuiScreenPendingInvitation.this.func_146718_q();
/* 131:    */           }
/* 132:    */           catch (ExceptionMcoService var3)
/* 133:    */           {
/* 134:148 */             GuiScreenPendingInvitation.logger.error("Couldn't accept invite");
/* 135:    */           }
/* 136:    */         }
/* 137:    */       }.start();
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   private void func_146718_q()
/* 142:    */   {
/* 143:157 */     int var1 = this.field_146731_r;
/* 144:159 */     if (this.field_146734_i.size() - 1 == this.field_146731_r) {
/* 145:161 */       this.field_146731_r -= 1;
/* 146:    */     }
/* 147:164 */     this.field_146734_i.remove(var1);
/* 148:166 */     if (this.field_146734_i.size() == 0) {
/* 149:168 */       this.field_146731_r = -1;
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void drawScreen(int par1, int par2, float par3)
/* 154:    */   {
/* 155:177 */     drawDefaultBackground();
/* 156:178 */     this.field_146733_h.func_148350_a(par1, par2, par3);
/* 157:179 */     drawCenteredString(this.fontRendererObj, I18n.format("mco.invites.title", new Object[0]), width / 2, 20, 16777215);
/* 158:180 */     super.drawScreen(par1, par2, par3);
/* 159:    */   }
/* 160:    */   
/* 161:    */   class List
/* 162:    */     extends GuiScreenSelectLocation
/* 163:    */   {
/* 164:    */     private static final String __OBFID = "CL_00000802";
/* 165:    */     
/* 166:    */     public List()
/* 167:    */     {
/* 168:189 */       super(GuiScreenPendingInvitation.width, GuiScreenPendingInvitation.height, 32, GuiScreenPendingInvitation.height - 64, 36);
/* 169:    */     }
/* 170:    */     
/* 171:    */     protected int func_148355_a()
/* 172:    */     {
/* 173:194 */       return GuiScreenPendingInvitation.this.field_146734_i.size() + 1;
/* 174:    */     }
/* 175:    */     
/* 176:    */     protected void func_148352_a(int p_148352_1_, boolean p_148352_2_)
/* 177:    */     {
/* 178:199 */       if (p_148352_1_ < GuiScreenPendingInvitation.this.field_146734_i.size()) {
/* 179:201 */         GuiScreenPendingInvitation.this.field_146731_r = p_148352_1_;
/* 180:    */       }
/* 181:    */     }
/* 182:    */     
/* 183:    */     protected boolean func_148356_a(int p_148356_1_)
/* 184:    */     {
/* 185:207 */       return p_148356_1_ == GuiScreenPendingInvitation.this.field_146731_r;
/* 186:    */     }
/* 187:    */     
/* 188:    */     protected boolean func_148349_b(int p_148349_1_)
/* 189:    */     {
/* 190:212 */       return false;
/* 191:    */     }
/* 192:    */     
/* 193:    */     protected int func_148351_b()
/* 194:    */     {
/* 195:217 */       return func_148355_a() * 36;
/* 196:    */     }
/* 197:    */     
/* 198:    */     protected void func_148358_c()
/* 199:    */     {
/* 200:222 */       GuiScreenPendingInvitation.this.drawDefaultBackground();
/* 201:    */     }
/* 202:    */     
/* 203:    */     protected void func_148348_a(int p_148348_1_, int p_148348_2_, int p_148348_3_, int p_148348_4_, Tessellator p_148348_5_)
/* 204:    */     {
/* 205:227 */       if (p_148348_1_ < GuiScreenPendingInvitation.this.field_146734_i.size()) {
/* 206:229 */         func_148382_b(p_148348_1_, p_148348_2_, p_148348_3_, p_148348_4_, p_148348_5_);
/* 207:    */       }
/* 208:    */     }
/* 209:    */     
/* 210:    */     private void func_148382_b(int p_148382_1_, int p_148382_2_, int p_148382_3_, int p_148382_4_, Tessellator p_148382_5_)
/* 211:    */     {
/* 212:235 */       PendingInvite var6 = (PendingInvite)GuiScreenPendingInvitation.this.field_146734_i.get(p_148382_1_);
/* 213:236 */       GuiScreenPendingInvitation.drawString(GuiScreenPendingInvitation.this.fontRendererObj, var6.field_148774_b, p_148382_2_ + 2, p_148382_3_ + 1, 16777215);
/* 214:237 */       GuiScreenPendingInvitation.drawString(GuiScreenPendingInvitation.this.fontRendererObj, var6.field_148775_c, p_148382_2_ + 2, p_148382_3_ + 12, 7105644);
/* 215:    */     }
/* 216:    */   }
/* 217:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.mco.GuiScreenPendingInvitation
 * JD-Core Version:    0.7.0.1
 */