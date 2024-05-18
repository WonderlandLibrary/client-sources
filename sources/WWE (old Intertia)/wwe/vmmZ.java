package wwe;

import baritone.api.event.events.*;
import baritone.api.*;

public class vmmZ extends HAxG
{
    public vmmZ() {
        super("b");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        try {
            RaoI(array);
            if (vmmZ.\u2000\u2009\u2004\u200c\u2000\u200a\u200d\u200b\u200b) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.kVjF(ex);
        }
    }
    
    @Override
    public String UiIp() {
        return "A auto walking bot";
    }
    
    @Override
    public String ZlRY() {
        return "b";
    }
    
    public static void RaoI(final String... array) {
        final Settings$Setting<Boolean> chatControl = BaritoneAPI.getSettings().chatControl;
        final boolean booleanValue = chatControl.value;
        chatControl.value = true;
        final ChatEvent chatEvent = new ChatEvent(String.join(" ", (CharSequence[])array));
        BaritoneAPI.getProvider().getPrimaryBaritone().getGameEventHandler().onSendChatMessage(chatEvent);
        if (!chatEvent.isCancelled() && !array[0].equals("damn")) {
            HAxG.TuzD("Invalid baritone command, try .b help");
        }
        chatControl.value = booleanValue;
    }
}
