package fun.ellant.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import fun.ellant.events.WorldEvent;
import fun.ellant.functions.impl.hud.HUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import fun.ellant.events.EventDisplay;
import fun.ellant.events.EventDisplay.Type;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.impl.combat.KillAura;
import fun.ellant.functions.settings.Setting;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.math.Vector4i;
import fun.ellant.utils.projections.ProjectionUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL11;


import static org.lwjgl.opengl.GL11C.GL_ONE;
import static org.lwjgl.opengl.GL11C.GL_SRC_ALPHA;


@FunctionRegister(name = "TargetESP", type = Category.RENDER, desc = "Отображает с киллаурой цель"
)
public class TargetESP extends Function {
    private final KillAura killAura;
    public ModeSetting mod = new ModeSetting("Мод", "Призраки", new String[]{"Client", "Wild", "Star", "Призраки", "Круг"});
    public SliderSetting speed = new SliderSetting("Скорость", 3.0F, 0.7F, 9.0F, 1.0F).setVisible(() -> mod.is("Призраки"));
    ;
    public SliderSetting size = new SliderSetting("Размер", 30.0F, 5.0F, 140.0F, 1.0F).setVisible(() -> mod.is("Призраки"));
    ;
    public SliderSetting bright = new SliderSetting("Яркость", 255.0F, 1.0F, 255.0F, 1.0F).setVisible(() -> mod.is("Призраки"));
    ;

    public TargetESP(KillAura killAura) {
        this.killAura = killAura;
        this.addSettings(new Setting[]{this.mod, this.speed, this.size, this.bright});
    }

    @Subscribe
    private void onDisplay(EventDisplay e) {
        if (this.mod.is("Призраки")) {
            if (e.getType() != Type.PRE) {
                return;
            }

            if (this.killAura.isState() && this.killAura.getTarget() != null) {
                float speedi = (Float)this.speed.get();
                float sizik = (Float)this.size.get();
                int yarkost = ((Float)this.bright.get()).intValue();
                double speed = (double)speedi;
                double time = (double)System.currentTimeMillis() / (500.0 / speed);
                double sin = Math.sin(time);
                double cos = Math.cos(time);
                float size = sizik;
                int brightness = yarkost;
                Vector3d headPos = this.killAura.getTarget().getPositon(e.getPartialTicks()).add(0.0, (double)this.killAura.getTarget().getHeight(), 0.0);
                Vector3d bodyPos = this.killAura.getTarget().getPositon(e.getPartialTicks()).add(0.0, (double)(this.killAura.getTarget().getHeight() / 2.0F), 0.0);
                Vector3d legPos = this.killAura.getTarget().getPositon(e.getPartialTicks());
                Vector3d[] upperPositions = new Vector3d[]{bodyPos.add(0.0, 0.5, 0.0)};
                Vector3d[] lowerPositions = new Vector3d[]{legPos.add(0.0, 0.5, 0.0)};
                ResourceLocation image = new ResourceLocation("expensive/images/hud/glow.png");

                for(int j = 0; j < 40; ++j) {
                    float alpha = (float)(brightness - j * 5);
                    if (alpha < 0.0F) {
                        alpha = 0.0F;
                    }

                    float trailSize = size * (1.0F - (float)j * 0.02F);
                    double trailTime = time - (double)j * 0.1;
                    double trailSin = Math.sin(trailTime);
                    double trailCos = Math.cos(trailTime);
                    float angleOffset = (float)j * 7.2F;

                    int i;
                    Vector3d pos3d;
                    Vector2f pos;
                    for(i = 0; i < upperPositions.length; ++i) {
                        pos3d = upperPositions[i].add(0.0, Math.sin(trailTime) * 0.26, 0.0);
                        pos = ProjectionUtil.project(pos3d.x + trailCos * 0.5, pos3d.y, pos3d.z + trailSin * 0.5);
                        GlStateManager.pushMatrix();
                        GlStateManager.translatef(pos.x, pos.y, 0.0F);
                        GlStateManager.rotatef((float)(trailSin * 360.0 + (double)(i * 180) + (double)angleOffset), 0.0F, 0.0F, 1.0F);
                        GlStateManager.translatef(-pos.x, -pos.y, 0.0F);
                        DisplayUtils.drawImage(image, pos.x - trailSize / 2.0F, pos.y - trailSize / 2.0F, trailSize, trailSize, new Vector4i(ColorUtils.setAlpha(HUD.getColor(0, 1.0F), (int)alpha), ColorUtils.setAlpha(HUD.getColor(90, 1.0F), (int)alpha), ColorUtils.setAlpha(HUD.getColor(180, 1.0F), (int)alpha), ColorUtils.setAlpha(HUD.getColor(270, 1.0F), (int)alpha)));
                        GlStateManager.popMatrix();
                    }

                    for(i = 0; i < lowerPositions.length; ++i) {
                        pos3d = lowerPositions[i].add(0.0, Math.sin(trailTime) * 0.26, 0.0);
                        pos = ProjectionUtil.project(pos3d.x - trailCos * 0.5, pos3d.y, pos3d.z - trailSin * 0.5);
                        GlStateManager.pushMatrix();
                        GlStateManager.translatef(pos.x, pos.y, 0.0F);
                        GlStateManager.rotatef((float)(-trailSin * 360.0 + (double)(i * 180) + (double)angleOffset), 0.0F, 0.0F, 1.0F);
                        GlStateManager.translatef(-pos.x, -pos.y, 0.0F);
                        DisplayUtils.drawImage(image, pos.x - trailSize / 2.0F, pos.y - trailSize / 2.0F, trailSize, trailSize, new Vector4i(ColorUtils.setAlpha(HUD.getColor(0, 1.0F), (int)alpha), ColorUtils.setAlpha(HUD.getColor(90, 1.0F), (int)alpha), ColorUtils.setAlpha(HUD.getColor(180, 1.0F), (int)alpha), ColorUtils.setAlpha(HUD.getColor(270, 1.0F), (int)alpha)));
                        GlStateManager.popMatrix();
                    }
                }
            }
        }
        
        if (this.mod.is("Star")) {
            if (e.getType() != Type.PRE) {
                return;
            }

            if (this.killAura.isState() && this.killAura.getTarget() != null) {
                double sin = Math.sin((double) System.currentTimeMillis() / 1000.0);
                float size = 70.0F;
                Vector3d interpolated = this.killAura.getTarget().getPositon(e.getPartialTicks());
                Vector2f pos = ProjectionUtil.project(interpolated.x, interpolated.y + (double) (this.killAura.getTarget().getHeight() / 2.0F), interpolated.z);
                GlStateManager.pushMatrix();
                GlStateManager.translatef(pos.x, pos.y, 0.0F);
                GlStateManager.rotatef((float) sin * 360.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.translatef(-pos.x, -pos.y, 0.0F);
                DisplayUtils.drawImage(new ResourceLocation("expensive/images/star.png"), pos.x - size / 2.0F, pos.y - size / 2.0F, size, size, new Vector4i(ColorUtils.rgb(255, 255, 255), ColorUtils.setAlpha(HUD.getColor(90, 1.0F), 220), ColorUtils.setAlpha(HUD.getColor(180, 1.0F), 220), ColorUtils.setAlpha(HUD.getColor(270, 1.0F), 220)));
                GlStateManager.popMatrix();
            }
        }
        if (this.mod.is("Wild")) {
            if (e.getType() != Type.PRE) {
                return;
            }

            if (this.killAura.isState() && this.killAura.getTarget() != null) {
                double sin = Math.sin((double) System.currentTimeMillis() / 1000.0);
                float size = 70.0F;
                Vector3d interpolated = this.killAura.getTarget().getPositon(e.getPartialTicks());
                Vector2f pos = ProjectionUtil.project(interpolated.x, interpolated.y + (double) (this.killAura.getTarget().getHeight() / 2.0F), interpolated.z);
                GlStateManager.pushMatrix();
                GlStateManager.translatef(pos.x, pos.y, 0.0F);
                GlStateManager.rotatef((float) sin * 360.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.translatef(-pos.x, -pos.y, 0.0F);
                DisplayUtils.drawImage(new ResourceLocation("expensive/images/target.png"), pos.x - size / 2.0F, pos.y - size / 2.0F, size, size, new Vector4i(ColorUtils.rgb(255, 255, 255), ColorUtils.setAlpha(HUD.getColor(90, 1.0F), 220), ColorUtils.setAlpha(HUD.getColor(180, 1.0F), 220), ColorUtils.setAlpha(HUD.getColor(270, 1.0F), 220)));
                GlStateManager.popMatrix();
            }
        }
        if (this.mod.is("Client")) {
            if (e.getType() != Type.PRE) {
                return;
            }

            if (this.killAura.isState() && this.killAura.getTarget() != null) {
                double sin = Math.sin((double) System.currentTimeMillis() / 1000.0);
                float size = 70.0F;
                Vector3d interpolated = this.killAura.getTarget().getPositon(e.getPartialTicks());
                Vector2f pos = ProjectionUtil.project(interpolated.x, interpolated.y + (double) (this.killAura.getTarget().getHeight() / 2.0F), interpolated.z);
                GlStateManager.pushMatrix();
                GlStateManager.translatef(pos.x, pos.y, 0.0F);
                GlStateManager.rotatef((float) sin * 360.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.translatef(-pos.x, -pos.y, 0.0F);
                DisplayUtils.drawImage(new ResourceLocation("expensive/images/target1.png"), pos.x - size / 2.0F, pos.y - size / 2.0F, size, size, new Vector4i(ColorUtils.rgb(255, 255, 255), ColorUtils.setAlpha(HUD.getColor(90, 1.0F), 220), ColorUtils.setAlpha(HUD.getColor(180, 1.0F), 220), ColorUtils.setAlpha(HUD.getColor(270, 1.0F), 220)));
                GlStateManager.popMatrix();
            }
        }
    }


}