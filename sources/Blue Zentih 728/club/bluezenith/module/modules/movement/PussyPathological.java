package club.bluezenith.module.modules.movement;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.exploit.Disabler;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.util.client.ClientUtils;
import club.bluezenith.util.math.Vec2d;
import club.bluezenith.util.player.MovementUtil;
import club.bluezenith.util.player.PacketUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import static club.bluezenith.BlueZenith.getBlueZenith;

public class PussyPathological extends Module {

    public ModeValue mode = new ModeValue("Mode", "Hypixel","Hypixel").setIndex(1);
    public final BooleanValue spoofY = new BooleanValue("Spoof Y", false).setIndex(2);
    public final ModeValue damage = new ModeValue("Damage", "Bow", "Bow", "Self", "Both").setIndex(3);
    public final BooleanValue awaitDamage = new BooleanValue("Await damage", true)
            .setIndex(4)
            .showIf(() -> damage.is("Self") || damage.is("Both"));

    public final BooleanValue safeSelfDamage = new BooleanValue("Safe damage", true)
            .setIndex(5)
            .showIf(awaitDamage::isVisible);

    BlockPos lastDist;
    public int damagedticks = 0;
    public int deaccelticks = 0;
    public boolean damaged = false;
    public boolean bowdmg = false;
    public int enabledTicks = 0;
    public double startY = 0;
    int slowdown = 1;
    double speed = 0;
    double motionY = 0;
    Vec2d lastpos;

    public PussyPathological() {
        super("LongJump", ModuleCategory.MOVEMENT);
    }

    @Listener
    public void onUpdate(UpdatePlayerEvent e) {
        if (mode.is("Hypixel") && e.isPre()) {
            if ((mc.thePlayer.hurtTime == 9 || !awaitDamage.getIfVisible()) && mc.thePlayer.onGround && mc.thePlayer.jumpTicks == 0 && !damaged) {
                startY = player.posY;
                final boolean selfDamage = !bowdmg,
                              speed2 = player.isPotionActive(Potion.moveSpeed) && player.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 1;

                player.motionY = selfDamage ? speed2 ? 0.75 : 0.64 : speed2 ? 0.8 : 0.65; //64 without 75 with speed 2
                mc.thePlayer.jumpTicks = 1;
                damaged = true;
                if (speed2) {
                    speed = selfDamage ? 0.5 : 0.8;
                    slowdown = selfDamage ? 55 : 41;
                } else {
                    speed = selfDamage ? 0.4 : 0.6;
                    slowdown = selfDamage ? 70 : 45;
                }

            }

            if (!damaged && bowdmg) {
                bowdamag(e);
            }
            motionY = player.motionY;
        }
    }

    @Listener
    public void onMove(MoveEvent e) {
        if (e.isPost()) return;

        if (mode.is("Hypixel")) {
            if (!mc.thePlayer.onGround && damaged){

                speed -= speed / slowdown;

                final double additionalY = 0.02;//0.0285;

                MovementUtil.setSpeed((float) speed, e);
                if (player.fallDistance == 0) {
                    player.motionY = motionY += additionalY;
                } else {
                    if (player.fallDistance > 4) {
                        player.motionY = motionY += additionalY;
                    } else {
                        player.motionY = motionY += additionalY;
                    }
                }
            }
            if (!damaged) {
                e.cancel();
            }
            if (damaged) {
                if (mc.thePlayer.onGround && damagedticks > 0) {
                    e.cancel();
                    this.setState(false);
                    ClientUtils.fancyMessage("distance: " + lastpos.distance(player.posX, player.posZ));
                }
                damagedticks++;
            }
        }
    }

    @Override
    public void onEnable() {
        bowdmg = false;

        int bow = getBowSlot();
        if (bow == -1 || damage.is("Self")) {
            selfdamag();
        }
        else {
            bowdmg = true;
        }
        if(mc.thePlayer != null)
            lastpos = new Vec2d(player.posX, player.posZ);
        damaged = false;
        damagedticks = 0;
        deaccelticks = 0;
        enabledTicks = 0;
    }

    void selfdamag() {
        if (damage.is("Bow")) {
            getBlueZenith().getNotificationPublisher().postError("LongJump", "Couldn't find a bow in your hotbar.", 2000);
            this.setState(false);
            return;
        }

        if(getCastedModule(Disabler.class).getBalance() < 99 && safeSelfDamage.get()) {
            getBlueZenith().getNotificationPublisher().postError("LongJump", "Not enough balance to damage yourself.", 2500);
            this.setState(false);
            return;
        }

        for (int i = 0; i <= 48; i++) {
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(
                    mc.thePlayer.posX, mc.thePlayer.posY + 0.0618865, mc.thePlayer.posZ, false));
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(
                    mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-12, mc.thePlayer.posZ, false));
            getCastedModule(Disabler.class).decreaseBalance(2);
        }
        PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
        getCastedModule(Disabler.class).decreaseBalance(1);
    }

    void bowdamag(UpdatePlayerEvent e) {
        boolean hasArrow = false;
        for (int z = 0; z < 36; z++) {
            ItemStack i = mc.thePlayer.inventory.mainInventory[z];
            if(i != null && i.getItem() != null && i.getItem() == Items.arrow){
                hasArrow = true;
            }
        }
        if (!hasArrow) {
            if(this.damage.is("Both")) {
                selfdamag();
            } else {
                getBlueZenith().getNotificationPublisher().postError("LongJump", "Couldn't find arrows in your inventory.", 2000);
                this.setState(false);
            }
            return;
        }
        int bow = getBowSlot();
        if(bow == -1) {
            //setState(false);
            return;
        }
        boolean shouldSwitch = true;
        if(bow == mc.thePlayer.inventory.currentItem) shouldSwitch = false;
        if (player.hurtTime == 9) {
            //setState(false);
            return;
        }

        if (enabledTicks >= 0 && enabledTicks <= 3) {
            e.pitch = -90;
        }
        if (enabledTicks == 0) {
            if (shouldSwitch)
                PacketUtil.send(new C09PacketHeldItemChange(bow));
            PacketUtil.send(new C08PacketPlayerBlockPlacement(player.inventory.getStackInSlot(bow)));
        }
        else if (enabledTicks == 4 * mc.timer.timerSpeed) {
            PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            if (shouldSwitch)
                PacketUtil.send(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
        }
        enabledTicks++;
    }

    private int getBowSlot() {
        for (int z = 0; z < 9; z++) {
            ItemStack i = mc.thePlayer.inventory.mainInventory[z];
            if(i != null && i.getItem() != null && i.getItem() instanceof ItemBow){
                return z;
            }
        }
        return -1;
    }

    @Listener
    public void onLagback(PacketEvent event) {
        if(!mc.isSingleplayer()) {
            if (event.packet instanceof S08PacketPlayerPosLook) {
                this.setState(false);
                getBlueZenith().getNotificationPublisher().postWarning("LongJump", "Disabled due to a lagback", 2000);
            }
        }
    }
}
