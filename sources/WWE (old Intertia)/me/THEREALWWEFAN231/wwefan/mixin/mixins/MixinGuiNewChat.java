package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import java.util.*;
import wwe.modules.exploits.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { GuiNewChat.class }, priority = 9999)
public class MixinGuiNewChat
{
    public MixinGuiNewChat() {
        super();
    }
    
    @Redirect(method = { "setChatLine" }, at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 0))
    public int setChatLine0(final List list) {
        return vlwv.hNxW.qHPF((Class<InfiniteChatLength>)PECw.class).IuiN() ? -2147483647 : list.size();
    }
    
    @Redirect(method = { "setChatLine" }, at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 2))
    public int setChatLine2(final List list) {
        return vlwv.hNxW.qHPF((Class<InfiniteChatLength>)PECw.class).IuiN() ? -2147483647 : list.size();
    }
}
