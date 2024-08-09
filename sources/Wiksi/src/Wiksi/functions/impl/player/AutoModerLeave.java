package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.util.text.ITextComponent;
import src.Wiksi.command.staffs.StaffStorage;
import java.util.Locale;
import src.Wiksi.utils.client.ClientUtil;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import java.util.Comparator;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.TextFormatting;
import src.Wiksi.utils.text.GradientUtil;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.settings.Setting;
import java.util.ArrayList;
import java.util.regex.Pattern;
import src.Wiksi.ui.display.impl.StaffListRenderer;
import java.util.List;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.api.Function;

@FunctionRegister(name = "StaffLeave", type = Category.Player)
public class AutoModerLeave extends Function
{
    private final BooleanSetting specLeave;
    private final List<StaffListRenderer.Staff> staffPlayers;
    private final Pattern namePattern;
    private final Pattern prefixMatches;

    public AutoModerLeave() {
        this.specLeave = new BooleanSetting("Ливать от спека", false);
        this.staffPlayers = new ArrayList<StaffListRenderer.Staff>();
        this.namePattern = Pattern.compile("^\\w{3,16}$");
        this.prefixMatches = Pattern.compile(".*(mod|der|модер|adm|help|wne|хелп|адм|поддержка|кура|own|taf|curat|dev|supp|yt|сотруд).*");
        this.addSettings(new Setting[] { (Setting)this.specLeave });
    }

    @Subscribe
    public void onUpdate(final EventUpdate e) {
        final ITextComponent iTextComponent = GradientUtil.gradient("Модератор в спеке!");
        final String serverHeader = TextFormatting.getTextWithoutFormattingCodes(AutoModerLeave.mc.ingameGUI.getTabList().header.getString());
        this.staffPlayers.clear();
        for (ScorePlayerTeam team : mc.world.getScoreboard().getTeams().stream().sorted(Comparator.comparing(Team::getName)).toList()) {
            final String name = team.getMembershipCollection().toString().replaceAll("[\\[\\]]", "");
            boolean vanish = true;
            for (final NetworkPlayerInfo info : AutoModerLeave.mc.getConnection().getPlayerInfoMap()) {
                if (info.getGameProfile().getName().equals(name)) {
                    vanish = false;
                }
            }
            if (!serverHeader.contains("Hub")) {
                if (!ClientUtil.isPvP() && this.namePattern.matcher(name).matches() && !name.equals(AutoModerLeave.mc.player.getName().getString()) && !(boolean)this.specLeave.get() && (this.prefixMatches.matcher(team.getPrefix().getString().toLowerCase(Locale.ROOT)).matches() || StaffStorage.isStaff(name))) {
                    AutoModerLeave.mc.player.connection.getNetworkManager().closeChannel(iTextComponent);
                }
                if (ClientUtil.isPvP() || ((!vanish || !(boolean)this.specLeave.get()) && (!StaffStorage.isStaff(name) || !(boolean)this.specLeave.get())) || team.getPrefix().getString().isEmpty()) {
                    continue;
                }
                AutoModerLeave.mc.player.connection.getNetworkManager().closeChannel(iTextComponent);
            }
        }
    }
}

