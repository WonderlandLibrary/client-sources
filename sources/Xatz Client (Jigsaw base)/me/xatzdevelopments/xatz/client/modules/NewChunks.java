package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.modules.target.AuraUtils;
import me.xatzdevelopments.xatz.client.modules.target.Team;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.client.tools.RenderTools;
import me.xatzdevelopments.xatz.client.tools.Utils;
import me.xatzdevelopments.xatz.gui.custom.clickgui.CheckBtnSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ModSetting;
import me.xatzdevelopments.xatz.hackerdetect.Hacker;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.network.AbstractPacket;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.BlockPos;

public class NewChunks extends Module {

	public NewChunks() {
		super("NewChunks", Keyboard.KEY_NONE, Category.RENDER, "Renders a box on entities.");
	}

	@Override
	public void onEnable() {
		super.onEnable();
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public void onRender() {

	//	for (Chunk chunk : coords) {
		//	int x, y, z;
			//x = chunk.x * 16;
			//y = 0;
			//z = chunk.z * 16;
			//chunkESP(x, y, z);
		//}

		super.onRender();
	}
	
	/*@Override
	public void onPacketRecieved(AbstractPacket packetIn) {
		if (packetIn instanceof S21PacketChunkData && !((S21PacketChunkData)) {
				coords.add(mc.theWorld.getChunkFromChunkCoords(((S21PacketChunkData) S21PacketChunkData.getChunkX(),
						((S21PacketChunkData) packet.getPacket()).getChunkZ()));
			}
		} */

}
