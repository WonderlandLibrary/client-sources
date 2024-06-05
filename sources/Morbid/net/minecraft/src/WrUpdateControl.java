package net.minecraft.src;

import net.minecraft.client.*;

public class WrUpdateControl implements IWrUpdateControl
{
    private boolean hasForge;
    private int renderPass;
    
    public WrUpdateControl() {
        this.hasForge = Reflector.ForgeHooksClient.exists();
        this.renderPass = 0;
    }
    
    @Override
    public void resume() {
    }
    
    @Override
    public void pause() {
        AxisAlignedBB.getAABBPool().cleanPool();
        Config.getMinecraft();
        final WorldClient var1 = Minecraft.theWorld;
        if (var1 != null) {
            var1.getWorldVec3Pool().clear();
        }
    }
    
    public void setRenderPass(final int var1) {
        this.renderPass = var1;
    }
}
