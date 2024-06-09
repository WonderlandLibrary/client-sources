/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import me.eagler.gui.stuff.HeraButton;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ public class GuiRenameWorld
/*     */   extends GuiScreen
/*     */ {
/*     */   private GuiScreen parentScreen;
/*     */   private GuiTextField field_146583_f;
/*     */   private final String saveName;
/*     */   
/*     */   public GuiRenameWorld(GuiScreen parentScreenIn, String saveNameIn) {
/*  19 */     this.parentScreen = parentScreenIn;
/*  20 */     this.saveName = saveNameIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  28 */     this.field_146583_f.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  37 */     Keyboard.enableRepeatEvents(true);
/*  38 */     this.buttonList.clear();
/*  39 */     this.buttonList.add(new HeraButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, 200, 20, I18n.format("selectWorld.renameButton", new Object[0])));
/*  40 */     this.buttonList.add(new HeraButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 20, I18n.format("gui.cancel", new Object[0])));
/*  41 */     ISaveFormat isaveformat = this.mc.getSaveLoader();
/*  42 */     WorldInfo worldinfo = isaveformat.getWorldInfo(this.saveName);
/*  43 */     String s = worldinfo.getWorldName();
/*  44 */     this.field_146583_f = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
/*  45 */     this.field_146583_f.setFocused(true);
/*  46 */     this.field_146583_f.setText(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  54 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  62 */     if (button.enabled)
/*     */     {
/*  64 */       if (button.id == 1) {
/*     */         
/*  66 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/*  68 */       else if (button.id == 0) {
/*     */         
/*  70 */         ISaveFormat isaveformat = this.mc.getSaveLoader();
/*  71 */         isaveformat.renameWorld(this.saveName, this.field_146583_f.getText().trim());
/*  72 */         this.mc.displayGuiScreen(this.parentScreen);
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
/*  83 */     this.field_146583_f.textboxKeyTyped(typedChar, keyCode);
/*  84 */     ((GuiButton)this.buttonList.get(0)).enabled = (this.field_146583_f.getText().trim().length() > 0);
/*     */     
/*  86 */     if (keyCode == 28 || keyCode == 156)
/*     */     {
/*  88 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  97 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*  98 */     this.field_146583_f.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 106 */     drawDefaultBackground();
/* 107 */     drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.renameTitle", new Object[0]), this.width / 2, 20, 16777215);
/* 108 */     drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), this.width / 2 - 100, 47, 10526880);
/* 109 */     this.field_146583_f.drawTextBox();
/* 110 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiRenameWorld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */