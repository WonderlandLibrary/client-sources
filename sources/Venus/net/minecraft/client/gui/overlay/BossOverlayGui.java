/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.overlay;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import java.util.UUID;
import mpp.venusfr.events.EventCancelOverlay;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.ClientBossInfo;
import net.minecraft.network.play.server.SUpdateBossInfoPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.world.BossInfo;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.reflect.Reflector;

public class BossOverlayGui
extends AbstractGui {
    private static final ResourceLocation GUI_BARS_TEXTURES = new ResourceLocation("textures/gui/bars.png");
    private final Minecraft client;
    private final Map<UUID, ClientBossInfo> mapBossInfos = Maps.newLinkedHashMap();

    public BossOverlayGui(Minecraft minecraft) {
        this.client = minecraft;
    }

    public void func_238484_a_(MatrixStack matrixStack) {
        EventCancelOverlay eventCancelOverlay = new EventCancelOverlay(EventCancelOverlay.Overlays.BOSS_LINE);
        venusfr.getInstance().getEventBus().post(eventCancelOverlay);
        if (eventCancelOverlay.isCancel()) {
            eventCancelOverlay.open();
            return;
        }
        if (!this.mapBossInfos.isEmpty()) {
            int n = this.client.getMainWindow().getScaledWidth();
            int n2 = 12;
            for (ClientBossInfo clientBossInfo : this.mapBossInfos.values()) {
                Object object;
                int n3 = n / 2 - 91;
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                boolean bl = true;
                int n4 = 19;
                if (Reflector.ForgeHooksClient_bossBarRenderPre.exists()) {
                    object = Reflector.ForgeHooksClient_bossBarRenderPre.call(matrixStack, this.client.getMainWindow(), clientBossInfo, n3, n2, 19);
                    bl = !Reflector.callBoolean(object, Reflector.Event_isCanceled, new Object[0]);
                    n4 = Reflector.callInt(object, Reflector.RenderGameOverlayEvent_BossInfo_getIncrement, new Object[0]);
                }
                if (bl) {
                    this.client.getTextureManager().bindTexture(GUI_BARS_TEXTURES);
                    this.func_238485_a_(matrixStack, n3, n2, clientBossInfo);
                    object = clientBossInfo.getName();
                    int n5 = this.client.fontRenderer.getStringPropertyWidth((ITextProperties)object);
                    int n6 = n / 2 - n5 / 2;
                    int n7 = n2 - 9;
                    int n8 = 0xFFFFFF;
                    if (Config.isCustomColors()) {
                        n8 = CustomColors.getBossTextColor(n8);
                    }
                    this.client.fontRenderer.func_243246_a(matrixStack, (ITextComponent)object, n6, n7, n8);
                }
                Reflector.ForgeHooksClient_bossBarRenderPost.callVoid(matrixStack, this.client.getMainWindow());
                if ((n2 += n4) < this.client.getMainWindow().getScaledHeight() / 3) continue;
                break;
            }
        }
    }

    private void func_238485_a_(MatrixStack matrixStack, int n, int n2, BossInfo bossInfo) {
        int n3;
        this.blit(matrixStack, n, n2, 0, bossInfo.getColor().ordinal() * 5 * 2, 182, 5);
        if (bossInfo.getOverlay() != BossInfo.Overlay.PROGRESS) {
            this.blit(matrixStack, n, n2, 0, 80 + (bossInfo.getOverlay().ordinal() - 1) * 5 * 2, 182, 5);
        }
        if ((n3 = (int)(bossInfo.getPercent() * 183.0f)) > 0) {
            this.blit(matrixStack, n, n2, 0, bossInfo.getColor().ordinal() * 5 * 2 + 5, n3, 5);
            if (bossInfo.getOverlay() != BossInfo.Overlay.PROGRESS) {
                this.blit(matrixStack, n, n2, 0, 80 + (bossInfo.getOverlay().ordinal() - 1) * 5 * 2 + 5, n3, 5);
            }
        }
    }

    public void read(SUpdateBossInfoPacket sUpdateBossInfoPacket) {
        if (sUpdateBossInfoPacket.getOperation() == SUpdateBossInfoPacket.Operation.ADD) {
            this.mapBossInfos.put(sUpdateBossInfoPacket.getUniqueId(), new ClientBossInfo(sUpdateBossInfoPacket));
        } else if (sUpdateBossInfoPacket.getOperation() == SUpdateBossInfoPacket.Operation.REMOVE) {
            this.mapBossInfos.remove(sUpdateBossInfoPacket.getUniqueId());
        } else {
            this.mapBossInfos.get(sUpdateBossInfoPacket.getUniqueId()).updateFromPacket(sUpdateBossInfoPacket);
        }
    }

    public void clearBossInfos() {
        this.mapBossInfos.clear();
    }

    public boolean shouldPlayEndBossMusic() {
        if (!this.mapBossInfos.isEmpty()) {
            for (BossInfo bossInfo : this.mapBossInfos.values()) {
                if (!bossInfo.shouldPlayEndBossMusic()) continue;
                return false;
            }
        }
        return true;
    }

    public boolean shouldDarkenSky() {
        if (!this.mapBossInfos.isEmpty()) {
            for (BossInfo bossInfo : this.mapBossInfos.values()) {
                if (!bossInfo.shouldDarkenSky()) continue;
                return false;
            }
        }
        return true;
    }

    public boolean shouldCreateFog() {
        if (!this.mapBossInfos.isEmpty()) {
            for (BossInfo bossInfo : this.mapBossInfos.values()) {
                if (!bossInfo.shouldCreateFog()) continue;
                return false;
            }
        }
        return true;
    }
}

