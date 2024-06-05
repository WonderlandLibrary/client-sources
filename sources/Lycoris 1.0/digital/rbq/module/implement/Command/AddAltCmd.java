package digital.rbq.module.implement.Command;

import digital.rbq.Lycoris;
import digital.rbq.gui.altMgr.Alt;
import digital.rbq.gui.altMgr.GuiAltMgr;
import digital.rbq.module.Command;

@Command.Info(name = "addalt", syntax = { "" }, help = "Add alt currently logged into Alt Manager")
public class AddAltCmd extends Command {
	@Override
	public void execute(String[] args) throws Error {
		if(Lycoris.currentAlt != null){
			GuiAltMgr.alts.add(0, new Alt(Lycoris.currentAlt[0], Lycoris.currentAlt[1], mc.getSession().getUsername()));
		}
	}
}
