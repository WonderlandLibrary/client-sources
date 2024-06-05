/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.move;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventMove;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.managers.ModuleManager;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.UTILS.helper.Helper;
import drunkclient.beta.UTILS.world.MovementUtil;
import drunkclient.beta.UTILS.world.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.potion.Potion;

public class LongJump
extends Module {
    public Mode<Enum> mode = new Mode("Mode", "Mode", (Enum[])Modes.values(), (Enum)Modes.Hypixel);
    private double moveSpeed;
    private boolean canDisable;
    private boolean velocitywasEnabled;
    private double z;
    private double O;
    private boolean fuck = false;
    Timer timer = new Timer();
    boolean canFly = false;
    private boolean J;

    public LongJump() {
        super("LongJump", new String[0], Type.MOVE, "No");
        this.addValues(this.mode);
    }

    /*
     * Recovered potentially malformed switches.  Disable with '--allowmalformedswitch false'
     */
    @Override
    public void onEnable() {
        this.moveSpeed = this.z = Minecraft.thePlayer.a(true, 0.2);
        if (this.mode.getModeAsString().equalsIgnoreCase("Hypixel")) {
            if (Minecraft.thePlayer.onGround) {
                Minecraft.thePlayer.jump();
            }
        }
        switch (this.mode.getModeAsString()) {
            default: 
        }
        this.timer.reset();
    }

    @Override
    public void onDisable() {
        LongJump.mc.timer.timerSpeed = 1.0f;
        this.canFly = false;
        this.fuck = false;
    }

    /*
     * Unable to fully structure code
     */
    @EventHandler
    public void onUpdate(EventPreUpdate e) {
        this.setSuffix(this.mode.getModeAsString());
        var2_2 = this.mode.getModeAsString();
        var3_3 = -1;
        switch (var2_2.hashCode()) {
            case -1248403467: {
                if (!var2_2.equals("Hypixel")) break;
                var3_3 = 0;
                break;
            }
            case 1431173555: {
                if (!var2_2.equals("SurvivalDub")) break;
                var3_3 = 1;
                break;
            }
            case 2567299: {
                if (!var2_2.equals("TAKA")) break;
                var3_3 = 2;
                break;
            }
            case 64733: {
                if (!var2_2.equals("AGC")) break;
                var3_3 = 3;
                break;
            }
        }
        switch (var3_3) {
            case 0: {
                LongJump.mc.timer.timerSpeed = 0.9f;
                return;
            }
            case 1: {
                Minecraft.thePlayer.setSprinting(false);
                if (Minecraft.thePlayer.hurtTime > 0) {
                    this.canFly = true;
                } else if (!this.canFly) {
                    ModuleManager.getModuleByName("Velocity").setEnabled(false);
                    e.setPitch(-89.0f);
                    Minecraft.thePlayer.motionX = 0.0;
                    Minecraft.thePlayer.motionZ = 0.0;
                    if (!(Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemBow)) {
                        Helper.sendMessage("Move Bow into your hand");
                        this.timer.reset();
                    } else if (!this.timer.hasElapsed(340L, false)) {
                        LongJump.mc.gameSettings.keyBindUseItem.pressed = true;
                    } else {
                        LongJump.mc.gameSettings.keyBindUseItem.pressed = false;
                        if (this.timer.hasElapsed(540L, false)) {
                            e.setPitch(Minecraft.thePlayer.rotationPitch);
                        }
                    }
                }
                if (this.canFly == false) return;
                LongJump.mc.gameSettings.keyBindForward.pressed = true;
                Minecraft.thePlayer.motionY = 0.0;
                MovementUtil.setMotion(0.4);
                return;
            }
            case 2: {
                if (Minecraft.thePlayer.hurtTime <= 0) ** GOTO lbl62
                this.fuck = true;
                ** GOTO lbl72
lbl62:
                // 1 sources

                if (this.fuck) ** GOTO lbl72
                e.setPitch(-89.0f);
                if (!(Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemBow)) ** GOTO lbl-1000
                if (Minecraft.thePlayer.getHeldItem().getItem() == null) lbl-1000:
                // 2 sources

                {
                    Helper.sendMessage("Move Bow into your hand");
                    this.timer.reset();
                } else {
                    LongJump.mc.gameSettings.keyBindUseItem.pressed = this.timer.hasElapsed(340L, false) == false;
                }
lbl72:
                // 4 sources

                if (this.fuck == false) return;
                v0 = Minecraft.thePlayer.rotationYaw + (float)(Minecraft.thePlayer.moveForward < 0.0f ? 100 : 0);
                if (!(Minecraft.thePlayer.moveStrafing > 0.0f)) ** GOTO lbl81
                v1 = -90.0f * (Minecraft.thePlayer.moveForward < 0.0f ? -0.5f : (Minecraft.thePlayer.moveForward > 0.0f ? 0.4f : 1.0f));
            }
            ** GOTO lbl82
lbl81:
            // 1 sources

            v1 = 0.0f;
lbl82:
            // 2 sources

            direction = v0 + v1;
            x = (float)Math.cos((double)(direction + 90.0f) * 3.141592653589793 / 100.0);
            z = (float)Math.sin((double)(direction + 90.0f) * 3.141592653589793 / 100.0);
            speed = 1.3f;
            if (this.timer.hasElapsed(10L, true)) {
                speed = (float)((double)speed + (Math.sqrt(Minecraft.thePlayer.motionX * Minecraft.thePlayer.motionY + 0.14000000059604645) + (double)speed));
            }
            if (Minecraft.thePlayer.hurtTime <= 1) return;
            if (!(Minecraft.thePlayer.fallDistance < 1.0f)) return;
            Helper.sendMessage("Test");
            Minecraft.thePlayer.motionX = x * speed;
            Minecraft.thePlayer.motionZ = z * speed;
            return;
            case 3: {
                if (Minecraft.thePlayer.hurtTime > 0) {
                    LongJump.mc.gameSettings.keyBindForward.pressed = true;
                    LongJump.mc.timer.timerSpeed = 0.1f;
                    if (Minecraft.thePlayer.onGround != false) return;
                    if (LongJump.mc.gameSettings.keyBindForward.isKeyDown() == false) return;
                    MovementUtil.setMotion(4.6);
                    return;
                }
                e.setPitch(-89.0f);
                if (!(Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemBow)) {
                    Helper.sendMessage("Move Bow into your hand");
                    this.timer.reset();
                    return;
                }
                if (!this.timer.hasElapsed(340L, false)) {
                    LongJump.mc.gameSettings.keyBindUseItem.pressed = true;
                    return;
                }
                LongJump.mc.gameSettings.keyBindUseItem.pressed = false;
                return;
            }
        }
    }

    @EventHandler
    public void onMove(EventMove e) {
        switch (this.mode.getModeAsString()) {
            case "Hypixel": {
                if (Minecraft.thePlayer.onGround) {
                    if (this.J) {
                        Minecraft.thePlayer.motionY = Minecraft.thePlayer.getBaseMotionY();
                        EventMove.setY(Minecraft.thePlayer.motionY);
                        this.moveSpeed *= 2.139999980926514;
                    }
                    this.moveSpeed = Minecraft.thePlayer.getBySprinting() * 2.0;
                }
                if (this.J) {
                    this.moveSpeed = this.O - 0.66 * (this.O - this.z);
                }
                this.moveSpeed -= this.O / 24.0;
                if (!Minecraft.thePlayer.isPotionActive(Potion.jump)) {
                    if (Minecraft.thePlayer.motionY < 0.0) {
                        this.moveSpeed = this.z;
                        if (Minecraft.thePlayer.ticksExisted % 2 == 0) {
                            if ((double)Minecraft.thePlayer.fallDistance < 0.45) {
                                this.moveSpeed = this.z * 1.2;
                                Minecraft.thePlayer.motionY = 0.0;
                            }
                        }
                    }
                }
                this.moveSpeed = Math.max(this.moveSpeed, this.z);
                e.setSpeed(e, this.moveSpeed);
                return;
            }
        }
    }

    static enum Modes {
        Hypixel,
        AGC,
        SurvivalDub,
        TAKA;

    }
}

