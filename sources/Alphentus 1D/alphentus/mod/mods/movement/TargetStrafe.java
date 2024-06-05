package alphentus.mod.mods.movement;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.mod.mods.combat.KillAura;
import alphentus.settings.Setting;
import alphentus.utils.MovementUtils;
import alphentus.utils.TimeUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 31/07/2020.
 */
public class TargetStrafe extends Mod {

    private KillAura aura;
    private int direction = -1;

    private TimeUtil timeUtil;

    public Setting radius = new Setting("Radius", 1F, 4F, 2F, false, this);
    public Setting holdSpace = new Setting("Hold Space", true, this);
    public Setting directionKeys = new Setting("DirectionKeys", true, this);

    public Setting randomRadius = new Setting("RandomRadius", true, this);

    public TargetStrafe() {
        super("TargetStrafe", Keyboard.KEY_NONE, true, ModCategory.MOVEMENT)
        ;
        Init.getInstance().settingManager.addSetting(radius);
        Init.getInstance().settingManager.addSetting(randomRadius);
        Init.getInstance().settingManager.addSetting(holdSpace);
        Init.getInstance().settingManager.addSetting(directionKeys);
        this.timeUtil = new TimeUtil();
    }

    @EventTarget
    public void event(Event event) {

        if (event.getType() == Type.RENDER2D) {
            if (randomRadius.isState()) {
                radius.setVisible(false);
            } else {
                radius.setVisible(true);
            }
        }

        if (!getState())
            return;

        if (event.getType() == Type.PRE) {
            if (mc.thePlayer.isCollidedHorizontally)
                switchDirection();
            if (mc.gameSettings.keyBindLeft.isPressed())
                this.direction = 1;
            if (mc.gameSettings.keyBindRight.isPressed())
                this.direction = -1;
        }
    }

    @Override
    public void onEnable() {
        if (aura == null)
            aura = Init.getInstance().modManager.getModuleByClass(KillAura.class);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    float randomRadiusRange = 0;
    boolean reverseRadius = false;

    public void strafe(Event event, double moveSpeed) {

        EntityLivingBase target = (EntityLivingBase) Init.getInstance().modManager.getModuleByClass(KillAura.class).finalEntity;


        if (randomRadius.isState()) {

            if (reverseRadius) {

                if (randomRadiusRange > 0.5F && reverseRadius) {
                    randomRadiusRange -= 0.25F;
                }else{
                    reverseRadius = false;
                }

                } else {
                if (randomRadiusRange < 8 && !reverseRadius) {
                    randomRadiusRange += 0.25F;
                } else {
                    reverseRadius = true;
                }
            }
        } else {
            randomRadiusRange = radius.getCurrent();
        }

        if (mc.thePlayer.getDistanceToEntity((Entity) target) <= (randomRadiusRange)) {
            MovementUtils.setSpeed(event, moveSpeed, Init.getInstance().modManager.getModuleByClass(KillAura.class).yaw, this.direction, 0.0D);
        } else {
            MovementUtils.setSpeed(event, moveSpeed, Init.getInstance().modManager.getModuleByClass(KillAura.class).yaw, this.direction, 1.0D);
        }

    }

    private void switchDirection() {
        if (this.direction == 1) {
            this.direction = -1;
        } else {
            this.direction = 1;
        }
    }


    public boolean canStrafe2() {
        return (this.aura.getState() && this.aura.finalEntity != null && getState() && (!this.holdSpace.isState() || mc.gameSettings.keyBindJump.pressed));
    }

}
