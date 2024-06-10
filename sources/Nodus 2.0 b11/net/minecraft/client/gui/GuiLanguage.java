/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import com.google.common.collect.Maps;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.SortedSet;
/*   9:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  10:    */ import net.minecraft.client.Minecraft;
/*  11:    */ import net.minecraft.client.renderer.Tessellator;
/*  12:    */ import net.minecraft.client.resources.I18n;
/*  13:    */ import net.minecraft.client.resources.Language;
/*  14:    */ import net.minecraft.client.resources.LanguageManager;
/*  15:    */ import net.minecraft.client.settings.GameSettings;
/*  16:    */ import net.minecraft.client.settings.GameSettings.Options;
/*  17:    */ 
/*  18:    */ public class GuiLanguage
/*  19:    */   extends GuiScreen
/*  20:    */ {
/*  21:    */   protected GuiScreen field_146453_a;
/*  22:    */   private List field_146450_f;
/*  23:    */   private final GameSettings field_146451_g;
/*  24:    */   private final LanguageManager field_146454_h;
/*  25:    */   private GuiOptionButton field_146455_i;
/*  26:    */   private GuiOptionButton field_146452_r;
/*  27:    */   private static final String __OBFID = "CL_00000698";
/*  28:    */   
/*  29:    */   public GuiLanguage(GuiScreen par1GuiScreen, GameSettings par2GameSettings, LanguageManager par3LanguageManager)
/*  30:    */   {
/*  31: 28 */     this.field_146453_a = par1GuiScreen;
/*  32: 29 */     this.field_146451_g = par2GameSettings;
/*  33: 30 */     this.field_146454_h = par3LanguageManager;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void initGui()
/*  37:    */   {
/*  38: 38 */     this.buttonList.add(this.field_146455_i = new GuiOptionButton(100, width / 2 - 155, height - 38, GameSettings.Options.FORCE_UNICODE_FONT, this.field_146451_g.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT)));
/*  39: 39 */     this.buttonList.add(this.field_146452_r = new GuiOptionButton(6, width / 2 - 155 + 160, height - 38, I18n.format("gui.done", new Object[0])));
/*  40: 40 */     this.field_146450_f = new List();
/*  41: 41 */     this.field_146450_f.func_148134_d(7, 8);
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  45:    */   {
/*  46: 46 */     if (p_146284_1_.enabled) {
/*  47: 48 */       switch (p_146284_1_.id)
/*  48:    */       {
/*  49:    */       case 5: 
/*  50:    */         break;
/*  51:    */       case 6: 
/*  52: 54 */         this.mc.displayGuiScreen(this.field_146453_a);
/*  53: 55 */         break;
/*  54:    */       case 100: 
/*  55: 58 */         if ((p_146284_1_ instanceof GuiOptionButton))
/*  56:    */         {
/*  57: 60 */           this.field_146451_g.setOptionValue(((GuiOptionButton)p_146284_1_).func_146136_c(), 1);
/*  58: 61 */           p_146284_1_.displayString = this.field_146451_g.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
/*  59:    */         }
/*  60: 64 */         break;
/*  61:    */       default: 
/*  62: 67 */         this.field_146450_f.func_148147_a(p_146284_1_);
/*  63:    */       }
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void drawScreen(int par1, int par2, float par3)
/*  68:    */   {
/*  69: 77 */     this.field_146450_f.func_148128_a(par1, par2, par3);
/*  70: 78 */     drawCenteredString(this.fontRendererObj, I18n.format("options.language", new Object[0]), width / 2, 16, 16777215);
/*  71: 79 */     drawCenteredString(this.fontRendererObj, "(" + I18n.format("options.languageWarning", new Object[0]) + ")", width / 2, height - 56, 8421504);
/*  72: 80 */     super.drawScreen(par1, par2, par3);
/*  73:    */   }
/*  74:    */   
/*  75:    */   class List
/*  76:    */     extends GuiSlot
/*  77:    */   {
/*  78: 85 */     private final List field_148176_l = Lists.newArrayList();
/*  79: 86 */     private final Map field_148177_m = Maps.newHashMap();
/*  80:    */     private static final String __OBFID = "CL_00000699";
/*  81:    */     
/*  82:    */     public List()
/*  83:    */     {
/*  84: 91 */       super(GuiLanguage.width, GuiLanguage.height, 32, GuiLanguage.height - 65 + 4, 18);
/*  85: 92 */       Iterator var2 = GuiLanguage.this.field_146454_h.getLanguages().iterator();
/*  86: 94 */       while (var2.hasNext())
/*  87:    */       {
/*  88: 96 */         Language var3 = (Language)var2.next();
/*  89: 97 */         this.field_148177_m.put(var3.getLanguageCode(), var3);
/*  90: 98 */         this.field_148176_l.add(var3.getLanguageCode());
/*  91:    */       }
/*  92:    */     }
/*  93:    */     
/*  94:    */     protected int getSize()
/*  95:    */     {
/*  96:104 */       return this.field_148176_l.size();
/*  97:    */     }
/*  98:    */     
/*  99:    */     protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_)
/* 100:    */     {
/* 101:109 */       Language var5 = (Language)this.field_148177_m.get(this.field_148176_l.get(p_148144_1_));
/* 102:110 */       GuiLanguage.this.field_146454_h.setCurrentLanguage(var5);
/* 103:111 */       GuiLanguage.this.field_146451_g.language = var5.getLanguageCode();
/* 104:112 */       GuiLanguage.this.mc.refreshResources();
/* 105:113 */       GuiLanguage.this.fontRendererObj.setUnicodeFlag((GuiLanguage.this.field_146454_h.isCurrentLocaleUnicode()) || (GuiLanguage.this.field_146451_g.forceUnicodeFont));
/* 106:114 */       GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.field_146454_h.isCurrentLanguageBidirectional());
/* 107:115 */       GuiLanguage.this.field_146452_r.displayString = I18n.format("gui.done", new Object[0]);
/* 108:116 */       GuiLanguage.this.field_146455_i.displayString = GuiLanguage.this.field_146451_g.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
/* 109:117 */       GuiLanguage.this.field_146451_g.saveOptions();
/* 110:    */     }
/* 111:    */     
/* 112:    */     protected boolean isSelected(int p_148131_1_)
/* 113:    */     {
/* 114:122 */       return ((String)this.field_148176_l.get(p_148131_1_)).equals(GuiLanguage.this.field_146454_h.getCurrentLanguage().getLanguageCode());
/* 115:    */     }
/* 116:    */     
/* 117:    */     protected int func_148138_e()
/* 118:    */     {
/* 119:127 */       return getSize() * 18;
/* 120:    */     }
/* 121:    */     
/* 122:    */     protected void drawBackground()
/* 123:    */     {
/* 124:132 */       GuiLanguage.this.drawDefaultBackground();
/* 125:    */     }
/* 126:    */     
/* 127:    */     protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_)
/* 128:    */     {
/* 129:137 */       GuiLanguage.this.fontRendererObj.setBidiFlag(true);
/* 130:138 */       GuiLanguage.drawCenteredString(GuiLanguage.this.fontRendererObj, ((Language)this.field_148177_m.get(this.field_148176_l.get(p_148126_1_))).toString(), this.field_148155_a / 2, p_148126_3_ + 1, 16777215);
/* 131:139 */       GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.field_146454_h.getCurrentLanguage().isBidirectional());
/* 132:    */     }
/* 133:    */   }
/* 134:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiLanguage
 * JD-Core Version:    0.7.0.1
 */