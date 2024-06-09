package me.valk.agway.modules.other;

import java.awt.Color;

import org.lwjgl.input.Mouse;

import me.valk.Vital;
import me.valk.event.EventListener;
import me.valk.event.events.player.EventPlayerUpdate;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import me.valk.utils.TimerUtils;
import net.minecraft.entity.player.EntityPlayer;

public class MiddleClickMod extends Module {
	private TimerUtils time;
	public static boolean down;

	public MiddleClickMod() {
		super(new ModData("MiddleClickFriend", 0, new Color(255, 50, 53)), ModType.OTHER);
		this.time = new TimerUtils();
	}

	@EventListener
	public void onUpdate(EventPlayerUpdate event) {
		if (Mouse.isButtonDown(2) && !MiddleClickMod.down) {
			if (this.mc.objectMouseOver.entityHit != null) {
				EntityPlayer player = (EntityPlayer) this.mc.objectMouseOver.entityHit;
				String playername = player.getName();
				if (!Vital.getManagers().getFriendManager().hasFriend(playername)) {
					this.mc.thePlayer.sendChatMessage(".friend add " + playername);
					this.addChat(playername + " Has been friended !");
				} else {
					this.mc.thePlayer.sendChatMessage(".friend remove " + playername);
					this.addChat(playername + " Has been unFriended");
				}
			}
			this.down = true;
		}
		if (!Mouse.isButtonDown(2)) {
			this.down = false;
		}
	}
}
