package zamorozka.modules.VISUALLY;

import org.lwjgl.input.Keyboard;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class NoLag extends Module {

	public NoLag() {
		super("NoLag", Keyboard.KEY_NONE, Category.VISUALLY);
	}
	
	@EventTarget
	public void receivePacket(EventReceivePacket event) {
		if (event.getPacket() instanceof SPacketParticles) {
			final SPacketParticles packet = (SPacketParticles)event.getPacket();
				event.setCancelled(true);
		}
		if (event.getPacket() instanceof SPacketEffect) {
			final SPacketEffect packet = (SPacketEffect)event.getPacket();
				event.setCancelled(true);
		}
		if (event.getPacket() instanceof SPacketSoundEffect) {
			final SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
            if (packet.getCategory() == SoundCategory.PLAYERS && packet.getSound() == SoundEvents.ITEM_ARMOR_EQUIP_GENERIC) {
                event.setCancelled(true);
            }
		}
		if (event.getPacket() instanceof SPacketSpawnMob) {
            final SPacketSpawnMob packet = (SPacketSpawnMob) event.getPacket();
            if (packet.getEntityType() == 55) {
                event.setCancelled(true);
            }
		}
		if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
            if (packet.getCategory() == SoundCategory.WEATHER && packet.getSound() == SoundEvents.ENTITY_LIGHTNING_THUNDER) {
                event.setCancelled(true);
            }
		}
		if (event.getPacket() instanceof SPacketEffect) {
            final SPacketEffect packet = (SPacketEffect) event.getPacket();
            if (packet.getSoundType() == 1038 || packet.getSoundType() == 1023 || packet.getSoundType() == 1028) {
                event.setCancelled(true);
            }
		}
		
		for (TileEntity e : mc.world.loadedTileEntityList) {
            if (e instanceof TileEntitySign) {
                TileEntitySign sign = (TileEntitySign) e;

                for (int i = 0; i <= 3; i++) {
                    sign.signText[i] = new TextComponentString("");
                }
            }
		}
		
		for (int i = 0; i <= 45; i++) {
            ItemStack item = mc.player.inventory.getStackInSlot(i);
            if (item.getItem() instanceof ItemBook) {
                mc.player.dropItem(item, false);
            }
		}
	}
	@Override
	public void onDisable() {
		mc.renderGlobal.loadRenderers();
		super.onDisable();
	}
	
}