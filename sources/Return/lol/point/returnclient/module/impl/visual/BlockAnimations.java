package lol.point.returnclient.module.impl.visual;

import lol.point.returnclient.events.impl.render.EventRenderItemUse;
import lol.point.returnclient.events.impl.render.EventRenderSwordUse;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.BooleanSetting;
import lol.point.returnclient.settings.impl.StringSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
        name = "BlockAnimations",
        description = "customizes sword blocking animations",
        category = Category.RENDER
)
public class BlockAnimations extends Module {

    public final StringSetting blockStyle = new StringSetting("Block style", "1.7", new String[]{"1.8", "1.7", "Exhibition", "Stab", "Spin", "Komorebi", "Rhys"});
    public final BooleanSetting spoof = new BooleanSetting("Spoof", false);

    public BlockAnimations() {
        addSettings(blockStyle, spoof);
    }

    public String getSuffix() {
        return blockStyle.value;
    }

    @Subscribe
    private final Listener<EventRenderSwordUse> onSwordUse = new Listener<>(renderSwordUse -> {
        renderSwordUse.setCancelled(true);

        final ItemRenderer itemRenderer = renderSwordUse.itemRenderer;
        final float realSwingProgress = renderSwordUse.realSwingProgress;
        final float convertedProgress = MathHelper.sin(MathHelper.sqrt_float(realSwingProgress) * (float) Math.PI);

        switch (blockStyle.value) {
            case "1.8" -> renderSwordUse.setCancelled(false);
            case "1.7" -> {
                itemRenderer.transformFirstPersonItem(renderSwordUse.equippedProgress, realSwingProgress);
                itemRenderer.doBlockTransformations();
            }
            case "Exhibition" -> {
                GlStateManager.translate(0, 0.18F, 0);
                float equippedProgress = renderSwordUse.equippedProgress / 2.0f;

                itemRenderer.transformFirstPersonItem(equippedProgress, realSwingProgress);
                itemRenderer.doBlockTransformations();
            }
            case "Stab" -> {
                final float spin = MathHelper.sin(MathHelper.sqrt_float(realSwingProgress) * (float) Math.PI);

                GlStateManager.translate(0.6f, 0.3f, -0.6f + -spin * 0.7);
                GlStateManager.rotate(6090, 0.0f, 0.0f, 0.1f);
                GlStateManager.rotate(6085, 0.0f, 0.1f, 0.0f);
                GlStateManager.rotate(6110, 0.1f, 0.0f, 0.0f);
                itemRenderer.transformFirstPersonItem(0.0F, 0.0f);
                itemRenderer.doBlockTransformations();
            }
            case "Spin" -> {
                itemRenderer.transformFirstPersonItem(realSwingProgress, 0.0F);
                GlStateManager.translate(0, 0.2F, -1);
                GlStateManager.rotate(-59, -1, 0, 3);
                // Don't make the /2 a float it causes the animation to break
                GlStateManager.rotate(-(System.currentTimeMillis() / 2 % 360), 1, 0, 0.0F);
                GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
            }

            case "Komorebi" -> {
                itemRenderer.transformFirstPersonItem(-0.25F, 1.0F + convertedProgress / 10.0F);
                GL11.glRotated(-convertedProgress * 25.0F, 1.0F, 0.0F, 0.0F);
                itemRenderer.doBlockTransformations();
            }

            case "Rhys" -> {
                GlStateManager.translate(0.41F, -0.25F, -0.5555557F);
                GlStateManager.translate(0.0F, 0, 0.0F);
                GlStateManager.rotate(35.0F, 0f, 1.5F, 0.0F);

                final float racism = MathHelper.sin(realSwingProgress * realSwingProgress / 64 * (float) Math.PI);

                GlStateManager.rotate(racism * -5.0F, 0.0F, 0.0F, 0.0F);
                GlStateManager.rotate(convertedProgress * -12.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(convertedProgress * -65.0F, 1.0F, 0.0F, 0.0F);
                itemRenderer.doBlockTransformations();
            }
        }
    });

    @Subscribe
    private final Listener<EventRenderItemUse> onItemUse = new Listener<>(renderItemUse -> renderItemUse.spoof = spoof.value && mc.thePlayer.isSwingInProgress && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword);

}
