package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Misc.EventChat;
import gq.vapu.czfclient.API.Events.Render.EventRender2D;
import gq.vapu.czfclient.API.Events.World.EventTick;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.Helper;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModCheck extends Module {

    private final String[] modlist = new String[]{"小阿狸", "造化钟神秀", "Owenkill", "chen_duxiu", "绅士龙", "mxu", "startover_", "chen_xixi",
            "tanker_01", "SnowDay"};
    private String modname;
    private final TimerUtil timer = new TimerUtil();
    private final ArrayList<String> offlinemod = new ArrayList();
    private final ArrayList<String> onlinemod = new ArrayList();
    private final Option<Boolean> showOffline = new Option<Boolean>("ShowOffline", "ShowOffline", true);
    private final Option<Boolean> showOnline = new Option<Boolean>("ShowOnline", "ShowOnline", true);
    private int counter;
    private boolean isFinished;

    public ModCheck() {
        super("ModCheck", new String[]{"ModCheck", "ModCheck"}, ModuleType.Player);
        this.addValues(this.showOffline, this.showOnline);
    }

    @Override
    public void onEnable() {
        if (mc.isSingleplayer()) {
            Helper.sendMessage("ModCheck is not work in Singleplayer Game!");
        }
    }

    @EventHandler
    public void onRender(EventRender2D e) {
        FontRenderer font = mc.fontRendererObj;
        List<String> listArray = Arrays.asList(modlist);
        listArray.sort((o1, o2) -> {
            return font.getStringWidth(o2) - font.getStringWidth(o1);
        });
        int counter2 = 0;
        for (String mods : listArray) {
            if (offlinemod.contains(mods) && showOffline.getValue()) {
                font.drawStringWithShadow(mods, 3, 80 + counter2 * 10, Color.RED.getRGB());
                counter2++;
            }
            if (onlinemod.contains(mods) && showOnline.getValue()) {
                font.drawStringWithShadow(mods, 3, 80 + counter2 * 10, Color.GREEN.getRGB());
                counter2++;
            }

        }
    }

    @EventHandler
    public void onChat(EventChat e) {
        if (e.getMessage().contains("这名玩家不在线！")) {
            e.setCancelled(true);
            if (onlinemod.contains(modname)) {
                onlinemod.remove(modname);
                offlinemod.add(modname);
                return;
            }
            if (!offlinemod.contains(modname)) {
                offlinemod.add(modname);
            }
        }
        if (e.getMessage().contains("未知指令。请使用/help来查看指令列表。")) {
            e.setCancelled(true);
            if (onlinemod.contains(modname)) {
                onlinemod.remove(modname);
                offlinemod.add(modname);
                return;
            }
            if (!offlinemod.contains(modname)) {
                offlinemod.add(modname);
            }
        }
        if (e.getMessage().contains("You cannot message this player.")) {
            e.setCancelled(true);
            if (offlinemod.contains(modname)) {
                offlinemod.remove(modname);
                onlinemod.add(modname);
                return;
            }
            if (!onlinemod.contains(modname)) {
                onlinemod.add(modname);
            }
        }
        if (e.getMessage().contains("将与玩家 [客服] " + modname + " 进行为时 5 分钟的聊天。使用指令 /chat a 结束聊天")) {
            e.setCancelled(true);
            if (offlinemod.contains(modname)) {
                offlinemod.remove(modname);
                onlinemod.add(modname);
                return;
            }
            if (!onlinemod.contains(modname)) {
                onlinemod.add(modname);
            }
            mc.thePlayer.sendChatMessage("/chat a");
        }
        if (e.getMessage().contains("找不到名为\"" + modname + "\" 的玩家")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTick(EventTick e) {
        if (timer.hasReached(isFinished ? 10000L : 5500L)) {
            if (counter >= modlist.length) {
                counter = -1;
                if (!isFinished) {
                    isFinished = true;
                }

            }
            counter += 1;
            modname = modlist[counter];
            mc.thePlayer.sendChatMessage("/message " + modname);
            timer.reset();
        }
    }
}