package net.minecraft.client.triton.impl.modules.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.Location;

import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.PacketReceiveEvent;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.Timer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

@Mod
public class NoteTuner extends Module {
	private BlockPos curBlock = null;
	private boolean listen;
	private boolean listen1;
	private int findMode = 0;
	private Timer time = new Timer();
	Location l = null;
	private HashMap<Integer, Integer> map = new HashMap();
	private List<Integer> tuned = new ArrayList();

	@EventTarget
	public void onPacketReceive(PacketReceiveEvent event) {
		if (event.getPacket() instanceof S24PacketBlockAction) {
			S24PacketBlockAction packet = (S24PacketBlockAction) event.getPacket();
			for (int i = 0; i < 25; i++) {
				int startX = (int) Math.floor(ClientUtils.player().posX) - 2;
				int startY = (int) Math.floor(ClientUtils.player().posY) - 1;
				int startZ = (int) Math.floor(ClientUtils.player().posZ) - 2;
				int x = startX + i % 5;
				int z = startZ + i / 5;

				BlockPos pos = packet.func_179825_a();
				if ((pos.getY() == startY) && (pos.getX() == x) && (pos.getZ() == z)) {
					this.map.put(Integer.valueOf(i), Integer.valueOf(packet.field_148873_e));
					if (this.listen) {
						this.listen = false;
					}
					if (this.listen1) {
						this.listen1 = false;
					}
					if ((!this.tuned.contains(Integer.valueOf(i))) && (i == packet.field_148873_e)) {
						this.tuned.add(Integer.valueOf(i));
						System.out.println(i + " tuned" + packet.field_148873_e);
					}
				}
			}
		}
	}

	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if (event.getState() == event.getState().PRE) {
			for (int i = 0; i < 25; i++) {
				int startX = (int) Math.floor(ClientUtils.player().posX) - 2;
				int startY = (int) Math.floor(ClientUtils.player().posY) - 1;
				int startZ = (int) Math.floor(ClientUtils.player().posZ) - 2;
				int x = startX + i % 5;
				int z = startZ + i / 5;
				if (!this.map.containsKey(Integer.valueOf(i))) {
					this.curBlock = new BlockPos(x, startY, z);

					float[] rotations = getBlockRotations(x, startY, z);

					event.setYaw(rotations[0]);
					event.setPitch(rotations[1]);
					ClientUtils.player().setPosition(ClientUtils.player().posX, ClientUtils.player().posY,
							ClientUtils.player().posZ);

					this.findMode = 0;
				} else if (!this.tuned.contains(Integer.valueOf(i))) {
					this.curBlock = new BlockPos(x, startY, z);

					float[] rotations = getBlockRotations(x, startY, z);

					event.setYaw(rotations[0]);
					event.setPitch(rotations[1]);
					ClientUtils.player().setPosition(ClientUtils.player().posX, ClientUtils.player().posY,
							ClientUtils.player().posZ);

					this.findMode = 1;
				}
			}
		}
		if (event.getState() == event.getState().POST) {
			if (this.l != null) {
				ClientUtils.player().setPosition(ClientUtils.player().posX, ClientUtils.player().posY,
						ClientUtils.player().posZ);
				this.l = null;
			}
			if (this.curBlock != null) {
				if (this.findMode == 0) {
					if (this.time.delay(200)) {
						ClientUtils.player().swingItem();
						ClientUtils.player().sendQueue.addToSendQueue(new C07PacketPlayerDigging(
								C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.curBlock, EnumFacing.UP));

						ClientUtils.player().swingItem();
						ClientUtils.player().sendQueue.addToSendQueue(new C07PacketPlayerDigging(
								C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.curBlock, EnumFacing.UP));

						this.curBlock = null;
						this.listen = true;
						this.time.reset();
					}
				} else if (this.time.delay(200)) {
					ClientUtils.player().sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.curBlock, 1,
							ClientUtils.player().getHeldItem(), 0.0F, 0.0F, 0.0F));
					ClientUtils.player().swingItem();
					this.curBlock = null;
					this.listen1 = true;
					this.time.reset();
				}
			}
		}
	}

	public void enable() {
		super.enable();
		this.map.clear();
		this.tuned.clear();
		this.listen = false;
		this.listen1 = false;
		time.reset();
	}

	public float[] getBlockRotations(double x, double y, double z) {
		double var4 = x - ClientUtils.player().posX + 0.5D;
		double var6 = z - ClientUtils.player().posZ + 0.5D;

		double var8 = y - (ClientUtils.player().posY + ClientUtils.player().getEyeHeight() - 1.0D);
		double var14 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
		float var12 = (float) (Math.atan2(var6, var4) * 180.0D / 3.141592653589793D) - 90.0F;

		return new float[] { var12, (float) -(Math.atan2(var8, var14) * 180.0D / 3.141592653589793D) };
	}
}
