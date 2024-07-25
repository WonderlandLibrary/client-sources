package club.bluezenith.module.modules.movement;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.player.PacketUtil;
import club.bluezenith.util.player.BypassUtil;
import club.bluezenith.util.player.MovementUtil;
import club.bluezenith.util.player.PlayerUtil;
import club.bluezenith.util.player.RotationUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class LongJump extends Module {
    public ModeValue mode = new ModeValue("Mode", "OldVerus","OldVerus", "Test", "le funny", "hypisel", "Vanilla").setIndex(1);
    private FloatValue vStrength = new FloatValue("Strength", 0.5f, 0.01f, 20f, 0.01f).showIf(() -> mode.is("Vanilla")).setIndex(2);
    private FloatValue vVBoost = new FloatValue("Jump height", 0.1f, 0.1f, 5f, 0.1f).showIf(() -> mode.is("Vanilla")).setIndex(3);;
    public ModeValue speedMode = new ModeValue("Speed mode", "boost","boost", "constant").showIf(() -> this.mode.is("hypisel")).setIndex(4);;
    public FloatValue speed = new FloatValue("Speed", 0.255f, 0, 2, 0.01f, true, null).showIf(() -> this.mode.is("hypisel")).setIndex(5);;
    public FloatValue deceleration = new FloatValue("Deceleration", 0.01f, 0, 2, 0.01f, true, null).showIf(() -> this.speedMode.isVisible() && this.speedMode.is("constant")).setIndex(6);;
    public FloatValue motionY = new FloatValue("Motion Y", 0.42f, 0, 2, 0.01f, true, null).showIf(() -> this.mode.is("hypisel")).setIndex(7);;

    public ModeValue bypassMode = new ModeValue("Damage", "Old", "None", "Old", "New").setDefaultVisibility(false)
            .showIf(() -> mode.is("OldVerus") || mode.is("Test")).setIndex(8);;

    public BooleanValue veris = new BooleanValue("veris", false).showIf(() -> mode.is("Vanilla")).setIndex(9);;
    public BooleanValue disableOnGround = new BooleanValue("Disable on ground", false).setIndex(10);

    public LongJump(){
        super("LongJump", ModuleCategory.MOVEMENT);
    }
    public final int[] b = new int[]{0};
    public final int jumps = 4;
    private int c = 0;
    private boolean hurt;
    private long f = System.currentTimeMillis();
    private boolean maccacokkk = false; // thx levzzz
    double lastPlayerX;
    double lastPlayerZ;
    boolean damaged = false;
    int damagedticks = 0;
    int deaccelticks = 0;
    int the = 0;
    boolean jumped, hpxDamaged;
    private final ArrayList<Packet<?>> packets = new ArrayList<Packet<?>>();
    private final MillisTimer tickTimer = new MillisTimer();

    @Override
    public void onEnable() {
        jumped = false;
        hpxDamaged = false;
        the = 0;
        if (mode.is("hypisel")) {
            final InventoryPlayer inventory = mc.thePlayer.inventory;
            final Container invcon = mc.thePlayer.inventoryContainer;

            final List<ItemStack> invStacks = invcon.getInventory();

            boolean hasArrow = false;
            for (ItemStack stack : invStacks) {
                if (stack == null || stack.stackSize == 0) continue;

                final Item item = stack.getItem();

                if (item.equals(Items.arrow)) {
                    hasArrow = true;
                }
            }
        }
        packets.clear();
        damaged = false;
        damagedticks = 0;
        deaccelticks = 0;
        //mc.thePlayer.jump();
        lastPlayerX = player.posX;
        lastPlayerZ = player.posZ;
        hurt = false;
        maccacokkk = false;
        if(this.mc.thePlayer != null && !bypassMode.get().equals("None") && !mode.is("Test")){
            if(bypassMode.get().equals("Old") && mc.thePlayer.onGround){
                PlayerUtil.damageNormal((float) Math.PI);
                b[0] = -1;
            }else{
                b[0] = 0;
            }
            c = 0;
        }
        if(mode.is("Vanilla")) {
            if(veris.get()) {
                PacketUtil.sendSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ, false));
                PacketUtil.sendSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 2, mc.thePlayer.posZ, false));
                PacketUtil.sendSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 3, mc.thePlayer.posZ, false));
                PacketUtil.sendSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.01, mc.thePlayer.posZ, false));
                PacketUtil.sendSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
            }
            mc.thePlayer.jump(mc.thePlayer.onGround ? vVBoost.get() : 0.42F);
            MovementUtil.setSpeed(mc.thePlayer.onGround ? vStrength.get() : vStrength.get()/2);
        }
    }
    @Listener
    public void onMove(MoveEvent e) {
        if (e.isPost()) return;
        if(jumped) {
            if (mc.thePlayer.onGround && disableOnGround.get()) {
                setState(false);
            }
        }
        if(!mc.thePlayer.onGround) jumped = true;
        if (e.isPost()) return;
        if(b[0] <= jumps && bypassMode.get().equals("New") && mode.is("OldVerus")){
            e.x = 0;
            e.z = 0;
        }
        if(mode.is("Test")) {
            //if(!hurt) e.cancel();
        }
        else if (mode.is("hypisel")) {
            if (!mc.thePlayer.onGround && damaged){
                if (damagedticks > 0) {
                    deaccelticks++;
                    MovementUtil.setSpeed(speed.get() - (deaccelticks * 0.0055f));
                }
                else {
                    MovementUtil.setSpeed(speed.get());
                }
            }
            if (!damaged) {
                e.cancel();
            }
            if (damaged) {
                if (the < 50 && mc.thePlayer.onGround && damagedticks > 0) {
                    e.cancel();
                    this.setState(false);
                }
                damagedticks++;
            }
        }
    }
    public boolean canStopSprinting(){
        String l = bypassMode.get();
        return (l.equals("None") && maccacokkk) || (l.equals("Old") && maccacokkk) || (l.equals("New") && b[0] > jumps);
    }
    @Listener
    public void onPlayerUpdate(UpdatePlayerEvent e){
        if (mode.is("le funny") && e.isPre()) {
            if (MovementUtil.areMovementKeysPressed()) {
                double oldMotionX = player.motionX;
                double oldMotionZ = player.motionZ;
                //PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY, player.posZ, player.onGround));
                MovementUtil.setSpeed(0.255f);
                PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(player.posX + player.motionX, player.posY, player.posZ + player.motionZ, player.onGround));
                MovementUtil.setSpeed(0.255f * 2);
                //PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(player.posX + player.motionX, player.posY, player.posZ + player.motionZ, player.onGround));

            } else {
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionZ = 0;
            }
        }
        else if(mode.is("OldVerus")) {
            if (bypassMode.get().equals("New") && !PlayerUtil.damageVerus(e, jumps, b)) {
                return;
            }
            if (mc.thePlayer.hurtTime == 9) {
                c = 0;
                maccacokkk = true;
            }
            if (!maccacokkk || !canStopSprinting()) return;
            if (c <= 6) {
                mc.thePlayer.jump();
                c++;
                if (c == 6) {
                    f = System.currentTimeMillis();
                }
            }
            long boostTime = 700;
            float lol = (boostTime - MathHelper.clamp((System.currentTimeMillis() - f), 0, boostTime)) / boostTime;
            if (lol > 0.1) {
                e.yaw = RotationUtil.getStrafeYaw();
                e.pitch = 0;
                MovementUtil.setSpeed(lol / BypassUtil.bypass_value);
            }
            if (mc.thePlayer.onGround && lol == 0 && c > 3 && maccacokkk) {
                setState(false);
            }
        }
        else if (mode.is("Test")) {
            if(mc.thePlayer.hurtTime > 0) {
                if(!hurt)
                    mc.thePlayer.jump(0.55);
                //else mc.thePlayer.motionY += 0.035;
                    hurt = true;
                MovementUtil.setSpeed((float) Math.max(MovementUtil.getNormalSpeed(), 0.355));
            }
        }
        else if (mode.is("hypisel")) {
            if (mc.thePlayer.hurtTime == 9 && mc.thePlayer.onGround && mc.thePlayer.jumpTicks == 0 && !damaged) {
                mc.thePlayer.jump(motionY.get());
                mc.thePlayer.jumpTicks = 1;
                damaged = true;
                tickTimer.reset();
                //MovementUtil.setSpeed(speed.get());
            }
        }
    }

    @Override
    public void onDisable() {
        MovementUtil.stopMoving();
    }

    @Override
    public String getTag() {
        return mode.get();
    }

    @Listener
    public void onPacket(PacketEvent e) {
        if (mc.thePlayer == null) return;
        if (e.direction == EnumPacketDirection.SERVERBOUND || mc.isSingleplayer()) return;
    }
}
