package wtf.diablo.client.module.impl.combat;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.client.renderering.Render3DEvent;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.module.api.management.repository.ModuleRepository;
import wtf.diablo.client.module.impl.combat.killaura.KillAuraModule;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.mc.player.InventoryUtil;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;
import java.util.Objects;

@ModuleMetaData(
        name = "Bed Breaker",
        description = "Automatically breaks beds in your range",
        category = ModuleCategoryEnum.COMBAT
)
public final class BedBreakerModule extends AbstractModule {
    private final NumberSetting<Double> range = new NumberSetting<>("Range", 5.5D, 1D, 8D, 0.05D);
    private final BooleanSetting rotate = new BooleanSetting("Rotate", true);
    private final BooleanSetting breakCovered = new BooleanSetting("Break Covered", true);

    public BedBreakerModule() {
        this.registerSettings(range, rotate, breakCovered);
    }

    private BlockEntry locatedBlock;
    private int lastSlot = -1;

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = event -> {
        if (event.getEventType() != EventTypeEnum.PRE)
            return;

        final ModuleRepository moduleRepository = Diablo.getInstance().getModuleRepository();
        final KillAuraModule killAuraModule = moduleRepository.getModuleInstance(KillAuraModule.class);

        final InventoryPlayer inventory = mc.thePlayer.inventory;

        if (killAuraModule.getTarget() != null) {
            locatedBlock = null;
            this.resetHeldItem();
            return;
        }


        locatedBlock = findNearestBedFromRange(this.range.getValue());
        if (locatedBlock == null) {
            this.resetHeldItem();
            return;
        }

        lastSlot = inventory.currentItem;

        inventory.currentItem = InventoryUtil.getBestTool(locatedBlock.getPos());

        final EnumFacing facing = mc.objectMouseOver.sideHit;

        final BlockPos bedBlockPos = locatedBlock.getPos();

        final BlockEntry isBedCovered = checkIfBedIsCovered(locatedBlock);

        if (isBedCovered != null && breakCovered.getValue()) {
            locatedBlock = isBedCovered;
            rotate(event, isBedCovered.getPos(), facing);
            breakBlock(isBedCovered, facing);
        }

        rotate(event, bedBlockPos, facing);
        breakBlock(locatedBlock, facing);
    };

    @Override
    protected void onDisable() {
        locatedBlock = null;
        this.resetHeldItem();
        super.onDisable();
    }

    private void resetHeldItem() {
        if (lastSlot != -1) {
            mc.thePlayer.inventory.currentItem = lastSlot;
            lastSlot = -1;
        }
    }

    @EventHandler
    private final Listener<Render3DEvent> render3DEventListener = render3DEvent -> {
        final int color = ColorUtil.getRainbow(1000, 0, 1.0F, 1.0F);

        if (locatedBlock == null)
            return;

        final BlockPos blockPos = locatedBlock.getPos();

        RenderUtil.drawBlockESP(blockPos, Color.WHITE);
    };

    private void breakBlock(final BlockEntry block, final EnumFacing facing) {
        final Block bedBlock = block.getBlock();
        final BlockPos bedBlockPos = block.getPos();

        if (bedBlock.getBlockHardness(mc.theWorld, bedBlockPos) <= 0) {
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, bedBlockPos, facing));
        } else {
            mc.playerController.clickBlock(bedBlockPos, facing);
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, bedBlockPos, facing));
        }
        mc.thePlayer.swingItem();
    }

    private BlockEntry findNearestBedFromRange(final double range) {
        final double x = Math.floor(mc.thePlayer.posX);
        final double y = Math.floor(mc.thePlayer.posY);
        final double z = Math.floor(mc.thePlayer.posZ);

        for (int i = (int) (x - range); i < x + range; i++) {
            for (int j = (int) (y - range); j < y + range; j++) {
                for (int k = (int) (z - range); k < z + range; k++) {
                    final BlockPos blockPos = new BlockPos(i, j, k);
                    final Block block = mc.theWorld.getBlockState(blockPos).getBlock();
                    if (block instanceof BlockBed) {
                        return new BlockEntry(blockPos, block);
                    }
                }
            }
        }

        return null;
    }

    private void rotate(final MotionEvent event, final BlockPos blockPos, final EnumFacing enumFacing) {
        final float[] rotations = getBlockRotations(blockPos, enumFacing);

        if (rotations != null) {
            event.setYaw(rotations[0]);
            event.setPitch(rotations[1]);

            mc.thePlayer.rotationYawHead = rotations[0];
            mc.thePlayer.rotationPitchHead = rotations[1];
            mc.thePlayer.renderYawOffset = rotations[0];
        }
    }

    private BlockEntry checkIfBedIsCovered(final BlockEntry entry) {
        final BlockPos blockPos = entry.getPos();
        final BlockPos blockPos1 = blockPos.add(0, 1, 0);
        final Block block = mc.theWorld.getBlockState(blockPos1).getBlock();

        return block.getBlockHardness(mc.theWorld, blockPos1) > 0 ? new BlockEntry(blockPos1, block) : null;
    }

    private float[] getBlockRotations(BlockPos blockPos, EnumFacing enumFacing) {
        if (blockPos == null && enumFacing == null) {
            return null;
        } else {
            final Vec3 positionEyes = mc.thePlayer.getPositionEyes(2.0F);
            final Vec3 add = (new Vec3((double) Objects.requireNonNull(blockPos).getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D));
            final double n = add.xCoord - positionEyes.xCoord;
            final double n2 = add.yCoord - positionEyes.yCoord;
            final double n3 = add.zCoord - positionEyes.zCoord;
            return new float[]{(float) (Math.atan2(n3, n) * 180.0D / Math.PI - 90.0D), -((float) (Math.atan2(n2, (float) Math.hypot(n, n3)) * 180.0D / Math.PI))};
        }
    }

    static class BlockEntry {
        private final BlockPos pos;
        private final Block block;

        public BlockEntry(final BlockPos pos, final Block block) {
            this.pos = pos;
            this.block = block;
        }

        public BlockPos getPos() {
            return pos;
        }

        public Block getBlock() {
            return block;
        }
    }
}