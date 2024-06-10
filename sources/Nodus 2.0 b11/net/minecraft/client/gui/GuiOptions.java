/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*   7:    */ import net.minecraft.client.audio.SoundCategory;
/*   8:    */ import net.minecraft.client.audio.SoundEventAccessorComposite;
/*   9:    */ import net.minecraft.client.audio.SoundHandler;
/*  10:    */ import net.minecraft.client.multiplayer.WorldClient;
/*  11:    */ import net.minecraft.client.renderer.EntityRenderer;
/*  12:    */ import net.minecraft.client.resources.I18n;
/*  13:    */ import net.minecraft.client.settings.GameSettings;
/*  14:    */ import net.minecraft.client.settings.GameSettings.Options;
/*  15:    */ import net.minecraft.world.storage.WorldInfo;
/*  16:    */ 
/*  17:    */ public class GuiOptions
/*  18:    */   extends GuiScreen
/*  19:    */ {
/*  20: 13 */   private static final GameSettings.Options[] field_146440_f = { GameSettings.Options.FOV, GameSettings.Options.DIFFICULTY };
/*  21:    */   private final GuiScreen field_146441_g;
/*  22:    */   private final GameSettings field_146443_h;
/*  23: 16 */   protected String field_146442_a = "Options";
/*  24:    */   private static final String __OBFID = "CL_00000700";
/*  25:    */   
/*  26:    */   public GuiOptions(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
/*  27:    */   {
/*  28: 21 */     this.field_146441_g = par1GuiScreen;
/*  29: 22 */     this.field_146443_h = par2GameSettings;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void initGui()
/*  33:    */   {
/*  34: 30 */     int var1 = 0;
/*  35: 31 */     this.field_146442_a = I18n.format("options.title", new Object[0]);
/*  36: 32 */     GameSettings.Options[] var2 = field_146440_f;
/*  37: 33 */     int var3 = var2.length;
/*  38: 35 */     for (int var4 = 0; var4 < var3; var4++)
/*  39:    */     {
/*  40: 37 */       GameSettings.Options var5 = var2[var4];
/*  41: 39 */       if (var5.getEnumFloat())
/*  42:    */       {
/*  43: 41 */         this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 - 12 + 24 * (var1 >> 1), var5));
/*  44:    */       }
/*  45:    */       else
/*  46:    */       {
/*  47: 45 */         GuiOptionButton var6 = new GuiOptionButton(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 - 12 + 24 * (var1 >> 1), var5, this.field_146443_h.getKeyBinding(var5));
/*  48: 47 */         if ((var5 == GameSettings.Options.DIFFICULTY) && (this.mc.theWorld != null) && (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()))
/*  49:    */         {
/*  50: 49 */           var6.enabled = false;
/*  51: 50 */           var6.displayString = (I18n.format("options.difficulty", new Object[0]) + ": " + I18n.format("options.difficulty.hardcore", new Object[0]));
/*  52:    */         }
/*  53: 53 */         this.buttonList.add(var6);
/*  54:    */       }
/*  55: 56 */       var1++;
/*  56:    */     }
/*  57: 59 */     this.buttonList.add(new NodusGuiButton(106, width / 2 - 152, height / 6 + 72 - 6, 150, 20, I18n.format("options.sounds", new Object[0])));
/*  58: 60 */     this.buttonList.add(new NodusGuiButton(8675309, width / 2 + 2, height / 6 + 72 - 6, 150, 20, "Super Secret Settings...")
/*  59:    */     {
/*  60:    */       private static final String __OBFID = "CL_00000701";
/*  61:    */       
/*  62:    */       public void func_146113_a(SoundHandler p_146113_1_)
/*  63:    */       {
/*  64: 65 */         SoundEventAccessorComposite var2 = p_146113_1_.func_147686_a(new SoundCategory[] { SoundCategory.ANIMALS, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.PLAYERS, SoundCategory.WEATHER });
/*  65: 67 */         if (var2 != null) {
/*  66: 69 */           p_146113_1_.playSound(PositionedSoundRecord.func_147674_a(var2.func_148729_c(), 0.5F));
/*  67:    */         }
/*  68:    */       }
/*  69: 72 */     });
/*  70: 73 */     this.buttonList.add(new NodusGuiButton(101, width / 2 - 152, height / 6 + 96 - 6, 150, 20, I18n.format("options.video", new Object[0])));
/*  71: 74 */     this.buttonList.add(new NodusGuiButton(100, width / 2 + 2, height / 6 + 96 - 6, 150, 20, I18n.format("options.controls", new Object[0])));
/*  72: 75 */     this.buttonList.add(new NodusGuiButton(102, width / 2 - 152, height / 6 + 120 - 6, 150, 20, I18n.format("options.language", new Object[0])));
/*  73: 76 */     this.buttonList.add(new NodusGuiButton(103, width / 2 + 2, height / 6 + 120 - 6, 150, 20, I18n.format("options.multiplayer.title", new Object[0])));
/*  74: 77 */     this.buttonList.add(new NodusGuiButton(105, width / 2 - 152, height / 6 + 144 - 6, 150, 20, I18n.format("options.resourcepack", new Object[0])));
/*  75: 78 */     this.buttonList.add(new NodusGuiButton(104, width / 2 + 2, height / 6 + 144 - 6, 150, 20, I18n.format("options.snooper.view", new Object[0])));
/*  76: 79 */     this.buttonList.add(new NodusGuiButton(200, width / 2 - 100, height / 6 + 168, I18n.format("gui.done", new Object[0])));
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  80:    */   {
/*  81: 84 */     if (p_146284_1_.enabled)
/*  82:    */     {
/*  83: 86 */       if ((p_146284_1_.id < 100) && ((p_146284_1_ instanceof GuiOptionButton)))
/*  84:    */       {
/*  85: 88 */         this.field_146443_h.setOptionValue(((GuiOptionButton)p_146284_1_).func_146136_c(), 1);
/*  86: 89 */         p_146284_1_.displayString = this.field_146443_h.getKeyBinding(GameSettings.Options.getEnumOptions(p_146284_1_.id));
/*  87:    */       }
/*  88: 92 */       if (p_146284_1_.id == 8675309) {
/*  89: 94 */         this.mc.entityRenderer.activateNextShader();
/*  90:    */       }
/*  91: 97 */       if (p_146284_1_.id == 101)
/*  92:    */       {
/*  93: 99 */         this.mc.gameSettings.saveOptions();
/*  94:100 */         this.mc.displayGuiScreen(new GuiVideoSettings(this, this.field_146443_h));
/*  95:    */       }
/*  96:103 */       if (p_146284_1_.id == 100)
/*  97:    */       {
/*  98:105 */         this.mc.gameSettings.saveOptions();
/*  99:106 */         this.mc.displayGuiScreen(new GuiControls(this, this.field_146443_h));
/* 100:    */       }
/* 101:109 */       if (p_146284_1_.id == 102)
/* 102:    */       {
/* 103:111 */         this.mc.gameSettings.saveOptions();
/* 104:112 */         this.mc.displayGuiScreen(new GuiLanguage(this, this.field_146443_h, this.mc.getLanguageManager()));
/* 105:    */       }
/* 106:115 */       if (p_146284_1_.id == 103)
/* 107:    */       {
/* 108:117 */         this.mc.gameSettings.saveOptions();
/* 109:118 */         this.mc.displayGuiScreen(new ScreenChatOptions(this, this.field_146443_h));
/* 110:    */       }
/* 111:121 */       if (p_146284_1_.id == 104)
/* 112:    */       {
/* 113:123 */         this.mc.gameSettings.saveOptions();
/* 114:124 */         this.mc.displayGuiScreen(new GuiSnooper(this, this.field_146443_h));
/* 115:    */       }
/* 116:127 */       if (p_146284_1_.id == 200)
/* 117:    */       {
/* 118:129 */         this.mc.gameSettings.saveOptions();
/* 119:130 */         this.mc.displayGuiScreen(this.field_146441_g);
/* 120:    */       }
/* 121:133 */       if (p_146284_1_.id == 105)
/* 122:    */       {
/* 123:135 */         this.mc.gameSettings.saveOptions();
/* 124:136 */         this.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
/* 125:    */       }
/* 126:139 */       if (p_146284_1_.id == 106)
/* 127:    */       {
/* 128:141 */         this.mc.gameSettings.saveOptions();
/* 129:142 */         this.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.field_146443_h));
/* 130:    */       }
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void drawScreen(int par1, int par2, float par3)
/* 135:    */   {
/* 136:152 */     drawDefaultBackground();
/* 137:153 */     drawCenteredString(this.fontRendererObj, this.field_146442_a, width / 2, 15, 16777215);
/* 138:154 */     super.drawScreen(par1, par2, par3);
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiOptions
 * JD-Core Version:    0.7.0.1
 */