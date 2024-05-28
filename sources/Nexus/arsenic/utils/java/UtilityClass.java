package arsenic.utils.java;

import net.minecraft.client.Minecraft;

public abstract class UtilityClass {

    protected static final Minecraft mc = Minecraft.getMinecraft();

    protected UtilityClass() {
        throw new RuntimeException("Instantiation of Utility class " + this.getClass().getSimpleName());
    }

}
