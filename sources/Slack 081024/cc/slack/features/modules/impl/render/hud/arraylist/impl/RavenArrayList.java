// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.render.hud.arraylist.impl;

import cc.slack.start.Slack;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.impl.render.Interface;
import cc.slack.features.modules.impl.render.hud.arraylist.IArraylist;
import cc.slack.utils.render.ColorUtil;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class RavenArrayList implements IArraylist {

    private class Pair {
        String first;
        String second;
    }

    private final List<Pair> modules = new ArrayList<>();

    @Override
    public void onUpdate(UpdateEvent event) {
        modules.clear();
        for (Module module : Slack.getInstance().getModuleManager().getModules()) {
            if (module.isToggle() || !module.disabledTime.hasReached(300)) {
                String displayName = module.getDisplayName();
                String mode = module.getMode();
                String key =  Keyboard.getKeyName(module.getKey());
                if (mode != null && !mode.isEmpty() && Slack.getInstance().getModuleManager().getInstance(Interface.class).tags.getValue()) {
                    switch (Slack.getInstance().getModuleManager().getInstance(Interface.class).tagsMode.getValue()) {
                        case "(Mode)":
                            displayName += "§7 (" + mode + ")";
                            break;
                        case "[Mode]":
                            displayName += "§7 [" + mode + "]";
                            break;
                        case "<Mode>":
                            displayName += "§7 <" + mode + ">";
                            break;
                        case "| Mode":
                            displayName += "§7 | " + mode;
                            break;
                        case "-> Mode":
                            displayName += "§7 -> " + mode;
                            break;
                        case "- Mode":
                            displayName += "§7 - " + mode;
                            break;
                    }
                }
                if (!key.contains("NONE")  && Slack.getInstance().getModuleManager().getInstance(Interface.class).binds.getValue()) {
                    switch (Slack.getInstance().getModuleManager().getInstance(Interface.class).bindsMode.getValue()) {
                        case "(Mode)":
                            displayName += "§7 (" + Keyboard.getKeyName(module.getKey()) + ")";
                            break;
                        case "[Mode]":
                            displayName += "§7 [" + Keyboard.getKeyName(module.getKey()) + "]";
                            break;
                        case "<Mode>":
                            displayName += "§7 <" + Keyboard.getKeyName(module.getKey()) + ">";
                            break;
                        case "| Mode":
                            displayName += "§7 | " + Keyboard.getKeyName(module.getKey());
                            break;
                        case "-> Mode":
                            displayName += "§7 -> " + Keyboard.getKeyName(module.getKey());
                            break;
                        case "- Mode":
                            displayName += "§7 - " + Keyboard.getKeyName(module.getKey());
                            break;
                    }
                }

                Pair pair = new Pair();
                pair.first = displayName;
                pair.second = module.getName();

                modules.add(pair);
            }
        }

        modules.sort((a, b) -> Integer.compare(Minecraft.getFontRenderer().getStringWidth(b.first), Minecraft.getFontRenderer().getStringWidth(a.first)));
    }

    @Override
    public void onRender(RenderEvent event) {
        int y = 3;
        double c = 0;
        for (Pair module : modules) {
            int stringLength = Minecraft.getFontRenderer().getStringWidth(module.first);
            Module m = Slack.getInstance().getModuleManager().getModuleByName(module.second);
            double ease;

            if (m.isToggle()) {
                if (m.enabledTime.hasReached(250)) {
                    ease = 0;
                } else {
                    ease = Math.pow(1 - (m.enabledTime.elapsed() / 250.0), 1);
                }
            } else {
                ease = Math.pow(m.disabledTime.elapsed() / 250.0, 1);
            }

            ease = 1 - 1.2 * ease;

            Minecraft.getFontRenderer().drawStringWithShadow(module.first, event.getWidth() - stringLength * ease - 3, y,  ColorUtil.getColor(Slack.getInstance().getModuleManager().getInstance(Interface.class).theme.getValue(), c).getRGB());
            y += (int) ((Minecraft.getFontRenderer().FONT_HEIGHT + 2) * Math.max(0, (ease + 0.2)/1.2));
            c += 0.15;
        }
    }

    @Override
    public String toString() {
        return "Raven";
    }
}
