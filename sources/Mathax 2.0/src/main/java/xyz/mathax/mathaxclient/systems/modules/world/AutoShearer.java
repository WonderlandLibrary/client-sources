package xyz.mathax.mathaxclient.systems.modules.world;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.DoubleSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.player.FindItemResult;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.Hand;

public class AutoShearer extends Module {
    private Entity entity;

    private boolean offHand;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Double> distanceSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Distance")
            .description("The maximum distance the sheep have to be to be sheared.")
            .defaultValue(5.0)
            .min(0.0)
            .build()
    );

    private final Setting<Boolean> antiBreakSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Anti break")
            .description("Prevent shears from being broken.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> rotateSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Rotate")
            .description("Automatically faces towards the animal being sheared.")
            .defaultValue(true)
            .build()
    );

    public AutoShearer(Category category) {
        super(category, "Auto Shearer", "Automatically shears sheep.");
    }

    @Override
    public void onDisable() {
        entity = null;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        entity = null;
        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof SheepEntity) || ((SheepEntity) entity).isSheared() || ((SheepEntity) entity).isBaby() || mc.player.distanceTo(entity) > distanceSetting.get()) {
                continue;
            }

            boolean findNewShears = false;
            if (mc.player.getInventory().getMainHandStack().getItem() instanceof ShearsItem) {
                if (antiBreakSetting.get() && mc.player.getInventory().getMainHandStack().getDamage() >= mc.player.getInventory().getMainHandStack().getMaxDamage() - 1) {
                    findNewShears = true;
                }
            } else if (mc.player.getInventory().offHand.get(0).getItem() instanceof ShearsItem) {
                if (antiBreakSetting.get() && mc.player.getInventory().offHand.get(0).getDamage() >= mc.player.getInventory().offHand.get(0).getMaxDamage() - 1) {
                    findNewShears = true;
                } else {
                    offHand = true;
                }
            } else {
                findNewShears = true;
            }

            boolean foundShears = !findNewShears;
            if (findNewShears) {
                FindItemResult shears = InvUtils.findInHotbar(itemStack -> (!antiBreakSetting.get() || (antiBreakSetting.get() && itemStack.getDamage() < itemStack.getMaxDamage() - 1)) && itemStack.getItem() == Items.SHEARS);
                if (InvUtils.swap(shears.slot(), true)) {
                    foundShears = true;
                }
            }

            if (foundShears) {
                this.entity = entity;

                if (rotateSetting.get()) {
                    Rotations.rotate(Rotations.getYaw(entity), Rotations.getPitch(entity), -100, this::interact);
                } else {
                    interact();
                }

                return;
            }
        }
    }

    private void interact() {
        mc.interactionManager.interactEntity(mc.player, entity, offHand ? Hand.OFF_HAND : Hand.MAIN_HAND);
        InvUtils.swapBack();
    }
}