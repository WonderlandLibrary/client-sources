package in.momin5.cookieclient.client.modules.player;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.event.events.ClickBlockEvent;
import in.momin5.cookieclient.api.event.events.DamageBlockEvent;
import in.momin5.cookieclient.api.event.events.DestroyBlockEvent;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;

public class SpeedMine extends Module {

	BlockPos blockPos1;
	EnumFacing facing1;

	public SpeedMine() {
		super("SpeedMine", Category.PLAYER);
	}

	private final SettingMode mode = register( new SettingMode("Mode", this, "Packet", "Damage", "Packet", "Instant", "Civbreak"));
	private final SettingNumber startDamage = register( new SettingNumber("StartDamage", this, 0.10D, 0.00D, 1.00D, 0.05D));
	private final SettingNumber stopDamage 	= register( new SettingNumber("StopDamage",  this, 0.90D, 0.00D, 1.00D, 0.05D));

	@Override
	public void onEnable() {
		if(nullCheck())
			return;
		CookieClient.EVENT_BUS.subscribe(this);
	}

	@Override
	public void onDisable() {
		CookieClient.EVENT_BUS.unsubscribe(this);
		MinecraftForge.EVENT_BUS.unregister(this);
	}

	@Override
	public void onUpdate() {
		if (nullCheck())
			return;

		if (mode.is("Civbreak") &&
				mc.player.getDistance(blockPos1.x, blockPos1.y, blockPos1.z) < 4 &&
				!(mc.world.isAirBlock(blockPos1)) &&
				canBreak(blockPos1)) {
			mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos1, facing1));
		}
	}

	@EventHandler
	public Listener<ClickBlockEvent> onBlockClicked = new Listener<>(event -> {
		blockPos1 = event.getBlockPos();
		facing1 = event.getEnumFacing();

		if (!mode.is("Damage")) {
			mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos1, facing1));
			mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos1, facing1));

			if (mode.is("Instant")) {
				mc.world.setBlockToAir(blockPos1);
			}
		} else {
			mc.playerController.curBlockDamageMP = (float) startDamage.getValue();

		}
	});

	@EventHandler
	public Listener<DamageBlockEvent> onBlockDamaged = new Listener<>(event -> {
		if (mode.is("Damage")) {
			if (mc.playerController.curBlockDamageMP >= stopDamage.getValue()) {
				mc.playerController.curBlockDamageMP = 1.0f;
			}
		}
	});

	@EventHandler
	public Listener<DestroyBlockEvent> onBlockDestroyed = new Listener<>(event -> {
	});

	private boolean canBreak(BlockPos position) {

		return mc.world.getBlockState(position).getBlockHardness(mc.world, position) != -1;
	}

}
