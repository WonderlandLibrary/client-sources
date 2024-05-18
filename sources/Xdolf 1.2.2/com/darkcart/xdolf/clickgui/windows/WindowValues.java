package com.darkcart.xdolf.clickgui.windows;

import com.darkcart.xdolf.clickgui.elements.XdolfWindow;
import com.darkcart.xdolf.mods.aura.AutoLog;
import com.darkcart.xdolf.mods.aura.CrystalAura;
import com.darkcart.xdolf.mods.aura.CrystalLog;
import com.darkcart.xdolf.mods.aura.KillAura;
import com.darkcart.xdolf.mods.player.AutoEat;
import com.darkcart.xdolf.mods.player.EntitySpeed;
import com.darkcart.xdolf.mods.player.EntityStep;
import com.darkcart.xdolf.mods.player.Flight;
import com.darkcart.xdolf.mods.world.Speedmine;

public class WindowValues extends XdolfWindow
{
	public WindowValues()
	{
		super("Values", 2, 2);
		addSlider(Flight.flySpeed, 0.1F, 10F, false).setValue(1.0F);
		addSlider(EntitySpeed.entitySpeed, 0.1F, 3.86F, false).setValue(3.00F);
		addSlider(EntityStep.entityStep, 1F, 256F, true).setValue(2.00F);
		addSlider(KillAura.auraRange, 3.0F, 10.0F, false).setValue(3.75F);
		addSlider(CrystalAura.crystalSpeed, 1.0F, 20.0F, true).setValue(8.0F);
		addSlider(CrystalAura.crystalRange, 3.0F, 10.0F, false).setValue(3.75F);
		addSlider(AutoLog.pvpHealthFactor, 1.00F, 19F, true).setValue(6.00F);
		addSlider(CrystalLog.distance, 1, 10, true).setValue(2);
		addSlider(AutoEat.hungerFactor, 0.00F, 19F, true).setValue(7.00F);
		addSlider(Speedmine.mineSpeed, 0.1F, 1F, false).setValue(0.4F);
	}
}
