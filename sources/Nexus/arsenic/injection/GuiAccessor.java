package arsenic.injection;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Gui.class)
public interface GuiAccessor {
    @Invoker
    void callDrawHorizontalLine(int p_drawHorizontalLine_1_, int p_drawHorizontalLine_2_, int p_drawHorizontalLine_3_, int p_drawHorizontalLine_4_);

    @Invoker
    void callDrawGradientRect(int p_drawGradientRect_1_, int p_drawGradientRect_2_, int p_drawGradientRect_3_, int p_drawGradientRect_4_, int p_drawGradientRect_5_, int p_drawGradientRect_6_);
}
