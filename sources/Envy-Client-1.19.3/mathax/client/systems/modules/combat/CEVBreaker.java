package mathax.client.systems.modules.combat;

import mathax.client.MatHax;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.packets.PacketEvent;
import mathax.client.events.render.Render3DEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.mixininterface.IVec3d;
import mathax.client.renderer.ShapeMode;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.entity.EntityUtils;
import mathax.client.utils.entity.SortPriority;
import mathax.client.utils.entity.TargetUtils;
import mathax.client.utils.player.DamageUtils;
import mathax.client.utils.player.InvUtils;
import mathax.client.utils.player.Rotations;
import mathax.client.utils.render.color.SettingColor;
import mathax.client.utils.world.BlockUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class CEVBreaker extends Module {
    private static final boolean assertionsDisabled = !CEVBreaker.class.desiredAssertionStatus();

    private BlockPos pos;

    private int pause;

    private boolean isDone;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgRender = settings.createGroup("Render");

    // General

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("How many ticks between block placements.")
        .defaultValue(4)
        .min(0)
        .sliderRange(0, 20)
        .build()
    );

    public final Setting<Double> targetRange = sgGeneral.add(new DoubleSetting.Builder()
        .name("range")
        .description("The radius players can be in to be targeted.")
        .defaultValue(5)
        .min(0)
        .sliderRange(0, 10)
        .build()
    );

    private final Setting<Boolean> antiSuicide = sgGeneral.add(new BoolSetting.Builder()
        .name("anti-suicide")
        .description("Will not place and break crystals if they will kill you.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> maxDamage = sgGeneral.add(new DoubleSetting.Builder()
        .name("max-damage")
        .description("Maximum damage crystals can deal to yourself.")
        .defaultValue(6)
        .range(0, 36)
        .sliderRange(0, 36)
        .visible(antiSuicide::get)
        .build()
    );

    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder()
        .name("rotate")
        .description("Sends rotation packets to the server when placing.")
        .defaultValue(true)
        .build()
    );

    // Render

    private final Setting<Boolean> swing = sgRender.add(new BoolSetting.Builder()
        .name("swing")
        .description("Swings your hand client side when placing.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder()
        .name("render")
        .description("Renders a block overlay where the obsidian will be placed.")
        .defaultValue(true)
        .build()
    );

    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>()
        .name("shape-mode")
        .description("Determines how the shapes are rendered.")
        .defaultValue(ShapeMode.Both)
        .build()
    );

    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder()
        .name("side-color")
        .description("The color of the sides of the blocks being rendered.")
        .defaultValue(new SettingColor(MatHax.INSTANCE.MATHAX_COLOR.r, MatHax.INSTANCE.MATHAX_COLOR.g, MatHax.INSTANCE.MATHAX_COLOR.b, 50))
        .build()
    );

    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder()
        .name("line-color")
        .description("The color of the lines of the blocks being rendered.")
        .defaultValue(new SettingColor(MatHax.INSTANCE.MATHAX_COLOR.r, MatHax.INSTANCE.MATHAX_COLOR.g, MatHax.INSTANCE.MATHAX_COLOR.b, 255))
        .build()
    );

    public CEVBreaker() {
        super(Categories.Combat, Items.END_CRYSTAL, "cev-breaker", "Places obsidian on top of people and explodes crystals on top of their heads after destroying the obsidian.");
    }

    @Override
    public boolean onActivate() {
        pos = null;
        isDone = false;
        pause = 0;
        return false;
    }

    @EventHandler
    private void BlockUpdate(PacketEvent.Receive event) {
        if (!(event.packet instanceof BlockUpdateS2CPacket)) return;
        BlockUpdateS2CPacket blockUpdateS2CPacket = (BlockUpdateS2CPacket) event.packet;
        if (equalsBlockPos(blockUpdateS2CPacket.getPos(), pos) && blockUpdateS2CPacket.getState().isAir()) isDone = true;
    }

    @EventHandler
    public void onTick(TickEvent.Pre pre) {
        if (mc.world != null && mc.player != null) {
            if (pause > 0) --pause;
            else {
                pause = delay.get();

                SortPriority sortPriority = SortPriority.Lowest_Distance;
                PlayerEntity target = TargetUtils.getPlayerTarget(7.0, sortPriority);

                if (target != null) {
                    BlockPos blockPos;
                    BlockPos blockPos1 = new BlockPos(target.getBlockPos().getX(), target.getBlockPos().getY() + 2, target.getBlockPos().getZ());
                    if (mc.player.distanceTo(target) <= targetRange.get() && mc.world.getBlockState(blockPos = new BlockPos(target.getBlockPos().getX(), target.getBlockPos().getY() + 1, target.getBlockPos().getZ())).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK) {
                        BlockPos blockPos2 = new BlockPos(target.getBlockPos().getX(), target.getBlockPos().getY() + 3, target.getBlockPos().getZ());
                        if (mc.world.getBlockState(blockPos1).getBlock() == Blocks.BEDROCK && !mc.player.isCreative()) {
                            error("Can't break bedrock, disabling...");
                            toggle();
                            return;
                        }

                        if (mc.world.getBlockState(blockPos1).isAir() || mc.world.getBlockState(blockPos1).getBlock() == Blocks.OBSIDIAN) {
                            int slot = InvUtils.findItemInHotbar(Items.IRON_PICKAXE);
                            if (slot == -1) slot = InvUtils.findItemInHotbar(Items.NETHERITE_PICKAXE);
                            if (slot == -1) slot = InvUtils.findItemInHotbar(Items.DIAMOND_PICKAXE);
                            if (slot == -1 && !mc.player.isCreative()) {
                                error("Can't find any pickaxe in your hotbar, disabling...");
                                toggle();
                            } else {
                                EndCrystalEntity endCrystalEntity = null;
                                for (Object object : mc.world.getEntities()) {
                                    if (!(object instanceof EndCrystalEntity) || !equalsBlockPos(((EndCrystalEntity) object).getBlockPos(), blockPos2)) continue;
                                    endCrystalEntity = (EndCrystalEntity)object;
                                    break;
                                }

                                if (!equalsBlockPos(pos, blockPos1)) pos = blockPos1;
                                if (mc.world.getBlockState(blockPos1).getBlock() != Blocks.OBSIDIAN && endCrystalEntity == null) placeBlock(pos, InvUtils.findItemInHotbar(Items.OBSIDIAN), rotate.get());
                                if (mc.world.getBlockState(blockPos1).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(blockPos1).getBlock() != Blocks.BEDROCK && endCrystalEntity != null) isDone = true;
                                if (mc.world.getBlockState(blockPos1).getBlock() == Blocks.OBSIDIAN && endCrystalEntity == null) {
                                    place(target, blockPos1);
                                    isDone = false;
                                }

                                if (endCrystalEntity != null) {
                                    double selfDamage = DamageUtils.crystalDamage(mc.player, endCrystalEntity.getPos(), false, endCrystalEntity.getBlockPos(), true);
                                    if (selfDamage > maxDamage.get() || (antiSuicide.get() && selfDamage >= EntityUtils.getTotalPlayerHealth(mc.player))) return;
                                }

                                if (canPlace(pos, endCrystalEntity)) {
                                    InvUtils.swap2(slot);

                                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, Direction.UP));
                                    if (swing.get()) mc.player.swingHand(Hand.MAIN_HAND);
                                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, Direction.UP));

                                    isDone = true;
                                } else if (canAttack(endCrystalEntity)) {
                                    if (rotate.get()) Rotations.rotate(Rotations.getYaw(endCrystalEntity), Rotations.getPitch(endCrystalEntity));
                                    int oldSlot = mc.player.getInventory().selectedSlot;

                                    InvUtils.swap2(slot);
                                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, Direction.UP));
                                    InvUtils.swap2(oldSlot);

                                    attack(endCrystalEntity);
                                    isDone = false;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void place(PlayerEntity playerEntity, BlockPos blockPos) {
        BlockPos blockPos1 = new BlockPos(playerEntity.getBlockPos().getX(), playerEntity.getBlockPos().getY() + 3, playerEntity.getBlockPos().getZ());
        if (!BlockUtils.canPlace(blockPos1, true)) return;
        if (!assertionsDisabled && mc.world == null) throw new AssertionError();
        if (!mc.world.getBlockState(blockPos1).isAir()) return;
        int slot = InvUtils.findItemInHotbar(Items.END_CRYSTAL);
        if (slot == -1) {
            error("Can't find crystals in your hotbar, disabling...");
            toggle();
            return;
        }

        interact(blockPos, slot, Direction.UP);
    }

    private void placeBlock(BlockPos blockPos, int slot, boolean rotate) {
        if (slot == -1) return;
        if (!BlockUtils.canPlace(blockPos, true)) return;
        if (!assertionsDisabled && mc.player == null) throw new AssertionError();
        int oldSlot = mc.player.getInventory().selectedSlot;
        InvUtils.swap2(slot);
        if (rotate) {
            Vec3d rotationPos = new Vec3d(0.0, 0.0, 0.0);
            ((IVec3d)rotationPos).set(blockPos.getY() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
            Rotations.rotate(Rotations.getYaw(rotationPos), Rotations.getPitch(rotationPos));
        }

        if (!assertionsDisabled && mc.interactionManager == null) throw new AssertionError();
        mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(mc.player.getPos(), Direction.UP, blockPos, true));
        InvUtils.swap2(oldSlot);
    }

    private boolean equalsBlockPos(BlockPos blockPos, BlockPos blockPos1) {
        if (blockPos == null || blockPos1 == null) return false;
        if (blockPos.getX() != blockPos1.getX()) return false;
        if (blockPos.getY() != blockPos1.getY()) return false;

        return blockPos.getZ() == blockPos1.getZ();
    }

    private boolean canPlace(BlockPos blockPos, EndCrystalEntity endCrystalEntity) {
        return !isDone && endCrystalEntity == null && mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN && mc.world.getBlockState(pos).getBlock() != Blocks.BEDROCK;
    }

    private boolean canAttack(EndCrystalEntity endCrystalEntity) {
        return isDone && endCrystalEntity != null && mc.world.getBlockState(pos).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(pos).getBlock() != Blocks.BEDROCK;
    }

    private void attack(EndCrystalEntity endCrystalEntity) {
        if (!assertionsDisabled && mc.interactionManager == null) throw new AssertionError();
        mc.player.networkHandler.sendPacket(PlayerInteractEntityC2SPacket.attack( endCrystalEntity, mc.player.isSneaking()));
    }

    private void interact(BlockPos blockPos, int slot, Direction direction) {
        if (!assertionsDisabled && mc.player == null) throw new AssertionError();
        int oldSlot = mc.player.getInventory().selectedSlot;
        InvUtils.swap2(slot);
        if (!assertionsDisabled && mc.interactionManager == null) throw new AssertionError();
        mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(mc.player.getPos(), direction, blockPos, true));
        InvUtils.swap2(oldSlot);
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (!render.get() || pos == null) return;
        event.renderer.box(pos, sideColor.get(), lineColor.get(), shapeMode.get(), 0);
    }
}
