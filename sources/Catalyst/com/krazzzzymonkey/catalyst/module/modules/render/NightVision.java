package com.krazzzzymonkey.catalyst.module.modules.render;

import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import com.krazzzzymonkey.catalyst.module.Modules;

public class NightVision extends Modules
{
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        Wrapper.INSTANCE.mcSettings().gammaSetting = 5.0f;
        super.onClientTick(event);
    }
    
    public NightVision() {
        super("FullBright", ModuleCategory.RENDER);
    }
    
    @Override
    public void onDisable() {
        Wrapper.INSTANCE.mcSettings().gammaSetting = 1.0f;
        super.onDisable();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
}
