package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.utils.MathUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPrePlayerUpdate;

public class CDrop extends Module {
	private Long	last	= null;

	public CDrop() {
		super("CDrop", "cdrop", 0, Category.MISC, new String[] { "dsdfsdfsdfsdghgh" });
	}

	public int	itemsthrown	= 0;

	@EventTarget
	public void onEvent(EventPrePlayerUpdate event) {
		for (int l = 0; l < 40; l++) {
			if (Wrapper.getPlayer().capabilities.isCreativeMode) {
				new Item();
				Item item = Item.getItemById(MathUtils.randInt(1, Item.itemRegistry.getKeys().size()));
				ItemStack itemStack = new ItemStack(item, 1);

				itemStack.setStackDisplayName(getString(20));
				if (item != null) {
					Wrapper.sendPacket(new C10PacketCreativeInventoryAction(Wrapper.getPlayer().inventory.currentItem + 36, itemStack));
				}
				if ((this.last == null) || (getTime().longValue() >= this.last.longValue() + 1L)) {
					for (int i = 0; i < 40; i++) {
						this.last = getTime();
						itemsthrown = itemsthrown + 2;
						Wrapper.getPlayer().dropOneItem(true);
					}
				}
			} else if (Wrapper.getPlayer().inventory.getCurrentItem() != null) {
				for (int i = 0; i < 40; i++) {
					itemsthrown = itemsthrown + 2;
					Wrapper.getPlayer().dropOneItem(true);
				}
			}
		}
	}

	public static Long getTime() {
		return System.nanoTime() / 100000;
	}

	public static String getString(int length) {
		String[] colors = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "k", "l", "m", "n", "o", "r" };

		String[] names = { "Sub> YT/HypnoHacks", "go fuck yourself", "get rekt", "1v1 me rust", "nice server pleb", "need to download more ram", "noice", "how dem frames doe?" };

		String string = "§" + colors[MathUtils.randInt(0, colors.length - 1)] + "#ProtocolClient ";
		for (int l = 0; l < 1; l++) {
			string = string + "§" + colors[MathUtils.randInt(0, colors.length - 1)] + names[MathUtils.randInt(0, colors.length - 1)];
		}
		return string;
	}

	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		itemsthrown = 0;
		EventManager.unregister(this);
	}
}
