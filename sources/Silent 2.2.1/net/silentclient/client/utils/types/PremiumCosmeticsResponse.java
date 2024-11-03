package net.silentclient.client.utils.types;

import java.util.ArrayList;

import net.silentclient.client.utils.reply.AbstractReply;
import net.silentclient.client.utils.types.PlayerResponse.Account.Cosmetics.CosmeticItem;

public class PremiumCosmeticsResponse extends AbstractReply {
	public ArrayList<PremiumCosmetics> cosmetics;
	
	public class PremiumCosmetics {
		public String type;
		public CosmeticItem item;
		
		public String getType() {
			return type;
		}
		
		public CosmeticItem getItem() {
			return item;
		}
	}
}
