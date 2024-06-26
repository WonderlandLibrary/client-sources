package us.dev.direkt.module.internal.misc.styledchat.internal.modes;

import com.google.common.collect.ImmutableMap;
import us.dev.direkt.module.internal.misc.styledchat.internal.AbstractStyledChatMode;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Foundry
 */
public class BubbleFontMode extends AbstractStyledChatMode {
    private static final Map<String, String> characterReplacementMap = new ImmutableMap.Builder<String, String>()
            .put(Pattern.quote("A"), Matcher.quoteReplacement(Character.toString((char)0x24B6)))
            .put(Pattern.quote("B"), Matcher.quoteReplacement(Character.toString((char)0x24B7)))
            .put(Pattern.quote("C"), Matcher.quoteReplacement(Character.toString((char)0x24B8)))
            .put(Pattern.quote("D"), Matcher.quoteReplacement(Character.toString((char)0x24B9)))
            .put(Pattern.quote("E"), Matcher.quoteReplacement(Character.toString((char)0x24BA)))
            .put(Pattern.quote("F"), Matcher.quoteReplacement(Character.toString((char)0x24BB)))
            .put(Pattern.quote("G"), Matcher.quoteReplacement(Character.toString((char)0x24BC)))
            .put(Pattern.quote("H"), Matcher.quoteReplacement(Character.toString((char)0x24BD)))
            .put(Pattern.quote("I"), Matcher.quoteReplacement(Character.toString((char)0x24BE)))
            .put(Pattern.quote("J"), Matcher.quoteReplacement(Character.toString((char)0x24BF)))
            .put(Pattern.quote("K"), Matcher.quoteReplacement(Character.toString((char)0x24C0)))
            .put(Pattern.quote("L"), Matcher.quoteReplacement(Character.toString((char)0x24C1)))
            .put(Pattern.quote("M"), Matcher.quoteReplacement(Character.toString((char)0x24C2)))
            .put(Pattern.quote("N"), Matcher.quoteReplacement(Character.toString((char)0x24C3)))
            .put(Pattern.quote("O"), Matcher.quoteReplacement(Character.toString((char)0x24C4)))
            .put(Pattern.quote("P"), Matcher.quoteReplacement(Character.toString((char)0x24C5)))
            .put(Pattern.quote("Q"), Matcher.quoteReplacement(Character.toString((char)0x24C6)))
            .put(Pattern.quote("R"), Matcher.quoteReplacement(Character.toString((char)0x24C7)))
            .put(Pattern.quote("S"), Matcher.quoteReplacement(Character.toString((char)0x24C8)))
            .put(Pattern.quote("T"), Matcher.quoteReplacement(Character.toString((char)0x24C9)))
            .put(Pattern.quote("U"), Matcher.quoteReplacement(Character.toString((char)0x24CA)))
            .put(Pattern.quote("V"), Matcher.quoteReplacement(Character.toString((char)0x24CB)))
            .put(Pattern.quote("W"), Matcher.quoteReplacement(Character.toString((char)0x24CC)))
            .put(Pattern.quote("X"), Matcher.quoteReplacement(Character.toString((char)0x24CD)))
            .put(Pattern.quote("Y"), Matcher.quoteReplacement(Character.toString((char)0x24CE)))
            .put(Pattern.quote("Z"), Matcher.quoteReplacement(Character.toString((char)0x24CF)))
            .put(Pattern.quote("a"), Matcher.quoteReplacement(Character.toString((char)0x24D0)))
            .put(Pattern.quote("b"), Matcher.quoteReplacement(Character.toString((char)0x24D1)))
            .put(Pattern.quote("c"), Matcher.quoteReplacement(Character.toString((char)0x24D2)))
            .put(Pattern.quote("d"), Matcher.quoteReplacement(Character.toString((char)0x24D3)))
            .put(Pattern.quote("e"), Matcher.quoteReplacement(Character.toString((char)0x24D4)))
            .put(Pattern.quote("f"), Matcher.quoteReplacement(Character.toString((char)0x24D5)))
            .put(Pattern.quote("g"), Matcher.quoteReplacement(Character.toString((char)0x24D6)))
            .put(Pattern.quote("h"), Matcher.quoteReplacement(Character.toString((char)0x24D7)))
            .put(Pattern.quote("i"), Matcher.quoteReplacement(Character.toString((char)0x24D8)))
            .put(Pattern.quote("j"), Matcher.quoteReplacement(Character.toString((char)0x24D9)))
            .put(Pattern.quote("k"), Matcher.quoteReplacement(Character.toString((char)0x24DA)))
            .put(Pattern.quote("l"), Matcher.quoteReplacement(Character.toString((char)0x24DB)))
            .put(Pattern.quote("m"), Matcher.quoteReplacement(Character.toString((char)0x24DC)))
            .put(Pattern.quote("n"), Matcher.quoteReplacement(Character.toString((char)0x24DD)))
            .put(Pattern.quote("o"), Matcher.quoteReplacement(Character.toString((char)0x24DE)))
            .put(Pattern.quote("p"), Matcher.quoteReplacement(Character.toString((char)0x24DF)))
            .put(Pattern.quote("q"), Matcher.quoteReplacement(Character.toString((char)0x24E0)))
            .put(Pattern.quote("r"), Matcher.quoteReplacement(Character.toString((char)0x24E1)))
            .put(Pattern.quote("s"), Matcher.quoteReplacement(Character.toString((char)0x24E2)))
            .put(Pattern.quote("t"), Matcher.quoteReplacement(Character.toString((char)0x24E3)))
            .put(Pattern.quote("u"), Matcher.quoteReplacement(Character.toString((char)0x24E4)))
            .put(Pattern.quote("v"), Matcher.quoteReplacement(Character.toString((char)0x24E5)))
            .put(Pattern.quote("w"), Matcher.quoteReplacement(Character.toString((char)0x24E6)))
            .put(Pattern.quote("x"), Matcher.quoteReplacement(Character.toString((char)0x24E7)))
            .put(Pattern.quote("y"), Matcher.quoteReplacement(Character.toString((char)0x24E8)))
            .put(Pattern.quote("z"), Matcher.quoteReplacement(Character.toString((char)0x24E9)))
            .put(Pattern.quote("`"), Matcher.quoteReplacement(Character.toString((char)0xFF40)))
            .put(Pattern.quote("1"), Matcher.quoteReplacement(Character.toString((char)0x2460)))
            .put(Pattern.quote("2"), Matcher.quoteReplacement(Character.toString((char)0x2461)))
            .put(Pattern.quote("3"), Matcher.quoteReplacement(Character.toString((char)0x2462)))
            .put(Pattern.quote("4"), Matcher.quoteReplacement(Character.toString((char)0x2463)))
            .put(Pattern.quote("5"), Matcher.quoteReplacement(Character.toString((char)0x2464)))
            .put(Pattern.quote("6"), Matcher.quoteReplacement(Character.toString((char)0x2465)))
            .put(Pattern.quote("7"), Matcher.quoteReplacement(Character.toString((char)0x2466)))
            .put(Pattern.quote("8"), Matcher.quoteReplacement(Character.toString((char)0x2467)))
            .put(Pattern.quote("9"), Matcher.quoteReplacement(Character.toString((char)0x2468)))
            .put(Pattern.quote("0"), Matcher.quoteReplacement(Character.toString((char)0x24C4)))
            .put(Pattern.quote("-"), Matcher.quoteReplacement(Character.toString((char)0xFF0D)))
            .put(Pattern.quote("="), Matcher.quoteReplacement(Character.toString((char)0xFF1D)))
            .put(Pattern.quote("~"), Matcher.quoteReplacement(Character.toString((char)0xFF5E)))
            .put(Pattern.quote("!"), Matcher.quoteReplacement(Character.toString((char)0xFF01)))
            .put(Pattern.quote("@"), Matcher.quoteReplacement(Character.toString((char)0xFF20)))
            .put(Pattern.quote("#"), Matcher.quoteReplacement(Character.toString((char)0xFF03)))
            .put(Pattern.quote("$"), Matcher.quoteReplacement(Character.toString((char)0xFF04)))
            .put(Pattern.quote("%"), Matcher.quoteReplacement(Character.toString((char)0xFF05)))
            .put(Pattern.quote("^"), Matcher.quoteReplacement(Character.toString((char)0xFF3E)))
            .put(Pattern.quote("&"), Matcher.quoteReplacement(Character.toString((char)0xFF06)))
            .put(Pattern.quote("*"), Matcher.quoteReplacement(Character.toString((char)0xFF0A)))
            .put(Pattern.quote("("), Matcher.quoteReplacement(Character.toString((char)0x27EE)))
            .put(Pattern.quote(")"), Matcher.quoteReplacement(Character.toString((char)0x27EF)))
            .put(Pattern.quote("_"), Matcher.quoteReplacement(Character.toString((char)0xFF3F)))
            .put(Pattern.quote("+"), Matcher.quoteReplacement(Character.toString((char)0xFF0B)))
            .put(Pattern.quote(","), Matcher.quoteReplacement(Character.toString((char)0xFF0C)))
            .put(Pattern.quote("<"), Matcher.quoteReplacement(Character.toString((char)0xFF1C)))
            .put(Pattern.quote("."), Matcher.quoteReplacement(Character.toString((char)0xFF0E)))
            .put(Pattern.quote("/"), Matcher.quoteReplacement(Character.toString((char)0x29F8)))
            .put(Pattern.quote("?"), Matcher.quoteReplacement(Character.toString((char)0xFF1F)))
            .put(Pattern.quote(";"), Matcher.quoteReplacement(Character.toString((char)0xFF1B)))
            .put(Pattern.quote(":"), Matcher.quoteReplacement(Character.toString((char)0xFF1A)))
            .put(Pattern.quote("'"), Matcher.quoteReplacement(Character.toString((char)0x2019)))
            .put(Pattern.quote("\""), Matcher.quoteReplacement(Character.toString((char)0x201D)))
            .put(Pattern.quote("["), Matcher.quoteReplacement(Character.toString((char)0x27E6)))
            .put(Pattern.quote("]"), Matcher.quoteReplacement(Character.toString((char)0x27E7)))
            .put(Pattern.quote("{"), Matcher.quoteReplacement(Character.toString((char)0x2774)))
            .put(Pattern.quote("}"), Matcher.quoteReplacement(Character.toString((char)0x2775)))
            .put(Pattern.quote("\\"), Matcher.quoteReplacement(Character.toString((char)0x29F9)))
            .put(Pattern.quote("|"), Matcher.quoteReplacement(Character.toString((char)0x23D0)))
            .build();

    public BubbleFontMode() {
        super("Bubble", characterReplacementMap);
    }

}
