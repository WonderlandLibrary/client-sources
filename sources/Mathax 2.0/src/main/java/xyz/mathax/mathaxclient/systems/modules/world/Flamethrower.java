package xyz.mathax.mathaxclient.systems.modules.world;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Flamethrower extends Module {
    private Entity entity;

    private int ticks = 0;

    private final SettingGroup sgGeneral = settings.createGroup("General");

    // General

    private final Setting<Object2BooleanMap<EntityType<?>>> entitiesSetting = sgGeneral.add(new EntityTypeListSetting.Builder()
            .name("Entities")
            .description("Entities to cook.")
            .defaultValue(
                    EntityType.PIG,
                    EntityType.COW,
                    EntityType.SHEEP,
                    EntityType.CHICKEN,
                    EntityType.RABBIT
            )
            .build()
    );

    private final Setting<Double> distanceSetting = sgGeneral.add(new DoubleSetting.Builder()
            .name("Distance")
            .description("The maximum distance the animal has to be to be roasted.")
            .min(0.0)
            .defaultValue(5.0)
            .build()
    );

    private final Setting<Boolean> antiBreakSetting = sgGeneral.add(new BoolSetting.Builder()
            .name("Anti break")
            .description("Prevents flint and steel from being broken.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> putOutFireSetting = sgGeneral.add(new BoolSetting.Builder()
            .name("Put out fire")
            .description("Tries to put out the fire when animal is low health, so the items don't burn.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> targetBabiesSetting = sgGeneral.add(new BoolSetting.Builder()
            .name("Target babies")
            .description("If checked babies will also be killed.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Integer> tickIntervalSetting = sgGeneral.add(new IntSetting.Builder()
            .name("Tick interval")
            .defaultValue(5)
            .build()
    );

    private final Setting<Boolean> rotateSetting = sgGeneral.add(new BoolSetting.Builder()
            .name("Rotate")
            .description("Automatically faces towards the animal roasted.")
            .defaultValue(true)
            .build()
    );

    public Flamethrower(Category category) {
        super(category, "Flamethrower", "Ignites every alive piece of food.");
    }

    @Override
    public void onEnable() {
        entity = null;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        entity = null;
        ticks++;
        for (Entity entity : mc.world.getEntities()) {
            if (!entitiesSetting.get().getBoolean(entity.getType()) || mc.player.distanceTo(entity) > distanceSetting.get()) {
                continue;
            }

            if (entity.isFireImmune()) {
                continue;
            }

            if (entity == mc.player) {
                continue;
            }

            if (!targetBabiesSetting.get() && entity instanceof LivingEntity && ((LivingEntity)entity).isBaby()) {
                continue;
            }

            boolean success = selectSlot();
            if (success) {
                this.entity = entity;

                if (rotateSetting.get()) {
                    Rotations.rotate(Rotations.getYaw(entity.getBlockPos()), Rotations.getPitch(entity.getBlockPos()), -100, this::interact);
                } else {
                    interact();
                }

                return;
            }
        }
    }

    private void interact() {
        Block block = mc.world.getBlockState(entity.getBlockPos()).getBlock();
        Block bottom = mc.world.getBlockState(entity.getBlockPos().down()).getBlock();
        if (block == Blocks.WATER || bottom == Blocks.WATER || bottom == Blocks.DIRT_PATH) {
            return;
        }

        if (block == Blocks.GRASS)  {
            mc.interactionManager.attackBlock(entity.getBlockPos(), Direction.DOWN);
        }

        if (putOutFireSetting.get() && entity instanceof LivingEntity animal && animal.getHealth() < 1) {
            mc.interactionManager.attackBlock(entity.getBlockPos(), Direction.DOWN);
            mc.interactionManager.attackBlock(entity.getBlockPos().west(), Direction.DOWN);
            mc.interactionManager.attackBlock(entity.getBlockPos().east(), Direction.DOWN);
            mc.interactionManager.attackBlock(entity.getBlockPos().north(), Direction.DOWN);
            mc.interactionManager.attackBlock(entity.getBlockPos().south(), Direction.DOWN);
        } else {
            if (ticks >= tickIntervalSetting.get() && !entity.isOnFire()) {
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(entity.getPos().subtract(new Vec3d(0, 1, 0)), Direction.UP, entity.getBlockPos().down(), false));
                ticks = 0;
            }
        }

        InvUtils.swapBack();
    }

    private boolean selectSlot() {
        boolean findNewFlintAndSteel = false;
        if (mc.player.getInventory().getMainHandStack().getItem() == Items.FLINT_AND_STEEL) {
            if (antiBreakSetting.get() && mc.player.getInventory().getMainHandStack().getDamage() >= mc.player.getInventory().getMainHandStack().getMaxDamage() - 1) {
                findNewFlintAndSteel = true;
            }
        } else if (mc.player.getInventory().offHand.get(0).getItem() == Items.FLINT_AND_STEEL) {
            if (antiBreakSetting.get() && mc.player.getInventory().offHand.get(0).getDamage() >= mc.player.getInventory().offHand.get(0).getMaxDamage() - 1) {
                findNewFlintAndSteel = true;
            }
        } else {
            findNewFlintAndSteel = true;
        }

        boolean foundFlintAndSteel = !findNewFlintAndSteel;
        if (findNewFlintAndSteel) {
            foundFlintAndSteel = InvUtils.swap(InvUtils.findInHotbar(itemStack -> (!antiBreakSetting.get() || (antiBreakSetting.get() && itemStack.getDamage() < itemStack.getMaxDamage() - 1)) && itemStack.getItem() == Items.FLINT_AND_STEEL).slot(), true);
        }

        return foundFlintAndSteel;
    }
}