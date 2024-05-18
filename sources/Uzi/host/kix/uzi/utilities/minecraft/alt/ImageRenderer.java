package host.kix.uzi.utilities.minecraft.alt;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImageRenderer {

    public static final ImageRenderer GENERIC_RENDERER = new ImageRenderer("/");

    private String directory;

    private Map<String, ResourceLocation> resources;

    public ImageRenderer(String directory) {
        this.directory = directory;
        this.resources = new HashMap<>();
    }

    public ImageRenderer(ImageRenderer base, String sub) {
        this(base.directory + File.pathSeparator + sub);
    }

    public void renderImage(String fileName, double startX, double startY, double width, double height) {
        String fullFileName = fileName + ".png";
        ResourceLocation resourceLocation = this.resources.get(fullFileName);
        if (resourceLocation == null) {
            resourceLocation = new ResourceLocation(
                    String.format("textures/energetic/%s/%s", this.directory, fullFileName));
            this.resources.put(fullFileName, resourceLocation);
        }
        this.renderImage(resourceLocation, startX, startY, width, height);
    }

    public void renderImage(ResourceLocation resourceLocation, double startX, double startY, double width,
                            double height) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        double scaleX = (width / 256d);
        double scaleY = (height / 256d);
        if (scaleX <= 0 || scaleY <= 0) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslated(startX, startY, 0);
        GL11.glScaled(scaleX, scaleY, 1);
        Gui.instance.drawTexturedModalRect(0, 0, 0, 0, 256, 256);
        GL11.glScaled(1 / scaleX, 1 / scaleY, 1);
        GL11.glPopMatrix();
    }
}
