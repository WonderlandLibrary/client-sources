package me.valk.agway.modules.movement;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import me.valk.agway.modes.*;
import me.valk.agway.modes.speed.Bhop;
import me.valk.agway.modes.speed.Gcheat;
import me.valk.agway.modes.speed.Guardian;
import me.valk.agway.modes.speed.JanitorMode;
import me.valk.agway.modes.speed.NCPMode;
import me.valk.agway.modes.speed.SlowHop;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;

public class SpeedMod extends Module {

	public SpeedMod(){
		super(new ModData("Speed", Keyboard.KEY_H, new Color(40, 255, 10)), ModType.MOVEMENT);

        addMode(new Guardian(this));
        addMode(new SlowHop(this));		
		addMode(new JanitorMode(this));
        addMode(new Bhop(this));
		addMode(new NCPMode(this));
		addMode(new Gcheat(this));
	}

}
