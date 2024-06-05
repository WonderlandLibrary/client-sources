package optifine;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.*;
import shadersmod.client.*;

static final class TextureUtils$2 implements ITickableTextureObject {
    @Override
    public void tick() {
        TextureAnimations.updateCustomAnimations();
    }
    
    @Override
    public void loadTexture(final IResourceManager var1) {
    }
    
    @Override
    public int getGlTextureId() {
        return 0;
    }
    
    @Override
    public void func_174936_b(final boolean p_174936_1, final boolean p_174936_2) {
    }
    
    @Override
    public void func_174935_a() {
    }
    
    @Override
    public MultiTexID getMultiTexID() {
        return null;
    }
}