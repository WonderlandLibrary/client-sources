package com.alan.clients.module.impl.other;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.WorldChangeEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.value.impl.BooleanValue;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@ModuleInfo(aliases = {"module.other.autogroomer.name"}, description = "Helps you groom people.", category = Category.PLAYER)
public final class AutoGroomer extends Module {

    private final Random random = new Random();

    private final char[] chars = new char[] {
            '⛍', '⛌', '⛗', '⛗', '⛟'
    };
    private final List<Integer> ids = new ArrayList<>();
    private final List<String> messages = Arrays.asList(
            "can I have some tittie pics?", "do you wanna be above or below?",
            "I am gonna be pounding you 24/7", "I am gonna send you something okay? no sharing :wink:", "I am fine below or above",
            "you are gonna be riding this dick all night", "oh I am creaming just looking at you", "I want to make you cum.",
            "my balls are gonna be dry tonight thanks to you", "I am gonna relieve you all night", "daddy is ready.",
            "you will be screaming my name tonight", "fly up here and you can have as much as you want",
            "lick it off like that, until I ram your mouth.", "daddy wants your mouth on all of this tonight",
            "I bet you like daddy pounding you so hard that your knees give out and drag your face forward as I literally pound you flat into the bed.",
            "I'm so hard rn", "I'm dripping", "Just wait until we get home", "I want to taste you", "I love how hard you can make me come",
            "Get your ass on that bed.", "Daddy's gonna eat that pussy tonight.", "Daddy's all bricked up now."
    );

    private final BooleanValue chatBypass = new BooleanValue("Chat Bypass", this, false);
    private final BooleanValue dm = new BooleanValue("DM Messages", this, false);
    private final BooleanValue spam = new BooleanValue("Spam", this, false);

    @EventLink
    private final Listener<PreMotionEvent> pre = event -> {
        if (!dm.getValue() && mc.thePlayer.ticksExisted % 69 == 0) {
            String message = messages.get(random.nextInt(messages.size()));

            if (chatBypass.getValue()) {
                final StringBuilder bypass = new StringBuilder(message.length() * 2);

                for (int i = 0; i < message.length(); i++) {
                    final char character = message.charAt(i);
                    final char randomChar = chars[random.nextInt(chars.length)];

                    bypass.append(character).append(randomChar);
                }

                message = bypass.toString();
            }

            mc.thePlayer.sendChatMessage(message);

            return;
        }

        if (spam.getValue() && mc.thePlayer.ticksExisted % 20 != 0) return;

        for (final EntityPlayer player : mc.theWorld.playerEntities) {
            if (player != mc.thePlayer && !player.isInvisible() && !Client.INSTANCE.getBotManager().contains(player)
                    && player.getDistanceSqToEntity(mc.thePlayer) < 64) {

                if (!ids.contains(player.getEntityId()) || spam.getValue()) {
                    String message = messages.get(random.nextInt(messages.size()));

                    if (chatBypass.getValue()) {
                        final StringBuilder bypass = new StringBuilder(message.length() * 2);

                        for (int i = 0; i < message.length(); i++) {
                            final char character = message.charAt(i);
                            final char randomChar = chars[random.nextInt(chars.length)];

                            bypass.append(character).append(randomChar);
                        }

                        message = bypass.toString();
                    }

                    mc.getNetHandler().addToSendQueue(new C01PacketChatMessage(
                            "/msg " + player.getCommandSenderName() + " " + message
                    ));

                    ids.add(player.getEntityId());
                }
            }
        }
    };

    @EventLink
    private final Listener<WorldChangeEvent> worldChange = event -> ids.clear();

    @Override
    public void onDisable() {
        ids.clear();
    }

    @Override
    public void onEnable() {
        ids.clear();
    }
}
