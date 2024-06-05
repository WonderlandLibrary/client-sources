package me.dinozoid.strife.module.implementations.combat;

import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.EnumProperty;
import me.dinozoid.strife.util.player.PlayerUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;

@ModuleInfo(name = "AutoHead", renderName = "AutoHead", description = "Automatically uses instant eat items.", category = Category.COMBAT)
public class AutoHeadModule extends Module {

    private final EnumProperty<HealthMode> healthProperty = new EnumProperty("Health", HealthMode.HALF);
    private final Property<Boolean> forceAbsorptionProperty = new Property("Force Absorption", true);
    private final Property<Boolean> checkRegenProperty = new Property("Check Regen", true);

    @Override
    public void init() {
        super.init();
        addValueChangeListener(healthProperty);
    }

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = new Listener<>(event -> {
        final float health;
        switch (healthProperty.value()) {
            case HALF:
                health = mc.thePlayer.getMaxHealth() / 2;
                break;
            case QUARTER:
                health = mc.thePlayer.getMaxHealth() / 4;
                break;
            case THREE_QUARTER:
                health = mc.thePlayer.getMaxHealth() / 3;
                break;
            default:
                health = mc.thePlayer.getMaxHealth();
                break;
        }
        if(mc.thePlayer.getHealth() < health && (!mc.thePlayer.isPotionActive(Potion.regeneration.id) && checkRegenProperty.value()) || (mc.thePlayer.getAbsorptionAmount() <= 0 && forceAbsorptionProperty.value())) {
            for (int slot = PlayerUtil.ONLY_HOT_BAR_BEGIN; slot < PlayerUtil.END; slot++) {
                ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
                if(stack != null && (stack.getDisplayName().contains("Golden Head") || stack.getDisplayName().contains("Rage Potato") || stack.getDisplayName().contains("Fractured Soul"))) {
                    mc.getNetHandler().getNetworkManager().sendPacket(new C09PacketHeldItemChange(slot - PlayerUtil.ONLY_HOT_BAR_BEGIN));
                    mc.getNetHandler().getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventoryContainer.getSlot(slot).getStack()));
                    mc.getNetHandler().getNetworkManager().sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
            }
        }
    });

    private enum HealthMode {
        QUARTER, HALF, THREE_QUARTER, FULL
    }

}
