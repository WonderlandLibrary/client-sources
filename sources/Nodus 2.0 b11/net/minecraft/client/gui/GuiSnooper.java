/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Set;
/*   8:    */ import java.util.TreeMap;
/*   9:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  10:    */ import net.minecraft.client.Minecraft;
/*  11:    */ import net.minecraft.client.renderer.Tessellator;
/*  12:    */ import net.minecraft.client.resources.I18n;
/*  13:    */ import net.minecraft.client.settings.GameSettings;
/*  14:    */ import net.minecraft.client.settings.GameSettings.Options;
/*  15:    */ import net.minecraft.profiler.PlayerUsageSnooper;
/*  16:    */ import net.minecraft.server.integrated.IntegratedServer;
/*  17:    */ 
/*  18:    */ public class GuiSnooper
/*  19:    */   extends GuiScreen
/*  20:    */ {
/*  21:    */   private final GuiScreen field_146608_a;
/*  22:    */   private final GameSettings field_146603_f;
/*  23: 17 */   private final List field_146604_g = new ArrayList();
/*  24: 18 */   private final List field_146609_h = new ArrayList();
/*  25:    */   private String field_146610_i;
/*  26:    */   private String[] field_146607_r;
/*  27:    */   private List field_146606_s;
/*  28:    */   private NodusGuiButton field_146605_t;
/*  29:    */   private static final String __OBFID = "CL_00000714";
/*  30:    */   
/*  31:    */   public GuiSnooper(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
/*  32:    */   {
/*  33: 27 */     this.field_146608_a = par1GuiScreen;
/*  34: 28 */     this.field_146603_f = par2GameSettings;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void initGui()
/*  38:    */   {
/*  39: 36 */     this.field_146610_i = I18n.format("options.snooper.title", new Object[0]);
/*  40: 37 */     String var1 = I18n.format("options.snooper.desc", new Object[0]);
/*  41: 38 */     ArrayList var2 = new ArrayList();
/*  42: 39 */     Iterator var3 = this.fontRendererObj.listFormattedStringToWidth(var1, width - 30).iterator();
/*  43: 41 */     while (var3.hasNext())
/*  44:    */     {
/*  45: 43 */       String var4 = (String)var3.next();
/*  46: 44 */       var2.add(var4);
/*  47:    */     }
/*  48: 47 */     this.field_146607_r = ((String[])var2.toArray(new String[0]));
/*  49: 48 */     this.field_146604_g.clear();
/*  50: 49 */     this.field_146609_h.clear();
/*  51: 50 */     this.buttonList.add(this.field_146605_t = new NodusGuiButton(1, width / 2 - 152, height - 30, 150, 20, this.field_146603_f.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED)));
/*  52: 51 */     this.buttonList.add(new NodusGuiButton(2, width / 2 + 2, height - 30, 150, 20, I18n.format("gui.done", new Object[0])));
/*  53: 52 */     boolean var6 = (this.mc.getIntegratedServer() != null) && (this.mc.getIntegratedServer().getPlayerUsageSnooper() != null);
/*  54: 53 */     Iterator var7 = new TreeMap(this.mc.getPlayerUsageSnooper().getCurrentStats()).entrySet().iterator();
/*  55: 56 */     while (var7.hasNext())
/*  56:    */     {
/*  57: 58 */       Map.Entry var5 = (Map.Entry)var7.next();
/*  58: 59 */       this.field_146604_g.add((var6 ? "C " : "") + (String)var5.getKey());
/*  59: 60 */       this.field_146609_h.add(this.fontRendererObj.trimStringToWidth((String)var5.getValue(), width - 220));
/*  60:    */     }
/*  61: 63 */     if (var6)
/*  62:    */     {
/*  63: 65 */       var7 = new TreeMap(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats()).entrySet().iterator();
/*  64: 67 */       while (var7.hasNext())
/*  65:    */       {
/*  66: 69 */         Map.Entry var5 = (Map.Entry)var7.next();
/*  67: 70 */         this.field_146604_g.add("S " + (String)var5.getKey());
/*  68: 71 */         this.field_146609_h.add(this.fontRendererObj.trimStringToWidth((String)var5.getValue(), width - 220));
/*  69:    */       }
/*  70:    */     }
/*  71: 75 */     this.field_146606_s = new List();
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  75:    */   {
/*  76: 80 */     if (p_146284_1_.enabled)
/*  77:    */     {
/*  78: 82 */       if (p_146284_1_.id == 2)
/*  79:    */       {
/*  80: 84 */         this.field_146603_f.saveOptions();
/*  81: 85 */         this.field_146603_f.saveOptions();
/*  82: 86 */         this.mc.displayGuiScreen(this.field_146608_a);
/*  83:    */       }
/*  84: 89 */       if (p_146284_1_.id == 1)
/*  85:    */       {
/*  86: 91 */         this.field_146603_f.setOptionValue(GameSettings.Options.SNOOPER_ENABLED, 1);
/*  87: 92 */         this.field_146605_t.displayString = this.field_146603_f.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED);
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void drawScreen(int par1, int par2, float par3)
/*  93:    */   {
/*  94:102 */     drawDefaultBackground();
/*  95:103 */     this.field_146606_s.func_148128_a(par1, par2, par3);
/*  96:104 */     drawCenteredString(this.fontRendererObj, this.field_146610_i, width / 2, 8, 16777215);
/*  97:105 */     int var4 = 22;
/*  98:106 */     String[] var5 = this.field_146607_r;
/*  99:107 */     int var6 = var5.length;
/* 100:109 */     for (int var7 = 0; var7 < var6; var7++)
/* 101:    */     {
/* 102:111 */       String var8 = var5[var7];
/* 103:112 */       drawCenteredString(this.fontRendererObj, var8, width / 2, var4, 8421504);
/* 104:113 */       var4 += this.fontRendererObj.FONT_HEIGHT;
/* 105:    */     }
/* 106:116 */     super.drawScreen(par1, par2, par3);
/* 107:    */   }
/* 108:    */   
/* 109:    */   class List
/* 110:    */     extends GuiSlot
/* 111:    */   {
/* 112:    */     private static final String __OBFID = "CL_00000715";
/* 113:    */     
/* 114:    */     public List()
/* 115:    */     {
/* 116:125 */       super(GuiSnooper.width, GuiSnooper.height, 80, GuiSnooper.height - 40, GuiSnooper.this.fontRendererObj.FONT_HEIGHT + 1);
/* 117:    */     }
/* 118:    */     
/* 119:    */     protected int getSize()
/* 120:    */     {
/* 121:130 */       return GuiSnooper.this.field_146604_g.size();
/* 122:    */     }
/* 123:    */     
/* 124:    */     protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {}
/* 125:    */     
/* 126:    */     protected boolean isSelected(int p_148131_1_)
/* 127:    */     {
/* 128:137 */       return false;
/* 129:    */     }
/* 130:    */     
/* 131:    */     protected void drawBackground() {}
/* 132:    */     
/* 133:    */     protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_)
/* 134:    */     {
/* 135:144 */       GuiSnooper.this.fontRendererObj.drawString((String)GuiSnooper.this.field_146604_g.get(p_148126_1_), 10, p_148126_3_, 16777215);
/* 136:145 */       GuiSnooper.this.fontRendererObj.drawString((String)GuiSnooper.this.field_146609_h.get(p_148126_1_), 230, p_148126_3_, 16777215);
/* 137:    */     }
/* 138:    */     
/* 139:    */     protected int func_148137_d()
/* 140:    */     {
/* 141:150 */       return this.field_148155_a - 10;
/* 142:    */     }
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiSnooper
 * JD-Core Version:    0.7.0.1
 */