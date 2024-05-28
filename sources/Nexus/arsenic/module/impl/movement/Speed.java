package arsenic.module.impl.movement;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventMove;
import arsenic.event.impl.EventUpdate;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.BooleanProperty;
import arsenic.module.property.impl.EnumProperty;
import arsenic.utils.minecraft.MoveUtil;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.potion.Potion;

@ModuleInfo(name = "Speed", category = ModuleCategory.Movement)
public class Speed extends Module {
    double vulcanY = 0;
    public int onTicks, offTicks;
    public final EnumProperty<sMode> mode = new EnumProperty<>("Speed Mode: ", sMode.Strafe);
    public final BooleanProperty autojump = new BooleanProperty("AutoJump", true);
    @EventLink
    public final Listener<EventMove> eventMoveListener = event -> {

        if (!MoveUtil.isMoving()) {
            return;
        }

        if (autojump.getValue()) {
            if (mc.thePlayer.onGround) {
                if (!mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.thePlayer.jump();
                }
            }
        }

        if (mode.getValue() == sMode.GStrafe) {
            if (mc.thePlayer.onGround) {
                MoveUtil.strafe(0.40);
            }
        }

        if (mode.getValue() == sMode.Strafe) {
            MoveUtil.strafe();
        }

        if (mode.getValue() == sMode.BlocksMC) {
            if (mc.thePlayer.onGround) {
                MoveUtil.strafe(getNCPSpeedOnGround());
            }
            if (mc.thePlayer.hurtTime != 9) {
                MoveUtil.strafe();
            } else if (mc.thePlayer.isAirBorne) {
                MoveUtil.strafe(0.7);
            }
        }
        if (mode.getValue() == sMode.Intave) {
            if (offTicks >= 10 && offTicks % 5 == 0) {
                MoveUtil.strafe(MoveUtil.getSpeed());
            }
        }
        if (mode.getValue() == sMode.Vulcan) {
            if (MoveUtil.isMoving()) {
                if (mc.thePlayer.onGround) {
                    vulcanY = mc.thePlayer.posY;
                    MoveUtil.strafe(0.48);
                } else if (offTicks <= 1 && mc.thePlayer.posY > vulcanY) {
                    mc.thePlayer.setPosition(mc.thePlayer.posX, vulcanY, mc.thePlayer.posZ);
                } else if (offTicks >= 4) {
                    mc.thePlayer.motionY = -0.22;
                }
            }
        }
        if (mode.getValue() == sMode.HVH) {
            MoveUtil.strafe(0.6);
        }
    };

    public enum sMode {
        GStrafe,
        Strafe,
        Vulcan,
        Intave,
        BlocksMC,
        HVH,
        None
    }

    @EventLink
    public final Listener<EventUpdate.Pre> preListener = event -> {
        if (mc.theWorld == null || mc.thePlayer == null) {
            return;
        }
        this.onTicks = mc.thePlayer.onGround ? ++this.onTicks : 0;
        this.offTicks = mc.thePlayer.onGround ? 0 : ++this.offTicks;
    };

    @Override
    protected void onEnable() {
        vulcanY = mc.thePlayer.posY;
        mc.timer.timerSpeed = 1.0f;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), GameSettings.isKeyDown(mc.gameSettings.keyBindJump));
        if (mode.getValue() == sMode.Strafe || mode.getValue() == sMode.GStrafe) {
            MoveUtil.stop();
        }
        mc.timer.timerSpeed = 1f;
    }

    private double getNCPSpeedOnGround() {
        double moveSpeed;

        if (mc.thePlayer.moveForward != 0) {
            if (mc.thePlayer.moveStrafing != 0) {
                moveSpeed = 0.45;
            } else {
                moveSpeed = 0.48;
            }
        } else {
            moveSpeed = 0.4f;
        }
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            moveSpeed *= 1.0 + 0.17 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }

        if (mc.thePlayer.isPotionActive(Potion.moveSlowdown)) {
            moveSpeed *= 0.9f;
        }

        return moveSpeed;
    }
}
