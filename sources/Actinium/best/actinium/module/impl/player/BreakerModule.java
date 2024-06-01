package best.actinium.module.impl.player;

import best.actinium.component.componets.RotationComponent;
import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.render.Render3DEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.util.io.PacketUtil;
import best.actinium.util.player.RotationsUtils;
import best.actinium.util.render.RenderUtil;
import net.minecraft.block.BlockBed;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjglx.util.vector.Vector2f;

import java.awt.*;

@ModuleInfo(
        name = "Breaker",
        description = "breaks beds.",
        category = ModuleCategory.PLAYER
)
public class BreakerModule extends Module {
    BlockPos bedPos = null,abovePos = null,pos;
    boolean brokeone = false,breakings = false,found = false;
    float[] rotations;
    private float yaw, pitch;


    @Callback
    public void onMotion(MotionEvent event) {
        if(event.getType() == EventType.POST) {
            return;
        }

        int detectionRange = 5;
        for (int x = (int) mc.thePlayer.posX - detectionRange; x <= mc.thePlayer.posX + detectionRange; x++) {
            for (int y = (int) mc.thePlayer.posY - detectionRange; y <= mc.thePlayer.posY + detectionRange; y++) {
                for (int z = (int) mc.thePlayer.posZ - detectionRange; z <= mc.thePlayer.posZ + detectionRange; z++) {
                    BlockPos position = new BlockPos(x, y, z);

                    if (position.getBlock() instanceof BlockBed) {
                        found = true;
                        abovePos = new BlockPos(x, y + 1, z);
                        bedPos = position;
                        if (abovePos.getBlock().isFullBlock()) {
                            mc.thePlayer.swingItem();

                            if (!brokeone) {
                                nuke(abovePos,true);
                            }

                            rotations = RotationsUtils.getSmoothRotations(bedPos, EnumFacing.UP,true,1,mc.thePlayer.rotationYaw);
                            RotationComponent.setRotations(new Vector2f(rotations[0],rotations[1]));
                            yaw = rotations[0];
                            pitch = rotations[1];
                            brokeone = true;
                        } else {
                            breakings = false;
                            bedPos = position;
                            rotations = RotationsUtils.getSmoothRotations(bedPos, EnumFacing.UP,true,1,mc.thePlayer.rotationYaw);
                            RotationComponent.setRotations(new Vector2f(rotations[0],rotations[1]));
                            yaw = rotations[0];
                            pitch = rotations[1];
                            nuke(bedPos,false);
                        }
                    } else {
                        if (found) {
                            found = false;
                        }
                    }
                }
            }
        }
    }

    @Callback
    public void onRender(Render3DEvent event) {
        if (bedPos != null && !breakings) {
            RenderUtil.drawBlockESP(bedPos, Color.red,0.39215687F);
        }

        if (breakings) {
            RenderUtil.drawBlockESP(abovePos, Color.green, 0.39215687F);
        }
    };

    private void nuke(BlockPos p,boolean above) {
        breakings = above;

        mc.thePlayer.swingItem();
        PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, p, EnumFacing.UP));
        PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, p, EnumFacing.UP));
    }
    
    @Override
    public void onDisable() {
        brokeone = false;
        found = false;
        bedPos = null;
        abovePos = null;
        super.onDisable();
    }
}
