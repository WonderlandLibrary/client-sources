package club.bluezenith.module.modules.misc;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.UpdateEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.ActionValue;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.module.value.types.StringValue;
import club.bluezenith.util.client.ClientUtils;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.player.PacketUtil;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.apache.commons.lang3.RandomStringUtils;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;

public class Spammer extends Module {
    public Spammer() {
        super("Spammer", ModuleCategory.MISC);
    }
    private final IntegerValue delay = new IntegerValue("Delay (ms)", 3000, 10, 10000, 50).setIndex(1);
    private final StringValue text = new StringValue("Text", "Buy Blue Zenith").setIndex(2);
    private final ModeValue mode = new ModeValue("Bypass", "Random", "Random", "Invisible", "None").setIndex(3);
    private final ModeValue randomMode = new ModeValue("Placing", "First", "First", "Last").setDefaultVisibility(false).showIf(() -> mode.is("Random")).setIndex(4);
    private final IntegerValue randomLength = new IntegerValue("String length", 5, 1, 20, 1).setDefaultVisibility(false).showIf(randomMode::isVisible).setIndex(5);
    private final ActionValue test = new ActionValue("Reset Text").setDefaultVisibility(true).setOnClickListener(() -> text.set("")).setIndex(6);
    private final ActionValue paste = new ActionValue("Copy from clipboard").setDefaultVisibility(true).setOnClickListener(this::fromClipboard).setIndex(7);

    private final MillisTimer timer = new MillisTimer();
    private String spamMessage = "unknown";

    @Listener
    public void spam(UpdateEvent event) {
        if (timer.hasTimeReached(delay.get())) {
           if(mode.is("Random")) switch (randomMode.get()) {
                case "First":
                    spamMessage = "[" + (mode.is("Random") ? RandomStringUtils.randomAlphanumeric(randomLength.get()) : "") + "] " + text.get();
                    PacketUtil.send(new C01PacketChatMessage(spamMessage));
                break;

                case "Last":
                    spamMessage = text.get() + " [" + (mode.is("Random") ? RandomStringUtils.randomAlphanumeric(randomLength.get()) : "") + "]";
                   PacketUtil.send(new C01PacketChatMessage(spamMessage));
                break;
            } else if(mode.is("Invisible")) {
               final String[] chars = text.get().split("");
               final StringBuilder stringBuilder = new StringBuilder();

               for (String aChar : chars) {
                   stringBuilder.append(aChar)
                           .append("\u070E");
               }

               mc.thePlayer.sendChatMessage(stringBuilder.toString());
           } else mc.thePlayer.sendChatMessage(text.get());//PacketUtil.send(new C01PacketChatMessage(text.get()));

            timer.reset();
        }
    }

    private void fromClipboard() {
        try {
            text.set(String.valueOf(Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)));
        } catch(Exception ignored) {
            ClientUtils.fancyMessage("This option only supports plain text!");
        }
    }

}
