package arsenic.injection.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBucket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemBucket.class)
public interface IItemBucket {

    @Accessor
    Block getIsFull();

}
