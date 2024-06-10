/*   1:    */ package me.connorm.Nodus.account;
/*   2:    */ 
/*   3:    */ import com.mojang.authlib.Agent;
/*   4:    */ import com.mojang.authlib.GameProfile;
/*   5:    */ import com.mojang.authlib.exceptions.AuthenticationException;
/*   6:    */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*   7:    */ import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
/*   8:    */ import java.net.Proxy;
/*   9:    */ import java.util.List;
/*  10:    */ import me.connorm.Nodus.Nodus;
/*  11:    */ import me.connorm.Nodus.manager.GuiNodusManager;
/*  12:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  13:    */ import me.connorm.Nodus.ui.NodusGuiMainMenu;
/*  14:    */ import me.connorm.Nodus.utils.RenderUtils;
/*  15:    */ import me.connorm.irc.IRCBot;
/*  16:    */ import me.connorm.irc.NodusIRCBot;
/*  17:    */ import net.minecraft.client.Minecraft;
/*  18:    */ import net.minecraft.client.gui.Gui;
/*  19:    */ import net.minecraft.client.gui.GuiScreen;
/*  20:    */ import net.minecraft.client.gui.GuiTextField;
/*  21:    */ import net.minecraft.util.Session;
/*  22:    */ import org.lwjgl.input.Keyboard;
/*  23:    */ 
/*  24:    */ public class GuiAccount
/*  25:    */   extends GuiScreen
/*  26:    */ {
/*  27: 26 */   public static String error = "123";
/*  28:    */   private GuiScreen parentScreen;
/*  29:    */   private GuiTextField usernameBox;
/*  30:    */   private GuiPasswordBox passwordBox;
/*  31:    */   
/*  32:    */   public GuiAccount(GuiScreen parent)
/*  33:    */   {
/*  34: 33 */     this.parentScreen = parent;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void updateScreen()
/*  38:    */   {
/*  39: 38 */     this.usernameBox.updateCursorCounter();
/*  40: 39 */     this.passwordBox.updateCursorCounter();
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected void keyTyped(char c, int i)
/*  44:    */   {
/*  45: 44 */     this.usernameBox.textboxKeyTyped(c, i);
/*  46: 45 */     this.passwordBox.textboxKeyTyped(c, i);
/*  47: 47 */     if (c == '\t') {
/*  48: 49 */       if (this.usernameBox.isFocused())
/*  49:    */       {
/*  50: 51 */         this.usernameBox.setFocused(false);
/*  51: 52 */         this.passwordBox.setFocused(true);
/*  52:    */       }
/*  53:    */       else
/*  54:    */       {
/*  55: 55 */         this.usernameBox.setFocused(true);
/*  56: 56 */         this.passwordBox.setFocused(false);
/*  57:    */       }
/*  58:    */     }
/*  59: 60 */     if (c == '\r') {
/*  60: 62 */       actionPerformed((NodusGuiButton)this.buttonList.get(0));
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected void mouseClicked(int i, int j, int k)
/*  65:    */   {
/*  66: 68 */     super.mouseClicked(i, j, k);
/*  67: 69 */     this.usernameBox.mouseClicked(i, j, k);
/*  68: 70 */     this.passwordBox.mouseClicked(i, j, k);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void initGui()
/*  72:    */   {
/*  73: 75 */     Keyboard.enableRepeatEvents(true);
/*  74: 76 */     this.buttonList.add(new NodusGuiButton(0, this.mc.displayWidth / 4 - 102, this.mc.displayHeight / 4 + 25, 204, 20, "Login"));
/*  75: 77 */     this.buttonList.add(new NodusGuiButton(1, this.mc.displayWidth / 4 - 102, this.mc.displayHeight / 4 + 49, 204, 20, "Back"));
/*  76: 78 */     this.usernameBox = new GuiTextField(this.mc.fontRenderer, this.mc.displayWidth / 4 - 100, 76, 200, 20);
/*  77: 79 */     this.passwordBox = new GuiPasswordBox(this.mc.fontRenderer, this.mc.displayWidth / 4 - 100, 116, 200, 20);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void drawScreen(int i, int j, float f)
/*  81:    */   {
/*  82: 84 */     RenderUtils.drawRect(0.0F, 0, Nodus.theNodus.getMinecraft().displayWidth, Nodus.theNodus.getMinecraft().displayHeight, -11184811);
/*  83: 85 */     Gui.drawString(Nodus.theNodus.getMinecraft().fontRenderer, "Username", this.mc.displayWidth / 4 - 100, 63, -1);
/*  84: 86 */     Gui.drawString(Nodus.theNodus.getMinecraft().fontRenderer, "Password", this.mc.displayWidth / 4 - 100, 104, -1);
/*  85: 87 */     this.usernameBox.drawTextBox();
/*  86: 88 */     this.passwordBox.drawTextBox();
/*  87: 89 */     super.drawScreen(i, j, f);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void onGuiClosed()
/*  91:    */   {
/*  92: 94 */     Keyboard.enableRepeatEvents(false);
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected void actionPerformed(NodusGuiButton par1)
/*  96:    */   {
/*  97: 99 */     if (par1.id == 1) {
/*  98:101 */       this.mc.displayGuiScreen(new GuiNodusManager(new NodusGuiMainMenu()));
/*  99:    */     }
/* 100:103 */     if (par1.id == 0)
/* 101:    */     {
/* 102:105 */       Account acc = new Account(this.usernameBox.getText(), this.passwordBox.getText());
/* 103:106 */       if ((acc != null) && (acc.isPremium()))
/* 104:    */       {
/* 105:107 */         String username = acc.getUsername();
/* 106:108 */         String password = acc.getPassword();
/* 107:    */         try
/* 108:    */         {
/* 109:111 */           error = setSessionData(username, password);
/* 110:    */         }
/* 111:    */         catch (Exception e)
/* 112:    */         {
/* 113:114 */           e.printStackTrace();
/* 114:    */         }
/* 115:116 */         this.mc.displayGuiScreen(this.parentScreen);
/* 116:    */       }
/* 117:    */       else
/* 118:    */       {
/* 119:119 */         this.mc.session = new Session(acc.getUsername(), "", "");
/* 120:120 */         error = "";
/* 121:121 */         NodusIRCBot.ircBot.dispose();
/* 122:122 */         NodusIRCBot.runBot();
/* 123:123 */         this.mc.displayGuiScreen(this.parentScreen);
/* 124:    */       }
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static final String setSessionData(String user, String pass)
/* 129:    */   {
/* 130:130 */     YggdrasilAuthenticationService authentication = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
/* 131:131 */     YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)authentication.createUserAuthentication(Agent.MINECRAFT);
/* 132:132 */     auth.setUsername(user);
/* 133:133 */     auth.setPassword(pass);
/* 134:    */     try
/* 135:    */     {
/* 136:135 */       auth.logIn();
/* 137:136 */       Nodus.theNodus.getMinecraft().session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId(), auth.getAuthenticatedToken());
/* 138:    */     }
/* 139:    */     catch (AuthenticationException e)
/* 140:    */     {
/* 141:138 */       return "Error!";
/* 142:    */     }
/* 143:141 */     return "";
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.account.GuiAccount
 * JD-Core Version:    0.7.0.1
 */