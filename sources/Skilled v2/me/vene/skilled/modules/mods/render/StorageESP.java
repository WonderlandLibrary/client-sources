 package me.vene.skilled.modules.mods.render;
 
 import java.util.Iterator;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.Box;
 import me.vene.skilled.utilities.OGLRender;
 import me.vene.skilled.utilities.ReflectionUtil;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.renderer.entity.RenderManager;
 import net.minecraft.tileentity.TileEntity;
 import net.minecraft.tileentity.TileEntityChest;
 import net.minecraft.tileentity.TileEntityEnderChest;
 import net.minecraftforge.client.event.RenderWorldLastEvent;
 import org.lwjgl.opengl.GL11;

public class StorageESP extends Module
{
    private BooleanValue enderchestOpt;
    private Minecraft mc;
    
    public StorageESP() {
        super(StringRegistry.register("Storage ESP"), 0, Category.R);
        this.enderchestOpt = new BooleanValue("Ender chest", false);
        this.mc = Minecraft.getMinecraft();
        this.addOption(this.enderchestOpt);
    }
    
    @Override
    public void onRenderEvent(final RenderWorldLastEvent event) {
        final Iterator<TileEntity> iterator = this.mc.theWorld.loadedTileEntityList.iterator();
        while (iterator.hasNext()) {
            final TileEntity tileEntity;
            if ((tileEntity = iterator.next()) instanceof TileEntity && !this.shouldDraw(tileEntity)) {
                continue;
            }
            final RenderManager renderthing = this.mc.getRenderManager();
            final String Xstring = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', '5', '_', 'b' });
            final String Ystring = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', '6', '_', 'c' });
            final String Zstring = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', '3', '_', 'd' });
            final double renderPosX = Double.valueOf(ReflectionUtil.getFieldValue(StringRegistry.register(Xstring), renderthing, RenderManager.class).toString());
            final double renderPosY = Double.valueOf(ReflectionUtil.getFieldValue(StringRegistry.register(Ystring), renderthing, RenderManager.class).toString());
            final double renderPosZ = Double.valueOf(ReflectionUtil.getFieldValue(StringRegistry.register(Zstring), renderthing, RenderManager.class).toString());
            final double x = tileEntity.getPos().getX() - renderPosX;
            final double y = tileEntity.getPos().getY() - renderPosY;
            final double z = tileEntity.getPos().getZ() - renderPosZ;
            final float[] color = this.getColor(tileEntity);
            Box box = new Box(x, y, z, x + 1.0, y + 1.0, z + 1.0);
            if (tileEntity instanceof TileEntityChest) {
                final TileEntityChest chest = TileEntityChest.class.cast(tileEntity);
                if (chest.adjacentChestZNeg != null) {
                    box = new Box(x + 0.0625, y, z - 0.9375, x + 0.9375, y + 0.875, z + 0.9375);
                }
                else if (chest.adjacentChestXNeg != null) {
                    box = new Box(x + 0.9375, y, z + 0.0625, x - 0.9375, y + 0.875, z + 0.9375);
                }
                else {
                    if (chest.adjacentChestZNeg != null || chest.adjacentChestXNeg != null || chest.adjacentChestXPos != null) {
                        continue;
                    }
                    if (chest.adjacentChestZPos != null) {
                        continue;
                    }
                    box = new Box(x + 0.0625, y, z + 0.0625, x + 0.9375, y + 0.875, z + 0.9375);
                }
            }
            else if (tileEntity instanceof TileEntityEnderChest && this.enderchestOpt.getState()) {
                box = new Box(x + 0.0625, y, z + 0.0625, x + 0.9375, y + 0.875, z + 0.9375);
            }
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glLineWidth(1.0f);
            GL11.glDisable(3553);
            GL11.glDepthMask(false);
            GL11.glEnable(2848);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glEnable(2848);
            GL11.glTranslated(x, y, z);
            GL11.glTranslated(-x, -y, -z);
            GL11.glColor4f(color[0], color[1], color[2], 0.15f);
            OGLRender.drawBox(box);
            GL11.glColor4f(color[0], color[1], color[2], 1.0f);
            OGLRender.drawOutlinedBox(box);
            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glPopMatrix();
        }
    }
    
    private boolean shouldDraw(final TileEntity tileEntity) {
        return tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityEnderChest;
    }
    
    private float[] getColor(final TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityChest) {
            return new float[] { 0.1f, 0.8f, 0.1f };
        }
        if (tileEntity instanceof TileEntityEnderChest) {
            return new float[] { 1.0f, 0.0f, 1.0f };
        }
        return new float[] { 1.0f, 1.0f, 1.0f };
    }
}
