package net.minecraft.client.main.neptune.Mod.Collection.Cmds;

import net.minecraft.client.main.neptune.Mod.Cmd;
import net.minecraft.client.main.neptune.Mod.Cmd.Info;
import net.minecraft.client.main.neptune.Utils.MathUtils;

@Info(name = "vclip", syntax = { "<height>" }, help = "TPs you up or down depending on <height>")
public class VClipCmd extends Cmd {
	@Override
	public void execute(String[] p0) throws Error {
		if (p0.length > 1) {
			this.syntaxError();
		} else if (p0.length == 1) {
			if (MathUtils.isDouble(p0[0])) {
				this.mc.thePlayer.setPosition(this.mc.thePlayer.posX,
						this.mc.thePlayer.posY + Double.parseDouble(p0[0]), this.mc.thePlayer.posZ);
			} else {
				this.syntaxError("<height> must be a valid integer!");
			}
		} else {
			this.syntaxError();
		}
	}
}
