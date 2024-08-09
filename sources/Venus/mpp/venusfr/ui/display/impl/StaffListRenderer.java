/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import mpp.venusfr.command.staffs.StaffStorage;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.ui.display.ElementRenderer;
import mpp.venusfr.ui.display.ElementUpdater;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.drag.Dragging;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.Scissor;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.utils.text.GradientUtil;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.GameType;

public class StaffListRenderer
implements ElementRenderer,
ElementUpdater {
    private final Dragging dragging;
    private final ResourceLocation logo = new ResourceLocation("venusfr/images/hud/staff.png");
    private float iconSizeX = 10.0f;
    private float iconSizeY = 10.0f;
    private final List<Staff> staffPlayers = new ArrayList<Staff>();
    private final Pattern namePattern = Pattern.compile("^\\w{3,16}$");
    private final Pattern prefixMatches = Pattern.compile(".*(mod|der|adm|help|wne|\u0445\u0435\u043b\u043f|\u0430\u0434\u043c|\u043f\u043e\u0434\u0434\u0435\u0440\u0436\u043a\u0430|\u043a\u0443\u0440\u0430|own|taf|curat|dev|supp|yt|\u0441\u043e\u0442\u0440\u0443\u0434).*");
    private float width;
    private float height;

    @Override
    public void update(EventUpdate eventUpdate) {
        this.staffPlayers.clear();
        for (ScorePlayerTeam scorePlayerTeam : StaffListRenderer.mc.world.getScoreboard().getTeams().stream().sorted(Comparator.comparing(Team::getName)).toList()) {
            Object object;
            String string = scorePlayerTeam.getMembershipCollection().toString().replaceAll("[\\[\\]]", "");
            boolean bl = true;
            for (NetworkPlayerInfo networkPlayerInfo : mc.getConnection().getPlayerInfoMap()) {
                if (!networkPlayerInfo.getGameProfile().getName().equals(string)) continue;
                bl = false;
            }
            if (!this.namePattern.matcher(string).matches() || string.equals(StaffListRenderer.mc.player.getName().getString())) continue;
            if (!bl && (this.prefixMatches.matcher(scorePlayerTeam.getPrefix().getString().toLowerCase(Locale.ROOT)).matches() || StaffStorage.isStaff(string))) {
                object = new Staff(scorePlayerTeam.getPrefix(), string, false, Status.NONE);
                this.staffPlayers.add((Staff)object);
            }
            if (!bl || scorePlayerTeam.getPrefix().getString().isEmpty()) continue;
            object = new Staff(scorePlayerTeam.getPrefix(), string, true, Status.VANISHED);
            this.staffPlayers.add((Staff)object);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack matrixStack = eventDisplay.getMatrixStack();
        float f = this.dragging.getX();
        float f2 = this.dragging.getY();
        float f3 = 5.0f;
        float f4 = 6.5f;
        StringTextComponent stringTextComponent = GradientUtil.gradient("Staff");
        String string = "Staff";
        float f5 = f2;
        this.drawStyledRect(f, f5, this.width, this.height, 5.0f);
        DisplayUtils.drawShadow(f, f2, this.width, this.height, 15, ColorUtils.rgba(21, 24, 40, 165));
        Scissor.push();
        try {
            Scissor.setFromComponentCoordinates(f, f2, this.width, this.height);
            Fonts.sfui.drawText(matrixStack, string, f + f3, f2 + f3 + 1.0f, ColorUtils.rgb(255, 255, 255), f4);
            float f6 = f + this.width - this.iconSizeX - f3;
            DisplayUtils.drawImage(this.logo, f6, f2 + 4.0f, this.iconSizeX, this.iconSizeY, ColorUtils.getColor(1));
            f2 += f4 + f3 * 2.0f;
            float f7 = Fonts.sfMedium.getWidth(stringTextComponent, f4) + f3 * 2.0f;
            float f8 = f4 + f3 * 2.0f;
            for (Staff staff : this.staffPlayers) {
                ITextComponent iTextComponent = staff.getPrefix();
                float f9 = Fonts.sfMedium.getWidth(iTextComponent, f4);
                String string2 = (iTextComponent.getString().isEmpty() ? "" : " ") + staff.getName();
                float f10 = Fonts.sfMedium.getWidth(string2, f4);
                float f11 = f9 + f10 + Fonts.sfMedium.getWidth(staff.getStatus().string, f4) + f3 * 3.0f;
                Fonts.sfMedium.drawText(matrixStack, iTextComponent, f + f3, f2, f4, 255);
                Fonts.sfMedium.drawText(matrixStack, string2, f + f3 + f9, f2, -1, f4);
                Fonts.sfMedium.drawText(matrixStack, staff.getStatus().string, f + this.width - f3 - Fonts.sfMedium.getWidth(staff.getStatus().string, f4), f2, staff.getStatus().color, f4);
                if (f11 > f7) {
                    f7 = f11;
                }
                f2 += f4 + f3;
                f8 += f4 + f3;
            }
            this.width = Math.max(f7, 80.0f);
            this.height = f8 + 2.5f;
            this.dragging.setWidth(this.width);
            this.dragging.setHeight(this.height);
        } finally {
            Scissor.pop();
        }
    }

    private void drawStyledRect(float f, float f2, float f3, float f4, float f5) {
        DisplayUtils.drawRoundedRect(f - 0.5f, f2 - 0.5f, f3 + 1.0f, f4 + 1.0f, f5 + 0.5f, ColorUtils.setAlpha(ColorUtils.rgb(21, 24, 40), 90));
        DisplayUtils.drawRoundedRect(f, f2, f3, f4, f5, ColorUtils.rgba(21, 24, 40, 90));
    }

    public StaffListRenderer(Dragging dragging) {
        this.dragging = dragging;
    }

    public static class Staff {
        ITextComponent prefix;
        String name;
        boolean isSpec;
        Status status;

        public void updateStatus() {
            for (NetworkPlayerInfo networkPlayerInfo : IMinecraft.mc.getConnection().getPlayerInfoMap()) {
                if (!networkPlayerInfo.getGameProfile().getName().equals(this.name)) continue;
                if (networkPlayerInfo.getGameType() == GameType.SPECTATOR) {
                    return;
                }
                this.status = Status.NONE;
                return;
            }
            this.status = Status.VANISHED;
        }

        public Staff(ITextComponent iTextComponent, String string, boolean bl, Status status2) {
            this.prefix = iTextComponent;
            this.name = string;
            this.isSpec = bl;
            this.status = status2;
        }

        public ITextComponent getPrefix() {
            return this.prefix;
        }

        public String getName() {
            return this.name;
        }

        public boolean isSpec() {
            return this.isSpec;
        }

        public Status getStatus() {
            return this.status;
        }

        public void setPrefix(ITextComponent iTextComponent) {
            this.prefix = iTextComponent;
        }

        public void setName(String string) {
            this.name = string;
        }

        public void setSpec(boolean bl) {
            this.isSpec = bl;
        }

        public void setStatus(Status status2) {
            this.status = status2;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof Staff)) {
                return true;
            }
            Staff staff = (Staff)object;
            if (!staff.canEqual(this)) {
                return true;
            }
            if (this.isSpec() != staff.isSpec()) {
                return true;
            }
            ITextComponent iTextComponent = this.getPrefix();
            ITextComponent iTextComponent2 = staff.getPrefix();
            if (iTextComponent == null ? iTextComponent2 != null : !iTextComponent.equals(iTextComponent2)) {
                return true;
            }
            String string = this.getName();
            String string2 = staff.getName();
            if (string == null ? string2 != null : !string.equals(string2)) {
                return true;
            }
            Status status2 = this.getStatus();
            Status status3 = staff.getStatus();
            return status2 == null ? status3 != null : !((Object)((Object)status2)).equals((Object)status3);
        }

        protected boolean canEqual(Object object) {
            return object instanceof Staff;
        }

        public int hashCode() {
            int n = 59;
            int n2 = 1;
            n2 = n2 * 59 + (this.isSpec() ? 79 : 97);
            ITextComponent iTextComponent = this.getPrefix();
            n2 = n2 * 59 + (iTextComponent == null ? 43 : iTextComponent.hashCode());
            String string = this.getName();
            n2 = n2 * 59 + (string == null ? 43 : string.hashCode());
            Status status2 = this.getStatus();
            n2 = n2 * 59 + (status2 == null ? 43 : ((Object)((Object)status2)).hashCode());
            return n2;
        }

        public String toString() {
            return "StaffListRenderer.Staff(prefix=" + this.getPrefix() + ", name=" + this.getName() + ", isSpec=" + this.isSpec() + ", status=" + this.getStatus() + ")";
        }
    }

    public static enum Status {
        NONE("", -1),
        VANISHED("V", ColorUtils.rgb(254, 68, 68));

        public final String string;
        public final int color;

        private Status(String string2, int n2) {
            this.string = string2;
            this.color = n2;
        }
    }
}

