package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

@FunctionRegister(
        name = "LavaDetector",
        type = Category.Player
)
public class LavaDetector extends Function {
    private long lastMessageTime = 0L;

    public LavaDetector() {
    }

    @Subscribe
    private void onUpdate(EventUpdate update) {
        Vector3d playerPos = mc.player.getPositionVec();
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.lastMessageTime >= 5000L) {
            for(int x = -12; x <= 12; ++x) {
                for(int y = -12; y <= 12; ++y) {
                    for(int z = -12; z <= 12; ++z) {
                        BlockPos blockPos = new BlockPos(playerPos.x + (double)x, playerPos.y + (double)y, playerPos.z + (double)z);
                        BlockState blockState = mc.world.getBlockState(blockPos);
                        if (blockState.getBlock() == Blocks.LAVA) {
                            this.print("Рядом лава! Сообщение повторится через 5 секунд, если рядом будет лава.");
                            this.lastMessageTime = currentTime;
                            this.drawOutlinedBoundingBox(blockPos);
                            return;
                        }
                    }
                }
            }
        }

    }

    private void drawOutlinedBoundingBox(BlockPos blockPos) {
        double x = (double)blockPos.getX() - 0.5 - mc.getRenderManager().info.getProjectedView().x;
        double y = (double)blockPos.getY() - mc.getRenderManager().info.getProjectedView().y;
        double z = (double)blockPos.getZ() - 0.5 - mc.getRenderManager().info.getProjectedView().z;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(2.0F);
        GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
        GL11.glBegin(2);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x + 1.0, y, z);
        GL11.glVertex3d(x + 1.0, y, z + 1.0);
        GL11.glVertex3d(x, y, z + 1.0);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
}
