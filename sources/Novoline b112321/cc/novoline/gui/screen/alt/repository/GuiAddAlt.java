package cc.novoline.gui.screen.alt.repository;

import cc.novoline.Novoline;
import cc.novoline.gui.button.RoundedButton;
import cc.novoline.gui.group.GuiGroupAltLogin;
import cc.novoline.gui.screen.alt.repository.AltRepositoryGUI;
import cc.novoline.gui.screen.alt.repository.PasswordTextField;
import cc.novoline.gui.screen.alt.repository.TokenField;
import cc.novoline.gui.screen.alt.repository.credential.AltCredential;
import cc.novoline.utils.fonts.api.FontRenderer;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_20;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_18;
import cc.novoline.utils.java.Checks;
import com.thealtening.api.TheAlteningException;
import com.thealtening.api.response.Account;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import net.CI;
import net.acE;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

public final class GuiAddAlt extends GuiScreen {
   private final AltRepositoryGUI gui;
   private final BiConsumer consumer;
   private final String addAltButtonName;
   private final String generateButtonName;
   private final String title;
   private GuiGroupAltLogin groupAltInfo;
   private PasswordTextField passwordField;
   private GuiTextField usernameField;
   private static final Pattern EMAIL_PATTERN = Pattern.compile("^(?:[a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&\'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])$");

   public GuiAddAlt(@NotNull AltRepositoryGUI var1, @NotNull String var2, @NotNull String var3, @NotNull String var4, @NotNull BiConsumer var5) {
      this.gui = var1;
      this.addAltButtonName = var2;
      this.title = var3;
      this.generateButtonName = var4;
      this.consumer = var5;
   }

   public void initGui() {
      int var2 = this.height / 4 + 24;
      CI.b();
      this.groupAltInfo = new GuiGroupAltLogin(this, this.title);
      List var3 = this.buttonList;
      var3.add(new RoundedButton(this.addAltButtonName, 0, this.width / 2 - 100, var2 + 72 + 12, 15, Fonts$SFBOLD$SFBOLD_20.SFBOLD_20));
      var3.add(new RoundedButton("Back", 1, this.width / 2 - 100, var2 + 72 + 12 + 24, 15, Fonts$SFBOLD$SFBOLD_20.SFBOLD_20));
      var3.add(new RoundedButton("Import alt", 2, this.width / 2 - 100, var2 + 72 + 12 + -24, 15, Fonts$SFBOLD$SFBOLD_20.SFBOLD_20));
      var3.add(new RoundedButton(this.generateButtonName, 3, this.width / 2 - 100, var2 + 72 + 12 + -48, 15, Fonts$SFBOLD$SFBOLD_20.SFBOLD_20));
      this.usernameField = new TokenField(0, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20, "Alt Email:");
      this.passwordField = new PasswordTextField(1, this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20, "Password:");
      this.usernameField.setFocused(true);
      Keyboard.enableRepeatEvents(true);
      if(acE.b() == null) {
         CI.b(new int[3]);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      FontRenderer var5 = Fonts$SFTHIN$SFTHIN_18.SFTHIN_18;
      drawRect(0, 0, this.width, this.height, (new Color(32, 34, 37)).getRGB());
      CI.b();
      GuiTextField var6 = this.usernameField;
      PasswordTextField var7 = this.passwordField;
      var6.drawTextBox();
      var7.drawTextBox();
      this.groupAltInfo.drawGroup(this.mc, var1, var2);
      if(StringUtils.isBlank(var6.getText()) && !var6.isFocused()) {
         var5.drawString("Username / E-Mail", (float)this.width / 2.0F - 96.0F, 64.0F, -7829368);
      }

      if(StringUtils.isBlank(var7.getText()) && !var7.isFocused()) {
         var5.drawString("Password", (float)this.width / 2.0F - 96.0F, 104.0F, -7829368);
      }

      super.drawScreen(var1, var2, var3);
   }

   static boolean isEmail(@NotNull String var0) {
      Checks.notBlank(var0, "email");
      return EMAIL_PATTERN.matcher(var0).matches();
   }

   private void add_login() {
      int[] var1 = CI.b();

      try {
         if(this.usernameField.getText().trim().isEmpty()) {
            return;
         }

         AltCredential var2 = new AltCredential(this.usernameField.getText(), this.passwordField.getText());
         String var3 = var2.getLogin();
         if(var2.getPassword() == null) {
            if(!var3.matches("^[a-zA-Z0-9_]+$")) {
               this.groupAltInfo.updateStatus(EnumChatFormatting.RED + "Illegal characters in username");
               return;
            }

            if(var3.length() > 16) {
               this.groupAltInfo.updateStatus(EnumChatFormatting.RED + "Username is too long");
               return;
            }
         } else if(!isEmail(var3)) {
            this.groupAltInfo.updateStatus(EnumChatFormatting.RED + "Illegal e-mail");
         }

         this.consumer.accept(this, var2);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   protected void actionPerformed(GuiButton var1) {
      int[] var2 = CI.b();
      switch(var1.id) {
      case 0:
         this.add_login();
      case 1:
         this.mc.displayGuiScreen(this.gui);
      case 2:
         AltCredential var3 = this.getDataFromClipboard();
         return;
      case 3:
         Novoline var4 = Novoline.getInstance();
         var4.getDataRetriever().updateKey(var4.getAltRepositoryGUI().getApiKeyField().getText());
         var4.generateAlteningAlt().whenComplete(this::lambda$actionPerformed$0);
      default:
      }
   }

   protected void keyTyped(char var1, int var2) {
      int[] var3 = CI.b();
      if(var2 == 1) {
         this.mc.displayGuiScreen(this.gui);
      } else if(var2 == 28) {
         this.add_login();
      } else {
         switch(var1) {
         case '\t':
            boolean var4 = this.passwordField.isFocused();
            boolean var5 = this.usernameField.isFocused();
            this.usernameField.setFocused(false);
            this.passwordField.setFocused(true);
            return;
         case '\r':
            this.actionPerformed((GuiButton)this.buttonList.get(0));
         default:
            this.usernameField.textboxKeyTyped(var1, var2);
            this.passwordField.textboxKeyTyped(var1, var2);
         }
      }
   }

   protected void mouseClicked(int var1, int var2, int var3) {
      GuiAddAlt var10000 = this;
      int var10001 = var1;
      int var10002 = var2;
      int var10003 = var3;

      try {
         var10000.mouseClicked(var10001, var10002, var10003);
      } catch (Throwable var5) {
         Novoline.getLogger().warn(var5);
      }

      this.usernameField.mouseClicked(var1, var2, var3);
      this.passwordField.mouseClicked(var1, var2, var3);
   }

   @Nullable
   private AltCredential getDataFromClipboard() {
      CI.b();
      String var2 = null;

      try {
         Transferable var3 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents((Object)null);
         if(var3 != null && var3.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            var2 = ((String)var3.getTransferData(DataFlavor.stringFlavor)).trim();
         }
      } catch (Throwable var4) {
         ;
      }

      if(var2 == null) {
         return null;
      } else {
         int var5 = var2.indexOf(58);
         return var5 == -1?(var2.endsWith("@alt.com")?new AltCredential(var2, (String)null):null):new AltCredential(var2.substring(0, var5), var2.substring(var5 + 1));
      }
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   public GuiGroupAltLogin getGroupAltInfo() {
      return this.groupAltInfo;
   }

   private void lambda$actionPerformed$0(Account var1, Throwable var2) {
      int[] var3 = CI.b();
      Novoline.getLogger().warn("An error occurred while generating an account!", var2);
      if(var2 instanceof TheAlteningException) {
         this.getGroupAltInfo().updateStatus(EnumChatFormatting.RED + "Invalid TheAltening token!");
      }

      this.getGroupAltInfo().updateStatus(EnumChatFormatting.RED + "Unexpected error occurred!");
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
