package me.travis.wurstplus.module.modules.combat;

import java.util.OptionalInt;

import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Module.Info(name = "AutoTotem", category = Module.Category.COMBAT)
public class AutoTotem extends Module {

    private Setting<Boolean> soft = this.register(Settings.b("Soft", true));
    private Setting<Integer> healthSwitch = this.register(Settings.integerBuilder("Health Switch").withRange(1, 20).withValue(4).withVisibility(o -> soft.getValue()).build());

    @Override
    public void onUpdate() {
        if (mc.player == null) return;
        if (mc.currentScreen instanceof GuiContainer) return;
        if (this.soft.getValue()) {
            if (mc.player.getHealth() + mc.player.getAbsorptionAmount() <= (float)this.healthSwitch.getValue() && this.getOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
                return;
            }
            if (!this.getOffhand().isEmpty() && mc.player.getHealth() + mc.player.getAbsorptionAmount() >= (float)this.healthSwitch.getValue()) {
                return;
            }
            this.findItem(Items.TOTEM_OF_UNDYING).ifPresent(slot -> {
                this.invPickup(slot);
                this.invPickup(45);
                this.invPickup(slot);
            });
        } else {
            if (this.getOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
                return;
            }
            this.findItem(Items.TOTEM_OF_UNDYING).ifPresent(slot -> {
                this.invPickup(slot);
                this.invPickup(45);
                this.invPickup(slot);
            });
        }        
    }

    private void invPickup(int slot) {
        mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
    }

    private OptionalInt findItem(Item ofType) {
        for (int i = 44; i >= 9; --i) {
            if (mc.player.inventoryContainer.getSlot(i).getStack().getItem() != ofType) continue;
            return OptionalInt.of(i);
        }
        return OptionalInt.empty();
    }

    private ItemStack getOffhand() {
        return mc.player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
    }
}
