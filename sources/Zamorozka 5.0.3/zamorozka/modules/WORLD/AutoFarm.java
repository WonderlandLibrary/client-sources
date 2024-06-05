package zamorozka.modules.WORLD;

import java.util.Comparator;

import de.Hero.settings.Setting;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSeeds;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketBlockAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.ui.BlockInteractionHelper;
import zamorozka.ui.EntityUtil;
import zamorozka.ui.PlayerUtil;
import zamorozka.ui.TimerHelper;

public class AutoFarm extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("FarmRadius", this, 4, 0, 10, true));
		Zamorozka.settingsManager.rSetting(new Setting("FarmDelay", this, 1, 0, 10, true));
	}

	private TimerHelper timer = new TimerHelper();

	public AutoFarm() {
		super("AutoFarmLand", 0, Category.WORLD);
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		double delay = Zamorozka.settingsManager.getSettingByName("FarmDelay").getValDouble();
		if (timer.hasReached(delay * 100))
			return;

		BlockPos l_ClosestPos = BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), (float) Zamorozka.settingsManager.getSettingByName("FarmRadius").getValDouble(), 6, false, true, 0).stream()
				.filter(p_Pos -> IsValidBlockPos(p_Pos)).min(Comparator.comparing(p_Pos -> EntityUtil.GetDistanceOfEntityToBlock(mc.player, p_Pos))).orElse(null);

		if (l_ClosestPos != null && mc.player.getHeldItemMainhand().getItem() instanceof ItemHoe) {
			timer.reset();

			event.setCancelled(true);

			final float[] l_Pos = EntityUtil.calculateLookAt(l_ClosestPos.getX() + 0.5, l_ClosestPos.getY() - 0.5, l_ClosestPos.getZ() + 0.5, mc.player);

			mc.player.rotationYawHead = (float) l_Pos[0];

			PlayerUtil.PacketFacePitchAndYaw((float) l_Pos[1], (float) l_Pos[0]);

			mc.player.swingArm(EnumHand.MAIN_HAND);

			mc.getConnection().sendPacket(new CPacketPlayerTryUseItemOnBlock(l_ClosestPos, EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
		}
	}

	private boolean IsValidBlockPos(final BlockPos p_Pos) {
		IBlockState l_State = mc.world.getBlockState(p_Pos);

		if (l_State.getBlock() instanceof BlockDirt || l_State.getBlock() instanceof BlockGrass)
			return mc.world.getBlockState(p_Pos.up()).getBlock() == Blocks.AIR;

		return false;
	}

}