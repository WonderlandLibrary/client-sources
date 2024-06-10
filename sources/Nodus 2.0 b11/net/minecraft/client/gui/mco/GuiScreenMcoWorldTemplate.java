/*   1:    */ package net.minecraft.client.gui.mco;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.concurrent.atomic.AtomicInteger;
/*   6:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   7:    */ import net.minecraft.client.Minecraft;
/*   8:    */ import net.minecraft.client.gui.FontRenderer;
/*   9:    */ import net.minecraft.client.gui.GuiScreen;
/*  10:    */ import net.minecraft.client.gui.GuiScreenSelectLocation;
/*  11:    */ import net.minecraft.client.mco.ExceptionMcoService;
/*  12:    */ import net.minecraft.client.mco.McoClient;
/*  13:    */ import net.minecraft.client.mco.WorldTemplate;
/*  14:    */ import net.minecraft.client.mco.WorldTemplateList;
/*  15:    */ import net.minecraft.client.renderer.Tessellator;
/*  16:    */ import net.minecraft.client.resources.I18n;
/*  17:    */ import net.minecraft.util.Session;
/*  18:    */ import org.apache.logging.log4j.LogManager;
/*  19:    */ import org.apache.logging.log4j.Logger;
/*  20:    */ import org.lwjgl.input.Keyboard;
/*  21:    */ 
/*  22:    */ public class GuiScreenMcoWorldTemplate
/*  23:    */   extends GuiScreen
/*  24:    */ {
/*  25: 24 */   private static final AtomicInteger field_146958_a = new AtomicInteger(0);
/*  26: 25 */   private static final Logger logger = LogManager.getLogger();
/*  27:    */   private final ScreenWithCallback field_146954_g;
/*  28:    */   private WorldTemplate field_146959_h;
/*  29: 28 */   private List field_146960_i = Collections.emptyList();
/*  30:    */   private SelectionList field_146957_r;
/*  31: 30 */   private int field_146956_s = -1;
/*  32:    */   private NodusGuiButton field_146955_t;
/*  33:    */   private static final String __OBFID = "CL_00000786";
/*  34:    */   
/*  35:    */   public GuiScreenMcoWorldTemplate(ScreenWithCallback par1ScreenWithCallback, WorldTemplate par2WorldTemplate)
/*  36:    */   {
/*  37: 36 */     this.field_146954_g = par1ScreenWithCallback;
/*  38: 37 */     this.field_146959_h = par2WorldTemplate;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void initGui()
/*  42:    */   {
/*  43: 45 */     Keyboard.enableRepeatEvents(true);
/*  44: 46 */     this.buttonList.clear();
/*  45: 47 */     this.field_146957_r = new SelectionList();
/*  46: 48 */     new Thread("MCO World Creator #" + field_146958_a.incrementAndGet())
/*  47:    */     {
/*  48:    */       private static final String __OBFID = "CL_00000787";
/*  49:    */       
/*  50:    */       public void run()
/*  51:    */       {
/*  52: 53 */         Session var1 = GuiScreenMcoWorldTemplate.this.mc.getSession();
/*  53: 54 */         McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/*  54:    */         try
/*  55:    */         {
/*  56: 58 */           GuiScreenMcoWorldTemplate.this.field_146960_i = var2.func_148693_e().field_148782_a;
/*  57:    */         }
/*  58:    */         catch (ExceptionMcoService var4)
/*  59:    */         {
/*  60: 62 */           GuiScreenMcoWorldTemplate.logger.error("Couldn't fetch templates");
/*  61:    */         }
/*  62:    */       }
/*  63: 65 */     }.start();
/*  64: 66 */     func_146952_h();
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void func_146952_h()
/*  68:    */   {
/*  69: 71 */     this.buttonList.add(new NodusGuiButton(0, width / 2 + 6, height - 52, 153, 20, I18n.format("gui.cancel", new Object[0])));
/*  70: 72 */     this.buttonList.add(this.field_146955_t = new NodusGuiButton(1, width / 2 - 154, height - 52, 153, 20, I18n.format("mco.template.button.select", new Object[0])));
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void updateScreen()
/*  74:    */   {
/*  75: 80 */     super.updateScreen();
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  79:    */   {
/*  80: 85 */     if (p_146284_1_.enabled) {
/*  81: 87 */       if (p_146284_1_.id == 1)
/*  82:    */       {
/*  83: 89 */         func_146947_i();
/*  84:    */       }
/*  85: 91 */       else if (p_146284_1_.id == 0)
/*  86:    */       {
/*  87: 93 */         this.field_146954_g.func_146735_a(null);
/*  88: 94 */         this.mc.displayGuiScreen(this.field_146954_g);
/*  89:    */       }
/*  90:    */       else
/*  91:    */       {
/*  92: 98 */         this.field_146957_r.func_148357_a(p_146284_1_);
/*  93:    */       }
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   private void func_146947_i()
/*  98:    */   {
/*  99:105 */     if ((this.field_146956_s >= 0) && (this.field_146956_s < this.field_146960_i.size()))
/* 100:    */     {
/* 101:107 */       this.field_146954_g.func_146735_a(this.field_146960_i.get(this.field_146956_s));
/* 102:108 */       this.mc.displayGuiScreen(this.field_146954_g);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void drawScreen(int par1, int par2, float par3)
/* 107:    */   {
/* 108:117 */     drawDefaultBackground();
/* 109:118 */     this.field_146957_r.func_148350_a(par1, par2, par3);
/* 110:119 */     drawCenteredString(this.fontRendererObj, I18n.format("mco.template.title", new Object[0]), width / 2, 20, 16777215);
/* 111:120 */     super.drawScreen(par1, par2, par3);
/* 112:    */   }
/* 113:    */   
/* 114:    */   class SelectionList
/* 115:    */     extends GuiScreenSelectLocation
/* 116:    */   {
/* 117:    */     private static final String __OBFID = "CL_00000788";
/* 118:    */     
/* 119:    */     public SelectionList()
/* 120:    */     {
/* 121:129 */       super(GuiScreenMcoWorldTemplate.width, GuiScreenMcoWorldTemplate.height, 32, GuiScreenMcoWorldTemplate.height - 64, 36);
/* 122:    */     }
/* 123:    */     
/* 124:    */     protected int func_148355_a()
/* 125:    */     {
/* 126:134 */       return GuiScreenMcoWorldTemplate.this.field_146960_i.size() + 1;
/* 127:    */     }
/* 128:    */     
/* 129:    */     protected void func_148352_a(int p_148352_1_, boolean p_148352_2_)
/* 130:    */     {
/* 131:139 */       if (p_148352_1_ < GuiScreenMcoWorldTemplate.this.field_146960_i.size())
/* 132:    */       {
/* 133:141 */         GuiScreenMcoWorldTemplate.this.field_146956_s = p_148352_1_;
/* 134:142 */         GuiScreenMcoWorldTemplate.this.field_146959_h = null;
/* 135:    */       }
/* 136:    */     }
/* 137:    */     
/* 138:    */     protected boolean func_148356_a(int p_148356_1_)
/* 139:    */     {
/* 140:148 */       return GuiScreenMcoWorldTemplate.this.field_146960_i.size() != 0;
/* 141:    */     }
/* 142:    */     
/* 143:    */     protected boolean func_148349_b(int p_148349_1_)
/* 144:    */     {
/* 145:153 */       return false;
/* 146:    */     }
/* 147:    */     
/* 148:    */     protected int func_148351_b()
/* 149:    */     {
/* 150:158 */       return func_148355_a() * 36;
/* 151:    */     }
/* 152:    */     
/* 153:    */     protected void func_148358_c()
/* 154:    */     {
/* 155:163 */       GuiScreenMcoWorldTemplate.this.drawDefaultBackground();
/* 156:    */     }
/* 157:    */     
/* 158:    */     protected void func_148348_a(int p_148348_1_, int p_148348_2_, int p_148348_3_, int p_148348_4_, Tessellator p_148348_5_)
/* 159:    */     {
/* 160:168 */       if (p_148348_1_ < GuiScreenMcoWorldTemplate.this.field_146960_i.size()) {
/* 161:170 */         func_148387_b(p_148348_1_, p_148348_2_, p_148348_3_, p_148348_4_, p_148348_5_);
/* 162:    */       }
/* 163:    */     }
/* 164:    */     
/* 165:    */     private void func_148387_b(int p_148387_1_, int p_148387_2_, int p_148387_3_, int p_148387_4_, Tessellator p_148387_5_)
/* 166:    */     {
/* 167:176 */       WorldTemplate var6 = (WorldTemplate)GuiScreenMcoWorldTemplate.this.field_146960_i.get(p_148387_1_);
/* 168:177 */       GuiScreenMcoWorldTemplate.drawString(GuiScreenMcoWorldTemplate.this.fontRendererObj, var6.field_148785_b, p_148387_2_ + 2, p_148387_3_ + 1, 16777215);
/* 169:178 */       GuiScreenMcoWorldTemplate.drawString(GuiScreenMcoWorldTemplate.this.fontRendererObj, var6.field_148784_d, p_148387_2_ + 2, p_148387_3_ + 12, 7105644);
/* 170:179 */       GuiScreenMcoWorldTemplate.drawString(GuiScreenMcoWorldTemplate.this.fontRendererObj, var6.field_148786_c, p_148387_2_ + 2 + 207 - GuiScreenMcoWorldTemplate.this.fontRendererObj.getStringWidth(var6.field_148786_c), p_148387_3_ + 1, 5000268);
/* 171:    */     }
/* 172:    */   }
/* 173:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.mco.GuiScreenMcoWorldTemplate
 * JD-Core Version:    0.7.0.1
 */