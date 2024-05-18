/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import me.eagler.gui.stuff.HeraButton;
/*     */ import net.minecraft.client.multiplayer.GuiConnecting;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.multiplayer.ServerList;
/*     */ import net.minecraft.client.network.LanServerDetector;
/*     */ import net.minecraft.client.network.OldServerPinger;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiMultiplayer
/*     */   extends GuiScreen
/*     */   implements GuiYesNoCallback
/*     */ {
/*  22 */   private static final Logger logger = LogManager.getLogger();
/*  23 */   private final OldServerPinger oldServerPinger = new OldServerPinger();
/*     */   
/*     */   private GuiScreen parentScreen;
/*     */   
/*     */   private ServerSelectionList serverListSelector;
/*     */   
/*     */   private ServerList savedServerList;
/*     */   
/*     */   private GuiButton btnEditServer;
/*     */   
/*     */   private GuiButton btnSelectServer;
/*     */   private GuiButton btnDeleteServer;
/*     */   private boolean deletingServer;
/*     */   private boolean addingServer;
/*     */   private boolean editingServer;
/*     */   private boolean directConnect;
/*     */   private String hoveringText;
/*     */   private ServerData selectedServer;
/*     */   private LanServerDetector.LanServerList lanServerList;
/*     */   private LanServerDetector.ThreadLanServerFind lanServerDetector;
/*     */   private boolean initialized;
/*     */   
/*     */   public GuiMultiplayer(GuiScreen parentScreen) {
/*  46 */     this.parentScreen = parentScreen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  55 */     Keyboard.enableRepeatEvents(true);
/*  56 */     this.buttonList.clear();
/*     */     
/*  58 */     if (!this.initialized) {
/*     */       
/*  60 */       this.initialized = true;
/*  61 */       this.savedServerList = new ServerList(this.mc);
/*  62 */       this.savedServerList.loadServerList();
/*  63 */       this.lanServerList = new LanServerDetector.LanServerList();
/*     */ 
/*     */       
/*     */       try {
/*  67 */         this.lanServerDetector = new LanServerDetector.ThreadLanServerFind(this.lanServerList);
/*  68 */         this.lanServerDetector.start();
/*     */       }
/*  70 */       catch (Exception exception) {
/*     */         
/*  72 */         logger.warn("Unable to start LAN server detection: " + exception.getMessage());
/*     */       } 
/*     */       
/*  75 */       this.serverListSelector = new ServerSelectionList(this, this.mc, this.width, this.height, 32, this.height - 64, 36);
/*  76 */       this.serverListSelector.func_148195_a(this.savedServerList);
/*     */     }
/*     */     else {
/*     */       
/*  80 */       this.serverListSelector.setDimensions(this.width, this.height, 32, this.height - 64);
/*     */     } 
/*     */     
/*  83 */     createButtons();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  91 */     super.handleMouseInput();
/*  92 */     this.serverListSelector.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   public void createButtons() {
/*  97 */     this.buttonList.add(this.btnEditServer = (GuiButton)new HeraButton(7, this.width / 2 - 154, this.height - 28, 70, 20, I18n.format("selectServer.edit", new Object[0])));
/*  98 */     this.buttonList.add(this.btnDeleteServer = (GuiButton)new HeraButton(2, this.width / 2 - 74, this.height - 28, 70, 20, I18n.format("selectServer.delete", new Object[0])));
/*  99 */     this.buttonList.add(this.btnSelectServer = (GuiButton)new HeraButton(1, this.width / 2 - 154, this.height - 52, 100, 20, I18n.format("selectServer.select", new Object[0])));
/* 100 */     this.buttonList.add(new HeraButton(4, this.width / 2 - 50, this.height - 52, 100, 20, I18n.format("selectServer.direct", new Object[0])));
/* 101 */     this.buttonList.add(new HeraButton(3, this.width / 2 + 4 + 50, this.height - 52, 100, 20, I18n.format("selectServer.add", new Object[0])));
/* 102 */     this.buttonList.add(new HeraButton(8, this.width / 2 + 4, this.height - 28, 70, 20, I18n.format("selectServer.refresh", new Object[0])));
/* 103 */     this.buttonList.add(new HeraButton(0, this.width / 2 + 4 + 76, this.height - 28, 75, 20, I18n.format("gui.cancel", new Object[0])));
/* 104 */     selectServer(this.serverListSelector.func_148193_k());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 112 */     super.updateScreen();
/*     */     
/* 114 */     if (this.lanServerList.getWasUpdated()) {
/*     */       
/* 116 */       List<LanServerDetector.LanServer> list = this.lanServerList.getLanServers();
/* 117 */       this.lanServerList.setWasNotUpdated();
/* 118 */       this.serverListSelector.func_148194_a(list);
/*     */     } 
/*     */     
/* 121 */     this.oldServerPinger.pingPendingNetworks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 129 */     Keyboard.enableRepeatEvents(false);
/*     */     
/* 131 */     if (this.lanServerDetector != null) {
/*     */       
/* 133 */       this.lanServerDetector.interrupt();
/* 134 */       this.lanServerDetector = null;
/*     */     } 
/*     */     
/* 137 */     this.oldServerPinger.clearPendingNetworks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 145 */     if (button.enabled) {
/*     */       
/* 147 */       GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.func_148193_k() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
/*     */       
/* 149 */       if (button.id == 2 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/*     */         
/* 151 */         String s4 = (((ServerListEntryNormal)guilistextended$iguilistentry).getServerData()).serverName;
/*     */         
/* 153 */         if (s4 != null)
/*     */         {
/* 155 */           this.deletingServer = true;
/* 156 */           String s = I18n.format("selectServer.deleteQuestion", new Object[0]);
/* 157 */           String s1 = "'" + s4 + "' " + I18n.format("selectServer.deleteWarning", new Object[0]);
/* 158 */           String s2 = I18n.format("selectServer.deleteButton", new Object[0]);
/* 159 */           String s3 = I18n.format("gui.cancel", new Object[0]);
/* 160 */           GuiYesNo guiyesno = new GuiYesNo(this, s, s1, s2, s3, this.serverListSelector.func_148193_k());
/* 161 */           this.mc.displayGuiScreen(guiyesno);
/*     */         }
/*     */       
/* 164 */       } else if (button.id == 1) {
/*     */         
/* 166 */         connectToSelected();
/*     */       }
/* 168 */       else if (button.id == 4) {
/*     */         
/* 170 */         this.directConnect = true;
/* 171 */         this.mc.displayGuiScreen(new GuiScreenServerList(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "", false)));
/*     */       }
/* 173 */       else if (button.id == 3) {
/*     */         
/* 175 */         this.addingServer = true;
/* 176 */         this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "", false)));
/*     */       }
/* 178 */       else if (button.id == 7 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/*     */         
/* 180 */         this.editingServer = true;
/* 181 */         ServerData serverdata = ((ServerListEntryNormal)guilistextended$iguilistentry).getServerData();
/* 182 */         this.selectedServer = new ServerData(serverdata.serverName, serverdata.serverIP, false);
/* 183 */         this.selectedServer.copyFrom(serverdata);
/* 184 */         this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer));
/*     */       }
/* 186 */       else if (button.id == 0) {
/*     */         
/* 188 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/* 190 */       else if (button.id == 8) {
/*     */         
/* 192 */         refreshServerList();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void refreshServerList() {
/* 199 */     this.mc.displayGuiScreen(new GuiMultiplayer(this.parentScreen));
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 204 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.func_148193_k() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
/*     */     
/* 206 */     if (this.deletingServer) {
/*     */       
/* 208 */       this.deletingServer = false;
/*     */       
/* 210 */       if (result && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/*     */         
/* 212 */         this.savedServerList.removeServerData(this.serverListSelector.func_148193_k());
/* 213 */         this.savedServerList.saveServerList();
/* 214 */         this.serverListSelector.setSelectedSlotIndex(-1);
/* 215 */         this.serverListSelector.func_148195_a(this.savedServerList);
/*     */       } 
/*     */       
/* 218 */       this.mc.displayGuiScreen(this);
/*     */     }
/* 220 */     else if (this.directConnect) {
/*     */       
/* 222 */       this.directConnect = false;
/*     */       
/* 224 */       if (result)
/*     */       {
/* 226 */         connectToServer(this.selectedServer);
/*     */       }
/*     */       else
/*     */       {
/* 230 */         this.mc.displayGuiScreen(this);
/*     */       }
/*     */     
/* 233 */     } else if (this.addingServer) {
/*     */       
/* 235 */       this.addingServer = false;
/*     */       
/* 237 */       if (result) {
/*     */         
/* 239 */         this.savedServerList.addServerData(this.selectedServer);
/* 240 */         this.savedServerList.saveServerList();
/* 241 */         this.serverListSelector.setSelectedSlotIndex(-1);
/* 242 */         this.serverListSelector.func_148195_a(this.savedServerList);
/*     */       } 
/*     */       
/* 245 */       this.mc.displayGuiScreen(this);
/*     */     }
/* 247 */     else if (this.editingServer) {
/*     */       
/* 249 */       this.editingServer = false;
/*     */       
/* 251 */       if (result && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/*     */         
/* 253 */         ServerData serverdata = ((ServerListEntryNormal)guilistextended$iguilistentry).getServerData();
/* 254 */         serverdata.serverName = this.selectedServer.serverName;
/* 255 */         serverdata.serverIP = this.selectedServer.serverIP;
/* 256 */         serverdata.copyFrom(this.selectedServer);
/* 257 */         this.savedServerList.saveServerList();
/* 258 */         this.serverListSelector.func_148195_a(this.savedServerList);
/*     */       } 
/*     */       
/* 261 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 271 */     int i = this.serverListSelector.func_148193_k();
/* 272 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (i < 0) ? null : this.serverListSelector.getListEntry(i);
/*     */     
/* 274 */     if (keyCode == 63) {
/*     */       
/* 276 */       refreshServerList();
/*     */ 
/*     */     
/*     */     }
/* 280 */     else if (i >= 0) {
/*     */       
/* 282 */       if (keyCode == 200) {
/*     */         
/* 284 */         if (isShiftKeyDown()) {
/*     */           
/* 286 */           if (i > 0 && guilistextended$iguilistentry instanceof ServerListEntryNormal)
/*     */           {
/* 288 */             this.savedServerList.swapServers(i, i - 1);
/* 289 */             selectServer(this.serverListSelector.func_148193_k() - 1);
/* 290 */             this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
/* 291 */             this.serverListSelector.func_148195_a(this.savedServerList);
/*     */           }
/*     */         
/* 294 */         } else if (i > 0) {
/*     */           
/* 296 */           selectServer(this.serverListSelector.func_148193_k() - 1);
/* 297 */           this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
/*     */           
/* 299 */           if (this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan)
/*     */           {
/* 301 */             if (this.serverListSelector.func_148193_k() > 0)
/*     */             {
/* 303 */               selectServer(this.serverListSelector.getSize() - 1);
/* 304 */               this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
/*     */             }
/*     */             else
/*     */             {
/* 308 */               selectServer(-1);
/*     */             }
/*     */           
/*     */           }
/*     */         } else {
/*     */           
/* 314 */           selectServer(-1);
/*     */         }
/*     */       
/* 317 */       } else if (keyCode == 208) {
/*     */         
/* 319 */         if (isShiftKeyDown()) {
/*     */           
/* 321 */           if (i < this.savedServerList.countServers() - 1)
/*     */           {
/* 323 */             this.savedServerList.swapServers(i, i + 1);
/* 324 */             selectServer(i + 1);
/* 325 */             this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
/* 326 */             this.serverListSelector.func_148195_a(this.savedServerList);
/*     */           }
/*     */         
/* 329 */         } else if (i < this.serverListSelector.getSize()) {
/*     */           
/* 331 */           selectServer(this.serverListSelector.func_148193_k() + 1);
/* 332 */           this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
/*     */           
/* 334 */           if (this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan)
/*     */           {
/* 336 */             if (this.serverListSelector.func_148193_k() < this.serverListSelector.getSize() - 1)
/*     */             {
/* 338 */               selectServer(this.serverListSelector.getSize() + 1);
/* 339 */               this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
/*     */             }
/*     */             else
/*     */             {
/* 343 */               selectServer(-1);
/*     */             }
/*     */           
/*     */           }
/*     */         } else {
/*     */           
/* 349 */           selectServer(-1);
/*     */         }
/*     */       
/* 352 */       } else if (keyCode != 28 && keyCode != 156) {
/*     */         
/* 354 */         super.keyTyped(typedChar, keyCode);
/*     */       }
/*     */       else {
/*     */         
/* 358 */         actionPerformed(this.buttonList.get(2));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 363 */       super.keyTyped(typedChar, keyCode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 373 */     this.hoveringText = null;
/* 374 */     drawDefaultBackground();
/* 375 */     this.serverListSelector.drawScreen(mouseX, mouseY, partialTicks);
/* 376 */     drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.title", new Object[0]), this.width / 2, 20, 16777215);
/* 377 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 379 */     if (this.hoveringText != null)
/*     */     {
/* 381 */       drawHoveringText(Lists.newArrayList(Splitter.on("\n").split(this.hoveringText)), mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void connectToSelected() {
/* 387 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.func_148193_k() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
/*     */     
/* 389 */     if (guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/*     */       
/* 391 */       connectToServer(((ServerListEntryNormal)guilistextended$iguilistentry).getServerData());
/*     */     }
/* 393 */     else if (guilistextended$iguilistentry instanceof ServerListEntryLanDetected) {
/*     */       
/* 395 */       LanServerDetector.LanServer lanserverdetector$lanserver = ((ServerListEntryLanDetected)guilistextended$iguilistentry).getLanServer();
/* 396 */       connectToServer(new ServerData(lanserverdetector$lanserver.getServerMotd(), lanserverdetector$lanserver.getServerIpPort(), true));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void connectToServer(ServerData server) {
/* 402 */     this.mc.displayGuiScreen((GuiScreen)new GuiConnecting(this, this.mc, server));
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectServer(int index) {
/* 407 */     this.serverListSelector.setSelectedSlotIndex(index);
/* 408 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (index < 0) ? null : this.serverListSelector.getListEntry(index);
/* 409 */     this.btnSelectServer.enabled = false;
/* 410 */     this.btnEditServer.enabled = false;
/* 411 */     this.btnDeleteServer.enabled = false;
/*     */     
/* 413 */     if (guilistextended$iguilistentry != null && !(guilistextended$iguilistentry instanceof ServerListEntryLanScan)) {
/*     */       
/* 415 */       this.btnSelectServer.enabled = true;
/*     */       
/* 417 */       if (guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/*     */         
/* 419 */         this.btnEditServer.enabled = true;
/* 420 */         this.btnDeleteServer.enabled = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public OldServerPinger getOldServerPinger() {
/* 427 */     return this.oldServerPinger;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHoveringText(String p_146793_1_) {
/* 432 */     this.hoveringText = p_146793_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 440 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 441 */     this.serverListSelector.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 449 */     super.mouseReleased(mouseX, mouseY, state);
/* 450 */     this.serverListSelector.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerList getServerList() {
/* 455 */     return this.savedServerList;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_175392_a(ServerListEntryNormal p_175392_1_, int p_175392_2_) {
/* 460 */     return (p_175392_2_ > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_175394_b(ServerListEntryNormal p_175394_1_, int p_175394_2_) {
/* 465 */     return (p_175394_2_ < this.savedServerList.countServers() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175391_a(ServerListEntryNormal p_175391_1_, int p_175391_2_, boolean p_175391_3_) {
/* 470 */     int i = p_175391_3_ ? 0 : (p_175391_2_ - 1);
/* 471 */     this.savedServerList.swapServers(p_175391_2_, i);
/*     */     
/* 473 */     if (this.serverListSelector.func_148193_k() == p_175391_2_)
/*     */     {
/* 475 */       selectServer(i);
/*     */     }
/*     */     
/* 478 */     this.serverListSelector.func_148195_a(this.savedServerList);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175393_b(ServerListEntryNormal p_175393_1_, int p_175393_2_, boolean p_175393_3_) {
/* 483 */     int i = p_175393_3_ ? (this.savedServerList.countServers() - 1) : (p_175393_2_ + 1);
/* 484 */     this.savedServerList.swapServers(p_175393_2_, i);
/*     */     
/* 486 */     if (this.serverListSelector.func_148193_k() == p_175393_2_)
/*     */     {
/* 488 */       selectServer(i);
/*     */     }
/*     */     
/* 491 */     this.serverListSelector.func_148195_a(this.savedServerList);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiMultiplayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */