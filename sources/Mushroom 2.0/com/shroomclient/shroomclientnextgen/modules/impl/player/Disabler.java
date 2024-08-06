package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Scaffold;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.PacketUtil;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

@RegisterModule(
    name = "Disabler",
    uniqueId = "disabler",
    description = "Disables Certain Anticheats",
    category = ModuleCategory.Player
)
public class Disabler extends Module {

    @ConfigOption(
        name = "Vulcan Scaffold",
        description = "Vulcan Acceleration Disabler",
        order = 1
    )
    public static Boolean vulcanScaffold = true;

    @ConfigOption(
        name = "Vulcan Sprint",
        description = "Vulcan Omni-Sprint Disabler",
        order = 1.1
    )
    public static Boolean vulcanOmniSprint = true;

    boolean lastOnGround = false;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        if (vulcanScaffold && Scaffold.scaffolding()) {
            if (MovementUtil.ticks % 10 == 0) {
                PacketUtil.sendPacket(
                    new ClientCommandC2SPacket(
                        C.p(),
                        ClientCommandC2SPacket.Mode.START_SPRINTING
                    ),
                    false
                );
                PacketUtil.sendPacket(
                    new ClientCommandC2SPacket(
                        C.p(),
                        ClientCommandC2SPacket.Mode.STOP_SPRINTING
                    ),
                    false
                );
            } else if (MovementUtil.ticks % 17 == 5) {
                PacketUtil.sendPacket(
                    new ClientCommandC2SPacket(
                        C.p(),
                        ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY
                    ),
                    false
                );
                PacketUtil.sendPacket(
                    new ClientCommandC2SPacket(
                        C.p(),
                        ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY
                    ),
                    false
                );
            }
        } else if (vulcanOmniSprint) {
            if (MovementUtil.ticks % 11 == 0) {
                PacketUtil.sendPacket(
                    new ClientCommandC2SPacket(
                        C.p(),
                        ClientCommandC2SPacket.Mode.START_SPRINTING
                    ),
                    false
                );
                PacketUtil.sendPacket(
                    new ClientCommandC2SPacket(
                        C.p(),
                        ClientCommandC2SPacket.Mode.STOP_SPRINTING
                    ),
                    false
                );
            }
        }
        // patched
        /*
        if (grim) {
            PacketUtil.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(C.p().getX() + 10, C.p().getY(), C.p().getZ(), C.p().isOnGround()), true);
            PacketUtil.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(C.p().getX() + 1000, C.p().getY(), C.p().getZ() + 1000, C.p().isOnGround()), true);
        }
         */
    }
    /*
    // would be a cool tp aura for any anticheat which doesnt have timer checks (like tubnet :skull:)
    Entity entAttacked = null;

    PlayerInteractEntityC2SPacket attack = null;
    boolean hit = false;


    @SubscribeEvent
    public void packetEvent(PacketEvent.Send.Pre e) {
        if (reach && e.getPacket() instanceof PlayerInteractEntityC2SPacket n && !hit && C.p().isOnGround() && MovementUtil.ticks % 20 == 0) {
            int entityId = ((PlayerInteractEntityC2SPacketAccessor) n).getEntityId();
            entAttacked = C.w().getEntityById(entityId);

            float yDif = (float)(C.p().getY() - entAttacked.getY());

            if (C.p().distanceTo(entAttacked) > 3 && Math.sqrt(yDif*yDif) < 3) {
                attack = n;
                e.cancel();
                hit = true;
            }
        }
    }

    @SubscribeEvent
    public void onUpdate(MotionEvent.Pre event) {
        if (attack != null) {

            Vec3d targetPos = entAttacked.getPos();

            Vec3d oldPos = C.p().getPos();
            Vec3d currentPos = C.p().getPos();


            double speed = 0.05;
            
            // close in on the ops
            while (MovementUtil.distanceTo(currentPos, targetPos) > 3) {
                double rotationYawNeed = RotationUtil.getRotation(targetPos).yaw;

                double cos = Math.cos(Math.toRadians(rotationYawNeed + 90.0f));
                double sin = Math.sin(Math.toRadians(rotationYawNeed + 90.0f));

                double x = currentPos.x;
                double z = currentPos.z;

                x += speed * cos;
                z += speed * sin;

                currentPos = new Vec3d(x,C.p().getY(),z);

                PacketUtil.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(false), true);
                PacketUtil.sendPacket(new PlayerMoveC2SPacket.Full(currentPos.x,currentPos.y,currentPos.z, event.getYaw(),event.getPitch(), true), true);
                C.p().setPos(currentPos.x,currentPos.y,currentPos.z);
            }

            // ATTACK
            C.p().swingHand(Hand.MAIN_HAND);
            PacketUtil.sendPacket(attack, true);

            attack = null;
            hit = false;

            // retreat in cowardess
            while (MovementUtil.distanceToExcludeY(currentPos, oldPos) > 0.01f) {
                double rotationYawNeed = RotationUtil.getRotation(oldPos).yaw;

                double cos = Math.cos(Math.toRadians(rotationYawNeed + 90.0f));
                double sin = Math.sin(Math.toRadians(rotationYawNeed + 90.0f));

                double x = currentPos.x;
                double z = currentPos.z;

                x += speed * cos;
                z += speed * sin;

                currentPos = new Vec3d(x,C.p().getY(),z);

                PacketUtil.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(false), true);
                PacketUtil.sendPacket(new PlayerMoveC2SPacket.Full(currentPos.x,currentPos.y,currentPos.z, event.getYaw(),event.getPitch(), true), true);
                C.p().setPos(currentPos.x,currentPos.y,currentPos.z);
            }
        }
    }
     */

}
