package wtf.shiyeno.modules.impl.combat;

import java.util.Objects;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;

@FunctionAnnotation(
        name = "TriggerBot",
        type = Type.Combat
)
public class TriggerBot extends Function {
    private final BooleanOption onlyCritical = new BooleanOption("Только криты", true);
    private final BooleanOption onlySpaceCritical;
    private long cpsLimit;

    public TriggerBot() {
        BooleanOption var10001 = new BooleanOption("Только с пробелом", false);
        BooleanOption var10002 = this.onlyCritical;
        Objects.requireNonNull(var10002);
        this.onlySpaceCritical = var10001.setVisible(var10002::get);
        this.cpsLimit = 0L;
        this.addSettings(new Setting[]{this.onlyCritical, this.onlySpaceCritical});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate e) {
            if (this.cpsLimit > System.currentTimeMillis()) {
                --this.cpsLimit;
            }

            if (mc.objectMouseOver.getType() == net.minecraft.util.math.RayTraceResult.Type.ENTITY && this.whenFalling() && this.cpsLimit <= System.currentTimeMillis()) {
                this.cpsLimit = System.currentTimeMillis() + 550L;
                if (mc.objectMouseOver.getType() == net.minecraft.util.math.RayTraceResult.Type.ENTITY) {
                    mc.playerController.attackEntity(mc.player, ((EntityRayTraceResult)mc.objectMouseOver).getEntity());
                    mc.player.swingArm(Hand.MAIN_HAND);
                }
            }
        }
    }

    public boolean whenFalling() {
        boolean critWater = mc.player.areEyesInFluid(FluidTags.WATER);
        boolean reasonForCancelCritical = mc.player.isPotionActive(Effects.BLINDNESS) || mc.player.isOnLadder() || mc.player.isInWater() && critWater || mc.player.isRidingHorse() || mc.player.abilities.isFlying || mc.player.isElytraFlying();
        boolean onSpace = this.onlySpaceCritical.get() && mc.player.isOnGround() && !mc.gameSettings.keyBindJump.isKeyDown();
        if (mc.player.getCooledAttackStrength(1.5F) < 0.92F) {
            return false;
        } else if (!reasonForCancelCritical && this.onlyCritical.get()) {
            return onSpace || !mc.player.isOnGround() && mc.player.fallDistance > 0.0F;
        } else {
            return true;
        }
    }
}