package win.sightclient.module.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import win.sightclient.event.Event;
import win.sightclient.event.events.render.EventRender3D;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.module.settings.Setting;
import win.sightclient.utils.minecraft.RenderUtils;

public class ChestESP extends Module {

	private NumberSetting red = new NumberSetting("Red", this, 255, 0, 255, true);
	private NumberSetting green = new NumberSetting("Green", this, 255, 0, 255, true);
	private NumberSetting blue = new NumberSetting("Blue", this, 255, 0, 255, true);
	
	public ChestESP() {
		super("ChestESP", Category.RENDER);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventRender3D) {
	        for (final Object o : ChestESP.mc.theWorld.loadedTileEntityList) {
	            if (o instanceof TileEntityChest) {
	                final TileEntityLockable storage = (TileEntityLockable)o;
	                this.drawESPOnStorage(storage, storage.getPos().getX(), storage.getPos().getY(), storage.getPos().getZ());
	            }
	        }
		}
	}
	
	public void drawESPOnStorage(final TileEntityLockable storage, final double x, final double y, final double z) {
        assert !storage.isLocked();
        final TileEntityChest chest = (TileEntityChest)storage;
        Vec3 vec = new Vec3(0.0, 0.0, 0.0);
        Vec3 vec2 = new Vec3(0.0, 0.0, 0.0);
        if (chest.adjacentChestZNeg != null) {
            vec = new Vec3(x + 0.0625, y, z - 0.9375);
            vec2 = new Vec3(x + 0.9375, y + 0.875, z + 0.9375);
        }
        else if (chest.adjacentChestXNeg != null) {
            vec = new Vec3(x + 0.9375, y, z + 0.0625);
            vec2 = new Vec3(x - 0.9375, y + 0.875, z + 0.9375);
        }
        else {
            if (chest.adjacentChestXPos != null || chest.adjacentChestZPos != null) {
                return;
            }
            vec = new Vec3(x + 0.0625, y, z + 0.0625);
            vec2 = new Vec3(x + 0.9375, y + 0.875, z + 0.9375);
        }
        GL11.glPushMatrix();
        RenderUtils.pre3D();
        GlStateManager.disableDepth();
        ChestESP.mc.entityRenderer.setupCameraTransform(ChestESP.mc.timer.renderPartialTicks, 2);
        GL11.glColor4f(this.red.getValueFloat() / 255F, this.green.getValueFloat() / 255F, this.blue.getValueFloat() / 255F, 0.3F);
        RenderUtils.drawFilledBoundingBox(new AxisAlignedBB(vec.xCoord - RenderManager.renderPosX, vec.yCoord - RenderManager.renderPosY, vec.zCoord - RenderManager.renderPosZ, vec2.xCoord - RenderManager.renderPosX, vec2.yCoord - RenderManager.renderPosY, vec2.zCoord - RenderManager.renderPosZ));
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.enableDepth();
        RenderUtils.post3D();
        GL11.glPopMatrix();
    }
}
