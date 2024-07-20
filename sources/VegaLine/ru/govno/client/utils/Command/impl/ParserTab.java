/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import org.lwjgl.Sys;
import ru.govno.client.Client;
import ru.govno.client.utils.Command.Command;

public class ParserTab
extends Command {
    Minecraft mc = Minecraft.getMinecraft();
    File parseDirectory;
    private File file;

    public ParserTab() {
        super("ParserTab", new String[]{"parse", "pt", "parsetab"});
        this.parseDirectory = new File(Minecraft.getMinecraft().mcDataDir, "parses");
    }

    List<ParsedName> getPlayerStrings() {
        List infoList = this.mc.getConnection().getPlayerInfoMap().stream().filter(net -> net.getGameProfile() != null).filter(net -> net.getDisplayName() != null).toList();
        ArrayList<ParsedName> parses = new ArrayList<ParsedName>();
        infoList.forEach(net -> {
            if (net != null && net.getGameProfile().getName() != null && net.getDisplayName() != null && net.getPlayerTeam() != null) {
                parses.add(new ParsedName(net.getGameProfile().getName(), net.getDisplayName().getUnformattedText(), net.getPlayerTeam().getColorPrefix()));
            }
        });
        return parses;
    }

    List<String> getTabPrefixTypeCounted() {
        ArrayList<String> prefs = new ArrayList<String>();
        for (ParsedName pn : this.getPlayerStrings()) {
            if (prefs.stream().anyMatch(pref -> pref.equalsIgnoreCase(pn.prefix))) continue;
            prefs.add(pn.prefix);
        }
        return prefs;
    }

    List<ParsedName> getParsesByPrefix(String prefix) {
        return this.getPlayerStrings().stream().filter(pn -> pn.prefix.equalsIgnoreCase(prefix)).collect(Collectors.toList());
    }

    List<ParsedNameGroup> getParsesGroups() {
        ArrayList<ParsedNameGroup> groupParses = new ArrayList<ParsedNameGroup>();
        this.getTabPrefixTypeCounted().forEach(pref -> groupParses.add(new ParsedNameGroup((String)pref, this.getParsesByPrefix((String)pref))));
        return groupParses;
    }

    List<String> getParsesFinalStrings(boolean chat) {
        ArrayList<String> listParses = new ArrayList<String>();
        this.getParsesGroups().forEach(parsed -> {
            String split = " ";
            listParses.add(split);
            String groupName = (String)(parsed.prefix.isEmpty() ? "Players" : parsed.prefix + " `s") + " | count = " + parsed.parse.size();
            listParses.add(groupName);
            int count = 1;
            for (ParsedName parse : parsed.parse) {
                String counter = "\u2116" + count + ": ";
                if (chat) {
                    listParses.add(counter + "\u00a7r\u00a77Name: " + parse.name + "\u00a7r\u00a77|DName: " + parse.displayName + "\u00a7r\u00a77|Pref: " + parse.prefix + "\u00a7r\u00a77");
                } else {
                    listParses.add(counter + "Name: " + parse.name);
                    listParses.add(counter + "DisplayName: " + parse.displayName);
                    listParses.add(counter + "Prefix: " + parse.displayName);
                }
                ++count;
            }
        });
        return listParses;
    }

    public JsonObject getJsonParses(String ip) {
        JsonObject object = new JsonObject();
        object.addProperty("Parses:", "");
        this.getParsesFinalStrings(false).forEach(str -> object.addProperty((String)str, ""));
        return object;
    }

    void writeFile() {
        this.parseDirectory = new File(Minecraft.getMinecraft().mcDataDir, "parses");
        String ip = "(" + (this.mc.isSingleplayer() ? "single" : (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().serverIP != null ? this.mc.getCurrentServerData().serverIP : "unknown")) + ")";
        this.file = new File(this.parseDirectory, "parse" + ip.replace(".", "_") + ".txt");
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (Exception exception) {
                // empty catch block
            }
        }
        String contentPrettyPrint = new GsonBuilder().setPrettyPrinting().create().toJson(this.getJsonParses(ip));
        try {
            FileWriter writer = new FileWriter(this.file);
            writer.write(contentPrettyPrint);
            writer.close();
        } catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    public void onCommand(String[] args) {
        try {
            List<String> parses;
            if (args[1].equalsIgnoreCase("write") || args[1].equalsIgnoreCase("get")) {
                parses = this.getParsesFinalStrings(false);
                if (parses.isEmpty()) {
                    Client.msg("\u00a7f\u00a7lParse:\u00a7r \u00a77\u041b\u0438\u0441\u0442 \u043f\u0430\u0440\u0441\u043e\u0432 \u043f\u0443\u0441\u0442.", false);
                    return;
                }
                this.writeFile();
                Client.msg("\u00a7f\u00a7lParse:\u00a7r \u00a77\u041b\u0438\u0441\u0442 \u043f\u0430\u0440\u0441\u043e\u0432 \u0437\u0430\u043f\u0438\u0441\u0430\u043d \u0432 \u0444\u0430\u0439\u043b.", false);
            }
            if (args[1].equalsIgnoreCase("view") || args[1].equalsIgnoreCase("show")) {
                parses = this.getParsesFinalStrings(true);
                Client.msg("\u00a7f\u00a7lParse:\u00a7r \u00a77\u041b\u0438\u0441\u0442 \u043f\u0430\u0440\u0441\u043e\u0432" + (parses.isEmpty() ? " \u043f\u0443\u0441\u0442." : " \u0442\u0435\u043a\u0443\u0449\u0435\u0433\u043e \u0441\u0435\u0440\u0432\u0435\u0440\u0430:"), false);
                parses.forEach(str -> Client.msg(str, false));
            }
            if (args[1].equalsIgnoreCase("dir") || args[1].equalsIgnoreCase("open")) {
                Client.msg("\u00a7f\u00a7lParse:\u00a7r \u00a77\u041e\u0442\u043a\u0440\u044b\u0432\u0430\u044e \u043f\u0430\u043f\u043a\u0443 \u043f\u0430\u0440\u0441\u043e\u0432.", false);
                Sys.openURL(this.file.getAbsolutePath());
            }
        } catch (Exception formatException) {
            Client.msg("\u00a7f\u00a7lParse:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
            Client.msg("\u00a7f\u00a7lParse:\u00a7r \u00a77write: write/get", false);
            Client.msg("\u00a7f\u00a7lParse:\u00a7r \u00a77print: view/show", false);
            Client.msg("\u00a7f\u00a7lParse:\u00a7r \u00a77open filder: dir/open", false);
        }
    }

    class ParsedName {
        String name;
        String displayName;
        String prefix;

        ParsedName(String name, String displayName, String prefix) {
            this.name = name;
            this.displayName = displayName;
            this.prefix = prefix;
        }
    }

    class ParsedNameGroup {
        String prefix;
        List<ParsedName> parse;

        ParsedNameGroup(String prefix, List<ParsedName> parse) {
            this.prefix = prefix;
            this.parse = parse;
        }
    }
}

