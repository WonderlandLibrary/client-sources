package club.bluezenith.module.modules.render;

import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.util.client.ClientUtils;
import club.bluezenith.module.value.types.ActionValue;
import javafx.stage.FileChooser;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SuppressWarnings("unused")
public class CustomCape extends Module {
    private ResourceLocation d = null;
    private final ActionValue selectCape = new ActionValue("Select cape", () -> {
        File temp = ClientUtils.openFileChooser(null, new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg"));
        if(temp == null || !temp.exists()){
            ClientUtils.fancyMessage("The selected path doesn't exist.");
            d = null;
            return;
        }
        d = new ResourceLocation("lmao you can't get this resource location LLLLL");
        try {
            mc.getTextureManager().deleteTexture(d);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Files.readAllBytes(temp.toPath()));
            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
            byteArrayInputStream.close();
            mc.getTextureManager().loadTexture(d, new DynamicTexture(bufferedImage));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }).setIndex(1);
    public CustomCape() {
        super("Cape", ModuleCategory.RENDER, "customcape", "cape");
        this.displayName = "Cape";
    }
    public ResourceLocation getCapeLocation(){
        return d == null ? new ResourceLocation("club/bluezenith/cape.png") : d;
    }
}