package us.dev.direkt.module.internal.movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;
import us.dev.api.property.Property;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.block.EventBlockCollision;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.direkt.util.client.MovementUtils;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

import java.util.ArrayList;
import java.util.List;

@ModData(label = "Liquid Walk", aliases = {"basilisk", "dolphin", "waterwalk"}, category = ModCategory.MOVEMENT)
public class LiquidWalk extends ToggleableModule {

    @Exposed(description = "Should you blink over lava to avoid being ignited")
    private Property<Boolean> lavaBlink = new Property<>("Lava Blink", false);

    private List<Packet> packets = new ArrayList<>();
    public static float mode = 0;
    private int delay = 0;
    private float godown;
    private boolean onLiquid = false;
    private boolean getdown = false;
    private Timer timer = new Timer();
    private boolean shouldStopLavaBlinking;

    @Listener
    protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
        if (Wrapper.getBlockUnderPlayer(Wrapper.getPlayer()).equals(Blocks.LAVA)) {
            double[] dir = Wrapper.moveLooking(0);
            double xDir = dir[0];
            double zDir = dir[1];
            if (Wrapper.getGameSettings().keyBindForward.isKeyDown() || Wrapper.getGameSettings().keyBindBack.isKeyDown() || Wrapper.getGameSettings().keyBindRight.isKeyDown() || Wrapper.getGameSettings().keyBindLeft.isKeyDown()) {
                Wrapper.getPlayer().motionX = xDir * 0.16F;
                Wrapper.getPlayer().motionZ = zDir * 0.16F;
                Wrapper.sendPacket(new CPacketEntityAction(Wrapper.getPlayer(), Action.STOP_SPRINTING));
            }
        }
        if (!shouldStopLavaBlinking && packets.size() > 40) {
            shouldStopLavaBlinking = true;
        }
        
        if (shouldStopLavaBlinking && !this.onLiquid)
            shouldStopLavaBlinking = false;
        
        if (Wrapper.getWorld().containsAnyLiquid(Wrapper.getPlayer().getEntityBoundingBox()) && Wrapper.getPlayer().isInsideOfMaterial(Material.AIR) && !Wrapper.getPlayer().isSneaking() && getWaterHeight() < 5 && ((!Wrapper.getPlayer().isCollidedVertically && Wrapper.getPlayer().fallDistance == 0) || (Wrapper.getPlayer().isInWater()))) {
            Wrapper.getPlayer().motionY = (0.08500000000000001D * (Wrapper.getPlayer().isCollidedHorizontally ? 1 : 1));
        } else if (Wrapper.getPlayer().isInWater() && Wrapper.getGameSettings().keyBindJump.isKeyDown()) {
            Wrapper.getPlayer().motionY = 0.085;
        } else if (Wrapper.getPlayer().isInWater() && Wrapper.getGameSettings().keyBindSneak.isKeyDown()) {
            Wrapper.getPlayer().motionY = -0.15;
        }
        //Wrapper.addChatMessage(getBlocksCollidedHorizontallyWith(Wrapper.getPlayer().getEntityBoundingBox()));
        onLiquid = isOnLiquid(Wrapper.getPlayer().getEntityBoundingBox());
        //Wrapper.addChatMessage(this.onLiquid);
        getdown = !getdown;
        if (lavaBlink.getValue()) {
            if (Wrapper.getWorld() != null && (!this.onLiquid || this.shouldStopLavaBlinking)) {
                packets.forEach(Wrapper::sendPacketDirect);
                packets.clear();
            }
        }
    });

    @Listener
    protected Link<EventSendPacket> onSendPacket = new Link<>(event -> {
        if (this.onLiquid) {
            if (Wrapper.getPlayer().fallDistance < 4)
                event.setPacket(new CPacketPlayer.PositionRotation(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, Wrapper.getPlayer().rotationYaw, Wrapper.getPlayer().rotationPitch, true));

            if (getdown)
                event.setPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 0.00001 + (Math.random() * 0.01), Wrapper.getPlayer().posZ, false));
            if (lavaBlink.getValue() && !shouldStopLavaBlinking) {
                if (MovementUtils.isMoving(Wrapper.getPlayer()) && Wrapper.getBlockUnderPlayer(Wrapper.getPlayer()).equals(Blocks.LAVA)) {
                    if (event.getPacket() instanceof CPacketPlayer) {
                        event.setCancelled(true);
                        packets.add(event.getPacket());
                    }
                }
            }
        }
    }, new PacketFilter<>(CPacketPlayer.class));

    @Listener
    protected Link<EventBlockCollision> onBlockCollision = new Link<>(event -> {
        if (Wrapper.getWorld() != null && event.getBlock() instanceof BlockLiquid && Wrapper.getPlayer().fallDistance <= 4 && !Wrapper.getPlayer().isSneaking() && getWaterHeight() < 5 && !(Wrapper.getPlayer().isInWater() || Wrapper.getPlayer().isInLava()))
            if (Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                event.setBoundingBox(new AxisAlignedBB(0, 0, 0, 1, 0.9, 1));
            } else
                event.setBoundingBox(new AxisAlignedBB(0.3, 0, 0.3, 0.7, 0.99999, 0.7));
    });

    public static boolean isOnLiquid(AxisAlignedBB boundingBox) {
        AxisAlignedBB bb = new AxisAlignedBB(boundingBox.minX + 0.305, boundingBox.minY - 0.1, boundingBox.minZ + 0.305, boundingBox.maxX - 0.305, boundingBox.maxY, boundingBox.maxZ - 0.305);
        boolean onLiquid = false;
        Block block = Wrapper.getBlock(new BlockPos(bb.minX, bb.minY, bb.minZ));

        if (!(block instanceof BlockLiquid)) {
            return false;
        }
        onLiquid = true;
        return onLiquid;
    }

    /*
     * @author DarkMagician6
     * All credits to the author.
     */
    public static boolean isInLiquid(AxisAlignedBB par1AxisAlignedBB) {
        par1AxisAlignedBB = par1AxisAlignedBB.expand(-0.001D, -0.1D, -0.001D);
        int var4 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
        int var6 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
        int var8 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int var9 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);

        if (Wrapper.getWorld().getChunkFromBlockCoords(new BlockPos(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ)) == null) {
            return false;
        } else {
            Vec3d var11 = new Vec3d(0.0D, 0.0D, 0.0D);
            for (int var12 = var4; var12 < var5; ++var12) {
                for (int var13 = var6; var13 < var7; ++var13) {
                    for (int var14 = var8; var14 < var9; ++var14) {
                        Block var15 = Wrapper.getBlock(new BlockPos(var12, var13, var14));
                        if (var15 instanceof BlockLiquid) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }

    private static int getWaterHeight() {
        int posX = (int) Math.floor(Wrapper.getPlayer().posX);
        int posY = (int) Math.floor(Wrapper.getPlayer().getEntityBoundingBox().minY);
        int posZ = (int) Math.floor(Wrapper.getPlayer().posZ);
        return Wrapper.getWorld().getBlockState(new BlockPos(posX, posY, posZ)).getBlock().getMetaFromState(Wrapper.getWorld().getBlockState(new BlockPos(posX, posY, posZ)));
    }


}
