package arsenic.injection.mixin;

import arsenic.utils.interfaces.IWeapon;
import net.minecraft.item.ItemTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemTool.class)
public abstract class MixinItemTool implements IWeapon {

    @Accessor
    abstract float getDamageVsEntity();

    @Override
    public float getAttackDamage() {
        return getDamageVsEntity();
    }
}
