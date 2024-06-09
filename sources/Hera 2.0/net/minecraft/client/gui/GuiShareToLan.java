/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ public class GuiShareToLan
/*     */   extends GuiScreen {
/*     */   private final GuiScreen field_146598_a;
/*     */   private GuiButton field_146596_f;
/*     */   private GuiButton field_146597_g;
/*  15 */   private String field_146599_h = "survival";
/*     */   
/*     */   private boolean field_146600_i;
/*     */   
/*     */   public GuiShareToLan(GuiScreen p_i1055_1_) {
/*  20 */     this.field_146598_a = p_i1055_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  29 */     this.buttonList.clear();
/*  30 */     this.buttonList.add(new GuiButton(101, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("lanServer.start", new Object[0])));
/*  31 */     this.buttonList.add(new GuiButton(102, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  32 */     this.buttonList.add(this.field_146597_g = new GuiButton(104, this.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
/*  33 */     this.buttonList.add(this.field_146596_f = new GuiButton(103, this.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
/*  34 */     func_146595_g();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_146595_g() {
/*  39 */     this.field_146597_g.displayString = String.valueOf(I18n.format("selectWorld.gameMode", new Object[0])) + " " + I18n.format("selectWorld.gameMode." + this.field_146599_h, new Object[0]);
/*  40 */     this.field_146596_f.displayString = String.valueOf(I18n.format("selectWorld.allowCommands", new Object[0])) + " ";
/*     */     
/*  42 */     if (this.field_146600_i) {
/*     */       
/*  44 */       this.field_146596_f.displayString = String.valueOf(this.field_146596_f.displayString) + I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/*  48 */       this.field_146596_f.displayString = String.valueOf(this.field_146596_f.displayString) + I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  57 */     if (button.id == 102) {
/*     */       
/*  59 */       this.mc.displayGuiScreen(this.field_146598_a);
/*     */     }
/*  61 */     else if (button.id == 104) {
/*     */       
/*  63 */       if (this.field_146599_h.equals("spectator")) {
/*     */         
/*  65 */         this.field_146599_h = "creative";
/*     */       }
/*  67 */       else if (this.field_146599_h.equals("creative")) {
/*     */         
/*  69 */         this.field_146599_h = "adventure";
/*     */       }
/*  71 */       else if (this.field_146599_h.equals("adventure")) {
/*     */         
/*  73 */         this.field_146599_h = "survival";
/*     */       }
/*     */       else {
/*     */         
/*  77 */         this.field_146599_h = "spectator";
/*     */       } 
/*     */       
/*  80 */       func_146595_g();
/*     */     }
/*  82 */     else if (button.id == 103) {
/*     */       
/*  84 */       this.field_146600_i = !this.field_146600_i;
/*  85 */       func_146595_g();
/*     */     }
/*  87 */     else if (button.id == 101) {
/*     */       ChatComponentText chatComponentText;
/*  89 */       this.mc.displayGuiScreen(null);
/*  90 */       String s = this.mc.getIntegratedServer().shareToLAN(WorldSettings.GameType.getByName(this.field_146599_h), this.field_146600_i);
/*     */ 
/*     */       
/*  93 */       if (s != null) {
/*     */         
/*  95 */         ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.publish.started", new Object[] { s });
/*     */       }
/*     */       else {
/*     */         
/*  99 */         chatComponentText = new ChatComponentText("commands.publish.failed");
/*     */       } 
/*     */       
/* 102 */       this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatComponentText);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 111 */     drawDefaultBackground();
/* 112 */     drawCenteredString(this.fontRendererObj, I18n.format("lanServer.title", new Object[0]), this.width / 2, 50, 16777215);
/* 113 */     drawCenteredString(this.fontRendererObj, I18n.format("lanServer.otherPlayers", new Object[0]), this.width / 2, 82, 16777215);
/* 114 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiShareToLan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */