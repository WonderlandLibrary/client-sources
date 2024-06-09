package us.dev.direkt.module.internal.misc.styledchat.internal.modes;

import com.google.common.collect.ImmutableMap;
import us.dev.direkt.module.internal.misc.styledchat.internal.AbstractStyledChatMode;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Foundry
 */
public class MirroredMode extends AbstractStyledChatMode {
    private static final Map<String, String> characterReplacementMap = new ImmutableMap.Builder<String, String>()
            .put(Pattern.quote("A"), Matcher.quoteReplacement(Character.toString((char)0x2200)))
            .put(Pattern.quote("B"), Matcher.quoteReplacement(Character.toString((char)0x0a98)))
            .put(Pattern.quote("C"), Matcher.quoteReplacement(Character.toString((char)0x0186)))
            .put(Pattern.quote("D"), Matcher.quoteReplacement(Character.toString((char)0x10a7)))
            .put(Pattern.quote("E"), Matcher.quoteReplacement(Character.toString((char)0x018e)))
            .put(Pattern.quote("F"), Matcher.quoteReplacement(Character.toString((char)0x2132)))
            .put(Pattern.quote("G"), Matcher.quoteReplacement(Character.toString((char)0x2141)))
            .put(Pattern.quote("H"), Matcher.quoteReplacement(Character.toString((char)0x0048)))
            .put(Pattern.quote("I"), Matcher.quoteReplacement(Character.toString((char)0x0049)))
            .put(Pattern.quote("J"), Matcher.quoteReplacement(Character.toString((char)0x017f)))
            .put(Pattern.quote("K"), Matcher.quoteReplacement(Character.toString((char)0x029e)))
            .put(Pattern.quote("L"), Matcher.quoteReplacement(Character.toString((char)0x10a8)))
            .put(Pattern.quote("M"), Matcher.quoteReplacement(Character.toString((char)0x0057)))
            .put(Pattern.quote("N"), Matcher.quoteReplacement(Character.toString((char)0x0418)))
            .put(Pattern.quote("O"), Matcher.quoteReplacement(Character.toString((char)0x004f)))
            .put(Pattern.quote("P"), Matcher.quoteReplacement(Character.toString((char)0x0500)))
            .put(Pattern.quote("Q"), Matcher.quoteReplacement(Character.toString((char)0x1ff8)))
            .put(Pattern.quote("R"), Matcher.quoteReplacement(Character.toString((char)0x0b27)))
            .put(Pattern.quote("S"), Matcher.quoteReplacement(Character.toString((char)0x01a7)))
            .put(Pattern.quote("T"), Matcher.quoteReplacement(Character.toString((char)0x22a5)))
            .put(Pattern.quote("U"), Matcher.quoteReplacement(Character.toString((char)0x2229)))
            .put(Pattern.quote("V"), Matcher.quoteReplacement(Character.toString((char)0x039b)))
            .put(Pattern.quote("W"), Matcher.quoteReplacement(Character.toString((char)0x004d)))
            .put(Pattern.quote("X"), Matcher.quoteReplacement(Character.toString((char)0x0058)))
            .put(Pattern.quote("Y"), Matcher.quoteReplacement(Character.toString((char)0x2144)))
            .put(Pattern.quote("Z"), Matcher.quoteReplacement(Character.toString((char)0x005a)))
            .put(Pattern.quote("a"), Matcher.quoteReplacement(Character.toString((char)0x0250)))
            .put(Pattern.quote("b"), Matcher.quoteReplacement(Character.toString((char)0x0070)))
            .put(Pattern.quote("c"), Matcher.quoteReplacement(Character.toString((char)0x0254)))
            .put(Pattern.quote("d"), Matcher.quoteReplacement(Character.toString((char)0x0071)))
            .put(Pattern.quote("e"), Matcher.quoteReplacement(Character.toString((char)0x0259)))
            .put(Pattern.quote("f"), Matcher.quoteReplacement(Character.toString((char)0x025f)))
            .put(Pattern.quote("g"), Matcher.quoteReplacement(Character.toString((char)0x0253)))
            .put(Pattern.quote("h"), Matcher.quoteReplacement(Character.toString((char)0x0265)))
            .put(Pattern.quote("i"), Matcher.quoteReplacement(Character.toString((char)0x0131)))
            .put(Pattern.quote("j"), Matcher.quoteReplacement(Character.toString((char)0x017f)))
            .put(Pattern.quote("k"), Matcher.quoteReplacement(Character.toString((char)0x029e)))
            .put(Pattern.quote("l"), Matcher.quoteReplacement(Character.toString((char)0x0e45)))
            .put(Pattern.quote("m"), Matcher.quoteReplacement(Character.toString((char)0x026f)))
            .put(Pattern.quote("n"), Matcher.quoteReplacement(Character.toString((char)0x03c5)))
            .put(Pattern.quote("o"), Matcher.quoteReplacement(Character.toString((char)0x006f)))
            .put(Pattern.quote("p"), Matcher.quoteReplacement(Character.toString((char)0x0062)))
            .put(Pattern.quote("q"), Matcher.quoteReplacement(Character.toString((char)0x0064)))
            .put(Pattern.quote("r"), Matcher.quoteReplacement(Character.toString((char)0x0279)))
            .put(Pattern.quote("s"), Matcher.quoteReplacement(Character.toString((char)0x01a8)))
            .put(Pattern.quote("t"), Matcher.quoteReplacement(Character.toString((char)0x0287)))
            .put(Pattern.quote("u"), Matcher.quoteReplacement(Character.toString((char)0x006e)))
            .put(Pattern.quote("v"), Matcher.quoteReplacement(Character.toString((char)0x028c)))
            .put(Pattern.quote("w"), Matcher.quoteReplacement(Character.toString((char)0x028d)))
            .put(Pattern.quote("x"), Matcher.quoteReplacement(Character.toString((char)0x0078)))
            .put(Pattern.quote("y"), Matcher.quoteReplacement(Character.toString((char)0x028e)))
            .put(Pattern.quote("z"), Matcher.quoteReplacement(Character.toString((char)0x007a)))
            .put(Pattern.quote("1"), Matcher.quoteReplacement(Character.toString((char)0x0196)))
            .put(Pattern.quote("2"), Matcher.quoteReplacement(Character.toString((char)0x0547)))
            .put(Pattern.quote("3"), Matcher.quoteReplacement(Character.toString((char)0x0190)))
            .put(Pattern.quote("4"), Matcher.quoteReplacement(Character.toString((char)0x029c)))
            .put(Pattern.quote("5"), Matcher.quoteReplacement(Character.toString((char)0x0aec)))
            .put(Pattern.quote("6"), Matcher.quoteReplacement(Character.toString((char)0x0b67)))
            .put(Pattern.quote("7"), Matcher.quoteReplacement(Character.toString((char)0x2143)))
            .put(Pattern.quote("8"), Matcher.quoteReplacement(Character.toString((char)0x0038)))
            .put(Pattern.quote("9"), Matcher.quoteReplacement(Character.toString((char)0x10db)))
            .put(Pattern.quote("0"), Matcher.quoteReplacement(Character.toString((char)0x0030)))
            .put(Pattern.quote("-"), Matcher.quoteReplacement(Character.toString((char)0x002d)))
            .put(Pattern.quote("="), Matcher.quoteReplacement(Character.toString((char)0x003d)))
            .put(Pattern.quote("~"), Matcher.quoteReplacement(Character.toString((char)0x007e)))
            .put(Pattern.quote("!"), Matcher.quoteReplacement(Character.toString((char)0x00a1)))
            .put(Pattern.quote("@"), Matcher.quoteReplacement(Character.toString((char)0x0040)))
            .put(Pattern.quote("#"), Matcher.quoteReplacement(Character.toString((char)0x0023)))
            .put(Pattern.quote("$"), Matcher.quoteReplacement(Character.toString((char)0x0024)))
            .put(Pattern.quote("%"), Matcher.quoteReplacement(Character.toString((char)0x0025)))
            .put(Pattern.quote("^"), Matcher.quoteReplacement(Character.toString((char)0x005e)))
            .put(Pattern.quote("&"), Matcher.quoteReplacement(Character.toString((char)0x214b)))
            .put(Pattern.quote("*"), Matcher.quoteReplacement(Character.toString((char)0x2093)))
            .put(Pattern.quote("("), Matcher.quoteReplacement(Character.toString((char)0x0029)))
            .put(Pattern.quote(")"), Matcher.quoteReplacement(Character.toString((char)0x0028)))
            .put(Pattern.quote("_"), Matcher.quoteReplacement(Character.toString((char)0x005f)))
            .put(Pattern.quote("+"), Matcher.quoteReplacement(Character.toString((char)0x002b)))
            .put(Pattern.quote(","), Matcher.quoteReplacement(Character.toString((char)0x02bb)))
            .put(Pattern.quote("<"), Matcher.quoteReplacement(Character.toString((char)0x003c)))
            .put(Pattern.quote("."), Matcher.quoteReplacement(Character.toString((char)0x0387)))
            .put(Pattern.quote("/"), Matcher.quoteReplacement(Character.toString((char)0x002f)))
            .put(Pattern.quote("?"), Matcher.quoteReplacement(Character.toString((char)0x00bf)))
            .put(Pattern.quote(";"), Matcher.quoteReplacement(Character.toString((char)0x003b)))
            .put(Pattern.quote(":"), Matcher.quoteReplacement(Character.toString((char)0x003a)))
            .put(Pattern.quote("'"), Matcher.quoteReplacement(Character.toString((char)0x02cc)))
            .put(Pattern.quote("\""), Matcher.quoteReplacement(Character.toString((char)0x02cc)))
            .put(Pattern.quote("["), Matcher.quoteReplacement(Character.toString((char)0x02cc)))
            .put(Pattern.quote("]"), Matcher.quoteReplacement(Character.toString((char)0x005b)))
            .put(Pattern.quote("{"), Matcher.quoteReplacement(Character.toString((char)0x005d)))
            .put(Pattern.quote("}"), Matcher.quoteReplacement(Character.toString((char)0x007b)))
            .put(Pattern.quote("\\"), Matcher.quoteReplacement(Character.toString((char)0x007d)))
            .put(Pattern.quote("|"), Matcher.quoteReplacement(Character.toString((char)0x005c)))
            .build();

    public MirroredMode() {
        super("Mirrored", characterReplacementMap);
    }

    @Override
    public String formatMessage(String message) {
        final String formattedOutput = super.formatMessage(message);
        return new StringBuilder(formattedOutput).reverse().toString();
    }
}
