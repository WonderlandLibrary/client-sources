package vestige.ui.menu;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPasswordField;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.jetbrains.annotations.NotNull;
import vestige.Flap;
import vestige.font.VestigeFontRenderer;
import vestige.shaders.impl.GaussianBlur;
import vestige.ui.menu.Auth.MicrosoftAuth;
import vestige.ui.menu.components.Button;
import vestige.ui.menu.components.TextFielde;
import vestige.util.misc.AudioUtil;
import vestige.util.render.ColorUtil2;
import vestige.util.render.DrawUtil;
import vestige.util.render.RenderUtils2;

public class AltLoginScreen extends GuiScreen {
   private TextFielde email;
   private GuiPasswordField password;
   private final Button[] buttons = new Button[]{new Button("Login with offline"), new Button("Back")};
   private String status;
   private VestigeFontRenderer font;
   private final int textColor = (new Color(255, 255, 255)).getRGB();
   private VestigeFontRenderer productSans;
   private int ravenXD$hoverValue1;
   private int ravenXD$hoverValue2;
   private ExecutorService executor = null;
   private CompletableFuture<Void> task = null;
   private static Field sessionField = null;

   public void initGui() {
      Flap.getDiscordRP().update("Adding an account", "Account Manager");
      ScaledResolution sr = new ScaledResolution(this.mc);
      int buttonHeight = 20;
      int totalHeight = buttonHeight * this.buttons.length;
      int y = Math.max(sr.getScaledHeight() / 2 - totalHeight / 2 - 40, 75);
      this.email = new TextFielde(0, this.mc.fontRendererObj, sr.getScaledWidth() / 2 - 90, y, 180, 25);
      this.font = Flap.instance.getFontManager().getProductSans();
      Button[] var5 = this.buttons;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Button button = var5[var7];
         button.updateState(false);
         button.setAnimationDone(true);
      }

   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      super.drawScreen(mouseX, mouseY, partialTicks);
      ScaledResolution sr = new ScaledResolution(this.mc);
      DrawUtil.drawImage(new ResourceLocation("flap/imagens/background.png"), 0, 0, this.mc.displayWidth / 2, this.mc.displayHeight / 2);
      GaussianBlur.startBlur();
      RenderUtils2.drawBloomShadow(0.0F, 0.0F, 1000.0F, 10000.0F, 6, 4, -1, false);
      GaussianBlur.endBlur(4.0F, 2.0F);
      this.email.drawTextBox();
      int buttonWidth = 140;
      int buttonHeight = 25;
      int totalHeight = buttonHeight * this.buttons.length;
      double y = Math.max((double)(sr.getScaledHeight() / 2) - (double)totalHeight * 0.2D, 120.0D);
      double titleY = (double)Math.max(sr.getScaledHeight() / 2 - totalHeight / 2 - 110, 20);
      Color rectColor = new Color(10, 10, 10, 100);
      rectColor = flap$interpolateColorC(rectColor, ColorUtil2.brighter(rectColor, 0.4F), -1.0F);
      String altLogin = "Alt login";
      this.font.drawStringWithShadow(altLogin, (double)(sr.getScaledWidth() / 2) - this.font.getStringWidth(altLogin) / 2.0D, titleY, -1);
      this.font.drawStringWithShadow(this.status, (double)(sr.getScaledWidth() / 2) - this.font.getStringWidth(this.status) / 2.0D, titleY + 25.0D, -1);
      int startX = sr.getScaledWidth() / 2 - buttonWidth / 2;
      int endX = sr.getScaledWidth() / 2 + buttonWidth / 2;
      Button[] var16 = this.buttons;
      int var17 = var16.length;

      for(int var18 = 0; var18 < var17; ++var18) {
         Button button = var16[var18];
         button.updateState(mouseX > startX && mouseX < endX && (double)mouseY > y && (double)mouseY < y + (double)buttonHeight);
         if (button.getName() == "Login with offline") {
            if (button.isHovered()) {
               this.ravenXD$hoverValue1 = (int)Math.min((double)this.ravenXD$hoverValue1 + 300.0D / (double)Minecraft.getDebugFPS(), 50.0D);
            } else {
               this.ravenXD$hoverValue1 = (int)Math.max((double)this.ravenXD$hoverValue1 - 300.0D / (double)Minecraft.getDebugFPS(), 0.0D);
            }

            rectColor = new Color(100, 100, 100, this.ravenXD$hoverValue1);
            RenderUtils2.drawRoundOutline((double)startX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 0.0D, new Color(0, 0, 0, 0), new Color(100, 100, 100, 100));
            RenderUtils2.drawRoundOutline((double)startX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 1.5D, rectColor, new Color(30, 30, 30, 0));
            String buttonName = button.getName();
            DrawUtil.drawImage(new ResourceLocation("flap/imagens/Microsoft.png"), (int)((double)(sr.getScaledWidth() / 2) - this.font.getStringWidth(buttonName) / 2.0D) - 7, (int)y + 7, 11, 11);
            this.font.drawString(buttonName, (double)(sr.getScaledWidth() / 2) - this.font.getStringWidth(buttonName) / 2.0D + 7.0D, y + 9.0D, this.textColor);
            y += (double)(buttonHeight + 6);
         }
      }

   }

   @NotNull
   private static Color flap$interpolateColorC(@NotNull Color color1, @NotNull Color color2, float amount) {
      if (color1 == null) {
         $$$reportNull$$$0(0);
      }

      if (color2 == null) {
         $$$reportNull$$$0(1);
      }

      amount = Math.min(1.0F, Math.max(0.0F, amount));
      return new Color(ColorUtil2.interpolateInt(color1.getRed(), color2.getRed(), (double)amount), ColorUtil2.interpolateInt(color1.getGreen(), color2.getGreen(), (double)amount), ColorUtil2.interpolateInt(color1.getBlue(), color2.getBlue(), (double)amount), ColorUtil2.interpolateInt(color1.getAlpha(), color2.getAlpha(), (double)amount));
   }

   public void keyTyped(char typedChar, int keyCode) {
      try {
         super.keyTyped(typedChar, keyCode);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      this.email.textboxKeyTyped(typedChar, keyCode);
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      try {
         super.mouseClicked(mouseX, mouseY, mouseButton);
      } catch (IOException var20) {
         var20.printStackTrace();
      }

      this.email.mouseClicked(mouseX, mouseY, mouseButton);
      ScaledResolution sr = new ScaledResolution(this.mc);
      int buttonWidth = 100;
      int buttonHeight = 25;
      int totalHeight = buttonHeight * this.buttons.length;
      double y = Math.max((double)(sr.getScaledHeight() / 2) - (double)totalHeight * 0.2D, 140.0D);
      int startX = sr.getScaledWidth() / 2 - buttonWidth / 2;
      int endX = sr.getScaledWidth() / 2 + buttonWidth / 2;
      Button[] var12 = this.buttons;
      int var13 = var12.length;

      for(int var14 = 0; var14 < var13; ++var14) {
         Button button = var12[var14];
         if (mouseX > startX && mouseX < endX && (double)mouseY > y && (double)mouseY < y + (double)buttonHeight) {
            String var16 = button.getName();
            byte var17 = -1;
            switch(var16.hashCode()) {
            case 2062599:
               if (var16.equals("Back")) {
                  var17 = 2;
               }
               break;
            case 27255808:
               if (var16.equals("Login with offline")) {
                  var17 = 0;
               }
               break;
            case 1074938921:
               if (var16.equals("Login from browser")) {
                  var17 = 1;
               }
            }

            switch(var17) {
            case 0:
               (new Thread(() -> {
                  this.mc.setSession(new Session(this.email.getText(), "none", "none", "mojang"));
                  this.status = "Logged into " + this.email.getText() + " - offline account";
               })).start();
               break;
            case 1:
               if (this.executor == null) {
                  this.executor = Executors.newSingleThreadExecutor();
               }

               AtomicReference<String> refreshToken = new AtomicReference("");
               AtomicReference<String> accessToken = new AtomicReference("");
               this.status = "§fCheck your browser to continue...§r";
               this.task = MicrosoftAuth.acquireMSAuthCode(this.executor).thenComposeAsync((msAuthCode) -> {
                  this.status = "§fAcquiring Microsoft access tokens§r";
                  return MicrosoftAuth.acquireMSAccessTokens(msAuthCode, this.executor);
               }).thenComposeAsync((msAccessTokens) -> {
                  this.status = "§fAcquiring Xbox access token§r";
                  refreshToken.set((String)msAccessTokens.get("refresh_token"));
                  return MicrosoftAuth.acquireXboxAccessToken((String)msAccessTokens.get("access_token"), this.executor);
               }).thenComposeAsync((xboxAccessToken) -> {
                  this.status = "§fAcquiring Xbox XSTS token§r";
                  return MicrosoftAuth.acquireXboxXstsToken(xboxAccessToken, this.executor);
               }).thenComposeAsync((xboxXstsData) -> {
                  this.status = "§fAcquiring Minecraft access token§r";
                  return MicrosoftAuth.acquireMCAccessToken((String)xboxXstsData.get("Token"), (String)xboxXstsData.get("uhs"), this.executor);
               }).thenComposeAsync((mcToken) -> {
                  this.status = "§fFetching your Minecraft profile§r";
                  accessToken.set(mcToken);
                  return MicrosoftAuth.login(mcToken, this.executor);
               }).thenAccept((session) -> {
                  this.status = "Logged in as: " + session.getUsername();
                  setSession(session);
               });
               break;
            case 2:
               this.mc.displayGuiScreen(Flap.instance.getMainMenu());
            }

            AudioUtil.buttonClick();
         }

         y += (double)buttonHeight;
      }

   }

   public static Session getSession() {
      Minecraft mc = Minecraft.getMinecraft();
      return mc.getSession();
   }

   public static Field getSessionField() {
      if (sessionField == null) {
         try {
            Field[] var0 = Minecraft.class.getDeclaredFields();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
               Field f = var0[var2];
               if (f.getType().isAssignableFrom(Session.class)) {
                  sessionField = f;
                  sessionField.setAccessible(true);
                  break;
               }
            }
         } catch (Exception var4) {
            sessionField = null;
         }
      }

      return sessionField;
   }

   public static void setSession(Session session) {
      Minecraft mc = Minecraft.getMinecraft();

      try {
         getSessionField().set(mc, session);
      } catch (IllegalAccessException var3) {
         System.err.println(var3.getMessage());
      }

   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      Object[] var10001 = new Object[3];
      switch(var0) {
      case 0:
      default:
         var10001[0] = "color1";
         break;
      case 1:
         var10001[0] = "color2";
      }

      var10001[1] = "vestige/ui/menu/AltLoginScreen";
      var10001[2] = "flap$interpolateColorC";
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", var10001));
   }
}
