package com.example.editme.mixin.client;

import com.example.editme.EditmeMod;
import com.example.editme.events.GuiScreenEvent;
import com.example.editme.util.client.Wrapper;
import com.example.editme.util.config.ConfigurationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {Minecraft.class},
   priority = Integer.MAX_VALUE
)
public class MixinMinecraft {
   @Shadow
   WorldClient field_71441_e;
   @Shadow
   EntityPlayerSP field_71439_g;
   @Shadow
   GuiScreen field_71462_r;
   @Shadow
   GameSettings field_71474_y;
   @Shadow
   GuiIngame field_71456_v;
   @Shadow
   boolean field_71454_w;
   @Shadow
   SoundHandler field_147127_av;

   @Inject(
      method = {"displayGuiScreen"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void displayGuiScreen(GuiScreen var1, CallbackInfo var2) {
      GuiScreenEvent.Closed var3 = new GuiScreenEvent.Closed(Wrapper.getMinecraft().field_71462_r);
      EditmeMod.EVENT_BUS.post(var3);
      GuiScreenEvent.Displayed var4 = new GuiScreenEvent.Displayed(var1);
      EditmeMod.EVENT_BUS.post(var4);
      Object var10 = var4.getScreen();
      if (var10 == null && this.field_71441_e == null) {
         var10 = new GuiMainMenu();
      } else if (var10 == null && this.field_71439_g.func_110143_aJ() <= 0.0F) {
         var10 = new GuiGameOver((ITextComponent)null);
      }

      GuiScreen var5 = this.field_71462_r;
      GuiOpenEvent var6 = new GuiOpenEvent((GuiScreen)var10);
      if (!MinecraftForge.EVENT_BUS.post(var6)) {
         var1 = var6.getGui();
         if (var5 != null && var1 != var5) {
            var5.func_146281_b();
         }

         if (var1 instanceof GuiMainMenu || var1 instanceof GuiMultiplayer) {
            this.field_71474_y.field_74330_P = false;
            this.field_71456_v.func_146158_b().func_146231_a(true);
         }

         this.field_71462_r = var1;
         if (var1 != null) {
            Minecraft.func_71410_x().func_71364_i();
            KeyBinding.func_74506_a();

            while(true) {
               if (!Mouse.next()) {
                  while(Keyboard.next()) {
                  }

                  ScaledResolution var7 = new ScaledResolution(Minecraft.func_71410_x());
                  int var8 = var7.func_78326_a();
                  int var9 = var7.func_78328_b();
                  var1.func_146280_a(Minecraft.func_71410_x(), var8, var9);
                  this.field_71454_w = false;
                  break;
               }
            }
         } else {
            this.field_147127_av.func_147687_e();
            Minecraft.func_71410_x().func_71381_h();
         }

         var2.cancel();
      }
   }

   @Inject(
      method = {"run"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V",
   shift = At.Shift.BEFORE
)}
   )
   public void displayCrashReport(CallbackInfo var1) {
      this.save();
   }

   @Inject(
      method = {"shutdown"},
      at = {@At("HEAD")}
   )
   public void shutdown(CallbackInfo var1) {
      this.save();
   }

   private void save() {
      System.out.println("Shutting down: saving EDITME configuration");
      ConfigurationUtil.saveConfiguration();
      System.out.println("Configuration saved.");
   }
}
