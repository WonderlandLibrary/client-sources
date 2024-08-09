package xyz.mathax.mathaxclient.systems.modules.world;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.entity.SortPriority;
import xyz.mathax.mathaxclient.utils.entity.TargetUtils;
import xyz.mathax.mathaxclient.utils.player.FindItemResult;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class AutoNametag extends Module {
    private Entity target;

    private boolean offHand;

    private final SettingGroup generalSettings = settings.createGroup("General");

    private final Setting<Object2BooleanMap<EntityType<?>>> entitiesSetting = generalSettings.add(new EntityTypeListSetting.Builder()
            .name("Entities")
            .description("Which entities to nametag.")
            .build()
    );

    private final Setting<Double> rangeSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Range")
            .description("The maximum range an entity can be to be nametagged.")
            .defaultValue(5)
            .min(0)
            .sliderRange(0, 6)
            .build()
    );

    private final Setting<SortPriority> prioritySetting = generalSettings.add(new EnumSetting.Builder<SortPriority>()
            .name("Priority")
            .description("Priority sort")
            .defaultValue(SortPriority.Lowest_Distance)
            .build()
    );

    private final Setting<Boolean> renametagSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Renametag")
            .description("Allows already nametagged entities to be renamed.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> rotateSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Rotate")
            .description("Automatically faces towards the mob being nametagged.")
            .defaultValue(true)
            .build()
    );

    public AutoNametag(Category category) {
        super(category, "Auto Nametag", "Automatically uses nametags on entities without a nametag. WILL nametag ALL entities in the specified distance.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        FindItemResult findNametag = InvUtils.findInHotbar(Items.NAME_TAG);
        if (!findNametag.found()) {
            error("No nametag found in hotbar, disabling...");
            forceToggle(false);
            return;
        }

        target = TargetUtils.get(entity -> {
            if (PlayerUtils.distanceTo(entity) > rangeSetting.get()) {
                return false;
            }

            if (!entitiesSetting.get().getBoolean(entity.getType())) {
                return false;
            }

            if (entity.hasCustomName()) {
                return renametagSetting.get() && entity.getCustomName() != mc.player.getInventory().getStack(findNametag.slot()).getName();
            }

            return false;
        }, prioritySetting.get());

        if (target == null) {
            return;
        }

        InvUtils.swap(findNametag.slot(), true);

        offHand = findNametag.isOffhand();

        if (rotateSetting.get()) {
            Rotations.rotate(Rotations.getYaw(target), Rotations.getPitch(target), -100, this::interact);
        } else {
            interact();
        }
    }

    private void interact() {
        mc.interactionManager.interactEntity(mc.player, target, offHand ? Hand.OFF_HAND : Hand.MAIN_HAND);
        InvUtils.swapBack();
    }
}