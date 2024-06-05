package zamorozka.modules.WORLD;

import java.util.Comparator;

import de.Hero.settings.Setting;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemHoe;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.ui.BlockInteractionHelper;
import zamorozka.ui.EntityUtil;
import zamorozka.ui.PlayerUtil;
import zamorozka.ui.Timer2;
import zamorozka.ui.TimerHelper;

public class Lawnmower extends Module {

	private Timer2 timer = new Timer2();

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("Radius", this, 4, 0, 20, true));
		Zamorozka.settingsManager.rSetting(new Setting("Flowers", this, true));
	}

	public Lawnmower() {
		super("Lawnmower", 0, Category.WORLD);
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		BlockPos l_ClosestPos = BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), (float) Zamorozka.settingsManager.getSettingByName("Radius").getValDouble(), 4, false, true, 0).stream().filter(p_Pos -> IsValidBlockPos(p_Pos))
				.min(Comparator.comparing(p_Pos -> EntityUtil.GetDistanceOfEntityToBlock(mc.player, p_Pos))).orElse(null);

		if (l_ClosestPos != null) {
			event.setCancelled(true);

			final float[] l_Pos = EntityUtil.calculateLookAt(l_ClosestPos.getX() + 0.5, l_ClosestPos.getY() - 0.5, l_ClosestPos.getZ() + 0.5, mc.player);

			mc.player.rotationYawHead = (float) l_Pos[0];
			event.setYaw(l_Pos[0]);
			event.setPitch(l_Pos[1]);

			mc.player.swingArm(EnumHand.MAIN_HAND);
			mc.playerController.clickBlock(l_ClosestPos, EnumFacing.UP);
		}
	}

	private boolean IsValidBlockPos(final BlockPos p_Pos) {
		IBlockState l_State = mc.world.getBlockState(p_Pos);

		if (l_State.getBlock() instanceof BlockTallGrass || l_State.getBlock() instanceof BlockDoublePlant || l_State.getBlock() instanceof BlockCrops)
			return true;

		if (Zamorozka.settingsManager.getSettingByName("Flowers").getValBoolean() && l_State.getBlock() instanceof BlockFlower)
			return true;

		return false;
	}
}