/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.utils.CustomFramebuffer;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.KawaseBlur;
import mpp.venusfr.utils.shader.impl.Outline;
import net.minecraft.client.settings.PointOfView;
import org.lwjgl.opengl.GL11;

@FunctionRegister(name="Glass Hand", type=Category.Visual)
public class GlassHand
extends Function {
    public CustomFramebuffer hands = new CustomFramebuffer(false).setLinear();
    public CustomFramebuffer mask = new CustomFramebuffer(false).setLinear();

    @Subscribe
    public void onRender(EventDisplay eventDisplay) {
        if (eventDisplay.getType() != EventDisplay.Type.HIGH) {
            return;
        }
        if (GlassHand.mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
            KawaseBlur.blur.updateBlur(3.0f, 4);
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.enableAlphaTest();
            ColorUtils.setColor(ColorUtils.getColor(0));
            KawaseBlur.blur.render(this::lambda$onRender$0);
            Outline.registerRenderCall(this::lambda$onRender$1);
            GlStateManager.disableAlphaTest();
            GlStateManager.popMatrix();
        }
    }

    public static void setSaturation(float f) {
        float[] fArray = new float[]{0.3086f * (1.0f - f) + f, 0.6094f * (1.0f - f), 0.082f * (1.0f - f), 0.0f, 0.0f, 0.3086f * (1.0f - f), 0.6094f * (1.0f - f) + f, 0.082f * (1.0f - f), 0.0f, 0.0f, 0.3086f * (1.0f - f), 0.6094f * (1.0f - f), 0.082f * (1.0f - f) + f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
        GL11.glLoadMatrixf(fArray);
    }

    private void lambda$onRender$1() {
        this.hands.draw();
    }

    private void lambda$onRender$0() {
        this.hands.draw();
    }
}

