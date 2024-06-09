package host.kix.uzi.module.modules.combat;

import com.darkmagician6.eventapi.SubscribeEvent;
import com.darkmagician6.eventapi.types.EventType;
import host.kix.uzi.Uzi;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.file.CustomFile;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.minecraft.Stopwatch;
import host.kix.uzi.utilities.value.Value;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Kix on 2/4/2017.
 */
public class Killaura extends Module {

    private Value<Boolean> teams = new Value<Boolean>("Teams", false);
    private Value<Integer> fov = new Value<Integer>("FOV", 360, 1, 360);
    private Value<Integer> delay = new Value<Integer>("Delay", 75, 0, 1000);
    private Value<Float> range = new Value<Float>("Range", 4.4F, 3.0F, 7.0F);
    private Value<Integer> ticksExisted = new Value<Integer>("Ticks", 16, 0, 1000);
    private Value<Mode> mode = new Value<Mode>("Mode", Mode.SINGLE);
    private Value<Boolean> autoBlock = new Value<Boolean>("AutoBlock", true);
    private Value<Boolean> players = new Value<Boolean>("Players", true);
    private Value<Boolean> animals = new Value<Boolean>("Animals", false);
    private Value<Boolean> monsters = new Value<Boolean>("Monsters", false);
    private Value<Boolean> invisibles = new Value<Boolean>("Invisibles", false);
    private List<EntityLivingBase> loaded = new ArrayList<>();
    private List<EntityLivingBase> possibleTargets = new ArrayList<EntityLivingBase>();
    private int currentTarget;
    private EntityLivingBase target;
    private Stopwatch time = new Stopwatch();
    private Stopwatch switchTime = new Stopwatch();


    public Killaura() {
        super("Killaura", 0, Category.COMBAT);
        getValues().add(delay);
        getValues().add(fov);
        getValues().add(range);
        getValues().add(ticksExisted);
        getValues().add(mode);
        getValues().add(autoBlock);
        getValues().add(teams);
        getValues().add(players);
        getValues().add(animals);
        getValues().add(monsters);
        getValues().add(invisibles);
        target = null;
        Uzi.getInstance().getFileManager().addContent(new CustomFile("killaura") {
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
                                    if (arguments[1].equalsIgnoreCase("single")) {
                                        value.setValue(Mode.SINGLE);
                                    } else if (arguments[1].equalsIgnoreCase("switch")) {
                                        value.setValue(Mode.SWITCH);
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

    private List<EntityLivingBase> getNearby(float range) {
        List<EntityLivingBase> nearby = new ArrayList<EntityLivingBase>();
        for (Entity e : mc.thePlayer.getEntityWorld().loadedEntityList) {
            if (Uzi.getInstance().getFriendManager().get(e.getName()).isPresent()) {
                continue;
            }
            if (!(e instanceof EntityLivingBase)
                    || e.getName().equalsIgnoreCase(mc.thePlayer.getName())
                    || !e.isEntityAlive() || mc.thePlayer.getDistanceToEntity(e) > range
                    || e == mc.thePlayer) {
                continue;
            }
            nearby.add((EntityLivingBase) e);
        }
        nearby.sort((o1, o2) -> Float.compare(mc.thePlayer.getDistanceToEntity(o1),
                mc.thePlayer.getDistanceToEntity(o2)));
        return nearby;
    }

    @SubscribeEvent
    public void update(UpdateEvent event) {
        switch (mode.getValue()) {
            case SINGLE:
                target = getBestEntity();
                boolean blockCheck1 = (autoBlock.getValue() && mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword);
                if (event.type == EventType.PRE && target != null) {
                    event.setYaw(getRotations(target)[0]);
                    event.setPitch(getRotations(target)[1]);
                } else {
                    if (target != null && blockCheck1 && mc.thePlayer.getDistanceToEntity(target) < range.getValue())
                        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
                    if (mc.thePlayer.isBlocking())
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.fromAngle(-255.0D)));
                    if (target != null && time.hasCompleted(delay.getValue())) {
                        attack(target);
                        if (mc.thePlayer.isBlocking())
                            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(0, 0, 0), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
                        time.reset();
                    }
                }
                break;
            case SWITCH:
                target = getBestEntity();
                boolean blockCheck = (autoBlock.getValue() && mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword);
                if (event.type == EventType.PRE && target != null) {
                    event.setYaw(getRotations(possibleTargets.get(currentTarget))[0]);
                    event.setPitch(getRotations(possibleTargets.get(currentTarget))[1]);
                } else {
                    if (target != null && blockCheck && mc.thePlayer.getDistanceToEntity(target) < range.getValue())
                        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
                    if (mc.thePlayer.isBlocking())
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.fromAngle(-255.0D)));
                    if (target != null && time.hasCompleted(delay.getValue())) {
                        this.possibleTargets = getNearby(range.getValue());
                        this.target = null;

                        if (!this.possibleTargets.isEmpty()) {
                            if (this.currentTarget >= this.possibleTargets.size()) {
                                this.currentTarget = 0;
                            }
                            this.possibleTargets = this.sort(this.possibleTargets);
                            this.target = this.possibleTargets.get(currentTarget);
                        }
                        if (switchTime.hasCompleted(500)) {
                            this.currentTarget++;
                            switchTime.reset();
                        }
                        attack(possibleTargets.get(currentTarget));
                        if (mc.thePlayer.isBlocking())
                            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(0, 0, 0), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
                        time.reset();
                    }
                }
                break;
        }
    }

    public List<EntityLivingBase> sort(List<EntityLivingBase> possibleTargets2) {
        possibleTargets2.sort((target1, target2) -> {
            double distance1 = angleDistanceYawNoAbs(target1);
            double distance2 = angleDistanceYawNoAbs(target2);
            return distance1 < distance2 ? 1 : distance1 == distance2 ? 0 : -1;
        });
        return possibleTargets2;
    }

    public int angleDistanceYawNoAbs(Entity entity) {
        float[] neededRotations = getRotationsNeeded(entity);
        if (neededRotations != null) {
            float neededYaw = neededRotations[0] - mc.thePlayer.rotationYaw;
            return (int) neededYaw;
        }
        return -1;
    }

    public float[] getRotationsNeeded(final Entity entity) {
        if (entity == null) {
            return null;
        }
        final double xSize = entity.posX - mc.thePlayer.posX;
        final double ySize = entity.posY + entity.getEyeHeight() / 2.0
                - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        final double zSize = entity.posZ - mc.thePlayer.posZ;
        final double theta = MathHelper.sqrt_double(xSize * xSize + zSize * zSize);
        final float yaw = (float) (Math.atan2(zSize, xSize) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float) (-(Math.atan2(ySize, theta) * 180.0 / 3.141592653589793));
        return new float[]{
                (mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw)) % 360.0f,
                (mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch))
                        % 360.0f};
    }

    private EntityLivingBase getBestEntity() {
        if (loaded != null) {
            loaded.clear();
        }
        for (Object object : mc.theWorld.loadedEntityList) {
            if (object instanceof EntityLivingBase) {
                EntityLivingBase e = (EntityLivingBase) object;
                if (isValid(e)) {
                    loaded.add(e);
                }
            }
        }
        assert loaded != null;
        if (loaded.isEmpty()) {
            return null;
        }
        loaded.sort((o1, o2) -> {
            float[] rot1 = getRotations(o1);
            float[] rot2 = getRotations(o2);
            return Float.compare((mc.thePlayer.rotationYaw - rot1[0]) % 0,
                    (mc.thePlayer.rotationYaw - rot2[0]) % 0);
        });
        return loaded.get(0);
    }


    public boolean isValid(EntityLivingBase entity) {
        if (teams.getValue() && entity != null) {
            final String name = entity.getDisplayName().getFormattedText();
            final StringBuilder append = new StringBuilder().append("§");
            if (name.startsWith(append.append(mc.thePlayer.getDisplayName().getFormattedText().charAt(1)).toString())) {
                return false;
            }
        }
        return (entity != null
                && entity.isEntityAlive()
                && isEntityInFov(entity, fov.getValue())
                && entity != mc.thePlayer
                && (entity instanceof EntityPlayer
                && players.getValue()
                || entity instanceof EntityAnimal
                && animals.getValue()
                || entity instanceof EntityMob ||
                entity instanceof EntitySlime
                        && monsters.getValue())
                && entity.getDistanceToEntity(mc.thePlayer) <= range.getValue()
                && (!entity.isInvisible()
                || invisibles.getValue())
                && entity.ticksExisted > ticksExisted.getValue() && !Uzi.getInstance().getFriendManager().get(entity.getName()).isPresent());
    }

    private boolean isEntityInFov(final EntityLivingBase entity, double angle) {
        angle *= 0.5;
        final double angleDifference = getAngleDifference(mc.thePlayer.rotationYaw, getRotations(entity)[0]);
        return (angleDifference > 0.0 && angleDifference < angle)
                || (-angle < angleDifference && angleDifference < 0.0);
    }

    private void attack(EntityLivingBase entity) {
        final float sharpLevel = EnchantmentHelper.func_152377_a(mc.thePlayer.getHeldItem(), mc.thePlayer.getCreatureAttribute());
        final boolean wasSprinting = mc.thePlayer.isSprinting();
        if (sharpLevel > 0) {
            mc.thePlayer.onEnchantmentCritical(entity);
        }
        if (wasSprinting)
            mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));

        mc.thePlayer.swingItem();
        mc.playerController.attackEntity(mc.thePlayer, entity);

        if (wasSprinting)
            mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
        mc.thePlayer.setSprinting(wasSprinting);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        currentTarget = 0;
    }

    private float[] getRotations(Entity entity) {
        if (entity == null)
            return null;

        double diffX = entity.posX - mc.thePlayer.posX;
        double diffZ = entity.posZ - mc.thePlayer.posZ;
        double diffY;
        if ((entity instanceof EntityLivingBase)) {
            EntityLivingBase elb = (EntityLivingBase) entity;
            diffY = elb.posY
                    + (elb.getEyeHeight() - 0.4)
                    - (mc.thePlayer.posY + mc.thePlayer
                    .getEyeHeight());
        } else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY)
                    / 2.0D
                    - (mc.thePlayer.posY + mc.thePlayer
                    .getEyeHeight());
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);

        return new float[]{yaw, pitch};
    }

    private float getAngleDifference(float direction, float rotationYaw) {
        float phi = Math.abs(rotationYaw - direction) % 360;
        return phi > 180 ? 360 - phi : phi;
    }

    private enum Mode {
        SINGLE, SWITCH
    }

}
