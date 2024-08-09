/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.SpectatorGui;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.categories.TeleportToPlayer;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TeleportToTeam
implements ISpectatorMenuView,
ISpectatorMenuObject {
    private static final ITextComponent field_243487_a = new TranslationTextComponent("spectatorMenu.team_teleport");
    private static final ITextComponent field_243488_b = new TranslationTextComponent("spectatorMenu.team_teleport.prompt");
    private final List<ISpectatorMenuObject> items = Lists.newArrayList();

    public TeleportToTeam() {
        Minecraft minecraft = Minecraft.getInstance();
        for (ScorePlayerTeam scorePlayerTeam : minecraft.world.getScoreboard().getTeams()) {
            this.items.add(new TeamSelectionObject(this, scorePlayerTeam));
        }
    }

    @Override
    public List<ISpectatorMenuObject> getItems() {
        return this.items;
    }

    @Override
    public ITextComponent getPrompt() {
        return field_243488_b;
    }

    @Override
    public void selectItem(SpectatorMenu spectatorMenu) {
        spectatorMenu.selectCategory(this);
    }

    @Override
    public ITextComponent getSpectatorName() {
        return field_243487_a;
    }

    @Override
    public void func_230485_a_(MatrixStack matrixStack, float f, int n) {
        Minecraft.getInstance().getTextureManager().bindTexture(SpectatorGui.SPECTATOR_WIDGETS);
        AbstractGui.blit(matrixStack, 0, 0, 16.0f, 0.0f, 16, 16, 256, 256);
    }

    @Override
    public boolean isEnabled() {
        for (ISpectatorMenuObject iSpectatorMenuObject : this.items) {
            if (!iSpectatorMenuObject.isEnabled()) continue;
            return false;
        }
        return true;
    }

    class TeamSelectionObject
    implements ISpectatorMenuObject {
        private final ScorePlayerTeam team;
        private final ResourceLocation location;
        private final List<NetworkPlayerInfo> players;
        final TeleportToTeam this$0;

        public TeamSelectionObject(TeleportToTeam teleportToTeam, ScorePlayerTeam scorePlayerTeam) {
            this.this$0 = teleportToTeam;
            this.team = scorePlayerTeam;
            this.players = Lists.newArrayList();
            for (String string : scorePlayerTeam.getMembershipCollection()) {
                NetworkPlayerInfo networkPlayerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(string);
                if (networkPlayerInfo == null) continue;
                this.players.add(networkPlayerInfo);
            }
            if (this.players.isEmpty()) {
                this.location = DefaultPlayerSkin.getDefaultSkinLegacy();
            } else {
                String string = this.players.get(new Random().nextInt(this.players.size())).getGameProfile().getName();
                this.location = AbstractClientPlayerEntity.getLocationSkin(string);
                AbstractClientPlayerEntity.getDownloadImageSkin(this.location, string);
            }
        }

        @Override
        public void selectItem(SpectatorMenu spectatorMenu) {
            spectatorMenu.selectCategory(new TeleportToPlayer(this.players));
        }

        @Override
        public ITextComponent getSpectatorName() {
            return this.team.getDisplayName();
        }

        @Override
        public void func_230485_a_(MatrixStack matrixStack, float f, int n) {
            Integer n2 = this.team.getColor().getColor();
            if (n2 != null) {
                float f2 = (float)(n2 >> 16 & 0xFF) / 255.0f;
                float f3 = (float)(n2 >> 8 & 0xFF) / 255.0f;
                float f4 = (float)(n2 & 0xFF) / 255.0f;
                AbstractGui.fill(matrixStack, 1, 1, 15, 15, MathHelper.rgb(f2 * f, f3 * f, f4 * f) | n << 24);
            }
            Minecraft.getInstance().getTextureManager().bindTexture(this.location);
            RenderSystem.color4f(f, f, f, (float)n / 255.0f);
            AbstractGui.blit(matrixStack, 2, 2, 12, 12, 8.0f, 8.0f, 8, 8, 64, 64);
            AbstractGui.blit(matrixStack, 2, 2, 12, 12, 40.0f, 8.0f, 8, 8, 64, 64);
        }

        @Override
        public boolean isEnabled() {
            return !this.players.isEmpty();
        }
    }
}

