package net.minecraft.client.gui.stream;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.aNh;
import net.af_;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.stream.GuiStreamUnavailable$1;
import net.minecraft.client.gui.stream.GuiStreamUnavailable$Reason;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.stream.IStream;
import net.minecraft.client.stream.NullStream;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Session$Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import tv.twitch.ErrorCode;

public class GuiStreamUnavailable extends GuiScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private final IChatComponent field_152324_f;
   private final GuiScreen parentScreen;
   private final GuiStreamUnavailable$Reason field_152326_h;
   private final List field_152327_i;
   private final List field_152323_r;

   public GuiStreamUnavailable(GuiScreen var1, GuiStreamUnavailable$Reason var2) {
      this(var1, var2, (List)null);
   }

   public GuiStreamUnavailable(GuiScreen var1, GuiStreamUnavailable$Reason var2, List var3) {
      this.field_152324_f = new ChatComponentTranslation("stream.unavailable.title", new Object[0]);
      this.field_152323_r = Lists.newArrayList();
      this.parentScreen = var1;
      this.field_152326_h = var2;
      this.field_152327_i = var3;
   }

   public void initGui() {
      if(this.field_152323_r.isEmpty()) {
         this.field_152323_r.addAll(this.fontRendererObj.listFormattedStringToWidth(this.field_152326_h.func_152561_a().getFormattedText(), (int)((float)this.width * 0.75F)));
         if(this.field_152327_i != null) {
            this.field_152323_r.add("");

            for(ChatComponentTranslation var2 : this.field_152327_i) {
               this.field_152323_r.add(var2.getUnformattedTextForChat());
            }
         }
      }

      if(this.field_152326_h.func_152559_b() != null) {
         this.buttonList.add(new GuiButton(0, this.width / 2 - 155, this.height - 50, 150, 20, I18n.format("gui.cancel", new Object[0])));
         this.buttonList.add(new GuiButton(1, this.width / 2 - 155 + 160, this.height - 50, 150, 20, I18n.format(this.field_152326_h.func_152559_b().getFormattedText(), new Object[0])));
      } else {
         this.buttonList.add(new GuiButton(0, this.width / 2 - 75, this.height - 50, 150, 20, I18n.format("gui.cancel", new Object[0])));
      }

   }

   public void onGuiClosed() {
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      int var4 = Math.max((int)((double)this.height * 0.85D / 2.0D - (double)((float)(this.field_152323_r.size() * this.fontRendererObj.getHeight()) / 2.0F)), 50);
      this.drawCenteredString(this.fontRendererObj, this.field_152324_f.getFormattedText(), this.width / 2, var4 - this.fontRendererObj.getHeight() * 2, 16777215);

      for(String var6 : this.field_152323_r) {
         this.drawCenteredString(this.fontRendererObj, var6, this.width / 2, var4, 10526880);
         var4 += this.fontRendererObj.getHeight();
      }

      super.drawScreen(var1, var2, var3);
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.enabled) {
         if(var1.id == 1) {
            switch(GuiStreamUnavailable$1.$SwitchMap$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason[this.field_152326_h.ordinal()]) {
            case 1:
            case 2:
               this.func_152320_a("https://account.mojang.com/me/settings");
               break;
            case 3:
               this.func_152320_a("https://account.mojang.com/migrate");
               break;
            case 4:
               this.func_152320_a("http://www.apple.com/osx/");
               break;
            case 5:
            case 6:
            case 7:
               this.func_152320_a("http://bugs.mojang.com/browse/MC");
            }
         }

         this.mc.displayGuiScreen(this.parentScreen);
      }

   }

   private void func_152320_a(String var1) {
      String var10000 = "java.awt.Desktop";

      try {
         Class var2 = af_.a(var10000);
         Object var3 = af_.b(var2, "getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
         af_.b(var2, "browse", new Class[]{URI.class}).invoke(var3, new Object[]{new URI(var1)});
      } catch (Throwable var4) {
         LOGGER.error("Couldn\'t open link", var4);
      }

   }

   public static void func_152321_a(GuiScreen var0) {
      Minecraft var1 = Minecraft.getInstance();
      IStream var2 = var1.getTwitchStream();
      if(!OpenGlHelper.framebufferSupported) {
         ArrayList var3 = Lists.newArrayList();
         var3.add(new ChatComponentTranslation("stream.unavailable.no_fbo.version", new Object[]{GL11.glGetString(7938)}));
         var3.add(new ChatComponentTranslation("stream.unavailable.no_fbo.blend", new Object[]{Boolean.valueOf(GLContext.getCapabilities().GL_EXT_blend_func_separate)}));
         var3.add(new ChatComponentTranslation("stream.unavailable.no_fbo.arb", new Object[]{Boolean.valueOf(GLContext.getCapabilities().GL_ARB_framebuffer_object)}));
         var3.add(new ChatComponentTranslation("stream.unavailable.no_fbo.ext", new Object[]{Boolean.valueOf(GLContext.getCapabilities().GL_EXT_framebuffer_object)}));
         var1.displayGuiScreen(new GuiStreamUnavailable(var0, GuiStreamUnavailable$Reason.NO_FBO, var3));
      } else if(var2 instanceof NullStream) {
         if(((NullStream)var2).func_152937_a().getMessage().contains("Can\'t load AMD 64-bit .dll on a IA 32-bit platform")) {
            var1.displayGuiScreen(new GuiStreamUnavailable(var0, GuiStreamUnavailable$Reason.LIBRARY_ARCH_MISMATCH));
         } else {
            var1.displayGuiScreen(new GuiStreamUnavailable(var0, GuiStreamUnavailable$Reason.LIBRARY_FAILURE));
         }
      } else if(!var2.func_152928_D() && var2.func_152912_E() == ErrorCode.TTV_EC_OS_TOO_OLD) {
         switch(GuiStreamUnavailable$1.$SwitchMap$net$minecraft$util$Util$EnumOS[aNh.a().ordinal()]) {
         case 1:
            var1.displayGuiScreen(new GuiStreamUnavailable(var0, GuiStreamUnavailable$Reason.UNSUPPORTED_OS_WINDOWS));
            break;
         case 2:
            var1.displayGuiScreen(new GuiStreamUnavailable(var0, GuiStreamUnavailable$Reason.UNSUPPORTED_OS_MAC));
            break;
         default:
            var1.displayGuiScreen(new GuiStreamUnavailable(var0, GuiStreamUnavailable$Reason.UNSUPPORTED_OS_OTHER));
         }
      } else if(!var1.getTwitchDetails().containsKey("twitch_access_token")) {
         if(var1.getSession().getSessionType() == Session$Type.LEGACY) {
            var1.displayGuiScreen(new GuiStreamUnavailable(var0, GuiStreamUnavailable$Reason.ACCOUNT_NOT_MIGRATED));
         } else {
            var1.displayGuiScreen(new GuiStreamUnavailable(var0, GuiStreamUnavailable$Reason.ACCOUNT_NOT_BOUND));
         }
      } else if(!var2.func_152913_F()) {
         switch(GuiStreamUnavailable$1.$SwitchMap$net$minecraft$client$stream$IStream$AuthFailureReason[var2.func_152918_H().ordinal()]) {
         case 1:
            var1.displayGuiScreen(new GuiStreamUnavailable(var0, GuiStreamUnavailable$Reason.FAILED_TWITCH_AUTH));
            break;
         case 2:
         default:
            var1.displayGuiScreen(new GuiStreamUnavailable(var0, GuiStreamUnavailable$Reason.FAILED_TWITCH_AUTH_ERROR));
         }
      } else if(var2.func_152912_E() != null) {
         List var4 = Arrays.asList(new ChatComponentTranslation[]{new ChatComponentTranslation("stream.unavailable.initialization_failure.extra", new Object[]{ErrorCode.getString(var2.func_152912_E())})});
         var1.displayGuiScreen(new GuiStreamUnavailable(var0, GuiStreamUnavailable$Reason.INITIALIZATION_FAILURE, var4));
      } else {
         var1.displayGuiScreen(new GuiStreamUnavailable(var0, GuiStreamUnavailable$Reason.UNKNOWN));
      }

   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
