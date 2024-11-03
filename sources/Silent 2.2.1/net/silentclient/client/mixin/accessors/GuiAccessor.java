package net.silentclient.client.mixin.accessors;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Gui.class)
public interface GuiAccessor {
    @Invoker("drawHorizontalLine") void silent$drawHorizontalLine(int startX, int endX, int y, int color);
    @Invoker("drawVerticalLine") void silent$drawVerticalLine(int x, int startY, int endY, int color);

    @Invoker("drawGradientRect") void silent$drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor);
}
