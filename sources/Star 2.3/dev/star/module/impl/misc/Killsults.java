package dev.star.module.impl.misc;

import dev.star.Client;
import dev.star.event.impl.player.ChatReceivedEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.utils.misc.FileUtils;
import dev.star.utils.player.ChatUtil;
import dev.star.utils.server.ServerUtils;
import net.minecraft.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Killsults extends Module {

    public final static File INSULT_DIR = new File(Client.DIRECTORY, "Killsults.txt");
    private final List<String> messages = new ArrayList<>();
    private int index;


    public Killsults() {
        super("Killsults", Category.MISC, "Insults the player that you just killed");
    }

    @Override
    public void onChatReceivedEvent(ChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (!message.contains(":") && (
                message.contains("by " + mc.thePlayer.getName()) // hypixel
                        || message.contains("para " + mc.thePlayer.getName())
                        || message.contains("fue destrozado a manos de " + mc.thePlayer.getName())
        )) {
            if (index >= messages.size()) index = 0;
            ChatUtil.send((ServerUtils.isGeniuneHypixel() ? "/ac " : "") + messages.get(index).replace("{player}", message.trim().split(" ")[0]));
            index++;
        }
    }

    @Override
    public void onEnable() {
        messages.clear();
        if (INSULT_DIR.exists()) {
            String killsults = FileUtils.readFile(INSULT_DIR);
            messages.addAll(Arrays.asList(killsults.split("\n")));
        } else {
            writeToFile();
        }
        super.onEnable();
    }



    private void writeToFile() {
        FileUtils.writeFile(INSULT_DIR, String.join("\n", "Some babies were dropped on their heads, but {player} was clearly thrown at a wall!\n" +
                "{player}'s skill is more disappointing than an unsalted pretzel.\n" +
                "{player}, don’t be ashamed of who you are. That’s your parents’ job.\n" +
                "Roses are red, violets are blue, God made me amazing, what happened to {player}?\n" +
                "{player} is not as bad as people say, they're much, much worse.\n" +
                "How old are you {player}? - Wait I shouldn't ask, you can't count that high.\n" +
                "How did you get here {player}? Did someone leave your cage open?\n" +
                "I was today years old when I realized I didn’t like {player}.\n" +
                "{player} is the reason God created the middle finger.\n" +
                "{player} brings everyone so much joy! You know, when they leave. But, still.\n" +
                "People clap when they see {player}. They clap their hands over their eyes.\n" +
                "I’d say {player} is ‘dumb as a giraffe,’ but at least a giraffe has a brain.\n" +
                "{player} should carry a plant around with them to replace the oxygen they waste.\n" +
                "Aww, it’s so cute when {player} tries to talk about things they don’t understand.\n" +
                "{player} fears success even though they really have nothing to worry about.\n" +
                "Two wrongs don’t make a right. Take {player}'s parents, for instance.\n" +
                "Keep barking {player}. Some day you'll say something intelligent!\n" +
                "Don’t get bitter {player}, just get better.\n" +
                "{player} is proof that God has a sense of humor.\n" +
                "Everyone who ever loved {player} was wrong.\n" +
                "If you look up gullible in the dictionary, you'll find {player}!\n" +
                "I know five fat people and {player} is three of them.\n" +
                "Everyone makes mistakes, especially {player}'s parents.\n" +
                "{player}'s father would be disappointed in them. If he stayed.\n" +
                "The last time I heard about {player}'s father, he was out buying milk.\n" +
                "I bet {player}'s family tree looks like a telephone pole!\n" +
                "I envy everyone who has never met {player}.\n" +
                "{player} is like the first slice of bread in a loaf, nobody wants them.\n" +
                "{player} isn't completely useless, they can always be used as a bad example.\n" +
                "{player}'s fat. I won't sugarcoat that, because they'll eat that too.\n" +
                "Every time I hear {player} talk, I can feel my brain cells dying one by one.\n" +
                "{player} is so fat that Thanos had to snap his fingers twice for something to happen."));
    }


}
