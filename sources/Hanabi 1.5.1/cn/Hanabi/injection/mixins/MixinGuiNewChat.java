package cn.Hanabi.injection.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.*;
import com.darkmagician6.eventapi.events.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiNewChat.class })
public class MixinGuiNewChat
{
    @Inject(method = { "printChatMessageWithOptionalDeletion" }, at = { @At("HEAD") }, cancellable = true)
    private void eventchat(final IChatComponent chatComponent, final int chatLineId, final CallbackInfo ci) {
        final EventChat event = new EventChat(chatComponent.getUnformattedText());
        EventManager.call(event);
        if (event.cancelled) {
            ci.cancel();
        }
    }
}
