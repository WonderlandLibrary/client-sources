package gq.vapu.czfclient.Module.Modules.Blatant;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Render.EventRender3D;
import gq.vapu.czfclient.API.Events.World.EventMove;
import gq.vapu.czfclient.API.Events.World.EventPostUpdate;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.Math.MathUtil;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockSign;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;


public class Teleport extends Module {
    public Mode mode = new Mode("Mode", "mode", TeleMode.values(), TeleMode.Hypixel);
    int counter;
    int level;
    double moveSpeed;
    double lastDist;
    boolean b2;
    boolean startTP = false, needTP = false;
    TimerUtil cooldown = new TimerUtil();
    boolean canTP;
    int TeleX, TeleZ;
    private final Numbers<Double> speed = new Numbers<Double>("Speed", "Speed", 14.0, 0.0, 20.0, 1.0);
    private final Numbers<Double> maxticks = new Numbers<Double>("MaxTicks", "MaxTicks", 25.0, 0.0, 50.0, 5.0);
    private final Option<Boolean> autodis = new Option<Boolean>("autodis", "autodis", true);
    private int zoom;
    private int packetCounter;
    private final TimerUtil deactivationDelay = new TimerUtil();

    public Teleport() {
        super("Teleport", new String[]{"Teleport"}, ModuleType.Blatant);
        setColor(new Color(158, 114, 243).getRGB());
        addValues(mode, speed, maxticks, autodis);
    }

    public static Block getBlock(final int x, final int y, final int z) {
        return mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static Block getBlock(final BlockPos pos) {
        return mc.theWorld.getBlockState(pos).getBlock();
    }

    public int Height() {
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            return mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        }
        return 0;
    }

    public void damagePlayer(int damage) {
        for (int index = 0; index < 49; index++) {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                    mc.thePlayer.posY + 0.06249D, mc.thePlayer.posZ, false));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                    mc.thePlayer.posY, mc.thePlayer.posZ, index == 49));
        }
    }

    public void onEnable() {
		/*try {
			WebUtils.getWeb("http://alxb.com.cn/Worst/upconfig.php?Name=1&type=2&content=AntiBot\r\n" +
					"[p]AutoSword\r\n" +
					"[p]Criticals\r\n" +
					"[p]InvMove\r\n" +
					"[p]Jesus\r\n" +
					"[p]NoSlowDown\r\n" +
					"[p]Sprint\r\n" +
					"[p]AntiFall\r\n" +
					"[p]AntiVelocity\r\n" +
					"[p]AutoTool\r\n" +
					"[p]NoFall\r\n" +
					"[p]NoHurtcam\r\n" +
					"[p]Teams\r\n" +
					"[p]Animations\r\n" +
					"[p]Brightness\r\n" +
					"[p]Chams\r\n" +
					"[p]Crosshair\r\n" +
					"[p]EnchantEffect\r\n" +
					"[p]Health\r\n" +
					"[p]HUD\r\n" +
					"[p]KeyRender\r\n" +
					"[p]NameTags\r\n" +
					"[p]Radar\r\n" +
					"[p]RainbowColor\r\n" +
					"[p]Scoreboard\r\n" +
					"[p]TargetHUD\r\n" +
					"[p]AutoArmor\r\n" +
					"[p]AutoGG\r\n" +
					"[p]AutoSpam\r\n" +
					"[p]SpeedMine\r\n" +
					"[p]PacketMotior\r\n" +
					"[p]");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}*/

        startTP = false;
        level = 1;
        moveSpeed = 0.1;
        b2 = true;
        lastDist = 0.0;
    }

    public void onDisable() {
        level = 1;
        moveSpeed = 0.1;
        b2 = false;
        lastDist = 0.0;
        net.minecraft.util.Timer.timerSpeed = 1.0f;
        canTP = false;
    }

    @EventHandler
    public void EventRender3D(EventRender3D er) {
        try {
            final int x = getTeleBlock().getBlockPos().getX();
            final int y = getTeleBlock().getBlockPos().getY();
            final int z = getTeleBlock().getBlockPos().getZ();
            final Block block1 = getBlock(x, y, z);
            final Block block2 = getBlock(x, y + 1, z);
            final Block block3 = getBlock(x, y + 2, z);
            final boolean blockBelow = !(block1 instanceof BlockSign) && block1.getMaterial().isSolid();
            final boolean blockLevel = !(block2 instanceof BlockSign) && block1.getMaterial().isSolid();
            final boolean blockAbove = !(block3 instanceof BlockSign) && block1.getMaterial().isSolid();
            canTP = getBlock(getTeleBlock().getBlockPos()).getMaterial() != Material.air && blockBelow && blockLevel
                    && blockAbove && !(getBlock(getTeleBlock().getBlockPos()) instanceof BlockChest);
        } catch (Exception e) {

        }
    }

    public Vec3 func_174824_e(float partial) {
        if (partial == 1.0F) {
            return new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
        } else {
            double var2 = mc.thePlayer.prevPosX + (mc.thePlayer.posX - mc.thePlayer.prevPosX) * partial;
            double var4 = mc.thePlayer.prevPosY + (mc.thePlayer.posY - mc.thePlayer.prevPosY) * partial
                    + mc.thePlayer.getEyeHeight();
            double var6 = mc.thePlayer.prevPosZ + (mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * partial;
            return new Vec3(var2, var4, var6);
        }
    }

    public MovingObjectPosition getTeleBlock() {
        Vec3 var4 = func_174824_e(EventRender3D.ticks);
        Vec3 var5 = mc.thePlayer.getLook(EventRender3D.ticks);
        Vec3 var6 = var4.addVector(var5.xCoord * 70, var5.yCoord * 70, var5.zCoord * 70);
        return mc.thePlayer.worldObj.rayTraceBlocks(var4, var6, false, false, true);

    }

    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        setSuffix(mode.getValue());
        if (cooldown.hasReached(500) && canTP && Mouse.isButtonDown(1) && !mc.thePlayer.isSneaking()
                && mc.inGameHasFocus && getTeleBlock().entityHit == null
                && !(getBlock(getTeleBlock().getBlockPos()) instanceof BlockChest)) {
            if (ModuleManager.getModuleByClass(Scaffold.class).isEnabled()) {
                ModuleManager.getModuleByClass(Scaffold.class).setEnabled(false);
            }
            cooldown.reset();
            if (mode.getValue() == TeleMode.Hypixel) {
                needTP = true;
                startTP = false;
                damagePlayer(1);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        startTP = true;
                        level = 1;
                        moveSpeed = 0.1;
                        b2 = true;
                        lastDist = 0.0;
                        zoom = maxticks.getValue().intValue();
                        mc.thePlayer.motionY = 0.41999999999875 + Height() * 0.1;
                        TeleX = getTeleBlock().getBlockPos().getX();
                        TeleZ = getTeleBlock().getBlockPos().getZ();
                        this.cancel();
                    }
                }, 240L);
            }
        }
        if (mode.getValue() == TeleMode.Hypixel) {
            if (needTP) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
                if (startTP) {
                    ++counter;
                    if (this.zoom > 0 && speed.getValue().floatValue() > 0.0F) {
                        net.minecraft.util.Timer.timerSpeed = 1.0F + speed.getValue().floatValue();
                    } else {
                        net.minecraft.util.Timer.timerSpeed = 1.0F;
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(),
                                Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
                        needTP = false;
                        if (this.autodis.getValue().booleanValue()) {
                            ModuleManager.getModuleByClass(Teleport.class).setEnabled(false);
                        }
                    }
                    if (Math.max(TeleX, mc.thePlayer.posX) - Math.min(TeleX, mc.thePlayer.posX) < 1
                            && Math.max(TeleZ, mc.thePlayer.posZ) - Math.min(TeleZ, mc.thePlayer.posZ) < 1) {
                        net.minecraft.util.Timer.timerSpeed = 1.0F;
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(),
                                Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
                        needTP = false;
                        if (this.autodis.getValue().booleanValue()) {
                            ModuleManager.getModuleByClass(Teleport.class).setEnabled(false);
                        }
                    }
                    zoom--;
                    if (mc.thePlayer.moveForward == 0.0f) {

                        if (mc.thePlayer.moveStrafing == 0.0f) {

                            EntityPlayerSP thePlayer4 = mc.thePlayer;

                            double n = mc.thePlayer.posX + 1.0;

                            double n2 = mc.thePlayer.posY + 1.0;

                            thePlayer4.setPosition(n, n2, mc.thePlayer.posZ + 1.0);

                            EntityPlayerSP thePlayer5 = mc.thePlayer;

                            double prevPosX = mc.thePlayer.prevPosX;

                            double prevPosY = mc.thePlayer.prevPosY;

                            thePlayer5.setPosition(prevPosX, prevPosY, mc.thePlayer.prevPosZ);

                            mc.thePlayer.motionX = 0.0;

                            mc.thePlayer.motionZ = 0.0;
                        }
                    }

                    mc.thePlayer.motionY = 0.0;
                    if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown()) {

                        EntityPlayerSP thePlayer6 = mc.thePlayer;
                        thePlayer6.motionY += 0.5;
                    }
                    if (Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown()) {

                        EntityPlayerSP thePlayer7 = mc.thePlayer;
                        thePlayer7.motionY -= 0.5;
                    }
                    if (counter != 1 && counter == 2) {

                        EntityPlayerSP thePlayer8 = mc.thePlayer;

                        double posX = mc.thePlayer.posX;

                        double n3 = mc.thePlayer.posY + 1.0E-10;

                        thePlayer8.setPosition(posX, n3, mc.thePlayer.posZ);
                        counter = 0;
                    }
                } else {
                    mc.thePlayer.motionX *= 0.2;
                    mc.thePlayer.motionZ *= 0.2;
                }
            }
        }
    }

    @EventHandler
    public void onPost(EventPostUpdate e) {
        if (mode.getValue() == TeleMode.Hypixel) {
            if (needTP) {
                if (startTP) {
                    double posX = mc.thePlayer.posX;
                    double xDist = posX - mc.thePlayer.prevPosX;
                    double posZ = mc.thePlayer.posZ;
                    double zDist = posZ - mc.thePlayer.prevPosZ;
                    lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
                }
            }
        }
    }

    @EventHandler
    private void onMove(EventMove e) {
        if (mode.getValue() == TeleMode.Hypixel) {
            if (needTP) {
                if (startTP) {
                    float forward = MovementInput.moveForward;
                    float strafe = MovementInput.moveStrafe;
                    float yaw = mc.thePlayer.rotationYaw;
                    double mx = Math.cos(Math.toRadians(yaw + 90.0f));
                    double mz = Math.sin(Math.toRadians(yaw + 90.0f));
                    if (forward == 0.0f && strafe == 0.0f) {
                        EventMove.x = 0.0;
                        EventMove.z = 0.0;
                    }
                    if (b2) {
                        Label_0393:
                        {
                            Label_0137:
                            {
                                if (level == 1) {

                                    if (mc.thePlayer.moveForward == 0.0f) {

                                        if (mc.thePlayer.moveStrafing == 0.0f) {
                                            break Label_0137;
                                        }
                                    }
                                    level = 2;
                                    double boost = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.56 : 2.034;
                                    moveSpeed = boost * MathUtil.getBaseMovementSpeed();
                                    break Label_0393;
                                }
                            }
                            if (level == 2) {
                                level = 3;
                                moveSpeed *= 2.1399;
                            } else if (level == 3) {
                                level = 4;
                                double difference = ((mc.thePlayer.ticksExisted % 2 == 0) ? 0.0103 : 0.0123)
                                        * (lastDist - MathUtil.getBaseMovementSpeed());
                                moveSpeed = lastDist - difference;
                            } else {

                                WorldClient theWorld = mc.theWorld;

                                EntityPlayerSP thePlayer = mc.thePlayer;

                                AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox();
                                double n2 = 0.0;

                                Label_0291:
                                {
                                    if (theWorld.getCollidingBoundingBoxes(thePlayer,
                                            boundingBox.offset(n2, mc.thePlayer.motionY, 0.0)).size() <= 0) {

                                        if (!mc.thePlayer.isCollidedVertically) {
                                            break Label_0291;
                                        }
                                    }
                                    level = 1;
                                }
                                moveSpeed = lastDist - lastDist / 159.0;
                            }
                        }
                        double moveSpeed = Math.max(this.moveSpeed, MathUtil.getBaseMovementSpeed());
                        this.moveSpeed = moveSpeed;
                        if (strafe == 0.0f) {
                            EventMove.x = forward * moveSpeed * mx + strafe * moveSpeed * mz;
                            EventMove.z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
                        } else if (strafe != 0.0f) {
                            mc.thePlayer.setMoveSpeed(e, moveSpeed);
                        }
                        if (forward == 0.0f && strafe == 0.0f) {
                            EventMove.x = 0.0;
                            EventMove.z = 0.0;
                        }
                    }
                }
            }
        }
    }

    double getBaseMoveSpeed() {
        double baseSpeed = 0.275;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }


    public enum TeleMode {
        Hypixel
    }
}