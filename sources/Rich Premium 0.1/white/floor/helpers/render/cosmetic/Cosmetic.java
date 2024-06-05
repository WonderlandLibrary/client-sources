package white.floor.helpers.render.cosmetic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import white.floor.helpers.render.cosmetic.impl.DragonWing;

public class Cosmetic {
    public static void renderAccessory(final String[] accessorys, final EntityPlayer player, final float partialTicks) {
        for (String accessory : accessorys) {
            switch (accessory) {
                case "Dragon_wing":
                    DragonWing.render(player, partialTicks);
                   break;
            }
        }
    }

    public static ResourceLocation getCape(String cape) {
        return new ResourceLocation("richclient/" + cape.toLowerCase() + ".png");
    }
    public static ResourceLocation getWing(String wing) {
        return new ResourceLocation("richclient/" + wing.toLowerCase() + ".png");
    }
}
