package club.bluezenith.module.modules.fun.zombies;

import club.bluezenith.BlueZenith;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.fun.zombies.components.StrategyComponent;
import club.bluezenith.module.value.types.*;
import club.bluezenith.util.client.MillisTimer;

public class Zombies extends Module {
    private final StrategyComponent strategyComponent = new StrategyComponent(this);
    public final BooleanValue showStrategy = new BooleanValue("Show strategy", false).setIndex(1)
            .setValueChangeListener((before, after) -> {
                if(after && !getStrategyURL().get().matches("(https://)?pastebin.com/raw/(.*)")) {
                    BlueZenith.getBlueZenith().getNotificationPublisher().postError(
                            "Strategy",
                            "To enable this option, input a raw pastebin link to the text field below.",
                            3000);
                    return before;
                } else if(after) {
                    BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess(
                            "Strategy",
                            "Loading the text, hang on..",
                            2000
                    );
                    strategyComponent.fetch();
                }
                return after;
            });
    public final StringValue strategyURL = new StringValue("Strategy pastebin (raw)", "").setIndex(2);
    public final IntegerValue strategyWidth = new IntegerValue("Widget width", 300, 100, 1080, 5)
            .setIndex(3)
            .showIf(showStrategy::get)
            .setValueChangeListener((before, after) -> {
                strategyComponent.resize(after, strategyComponent.getHeight());
                return after;
            });

    public final IntegerValue strategyHeight = new IntegerValue("Widget height", 150, 40, 700, 5)
            .setIndex(4)
            .showIf(showStrategy::get)
            .setValueChangeListener((before, after) -> {
                strategyComponent.resize(strategyComponent.getWidth(), after);
                return after;
            });

    public final BooleanValue strategyBackground = new BooleanValue("Widget background", false)
            .setIndex(5)
            .showIf(showStrategy::get);

    public final FloatValue scrollSpeed = new FloatValue("Scroll speed", 40, 10, 100, 5)
            .setIndex(6)
            .showIf(showStrategy::get);

    private final BooleanValue goldInScoreboard = new BooleanValue("Gold in scoreboard", false).setIndex(7);
    private final BooleanValue transparentPlayers = new BooleanValue("Transparent players", false).setIndex(8);
    private final IntegerValue transparencyDistance = new IntegerValue("Distance (blocks)", 10, 1, 50, 1)
            .setIndex(9)
            .showIf(transparentPlayers::get);

    private final BooleanValue reviveFix = new BooleanValue("Fast revive fix", false).setIndex(10);
    private final IntegerValue reviveMaxTries = new IntegerValue("Max attempts", 5, 1, 20, 1)
            .setIndex(11)
            .showIf(reviveFix::get);
    private final ModeValue resetTriesOn = new ModeValue("Reset tries on", "Re-sneak", "Cooldown", "Re-Sneak")
            .setIndex(12)
            .showIf(reviveFix::get);
    private final IntegerValue reviveCooldown = new IntegerValue("Cooldown", 5000, 1600, 20000, 100)
            .setIndex(13)
            .showIf(() -> resetTriesOn.isVisible() && resetTriesOn.is("Cooldown"));


    private final BooleanValue hologramFix = new BooleanValue("Hologram fix", false)
            .setIndex(14);

    private final MillisTimer reviveCooldownTimer = new MillisTimer();

    public Zombies() {
        super("Zombies", ModuleCategory.FUN);
    }

    private int reviveAttemptsLeft;

    private StringValue getStrategyURL() {
        return strategyURL;
    }

}
