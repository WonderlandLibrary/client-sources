package net.silentclient.client.mixin.accessors;

import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GuiIngame.class)
public interface GuiInGameAccessor {
    @Invoker("showCrosshair")
    boolean silent$showCrosshair();
}
