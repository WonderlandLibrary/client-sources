/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.SpectatorGui;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.PlayerMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;

public class TeleportToPlayer
implements ISpectatorMenuView,
ISpectatorMenuObject {
    private static final Ordering<NetworkPlayerInfo> PROFILE_ORDER = Ordering.from(TeleportToPlayer::lambda$static$0);
    private static final ITextComponent field_243485_b = new TranslationTextComponent("spectatorMenu.teleport");
    private static final ITextComponent field_243486_c = new TranslationTextComponent("spectatorMenu.teleport.prompt");
    private final List<ISpectatorMenuObject> items = Lists.newArrayList();

    public TeleportToPlayer() {
        this(PROFILE_ORDER.sortedCopy(Minecraft.getInstance().getConnection().getPlayerInfoMap()));
    }

    public TeleportToPlayer(Collection<NetworkPlayerInfo> collection) {
        for (NetworkPlayerInfo networkPlayerInfo : PROFILE_ORDER.sortedCopy(collection)) {
            if (networkPlayerInfo.getGameType() == GameType.SPECTATOR) continue;
            this.items.add(new PlayerMenuObject(networkPlayerInfo.getGameProfile()));
        }
    }

    @Override
    public List<ISpectatorMenuObject> getItems() {
        return this.items;
    }

    @Override
    public ITextComponent getPrompt() {
        return field_243486_c;
    }

    @Override
    public void selectItem(SpectatorMenu spectatorMenu) {
        spectatorMenu.selectCategory(this);
    }

    @Override
    public ITextComponent getSpectatorName() {
        return field_243485_b;
    }

    @Override
    public void func_230485_a_(MatrixStack matrixStack, float f, int n) {
        Minecraft.getInstance().getTextureManager().bindTexture(SpectatorGui.SPECTATOR_WIDGETS);
        AbstractGui.blit(matrixStack, 0, 0, 0.0f, 0.0f, 16, 16, 256, 256);
    }

    @Override
    public boolean isEnabled() {
        return !this.items.isEmpty();
    }

    private static int lambda$static$0(NetworkPlayerInfo networkPlayerInfo, NetworkPlayerInfo networkPlayerInfo2) {
        return ComparisonChain.start().compare(networkPlayerInfo.getGameProfile().getId(), networkPlayerInfo2.getGameProfile().getId()).result();
    }
}

