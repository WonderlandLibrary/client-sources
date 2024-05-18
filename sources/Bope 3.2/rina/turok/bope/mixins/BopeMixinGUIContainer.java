package rina.turok.bope.mixins;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(
   value = {GuiContainer.class},
   priority = 998
)
public abstract class BopeMixinGUIContainer extends GuiScreen {
}
