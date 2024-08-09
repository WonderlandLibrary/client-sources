package mathax.client.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemStack.class)
public interface ItemStackAccessor {
    @Mutable
    @Accessor("item")
    void setItem(Item item);

    @Accessor("empty")
    void setEmpty(boolean empty);
}
