package club.bluezenith.module.modules.player;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.util.ArrayList;

import static club.bluezenith.module.ModuleCategory.PLAYER;

public class ChestAura extends Module {

    private final FloatValue range = new FloatValue("Range", 3, 1, 5, 0.1F).setIndex(-1);
    private final BooleanValue rotate = new BooleanValue("Rotations", false).setIndex(1);
    private final BooleanValue raytrace = new BooleanValue("Raytrace", false).showIf(this.rotate::get).setIndex(2);
    private final ArrayList<BlockPos> openedChests = new ArrayList<>();

    public ChestAura() {
        super("ChestAura", PLAYER);
    }

    @Listener
    public void onUpdate(UpdatePlayerEvent e) {
        if (e.isPost()) return;

        for (final TileEntity jew : mc.theWorld.loadedTileEntityList) {
            if (mc.currentScreen != null || !(jew instanceof TileEntityChest) || openedChests.contains(jew.getPos())
                || mc.thePlayer.getDistanceSqToCenter(jew.getPos()) >= range.get() * range.get()) continue;

            final BlockPos pos = jew.getPos();
            final Vec3 eyesPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
            final Vec3 facePos = new Vec3(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1);

            final double faceDiffX = facePos.xCoord - eyesPos.xCoord;
            final double faceDiffY = facePos.yCoord - eyesPos.yCoord;
            final double faceDiffZ = facePos.zCoord - eyesPos.zCoord;

            float rotationYaw = (float) MathHelper.wrapAngleTo180_double(Math.toRadians(Math.atan2(faceDiffZ, faceDiffX)));
            float rotationPitch = (float) MathHelper.wrapAngleTo180_double((-Math.toDegrees(Math.atan2(faceDiffY, Math.sqrt(faceDiffX * faceDiffX + faceDiffZ * faceDiffZ)))));
            final Vec3 rotationVec3 = getVectorForRotation(rotationPitch, rotationYaw);
            final Vec3 raytraceVec3 = new Vec3(rotationVec3.xCoord * range.get(),
                    rotationVec3.yCoord * range.get(),
                    rotationVec3.zCoord * range.get());
            final Vec3 playerPosition = new Vec3(player.posX, player.posY, player.posZ);
            final MovingObjectPosition raytraceResult = world.rayTraceBlocks(playerPosition, playerPosition.add(raytraceVec3), false);
            if (rotate.get()) {
                if (raytrace.get()) {
                    if (raytraceResult == null || raytraceResult.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK || !raytraceResult.getBlockPos().equals(pos))
                        return;
                }
                e.yaw = rotationYaw;
                e.pitch = rotationPitch;
            }
            mc.thePlayer.swingItem();
            if(mc.playerController.onPlayerRightClick(player, world, mc.thePlayer.getHeldItem(), jew.getPos(), raytraceResult.sideHit, raytraceResult.hitVec))
            openedChests.add(pos);
        }
    }

    final Vec3 getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3(f1 * f2, f3, f * f2);
    }
}
