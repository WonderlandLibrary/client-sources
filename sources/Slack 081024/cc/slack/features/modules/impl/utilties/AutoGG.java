package cc.slack.features.modules.impl.utilties;

import cc.slack.start.Slack;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.StringValue;
import cc.slack.features.modules.impl.render.Interface;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;

@ModuleInfo(
        name = "AutoGG",
        category = Category.UTILITIES
)
public class AutoGG extends Module {

    private final StringValue ggMessage = new StringValue("AutoGG Message", "GG");


    public AutoGG() {
        addSettings(ggMessage);
    }

    @Listen
    public void onPacket(PacketEvent event) {
        if (!(event.getPacket() instanceof S02PacketChat)) return;

        IChatComponent chatComponent = ((S02PacketChat) event.getPacket()).getChatComponent();
        String unformattedText = chatComponent.getUnformattedText();
        if (
                unformattedText.contains("Jugar de nuevo") ||
                unformattedText.contains("Ha ganado") ||
                unformattedText.contains("Has muerto") ||
                unformattedText.contains("You won! Want to play again? Click here!") ||
                unformattedText.contains("You lost! Want to play again? Click here!") ||
                unformattedText.contains("You died! Want to play again? Click here!") ||
                        unformattedText.contains(mc.thePlayer.getNBTTagCompound() + "dead")
        ) {
            mc.thePlayer.sendChatMessage(ggMessage.getValue());
            iscorrectjoin();
        }
    }

    public void iscorrectjoin() {
        Slack.getInstance().getModuleManager().getInstance(Interface.class).addNotification("AutoGG:  Sending GG message!", "", 1500L, Slack.NotificationStyle.SUCCESS);
    }

}
