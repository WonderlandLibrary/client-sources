package rip.helium.cheat.impl.combat.aura;

import me.hippo.systems.lwjeb.annotation.Collect;
import me.tojatta.api.utilities.angle.Angle;
import me.tojatta.api.utilities.angle.AngleUtility;
import me.tojatta.api.utilities.vector.impl.Vector3;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.*;
import java.awt.Color;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.lwjgl.opengl.GL11;
import com.google.common.collect.Lists;

import net.minecraft.util.Vec3;
import rip.helium.ClientBase;
import rip.helium.Helium;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.cheat.FriendManager;
import rip.helium.cheat.commands.cmds.Friend;
import rip.helium.cheat.impl.combat.AntiBot;
import rip.helium.cheat.impl.movement.Flight;
import rip.helium.cheat.impl.visual.Hud;
import rip.helium.event.Stage;
import rip.helium.event.minecraft.*;
import rip.helium.utils.*;
import rip.helium.utils.font.Fonts;
import rip.helium.utils.property.impl.BooleanProperty;
import rip.helium.utils.property.impl.DoubleProperty;
import rip.helium.utils.property.impl.StringsProperty;

public class Aura extends Cheat {

    public static List<EntityLivingBase> targets;

    private StringsProperty abmode = new StringsProperty("Autoblock Mode", "Changes the AB mode", null, false, true, new String[]{"Hypixel", "Vanilla"}, new Boolean[]{true,false});
    private StringsProperty mode = new StringsProperty("Mode", "Changes the mode", null, false, true, new String[]{"Priority"}, new Boolean[]{true});
    private StringsProperty targetPriority = new StringsProperty("Priority", "How the priority target will be selected.", null, false, true, new String[]{"Lowest Health", "Least Armor", "Closest"}, new Boolean[]{true, false, false});
    private StringsProperty targetEntities = new StringsProperty("Targets", "The entites that will be targetted.", null, true, false, new String[]{"Players", "Monsters", "Animals", "Villagers", "Golems"}, new Boolean[]{true, true, false, false, false});
    private BooleanProperty prioritisePlayers = new BooleanProperty("Prioritize Players", "Always hit players before monsters.", () -> targetEntities.getValue().get("Players") && targetEntities.getSelectedStrings().size() > 2, false);
    private static BooleanProperty hvh = new BooleanProperty("HvH", "Optimises Aura for HvH.", null, false);
    private BooleanProperty click = new BooleanProperty("Left Click", "makes it sqo u have to hit left hit", null, false);
    private BooleanProperty silent = new BooleanProperty("Silent", "Silent / Lock Aimbot", null, true);
    private BooleanProperty debug = new BooleanProperty("Debug", "Toggle the debug mode", null, true);
    private BooleanProperty hidedis = new BooleanProperty("Hide Display Name", "in debug mode", null, true);
    private BooleanProperty retarded = new BooleanProperty("SpinnySpin", "yeet", null, true);
    private BooleanProperty multi = new BooleanProperty("Multi", "Hits multiple people, detected.", null, true);
    private BooleanProperty middleClickIgnore = new BooleanProperty("Middle Click to Ignore", "Ignores any entity that you middle click on.", null, false);
    //private BooleanProperty propWalls = new BooleanProperty("Walls", "Ignore entities through walls", null, false);
    public BooleanProperty middleClickReset = new BooleanProperty("Middle Click Reset", "Resets your ignored targets when the world changes.", () -> middleClickIgnore.getValue(), false);
    private BooleanProperty teams = new BooleanProperty("Teams", "Hypixel teams", null, false);
    private BooleanProperty autoBlock = new BooleanProperty("Auto Block", "Blocks when you arent attacking to decrease the amount of damage you take.", null, false);
    private DoubleProperty minimumAttackSpeed = new DoubleProperty("APS Flucutation", "The maximum amount your aps can fluctuate.", null, 2.0, 0, 5.0, 0.1, null);
    private static DoubleProperty prop_maxDistance = new DoubleProperty("Max Distance", "The maximum distance en entity can be to be targetted.", null, 4.0, 0.1, 6.0, 0.1, null);
    private DoubleProperty maximumAttackSpeed = new DoubleProperty("APS", "The minimum amount of times you will attack in a second.", null, 10, 1, 20.0, 0.1, null);

    private boolean isBlocking;
    private static boolean apsDecrease;
    private boolean yawDecrease;
    private boolean pitchDecrease;

    private static Stopwatch apsStopwatch;
    private Stopwatch botClearStopwatch;

    public static int blockDelay, attackSpeed;
    public int waitDelay, groundTicks, crits;
    private static float aps;
    public long lastHit;
    private ScaledResolution sr;
    
    private static int auraDelay;
    private float yaw, pitch, yawIncrease, pitchIncrease, serverSideYaw, serverSidePitch;

    public static int targetIndex;

    private ArrayList<EntityLivingBase> mcf;
    private static ArrayList<EntityLivingBase> targetList;
    public ArrayList<EntityLivingBase> ignoredEntities;
    private ArrayList<EntityLivingBase> whitelistedEntity;

    private double y, x, z, fall;

    public Aura() {
        super("KillAura", "Automatically attacks entities around you.", CheatCategory.COMBAT);
        registerProperties(mode, hvh, retarded, click, targetPriority, targetEntities, multi, debug, teams, prioritisePlayers, middleClickIgnore, middleClickReset, minimumAttackSpeed, maximumAttackSpeed, prop_maxDistance, autoBlock);

        isBlocking = false;
        targetIndex = 0;
        mcf = new ArrayList<>();
        this.sr = RenderUtils.getResolution();
        targetList = new ArrayList<>();
        apsStopwatch = new Stopwatch();
        ignoredEntities = new ArrayList<>();
        whitelistedEntity = new ArrayList<>();
        botClearStopwatch = new Stopwatch();

    }

    static {
        Aura.targets = new ArrayList<EntityLivingBase>();
    }

    public void onEnable() {
        propertyupdate();
        //ClientBase.chat("");
    }

    public void onDisable() {
        propertyupdate();
    }

    public void propertyupdate() {

        //Start fresh with a new target list

        targetIndex = -1;
        targetList.clear();
        ignoredEntities.clear();

        /* Reset block start*/

        isBlocking = false;

        /*Reset block state with packet AutoBlock*/
        attemptStopAutoblockNoSet_Watchdog();

        /*Make last reported aps to 6 aps*/
        aps = 1000 / 6;
        yawIncrease = 0;
        pitchIncrease = 0;

        /*Make sure player isn't null when setting yaw - ClickGui can be called from main menu*/
        if (mc.thePlayer != null) {
            serverSideYaw = getPlayer().rotationYaw;
            serverSidePitch = getPlayer().rotationPitch;
        }

        lastHit = System.currentTimeMillis() + 50;
        setDelay(0);
    }

    /*Pre and post motion updates*/
    @Collect
    public void onBlockStep(BlockStepEvent event) {
        if (mc.thePlayer == null)
            return;
        if (getPlayer().getEntityBoundingBox().minY - getPlayer().posY < .626 && getPlayer().getEntityBoundingBox().minY - getPlayer().posY > .4) {
            waitDelay = 4;
        }
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        setMode("Resolver");

        if (mc.currentScreen != null)
            return;


        boolean preblocking = !mc.thePlayer.isMoving() && mc.thePlayer.ticksExisted % 2 == 0;
        if (auraDelay <= 0) {
            auraDelay = 0;

            if (botClearStopwatch.hasPassed(30000)) {
                ignoredEntities.clear();
                botClearStopwatch.reset();
            }

            getWorld().getLoadedEntityList().forEach(entity -> {
                if (entity != getPlayer() && entity instanceof EntityLivingBase) {
                    if (AntiBot.prop_mode.getValue().get("Watchdog") && Helium.instance.cheatManager.isCheatEnabled("AntiBot")) {
                        if (entity != getPlayer()) {
                            if (entity != getPlayer() && entity instanceof EntityPlayer) {
                                EntityPlayer entityPlayer = (EntityPlayer) entity;
                                if (!isInTablist(entityPlayer)) {
                                    if (!ignoredEntities.contains(entityPlayer)) {
                                        ignoredEntities.add(entityPlayer);
                                        entityPlayer.setInvisible(true);
                                    }
                                }
                            }
                        }
                    }
                }
            });

            updateTargetList();

            if (targetList.isEmpty() || targetList.size() - 1 < targetIndex) {
                targetIndex = -1;
                attemptStopAutoblock_Watchdog();
                if (groundTicks != 0) {
                    getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(getPlayer().posX, mc.thePlayer.posY, mc.thePlayer.posZ, event.getYaw(), event.getPitch(), event.onGround));
                    groundTicks = 0;
                }
                return;
            }

            if (targetIndex == -1) {
                targetIndex = 0;
                attemptStopAutoblock_Watchdog();
                if (groundTicks != 0) {
                    getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(getPlayer().posX, mc.thePlayer.posY, mc.thePlayer.posZ, event.getYaw(), event.getPitch(), event.onGround));
                    groundTicks = 0;
                }
                return;
            }

            if (!isValidTarget(targetList.get(targetIndex))) {
                targetIndex = -1;
                attemptStopAutoblock_Watchdog();
                if (groundTicks != 0) {
                    getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(getPlayer().posX, mc.thePlayer.posY, mc.thePlayer.posZ, event.getYaw(), event.getPitch(), event.onGround));
                    groundTicks = 0;
                }
                return;
            }

            if (targetIndex == -1) {
                targetIndex = 0;
                return;
            }

            if (!isValidTarget(targetList.get(targetIndex))) {
                targetIndex = -1;
                attemptStopAutoblock_Watchdog();
                return;
            }

            pitchIncrease += pitchDecrease ? -Mafs.getRandomInRange(.1, .12) : Mafs.getRandomInRange(.1, .12);
            if (pitchIncrease >= (Aim(targetList.get(targetIndex), event, false)[1] - 30)) {
                pitchDecrease = true;
            }
            if (event.getPitch() <= (Aim(targetList.get(targetIndex), event, false)[1] + 5)) {
                pitchDecrease = false;
            }
            if (event.isPre()) {
                if (mc.thePlayer.fallDistance != 0) {
                    waitDelay = 2;
                }
                boolean cancritical = !Helium.instance.cheatManager.isCheatEnabled("LongJump") && !Helium.instance.cheatManager.isCheatEnabled("Flight") && !Helium.instance.cheatManager.isCheatEnabled("Speed") && targetIndex != -1 && getPlayer().fallDistance == 0.0 && !mc.gameSettings.keyBindJump.isKeyDown() && getPlayer().isCollidedVertically && getPlayer().onGround && !getPlayer().isInWater();
                if (Helium.instance.cheatManager.isCheatEnabled("Criticals")) {
                    if (BlockUtils.getBlockAtPos(new BlockPos(getPlayer().posX, getPlayer().posY - 1, getPlayer().posZ)).isFullBlock() && !BlockUtils.getBlockAtPos(new BlockPos(getPlayer().posX, getPlayer().posY + 1, getPlayer().posZ)).isFullBlock()) {
                        if (waitDelay <= 0) {
                            waitDelay = 0;

                            if (cancritical) {
                                event.setOnGround(false);
                                groundTicks += 1;
                                if (groundTicks == 1) {
                                    event.setOnGround(false);
                                    event.setPosY(event.getPosY() + 0.0627878);
                                } else if (groundTicks == 2) {
                                    event.setOnGround(false);
                                    event.setPosY(event.getPosY() + 0.062663);
                                } else if (groundTicks == 3) {
                                    event.setOnGround(false);
                                    event.setPosY(event.getPosY() + 0.0001);
                                } else if (groundTicks >= 4) {
                                    event.setOnGround(false);
                                    event.setPosY(event.getPosY() + 0.0001);
                                    groundTicks = 0;
                                }
                            } else {
                                waitDelay = 2;
                            }
                        } else {
                            waitDelay -= 1;
                        }
                    } else if (groundTicks != 0) {
                        waitDelay = 4;
                        groundTicks = 0;
                    }
                }
                if (getPlayer().getDistanceToEntity(targetList.get(targetIndex)) > (.1 + Math.abs(getPlayer().posY - targetList.get(targetIndex).posY) * .1)) {
                    /*/
                     * messy code ik
                     */
                    if (!click.getValue()) {
                        float randomr = (float) Mafs.getRandomInRange(0.01, 0.999999);
                        float yeet = (float) Mafs.getRandomInRange(10, 360);
                        if (!retarded.getValue()) {
                            event.setYaw(serverSidePitch = randomr + Aim(targetList.get(targetIndex), event, false)[0]);
                            event.setPitch(serverSidePitch = randomr + Aim(targetList.get(targetIndex), event, false)[1]);
                        } else {
                            event.setYaw(yeet);
                        }
                    } else {
                        float yeet = (float) Mafs.getRandomInRange(0, 360);
                        if (!retarded.getValue()) {
                            event.setYaw(serverSideYaw);
                            event.setPitch(serverSidePitch);
                        } else {
                            event.setYaw(serverSidePitch =  Aim(targetList.get(targetIndex), event, false)[0]);
                            event.setPitch(serverSidePitch =  Aim(targetList.get(targetIndex), event, false)[1]);
                            event.setYaw(yeet);
                        }
                    }
                    if (click.getValue()) {
                        if (mc.gameSettings.keyBindAttack.pressed && mc.gameSettings.keyBindAttack.isKeyDown()) {
                            float randomr = (float) Mafs.getRandomInRange(0.01, 0.999999);
                            event.setYaw(serverSidePitch = randomr + Aim(targetList.get(targetIndex), event, false)[0]);
                            event.setPitch(serverSidePitch = randomr + Aim(targetList.get(targetIndex), event, false)[1]);
                        } else {
                            if (click.getValue()) {
                                if (mc.gameSettings.keyBindAttack.pressed && mc.gameSettings.keyBindAttack.isKeyDown()) {
                                    event.setYaw(serverSideYaw);
                                    event.setPitch(serverSidePitch);
                                }
                            }
                        }
                    }
                }


                if (!holdingSword() && isBlocking) {
                    isBlocking = false;
                }

                if (aps >= (1000 / maximumAttackSpeed.getValue() - minimumAttackSpeed.getValue() + 2)) {
                    apsDecrease = true;
                }
                if (apsDecrease) {
                    if (aps <= ((1000 / (maximumAttackSpeed.getValue())))) {
                        apsDecrease = false;
                    }
                }
                if (mode.getValue().get("Priority")) {
                    attemptStopAutoblock_Watchdog();
                    blockDelay++;
                    if (!multi.getValue()) {
                        if (apsStopwatch.hasPassed((hvh.getValue() ? 50 : aps))) {
                            // no click, just hit normally
                            if (!click.getValue()) {
                                attackexecute(event);
                                if (debug.getValue()) {
                                    if (this.getCurrentTarget() instanceof EntityPlayer) {
                                        ClientBase.chat("§7Attack -> Name: " + this.getCurrentTarget().getName() + " | Dist: " + this.getCurrentTarget().getDistanceToEntity(mc.thePlayer) + " | Your Pitch: " + event.getPitch() + " | Your Yaw: " + event.getYaw());

                                    }
                                }
                            } else {
                                // is holding attack kb?
                                if (mc.gameSettings.keyBindAttack.pressed) {
                                    attackexecute(event);
                                }
                            }
                        }
                    } else {
                        if (event.isPre()) {
                            for (Entity player : mc.theWorld.loadedEntityList) {
                                if (player instanceof EntityLivingBase) {
                                    final EntityLivingBase o = (EntityLivingBase) player;
                                    if (o.getDistanceToEntity(mc.thePlayer) > prop_maxDistance.getValue()) {
                                        continue;
                                    }
                                    if (o == mc.thePlayer) {
                                        continue;
                                    }

                                    if (FriendManager.friends.containsKey(o.getName())) {
                                        continue;
                                    }
                                    Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, o);

                                }
                            }
                        }
                    }
                }
            } else
                attemptStartAutoblock_Watchdog();


            yaw = event.getYaw();
            pitch = event.getPitch();
        } else {
            auraDelay -= 1;
        }
    }

    public static void attackexecute(PlayerUpdateEvent event) {
        if (targetList.isEmpty())
            return;

        if (targetIndex == -1)
            return;

        if (targetIndex > targetList.size() - 1)
            return;

        attack(event);
        apsStopwatch.reset();
    }

    public boolean isInputBetween(double input, double min, double max) {
        return input >= min && input <= max;//Check to see if yaw is between a specified number(input*)
    }

    public Entity raycast(EntityLivingBase target) {
        for (Object object : mc.theWorld.loadedEntityList) {
            Entity entity = (Entity) object;//Credits to verble for this
            if (entity.isInvisible() && object != null) {
                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)
                        && !(entity instanceof EntityArrow)) {
                    if (entity.getEntityBoundingBox().intersectsWith(getPlayer().getEntityBoundingBox())) {
                        return entity;
                    }
                }
            }
        }
        return target;
    }
    
    private void inTab(EntityLivingBase player) {
    	String status = "§cFalse";
    	if (isInTablist(player)) {
    		status = "§aTrue";
    	} else {
    		status = "§cFalse";
    	}
    }
    

    private boolean isInTablist(EntityLivingBase player) {
        if (mc.isSingleplayer()) {
            return true;
        }
        for (Object o : mc.getNetHandler().getPlayerInfoMap()) {
            NetworkPlayerInfo playerInfo = (NetworkPlayerInfo) o;
            if (playerInfo.getGameProfile().getName().equalsIgnoreCase(player.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean raytraceCheck(EntityLivingBase entity) {
        EntitySnowball entitySnowball = new EntitySnowball(mc.theWorld);
        entitySnowball.posX = entity.posX;
        entitySnowball.posY = entity.posY + entity.getEyeHeight() / 2;
        entitySnowball.posZ = entity.posZ;
        return getPlayer().canEntityBeSeen(entitySnowball);
    }

    public float wrapAngleToSpecified_float(float value, float maxangle) {
        value = value % 360.0F;
        if (value >= maxangle) {
            value -= 360.0F;
        }
        if (value < -maxangle) {
            value += 360.0F;
        }
        return value;
    }

    private boolean isPosSolid(BlockPos pos) {
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        return (block.getMaterial().isSolid() || !block.isTranslucent() || block.isSolidFullCube() || block instanceof BlockLadder || block instanceof BlockCarpet || block instanceof BlockSnow || block instanceof BlockSkull) && !block.getMaterial().isLiquid() && !(block instanceof BlockContainer);
    }

    public float[] Aim(Entity ent, PlayerUpdateEvent event, boolean BBB) {
        double x = ent.posX - Minecraft.getMinecraft().thePlayer.posX, y = ent.posY + (BBB ? .1 : ent.getEyeHeight() / 2) - event.getPosY() - (BBB ? 0 : 1.2), z = ent.posZ - Minecraft.getMinecraft().thePlayer.posZ;


        return new float[]{MathHelper.wrapAngleTo180_float((float) (Math.atan2(z, x) * 180 / Math.PI) - 90), (float) -(Math.atan2(y, MathHelper.sqrt_double(x * x + z * z)) * 180 / Math.PI)};
    }

    public static void attack(PlayerUpdateEvent event) {
        EntityLivingBase target = targetList.get(targetIndex);
        attackexecute(target);

        aps += apsDecrease ? -Mafs.getRandomInRange(15, 75) : Mafs.getRandomInRange(15, 75);
        attackSpeed += 1;
        if (attackSpeed >= 4) {
            attackSpeed = 0;
        }
        blockDelay = 5;
    }

    public static void attackexecute(EntityLivingBase target) {
        if (getPlayer().getDistanceToEntity(targetList.get(targetIndex)) <= (hvh.getValue() ? 4.5 : prop_maxDistance.getValue())) {
            getPlayer().swingItem();
            UPlayer.sendPackets(new C02PacketUseEntity(target, mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.toLowerCase().contains("cubecraft") && attackSpeed >= 5 || target instanceof EntityVillager ? Action.INTERACT : Action.ATTACK));
            target.attacks += 1;
        } else {
            if (target.attacks <= 0) {
                target.attacks = 0;

            } else {
                target.attacks -= 1;
            }
            attackSpeed = 0;
        }
    }

    public boolean isValidTarget(EntityLivingBase entity) {
        if (teams.getValue() && entity.getDisplayName().getUnformattedText().contains("\247a") && !FriendManager.friends.containsKey(entity)) {
            return false;
        }
        if (ignoredEntities.contains(entity) && !whitelistedEntity.contains(entity)) {
            return false;
        }

        if (FriendManager.friends.containsKey(entity.getName())) {
            return false;
        }
        
        if (mc.getCurrentServerData().serverIP.contains("mineplex")) {
        	if (!Double.isNaN(entity.getHealth())) {
        		return false;
        	}
        }

        if (entity != getPlayer() && (entity instanceof EntityPlayer && targetEntities.getValue().get("Players"))
                || (entity instanceof EntityMob && targetEntities.getValue().get("Monsters"))
                || (entity instanceof EntitySlime && targetEntities.getValue().get("Monsters"))
                || (entity instanceof EntityAnimal && targetEntities.getValue().get("Animals"))
                || (entity instanceof EntityPig && targetEntities.getValue().get("Animals"))
                || (entity instanceof EntityVillager && targetEntities.getValue().get("Villagers"))
                || (entity instanceof EntityGolem && targetEntities.getValue().get("Golems"))) {
            if ((!prop_maxDistance.checkDependency() || (getPlayer().getDistanceToEntity(entity) <= (prop_maxDistance.getValue()))
                    //&& (!entity.isDead)
                    //&& ((entity).getHealth() > 0)
                    && (!middleClickIgnore.getValue() || !mcf.contains(entity)))) {
                return true;
            }

        }
        return false;
    }


    private static boolean canPassThrow(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock();
        return block.getMaterial() == Material.air || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine || block == Blocks.ladder || block == Blocks.water || block == Blocks.flowing_water || block == Blocks.wall_sign || block == Blocks.standing_sign;
    }


    public void updateTargetList() {
        targetList.clear();

        getWorld().getLoadedEntityList().forEach(entity -> {
            if (entity instanceof EntityLivingBase) {
                if (isValidTarget((EntityLivingBase) entity)) {
                    targetList.add((EntityLivingBase) entity);
                } else {
                    targetList.remove((EntityLivingBase) entity);
                }

            }
        });

        if (targetList.size() > 1) {
            if (targetPriority.getValue().get("Lowest Health"))
                targetList.sort(Comparator.comparingDouble(EntityLivingBase::getHealth).reversed());
            else if (targetPriority.getValue().get("Least Armor"))
                targetList.sort(Comparator.comparingInt(EntityLivingBase::getTotalArmorValue));
            else if (targetPriority.getValue().get("Closest"))
                targetList.sort(Comparator.comparingDouble(UPlayer::getDistanceToEntity));

            if (prioritisePlayers.checkDependency() && prioritisePlayers.getValue()) {
                targetList.sort((e1, e2) -> Boolean.compare(e2 instanceof EntityPlayer, e1 instanceof EntityPlayer));
            }
        }
    }

    /*Things for autoblock*/
    public boolean holdingSword() {
        if (getPlayer().getCurrentEquippedItem() != null && getPlayer().inventory.getCurrentItem().getItem() instanceof ItemSword) {
            return true;
        }
        return false;
    }

    public void attemptStartAutoblock_Watchdog() {
        if (autoBlock.getValue()) {
            if (!targetList.isEmpty() && targetIndex <= targetList.size() - 1 && UPlayer.getDistanceToEntity(targetList.get(targetIndex)) <= prop_maxDistance.getValue() + (hvh.getValue() ? 2 : 0) && holdingSword() && !isBlocking) {
                getPlayer().sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-.8, -.8, -.8), -1, getPlayer().getHeldItem(), 0, 0, 0));
                isBlocking = true;
            }
        }
    }


    @Collect
    public void onPlayerJump(PlayerJumpEvent event) {

        if (Helium.instance.cheatManager.isCheatEnabled("Criticals") && groundTicks != 0 && getPlayer().isMoving()) {
            event.setCancelled(true);
            getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(getPlayer().posX, mc.thePlayer.posY, mc.thePlayer.posZ, yaw, pitch, true));
            mc.thePlayer.motionY = .42f;
            groundTicks = 0;
        } else {
            event.setCancelled(false);
        }
    }

    public void attemptStopAutoblock_Watchdog() {
        if (holdingSword() && autoBlock.getValue()) {
            if (isBlocking) {
                getPlayer().sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-.8, -.8, -.8), EnumFacing.DOWN));
                isBlocking = false;
            }
        }
    }

    public void attemptStopAutoblockNoSet_Watchdog() {
        if (autoBlock.getValue()) {
            if (isBlocking) {
                //getPlayer().addChatMessage(new ChatComponentText("UnBlocc"));
                getPlayer().sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-.8, -.8, -.8), EnumFacing.DOWN));
                isBlocking = false;
            }
        }
    }

    public boolean shouldDoAutoblockAnim() {
        return isBlocking;
    }

    public static EntityLivingBase getCurrentTarget() {
        if (!targetList.isEmpty() && targetIndex != -1) {
            return targetList.get(targetIndex);
        } else {
            return null;
        }
    }

    public boolean isBlocking() {
        return isBlocking;
    }

    public void setBlocking(boolean blocking) {
        isBlocking = blocking;
    }

    boolean didcrit;

    @Collect
    public void onMouseClick(MouseClickEvent mouseClickEvent) {
        if (mouseClickEvent.getMouseButton() == 2) {
            if (getMc().objectMouseOver != null && getMc().objectMouseOver.entityHit != null && getMc().objectMouseOver.entityHit instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) getMc().objectMouseOver.entityHit;
                if (!mcf.contains(entity)) {
                    mcf.add(entity);
                } else {
                    mcf.remove(entity);
                }
            }
        }
    }
    
    @Collect
    public void onRender(RenderOverlayEvent e) {
        if (Hud.targethud.getValue()) {
            if (Minecraft.getMinecraft().thePlayer != null && this.getCurrentTarget() instanceof EntityPlayer) {
                final NetworkPlayerInfo networkPlayerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getCurrentTarget().getUniqueID());
                final String ping = "Ping: " + (Objects.isNull(networkPlayerInfo) ? "0ms" : (networkPlayerInfo.getResponseTime() + "ms"));
                final String playerName = "" + StringUtils.stripControlCodes(this.getCurrentTarget().getName());
                Gui.drawRect(455, 340, 280, 270, RenderUtil.withTransparency(new Color(255, 255, 255).getRGB(), (float) 0.5)); // get white rgb and set it as the bg.
                //Draw.drawImg(new ResourceLocation("client/steve2.png"), 285.0, 275.0, 55.0, 60.0);
                GuiInventory.drawEntityOnScreen(305, (int) 337, 33, getCurrentTarget().rotationYaw, getCurrentTarget().rotationPitch, getCurrentTarget());
                Fonts.bf18.drawString(playerName, 345, 280, 1);
                drawHealthBar(getCurrentTarget());
                Fonts.bf18.drawString("" + getCurrentTarget().getHealth(), 385, 297, 1);
                Fonts.bf18.drawString(ping, 345, 320, 0);
                //Fonts.f26.drawStringWithShadow(playerName, this.x + 46.5, this.y + 204.0, -1);
                //Fonts.f26.drawStringWithShadow("§fDistance§7: §7" + MathUtils.round(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(this.getCurrentTarget()), 2), this.x + 46.5, this.y + 212.0, -1);
                //Fonts.f26.drawStringWithShadow(ping, this.x + 46.5, this.y + 228.0, new Color(6118236).getRGB());
                //Fonts.f26.drawStringWithShadow("§fHealth§7: §7" + MathUtils.round(this.getCurrentTarget().getHealth() / 2.0f, 2), this.x + 46.5, this.y + 220.0, this.getHealthColor(this.getCurrentTarget()));
                //this.drawFace(this.x + 0.5, this.y + 0.5, 8.0f, 8.0f, 8, 8, 44, 44, 64.0f, 64.0f, (AbstractClientPlayer)killAura.target);
                //Draw.drawBorderedRect(this.x + 46.0, this.y + this.height - 10.0 + 200, 92.0, 8.0, 0.5, new Color(0).getRGB(), new Color(35, 35, 35).getRGB());
                final double inc = 91.0f / this.getCurrentTarget().getMaxHealth();
                final double end = inc * ((this.getCurrentTarget().getHealth() > this.getCurrentTarget().getMaxHealth()) ? this.getCurrentTarget().getMaxHealth() : this.getCurrentTarget().getHealth());
                //RenderUtil.drawRect(this.x + 46.5, this.y + this.height - 209.5, end, 7.0, this.getHealthColor(killAura.target));
            }
        }
    }
    
    private void drawHealthBar(final EntityLivingBase player) {
        /*/  Unepic  /*/
        if (player.getHealth() > 30) {
    		Gui.drawRect(445, 310, 345, 292, new Color(33, 235, 53).getRGB());
    	}
        else if (player.getHealth() > 19) {
    		Gui.drawRect(445, 310, 345, 292, new Color(33, 235, 53).getRGB());
    	}
    	else if (player.getHealth() > 15) {
    		Gui.drawRect(440, 310, 345, 292, new Color(15, 15, 15).getRGB());
    		Gui.drawRect(410, 310, 345, 292, new Color(158, 250, 0).getRGB());
    	}
    	else if (player.getHealth() > 10) {
    		Gui.drawRect(445, 310, 345, 292, new Color(15, 15, 15).getRGB());
    		Gui.drawRect(405, 310, 345, 292, new Color(228, 235, 33).getRGB());
    	}
    	else if (player.getHealth() > 5) {
    		Gui.drawRect(445, 310, 345, 292, new Color(15, 15, 15).getRGB());
    		Gui.drawRect(375, 310, 345, 292, new Color(250, 200, 0).getRGB());
    	}
    	else if (player.getHealth() > 4) {
    		Gui.drawRect(445, 310, 345, 292, new Color(15, 15, 15).getRGB());
    		Gui.drawRect(370, 310, 345, 292, new Color(250, 200, 0).getRGB());
    	}
    	else if (player.getHealth() > 3) {
    		Gui.drawRect(445, 310, 345, 292, new Color(15, 15, 15).getRGB());
    		Gui.drawRect(360, 310, 345, 292, new Color(250, 75, 0).getRGB());
    	}
    	else if (player.getHealth() > 2) {
    		Gui.drawRect(445, 310, 345, 292, new Color(15, 15, 15).getRGB());
    		Gui.drawRect(350, 310, 345, 292, new Color(250, 0, 0).getRGB());
    	}
    	else if (player.getHealth() > 1) {
    		Gui.drawRect(445, 310, 345, 292, new Color(15, 15, 15).getRGB());
    		Gui.drawRect(340, 310, 345, 292, new Color(250, 0, 0).getRGB());
    	}
    	else if (player.getHealth() > 0) {
    		Gui.drawRect(445, 310, 345, 292, new Color(15, 15, 15).getRGB());
    		Gui.drawRect(400, 310, 345, 292, new Color(0, 0, 0).getRGB());
    	}
    	
    }
    
    
    private int getHealthColor(final EntityLivingBase player) {
        final float f = player.getHealth();
        final float f2 = player.getMaxHealth();
        final float f3 = Math.max(0.0f, Math.min(f, f2) / f2);
        return Color.HSBtoRGB(f3 / 3.0f, 1.0f, 0.75f) | 0xFF000000;
    }



    @Collect
    public void onProcessPacket(ProcessPacketEvent processPacketEvent) {
        if (processPacketEvent.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) processPacketEvent.getPacket();
            if (packet.getYaw() == 0.0 && packet.getPitch() == 0.0 && packet.getX() == 0.0 && packet.getZ() == 0.0 && packet.getY() == 0.0) {
                packet.setYaw(getPlayer().rotationYaw);
                packet.setPitch(getPlayer().rotationPitch);
            }
        }
    }

    public void setDelay(int delay) {//set the delay to actually use aura
        auraDelay = delay;
    }

}