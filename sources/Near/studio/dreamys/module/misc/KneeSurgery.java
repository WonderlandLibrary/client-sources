package studio.dreamys.module.misc;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import studio.dreamys.module.Category;
import studio.dreamys.module.Module;
import studio.dreamys.near;
import studio.dreamys.setting.Setting;

public class KneeSurgery extends Module {
    public KneeSurgery() {
        super("Knee Surgery", Category.MISC);
        set(new Setting("Height", this, 1.62, 0, 3, false));
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0)
            Minecraft.getMinecraft().thePlayer.eyeHeight = (float) near.settingsManager.getSettingByName(this, "Height").getValDouble();
        else
            Minecraft.getMinecraft().thePlayer.eyeHeight = Minecraft.getMinecraft().thePlayer.getDefaultEyeHeight();
    }


    @Override
    public void onDisable() {
        super.onDisable();
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.eyeHeight = Minecraft.getMinecraft().thePlayer.getDefaultEyeHeight();
    }
}
