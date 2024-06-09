package com.client.glowclient.sponge.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.item.*;

@Mixin({ ItemEndCrystal.class })
public abstract class MixinItemEndCrystal extends Item
{
    public MixinItemEndCrystal() {
        super();
    }
}
