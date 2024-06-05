package zamorozka.modules.PLAYER;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import optifine.BlockDir;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.event.events.EventRender2D;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.COMBAT.KillAura;
import zamorozka.ui.*;

public class ChestStealer extends Module {

	private boolean canSteal;

	private Container inventory;
	private IInventory iinventory;
	private Integer windowId;

	private Timer2 time = new Timer2();
	private Timer2 time2 = new Timer2();

	private SPacketWindowItems packet;

	public ChestStealer() {
		super("ChestStealer", 0, Category.PLAYER);
	}

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("FirstTickDelay", this, 250, 0, 2000, false));
		Zamorozka.settingsManager.rSetting(new Setting("SlotRandomization", this, 2, 0, 10, false));
		Zamorozka.settingsManager.rSetting(new Setting("StealerSpeed", this, 100, 0, 1000, false));
		Zamorozka.settingsManager.rSetting(new Setting("StealerSpeedRandom", this, 50, 0, 150, false));
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		double reach = Zamorozka.settingsManager.getSettingByName("StealerSpeed").getValDouble();
		this.setDisplayName("ChestStealer §f§ " + (int) Zamorozka.settingsManager.getSettingByName("StealerSpeed").getValDouble());
		if ((mc.player.openContainer != null) && ((mc.player.openContainer instanceof ContainerChest))) {
			new Thread(() -> {
				try {
					long Zd = (long) Zamorozka.settingsManager.getSettingByName("FirstTickDelay").getValDouble();
					Thread.sleep(Zd);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				ContainerChest container = (ContainerChest) mc.player.openContainer;
				for (int i = 0; i < container.inventorySlots.size(); i++) {
					if ((container.getLowerChestInventory().getStackInSlot(i).getItem() != Item.getItemById(0))
							&& (time.check((float) (Zamorozka.settingsManager.getSettingByName("StealerSpeed").getValDouble() + KillauraUtil.getRandomDouble(0, Zamorozka.settingsManager.getSettingByName("StealerSpeedRandom").getValDouble()))))) {
						int Random = (int) Zamorozka.settingsManager.getSettingByName("SlotRandomization").getValDouble();
					    /*int index = this.next(this.inventory);
						this.inventory.putStackInSlot(index, mc.player.inventory.getStackInSlot(index));*/
					mc.playerController.windowClick(container.windowId, MathUtil.getRandomInRange(i, i + Random), 0, ClickType.QUICK_MOVE, mc.player);
						time.reset();

					} else if (empty(container)) {
						mc.player.closeScreen();
						this.packet = null;

					}
				}
			}).start();
		}
	}
	
   /* private int next(final Container c) {
        for (int slot = (c.inventorySlots.size() == 90) ? 54 : 27, i = 0; i < slot; ++i) {
            if (c.getInventory().get(i) != null) {
                return i;
            }
        }
        return -1;
    }*/

	public boolean empty(Container container) {
		boolean voll = true;
		int i = 0;
		for (int slotAmount = container.inventorySlots.size() == 90 ? 54 : 27; i < slotAmount; i++) {
			if (container.getSlot(i).getHasStack()) {
				voll = false;
			}
		}
		return voll;
	}
}