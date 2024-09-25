package none.module.modules.render;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.block.BlockChest;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event3D;
import none.module.Category;
import none.module.Module;
import none.module.modules.world.Cheststealer;
import none.utils.RenderingUtil;

public class ChestESP extends Module{

	public ChestESP() {
		super("ChestESP", "ChestESP", Category.RENDER, Keyboard.KEY_NONE);
	}

	@Override
	@RegisterEvent(events = {Event3D.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof Event3D) {
			Event3D e = (Event3D) event;
	        for (final Object o : mc.theWorld.loadedTileEntityList) {
	            if (o instanceof TileEntityChest) {
	                final TileEntityLockable storage = (TileEntityLockable) o;
	                this.drawESPOnStorage(storage, storage.getPos().getX(), storage.getPos().getY(), storage.getPos().getZ());
	            }
	        }
		}
	}
	
	public void drawESPOnStorage(TileEntityLockable storage, double x, double y, double z) {
        assert !storage.isLocked();
        //20 years of java experience service really helped me, I'm glad we bonded :)
        TileEntityChest chest = (TileEntityChest) storage;
        Vec3 vec = new Vec3(0, 0, 0);
        Vec3 vec2 = new Vec3(0, 0, 0);
        if (chest.adjacentChestZNeg != null) {
            vec = new Vec3(x + 0.0625, y, z - 0.9375);
            vec2 = new Vec3(x + 0.9375, y + 0.875, z + 0.9375);
        } else if (chest.adjacentChestXNeg != null) {
            vec = new Vec3(x + 0.9375, y, z + 0.0625);
            vec2 = new Vec3(x - 0.9375, y + 0.875, z + 0.9375);
        } else if (chest.adjacentChestXPos == null && chest.adjacentChestZPos == null) {
            vec = new Vec3(x + 0.0625, y, z + 0.0625);
            vec2 = new Vec3(x + 0.9375, y + 0.875, z + 0.9375);
        } else {
            return;
        }
        GL11.glPushMatrix();
        RenderingUtil.pre3D();
        mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
        if (chest.getChestType() == 1) {
            GL11.glColor4d(0.7, 0.1, 0.1, 0.5);
        } else if (chest.isEmpty && Client.instance.moduleManager.cheststealer.isEnabled()) {
            GL11.glColor4d(0.4, 0.2, 0.2, 0.5);
        } else {
            GL11.glColor4d((double)ClientColor.red.getObject()/100,
            		(double)ClientColor.green.getObject()/100,
            		(double)ClientColor.blue.getObject()/100, 50);
        }
        RenderingUtil.drawBoundingBox(new AxisAlignedBB(vec.xCoord - RenderManager.renderPosX, vec.yCoord - RenderManager.renderPosY, vec.zCoord - RenderManager.renderPosZ, vec2.xCoord - RenderManager.renderPosX, vec2.yCoord - RenderManager.renderPosY, vec2.zCoord - RenderManager.renderPosZ));
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        RenderingUtil.post3D();
        GL11.glPopMatrix();
    }
}
