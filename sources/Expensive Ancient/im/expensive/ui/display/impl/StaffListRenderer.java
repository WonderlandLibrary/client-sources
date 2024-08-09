package im.expensive.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.Expensive;
import im.expensive.command.staffs.StaffStorage;
import im.expensive.events.EventDisplay;
import im.expensive.events.EventUpdate;
import im.expensive.functions.impl.render.HUD;
import im.expensive.ui.display.ElementRenderer;
import im.expensive.ui.display.ElementUpdater;
import im.expensive.ui.styles.Style;
import im.expensive.utils.drag.Dragging;
import im.expensive.utils.math.Vector4i;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;
import im.expensive.utils.text.GradientUtil;
import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.ITextComponent;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class StaffListRenderer implements ElementRenderer, ElementUpdater {

    final Dragging dragging;


    private Set<StaffData> staffPlayers = new LinkedHashSet<>();
    private final Pattern namePattern = Pattern.compile("^\\w{3,16}$");
    private final Pattern prefixMatches = Pattern.compile(".*(mod|der|adm|help|wne|хелп|адм|поддержка|кура|own|taf|curat|dev|supp|yt|сотруд).*");
    @Override
    public void update(EventUpdate e) {
        staffPlayers.clear();
        mc.world.getScoreboard().getTeams().stream().sorted(Comparator.comparing(Team::getName)).toList().forEach(team -> {
            String staffName = team.getMembershipCollection().toString().replaceAll("[\\[\\]]", "");
            boolean vanish = true;

            if (mc.getConnection() != null) {
                for (NetworkPlayerInfo info : mc.getConnection().getPlayerInfoMap()) {
                    if (info.getGameProfile().getName().equals(staffName)) {
                        vanish = false;
                    }
                }
            }

            if (namePattern.matcher(staffName).matches() && !staffName.equals(mc.player.getName().getString())) {
                if (!vanish) {
                    if (prefixMatches.matcher(team.getPrefix().getString().toLowerCase(Locale.ROOT)).matches() || StaffStorage.isStaff(staffName)) {
                        staffPlayers.add(new StaffData(team.getPrefix(), staffName, StaffData.Status.NONE));
                    }
                }
                if (vanish && !team.getPrefix().getString().isEmpty()) {
                    staffPlayers.add(new StaffData(team.getPrefix(), staffName, StaffData.Status.VANISHED));
                }
            }
        });

        this.staffPlayers = staffPlayers.stream()
                .sorted(Comparator.comparing(this::getPriority))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    float width;
    float height;

    @Override
    public void render(EventDisplay eventDisplay) {

        float posX = dragging.getX();
        float posY = dragging.getY();
        float padding = 5;
        float fontSize = 6.5f;
        MatrixStack ms = eventDisplay.getMatrixStack();
        ITextComponent name = GradientUtil.gradient("StaffList");


        drawStyledRect(posX, posY, width, height, 4);
        Fonts.sfui.drawCenteredText(ms, name, posX + width / 2, posY + padding + 1f, fontSize);

        posY += fontSize + padding * 2;

        float maxWidth = Fonts.sfMedium.getWidth(name, fontSize) + padding * 2;
        float localHeight = fontSize + padding * 2;

        DisplayUtils.drawRectHorizontalW(posX + 0.5f, posY, width - 1, 2.5f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.25f)));
        posY += 3.5f;
        for (StaffData f : staffPlayers) {
            ITextComponent prefix = f.getPrefix();

            float prefixWidth = Fonts.sfMedium.getWidth(prefix, fontSize);
            String staff = (prefix.getString().isEmpty() ? "" : " ") + f.getName();
            float nameWidth = Fonts.sfMedium.getWidth(staff, fontSize);


            float localWidth = prefixWidth + nameWidth + Fonts.sfMedium.getWidth(f.getStatus().string, fontSize) + padding * 3;

            Fonts.sfMedium.drawText(ms, prefix, posX + padding, posY, fontSize, 255);
            Fonts.sfMedium.drawText(ms, staff, posX + padding + prefixWidth, posY, -1, fontSize);
            Fonts.sfMedium.drawText(ms, f.getStatus().string, posX + width - padding - Fonts.sfMedium.getWidth(f.getStatus().string, fontSize), posY, f.getStatus().color, fontSize);

            if (localWidth > maxWidth) {
                maxWidth = localWidth;
            }

            posY += fontSize + padding;
            localHeight += fontSize + padding;
        }

        width = Math.max(maxWidth, 80);
        height = localHeight + 2.5f;
        dragging.setWidth(width);
        dragging.setHeight(height);
    }

    @AllArgsConstructor
    @Data
    public static class StaffData {
        ITextComponent prefix;
        String name;
        Status status;

        public enum Status {
            NONE("", -1),
            VANISHED("V", ColorUtils.rgb(254, 68, 68));
            public final String string;
            public final int color;

            Status(String string, int color) {
                this.string = string;
                this.color = color;
            }
        }

        @Override
        public String toString() {
            return prefix.getString();
        }
    }


    private int getPriority(StaffData staffData) {
        return switch (staffData.toString()) {
            case "admin", "админ" -> 0;
            case "ml.admin" -> 1;
            case "gl.moder" -> 2;
            case "st.moder", "s.moder" -> 3;
            case "moder", "модератор", "куратор" -> 4;
            case "j.moder" -> 5;
            case "st.helper" -> 6;
            case "helper+" -> 7;
            case "helper" -> 8;
            case "yt+" -> 9;
            case "yt" -> 10;
            default -> 11;
        };
    }

    private void drawStyledRect(float x,
                                float y,
                                float width,
                                float height,
                                float radius) {
        Vector4i vector4i = new Vector4i(HUD.getColor(0), HUD.getColor(90), HUD.getColor(180), HUD.getColor(170));
        DisplayUtils.drawShadow(x, y, width, height, 10, vector4i.x, vector4i.y, vector4i.z, vector4i.w);
        DisplayUtils.drawGradientRound(x, y, width, height, radius + 0.5f, vector4i.x, vector4i.y, vector4i.z, vector4i.w); // outline
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 230));
    }
}
