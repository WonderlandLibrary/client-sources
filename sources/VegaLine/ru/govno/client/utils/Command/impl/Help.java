/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import net.minecraft.client.Minecraft;
import ru.govno.client.Client;
import ru.govno.client.utils.Command.Command;

public class Help
extends Command {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public Help() {
        super("Help", new String[]{"help", "\u0445\u0435\u043b\u043f", "\u043f\u043e\u043c\u043e\u0433\u0438\u0442\u0435"});
    }

    @Override
    public void onCommand(String[] args) {
        if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("\u0445\u0435\u043b\u043f") || args[0].equalsIgnoreCase("\u043f\u043e\u043c\u043e\u0433\u0438\u0442\u0435")) {
            try {
                Client.msg("\u00a76\u00a7lClient\u00a7r\u00a77: \u0412\u0441\u0435 \u043a\u043e\u043c\u043c\u0430\u043d\u0434\u044b \u043a\u043b\u0438\u0435\u043d\u0442\u0430 \u00a7b\u00a7lVegaLine\u00a7r\u00a77:", false);
                Client.msg("\u00a79\u00a7lTeleport:\u00a7r \u00a77teleport: teleport/tport/tp [\u00a7lName\u00a7r\u00a77]", false);
                Client.msg("\u00a79\u00a7lTeleport:\u00a7r \u00a77teleport: teleport/tport/tp [\u00a7lx,y,z/x,z\u00a7r\u00a77]", false);
                Client.msg("\u00a7e\u00a7lMotion:\u00a7r \u00a77vmotion: vmotion/vm [\u00a7ly+\u00a7r\u00a77].", false);
                Client.msg("\u00a7e\u00a7lMotion:\u00a7r \u00a77hmotion: hmotion/hm [\u00a7lh+\u00a7r\u00a77].", false);
                Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77use points: points/point/p/way", false);
                Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77add: add/to/new [\u00a7lname+[x,y,z/x,z]/name/''\u00a7r\u00a77]", false);
                Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77clear all: ci/clear", false);
                Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77list: list/see", false);
                Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77remove: del/remove [\u00a7lname\u00a7r\u00a77]", false);
                Client.msg("\u00a76\u00a7lMacros:\u00a7r\u00a77 \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u044b\u00a7r: \u00a78[\u00a77macros/macro/mc\u00a78].", false);
                Client.msg("\u00a76\u00a7lMacros:\u00a7r\u00a77 \u00a77\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c\u00a7r: \u00a78[\u00a77add/new name key msg\u00a78].", false);
                Client.msg("\u00a76\u00a7lMacros:\u00a7r\u00a77 \u00a77\u0423\u0434\u0430\u043b\u0438\u0442\u044c\u00a7r: \u00a78[\u00a77remove/delete/del [name].", false);
                Client.msg("\u00a76\u00a7lMacros:\u00a7r\u00a77 \u00a77\u041e\u0447\u0438\u0441\u0442\u0438\u0442\u044c\u00a7r: \u00a78[\u00a77clear all/ci\u00a78].", false);
                Client.msg("\u00a76\u00a7lMacros:\u00a7r\u00a77 \u00a77\u0421\u043f\u0438\u0441\u043e\u043a\u00a7r: \u00a78[\u00a77list\u00a78].", false);
                Client.msg("\u00a76\u00a7lMacros:\u00a7r\u00a77 \u00a77\u0422\u0435\u0441\u0442\u00a7r: \u00a78[\u00a77use/test\u00a78].", false);
                Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77add: add/new [\u00a7lNAME\u00a7r\u00a77]", false);
                Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77remove: remove/del [\u00a7lNAME\u00a7r\u00a77]", false);
                Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77clear: clear/ci", false);
                Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77list: list/see", false);
                Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77massage: massage/msg/tell [\u00a7lTEXT / coords\u00a7r\u00a77]", false);
                Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77bind: bind/bnd/b [\u00a7lname\u00a7r\u00a77] [\u00a7lkey\u00a7r\u00a77].", false);
                Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77unbind: unbind/unbnd [\u00a7lname\u00a7r\u00a77 | \u00a7lall\u00a7r\u00a77]", false);
                Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77show binds: list/see", false);
                Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77get bind: get [\u00a7lname\u00a7r\u00a77]", false);
                Client.msg("\u00a7d\u00a7lClip:\u00a7r \u00a77vclip: vclip/vc [\u00a7ly+\u00a7r\u00a77]", false);
                Client.msg("\u00a7d\u00a7lClip:\u00a7r \u00a77up/down/bd", false);
                Client.msg("\u00a7d\u00a7lClip:\u00a7r \u00a77hclip: hclip/hc [\u00a7lh+\u00a7r\u00a77]", false);
                Client.msg("\u00a7d\u00a7lClip:\u00a7r \u00a77dclip: dclip/dc [\u00a7lv+,h+\u00a7r\u00a77]", false);
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u044c: ss/setsetting/module/m", false);
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043f\u0435\u0440\u0435\u0437\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c \u0432\u0441\u0451: reload", false);
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0432\u043a\u043b\u044e\u0447\u0438\u0442\u044c:[\u00a7lNAME\u00a7r\u00a77] true/on/+", false);
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0432\u044b\u043a\u043b\u044e\u0447\u0438\u0442\u044c:[\u00a7lNAME\u00a7r\u00a77] false/off/-", false);
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0441\u043b\u0430\u0439\u0434\u0435\u0440:[\u00a7lNAME\u00a7r\u00a77] [\u00a7lSlider\u00a7r\u00a77] [\u00a7lValue\u00a7r\u00a77]", false);
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0447\u0435\u043a:[\u00a7lNAME\u00a7r\u00a77] [\u00a7lCheck\u00a7r\u00a77] [\u00a7ltrue/+/off/-/toggle/tog\u00a7r\u00a77]", false);
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043c\u043e\u0434\u044b:[\u00a7lNAME\u00a7r\u00a77] [\u00a7lModes\u00a7r\u00a77] [\u00a7lSelected\u00a7r\u00a77]", false);
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0446\u0432\u0435\u0442:[\u00a7lNAME\u00a7r\u00a77] [\u00a7lColor\u00a7r\u00a77] [\u00a7lrgba/rgb/ba/b/int\u00a7r\u00a77]", false);
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u0441\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c: save [\u00a7lNAME\u00a7r\u00a77]", false);
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u0437\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c: load [\u00a7lNAME\u00a7r\u00a77]", false);
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u0434\u043e\u0431\u0430\u0432\u0438\u0442\u044c: add/new [\u00a7lNAME\u00a7r\u00a77]", false);
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u0443\u0434\u0430\u043b\u0438\u0442\u044c: remove/del [\u00a7lNAME\u00a7r\u00a77]", false);
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u043f\u043e\u043a\u0430\u0437\u0430\u0442\u044c \u0432\u0441\u0435: list/see", false);
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u043f\u043e\u043a\u0430\u0437\u0430\u0442\u044c \u043f\u0430\u043f\u043a\u0443: dir/folder", false);
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u043e\u0431\u043d\u0443\u043b\u0438\u0442\u044c: reset/unload", false);
                Client.msg("\u00a77\u00a7lLogin:\u00a7r \u00a77login: login/l [\u00a7lNAME / random\u00a7r\u00a77]", false);
                Client.msg("\u00a77\u00a7lLogin:\u00a7r \u00a77connect: connect/con [\u00a7lIP / IP + NAME\u00a7r\u00a77]", false);
                Client.msg("\u00a79\u00a7lRiding:\u00a7r \u00a77use: entity/ent/riding/ride", false);
                Client.msg("\u00a79\u00a7lRiding:\u00a7r \u00a77desync: desync/des", false);
                Client.msg("\u00a79\u00a7lRiding:\u00a7r \u00a77resync: resync/res", false);
                Client.msg("\u00a79\u00a7lRiding:\u00a7r \u00a77dismount: dismount/dis", false);
                Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77border size: mapsize/mps/size", false);
                Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77biome: biome/bm", false);
                Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77rules: rules/rs", false);
                Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77difficulty: difficulty/dif", false);
                Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77seed: seed/s", false);
                Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77height: height/h", false);
                Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77spawnpoint: spawnpoint/sp", false);
                Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77entities: enities/ents", false);
                Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77system gc: gc", false);
                Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77world type: type/t", false);
                Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77use: ignore", false);
                Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77add: add/new [\u00a7lSTRING\u00a7r\u00a77]", false);
                Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77remove: remove/del [\u00a7lSTRING\u00a7r\u00a77]", false);
                Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77list: list/see", false);
                Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77clear: clear/ci", false);
                Client.msg("\u00a72\u00a7lPanic:\u00a7r \u00a77panic [\u00a7lon/code\u00a7r\u00a77]", false);
                Client.msg("\u00a7f\u00a7lParse:\u00a7r \u00a77write: write/get", false);
                Client.msg("\u00a7f\u00a7lParse:\u00a7r \u00a77print: view/show", false);
                Client.msg("\u00a7f\u00a7lParse:\u00a7r \u00a77open filder: dir/open", false);
                Client.msg("\u00a75\u00a7lGetCommands:\u00a7r \u00a77use: getcommands/getcom/gc", false);
            } catch (Exception formatException) {
                Client.msg("\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", true);
                Client.msg("\u00a77HELP: \u00a7f?\u00a7r\u00a77 .help \u00a7f?\u00a7r", true);
                formatException.printStackTrace();
            }
        }
    }
}

