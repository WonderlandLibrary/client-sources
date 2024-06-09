package me.swezedcode.client.module.modules.World;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.manager.managers.FriendManager;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.values.BooleanValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class MurdurerFinder extends Module {

	public MurdurerFinder() {
		super("MurdurerFinder", Keyboard.KEY_NONE, 0xFFFC4783, ModCategory.World);
	}

	@EventListener
	public void onPre(EventPreMotionUpdates event) {
		for (Object o : mc.theWorld.loadedEntityList) {
			if ((!(o instanceof EntityPlayerSP)) && ((o instanceof EntityPlayer))) {
				EntityLivingBase ent = (EntityLivingBase) o;
				for (int i = 0; i < 1; i++) {
					if ((ent != null) && (ent.getName() != mc.thePlayer.getName())
							&& ent.getHeldItem().getItem() == Items.iron_sword) {
						msg("§c" + ent.getName().toUpperCase() + " IS THE MURDURER");
					}
				}
			}
		}
	}

}
