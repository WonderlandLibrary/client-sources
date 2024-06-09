package com.client.glowclient.sponge.mixin;

import net.minecraft.network.play.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import com.client.glowclient.sponge.mixinutils.*;
import java.io.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ CPacketUpdateSign.class })
public abstract class MixinCPacketUpdateSign implements Packet<INetHandlerPlayServer>
{
    @Shadow
    private BlockPos field_179723_a;
    @Shadow
    private String[] field_149590_d;
    
    public MixinCPacketUpdateSign() {
        super();
    }
    
    @Overwrite
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.pos);
        for (int i = 0; i < 4; ++i) {
            if (HookTranslator.v4) {
                packetBuffer.writeString(this.lines[i].replace("&", "§§§cc"));
            }
            else {
                packetBuffer.writeString(this.lines[i]);
            }
        }
    }
}
