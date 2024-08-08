package me.zeroeightsix.kami.module.modules.chat;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ChatTextUtils;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.*;

import static me.zeroeightsix.kami.util.ChatTextUtils.generateRandomHexSuffix;
import static me.zeroeightsix.kami.util.FileHelper.readTextFileAllLines;

/**
 * Created by hub on 15 June 2019
 * Updated 23 November 2019 by hub
 */
@Module.Info(name = "Spammer", category = Module.Category.CHAT, description = "SPAM")
public class Spammer extends Module {

    private static final String fileName = "Hephaestus_Spammer.txt";
    private static final String defaultMessage = "Join 0b0t.org - Worlds oldest Minecraft Server!";

    private static List<String> spamMessages = new ArrayList();
    private static Random rnd = new Random();
    private static Timer timer;
    private static TimerTask task;

    private Setting<Boolean> random = register(Settings.b("Random", false));
    private Setting<Boolean> greentext = register(Settings.b("Greentext", false));
    private Setting<Boolean> randomsuffix = register(Settings.b("Anti Spam", true));
    private Setting<Integer> delay = register(Settings.integerBuilder("Send Delay").withRange(100, 60000).withValue(4000).build());
    private Setting<Boolean> readfile = register(Settings.b("Load File", false));

    @Override
    public void onEnable() {
        readSpamFile();
        timer = new Timer();
        if (mc.player == null) {
            this.disable();
            return;
        }
        task = new TimerTask() {
            @Override
            public void run() {
                runCycle();
            }
        };
        timer.schedule(task, 0, delay.getValue());
    }

    @Override
    public void onDisable() {
        timer.cancel();
        timer.purge();
        spamMessages.clear();
    }

    private void runCycle() {

        if (mc.player == null) {
            return;
        }

        if (readfile.getValue()) {
            readSpamFile();
            readfile.setValue(false);
        }

        if (spamMessages.size() > 0) {
            String messageOut;
            if (random.getValue()) {
                int index = rnd.nextInt(spamMessages.size());
                messageOut = spamMessages.get(index);
                spamMessages.remove(index);
            } else {
                messageOut = spamMessages.get(0);
                spamMessages.remove(0);
            }
            spamMessages.add(messageOut);
            if (greentext.getValue()) {
                messageOut = "> " + messageOut;
            }
            int reserved = 0;
            ArrayList<String> messageAppendix = new ArrayList();
            if (ModuleManager.isModuleEnabled("ChatSuffix")) {
                reserved = reserved + ChatTextUtils.CHAT_SUFFIX.length();
            }
            if (randomsuffix.getValue()) {
                messageAppendix.add(generateRandomHexSuffix(2));
            }
            if (messageAppendix.size() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(" ");
                for (String msg : messageAppendix) {
                    sb.append(msg);
                }
                messageOut = ChatTextUtils.cropMaxLengthMessage(messageOut, sb.toString().length() + reserved);
                messageOut = messageOut + sb.toString();
            }
            mc.player.connection.sendPacket(new CPacketChatMessage(messageOut.replaceAll(ChatTextUtils.SECTIONSIGN, "")));
        }

    }

    private void readSpamFile() {
        List<String> fileInput = readTextFileAllLines(fileName);
        Iterator<String> i = fileInput.iterator();
        spamMessages.clear();
        while (i.hasNext()) {
            String s = i.next();
            if (!s.replaceAll("\\s", "").isEmpty()) {
                spamMessages.add(s);
            }
        }
        if (spamMessages.size() == 0) {
            spamMessages.add(defaultMessage);
        }
    }

}
