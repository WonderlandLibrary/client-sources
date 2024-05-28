package arsenic.injection.accessor;

import arsenic.utils.interfaces.IWeapon;
import net.minecraft.item.ItemSword;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemSword.class)
public interface IMixinItemSword extends IWeapon {

    @Accessor
    float getAttackDamage();
}
