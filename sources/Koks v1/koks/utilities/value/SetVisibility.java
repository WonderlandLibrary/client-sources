package koks.utilities.value;

import koks.Koks;
import koks.modules.Module;
import koks.modules.impl.combat.KillAura;
import koks.modules.impl.movement.Fly;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 16:43
 */
public class SetVisibility {

    public void setVisibility() {
        for (Module module : Koks.getKoks().moduleManager.getModules()) {
            if (module instanceof KillAura) {
                if(((KillAura) module).targetSettings.isExpanded()){
                    ((KillAura) module).preferTarget.setVisible(!((KillAura) module).targetMode.getSelectedMode().equals("Switch"));
                }
                ((KillAura) module).preAimRange.setVisible(((KillAura) module).preAim.isToggled());

                ((KillAura) module).serverSideSwing.setVisible(((KillAura) module).silentSwing.isToggled());
                ((KillAura) module).swingChance.setVisible(((KillAura) module).silentSwing.isToggled());
                ((KillAura) module).ignoreTeam.setVisible(((KillAura) module).player.isToggled());
                ((KillAura) module).ignoreFriend.setVisible(((KillAura) module).player.isToggled());
            }
            if(module instanceof Fly) {
                ((Fly) module).aac322boost.setVisible(((Fly) module).modeValue.getSelectedMode().equalsIgnoreCase("AAC3.2.2"));
            }
        }
    }

}