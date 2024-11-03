package net.silentclient.client.mods.staff;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.silentclient.client.Client;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.utils.NotificationUtils;

public class DebugNpcMod extends Mod {
	private EntityOtherPlayerMP npc;
	
	public DebugNpcMod() {
		super("Debug NPC", ModCategory.MODS, null);
	}
	
	@Override
	public void setup() {
		this.addInputSetting("Username", this, Minecraft.getMinecraft().getSession().getUsername());
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		if(mc.thePlayer == null && mc.theWorld == null) {
			this.setEnabled(false);
			return;
		}
		
		if(!mc.isSingleplayer()) {
			NotificationUtils.showNotification("Error", "This mod can only be used in single player");
			this.setEnabled(false);
			return;
		}
		
		npc = new EntityOtherPlayerMP(mc.theWorld, new GameProfile(UUID.randomUUID(), Client.getInstance().getSettingsManager().getSettingByName(this, "Username").getValString()));
		npc.copyLocationAndAnglesFrom(mc.thePlayer);
		npc.setRotationYawHead(mc.thePlayer.rotationYawHead);
		
		mc.theWorld.addEntityToWorld(npc.getEntityId(), npc);
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
		if(mc.thePlayer == null || mc.theWorld == null) {
			return;
		}
		
		if(npc != null) {
			mc.theWorld.removeEntityFromWorld(npc.getEntityId());
			npc = null;
		}
	}
}
