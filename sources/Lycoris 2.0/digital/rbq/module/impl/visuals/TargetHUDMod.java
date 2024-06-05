/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package digital.rbq.module.impl.visuals;

import java.awt.Color;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import digital.rbq.annotations.Label;
import digital.rbq.core.Autumn;
import digital.rbq.events.render.RenderGuiEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.impl.combat.AuraMod;
import digital.rbq.utils.ColorUtils;
import digital.rbq.utils.Stopwatch;
import digital.rbq.utils.render.AnimationUtils;
import digital.rbq.utils.render.RenderUtils;

@Label(value="TargetHUD")
@Category(value=ModuleCategory.VISUALS)
public final class TargetHUDMod
extends Module {
    private static final Color COLOR = new Color(0, 0, 0, 180);
    private final Stopwatch animationStopwatch = new Stopwatch();
    private EntityOtherPlayerMP target;
    private double healthBarWidth;
    private double hudHeight;

    @Listener(value=RenderGuiEvent.class)
    public final void onRenderGui(RenderGuiEvent event) {
        AuraMod aura = Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(AuraMod.class);
        float scaledWidth = event.getScaledResolution().getScaledWidth();
        float scaledHeight = event.getScaledResolution().getScaledHeight();
        if (aura.getTarget() != null && aura.isEnabled()) {
            if (aura.getTarget() instanceof EntityOtherPlayerMP) {
                this.target = (EntityOtherPlayerMP)aura.getTarget();
                float width = 140.0f;
                float height = 40.0f;
                float xOffset = 40.0f;
                float x = scaledWidth / 2.0f - 70.0f;
                float y = scaledHeight / 2.0f + 80.0f;
                float health = this.target.getHealth();
                double hpPercentage = health / this.target.getMaxHealth();
                hpPercentage = MathHelper.clamp_double(hpPercentage, 0.0, 1.0);
                double hpWidth = 92.0 * hpPercentage;
                int healthColor = ColorUtils.getHealthColor(this.target.getHealth(), this.target.getMaxHealth()).getRGB();
                String healthStr = String.valueOf((float)((int)this.target.getHealth()) / 2.0f);
                if (this.animationStopwatch.elapsed(15L)) {
                    this.healthBarWidth = AnimationUtils.animate(hpWidth, this.healthBarWidth, 0.353f);
                    this.hudHeight = AnimationUtils.animate(40.0, this.hudHeight, 0.1f);
                    this.animationStopwatch.reset();
                }
                GL11.glEnable((int)3089);
                RenderUtils.prepareScissorBox(x, y, x + 140.0f, (float)((double)y + this.hudHeight));
                Gui.drawRect(x, y, x + 140.0f, y + 40.0f, COLOR.getRGB());
                Gui.drawRect(x + 40.0f, y + 15.0f, (double)(x + 40.0f) + this.healthBarWidth, y + 25.0f, healthColor);
                TargetHUDMod.mc.fontRendererObj.drawStringWithShadow(healthStr, x + 40.0f + 46.0f - (float)TargetHUDMod.mc.fontRendererObj.getStringWidth(healthStr) / 2.0f, y + 16.0f, -1);
                TargetHUDMod.mc.fontRendererObj.drawStringWithShadow(this.target.getName(), x + 40.0f, y + 2.0f, -1);
                GuiInventory.drawEntityOnScreen((int)(x + 13.333333f), (int)(y + 40.0f), 20, this.target.rotationYaw, this.target.rotationPitch, this.target);
                GL11.glDisable((int)3089);
            }
        } else {
            this.healthBarWidth = 92.0;
            this.hudHeight = 0.0;
            this.target = null;
        }
    }
}

