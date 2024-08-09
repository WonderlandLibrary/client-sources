package xyz.mathax.mathaxclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.events.game.ReceiveMessageEvent;
import xyz.mathax.mathaxclient.mixininterface.IChatHud;
import xyz.mathax.mathaxclient.mixininterface.IChatHudLine;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.NoRender;
import xyz.mathax.mathaxclient.utils.misc.MatHaxIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.regex.Pattern;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin implements IChatHud {
    private static final Pattern MATHAX_PREFIX_REGEX = Pattern.compile("^\\s{0,2}(<[0-9]{1,2}:[0-9]{1,2}>\\s)?\\[MatHax]");
    private static final Identifier MATHAX_CHAT_ICON = new MatHaxIdentifier("textures/icons/chat/mathax.png");

    private static final Pattern BARITONE_PREFIX_REGEX = Pattern.compile("^\\s{0,2}(<[0-9]{1,2}:[0-9]{1,2}>\\s)?\\[Baritone]");
    private static final Identifier BARITONE_CHAT_ICON = new MatHaxIdentifier("textures/icons/chat/baritone.png");

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Final
    private List<ChatHudLine.Visible> visibleMessages;

    @Shadow
    private int scrolledLines;

    @Shadow
    private static double getMessageOpacityMultiplier(int age) {
        throw new AssertionError();
    }

    @Shadow
    @Final
    private List<ChatHudLine> messages;

    @Unique
    private int nextId;

    @Unique
    private boolean skipOnAddMessage;

    @Shadow
    protected abstract void addMessage(Text message, @Nullable MessageSignatureData signature, int ticks, @Nullable MessageIndicator indicator, boolean refresh);

    @Shadow
    public abstract void addMessage(Text message);

    @Override
    public void add(Text message, int id) {
        nextId = id;
        addMessage(message);
        nextId = 0;
    }

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At(value = "INVOKE", target = "Ljava/util/List;add(ILjava/lang/Object;)V", ordinal = 0, shift = At.Shift.AFTER))
    private void onAddMessageAfterNewChatHudLineVisible(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo info) {
        ((IChatHudLine) (Object) visibleMessages.get(0)).setId(nextId);
    }

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At(value = "INVOKE", target = "Ljava/util/List;add(ILjava/lang/Object;)V", ordinal = 1, shift = At.Shift.AFTER))
    private void onAddMessageAfterNewChatHudLine(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo info) {
        ((IChatHudLine) (Object) messages.get(0)).setId(nextId);
    }

    @Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", cancellable = true)
    private void onAddMessage(Text message, @Nullable MessageSignatureData signature, int ticks, @Nullable MessageIndicator indicator, boolean refresh, CallbackInfo info) {
        if (skipOnAddMessage) {
            return;
        }

        ReceiveMessageEvent event = MatHax.EVENT_BUS.post(ReceiveMessageEvent.get(message, indicator, nextId));
        if (event.isCancelled()) {
            info.cancel();
        } else {
            visibleMessages.removeIf((msg) -> ((IChatHudLine) (Object) msg).getId() == nextId && nextId != 0);
            messages.removeIf((msg) -> ((IChatHudLine) (Object) msg).getId() == nextId && nextId != 0);

            if (event.isModified()) {
                info.cancel();

                skipOnAddMessage = true;
                addMessage(event.getMessage(), signature, ticks, event.getIndicator(), refresh);
                skipOnAddMessage = false;
            }
        }
    }

    /*@Redirect(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/hud/ChatHud;visibleMessages:Ljava/util/List;")), at = @At(value = "INVOKE", target = "Ljava/util/List;size()I"))
    private int addMessageListSizeProxy(List<ChatHudLine> list) {
        BetterChat betterChat = Modules.get().get(BetterChat.class);
        if (betterChat.isLongerChat() && betterChat.getChatLength() >= 100) {
            return list.size() - betterChat.getChatLength();
        }

        return list.size();
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, int currentTick, int mouseX, int mouseY, CallbackInfo info) {
        if (!Modules.get().get(BetterChat.class).displayPlayerHeads()) {
            return;
        }

        if (mc.options.getChatVisibility().getValue() == ChatVisibility.HIDDEN) {
            return;
        }

        int maxLineCount = mc.inGameHud.getChatHud().getVisibleLineCount();

        double d = mc.options.getChatOpacity().getValue() * 0.8999999761581421D + 0.10000000149011612D;
        double g = 9.0D * (mc.options.getChatLineSpacing().getValue() + 1.0D);
        double h = -8.0D * (mc.options.getChatLineSpacing().getValue() + 1.0D) + 4.0D * mc.options.getChatLineSpacing().getValue() + 8.0D;

        matrixStack.push();
        matrixStack.translate(2, -0.1f, 10);
        RenderSystem.enableBlend();
        for (int m = 0; m + this.scrolledLines < this.visibleMessages.size() && m < maxLineCount; ++m) {
            ChatHudLine.Visible chatHudLine = this.visibleMessages.get(m + this.scrolledLines);
            if (chatHudLine != null) {
                int x = currentTick - chatHudLine.addedTime();
                if (x < 200 || isChatFocused()) {
                    double o = isChatFocused() ? 1.0D : getMessageOpacityMultiplier(x);
                    if (o * d > 0.01D) {
                        double s = ((double)(-m) * g);
                        StringCharacterVisitor visitor = new StringCharacterVisitor();
                        chatHudLine.content().accept(visitor);
                        drawIcon(matrixStack, visitor.result.toString(), (int)(s + h), (float)(o * d));
                    }
                }
            }
        }

        RenderSystem.disableBlend();
        matrixStack.pop();
    }*/

    private boolean isChatFocused() {
        return client.currentScreen instanceof ChatScreen;
    }

    private void drawIcon(MatrixStack matrixStack, String line, int y, float opacity) {
        if (MATHAX_PREFIX_REGEX.matcher(line).find()) {
            RenderSystem.setShaderTexture(0, MATHAX_CHAT_ICON);
            matrixStack.push();
            RenderSystem.setShaderColor(1, 1, 1, opacity);
            matrixStack.translate(0, y, 0);
            matrixStack.scale(0.125f, 0.125f, 1);
            DrawableHelper.drawTexture(matrixStack, 0, 0, 0f, 0f, 64, 64, 64, 64);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            matrixStack.pop();
            return;
        } else if (BARITONE_PREFIX_REGEX.matcher(line).find()) {
            RenderSystem.setShaderTexture(0, BARITONE_CHAT_ICON);
            matrixStack.push();
            RenderSystem.setShaderColor(1, 1, 1, opacity);
            matrixStack.translate(0, y, 10);
            matrixStack.scale(0.125f, 0.125f, 1);
            DrawableHelper.drawTexture(matrixStack, 0, 0, 0f, 0f, 64, 64, 64, 64);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            matrixStack.pop();
            return;
        }

        Identifier skin = getMessageTexture(line);
        if (skin != null) {
            RenderSystem.setShaderColor(1, 1, 1, opacity);
            RenderSystem.setShaderTexture(0, skin);
            DrawableHelper.drawTexture(matrixStack, 0, y, 8, 8, 8.0F, 8.0F,8, 8, 64, 64);
            DrawableHelper.drawTexture(matrixStack, 0, y, 8, 8, 40.0F, 8.0F,8, 8, 64, 64);
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }
    }

    private Identifier getMessageTexture(String message) {
        if (client.getNetworkHandler() == null) {
            return null;
        }

        for (String part : message.split("(§.)|[^\\w]")) {
            if (part.isBlank()) {
                continue;
            }

            PlayerListEntry playerListEntry = client.getNetworkHandler().getPlayerListEntry(part);
            if (playerListEntry != null) {
                return playerListEntry.getSkinTexture();
            }
        }

        return null;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHudLine$Visible;indicator()Lnet/minecraft/client/gui/hud/MessageIndicator;"))
    private MessageIndicator onMessageIndicator(ChatHudLine.Visible message) {
        return Modules.get().get(NoRender.class).noMessageSignatureIndicator() ? null : message.indicator();
    }
}
