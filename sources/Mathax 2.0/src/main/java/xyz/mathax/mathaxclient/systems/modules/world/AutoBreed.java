package xyz.mathax.mathaxclient.systems.modules.world;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.List;

public class AutoBreed extends Module {
    private final List<Entity> animalsFed = new ArrayList<>();

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Object2BooleanMap<EntityType<?>>> entitiesSetting = generalSettings.add(new EntityTypeListSetting.Builder()
            .name("Entities")
            .description("Entities to breed.")
            .defaultValue(EntityType.HORSE,
                    EntityType.DONKEY,
                    EntityType.COW,
                    EntityType.MOOSHROOM,
                    EntityType.SHEEP,
                    EntityType.PIG,
                    EntityType.CHICKEN,
                    EntityType.WOLF,
                    EntityType.CAT,
                    EntityType.OCELOT,
                    EntityType.RABBIT,
                    EntityType.LLAMA,
                    EntityType.TURTLE,
                    EntityType.PANDA,
                    EntityType.FOX,
                    EntityType.BEE,
                    EntityType.STRIDER,
                    EntityType.HOGLIN)
            .onlyAttackable()
            .build()
    );

    private final Setting<Double> rangeSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Range")
            .description("How far away the animals can be to be bred.")
            .min(0)
            .defaultValue(4.5)
            .build()
    );

    private final Setting<Hand> handSetting = generalSettings.add(new EnumSetting.Builder<Hand>()
            .name("Hand for breeding")
            .description("The hand to use for breeding.")
            .defaultValue(Hand.MAIN_HAND)
            .build()
    );

    private final Setting<Boolean> ignoreBabiesSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Ignore babies")
            .description("Whether or not to ignore the baby variants of the specified entity.")
            .defaultValue(true)
            .build()
    );

    public AutoBreed(Category category) {
        super(category, "Auto Breed", "Automatically breeds specified animals.");
    }

    @Override
    public void onEnable() {
        animalsFed.clear();
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof AnimalEntity animal) {
                if (!entitiesSetting.get().getBoolean(animal.getType()) || (animal.isBaby() && !ignoreBabiesSetting.get()) || animalsFed.contains(animal) || mc.player.distanceTo(animal) > rangeSetting.get() || !animal.isBreedingItem(handSetting.get() == Hand.MAIN_HAND ? mc.player.getMainHandStack() : mc.player.getOffHandStack())) {
                    continue;
                }

                Rotations.rotate(Rotations.getYaw(entity), Rotations.getPitch(entity), -100, () -> {
                    mc.interactionManager.interactEntity(mc.player, animal, handSetting.get());
                    mc.player.swingHand(handSetting.get());
                    animalsFed.add(animal);
                });

                return;
            }
        }
    }
}