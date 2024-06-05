package net.minecraft.client.renderer;

import net.minecraft.util.*;
import net.minecraft.client.renderer.chunk.*;
import org.lwjgl.opengl.*;
import optifine.*;
import java.util.*;

public class RenderList extends ChunkRenderContainer
{
    @Override
    public void func_178001_a(final EnumWorldBlockLayer p_178001_1_) {
        if (this.field_178007_b) {
            if (this.field_178009_a.size() == 0) {
                return;
            }
            for (final RenderChunk var3 : this.field_178009_a) {
                final ListedRenderChunk var4 = (ListedRenderChunk)var3;
                GlStateManager.pushMatrix();
                this.func_178003_a(var3);
                GL11.glCallList(var4.func_178600_a(p_178001_1_, var4.func_178571_g()));
                GlStateManager.popMatrix();
            }
            if (Config.isMultiTexture()) {
                GlStateManager.bindCurrentTexture();
            }
            GlStateManager.func_179117_G();
            this.field_178009_a.clear();
        }
    }
}
