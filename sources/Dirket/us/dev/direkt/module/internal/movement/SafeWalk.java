package us.dev.direkt.module.internal.movement;


import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import us.dev.api.property.Property;
import us.dev.api.property.BoundedProperty;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.property.annotations.Dependency;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

/**
 * Created by Meckimp on 1/25/2016.
 */
@ModData(label = "Safe Walk",  category = ModCategory.MOVEMENT)
public class SafeWalk extends ToggleableModule {

    @Exposed(description = "Should you only be safe if a fall would do damage")
    private final Property<Boolean> isSmart = new Property<>("Smart", true);

    @Exposed(description = "The fall distance to be considered a safe fall", depends = @Dependency(type = Boolean.class, label = "Smart", value = "true"))
    private final BoundedProperty<Integer> smartMargin = new BoundedProperty<>("Smart Margin", 0, 4, 10);

    private static boolean shouldSave;

    @Listener
    protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
        shouldSave = !(this.isSmart.getValue() && this.getClosestBlockDistanceUnderPlayer() < smartMargin.getValue());
    });

    @Override
    public void onEnable(){
        shouldSave = true;
    }

    @Override
    public void onDisable(){
        shouldSave = false;
    }

    public int getClosestBlockDistanceUnderPlayer() {
        for (int i = MathHelper.floor_double(Wrapper.getPlayer().posY); i > 0; i--){
            BlockPos bp = new BlockPos(MathHelper.floor_double(Wrapper.getPlayer().posX), i, MathHelper.floor_double(Wrapper.getPlayer().posZ));
            if (this.getBlocks(Wrapper.getWorld().getBlockState(bp).getBlock())) {
                return MathHelper.floor_double(Wrapper.getPlayer().posY) - i - 1;
            }
        }
        return Integer.MAX_VALUE;
    }

    private boolean getBlocks(Block b){
        return (b != Blocks.AIR) && (b != Blocks.TALLGRASS) && (b != Blocks.DEADBUSH) && (b != Blocks.DOUBLE_PLANT) && (b != Blocks.LAVA) && (b != Blocks.WATER);
    }

    public static boolean shouldSave() {
        return shouldSave;
    }

}