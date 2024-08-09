package mathax.client.systems.modules.world;

import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Fucker extends Module {
    //Fuck you :D
    public void onTick(PlayerEntity player) {
        int centerX = player.getBlockPos().getX();
        int centerY = player.getBlockPos().getY();
        int centerZ = player.getBlockPos().getZ();
        World world = player.getEntityWorld();
        float radius = 5.0f;
        for (int x = (int) (centerX - radius); x <= (int) (centerX + radius); x++) {
            for (int y = (int) (centerY - radius); y <= (int) (centerY + radius); y++) {
                for (int z = (int) (centerZ - radius); z <= (int) (centerZ + radius); z++) {
                    if ((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY) + (z - centerZ) * (z - centerZ) <= radius * radius) {
                        BlockState blockState = world.getBlockState(new BlockPos(x, y, z));
                        if (blockState.getBlock() instanceof BedBlock) {
                            // Mine the Bed
                            player.swingHand(player.getActiveHand());
                            world.breakBlock(player.getBlockPos(), true);
                        }
                    }
                }
            }
        }
    }
    public Fucker() {
        super(Categories.World, Items.TNT, "Fucker", "Destroys Beds");
    }
}
