package com.krazzzzymonkey.catalyst.module.modules.misc;

import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import com.krazzzzymonkey.catalyst.utils.BlockUtils;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.utils.PlayerControllerUtils;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import com.krazzzzymonkey.catalyst.module.Modules;

public class FastBreak extends Modules
{
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent tickEvent) {
        PlayerControllerUtils.setBlockHitDelay(0);
        super.onClientTick(tickEvent);
    }
    
    private static int lIIIIllllI(final float n, final float n2) {
        return fcmpl(n, n2);
    }
    
    @Override
    public void onLeftClickBlock(final PlayerInteractEvent.LeftClickBlock click) {
        final float hardness = PlayerControllerUtils.getCurBlockDamageMP() + BlockUtils.getHardness(click.getPos());
        if ((fcmpl(hardness, 1.0f)) >= 0) {
            return;
        }
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, click.getPos(), Wrapper.INSTANCE.mc().objectMouseOver.sideHit));
        super.onLeftClickBlock(click);
    }
    
    public FastBreak() {
        super("FastBreak", ModuleCategory.MISC);
    }
}
