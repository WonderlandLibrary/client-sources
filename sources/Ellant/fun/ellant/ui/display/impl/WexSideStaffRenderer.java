package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.command.staffs.StaffStorage;
import fun.ellant.events.EventDisplay;
import fun.ellant.events.EventUpdate;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.display.ElementUpdater;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Stencil;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameType;
import org.lwjgl.opengl.GL11;
import ru.hogoshi.Animation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;


@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class WexSideStaffRenderer implements ElementRenderer, ElementUpdater {

    private final ResourceLocation staffpng = new ResourceLocation("expensive/images/hud/staff.png");

    final Dragging dragging;


    private final List<Staff> staffPlayers = new ArrayList<>();
    private final Pattern namePattern = Pattern.compile("^\\w{3,16}$");
    private final Pattern prefixMatches = Pattern.compile(".*(mod|der|adm|help|wne|хелп|адм|поддержка|кура|own|taf|curat|dev|supp|yt|сотруд).*");
    private final Animation animation = new Animation();

    @Override
    public void update(EventUpdate e) {
        staffPlayers.clear();

        for (ScorePlayerTeam team : mc.world.getScoreboard().getTeams().stream().sorted(Comparator.comparing(Team::getName)).toList()) {
            String name = team.getMembershipCollection().toString().replaceAll("[\\[\\]]", "");
            boolean vanish = true;
            for (NetworkPlayerInfo info : mc.getConnection().getPlayerInfoMap()) {
                if (info.getGameProfile().getName().equals(name)) {
                    vanish = false;
                }
            }
            if (namePattern.matcher(name).matches() && !name.equals(mc.player.getName().getString())) {
                if (!vanish) {
                    if (prefixMatches.matcher(team.getPrefix().getString().toLowerCase(Locale.ROOT)).matches() || StaffStorage.isStaff(name)) {
                        Staff staff = new Staff(team.getPrefix(), name, false, Status.NONE);
                        staffPlayers.add(staff);
                    }
                }
                if (vanish && !team.getPrefix().getString().isEmpty()) {
                    Staff staff = new Staff(team.getPrefix(), name, true, Status.VANISHED);
                    staffPlayers.add(staff);
                }
            }
        }
    }

    float width;
    float height;

    @Override
    public void render(EventDisplay eventDisplay) {

        LivingEntity entity = null;
        float posX = dragging.getX();
        float posY = dragging.getY();
        float padding = 5;
        float fontSize = 6.5f;
        MatrixStack ms = eventDisplay.getMatrixStack();
        ITextComponent name = GradientUtil.white("Staff List");


        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();


        DisplayUtils.drawShadow(posX, posY, 60, 16, 4, ColorUtils.rgba(32, 35, 70, 156));
        DisplayUtils.drawRoundedRect(posX, posY, 60, 16, 2, ColorUtils.rgba(0,0,0,140));
        Fonts.montserrat.drawText(ms, "Staff List", posX + 38 / 2, posY + padding - 0.5f, -1, 6f, 0.07f);
        DisplayUtils.drawImage(staffpng, posX + 4, posY + 1, 12, 12, ColorUtils.getColor(0));
        posY += fontSize + padding * 2;

        float maxWidth = Fonts.sfMedium.getWidth(name, fontSize) + padding * 2;
        float localHeight = fontSize + padding * 2;

        posY += 3.5f;
        for (WexSideStaffRenderer.Staff f : staffPlayers) {

            ITextComponent prefix = f.getPrefix();
            float prefixWidth = Fonts.sfMedium.getWidth(prefix, fontSize);
            String staff = (prefix.getString().isEmpty() ? "" : " ") + f.getName();
            float nameWidth = Fonts.sfMedium.getWidth(staff, fontSize);


            float localWidth = prefixWidth + nameWidth + Fonts.sfMedium.getWidth(f.getStatus().string, fontSize) + padding * 3;

            DisplayUtils.drawShadow(posX + 13, posY - 1, prefixWidth + nameWidth + 4, 10.0f, 4, ColorUtils.rgba(32, 35, 90, 156));
            DisplayUtils.drawRoundedRect(posX + 13, posY - 1, prefixWidth + nameWidth + 4, 10.0f, 2, ColorUtils.rgba(0, 0, 0, 140));
            DisplayUtils.drawShadow(posX + 1, posY - 1, 10, 10, 5, ColorUtils.rgba(30,32,90, 156));
            DisplayUtils.drawRoundedRect(posX + 1, posY - 1, 10, 10, 5, ColorUtils.rgba(0,0,0, 140));
            Fonts.montserrat.drawText(ms, prefix, posX + 15, posY + 1f, 6, 255);
            Fonts.montserrat.drawText(ms, staff, posX + prefixWidth + 14, posY + 1f, -1, 6, 0.07f);
            DisplayUtils.drawCircle(posX + 6, posY + 4, 4.5f, f.getStatus().color);

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
    public static class Staff {
        ITextComponent prefix;
        String name;
        boolean isSpec;
        Status status;

        public void updateStatus() {
            for (NetworkPlayerInfo info : mc.getConnection().getPlayerInfoMap()) {
                if (info.getGameProfile().getName().equals(name)) {
                    if (info.getGameType() == GameType.SPECTATOR) {
                        return;
                    }
                    status = Status.NONE;
                    return;
                }
            }
            status = Status.VANISHED;
        }
    }

    public enum Status {
        NONE("", ColorUtils.setAlpha(ColorUtils.getColor(0), 128)),
        VANISHED("", ColorUtils.setAlpha(ColorUtils.getColor(0), 255));
        public final String string;
        public final int color;

        Status(String string, int color) {
            this.string = string;
            this.color = color;
        }
    }

    private void drawStyledRect(float x,
                                float y,
                                float width,
                                float height,
                                float radius) {

        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(20, 20, 30, 255));
    }
}
