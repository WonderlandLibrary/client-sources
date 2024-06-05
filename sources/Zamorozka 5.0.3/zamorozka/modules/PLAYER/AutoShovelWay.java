package zamorozka.modules.PLAYER;

import java.util.Comparator;

import de.Hero.settings.Setting;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSpade;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.BlockInteractionHelper;
import zamorozka.ui.EntityUtil;
import zamorozka.ui.PlayerUtil;

public class AutoShovelWay extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("Raduis", this, 4, 1, 10, true));
	}

	public AutoShovelWay() {
		super("AutoSpade", 0, Category.WORLD);
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		BlockPos l_ClosestPos = BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), (float) Zamorozka.settingsManager.getSettingByName("Radius").getValDouble(), 4, false, true, 0).stream().filter(p_Pos -> IsValidBlockPos(p_Pos))
				.min(Comparator.comparing(p_Pos -> EntityUtil.GetDistanceOfEntityToBlock(mc.player, p_Pos))).orElse(null);

		if (l_ClosestPos != null && mc.player.getHeldItemMainhand().getItem() instanceof ItemSpade) {
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

		if (l_State.getBlock() instanceof BlockGrass)
			return mc.world.getBlockState(p_Pos.up()).getBlock() == Blocks.AIR;

		return false;
	}
}