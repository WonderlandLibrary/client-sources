package com.darkcart.xdolf.clickgui.windows;

import com.darkcart.xdolf.clickgui.elements.XuluBWindow;
import com.darkcart.xdolf.commands.CmdVClip;
import com.darkcart.xdolf.mods.aura.AutoLog;
import com.darkcart.xdolf.mods.aura.CrystalAura;
import com.darkcart.xdolf.mods.aura.KillAura;
import com.darkcart.xdolf.mods.movement.EntitySpeed;
import com.darkcart.xdolf.mods.movement.Flight;
import com.darkcart.xdolf.mods.movement.entityStep;
import com.darkcart.xdolf.mods.player.AutoEat;
import com.darkcart.xdolf.mods.player.vClip;

public class WindowValues extends XuluBWindow
{
	public WindowValues()
	{
		/**
		 * Added vClip Options, both .vclip and vClip module. Also  Changed "Values" to "Options". [v15]
		 * 		 */
		super("Options", 2, 2);
		addSlider(Flight.flySpeed, 0.1F, 10F, false).setValue(1.0F);
		addSlider(EntitySpeed.entitySpeed, 0.1F, 5.06F, false).setValue(3.00F);
		addSlider(KillAura.auraRange, 3.0F, 10.0F, false).setValue(3.75F);
		addSlider(CrystalAura.crystalSpeed, 1.0F, 50.0F, true).setValue(8.0F);
		addSlider(CrystalAura.crystalRange, 3.0F, 10.0F, false).setValue(3.75F);
		addSlider(CrystalAura.crystalHit, 0.0F, 10.0F, false).setValue(2.75F);
		addSlider(CmdVClip.height, -10.0F, 10.0F, false).setValue(2.75F);
		addSlider(vClip.height, -10.0F, 10.0F, false).setValue(0.75F);
		addSlider(entityStep.entityStep, 1F, 256F, true).setValue(2.00F);
		addSlider(AutoLog.pvpHealthFactor, 1.00F, 19F, true).setValue(6.00F);
		addSlider(AutoEat.hungerFactor, 0.00F, 19F, true).setValue(7.00F);
	}
}