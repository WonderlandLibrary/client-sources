package cn.Hanabi.injection.mixins;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.jetbrains.annotations.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.*;
import ClassSub.*;
import org.spongepowered.asm.mixin.injection.*;

@SideOnly(Side.CLIENT)
@Mixin({ GuiScreen.class })
public class MixinGuiScreen
{
    @Shadow
    public Minecraft mc;
    
    @Inject(method = { "sendChatMessage(Ljava/lang/String;Z)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onChat(final String msg, final boolean addToChat, @NotNull final CallbackInfo ci) {
        if (msg.startsWith(".") && msg.length() > 1 && !ModManager.getModule("NoCommand").isEnabled()) {
            if (Hanabi.INSTANCE.commandManager.executeCommand(msg)) {
                this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
            }
            if (Class334.username.length() < 1) {
                System.exit(0);
            }
            ci.cancel();
        }
    }
}
