package wtf.shiyeno.modules.impl.combat;

import net.minecraft.item.Items;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.SliderSetting;

@FunctionAnnotation(
        name = "AutoGApple",
        type = Type.Combat
)
public class AutoGAppleFunction extends Function {
    private final SliderSetting healthThreshold = new SliderSetting("Здоровье", 13.0F, 3.0F, 20.0F, 0.05F);
    private final BooleanOption withAbsorption = new BooleanOption("+ Золотые сердечки", true);
    private boolean isEating;

    public AutoGAppleFunction() {
        this.addSettings(new Setting[]{this.healthThreshold, this.withAbsorption});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate eventUpdate) {
            this.handleEating();
        }
    }

    private void handleEating() {
        if (this.canEat()) {
            this.startEating();
        } else if (this.isEating) {
            this.stopEating();
        }
    }

    public boolean canEat() {
        float health = mc.player.getHealth();
        if (this.withAbsorption.get()) {
            health += mc.player.getAbsorptionAmount();
        }

        return !mc.player.getShouldBeDead() && mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && health <= this.healthThreshold.getValue().floatValue() && !mc.player.getCooldownTracker().hasCooldown(Items.GOLDEN_APPLE);
    }

    private void startEating() {
        if (!mc.gameSettings.keyBindUseItem.isKeyDown()) {
            mc.gameSettings.keyBindUseItem.setPressed(true);
            this.isEating = true;
        }
    }

    private void stopEating() {
        mc.gameSettings.keyBindUseItem.setPressed(false);
        this.isEating = false;
    }
}