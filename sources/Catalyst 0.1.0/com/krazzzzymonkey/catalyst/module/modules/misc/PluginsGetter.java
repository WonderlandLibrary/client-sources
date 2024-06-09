package com.krazzzzymonkey.catalyst.module.modules.misc;

import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import java.util.List;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import joptsimple.internal.Strings;
import java.util.Collections;
import java.util.ArrayList;
import net.minecraft.network.play.server.SPacketTabComplete;
import com.krazzzzymonkey.catalyst.utils.system.Connection;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketTabComplete;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.module.Modules;

public class PluginsGetter extends Modules
{
   
    
    @Override
    public void onEnable() {
        if (Wrapper.INSTANCE.player() == null) {
            return;
        }
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketTabComplete("/", (BlockPos)null, false));
        super.onEnable();
    }
    
    @Override
    public boolean onPacket(final Object obj, final Connection.Side connectionSide) {
        if (obj instanceof SPacketTabComplete) {
            final SPacketTabComplete spacketTabComplete = (SPacketTabComplete)obj;
            final List<String> list = new ArrayList<String>();
            final String[] matches = spacketTabComplete.getMatches();
            int i = 0;
            while (i < matches.length) {
                final String[] spit = matches[i].split(":");
                if (spit.length > 1) {
                    final String spit2 = spit[0].replace("/", "");
                    if (!(list.contains(spit2))) {
                        list.add(spit2);
                    }
                }
                ++i;
            }
            Collections.sort(list);
            if (!(list.isEmpty())) {
                ChatUtils.message(String.valueOf(new StringBuilder().append("Plugins §7(§8").append(list.size()).append("§7): §9").append(Strings.join((String[])list.toArray(new String[0]), "§7, §9"))));
            }
            else {
                ChatUtils.error("No plugins found.");
            }
            this.setToggled(false);
        }
        return true;
    }

    public PluginsGetter() {
        super("PluginsGetter", ModuleCategory.MISC);
    }

    
}
