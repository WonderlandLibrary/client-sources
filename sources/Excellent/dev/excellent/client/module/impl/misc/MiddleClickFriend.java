package dev.excellent.client.module.impl.misc;

import dev.excellent.Excellent;
import dev.excellent.api.event.impl.input.MouseInputEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.module.impl.combat.KillAura;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.util.pattern.Singleton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TextFormatting;

@ModuleInfo(name = "Middle Click Friend", description = "Добавляет сущность в друзья при нажатии СКМ.", category = Category.MISC)
public class MiddleClickFriend extends Module {
    public static Singleton<MiddleClickFriend> singleton = Singleton.create(() -> Module.link(MiddleClickFriend.class));

    private final Listener<MouseInputEvent> onMouseInput = event -> {
        if (event.getMouseButton() != 2 || KillAura.singleton.get().isEnabled()) return;


        if (mc.pointedEntity instanceof PlayerEntity player) {
            final String name = TextFormatting.getTextWithoutFormattingCodes(player.getName().getString());
            final boolean friend = Excellent.getInst().getFriendManager().isFriend(name);

            if (friend) {
                Excellent.getInst().getFriendManager().removeFriend(name);
                ChatUtil.addText(TextFormatting.RED + "\"" + name + "\" удалён из списка друзей.");
            } else {
                Excellent.getInst().getFriendManager().addFriend(name);
                ChatUtil.addText(TextFormatting.GREEN + "\"" + name + "\" добавлен в список друзей.");
            }
        }
    };
}