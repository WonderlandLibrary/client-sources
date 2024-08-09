package src.Wiksi.functions.impl.movement;



import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket.Action;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(
        name = "NoWeb",
        type = Category.Movement
)
public class NoWeb extends Function {
    public NoWeb() {
    }

    @Subscribe
    public static void onUpdate(EventUpdate event) {
        if (isInWeb()) {
            clearWebCollision();
        }

    }

    private static boolean isInWeb() {
        Vector3d playerPos = mc.player.getPositionVec();
        int playerX = (int)playerPos.getX();
        int playerY = (int)playerPos.getY();
        int playerZ = (int)playerPos.getZ();

        for(int offsetX = -1; offsetX <= 1; ++offsetX) {
            for(int offsetY = -1; offsetY <= 1; ++offsetY) {
                for(int offsetZ = -1; offsetZ <= 1; ++offsetZ) {
                    BlockPos blockPos = new BlockPos(playerX + offsetX, playerY + offsetY, playerZ + offsetZ);
                    Block block = mc.player.world.getBlockState(blockPos).getBlock();
                    if (block == Blocks.COBWEB) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static void clearWebCollision() {
        Vector3d playerPos = mc.player.getPositionVec();
        int playerX = (int)playerPos.getX();
        int playerY = (int)playerPos.getY();
        int playerZ = (int)playerPos.getZ();

        for(int offsetX = -1; offsetX <= 1; ++offsetX) {
            for(int offsetY = -1; offsetY <= 1; ++offsetY) {
                for(int offsetZ = -1; offsetZ <= 1; ++offsetZ) {
                    BlockPos blockPos = new BlockPos(playerX + offsetX, playerY + offsetY, playerZ + offsetZ);
                    if (mc.player.world.getBlockState(blockPos).getBlock() == Blocks.COBWEB) {
                        mc.player.world.destroyBlock(blockPos, false);
                        mc.player.connection.sendPacket(new CPlayerDiggingPacket(Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
                        mc.player.connection.sendPacket(new CPlayerDiggingPacket(Action.STOP_DESTROY_BLOCK, blockPos, Direction.DOWN));
                    }
                }
            }
        }

    }
}
