package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.render.Ambience;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientWorld.Properties.class)
public class ClientWorldPropertiesMixin {

    @Shadow
    private long timeOfDay;

    @Shadow
    private boolean raining;

    /**
     * @author scoliosis
     * @reason ambience
     */
    @Overwrite
    public long getTimeOfDay() {
        if (ModuleManager.isEnabled(Ambience.class)) {
            return Ambience.TOD;
        }
        return this.timeOfDay;
    }

    /**
     * @author scoliosis
     * @reason doesnt do anything on true or false, fun!
     */
    @Overwrite
    public boolean isThundering() {
        return false;
    }

    /**
     * @author scoliosis
     * @reason you can set this to true for raining, yayy
     */
    @Overwrite
    public boolean isRaining() {
        return this.raining;
    }
}
