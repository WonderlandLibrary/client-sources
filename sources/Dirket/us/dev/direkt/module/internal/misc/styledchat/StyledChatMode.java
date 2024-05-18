package us.dev.direkt.module.internal.misc.styledchat;

import us.dev.api.interfaces.Labeled;

/**
 * @author Foundry
 */
public interface StyledChatMode extends Labeled {
    String formatMessage(String message);
}
