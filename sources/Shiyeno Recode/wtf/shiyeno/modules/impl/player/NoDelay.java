package wtf.shiyeno.modules.impl.player;

import net.minecraft.item.Items;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.MultiBoxSetting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(
        name = "NoDelay",
        type = Type.Player
)
public class NoDelay extends Function {
    private final MultiBoxSetting actions = new MultiBoxSetting("Действия", new BooleanOption[]{new BooleanOption("Прыжок", true), new BooleanOption("Ставить", false), new BooleanOption("Бутыльки опыта", false)});
    private final SliderSetting msPlace = (new SliderSetting("Place миллисекунды", 200.0F, 0.0F, 400.0F, 1.0F)).setVisible(() -> {
        return this.actions.get(1);
    });
    private final SliderSetting msPlaceBottle = (new SliderSetting("Бутыльки опыта миллисекунды", 200.0F, 0.0F, 400.0F, 1.0F)).setVisible(() -> {
        return this.actions.get(2);
    });
    private final TimerUtil timerUtil = new TimerUtil();

    public NoDelay() {
        this.addSettings(new Setting[]{this.actions, this.msPlace, this.msPlaceBottle});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (this.actions.get(0)) {
                mc.player.jumpTicks = 0;
            }

            if ((mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE || mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE && !mc.player.isHandActive()) && this.actions.get(2)) {
                if (this.timerUtil.hasTimeElapsed((long)this.msPlaceBottle.getValue().intValue())) {
                    mc.rightClickDelayTimer = 1;
                    this.timerUtil.reset();
                }

                return;
            }

            if (this.actions.get(1) && this.timerUtil.hasTimeElapsed((long)this.msPlace.getValue().intValue())) {
                mc.rightClickDelayTimer = 1;
                this.timerUtil.reset();
            }
        }
    }
}