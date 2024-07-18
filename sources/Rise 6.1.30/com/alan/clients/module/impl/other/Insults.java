package com.alan.clients.module.impl.other;

import com.alan.clients.component.impl.player.BadPacketsComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.AttackEvent;
import com.alan.clients.event.impl.other.WorldChangeEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.impl.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@ModuleInfo(aliases = {"module.other.insults.name"}, description = "module.other.insults.description", category = Category.PLAYER)
public final class Insults extends Module {

    public final ModeValue mode = new ModeValue("Mode", this)
            .add(new SubMode("Default"))
            .add(new SubMode("Watchdog"))
            .add(new SubMode("WhatsApp"))
            .add(new SubMode("CSGO"))
            .add(new SubMode("NerdyAss"))
            .setDefault("Default");

    public final Map<String, List<String>> map = new HashMap<>();

    private final StringValue prefix = new StringValue("Prefix", this, "");
    private final NumberValue delay = new NumberValue("Delay", this, 0, 0, 50, 1);
    private final BooleanValue randomizer = new BooleanValue("Randomizer", this, false);

    private final String[] defaultInsults = {
            "Wow! My combo is Rise'n!",
            "Why would someone as bad as you not use Rise 6.0?",
            "Here's your ticket to spectator from Rise 6.0!",
            "I see you're a pay to lose player, huh?",
            "Do you need some PvP advice? Well Rise 6.0 is all you need.",
            "Hey! Wise up, don't waste another day without Rise.",
            "You didn't even stand a chance against Rise.",
            "We regret to inform you that your free trial of life has unfortunately expired.",
            "RISE against other cheaters by getting Rise!",
            "You can pay for that loss by getting Rise.",
            "Remember to use hand sanitizer to get rid of bacteria like you!",
            "Hey, try not to drown in your own salt.",
            "Having problems with forgetting to left click? Rise 6.0 can fix it!",
            "Come on, is that all you have against Rise 6.0?",
            "Rise up today by getting Rise 6.0!",
            "Get Rise, you need it.",
            "how about you rise up to heaven by ending it",
            "Did you know Watchdog has banned 6346 players in the last 7 days."
    };

    private final String[] whatsAppInsults = {
            "Add me on WhatsApp ",
    };

    // Bro we giving advertising to him :cry:
    private final String nerdyass = "LOL %s GOT SNIPED BY NERDYASS ON YOUTUBE";

    private final String[] csgo = {
            "Missed %s due to correction",
            "Missed %s due to spread",
            "Missed %s due to prediction error",
            "Missed %s due to invalid backtrack",
            "Missed %s due to ?",
            "Shot at head, and missed head, but hit anyways because of spread (lol)",
            "Missed %s due to resolver",
    };

    private EntityPlayer target;
    private int ticks;

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (target != null && !mc.theWorld.playerEntities.contains(target)) {
            if (ticks >= delay.getValue().intValue() + Math.random() * 2 && !BadPacketsComponent.bad()) {
                String insult = "";

                switch (mode.getValue().getName()) {
                    case "Default":
                        insult = defaultInsults[RandomUtils.nextInt(0, defaultInsults.length)];
                        break;

                    case "Watchdog":
                        insult = "[STAFF] [WATCHDOG] %s reeled in.";
                        break;

                    case "WhatsApp":
                        insult = whatsAppInsults[RandomUtils.nextInt(0, whatsAppInsults.length)];
                        break;

                    case "CSGO":
                        insult = csgo[RandomUtils.nextInt(0, csgo.length)];
                        break;

                    case "NerdyAss":
                        insult = nerdyass;
                }

                insult = String.format(insult, PlayerUtil.name(target));

                if (!this.prefix.getValue().isEmpty()) {
                    insult = this.prefix.getValue() + " " + insult;
                }
                final String generatedString = new Random().ints(97, 123)
                        .limit(10)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();

                if (randomizer.getValue()) {
                    mc.thePlayer.sendChatMessage(insult + " " + generatedString);
                } else {
                    mc.thePlayer.sendChatMessage(insult);
                }
                target = null;
            }

            ticks++;
        }
    };

    @EventLink
    public final Listener<AttackEvent> onAttack = event -> {
        final Entity target = event.getTarget();

        if (target instanceof EntityPlayer) {
            this.target = (EntityPlayer) target;
            ticks = 0;
        }
    };

    @EventLink
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        target = null;
        ticks = 0;
    };
}