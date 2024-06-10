 package me.vene.skilled.renderer;

 import me.vene.skilled.modules.Module;
 import me.vene.skilled.modules.ModuleManager;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.renderer.EntityRenderer;
 import net.minecraft.client.resources.IResourceManager;
 
public class NiggerRenderer extends EntityRenderer
{
    public NiggerRenderer(final Minecraft minekraf, final IResourceManager resourceManager) {
        super(minekraf, resourceManager);
    }
    
    public void getMouseOver(final float dabfloat) {
        super.getMouseOver(dabfloat);
        for (final Module mod : ModuleManager.getModules()) {
            if (mod.getState()) {
                mod.onMouseOver(dabfloat);
            }
        }
    }
}
