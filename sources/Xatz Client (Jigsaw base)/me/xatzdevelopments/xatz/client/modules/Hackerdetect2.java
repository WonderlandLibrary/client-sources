package me.xatzdevelopments.xatz.client.modules;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.hackerdetect.Hacker;
import me.xatzdevelopments.xatz.hackerdetect.checks.AntiKBCheck;
import me.xatzdevelopments.xatz.hackerdetect.checks.Check;
import me.xatzdevelopments.xatz.hackerdetect.checks.KillAuraCheck;
import me.xatzdevelopments.xatz.hackerdetect.checks.KillAuraCheck3;
import me.xatzdevelopments.xatz.hackerdetect.checks.KillAuraCheck4;
import me.xatzdevelopments.xatz.hackerdetect.checks.NoSlowCheck;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;

public class Hackerdetect2 extends Module {

	public static HashMap<String, Hacker> players = new HashMap<String, Hacker>();
	public static ArrayList<String> muted = new ArrayList<String>();
	public static ArrayList<Check> checks = new ArrayList<Check>();

	public Hackerdetect2() {
		super("HackerDetect2", Keyboard.KEY_NONE, Category.HIDDEN, "Walmart edition. Only has noslow checks.");
	}

	@Override
	public void onDisable() {

		super.onDisable();
	}

	@Override
	public void onEnable() {
		
		super.onEnable();
	}

	@Override
	public void onUpdate() {
		if (mc.theWorld == null) {
			return;
		}
		for (EntityPlayer ep : mc.theWorld.playerEntities) {
				// NoSlowDown
				double xSpeed = ep.posX - ep.lastTickPosX;
				double zSpeed = ep.posZ - ep.lastTickPosZ;
				double speed = Math.abs(xSpeed) + Math.abs(zSpeed);
				double speedValue = ep.getActivePotionEffect(Potion.moveSpeed) != null ? ((double)ep.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1) / 30 : 0;
				if (ep.isBlocking() && speed > 0.31 + speedValue && speed < 0.5 + speedValue && ep.hurtTime <= 5) {
					this.failed(ep, CheckType.NOSLOWDOWN);
				}
				
				//Ground Spoof
				if (ep.onGround && !this.isOnGround(ep)) {
					this.failed(ep, CheckType.GROUNDSPOOF);
				}
			}
		}
	
	
	private void failed(EntityPlayer ep, CheckType ct) {
		if (ep == mc.thePlayer) {
			return;
		}
		
		if (ct == CheckType.NOSLOWDOWN) {
			Xatz.sendChatMessageFinal(ep.getName() + " has failed NoSlow");
		}
		
		if (ct == CheckType.GROUNDSPOOF) {
			//ChatUtils.sendMessage(ep.getName() + " has failed GroundSpoof");
		}
	}
	
	private boolean isOnGround(EntityPlayer ep) {
		double expand = 0.8;
		if (mc.getNetHandler() == null || ep.getUniqueID() == null) {
			expand = 1.5;
		} else {
			NetworkPlayerInfo i = mc.getNetHandler().getPlayerInfo(ep.getUniqueID());
			expand += ((double)i.getResponseTime() / 300);
		}
		if (ep.capabilities.allowFlying || ep.capabilities.isFlying) {
			return ep.onGround;
		}
		for (double x = -expand; x < expand; x += expand) {
			for (double z = -expand; z <= expand; z += expand) {
				Block block = mc.theWorld.getBlockState(ep.getPosition().add(x, -0.5001, z)).getBlock();
				if (block != Blocks.air && block != Blocks.grass
						&& block != Blocks.water
						&& block != Blocks.flowing_water
						&& block != Blocks.flowing_lava
						&& block != Blocks.lava
						&& block != Blocks.tallgrass) {
					return true;
				}
			}
		}
		return false;
	}
	
}

enum CheckType {
	NOSLOWDOWN, GROUNDSPOOF;
}