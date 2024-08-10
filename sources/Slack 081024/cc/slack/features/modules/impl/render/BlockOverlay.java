package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.render.ColorUtil;
import cc.slack.utils.render.RenderUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleInfo(
        name = "BlockOverlay",
        category = Category.RENDER
)
public class BlockOverlay extends Module {

    private final NumberValue<Float> lineWidth = new NumberValue<>("Line Width", 2f, 1f, 10f, 0.1f);
    public final ModeValue<String> colormodes = new ModeValue<>("Color", new String[] { "Client Theme", "Rainbow", "Custom" });
    private final NumberValue<Integer> redValue = new NumberValue<>("Red", 0, 0, 255, 1);
    private final NumberValue<Integer> greenValue = new NumberValue<>("Green", 255, 0, 255, 1);
    private final NumberValue<Integer> blueValue = new NumberValue<>("Blue", 255, 0, 255, 1);
    private final NumberValue<Integer> alphaValue = new NumberValue<>("Alpha", 150, 0, 255, 1);

    public BlockOverlay () {
        addSettings(lineWidth,colormodes, redValue, greenValue, blueValue, alphaValue);
    }

    @Listen
    public void onRender (RenderEvent event) {
        Color ct = ColorUtil.getColor();

        if (event.getState() != RenderEvent.State.RENDER_3D) return;

        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos pos = mc.objectMouseOver.getBlockPos();
            final Block block = mc.theWorld.getBlockState(pos).getBlock();
            final RenderManager renderManager = mc.getRenderManager();
            final String s = block.getLocalizedName();
            mc.getRenderManager();
            final double x = pos.getX() - renderManager.getRenderPosX();
            mc.getRenderManager();
            final double y = pos.getY() - renderManager.getRenderPosY();
            mc.getRenderManager();
            final double z = pos.getZ() - renderManager.getRenderPosZ();
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            final Color c = (colormodes.getValue().equals("Client Theme")) ? ct : new Color((!colormodes.getValue().equals("Rainbow")) ? new Color(redValue.getValue(), greenValue.getValue(), blueValue.getValue()).getRGB() : ColorUtil.rainbow(-100, 1.0f, 0.47f).getRGB());
            final int r = c.getRed();
            final int g = c.getGreen();
            final int b = c.getBlue();
            RenderUtil.glColor(new Color(r, g, b, alphaValue.getValue()).getRGB());
            final double minX = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0 : block.getBlockBoundsMinX();
            final double minY = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0 : block.getBlockBoundsMinY();
            final double minZ = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0 : block.getBlockBoundsMinZ();
            GL11.glLineWidth(this.lineWidth.getValue());
            RenderUtil.drawSelectionBoundingBox(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
            RenderUtil.glColor(new Color(0, 0, 0).getRGB());
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

    }

}
