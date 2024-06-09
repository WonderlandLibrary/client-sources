package net.minecraft.client.main.neptune.Mod.Collection.Movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Events.EventMove;
import net.minecraft.client.main.neptune.Events.EventUpdate;
import net.minecraft.client.main.neptune.Mod.BoolOption;
import net.minecraft.client.main.neptune.Mod.Category;
import net.minecraft.client.main.neptune.Mod.Mod;
import net.minecraft.client.main.neptune.memes.Memeager;
import net.minecraft.client.main.neptune.memes.Memetarget;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Util;

import org.lwjgl.input.Keyboard;

public class Jesus extends Mod {

    public static boolean nextTick;
    public AxisAlignedBB boundingBox;
    private double fallDist;
    public static boolean active;

	public Jesus() {
		super("Jesus", Category.HACKS);
		this.setBind(Keyboard.KEY_J);
	}

	public void onEnable() {
		Memeager.register(this);
	}

	public void onDisable() {
		Memeager.unregister(this);
	}
	
	public static Minecraft mc = Minecraft.getMinecraft();

	public static boolean isInLiquid() {
        boolean inLiquid = false;
        final int y = (int)mc.thePlayer.boundingBox.minY;
        for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                final Block block = getBlock(x, y, z);
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }
    
    public static boolean isOnLiquid() {
        boolean onLiquid = false;
        final int y = (int)mc.thePlayer.boundingBox.offset(0.0, -0.01, 0.0).minY;
        for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                final Block block = getBlock(x, y, z);
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }
    
    @Memetarget
    public void onPreMotion(EventMove event) {
        if (isInLiquid() && Jesus.mc.thePlayer.isInsideOfMaterial(Material.air) && !Jesus.mc.thePlayer.isSneaking()) {
            Jesus.mc.thePlayer.motionY = 0.065 * (1);
        //    mc.thePlayer.motionY = -0.01;
        }
        if(mc.thePlayer.isCollidedHorizontally && mc.thePlayer.isInWater()) {
        	mc.thePlayer.motionY = 0.1;
        }
    }   
    
    @Memetarget
    public void onPacketSend(final Packet packet) {
        if (packet instanceof C03PacketPlayer) {
            final C03PacketPlayer prepacket = (C03PacketPlayer)packet;
            if (isOnLiquid()) {
                Jesus.nextTick = !Jesus.nextTick;
                if (Jesus.nextTick) {
                    final C03PacketPlayer c03PacketPlayer = prepacket;
                    c03PacketPlayer.y -= 0.01;
                }
            }
        }
        if (packet instanceof C03PacketPlayer) {
            final C03PacketPlayer p = (C03PacketPlayer)packet;
            if (Jesus.mc.thePlayer.fallDistance > 3.0f) {
                Jesus.mc.thePlayer.fallDistance = 0.0f;
                p.field_149474_g = true;
            }
        }
    }
    
    public static Block getBlock(final double posX, final double posY, final double posZ) {
        final BlockPos bpos = new BlockPos(posX, posY, posZ);
        final Block block = Minecraft.getMinecraft().theWorld.getBlockState(bpos).getBlock();
        return block;
    }
    
    public void onBoundingBox(final Block block, final BlockPos pos) {
        if (Jesus.mc.thePlayer == null) {
            return;
        }
        if (block instanceof BlockLiquid && !isInLiquid() && !Jesus.mc.thePlayer.isSneaking()) {
            this.boundingBox = AxisAlignedBB.fromBounds(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
        }
    }
    
    public boolean isSafe() {
        return Jesus.mc.thePlayer.isInWater() || Jesus.mc.thePlayer.isInsideOfMaterial(Material.lava) || Jesus.mc.thePlayer.isOnLadder() || Jesus.mc.thePlayer.getActivePotionEffects().contains(Potion.blindness) || Jesus.mc.thePlayer.ridingEntity != null;
    }
    
    private Block getBlockz(final int offset) {
        final int x = (int)Math.round(Jesus.mc.thePlayer.posX - 0.5);
        final int y = (int)Math.round(Jesus.mc.thePlayer.posY - 0.5);
        final int z = (int)Math.round(Jesus.mc.thePlayer.posZ - 0.5);
        final Block block = getBlock(x, y - offset, z);
        return block;
    }
}

