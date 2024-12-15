package com.alan.clients.module.impl.other;

import com.alan.clients.component.impl.player.IRCInfoComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.ChatInputEvent;
import com.alan.clients.event.impl.other.BackendPacketEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.chat.ChatUtil;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.StringUtils;
import rip.vantage.commons.packet.impl.client.community.C2SPacketIRCMessage;
import rip.vantage.commons.packet.impl.server.community.S2CPacketIRCMessage;
import rip.vantage.commons.util.vantage.VantageProduct;
import rip.vantage.network.core.Network;

@ModuleInfo(aliases = {"module.other.irc.name"}, description = "module.other.irc.description", category = Category.RENDER, autoEnabled = true)
public final class IRC extends Module {

    @EventLink
    public final Listener<ChatInputEvent> onChat = event -> {
        String message = event.getMessage();

        if (message.startsWith("#") && message.length() > 1) {
            event.setCancelled();
            message = message.substring(1);
            message = StringUtils.normalizeSpace(message);
            Network.getInstance().getClient().sendMessage(new C2SPacketIRCMessage(message).export());
        }
    };

    @EventLink
    public final Listener<BackendPacketEvent> onBackend = event -> {
        if (event.getPacket() instanceof S2CPacketIRCMessage) {
            S2CPacketIRCMessage wrapper = (S2CPacketIRCMessage) event.getPacket();
            VantageProduct product = VantageProduct.values()[wrapper.getProduct()];
            ChatUtil.displayNoPrefix(IRCInfoComponent.getPrefix("[" + product.getDisplayName() + "] \247" + wrapper.getAuthor(), product.getChatColor()) + wrapper.getMessage() + " " + (Math.random() > 0.9 ? (EnumChatFormatting.GRAY + "Start your msg with # to chat") : ""));
        }
    };
}
