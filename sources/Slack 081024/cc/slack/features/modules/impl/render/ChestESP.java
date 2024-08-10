// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.render;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.render.ColorUtil;
import cc.slack.utils.render.RenderUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleInfo(
        name = "ChestESP",
        category = Category.RENDER
)
public class ChestESP extends Module {

    private final NumberValue<Float> lineWidth = new NumberValue<>("Line Width", 2.5f, 1f, 10f, 0.1f);
    public final ModeValue<String> colormodes = new ModeValue<>("Color", new String[] { "Client Theme", "Rainbow", "Custom" });
    private final NumberValue<Integer> redValue = new NumberValue<>("Red", 0, 0, 255, 1);
    private final NumberValue<Integer> greenValue = new NumberValue<>("Green", 255, 0, 255, 1);
    private final NumberValue<Integer> blueValue = new NumberValue<>("Blue", 255, 0, 255, 1);
    private final NumberValue<Integer> alphaValue = new NumberValue<>("Alpha", 200, 0, 255, 1);

    public ChestESP() {
        addSettings(lineWidth ,colormodes,redValue, greenValue, blueValue, alphaValue);
    }

    @Listen
    public void onRender(RenderEvent event) {
        Color ct = ColorUtil.getColor();
        int color = colormodes.getValue().equals("Client Theme") ? ct.getRGB() :
                colormodes.getValue().equals("Rainbow") ? ColorUtil.rainbow(-100, 1.0f, 0.47f).getRGB() :
                        new Color(redValue.getValue(), greenValue.getValue(), blueValue.getValue(), alphaValue.getValue()).getRGB();

        for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
            if (tileEntity instanceof TileEntityChest) {
                RenderUtil.renderOne(lineWidth.getValue());
                TileEntityRendererDispatcher.instance.renderTileEntity(tileEntity, event.getPartialTicks(), -1);
                RenderUtil.renderTwo();
                TileEntityRendererDispatcher.instance.renderTileEntity(tileEntity, event.getPartialTicks(), -1);
                RenderUtil.renderThree();
                TileEntityRendererDispatcher.instance.renderTileEntity(tileEntity, event.getPartialTicks(), -1);
                RenderUtil.renderFour(new Color(color));
                TileEntityRendererDispatcher.instance.renderTileEntity(tileEntity, event.getPartialTicks(), -1);
                RenderUtil.renderFive();
                GL11.glColor4f(1, 1, 1, 1);
                GlStateManager.resetColor();
            }
        }
    }
}
