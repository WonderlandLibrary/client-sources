package com.darkcart.xdolf.mods.player;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;

public class Insulter extends Module {
	
	/**
	 * Code from 2hacks2exploits by 2F4U.
	 */
	
	int clock = 0;
	  static String[] insults;
	
	public Insulter() {
		super("insulter", "NoCheat+", "Insults a random player.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			ArrayList playermap = new ArrayList(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
		      for (Object npi : playermap) {
		        onInsult((NetworkPlayerInfo)npi);
		      }
		}
	}
	
	public void onInsult(NetworkPlayerInfo npi)
	  {
	    this.clock += 1;
	    int playercount = npi.getGameProfile().getName().length();
	    String player = npi.getGameProfile().getName();
	    if (this.clock >= 12000)
	    {
	      String[] insults = {  "Squeeze.. Squeeze @p", "Oh, hello @p. No, I won't give your dildo back.", "@p messaged me: Hey, I am gay ;( Don't tell anyone, please.", "7b7t, 2b2t & 3b3t.pw is the best Minecraft Server.", "Anarchy.PW is the best Minecraft Server.","DezzDox is Guzar's dad.", "xdolf is trash.", "Oh, hey @p! I saw you at the Sex Store yesterday :D", "Did you know? @p just did the Llama dupe, and duped: 5xRed_shulker_box5xBlack_shulker_box", "@p why don't you end your pathetic life?", "Nobody likes @p, because he's gay.", "@p Join #TeamSprite", "@p has mommy issues.", "Hey @p, Why don't you jack off to horse porn?", "The most gay person on this server is... @p", "Jollerino loves candy.", "Did you know? Xulu was coded by SLiZ_D_2017", "The person I mention now is Yohan's son... @p" };
	      Random random = new Random();
	      String iteminhand = Minecraft.getMinecraft().player.getHeldEquipment().toString();
	      int index = random.nextInt(insults.length);
	      String chat = insults[index];
	      chat = chat.replace("@p", npi.getGameProfile().getName());
	      chat = chat.replaceAll("itemhand", iteminhand);
	      Minecraft.getMinecraft().player.sendChatMessage("> " + chat);
	      
	      this.clock = 0;
	    }
	    else {}
	}
	
	@Override
	public void onDisable() {
	}
}
