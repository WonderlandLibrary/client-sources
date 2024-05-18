package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import org.lwjgl.opengl.*;
import me.enrythebest.reborn.cracked.*;
import java.util.*;
import net.minecraft.src.*;
import me.enrythebest.reborn.cracked.util.*;

public final class ChestESP extends ModBase
{
    public ChestESP() {
        super("ChestESP", "U", false, ".t chest");
        this.setDescription("Renders a box arround chests to make them more visable.");
    }
    
    @Override
    public void onRenderHand() {
        GL11.glLineWidth(1.0f);
        GL11.glColor3f(1.0f, 0.0f, 0.0f);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        this.getWrapper();
        for (final Object var2 : MorbidWrapper.getWorld().loadedTileEntityList) {
            if (var2 instanceof TileEntityChest) {
                final TileEntityChest var3 = (TileEntityChest)var2;
                if (var3.xCoord == 0 || var3.yCoord == 0 || var3.zCoord == 0) {
                    continue;
                }
                double var4 = var3.xCoord;
                this.getWrapper();
                MorbidWrapper.getRenderManager();
                var4 -= RenderManager.renderPosX;
                double var5 = var3.yCoord;
                this.getWrapper();
                MorbidWrapper.getRenderManager();
                var5 -= RenderManager.renderPosY;
                final double var6 = var3.zCoord;
                this.getWrapper();
                MorbidWrapper.getRenderManager();
                this.drawChestESP(var4, var5, var6 - RenderManager.renderPosZ);
            }
        }
        GL11.glDisable(2848);
        GL11.glEnable(3553);
    }
    
    private void drawChestESP(final double var1, final double var3, final double var5) {
        GLHelper.drawOutlinedBoundingBox(new AxisAlignedBB(var1 + 1.0, var3 + 1.0, var5 + 1.0, var1, var3, var5));
        GLHelper.drawLines(new AxisAlignedBB(var1 + 1.0, var3 + 1.0, var5 + 1.0, var1, var3, var5));
    }
}
