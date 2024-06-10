package me.rowin.clicker;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jnativehook.GlobalScreen;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Clicker {

    private static final String TOKEN = "ODQ2MzU4NTcxODEzMzA2Mzc4.YKuW2A.yll8AMshaqABxBZZmYA7Cp-_Phw";
    private static final String GUILD_ID = "846358951338836020";
    private static final String CHANNEL_ID = "846358951838875681";

    private boolean state = false;
    private boolean holding = false;
    private boolean skipNextPress = false;
    private boolean skipNextRelease = false;
    private boolean chill = false;
    private int cps = 12;

    public static void main(String[] args) throws Exception {
        new Clicker().start();
    }

    private void start() throws Exception {
        JDA jda = JDABuilder.createDefault(TOKEN)
                .build()
                .awaitReady();

        Guild guild = jda.getGuildById(GUILD_ID);

        if (guild == null) {
            System.out.println("Could not find guild with ID " + GUILD_ID);
            return;
        }

        TextChannel channel = guild.getTextChannelById(CHANNEL_ID);

        if (channel == null) {
            System.out.println("Could not find channel with ID " + CHANNEL_ID);
            return;
        }

        Message message = channel.sendMessage(buildEmbed()).complete();
        message.addReaction("\uD83D\uDCA3").queue();
        message.addReaction("⬆️").queue();
        message.addReaction("⬇️").queue();
        message.addReaction("\uD83D\uDFE2").queue();
        message.addReaction("\uD83D\uDD34").queue();

        jda.addEventListener(new ListenerAdapter() {
            @Override
            public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
                User user = event.getUser();

                if (user.isBot()) return;
                if (event.getMessageIdLong() != message.getIdLong()) return;

                event.getReaction().removeReaction(user).queue();

                String emoji = event.getReactionEmote().getEmoji();

                switch (emoji) {
                    case "\uD83D\uDCA3":
                        message.delete().queue($ -> System.exit(0));
                        break;
                    case "⬆️":
                        cps = Math.min(20, cps + 2);
                        message.editMessage(buildEmbed()).queue();
                        break;
                    case "⬇️":
                        cps = Math.max(6, cps - 2);
                        message.editMessage(buildEmbed()).queue();
                        break;
                    case "\uD83D\uDFE2":
                        state = true;
                        message.editMessage(buildEmbed()).queue();
                        break;
                    case "\uD83D\uDD34":
                        state = false;
                        message.editMessage(buildEmbed()).queue();
                        break;
                }
            }
        });

        // Disable annoying JNativeHook logging
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);

        GlobalScreen.registerNativeHook();

        GlobalScreen.addNativeMouseListener(new NativeMouseListener() {
            @Override
            public void nativeMouseClicked(NativeMouseEvent event) {
                // Unused
            }

            @Override
            public void nativeMousePressed(NativeMouseEvent event) {
                if (event.getButton() == NativeMouseEvent.BUTTON1) {
                    if (skipNextPress) {
                        skipNextPress = false;
                    } else {
                        holding = true;
                        chill = true;
                    }
                }
            }

            @Override
            public void nativeMouseReleased(NativeMouseEvent event) {
                if (event.getButton() == NativeMouseEvent.BUTTON1) {
                    if (skipNextRelease) {
                        skipNextRelease = false;
                    } else {
                        holding = false;
                    }
                }
            }
        });

        Robot robot = new Robot();
        Random random = new Random();

        //noinspection InfiniteLoopStatement
        while (true) {
            if (state && holding) {
                if (!chill) {
                    skipNextPress = true;
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                }

                long ms = (long) (1000 / (cps - random.nextDouble()));

                if (random.nextInt(10) == 0) {
                    ms += 20 + random.nextInt(20);
                }

                if (random.nextInt(20) == 0) {
                    ms += 50 + random.nextInt(50);
                }

                //noinspection BusyWait
                Thread.sleep(ms / 2);

                if (!chill) {
                    skipNextRelease = true;
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }

                //noinspection BusyWait
                Thread.sleep(ms / 2);

                chill = false;
            }

            //noinspection BusyWait
            Thread.sleep(1);
        }
    }

    private MessageEmbed buildEmbed() {
        return new EmbedBuilder()
                .setColor(Color.MAGENTA)
                .setDescription("Press \uD83D\uDCA3 to exit the auto clicker\n"
                        + "Press ⬆️️ to increase your CPS️\n"
                        + "Press ⬇️ to decrease your CPS\n"
                        + "Press \uD83D\uDFE2 to enable the auto clicker\n"
                        + "Press \uD83D\uDD34 to disable the auto clicker\n"
                        + "\n"
                        + "Enabled: " + (state ? "\uD83D\uDFE2" : "\uD83D\uDD34") + "\n"
                        + "CPS: " + cps)
                .build();
    }
}
