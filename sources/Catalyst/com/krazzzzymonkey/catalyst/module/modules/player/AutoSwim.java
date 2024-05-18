package com.krazzzzymonkey.catalyst.module.modules.player;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import net.minecraft.client.entity.EntityPlayerSP;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import com.krazzzzymonkey.catalyst.value.Value;
import com.krazzzzymonkey.catalyst.value.Mode;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.krazzzzymonkey.catalyst.value.ModeValue;
import com.krazzzzymonkey.catalyst.module.Modules;

public class AutoSwim extends Modules
{
    public ModeValue mode;
    
    
    public AutoSwim() {
        super("AutoSwim", ModuleCategory.PLAYER);
        final String modes = "Mode";
        final Mode[] modes = new Mode[3];
        modes[0] = new Mode("Jump", (boolean)(1 != 0));
        modes[1] = new Mode("Dolphin", (boolean)(0 != 0));
        modes[2] = new Mode("Fish", (boolean)(0 != 0));
        this.mode = new ModeValue(modes, modes);
        final Value[] value = new Value[1];
        value[0] = this.mode;
        this.addValue(value);
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent tickEvent) {
        if (!(Wrapper.INSTANCE.player().isInWater()) && !(Wrapper.INSTANCE.player().isInLava())) {
            return;
        }
        if (Wrapper.INSTANCE.player().isSneaking() || Wrapper.INSTANCE.mcSettings().keyBindJump.isKeyDown()) {
            return;
        }
        if (this.mode.getMode("Jump").isToggled()) {
            Wrapper.INSTANCE.player().jump();
        }
        else if (this.mode.getMode("Dolphin").isToggled()) {
            final EntityPlayerSP player = Wrapper.INSTANCE.player();
            player.motionY += 0.03999999910593033;
        }
        else if (this.mode.getMode("Fish").isToggled()) {
            final EntityPlayerSP player2 = Wrapper.INSTANCE.player();
            player2.motionY += 0.019999999552965164;
        }
        super.onClientTick(tickEvent);
    }

}
