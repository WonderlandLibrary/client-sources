package studio.dreamys.module.cosmetics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import studio.dreamys.module.Category;
import studio.dreamys.module.Module;
import studio.dreamys.near;
import studio.dreamys.setting.Setting;

import java.awt.*;

public class DragonWings extends Module {
    public DragonWings() {
        super("Dragon Wings", Category.COSMETICS);

        set(new Setting("Scale", this, 1, 0.1, 3, false));
        set(new Setting("Color", this, 1, 1, 255, true));
        set(new Setting("Chroma", this, false));
    }

    @Override
    public void onEnable() { //override because inner class
        MinecraftForge.EVENT_BUS.register(new RenderWings());
    }

    @Override
    public void onDisable() { //override because inner class
        MinecraftForge.EVENT_BUS.unregister(new RenderWings());
    }

    public static class RenderWings extends ModelBase {
        private final Minecraft mc;
        private final ResourceLocation location;
        private final ModelRenderer wing;
        private final ModelRenderer wingTip;
        private final boolean playerUsesFullHeight;

        public RenderWings() {
            mc = Minecraft.getMinecraft();
            location = new ResourceLocation(near.MODID, "wings.png");
            playerUsesFullHeight = Loader.isModLoaded("animations");

            // Set texture offsets.
            setTextureOffset("wing.bone", 0, 0);
            setTextureOffset("wing.skin", -10, 8);
            setTextureOffset("wingtip.bone", 0, 5);
            setTextureOffset("wingtip.skin", -10, 18);

            // Create wing model renderer.
            wing = new ModelRenderer(this, "wing");
            wing.setTextureSize(30, 30); // 300px / 10px
            wing.setRotationPoint(-2F, 0, 0);
            wing.addBox("bone", -10.0F, -1.0F, -1.0F, 10, 2, 2);
            wing.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);

            // Create wing tip model renderer.
            wingTip = new ModelRenderer(this, "wingtip");
            wingTip.setTextureSize(30, 30); // 300px / 10px
            wingTip.setRotationPoint(-10.0F, 0.0F, 0.0F);
            wingTip.addBox("bone", -10.0F, -0.5F, -0.5F, 10, 1, 1);
            wingTip.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);
            wing.addChild(wingTip); // Make the wingtip rotate around the wing.
        }

        public float[] getColors() {
            if (near.settingsManager.getSettingByName(near.moduleManager.getModule("Dragon Wings"), "Chroma").getValBoolean()) {
                Color color = Color.getHSBColor((System.currentTimeMillis() % 1000) / 1000F, 0.8F, 1F);
                return new float[]{color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F};
            }
            Color color = new Color((int) near.settingsManager.getSettingByName(near.moduleManager.getModule("Dragon Wings"), "Color").getValDouble());
            return new float[]{color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F};
        }

        @SubscribeEvent
        public void onRenderPlayer(RenderPlayerEvent.Post event) {
            EntityPlayer player = event.entityPlayer;

            if (near.moduleManager.getModule("Dragon Wings").isToggled() && player.equals(mc.thePlayer) && !player.isInvisible()) // Should render wings onto this player?
            {
                renderWings(player, event.partialRenderTick);
            }
        }

        private void renderWings(EntityPlayer player, float partialTicks) {
            double scale = near.settingsManager.getSettingByName(near.moduleManager.getModule("Dragon Wings"), "Scale").getValDouble();
            double rotate = interpolate(player.prevRenderYawOffset, player.renderYawOffset, partialTicks);

            GL11.glPushMatrix();
            GL11.glScaled(-scale, -scale, scale);
            GL11.glRotated(180 + rotate, 0, 1, 0); // Rotate the wings to be with the player.
            GL11.glTranslated(0, -(playerUsesFullHeight ? 1.45 : 1.25) / scale, 0); // Move wings correct amount up.
            GL11.glTranslated(0, 0, 0.2 / scale);

            if (player.isSneaking()) {
                GL11.glTranslated(0D, 0.125D / scale, 0D);
            }

            float[] colors = getColors();
            GL11.glColor3f(colors[0], colors[1], colors[2]);
            mc.getTextureManager().bindTexture(location);

            for (int j = 0; j < 2; ++j) {
                GL11.glEnable(GL11.GL_CULL_FACE);
                float f11 = (System.currentTimeMillis() % 1000) / 1000F * (float) Math.PI * 2.0F;
                wing.rotateAngleX = (float) Math.toRadians(-80F) - (float) Math.cos(f11) * 0.2F;
                wing.rotateAngleY = (float) Math.toRadians(20F) + (float) Math.sin(f11) * 0.4F;
                wing.rotateAngleZ = (float) Math.toRadians(20F);
                wingTip.rotateAngleZ = -((float) (Math.sin(f11 + 2.0F) + 0.5D)) * 0.75F;
                wing.render(0.0625F);
                GL11.glScalef(-1.0F, 1.0F, 1.0F);

                if (j == 0) {
                    GL11.glCullFace(1028);
                }
            }

            GL11.glCullFace(1029);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glColor3f(255F, 255F, 255F);
            GL11.glPopMatrix();
        }

        private float interpolate(float yaw1, float yaw2, float percent) {
            float f = (yaw1 + (yaw2 - yaw1) * percent) % 360;

            if (f < 0) {
                f += 360;
            }

            return f;
        }
    }
}
