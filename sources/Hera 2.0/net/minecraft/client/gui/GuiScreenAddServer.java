/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.io.IOException;
/*     */ import java.net.IDN;
/*     */ import me.eagler.gui.stuff.HeraButton;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiScreenAddServer
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen parentScreen;
/*     */   private final ServerData serverData;
/*     */   private GuiTextField serverIPField;
/*     */   private GuiTextField serverNameField;
/*     */   private GuiButton serverResourcePacks;
/*     */   
/*  20 */   private Predicate<String> field_181032_r = new Predicate<String>()
/*     */     {
/*     */       public boolean apply(String p_apply_1_)
/*     */       {
/*  24 */         if (p_apply_1_.length() == 0)
/*     */         {
/*  26 */           return true;
/*     */         }
/*     */ 
/*     */         
/*  30 */         String[] astring = p_apply_1_.split(":");
/*     */         
/*  32 */         if (astring.length == 0)
/*     */         {
/*  34 */           return true;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*  40 */           String s = IDN.toASCII(astring[0]);
/*  41 */           return true;
/*     */         }
/*  43 */         catch (IllegalArgumentException var4) {
/*     */           
/*  45 */           return false;
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiScreenAddServer(GuiScreen p_i1033_1_, ServerData p_i1033_2_) {
/*  54 */     this.parentScreen = p_i1033_1_;
/*  55 */     this.serverData = p_i1033_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  63 */     this.serverNameField.updateCursorCounter();
/*  64 */     this.serverIPField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  73 */     Keyboard.enableRepeatEvents(true);
/*  74 */     this.buttonList.clear();
/*  75 */     this.buttonList.add(new HeraButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, 200, 20, I18n.format("addServer.add", new Object[0])));
/*  76 */     this.buttonList.add(new HeraButton(1, this.width / 2 - 100, this.height / 4 + 120 + 18, 200, 20, I18n.format("gui.cancel", new Object[0])));
/*     */     
/*  78 */     this.serverNameField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 66, 200, 20);
/*  79 */     this.serverNameField.setFocused(true);
/*  80 */     this.serverNameField.setText(this.serverData.serverName);
/*  81 */     this.serverIPField = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, 106, 200, 20);
/*  82 */     this.serverIPField.setMaxStringLength(128);
/*  83 */     this.serverIPField.setText(this.serverData.serverIP);
/*  84 */     this.serverIPField.func_175205_a(this.field_181032_r);
/*  85 */     ((GuiButton)this.buttonList.get(0)).enabled = (this.serverIPField.getText().length() > 0 && (this.serverIPField.getText().split(":")).length > 0 && this.serverNameField.getText().length() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  93 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 101 */     if (button.enabled)
/*     */     {
/* 103 */       if (button.id == 2) {
/*     */         
/* 105 */         this.serverData.setResourceMode(ServerData.ServerResourceMode.values()[(this.serverData.getResourceMode().ordinal() + 1) % (ServerData.ServerResourceMode.values()).length]);
/* 106 */         this.serverResourcePacks.displayString = String.valueOf(I18n.format("addServer.resourcePack", new Object[0])) + ": " + this.serverData.getResourceMode().getMotd().getFormattedText();
/*     */       }
/* 108 */       else if (button.id == 1) {
/*     */         
/* 110 */         this.parentScreen.confirmClicked(false, 0);
/*     */       }
/* 112 */       else if (button.id == 0) {
/*     */         
/* 114 */         this.serverData.serverName = this.serverNameField.getText();
/* 115 */         this.serverData.serverIP = this.serverIPField.getText();
/* 116 */         this.parentScreen.confirmClicked(true, 0);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 127 */     this.serverNameField.textboxKeyTyped(typedChar, keyCode);
/* 128 */     this.serverIPField.textboxKeyTyped(typedChar, keyCode);
/*     */     
/* 130 */     if (keyCode == 15) {
/*     */       
/* 132 */       this.serverNameField.setFocused(!this.serverNameField.isFocused());
/* 133 */       this.serverIPField.setFocused(!this.serverIPField.isFocused());
/*     */     } 
/*     */     
/* 136 */     if (keyCode == 28 || keyCode == 156)
/*     */     {
/* 138 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*     */     
/* 141 */     ((GuiButton)this.buttonList.get(0)).enabled = (this.serverIPField.getText().length() > 0 && (this.serverIPField.getText().split(":")).length > 0 && this.serverNameField.getText().length() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 149 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 150 */     this.serverIPField.mouseClicked(mouseX, mouseY, mouseButton);
/* 151 */     this.serverNameField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 159 */     drawDefaultBackground();
/* 160 */     drawCenteredString(this.fontRendererObj, I18n.format("addServer.title", new Object[0]), this.width / 2, 17, 16777215);
/* 161 */     drawString(this.fontRendererObj, I18n.format("addServer.enterName", new Object[0]), this.width / 2 - 100, 53, 10526880);
/* 162 */     drawString(this.fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), this.width / 2 - 100, 94, 10526880);
/* 163 */     this.serverNameField.drawTextBox();
/* 164 */     this.serverIPField.drawTextBox();
/* 165 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiScreenAddServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */