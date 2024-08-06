package com.shroomclient.shroomclientnextgen.commands;

import com.shroomclient.shroomclientnextgen.ShroomClient;
import com.shroomclient.shroomclientnextgen.annotations.AlwaysPost;
import com.shroomclient.shroomclientnextgen.annotations.RegisterListeners;
import com.shroomclient.shroomclientnextgen.auth.MSAuth;
import com.shroomclient.shroomclientnextgen.auth.SessionManager;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.mixin.ChatMessageC2SPacketAccessor;
import com.shroomclient.shroomclientnextgen.modules.KeybindManager;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.client.ClickGUI;
import com.shroomclient.shroomclientnextgen.modules.impl.client.Notifications;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.ChatUtil;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import net.minecraft.client.session.Session;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

@RegisterListeners
public class CommandManager {

    @AlwaysPost
    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send.Pre e) {
        if (e.getPacket() instanceof ChatMessageC2SPacket p) {
            String m = p.chatMessage();
            if (m.startsWith(".")) {
                if (m.startsWith("..")) {
                    ((ChatMessageC2SPacketAccessor) e.getPacket()).setChatMessage(
                            m.substring(1)
                        );
                } else {
                    e.cancel();
                    handleMessage(m);
                }
            }
        }
    }

    // TODO Annotation based command registering
    private void handleMessage(String m) {
        String command = m.substring(1).toLowerCase();
        String[] commandSplits = command.split(" ");
        if (
            command.startsWith("clickgui") ||
            command.startsWith("cgui") ||
            command.startsWith("gui") ||
            command.startsWith("g")
        ) {
            ModuleManager.getModule(ClickGUI.class).setEnabled(true, false);
        } else if (
            command.startsWith("bind") && !command.startsWith("bind list")
        ) {
            if (commandSplits.length < 2) return;

            String q = commandSplits[1];
            ArrayList<ModuleManager.ModuleWithInfo> theMods =
                ModuleManager.getModules();
            List<String> mods = theMods
                .stream()
                .map(mod -> mod.an().name())
                .collect(Collectors.toList());
            Optional<ExtractedResult> best = FuzzySearch.extractAll(q, mods, 40)
                .stream()
                .max(Comparator.comparingInt(ExtractedResult::getScore));
            if (best.isEmpty()) {
                Notifications.notify(
                    q + " doesn't exist",
                    ThemeUtil.themeColors()[0],
                    2
                );
                return;
            }
            ExtractedResult peak = best.get();
            ModuleManager.ModuleWithInfo keyM = theMods.get(peak.getIndex());
            try {
                if (commandSplits.length == 2) {
                    new Timer()
                        .schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    KeybindManager.listenForBind(
                                        (Class<? extends Module>) keyM.clazz()
                                    );
                                    Notifications.notify(
                                        "Press to bind " +
                                        ModuleManager.getModuleWithInfo(
                                            ModuleManager.getModule(
                                                (Class<
                                                        ? extends Module
                                                    >) keyM.clazz()
                                            )
                                        )
                                            .an()
                                            .name(),
                                        ThemeUtil.themeColors()[0],
                                        0
                                    );
                                }
                            },
                            200
                        ); // Some delay because otherwise we bind to enter :sob:
                } else {
                    String key = commandSplits[2];
                    if (key.toLowerCase().startsWith("none")) {
                        KeybindManager.unBind(
                            (Class<? extends Module>) keyM.clazz(),
                            true
                        );
                        Notifications.notify(
                            "Unbound " +
                            ModuleManager.getModuleWithInfo(
                                ModuleManager.getModule(
                                    (Class<? extends Module>) keyM.clazz()
                                )
                            )
                                .an()
                                .name(),
                            ThemeUtil.themeColors()[0],
                            1
                        );
                    } else {
                        // batmans old thing tried to read the text as an int...? thats NOT working.
                        KeybindManager.bind(
                            Character.getNumericValue(key.charAt(0)) + 55,
                            (Class<? extends Module>) keyM.clazz(),
                            true
                        );
                    }
                }
            } catch (Exception ignored) {}
        } else if (command.startsWith("bind list")) {
            StringBuilder b = new StringBuilder();
            for (int key : KeybindManager.getKeyBinds().keySet()) {
                ArrayList<Class<? extends Module>> t =
                    KeybindManager.getKeyBinds().get(key);
                for (Class<? extends Module> clazz : t) {
                    String name = ModuleManager.getModuleWithInfo(
                        ModuleManager.getModule(clazz)
                    )
                        .an()
                        .name();
                    b.append(name);
                    b.append(" - ");
                    b.append((char) key);
                    b.append("\n");
                }
            }
            ChatUtil.sendPrefixMessage(" §4keybinds:§f\n" + b);
        } else if (command.startsWith("config")) {
            if (commandSplits.length >= 2) {
                if (commandSplits[1].equals("save")) {
                    if (commandSplits.length >= 3) {
                        ModuleManager.removeOldConfig();

                        ShroomClient.selectedConfig = commandSplits[2] +
                        ".mushroom";

                        ModuleManager.configsFile = new File(
                            ModuleManager.configFileBaseString +
                            "\\" +
                            ShroomClient.selectedConfig
                        );

                        ModuleManager.save();

                        Notifications.notify(
                            "New Config: " + commandSplits[2],
                            ThemeUtil.themeColors()[0],
                            1
                        );
                    } else {
                        ChatUtil.sendPrefixMessage(".config save [name]");
                    }
                } else if (commandSplits[1].equals("folder")) {
                    try {
                        if (
                            !GraphicsEnvironment.isHeadless() &&
                            Desktop.isDesktopSupported()
                        ) {
                            Desktop.getDesktop()
                                .open(new File(C.mc.runDirectory.getPath()));
                            ChatUtil.sendPrefixMessage(
                                "Config Folder Opened Using Desktop"
                            );
                        } else {
                            Runtime.getRuntime()
                                .exec(
                                    "explorer " +
                                    ModuleManager.configFileBaseString
                                );
                            ChatUtil.sendPrefixMessage(
                                "Config Folder Opened Using CMD"
                            );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ChatUtil.sendPrefixMessage(
                            "Failed To Open.\n" +
                            Arrays.toString(e.getStackTrace())
                        );
                    }
                }
            } else {
                ChatUtil.sendPrefixMessage(
                    """
                     §9.config save [name] §3To Save Your Config
                     §9.config folder §3To Open Config Folder
                    """
                );
            }
        } else if (command.startsWith("name") || command.startsWith("ign")) {
            if (C.p() == null) return;
            ChatUtil.sendPrefixMessage(RenderUtil.getUnhiddenName());
        } else if (command.startsWith("ssid")) {
            if (commandSplits.length >= 2) {
                String token = commandSplits[1];
                if (
                    SessionManager.sessionOverride == null ||
                    !SessionManager.sessionOverride
                        .getAccessToken()
                        .equals(token)
                ) {
                    try {
                        MSAuth.UUIDUsername pf = MSAuth.getProfile(token);
                        SessionManager.sessionOverride = new Session(
                            pf.username,
                            UUID.fromString(
                                pf.uuid.replaceFirst(
                                    "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                                    "$1-$2-$3-$4-$5"
                                )
                            ),
                            token,
                            Optional.empty(),
                            Optional.empty(),
                            Session.AccountType.MOJANG
                        );
                        ChatUtil.sendPrefixMessage(
                            "Logged in with SSID, disconnect from your server and reconnect for it to apply."
                        );
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        ChatUtil.sendPrefixMessage("Failed to login with SSID");
                    }
                }
            }
        } else {
            ChatUtil.sendPrefixMessage(
                """

                 §4help
                §9.gui §3To Open The Click GUI
                §9.bind [module] key §3To Bind A Module To A Set key
                §9.bind [module] *enter* *key* §3To Bind A Module
                §9.bind list §3To View Keybinds
                §9.config save [name] §3To Save Your Config
                §9.config folder §3To Open Config Folder
                §9.ign | .name §3To See Your IGN
                """
            );
        }
    }
}
