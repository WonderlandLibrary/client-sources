package dev.darkmoon.client.module.impl.player;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.input.EventMouse;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.misc.ChatUtility;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;

@ModuleAnnotation(name = "ClickFriend", category = Category.PLAYER)
public class ClickFriend extends Module {
    @EventTarget
    public void onMouse(EventMouse event) {
        if (event.getButton() == 2 && mc.pointedEntity instanceof EntityLivingBase) {
            String name = mc.pointedEntity.getName();
            if (DarkMoon.getInstance().getFriendManager().isFriend(name)) {
                DarkMoon.getInstance().getFriendManager().removeFriend(name);
                ChatUtility.addChatMessage(TextFormatting.RED + name + TextFormatting.GRAY + " успешно удален из списка друзей." + TextFormatting.RESET);
            } else {
                DarkMoon.getInstance().getFriendManager().addFriend(name);
                ChatUtility.addChatMessage(TextFormatting.RED + name + TextFormatting.GRAY + " успешно добавлен в друзья!" + TextFormatting.RESET);
            }
        }
    }
}
