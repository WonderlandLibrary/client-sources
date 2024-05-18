package host.kix.uzi.module.addons.theme;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class ObjectRenderer<T> {

    protected FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    private Class objectClass;
    protected Minecraft mc = Minecraft.getMinecraft();


    public ObjectRenderer(Class objectClass) {
        this.objectClass = objectClass;
    }


    public Class getObjectClass() {
        return objectClass;
    }


    protected void render(T object) {
    }
}
