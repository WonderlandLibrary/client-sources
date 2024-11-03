package net.silentclient.client.mixin.mixins;

import net.minecraft.world.storage.WorldInfo;
import net.silentclient.client.Client;
import net.silentclient.client.mods.world.TimeChangerMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WorldInfo.class)
public class WorldInfoMixin {
    @Shadow
    private long worldTime;

    /**
     * @author kirillsaint
     * @reason Time Changer Mod
     */
    @Overwrite
    public void setWorldTime(long time) {
        boolean toggle = Client.getInstance().getModInstances().getModByClass(TimeChangerMod.class).isToggled();
        long customTime = (long) Client.getInstance().getSettingsManager().getSettingByClass(TimeChangerMod.class, "Time").getValDouble();
        this.worldTime = toggle ? customTime : time;
    }
}
