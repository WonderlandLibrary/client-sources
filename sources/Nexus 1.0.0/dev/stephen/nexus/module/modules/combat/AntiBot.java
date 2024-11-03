package dev.stephen.nexus.module.modules.combat;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;
import dev.stephen.nexus.module.setting.impl.ModeSetting;
import net.minecraft.entity.player.PlayerEntity;

public class AntiBot extends Module {
    public static final ModeSetting mode = new ModeSetting("Mode", "Hypixel", "Hypixel", "Custom");

    public static final BooleanSetting nameCheck = new BooleanSetting("Name check", true);
    public static final BooleanSetting deadCheck = new BooleanSetting("Dead check", true);
    public static final BooleanSetting godCheck = new BooleanSetting("God check", true);
    public static final BooleanSetting invisCheck = new BooleanSetting("Invis check", true);

    public AntiBot() {
        super("AntiBot", "Finds bots", 0, ModuleCategory.COMBAT);
        this.addSettings(mode, nameCheck, deadCheck, godCheck, invisCheck);
        nameCheck.addDependency(mode, "Custom");
        deadCheck.addDependency(mode, "Custom");
        godCheck.addDependency(mode, "Custom");
        invisCheck.addDependency(mode, "Custom");
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        this.setSuffix(mode.getMode());
    };

    public boolean isBot(PlayerEntity playerEntity) {
        if (this.isEnabled()) {
            switch (mode.getMode()) {
                case "Hypixel":
                    if (isInvisible(playerEntity) || isGod(playerEntity)) {
                        return true;
                    }
                    break;
                case "Custom":
                    if (nameCheck.getValue() && isBotName(playerEntity)) {
                        return true;
                    } else if (deadCheck.getValue() && isDead(playerEntity)) {
                        return true;
                    } else if (godCheck.getValue() && isGod(playerEntity)) {
                        return true;
                    } else if (invisCheck.getValue() && isInvisible(playerEntity)) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }


    private boolean isDead(PlayerEntity en) {
        return en.getHealth() < 0.0F || en.isDead();
    }

    private boolean isGod(PlayerEntity en) { return en.getAbilities().creativeMode || en.getAbilities().flying || en.getAbilities().invulnerable; }

    private boolean isInvisible(PlayerEntity en) {
        return en.isInvisibleTo(mc.player);
    }

    private boolean isBotName(PlayerEntity en) {
        String rawName = en.getDisplayName().getString().toLowerCase();
        if (rawName.contains(":")) {
            return true;
        }
        if (rawName.contains("+")) {
            return true;
        }
        if (rawName.startsWith("cit")) {
            return true;
        }
        if (rawName.startsWith("npc")) {
            return true;
        }
        if (rawName.contains(" ")) {
            return true;
        }
        if (rawName.isEmpty()) {
            return true;
        }
        return false;
    }
}
