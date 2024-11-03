package net.silentclient.client.utils.types;

import net.silentclient.client.utils.reply.AbstractReply;
import net.silentclient.client.utils.types.PlayerResponse.Account.Cosmetics;

public class CosmeticsResponse extends AbstractReply {
	public boolean error;
	public Cosmetics cosmetics;
	
	public Cosmetics getCosmetics() {
		return cosmetics;
	}
}
