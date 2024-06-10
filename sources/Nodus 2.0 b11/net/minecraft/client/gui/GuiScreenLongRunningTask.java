/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.concurrent.atomic.AtomicInteger;
/*   7:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   8:    */ import net.minecraft.client.Minecraft;
/*   9:    */ import net.minecraft.client.resources.I18n;
/*  10:    */ 
/*  11:    */ public class GuiScreenLongRunningTask
/*  12:    */   extends GuiScreen
/*  13:    */ {
/*  14: 13 */   private static final AtomicInteger field_146908_f = new AtomicInteger(0);
/*  15: 14 */   private final int field_146910_g = 666;
/*  16: 15 */   private final int field_146917_h = 667;
/*  17:    */   private final GuiScreen field_146919_i;
/*  18:    */   private final Thread field_146914_r;
/*  19: 18 */   private volatile String field_146913_s = "";
/*  20:    */   private volatile boolean field_146912_t;
/*  21:    */   private volatile String field_146911_u;
/*  22:    */   private volatile boolean field_146909_v;
/*  23:    */   private int field_146907_w;
/*  24:    */   private TaskLongRunning field_146918_x;
/*  25: 24 */   private int field_146916_y = 212;
/*  26: 25 */   public static final String[] field_146915_a = { "▃ ▄ ▅ ▆ ▇ █ ▇ ▆ ▅ ▄ ▃", "_ ▃ ▄ ▅ ▆ ▇ █ ▇ ▆ ▅ ▄", "_ _ ▃ ▄ ▅ ▆ ▇ █ ▇ ▆ ▅", "_ _ _ ▃ ▄ ▅ ▆ ▇ █ ▇ ▆", "_ _ _ _ ▃ ▄ ▅ ▆ ▇ █ ▇", "_ _ _ _ _ ▃ ▄ ▅ ▆ ▇ █", "_ _ _ _ ▃ ▄ ▅ ▆ ▇ █ ▇", "_ _ _ ▃ ▄ ▅ ▆ ▇ █ ▇ ▆", "_ _ ▃ ▄ ▅ ▆ ▇ █ ▇ ▆ ▅", "_ ▃ ▄ ▅ ▆ ▇ █ ▇ ▆ ▅ ▄", "▃ ▄ ▅ ▆ ▇ █ ▇ ▆ ▅ ▄ ▃", "▄ ▅ ▆ ▇ █ ▇ ▆ ▅ ▄ ▃ _", "▅ ▆ ▇ █ ▇ ▆ ▅ ▄ ▃ _ _", "▆ ▇ █ ▇ ▆ ▅ ▄ ▃ _ _ _", "▇ █ ▇ ▆ ▅ ▄ ▃ _ _ _ _", "█ ▇ ▆ ▅ ▄ ▃ _ _ _ _ _", "▇ █ ▇ ▆ ▅ ▄ ▃ _ _ _ _", "▆ ▇ █ ▇ ▆ ▅ ▄ ▃ _ _ _", "▅ ▆ ▇ █ ▇ ▆ ▅ ▄ ▃ _ _", "▄ ▅ ▆ ▇ █ ▇ ▆ ▅ ▄ ▃ _" };
/*  27:    */   private static final String __OBFID = "CL_00000783";
/*  28:    */   
/*  29:    */   public GuiScreenLongRunningTask(Minecraft par1Minecraft, GuiScreen par2GuiScreen, TaskLongRunning par3TaskLongRunning)
/*  30:    */   {
/*  31: 30 */     this.buttonList = Collections.synchronizedList(new ArrayList());
/*  32: 31 */     this.mc = par1Minecraft;
/*  33: 32 */     this.field_146919_i = par2GuiScreen;
/*  34: 33 */     this.field_146918_x = par3TaskLongRunning;
/*  35: 34 */     par3TaskLongRunning.func_148412_a(this);
/*  36: 35 */     this.field_146914_r = new Thread(par3TaskLongRunning, "MCO Task #" + field_146908_f.incrementAndGet());
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void func_146902_g()
/*  40:    */   {
/*  41: 40 */     this.field_146914_r.start();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void updateScreen()
/*  45:    */   {
/*  46: 48 */     super.updateScreen();
/*  47: 49 */     this.field_146907_w += 1;
/*  48: 50 */     this.field_146918_x.func_148414_a();
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void keyTyped(char par1, int par2) {}
/*  52:    */   
/*  53:    */   public void initGui()
/*  54:    */   {
/*  55: 63 */     this.field_146918_x.func_148411_d();
/*  56: 64 */     this.buttonList.add(new NodusGuiButton(666, width / 2 - this.field_146916_y / 2, 170, this.field_146916_y, 20, I18n.format("gui.cancel", new Object[0])));
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  60:    */   {
/*  61: 69 */     if ((p_146284_1_.id == 666) || (p_146284_1_.id == 667))
/*  62:    */     {
/*  63: 71 */       this.field_146909_v = true;
/*  64: 72 */       this.mc.displayGuiScreen(this.field_146919_i);
/*  65:    */     }
/*  66: 75 */     this.field_146918_x.func_148415_a(p_146284_1_);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void drawScreen(int par1, int par2, float par3)
/*  70:    */   {
/*  71: 83 */     drawDefaultBackground();
/*  72: 84 */     drawCenteredString(this.fontRendererObj, this.field_146913_s, width / 2, height / 2 - 50, 16777215);
/*  73: 85 */     drawCenteredString(this.fontRendererObj, "", width / 2, height / 2 - 10, 16777215);
/*  74: 87 */     if (!this.field_146912_t) {
/*  75: 89 */       drawCenteredString(this.fontRendererObj, field_146915_a[(this.field_146907_w % field_146915_a.length)], width / 2, height / 2 + 15, 8421504);
/*  76:    */     }
/*  77: 92 */     if (this.field_146912_t) {
/*  78: 94 */       drawCenteredString(this.fontRendererObj, this.field_146911_u, width / 2, height / 2 + 15, 16711680);
/*  79:    */     }
/*  80: 97 */     super.drawScreen(par1, par2, par3);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void func_146905_a(String p_146905_1_)
/*  84:    */   {
/*  85:102 */     this.field_146912_t = true;
/*  86:103 */     this.field_146911_u = p_146905_1_;
/*  87:104 */     this.buttonList.clear();
/*  88:105 */     this.buttonList.add(new NodusGuiButton(667, width / 2 - this.field_146916_y / 2, height / 4 + 120 + 12, I18n.format("gui.back", new Object[0])));
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Minecraft func_146903_h()
/*  92:    */   {
/*  93:110 */     return this.mc;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void func_146906_b(String p_146906_1_)
/*  97:    */   {
/*  98:115 */     this.field_146913_s = p_146906_1_;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean func_146904_i()
/* 102:    */   {
/* 103:120 */     return this.field_146909_v;
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenLongRunningTask
 * JD-Core Version:    0.7.0.1
 */