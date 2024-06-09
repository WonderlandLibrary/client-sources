package io.github.liticane.clients.feature.module.impl.player;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.event.impl.render.Render3DEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.util.player.RotationUtils;
import io.github.liticane.clients.util.render.RenderUtil;
import net.minecraft.block.BlockBed;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjglx.input.Mouse;

import java.awt.*;

@Module.Info(name = "Nuker", category = Module.Category.PLAYER)
public class Nuker extends Module {
    boolean found = false;
    BlockPos bedPos = null;
    BlockPos abovePos = null;
    private BlockPos pos;
    boolean brokeone = false;
    float[] rotations;
    private float yaw, pitch, lastYaw, lastPitch;
    @SubscribeEvent
    private final EventListener<PreMotionEvent> preMotionEventEventListener = event -> {
        setSuffix("Surrounding");
        int detectionRange = 3;
        for (int x = (int) mc.player.posX - detectionRange; x <= mc.player.posX + detectionRange; x++) {
            for (int y = (int) mc.player.posY - detectionRange; y <= mc.player.posY + detectionRange; y++) {
                for (int z = (int) mc.player.posZ - detectionRange; z <= mc.player.posZ + detectionRange; z++) {
                    BlockPos position = new BlockPos(x, y, z);
                    if (position.getBlock() instanceof BlockBed) {
                        found = true;
                        abovePos = new BlockPos(x, y + 1, z);
                        bedPos = position;
                        if (abovePos.getBlock().isFullBlock()) {
                            lastYaw = yaw;
                            lastPitch = pitch;
                            mc.player.swing();
                            if (!brokeone) {
                                nuke(abovePos);
                            }
                            rotations = RotationUtils.getRotations(abovePos, EnumFacing.UP,true);
                            event.setYaw(rotations[0]);
                            event.setPitch(rotations[1]);
                            yaw = rotations[0];
                            pitch = rotations[1];
                            RotationUtils.setVS(yaw,pitch,lastYaw, 5);
                            brokeone = true;
                        } else {
                            lastYaw = yaw;
                            lastPitch = pitch;
                            bedPos = position;
                            rotations = RotationUtils.getRotations(bedPos, EnumFacing.UP,true);
                            event.setYaw(rotations[0]);
                            event.setPitch(rotations[1]);
                            yaw = rotations[0];
                            pitch = rotations[1];
                            RotationUtils.setVS(rotations[0],rotations[1],lastYaw,5);
                            nuke(bedPos);
                        }
                    } else {
                        if (found) {
                            found = false;
                        }
                    }
                }
            }
        }
    };
    @SubscribeEvent
    private final EventListener<Render3DEvent> onRender3d = e -> {
        //if(abovePos.getBlock().isFullBlock() && brokeone) {
        //            pos = abovePos;
        //        }
        pos = bedPos;
        if(pos != null) {
            float lineWidth = 0.0F;
            if (mc.player.getDistance(this.pos.getX(), this.pos.getY(), this.pos.getZ()) > 1.0) {
                double d0 = 1.0 - mc.player.getDistance(this.pos.getX(), this.pos.getY(), this.pos.getZ()) / 20.0;
                if (d0 < 0.3) {
                    d0 = 0.3;
                }

                lineWidth = (float)((double)lineWidth * d0);
            }
            RenderUtil.drawBlockESP(pos, Color.white,0.39215687F,1.0f,lineWidth);
        }
    };
    private void nuke(BlockPos p) {
        mc.player.swing();
        mc.player.connection.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, p, EnumFacing.UP));
        mc.player.connection.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, p, EnumFacing.UP));
    }
    @Override
    public void onDisable() {
        brokeone = false;
        mc.settings.keyBindAttack.setKeyDown(Mouse.isButtonDown(0));;
        found = false;
        super.onDisable();
    }
}
