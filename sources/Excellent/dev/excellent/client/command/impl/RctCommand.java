package dev.excellent.client.command.impl;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.command.Command;
import dev.excellent.client.script.ScriptConstructor;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.util.player.PlayerUtil;
import net.minecraft.client.gui.ClientBossInfo;
import net.minecraft.world.BossInfo;

public class RctCommand extends Command {
    public RctCommand() {
        super("", "rct", "reconnect");
    }

    private final ScriptConstructor script = ScriptConstructor.create();
    private final Listener<UpdateEvent> onUpdate = event -> script.update();

    private boolean isInHub() {
        return mc.ingameGUI.getBossOverlay()
                .getMapBossInfos()
                .entrySet()
                .stream()
                .anyMatch(entry -> {
                    ClientBossInfo value = entry.getValue();
                    if (value.getName().getString().equals("Наш дискорд сервер dd.funtime.su") && value.getColor().equals(BossInfo.Color.PURPLE)) {
                        return true;
                    } else if (value.getName().getString().equals("Вы играете на ФанТайм!") && value.getColor().equals(BossInfo.Color.RED)) {
                        return true;
                    } else if (value.getName().getString().equals("Подпишись на нас /links") && value.getColor().equals(BossInfo.Color.BLUE)) {
                        return true;
                    } else
                        return value.getName().getString().equals("Наш айпи: play.funtime.su") && value.getColor().equals(BossInfo.Color.YELLOW);
                });
    }

    @Override
    public void execute(String[] args) {
        if (PlayerUtil.isFuntime()) {
            int anarchy = PlayerUtil.getAnarchy();
            if (anarchy != -1) {
                if (!PlayerUtil.isPvp()) {
                    ChatUtil.addText("&aПерезахожу..");
                    script.cleanup()
                            .addStep(0, () -> ChatUtil.sendText("/hub"))
                            .addStep(0, () -> ChatUtil.sendText("/an" + anarchy), this::isInHub);
                } else {
                    ChatUtil.addText("&cВы находитесь в режиме PVP.");
                }
            } else {
                ChatUtil.addText("&cВы должны находится на сервере Анархии.");
            }
        } else {
            ChatUtil.addText("&cК сожалению &f\".rct\" &cне работает на этом сервере.");
        }
    }
}
