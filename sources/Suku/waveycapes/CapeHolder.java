package waveycapes;

import net.minecraft.client.Minecraft;
import waveycapes.sim.StickSimulation;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public interface CapeHolder {
	StickSimulation getSimulation();

	default void updateSimulation(final EntityPlayer abstractClientPlayer, final int partCount) {
		final StickSimulation simulation = this.getSimulation();
		boolean dirty = false;
		if (simulation.points.size() != partCount) {
			simulation.points.clear();
			simulation.sticks.clear();
			for (int i = 0; i < partCount; ++i) {
				final StickSimulation.Point point = new StickSimulation.Point();
				point.position.y = (float) (-i);
				point.locked = (i == 0);
				simulation.points.add(point);
				if (i > 0) {
					simulation.sticks.add(new StickSimulation.Stick(
							(StickSimulation.Point) simulation.points.get(i - 1), point, 1.0f));
				}
			}
			dirty = true;
		}
		if (dirty) {
			for (int i = 0; i < 10; ++i) {
				this.simulate(abstractClientPlayer);
			}
		}
	}

	default void simulate(final EntityPlayer abstractClientPlayer) {
		StickSimulation simulation = getSimulation();

		if (simulation.points.isEmpty()) {
			return;
		}

		simulation.points.get(0).prevPosition.copy(simulation.points.get(0).position);

		double d0 = abstractClientPlayer.prevChasingPosX + (abstractClientPlayer.chasingPosX - abstractClientPlayer.prevChasingPosX) * (double) Minecraft.getInstance().timer.renderPartialTicks - (abstractClientPlayer.prevPosX + (abstractClientPlayer.posX - abstractClientPlayer.prevPosX) * (double) Minecraft.getInstance().timer.renderPartialTicks);
		double d2 = abstractClientPlayer.prevChasingPosZ + (abstractClientPlayer.chasingPosZ - abstractClientPlayer.prevChasingPosZ) * (double) Minecraft.getInstance().timer.renderPartialTicks - (abstractClientPlayer.prevPosZ + (abstractClientPlayer.posZ - abstractClientPlayer.prevPosZ) * (double) Minecraft.getInstance().timer.renderPartialTicks);

		float n = abstractClientPlayer.prevRenderYawOffset + abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset;
		double o = Math.sin(n * 0.017453292F);
		double p = -Math.cos(n * 0.017453292F);

		float heightMul = 16;

		double fallHack = MathHelper.clamp_double((simulation.points.get(0).position.y - (abstractClientPlayer.posY * heightMul)), -0.5, 0.5);

		// Gradually apply the animation updates over multiple frames
		simulation.points.get(0).position.x += (float) ((d0 * o + d2 * p) * 0.18 + fallHack);
		float playerPosY = (float) (abstractClientPlayer.posY * heightMul + (abstractClientPlayer.isSneaking() ? -4 : 0));
		//maybe change idk//176
		simulation.points.get(0).position.y += (float) ((playerPosY - simulation.points.get(0).position.y) * 0.10);

		simulation.simulate();
	}

}
