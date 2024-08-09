package mathax.client.systems.commands.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import mathax.client.mixin.ClientPlayNetworkHandlerAccessor;
import mathax.client.mixin.ClientPlayerEntityAccessor;
import mathax.client.systems.commands.Command;
import mathax.client.systems.modules.Modules;
import mathax.client.utils.misc.ChatUtils;
import mathax.client.systems.modules.chat.BetterChat;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.network.encryption.NetworkEncryptionUtils;
import net.minecraft.network.message.LastSeenMessagesCollector;
import net.minecraft.network.message.MessageBody;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.time.Instant;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class SayCommand extends Command {
    public String messageText = "";

    public SayCommand() {
        super("say", "Sends messages in chat.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("message", StringArgumentType.greedyString()).executes(context -> {
            BetterChat bc = Modules.get().get(BetterChat.class);

            if (bc.isActive()) {
                messageText = context.getArgument("message", String.class);

                String message = messageText;

                if (bc.emojiFix.get()) message = bc.applyEmojiFix(message);

                if (bc.annoy.get()) message = bc.applyAnnoy(message);

                switch (bc.fancy.get()) {
                    case Full_Width -> message = bc.applyFull(message);
                    case Small_CAPS -> message = bc.applySmall(message);
                    case UwU -> message = bc.applyUwU(message);
                    case Leet -> message = bc.applyLeet(message);
                    case ZV -> message = bc.applyZV(message);
                }

                if (bc.prefix.get()) message = bc.getAffix(bc.prefixText.get(), bc.prefixFont.get(), bc.prefixRandom.get()) + message;

                if (bc.suffix.get()) message = bc.getAffix(bc.suffixText.get(), bc.suffixFont.get(), bc.suffixRandom.get()) + message;

                if (bc.coordsProtection.get() && bc.containsCoordinates(message)) {
                    MutableText warningMessage = Text.literal("It looks like there are coordinates in your message! ");

                    MutableText sendButton = bc.getSendButton(message);
                    warningMessage.append(sendButton);

                    ChatUtils.sendMsg(warningMessage);

                    return SINGLE_SUCCESS;
                }

                Instant instant = Instant.now();
                long l = NetworkEncryptionUtils.SecureRandomUtil.nextLong();
                ClientPlayNetworkHandler handler = mc.getNetworkHandler();
                LastSeenMessagesCollector.LastSeenMessages lastSeenMessages = ((ClientPlayNetworkHandlerAccessor) handler).getLastSeenMessagesCollector().collect();
                MessageSignatureData messageSignatureData = ((ClientPlayNetworkHandlerAccessor) handler).getMessagePacker().pack(new MessageBody(message, instant, l, lastSeenMessages.lastSeen()));
                handler.sendPacket(new ChatMessageC2SPacket(message, instant, l, messageSignatureData, lastSeenMessages.update()));
            } else {
                String message = context.getArgument("message", String.class);
                Instant instant = Instant.now();
                long l = NetworkEncryptionUtils.SecureRandomUtil.nextLong();
                ClientPlayNetworkHandler handler = mc.getNetworkHandler();
                LastSeenMessagesCollector.LastSeenMessages lastSeenMessages = ((ClientPlayNetworkHandlerAccessor) handler).getLastSeenMessagesCollector().collect();
                MessageSignatureData messageSignatureData = ((ClientPlayNetworkHandlerAccessor) handler).getMessagePacker().pack(new MessageBody(message, instant, l, lastSeenMessages.lastSeen()));
                handler.sendPacket(new ChatMessageC2SPacket(message, instant, l, messageSignatureData, lastSeenMessages.update()));
            }

            return SINGLE_SUCCESS;
        }));
    }
}
