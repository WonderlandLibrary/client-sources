package studio.dreamys.module.dungeons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec4b;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import studio.dreamys.module.Category;
import studio.dreamys.module.Module;
import studio.dreamys.near;
import studio.dreamys.setting.Setting;

public class Map extends Module {
    public Map() {
        super("Map", Category.DUNGEONS);

        set(new Setting("Scale", this,1, 0.1, 3, false));
        set(new Setting("Background", this,false));
        set(new Setting("Players", this,true));
    }

    static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
    static MapData mapData;

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event){
        if(event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;
        renderOverlay();
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        mapData = null;
    }

    public void renderOverlay() {
        double x = near.settingsManager.getSettingByName(this, "Background").getValBoolean() ? 10: 0;
        double y = near.settingsManager.getSettingByName(this, "Background").getValBoolean() ? 10 :0;
        float scale = (float) near.settingsManager.getSettingByName(this, "Scale").getValDouble();
        ItemStack[] items = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;
        for (ItemStack item : items) {
            if (item != null) {
                if (item.getItem().isMap()) {
                    if (item.getItem() instanceof ItemMap) {
                        ItemMap mapItem = (ItemMap) item.getItem();
                        mapData = mapItem.getMapData(item, Minecraft.getMinecraft().thePlayer.getEntityWorld());
                    }
                }
            }
        }

        if (mapData == null) {
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0.0);
        GlStateManager.scale(scale, scale, 1);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if (near.settingsManager.getSettingByName(this, "Background").getValBoolean()) {
            drawMapBackground();
        }

        Minecraft.getMinecraft().entityRenderer.getMapItemRenderer().renderMap(mapData, true);

        if (near.settingsManager.getSettingByName(this, "Players").getValBoolean()) {
            drawPlayersOnMap();
        }

        GlStateManager.popMatrix();
    }

    // source: net.minecraft.client.gui/mapItemRenderer
    public static void drawPlayersOnMap() {
        int i = 0;
        int j = 0;
        int k = 0;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        float z = 1.0F;

        for (Vec4b vec4b : mapData.mapDecorations.values()) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0, 0, z);
            GlStateManager.translate(i + vec4b.func_176112_b() / 2.0 + 64.0, j + vec4b.func_176113_c() / 2.0 + 64.0,
                    -0.02);
            GlStateManager.rotate((vec4b.func_176111_d() * 360F) / 16.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.scale(4.0, 4.0, 1);
            GlStateManager.translate(-0.125, 0.125, 0.0);
            double b0 = vec4b.func_176110_a();
            double f1 = (b0 % 4) / 4.0;
            double f2 = (Math.floor(b0 / 4)) / 4.0;
            double f3 = (b0 % 4 + 1) / 4.0;
            double f4 = (Math.floor(b0 / 4) + 1) / 4.0;
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos(-1.0D, 1.0D, (float) k * -0.001F).tex(f1, f2).endVertex();
            worldrenderer.pos(1.0D, 1.0D, (float) k * -0.001F).tex(f3, f2).endVertex();
            worldrenderer.pos(1.0D, -1.0D, (float) k * -0.001F).tex(f3, f4).endVertex();
            worldrenderer.pos(-1.0D, -1.0D, (float) k * -0.001F).tex(f1, f4).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
            k++;
            z++;
        }

        GlStateManager.pushMatrix();
        GlStateManager.scale(0.0F, 0.0F, -0.04F);
        GlStateManager.translate(1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    public static void drawMapBackground() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(RES_MAP_BACKGROUND);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GL11.glNormal3f(0.0F, 0.0F, -1.0F);
        GlStateManager.translate(0F, 0F, -1.0F);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(-7.0, 135.0, 0.0).tex(0.0, 1.0).endVertex();
        worldrenderer.pos(135.0, 135.0, 0.0).tex(1.0, 1.0).endVertex();
        worldrenderer.pos(135.0, -7.0, 0.0).tex(1.0, 0.0).endVertex();
        worldrenderer.pos(-7.0, -7.0, 0.0).tex(0.0, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
    }
}
