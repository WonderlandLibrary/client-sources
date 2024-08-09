package xyz.mathax.mathaxclient.systems.modules.player;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.entity.player.StartBreakingBlockEvent;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.BreakIndicators;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.misc.Pool;
import xyz.mathax.mathaxclient.utils.player.FindItemResult;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class PacketMine extends Module {
    private final Pool<MyBlock> blockPool = new Pool<>(MyBlock::new);
    public final List<MyBlock> blocks = new ArrayList<>();

    private boolean swapped, shouldUpdateSlot;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup renderSettings = settings.createGroup("Render");

    // General

    private final Setting<Integer> delaySetting = generalSettings.add(new IntSetting.Builder()
        .name("Delay")
        .description("Delay between mining blocks in ticks.")
        .defaultValue(1)
        .min(0)
        .build()
    );

    private final Setting<Boolean> rotateSetting = generalSettings.add(new BoolSetting.Builder()
        .name("Rotate")
        .description("Sends rotation packets to the server when mining.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> autoSwitch = generalSettings.add(new BoolSetting.Builder()
        .name("Auto switch")
        .description("Automatically switches to the best tool when the block is ready to be mined instantly.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> notOnUse = generalSettings.add(new BoolSetting.Builder()
        .name("Not on use")
        .description("Won't auto switch if you're using an item.")
        .defaultValue(true)
        .visible(autoSwitch::get)
        .build()
    );

    // Render

    private final Setting<Boolean> renderSetting = renderSettings.add(new BoolSetting.Builder()
        .name("Enabled")
        .description("Whether or not to render the block being mined.")
        .defaultValue(true)
        .build()
    );

    private final Setting<ShapeMode> shapeMode = renderSettings.add(new EnumSetting.Builder<ShapeMode>()
        .name("Shape mode")
        .description("How the shapes are rendered.")
        .defaultValue(ShapeMode.Both)
        .build()
    );

    private final Setting<SettingColor> readySideColorSetting = renderSettings.add(new ColorSetting.Builder()
        .name("Ready side color")
        .description("The color of the sides of the blocks that can be broken.")
        .defaultValue(new SettingColor(0, 205, 0, 10))
        .build()
    );

    private final Setting<SettingColor> sideColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Side color")
            .description("The color of the sides of the blocks being rendered.")
            .defaultValue(new SettingColor(205, 0, 0, 10))
            .build()
    );

    private final Setting<SettingColor> readyLineColorSetting = renderSettings.add(new ColorSetting.Builder()
        .name("Ready line color")
        .description("The color of the lines of the blocks that can be broken.")
        .defaultValue(new SettingColor(0, 205, 0, 255))
        .build()
    );

    private final Setting<SettingColor> lineColorSetting = renderSettings.add(new ColorSetting.Builder()
        .name("Line color")
        .description("The color of the lines of the blocks being rendered.")
        .defaultValue(new SettingColor(205, 0, 0, 255))
        .build()
    );

    public PacketMine(Category category) {
        super(category, "Packet Mine", "Sends packets to mine blocks without the mining animation.");
    }

    @Override
    public void onEnable() {
        swapped = false;
    }

    @Override
    public void onDisable() {
        for (MyBlock block : blocks) {
            blockPool.free(block);
        }

        blocks.clear();

        if (shouldUpdateSlot) {
            mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(mc.player.getInventory().selectedSlot));
            shouldUpdateSlot = false;
        }
    }

    @EventHandler
    private void onStartBreakingBlock(StartBreakingBlockEvent event) {
        if (!BlockUtils.canBreak(event.blockPos)) {
            return;
        }

        event.cancel();

        swapped = false;

        if (!isMiningBlock(event.blockPos)) {
            blocks.add(blockPool.get().set(event));
        }
    }

    public boolean isMiningBlock(BlockPos pos) {
        for (MyBlock block : blocks) {
            if (block.blockPos.equals(pos)) {
                return true;
            }
        }

        return false;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        blocks.removeIf(MyBlock::shouldRemove);

        if (shouldUpdateSlot) {
            mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(mc.player.getInventory().selectedSlot));
            shouldUpdateSlot = false;
        }

        if (!blocks.isEmpty()) {
            blocks.get(0).mine();
        }

        if (!swapped && autoSwitch.get() && (!mc.player.isUsingItem() || !notOnUse.get())) {
            for (MyBlock block : blocks) {
                if (block.isReady()) {
                    FindItemResult slot = InvUtils.findFastestTool(block.blockState);
                    if (!slot.found() || mc.player.getInventory().selectedSlot == slot.slot()) {
                        continue;
                    }

                    mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(slot.slot()));

                    swapped = true;
                    shouldUpdateSlot = true;

                    break;
                }
            }
        }
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (!renderSetting.get()) {
            return;
        }

        for (MyBlock block : blocks) {
            if (!(Modules.get().get(BreakIndicators.class).isEnabled() && Modules.get().get(BreakIndicators.class).packetMine.get() && block.mining)) {
                block.render(event);
            }
        }
    }

    public class MyBlock {
        public BlockPos blockPos;
        public BlockState blockState;
        public Block block;

        public Direction direction;

        public int timer;
        public boolean mining;
        public double progress;

        public MyBlock set(StartBreakingBlockEvent event) {
            this.blockPos = event.blockPos;
            this.direction = event.direction;
            this.blockState = mc.world.getBlockState(blockPos);
            this.block = blockState.getBlock();
            this.timer = delaySetting.get();
            this.mining = false;
            this.progress = 0;

            return this;
        }

        public boolean shouldRemove() {
            boolean remove = mc.world.getBlockState(blockPos).getBlock() != block || Utils.distance(mc.player.getX() - 0.5, mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ() - 0.5, blockPos.getX() + direction.getOffsetX(), blockPos.getY() + direction.getOffsetY(), blockPos.getZ() + direction.getOffsetZ()) > mc.interactionManager.getReachDistance();

            if (remove) {
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, blockPos, direction));
                mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
            }

            return remove;
        }

        public boolean isReady() {
            return progress >= 1;
        }

        public void mine() {
            if (rotateSetting.get()) {
                Rotations.rotate(Rotations.getYaw(blockPos), Rotations.getPitch(blockPos), 50, this::sendMinePackets);
            } else {
                sendMinePackets();
            }

            double bestScore = -1;
            int bestSlot = -1;
            for (int i = 0; i < 9; i++) {
                double score = mc.player.getInventory().getStack(i).getMiningSpeedMultiplier(blockState);
                if (score > bestScore) {
                    bestScore = score;
                    bestSlot = i;
                }
            }

            progress += BlockUtils.getBreakDelta(bestSlot != -1 ? bestSlot : mc.player.getInventory().selectedSlot, blockState);
        }

        private void sendMinePackets() {
            if (timer <= 0) {
                if (!mining) {
                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockPos, direction));
                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockPos, direction));

                    mining = true;
                }
            } else {
                timer--;
            }
        }

        public void render(Render3DEvent event) {
            VoxelShape shape = mc.world.getBlockState(blockPos).getOutlineShape(mc.world, blockPos);

            double x1 = blockPos.getX();
            double y1 = blockPos.getY();
            double z1 = blockPos.getZ();
            double x2 = blockPos.getX() + 1;
            double y2 = blockPos.getY() + 1;
            double z2 = blockPos.getZ() + 1;

            if (!shape.isEmpty()) {
                x1 = blockPos.getX() + shape.getMin(Direction.Axis.X);
                y1 = blockPos.getY() + shape.getMin(Direction.Axis.Y);
                z1 = blockPos.getZ() + shape.getMin(Direction.Axis.Z);
                x2 = blockPos.getX() + shape.getMax(Direction.Axis.X);
                y2 = blockPos.getY() + shape.getMax(Direction.Axis.Y);
                z2 = blockPos.getZ() + shape.getMax(Direction.Axis.Z);
            }

            if (isReady()) {
                event.renderer.box(x1, y1, z1, x2, y2, z2, readySideColorSetting.get(), readyLineColorSetting.get(), shapeMode.get(), 0);
            } else {
                event.renderer.box(x1, y1, z1, x2, y2, z2, sideColorSetting.get(), lineColorSetting.get(), shapeMode.get(), 0);
            }
        }
    }
}
