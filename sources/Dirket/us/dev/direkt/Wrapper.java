package us.dev.direkt;

import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

/**
 * @author Foundry
 */
public class Wrapper {

    /*
    Getters for various parts of Minecraft
     */
    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public static EntityPlayerSP getPlayer() {
        return getMinecraft().thePlayer;
    }

    public static WorldClient getWorld() {
        return getMinecraft().theWorld;
    }

    public static FontRenderer getFontRenderer() {
        return getMinecraft().fontRendererObj;
    }

    public static PlayerControllerMP getPlayerController() {
        return getMinecraft().playerController;
    }

    public static NetHandlerPlayClient getSendQueue() {
        return getPlayer().connection;
    }

    public static GameSettings getGameSettings() {
        return getMinecraft().gameSettings;
    }

    public static InventoryPlayer getInventory() {
        return getPlayer().inventory;
    }

    public static RayTraceResult getMouseOver() {
        return getMinecraft().objectMouseOver;
    }

    public static String getDirection() {
        return getMinecraft().getRenderViewEntity().getHorizontalFacing().name();
    }

    public static ScaledResolution getResolution() {
        return new ScaledResolution(getMinecraft());
    }

    public static net.minecraft.util.Timer getTimer() {
        return getMinecraft().getTimer();
    }

    public static Block getBlock(BlockPos pos) {
        return getMinecraft().theWorld.getBlockState(pos).getBlock();
    }

    public static List<Entity> getLoadedEntities() {
        return getWorld().getLoadedEntityList();
    }

    public static List<EntityPlayer> getLoadedPlayers() {
        return getWorld().playerEntities;
    }

    public static List<EntityPlayer> getLoadedPlayersNoNPCs() {
        return getLoadedPlayers().stream()
                .filter(player -> getPlayer().connection.getPlayerInfo(player.getUniqueID()) != null)
                .collect(Collectors.toList());
    }

    public static List<TileEntity> getLoadedTileEntities() {
        return getWorld().loadedTileEntityList;
    }

    /*
    Accessors for server utilities
     */
    public static void sendPacket(Packet<INetHandlerPlayServer> packet) {
        getSendQueue().sendPacket(packet);
    }

    public static void receivePacket(Packet<INetHandlerPlayClient> packet) {
        packet.processPacket(getSendQueue());
    }

    public static void sendPacketDirect(Packet packet) {
        getSendQueue().getNetworkManager().sendPacket(packet);
    }

    public static void sendChatMessage(String msg) {
        sendPacketDirect(new CPacketChatMessage(msg));
    }

    public static double getDistance(Entity e, BlockPos pos) {
        return Math.sqrt((e.posX - pos.getX()) * (e.posX - pos.getX()) + (e.posY - pos.getY()) * (e.posY - pos.getY()) + (e.posZ - pos.getZ()) * (e.posZ - pos.getZ()));
    }

    public static double getDistance(double x, double y, double z, double x2, double y2, double z2) {
    	double dx = x2 - x;
    	double dy = y2 - y;
    	double dz = z2 - z;
    	//System.out.println(getBlockDistance(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z), MathHelper.floor_double(x2), MathHelper.floor_double(y2), MathHelper.floor_double(z2)));
    	return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    public static int drawStringWithShadow(String string, float x, float y, int color) {
        return getFontRenderer().drawStringWithShadow(string, x, y, color);
    }

    public static int drawString(String string, int x, int y, int color) {
        return getFontRenderer().drawString(string, x, y, color);
    }

    public static void updateInventory() {
        for (int i = 0; i < Wrapper.getPlayer().inventory.mainInventory.length; i++) {
        	final int offset = i < 9 ? 36 : 0;
        	Wrapper.sendPacketDirect(new CPacketCreativeInventoryAction(i + offset, Wrapper.getPlayer().inventory.mainInventory[i]));
        }
    }
    
    public static void faceEntity(Entity entity) {
        double 	posX 		= entity.posX - getPlayer().posX,
                posZ 		= entity.posZ - getPlayer().posZ,
                posY 		= (entity.posY + entity.getEyeHeight() - getPlayer().posY + (getPlayer().getEyeHeight())),
                helper 		= MathHelper.sqrt_double((posX * posX) + (posZ * posZ));
        float 	newYaw 		= (float) Math.toDegrees(-Math.atan(posX / posZ)),
                newPitch 	= (float) -Math.toDegrees(Math.atan(posY / helper));

        if ((posZ < 0) && (posX < 0)) {
            newYaw = (float) (90 + Math.toDegrees(Math.atan(posZ / posX)));
        } else if ((posZ < 0) && (posX > 0)) {
            newYaw = (float) (-90 + Math.toDegrees(Math.atan(posZ / posX)));
        }

        getSendQueue().sendPacket(new CPacketPlayer.Rotation(newYaw, newPitch - 0.1F, getPlayer().onGround));
    }

    public static float getDistanceBetweenAngles(float par1, float par2) {
        float angle = (Math.abs(par1 - par2)) % 360;
        if (angle > 180F)
            angle = (360F - angle);
        return angle;
    }


    public static Block getBlockUnderPlayer(EntityPlayer inPlayer) {
        return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - 0.5, inPlayer.posZ));
    }

    public Float[] getRotationToPosition(Entity e, BlockPos pos) {
        double 	x 			= pos.getX() - e.posX,
                y 			= pos.getY() - (e.posY + e.getEyeHeight()),
                z 			= pos.getZ() - e.posZ,
                helper 		= MathHelper.sqrt_double((x * x) + (z * z));
        float 	newYaw 		= (float) Math.toDegrees(-Math.atan(x / z)),
                newPitch 	= (float) -Math.toDegrees(Math.atan(y / helper));

        if ((z < 0) && (x < 0)) {
            newYaw = (float) (90 + Math.toDegrees(Math.atan(z / x)));
        } else if ((z < 0) && (x > 0)) {
            newYaw = (float) (-90 + Math.toDegrees(Math.atan(z / x)));
        }

        return new Float[] {newYaw, newPitch};
    }

    public static boolean isHolding(EntityPlayer inPlayer, Class<? extends Item> itemClass){
        boolean check = false;
        if (inPlayer.getHeldItem(EnumHand.MAIN_HAND) != null)
            check =  inPlayer.getHeldItem(EnumHand.MAIN_HAND).getItem().getClass().isAssignableFrom(itemClass);
        else if (inPlayer.getHeldItem(EnumHand.OFF_HAND) != null)
            check =  inPlayer.getHeldItem(EnumHand.MAIN_HAND).getItem().getClass().isAssignableFrom(itemClass);
        return check;
    }

    public static int getClosestBlockDistanceUnderPlayer(EntityPlayer p){
        for(int i = MathHelper.floor_double(p.posY); i > 0; i--){
            BlockPos bp = new BlockPos(MathHelper.floor_double(p.posX), i, MathHelper.floor_double(p.posZ));
            if(Wrapper.getBlocks(Wrapper.getWorld().getBlockState(bp).getBlock())){
                return MathHelper.floor_double(p.posY) - i - 1;
            }
        }
        return -1;
    }

    public static boolean onGround() {
		return Minecraft.getMinecraft().thePlayer.onGround || (Minecraft.getMinecraft().thePlayer.isCollidedVertically);
    }
    
    private static boolean getBlocks(Block b){
        return (b != Blocks.AIR) && (b != Blocks.TALLGRASS) && (b != Blocks.DEADBUSH) && (b != Blocks.DOUBLE_PLANT) && (b != Blocks.LAVA) && (b != Blocks.WATER) && (b != Blocks.LADDER);
    }

    public static boolean isInBlock(Entity e, float offset) {
        for (int x = MathHelper.floor_double(e.getEntityBoundingBox().minX); x < MathHelper.floor_double(e.getEntityBoundingBox().maxX) + 1; x++) {
            for (int y = MathHelper.floor_double(e.getEntityBoundingBox().minY); y < MathHelper.floor_double(e.getEntityBoundingBox().maxY) + 1; y++) {
                for (int z = MathHelper.floor_double(e.getEntityBoundingBox().minZ); z < MathHelper.floor_double(e.getEntityBoundingBox().maxZ) + 1; z++) {
                    Block block = Wrapper.getWorld().getBlockState(new BlockPos(x, y + offset, z)).getBlock();
                    if (block == null || block instanceof BlockAir || block instanceof BlockLiquid) {
                        continue;
                    }

                    AxisAlignedBB boundingBox = block.getCollisionBoundingBox(Wrapper.getWorld().getBlockState(new BlockPos(x, y + offset, z)) , Wrapper.getWorld(), new BlockPos(x, y + offset, z));
                    if (boundingBox != null && e.getEntityBoundingBox().intersectsWith(boundingBox)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static double[] moveLooking( float yawOffset) {
		float dir = Wrapper.getPlayer().rotationYaw + yawOffset;
		//dir += (Wrapper.getPlayer().moveForward < 0.0F ? 180.0F : (Wrapper.getPlayer().moveStrafing > 0.0F ? -(90.0F * (Wrapper.getPlayer().moveForward < 0.0F ? -0.5F : Wrapper.getPlayer().moveForward > 0.0F ? 0.5F : 1.0F)) : ( Wrapper.getPlayer().moveStrafing < 0.0F ? (90.0F * (Wrapper.getPlayer().moveForward < 0.0F ? -0.5F : Wrapper.getPlayer().moveForward > 0.0F ? 0.5F : 1.0F)) : 0)));
		//import is a faggot
						if (Wrapper.getPlayer().moveForward < 0.0F) {
			dir += 180.0F;
		}
		if (Wrapper.getPlayer().moveStrafing > 0.0F) {
			dir -= 90.0F * (Wrapper.getPlayer().moveForward < 0.0F ? -0.5F : Wrapper.getPlayer().moveForward > 0.0F ? 0.5F : 1.0F);
		}
		if (Wrapper.getPlayer().moveStrafing < 0.0F) {
			dir += 90.0F * (Wrapper.getPlayer().moveForward < 0.0F ? -0.5F : Wrapper.getPlayer().moveForward > 0.0F ? 0.5F : 1.0F);
		}

		float xD = (float)Math.cos((dir + 90.0F) * Math.PI / 180.0D);
		float zD = (float)Math.sin((dir + 90.0F) * Math.PI / 180.0D); // sin(x + 90) = cos(x) ? Explain?
		return new double[] { xD, zD };
    }

    public static String getGpu() {
    	return GlStateManager.glGetString(GL11.GL_RENDERER);
    }
    
    public static void updatePosition(double x, double y, double z) {
        Wrapper.sendPacket(new CPacketPlayer.Position(x, y, z, Wrapper.getPlayer().onGround));
    }

    /*
    Accessors for client utilities
     */
    public static void addChatMessage(Object msg) {
        getPlayer().addChatComponentMessage(new TextComponentTranslation("\2478[\2479" + Direkt.getInstance().getClientName() + "\2478] ").appendSibling(new TextComponentTranslation(String.valueOf(msg)).setStyle(new Style().setColor(TextFormatting.GRAY))));
    }

    
}