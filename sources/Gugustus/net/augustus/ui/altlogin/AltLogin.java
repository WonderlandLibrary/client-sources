package net.augustus.ui.altlogin;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.augustus.Augustus;
import net.augustus.ui.widgets.CustomButton;
import net.augustus.ui.widgets.CustomLoginButton;
import net.augustus.ui.widgets.PasswordTextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.Session;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

public class AltLogin extends GuiScreen {
   private String username;
   private String password;
   private GuiTextField emailTextField;
   private PasswordTextField passwordTextField;
   private String loginStatus = "Waiting...";
   private LoginThread loginThread;
   private int clickCounter;
   private GuiScreen parent;

   public AltLogin(GuiScreen parent) {
      this.parent = parent;
   }

   public GuiScreen start(GuiScreen parent) {
      this.parent = parent;
      return this;
   }

   @Override
   public void initGui() {
      super.initGui();
      this.clickCounter = Augustus.getInstance().getLastAlts().size() - 1;
      ScaledResolution sr = new ScaledResolution(this.mc);
      this.emailTextField = new GuiTextField(5, this.fontRendererObj, sr.getScaledWidth() / 2 - 100, 55, 200, 20);
      this.passwordTextField = new PasswordTextField(6, this.fontRendererObj, sr.getScaledWidth() / 2 - 100, 85, 200, 20);
      this.emailTextField.setMaxStringLength(1337);
      this.passwordTextField.setMaxStringLength(1337);
      this.buttonList.add(new CustomLoginButton(1, sr.getScaledWidth() / 2 - 100, 135, 200, 20, "Login", Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomButton(2, sr.getScaledWidth() / 2 - 100, 165, 200, 20, "Last Alt", Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomLoginButton(3, sr.getScaledWidth() / 2 - 100, 195, 200, 20, "Clipboard Login", Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomLoginButton(6, sr.getScaledWidth() / 2 - 100, 225, 200, 20, "Generate Cracked", Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomLoginButton(420, sr.getScaledWidth() / 2 - 100, 260, 200, 20, "Browser Login", Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomButton(5, sr.getScaledWidth() - 100, sr.getScaledHeight() - 20, 100, 20, "Reset IP", Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomButton(1337, 0, sr.getScaledHeight() - 20, 100, 20, "Token", Augustus.getInstance().getClientColor()));
      this.buttonList
         .add(
            new CustomButton(
               4, sr.getScaledWidth() / 2 - 100, sr.getScaledHeight() - sr.getScaledHeight() / 10, 200, 20, "Back", Augustus.getInstance().getClientColor()
            )
         );
   }

   @Override
   protected void actionPerformed(GuiButton button) throws IOException {
      if (button.id == 1) {
         this.buttonLogin();
      }

      if (button.id == 420) {
         this.browserLogin();
      }

      if (button.id == 2) {
         this.lastAlt();
      }

      if (button.id == 1337) {
         loginToken(getClipboardString());
      }

      if (button.id == 3) {
         this.clipBoardLogin();
      }

      if (button.id == 4) {
         this.mc.displayGuiScreen(this.parent);
      }

      if (button.id == 6) {
         String naem = RandomStringUtils.random(10, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789");
         this.loginThread = new LoginThread(naem, "", true, false);
         this.loginThread.start();
      }

      if (button.id == 5) {
         try {
            File dir = new File(this.mc.mcDataDir, "xenza");
            ProcessBuilder builder = new ProcessBuilder("wscript", "ip_changer_fritzbox.vbs");
            builder.directory(dir);
            builder.start();
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }
   }

   private void browserLogin() {
      this.loginThread = new LoginThread(this.username, this.password, false, true);
      this.loginThread.start();
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      super.drawBackground(0);
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.emailTextField.drawTextBox();
      this.passwordTextField.drawTextBox();
      ScaledResolution sr = new ScaledResolution(this.mc);
      int scaledWidth = sr.getScaledWidth();
      this.fontRendererObj
         .drawStringWithShadow(
            "Alt Login", (float)scaledWidth / 2.0F - (float)this.fontRendererObj.getStringWidth("Alt Login") / 2.0F, 10.0F, Color.lightGray.getRGB()
         );
      Color loginStatusColor = Color.green;
      if (this.loginStatus.equals("Waiting...")) {
         loginStatusColor = Color.gray;
      }

      if (this.loginThread != null) {
         this.loginStatus = this.loginThread.getStatus();
         loginStatusColor = this.loginThread.getColor();
      }

      this.fontRendererObj
         .drawStringWithShadow(
            this.loginStatus, (float)scaledWidth / 2.0F - (float)this.fontRendererObj.getStringWidth(this.loginStatus) / 2.0F, 22.0F, loginStatusColor.getRGB()
         );
      if (this.emailTextField.isFocused()) {
         this.passwordTextField.setFocused(false);
      }

      if (this.passwordTextField.isFocused()) {
         this.emailTextField.setFocused(false);
      }

      if (this.emailTextField.getText().isEmpty() && !this.emailTextField.isFocused()) {
         this.fontRendererObj.drawStringWithShadow("E-Mail / E-Mail:Password / Alt-Token", (float)scaledWidth / 2.0F - 96.0F, 61.0F, Color.gray.getRGB());
      }

      if (this.passwordTextField.getText().isEmpty() && !this.passwordTextField.isFocused()) {
         this.fontRendererObj.drawStringWithShadow("Password", (float)scaledWidth / 2.0F - 96.0F, 91.0F, Color.gray.getRGB());
      }
   }

   @Override
   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      if (keyCode == 28) {
         this.buttonLogin();
      }

      this.emailTextField.textboxKeyTyped(typedChar, keyCode);
      this.passwordTextField.textboxKeyTyped(typedChar, keyCode);
      super.keyTyped(typedChar, keyCode);
   }

   @Override
   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      this.emailTextField.mouseClicked(mouseX, mouseY, mouseButton);
      this.passwordTextField.mouseClicked(mouseX, mouseY, mouseButton);
      super.mouseClicked(mouseX, mouseY, mouseButton);
   }

   @Override
   public void updateScreen() {
      this.emailTextField.updateCursorCounter();
      this.passwordTextField.updateCursorCounter();
      super.updateScreen();
   }

   private void lastAlt() {
      List<String> lastAlts = Augustus.getInstance().getLastAlts();
      if (lastAlts.size() > 0) {
         if (lastAlts.size() <= this.clickCounter) {
            this.clickCounter = lastAlts.size() - 1;
         }

         String[] s = lastAlts.get(Math.max(0, this.clickCounter)).split(":");
         this.emailTextField.setText(s[0]);
         this.passwordTextField.setText(s.length > 1 ? s[1] : "");
         --this.clickCounter;
         if (this.clickCounter < 0) {
            this.clickCounter = lastAlts.size() - 1;
         }
      }
   }

   private void clipBoardLogin() {
      String[] s = GuiScreen.getClipboardString().split(":");
      this.username = s[0];
      this.password = s.length > 1 ? s[1] : "";
      this.login();
   }

   private void buttonLogin() {
      if (this.emailTextField.getText().isEmpty() && this.passwordTextField.getText().isEmpty()) {
         StringBuilder randomName = new StringBuilder();
         String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789_";
         int random = ThreadLocalRandom.current().nextInt(8, 16);

         for(int i = 0; i < random; ++i) {
            randomName.append(alphabet.charAt(ThreadLocalRandom.current().nextInt(1, alphabet.length())));
         }

         this.username = String.valueOf(randomName);
         this.password = this.passwordTextField.getText();
      } else {
         this.username = this.emailTextField.getText();
         this.password = this.passwordTextField.getText();
      }

      this.login();
   }

   private void login() {
      this.loginThread = new LoginThread(this.username, this.password, false, false);
      this.loginThread.start();
   }

   public static boolean loginToken(String token) {
      URL url = null;

      try {
         url = new URL("https://api.minecraftservices.com/minecraft/profile/");
      } catch (MalformedURLException var15) {
         var15.printStackTrace();
      }

      HttpURLConnection conn = null;

      try {
         conn = (HttpURLConnection)url.openConnection();
      } catch (IOException var14) {
         var14.printStackTrace();
      }

      conn.setRequestProperty("Authorization", "Bearer " + token);
      conn.setRequestProperty("Content-Type", "application/json");

      try {
         conn.setRequestMethod("GET");
      } catch (ProtocolException var13) {
         var13.printStackTrace();
      }

      BufferedReader in = null;

      try {
         in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      } catch (IOException var12) {
         var12.printStackTrace();
         return false;
      }

      StringBuilder response = new StringBuilder();

      while(true) {
         String output;
         try {
            if ((output = in.readLine()) == null) {
               break;
            }
         } catch (IOException var16) {
            var16.printStackTrace();
            return false;
         }

         response.append(output);
      }

      String jsonString = response.toString();
      JSONObject obj = new JSONObject(jsonString);
      String username = obj.getString("name");
      String uuid = obj.getString("id");
      Minecraft.getMinecraft().session = new Session(username, uuid, token, "null");

      try {
         in.close();
      } catch (IOException var11) {
         var11.printStackTrace();
      }

      return true;
   }
}
