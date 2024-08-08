package lol.point.returnclient.module.impl.player;

import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.BooleanSetting;
import lol.point.returnclient.settings.impl.NumberSetting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;

@ModuleInfo(
        name = "Stealer",
        description = "Takes items from chest",
        category = Category.PLAYER
)

public class Stealer extends Module {

    private final NumberSetting minimumDelay = new NumberSetting("Minimum delay", 50, 10, 1000);
    private final NumberSetting maximumDelay = new NumberSetting("Maximum delay", 100, 10, 1000);



}
