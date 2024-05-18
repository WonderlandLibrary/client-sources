/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import pw.vertexcode.nemphis.Nemphis;
import pw.vertexcode.nemphis.friend.Friend;
import pw.vertexcode.nemphis.friend.FriendManager;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.RENDER, color=-16745439, name="NameProtect")
public class NameProtect
extends ToggleableModule {
    @Override
    public void onEnabled() {
        Nemphis.instance.friendManager.newFriend(new Friend(NameProtect.mc.thePlayer.getName(), "you"));
        super.onEnabled();
    }

    @Override
    public void onDisable() {
        Nemphis.instance.friendManager.removeFriend(new Friend(NameProtect.mc.thePlayer.getName(), "you"));
        super.onDisable();
    }
}

