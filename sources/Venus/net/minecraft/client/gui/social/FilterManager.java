/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.social;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.SocialInteractionsService;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.social.SocialInteractionsScreen;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.util.Util;

public class FilterManager {
    private final Minecraft field_244642_a;
    private final Set<UUID> field_244643_b = Sets.newHashSet();
    private final SocialInteractionsService field_244755_c;
    private final Map<String, UUID> field_244796_d = Maps.newHashMap();

    public FilterManager(Minecraft minecraft, SocialInteractionsService socialInteractionsService) {
        this.field_244642_a = minecraft;
        this.field_244755_c = socialInteractionsService;
    }

    public void func_244646_a(UUID uUID) {
        this.field_244643_b.add(uUID);
    }

    public void func_244647_b(UUID uUID) {
        this.field_244643_b.remove(uUID);
    }

    public boolean func_244756_c(UUID uUID) {
        return this.func_244648_c(uUID) || this.func_244757_e(uUID);
    }

    public boolean func_244648_c(UUID uUID) {
        return this.field_244643_b.contains(uUID);
    }

    public boolean func_244757_e(UUID uUID) {
        return this.field_244755_c.isBlockedPlayer(uUID);
    }

    public Set<UUID> func_244644_a() {
        return this.field_244643_b;
    }

    public UUID func_244797_a(String string) {
        return this.field_244796_d.getOrDefault(string, Util.DUMMY_UUID);
    }

    public void func_244645_a(NetworkPlayerInfo networkPlayerInfo) {
        Screen screen;
        GameProfile gameProfile = networkPlayerInfo.getGameProfile();
        if (gameProfile.isComplete()) {
            this.field_244796_d.put(gameProfile.getName(), gameProfile.getId());
        }
        if ((screen = this.field_244642_a.currentScreen) instanceof SocialInteractionsScreen) {
            SocialInteractionsScreen socialInteractionsScreen = (SocialInteractionsScreen)screen;
            socialInteractionsScreen.func_244683_a(networkPlayerInfo);
        }
    }

    public void func_244649_d(UUID uUID) {
        Screen screen = this.field_244642_a.currentScreen;
        if (screen instanceof SocialInteractionsScreen) {
            SocialInteractionsScreen socialInteractionsScreen = (SocialInteractionsScreen)screen;
            socialInteractionsScreen.func_244685_a(uUID);
        }
    }
}

