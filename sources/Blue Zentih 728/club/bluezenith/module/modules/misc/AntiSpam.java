package club.bluezenith.module.modules.misc;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.builders.AbstractBuilder;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.StringValue;
import com.google.common.collect.Lists;
import net.minecraft.network.play.server.S02PacketChat;

import java.io.BufferedReader;
import java.util.List;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static java.util.Locale.ROOT;

public class AntiSpam extends Module {
    
    public static List<String> blacklistedMessages = Lists.newArrayList();
    public static List<String> ignoredPlayers = Lists.newArrayList();
    private final BooleanValue ignoreCase = AbstractBuilder.createBoolean("Ignore case").index(1).build();
    private final StringValue ignPattern = new StringValue("IGN Pattern", "").setIndex(2);

    public AntiSpam() {
        super("AntiSpam", ModuleCategory.MISC);
    }
    
    @Listener
    public void onChat(PacketEvent event) {
        if(!(event.packet instanceof S02PacketChat)) return;
        String message = ((S02PacketChat)event.packet).getChatComponent().getUnformattedText();
        if(ignoreCase.get())
            message = message.toLowerCase(ROOT);

        if(!ignoredPlayers.isEmpty()) {
            final String lowercaseString = message.toLowerCase(ROOT);
            for (String ignoredPlayer : ignoredPlayers) {
                if(!ignPattern.get().isEmpty()) {
                    if(lowercaseString.contains(" ")) {
                        for (String str : lowercaseString.split(" ")) {
                            if (str.toLowerCase(ROOT).matches(ignPattern.get().replaceAll("\\$d", ignoredPlayer).toLowerCase(ROOT))) {
                                event.cancel();
                                return;
                            }
                        }
                    } else if (lowercaseString.toLowerCase(ROOT).contains(ignPattern.get().replaceAll("\\$d", ignoredPlayer).toLowerCase(ROOT))) {
                        event.cancel();
                        return;
                    }
                } else {
                    if(lowercaseString.contains(ignoredPlayer.toLowerCase())) {
                        event.cancel();
                        return;
                    }
                 }
            }
        }

        for (String blacklistedMessage : blacklistedMessages) {
            if(ignoreCase.get())
                blacklistedMessage = blacklistedMessage.toLowerCase(ROOT);
            if(message.contains(blacklistedMessage)) {
                event.cancel();
            }
        }
    }

    public static void init() {
        if(!getBlueZenith().getResourceRepository().fileExists("antispam.txt")) return;
        String line;
        BufferedReader reader = getBlueZenith().getResourceRepository().getReaderForFile("antispam.txt");
        try {
            while ((line = reader.readLine()) != null)
                blacklistedMessages.add(line);
        } catch (Exception exception){}
    }

    public static void save() {
        if(!getBlueZenith().getResourceRepository().createFileInDirectory("antispam.txt", false)) return;
        getBlueZenith().getResourceRepository().deleteFile("antispam.txt");
        final StringBuilder builder = new StringBuilder();
        for (String mes : blacklistedMessages) {
            builder.append(mes).append("\n");
        }
        getBlueZenith().getResourceRepository().writeToFile("antispam.txt", builder.toString());
    }
    
}
