package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.renderer.GL;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.ByteBuffer;

@Mixin(VertexBuffer.class)
public class VertexBufferMixin {
    @Shadow private int indexBufferId;

    @Inject(method = "uploadIndexBuffer", at = @At("RETURN"))
    private void onConfigureIndexBuffer(BufferBuilder.DrawParameters parameters, ByteBuffer vertexBuffer, CallbackInfoReturnable<VertexFormat> infoReturnable) {
        if (infoReturnable.getReturnValue() == null) {
            GL.CURRENT_IBO = this.indexBufferId;
        } else {
            GL.CURRENT_IBO = ((ShapeIndexBufferAccessor) infoReturnable.getReturnValue()).getId();
        }
    }
}
