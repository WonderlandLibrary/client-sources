package xyz.mathax.mathaxclient.mixin;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.handler.proxy.Socks4ProxyHandler;
import io.netty.handler.proxy.Socks5ProxyHandler;
import xyz.mathax.mathaxclient.systems.proxies.Proxies;
import xyz.mathax.mathaxclient.systems.proxies.Proxy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.InetSocketAddress;

@Mixin(targets = "net.minecraft.network.ClientConnection$1")
public class ClientConnectionInitChannelMixin {
    @Inject(method = "initChannel", at = @At("HEAD"))
    private void onInitChannel(Channel channel, CallbackInfo info) {
        Proxy proxy = Proxies.get().getEnabled();
        if (proxy == null) {
            return;
        }

        channel.pipeline().addFirst(getChannel(proxy));
    }

    private ChannelHandler getChannel(Proxy proxy) {
        return switch (proxy.typeSetting.get()) {
            case Socks4 -> new Socks4ProxyHandler(new InetSocketAddress(proxy.addressSetting.get(), proxy.portSetting.get()), proxy.usernameSetting.get());
            case Socks5 -> new Socks5ProxyHandler(new InetSocketAddress(proxy.addressSetting.get(), proxy.portSetting.get()), proxy.usernameSetting.get(), proxy.passwordSetting.get());
        };
    }
}
