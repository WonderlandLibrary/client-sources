package gq.vapu.czfclient.Module.Modules.Misc;


import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Misc.EventChat;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Blatant.Killaura;
import gq.vapu.czfclient.Util.Helper;

import java.awt.*;
import java.util.Random;

public class WatchdogReporter extends Module {
    public WatchdogReporter() {
        super("SuperReporter", new String[]{"wdr, wder"}, ModuleType.Player);
        this.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
        this.setSuffix("Watchdog");

    }

    public static String getRandomString(double d) {
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < d; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    @EventHandler
    private void onChat(EventChat e) {
        if (
                Killaura.Target.getName() != null &&
                        e.getMessage().contains("被" + mc.thePlayer.getName() + "击杀") ||
                        e.getMessage().contains("被" + mc.thePlayer.getName() + "扔下了虚空") ||
                        e.getMessage().contains(" 被击杀，击杀者： " + mc.thePlayer.getName()) ||
                        e.getMessage().contains(" 被扔下悬崖，击杀者： " + mc.thePlayer.getName()) ||
                        e.getMessage().contains(" 被扔下虚空，击杀者： " + mc.thePlayer.getName())) {
            e.setCancelled(false);
            mc.thePlayer.sendChatMessage("/wdr " + Killaura.Target.getName() + " killaura autoclick speed fly reach");
        }
        if (e.getMessage().contains("[WATCHDOG CHEAT DETECTION]")) {
//            mc.thePlayer.sendChatMessage("有大主播开纪被看门狗咬了，全体注意！");
            Helper.mc.thePlayer.sendChatMessage("Hacker L");
        }
    }
}
