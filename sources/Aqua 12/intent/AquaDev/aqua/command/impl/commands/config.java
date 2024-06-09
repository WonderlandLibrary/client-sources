// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.command.impl.commands;

import java.util.Iterator;
import java.net.URLConnection;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import intent.AquaDev.aqua.utils.ChatUtil;
import intent.AquaDev.aqua.config.ConfigOnline;
import intent.AquaDev.aqua.config.Config;
import intent.AquaDev.aqua.Aqua;
import net.minecraft.client.Minecraft;
import java.io.File;
import intent.AquaDev.aqua.command.Command;

public class config extends Command
{
    private final File dir;
    
    public config() {
        super("config");
        this.dir = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/configs/");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("load")) {
                StringBuilder s = new StringBuilder();
                for (int i = 1; i < args.length; ++i) {
                    s.append(args[i]);
                }
                s = new StringBuilder(s.toString().trim());
                final Config cfg = new Config(s.toString());
                cfg.load();
            }
            if (args[0].equalsIgnoreCase("onlineload")) {
                StringBuilder s = new StringBuilder();
                for (int i = 1; i < args.length; ++i) {
                    s.append(args[i]);
                }
                s = new StringBuilder(s.toString().trim());
                final ConfigOnline skid = new ConfigOnline();
                skid.loadConfigOnline(s.toString());
            }
            if (args[0].equalsIgnoreCase("save")) {
                StringBuilder s = new StringBuilder();
                for (int i = 1; i < args.length; ++i) {
                    s.append(args[i]);
                }
                s = new StringBuilder(s.toString().trim());
                final Config cfg = new Config(s.toString());
                cfg.saveCurrent();
            }
            if (args[0].equalsIgnoreCase("onlinelist")) {
                final ArrayList<String> configs;
                URLConnection urlConnection;
                final BufferedReader bufferedReader2;
                BufferedReader bufferedReader;
                String text;
                final Object o;
                final Throwable t2;
                final Iterator<String> iterator;
                String config;
                final Thread thread = new Thread(() -> {
                    ChatUtil.sendChatMessageWithPrefix("Loading...");
                    configs = new ArrayList<String>();
                    try {
                        urlConnection = new URL("https://raw.githubusercontent.com/aquaclient/aquaclient.github.io/main/configs/configs.json").openConnection();
                        urlConnection.setConnectTimeout(10000);
                        urlConnection.connect();
                        new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        bufferedReader = bufferedReader2;
                        try {
                            while (true) {
                                text = bufferedReader.readLine();
                                if (o != null) {
                                    if (text.contains("404: Not Found")) {
                                        ChatUtil.sendChatMessageWithPrefix("An error occurred while loading the configs from Github.");
                                        return;
                                    }
                                    else {
                                        configs.add(text);
                                    }
                                }
                                else {
                                    break;
                                }
                            }
                        }
                        catch (Throwable t) {
                            throw t;
                        }
                        finally {
                            if (bufferedReader != null) {
                                if (t2 != null) {
                                    try {
                                        bufferedReader.close();
                                    }
                                    catch (Throwable exception) {
                                        t2.addSuppressed(exception);
                                    }
                                }
                                else {
                                    bufferedReader.close();
                                }
                            }
                        }
                    }
                    catch (IOException e) {
                        ChatUtil.sendChatMessageWithPrefix("An error occurred while loading the configs from Github.");
                        e.printStackTrace();
                    }
                    configs.iterator();
                    while (iterator.hasNext()) {
                        config = iterator.next();
                        ChatUtil.sendChatMessageWithPrefix(config);
                    }
                    return;
                });
                thread.start();
            }
            if (args[0].equalsIgnoreCase("list")) {
                try {
                    final File[] files = this.dir.listFiles();
                    String list = "";
                    for (int j = 0; j < files.length; ++j) {
                        list = list + ", " + files[j].getName().replace(".txt", "");
                    }
                    ChatUtil.sendChatMessageWithPrefix("§7Configs: §f" + list.substring(2));
                }
                catch (Exception ex) {}
            }
        }
        else {
            ChatUtil.sendChatMessageWithPrefix("§7config §8<§bload/save/list§8> §8<§bname§8>§f");
        }
    }
}
