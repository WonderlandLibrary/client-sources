/*   1:    */ package me.connorm.Nodus.ui.tab;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import me.connorm.Nodus.Nodus;
/*   5:    */ import me.connorm.Nodus.module.NodusModuleManager;
/*   6:    */ import me.connorm.Nodus.module.core.Category;
/*   7:    */ import me.connorm.Nodus.module.core.NodusModule;
/*   8:    */ import me.connorm.Nodus.ui.NodusGuiConsole;
/*   9:    */ import me.connorm.Nodus.ui.gui.theme.nodus.ColorUtils;
/*  10:    */ import me.connorm.Nodus.ui.gui.theme.nodus.NodusUtils;
/*  11:    */ import me.connorm.Nodus.utils.GuiManagerDisplayScreen;
/*  12:    */ import net.minecraft.client.Minecraft;
/*  13:    */ import net.minecraft.client.gui.FontRenderer;
/*  14:    */ 
/*  15:    */ public class NodusTabGui
/*  16:    */ {
/*  17: 17 */   public String colorNormal = "§f";
/*  18: 18 */   public String colorHighlight = "§a";
/*  19: 19 */   public String selectedItemChar = ">";
/*  20: 20 */   public int colorBorder = 16777215;
/*  21: 21 */   public int colorBody = -1728053248;
/*  22: 22 */   public int guiWidth = 75;
/*  23: 23 */   public int guiHeight = 0;
/*  24: 24 */   public int tabHeight = 12;
/*  25: 25 */   public int selectedTab = 0;
/*  26: 26 */   public int selectedItem = 0;
/*  27: 27 */   public boolean mainMenu = true;
/*  28:    */   public ArrayList tabsList;
/*  29:    */   private Minecraft mc;
/*  30:    */   public ClientModules ch;
/*  31:    */   public GuiHandler handler;
/*  32:    */   
/*  33:    */   public NodusTabGui(ClientModules var1, Minecraft var2)
/*  34:    */   {
/*  35: 35 */     this.mc = var2;
/*  36: 36 */     this.ch = var1;
/*  37: 37 */     this.tabsList = new ArrayList();
/*  38:    */     
/*  39: 39 */     GuiTab player = new GuiTab(this, "Player");
/*  40: 40 */     GuiTab world = new GuiTab(this, "World");
/*  41: 41 */     GuiTab render = new GuiTab(this, "Display");
/*  42: 42 */     GuiTab combat = new GuiTab(this, "Combat");
/*  43: 44 */     for (NodusModule m : Nodus.theNodus.moduleManager.getModules())
/*  44:    */     {
/*  45: 46 */       if (m.getCategory() == Category.PLAYER) {
/*  46: 48 */         player.hacks.add(new ItemModule(m.getName(), m.getName().toLowerCase(), true, var1));
/*  47:    */       }
/*  48: 50 */       if (m.getCategory() == Category.WORLD) {
/*  49: 52 */         world.hacks.add(new ItemModule(m.getName(), m.getName().toLowerCase(), true, var1));
/*  50:    */       }
/*  51: 54 */       if (m.getCategory() == Category.DISPLAY) {
/*  52: 56 */         render.hacks.add(new ItemModule(m.getName(), m.getName().toLowerCase(), true, var1));
/*  53:    */       }
/*  54: 58 */       if (m.getCategory() == Category.COMBAT) {
/*  55: 60 */         combat.hacks.add(new ItemModule(m.getName(), m.getName().toLowerCase(), true, var1));
/*  56:    */       }
/*  57:    */     }
/*  58: 64 */     this.tabsList.add(player);
/*  59: 65 */     this.tabsList.add(world);
/*  60: 66 */     this.tabsList.add(render);
/*  61: 67 */     this.tabsList.add(combat);
/*  62:    */     
/*  63: 69 */     this.guiHeight = (this.tabHeight + this.tabsList.size() * this.tabHeight);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void drawGui(FontRenderer var1)
/*  67:    */   {
/*  68: 74 */     if (((Nodus.theNodus.getMinecraft().currentScreen instanceof NodusGuiConsole)) || ((Nodus.theNodus.getMinecraft().currentScreen instanceof GuiManagerDisplayScreen))) {
/*  69: 75 */       return;
/*  70:    */     }
/*  71: 76 */     byte var2 = 2;
/*  72: 77 */     byte var3 = 0;
/*  73:    */     
/*  74: 79 */     NodusUtils.drawNodusTabRect(2.0F, 14.0F, 74.0F, 60.0F);
/*  75:    */     
/*  76:    */ 
/*  77:    */ 
/*  78: 83 */     int var4 = var3 + 14;
/*  79: 85 */     for (int var5 = 0; var5 < this.tabsList.size(); var5++)
/*  80:    */     {
/*  81: 87 */       var1.drawString((var5 == this.selectedTab ? this.selectedItemChar : "") + ((GuiTab)this.tabsList.get(var5)).tabName, var2 + 2, var4 + 1, var5 == this.selectedTab ? ColorUtils.secondaryColor : ColorUtils.primaryColor);
/*  82: 88 */       var1.drawString((var5 == this.selectedTab) && (!this.mainMenu) ? "<<" : ">>", var2 + this.guiWidth - 14, var4 + 1, var5 == this.selectedTab ? ColorUtils.secondaryColor : ColorUtils.primaryColor);
/*  83:    */       
/*  84: 90 */       this.tabsList.size();
/*  85: 95 */       if ((var5 == this.selectedTab) && (!this.mainMenu)) {
/*  86: 97 */         ((GuiTab)this.tabsList.get(var5)).drawTabMenu(var2 + this.guiWidth + 2, var4 + 1, var1);
/*  87:    */       }
/*  88:100 */       var4 += this.tabHeight;
/*  89:    */     }
/*  90:103 */     var4 += 5;
/*  91:105 */     if (this.ch.enabledHacks.size() >= 1)
/*  92:    */     {
/*  93:107 */       int var5 = this.ch.enabledHacks.size() * this.tabHeight;
/*  94:111 */       for (int var6 = 0; var6 < this.ch.enabledHacks.size(); var6++)
/*  95:    */       {
/*  96:113 */         var1.drawStringWithShadow(this.ch.enabledHacks.get(var6), var2 + 2, var4 + 2, ColorUtils.primaryColor);
/*  97:    */         
/*  98:115 */         this.ch.enabledHacks.size();
/*  99:    */         
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:120 */         var4 += this.tabHeight;
/* 104:    */       }
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void parseKeyUp()
/* 109:    */   {
/* 110:127 */     if (this.mainMenu)
/* 111:    */     {
/* 112:129 */       this.selectedTab -= 1;
/* 113:131 */       if (this.selectedTab < 0) {
/* 114:133 */         this.selectedTab = (this.tabsList.size() - 1);
/* 115:    */       }
/* 116:    */     }
/* 117:    */     else
/* 118:    */     {
/* 119:138 */       this.selectedItem -= 1;
/* 120:140 */       if (this.selectedItem < 0) {
/* 121:142 */         this.selectedItem = (((GuiTab)this.tabsList.get(this.selectedTab)).hacks.size() - 1);
/* 122:    */       }
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void parseKeyDown()
/* 127:    */   {
/* 128:149 */     if (this.mainMenu)
/* 129:    */     {
/* 130:151 */       this.selectedTab += 1;
/* 131:153 */       if (this.selectedTab > this.tabsList.size() - 1) {
/* 132:155 */         this.selectedTab = 0;
/* 133:    */       }
/* 134:    */     }
/* 135:    */     else
/* 136:    */     {
/* 137:160 */       this.selectedItem += 1;
/* 138:162 */       if (this.selectedItem > ((GuiTab)this.tabsList.get(this.selectedTab)).hacks.size() - 1) {
/* 139:164 */         this.selectedItem = 0;
/* 140:    */       }
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void parseKeyLeft()
/* 145:    */   {
/* 146:171 */     if (!this.mainMenu) {
/* 147:173 */       this.mainMenu = true;
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void parseKeyRight()
/* 152:    */   {
/* 153:179 */     if (this.mainMenu)
/* 154:    */     {
/* 155:181 */       this.mainMenu = false;
/* 156:182 */       this.selectedItem = 0;
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void parseKeyToggle()
/* 161:    */   {
/* 162:188 */     if (!this.mainMenu)
/* 163:    */     {
/* 164:190 */       int var1 = this.selectedItem;
/* 165:191 */       ((ItemModule)((GuiTab)this.tabsList.get(this.selectedTab)).hacks.get(var1)).exeHack();
/* 166:    */     }
/* 167:    */   }
/* 168:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.tab.NodusTabGui
 * JD-Core Version:    0.7.0.1
 */