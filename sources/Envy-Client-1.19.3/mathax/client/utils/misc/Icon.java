package mathax.client.utils.misc;

import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.Objects;

import static mathax.client.MatHax.mc;

public class Icon {
    public static boolean iconChanged = false;

    public static void setIcon(Identifier icon1, Identifier icon2) {
        mc.getWindow().setIcon(
            () -> Objects.requireNonNull(Icon.class.getResourceAsStream("/assets/" + icon1.getNamespace() +"/" + icon1.getPath())),
            () -> Objects.requireNonNull(Icon.class.getResourceAsStream("/assets/" + icon2.getNamespace() +"/" + icon2.getPath()))
        );
        iconChanged = true;
    }

    @SuppressWarnings("unused")
    public static void setMinecraft() {
        mc.getWindow().setIcon(MinecraftClient.getInstance().getDefaultResourcePack().open(ResourceType.CLIENT_RESOURCES, new Identifier("icons/icon_16x16.png")), MinecraftClient.getInstance().getDefaultResourcePack().open(ResourceType.CLIENT_RESOURCES, new Identifier("icons/icon_32x32.png")));
        iconChanged = false;
    }
}

