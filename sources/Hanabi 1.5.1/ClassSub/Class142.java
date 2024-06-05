package ClassSub;

import net.minecraft.client.*;
import cn.Hanabi.value.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.block.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.client.entity.*;
import cn.Hanabi.events.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.network.*;
import java.math.*;

public class Class142
{
    Minecraft mc;
    int level;
    double moveSpeed;
    double lastDist;
    private boolean b2;
    public int stage;
    private boolean decreasing2;
    private boolean hypixelboost;
    private boolean canboost;
    private boolean keepy;
    private double starty;
    public static double hypixel;
    private float timervalue;
    public static int fastFlew;
    double count;
    Class205 timer;
    Value<String> mode;
    Value<Boolean> uhc;
    
    
    public Class142() {
        this.mc = Minecraft.getMinecraft();
        this.level = 1;
        this.timer = new Class205();
        this.mode = new Value<String>("Fly", "HypixelMode", 0);
        this.uhc = new Value<Boolean>("Fly_UHCZoom", false);
        this.mode.addValue("Normal");
        this.mode.addValue("Boost");
        this.mode.addValue("Zoom");
    }
    
    public void onLagback(final EventPullback eventPullback) {
        Class142.hypixel = 0.0;
    }
    
    public static Block getBlockUnderPlayer(final EntityPlayer entityPlayer, final double n) {
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(entityPlayer.posX, entityPlayer.posY - n, entityPlayer.posZ)).getBlock();
    }
    
    public void onPacket(final EventPacket eventPacket) {
        final Block blockUnderPlayer = getBlockUnderPlayer((EntityPlayer)this.mc.thePlayer, 0.2);
        if (eventPacket.packet instanceof C03PacketPlayer && !this.isOnGround(1.0E-7) && !blockUnderPlayer.isFullBlock() && !(blockUnderPlayer instanceof BlockGlass)) {
            ((IC03PacketPlayer)eventPacket.packet).setOnGround(false);
        }
    }
    
    public void onPre(final EventPreMotion eventPreMotion) {
        if (this.mode.isCurrentMode("Normal")) {
            if (this.mc.thePlayer.onGround) {
                this.mc.thePlayer.jump();
            }
            else {
                this.mc.thePlayer.motionY = 0.0;
                switch (++this.stage) {
                    case 1: {
                        this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0E-12, this.mc.thePlayer.posZ);
                        break;
                    }
                    case 2: {
                        this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0E-12, this.mc.thePlayer.posZ);
                        break;
                    }
                    case 3: {
                        this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0E-12, this.mc.thePlayer.posZ);
                        this.stage = 0;
                        break;
                    }
                }
            }
            Class200.setSpeed(Class200.getSpeed());
        }
        if (this.mode.isCurrentMode("Boost")) {
            final double n = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
            final double n2 = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
            this.lastDist = Math.sqrt(n * n + n2 * n2);
            if (this.canboost && this.hypixelboost) {
                this.timervalue += (float)(this.decreasing2 ? -0.01 : 0.05);
                if (this.timervalue >= 1.4) {
                    this.decreasing2 = true;
                }
                if (this.timervalue <= 0.9) {
                    this.decreasing2 = false;
                }
                if (this.timer.isDelayComplete(4000L)) {
                    this.canboost = false;
                }
            }
            if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                this.mc.thePlayer.setPositionAndUpdate(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.4, this.mc.thePlayer.posZ);
                this.mc.thePlayer.motionY = 0.8;
                final EntityPlayerSP thePlayer = this.mc.thePlayer;
                thePlayer.motionX *= 0.1;
                final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                thePlayer2.motionZ *= 0.1;
            }
            if (this.mc.thePlayer.ticksExisted % 2 == 0) {
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + getRandomInRange(1.2354235325235235E-14, 1.2354235325235233E-13), this.mc.thePlayer.posZ);
            }
            this.mc.thePlayer.motionY = 0.0;
        }
        else if (this.mode.isCurrentMode("Zoom")) {
            ++Class142.fastFlew;
            final Block blockUnderPlayer = getBlockUnderPlayer((EntityPlayer)this.mc.thePlayer, 0.2);
            if (!this.isOnGround(1.0E-7) && !blockUnderPlayer.isFullBlock() && !(blockUnderPlayer instanceof BlockGlass)) {
                this.mc.thePlayer.motionY = 0.0;
                this.mc.thePlayer.motionX = 0.0;
                this.mc.thePlayer.motionZ = 0.0;
                float n3 = 0.29f + this.getSpeedEffect() * 0.06f;
                if (Class142.hypixel > 0.0) {
                    if ((this.mc.thePlayer.moveForward == 0.0f && this.mc.thePlayer.moveStrafing == 0.0f) || this.mc.thePlayer.isCollidedHorizontally) {
                        Class142.hypixel = 0.0;
                    }
                    n3 += (float)(Class142.hypixel / 18.0);
                    Class142.hypixel -= round(0.155, 2) + this.getSpeedEffect() * 0.006;
                }
                Class180.setMotion(n3);
                this.mc.thePlayer.jumpMovementFactor = 0.0f;
                this.mc.thePlayer.onGround = false;
                ++this.count;
                ((IEntityPlayerSP)this.mc.thePlayer).setLastReportedPosY(0.0);
                if (this.count <= 2.0) {
                    this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 8.3467347E-12, this.mc.thePlayer.posZ);
                }
                else if (this.count == 4.0) {
                    this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 3.575474E-12, this.mc.thePlayer.posZ);
                }
                else if (this.count >= 5.0) {
                    this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 7.57457457E-12, this.mc.thePlayer.posZ);
                    this.count = 0.0;
                }
            }
        }
    }
    
    public void onMove(final EventMove eventMove) {
        if (this.mode.isCurrentMode("Boost")) {
            final float rotationYaw = this.mc.thePlayer.rotationYaw;
            double n = this.mc.thePlayer.movementInput.moveStrafe;
            double n2 = this.mc.thePlayer.movementInput.moveForward;
            final double n3 = -Math.sin(Math.toRadians(rotationYaw));
            final double cos = Math.cos(Math.toRadians(rotationYaw));
            if (n2 == 0.0 && n == 0.0) {
                eventMove.setX(0.0);
                eventMove.setZ(0.0);
            }
            if (n2 != 0.0 && n != 0.0) {
                n2 *= Math.sin(0.7853981633974483);
                n *= Math.cos(0.7853981633974483);
            }
            if (this.hypixelboost) {
                if (this.level != 1 || (this.mc.thePlayer.moveForward == 0.0f && this.mc.thePlayer.moveStrafing == 0.0f)) {
                    if (this.level == 2) {
                        this.level = 3;
                        this.moveSpeed *= 2.1499999;
                    }
                    else if (this.level == 3) {
                        this.level = 4;
                        this.moveSpeed = this.lastDist - 0.73 * (this.lastDist - getBaseMoveSpeed());
                    }
                    else {
                        if (this.mc.theWorld.getCollidingBoundingBoxes((Entity)this.mc.thePlayer, this.mc.thePlayer.getEntityBoundingBox().offset(0.0, this.mc.thePlayer.motionY, 0.0)).size() > 0 || this.mc.thePlayer.isCollidedVertically) {
                            this.level = 1;
                        }
                        this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                    }
                }
                else {
                    this.level = 2;
                    this.moveSpeed = (this.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.706 : 2.034) * getBaseMoveSpeed() - 0.01;
                }
                this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
                eventMove.setX(n2 * this.moveSpeed * n3 + n * this.moveSpeed * cos);
                eventMove.setZ(n2 * this.moveSpeed * cos - n * this.moveSpeed * n3);
                if (n2 == 0.0 && n == 0.0) {
                    eventMove.setX(0.0);
                    eventMove.setZ(0.0);
                }
            }
        }
        else if (this.mode.isCurrentMode("Zoom")) {}
    }
    
    public void onEnable() {
        this.stage = 0;
        this.level = 1;
        this.moveSpeed = 0.1;
        this.lastDist = 0.0;
        Class142.hypixel = 0.0;
        this.count = 0.0;
        Class142.fastFlew = 100;
        if (this.mode.isCurrentMode("Boost")) {
            this.canboost = true;
            double motionY = 0.40123128;
            this.timervalue = 1.0f;
            if (this.mc.thePlayer.onGround) {
                if ((this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) && this.mc.thePlayer.isCollidedVertically) {
                    if (this.mc.thePlayer.isPotionActive(Potion.jump)) {
                        motionY += (this.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
                    }
                    this.mc.thePlayer.motionY = motionY;
                }
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.28E-10, this.mc.thePlayer.posZ);
                this.level = 1;
                this.moveSpeed = 0.1;
                this.hypixelboost = true;
                this.lastDist = 0.0;
            }
            else {
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.26E-10, this.mc.thePlayer.posZ);
            }
            this.timer.reset();
        }
        if (this.mode.isCurrentMode("Zoom")) {
            this.damagePlayerNew();
            Class180.setMotion(0.3 + this.getSpeedEffect() * 0.05f);
            this.mc.thePlayer.motionY = 0.42;
            Class142.fastFlew = 25;
            Class142.hypixel = 18.4;
        }
    }
    
    public void damagePlayer(int floor_double) {
        if (floor_double < 1) {
            floor_double = 1;
        }
        if (floor_double > MathHelper.floor_double((double)this.mc.thePlayer.getMaxHealth())) {
            floor_double = MathHelper.floor_double((double)this.mc.thePlayer.getMaxHealth());
        }
        final double n = 0.0625;
        if (this.mc.thePlayer != null && this.mc.getNetHandler() != null && this.mc.thePlayer.onGround) {
            for (int n2 = 0; n2 <= (3 + floor_double) / n; ++n2) {
                this.mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + n + new Random().nextFloat() * 1.0E-6, this.mc.thePlayer.posZ, false));
                this.mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, n2 == (3 + floor_double) / n));
            }
        }
    }
    
    public void damagePlayerNew() {
        if (!this.mc.thePlayer.onGround) {
            return;
        }
        for (int i = 0; i <= (this.uhc.getValueState() ? 60 : 49); ++i) {
            double[] array;
            for (int length = (array = new double[] { 0.05099991337, 0.06199991337, 0.0 }).length, j = 0; j < length; ++j) {
                this.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY + array[j], this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, false));
            }
        }
        this.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
    }
    
    public void onDisable() {
        this.level = 1;
        this.moveSpeed = 0.1;
        this.b2 = false;
        this.lastDist = 0.0;
        this.count = 0.0;
        Class142.fastFlew = 100;
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        thePlayer.motionX *= 0.0;
        final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
        thePlayer2.motionZ *= 0.0;
        this.mc.thePlayer.jumpMovementFactor = 0.1f;
        Class180.setMotion(0.2);
    }
    
    public static double round(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(n).setScale(n2, RoundingMode.HALF_UP).doubleValue();
    }
    
    public boolean isOnGround(final double n) {
        return !this.mc.theWorld.getCollidingBoundingBoxes((Entity)this.mc.thePlayer, this.mc.thePlayer.getEntityBoundingBox().offset(0.0, -n, 0.0)).isEmpty();
    }
    
    public int getSpeedEffect() {
        if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }
        return 0;
    }
    
    private void setMotion(final EventMove eventMove, final double n) {
        double n2 = this.mc.thePlayer.movementInput.moveForward;
        double n3 = this.mc.thePlayer.movementInput.moveStrafe;
        float rotationYaw = this.mc.thePlayer.rotationYaw;
        if (n2 == 0.0 && n3 == 0.0) {
            eventMove.setX(0.0);
            eventMove.setZ(0.0);
        }
        else {
            if (n2 != 0.0) {
                if (n3 > 0.0) {
                    rotationYaw += ((n2 > 0.0) ? -45 : 45);
                }
                else if (n3 < 0.0) {
                    rotationYaw += ((n2 > 0.0) ? 45 : -45);
                }
                n3 = 0.0;
                if (n2 > 0.0) {
                    n2 = 1.0;
                }
                else if (n2 < 0.0) {
                    n2 = -1.0;
                }
            }
            eventMove.setX(n2 * n * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + n3 * n * Math.sin(Math.toRadians(rotationYaw + 90.0f)));
            eventMove.setZ(n2 * n * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - n3 * n * Math.cos(Math.toRadians(rotationYaw + 90.0f)));
        }
    }
    
    public static double getRandomInRange(final double n, final double n2) {
        double n3 = new Random().nextDouble() * (n2 - n);
        if (n3 > n2) {
            n3 = n2;
        }
        double n4 = n3 + n;
        if (n4 > n2) {
            n4 = n2;
        }
        return n4;
    }
    
    public static double getBaseMoveSpeed() {
        double n = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            n *= 1.0 + 0.2 * (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return n;
    }
    
    static {
        Class142.hypixel = 0.0;
    }
}
