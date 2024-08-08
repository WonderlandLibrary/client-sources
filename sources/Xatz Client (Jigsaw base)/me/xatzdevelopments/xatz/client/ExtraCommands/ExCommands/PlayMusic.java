package me.xatzdevelopments.xatz.client.ExtraCommands.ExCommands;

import me.xatzdevelopments.xatz.client.ExtraCommands.ExCommand;
import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.gui.Level;
import me.xatzdevelopments.xatz.gui.Notification;
import me.xatzdevelopments.xatz.utils.Wrapper;

public class PlayMusic extends ExCommand {

    public PlayMusic(){
        super("playmusic", ".play musicname", 0);
    }

    @Override
    public void onCommand(String command, String[] args){
        //Xatz.instance.soundHandler.playSound("stiletostateofmind.wav");
       /* if(Xatz.instance.musicManager.getSongByName(args[0]) == null) {
        	Wrapper.tellPlayer("Song Not Found!");
			Xatz.getNotificationManager().addNotification(new Notification(Level.WARNING, 
					"Do .songs for a list of songs"));
            return;
        }
        Xatz.instance.musicManager.getSongByName(args[0]).play();
		Xatz.getNotificationManager().addNotification(new Notification(Level.INFO, 
				"Aight, playing the song " + args[0]));
		Wrapper.tellPlayer("Playing " + args[0]); */
    	Wrapper.tellPlayer("This command has been removed temporarily, as it was gay with the obfuscation and decided not to work..");
        super.onCommand(command, args);

    }
}
