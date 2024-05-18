package host.kix.uzi.module.modules.movement;

import com.darkmagician6.eventapi.SubscribeEvent;

import com.darkmagician6.eventapi.types.EventType;
import host.kix.uzi.Uzi;
import host.kix.uzi.events.MotionEvent;
import host.kix.uzi.events.TickEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.file.CustomFile;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.value.Value;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.util.List;

/**
 * Created by myche on 2/4/2017.
 */
public class Speed extends Module {

    private boolean quick = true;
    private double moveSpeed;
    private double lastDist;
    private int stage;
    private double veltStage;
    private int capsarstage;
    private Value<Mode> mode = new Value<Mode>("Mode", Mode.HOP);

    public Speed() {
        super("Speed", Keyboard.KEY_NONE, Category.MOVEMENT);
        add(mode);
        Uzi.getInstance().getFileManager().addContent(new CustomFile("speed") {
            @Override
            public void loadFile() {
                try {
                    final BufferedReader reader = new BufferedReader(new FileReader(getFile()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        final String[] arguments = line.split(":");
                        if (arguments.length == 2) {
                            final Value value = findGivenValue(arguments[0]);
                            if (value != null) {
                                if (value.getValue() instanceof Boolean) {
                                    value.setValue(Boolean.parseBoolean(arguments[1]));
                                } else if (value.getValue() instanceof Integer) {
                                    value.setValue(Integer.parseInt(arguments[1]));
                                } else if (value.getValue() instanceof Double) {
                                    value.setValue(Double.parseDouble(arguments[1]));
                                } else if (value.getValue() instanceof Float) {
                                    value.setValue(Float.parseFloat(arguments[1]));
                                } else if (value.getValue() instanceof Long) {
                                    value.setValue(Long.parseLong(arguments[1]));
                                } else if (value.getValue() instanceof String) {
                                    value.setValue(arguments[1]);
                                } else if (value.getValue() instanceof Mode) {
                                    if (arguments[1].equalsIgnoreCase("hop")) {
                                        value.setValue(Mode.HOP);
                                    } else if (arguments[1].equalsIgnoreCase("capsar")) {
                                        value.setValue(Mode.CAPSAR);
                                    } else if (arguments[1].equalsIgnoreCase("kix")) {
                                        value.setValue(Mode.KIX);
                                    } else if (arguments[1].equalsIgnoreCase("offset")) {
                                        value.setValue(Mode.OFFSET);
                                    } else if (arguments[1].equalsIgnoreCase("timer")) {
                                        value.setValue(Mode.TIMER);
                                    } else if (arguments[1].equalsIgnoreCase("valk")) {
                                        value.setValue(Mode.VALK);
                                    } else if (arguments[1].equalsIgnoreCase("yport")) {
                                        value.setValue(Mode.YPORT);
                                    } else if (arguments[1].equalsIgnoreCase("velt")) {
                                        value.setValue(Mode.VELT);
                                    }
                                }
                            }
                        }
                    }
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void saveFile() {
                try {
                    final BufferedWriter writer = new BufferedWriter(new FileWriter(
                            getFile()));
                    for (final Value val : getValues()) {
                        writer.write(val.getName().toLowerCase() + ":"
                                + val.getValue());
                        writer.newLine();
                    }
                    writer.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static boolean isMoving(Entity ent) {
        return ent == Minecraft.getMinecraft().thePlayer ? ((Minecraft.getMinecraft().thePlayer.moveForward != 0 || Minecraft.getMinecraft().thePlayer.moveStrafing != 0)) : (ent.motionX != 0 || ent.motionZ != 0);
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1f;
        this.moveSpeed = this.getBaseMoveSpeed();
        mc.gameSettings.viewBobbing = true;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        if (mc.thePlayer != null) {
            this.moveSpeed = this.getBaseMoveSpeed();
        }
        this.lastDist = 0.0;
        this.stage = 4;
        super.onEnable();
    }

    public float getDirection() {
        float yaw = this.mc.thePlayer.rotationYaw;
        if (this.mc.thePlayer.moveForward < 0) {
            yaw += 180;
        }
        float forward = 1;
        if (this.mc.thePlayer.moveForward < 0) {
            forward = -0.5F;
        } else if (this.mc.thePlayer.moveForward > 0) {
            forward = 0.5F;
        }
        if (this.mc.thePlayer.moveStrafing > 0) {
            yaw -= 90 * forward;
        }
        if (this.mc.thePlayer.moveStrafing < 0) {
            yaw += 90 * forward;
        }
        yaw *= 0.017453292F;
        return yaw;
    }

    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

    public float getSpeed() {
        return (float) Math.sqrt((this.mc.thePlayer.motionX * this.mc.thePlayer.motionX) + (this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ));
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        switch (mode.getValue()) {
            case HOP: {
                if (!(this.isOnLiquid() || this.isInLiquid())) {
                    if (isMoving(this.mc.thePlayer) && this.mc.thePlayer.onGround) {
                        if (this.mc.thePlayer.ticksExisted % 2 != 0 && !this.mc.isSingleplayer() && !this.mc.thePlayer.movementInput.jump) {
                            //event.y = this.mc.thePlayer.posY + 0.4;
                            event.onGround = false;
                            float speed = this.mc.thePlayer.ticksExisted % 2 == 0 ? 0.243f * 2.4f : 0.45f;
                            this.mc.thePlayer.motionX = -(Math.sin(this.getDirection()) * speed);
                            mc.thePlayer.motionY = 0.4;
                            this.mc.thePlayer.motionZ = Math.cos(this.getDirection()) * speed;
                            mc.timer.timerSpeed = 1.12f;
                        }
                    }
                }
                break;
            }
            case KIX:
                mc.timer.timerSpeed = quick ? 4F : 0.8F;
                break;
            case TIMER:
                mc.timer.timerSpeed = 2F;
                break;
            case OFFSET:
                if (event.type == EventType.PRE) {
                    if (!(this.isOnLiquid() || this.isInLiquid())) {
                        if (isMoving(this.mc.thePlayer) && this.mc.thePlayer.onGround) {
                            if (this.mc.thePlayer.ticksExisted % 2 != 0 && !this.mc.isSingleplayer()
                                    && !this.mc.thePlayer.movementInput.jump) {
                                event.y = this.mc.thePlayer.posY + 0.4;
                                event.onGround = false;
                            }
                            float speed = this.mc.thePlayer.ticksExisted % 2 == 0 ? 0.15f * 2.32F : 0.15F;
                            this.mc.thePlayer.motionX = -(Math.sin(this.getDirection()) * speed);
                            this.mc.thePlayer.motionZ = Math.cos(this.getDirection()) * speed;

                            mc.timer.timerSpeed = 1.095f;
                        }
                    }
                }
                break;
            case YPORT:
                double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
                double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
                this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
                this.mc.thePlayer.motionY = -0.41D;
                break;
            case VALK:
                this.mc.gameSettings.viewBobbing = false;
                if ((event.type == EventType.PRE) &&
                        (!isOnLiquid()) && (!isInLiquid()) &&
                        (isMoving(this.mc.thePlayer)) && (this.mc.thePlayer.onGround)) {
                    if ((this.mc.thePlayer.ticksExisted % 2 != 0) && (!this.mc.isSingleplayer()) &&
                            (!this.mc.thePlayer.movementInput.jump)) {
                        event.y = (this.mc.thePlayer.posY + 0.4D);
                        event.onGround = false;
                    }
                    float speed = this.mc.thePlayer.ticksExisted % 2 == 0 ? getSpeed() * 3.59F : 0.55F;
                    mc.timer.timerSpeed = this.mc.thePlayer.ticksExisted % 2 == 0 ? 1.2F : 1.0F;
                    this.mc.thePlayer.motionX = (-(Math.sin(getDirection()) * speed));
                    this.mc.thePlayer.motionZ = (Math.cos(getDirection()) * speed);
                }
                break;
            case CAPSAR: {
                switch (this.capsarstage) {
                    case 1: {
                        event.y = event.y + 1.0E-4;
                        ++this.capsarstage;
                        break;
                    }
                    case 2: {
                        event.y = event.y + 2.0E-4;
                        ++this.capsarstage;
                        break;
                    }
                    default: {
                        this.capsarstage = 1;
                        if (!mc.thePlayer.isSneaking() && (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f) && !Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed()) {
                            this.capsarstage = 1;
                            break;
                        }
                        this.moveSpeed = this.getBaseMoveSpeed();
                        break;
                    }
                }
                break;
            }
            case VELT:
                if (this.veltStage == 3.0) {
                    event.setY(event.getY() + 0.4);
                }
                final double x2Dist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
                final double z2Dist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
                this.lastDist = Math.sqrt(x2Dist * x2Dist + z2Dist * z2Dist);
                break;
        }
    }

    @SubscribeEvent
    public void motion(MotionEvent event) {
        if (mode.getValue() == Mode.YPORT) {
            mc.timer.timerSpeed = 1.3F;
            if (MathHelper.round(this.mc.thePlayer.posY - (int) this.mc.thePlayer.posY, 3) == MathHelper.round(0.138D, 3)) {
                this.mc.thePlayer.motionY -= 1.0D;
                event.y -= 0.0631D;
                this.mc.thePlayer.posY -= 0.0631D;
            }
            if ((this.mc.thePlayer.onGround) && ((this.mc.thePlayer.motionX != 0.0D) || (this.mc.thePlayer.motionZ != 0.0D))) {
                this.stage = 2;
            }
            if ((this.stage == 2) && ((this.mc.thePlayer.moveForward != 0.0F) || (this.mc.thePlayer.moveStrafing != 0.0F))) {
                event.y = (this.mc.thePlayer.motionY = 0.39936D);
                this.moveSpeed *= 2.1499999D;
            } else if (this.stage == 3) {
                double difference = 0.66D * (this.lastDist - getBaseMoveSpeed());
                this.moveSpeed = (this.lastDist - difference);
            } else {
                if ((this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0D, this.mc.thePlayer.motionY, 0.0D)).size() > 0) ||
                        (this.mc.thePlayer.isCollidedVertically)) {
                    this.stage = 1;
                }
                this.moveSpeed = (this.lastDist - this.lastDist / 159.0D);
            }
            this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
            float forward = this.mc.thePlayer.movementInput.moveForward;
            float strafe = this.mc.thePlayer.movementInput.moveStrafe;
            float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
            if ((forward == 0.0F) && (strafe == 0.0F)) {
                event.x = 0.0D;
                event.z = 0.0D;
            } else if (forward != 0.0F) {
                if (strafe >= 1.0F) {
                    yaw += (forward > 0.0F ? -45 : 45);
                    strafe = 0.0F;
                } else if (strafe <= -1.0F) {
                    yaw += (forward > 0.0F ? 45 : -45);
                    strafe = 0.0F;
                }
                if (forward > 0.0F) {
                    forward = 1.0F;
                } else if (forward < 0.0F) {
                    forward = -1.0F;
                }
            }
            double mx = Math.cos(Math.toRadians(yaw + 90.0F));
            double mz = Math.sin(Math.toRadians(yaw + 90.0F));
            event.x = (forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz);
            event.z = (forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx);
            this.mc.thePlayer.stepHeight = 0.6F;
            if ((forward == 0.0F) && (strafe == 0.0F)) {
                event.x = 0.0D;
                event.z = 0.0D;
            } else if (forward != 0.0F) {
                if (strafe >= 1.0F) {
                    yaw += (forward > 0.0F ? -45 : 45);
                    strafe = 0.0F;
                } else if (strafe <= -1.0F) {
                    yaw += (forward > 0.0F ? 45 : -45);
                    strafe = 0.0F;
                }
                if (forward > 0.0F) {
                    forward = 1.0F;
                } else if (forward < 0.0F) {
                    forward = -1.0F;
                }
            }
            this.stage += 1;
        } else if (this.mode.getValue() == Mode.VELT) {
            if (mc.thePlayer.onGround || this.veltStage == 3.0) {
                if ((!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward != 0.0f) || mc.thePlayer.moveStrafing != 0.0f) {
                    if (this.veltStage == 2.0) {
                        this.moveSpeed *= 2.149;
                        this.veltStage = 3.0;
                    } else if (this.stage == 3.0) {
                        this.veltStage = 2.0;
                        final double n = 0.66;
                        final double lastDist = this.lastDist;
                        final double difference = n * (lastDist - getBaseMoveSpeed());
                        this.moveSpeed = this.lastDist - difference;
                    } else {
                        final List collidingList = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(0.0, mc.thePlayer.motionY, 0.0));
                        if (collidingList.size() > 0 || mc.thePlayer.isCollidedVertically) {
                            this.veltStage = 1.0;
                        }
                    }
                } else {
                    mc.timer.timerSpeed = 1.0f;
                }
                final double moveSpeed = this.moveSpeed;
                mc.thePlayer.setSpeed(this.moveSpeed = Math.max(moveSpeed, getBaseMoveSpeed()));
            }
        }
    }

    @SubscribeEvent
    public void tick(TickEvent e) {
        if (mode.getValue() == Mode.KIX) {
            quick = !quick;
        }
    }

    private boolean isInLiquid() {
        if (mc.thePlayer == null) return false;
        boolean inLiquid = false;
        int y = (int) mc.thePlayer.boundingBox.minY;
        for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++) {
                Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if ((block != null) && (!(block instanceof BlockAir))) {
                    if (!(block instanceof BlockLiquid)) return false;
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }

    private boolean isOnLiquid() {
        if (mc.thePlayer == null) return false;
        boolean onLiquid = false;
        int y = (int) mc.thePlayer.boundingBox.offset(0.0D, -0.01D, 0.0D).minY;
        for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++) {
                Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if ((block != null) && (!(block instanceof BlockAir))) {
                    if (!(block instanceof BlockLiquid)) return false;
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }

    private enum Mode {
        HOP, CAPSAR, OFFSET, KIX, TIMER, YPORT, VALK, VELT
    }

}
