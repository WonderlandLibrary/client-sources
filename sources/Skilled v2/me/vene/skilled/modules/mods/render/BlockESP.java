 package me.vene.skilled.modules.mods.render;
 
 import java.awt.Color;
 import java.lang.reflect.Field;
 import java.util.ArrayList;
 import java.util.List;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.RenderUtil;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.block.Block;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.entity.EntityPlayerSP;
 import net.minecraft.client.multiplayer.WorldClient;
 import net.minecraft.client.renderer.entity.RenderManager;
 import net.minecraft.util.AxisAlignedBB;
 import net.minecraft.util.BlockPos;
 import net.minecraftforge.client.event.RenderWorldLastEvent;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 import org.lwjgl.opengl.GL11;
 
public class BlockESP extends Module
{
    private static final int DELAY = 600;
    private List<BlockESPBlock> blockList;
    private int searchTicks;
    private Minecraft mc;
    private NumberValue range;
    private BooleanValue emerald;
    private BooleanValue diamond;
    private BooleanValue gold;
    private BooleanValue lapis;
    private BooleanValue redstone;
    private BooleanValue iron;
    private BooleanValue coal;
    private BooleanValue obsd;
    
    public BlockESP() {
        super("Block ESP", 0, Category.R);
        this.blockList = new ArrayList<BlockESPBlock>();
        this.searchTicks = 0;
        this.mc = Minecraft.getMinecraft();
        this.range = new NumberValue("Range", 40.0, 20.0, 60.0);
        this.emerald = new BooleanValue("Emerald Ore", true);
        this.diamond = new BooleanValue("Diamond Ore", true);
        this.gold = new BooleanValue("Gold Ore", true);
        this.lapis = new BooleanValue("Lapis Lazuli Ore", true);
        this.redstone = new BooleanValue("Redstone Ore", true);
        this.iron = new BooleanValue("Iron Ore", true);
        this.coal = new BooleanValue("Coal Ore", true);
        this.obsd = new BooleanValue("Obsidian", true);
        this.addValue(this.range);
        this.addOption(this.emerald);
        this.addOption(this.diamond);
        this.addOption(this.gold);
        this.addOption(this.lapis);
        this.addOption(this.redstone);
        this.addOption(this.iron);
        this.addOption(this.coal);
        this.addOption(this.obsd);
    }
    
    @Override
    public void onEnable() {
        this.search();
    }
    
    @Override
    public void onDisable() {
        this.blockList.clear();
        this.searchTicks = 0;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.theWorld == null) {
            this.searchTicks = 0;
            return;
        }
        if (++this.searchTicks == 600) {
            this.searchTicks = 0;
            this.search();
        }
    }
    
    @Override
    public void onRenderEvent(final RenderWorldLastEvent event) {
        if (this.blockList.isEmpty()) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.0f);
        for (final BlockESPBlock block : this.blockList) {
            final Color color = block.getColor();
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            final String Xstring = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', '5', '_', 'b' });
            final String Ystring = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', '6', '_', 'c' });
            final String Zstring = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', '3', '_', 'd' });
            final double renderPosX = GetFieldByReflection(RenderManager.class, this.mc.getRenderManager(), StringRegistry.register(Xstring));
            final double renderPosY = GetFieldByReflection(RenderManager.class, this.mc.getRenderManager(), StringRegistry.register(Ystring));
            final double renderPosZ = GetFieldByReflection(RenderManager.class, this.mc.getRenderManager(), StringRegistry.register(Zstring));
            final double x = block.getX() - renderPosX;
            final double y = block.getY() - renderPosY;
            final double z = block.getZ() - renderPosZ;
            RenderUtil.drawOutlinedBoundingBox(AxisAlignedBB.fromBounds(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        }
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    private void search() {
        this.blockList.clear();
        final EntityPlayerSP player = this.mc.thePlayer;
        final WorldClient world = this.mc.theWorld;
        int y;
        for (int range = y = (int)this.range.getValue(); y >= -range; --y) {
            for (int x = range; x >= -range; --x) {
                for (int z = range; z >= -range; --z) {
                    final int posX = (int)(player.posX + x);
                    final int posY = (int)(player.posY + y);
                    final int posZ = (int)(player.posZ + z);
                    final Block block = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
                    final int id = Block.getIdFromBlock(block);
                    if (id == 129 && this.emerald.getState()) {
                        this.blockList.add(new BlockESPBlock(posX, posY, posZ, Color.GREEN));
                    }
                    else if (id == 56 && this.diamond.getState()) {
                        this.blockList.add(new BlockESPBlock(posX, posY, posZ, Color.CYAN));
                    }
                    else if (id == 14 && this.gold.getState()) {
                        this.blockList.add(new BlockESPBlock(posX, posY, posZ, Color.YELLOW));
                    }
                    else if (id == 21 && this.lapis.getState()) {
                        this.blockList.add(new BlockESPBlock(posX, posY, posZ, Color.BLUE));
                    }
                    else if ((id == 73 || id == 74) && this.redstone.getState()) {
                        this.blockList.add(new BlockESPBlock(posX, posY, posZ, Color.RED));
                    }
                    else if (id == 15 && this.iron.getState()) {
                        this.blockList.add(new BlockESPBlock(posX, posY, posZ, Color.PINK));
                    }
                    else if (id == 16 && this.coal.getState()) {
                        this.blockList.add(new BlockESPBlock(posX, posY, posZ, Color.BLACK));
                    }
                    else if (id == 49 && this.obsd.getState()) {
                        this.blockList.add(new BlockESPBlock(posX, posY, posZ, new Color(255, 0, 255)));
                    }
                }
            }
        }
    }
    
    public static <T, E> T GetFieldByReflection(final Class<? super E> classToAccess, final E instance, final String... fieldNames) {
        Field field = null;
        for (final String fieldName : fieldNames) {
            try {
                field = classToAccess.getDeclaredField(fieldName);
            }
            catch (NoSuchFieldException ex) {}
            if (field != null) {
                break;
            }
        }
        if (field != null) {
            field.setAccessible(true);
            T fieldT = null;
            try {
                fieldT = (T)field.get(instance);
            }
            catch (IllegalArgumentException ex2) {}
            catch (IllegalAccessException ex3) {}
            return fieldT;
        }
        return null;
    }
    
    private class BlockESPBlock
    {
        private int x;
        private int y;
        private int z;
        private Color color;
        
        BlockESPBlock(final int x, final int y, final int z, final Color color) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.color = color;
        }
        
        private int getX() {
            return this.x;
        }
        
        private int getY() {
            return this.y;
        }
        
        private int getZ() {
            return this.z;
        }
        
        private Color getColor() {
            return this.color;
        }
    }
}