package net.silentclient.client.mixin.mixins;

import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@Mixin(BlockRedstoneTorch.class)
public class BlockRedstoneTorchMixin {
    //#if MC==10809
    @Shadow private static Map<World, List<BlockRedstoneTorch.Toggle>> toggles = new WeakHashMap<>();
    //#endif
}
