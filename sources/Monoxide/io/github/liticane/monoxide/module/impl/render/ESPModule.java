package io.github.liticane.monoxide.module.impl.render;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.minecraft.render.Render2DEvent;
import io.github.liticane.monoxide.listener.event.minecraft.render.Render3DEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.interfaces.Methods;
import io.github.liticane.monoxide.util.math.interpolation.InterpolationUtil;
import io.github.liticane.monoxide.util.player.combat.FightUtil;
import io.github.liticane.monoxide.util.render.RenderUtil;
import io.github.liticane.monoxide.util.render.color.ColorUtil;

import java.awt.*;
import java.util.Calendar;

@ModuleData(name = "ESP", description = "Render little things around players", category = ModuleCategory.RENDER)
public class ESPModule extends Module {

    public ModeValue mode = new ModeValue("Mode", this, new String[]{"2D"});
    public ModeValue twoDMode = new ModeValue("2D Mode", this, new String[]{"Normal", "Corners"});
    public BooleanValue background = new BooleanValue("Background", this, false, new Supplier[]{() -> mode.is("2D") && twoDMode.is("Normal")});
    public BooleanValue border = new BooleanValue("Border", this, true, new Supplier[]{() -> mode.is("2D") && twoDMode.is("Normal")});
    public NumberValue<Integer> backgroundOpacity = new NumberValue<>("Background Opacity", this, 110, 0, 255, 0, new Supplier[]{() -> mode.is("2D") && twoDMode.is("Normal")});
    public NumberValue<Integer> width = new NumberValue<Integer>("Width", this, 2, 1, 5, 0, new Supplier[]{() -> mode.is("Fake 2D")});
    public BooleanValue rangeLimited = new BooleanValue("Range Limited", this, false);
    public BooleanValue renderHealth = new BooleanValue("Health", this, true, new Supplier[]{() -> mode.is("2D") && twoDMode.is("Corners")});
    public NumberValue<Float> range = new NumberValue<>("Range", this, 100f, 0f, 1000f, 0);
    public BooleanValue players = new BooleanValue("Players", this, true);
    public BooleanValue animals = new BooleanValue("Animals", this, true);
    public BooleanValue monsters = new BooleanValue("Monsters", this, true);
    public BooleanValue invisible = new BooleanValue("Invisibles", this, true);
    public BooleanValue color = new BooleanValue("Color", this, true);
    private ModeValue customColorMode = new ModeValue("Color Mode", this, new String[]{"Static", "Fade", "Gradient", "Rainbow", "Astolfo Sky"}, new Supplier[]{() -> color.getValue()});
    private NumberValue<Integer> red = new NumberValue<Integer>("Red", this, 255, 0, 255, 0, new Supplier[]{() -> color.getValue() && (customColorMode.is("Static") || customColorMode.is("Random") || customColorMode.is("Fade") || customColorMode.is("Gradient"))});
    private NumberValue<Integer> green = new NumberValue<Integer>("Green", this, 255, 0, 255, 0, new Supplier[]{() -> color.getValue() && (customColorMode.is("Static") || customColorMode.is("Random") || customColorMode.is("Fade") || customColorMode.is("Gradient"))});
    private NumberValue<Integer> blue = new NumberValue<Integer>("Blue", this, 255, 0, 255, 0, new Supplier[]{() -> color.getValue() && (customColorMode.is("Static") || customColorMode.is("Random") || customColorMode.is("Fade") || customColorMode.is("Gradient"))});
    private NumberValue<Integer> red2 = new NumberValue<Integer>("Second Red", this, 255, 0, 255, 0, new Supplier[]{() -> color.getValue() && customColorMode.is("Gradient")});
    private NumberValue<Integer> green2 = new NumberValue<Integer>("Second Green", this, 255, 0, 255, 0, new Supplier[]{() -> color.getValue() && customColorMode.is("Gradient")});
    private NumberValue<Integer> blue2 = new NumberValue<Integer>("Second Blue", this, 255, 0, 255, 0, new Supplier[]{() -> color.getValue() && customColorMode.is("Gradient")});
    private NumberValue<Float> darkFactor = new NumberValue<Float>("Dark Factor", this, 0.49F, 0F, 1F, 2, new Supplier[]{() -> color.getValue() && customColorMode.is("Fade")});

    // 2D
    private final Frustum frustum = new Frustum();

    @Override
    public String getSuffix() {
        return mode.getValue();
    }

    @Listen
    public void on2D(Render2DEvent render2DEvent) {
        float length = 0.5f;
        final int counter = 1;

        if (mode.getValue().equals("2D") && twoDMode.getValue().equals("Normal")) {
            frustum.setPosition(Methods.mc.getRenderManager().renderPosX, Methods.mc.getRenderManager().renderPosY, Methods.mc.getRenderManager().renderPosZ);
            for (final Entity entity : getWorld().loadedEntityList) {
                if (!frustum.isBoundingBoxInFrustum(entity.getEntityBoundingBox())) continue;
                if (entity == getPlayer() && Methods.mc.gameSettings.thirdPersonView == 0) continue;
                if (!FightUtil.isValidWithPlayer(entity, rangeLimited.getValue() ? range.getValue() : 1000000, invisible.getValue(), players.getValue(), animals.getValue(), monsters.getValue()))
                    continue;
                final double[] coords = new double[4];
                InterpolationUtil.convertBox(coords, entity);
                float x = (float) coords[0];
                float y = (float) coords[1];
                float width = (float) (coords[2] - coords[0]);
                float height = (float) (coords[3] - coords[1]);

                // Back
                if (this.background.getValue())
                    RenderUtil.drawRect(x, y, width, height, new Color(0, 0, 0, this.backgroundOpacity.getValue()).getRGB());

                // Border
                RenderUtil.drawBorderedRect(x, y, x + width, y + height, length, 0, getColor(counter), true);
                if (this.border.getValue()) {
                    RenderUtil.drawBorderedRect(x, y, x + width, y + height, length, 0, Color.black.getRGB(), false);
                    RenderUtil.drawBorderedRect(x + length, y + length, (x - length) + width, (y - length) + height, length, 0, Color.black.getRGB(), true);
                }
                GlStateManager.resetColor();
            }
        }
    }

    @Listen
    public void on3D(Render3DEvent render3DEvent) {
        if (mode.getValue().equals("2D") && twoDMode.getValue().equals("Corners")) {
            for (EntityPlayer player : getWorld().playerEntities) {
                if (player == getPlayer()) continue;

                draw2DESP(player);

                if (renderHealth.getValue()) {
                    drawEntityHealthBar(player);
                }
            }
        }
    }

    final Calendar calendar = Calendar.getInstance();

    private int getColor(int counter) {
        int color = 0;
        switch (this.customColorMode.getValue()) {
            case "Static":
                color = new Color(red.getValue(), green.getValue(), blue.getValue(), 255).getRGB();
                break;
            case "Fade": {
                int firstColor = new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB();
                color = ColorUtil.fadeBetween(firstColor, ColorUtil.darken(firstColor, darkFactor.getValue()), counter * 150L);
                break;
            }
            case "Gradient": {
                int firstColor = new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB();
                int secondColor = new Color(red2.getValue(), green2.getValue(), blue2.getValue()).getRGB();
                color = ColorUtil.fadeBetween(firstColor, secondColor, counter * 150L);
                break;
            }
            case "Rainbow":
                color = ColorUtil.getRainbow(3000, (int) (counter * 150L));
                break;
            case "Astolfo Sky":
                color = ColorUtil.blendRainbowColours(counter * 150L);
                break;
        }
        return color;
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    public static void draw2DESP(EntityPlayer entity) {
        //Draw a 2D square around the entity
        GlStateManager.pushMatrix();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        //GlStateManager.glLineWidth(1.5F);
        GL11.glLineWidth(1.5F);
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks - renderManager.renderPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks - renderManager.renderPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks - renderManager.renderPosZ;
        GlStateManager.translate(x, y, z);

        //Rotate to match the player facing
        GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);

        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        //Top left corner
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(0.5, 2, 0).endVertex();
        worldRenderer.pos(0.5, 1.8, 0).endVertex();
        worldRenderer.pos(0.5, 2, 0).endVertex();
        worldRenderer.pos(0.3, 2, 0).endVertex();
        tessellator.draw();

        //Top right corner
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(-0.5, 2, 0).endVertex();
        worldRenderer.pos(-0.5, 1.8, 0).endVertex();
        worldRenderer.pos(-0.5, 2, 0).endVertex();
        worldRenderer.pos(-0.3, 2, 0).endVertex();
        tessellator.draw();

        //Bottom left corner
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(0.5, 0, 0).endVertex();
        worldRenderer.pos(0.5, 0.2, 0).endVertex();
        worldRenderer.pos(0.5, 0, 0).endVertex();
        worldRenderer.pos(0.3, 0, 0).endVertex();
        tessellator.draw();

        //Bottom right corner
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(-0.5, 0, 0).endVertex();
        worldRenderer.pos(-0.5, 0.2, 0).endVertex();
        worldRenderer.pos(-0.5, 0, 0).endVertex();
        worldRenderer.pos(-0.3, 0, 0).endVertex();
        tessellator.draw();

        GlStateManager.resetColor();
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }

    public static void drawEntityHealthBar(EntityPlayer p) {
        //Draw a floating health bar beside the entity
        GlStateManager.pushMatrix();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        //GlStateManager.glLineWidth(1.5F);
        GL11.glLineWidth(1.5F);
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        double x = p.lastTickPosX + (p.posX - p.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks - renderManager.renderPosX;
        double y = p.lastTickPosY + (p.posY - p.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks - renderManager.renderPosY;
        double z = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks - renderManager.renderPosZ;
        GlStateManager.translate(x, y, z);

        //Rotate to match the player facing
        GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);

        //Draw the health bar
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        //Draw a filled rectangle for the background
        //For some reason, this renders a triangle instead of a rectangle so we have to draw 2
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(-0.6, 1.8, 0).endVertex();
        worldRenderer.pos(-0.7, 1.8, 0).endVertex();
        worldRenderer.pos(-0.6, 0.2, 0).endVertex();
        worldRenderer.pos(-0.7, 0.2, 0).endVertex();
        tessellator.draw();

        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(-0.7, 1.8, 0).endVertex();
        worldRenderer.pos(-0.6, 1.8, 0).endVertex();
        worldRenderer.pos(-0.7, 0.2, 0).endVertex();
        worldRenderer.pos(-0.6, 0.2, 0).endVertex();
        tessellator.draw();

        //Calculate the health bar height
        double health = p.getHealth();
        double maxHealth = p.getMaxHealth();
        double healthPercentage = health / maxHealth;
        double healthBarHeight = 1.6 * healthPercentage;

        //Calculate the color of the health bar
        //the color is green when the health is above 80%, yellow when between 80% and 50%, orange when between 50% and 20%, and red when below 20%
        double r = 0;
        double g = 0;
        double b = 0;
        double a = 0;

        if (healthPercentage > 0.8) {
            r = 0;
            g = 1;
            b = 0;
            a = 1;
        } else if (healthPercentage > 0.5) {
            r = 1;
            g = 1;
            b = 0;
            a = 1;
        } else if (healthPercentage > 0.2) {
            r = 1;
            g = 0.5;
            b = 0;
            a = 1;
        } else {
            r = 1;
            g = 0;
            b = 0;
            a = 1;
        }

        //Draw the health bar
        GlStateManager.color((float) r, (float) g, (float) b, (float) a);
        //Draw a filled rectangle for the health bar
        //For some reason, this renders a triangle instead of a rectangle so we have to draw 2
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(-0.6, 0.2 + healthBarHeight, 0).endVertex();
        worldRenderer.pos(-0.7, 0.2 + healthBarHeight, 0).endVertex();
        worldRenderer.pos(-0.6, 0.2, 0).endVertex();
        worldRenderer.pos(-0.7, 0.2, 0).endVertex();
        tessellator.draw();

        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(-0.7, 0.2 + healthBarHeight, 0).endVertex();
        worldRenderer.pos(-0.6, 0.2 + healthBarHeight, 0).endVertex();
        worldRenderer.pos(-0.7, 0.2, 0).endVertex();
        worldRenderer.pos(-0.6, 0.2, 0).endVertex();
        tessellator.draw();

        //Draw the outline
        GlStateManager.color(GL11.GL_LINES, 1, 1, 1);
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(-0.6, 1.8, 0).endVertex();
        worldRenderer.pos(-0.7, 1.8, 0).endVertex();
        tessellator.draw();

        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(-0.6, 0.2, 0).endVertex();
        worldRenderer.pos(-0.7, 0.2, 0).endVertex();
        tessellator.draw();

        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(-0.6, 1.8, 0).endVertex();
        worldRenderer.pos(-0.6, 0.2, 0).endVertex();
        tessellator.draw();

        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(-0.7, 1.8, 0).endVertex();
        worldRenderer.pos(-0.7, 0.2, 0).endVertex();
        tessellator.draw();

        GlStateManager.resetColor();
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }

}
