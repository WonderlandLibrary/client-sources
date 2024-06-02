/**
 * 
 */
package cafe.kagu.kagu.mods.impl.move;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventPacketSend;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.combat.ModKillAura;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * @author lavaflowglow
 *
 */
public class ModNoSlow extends Module {
	
	public ModNoSlow() {
		super("NoSlow", Category.MOVEMENT);
		setSettings(cancelItemSlowdown, itemNoSlowBypass, cancelSneakSlowdown, cancelWebSlowdown, cancelLadderSlowdown,
				cancelLadderDescentSlowdown);
	}
	
	private BooleanSetting cancelItemSlowdown = new BooleanSetting("Cancel Item Slowdown", true);
	private ModeSetting itemNoSlowBypass = new ModeSetting("Item NoSlow Bypass", "None", "None", "NCP", "AACv4").setDependency(cancelItemSlowdown::isEnabled);
	private BooleanSetting cancelSneakSlowdown = new BooleanSetting("Cancel Sneak Slowdown", false);
	private BooleanSetting cancelWebSlowdown = new BooleanSetting("Cancel Web Slowdown", false);
	private BooleanSetting cancelLadderSlowdown = new BooleanSetting("Cancel Ladder Slowdown", false);
	private BooleanSetting cancelLadderDescentSlowdown = new BooleanSetting("Cancel Ladder Descent Slowdown", false);
	
	@EventHandler
	private Handler<EventPacketSend> onPacketSend = e -> {
		if (cancelItemSlowdown.isDisabled())
			return;
		EntityPlayerSP thePlayer = mc.thePlayer;
		switch (itemNoSlowBypass.getMode()) {
			case "NCP":{
				if (!(e.getPacket() instanceof C03PacketPlayer) || !((C03PacketPlayer)e.getPacket()).isMoving() || !(thePlayer.isUsingItem() || thePlayer.isBlocking() 
						|| Kagu.getModuleManager().getModule(ModKillAura.class).isBlocking()))
					return;
				if (e.isPre()) {
					mc.getNetHandler().getNetworkManager().sendPacket(
							new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
				}else {
					mc.getNetHandler().getNetworkManager().sendPacket(
							new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 255, thePlayer.getHeldItem(), 0, 0, 0));
				}
			}break;
			case "AACv4":{
				if (!(e.getPacket() instanceof C03PacketPlayer) || !((C03PacketPlayer)e.getPacket()).isMoving() || !(thePlayer.isUsingItem() || thePlayer.isBlocking() 
						||Kagu.getModuleManager().getModule(ModKillAura.class).isBlocking()))
					return;
				if (thePlayer.ticksExisted % 2 != 0)
					return;
				if (e.isPre()) {
					mc.getNetHandler().getNetworkManager().sendPacket(
							new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
				}else {
					mc.getNetHandler().getNetworkManager().sendPacket(
							new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 255, thePlayer.getHeldItem(), 0, 0, 0));
				}
			}break;
		}
	};
	
	/**
	 * @return the cancelItemSlowdown
	 */
	public BooleanSetting getCancelItemSlowdown() {
		return cancelItemSlowdown;
	}
	
	/**
	 * @return the cancelSneakSlowdown
	 */
	public BooleanSetting getCancelSneakSlowdown() {
		return cancelSneakSlowdown;
	}
	
	/**
	 * @return the cancelWebSlowdown
	 */
	public BooleanSetting getCancelWebSlowdown() {
		return cancelWebSlowdown;
	}
	
	/**
	 * @return the cancelLadderSlowdown
	 */
	public BooleanSetting getCancelLadderSlowdown() {
		return cancelLadderSlowdown;
	}
	
	/**
	 * @return the cancelLadderDescentSlowdown
	 */
	public BooleanSetting getCancelLadderDescentSlowdown() {
		return cancelLadderDescentSlowdown;
	}
	
}
