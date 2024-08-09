/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.multiplayer;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.AdvancementToast;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.play.client.CSeenAdvancementsPacket;
import net.minecraft.network.play.server.SAdvancementInfoPacket;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientAdvancementManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Minecraft mc;
    private final AdvancementList advancementList = new AdvancementList();
    private final Map<Advancement, AdvancementProgress> advancementToProgress = Maps.newHashMap();
    @Nullable
    private IListener listener;
    @Nullable
    private Advancement selectedTab;

    public ClientAdvancementManager(Minecraft minecraft) {
        this.mc = minecraft;
    }

    public void read(SAdvancementInfoPacket sAdvancementInfoPacket) {
        if (sAdvancementInfoPacket.isFirstSync()) {
            this.advancementList.clear();
            this.advancementToProgress.clear();
        }
        this.advancementList.removeAll(sAdvancementInfoPacket.getAdvancementsToRemove());
        this.advancementList.loadAdvancements(sAdvancementInfoPacket.getAdvancementsToAdd());
        for (Map.Entry<ResourceLocation, AdvancementProgress> entry : sAdvancementInfoPacket.getProgressUpdates().entrySet()) {
            Advancement advancement = this.advancementList.getAdvancement(entry.getKey());
            if (advancement != null) {
                AdvancementProgress advancementProgress = entry.getValue();
                advancementProgress.update(advancement.getCriteria(), advancement.getRequirements());
                this.advancementToProgress.put(advancement, advancementProgress);
                if (this.listener != null) {
                    this.listener.onUpdateAdvancementProgress(advancement, advancementProgress);
                }
                if (sAdvancementInfoPacket.isFirstSync() || !advancementProgress.isDone() || advancement.getDisplay() == null || !advancement.getDisplay().shouldShowToast()) continue;
                this.mc.getToastGui().add(new AdvancementToast(advancement));
                continue;
            }
            LOGGER.warn("Server informed client about progress for unknown advancement {}", (Object)entry.getKey());
        }
    }

    public AdvancementList getAdvancementList() {
        return this.advancementList;
    }

    public void setSelectedTab(@Nullable Advancement advancement, boolean bl) {
        ClientPlayNetHandler clientPlayNetHandler = this.mc.getConnection();
        if (clientPlayNetHandler != null && advancement != null && bl) {
            clientPlayNetHandler.sendPacket(CSeenAdvancementsPacket.openedTab(advancement));
        }
        if (this.selectedTab != advancement) {
            this.selectedTab = advancement;
            if (this.listener != null) {
                this.listener.setSelectedTab(advancement);
            }
        }
    }

    public void setListener(@Nullable IListener iListener) {
        this.listener = iListener;
        this.advancementList.setListener(iListener);
        if (iListener != null) {
            for (Map.Entry<Advancement, AdvancementProgress> entry : this.advancementToProgress.entrySet()) {
                iListener.onUpdateAdvancementProgress(entry.getKey(), entry.getValue());
            }
            iListener.setSelectedTab(this.selectedTab);
        }
    }

    public static interface IListener
    extends AdvancementList.IListener {
        public void onUpdateAdvancementProgress(Advancement var1, AdvancementProgress var2);

        public void setSelectedTab(@Nullable Advancement var1);
    }
}

