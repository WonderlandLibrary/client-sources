package club.bluezenith.module.modules.movement;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.*;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.exploit.Disabler;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.ExtendedModeValue;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.util.client.Chat;
import club.bluezenith.util.client.Pair;
import club.bluezenith.util.math.Range;
import club.bluezenith.util.player.MovementUtil;
import club.bluezenith.util.player.PacketUtil;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

import java.awt.*;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.module.value.builders.AbstractBuilder.*;

public class LongJump2 extends Module {
    private final ExtendedModeValue mode = new ExtendedModeValue(this, "Mode",
            new Pair<>("Hypixel", new HypixelMode())).setIndex(1);

    private final BooleanValue sameY = createBoolean("Same Y")
            .index(2)
            .defaultOf(false)
            .build();

    private final ModeValue damage = createMode("Damage")
            .index(3)
            .range("Bow, Self, Both")
            .defaultOf("Bow")
            .build();

    private final ModeValue selfDamage = createMode("Self Damage")
            .index(4)
            .range("New, Old, Edit, None")
            .showIf(() -> damage.is("Self") || damage.is("Both"))
            .build();

    private final IntegerValue newDelay = createInteger("Delay")
            .index(5)
            .range(Range.of(0, 30))
            .increment(1)
            .showIf(() -> selfDamage.is("New"))
            .build();

    private final BooleanValue awaitDamage = createBoolean("Await damage")
            .index(6)
            .defaultOf(true)
            .showIf(() -> selfDamage.isVisible() && selfDamage.is("Old"))
            .build();

    private final BooleanValue safeSelfDamage = createBoolean("Safe damage")
            .index(7)
            .defaultOf(true)
            .showIf(() -> !damage.is("Bow") && !selfDamage.is("Edit") && !selfDamage.is("None"))
            .build();

    private final BooleanValue editWhenUnsafe = createBoolean("Edit when unsafe")
            .index(8)
            .defaultOf(true)
            .showIf(safeSelfDamage::getIfVisible)
            .build();

    private int enabledTicks, damagedTicks, preDamageTicks;

    private boolean hasDamaged, isBowDamage, isDamagingNoPackets;

    private double moveSpeed, motionY, slowdownAmount;

    private float visualBarProgress;

    private Vec3 startPosition;

    public LongJump2() {
        super("LongJump", ModuleCategory.MOVEMENT);
    }

    private static final double[] hypixelJumpValues =  {
            0.4199999868869781,
            0.7531999805212017,
            1.0013359791121474,
            1.1661092609382138,
            1.249187078744681,
            1.249187078744681,
            1.1707870772188023,
            1.0155550727022007,
            0.7850277037892366,
            0.4807108763316923,
            0.104080378093037,
            0.0
    };

    private boolean sentNewDamagePackets, doNewDamage;

    private int jumpValueIndex;

    @Listener
    public void onCameraTranslation(TranslateCameraEvent event) {
        if(sameY.get())
            event.y = startPosition.yCoord;
    }

    @Override
    public void onEnable() {
        if(player != null)
          startPosition = new Vec3(player.posX, player.posY, player.posZ);

        doNewDamage = false;
    }

    @Override
    public String getTag() {
        return this.mode.get().getKey();
    }

    private class HypixelMode implements ExtendedModeValue.Mode {

        @Listener
        public void onRender2D(Render2DEvent event) {
            if(isDamagingNoPackets || doNewDamage) {
                final float targetProgress = doNewDamage ? newDelay.get() : (hypixelJumpValues.length * 3F) - 3F;
                visualBarProgress = RenderUtil.animate((preDamageTicks / targetProgress), visualBarProgress, 0.15F);
                final float barHeight = 5, barWidth = 100;

                final float halfScreenWidth = (float) (event.getWidth() / 2F),
                            halfScreenHeight = (float) (event.getHeight() / 2F);

                final float x1 = halfScreenWidth - barWidth / 2F,
                            y1 = halfScreenHeight - barHeight / 2F,
                            x2 = halfScreenWidth + barWidth / 2F,
                            y2 = halfScreenHeight + barHeight / 2F;

                final float outlineWidth = 1;

                RenderUtil.rect(x1 - outlineWidth, y1 - outlineWidth, x2 + outlineWidth, y2 + outlineWidth, new Color(0, 0, 0, 150));
                RenderUtil.drawGradientRectHorizontal(x1, y1, x1 + (x2 - x1) * visualBarProgress, y2, HUD.module.getColor(1), HUD.module.getColor(5));
            }
        }

        @Listener
        public void onUpdatePlayer(UpdatePlayerEvent event) {
            if (event.isPre()) {

                if(!isBowDamage && doNewDamage && !hasDamaged && !sentNewDamagePackets) {
                    preDamageTicks++;

                    if(preDamageTicks < newDelay.get()) return; //wait 20 ticks

                    sentNewDamagePackets = true;
                    doNewPacketDamage(getCastedModule(Disabler.class));
                }

                if (shouldJump()) {
                    final boolean isSelfDamaging = !isBowDamage,
                            hasSpeedEffect = player.isPotionActive(Potion.moveSpeed) && player.getActivePotionEffect(Potion.moveSpeed).getAmplifier() >= 1;

                    player.motionY = isSelfDamaging ?
                            hasSpeedEffect ? 0.75 : 0.55
                            : hasSpeedEffect ? 0.8 : 0.65;

                    player.jumpTicks = 1;

                    hasDamaged = true;
                    isDamagingNoPackets = doNewDamage = false;

                    if (hasSpeedEffect) {
                        moveSpeed = isSelfDamaging ? 0.5 : 0.8;
                        slowdownAmount = isSelfDamaging ? 55 : 41;
                    } else {
                        moveSpeed = isSelfDamaging ? 0.5 : 0.6;
                        slowdownAmount = isSelfDamaging ? 60 : 45;
                    }
                }

                if (!hasDamaged) {
                    if(isBowDamage) {
                        bowDamage(event);
                    } else if(isDamagingNoPackets && (selfDamage.is("Edit") || editWhenUnsafe.getIfVisible())) {
                        doNewPacketLessDamage(event);
                    }
                }

                motionY = player.motionY;
            }
        }

        private boolean shouldJump() { //the player should jump if
            return (player.hurtTime == 9 || awaitDamage.isVisible() && (!awaitDamage.get() && !isDamagingNoPackets && !selfDamage.is("None")) && !isBowDamage) //the damage has been dealt, or we shouldn't wait for it
                    && player.onGround  //the player is on ground
                    && player.jumpTicks == 0  //hasn't jumped yet
                    && !hasDamaged; //and hasn't damaged yet either
        }

        @Listener
        public void onMove(MoveEvent event) {
            if (event.isPost()) return;

            if (!player.onGround && hasDamaged) {
                moveSpeed -= moveSpeed / slowdownAmount;

                final double additionalY = 0.00;
                MovementUtil.setSpeed(moveSpeed, event);

                player.motionY = motionY += additionalY;

            }

            if (!hasDamaged) {
                event.cancel();
            } else {
                if (player.onGround && damagedTicks > 0) {
                    event.cancel();
                    setState(false);
                    Chat.bz("Distance travelled: " + startPosition.distanceTo(new Vec3(player.posX, player.posY, player.posZ)));
                }
                damagedTicks++;
            }
        }

        @Override
        public void onEnable(Module module) {
            if (player == null) return;
            if (!player.onGround) {
                setState(false);
                getBlueZenith().getNotificationPublisher().postError(
                        displayName,
                        "You must be on ground for " + displayName + " to work.",
                        3000
                );
                return;
            }

            isBowDamage = false;
            final int bowSlot = getBowSlot();

            isBowDamage = bowSlot > -1 && (damage.is("Bow") || damage.is("Both"));

            if (!isBowDamage) {
                if (damage.is("Bow")) {
                    setState(false);
                    getBlueZenith().getNotificationPublisher().postError(displayName,
                            "Could not find a bow in your hotbar.",
                            2500
                    );
                    return;
                }
                selfDamage();
            }

            moveSpeed = 0;
            hasDamaged = sentNewDamagePackets = false;
            damagedTicks = 0;
            preDamageTicks = 0;
            visualBarProgress = 0;
            enabledTicks = 0;
            jumpValueIndex = 0;
        }

        private int getBowSlot() {
            final ItemStack[] hotbar = mc.thePlayer.inventory.mainInventory;

            for (int i = 0; i < hotbar.length; i++) {
                ItemStack itemStack = hotbar[i];
                if (itemStack == null || itemStack.getItem() == null) continue;
                if (!(itemStack.getItem() instanceof ItemBow)) continue;

                return i;
            }

            return -1;
        }

        private void selfDamage() {
            final Disabler disabler = getCastedModule(Disabler.class);

            if(disabler.getBalance() < getLeastRequiredBalanceCount() && safeSelfDamage.getIfVisible()) {
                getBlueZenith().getNotificationPublisher().postWarning(displayName,
                        "Not enough balance to damage yourself." +
                                (editWhenUnsafe.getIfVisible() ? "\nUsing Edit damage instead" : ""),
                        2500
                );

                if(editWhenUnsafe.get()) {
                    isDamagingNoPackets = true;
                    doNewPacketLessDamage(null);
                } else setState(false);
                return;
            }

            switch (selfDamage.get()) {
                case "New": //called in onPlayerUpdate because need to wait a few ticks
                    /*doNewPacketDamage(disabler);*/
                    doNewDamage = true;
                break;

                case "Old":
                case "None":
                    doOldPacketDamage(disabler);
                break;

                case "Edit":
                    doNewPacketLessDamage(null);
                break;
            }
        }

        private void bowDamage(UpdatePlayerEvent event) {
            final boolean hasArrows = hasArrows();

            if(!hasArrows) {
                getBlueZenith().getNotificationPublisher().postError(displayName,
                        "Couldn't find any arrows in your inventory.",
                        3000
                );

                if(damage.is("Both")) {
                     selfDamage();
                } else setState(false);

                return;
            }

            final int bowSlot = getBowSlot();

            if(bowSlot == -1) {
                setState(false);
                return;
            }

            final boolean switchSlot = bowSlot != mc.thePlayer.inventory.currentItem;

            final int ticksToWait = (int) (3 * mc.timer.timerSpeed);

            if (enabledTicks >= 0 && enabledTicks <= ticksToWait) {
                event.pitch = -90;
            }

            if (enabledTicks == 0) {
                if (switchSlot)
                    PacketUtil.send(new C09PacketHeldItemChange(bowSlot));
                PacketUtil.send(new C08PacketPlayerBlockPlacement(player.inventory.getStackInSlot(bowSlot)));
            }
            else if (enabledTicks == ticksToWait) {
                PacketUtil.send(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                if (switchSlot)
                    PacketUtil.send(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            }
            enabledTicks++;
        }

        private void doOldPacketDamage(Disabler disabler) {
            if(selfDamage.is("Old")) {
                for (int i = 0; i <= 48; i++) {
                    PacketUtil.send(new C04PacketPlayerPosition(
                            mc.thePlayer.posX, mc.thePlayer.posY + 0.0618865, mc.thePlayer.posZ, false));
                    PacketUtil.send(new C04PacketPlayerPosition(
                            mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-12, mc.thePlayer.posZ, false));
                    disabler.decreaseBalance(2);
                }
                PacketUtil.send(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                disabler.decreaseBalance(1);
            }
        }

        private void doNewPacketDamage(Disabler disabler) {
            for (int i = 0; i < 3; i++) {
                for (double jumpValue : hypixelJumpValues) {
                    PacketUtil.send(new C04PacketPlayerPosition(player.posX, player.posY + jumpValue, player.posZ, false));
                    disabler.decreaseBalance(1);
                }
            }
            PacketUtil.send(new C04PacketPlayerPosition(player.posX, player.posY, player.posZ, true));
            disabler.decreaseBalance(1);
        }

        private void doNewPacketLessDamage(UpdatePlayerEvent event) {
            if(event == null) {
                isDamagingNoPackets = true;
                return;
            }

            event.y += 0.00000000000001;
            event.y += hypixelJumpValues[jumpValueIndex];
            event.onGround = false;

            jumpValueIndex++;
            preDamageTicks++;

            if(jumpValueIndex == hypixelJumpValues.length)
                jumpValueIndex = 0;

            if(preDamageTicks + 1 >= hypixelJumpValues.length * 3) {
                event.onGround = true;
                isDamagingNoPackets = false;
            }
        }

        private int getLeastRequiredBalanceCount() {
            return selfDamage.is("Old") ? 99 : selfDamage.is("New") ? 36 : Integer.MIN_VALUE;
        }

        private boolean hasArrows() {
            for (int i = 0; i < 36; i++) {
                final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
                if(itemStack == null || itemStack.getItem() == null || itemStack.getItem() != Items.arrow) continue;

                return true;
            }
            return false;
        }
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
