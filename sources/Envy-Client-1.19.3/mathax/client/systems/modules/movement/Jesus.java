package mathax.client.systems.modules.movement;

import baritone.api.BaritoneAPI;
import com.google.common.collect.Streams;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.entity.player.CanWalkOnFluidEvent;
import mathax.client.events.packets.PacketEvent;
import mathax.client.events.world.CollisionShapeEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.mixin.LivingEntityAccessor;
import mathax.client.mixininterface.IVec3d;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.algorithms.extra.MovementUtils;
import mathax.client.utils.entity.EntityUtils;
import mathax.client.utils.vayzeutils.VulcanBooster;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.GameMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//todo: add a Vulcan Mode
//Done but its kinda janky
public class Jesus extends Module {
    private final BlockPos.Mutable blockPos = new BlockPos.Mutable();

    private boolean preBaritoneAssumeWalkOnWater;
    private boolean preBaritoneAssumeWalkOnLava;

    private int tickTimer = 10;
    private int packetTimer = 0;

    private final SettingGroup sgGeneral = settings.createGroup("General");
    private final SettingGroup sgWater = settings.createGroup("Water");
    private final SettingGroup sgLava = settings.createGroup("Lava");

    // General

    private final Setting<Boolean> powderSnow = sgGeneral.add(new BoolSetting.Builder()
        .name("powder-snow")
        .description("Walk on powder snow.")
        .defaultValue(true)
        .build()
    );

    // Water

    private final Setting<Mode> waterMode = sgWater.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("How to treat the water.")
        .defaultValue(Mode.Solid)
        .build()
    );

    private final Setting<Boolean> dipIfBurning = sgWater.add(new BoolSetting.Builder()
        .name("dip-if-burning")
        .description("Lets you go into the water when you are burning.")
        .defaultValue(true)
        .visible(() -> waterMode.get() == Mode.Solid)
        .build()
    );

    private final Setting<Boolean> dipOnSneakWater = sgWater.add(new BoolSetting.Builder()
        .name("dip-on-sneak")
        .description("Lets you go into the water when your sneak key is held.")
        .defaultValue(true)
        .visible(() -> waterMode.get() == Mode.Solid)
        .build()
    );

    private final Setting<Boolean> dipOnFallWater = sgWater.add(new BoolSetting.Builder()
        .name("dip-on-fall")
        .description("Lets you go into the water when you fall over a certain height.")
        .defaultValue(true)
        .visible(() -> waterMode.get() == Mode.Solid)
        .build()
    );

    private final Setting<Integer> dipFallHeightWater = sgWater.add(new IntSetting.Builder()
        .name("dip-fall-height")
        .description("The fall height at which you will go into the water.")
        .defaultValue(4)
        .range(1, 255)
        .sliderRange(3, 20)
        .visible(() -> waterMode.get() == Mode.Solid && dipOnFallWater.get())
        .build()
    );

    // Lava

    private final Setting<Mode> lavaMode = sgLava.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("How to treat the lava.")
        .defaultValue(Mode.Solid)
        .build()
    );

    private final Setting<Boolean> dipIfFireResistant = sgLava.add(new BoolSetting.Builder()
        .name("dip-if-resistant")
        .description("Lets you go into the lava if you have Fire Resistance effect.")
        .defaultValue(true)
        .visible(() -> lavaMode.get() == Mode.Solid)
        .build()
    );

    private final Setting<Boolean> dipOnSneakLava = sgLava.add(new BoolSetting.Builder()
        .name("dip-on-sneak")
        .description("Lets you go into the lava when your sneak key is held.")
        .defaultValue(true)
        .visible(() -> lavaMode.get() == Mode.Solid)
        .build()
    );

    private final Setting<Boolean> dipOnFallLava = sgLava.add(new BoolSetting.Builder()
        .name("dip-on-fall")
        .description("Lets you go into the lava when you fall over a certain height.")
        .defaultValue(true)
        .visible(() -> lavaMode.get() == Mode.Solid)
        .build()
    );

    private final Setting<Integer> dipFallHeightLava = sgLava.add(new IntSetting.Builder()
        .name("dip-fall-height")
        .description("The fall height at which you will go into the lava.")
        .defaultValue(4)
        .range(1, 255)
        .sliderRange(3, 20)
        .visible(() -> lavaMode.get() == Mode.Solid && dipOnFallLava.get())
        .build()
    );

    public Jesus() {
        super(Categories.Movement, Items.IRON_BOOTS, "Jesus", "Allows you to walk on liquids and powder snow like Jesus.");
    }

    @Override
    public boolean onActivate() {
        preBaritoneAssumeWalkOnWater = BaritoneAPI.getSettings().assumeWalkOnWater.value;
        preBaritoneAssumeWalkOnLava = BaritoneAPI.getSettings().assumeWalkOnLava.value;

        BaritoneAPI.getSettings().assumeWalkOnWater.value = waterMode.get() == Mode.Solid;
        BaritoneAPI.getSettings().assumeWalkOnLava.value = lavaMode.get() == Mode.Solid;
        return false;
    }

    @Override
    public void onDeactivate() {
        BaritoneAPI.getSettings().assumeWalkOnWater.value = preBaritoneAssumeWalkOnWater;
        BaritoneAPI.getSettings().assumeWalkOnLava.value = preBaritoneAssumeWalkOnLava;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if ((waterMode.get() == Mode.Bob && mc.player.isTouchingWater()) || (lavaMode.get() == Mode.Bob && mc.player.isInLava())) {
            double fluidHeight;
            if (mc.player.isInLava()) fluidHeight = mc.player.getFluidHeight(FluidTags.LAVA);
            else fluidHeight = mc.player.getFluidHeight(FluidTags.WATER);

            double swimHeight = mc.player.getSwimHeight();

            if (mc.player.isTouchingWater() && fluidHeight > swimHeight) ((LivingEntityAccessor) mc.player).swimUpwards(FluidTags.WATER);
            else if (mc.player.isOnGround() && fluidHeight <= swimHeight && ((LivingEntityAccessor) mc.player).getJumpCooldown() == 0) {
                mc.player.jump();
                ((LivingEntityAccessor) mc.player).setJumpCooldown(10);
            } else ((LivingEntityAccessor) mc.player).swimUpwards(FluidTags.LAVA);
        }

        if (mc.player.isTouchingWater() && !waterShouldBeSolid()) return;
        if (mc.player.isInLava() && !lavaShouldBeSolid()) return;

        if (mc.player.isTouchingWater() || mc.player.isInLava()) {
            ((IVec3d) mc.player.getVelocity()).setY(0.11);
            tickTimer = 0;
            return;
        }

        if (tickTimer == 0) ((IVec3d) mc.player.getVelocity()).setY(0.30);
        else if (tickTimer == 1) ((IVec3d) mc.player.getVelocity()).setY(0);

        tickTimer++;

        Block water = mc.world.getBlockState(mc.player.getBlockPos().down()).getBlock();

        if (water == Blocks.WATER && waterMode.get() == Mode.Hydrophobe || water == Blocks.LAVA && lavaMode.get() == Mode.Hydrophobe) {
            mc.player.setPos(mc.player.getX(), mc.player.getY() + 0.2, mc.player.getZ());
            mc.player.setVelocity(mc.player.getVelocity().x, 0, mc.player.getVelocity().z);
            mc.player.fallDistance = 0f;
            mc.player.setOnGround(false);
            if (mc.player.getVelocity().y < 0.3) {
                mc.player.setVelocity(mc.player.getVelocity().x, 0.5, mc.player.getVelocity().z);
            }
        }

        if (waterMode.get() == Mode.Vulcan) {
            if (MovementUtils.isMoving()) {
                assert mc.player != null;
                if (mc.player.isTouchingWater()) {
                    mc.player.setVelocity(mc.player.getVelocity().x, 0.3, mc.player.getVelocity().z);
                    VulcanBooster.VulcanBoost(0.14);
                }
                else {
                    if (!mc.player.isOnGround()) {
                        mc.player.setVelocity(mc.player.getVelocity().x, -0.25, mc.player.getVelocity().z);
                    }
                }
            }
            else {
                assert mc.player != null;
                if (mc.player.isTouchingWater()) {
                    mc.player.setVelocity(mc.player.getVelocity().x, 0.01, mc.player.getVelocity().z);
                }
                else {
                    mc.player.setVelocity(mc.player.getVelocity().x,-0.01 ,mc.player.getVelocity().z);
                }
            }
        }
    }

    @EventHandler
    private void onCanWalkOnFluid(CanWalkOnFluidEvent event) {
        if ((event.fluidState.getFluid() == Fluids.WATER || event.fluidState.getFluid() == Fluids.FLOWING_WATER) && waterShouldBeSolid()) event.walkOnFluid = true;
        else if ((event.fluidState.getFluid() == Fluids.LAVA || event.fluidState.getFluid() == Fluids.FLOWING_LAVA) && lavaShouldBeSolid()) event.walkOnFluid = true;
    }

    @EventHandler
    private void onFluidCollisionShape(CollisionShapeEvent event) {
        if (event.type != CollisionShapeEvent.CollisionType.FLUID) return;
        if (event.state.getMaterial() == Material.WATER && !mc.player.isTouchingWater() && waterShouldBeSolid()) event.shape = VoxelShapes.fullCube();
        else if (event.state.getMaterial() == Material.LAVA && !mc.player.isInLava() && lavaShouldBeSolid()) event.shape = VoxelShapes.fullCube();
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        if (!(event.packet instanceof PlayerMoveC2SPacket packet)) return;
        if (mc.player.isTouchingWater() && !waterShouldBeSolid()) return;
        if (mc.player.isInLava() && !lavaShouldBeSolid()) return;

        if (!(packet instanceof PlayerMoveC2SPacket.PositionAndOnGround || packet instanceof PlayerMoveC2SPacket.Full)) return;

        if (mc.player.isTouchingWater() || mc.player.isInLava() || mc.player.fallDistance > 3f || !isOverLiquid()) return;

        if (mc.player.input.movementForward == 0 && mc.player.input.movementSideways == 0) {
            event.cancel();
            return;
        }

        if (packetTimer++ < 4) return;
        packetTimer = 0;

        event.cancel();

        double x = packet.getX(0);
        double y = packet.getY(0) + 0.05;
        double z = packet.getZ(0);

        Packet<?> newPacket;
        if (packet instanceof PlayerMoveC2SPacket.PositionAndOnGround) newPacket = new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, true);
        else newPacket = new PlayerMoveC2SPacket.Full(x, y, z, packet.getYaw(0), packet.getPitch(0), true);

        mc.getNetworkHandler().getConnection().send(newPacket);
    }

    private boolean waterShouldBeSolid() {
        if (EntityUtils.getGameMode(mc.player) == GameMode.SPECTATOR || mc.player.getAbilities().flying) return false;

        if (dipIfBurning.get() && mc.player.isOnFire()) return false;

        if (dipOnSneakWater.get() && mc.options.sneakKey.isPressed()) return false;
        if (dipOnFallWater.get() && mc.player.fallDistance > dipFallHeightWater.get()) return false;

        return waterMode.get() == Mode.Solid;
    }

    private boolean lavaShouldBeSolid() {
        if (EntityUtils.getGameMode(mc.player) == GameMode.SPECTATOR || mc.player.getAbilities().flying) return false;

        if (!lavaIsSafe() && lavaMode.get() == Mode.Solid) return true;

        if (dipOnSneakLava.get() && mc.options.sneakKey.isPressed()) return false;
        if (dipOnFallLava.get() && mc.player.fallDistance > dipFallHeightLava.get()) return false;

        return lavaMode.get() == Mode.Solid;
    }

    private boolean lavaIsSafe() {
        if (!dipIfFireResistant.get()) return false;
        return mc.player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE) && (mc.player.getStatusEffect(StatusEffects.FIRE_RESISTANCE).getDuration() > ProtectionEnchantment.transformFireDuration(mc.player, 15 * 20));
    }

    private boolean isOverLiquid() {
        boolean foundLiquid = false;
        boolean foundSolid = false;

        List<Box> blockCollisions = Streams.stream(mc.world.getBlockCollisions(mc.player, mc.player.getBoundingBox().offset(0, -0.5, 0))).map(VoxelShape::getBoundingBox).collect(Collectors.toCollection(ArrayList::new));

        for (Box bb : blockCollisions) {
            blockPos.set(MathHelper.lerp(0.5D, bb.minX, bb.maxX), MathHelper.lerp(0.5D, bb.minY, bb.maxY), MathHelper.lerp(0.5D, bb.minZ, bb.maxZ));
            Material material = mc.world.getBlockState(blockPos).getMaterial();

            if (material == Material.WATER || material == Material.LAVA) foundLiquid = true;
            else if (material != Material.AIR) foundSolid = true;
        }

        return foundLiquid && !foundSolid;
    }

    public boolean canWalkOnPowderSnow() {
        return isActive() && powderSnow.get();
    }

    public enum Mode {
        Solid("Solid"),
        Bob("Bob"),

        Ignore("Ignore"),
        Hydrophobe("Vulcan-Blatant"),
        Vulcan("Vulcan");

        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
