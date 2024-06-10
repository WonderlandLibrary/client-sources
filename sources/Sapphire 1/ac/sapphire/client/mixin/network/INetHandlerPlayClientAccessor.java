package ac.sapphire.client.mixin.network;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.UUID;

@Mixin(NetHandlerPlayClient.class)
public interface INetHandlerPlayClientAccessor {
    @Accessor(value = "playerInfoMap")
    Map<UUID, NetworkPlayerInfo> playerInfoMap();
}
