package arsenic.injection.accessor;

import arsenic.utils.java.PlayerInfo;
import jdk.nashorn.internal.objects.annotations.Setter;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityPlayerSP.class)
public interface IMixinEntityPlayerSP {

    @Accessor
    float getLastReportedYaw();

    @Accessor
    float getLastReportedPitch();

    @Accessor
    boolean getServerSprintState();

    @Accessor
    void setLastReportedYaw(float lastReportedYaw);

    @Accessor
    void setLastReportedPitch(float lastReportedPitch);

    @Accessor
    void setServerSprintState(boolean serverSprintState);
}
