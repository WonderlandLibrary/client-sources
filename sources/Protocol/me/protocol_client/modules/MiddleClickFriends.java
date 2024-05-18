package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class MiddleClickFriends extends Module{

	public MiddleClickFriends() {
		super("MCF", "mvmbmcmvcnnvncnvncxncv",0, Category.MISC, new String[]{"dsdfsdfsdfsdghgh"});
	}
	@Override
	public void onMiddleClick() {
		if(isToggled()){
			if(Minecraft.getMinecraft().objectMouseOver != null && Minecraft.getMinecraft().objectMouseOver.entityHit instanceof EntityPlayer){
				String name = Minecraft.getMinecraft().objectMouseOver.entityHit.getName();
				if(!Protocol.getFriendManager().isFriend(name)){
				Protocol.nukerModule.runCmd("add " + name);
			}else{
				Protocol.nukerModule.runCmd("del " + name);
			}
			}
		}
		super.onMiddleClick();
	}
}
