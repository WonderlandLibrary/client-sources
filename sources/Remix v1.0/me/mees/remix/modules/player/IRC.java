package me.mees.remix.modules.player;

import me.satisfactory.base.module.*;
import me.satisfactory.base.*;
import me.satisfactory.base.events.*;
import pw.stamina.causam.scan.method.model.*;
import me.mees.remix.irc.*;

public class IRC extends Module
{
    IrcManager irc;
    
    public IRC() {
        super("IRC", 0, Category.PLAYER);
        this.irc = Base.INSTANCE.ircManager;
    }
    
    @Override
    public void onDisable() {
        IRC.mc.rightClickDelayTimer = 4;
        super.onDisable();
    }
    
    @Override
    public void onToggle() {
        Base.INSTANCE.addIRCMessage("Use '@Message' to chat");
        Base.INSTANCE.addIRCMessage("You will be know as: §3" + Base.INSTANCE.ircManager.getNick());
    }
    
    @Subscriber
    private void onUpdate(final EventPlayerUpdate event) {
        if (Base.INSTANCE.ircManager.newMessages()) {
            Base.INSTANCE.ircManager.getUnreadLines().forEach(irc -> {
                Base.INSTANCE.addIRCMessage(irc.getSender() + ": " + irc.getLine());
                irc.setRead(true);
            });
        }
    }
}
