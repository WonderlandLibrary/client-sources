package xyz.mathax.mathaxclient.mixin;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.misc.InventoryTweaks;

@Mixin(ShulkerBoxScreen.class)
public abstract class ShulkerBoxScreenMixin extends HandledScreen<ShulkerBoxScreenHandler> {
    public ShulkerBoxScreenMixin(ShulkerBoxScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        InventoryTweaks inventoryTweaks = Modules.get().get(InventoryTweaks.class);
        if (inventoryTweaks.isEnabled() && inventoryTweaks.showButtons()) {
            addDrawableChild(new ButtonWidget.Builder(Text.literal("Steal"), button -> inventoryTweaks.steal(handler)).position(x + backgroundWidth - 88, y + 3).size(40, 12).build());
            addDrawableChild(new ButtonWidget.Builder(Text.literal("Dump"), button -> inventoryTweaks.dump(handler)).position(x + backgroundWidth - 46, y + 3).size(40, 12).build());
        }
    }
}
