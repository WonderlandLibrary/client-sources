package com.krazzzzymonkey.catalyst.module.modules.player;

import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import net.minecraft.client.settings.KeyBinding;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.module.Modules;

public class AutoWalk extends Modules
{


    @Override
    public void onClientTick(final TickEvent.ClientTickEvent tickEvent) {
        KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindForward.getKeyCode(), true);
        super.onClientTick(tickEvent);
    }
    
    public AutoWalk() {
        super("AutoWalk", ModuleCategory.PLAYER);
    }
    
    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindForward.getKeyCode(), false);
        super.onDisable();
    }
}
