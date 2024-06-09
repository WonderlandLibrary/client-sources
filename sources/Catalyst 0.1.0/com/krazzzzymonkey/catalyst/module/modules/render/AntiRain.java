package com.krazzzzymonkey.catalyst.module.modules.render;

import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.module.Modules;

public class AntiRain extends Modules
{
    

    public AntiRain() {
        super("NoWeather", ModuleCategory.RENDER);
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        Wrapper.INSTANCE.world().setRainStrength(0.0f);
        super.onClientTick(event);
    }

}
