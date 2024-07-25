package club.bluezenith.module.modules.misc;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.SentMessageEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.builders.AbstractBuilder;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.ModeValue;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class ChatBypass extends Module {
    private static final Map<String, String> LATIN_LETTER_TABLE = new HashMap<>();

    private final ModeValue mode = AbstractBuilder.createMode("Mode")
            .index(1)
            .range("Invisible, Dots, Latin")
            .build();

    private final BooleanValue addLessCharacters = AbstractBuilder.createBoolean("Less characters")
            .index(2)
            .build();

    public ChatBypass() {
        super("ChatBypass", ModuleCategory.MISC);
    }

    @Listener
    public void onSentMessageEvent(SentMessageEvent event) {
        if(event.message.startsWith(".")) return;
        String command = "";

        if(event.message.startsWith("/")) {
            final String[] words = event.message.split(" ");
            final int argumentsToSkip = Math.min(2, words.length);

            final StringJoiner actualMessage = new StringJoiner(" ");

            for (int i = argumentsToSkip; i < words.length; i++) {
                actualMessage.add(words[i]);
            }

            StringJoiner commandWithArgument = new StringJoiner(" ").add(words[0]);

            if(words.length >= 2)
               commandWithArgument.add(words[1]);

            command = commandWithArgument.add("").toString();

            event.message = actualMessage.toString();
        }
        final String[] chars = event.message.split("");


        final StringBuilder scrambledMessage = new StringBuilder();

        switch (mode.get()) {
            case "Invisible":
                for (int i = 0; i < chars.length; i++) {
                    String aChar = chars[i];
                    final boolean addChar = !addLessCharacters.get() || i % 2 == 0;
                    scrambledMessage.append(addChar ? "\u070E" : "")
                            .append(aChar);
                }
            break;

            case "Dots":
                for (int i = 0; i < chars.length; i++) {
                    String aChar = chars[i];
                    final boolean addChar = !addLessCharacters.get() || i % 2 == 0;
                    scrambledMessage.append(addChar ? "¸" : "")
                            .append(aChar);
                }
            break;

            case "Latin":
                for (int i = 0; i < chars.length; i++) {
                    String aChar = chars[i];
                    if (!addLessCharacters.get() || i % 2 == 0) {
                        final String alternative = LATIN_LETTER_TABLE.get(aChar);
                        scrambledMessage.append(alternative == null ? aChar : alternative);
                    } else scrambledMessage.append(aChar);
                }
            break;
        }

        event.message = command + scrambledMessage;
    }

    static {
        //noinspection all
         put("a", "Ã").put("b", "Ɓ").put("c", "Ƈ").put("d", "Ɗ").put("e", "Ē")
        .put("f", "Ḟ").put("g", "ĝ").put("h", "Ĥ").put("i", "Ì").put("j", "Ĵ").put("k", "ķ")
        .put("l", "ĺ").put("m", "Ṁ").put("n", "Ñ").put("o", "Ò").put("p", "Ƥ").put("q", "Ɋ")
        .put("r", "Ɍ").put("s", "Ṡ").put("t", "Ṫ").put("u", "ȗ").put("v", "Ʋ").put("w", "Ẁ")
        .put("x", "х").put("y", "Ý").put("z", "Z");
    }

    private static ChatBypass put(String key, String value) {
        LATIN_LETTER_TABLE.put(key.toUpperCase(), value.toUpperCase());
        LATIN_LETTER_TABLE.put(key.toLowerCase(), value.toLowerCase());

        return null;
    }
}
