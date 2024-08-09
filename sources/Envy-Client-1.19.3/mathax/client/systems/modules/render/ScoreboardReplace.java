package mathax.client.systems.modules.render;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.settings.StringSetting;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Category;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;
//this is fucking not working ima just give up for now
public class ScoreboardReplace extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public ScoreboardReplace() {
        super(Categories.Render, Items.AIR, "scoreboard-replace", "Replaces the scoreboard with a custom one. || Broken someone fix this");
    }

    private final Setting<String> find = sgGeneral.add(new StringSetting.Builder()
        .name("find")
        .description("The text to find")
        .defaultValue("&ehypixel.net")
        .build()
    );

    private final Setting<String> replace = sgGeneral.add(new StringSetting.Builder()
        .name("replace")
        .description("The text to replace with")
        .defaultValue("&cVolcanware.xyz")
        .build()
    );

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.getServer() == null) return;
        if (mc.world != null && mc.player != null && mc.getServer().getScoreboard() != null) {
            Scoreboard scoreboard = (Scoreboard) mc.getServer().getScoreboard().getObjectives();
            for (ScoreboardObjective objective : scoreboard.getObjectives()) {
                if (objective.getName().equals(find.get())) {
                    objective.getScoreboard().getObjective(find.get()).setDisplayName(Text.of(replace.get()));
                }
            }
        }
    }
}
