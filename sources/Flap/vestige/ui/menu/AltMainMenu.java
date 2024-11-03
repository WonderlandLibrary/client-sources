package vestige.ui.menu;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.jetbrains.annotations.NotNull;
import vestige.Flap;
import vestige.event.impl.GuiRender;
import vestige.font.VestigeFontRenderer;
import vestige.module.impl.client.NotificationManagerExterno;
import vestige.module.impl.visual.ClientTheme;
import vestige.shaders.impl.GaussianBlur;
import vestige.ui.menu.Auth.MicrosoftAuth;
import vestige.ui.menu.components.Button;
import vestige.util.misc.AudioUtil;
import vestige.util.render.ColorUtil2;
import vestige.util.render.DrawUtil;
import vestige.util.render.RenderUtils2;

public class AltMainMenu extends GuiScreen {
   private final Button[] buttons = new Button[]{new Button("Offline"), new Button("Microsoft"), new Button("Cookie (BETA)")};
   private final int textColor = (new Color(255, 255, 255)).getRGB();
   private static ClientTheme theme;
   private VestigeFontRenderer productSans;
   private int ravenXD$hoverValue1;
   private int ravenXD$hoverValue2;
   private int ravenXD$hoverValue3;
   private String status;
   private String refreshToken;
   private static Consumer<String> callback;
   private ExecutorService executor = null;
   private CompletableFuture<Void> task = null;
   private static Field sessionField = null;

   public void initGui() {
      Flap.getDiscordRP().update("Idling", "Main Menu");
      Button[] var1 = this.buttons;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Button button = var1[var3];
         button.updateState(false);
         button.setAnimationDone(true);
      }

   }

   protected void actionPerformed(GuiButton button) {
      try {
         super.actionPerformed(button);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      RenderUtils2.drawRoundedRectangle(0.0F, 0.0F, 10000.0F, 10000.0F, 0.0F, (new Color(0, 0, 0, 100)).getRGB());
      VestigeFontRenderer bigFont = Flap.instance.getFontManager().getProductSanBold();
      Flap.instance.getFontManager().getProductSansTitle();
      ScaledResolution sr = new ScaledResolution(this.mc);
      int buttonWidth = 80;
      int buttonHeight = 100;
      int totalHeight = buttonHeight * this.buttons.length;
      double y = (double)(sr.getScaledHeight() / 2) - (double)totalHeight * 0.3D + 50.0D;
      Math.max(y - 70.0D, 25.0D);
      DrawUtil.drawImage(new ResourceLocation("flap/imagens/background.png"), 0, 0, this.mc.displayWidth / 2, this.mc.displayHeight / 2);
      GaussianBlur.startBlur();
      RenderUtils2.drawBloomShadow(0.0F, 0.0F, 1000.0F, 10000.0F, 6, 4, -1, false);
      GaussianBlur.endBlur(4.0F, 2.0F);
      int startXInitial = sr.getScaledWidth() / 2 - buttonWidth / 2 - buttonWidth - 10;
      int var10000 = sr.getScaledWidth() / 2 + buttonWidth / 2;
      Color rectColor = new Color(10, 10, 10, 100);
      rectColor = flap$interpolateColorC(rectColor, ColorUtil2.brighter(rectColor, 0.4F), -1.0F);
      double titleY = (double)Math.max(sr.getScaledHeight() / 2 - totalHeight / 2 - 110, 20);
      bigFont.drawStringWithShadow(this.status, (double)(sr.getScaledWidth() / 2) - bigFont.getStringWidth(this.status) / 2.0D, titleY + 25.0D, -1);
      int currentX = startXInitial;
      Button[] var20 = this.buttons;
      int var21 = var20.length;

      for(int var22 = 0; var22 < var21; ++var22) {
         Button button = var20[var22];
         boolean isHovered = mouseX > currentX && mouseX < currentX + buttonWidth && (double)mouseY > y && (double)mouseY < y + (double)buttonHeight;
         button.updateState(isHovered);
         if (button.getName().equals("Microsoft")) {
            if (isHovered) {
               this.ravenXD$hoverValue1 = (int)Math.min((double)this.ravenXD$hoverValue1 + 300.0D / (double)Minecraft.getDebugFPS(), 50.0D);
            } else {
               this.ravenXD$hoverValue1 = (int)Math.max((double)this.ravenXD$hoverValue1 - 300.0D / (double)Minecraft.getDebugFPS(), 0.0D);
            }

            rectColor = new Color(100, 100, 100, this.ravenXD$hoverValue1);
            RenderUtils2.drawRoundOutline((double)currentX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 0.0D, new Color(0, 0, 0, 0), new Color(100, 100, 100, 100));
            RenderUtils2.drawRoundOutline((double)currentX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 1.5D, rectColor, new Color(30, 30, 30, 0));
            DrawUtil.drawImage(new ResourceLocation("flap/imagens/" + button.getName() + ".png"), currentX + buttonWidth / 2 - 20, (int)y + 40, 40, 40);
            bigFont.drawString(button.getName(), (double)(currentX + buttonWidth / 2) - bigFont.getStringWidth(button.getName()) / 2.0D, y + 14.0D, this.textColor);
         } else if (button.getName().equals("Offline")) {
            if (isHovered) {
               this.ravenXD$hoverValue2 = (int)Math.min((double)this.ravenXD$hoverValue2 + 300.0D / (double)Minecraft.getDebugFPS(), 50.0D);
            } else {
               this.ravenXD$hoverValue2 = (int)Math.max((double)this.ravenXD$hoverValue2 - 300.0D / (double)Minecraft.getDebugFPS(), 0.0D);
            }

            rectColor = new Color(100, 100, 100, this.ravenXD$hoverValue2);
            RenderUtils2.drawRoundOutline((double)currentX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 0.0D, new Color(0, 0, 0, 0), new Color(100, 100, 100, 100));
            RenderUtils2.drawRoundOutline((double)currentX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 1.5D, rectColor, new Color(30, 30, 30, 0));
            DrawUtil.drawImage(new ResourceLocation("flap/imagens/" + button.getName() + ".png"), currentX + buttonWidth / 2 - 20, (int)y + 40, 40, 40);
            bigFont.drawString(button.getName(), (double)(currentX + buttonWidth / 2) - bigFont.getStringWidth(button.getName()) / 2.0D, y + 14.0D, this.textColor);
         } else if (button.getName().equals("Cookie (BETA)")) {
            if (isHovered) {
               this.ravenXD$hoverValue3 = (int)Math.min((double)this.ravenXD$hoverValue3 + 300.0D / (double)Minecraft.getDebugFPS(), 50.0D);
            } else {
               this.ravenXD$hoverValue3 = (int)Math.max((double)this.ravenXD$hoverValue3 - 300.0D / (double)Minecraft.getDebugFPS(), 0.0D);
            }

            rectColor = new Color(100, 100, 100, this.ravenXD$hoverValue3);
            RenderUtils2.drawRoundOutline((double)currentX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 0.0D, new Color(0, 0, 0, 0), new Color(100, 100, 100, 100));
            RenderUtils2.drawRoundOutline((double)currentX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 1.5D, rectColor, new Color(30, 30, 30, 0));
            DrawUtil.drawImage(new ResourceLocation("flap/imagens/" + button.getName() + ".png"), currentX + buttonWidth / 2 - 20, (int)y + 40, 40, 40);
            bigFont.drawString(button.getName(), (double)(currentX + buttonWidth / 2) - bigFont.getStringWidth(button.getName()) / 2.0D, y + 14.0D, this.textColor);
         }

         currentX += buttonWidth + 10;
      }

      Flap.instance.getEventManager().post(new GuiRender());
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

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      try {
         super.mouseClicked(mouseX, mouseY, mouseButton);
      } catch (IOException var21) {
         var21.printStackTrace();
      }

      if (mouseButton == 0) {
         int buttonWidth = 80;
         int buttonHeight = 100;
         int totalHeight = buttonHeight * this.buttons.length;
         ScaledResolution sr = new ScaledResolution(this.mc);
         double y = (double)(sr.getScaledHeight() / 2) - (double)totalHeight * 0.3D + 50.0D;
         int totalWidth = this.buttons.length * buttonWidth + (this.buttons.length - 1) * 10;
         int startX = sr.getScaledWidth() / 2 - totalWidth / 2;
         Button[] var12 = this.buttons;
         int var13 = var12.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            Button button = var12[var14];
            int endX = startX + buttonWidth;
            if (mouseX > startX && mouseX < endX && (double)mouseY > y && (double)mouseY < y + (double)buttonHeight) {
               String var17 = button.getName();
               byte var18 = -1;
               switch(var17.hashCode()) {
               case 116041155:
                  if (var17.equals("Offline")) {
                     var18 = 0;
                  }
                  break;
               case 484531757:
                  if (var17.equals("Cookie (BETA)")) {
                     var18 = 2;
                  }
                  break;
               case 1909739726:
                  if (var17.equals("Microsoft")) {
                     var18 = 1;
                  }
               }

               switch(var18) {
               case 0:
                  this.mc.displayGuiScreen(new AltLoginScreen());
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
                  NotificationManagerExterno.showNotification("Notificação Teste", "Isso é um exemplo de notificação", NotificationManagerExterno.NotificationType.INFO, 5000L);
               }

               AudioUtil.buttonClick();
            }

            startX += buttonWidth + 10;
         }
      }

   }

   public static void setSession(Session session) {
      Minecraft mc = Minecraft.getMinecraft();

      try {
         getSessionField().set(mc, session);
      } catch (IllegalAccessException var3) {
         System.err.println(var3.getMessage());
      }

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

   public static Session getSession() {
      Minecraft mc = Minecraft.getMinecraft();
      return mc.getSession();
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

      var10001[1] = "vestige/ui/menu/AltMainMenu";
      var10001[2] = "flap$interpolateColorC";
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", var10001));
   }
}
