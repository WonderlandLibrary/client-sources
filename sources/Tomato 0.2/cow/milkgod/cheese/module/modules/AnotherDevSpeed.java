package cow.milkgod.cheese.module.modules;

import cow.milkgod.cheese.properties.*;
import cow.milkgod.cheese.module.*;
import net.minecraft.util.*;
import net.minecraft.util.Timer;
import cow.milkgod.cheese.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import com.darkmagician6.eventapi.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.potion.*;
import net.minecraft.init.*;
import cow.milkgod.cheese.events.*;
import cow.milkgod.cheese.utils.*;
import cow.milkgod.cheese.commands.*;

public class AnotherDevSpeed extends Module
{
    private double speed;
    private int level;
    private boolean disabling;
    private boolean stopMotionUntilNext;
    private double moveSpeed;
    private boolean spedUp;
    public static boolean canStep;
    private double lastDist;
    public static double yOffset;
    private boolean cancel;
    private boolean speedTick;
    private float speedTimer;
    private int timerDelay;
    private double delay1;
    private double delay2;
    private double hAllowedDistance;
    public Property<Boolean> ice;
    public Property<Boolean> sprint;
    public Property<Boolean> ladders;
    public Property<Boolean> sanik;
    private double delay3;
    private boolean b1;
    private boolean b2;
    private boolean b3;
    private int jumpTicks;
    private int groundTicks;
    private int airTicks;
    
    public AnotherDevSpeed() {
        super("AnotherDevSpeed", 0, Category.MOVEMENT, 10027008, true, "Increases tickrate to make you hop fast", new String[] { "hoptick", "th" });
        this.delay1 = 0.0;
        this.delay2 = 0.0;
        this.hAllowedDistance = 0.2873000087011036;
        this.ice = new Property<Boolean>(this, "Ice", true);
        this.sprint = new Property<Boolean>(this, "Sprint", true);
        this.ladders = new Property<Boolean>(this, "Ladders", true);
        this.sanik = new Property<Boolean>(this, "Sanik", true);
        this.speed = 6.0;
        this.level = 1;
        this.moveSpeed = 0.2873;
        this.speedTimer = 1.3f;
    }
    
    private boolean canSpeed() {
        return !Wrapper.getPlayer().isOnLadder() && !Wrapper.getPlayer().isInWater() && !Wrapper.getPlayer().isSneaking() && Wrapper.getPlayer().motionX != 0.0 && Wrapper.getPlayer().motionZ != 0.0 && !Wrapper.isOnLiquid();
    }
    
    private Block getBlock(final AxisAlignedBB bb) {
        final int y = (int)bb.minY;
        for (int x = MathHelper.floor_double(bb.minX); x < MathHelper.floor_double(bb.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(bb.minZ); z < MathHelper.floor_double(bb.maxZ) + 1; ++z) {
                final Block block = Wrapper.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null) {
                    return block;
                }
            }
        }
        return null;
    }
    
    @EventTarget
    public void onPre(final EventPreMotionUpdates e) {
        if (this.sanik.value) {
            ++this.delay1;
        }
        if (this.canSpeed() && this.sanik.value) {
            ++this.delay2;
        }
        if (!this.canSpeed()) {
            this.delay2 = -2.0;
            Timer.timerSpeed = 1.0f;
        }
        if (this.ladders.value) {
            if (Wrapper.mc.thePlayer.isOnLadder() && Wrapper.mc.thePlayer.isSneaking()) {
                final EntityPlayerSP thePlayer3;
                final EntityPlayerSP thePlayer = thePlayer3 = Wrapper.mc.thePlayer;
                thePlayer3.motionY *= 0.0;
            }
            else if (Wrapper.mc.thePlayer.isOnLadder() && Wrapper.mc.thePlayer.isCollidedHorizontally) {
                final EntityPlayerSP thePlayer4;
                final EntityPlayerSP thePlayer2 = thePlayer4 = Wrapper.mc.thePlayer;
                thePlayer4.motionY *= 2.4;
            }
        }
        if (this.sprint.value) {
            if (Wrapper.mc.thePlayer.isSneaking() || Wrapper.mc.thePlayer.isCollidedHorizontally || Wrapper.mc.thePlayer.motionX == 0.0 || Wrapper.mc.thePlayer.isOnLadder() || (!Cheese.moduleManager.getModState("NoSlowDown") && Wrapper.mc.thePlayer.isBlocking())) {
                Wrapper.mc.thePlayer.setSprinting(false);
            }
            else if (!this.sanik.value && Wrapper.mc.thePlayer.moveForward > 0.0f) {
                Wrapper.mc.thePlayer.setSprinting(true);
            }
            else if (this.sanik.value) {
                Wrapper.mc.thePlayer.setSprinting(true);
            }
        }
        final double speed = 3.18;
        if (this.isColliding(Wrapper.mc.thePlayer.boundingBox.offset(Wrapper.getPlayer().motionX * 2.0, 0.0, Wrapper.getPlayer().motionZ * 2.0)) || !Wrapper.mc.thePlayer.onGround || !Wrapper.mc.thePlayer.isCollidedVertically) {
            return;
        }
        final boolean critting = Criticals.attacking;
        if (Wrapper.getBlockAbovePlayer(Wrapper.mc.thePlayer, 1.2).isCollidable() && Wrapper.getBlockAbovePlayer(Wrapper.mc.thePlayer, 1.2).isFullBlock()) {
            return;
        }
        if (this.delay2 == 2.0) {
            e.setY(e.getY() + (critting ? 0.36251099999999997 : 0.4));
        }
    }
    
    private boolean isColliding(AxisAlignedBB bb) {
        boolean colliding = false;

        for(Iterator iterator = Wrapper.mc.theWorld.getCollidingBoundingBoxes(Wrapper.mc.thePlayer, bb).iterator(); iterator.hasNext(); colliding = true) {
           AxisAlignedBB boundingBox = (AxisAlignedBB)iterator.next();
        }

        if(this.getBlock(bb.offset(0.0D, -0.1D, 0.0D)) instanceof BlockAir) {
           colliding = true;
        }
		return colliding;
    }
    
    @EventTarget
    public void onUpdate(final EventPostMotionUpdates post) {
        if (!Wrapper.getBlockAbovePlayer(Wrapper.mc.thePlayer, 1.2).isCollidable() || !Wrapper.getBlockAbovePlayer(Wrapper.mc.thePlayer, 1.2).isFullBlock()) {
            if (Wrapper.mc.thePlayer.onGround && Wrapper.mc.thePlayer.isCollidedVertically && Wrapper.isMoving()) {
                final boolean decrease = Wrapper.mc.thePlayer.ticksExisted % 2 == 0;
                final boolean strafing = Wrapper.mc.gameSettings.keyBindForward.getIsKeyPressed() && (Wrapper.mc.gameSettings.keyBindLeft.getIsKeyPressed() || Wrapper.mc.gameSettings.keyBindRight.getIsKeyPressed());
                final double speed = strafing ? 3.03 : (decrease ? 3.3 : 3.4);
                final boolean c = Criticals.attacking;
                if (Wrapper.mc.thePlayer.ticksExisted % 2 == 0) {
                    Timer.timerSpeed = 1.55f;
                }
                else {
                    Timer.timerSpeed = 1.0f;
                }
                if (this.delay2 == 1.0) {
                    final EntityPlayerSP thePlayer5;
                    final EntityPlayerSP thePlayer = thePlayer5 = Wrapper.mc.thePlayer;
                    thePlayer5.motionX *= speed;
                    final EntityPlayerSP thePlayer6;
                    final EntityPlayerSP thePlayer2 = thePlayer6 = Wrapper.mc.thePlayer;
                    thePlayer6.motionZ *= speed;
                }
                else if (this.delay2 == 2.0) {
                    Timer.timerSpeed = 1.1f;
                    final EntityPlayerSP thePlayer7;
                    final EntityPlayerSP thePlayer3 = thePlayer7 = Wrapper.mc.thePlayer;
                    thePlayer7.motionX /= 1.4;
                    final EntityPlayerSP thePlayer8;
                    final EntityPlayerSP thePlayer4 = thePlayer8 = Wrapper.mc.thePlayer;
                    thePlayer8.motionZ /= 1.4;
                    this.delay2 = 0.0;
                }
                return;
            }
        }
        this.delay2 = 0.0;
    }
    
    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (Wrapper.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Wrapper.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        final double delay1 = 0.0;
        this.delay3 = 0.0;
        this.delay2 = 0.0;
        this.delay1 = 0.0;
        Timer.timerSpeed = 1.0f;
        Blocks.packed_ice.slipperiness = 0.98f;
        Blocks.ice.slipperiness = 0.98f;
    }
    
    @Override
    protected void addCommand() {
        Cheese.getInstance();
        final CommandManager commandManager = Cheese.commandManager;
        CommandManager.addCommand(new Command("AnotherDevSpeed", "Unknown Option! ", null, "<Enter value of tickspeed>", "Fucklord shines down cheese walking shoes unto you") {
            @EventTarget
            public void onTick(final EventTick ev) {
                final String nigger = EventChatSend.getMessage().split(" ")[1];
                AnotherDevSpeed.access$0(AnotherDevSpeed.this, Float.parseFloat(nigger));
                Logger.logChat("Tick speed set to " + AnotherDevSpeed.this.speedTimer);
                Cheese.getInstance();
                Cheese.fileManager.saveFiles();
                this.Toggle();
            }
        });
    }
    
    static /* synthetic */ void access$0(final AnotherDevSpeed anotherDevSpeed, final float speedTimer) {
        anotherDevSpeed.speedTimer = speedTimer;
    }
}
