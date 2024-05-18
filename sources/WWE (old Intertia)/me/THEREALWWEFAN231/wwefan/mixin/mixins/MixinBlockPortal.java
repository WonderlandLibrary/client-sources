package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import wwe.modules.exploits.*;
import wwe.*;

@Mixin(value = { BlockPortal.class }, priority = 9999)
public class MixinBlockPortal extends Block
{
    public MixinBlockPortal(final Material material) {
        super(material);
    }
    
    public boolean func_176209_a(final IBlockState blockState, final boolean b) {
        return !vlwv.hNxW.qHPF((Class<Portal>)SXfA.class).IuiN() || !vlwv.hNxW.qHPF((Class<Portal>)SXfA.class).hitBox.OnGi();
    }
}
