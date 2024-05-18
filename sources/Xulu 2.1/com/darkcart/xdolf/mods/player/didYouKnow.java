package com.darkcart.xdolf.mods.player;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;

public class didYouKnow extends Module {
	
	/**
	 * Code from 2hacks2exploits by 2F4U.
	 */
	
	int clock = 0;
	  static String[] insults;
	
	public didYouKnow() {
		super("teamSprite", "New", "Sends >|#TeamSprite| messages", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			ArrayList playermap = new ArrayList(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
		      for (Object npi : playermap) {
		        onSentMessage((NetworkPlayerInfo)npi);
		      }
		}
	}
	
	public void onSentMessage(NetworkPlayerInfo npi)
	  {
	    this.clock += 1;
	    int playercount = npi.getGameProfile().getName().length();
	    String player = npi.getGameProfile().getName();
	    if (this.clock >= 11000)
	    {
	      String[] insults = {  "OfficialAutism thinks he's so good when he is getting rekt 3 times each day.", "usedmeme thinks he's good when he runs away.","Niggers","086 o.O","Dumpstered's friend is going to die, good.","gustav000 is transgender.", "Xulu vs Future = Xulu wins ;3.", "You're all so easy.","2F4U okay, okay..","No, shutup.","We always win.", "Oh, I'm soo sorry for hurting your feelings.", "Am I supposed to care?", "Dupe is all you idiots can do.", "Camoaka turns on Spammer when he's crying.", "Jollerino thinks he's a god.", "Ignoring me = Can't handle the autism.", "Dumpstered is a money whore.", "Team TopKek can't fight, they just run.", "Your insults are weak.", "Was that a insult? Oh, I didn't notice.", "Jolle logs at 3 hearts.", "Xulu is the best hacked client. https://github.com/SLiZ2k17/xulu", "Download our hacked client now! https://github.com/SLiZ2k17/xulu", "Team TopRunners", "TeamTopKek is a newfag team.", "CrystalAura takes skill.. ;)", "ᴡᴇ ᴅᴏɴ'ᴛ ᴄᴀʀᴇ", "ProxyCake logged in a 1v1.", "RMextreme is too afraid to fight us.", "ProxyLogger", "1 of us can beat a 3v1 by him/herself." };
	      Random random = new Random();
	      String iteminhand = Minecraft.getMinecraft().player.getHeldEquipment().toString();
	      int index = random.nextInt(insults.length);
	      String chat = insults[index];
	      chat = chat.replace("@p", npi.getGameProfile().getName());
	      chat = chat.replaceAll("itemhand", iteminhand);
	      Minecraft.getMinecraft().player.sendChatMessage(">|#TeamSprite| " + chat + " ɪ ❤ xᴜʟᴜ");
	      
	      this.clock = 0;
	    }
	    else {}
	}
	
	@Override
	public void onDisable() {
	}
}
