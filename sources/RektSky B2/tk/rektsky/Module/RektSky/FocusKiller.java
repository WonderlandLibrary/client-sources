package tk.rektsky.Module.RektSky;

import tk.rektsky.Event.*;
import tk.rektsky.Event.Events.*;
import tk.rektsky.Module.Combat.*;
import tk.rektsky.Module.*;
import tk.rektsky.Utils.Entity.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class FocusKiller extends Module
{
    public String targetName;
    public boolean sentToTarget;
    public String[] focusKillerMessages;
    
    public FocusKiller() {
        super("FocusKiller", "Let your target could not focus on fighting", Category.REKTSKY);
        this.targetName = "";
        this.sentToTarget = false;
        this.focusKillerMessages = new String[] { "YetYetYetYetYetYetYetYetYetYetYetYetYetYetYetYet", "LOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOLOL", "TEKTEKTEKTEKTEKTEKTEKTEKTEKTEKTEKTEKTEKTEKTEKTEK", "OUCHOUCHOUCHOUCHOUCHOUCHOUCHOUCHOUCHOUCHOUCHOUCH", "OOPS OOPS OOPS OOPS OOPS OOPS OOPS OOPS OOPS OOPS", "RekkedRekkedRekkedRekkedRekkedRekkedRekkedRekkedRekked", "YOU WASTED 2 SECONDS LOLOLOLOLOLOLOLOLOLOLOLOLOLOLOL", "GAMING CHAIRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR", "OOOOOOOOOOOOOOO YAAAAAAAAAAAAAAAAAAAAAAAAA", "HHAAAAIIIIIIIIIIIIIIIIIII YYYYYYYAAAAAAAAAAAAAAAAA", "Uncle roger thinks you need to buy a rice cooker", "uvuvwevwevwe onyentyetyetye ugwemubwim osassss", "Tachanka, will always, stand  by  you", "LLLLLMMMMMMMMMGGGGGGGGG MOUNTTTTTTTTTTTTTTTED", "SURPRISE MDFK SURPRISE MDFK SURPRISE MDFK SURPRISE MDFK" };
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof WorldTickEvent) {
            final KillAura killAura = (KillAura)ModulesManager.getModuleByClass(KillAura.class);
            if (killAura.t != null && this.enabledTicks % 4 == 0 && EntityUtil.getEntitiesWithAntiBot().contains(killAura.t) && killAura.t instanceof EntityPlayer && this.targetName.equals("")) {
                if (!killAura.attackPost) {
                    killAura.attackPost = true;
                    this.mc.thePlayer.sendChatMessage("/tell " + killAura.t.getName() + " " + this.focusKillerMessages[new Random().nextInt(this.focusKillerMessages.length)]);
                }
                else {
                    this.mc.thePlayer.sendChatMessage("/r " + this.focusKillerMessages[new Random().nextInt(this.focusKillerMessages.length)]);
                }
            }
            if (!this.targetName.equals("")) {
                if (!this.sentToTarget) {
                    this.sentToTarget = true;
                    this.mc.thePlayer.sendChatMessage("/tell " + this.targetName + " " + this.focusKillerMessages[new Random().nextInt(this.focusKillerMessages.length)]);
                }
                else {
                    this.mc.thePlayer.sendChatMessage("/r " + this.focusKillerMessages[new Random().nextInt(this.focusKillerMessages.length)]);
                }
            }
            if (killAura.t == null && killAura.attackPost) {
                killAura.attackPost = false;
            }
        }
    }
}
