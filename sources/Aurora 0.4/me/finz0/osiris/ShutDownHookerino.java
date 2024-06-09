package me.finz0.osiris;

public class ShutDownHookerino extends Thread {
    @Override
    public void run(){
        saveConfig();
    }

    public static void saveConfig(){
        AuroraMod.getInstance().configUtils.saveMods();
        AuroraMod.getInstance().configUtils.saveSettingsList();
        AuroraMod.getInstance().configUtils.saveBinds();
        AuroraMod.getInstance().configUtils.saveDrawn();
        AuroraMod.getInstance().configUtils.saveFriends();
        AuroraMod.getInstance().configUtils.saveGui();
        AuroraMod.getInstance().configUtils.savePrefix();
        AuroraMod.getInstance().configUtils.saveRainbow();
        AuroraMod.getInstance().configUtils.saveMacros();
        AuroraMod.getInstance().configUtils.saveMsgs();
        AuroraMod.getInstance().configUtils.saveAutoGG();
        AuroraMod.getInstance().configUtils.saveSpammer();
        AuroraMod.getInstance().configUtils.saveAutoReply();
        AuroraMod.getInstance().configUtils.saveAnnouncer();
        AuroraMod.getInstance().configUtils.saveWaypoints();
        AuroraMod.getInstance().configUtils.saveHudComponents();
        AuroraMod.getInstance().configUtils.saveFont();
        AuroraMod.getInstance().configUtils.saveEnemies();
        AuroraMod.getInstance().configUtils.saveClientname();
    }
}
