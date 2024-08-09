/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.social;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.social.FilterListEntry;
import net.minecraft.client.gui.social.SocialInteractionsScreen;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.client.network.play.NetworkPlayerInfo;

public class FilterList
extends AbstractOptionList<FilterListEntry> {
    private final SocialInteractionsScreen field_244650_a;
    private final Minecraft field_244651_o;
    private final List<FilterListEntry> field_244652_p = Lists.newArrayList();
    @Nullable
    private String field_244653_q;

    public FilterList(SocialInteractionsScreen socialInteractionsScreen, Minecraft minecraft, int n, int n2, int n3, int n4, int n5) {
        super(minecraft, n, n2, n3, n4, n5);
        this.field_244650_a = socialInteractionsScreen;
        this.field_244651_o = minecraft;
        this.func_244605_b(true);
        this.func_244606_c(true);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        double d = this.field_244651_o.getMainWindow().getGuiScaleFactor();
        RenderSystem.enableScissor((int)((double)this.getRowLeft() * d), (int)((double)(this.height - this.y1) * d), (int)((double)(this.getScrollbarPosition() + 6) * d), (int)((double)(this.height - (this.height - this.y1) - this.y0 - 4) * d));
        super.render(matrixStack, n, n2, f);
        RenderSystem.disableScissor();
    }

    public void func_244759_a(Collection<UUID> collection, double d) {
        this.field_244652_p.clear();
        for (UUID uUID : collection) {
            NetworkPlayerInfo networkPlayerInfo = this.field_244651_o.player.connection.getPlayerInfo(uUID);
            if (networkPlayerInfo == null) continue;
            this.field_244652_p.add(new FilterListEntry(this.field_244651_o, this.field_244650_a, networkPlayerInfo.getGameProfile().getId(), networkPlayerInfo.getGameProfile().getName(), networkPlayerInfo::getLocationSkin));
        }
        this.func_244661_g();
        this.field_244652_p.sort(FilterList::lambda$func_244759_a$0);
        this.replaceEntries(this.field_244652_p);
        this.setScrollAmount(d);
    }

    private void func_244661_g() {
        if (this.field_244653_q != null) {
            this.field_244652_p.removeIf(this::lambda$func_244661_g$1);
            this.replaceEntries(this.field_244652_p);
        }
    }

    public void func_244658_a(String string) {
        this.field_244653_q = string;
    }

    public boolean func_244660_f() {
        return this.field_244652_p.isEmpty();
    }

    public void func_244657_a(NetworkPlayerInfo networkPlayerInfo, SocialInteractionsScreen.Mode mode) {
        UUID uUID = networkPlayerInfo.getGameProfile().getId();
        for (FilterListEntry filterListEntry : this.field_244652_p) {
            if (!filterListEntry.func_244640_c().equals(uUID)) continue;
            filterListEntry.func_244641_c(true);
            return;
        }
        if ((mode == SocialInteractionsScreen.Mode.ALL || this.field_244651_o.func_244599_aA().func_244756_c(uUID)) && (Strings.isNullOrEmpty(this.field_244653_q) || networkPlayerInfo.getGameProfile().getName().toLowerCase(Locale.ROOT).contains(this.field_244653_q))) {
            FilterListEntry filterListEntry = new FilterListEntry(this.field_244651_o, this.field_244650_a, networkPlayerInfo.getGameProfile().getId(), networkPlayerInfo.getGameProfile().getName(), networkPlayerInfo::getLocationSkin);
            this.addEntry(filterListEntry);
            this.field_244652_p.add(filterListEntry);
        }
    }

    public void func_244659_a(UUID uUID) {
        for (FilterListEntry filterListEntry : this.field_244652_p) {
            if (!filterListEntry.func_244640_c().equals(uUID)) continue;
            filterListEntry.func_244641_c(false);
            return;
        }
    }

    private boolean lambda$func_244661_g$1(FilterListEntry filterListEntry) {
        return !filterListEntry.func_244636_b().toLowerCase(Locale.ROOT).contains(this.field_244653_q);
    }

    private static int lambda$func_244759_a$0(FilterListEntry filterListEntry, FilterListEntry filterListEntry2) {
        return filterListEntry.func_244636_b().compareToIgnoreCase(filterListEntry2.func_244636_b());
    }
}

