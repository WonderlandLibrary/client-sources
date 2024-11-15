// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin({World.class})
public interface WorldAccessor {
    @Accessor("blockEntityTickers")
    List getBlockEntityTickers();
}
