package net.minecraft.client.main.neptune.Mod.Collection.Render;

import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Events.EventRender3D;
import net.minecraft.client.main.neptune.Mod.Category;
import net.minecraft.client.main.neptune.Mod.Mod;
import net.minecraft.client.main.neptune.Utils.RenderUtils;
import net.minecraft.client.main.neptune.memes.Memeager;
import net.minecraft.client.main.neptune.memes.Memetarget;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;

public class StorageESP extends Mod {
	
	boolean tags;
	
	public StorageESP() {
		super("ChestESP", Category.HACKS);
		this.setName("StorageESP");
	}

	@Override
	public void onEnable() {
		if(Neptune.getWinter().theMods.getMod(Tags.class).isEnabled()) {
			tags = true;
			Neptune.getWinter().theMods.getMod(Tags.class).setEnabled(false);
		}
		Memeager.register(this);
	}

	@Override
	public void onDisable() {
		Memeager.unregister(this);
	}

	@Memetarget
	public void onRender3D(EventRender3D event) {
		if(tags && !Neptune.getWinter().theMods.getMod(Tags.class).isEnabled()) {
			Neptune.getWinter().theMods.getMod(Tags.class).setEnabled(true);
			tags = false;
		}
		for (Object obj : this.mc.theWorld.loadedTileEntityList) {
			if (obj instanceof TileEntityChest) {
				TileEntityChest chest = (TileEntityChest) obj;
				double posX = chest.getPos().getX() - this.mc.getRenderManager().renderPosX;
				double posY = chest.getPos().getY() - this.mc.getRenderManager().renderPosY;
				double posZ = chest.getPos().getZ() - this.mc.getRenderManager().renderPosZ;
				RenderUtils.drawBlockESP(posX, posY, posZ, 1, 1, 0, 0.11F, 1, 1, 0, 0.5F, 1F);
			} else if (obj instanceof TileEntityEnderChest) {
				TileEntityEnderChest enderchest = (TileEntityEnderChest) obj;
				double posX = enderchest.getPos().getX() - this.mc.getRenderManager().renderPosX;
				double posY = enderchest.getPos().getY() - this.mc.getRenderManager().renderPosY;
				double posZ = enderchest.getPos().getZ() - this.mc.getRenderManager().renderPosZ;
				RenderUtils.drawBlockESP(posX, posY, posZ, 1, 0, 1, 0.11F, 1, 0, 1, 0.5F, 1F);
			} else {
				if (!(obj instanceof TileEntityDispenser)) {
					continue;
				}
				TileEntityDispenser dispenser = (TileEntityDispenser) obj;
				double posX = dispenser.getPos().getX() - this.mc.getRenderManager().renderPosX;
				double posY = dispenser.getPos().getY() - this.mc.getRenderManager().renderPosY;
				double posZ = dispenser.getPos().getZ() - this.mc.getRenderManager().renderPosZ;
				RenderUtils.drawBlockESP(posX, posY, posZ, 0, 0, 0, 0.11F, 0, 0, 0, 0.5F, 1F);
			}
		}
	}
}