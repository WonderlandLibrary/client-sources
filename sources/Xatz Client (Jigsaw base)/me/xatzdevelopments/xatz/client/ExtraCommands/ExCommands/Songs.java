package me.xatzdevelopments.xatz.client.ExtraCommands.ExCommands;

import me.xatzdevelopments.xatz.client.ExtraCommands.ExCommand;
import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.gui.Level;
import me.xatzdevelopments.xatz.gui.Notification;
import me.xatzdevelopments.xatz.utils.Wrapper;

public class Songs extends ExCommand {

    public Songs(){
        super("Songs", ".Songs", 0);
    }

    @Override
    public void onCommand(String command, String[] args){
    	Xatz.chatMessage("Current songs that are built in, Closer, SilentPartnerSpaceWalk, TheseDays.");
    	Xatz.chatMessage("(I will add a better song system in the future, don't judge its my first ever client)");
        super.onCommand(command, args);

    }
}
