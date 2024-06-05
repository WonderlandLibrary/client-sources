package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Misc.EventChat;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;

public class AntiSpam extends Module {
    int i;

    public AntiSpam() {
        super("ChatFilter", new String[]{"AntiSpam"}, ModuleType.World);
        this.setSuffix("Times:0");
    }

    @EventHandler
    public void onChat(EventChat e) {
        if (e.getMessage().contains(".com") || e.getMessage().contains(".cn") || e.getMessage().contains(".xyz")
                || e.getMessage().contains(".cf") || e.getMessage().contains("http")
                || e.getMessage().contains("神仙") || e.getMessage().contains("外挂") || e.getMessage().contains("内部")
                || e.getMessage().contains("外部") || e.getMessage().contains("购买") || e.getMessage().contains("加群")
                || e.getMessage().contains("q群") || e.getMessage().contains("外g") || e.getMessage().contains("开挂")
                || e.getMessage().contains("开g") || e.getMessage().contains("配置") || e.getMessage().contains("开端")) {
            e.setCancelled(true);
            i++;
            this.setSuffix("Times:" + i);
        }
    }
}
