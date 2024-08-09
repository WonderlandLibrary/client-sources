package wtf.resolute.moduled.impl.combat;

import com.google.common.eventbus.Subscribe;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.status.client.CPingPacket;
import net.minecraft.world.chunk.Chunk;
import wtf.resolute.evented.EventPacket;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.settings.impl.ModeSetting;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import wtf.resolute.moduled.ModuleAnontion;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

@ModuleAnontion(name = "Velocity", type = Categories.Combat,server = "")
public class   Velocity extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Cancel", "Cancel", "Grim Skip", "Grim Cancel", "Funtime", "Test");

    private int skip = 0;
    private boolean cancel;
    boolean damaged;

    public Velocity() {
        addSettings(mode);
    }

    @Subscribe
    public void onPacket(EventPacket e) {
        if (mc.player == null) return;
        if (e.isReceive()) {
            if (e.getPacket() instanceof SEntityVelocityPacket p && p.getEntityID() != mc.player.getEntityId()) return;
            switch (mode.getIndex()) {
                case 0 -> { // Cancel
                    if (e.getPacket() instanceof SEntityVelocityPacket) {
                        e.cancel();
                    }
                }

                case 1 -> { // Grim Skip
                    if (e.getPacket() instanceof SEntityVelocityPacket) {
                        skip = 6;
                        e.cancel();
                    }

                    if (e.getPacket() instanceof CPlayerPacket) {
                        if (skip > 0) {
                            skip--;
                            e.cancel();
                        }
                    }
                }

                case 2 -> { // Grim Cancel
                    if (e.getPacket() instanceof SEntityVelocityPacket) {
                        e.cancel();
                        cancel = true;
                    }
                    if (e.getPacket() instanceof SPlayerPositionLookPacket) {
                        skip = 3;
                    }

                    if (e.getPacket() instanceof CPlayerPacket) {
                        skip--;
                        if (cancel) {
                            if (skip <= 0) {
                                BlockPos blockPos = new BlockPos(mc.player.getPositionVec());
                                mc.player.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), mc.player.rotationYaw, mc.player.rotationPitch, mc.player.isOnGround()));
                                mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
                            }
                            cancel = false;
                        }
                    }
                }

                case 3 -> { // Funtime
                    if (e.getPacket() instanceof SEntityVelocityPacket p) {
                        if (skip >= 2) {
                            return;
                        }
                        if (p.getEntityID() != mc.player.getEntityId()) {
                            return;
                        }
                        e.cancel();
                        damaged = true;
                    }
                    if (e.getPacket() instanceof SPlayerPositionLookPacket) {
                        skip = 3;
                    }
                }

                case 4 -> { // Test
                    if (e.getPacket() instanceof SEntityVelocityPacket p) {
                        if (p.getEntityID() != mc.player.getEntityId()) {
                            return;
                        }
                        if (hasCobwebInHotbar()) {
                            e.cancel();
                            damaged = true;
                        }
                    }
                    if (e.getPacket() instanceof SPlayerPositionLookPacket) {
                        skip = 3;
                    }
                }
            }
        }
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (mode.is("Funtime") || mode.is("Test")) {
            skip--;
            if (damaged) {
                BlockPos blockPos = mc.player.getPosition();
                mc.player.connection.sendPacketWithoutEvent(new CPlayerPacket.PositionRotationPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), mc.player.rotationYaw, mc.player.rotationPitch, mc.player.isOnGround()));
                mc.player.connection.sendPacketWithoutEvent(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
                damaged = false;
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        skip = 0;
        cancel = false;
        damaged = false;
    }

    private boolean hasCobwebInHotbar() {
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() == Items.COBWEB) {
                return true;
            }
        }
        return false;
    }
}
