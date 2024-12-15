package com.alan.clients.command.impl;

import com.alan.clients.Client;
import com.alan.clients.command.Command;
import com.alan.clients.script.ScriptManager;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.file.FileType;
import com.alan.clients.util.file.config.ConfigFile;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class Script extends Command {

    public Script() {
        super("command.script.description", "script", "scripts", "js");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length < 2) {
            error("Valid actions are load, reload, unload, disablesecurity, folder and enablesecurity");
            return;
        }

        final String action = args[1].toLowerCase(Locale.getDefault());

        final ScriptManager scriptManager = Client.INSTANCE.getScriptManager();

        final com.alan.clients.script.Script script;
        if (args.length > 3) {
            script = scriptManager.getScript(args[2]);
            if (script == null) {
                ChatUtil.display("command.script.notfound", args[2]);
                return;
            }
        } else script = null;

        try {
            switch (action) {
                case "load": {
                    if (script == null) scriptManager.loadScripts();
                    else script.load();
                    break;
                }

                case "reload": {
                    Client.INSTANCE.getScriptManager().reloadScripts();
                    Client.INSTANCE.getClickGUI().moduleList = new ConcurrentLinkedQueue<>();
                    break;
                }

                case "unload": {
                    if (script == null) scriptManager.unloadScripts();
                    else script.unload();
                    break;
                }

                case "disablesecurity": {
                    scriptManager.setSecurityMeasures(false);
                    ChatUtil.display("command.script.disablesecurity");
                    return;
                }

                case "enablesecurity": {
                    scriptManager.setSecurityMeasures(true);
                    ChatUtil.display("command.script.enablesecurity");
                    return;
                }

                case "download":
                case "install":
                    new Thread(() -> {
                        try {
                            final HttpURLConnection connection =
                                    (HttpURLConnection) new URL("http://165.22.2.247/getscript?id=" + args[2])
                                            .openConnection();

                            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                            connection.addRequestProperty("rise", ">astolfo");
                            final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                            String current;
                            final ArrayList<String> response = new ArrayList<>();

                            while ((current = in.readLine()) != null) {
                                response.add(current);
                            }

                            ConfigFile configFile = new ConfigFile(new File(ScriptManager.SCRIPT_DIRECTORY + File.separator + args[2] + ".js"), FileType.CONFIG, args[2]);
                            configFile.forceWrite(ScriptManager.SCRIPT_DIRECTORY + File.separator + args[2] + ".js", response);
                            ChatUtil.display("Installed the Script!");
                            ChatUtil.display("Use .script reload, to reload scripts");

                        } catch (final Throwable t) {
                            t.printStackTrace();
                        }
                    }).start();
                    return;

                case "folder": {
                    try {
                        Desktop desktop = Desktop.getDesktop();
                        File dirToOpen = new File(String.valueOf(ScriptManager.SCRIPT_DIRECTORY));
                        desktop.open(dirToOpen);
                        ChatUtil.display("command.script.folder");
                    } catch (IllegalArgumentException | IOException iae) {
                        ChatUtil.display("command.script.notfoundfolder");
                    }
                    return;
                }

                default: {
                    ChatUtil.display("command.script.unknownaction", "Valid actions are load, reload, unload, disablesecurity and enablesecurity");
                    return;
                }
            }

            ChatUtil.display(
                    "Successfully " + action + "ed "
                            + (script == null ? "all scripts" : "\"" + script.getName() + "\"")
                            + "."
            );
        } catch (final Exception ex) {
            ex.printStackTrace();
            ChatUtil.display("Failed to " + action + " a script. Stacktrace printed.");
        }
    }
}
