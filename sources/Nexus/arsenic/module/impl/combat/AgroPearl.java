package arsenic.module.impl.combat;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventSilentRotation;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.doubleproperty.DoubleProperty;
import arsenic.module.property.impl.doubleproperty.DoubleValue;
import arsenic.utils.minecraft.PlayerUtils;
import arsenic.utils.rotations.RotationUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;

@ModuleInfo(name = "AgroPearl", category = ModuleCategory.Combat)
public class AgroPearl extends Module {
    int rodslot;
    boolean usedrod = false;
    int ogslot;
    private boolean switched = false;
    public final DoubleProperty range = new DoubleProperty("Pearl Range", new DoubleValue(0, 50, 10, 0.1));
    @EventLink
    public final Listener<EventSilentRotation> eventSilentRotationListener = event -> {

        if (!PlayerUtils.isPlayerInGame()) {
            return;
        }

        Entity target = PlayerUtils.getClosestPlayerWithin(range.getValue().getInput());
        if (target == null) {
            return;
        }
        for (int slot = 0; slot <= 8; slot++) {
            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
            if (itemInSlot != null && itemInSlot.getItem() instanceof ItemEnderPearl) {
                if (mc.thePlayer.inventory.currentItem != slot && !switched) {
                    mc.thePlayer.inventory.currentItem = slot;
                    rodslot = slot;
                    switched = true;
                }
            }
        }
        if (switched) {
            float[] rotations = RotationUtils.getTargetRotations(target);
            event.setSpeed(180F);
            event.setYaw(rotations[0]);
            event.setPitch(rotations[1] + 10);
            event.setJumpFix(true);
            event.setDoMovementFix(true);
            if (!usedrod) {
                usedrod = true;
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            } else {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                toggle();
            }
        }

    };

    @Override
    protected void onEnable() {
        ogslot = mc.thePlayer.inventory.currentItem;
        usedrod = false;
        switched = false;
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        mc.thePlayer.inventory.currentItem = ogslot;
        super.onDisable();
    }
}
