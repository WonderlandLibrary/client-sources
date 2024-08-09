package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.other.WorldChangeEvent;
import dev.excellent.api.event.impl.other.WorldLoadEvent;
import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.render.RectUtil;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SMapDataPacket;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import net.optifine.util.TextureUtils;

import java.awt.image.BufferedImage;

@ModuleInfo(name = "Frame Test", description = "dev", category = Category.MISC)
public class FrameTest extends Module {
//    private final List<DynamicTexture> mapEntities = new CopyOnWriteArrayList<>();

    @Override
    protected void onEnable() {
        super.onEnable();
//        mapEntities.clear();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        clearMap();
    }

    private void clearMap() {
//        mapEntities.clear();
    }

    private final Listener<WorldChangeEvent> onWorldChange = event -> clearMap();
    private final Listener<WorldLoadEvent> onWorldLoad = event -> clearMap();
    private final Listener<Render2DEvent> onRender = this::handleRender;
    private final Listener<PacketEvent> onPacket = this::handlePacket;

    private void handleRender(Render2DEvent event) {
//        ChatUtil.addText(mapEntities.size());
//        int offsetX = 0;
//        int mult = 0;
//        for (DynamicTexture map : mapEntities) {
//            renderMapImage(event.getMatrix(), map.getGlTextureId(), 50 + offsetX, 50 + (105 * mult % 2), 100, 100);
//            offsetX += 105;
//            mult++;
//        }
    }

    private void handlePacket(PacketEvent event) {
        IPacket<?> packet = event.getPacket();
        if (packet instanceof SMapDataPacket wrapper) {
//            BufferedImage mapImage = generateMapImage(wrapper);
//            DynamicTexture dynamicTexture = new DynamicTexture(TextureUtils.toNativeImage(mapImage));
//            mapEntities.add(dynamicTexture);
//            try {
//                ImageIO.write(mapImage, "png", new File("map_" + RandomStringUtils.randomAlphabetic(12) + ".png"));
//                ChatUtil.addText("Изображение успешно сохранено.");
//            } catch (IOException e) {
//                ChatUtil.addText("Не удалось сохранить");
//            }
        }
    }

    public static void renderMapImage(MatrixStack matrix, int textureID, int x, int y, int width, int height) {
        if (textureID == -1) return;
        GlStateManager.bindTexture(textureID);
        RectUtil.drawRect(matrix, x, y, x + width, y + height, -1, -1, -1, -1, false, true);
    }

    public static BufferedImage generateMapImage(SMapDataPacket mapPacket) {
        int width = mapPacket.getColumns();
        int height = mapPacket.getRows();
        byte[] mapDataBytes = mapPacket.getMapDataBytes();
        byte[] colorIndices = new byte[width * height];

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int pixelX = 0; pixelX < width; ++pixelX) {
            for (int pixelY = 0; pixelY < height; ++pixelY) {
                int dataIndex = mapPacket.getMinX() + pixelX + (mapPacket.getMinZ() + pixelY) * width;
                colorIndices[dataIndex] = mapDataBytes[pixelX + pixelY * width];
                int colorIndex = colorIndices[dataIndex] & 255;
                int pixelColor = MaterialColor.COLORS[colorIndex / 4].colorValue;
                image.setRGB(pixelX, pixelY, pixelColor);
            }
        }
        return image;
    }

    public static int loadTexture(BufferedImage image) {
        DynamicTexture texture = new DynamicTexture(TextureUtils.toNativeImage(image));
        int textureId;
        try {
            textureId = texture.getGlTextureId();
        } catch (Exception ignored) {
            textureId = -1;
        }
        return textureId;
    }
}
