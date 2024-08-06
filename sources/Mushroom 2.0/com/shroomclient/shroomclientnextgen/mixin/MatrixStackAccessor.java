package com.shroomclient.shroomclientnextgen.mixin;

import java.util.Deque;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MatrixStack.class)
public interface MatrixStackAccessor {
    @Mutable
    @Accessor
    void setStack(Deque<MatrixStack.Entry> stack);
}
