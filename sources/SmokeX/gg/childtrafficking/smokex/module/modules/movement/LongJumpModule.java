// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.movement;

import gg.childtrafficking.smokex.utils.player.ChatUtils;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import gg.childtrafficking.smokex.utils.player.PlayerUtils;
import gg.childtrafficking.smokex.utils.system.StringUtils;
import gg.childtrafficking.smokex.utils.player.MovementUtils;
import gg.childtrafficking.smokex.event.events.network.EventReceivePacket;
import gg.childtrafficking.smokex.event.events.player.EventMove;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "LongJump", renderName = "Long Jump", description = "Allows you to jump further", aliases = { "bigjump" }, category = ModuleCategory.MOVEMENT)
public final class LongJumpModule extends Module
{
    int stage;
    private double moveSpeed;
    private double verticalSpeed;
    private double cachedPosY;
    private final EnumProperty<Mode> modeEnumProperty;
    private final BooleanProperty damageProperty;
    private final BooleanProperty safeDamage;
    private final BooleanProperty extraBoostProperty;
    private final EventListener<EventMove> movePlayerEventCallback;
    private EventListener<EventReceivePacket> eventReceivePacketEventListener;
    
    public LongJumpModule() {
        this.stage = 0;
        this.moveSpeed = MovementUtils.getBaseMoveSpeed();
        this.verticalSpeed = 0.0;
        this.modeEnumProperty = new EnumProperty<Mode>("Mode", Mode.WATCHDOG);
        this.damageProperty = new BooleanProperty("Damage", true);
        this.safeDamage = new BooleanProperty("SafeDamage", "Safe Damage", false, this.damageProperty::getValue);
        this.extraBoostProperty = new BooleanProperty("Boost", "Extra Boost", true);
        this.movePlayerEventCallback = (event -> {
            this.setSuffix(StringUtils.upperSnakeCaseToPascal(this.modeEnumProperty.getValueAsString()));
            switch (this.modeEnumProperty.getValue()) {
                case WATCHDOG: {
                    if (MovementUtils.isOnGround() || this.stage > 0) {
                        if (this.damageProperty.getValue()) {
                            switch (this.stage) {
                                case 0: {
                                    this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.004 * Math.random(), this.mc.thePlayer.posZ);
                                    PlayerUtils.damage(this.safeDamage.getValue());
                                    this.mc.thePlayer.motionY = 0.8;
                                    this.moveSpeed = MovementUtils.getBaseMoveSpeed2() * ((PlayerUtils.isHoldingSword() && !this.safeDamage.getValue()) ? 2.19 : 2.19);
                                    break;
                                }
                                case 1: {
                                    this.moveSpeed *= 0.77;
                                    break;
                                }
                                default: {
                                    this.moveSpeed *= 0.97;
                                    break;
                                }
                            }
                            if (this.stage > 2) {}
                            ++this.stage;
                            if (MovementUtils.isOnGround() && this.stage > 4) {
                                this.toggle();
                            }
                            MovementUtils.setSpeed(event, Math.max(MovementUtils.getBaseMoveSpeed(), this.moveSpeed));
                            break;
                        }
                        else {
                            switch (this.stage) {
                                case 0: {
                                    MovementUtils.ncpBoost(2);
                                    this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.004 * Math.random(), this.mc.thePlayer.posZ);
                                    this.verticalSpeed = MovementUtils.getBaseJumpMotion() * 0.95;
                                    this.moveSpeed = MovementUtils.getBaseMoveSpeed2() * 2.149;
                                    break;
                                }
                                case 1: {
                                    this.moveSpeed *= 0.65;
                                    break;
                                }
                            }
                            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.cachedPosY, this.mc.thePlayer.posZ);
                            event.setY(this.verticalSpeed);
                            if (this.stage > 10) {
                                this.moveSpeed *= 0.98;
                                this.verticalSpeed -= 0.035;
                            }
                            else {
                                this.moveSpeed *= 0.98;
                                this.verticalSpeed *= 0.65;
                            }
                            ++this.stage;
                            if (!MovementUtils.isOnGround() || this.stage > 4) {}
                            MovementUtils.setSpeed(event, Math.max(MovementUtils.getBaseMoveSpeed(), this.moveSpeed));
                            break;
                        }
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        this.eventReceivePacketEventListener = (event -> {
            if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                ChatUtils.addChatMessage("Xi Jinping has detected one flag. Automatically toggled off Long Jump for you. Glory to the CCP!");
                this.toggle();
            }
        });
    }
    
    @Override
    public void onEnable() {
        this.cachedPosY = this.mc.thePlayer.posY;
        this.stage = 0;
        super.onEnable();
    }
    
    private enum Mode
    {
        WATCHDOG;
    }
}
