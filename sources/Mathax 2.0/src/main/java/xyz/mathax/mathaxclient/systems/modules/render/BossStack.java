package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.RenderBossBarEvent;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.DoubleSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.WeakHashMap;

public class BossStack extends Module {
    public static final WeakHashMap<ClientBossBar, Integer> barMap = new WeakHashMap<>();

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    public final Setting<Boolean> stackSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Stack")
            .description("Stack boss bars and adds a counter to the text.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> hideNameSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Hide name")
            .description("Hide the names of boss bars.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Double> spacingSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Bar spacing")
            .description("The spacing reduction between each boss bar.")
            .defaultValue(10)
            .min(0)
            .sliderRange(0, 20)
            .build()
    );

    public BossStack(Category category) {
        super(category, "Boss Stack", "Stacks boss bars to make your HUD less cluttered.");
    }

    @EventHandler
    private void onFetchText(RenderBossBarEvent.BossText event) {
        if (hideNameSetting.get()) {
            event.name = Text.of("");
            return;
        } else if (barMap.isEmpty() || !stackSetting.get()) {
            return;
        }

        ClientBossBar bossBar = event.bossBar;
        Integer integer = barMap.get(bossBar);
        barMap.remove(bossBar);
        if (integer != null && !hideNameSetting.get()) {
            event.name = event.name.copy().append(" x" + integer);
        }
    }

    @EventHandler
    private void onSpaceBars(RenderBossBarEvent.BossSpacing event) {
        event.spacing = spacingSetting.get().intValue();
    }

    @EventHandler
    private void onGetBars(RenderBossBarEvent.BossIterator event) {
        if (stackSetting.get()) {
            HashMap<String, ClientBossBar> chosenBarMap = new HashMap<>();
            event.iterator.forEachRemaining(bar -> {
                String name = bar.getName().getString();
                if (chosenBarMap.containsKey(name)) {
                    barMap.compute(chosenBarMap.get(name), (clientBossBar, integer) -> (integer == null) ? 2 : integer + 1);
                } else {
                    chosenBarMap.put(name, bar);
                }
            });

            event.iterator = chosenBarMap.values().iterator();
        }
    }
}
