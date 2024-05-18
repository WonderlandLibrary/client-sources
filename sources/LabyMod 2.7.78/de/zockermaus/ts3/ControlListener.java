package de.zockermaus.ts3;

public interface ControlListener
{
    void onPokeRecieved(TeamSpeakUser var1, String var2);

    void onClientDisconnected(TeamSpeakUser var1, String var2);

    void onClientTimout(TeamSpeakUser var1);

    void onClientConnect(TeamSpeakUser var1);

    void onMessageRecieved(TeamSpeakUser var1, TeamSpeakUser var2, String var3);

    void onClientStartTyping(TeamSpeakUser var1);

    void onDisconnect();

    void onConnect();

    void onChannelMessageRecieved(TeamSpeakUser var1, String var2);

    void onServerMessageRecieved(TeamSpeakUser var1, String var2);

    void onError(int var1, String var2);
}
