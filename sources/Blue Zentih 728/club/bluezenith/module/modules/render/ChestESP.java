package club.bluezenith.module.modules.render;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.Render3DEvent;
import club.bluezenith.events.impl.RightClickBlockEvent;
import club.bluezenith.events.impl.SpawnPlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.ColorValue;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.util.render.RenderUtil;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ChestESP extends Module {

    public final ColorValue espColor = new ColorValue("Color").setIndex(2);
    public final IntegerValue alpha = new IntegerValue("Alpha", 120, 0, 255, 1).setIndex(3);
    public final BooleanValue hideOpened = new BooleanValue("Hide opened", false).setIndex(1);
    private final List<BlockPos> chests = Lists.newArrayList();

    public ChestESP() {
        super("ChestESP", ModuleCategory.RENDER);
    }

    @Listener
    public void on3D(Render3DEvent event) {
        for(final TileEntity jew : mc.theWorld.loadedTileEntityList) {
            if(jew instanceof TileEntityChest) {
                if(!hideOpened.get() || !chests.contains(jew.getPos())) {
                    final RenderManager renderManager = mc.getRenderManager();
                    final double x1 = jew.getPos().getX() - renderManager.renderPosX;
                    final double y1 = jew.getPos().getY() - renderManager.renderPosY;
                    final double z1 = jew.getPos().getZ() - renderManager.renderPosZ;

                    final TileEntityChest chest = (TileEntityChest) jew;

                    TileEntityChest otherjew = null;
                    if(chest.adjacentChestXNeg != null)
                        otherjew = chest.adjacentChestXNeg;
                    else if(chest.adjacentChestXPos != null)
                        otherjew = chest.adjacentChestXPos;
                    else if(chest.adjacentChestZNeg != null)
                        otherjew = chest.adjacentChestZNeg;
                    else if(chest.adjacentChestZPos != null)
                        otherjew = chest.adjacentChestZPos;

                    AxisAlignedBB axisAlignedBB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(x1, y1, z1);
                    if(otherjew != null) {
                        if(chests.contains(otherjew.getPos()) && hideOpened.get()) continue;
                        final double x2 = otherjew.getPos().getX() - renderManager.renderPosX;
                        final double y2 = otherjew.getPos().getY() - renderManager.renderPosY;
                        final double z2 = otherjew.getPos().getZ() - renderManager.renderPosZ;
                       axisAlignedBB = axisAlignedBB.union(new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(x2, y2, z2));
                    }
                    draw(axisAlignedBB, espColor.getRGB());
                }
            }
        }
    }

    void draw(AxisAlignedBB axisAlignedBB, int color) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_DEPTH_TEST);

            GL11.glDepthMask(false);

            float f3 = (float) alpha.get() / 255.0F;
            float f = (float) (color >> 16 & 255) / 255.0F;
            float f1 = (float) (color >> 8 & 255) / 255.0F;
            float f2 = (float) (color & 255) / 255.0F;

            GlStateManager.color(f, f1, f2, f3);
            RenderUtil.drawAxis(axisAlignedBB);
            GlStateManager.resetColor();
            GL11.glDepthMask(true);

            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
        }

    @Listener
    public void fuckJews(SpawnPlayerEvent event) {
        if(!chests.isEmpty())
        chests.clear();
    }


    @Listener
    public void onRightClick(RightClickBlockEvent event) {
        if(world.getBlockState(event.clickPos).getBlock() instanceof BlockChest) {
            chests.add(event.clickPos);
        }
    }
}
