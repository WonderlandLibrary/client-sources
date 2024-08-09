package dev.excellent.client.module.impl.misc;

import com.mojang.authlib.GameProfile;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.value.impl.DragValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import org.joml.Vector2d;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ModuleInfo(name = "Staff Activity", description = "Отображает список активного персонала на сервере.", category = Category.MISC)
public class StaffActivity extends Module {
    private static final Pattern pattern = Pattern.compile("^\\w{3,16}$");
    public final DragValue position = new DragValue("Position", this, new Vector2d(100, 50));
    private final Animation expandAnimation = new Animation(Easing.EASE_OUT_QUART, 100);
    private final Font interbold14 = Fonts.INTER_BOLD.get(14);
    private final Font interbold12 = Fonts.INTER_BOLD.get(12);
    private final Font interbold10 = Fonts.INTER_BOLD.get(10);
    static List<String> staffPrefix = Arrays.asList(
            "хелпер",
            "helper",
            "модер",
            "developer",
            "moder",
            "админ",
            "аgмин",
            "admin",
            "ют",
            "ютуб",
            "yt",
            "youtube",
            "помощник",
            "сотрудник",
            "заместитель",
            "куратор",
            "стажёр"
    );
    private List<ScorePlayerTeam> vanishPlayers = new ArrayList<>();
    private List<ScorePlayerTeam> onlinePlayers = new ArrayList<>();
    private List<ScorePlayerTeam> spectatePlayers = new ArrayList<>();

    public final Listener<UpdateEvent> onUpdate = event -> {
        if (mc.player.ticksExisted % 10 == 0) {
            vanishPlayers = getVanishPlayers();
            onlinePlayers = getOnlinePlayers();
            spectatePlayers = getSpectatePlayers();
        }
    };
    public final Listener<Render2DEvent> onRender2D = event -> {
        HashMap<ScorePlayerTeam, Action> all = new LinkedHashMap<>();
        for (ScorePlayerTeam player : onlinePlayers) {
            all.putIfAbsent(player, Action.ONLINE);
        }
        for (ScorePlayerTeam player : vanishPlayers) {
            all.putIfAbsent(player, Action.VANISH);
        }
        for (ScorePlayerTeam player : spectatePlayers) {
            all.putIfAbsent(player, Action.SPEC);
        }

        boolean expand = !(vanishPlayers.isEmpty() && onlinePlayers.isEmpty() && spectatePlayers.isEmpty());

        String name = "Staff activity";

        double width = position.size.x = all.entrySet().stream()
                .mapToDouble(staff -> {
                    String[] staffName = staff.getKey().getMembershipCollection().toString().replaceAll("[\\[\\]]", "").replaceAll(" ", "").trim().split(",");
                    String finalName = Arrays.stream(staffName).filter(z -> excellent.getStaffManager().isStaff(z)).findFirst().orElse(null);
                    return Math.max(40 + interbold14.getWidth("Staff activity"), 5 + 40 + interbold12.getWidth(staff.getKey().getPrefix().getString()) + (Objects.requireNonNull(TextFormatting.getTextWithoutFormattingCodes(staff.getKey().getPrefix().getString())).isEmpty() ? 0 : 3) + interbold12.getWidth(TextFormatting.getTextWithoutFormattingCodes(Objects.requireNonNullElseGet(finalName, () -> Arrays.asList(staff.getKey().getMembershipCollection().toArray()).toString().replaceAll("[\\[\\]]", ""))).trim() + interbold10.getWidth(staff.getValue().getAction()) + 5));
                })
                .max()
                .orElse(40 + interbold14.getWidth("Staff activity"));

        expandAnimation.run(position.size.y);

        double height = expandAnimation.getValue();

        position.size.y = 3 + interbold14.getHeight() + 3;

        if (mc.gameSettings.showDebugInfo) return;

        double x = position.position.x;
        double y = position.position.y;

        double expaned = 14;
        position.size.y = expaned;

        RenderUtil.renderClientRect(event.getMatrix(), (float) x, (float) y, (float) width, (float) height, expand, expaned);

        interbold14.drawCenter(event.getMatrix(), name, x + width / 2F, y + (expaned / 2F) - (interbold14.getHeight() / 2F), -1);

        if (!expand || mc.player == null || mc.world == null || mc.getCurrentServerData() == null) return;

        int i = 0;
        for (Map.Entry<ScorePlayerTeam, Action> player : all.entrySet()) {
            String[] staffName = player.getKey().getMembershipCollection().toString().replaceAll("[\\[\\]]", "").replaceAll(" ", "").trim().split(",");
            String finalName = Arrays.stream(staffName).filter(z -> excellent.getStaffManager().isStaff(z)).findFirst().orElse(null);
            double prefixWidth = (Objects.requireNonNull(TextFormatting.getTextWithoutFormattingCodes(player.getKey().getPrefix().getString())).isEmpty() ? 0 : 3) + interbold12.getWidth(TextFormatting.getTextWithoutFormattingCodes(player.getKey().getPrefix().getString()));
            String prefix = player.getKey().getPrefix().getString().trim();

            boolean check = player.getKey().getPrefix().getString().startsWith("§");
            if (check) {
                interbold12.drawShadow(event.getMatrix(), TextFormatting.getTextWithoutFormattingCodes(prefix), x + 5, y + 10 + interbold14.getHeight() + i, TextFormatting.GRAY.getColor());
            } else {
                RenderUtil.drawITextComponent(event.getMatrix(), player.getKey().getPrefix(), interbold12, x + 5, y + 10 + interbold14.getHeight() + i, -1, true);
            }
            RenderUtil.drawITextComponent(event.getMatrix(), player.getKey().getDisplayName(), interbold12, x + 5, y + 10 + interbold14.getHeight() + i, -1, true);
            interbold12.drawShadow(event.getMatrix(), " " + TextFormatting.getTextWithoutFormattingCodes(Objects.requireNonNullElseGet(finalName, () -> Arrays.asList(player.getKey().getMembershipCollection().toArray()).toString().replaceAll("[\\[\\]]", ""))).trim(), x + 5 + prefixWidth, y + 10 + interbold14.getHeight() + i, -1);
            interbold10.drawRightOutline(event.getMatrix(), player.getValue().getAction(), x + width - 5, y + 10.5 + interbold14.getHeight() + i, player.getValue().getColor().hashCode());

            i += 10;
            position.size.y = 10 + interbold14.getHeight() + i + 2;
        }

        all.clear();

    };

    private List<String> getAllPlayers() {
        return mc.player.connection.getPlayerInfoMap().stream()
                .map(NetworkPlayerInfo::getGameProfile)
                .map(GameProfile::getName)
                .filter(profileName -> pattern.matcher(profileName).matches())
                .collect(Collectors.toList());
    }

    public static List<ScorePlayerTeam> getSpectatePlayers() {
        List<ScorePlayerTeam> list = new ArrayList<>();
        for (NetworkPlayerInfo player : mc.player.connection.getPlayerInfoMap()) {
            if (mc.isSingleplayer() || player.getPlayerTeam() == null) break;
            if (check(player.getPlayerTeam())) {
                if (player.getGameType() == GameType.SPECTATOR) {
                    list.add(player.getPlayerTeam());
                }
            }
        }
        return list;
    }

    public static List<ScorePlayerTeam> getOnlinePlayers() {
        List<ScorePlayerTeam> list = new ArrayList<>();
        for (NetworkPlayerInfo player : mc.player.connection.getPlayerInfoMap()) {
            if (mc.isSingleplayer() || player.getPlayerTeam() == null) break;
            if (check(player.getPlayerTeam()) || player.getPlayerTeam().getPrefix().getString().contains("YT")) {
                if (player.getGameType() == GameType.SPECTATOR) {
                    continue;
                }
                list.add(player.getPlayerTeam());
            }
        }
        return list;
    }

    private List<ScorePlayerTeam> getVanishPlayers() {
        List<ScorePlayerTeam> list = new ArrayList<>();
        for (ScorePlayerTeam player : mc.world.getScoreboard().getTeams()) {
            if (player.getPrefix().getString().isEmpty() || mc.isSingleplayer())
                continue;
            String name = TextFormatting.getTextWithoutFormattingCodes(Arrays.asList(player.getMembershipCollection().toArray()).toString().replaceAll("[\\[\\]]", ""));
            if (check(player)) {
                if (getAllPlayers().contains(name) || Objects.requireNonNull(name).isEmpty())
                    continue;
                list.add(player);
            }
        }
        return list;
    }

    public static boolean check(ScorePlayerTeam playerTeam) {
        for (String str : staffPrefix) {
            if (playerTeam.getPrefix().getString().toLowerCase().contains(str.toLowerCase())) return true;
        }
        String[] staffName = playerTeam.getMembershipCollection().toString().replaceAll("[\\[\\]]", "").replaceAll(" ", "").trim().split(",");
        return Arrays.stream(staffName).anyMatch(x -> excellent.getStaffManager().isStaff(x));
    }

    @Getter
    @RequiredArgsConstructor
    private enum Action {

        ONLINE("Online", new Color(50, 255, 50)),
        VANISH("Vanish", new Color(214, 113, 255)),
        SPEC("Spectate", new Color(255, 50, 50));

        private final String action;
        private final Color color;
    }

}