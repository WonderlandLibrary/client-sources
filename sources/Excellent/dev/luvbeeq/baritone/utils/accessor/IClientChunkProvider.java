package dev.luvbeeq.baritone.utils.accessor;

import net.minecraft.client.multiplayer.ClientChunkProvider;

public interface IClientChunkProvider {
    ClientChunkProvider createThreadSafeCopy();

    IChunkArray extractReferenceArray();
}
