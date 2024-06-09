package exhibition.gui.screen.impl.mainmenu;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.crypto.spec.SecretKeySpec;

import org.lwjgl.input.Keyboard;

import exhibition.Client;
import exhibition.gui.altmanager.PasswordField;
import exhibition.util.RenderingUtil;
import exhibition.util.render.Colors;
import exhibition.util.security.AuthenticatedUser;
import exhibition.util.security.AuthenticationUtil;
import exhibition.util.security.Crypto;
import exhibition.util.security.LoginUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;

public class GuiLoginMenu extends GuiScreen {
   private Client oldInstance;
   private transient PasswordField password;
   private transient GuiTextField username;
   private transient GuiLoginMenu.Status status;

   public GuiLoginMenu() {
      this.status = GuiLoginMenu.Status.Idle;
      this.oldInstance = Client.instance;

      try {
    	  Client.instance = this.oldInstance;
      } catch (Exception var9) {
    	  var9.printStackTrace();
      }
   }

   public void initGui() {
      int var3 = this.height / 4 + 24;
      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72 + 12, "Login"));
      this.username = new GuiTextField(var3, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
      this.password = new PasswordField(this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
      this.password.setMaxStringLength(256);
      this.username.setFocused(true);
      List okHand = LoginUtil.getLoginInformation();

      try {
         if (!okHand.isEmpty() && okHand.size() > 1) {
            this.username.setText(this.getDecrypted((String)okHand.get(0)));
            this.password.setText(this.getDecrypted((String)okHand.get(1)));
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   private static SecretKeySpec getSecretOLD() {
      byte[] secret = Crypto.getUserKeyOLD(16);
      return new SecretKeySpec(secret, 0, secret.length, "AES");
   }

   private static SecretKeySpec getSecretNew() {
      byte[] secret = Crypto.getUserKey(16);
      return new SecretKeySpec(secret, 0, secret.length, "AES");
   }

   private String getCrypted(String str) throws Exception {
      return Crypto.encrypt(getSecretNew(), str);
   }

   private String getDecrypted(String str) throws Exception {
      return Crypto.decrypt(getSecretNew(), str);
   }

   protected void actionPerformed(GuiButton button) {
      if (button.id <= 0) {
         Client.instance = null;
         try {
        	 this.status = GuiLoginMenu.Status.Failed;
             if (this.username.getText() != null && !Objects.equals(this.username.getText(), "") && this.password.getText() != null && !Objects.equals(this.password.getText(), "")) {
                try {
                   this.status = GuiLoginMenu.Status.Authenticating;
                   Object one = this.getCrypted(this.username.getText());
                   Object two = this.getCrypted(this.password.getText());
                   Object three = this.getCrypted(AuthenticationUtil.getMD5Hash(this.username.getText()));
                   Object four = this.getCrypted(AuthenticationUtil.getMD5Hash(this.password.getText()));
                   
                   if (Arrays.toString(AuthenticationUtil.isAuth(one.toString(), two.toString(), three.toString(), four.toString()).getBytes()).equals(Arrays.toString((this.username.getText() + this.password.getText()).getBytes()))) {
                	   if (Client.instance == null) {
                           Client.instance = this.oldInstance;
                       }
                       LoginUtil.saveLogin((String)one, (String)two);
                       this.status = GuiLoginMenu.Status.Success;
                   } else {
                	   this.status = GuiLoginMenu.Status.Failed;
                   }
                } catch (Exception var5) {
                	var5.printStackTrace();
                    this.status = GuiLoginMenu.Status.Error;
                }
             }
         } catch (Exception ex) {
        	 ex.printStackTrace();
         }
      }
   }

   public void drawScreen(int x, int y, float z) {
      ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
      RenderingUtil.rectangle(0.0D, 0.0D, (double)res.getScaledWidth(), (double)res.getScaledHeight(), Colors.getColor(0));
      this.username.drawTextBox();
      this.password.drawTextBox();
      this.drawCenteredString(this.mc.fontRendererObj, this.status.name(), this.width / 2, 32, -1);
      GuiButton button = (GuiButton)this.buttonList.get(0);
      button.enabled = !this.status.equals(GuiLoginMenu.Status.Authenticating);
      boolean renderUser = this.username.getText().isEmpty() && !this.username.isFocused();
      if (renderUser) {
         this.drawString(this.mc.fontRendererObj, "Username", this.width / 2 - 96, 66, -7829368);
      }

      boolean renderPass = this.password.getText().isEmpty() && !this.password.isFocused();
      if (renderPass) {
         this.drawString(this.mc.fontRendererObj, "Password", this.width / 2 - 96, 106, -7829368);
      }
      
      if (!renderPass && this.status == GuiLoginMenu.Status.Success && !renderUser && Client.authUser != null) {
          Object object = Client.authUser;
          this.mc.displayGuiScreen((GuiScreen)(this.status == GuiLoginMenu.Status.Success ? new ClientMainMenu((AuthenticatedUser)object, Client.authUser.getDecryptedPassword()) : new GuiGameOver()));
       }
      super.drawScreen(x, y, z);
   }

   protected void keyTyped(char character, int key) {
      if (character == '\t') {
         if (!this.username.isFocused() && !this.password.isFocused()) {
            this.username.setFocused(true);
         } else {
            this.username.setFocused(this.password.isFocused());
            this.password.setFocused(!this.username.isFocused());
         }
      }

      if (character == '\r') {
         this.actionPerformed((GuiButton)this.buttonList.get(0));
      }

      this.username.textboxKeyTyped(character, key);
      this.password.textboxKeyTyped(character, key);
   }

   protected void mouseClicked(int x, int y, int button) {
      try {
         super.mouseClicked(x, y, button);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      this.username.mouseClicked(x, y, button);
      this.password.mouseClicked(x, y, button);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   public void updateScreen() {
      this.username.updateCursorCounter();
      this.password.updateCursorCounter();
   }

   static enum Status {
      Idle,
      Authenticating,
      Success,
      Failed,
      Error;
   }
}
