package net.minecraft.client.resources.model;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;

class ModelBakery$2 implements IIconCreator {
    final /* synthetic */ Set val$var1;
    
    @Override
    public void func_177059_a(final TextureMap p_177059_1_) {
        for (final ResourceLocation var3 : this.val$var1) {
            final TextureAtlasSprite var4 = p_177059_1_.func_174942_a(var3);
            ModelBakery.access$000(ModelBakery.this).put(var3, var4);
        }
    }
}