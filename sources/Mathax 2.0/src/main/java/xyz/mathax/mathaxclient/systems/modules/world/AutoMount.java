package xyz.mathax.mathaxclient.systems.modules.world;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Hand;

public class AutoMount extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup mountSettings = settings.createGroup("Mount");

    // General

    private final Setting<Boolean> checkSaddleSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Check saddle")
            .description("Checks if the entity contains a saddle before mounting.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> rotateSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Rotate")
            .description("Faces the entity you mount.")
            .defaultValue(true)
            .build()
    );

    // Mount

    private final Setting<Boolean> horsesSetting = mountSettings.add(new BoolSetting.Builder()
            .name("Horse")
            .description("Mount horses.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> donkeysSetting = mountSettings.add(new BoolSetting.Builder()
            .name("Donkey")
            .description("Mount donkeys.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> mulesSetting = mountSettings.add(new BoolSetting.Builder()
            .name("Mule")
            .description("Mount mules.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> skeletonHorseSetting = mountSettings.add(new BoolSetting.Builder()
            .name("Skeleton Horse")
            .description("Mount skeleton horses.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> llamasSetting = mountSettings.add(new BoolSetting.Builder()
            .name("Llama")
            .description("Mount llamas.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> pigsSetting = mountSettings.add(new BoolSetting.Builder()
            .name("Pig")
            .description("Mount pigs.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> boatsSetting = mountSettings.add(new BoolSetting.Builder()
            .name("Boat")
            .description("Mount boats.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> minecartsSetting = mountSettings.add(new BoolSetting.Builder()
            .name("Minecart")
            .description("Mount minecarts.")
            .defaultValue(false)
            .build()
    );

    public AutoMount(Category category) {
        super(category, "Auto Mount", "Automatically mounts entities.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player.hasVehicle()) {
            return;
        }

        for (Entity entity : mc.world.getEntities()){
            if (mc.player.distanceTo(entity) > 4) {
                continue;
            }

            if (mc.player.getMainHandStack().getItem() instanceof SpawnEggItem) {
                return;
            }

            if (donkeysSetting.get() && entity instanceof DonkeyEntity && (!checkSaddleSetting.get() || ((DonkeyEntity) entity).isSaddled())) {
                interact(entity);
            } else if (llamasSetting.get() && entity instanceof LlamaEntity) {
                interact(entity);
            } else if (boatsSetting.get() && entity instanceof BoatEntity) {
                interact(entity);
            } else if (minecartsSetting.get() && entity instanceof MinecartEntity) {
                interact(entity);
            } else if (horsesSetting.get() && entity instanceof HorseEntity && (!checkSaddleSetting.get() || ((HorseEntity) entity).isSaddled())) {
                interact(entity);
            } else if (pigsSetting.get() && entity instanceof PigEntity && ((PigEntity) entity).isSaddled()) {
                interact(entity);
            } else if (mulesSetting.get() && entity instanceof MuleEntity && (!checkSaddleSetting.get() || ((MuleEntity) entity).isSaddled())) {
                interact(entity);
            } else if (skeletonHorseSetting.get() && entity instanceof SkeletonHorseEntity && (!checkSaddleSetting.get() || ((SkeletonHorseEntity) entity).isSaddled())) {
                interact(entity);
            }
        }
    }

    private void interact(Entity entity) {
        if (rotateSetting.get()) {
            Rotations.rotate(Rotations.getYaw(entity), Rotations.getPitch(entity), -100, () -> mc.interactionManager.interactEntity(mc.player, entity, Hand.MAIN_HAND));
        } else {
            mc.interactionManager.interactEntity(mc.player, entity, Hand.MAIN_HAND);
        }
    }
}