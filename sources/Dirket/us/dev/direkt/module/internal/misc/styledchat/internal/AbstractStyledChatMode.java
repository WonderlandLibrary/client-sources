package us.dev.direkt.module.internal.misc.styledchat.internal;

import us.dev.direkt.module.internal.misc.styledchat.StyledChatMode;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Foundry
 */
public abstract class AbstractStyledChatMode implements StyledChatMode {
    private final String label;

    private final Map<String, String> stringReplacementMap;

    private final Pattern stringReplacementRegex;

    protected AbstractStyledChatMode(String label, Map<String, String> stringReplacementMap) {
        this.label = label;
        this.stringReplacementMap = stringReplacementMap;
        this.stringReplacementRegex = Pattern.compile(stringReplacementMap.keySet().stream().collect(Collectors.joining("|")));
    }

    @Override
    public String formatMessage(String message) {
        final Matcher inputMatcher = stringReplacementRegex.matcher(message);
        final StringBuffer messageBuilder = new StringBuffer(message.length());
        while (inputMatcher.find()) {
            inputMatcher.appendReplacement(messageBuilder, stringReplacementMap.get(Pattern.quote(inputMatcher.group())));
        }
        return inputMatcher.appendTail(messageBuilder).toString();
    }

    public String getLabel() {
        return this.label;
    }
}
