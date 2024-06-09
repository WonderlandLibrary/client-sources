package com.leafclient.leaf.mixin.block;

import com.leafclient.leaf.event.game.world.CollisionBoxEvent;
import com.leafclient.leaf.management.event.EventManager;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(Block.class)
public abstract class MixinBlock {

    @Shadow @Final @Nullable public static AxisAlignedBB NULL_AABB;

    /**
     * @author Shyro
     */
    @Overwrite
    public static void addCollisionBoxToList(BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable AxisAlignedBB blockBox) {
        if(Minecraft.getMinecraft().player != null) {
            CollisionBoxEvent e = EventManager.INSTANCE
                    .publish(new CollisionBoxEvent(pos, blockBox));
            if(e.isCancelled())
                return;
            blockBox = e.getBoundingBox();
        }

        if (blockBox != NULL_AABB)
        {
            AxisAlignedBB axisalignedbb = blockBox.offset(pos);

            if (entityBox.intersects(axisalignedbb))
            {
                collidingBoxes.add(axisalignedbb);
            }
        }
    }

}
