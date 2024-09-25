/*
 * Decompiled with CFR 0.150.
 */
package viamcp.platform;

import de.gerrygames.viarewind.api.ViaRewindConfig;
import de.gerrygames.viarewind.api.ViaRewindPlatform;
import java.util.logging.Logger;
import us.myles.ViaVersion.api.Via;

public class ViaRewindPlatformImplementation
implements ViaRewindPlatform {
    public ViaRewindPlatformImplementation() {
        this.init(new ViaRewindConfig(){

            @Override
            public ViaRewindConfig.CooldownIndicator getCooldownIndicator() {
                return ViaRewindConfig.CooldownIndicator.TITLE;
            }

            @Override
            public boolean isReplaceAdventureMode() {
                return true;
            }

            @Override
            public boolean isReplaceParticles() {
                return true;
            }
        });
    }

    @Override
    public Logger getLogger() {
        return Via.getPlatform().getLogger();
    }
}

