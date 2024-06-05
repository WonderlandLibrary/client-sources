package cn.Hanabi.injection.mixins;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.*;
import cn.Hanabi.modules.Player.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiChest.class })
public class MixinGuiChest
{
    @Shadow
    private IInventory lowerChestInventory;
    
    @Inject(method = { "drawGuiContainerForegroundLayer" }, at = { @At("HEAD") })
    private void nmsl(final int mouseX, final int mouseY, final CallbackInfo ci) {
        if (StatCollector.translateToLocal("container.chest").equalsIgnoreCase(this.lowerChestInventory.getDisplayName().getUnformattedText()) || StatCollector.translateToLocal("container.chestDouble").equalsIgnoreCase(this.lowerChestInventory.getDisplayName().getUnformattedText())) {
            ChestStealer.isChest = true;
        }
        else {
            ChestStealer.isChest = false;
        }
    }
}
