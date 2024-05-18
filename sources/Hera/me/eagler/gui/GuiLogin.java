/*     */ package me.eagler.gui;
/*     */ 
/*     */ import com.mojang.authlib.Agent;
/*     */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*     */ import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
/*     */ import java.awt.Color;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.Clipboard;
/*     */ import java.awt.datatransfer.ClipboardOwner;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.io.IOException;
/*     */ import java.net.Proxy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import me.eagler.font.FontHelper;
/*     */ import me.eagler.gui.stuff.HeraShadowButton;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.util.Session;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiLogin
/*     */   extends GuiScreen
/*     */   implements ClipboardOwner
/*     */ {
/*     */   public GuiTextField email;
/*     */   public GuiTextField cracked;
/*     */   public GuiScreen oldScreen;
/*     */   public String status;
/*     */   public String lastPW;
/*     */   public String lastName;
/*  43 */   public static String name = "";
/*     */   
/*     */   public GuiLogin(GuiScreen old) {
/*  46 */     this.oldScreen = old;
/*     */   }
/*     */   
/*     */   public void initGui() {
/*  50 */     Keyboard.enableRepeatEvents(true);
/*  51 */     this.buttonList
/*  52 */       .add(new HeraShadowButton(1, this.width / 2 - 30 - 10, this.height / 4 + 48 + 72 - 35, 80, 15, "Login"));
/*  53 */     this.buttonList
/*  54 */       .add(new HeraShadowButton(3, this.width / 2 - 30 - 10, this.height / 4 + 48 + 96 - 40, 80, 15, "Clipboard"));
/*  55 */     this.buttonList
/*  56 */       .add(new HeraShadowButton(4, this.width / 2 - 30 - 10, this.height / 4 + 48 + 120 - 45, 80, 15, "Cracked"));
/*  57 */     this.buttonList
/*  58 */       .add(new HeraShadowButton(2, this.width / 2 - 30 - 10, this.height / 4 + 48 + 144 - 50, 80, 15, "Leave"));
/*     */     
/*  60 */     this.email = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 98, this.height / 4 + 48 + 14 - 68, 200, 20);
/*     */     
/*  62 */     this.email.setMaxStringLength(50);
/*     */   }
/*     */   
/*     */   public void onGuiClosed() {
/*  66 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */   
/*     */   public void updateScreen() {
/*  70 */     this.email.updateCursorCounter();
/*     */   }
/*     */   
/*     */   protected void mouseClicked(int x, int y, int m) {
/*  74 */     this.email.mouseClicked(x, y, m);
/*     */     try {
/*  76 */       super.mouseClicked(x, y, m);
/*  77 */     } catch (Exception e) {
/*  78 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void keyTyped(char c, int i) {
/*  83 */     this.email.textboxKeyTyped(c, i);
/*  84 */     if (c == '\t')
/*  85 */       if (this.email.isFocused()) {
/*  86 */         this.email.setFocused(false);
/*     */       } else {
/*  88 */         this.email.setFocused(true);
/*     */       }  
/*  90 */     if (c == '\r') {
/*     */       try {
/*  92 */         actionPerformed(this.buttonList.get(0));
/*  93 */       } catch (IOException e) {
/*  94 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 100 */     drawBackground();
/*     */     
/* 102 */     if (this.status != null)
/* 103 */       FontHelper.cfArrayList.drawCenteredString(this.status, (this.width / 2), 30.0D, Color.WHITE.getRGB(), 
/* 104 */           false); 
/* 105 */     this.email.drawTextBox();
/* 106 */     drawString(this.fontRendererObj, "Data (E-Mail:Password):", this.width / 2 - 98, this.height / 4 + 48 + 14 - 82, 
/* 107 */         -1);
/* 108 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 112 */     if (button.id == 2) {
/* 113 */       this.mc.displayGuiScreen(this.oldScreen);
/* 114 */     } else if (button.id == 1) {
/* 115 */       if (!this.email.getText().trim().isEmpty()) {
/* 116 */         YggdrasilUserAuthentication a = (YggdrasilUserAuthentication)(new YggdrasilAuthenticationService(
/* 117 */             Proxy.NO_PROXY, "")).createUserAuthentication(Agent.MINECRAFT);
/* 118 */         String[] split = this.email.getText().trim().split(":");
/* 119 */         String e = split[0];
/* 120 */         String p = null;
/*     */         try {
/* 122 */           p = split[1];
/* 123 */         } catch (Exception exception) {}
/*     */         
/* 125 */         a.setUsername(e);
/* 126 */         a.setPassword(p);
/* 127 */         this.lastName = this.email.getText();
/* 128 */         if (p != null) {
/*     */           try {
/* 130 */             a.logIn();
/* 131 */             this.mc.session = new Session(a.getSelectedProfile().getName(), 
/* 132 */                 a.getSelectedProfile().getId().toString(), a.getAuthenticatedToken(), "mojang");
/* 133 */             this.status = "§aLogged in §7(" + this.mc.session.getUsername() + ")";
/* 134 */           } catch (Exception e2) {
/* 135 */             this.status = "§cThis Account doesn't work.";
/*     */           } 
/*     */         } else {
/*     */           try {
/* 139 */             this.mc.session = new Session(e, "-", "-", "Legacy");
/* 140 */             this.status = "§aLogged in §7(Cracked)";
/* 141 */           } catch (Exception exception) {}
/*     */         } 
/*     */       } else {
/*     */         
/* 145 */         this.status = "§cPlease enter something.";
/*     */       } 
/* 147 */     } else if (button.id == 3) {
/* 148 */       this.email.setText(getClipboardContents());
/* 149 */       if (!this.email.getText().trim().isEmpty()) {
/* 150 */         YggdrasilUserAuthentication a = (YggdrasilUserAuthentication)(new YggdrasilAuthenticationService(
/* 151 */             Proxy.NO_PROXY, "")).createUserAuthentication(Agent.MINECRAFT);
/* 152 */         String[] split = this.email.getText().trim().split(":");
/* 153 */         String e = split[0];
/* 154 */         String p = split[1];
/* 155 */         a.setUsername(e);
/* 156 */         a.setPassword(p);
/* 157 */         this.lastName = this.email.getText();
/*     */         try {
/* 159 */           a.logIn();
/* 160 */           this.mc.session = new Session(a.getSelectedProfile().getName(), 
/* 161 */               a.getSelectedProfile().getId().toString(), a.getAuthenticatedToken(), "mojang");
/* 162 */           this.status = "§aLogged in §7(" + this.mc.session.getUsername() + ")";
/* 163 */         } catch (Exception e2) {
/* 164 */           this.status = "§cThis Account doesn't work.";
/*     */         } 
/*     */       } else {
/* 167 */         this.status = "§cPlease enter something.";
/*     */       } 
/* 169 */     } else if (button.id == 4) {
/* 170 */       this.status = "§aLogged in §7(Cracked)";
/* 171 */       loginRDMCracked();
/* 172 */       this.email.setText(name);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getClipboardContents() {
/* 177 */     String result = "";
/* 178 */     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 179 */     Transferable contents = clipboard.getContents(null);
/* 180 */     boolean hasTransferableText = (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor));
/* 181 */     if (hasTransferableText)
/*     */       try {
/*     */         try {
/* 184 */           result = (String)contents.getTransferData(DataFlavor.stringFlavor);
/* 185 */         } catch (UnsupportedFlavorException e) {
/* 186 */           e.printStackTrace();
/*     */         } 
/* 188 */         return result;
/* 189 */       } catch (IOException ex) {
/* 190 */         System.out.println(ex);
/* 191 */         ex.printStackTrace();
/*     */       }  
/* 193 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void lostOwnership(Clipboard clipboard, Transferable contents) {}
/*     */   
/*     */   public static void loginRDMCracked() {
/* 200 */     ArrayList<String> letters = new ArrayList<String>();
/* 201 */     letters.add("a");
/* 202 */     letters.add("b");
/* 203 */     letters.add("c");
/* 204 */     letters.add("d");
/* 205 */     letters.add("e");
/* 206 */     letters.add("f");
/* 207 */     letters.add("g");
/* 208 */     letters.add("h");
/* 209 */     letters.add("i");
/* 210 */     letters.add("j");
/* 211 */     letters.add("k");
/* 212 */     letters.add("l");
/* 213 */     letters.add("m");
/* 214 */     letters.add("n");
/* 215 */     letters.add("o");
/* 216 */     letters.add("p");
/* 217 */     letters.add("q");
/* 218 */     letters.add("r");
/* 219 */     letters.add("s");
/* 220 */     letters.add("t");
/* 221 */     letters.add("u");
/* 222 */     letters.add("v");
/* 223 */     letters.add("w");
/* 224 */     letters.add("x");
/* 225 */     letters.add("y");
/* 226 */     letters.add("z");
/* 227 */     Random rdm = new Random();
/* 228 */     String name = String.valueOf(String.valueOf(letters.get(rdm.nextInt(25)))) + rdm.nextInt(9) + 
/* 229 */       (String)letters.get(rdm.nextInt(25)) + rdm.nextInt(99) + (String)letters.get(rdm.nextInt(25)) + 
/* 230 */       rdm.nextInt(999) + (String)letters.get(rdm.nextInt(25));
/* 231 */     (Minecraft.getMinecraft()).session = new Session(name, "-", "-", "Legacy");
/* 232 */     GuiLogin.name = name;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\gui\GuiLogin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */