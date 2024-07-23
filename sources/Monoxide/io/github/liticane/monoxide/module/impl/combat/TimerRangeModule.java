package io.github.liticane.monoxide.module.impl.combat;

import io.github.liticane.monoxide.module.ModuleManager;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.listener.event.minecraft.game.TimerManipulationEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.math.time.TimeHelper;
import io.github.liticane.monoxide.util.player.combat.FightUtil;

@ModuleData(name = "TimerRange", description = "Manipulates Timer to give you an advantage.", category = ModuleCategory.COMBAT)
public class TimerRangeModule extends Module {

	public final NumberValue<Long> maxBalance = new NumberValue<>("Max Balance", this, 100L, 0L, 5000L, 0);
	public final NumberValue<Long> delay = new NumberValue<>("Delay", this, 2500L, 100L, 10000L, 0);
	public final NumberValue<Float> range = new NumberValue<>("Range", this, 3f, 0.1f,6f, 1);

	private KillAuraModule killAura;
	private long shifted, previousTime;
	private final TimeHelper timeHelper = new TimeHelper();

	@Listen
	public void onTime(TimerManipulationEvent timerManipulationEvent) {
		if(killAura == null)
			killAura = ModuleManager.getInstance().getModule(KillAuraModule.class);

		if(shouldCharge() && this.timeHelper.hasReached(delay.getValue())) {
			shifted += timerManipulationEvent.getTime() - previousTime;
		}

		if(shouldDischarge()) {
			shifted = 0;
			this.timeHelper.reset();
		}

		previousTime = timerManipulationEvent.getTime();
		timerManipulationEvent.setTime(timerManipulationEvent.getTime() - shifted);
	}

	private boolean shouldCharge() {
		return killAura.isEnabled() && KillAuraModule.currentTarget != null && this.shifted < maxBalance.getValue();
	}

	private boolean shouldDischarge() {
		return this.shifted >= this.maxBalance.getValue() && killAura.isEnabled() && KillAuraModule.currentTarget != null && FightUtil.getRange(KillAuraModule.currentTarget) > range.getValue();
	}

	@Override
	public void onDisable() {
		this.shifted = 0;
	}

	@Override
	public void onEnable() {
		this.shifted = 0;
		this.previousTime = (System.nanoTime() / 1000000L) / 1000L;
	}

}
