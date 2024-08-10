package cc.slack.features.modules.impl.world;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.other.MathUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.util.MovingObjectPosition;

@ModuleInfo(
        name = "SpeedMine",
        category = Category.WORLD
)
public class SpeedMine extends Module {

    private final ModeValue<String> mode = new ModeValue<>(new String[]{"Vanilla", "Percent", "Instant", "NCP"});
    private final NumberValue<Double> percent = new NumberValue<>("Percent", 0.8D, 0D, 1D, 0.05D);
    private final NumberValue<Double> speed = new NumberValue<>("Speed", 1.0D, 0.1D, 2.0D, 0.1D);

    public SpeedMine() {
        addSettings(mode, percent, speed);
    }

    @Listen
    public void onUpdate (UpdateEvent event) {
        boolean isValid = mc.gameSettings.keyBindAttack.pressed && (mc.objectMouseOver != null &&
                        mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK &&
                        mc.objectMouseOver.getBlockPos() != null);
        switch (mode.getValue()) {
            case "Vanilla":
                if (mc.playerController.curBlockDamageMP * speed.getValue() > 1) {
                    mc.playerController.curBlockDamageMP = 1f;
                }
                break;
            case "Instant":
                if (isValid) {
                    mc.playerController.curBlockDamageMP = 1f;
                }
                break;
            case "Percent":
                if (mc.playerController.curBlockDamageMP >= percent.getValue()) {
                    mc.playerController.curBlockDamageMP = 1f;
                }
                break;
            case "NCP":
                if (isValid) {
                    if (mc.playerController.curBlockDamageMP >= 0.5f && !mc.thePlayer.isDead) {
                        mc.playerController.curBlockDamageMP += (MathUtil.getDifference(mc.playerController.curBlockDamageMP, 1.0f) * 0.7f);
                    }
                }
                break;
        }
    }


}
