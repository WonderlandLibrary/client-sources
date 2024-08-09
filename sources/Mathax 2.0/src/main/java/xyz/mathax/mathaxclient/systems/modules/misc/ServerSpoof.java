package xyz.mathax.mathaxclient.systems.modules.misc;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.ResourcePackSendS2CPacket;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.mixin.CustomPayloadC2SPacketAccessor;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

import java.util.List;

public class ServerSpoof extends Module {
    private final SettingGroup brandSettings = settings.createGroup("Brands");
    private final SettingGroup resourcePackSettings = settings.createGroup("Resource Pack");
    private final SettingGroup blockChannelsSettings = settings.createGroup("Block Channels");


    // Brand

    private final Setting<Boolean> spoofBrandSetting = brandSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Whether or not to spoof the brand.")
            .defaultValue(true)
            .build()
    );

    private final Setting<String> brandSetting = brandSettings.add(new StringSetting.Builder()
            .name("Brand")
            .description("Specify the brand that will be send to the server.")
            .defaultValue("vanilla")
            .visible(spoofBrandSetting::get)
            .build()
    );

    // Resource Pack

    private final Setting<Boolean> resourcePackSetting = resourcePackSettings.add(new BoolSetting.Builder()
            .name("Resource Pack")
            .description("Spoof accepting server resource pack.")
            .defaultValue(false)
            .build()
    );

    // Block Channels

    private final Setting<Boolean> blockChannelsSetting = blockChannelsSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Whether or not to block some channels.")
            .defaultValue(true)
            .build()
    );

    private final Setting<List<String>> channelsSetting = blockChannelsSettings.add(new StringListSetting.Builder()
            .name("Channels")
            .description("If the channel contains the keyword, this outgoing channel will be blocked.")
            .defaultValue("minecraft:register")
            .visible(blockChannelsSetting::get)
            .build()
    );

    public ServerSpoof(Category category) {
        super(category, "Server Spoof", "Spoof client brand, resource pack and channels.");

        MatHax.EVENT_BUS.subscribe(new Listener());
    }

    private class Listener {
        @EventHandler
        private void onPacketSend(PacketEvent.Send event) {
            if (!isEnabled()) {
                return;
            }

            if (!(event.packet instanceof CustomPayloadC2SPacket)) {
                return;
            }

            CustomPayloadC2SPacketAccessor packet = (CustomPayloadC2SPacketAccessor) event.packet;
            Identifier id = packet.getChannel();

            if (spoofBrandSetting.get() && id.equals(CustomPayloadC2SPacket.BRAND)) {
                packet.setData(new PacketByteBuf(Unpooled.buffer()).writeString(brandSetting.get()));
            }

            if (blockChannelsSetting.get()) {
                for (String channel : channelsSetting.get()) {
                    if (StringUtils.containsIgnoreCase(channel, id.toString())) {
                        event.cancel();
                        return;
                    }
                }
            }
        }

        @EventHandler
        private void onPacketReceive(PacketEvent.Receive event) {
            if (!isEnabled()) {
                return;
            }

            if (resourcePackSetting.get()) {
                if (!(event.packet instanceof ResourcePackSendS2CPacket packet)) {
                    return;
                }

                event.cancel();

                MutableText message = Text.literal("This server has ");
                message.append(packet.isRequired() ? "a required " : "an optional ");
                MutableText link = Text.literal("resource pack");
                link.setStyle(link.getStyle().withColor(Formatting.BLUE).withUnderline(true).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, packet.getURL())).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to download"))));
                message.append(link);
                message.append(".");
                info(message);
            }
        }
    }
}
