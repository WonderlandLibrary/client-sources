/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Combat.EntityUtil;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.Movement.MovementHelper;
import ru.govno.client.utils.Wrapper;

public class LongJump
extends Module {
    public static boolean isFallDamage;
    private int ticks;
    private double packetMotionY;
    private float speed;
    public static boolean doSpeed;
    public static boolean doBow;
    public static boolean stopBow;
    public static Item oldSlot;
    private final TimerHelper timerHelper = new TimerHelper();
    public static LongJump get;
    public Settings Type = new Settings("Type", "LongJump", (Module)this, new String[]{"LongJump", "BowBoost", "Solid", "DamageFly", "InstantLong", "FlagBoost"});
    public Settings AutoBow;

    public LongJump() {
        super("LongJump", 0, Module.Category.MOVEMENT);
        this.settings.add(this.Type);
        this.AutoBow = new Settings("AutoBow", true, (Module)this, () -> this.Type.currentMode.equalsIgnoreCase("DamageFly"));
        this.settings.add(this.AutoBow);
        get = this;
    }

    void flagHop() {
        Minecraft.player.motionY = 0.4229;
        MoveMeHelp.setSpeed(1.953);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        Packet packet = event.getPacket();
        if (packet instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook look = (SPacketPlayerPosLook)packet;
            if (this.Type.currentMode.equalsIgnoreCase("FlagBoost")) {
                Minecraft.player.setPosition(look.getX(), look.getY(), look.getZ());
                Minecraft.player.connection.sendPacket(new CPacketConfirmTeleport(look.getTeleportId()));
                this.flagHop();
                event.setCancelled(true);
            }
        }
        if (!isFallDamage) {
            SPacketEntityStatus sPacketEntityStatus;
            if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event.getPacket()).getEntityID() == Minecraft.player.getEntityId()) {
                this.packetMotionY = (double)((SPacketEntityVelocity)event.getPacket()).motionY / 8000.0;
            }
            if ((packet = event.getPacket()) instanceof SPacketEntityStatus && (sPacketEntityStatus = (SPacketEntityStatus)packet).getOpCode() == 2 && sPacketEntityStatus.getEntity(LongJump.mc.world) == Minecraft.player) {
                doSpeed = true;
            }
        } else {
            EntityLivingBase.isMatrixDamaged = false;
            doSpeed = false;
            isFallDamage = false;
            stopBow = true;
        }
    }

    @Override
    public void onUpdate() {
        float dir2;
        float dir1;
        if (this.Type.currentMode.equalsIgnoreCase("FlagBoost")) {
            if (Minecraft.player.motionY != -0.0784000015258789) {
                this.timerHelper.reset();
            }
            if (!MoveMeHelp.isMoving()) {
                this.timerHelper.setTime(this.timerHelper.getCurrentMS() + 50L);
            }
            if (this.timerHelper.hasReached(100.0)) {
                this.flagHop();
                Entity.motiony = 1.0;
            }
        }
        if (this.Type.currentMode.equalsIgnoreCase("InstantLong") && Minecraft.player.hurtTime == 7) {
            MoveMeHelp.setCuttingSpeed(6.603774070739746);
            Minecraft.player.motionY = 0.42;
        }
        if (this.Type.currentMode.equalsIgnoreCase("BowBoost")) {
            if (Minecraft.player.onGround && doSpeed) {
                dir1 = (float)(-Math.sin(MovementHelper.getDirection())) * (float)(LongJump.mc.gameSettings.keyBindBack.isKeyDown() ? -1 : 1);
                dir2 = (float)Math.cos(MovementHelper.getDirection()) * (float)(LongJump.mc.gameSettings.keyBindBack.isKeyDown() ? -1 : 1);
                if (MovementHelper.isMoving() || LongJump.mc.gameSettings.keyBindForward.isKeyDown() || LongJump.mc.gameSettings.keyBindBack.isKeyDown()) {
                    if (MoveMeHelp.getSpeed() < 0.08) {
                        MoveMeHelp.setSpeed(0.42);
                    } else {
                        Minecraft.player.addVelocity((double)dir1 * 9.8 / 25.0, 0.0, (double)dir2 * 9.8 / 25.0);
                        MoveMeHelp.setSpeed(MoveMeHelp.getSpeed());
                    }
                } else if (Minecraft.player.isInWater()) {
                    Minecraft.player.addVelocity((double)dir1 * 8.5 / 25.0, 0.0, (double)dir2 * 9.5 / 25.0);
                    MoveMeHelp.setSpeed(MoveMeHelp.getSpeed());
                } else if (!Minecraft.player.onGround) {
                    if (MoveMeHelp.getSpeed() < 0.22) {
                        MoveMeHelp.setSpeed(0.22);
                    } else {
                        MoveMeHelp.setSpeed(MoveMeHelp.getSpeed() * (Minecraft.player.isMoving() ? 1.0082 : 1.0088));
                    }
                }
                if (LongJump.mc.gameSettings.keyBindJump.isKeyDown() && MoveMeHelp.getSpeed() > 0.7 && Minecraft.player.fallDistance == 0.0f) {
                    MoveMeHelp.setSpeed(0.7);
                }
            } else {
                MoveMeHelp.setCuttingSpeed(0.0);
            }
        }
        if (this.Type.currentMode.equalsIgnoreCase("LongJump")) {
            if (EntityLivingBase.isMatrixDamaged) {
                Minecraft.player.speedInAir = 0.3f;
            } else if (Minecraft.player.speedInAir == 0.3f) {
                Minecraft.player.speedInAir = 0.02f;
            }
        }
        if (this.Type.currentMode.equalsIgnoreCase("Solid")) {
            this.ticks = Minecraft.player.onGround ? ++this.ticks : 0;
            if (EntityLivingBase.isMatrixDamaged) {
                Minecraft.player.stepHeight = 0.0f;
                if (this.ticks > 1 && MoveMeHelp.getSpeed() < 1.2 && !LongJump.mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.getEntityBoundingBox().offset(0.0, Minecraft.player.motionY, 0.0)).isEmpty()) {
                    dir1 = (float)(-Math.sin(MovementHelper.getDirection())) * (float)(LongJump.mc.gameSettings.keyBindBack.isKeyDown() ? -1 : 1);
                    dir2 = (float)Math.cos(MovementHelper.getDirection()) * (float)(LongJump.mc.gameSettings.keyBindBack.isKeyDown() ? -1 : 1);
                    if (MovementHelper.isMoving() || LongJump.mc.gameSettings.keyBindForward.isKeyDown() || LongJump.mc.gameSettings.keyBindBack.isKeyDown()) {
                        if (MoveMeHelp.getSpeed() < 0.08) {
                            MoveMeHelp.setSpeed(0.42);
                        } else {
                            Minecraft.player.addVelocity((double)dir1 * 9.8 / 25.0, 0.0, (double)dir2 * 9.8 / 25.0);
                            MoveMeHelp.setSpeed(MoveMeHelp.getSpeed());
                        }
                    } else if (Minecraft.player.isInWater()) {
                        Minecraft.player.addVelocity((double)dir1 * 8.5 / 15.0, 0.0, (double)dir2 * 9.5 / 15.0);
                        MoveMeHelp.setSpeed(MoveMeHelp.getSpeed());
                    } else if (!Minecraft.player.onGround) {
                        if (MoveMeHelp.getSpeed() < 0.22) {
                            MoveMeHelp.setSpeed(0.22);
                        } else {
                            MoveMeHelp.setSpeed(MoveMeHelp.getSpeed() * (Minecraft.player.isMoving() ? 1.0082 : 1.0088));
                        }
                    }
                    if (LongJump.mc.gameSettings.keyBindJump.isKeyDown() && MoveMeHelp.getSpeed() > 0.7 && Minecraft.player.fallDistance == 0.0f) {
                        MoveMeHelp.setSpeed(0.7);
                    }
                } else if (Speed.canMatrixBoost()) {
                    MoveMeHelp.setSpeed(MoveMeHelp.getSpeed() * 2.0);
                }
                if (this.timerHelper.hasReached(1350.0)) {
                    doSpeed = false;
                    Minecraft.player.stepHeight = 0.6f;
                    Minecraft.player.speedInAir = 0.02f;
                    this.timerHelper.reset();
                    LongJump.mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(LongJump.mc.gameSettings.keyBindJump.getKeyCode());
                }
            }
        }
    }

    public int oldSlot() {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = Minecraft.player.inventoryContainer.getSlot(i).getStack();
            if (itemStack.getItem() != oldSlot) continue;
            return i;
        }
        return -1;
    }

    @EventTarget
    public void onPlayerMotionUpdate(EventPlayerMotionUpdate e) {
        int i;
        if (this.Type.currentMode.equalsIgnoreCase("DamageFly") && this.actived) {
            if (this.AutoBow.bValue && !stopBow && !doSpeed) {
                if (!stopBow) {
                    for (i = 0; i < 9; ++i) {
                        if (Minecraft.player.inventory.currentItem != EntityUtil.getBowAtHotbar() && !doBow) {
                            oldSlot = Minecraft.player.inventoryContainer.getSlot(Minecraft.player.inventory.currentItem).getStack().getItem();
                        }
                        if (!(Minecraft.player.inventory.getStackInSlot(i).getItem() instanceof ItemBow) || doSpeed || !doBow) continue;
                        Minecraft.player.inventory.currentItem = EntityUtil.getBowAtHotbar();
                        Minecraft.player.rotationPitchHead = EventPlayerMotionUpdate.pitch = -90.0f;
                    }
                    if (Minecraft.player.inventory.currentItem == EntityUtil.getBowAtHotbar() && !doSpeed && doBow && e.getPitch() == -90.0f) {
                        boolean bl = LongJump.mc.gameSettings.keyBindUseItem.pressed = (double)Minecraft.player.getItemInUseMaxCount() < 2.1;
                        if ((double)Minecraft.player.getItemInUseMaxCount() >= 2.1) {
                            doBow = false;
                        }
                    }
                }
                if (!doBow && oldSlot != null && Minecraft.player.inventory.currentItem != this.oldSlot()) {
                    Minecraft.player.inventory.currentItem = this.oldSlot();
                    oldSlot = null;
                    stopBow = true;
                }
            }
            if (!doSpeed && this.AutoBow.bValue && Minecraft.player.onGround) {
                MoveMeHelp.setSpeed(0.0);
                e.ground = Minecraft.player.onGround;
                Minecraft.player.onGround = false;
                Minecraft.player.jumpMovementFactor = 0.0f;
                doBow = true;
            }
            if (doSpeed && !MoveMeHelp.isBlockAboveHead()) {
                stopBow = false;
                this.ticks = 0;
                if (this.AutoBow.bValue) {
                    doBow = false;
                }
                if (EntityLivingBase.isMatrixDamaged) {
                    if (Minecraft.player.onGround && !Minecraft.player.isJumping()) {
                        Minecraft.player.jump();
                    }
                    if (!doBow) {
                        Minecraft.player.motionY = Minecraft.player.onGround ? 0.42 : this.packetMotionY;
                        Minecraft.player.jumpMovementFactor = 0.415f;
                        MoveMeHelp.setCuttingSpeed(MoveMeHelp.getSpeed() / 1.06);
                        stopBow = false;
                    }
                } else if (doSpeed) {
                    doSpeed = false;
                    if (Minecraft.player.onGround) {
                        doBow = true;
                    }
                }
            }
        }
        if (this.Type.currentMode.equalsIgnoreCase("BowBoost") && this.actived) {
            this.speed = MathUtils.lerp(this.speed, doSpeed ? 0.8f : 0.0f, 0.2f);
            for (i = 0; i < 9; ++i) {
                if (Minecraft.player.inventory.currentItem != EntityUtil.getBowAtHotbar() && !doBow) {
                    oldSlot = Minecraft.player.inventoryContainer.getSlot(Minecraft.player.inventory.currentItem).getStack().getItem();
                }
                if (!(Minecraft.player.inventory.getStackInSlot(i).getItem() instanceof ItemBow) || doSpeed || !doBow) continue;
                Minecraft.player.inventory.currentItem = EntityUtil.getBowAtHotbar();
            }
            if (!doBow && oldSlot != null && Minecraft.player.inventory.currentItem != this.oldSlot()) {
                Minecraft.player.inventory.currentItem = this.oldSlot();
                oldSlot = null;
            }
            if (Minecraft.player.inventory.currentItem == EntityUtil.getBowAtHotbar() && !doSpeed && doBow) {
                boolean bl = LongJump.mc.gameSettings.keyBindUseItem.pressed = Minecraft.player.getItemInUseMaxCount() < 4;
                if ((double)Minecraft.player.getItemInUseMaxCount() > 2.5) {
                    Minecraft.player.rotationPitchHead = EventPlayerMotionUpdate.pitch = Wrapper.getPlayer().isPotionActive(Potion.getPotionById(1)) ? -30.0f : -45.0f;
                }
            }
            if ((double)Minecraft.player.getItemInUseMaxCount() > 3.5) {
                doBow = false;
            }
            if (doBow && Minecraft.player.hurtTime != 0) {
                LongJump.mc.gameSettings.keyBindUseItem.pressed = false;
                doBow = false;
            }
            if (Minecraft.player.hurtTime != 0) {
                doSpeed = true;
                if (Minecraft.player.hurtTime > 7) {
                    this.timerHelper.reset();
                }
            }
            if (doSpeed) {
                MoveMeHelp.setSpeed(doSpeed ? (double)this.speed : 0.0);
            }
            if (this.timerHelper.hasReached(1300.0)) {
                doSpeed = false;
                if (this.timerHelper.hasReached(1460.0) && LongJump.mc.gameSettings.keyBindForward.isKeyDown()) {
                    doBow = true;
                    this.timerHelper.reset();
                }
            }
        }
    }

    @Override
    public String getDisplayName() {
        return this.getDisplayByMode(this.Type.currentMode);
    }

    @Override
    public void onToggled(boolean actived) {
        if (!actived) {
            stopBow = false;
            this.ticks = 0;
            isFallDamage = false;
            LongJump.mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(LongJump.mc.gameSettings.keyBindJump.getKeyCode());
            if (this.Type.currentMode.equalsIgnoreCase("BowBoost")) {
                LongJump.mc.gameSettings.keyBindUseItem.pressed = false;
            }
            oldSlot = null;
            doSpeed = false;
            doBow = false;
            Minecraft.player.stepHeight = 0.6f;
            Minecraft.player.speedInAir = 0.02f;
        }
        super.onToggled(actived);
    }

    static {
        oldSlot = null;
    }
}

