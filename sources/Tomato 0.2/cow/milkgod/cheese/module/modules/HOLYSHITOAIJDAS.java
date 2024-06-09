/*
 * Decompiled with CFR 0_115.
 */
package cow.milkgod.cheese.module.modules;

import com.darkmagician6.eventapi.EventTarget;
import cow.milkgod.cheese.Cheese;
import cow.milkgod.cheese.commands.Command;
import cow.milkgod.cheese.commands.CommandManager;
import cow.milkgod.cheese.events.EventChatSend;
import cow.milkgod.cheese.events.EventPreMotionUpdates;
import cow.milkgod.cheese.events.EventTick;
import cow.milkgod.cheese.events.MoveEvent;
import cow.milkgod.cheese.module.Category;
import cow.milkgod.cheese.module.Module;
import cow.milkgod.cheese.utils.Logger;
import cow.milkgod.cheese.utils.MathUtils;
import cow.milkgod.cheese.utils.Wrapper;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;

public class HOLYSHITOAIJDAS
extends Module {
    private double speed = 6.0;
    private int level = 1;
    private boolean disabling;
    private boolean stopMotionUntilNext;
    private double moveSpeed = 0.2873;
    private boolean spedUp;
    public static boolean canStep;
    private double lastDist;
    public static double yOffset;
    private boolean cancel;
    private boolean speedTick;
    private float speedTimer = 1.3f;
    private int timerDelay;
    private int movement;
    private float modifier;
    private float movement2;
    private float modifier2;
    private boolean reverse = true;
    private int groundTicks;
    private double height = 1.0;
    private int recentStepTicks;

    public HOLYSHITOAIJDAS() {
        super("HOLYSHITOAIJDAS", 0, Category.MOVEMENT, 556274307, true, "ADASDASDAFASDASD", new String[]{"AMAZINGASFUCKSPEED"});
    }

    @EventTarget
    private void onUpdate(EventPreMotionUpdates event) {
        Wrapper.isMoving(Wrapper.getPlayer());
        if (!(!this.reverse || Wrapper.mc.gameSettings.keyBindJump.pressed || Wrapper.mc.thePlayer.isOnLadder() || Wrapper.mc.thePlayer.isInsideOfMaterial(Material.water) || Wrapper.mc.thePlayer.isInsideOfMaterial(Material.lava) || Wrapper.mc.thePlayer.isInWater() || (this.getBlock(-1.1) instanceof BlockAir || this.getBlock(-1.1) instanceof BlockAir) && (this.getBlock(-0.1) instanceof BlockAir || Wrapper.mc.thePlayer.motionX == 0.0 || Wrapper.mc.thePlayer.motionZ == 0.0 || !this.reverse || Wrapper.mc.thePlayer.onGround || Wrapper.mc.thePlayer.fallDistance >= 3.0f || (double)Wrapper.mc.thePlayer.fallDistance <= 0.05) || this.level != 3)) {
            Wrapper.mc.thePlayer.motionY = -0.3994;
        }
        ++this.recentStepTicks;
        this.groundTicks = Wrapper.mc.thePlayer.onGround ? ++this.groundTicks : 0;
        String currentMode = "";
        double xDist = Wrapper.mc.thePlayer.posX - Wrapper.mc.thePlayer.prevPosX;
        double zDist = Wrapper.mc.thePlayer.posZ - Wrapper.mc.thePlayer.prevPosZ;
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }

    public Block getBlock(AxisAlignedBB bb2) {
        int y2 = (int)bb2.minY;
        int x2 = MathHelper.floor_double(bb2.minX);
        while (x2 < MathHelper.floor_double(bb2.maxX) + 1) {
            int z2 = MathHelper.floor_double(bb2.minZ);
            while (z2 < MathHelper.floor_double(bb2.maxZ) + 1) {
                Block block = Wrapper.mc.theWorld.getBlockState(new BlockPos(x2, y2, z2)).getBlock();
                if (block != null) {
                    return block;
                }
                ++z2;
            }
            ++x2;
        }
        return null;
    }

    public Block getBlock(double offset) {
        return this.getBlock(Wrapper.mc.thePlayer.getEntityBoundingBox().offset(0.0, offset, 0.0));
    }

    @Override
    public void onEnable() {
        Timer.timerSpeed = 1.0f;
        this.cancel = false;
        this.level = 1;
        double d2 = this.moveSpeed = Wrapper.mc.thePlayer == null ? 0.2873 : this.getBaseMoveSpeed();
        if (!this.disabling) {
            super.onEnable();
        }
    }

    @EventTarget
    public void onPreMotion(MoveEvent event) {
        this.speedTick = !this.speedTick;
        ++this.timerDelay;
        this.timerDelay %= 5;
        if (this.timerDelay != 0) {
            Timer.timerSpeed = 1.0f;
        } else {
            if (Wrapper.isMoving(Wrapper.getPlayer())) {
                Timer.timerSpeed = 32767.0f;
            }
            if (Wrapper.isMoving(Wrapper.getPlayer())) {
                Timer.timerSpeed = this.speedTimer;
                Wrapper.getPlayer().motionX *= 1.0199999809265137;
                Wrapper.getPlayer().motionZ *= 1.0199999809265137;
            }
        }
        if (Wrapper.mc.thePlayer.onGround && Wrapper.isMoving(Wrapper.getPlayer())) {
            this.level = 2;
        }
        if (MathUtils.round(Wrapper.mc.thePlayer.posY - (double)((int)Wrapper.mc.thePlayer.posY), 3) == MathUtils.round(0.138, 3)) {
            EntityPlayerSP thePlayer = Wrapper.mc.thePlayer;
            thePlayer.motionY -= 0.08;
            event.y -= 0.09316090325960147;
            EntityPlayerSP thePlayer2 = Wrapper.mc.thePlayer;
            thePlayer2.posY -= 0.09316090325960147;
        }
        if (this.level == 1 && (Wrapper.mc.thePlayer.moveForward != 0.0f || Wrapper.mc.thePlayer.moveStrafing != 0.0f)) {
            this.level = 2;
            this.moveSpeed = 1.38 * this.getBaseMoveSpeed() - 0.01;
        } else if (this.level == 2) {
            this.level = 3;
            Wrapper.mc.thePlayer.motionY = 0.399399995803833;
            event.y = 0.399399995803833;
            this.moveSpeed *= 2.149;
        } else if (this.level == 3) {
            this.level = 4;
            double difference = 0.66 * (this.lastDist - this.getBaseMoveSpeed());
            this.moveSpeed = this.lastDist - difference;
        } else {
            if (Wrapper.mc.theWorld.getCollidingBoundingBoxes(Wrapper.mc.thePlayer, Wrapper.mc.thePlayer.boundingBox.offset(0.0, Wrapper.mc.thePlayer.motionY, 0.0)).size() > 0 || Wrapper.mc.thePlayer.isCollidedVertically) {
                this.level = 1;
            }
            this.moveSpeed = this.lastDist - this.lastDist / 159.0;
        }
        this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
        MovementInput movementInput = Wrapper.mc.thePlayer.movementInput;
        float forward = movementInput.moveForward;
        float strafe = movementInput.moveStrafe;
        float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        if (forward == 0.0f && strafe == 0.0f) {
            event.x = 0.0;
            event.z = 0.0;
        } else if (forward != 0.0f) {
            if (strafe >= 1.0f) {
                yaw += (float)(forward > 0.0f ? -45 : 45);
                strafe = 0.0f;
            } else if (strafe <= -1.0f) {
                yaw += (float)(forward > 0.0f ? 45 : -45);
                strafe = 0.0f;
            }
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        double mx2 = Math.cos(Math.toRadians(yaw + 90.0f));
        double mz2 = Math.sin(Math.toRadians(yaw + 90.0f));
        double motionX = (double)forward * this.moveSpeed * mx2 + (double)strafe * this.moveSpeed * mz2;
        double motionZ = (double)forward * this.moveSpeed * mz2 - (double)strafe * this.moveSpeed * mx2;
        event.x = (double)forward * this.moveSpeed * mx2 + (double)strafe * this.moveSpeed * mz2;
        event.z = (double)forward * this.moveSpeed * mz2 - (double)strafe * this.moveSpeed * mx2;
        canStep = true;
        Wrapper.mc.thePlayer.stepHeight = 0.6f;
        if (forward == 0.0f && strafe == 0.0f) {
            event.x = 0.0;
            event.z = 0.0;
        } else {
            boolean collideCheck = false;
            if (Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(Wrapper.mc.thePlayer, Wrapper.mc.thePlayer.boundingBox.expand(0.5, 0.0, 0.5)).size() > 0) {
                collideCheck = true;
            }
            if (forward != 0.0f) {
                if (strafe >= 1.0f) {
                    yaw += (float)(forward > 0.0f ? -45 : 45);
                    strafe = 0.0f;
                } else if (strafe <= -1.0f) {
                    yaw += (float)(forward > 0.0f ? 45 : -45);
                    strafe = 0.0f;
                }
                if (forward > 0.0f) {
                    forward = 1.0f;
                } else if (forward < 0.0f) {
                    forward = -1.0f;
                }
            }
        }
    }

    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (Wrapper.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = Wrapper.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }

    @Override
    public void onDisable() {
        Timer.timerSpeed = 1.0f;
        this.moveSpeed = this.getBaseMoveSpeed();
        yOffset = 0.0;
        this.level = 0;
        this.disabling = false;
        super.onDisable();
    }

    @Override
    protected void addCommand() {
        Cheese.getInstance();
        CommandManager commandManager = Cheese.commandManager;
        CommandManager.addCommand(new Command("holyshit", "Unknown Option! ", null, "<Enter value of tickspeed>", "Fucklord shines down cheese walking shoes unto you"){

            @EventTarget
            public void onTick(EventTick ev2) {
                String nigger = EventChatSend.getMessage().split(" ")[1];
                HOLYSHITOAIJDAS.access$0(HOLYSHITOAIJDAS.this, Float.parseFloat(nigger));
                Logger.logChat("Tick speed set to " + HOLYSHITOAIJDAS.this.speedTimer);
                this.Toggle();
            }
        });
    }

    static /* synthetic */ void access$0(HOLYSHITOAIJDAS hOLYSHITOAIJDAS, float f2) {
        hOLYSHITOAIJDAS.speedTimer = f2;
    }

}

