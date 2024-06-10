package ac.sapphire.client.mixin.gui;

import com.google.common.collect.Ordering;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiPlayerTabOverlay.class)
public interface IPlayerTabOverlayAccessor {
    @Accessor
    Ordering<NetworkPlayerInfo> getField_175252_a();
}
