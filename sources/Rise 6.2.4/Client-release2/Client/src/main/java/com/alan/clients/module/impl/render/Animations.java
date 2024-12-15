package com.alan.clients.module.impl.render;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.render.RenderItemEvent;
import com.alan.clients.event.impl.render.SwingAnimationEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.constants.ConstantsManager;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.NumberValue;
import com.alan.clients.value.impl.SubMode;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemMap;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
@ModuleInfo(aliases = {"module.render.animations.name"}, description = "module.render.animations.description", category = Category.RENDER)
public final class
Animations extends Module {

    private final ModeValue blockAnimation = new ModeValue("Block Animation", this)
            .add(new SubMode("None"))
            .add(new SubMode("1.7"))
            .add(new SubMode("Sunny"))
            .add(new SubMode("Lucid"))
            .add(new SubMode("Astro"))
            .add(new SubMode("Smooth"))
            .add(new SubMode("Spin"))
            .add(new SubMode("Leaked"))
            .add(new SubMode("Old"))
            .add(new SubMode("Exhibition"))
            .add(new SubMode("Exhibition Old"))
            .add(new SubMode("Exhibition New"))
            .add(new SubMode("Swong"))
            .add(new SubMode("Stella"))
            .add(new SubMode("Flup"))
            .add(new SubMode("Noov"))
            .add(new SubMode("Komorebi"))
            .add(new SubMode("Rhys"))
            .add(new SubMode("Swing"))
            .add(new SubMode("?"))
            .add(new SubMode("Stab"))
            .add(new SubMode("Beta"))
            .add(new SubMode("Dortware"))
            .add(new SubMode("Avatar"))
            .add(new SubMode("Tap"))
            //.add(new SUbmode("Slide"))
            .setDefault("None");

    public final ModeValue swingAnimation = new ModeValue("Swing Animation", this)
            .add(new SubMode("None"))
            .add(new SubMode("Punch"))
            .add(new SubMode("Shove"))
            .add(new SubMode("Smooth"))
            .add(new SubMode("1.9+"))
            .setDefault("None");

    private final BooleanValue onlyWhenBlocking = new BooleanValue("Update Position Only When Blocking", this, true);
    public final NumberValue swingSpeed = new NumberValue("Swing Speed", this, 1, -200, 50, 1);

    private final NumberValue x = new NumberValue("X", this, 0.0F, -2.0F, 2.0F, 0.05f);
    private final NumberValue y = new NumberValue("Y", this, 0.0F, -2.0F, 2.0F, 0.05f);
    private final NumberValue z = new NumberValue("Z", this, 0.0F, -2.0F, 2.0F, 0.05f);
    private final NumberValue scale = new NumberValue("Scale", this, 1, 0.1, 2, 0.1);
    private final BooleanValue alwaysShow = new BooleanValue("Always Show", this, false);

    @EventLink
    public final Listener<RenderItemEvent> onRenderItem = event -> {
        if (event.getItemToRender().getItem() instanceof ItemMap) {
            return;
        }

        if (!onlyWhenBlocking.getValue())

            GlStateManager.translate(x.getValue().floatValue(), y.getValue().floatValue(), z.getValue().floatValue());

        double var7 = 0;

        Number scaleValue = scale.getValue();
        double scaleDouble = scaleValue.doubleValue();
        var7 = scaleDouble;

        final EnumAction itemAction = event.getEnumAction();
        final ItemRenderer itemRenderer = mc.getItemRenderer();
        final float animationProgression = alwaysShow.getValue() && event.isUseItem() ? 0.0F : event.getAnimationProgression();
        final float swingProgress = event.getSwingProgress();
        final float convertedProgress = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) ConstantsManager.a);

        if (event.isUseItem() && itemAction == EnumAction.BLOCK) {

            if (onlyWhenBlocking.getValue())
                GlStateManager.translate(x.getValue().floatValue(), y.getValue().floatValue(), z.getValue().floatValue());

            switch (blockAnimation.getValue().getName()) {

                case "None": {
                    itemRenderer.transformFirstPersonItem(animationProgression, 0.0F);
                    GlStateManager.scale(var7, var7, var7);
                    itemRenderer.blockTransformation();

                    break;
                }

                case "1.7": {

                    itemRenderer.transformFirstPersonItem(animationProgression, swingProgress);
                    GlStateManager.scale(var7, var7, var7);
                    itemRenderer.blockTransformation();

                    break;
                }

                case "Sunny": {
                    var7 =.99;
                    GlStateManager.translate(.05f, -.05f, -.12f);
                    itemRenderer.transformFirstPersonItem(animationProgression + 0.15f, swingProgress);
                    GlStateManager.scale(var7, var7, var7);
                    itemRenderer.blockTransformation();

                    GlStateManager.translate(-0.5f, 0.2f, 0.0f);

                    break;
                }

                case "Lucid": {
                    itemRenderer.transformFirstPersonItem(animationProgression - 0.1F, swingProgress);
                    GlStateManager.scale(var7, var7, var7);
                    itemRenderer.blockTransformation();

                    break;
                }

                case "Astro": {
                    GlStateManager.translate(.0f, .03f, -.05f);
                    itemRenderer.transformFirstPersonItem(animationProgression / 2, swingProgress);
                    GlStateManager.scale(var7, var7, var7);
                    GlStateManager.rotate(convertedProgress * 30.0F / 2.0F, -convertedProgress, -0.0F, 9.0F);
                    GlStateManager.rotate(convertedProgress * 40.0F, 1.0F, -convertedProgress / 2.0F, -0.0F);
                    itemRenderer.blockTransformation();

                    break;
                }

                case "Tap":
                    GL11.glTranslatef(0, 0.3f, 0);
                    float smooth = (swingProgress * 0.8f - (swingProgress * swingProgress) * 0.8f);
                    GlStateManager.scale(var7, var7, var7);
                    GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
                    GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(smooth * -90.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.scale(0.37F, 0.37F, 0.37F);
                    itemRenderer.blockTransformation();
                    break;

                case "Beta":
                    GL11.glTranslatef(0, 0.3f, 0);
                    float var15 = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
                    itemRenderer.transformFirstPersonItem(itemRenderer.equippedProgress * 0.5f, 0);
                    GlStateManager.scale(var7, var7, var7);
                    GlStateManager.rotate(-var15 * 55 / 2.0F, -8.0F, -0.0F, 9.0F);
                    GlStateManager.rotate(-var15 * 45, 1.0F, var15 / 2, -0.0F);
                    itemRenderer.blockTransformation();
                    GL11.glTranslated(1.2, 0.3, 0.5);
                    GL11.glTranslatef(-1, this.mc.thePlayer.isSneaking() ? -0.1F : -0.2F, 0.2F);
                    break;

                case "Slide":
                    GL11.glTranslatef(0, 0.3f, 0);
                    float smooth2 = (swingProgress * 0.8f - (swingProgress * swingProgress) * 0.8f);
                    GlStateManager.scale(var7, var7, var7);
                    GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
                    GlStateManager.translate(0.0F, itemRenderer.equippedProgress * 0.3 * -0.6F, 0.0F);
                    GlStateManager.rotate(45.0F, 0.0F, 2 + smooth2 * 0.5f, smooth2 * 3);
                    GlStateManager.rotate(0f, 0.0F, 1.0F, 0.0F);
                    GlStateManager.scale(0.37F, 0.37F, 0.37F);
                    itemRenderer.blockTransformation();
                    break;

                case "Avatar":

                    GlStateManager.translate(0.56F, -0.52F, -0.71999997F);

                    GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                    float f = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
                    float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);

                    GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
                    GlStateManager.rotate(f1 * -40.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.scale(0.4F, 0.4F, 0.4F);

                    itemRenderer.blockTransformation();
                    break;

                case "Smooth": {
                    itemRenderer.transformFirstPersonItem(animationProgression, 0.0F);
                    GlStateManager.scale(var7, var7, var7);
                    final float y = -convertedProgress * 2.0F;
                    GlStateManager.translate(0.0F, y / 10.0F + 0.1F, 0.0F);
                    GlStateManager.rotate(y * 10.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(250, 0.2F, 1.0F, -0.6F);
                    GlStateManager.rotate(-10.0F, 1.0F, 0.5F, 1.0F);
                    GlStateManager.rotate(-y * 20.0F, 1.0F, 0.5F, 1.0F);

                    break;
                }

                case "Stab": {
                    final float spin = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) ConstantsManager.a);

                    GlStateManager.translate(0.6f, 0.3f, -0.6f + -spin * 0.7);
                    GlStateManager.rotate(6090, 0.0f, 0.0f, 0.1f);
                    GlStateManager.rotate(6085, 0.0f, 0.1f, 0.0f);
                    GlStateManager.rotate(6110, 0.1f, 0.0f, 0.0f);
                    itemRenderer.transformFirstPersonItem(0.0F, 0.0f);
                    GlStateManager.scale(var7, var7, var7);
                    itemRenderer.blockTransformation();
                    break;
                }

                case "Spin": {
                    itemRenderer.transformFirstPersonItem(animationProgression, 0.0F);
                    GlStateManager.scale(var7, var7, var7);
                    GlStateManager.translate(0, 0.2F, -1);
                    GlStateManager.rotate(-59, -1, 0, 3);
                    // Don't make the /2 a float it causes the animation to break
                    GlStateManager.rotate(-(System.currentTimeMillis() / 2 % 360), 1, 0, 0.0F);
                    GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
                    break;
                }

                case "Leaked": {
                    GlStateManager.translate(.0f, -.03f, -.13f);
                    itemRenderer.transformFirstPersonItem(animationProgression / 3F, 0.0F);
                    GlStateManager.scale(var7, var7, var7);
                    GlStateManager.translate(0.0f, 0.1F, 0.0F);
                    itemRenderer.blockTransformation();
                    GlStateManager.rotate(convertedProgress * 20.0F / 2.0F, 0.0F, 1.0F, 1.5F);
                    GlStateManager.rotate(-convertedProgress * 200.0F / 4.0F, 1.0f, 0.9F, 0.0F);

                    break;
                }

                case "Old": {

                    GlStateManager.translate(0.0F, 0.1F, 0.0F);
                    itemRenderer.transformFirstPersonItem(animationProgression / 2f - 0.2F, swingProgress);
                    GlStateManager.scale(var7, var7, var7);
                    itemRenderer.blockTransformation();

                    break;
                }

                case "Exhibition": {
                    GlStateManager.translate(.0f, -.05f, -0f);
                    itemRenderer.transformFirstPersonItem(animationProgression / 2.0F, 0.0F);
                    GlStateManager.scale(var7, var7, var7);
                    GlStateManager.translate(0.0F, 0.3F, -0.0F);
                    GlStateManager.rotate(-convertedProgress * 31.0F, 1.0F, 0.0F, 2.0F);
                    GlStateManager.rotate(-convertedProgress * 33.0F, 1.5F, (convertedProgress / 1.1F), 0.0F);
                    itemRenderer.blockTransformation();

                    break;
                }

                case "Exhibition Old": {
                    GlStateManager.translate(.0f, -.05f, 0f);
                    GlStateManager.translate(-0.04F, 0.13F, 0.0F);
                    itemRenderer.transformFirstPersonItem(animationProgression / 2.5F, 0.0F);
                    GlStateManager.scale(var7, var7, var7);
                    GlStateManager.rotate(-convertedProgress * 40.0F / 2.0F, convertedProgress / 2.0F, 1.0F, 4.0F);
                    GlStateManager.rotate(-convertedProgress * 30.0F, 1.0F, convertedProgress / 3.0F, -0.0F);
                    itemRenderer.blockTransformation();

                    break;
                }

                case "Exhibition New": {
                    GlStateManager.translate(.0f, -.04f, -.01f);
                    itemRenderer.transformFirstPersonItem(animationProgression / 2.0F, 0.0F);
                    GlStateManager.scale(var7, var7, var7);
                    GlStateManager.translate(0.0F, 0.3F, -0.0F);
                    GlStateManager.rotate(-convertedProgress * 30.0F, 1.0F, 0.0F, 2.0F);
                    GlStateManager.rotate(-convertedProgress * 44.0F, 1.5F, (convertedProgress / 1.2F), 0.0F);
                    itemRenderer.blockTransformation();

                    break;
                }

                case "Swong": {
                    GlStateManager.translate(.0f, .1f, -.05f);
                    itemRenderer.transformFirstPersonItem(animationProgression / 2.0F, swingProgress);
                    GlStateManager.scale(var7, var7, var7);
                    GlStateManager.rotate(convertedProgress * 30.0F, -convertedProgress, -0.0F, 9.0F);
                    GlStateManager.rotate(convertedProgress * 40.0F, 1.0F, -convertedProgress, -0.0F);
                    itemRenderer.blockTransformation();

                    break;
                }

                case "Stella": {
                    itemRenderer.transformFirstPersonItem(-0.1F, swingProgress);
                    GlStateManager.scale(var7, var7, var7);
                    GlStateManager.translate(-0.5F, 0.4F, -0.2F);
                    GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(-70.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(40.0F, 0.0F, 1.0F, 0.0F);
                    break;
                }

                case "Flup": {
                    GlStateManager.translate(.0f, .1f, -.05f);
                    itemRenderer.transformFirstPersonItem(animationProgression, 0.0F);
                    GlStateManager.scale(var7, var7, var7);
                    itemRenderer.blockTransformation();
                    GlStateManager.translate(-0.05F, 0.2F, 0.0F);
                    GlStateManager.rotate(-convertedProgress * 70.0F / 2.0F, -8.0F, -0.0F, 9.0F);
                    GlStateManager.rotate(-convertedProgress * 70.0F, 1.0F, -0.4F, -0.0F);

                    break;
                }

                case "Noov": {
                    itemRenderer.transformFirstPersonItem(animationProgression / 1.5F, 0.0F);
                    GlStateManager.scale(var7, var7, var7);
                    itemRenderer.blockTransformation();
                    GlStateManager.translate(-0.05F, 0.3F, 0.3F);
                    GlStateManager.rotate(-convertedProgress * 140.0F, 8.0F, 0.0F, 8.0F);
                    GlStateManager.rotate(convertedProgress * ConstantsManager.b, 8.0F, 0.0F, 8.0F);

                    break;
                }

                case "Komorebi": {
                    itemRenderer.transformFirstPersonItem(-0.25F, 1.0F + convertedProgress / 10.0F);
                    GlStateManager.scale(var7, var7, var7);
                    GL11.glRotated(-convertedProgress * 25.0F, 1.0F, 0.0F, 0.0F);
                    itemRenderer.blockTransformation();

                    break;
                }

                case "Rhys": {
                    GlStateManager.translate(0.41F, -0.25F, -0.5555557F);
                    GlStateManager.translate(0.0F, 0, 0.0F);
                    GlStateManager.rotate(35.0F, 0f, 1.5F, 0.0F);

                    final float racism = MathHelper.sin(swingProgress * swingProgress / 64 * (float) ConstantsManager.a);

                    GlStateManager.rotate(racism * -5.0F, 0.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(convertedProgress * -12.0F, 0.0F, 0.0F, 1.0F);
                    GlStateManager.rotate(convertedProgress * -65.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.scale(var7, var7, var7);
                    itemRenderer.blockTransformation();

                    break;
                }

                case "Swing": {
                    itemRenderer.transformFirstPersonItem(animationProgression, swingProgress);
                    GlStateManager.scale(var7, var7, var7);
                    itemRenderer.blockTransformation();
                    GlStateManager.translate(-0.3F, -0.1F, -0.0F);

                    break;
                }

                case "?": {
                    itemRenderer.transformFirstPersonItem(animationProgression, swingProgress);
                    GlStateManager.scale(var7, var7, var7);
                    GL11.glTranslatef(-0.35F, 0.1F, 0.0F);
                    GL11.glTranslatef(-0.05F, -0.1F, 0.1F);

                    itemRenderer.blockTransformation();

                    break;
                }

                case "Dortware": {
                    final float var1 = MathHelper.sin((float) (swingProgress * swingProgress * Math.PI - 3));
                    final float var = MathHelper.sin((float) (MathHelper.sqrt_float(swingProgress) * Math.PI));

                    itemRenderer.transformFirstPersonItem(animationProgression, 1.0f);

                    GlStateManager.rotate(-var * 10, 0.0f, 15.0f, 200.0f);
                    GlStateManager.rotate(-var * 10f, 300.0f, var / 2.0f, 1.0f);

                    itemRenderer.blockTransformation();

                    GL11.glTranslated(2.4, 0.3, 0.5);
                    GL11.glTranslatef(-2.10f, -0.2f, 0.1f);
                    GlStateManager.rotate(var1 * 13.0f, -10.0f, -1.4f, -10.0f);
                }
            }

            event.setCancelled();

        } else if (!event.isUseItem()) {

            switch (swingAnimation.getValue().getName()) {
                case "None":
                    itemRenderer.func_178105_d(swingProgress);
                    itemRenderer.transformFirstPersonItem(animationProgression, swingProgress);
                    if (!onlyWhenBlocking.getValue()) {
                        GlStateManager.scale(var7, var7, var7);
                    }
                    break;

                case "Punch": {
                    itemRenderer.transformFirstPersonItem(animationProgression, swingProgress);
                    itemRenderer.func_178105_d(swingProgress);
                    if (!onlyWhenBlocking.getValue()) {
                        GlStateManager.scale(var7, var7, var7);
                    }
                    break;
                }

                case "1.9+": {
                    itemRenderer.func_178105_d(swingProgress);
                    itemRenderer.transformFirstPersonItem(animationProgression, swingProgress);
//                    GlStateManager.translate(0, -((swing - 1) -
//                            (swing == 0 ? 0 : mc.timer.renderPartialTicks)) / 5f, 0);
                    if (!onlyWhenBlocking.getValue()) {
                        GlStateManager.scale(var7, var7, var7);
                    }
                    break;
                }

                case "Shove": {
                    itemRenderer.transformFirstPersonItem(animationProgression, animationProgression);
                    itemRenderer.func_178105_d(swingProgress);
                    if (!onlyWhenBlocking.getValue()) {
                        GlStateManager.scale(var7, var7, var7);
                    }
                    break;
                }

                case "Smooth": {
                    itemRenderer.transformFirstPersonItem(animationProgression, swingProgress);
                    itemRenderer.func_178105_d(animationProgression);
                    if (!onlyWhenBlocking.getValue()) {
                        GlStateManager.scale(var7, var7, var7);
                    }
                    break;
                }
            }

            event.setCancelled();
        }
    };

    @EventLink
    public final Listener<SwingAnimationEvent> onSwingAnimation = event -> {
        int swingAnimationEnd = event.getAnimationEnd();

        swingAnimationEnd *= (-swingSpeed.getValue().floatValue() / 100f) + 1f;

        event.setAnimationEnd(swingAnimationEnd);
    };
}

