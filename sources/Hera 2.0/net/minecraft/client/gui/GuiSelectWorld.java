/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import me.eagler.gui.stuff.HeraButton;
/*     */ import net.minecraft.client.AnvilConverterException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.SaveFormatComparator;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiSelectWorld
/*     */   extends GuiScreen
/*     */   implements GuiYesNoCallback {
/*  25 */   private static final Logger logger = LogManager.getLogger();
/*  26 */   private final DateFormat field_146633_h = new SimpleDateFormat();
/*     */   protected GuiScreen parentScreen;
/*  28 */   protected String field_146628_f = "Select world";
/*     */   private boolean field_146634_i;
/*     */   private int field_146640_r;
/*     */   private java.util.List<SaveFormatComparator> field_146639_s;
/*     */   private List field_146638_t;
/*     */   private String field_146637_u;
/*     */   private String field_146636_v;
/*  35 */   private String[] field_146635_w = new String[4];
/*     */   
/*     */   private boolean field_146643_x;
/*     */   private GuiButton deleteButton;
/*     */   private GuiButton selectButton;
/*     */   private GuiButton renameButton;
/*     */   private GuiButton recreateButton;
/*     */   
/*     */   public GuiSelectWorld(GuiScreen parentScreenIn) {
/*  44 */     this.parentScreen = parentScreenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  53 */     this.field_146628_f = I18n.format("selectWorld.title", new Object[0]);
/*     */ 
/*     */     
/*     */     try {
/*  57 */       func_146627_h();
/*     */     }
/*  59 */     catch (AnvilConverterException anvilconverterexception) {
/*     */       
/*  61 */       logger.error("Couldn't load level list", (Throwable)anvilconverterexception);
/*  62 */       this.mc.displayGuiScreen(new GuiErrorScreen("Unable to load worlds", anvilconverterexception.getMessage()));
/*     */       
/*     */       return;
/*     */     } 
/*  66 */     this.field_146637_u = I18n.format("selectWorld.world", new Object[0]);
/*  67 */     this.field_146636_v = I18n.format("selectWorld.conversion", new Object[0]);
/*  68 */     this.field_146635_w[WorldSettings.GameType.SURVIVAL.getID()] = I18n.format("gameMode.survival", new Object[0]);
/*  69 */     this.field_146635_w[WorldSettings.GameType.CREATIVE.getID()] = I18n.format("gameMode.creative", new Object[0]);
/*  70 */     this.field_146635_w[WorldSettings.GameType.ADVENTURE.getID()] = I18n.format("gameMode.adventure", new Object[0]);
/*  71 */     this.field_146635_w[WorldSettings.GameType.SPECTATOR.getID()] = I18n.format("gameMode.spectator", new Object[0]);
/*  72 */     this.field_146638_t = new List(this.mc);
/*  73 */     this.field_146638_t.registerScrollButtons(4, 5);
/*  74 */     func_146618_g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  82 */     super.handleMouseInput();
/*  83 */     this.field_146638_t.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_146627_h() throws AnvilConverterException {
/*  88 */     ISaveFormat isaveformat = this.mc.getSaveLoader();
/*  89 */     this.field_146639_s = isaveformat.getSaveList();
/*  90 */     Collections.sort(this.field_146639_s);
/*  91 */     this.field_146640_r = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String func_146621_a(int p_146621_1_) {
/*  96 */     return ((SaveFormatComparator)this.field_146639_s.get(p_146621_1_)).getFileName();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String func_146614_d(int p_146614_1_) {
/* 101 */     String s = ((SaveFormatComparator)this.field_146639_s.get(p_146614_1_)).getDisplayName();
/*     */     
/* 103 */     if (StringUtils.isEmpty(s))
/*     */     {
/* 105 */       s = String.valueOf(I18n.format("selectWorld.world", new Object[0])) + " " + (p_146614_1_ + 1);
/*     */     }
/*     */     
/* 108 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146618_g() {
/* 113 */     this.buttonList.add(this.selectButton = (GuiButton)new HeraButton(1, this.width / 2 - 154, this.height - 52, 150, 20, I18n.format("selectWorld.select", new Object[0])));
/* 114 */     this.buttonList.add(new HeraButton(3, this.width / 2 + 4, this.height - 52, 150, 20, I18n.format("selectWorld.create", new Object[0])));
/* 115 */     this.buttonList.add(this.renameButton = (GuiButton)new HeraButton(6, this.width / 2 - 154, this.height - 28, 72, 20, I18n.format("selectWorld.rename", new Object[0])));
/* 116 */     this.buttonList.add(this.deleteButton = (GuiButton)new HeraButton(2, this.width / 2 - 76, this.height - 28, 72, 20, I18n.format("selectWorld.delete", new Object[0])));
/* 117 */     this.buttonList.add(this.recreateButton = (GuiButton)new HeraButton(7, this.width / 2 + 4, this.height - 28, 72, 20, I18n.format("selectWorld.recreate", new Object[0])));
/* 118 */     this.buttonList.add(new HeraButton(0, this.width / 2 + 82, this.height - 28, 72, 20, I18n.format("gui.cancel", new Object[0])));
/* 119 */     this.selectButton.enabled = false;
/* 120 */     this.deleteButton.enabled = false;
/* 121 */     this.renameButton.enabled = false;
/* 122 */     this.recreateButton.enabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 130 */     if (button.enabled)
/*     */     {
/* 132 */       if (button.id == 2) {
/*     */         
/* 134 */         String s = func_146614_d(this.field_146640_r);
/*     */         
/* 136 */         if (s != null)
/*     */         {
/* 138 */           this.field_146643_x = true;
/* 139 */           GuiYesNo guiyesno = func_152129_a(this, s, this.field_146640_r);
/* 140 */           this.mc.displayGuiScreen(guiyesno);
/*     */         }
/*     */       
/* 143 */       } else if (button.id == 1) {
/*     */         
/* 145 */         func_146615_e(this.field_146640_r);
/*     */       }
/* 147 */       else if (button.id == 3) {
/*     */         
/* 149 */         this.mc.displayGuiScreen(new GuiCreateWorld(this));
/*     */       }
/* 151 */       else if (button.id == 6) {
/*     */         
/* 153 */         this.mc.displayGuiScreen(new GuiRenameWorld(this, func_146621_a(this.field_146640_r)));
/*     */       }
/* 155 */       else if (button.id == 0) {
/*     */         
/* 157 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/* 159 */       else if (button.id == 7) {
/*     */         
/* 161 */         GuiCreateWorld guicreateworld = new GuiCreateWorld(this);
/* 162 */         ISaveHandler isavehandler = this.mc.getSaveLoader().getSaveLoader(func_146621_a(this.field_146640_r), false);
/* 163 */         WorldInfo worldinfo = isavehandler.loadWorldInfo();
/* 164 */         isavehandler.flush();
/* 165 */         guicreateworld.func_146318_a(worldinfo);
/* 166 */         this.mc.displayGuiScreen(guicreateworld);
/*     */       }
/*     */       else {
/*     */         
/* 170 */         this.field_146638_t.actionPerformed(button);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146615_e(int p_146615_1_) {
/* 177 */     this.mc.displayGuiScreen(null);
/*     */     
/* 179 */     if (!this.field_146634_i) {
/*     */       
/* 181 */       this.field_146634_i = true;
/* 182 */       String s = func_146621_a(p_146615_1_);
/*     */       
/* 184 */       if (s == null)
/*     */       {
/* 186 */         s = "World" + p_146615_1_;
/*     */       }
/*     */       
/* 189 */       String s1 = func_146614_d(p_146615_1_);
/*     */       
/* 191 */       if (s1 == null)
/*     */       {
/* 193 */         s1 = "World" + p_146615_1_;
/*     */       }
/*     */       
/* 196 */       if (this.mc.getSaveLoader().canLoadWorld(s))
/*     */       {
/* 198 */         this.mc.launchIntegratedServer(s, s1, null);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 205 */     if (this.field_146643_x) {
/*     */       
/* 207 */       this.field_146643_x = false;
/*     */       
/* 209 */       if (result) {
/*     */         
/* 211 */         ISaveFormat isaveformat = this.mc.getSaveLoader();
/* 212 */         isaveformat.flushCache();
/* 213 */         isaveformat.deleteWorldDirectory(func_146621_a(id));
/*     */ 
/*     */         
/*     */         try {
/* 217 */           func_146627_h();
/*     */         }
/* 219 */         catch (AnvilConverterException anvilconverterexception) {
/*     */           
/* 221 */           logger.error("Couldn't load level list", (Throwable)anvilconverterexception);
/*     */         } 
/*     */       } 
/*     */       
/* 225 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 234 */     this.field_146638_t.drawScreen(mouseX, mouseY, partialTicks);
/* 235 */     drawCenteredString(this.fontRendererObj, this.field_146628_f, this.width / 2, 20, 16777215);
/* 236 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public static GuiYesNo func_152129_a(GuiYesNoCallback p_152129_0_, String p_152129_1_, int p_152129_2_) {
/* 241 */     String s = I18n.format("selectWorld.deleteQuestion", new Object[0]);
/* 242 */     String s1 = "'" + p_152129_1_ + "' " + I18n.format("selectWorld.deleteWarning", new Object[0]);
/* 243 */     String s2 = I18n.format("selectWorld.deleteButton", new Object[0]);
/* 244 */     String s3 = I18n.format("gui.cancel", new Object[0]);
/* 245 */     GuiYesNo guiyesno = new GuiYesNo(p_152129_0_, s, s1, s2, s3, p_152129_2_);
/* 246 */     return guiyesno;
/*     */   }
/*     */   
/*     */   class List
/*     */     extends GuiSlot
/*     */   {
/*     */     public List(Minecraft mcIn) {
/* 253 */       super(mcIn, GuiSelectWorld.this.width, GuiSelectWorld.this.height, 32, GuiSelectWorld.this.height - 64, 36);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 258 */       return GuiSelectWorld.this.field_146639_s.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 263 */       GuiSelectWorld.this.field_146640_r = slotIndex;
/* 264 */       boolean flag = (GuiSelectWorld.this.field_146640_r >= 0 && GuiSelectWorld.this.field_146640_r < getSize());
/* 265 */       GuiSelectWorld.this.selectButton.enabled = flag;
/* 266 */       GuiSelectWorld.this.deleteButton.enabled = flag;
/* 267 */       GuiSelectWorld.this.renameButton.enabled = flag;
/* 268 */       GuiSelectWorld.this.recreateButton.enabled = flag;
/*     */       
/* 270 */       if (isDoubleClick && flag)
/*     */       {
/* 272 */         GuiSelectWorld.this.func_146615_e(slotIndex);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 278 */       return (slotIndex == GuiSelectWorld.this.field_146640_r);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getContentHeight() {
/* 283 */       return GuiSelectWorld.this.field_146639_s.size() * 36;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {
/* 288 */       GuiSelectWorld.this.drawDefaultBackground();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 293 */       SaveFormatComparator saveformatcomparator = GuiSelectWorld.this.field_146639_s.get(entryID);
/* 294 */       String s = saveformatcomparator.getDisplayName();
/*     */       
/* 296 */       if (StringUtils.isEmpty(s))
/*     */       {
/* 298 */         s = String.valueOf(GuiSelectWorld.this.field_146637_u) + " " + (entryID + 1);
/*     */       }
/*     */       
/* 301 */       String s1 = saveformatcomparator.getFileName();
/* 302 */       s1 = String.valueOf(s1) + " (" + GuiSelectWorld.this.field_146633_h.format(new Date(saveformatcomparator.getLastTimePlayed()));
/* 303 */       s1 = String.valueOf(s1) + ")";
/* 304 */       String s2 = "";
/*     */       
/* 306 */       if (saveformatcomparator.requiresConversion()) {
/*     */         
/* 308 */         s2 = String.valueOf(GuiSelectWorld.this.field_146636_v) + " " + s2;
/*     */       }
/*     */       else {
/*     */         
/* 312 */         s2 = GuiSelectWorld.this.field_146635_w[saveformatcomparator.getEnumGameType().getID()];
/*     */         
/* 314 */         if (saveformatcomparator.isHardcoreModeEnabled())
/*     */         {
/* 316 */           s2 = EnumChatFormatting.DARK_RED + I18n.format("gameMode.hardcore", new Object[0]) + EnumChatFormatting.RESET;
/*     */         }
/*     */         
/* 319 */         if (saveformatcomparator.getCheatsEnabled())
/*     */         {
/* 321 */           s2 = String.valueOf(s2) + ", " + I18n.format("selectWorld.cheats", new Object[0]);
/*     */         }
/*     */       } 
/*     */       
/* 325 */       GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, s, p_180791_2_ + 2, p_180791_3_ + 1, 16777215);
/* 326 */       GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, s1, p_180791_2_ + 2, p_180791_3_ + 12, 8421504);
/* 327 */       GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, s2, p_180791_2_ + 2, p_180791_3_ + 12 + 10, 8421504);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiSelectWorld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */