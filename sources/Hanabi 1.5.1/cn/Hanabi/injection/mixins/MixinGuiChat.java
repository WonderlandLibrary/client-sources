package cn.Hanabi.injection.mixins;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.jetbrains.annotations.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.*;
import org.spongepowered.asm.mixin.injection.*;
import cn.Hanabi.modules.Player.*;

@SideOnly(Side.CLIENT)
@Mixin({ GuiChat.class })
public abstract class MixinGuiChat
{
    @Shadow
    private boolean waitingOnAutocomplete;
    
    @Shadow
    public abstract void onAutocompleteResponse(final String[] p0);
    
    @Inject(method = { "sendAutocompleteRequest" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/network/NetHandlerPlayClient;addToSendQueue(Lnet/minecraft/network/Packet;)V", shift = At.Shift.BEFORE) }, cancellable = true)
    private void autocomplete(final String cmd, final String p_146405_2_, @NotNull final CallbackInfo ci) {
        if (cmd.startsWith(".") && !ModManager.getModule("NoCommand").isEnabled()) {
            final String[] ls = Hanabi.INSTANCE.commandManager.autoComplete(cmd).toArray(new String[0]);
            if (ls.length == 0 || cmd.toLowerCase().endsWith(ls[ls.length - 1].toLowerCase())) {
                return;
            }
            this.waitingOnAutocomplete = true;
            this.onAutocompleteResponse(ls);
            ci.cancel();
        }
    }
    
    @Inject(method = { "drawScreen" }, at = { @At("HEAD") })
    public void mouse(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo info) {
        if (ModManager.getModule("StaffAnalyzer").isEnabled() && StaffAnalyzer.ui != null) {
            StaffAnalyzer.ui.mouseListener(mouseX, mouseY);
        }
        if (Hanabi.INSTANCE.sbm != null) {
            Hanabi.INSTANCE.sbm.moveWindow(mouseX, mouseY);
        }
    }
}
