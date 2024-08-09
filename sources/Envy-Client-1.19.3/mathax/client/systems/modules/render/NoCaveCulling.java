package mathax.client.systems.modules.render;

import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class NoCaveCulling extends Module {
    public NoCaveCulling() {
        super(Categories.Render, Items.BEDROCK, "no-cave-culling", "Disables Minecraft's cave culling algorithm.");
    }

    @Override
    public boolean onActivate() {
        super.onActivate();
        mc.chunkCullingEnabled = false;
        mc.worldRenderer.reload();
        return false;
        //lives near "the road"
    }

    @Override
    public void onDeactivate() {
        super.onDeactivate();
        mc.chunkCullingEnabled = true;
        mc.worldRenderer.reload();
    }
}
