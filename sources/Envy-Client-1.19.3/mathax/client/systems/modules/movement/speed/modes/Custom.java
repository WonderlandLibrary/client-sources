package mathax.client.systems.modules.movement.speed.modes;

import mathax.client.events.entity.player.PlayerMoveEvent;
import mathax.client.mixininterface.IVec3d;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.movement.AutoJump;
import mathax.client.systems.modules.movement.speed.Speed;
import mathax.client.systems.modules.movement.speed.SpeedMode;
import mathax.client.systems.modules.movement.speed.SpeedModes;
import mathax.client.systems.modules.world.Timer;
import mathax.client.utils.EnvyUtils;
import mathax.client.utils.algorithms.extra.MovementUtils;
import mathax.client.utils.player.PlayerUtils;
import mathax.client.utils.world.TickRate;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;


public class Custom extends SpeedMode {
    public Custom() {
        super(SpeedModes.Custom);
    }

    int vulcanticks;

    int doublehopticks;

    @Override
    public boolean onTick() {
        if (settings.autojump.get() && mc.player.isOnGround()) {
            if (!settings.MoveOnly.get()) {
                mc.player.jump();
            }
            if (settings.MoveOnly.get() && PlayerUtils.isMoving()) {
                mc.player.jump();
            }
        }

        if (settings.viperhigh.get()) {
            if (PlayerUtils.isMoving()) {
                if (mc.player.isOnGround()) {
                    mc.player.setVelocity(mc.player.getVelocity().getX(), 0.7, mc.player.getVelocity().getZ());
                }
            }
        }

/*        if (settings.doublehop.get()) {
            doublehopticks++;
            if (PlayerUtils.isMoving()) {
                if (mc.player.isOnGround()) {
                    if (doublehopticks == 0) {
                        mc.player.jump();
                    }
                }
            }
            if (PlayerUtils.isMoving()) {
                if (!mc.player.isOnGround()) {
                    if (doublehopticks == 3) {
                        mc.player.jump();
                        doublehopticks = 0;
                    }
                }
            }
        }*/

        if (mc.player.isOnGround() && PlayerUtils.isMoving() && settings.ymotiontoggle.get()) {
            mc.player.setVelocity(mc.player.getVelocity().getX(), settings.ymotion.get(), mc.player.getVelocity().getZ());
        }

        if (settings.autoSprint.get()) {
            mc.player.setSprinting(true);
        }
        if (!mc.player.isOnGround()) {
            mc.player.setVelocity(mc.player.getVelocity().x * settings.airstrafe.get(), mc.player.getVelocity().y, mc.player.getVelocity().z * settings.airstrafe.get());
        }
        if (mc.player.isOnGround()) {
            mc.player.setVelocity(mc.player.getVelocity().x * settings.groundStrafe.get(), mc.player.getVelocity().y, mc.player.getVelocity().z * settings.groundStrafe.get());
        }
        if (mc.player.isOnFire()) {
            mc.player.setVelocity(mc.player.getVelocity().x * settings.onfire.get(), mc.player.getVelocity().y, mc.player.getVelocity().z * settings.onfire.get());
        }
        if (!mc.player.isOnGround()) {
            mc.player.setVelocity(mc.player.getVelocity().x, mc.player.getVelocity().y / settings.floating.get(), mc.player.getVelocity().z);
        }
        //This should be fixed now
        if (settings.groundspoof.get()) {
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), true));
        }
        if (mc.player.getOffHandStack().getItem() == Items.BOW) {
            mc.player.setVelocity(mc.player.getVelocity().x * settings.bowspeed.get(), mc.player.getVelocity().y, mc.player.getVelocity().z * settings.bowspeed.get());
        }
        if (mc.player.getMainHandStack().getItem() instanceof SwordItem) {
            mc.player.setVelocity(mc.player.getVelocity().x * settings.swordspeed.get(), mc.player.getVelocity().y, mc.player.getVelocity().z * settings.swordspeed.get());
        }
        if (mc.player.getHealth() < settings.lowhealthdisable.get()) {
            (Modules.get().get(Speed.class)).forceToggle(false);
        }
        //This needs a slider
        if (settings.Fall.get()) {
            EnvyUtils.fall();
        }
        if (TickRate.INSTANCE.getTickRate() < settings.TPSDisable.get()) {
            (Modules.get().get(Speed.class)).forceToggle(false);
        }

        //Adds Vulcan Bypass
        if (settings.vulcan.get()) {
            mc.options.jumpKey.setPressed(false);
            if (mc.player.isOnGround() && PlayerUtils.isMoving()) {
                vulcanticks = 0;
                mc.player.jump();

                MovementUtils.Vulcanstrafe();
                if (MovementUtils.getSpeed() < 0.5f) {
                    MovementUtils.VulcanMoveStrafe(0.484f);
                }
            }

            if (!mc.player.isOnGround()) {
                vulcanticks++;
            }
            if (vulcanticks == 4) {
                mc.player.setVelocity(mc.player.getVelocity().getX(), mc.player.getVelocity().getY() - 0.17, mc.player.getVelocity().getZ());
            }

            if (vulcanticks == 1) {
                MovementUtils.strafe(0.33f);
            }
        }
        return false;
    }

    @Override
    public boolean onMove(PlayerMoveEvent event) {
        if (settings.strafe.get()) {
            Vec3d vel = PlayerUtils.getHorizontalVelocity(settings.strafespeed.get());
            double velX = vel.getX();
            double velZ = vel.getZ();
            ((IVec3d) event.movement).set(velX, event.movement.y, velZ);
        }

        //This is mainly for older versions of AAC
        if (settings.timerhop.get()) {
            if (settings.timehopnormal.get()) {
                if (PlayerUtils.isMoving()) {
                    (Modules.get().get(AutoJump.class)).toggle();
                }
                if (!PlayerUtils.isMoving()) {
                    (Modules.get().get(AutoJump.class)).forceToggle(false);
                }

                if (mc.player.fallDistance <= 0.1)
                    Modules.get().get(Timer.class).setOverride(1.7f);
                else if (mc.player.fallDistance < 1.3)
                    Modules.get().get(Timer.class).setOverride(0.8f);
                else
                    Modules.get().get(Timer.class).setOverride(1.0f);
            }
            if (settings.timerhopstrict.get()) {
                if (PlayerUtils.isMoving()) {
                    (Modules.get().get(AutoJump.class)).toggle();
                }
                if (!PlayerUtils.isMoving()) {
                    (Modules.get().get(AutoJump.class)).forceToggle(false);
                }

                if (mc.player.fallDistance <= 0.1)
                    Modules.get().get(Timer.class).setOverride(2f);
                else if (mc.player.fallDistance < 1.3)
                    Modules.get().get(Timer.class).setOverride(0.9f);
                else
                    Modules.get().get(Timer.class).setOverride(1.0f);
            }
            if (settings.timerhopsubtle.get()) {
                if (PlayerUtils.isMoving()) {
                    (Modules.get().get(AutoJump.class)).toggle();
                }
                if (!PlayerUtils.isMoving()) {
                    (Modules.get().get(AutoJump.class)).forceToggle(false);
                }

                if (mc.player.fallDistance <= 0.1)
                    Modules.get().get(Timer.class).setOverride(1.1f);
                else if (mc.player.fallDistance < 1.3)
                    Modules.get().get(Timer.class).setOverride(0.7f);
                else
                    Modules.get().get(Timer.class).setOverride(1.0f);
            }
        }
        if (MovementUtils.isMoving()) {
            if (settings.bypass1.get()) {
                if (mc.player.isAlive() && !mc.player.isSleeping() && mc.player.isOnGround()) {
                    mc.player.setVelocity(mc.player.getVelocity().x, 0.7, mc.player.getVelocity().z);
                }
                MovementUtils.strafe(3.4);
            }
        }
        return false;
    }

    @Override
    public void onRubberband() {
        if (settings.rubberband.get()) {
            (Modules.get().get(Speed.class)).forceToggle(false);
        }
    }

    @Override
    public void onDeactivate() {
        Modules.get().get(AutoJump.class).forceToggle(false);
    }
}
