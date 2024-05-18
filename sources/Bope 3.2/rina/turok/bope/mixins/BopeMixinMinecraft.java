package rina.turok.bope.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.events.BopeEventGUIScreen;

@Mixin({Minecraft.class})
public class BopeMixinMinecraft {
   @Inject(
      method = "displayGuiScreen",
      at = @At("HEAD")
   )
   private void displayGuiScreen(GuiScreen guiScreenIn, CallbackInfo info) {
      BopeEventGUIScreen guiscreen = new BopeEventGUIScreen(guiScreenIn);
      Bope.ZERO_ALPINE_EVENT_BUS.post(guiscreen);
   }

   @Inject(
      method = "shutdown",
      at = @At("HEAD")
   )
   private void shutdown(CallbackInfo info) {
      Bope.send_client_log("The client were saved.");
      Bope.get_config_manager().save_friends();
      Bope.get_config_manager().save_log();
   }

   @Redirect(
      method = "run",
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"
)
   )
   private void crash(Minecraft minecraft, CrashReport crash) {
      Bope.send_client_log("The client were saved before crash.");
      Bope.send_client_log(crash.getCrashCause() + ": " + crash.getDescription());
      Bope.get_config_manager().save_friends();
      Bope.get_config_manager().save_log();
   }
}
