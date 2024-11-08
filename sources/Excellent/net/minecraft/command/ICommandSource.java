package net.minecraft.command;

import net.minecraft.util.text.ITextComponent;

import java.util.UUID;

public interface ICommandSource
{
    /** A CommandSource that ignores all messages. */
    ICommandSource DUMMY = new ICommandSource()
    {
        public void sendMessage(ITextComponent component, UUID senderUUID)
        {
        }
        public boolean shouldReceiveFeedback()
        {
            return false;
        }
        public boolean shouldReceiveErrors()
        {
            return false;
        }
        public boolean allowLogging()
        {
            return false;
        }
    };

    /**
     * Send a chat message to the CommandSender
     */
    void sendMessage(ITextComponent component, UUID senderUUID);

    boolean shouldReceiveFeedback();

    boolean shouldReceiveErrors();

    boolean allowLogging();
}
