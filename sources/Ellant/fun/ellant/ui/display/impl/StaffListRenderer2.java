package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.command.staffs.StaffStorage;
import fun.ellant.events.EventDisplay;
import fun.ellant.events.EventUpdate;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.display.ElementUpdater;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.KawaseBlur;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameType;
import ru.hogoshi.Animation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class StaffListRenderer2 implements ElementRenderer, ElementUpdater {

    final Dragging dragging;
    final ResourceLocation logo = new ResourceLocation("expensive/images/hud/staff1.png");
    final float iconSize = 10;

    private final List<Staff> staffPlayers = new ArrayList<>();
    private final Pattern namePattern = Pattern.compile("^\\w{3,16}$");
    private final Pattern prefixMatches = Pattern.compile(".*(mod|der|adm|help|wne|хелп|адм|поддержка|кура|own|taf|curat|dev|supp|yt|сотруд|ст.сотруд|мл.сотру|гл.сотру|admin|owner|овн|d.mod|д.мод|ᴀᴅᴍ|ᴄᴜʀᴀᴛ|ᴍʟ.ᴀᴅᴍ|тех.сотру).*");
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

        float posX = dragging.getX();
        float posY = dragging.getY();
        float padding = 5;
        float fontSize = 6.5f;
        MatrixStack ms = eventDisplay.getMatrixStack();
        ITextComponent name = GradientUtil.gradient("Staff-List", ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));

        // Увеличиваем размытие
        int blurPasses = 3;
        int blurRadius = 4;

        // Вычисляем максимальную ширину и высоту перед рендерингом
        float maxWidth = Fonts.sfMedium.getWidth(name, fontSize) + padding * 2;
        float localHeight = fontSize + padding * 2;
        float tempPosY = posY + fontSize + padding * 2 + 3.5f;

        for (StaffListRenderer2.Staff f : staffPlayers) {
            ITextComponent prefix = f.getPrefix();
            float prefixWidth = Fonts.sfMedium.getWidth(prefix, fontSize);
            String staff = (prefix.getString().isEmpty() ? "" : " ") + f.getName();
            float nameWidth = Fonts.sfMedium.getWidth(staff, fontSize);

            float localWidth = prefixWidth + nameWidth + Fonts.sfMedium.getWidth(f.getStatus().string, fontSize) + padding * 3;

            if (localWidth > maxWidth) {
                maxWidth = localWidth;
            }

            tempPosY += fontSize + padding;
            localHeight += fontSize + padding;
        }

        width = Math.max(maxWidth, 90);
        height = localHeight + 4.5f;
        dragging.setWidth(width);
        dragging.setHeight(height);

        // Рендерим размытие на фоне
        KawaseBlur.blur.updateBlur(blurPasses, blurRadius);
        float finalPosY = posY;
        KawaseBlur.blur.render(() -> {
            DisplayUtils.drawRoundedRect(posX, finalPosY, width + 10, height - 10, 2, ColorUtils.rgba(21, 21, 21, 155));
        });

        // Отрисовываем текст и иконку поверх размытого фона
        DisplayUtils.drawImage(logo, posX + padding, posY + padding, iconSize, iconSize, ColorUtils.rgb(255, 255, 255));
        Fonts.sfui.drawCenteredText(ms, name, posX + width / 2, posY + padding + 1f, fontSize);

        posY += fontSize + padding * 2;
        DisplayUtils.drawRectHorizontalW(posX + 0.5f, posY, width - 1, 2.5f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.25f)));
        DisplayUtils.drawRectVerticalW(posX + 20.0f, posY - 13.5f, 1, 11.0f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.75f)));
        posY += 3.5f;

        for (StaffListRenderer2.Staff f : staffPlayers) {
            ITextComponent prefix = f.getPrefix();
            float prefixWidth = Fonts.sfMedium.getWidth(prefix, fontSize);
            String staff = (prefix.getString().isEmpty() ? "" : " ") + f.getName();
            float nameWidth = Fonts.sfMedium.getWidth(staff, fontSize);

            Fonts.sfMedium.drawText(ms, prefix, posX + padding, posY, fontSize, 255);
            Fonts.sfMedium.drawText(ms, staff, posX + padding + prefixWidth, posY, ColorUtils.rgb(210, 210, 210), fontSize);
            Fonts.sfMedium.drawText(ms, f.getStatus().string, posX + width - padding - Fonts.sfMedium.getWidth(f.getStatus().string, fontSize), posY, f.getStatus().color, fontSize);

            posY += fontSize + padding;
        }
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
        NONE("", -1),
        VANISHED("V", ColorUtils.rgb(254, 68, 68));
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

        // DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, radius + 0.5f, ColorUtils.getColor(0)); // outline
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(0, 0, 0, 255));
    }
}
