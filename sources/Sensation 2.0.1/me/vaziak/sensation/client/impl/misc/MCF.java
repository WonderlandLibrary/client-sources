package me.vaziak.sensation.client.impl.misc;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.MouseClickEvent;
import me.vaziak.sensation.client.api.friend.Friend;
import me.vaziak.sensation.utils.client.ChatUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumChatFormatting;

public class MCF extends Module {

	public MCF() {
		super("MCF", Category.MISC);
	}
	
	@Collect
	public void onMouseClick(MouseClickEvent mouseClickEvent) {
		if (mouseClickEvent.getMouseButton() == 2) {
			if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
				EntityLivingBase entity = (EntityLivingBase) mc.objectMouseOver.entityHit;
				if (Sensation.instance.friendManager.getFriend(entity.getName()) == null) {
					Sensation.instance.friendManager.addFriend(new Friend(entity.getName(), entity.getName()));
					ChatUtils.log("You have added " + EnumChatFormatting.RED + entity.getName() + EnumChatFormatting.WHITE + " as a friend!");
				}
			}
		}
	}
	
}
