package fr.dog.module.impl.render;

import fr.dog.Dog;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.render.Render3DEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.module.impl.player.BreakerModule;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.util.render.animation.Animation;
import fr.dog.util.render.animation.Easing;
import net.minecraft.block.BlockAir;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.BlockPos;


public class BreakProgress extends Module {

    private final BooleanProperty animationSetting = BooleanProperty.newInstance("Animation", true);
    private final Animation popIn = new Animation(Easing.EASE_IN_OUT_QUAD, 250);

    public BreakProgress() {
        super("BreakProgress", ModuleCategory.RENDER);
        this.registerProperty(animationSetting);
    }

    //Just inline everything, BLOATED CODE !
    @SubscribeEvent
    private void onRender2D(Render3DEvent event) {
        final double progress = animationSetting.getValue() ? popIn.getValue() : 1;

        BreakerModule module = Dog.getInstance().getModuleManager().getModule(BreakerModule.class);

        BlockPos blockPos;

        if (module.isEnabled())
            blockPos = module.breakPos;
        else
            blockPos = mc.objectMouseOver.getBlockPos();



        boolean isBreaking = mc.playerController.curBlockDamageMP != 0 || (module.isEnabled() && module.blockDamage != 0) && blockPos != null;

        if(mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockAir){
            isBreaking = false;
        }


        popIn.run(isBreaking ? 1.0F : 0.0F);

        if (blockPos == null)
            return;

        final double x = blockPos.getX() + 0.5 - RenderManager.viewerPosX;
        final double y = blockPos.getY() + 0.5 - RenderManager.viewerPosY;
        final double z = blockPos.getZ() + 0.5 - RenderManager.viewerPosZ;

        int damagePercentage = (int) ((mc.playerController.curBlockDamageMP != 0 ? mc.playerController.curBlockDamageMP : module.blockDamage) * 100);

        //good to not use a func to do the same, tho ew, it takes so much space !
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-0.022f * progress, -0.022f * progress, -0.022f * progress);
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        mc.fontRendererObj.drawString(damagePercentage + "%", (float) (-mc.fontRendererObj.getStringWidth(damagePercentage + "%") / 2), -3.0f, -1, true);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }
}