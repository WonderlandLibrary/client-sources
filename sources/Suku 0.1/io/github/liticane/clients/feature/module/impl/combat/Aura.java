package io.github.liticane.clients.feature.module.impl.combat;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.input.MoveInputEvent;
import io.github.liticane.clients.feature.event.impl.motion.LClickEvent;
import io.github.liticane.clients.feature.event.impl.motion.PostMotionEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.event.impl.other.JumpFixEvent;
import io.github.liticane.clients.feature.event.impl.other.PacketEvent;
import io.github.liticane.clients.feature.event.impl.other.StrafeEvent;
import io.github.liticane.clients.feature.event.impl.other.TickEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.feature.property.impl.StringProperty;
import io.github.liticane.clients.util.misc.ChatUtil;
import io.github.liticane.clients.util.misc.Random;
import io.github.liticane.clients.util.misc.TimerUtil;
import io.github.liticane.clients.util.player.MoveUtil;
import io.github.liticane.clients.util.player.RotationUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.List;
//maybe fix the expetions
//todo: optimise the ab code if it works
@Module.Info(name = "Killaura", category = Module.Category.COMBAT)
public class Aura extends Module {
    public NumberProperty range = new NumberProperty("Range", this, 3, 3, 6, 1);
    public NumberProperty minc = new NumberProperty("Min Cps", this, 10, 1, 20, 1);
    public NumberProperty maxc = new NumberProperty("Max Cps", this, 15, 1, 20, 1);
    public NumberProperty targets = new NumberProperty("Targets", this, 5, 1, 10, 1);
    public NumberProperty switchdelay = new NumberProperty("Switch Delay", this, 100, 0, 1500, 100);
    public StringProperty hite = new StringProperty("Attack Event Mode",this,"Pre","Pre","Post","Legit");
    public StringProperty autoblocke = new StringProperty("Auto Block Event",this,"Pre","Pre","Post","Legit");
    public StringProperty autoblockm = new StringProperty("Auto Block Modes",this,"Vanilla","Vanilla","Fake","test","Watchdog");
    public StringProperty attackkm = new StringProperty("Attack Method",this,"Legit","Legit","Packet");
    public StringProperty raycastm = new StringProperty("Raycast Mode",this,"Normal","Normal","Legit");
    public StringProperty auram = new StringProperty("Aura Mode",this,"Single","Single","Switch");
    public StringProperty priority = new StringProperty("Priority",this,"Distance","Distance","Health","Direction");
    public BooleanProperty autoblock = new BooleanProperty("Auto Block",this,false);
    public BooleanProperty movefix = new BooleanProperty("Move Fix",this,false);
    public BooleanProperty raycast = new BooleanProperty("Raycast",this,false);
    private BooleanProperty rr = new BooleanProperty("Rotations",this,true);
    private BooleanProperty players = new BooleanProperty("Players",this,true);
    private BooleanProperty animals = new BooleanProperty("Animals",this,false);
    private BooleanProperty mobs = new BooleanProperty("Mobs",this,true);
    private BooleanProperty invis = new BooleanProperty("Invisible",this,false);
    private final TimerUtil cpsTimer = new TimerUtil();
    public static EntityLivingBase target;
    private float yaw, pitch, lastYaw, lastPitch;
    private double currentAPS = 2;
    private TimerUtil timer = new TimerUtil();
    private int cur = 0;
    static ArrayList<EntityLivingBase> curTargets = new ArrayList<>();
    private boolean blocking = false;
    private boolean blocked = false;
    private boolean blinking = false;
    private boolean block = false;
    private int c02 = 0;
    private int delay = 0;
    private boolean shouldblink = false;
    @Override
    protected void onEnable() {
        shouldblink = false;
        try {
            yaw = mc.player.rotationYaw;
            pitch = mc.player.rotationPitch;

            lastYaw = mc.player.rotationYaw;
            lastPitch = mc.player.rotationPitch;

        } catch (Exception exception) {

        }
        super.onEnable();
    }
    @Override
    protected void onDisable() {
        target = null;
        blocking = false; blocked = false;
        c02 = 0; delay = 0;
        shouldblink = false;
        curTargets.clear();
        mc.settings.keyBindUseItem.pressed = false;
        super.onDisable();
    }

    @SubscribeEvent
    private final EventListener<TickEvent> onTick = e -> {
        List<EntityLivingBase> livingEntities = new ArrayList<>();

        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityLivingBase) {
                livingEntities.add((EntityLivingBase) entity);
            }
        }

        getTargets(livingEntities);
        if (!curTargets.isEmpty()) {
            switchTarget();
            if (cur > curTargets.size() - 1) {
                cur = 0;
            }
            target = curTargets.get(cur);
            calculateRotations();
        }

    };


    @SubscribeEvent
    private final EventListener<LClickEvent> onLclick = e -> {
        /*
         * Attacks
         */
        if(hite.is("Legit")) {
            attack();
        }
        /*
         * Blocks
         */
        if(autoblocke.is("Legit")) {
            ab();
            /*
             * UnBlocks
             */
            unblock();
        }
    };

    @SubscribeEvent
    private final EventListener<PostMotionEvent> PostMotion = e -> {
        /*
         * Attacks
         */
        if(hite.is("Post")) {
            attack();
        }
        /*
         * Blocks
         */
        if(autoblocke.is("Post")) {
            ab();
            /*
             * UnBlocks
             */
            unblock();
        }
    };

    @SubscribeEvent
    private final EventListener<PreMotionEvent> preMotionEventEventListener = e -> {
        setSuffix(auram.getMode());
        /*
         * some seeting visible shit
         */
        ssettings();
        /*
         * Rotates
         */
        rotate(e);
        /*
         * Attacks
         */
        if(hite.is("Pre")) {
            attack();
        }
        /*
         * Blocks
         */
        if(autoblocke.is("Pre")) {
            ab();
            /*
             * UnBlocks
             */
            unblock();
        }
        if(autoblockm.is("test")) {
            hyixkceab(e);
        }
    };

    private void hyixkceab(PreMotionEvent event) {

        if (isHoldingSword() && Aura.target != null && !blinking) {
            shouldblink = true;
            blinking = true;
            blocked = false;
        }
        if (blinking && isHoldingSword() && Aura.target != null && !block) {
            delay = delay + 1;
            if (delay >= 2) {
                mc.player.connection.send(new C09PacketHeldItemChange(mc.player.inventory.currentItem % 8 + 1));
                mc.player.connection.send(new C09PacketHeldItemChange(mc.player.inventory.currentItem));
                blocked = false;
                block = true;
                delay = 0;
            }
        }
        if (isHoldingSword() && blinking && Aura.target != null && block) {
            if (c02 >= 1) {
                shouldblink = false;
                mc.player.connection.send(new C08PacketPlayerBlockPlacement(mc.player.inventory.getItemStack()));
                blocking = true;
                blinking = false;
                block = false;
                blocked = true;
                c02 = 0;
            }
        }
        if (blocking && target == null) {
            if (isHoldingSword()) {
                C07PacketPlayerDigging packet = new C07PacketPlayerDigging(
                        C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK,  // Replace with the desired action
                        new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ),  // Replace with the coordinates of the block
                        EnumFacing.UP  // Replace with the direction the player is facing
                );
                mc.player.connection.send(packet);
            }
            blocking = false;
            blocked = false;
            c02 = 0;
            delay = 0;
            shouldblink = false;
        }
    }
    @SubscribeEvent
    private final EventListener<MoveInputEvent> moveinput = event -> {
        if (mc.world == null || mc.player == null) {
            return;
        }

        if (movefix.isToggled() && target != null) {
           MoveUtil.fixMovement(event, yaw);
        }
    };
    @SubscribeEvent
    private final EventListener<StrafeEvent> strafe = event -> {
        if (mc.world == null || mc.player == null) {
            return;
        }

        if (movefix.isToggled() && target != null) {
            event.setYaw(yaw);
        }
    };
    @SubscribeEvent
    private final EventListener<JumpFixEvent> jump = event -> {
        if (mc.world == null || mc.player == null) {
            return;
        }

        if (movefix.isToggled() && target != null) {
            event.setYaw(yaw);
        }
    };

    private void ssettings() {
        autoblockm.setVisible(() -> autoblock.isToggled());
        raycastm.setVisible(() -> raycast.isToggled());
    }
    private void calculateRotations() {
        lastYaw = yaw;
        lastPitch = pitch;

        float[] rotations = new float[] {0, 0};
        rotations = RotationUtils.getRotations(target, yaw, pitch);
        yaw = rotations[0];
        pitch = rotations[1];


        float[] fixedRotations = RotationUtils.getFixedRotations(
                new float[] { yaw, pitch },
                new float[] { lastYaw, lastPitch }
        );

        yaw = fixedRotations[0];
        pitch = fixedRotations[1];
    }

    private void rotate(PreMotionEvent event) {
        //may cause a crash idk
        if(rr.isToggled() && target != null) {
            event.setYaw(yaw);
            event.setPitch(pitch);
            RotationUtils.setVS(yaw,pitch,lastYaw,3);
        }
    }

    public void getTargets(List<? extends EntityLivingBase> entities) {
        curTargets.clear();
        target = null;
        //prob u dont have to do this
        for (EntityLivingBase entity : entities) {
            if (entity == mc.player)
                continue;
            if (!entity.isEntityAlive())
                continue;
            if (curTargets.size() > (int) targets.getValue())
                continue;

            if (mc.player.getDistanceToEntity(entity) > range.getValue())
                continue;

            try {
                // Check if the entity is a teammate before casting
                if (entity instanceof EntityPlayer && Client.INSTANCE.getModuleManager().get(Teams.class).isToggled() && Client.INSTANCE.getModuleManager().get(Teams.class).isTeammate(entity)) {
                    continue;
                }
            } catch (ClassCastException e) {
                e.printStackTrace();
                continue; // Handle the exception and skip the current entity
            }

            // TODO: add options for targets
            if (entity instanceof EntityMob && mobs.isToggled())
                curTargets.add(entity);

            if (entity instanceof EntityAnimal && animals.isToggled())
                curTargets.add(entity);

            if (entity instanceof EntityPlayer && players.isToggled()) {
                if (entity.isInvisible() && invis.isToggled())
                    curTargets.add(entity);
                else if (!entity.isInvisible())
                    curTargets.add(entity);
            }
        }
    }

    private void switchTarget() {
        if (auram.is("Switch")) {
            if (timer.hasTimeElapsed((int)switchdelay.getValue())) {
                if (cur < curTargets.size() - 1) {
                    cur++;
                } else {
                    cur = 0;
                }
                timer.reset();
            }
        } else if (auram.is("Single")) {
            switch (priority.getMode()) {
                case "Distance":
                    curTargets.sort(((o1,
                                      o2) -> (int) (o2.getDistanceToEntity(mc.player) - o1.getDistanceToEntity(mc.player))));
                    break;
                case "Health":
                    curTargets.sort(((o1, o2) -> (int) (o2.getHealth() - o1.getHealth())));
                    break;
                case "Direction":
                    //curTargets.sort(((o1, o2) -> (int) (getRotationDis(o2) - getRotationDis(o1))));
                    break;
            }
            cur = 0;
        }
    }


    private void attack() {
        // if (Client.INSTANCE.getModuleManager().get(Scaffold.class).isToggled)
        //    return;
        //TODOl:MAKE SOME GOFFYY YAH THINGY
        if (raycast.isToggled() && !RotationUtils.isMouseOver(mc.player.rotationYaw, mc.player.rotationPitch, target, (float)range.getValue())) {
            ChatUtil.display("Cancelled hit");
            return;
        }
        //filter(mc.world.getLoadedEntityList());
        //        switchTarget();
        //        if (curTargets.size() != 0) {
        //            if (cur > curTargets.size() - 1) {
        //                cur = 0;
        //            }
        //            target = curTargets.get(cur);
        //            // float[] rotations = PlayerUtils.getRotations(target);
        //            //rotate(rotations, e);
        //        }
        if (cpsTimer.hasTimeElapsed((1000 / currentAPS)) && target != null) {
            mc.player.swing();
            if (attackkm.is("Packet")) {
                mc.player.connection.send(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            } else {
                mc.controller.attackEntity(mc.player, target);
            }
            currentAPS = Random.nextDouble(
                    minc.getValue(),
                    maxc.getValue()
            );
           // ChatUtil.display(currentAPS);
            cpsTimer.reset();
        }
    }
    private void ab() {
        if(autoblock.isToggled()&& target != null && !autoblockm.is("test")) {
            switch (autoblockm.getMode()) {
                case "Vanilla":
                    if (mc.player.inventory.getCurrentItem() != null)
                        mc.controller.sendUseItem(mc.player, mc.world, mc.player.inventory.getCurrentItem());
                    break;
                case "Watchdog":
                    //2
                    if(mc.player.ticksExisted % MathHelper.getRandomIntegerInRange(new java.util.Random(),2,5) == 0) {
                        KeyBinding.setKeyBindState(mc.settings.keyBindUseItem.getKeyCode(), true);
                      //  ChatUtil.display("Block");
                    }
                    //3
                    if(mc.player.ticksExisted % MathHelper.getRandomIntegerInRange(new java.util.Random(),3,6) == 0) {
                       // ChatUtil.display("unblock");
                      KeyBinding.setKeyBindState(mc.settings.keyBindUseItem.getKeyCode(), false);
                    }
                    break;

            }

        }
    }
    private void unblock() {
        if(target == null && !autoblockm.is("test")) {
            KeyBinding.setKeyBindState(mc.settings.keyBindUseItem.getKeyCode(), false);
        }
    }

    @SubscribeEvent
    private final EventListener<PacketEvent> onpacketo = e -> {
        switch (autoblockm.getMode()) {
            case "test":
                ChatUtil.display(shouldblink);
                //
                if(e.getType().equals(PacketEvent.Type.SENT)) {
                    final Packet<?> packet = e.getPacket();
                    if (e.getPacket() instanceof C03PacketPlayer) {
                        if (shouldblink) {
                            e.setCancelled(true);
                        }
                    }
                    if (isHoldingSword() && Aura.target != null && blocking) {
                        if (e.getPacket() instanceof C08PacketPlayerBlockPlacement && e.getPacket() instanceof C07PacketPlayerDigging) {
                            e.setCancelled(true);
                        }
                    }
                    if (isHoldingSword() && Aura.target != null && blocked) {
                        if (packet instanceof C02PacketUseEntity) {
                            C02PacketUseEntity wrapper = (C02PacketUseEntity) packet;
                            if (wrapper.getAction() == C02PacketUseEntity.Action.ATTACK) {
                                e.setCancelled(true);
                                blocked = false;
                                ChatUtil.display("cacer");
                            }
                        }
                    }
                    if (packet instanceof C02PacketUseEntity && blinking) {
                        C02PacketUseEntity wrapper = (C02PacketUseEntity) packet;
                        if (wrapper.getAction() == C02PacketUseEntity.Action.ATTACK) {
                            c02 = c02 + 1;
                        }
                    }
                }
                break;
        }
    };
    private boolean isHoldingSword () {
        return mc.player != null && this.mc.player.getCurrentEquippedItem() != null && this.mc.player.getCurrentEquippedItem().getItem() instanceof ItemSword;
    }
}
