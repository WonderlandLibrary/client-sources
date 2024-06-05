package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import zamorozka.event.EventTarget;
import zamorozka.event.events.EventMove;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventSendPacket;
import zamorozka.event.events.EventUpdate;
import zamorozka.event.events.UpdateEvent;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.COMBAT.TargetStrafe;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.PlayerUtil;
import zamorozka.ui.Timerr;

public class Flight extends Module {

    private final Timerr timer = new Timerr();
    private final Timerr groundTimer = new Timerr();
    private int counter, level, stage, ticks;
    private double moveSpeed, lastDist, y;
	
    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Hypixel");
        options.add("Shotbow");
        options.add("CubeCraft");
        options.add("Float");
        options.add("Vanilla");
        options.add("Smooth");
        Zamorozka.instance.settingsManager.rSetting(new Setting("Flight Mode", this, "Vanilla", options));
        ArrayList<String> options2 = new ArrayList<>();
        options2.add("Zoom");
        options2.add("Normal");
        Zamorozka.instance.settingsManager.rSetting(new Setting("Shotbow Mode", this, "Zoom", options2));
    	Zamorozka.settingsManager.rSetting(new Setting("FlightSpeed", this,  1, 0.1, 10, true));
    	Zamorozka.settingsManager.rSetting(new Setting("FlightBobbing", this, true));
    	Zamorozka.settingsManager.rSetting(new Setting("FlightMultiplier", this, true));
    	Zamorozka.settingsManager.rSetting(new Setting("KickBypasses", this, true));
    	Zamorozka.settingsManager.rSetting(new Setting("OnGroundFlight", this, false));
    }
    
	public Flight() {
		super("Flight", 0, Category.TRAFFIC);
	}
	
    @Override
    public void onEnable() {
        if (mc.player == null || mc.player == null)
            return;

        this.y = 0.0D;
        this.lastDist = 0.0D;
        this.moveSpeed = 0.0D;
        this.counter = 0;
        this.stage = 0;
        this.ticks = 0;
        String mode = Zamorozka.settingsManager.getSettingByName("Flight Mode").getValString();
        String mode2 = Zamorozka.settingsManager.getSettingByName("Shotbow Mode").getValString();
        if(mode.equalsIgnoreCase("Hypixel")) {
                mc.player.motionX *= 0.0;
                mc.player.motionZ *= 0.0;
        }
        if(mode.equalsIgnoreCase("Shotbow")) {
        	if(mode2.equalsIgnoreCase("Normal")) {
        		MovementUtilis.setMotion(0.3 + MovementUtilis.getSpeedEffect() * 0.05f);
        	}
        	if(mode2.equalsIgnoreCase("Zoom")) {
                        //PlayerUtils.damage2();
                        PlayerUtil.damage3();
                        this.mc.player.motionY = 0.41999998688698f;
                        level = 1;
                        timer.reset();
                }

        }
        if(mode.equalsIgnoreCase("Floats")) {
        	MovementUtilis.setMotion(0.3 + MovementUtilis.getSpeedEffect() * 0.05f);
                mc.player.motionY = 0.41999998688698f + MovementUtilis.getJumpEffect() * 0.1;
                mc.player.jumpMovementFactor = 0;
        }
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        String mode = Zamorozka.settingsManager.getSettingByName("Flight Mode").getValString();
        String mode2 = Zamorozka.settingsManager.getSettingByName("Shotbow Mode").getValString();
        mc.player.capabilities.isFlying = false;
        mc.player.jumpMovementFactor = 0;
        mc.timer.timerSpeed = 1.0F;
        mc.player.stepHeight = 0.625F;
        MovementUtilis.setMotion(0.2F);
        if (mode.equalsIgnoreCase("Hypixel")) {
            mc.player.setPosition(mc.player.posX, mc.player.posY + this.y, mc.player.posZ);
        } else if (mode.equalsIgnoreCase("Shotbow")) {
            if (mode2.equalsIgnoreCase("Zoom")) {
                mc.player.motionY = -0.003;
            }
        }

        this.y = 0.0D;
        this.lastDist = 0.0D;
        this.moveSpeed = 0.0D;
        this.counter = 0;
        this.stage = 0;
        this.ticks = 0;
        this.timer.reset();
        super.onDisable();
    }

    @EventTarget
    public void onUpdate(EventPreMotionUpdates event) {
        String mode = Zamorozka.settingsManager.getSettingByName("Flight Mode").getValString();
        String mode2 = Zamorozka.settingsManager.getSettingByName("Shotbow Mode").getValString();
        double speed = (Zamorozka.settingsManager.getSettingByName("FlightSpeed").getValDouble());
        if(Zamorozka.settingsManager.getSettingByName("Boobing").getValBoolean()) {
            mc.player.cameraYaw = 0.05F;
        }
        if(Zamorozka.settingsManager.getSettingByName("OnGroundFlight").getValBoolean()) {
            mc.player.onGround = true;
        }
        if(Zamorozka.settingsManager.getSettingByName("FlightMultiplier").getValBoolean()) {
            if (!timer.delay(1190)) {
                mc.timer.timerSpeed = 1.8F;
            } else {
                mc.timer.timerSpeed = 1.0F;
            }
        }
        if(mode.equalsIgnoreCase("Hypixel")) {
        	if (this.stage > 2) {
                mc.player.motionY = 0.0;
            }
            if (this.stage > 2) {
                mc.player.setPosition(mc.player.posX, mc.player.posY - 0.003, mc.player.posZ);
                ++this.ticks;
                switch (ticks) {
                case 1: this.y *= -0.949999988079071;ticks++; break;
                    case 2:
                    case 3:
                    case 4: this.y += 3.25E-4; ticks++; break;
                    case 5: this.y += 5.0E-4; this.ticks = 0; break;
                }
                event.setY(mc.player.posY + this.y);
            }
        } else if (this.stage > 2) {
            mc.player.setPosition(mc.player.posX, mc.player.posY + 0.003, mc.player.posZ);
        }
        if(mode.equalsIgnoreCase("CubeCraft")) {
            if (mc.player.onGround) {
                this.moveSpeed = 1.4;
                PlayerUtil.damage2();
                mc.player.jump();
            } else {
                ++this.stage;
                double x2 = mc.player.posX;
                double y2 = mc.player.posY;
                double z2 = mc.player.posZ;
                switch (stage) {
                    case 1: mc.player.setPosition(x2, y2 + 1.0E-12, z2); stage++; break;
                    case 2: mc.player.setPosition(x2, y2 - 1.0E-12, z2); stage++; break;
                    case 3: mc.player.setPosition(x2, y2 + 1.0E-12, z2); stage++; this.stage = 0; break;
                    }
                }
                mc.player.motionY = 0.0;
                MovementUtilis.setMotion(this.moveSpeed);
                if (timer.delay(25L) && this.moveSpeed > 0.26) {
                    this.moveSpeed -= 0.035;
                    timer.reset();
                }
        }
        if(mode.equalsIgnoreCase("Shotbow")) {
        	if(mode2.equalsIgnoreCase("Zoom")) {
                ++this.counter;
                if (!mc.player.onGround) {
                    switch (counter) {
                        case 1: mc.player.setPosition(mc.player.posX, mc.player.posY + 1.0E-10D, mc.player.posZ); counter++; break;
                        case 2: mc.player.setPosition(mc.player.posX, mc.player.posY - 1.0E-10D, mc.player.posZ); counter++; counter = 0; break;
                    }
                    mc.player.motionY = 0.0D;
                }
            } else if (mode2.equalsIgnoreCase("Normal")) {
                mc.player.motionY = -0.003D;
            }
        }
        if(mode.equalsIgnoreCase("Float")) {
            ++counter;
            if (MovementInput.moveForward == 0.0F && MovementInput.moveStrafe == 0.0F) {
                mc.player.setPosition(mc.player.posX + 1.0D, mc.player.posY + 1.0D, mc.player.posZ + 1.0D);
                mc.player.setPosition(mc.player.prevPosX, mc.player.prevPosY, mc.player.prevPosZ);
            }
            mc.player.motionY = 0.0D;
            if (counter == 2 && !mc.player.onGround) {
                mc.player.setPosition(mc.player.posX, mc.player.posY + 1.0E-10D, mc.player.posZ);
                counter = 0;
            }
            if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                this.mc.player.motionY = 0.5;
            } else if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                this.mc.player.motionY = -0.5;
            }
        }
        if(mode.equalsIgnoreCase("Vanilla")) {
            mc.player.capabilities.isFlying = false;
            mc.player.motionY = 0;
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
            if (mc.gameSettings.keyBindJump.isKeyDown())
                mc.player.motionY += speed;
            if (mc.gameSettings.keyBindSneak.isKeyDown())
                mc.player.motionY -= speed;
            handleVanillaKickBypass();
        }
        if(mode.equalsIgnoreCase("Smooth")) {
            mc.player.capabilities.isFlying = true;
            handleVanillaKickBypass();
        }
        double xDif = mc.player.posX - mc.player.prevPosX;
        double zDif = mc.player.posZ - mc.player.prevPosZ;
        this.lastDist = Math.sqrt(xDif * xDif + zDif * zDif);
    }
    
    @EventTarget
    public void onMove(EventMove event) {
        String mode = Zamorozka.settingsManager.getSettingByName("Flight Mode").getValString();
        String mode2 = Zamorozka.settingsManager.getSettingByName("Shotbow Mode").getValString();
        double speed = (Zamorozka.settingsManager.getSettingByName("FlightSpeed").getValDouble());
        TargetStrafe targetStrafe = (TargetStrafe) ModuleManager.getModule(TargetStrafe.class);
        if(mode.equalsIgnoreCase("Hypixel")) {
                if (mc.player.isMoving()) {
                    switch (stage) {
                    case 0: {
                        if (mc.player.onGround && mc.player.isCollidedVertically) {
                            PlayerUtil.damage();
                            this.moveSpeed = 0.5 * speed;
                            break;
                        }
                    }
                    case 1: {
                        if (mc.player.onGround && mc.player.isCollidedVertically) {
                            final EntityPlayerSP entityPlayerSP = mc.player;
                            final double jumpBoostModifier = MovementUtilis.getJumpBoostModifier(0.39999994);
                            entityPlayerSP.motionY = jumpBoostModifier;
                            event.setY(jumpBoostModifier);
                        }
                        this.moveSpeed *= 2.149;
                        break;
                    }
                    case 2: {
                        double boost = mc.player.isPotionActive(Potion.getPotionById(1)) ? 1.85D : 2.12;
                        this.moveSpeed = boost * MovementUtilis.getBaseMoveSpeed();
                        break;
                    }
                    default: {
                        this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                        break;
                    }
                }
            }
            if (targetStrafe.canStrafe()) {
                targetStrafe.strafe(event, Math.max(this.moveSpeed, MovementUtilis.getBaseMoveSpeed()));
            } else {
                MovementUtilis.setMotion(event, Math.max(this.moveSpeed, MovementUtilis.getBaseMoveSpeed()));
            }
            ++this.stage;
        }
        if(mode.equalsIgnoreCase("Shotbow")) {
        	if(mode2.equalsIgnoreCase("Zoom")) {
        		if (level != 1) {
                    if (level == 2) {
                        level = 3;
                        moveSpeed *= 2.149D;
                        mc.timer.timerSpeed = 1.0f;
                    } else if (level == 3) {
                        level = 4;
                        double difference = (mc.player.ticksExisted % 2 == 0 ? 0.0103D : 0.0123D) * (lastDist - MovementUtilis.getBaseMoveSpeed());
                        moveSpeed = lastDist - difference;
                    } else {
                        moveSpeed = lastDist - lastDist / 159.0D;
                        if (moveSpeed <= 0.600 && moveSpeed >= 0.400) {
                            mc.timer.elapsedPartialTicks = 0.22F;
                        }
                    }
                } else {
                    level = 2;
                    double boost = mc.player.isPotionActive(Potion.getPotionById(1)) ? 1.65D : 2.0;
                    this.moveSpeed = boost * MovementUtilis.getBaseMoveSpeed();
                }

                if (targetStrafe.canStrafe()) {
                    targetStrafe.strafe(event, Math.max(this.moveSpeed, MovementUtilis.getBaseMoveSpeed()));
                } else {
                	MovementUtilis.setMotion(event, Math.max(this.moveSpeed, MovementUtilis.getBaseMoveSpeed()));
                }
            } else if (mode2.equalsIgnoreCase("Normal")) {
                if (this.stage == 1 && (mc.player.field_191988_bg != 0.0F || mc.player.moveStrafing != 0.0F)) {
                    this.moveSpeed = 1.2D * MovementUtilis.getBaseMoveSpeed() - 0.01D;
                } else if (this.stage == 2) {
                    event.setY(0.425D);
                    mc.player.motionY = 0.425D;
                    this.moveSpeed *= 2.1D;
                } else if (this.stage == 3) {
                    double difference = 0.66D * (this.lastDist - MovementUtilis.getBaseMoveSpeed());
                    this.moveSpeed = this.lastDist - difference;
                } else {
                    if (mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, mc.player.boundingBox.offset(0.0D, mc.player.motionY, 0.0D)).size() > 0 || mc.player.isCollidedVertically)
                        this.stage = 4;
                    this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
                }
                this.moveSpeed = Math.max(this.moveSpeed, MovementUtilis.getBaseMoveSpeed());
                MovementUtilis.setMotion(this.moveSpeed);
                if (mc.player.moveForward != 0.0F || mc.player.moveStrafing != 0.0F)
                    this.stage++;
            }
        }
        if(mode.equalsIgnoreCase("Vanilla")) {
        	
        }
        if(mode.equalsIgnoreCase("Float")) {
                if (targetStrafe.canStrafe()) {
                    targetStrafe.strafe(event, speed);
                } else {
                    MovementUtilis.setMotion(event, speed);
                }
        }
    }
    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        String mode = Zamorozka.settingsManager.getSettingByName("Flight Mode").getValString();
        if (mode.equalsIgnoreCase("Hypixel") && this.stage == 0) {
            event.setCancelled(true);
        }
    }

    private void handleVanillaKickBypass() {
        if (!(Zamorozka.settingsManager.getSettingByName("KickBypasses").getValBoolean() || !groundTimer.delay(1000)))
            return;

        final double ground = calculateGround();

        for (double posY = mc.player.posY; posY > ground; posY -= 8D) {
            mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, posY, mc.player.posZ, true));

            if (posY - 8D < ground)
                break; // Prevent next step
        }

        mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, ground, mc.player.posZ, true));

        for (double posY = ground; posY < mc.player.posY; posY += 8D) {
            mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, posY, mc.player.posZ, true));

            if (posY + 8D > mc.player.posY)
                break; // Prevent next step
        }
        mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
        groundTimer.reset();
    }
    private double calculateGround() {
        final AxisAlignedBB playerBoundingBox = mc.player.getEntityBoundingBox();
        double blockHeight = 1D;

        for (double ground = mc.player.posY; ground > 0D; ground -= blockHeight) {
            final AxisAlignedBB customBox = new AxisAlignedBB(playerBoundingBox.maxX, ground + blockHeight, playerBoundingBox.maxZ, playerBoundingBox.minX, ground, playerBoundingBox.minZ);

            if (mc.world.checkBlockCollision(customBox)) {
                if (blockHeight <= 0.05D)
                    return ground + blockHeight;

                ground += blockHeight;
                blockHeight = 0.05D;
            }
        }

        return 0F;
    }
}