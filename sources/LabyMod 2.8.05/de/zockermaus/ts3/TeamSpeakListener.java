package de.zockermaus.ts3;

import de.labystudio.chat.EnumAlertType;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;

public class TeamSpeakListener implements ControlListener
{
    public void onPokeRecieved(TeamSpeakUser user, String pokeMessage)
    {
        if (user != null)
        {
            String s = Color.cl("9") + user.getNickName() + Color.cl("a") + " pokes you.";

            if (!pokeMessage.isEmpty())
            {
                s = Color.cl("9") + user.getNickName() + Color.cl("a") + " pokes you: " + pokeMessage;
            }

            TeamSpeak.infoAll(s);

            if (ConfigManager.settings.teamSpeakAlertTypeChat)
            {
                if (ConfigManager.settings.alertsTeamSpeak)
                {
                    LabyMod.getInstance().displayMessageInChat(TeamSpeak.chatPrefix + s);
                }
            }
            else
            {
                LabyMod.getInstance().achievementGui.displayBroadcast(user.getNickName(), TeamSpeak.url(pokeMessage), EnumAlertType.TEAMSPEAK);
            }

            TeamSpeak.overlayWindows.openInfo(user.getClientId(), "You Have Been Poked", s);
        }
    }

    public void onClientDisconnected(TeamSpeakUser user, String reason)
    {
        if (user != null)
        {
            TeamSpeak.addChat((TeamSpeakUser)null, (TeamSpeakUser)null, Color.cl("9") + user.getNickName() + Color.cl("7") + " disconnected from the Server (" + reason + ")", EnumTargetMode.SERVER);

            for (Chat chat : TeamSpeak.chats)
            {
                if (chat.getChatOwner() != null && chat.getTargetMode() == EnumTargetMode.USER && chat.getChatOwner().getClientId() == user.getClientId())
                {
                    chat.addMessage((TeamSpeakUser)null, Color.cl("7") + "Your chat partner has disconnected.");
                }
            }
        }
    }

    public void onClientTimout(TeamSpeakUser user)
    {
        if (user != null)
        {
            TeamSpeak.addChat((TeamSpeakUser)null, (TeamSpeakUser)null, Color.cl("9") + user.getNickName() + Color.cl("7") + " timed out.", EnumTargetMode.SERVER);
        }
    }

    public void onClientConnect(TeamSpeakUser user)
    {
        TeamSpeak.addChat((TeamSpeakUser)null, (TeamSpeakUser)null, Color.cl("9") + user.getNickName() + Color.cl("7") + " connected to the Server.", EnumTargetMode.SERVER);
    }

    public void onMessageRecieved(TeamSpeakUser target, TeamSpeakUser user, String message)
    {
        if (user != null && target != null)
        {
            TeamSpeak.addChat(target, user, message, EnumTargetMode.USER);

            if (TeamSpeakController.getInstance().me.getClientId() != user.getClientId())
            {
                if (ConfigManager.settings.teamSpeakAlertTypeChat)
                {
                    if (ConfigManager.settings.alertsTeamSpeak)
                    {
                        LabyMod.getInstance().displayMessageInChat(TeamSpeak.chatPrefix + Color.cl("9") + user.getNickName() + Color.cl("7") + ": " + TeamSpeak.colors(TeamSpeak.url(message)));
                    }
                }
                else
                {
                    LabyMod.getInstance().achievementGui.displayBroadcast(user.getNickName(), TeamSpeak.colors(TeamSpeak.url(message)), EnumAlertType.TEAMSPEAK);
                }
            }
        }
    }

    public void onChannelMessageRecieved(TeamSpeakUser user, String message)
    {
        TeamSpeak.addChat((TeamSpeakUser)null, user, message, EnumTargetMode.CHANNEL);

        if (TeamSpeakController.getInstance().me != null && TeamSpeakController.getInstance().me.getClientId() != user.getClientId())
        {
            if (ConfigManager.settings.teamSpeakAlertTypeChat)
            {
                if (ConfigManager.settings.alertsTeamSpeak)
                {
                    LabyMod.getInstance().displayMessageInChat(TeamSpeak.chatPrefix + Color.cl("9") + user.getNickName() + Color.cl("7") + ": " + TeamSpeak.colors(TeamSpeak.url(message)));
                }
            }
            else
            {
                LabyMod.getInstance().achievementGui.displayBroadcast(user.getNickName(), TeamSpeak.colors(TeamSpeak.url(message)), EnumAlertType.TEAMSPEAK);
            }
        }
    }

    public void onServerMessageRecieved(TeamSpeakUser user, String message)
    {
        TeamSpeak.addChat((TeamSpeakUser)null, user, message, EnumTargetMode.SERVER);
    }

    public void onClientStartTyping(TeamSpeakUser user)
    {
    }

    public void onDisconnect()
    {
    }

    public void onConnect()
    {
        TeamSpeak.scrollChannel = 0;
    }

    public void onError(int errorId, String errorMessage)
    {
        String s = Color.cl("c") + TeamSpeak.fix(errorMessage) + " (Error " + errorId + ")";
        TeamSpeak.error(s);
    }
}
