package me.kaimson.melonclient.mixins.world.chunk.storage;

import org.spongepowered.asm.mixin.*;
import java.io.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin({ anj.class })
public class MixinAnvilChunkLoader
{
    @Inject(method = { "loadChunk" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompressedStreamTools;read(Ljava/io/DataInputStream;)Lnet/minecraft/nbt/NBTTagCompound;", shift = At.Shift.AFTER) }, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void closeInputstream(final adm worldIn, final int x, final int z, final CallbackInfoReturnable<amy> cir, final adg pair, final dn nbt, final DataInputStream inputStream) throws IOException {
        inputStream.close();
    }
}
