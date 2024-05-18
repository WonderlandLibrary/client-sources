package com.kilo.mod.all;

import net.minecraft.entity.player.EntityPlayer;

import com.kilo.manager.HackFriendManager;
import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.toolbar.dropdown.SettingsRel;
import com.kilo.ui.inter.slotlist.part.HackFriend;

public class Friends extends Module {
	
	public Friends(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Edit", "Add or remove friends", Interactable.TYPE.SETTINGS, SettingsRel.FRIENDS, null, false);
	}
	
	public void update() {
		if (!active) { return; }
	}
	
	public void onPlayerPick() {
		if (mc.objectMouseOver != null) {
			if (mc.objectMouseOver.entityHit != null) {
				if (mc.objectMouseOver.entityHit instanceof EntityPlayer) {
					if (HackFriendManager.getHackFriend(mc.objectMouseOver.entityHit.getCommandSenderName()) == null) {
						HackFriendManager.addHackFriend(new HackFriend(mc.objectMouseOver.entityHit.getCommandSenderName(), ""));
					} else {
						HackFriendManager.removeHackFriend(HackFriendManager.getHackFriend(mc.objectMouseOver.entityHit.getCommandSenderName()));
					}
				}
			}
		}
	}
}
