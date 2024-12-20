package us.dev.direkt.module.internal.misc.styledchat.internal.modes;

import com.google.common.collect.ImmutableMap;
import us.dev.direkt.module.internal.misc.styledchat.internal.AbstractStyledChatMode;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Foundry
 */
public class FancyMode extends AbstractStyledChatMode {
    private static final Map<String, String> characterReplacementMap = new ImmutableMap.Builder<String, String>()
            .put(Pattern.quote("A"), Matcher.quoteReplacement(Character.toString((char) 0xFF21)))
            .put(Pattern.quote("B"), Matcher.quoteReplacement(Character.toString((char) 0xFF22)))
            .put(Pattern.quote("C"), Matcher.quoteReplacement(Character.toString((char) 0xFF23)))
            .put(Pattern.quote("D"), Matcher.quoteReplacement(Character.toString((char) 0xFF24)))
            .put(Pattern.quote("E"), Matcher.quoteReplacement(Character.toString((char) 0xFF25)))
            .put(Pattern.quote("F"), Matcher.quoteReplacement(Character.toString((char) 0xFF26)))
            .put(Pattern.quote("G"), Matcher.quoteReplacement(Character.toString((char) 0xFF27)))
            .put(Pattern.quote("H"), Matcher.quoteReplacement(Character.toString((char) 0xFF28)))
            .put(Pattern.quote("I"), Matcher.quoteReplacement(Character.toString((char) 0xFF29)))
            .put(Pattern.quote("J"), Matcher.quoteReplacement(Character.toString((char) 0xFF2A)))
            .put(Pattern.quote("K"), Matcher.quoteReplacement(Character.toString((char) 0xFF2B)))
            .put(Pattern.quote("L"), Matcher.quoteReplacement(Character.toString((char) 0xFF2C)))
            .put(Pattern.quote("M"), Matcher.quoteReplacement(Character.toString((char) 0xFF2D)))
            .put(Pattern.quote("N"), Matcher.quoteReplacement(Character.toString((char) 0xFF2E)))
            .put(Pattern.quote("O"), Matcher.quoteReplacement(Character.toString((char) 0xFF2F)))
            .put(Pattern.quote("P"), Matcher.quoteReplacement(Character.toString((char) 0xFF30)))
            .put(Pattern.quote("Q"), Matcher.quoteReplacement(Character.toString((char) 0xFF31)))
            .put(Pattern.quote("R"), Matcher.quoteReplacement(Character.toString((char) 0xFF32)))
            .put(Pattern.quote("S"), Matcher.quoteReplacement(Character.toString((char) 0xFF33)))
            .put(Pattern.quote("T"), Matcher.quoteReplacement(Character.toString((char) 0xFF34)))
            .put(Pattern.quote("U"), Matcher.quoteReplacement(Character.toString((char) 0xFF35)))
            .put(Pattern.quote("V"), Matcher.quoteReplacement(Character.toString((char) 0xFF36)))
            .put(Pattern.quote("W"), Matcher.quoteReplacement(Character.toString((char) 0xFF37)))
            .put(Pattern.quote("X"), Matcher.quoteReplacement(Character.toString((char) 0xFF38)))
            .put(Pattern.quote("Y"), Matcher.quoteReplacement(Character.toString((char) 0xFF39)))
            .put(Pattern.quote("Z"), Matcher.quoteReplacement(Character.toString((char) 0xFF3A)))
            .put(Pattern.quote("a"), Matcher.quoteReplacement(Character.toString((char) 0xFF41)))
            .put(Pattern.quote("b"), Matcher.quoteReplacement(Character.toString((char) 0xFF42)))
            .put(Pattern.quote("c"), Matcher.quoteReplacement(Character.toString((char) 0xFF43)))
            .put(Pattern.quote("d"), Matcher.quoteReplacement(Character.toString((char) 0xFF44)))
            .put(Pattern.quote("e"), Matcher.quoteReplacement(Character.toString((char) 0xFF45)))
            .put(Pattern.quote("f"), Matcher.quoteReplacement(Character.toString((char) 0xFF46)))
            .put(Pattern.quote("g"), Matcher.quoteReplacement(Character.toString((char) 0xFF47)))
            .put(Pattern.quote("h"), Matcher.quoteReplacement(Character.toString((char) 0xFF48)))
            .put(Pattern.quote("i"), Matcher.quoteReplacement(Character.toString((char) 0xFF49)))
            .put(Pattern.quote("j"), Matcher.quoteReplacement(Character.toString((char) 0xFF4A)))
            .put(Pattern.quote("k"), Matcher.quoteReplacement(Character.toString((char) 0xFF4B)))
            .put(Pattern.quote("l"), Matcher.quoteReplacement(Character.toString((char) 0xFF4C)))
            .put(Pattern.quote("m"), Matcher.quoteReplacement(Character.toString((char) 0xFF4D)))
            .put(Pattern.quote("n"), Matcher.quoteReplacement(Character.toString((char) 0xFF4E)))
            .put(Pattern.quote("o"), Matcher.quoteReplacement(Character.toString((char) 0xFF4F)))
            .put(Pattern.quote("p"), Matcher.quoteReplacement(Character.toString((char) 0xFF50)))
            .put(Pattern.quote("q"), Matcher.quoteReplacement(Character.toString((char) 0xFF51)))
            .put(Pattern.quote("r"), Matcher.quoteReplacement(Character.toString((char) 0xFF52)))
            .put(Pattern.quote("s"), Matcher.quoteReplacement(Character.toString((char) 0xFF53)))
            .put(Pattern.quote("t"), Matcher.quoteReplacement(Character.toString((char) 0xFF54)))
            .put(Pattern.quote("u"), Matcher.quoteReplacement(Character.toString((char) 0xFF55)))
            .put(Pattern.quote("v"), Matcher.quoteReplacement(Character.toString((char) 0xFF56)))
            .put(Pattern.quote("w"), Matcher.quoteReplacement(Character.toString((char) 0xFF57)))
            .put(Pattern.quote("x"), Matcher.quoteReplacement(Character.toString((char) 0xFF58)))
            .put(Pattern.quote("y"), Matcher.quoteReplacement(Character.toString((char) 0xFF59)))
            .put(Pattern.quote("z"), Matcher.quoteReplacement(Character.toString((char) 0xFF5A)))
            .put(Pattern.quote("`"), Matcher.quoteReplacement(Character.toString((char) 0xFF40)))
            .put(Pattern.quote("1"), Matcher.quoteReplacement(Character.toString((char) 0xFF11)))
            .put(Pattern.quote("2"), Matcher.quoteReplacement(Character.toString((char) 0xFF12)))
            .put(Pattern.quote("3"), Matcher.quoteReplacement(Character.toString((char) 0xFF13)))
            .put(Pattern.quote("4"), Matcher.quoteReplacement(Character.toString((char) 0xFF14)))
            .put(Pattern.quote("5"), Matcher.quoteReplacement(Character.toString((char) 0xFF15)))
            .put(Pattern.quote("6"), Matcher.quoteReplacement(Character.toString((char) 0xFF16)))
            .put(Pattern.quote("7"), Matcher.quoteReplacement(Character.toString((char) 0xFF17)))
            .put(Pattern.quote("8"), Matcher.quoteReplacement(Character.toString((char) 0xFF18)))
            .put(Pattern.quote("9"), Matcher.quoteReplacement(Character.toString((char) 0xFF19)))
            .put(Pattern.quote("0"), Matcher.quoteReplacement(Character.toString((char) 0xFF10)))
            .put(Pattern.quote("-"), Matcher.quoteReplacement(Character.toString((char) 0xFF0D)))
            .put(Pattern.quote("="), Matcher.quoteReplacement(Character.toString((char) 0xFF1D)))
            .put(Pattern.quote("~"), Matcher.quoteReplacement(Character.toString((char) 0xFF5E)))
            .put(Pattern.quote("!"), Matcher.quoteReplacement(Character.toString((char) 0xFF01)))
            .put(Pattern.quote("@"), Matcher.quoteReplacement(Character.toString((char) 0xFF20)))
            .put(Pattern.quote("#"), Matcher.quoteReplacement(Character.toString((char) 0xFF03)))
            .put(Pattern.quote("$"), Matcher.quoteReplacement(Character.toString((char) 0xFF04)))
            .put(Pattern.quote("%"), Matcher.quoteReplacement(Character.toString((char) 0xFF05)))
            .put(Pattern.quote("^"), Matcher.quoteReplacement(Character.toString((char) 0xFF3E)))
            .put(Pattern.quote("&"), Matcher.quoteReplacement(Character.toString((char) 0xFF06)))
            .put(Pattern.quote("*"), Matcher.quoteReplacement(Character.toString((char) 0xFF0A)))
            .put(Pattern.quote("("), Matcher.quoteReplacement(Character.toString((char) 0x27EE)))
            .put(Pattern.quote(")"), Matcher.quoteReplacement(Character.toString((char) 0x27EF)))
            .put(Pattern.quote("_"), Matcher.quoteReplacement(Character.toString((char) 0xFF3F)))
            .put(Pattern.quote("+"), Matcher.quoteReplacement(Character.toString((char) 0xFF0B)))
            .put(Pattern.quote("),"), Matcher.quoteReplacement(Character.toString((char) 0xFF0C)))
            .put(Pattern.quote("<"), Matcher.quoteReplacement(Character.toString((char) 0xFF1C)))
            .put(Pattern.quote("."), Matcher.quoteReplacement(Character.toString((char) 0xFF0E)))
            .put(Pattern.quote("/"), Matcher.quoteReplacement(Character.toString((char) 0x29F8)))
            .put(Pattern.quote("?"), Matcher.quoteReplacement(Character.toString((char) 0xFF1F)))
            .put(Pattern.quote(";"), Matcher.quoteReplacement(Character.toString((char) 0xFF1B)))
            .put(Pattern.quote(":"), Matcher.quoteReplacement(Character.toString((char) 0xFF1A)))
            .put(Pattern.quote("'"), Matcher.quoteReplacement(Character.toString((char) 0x2019)))
            .put(Pattern.quote("\""), Matcher.quoteReplacement(Character.toString((char) 0x201D)))
            .put(Pattern.quote("["), Matcher.quoteReplacement(Character.toString((char) 0x27E6)))
            .put(Pattern.quote("]"), Matcher.quoteReplacement(Character.toString((char) 0x27E7)))
            .put(Pattern.quote("{"), Matcher.quoteReplacement(Character.toString((char) 0x2774)))
            .put(Pattern.quote("}"), Matcher.quoteReplacement(Character.toString((char) 0x2775)))
            .put(Pattern.quote("\\"), Matcher.quoteReplacement(Character.toString((char) 0x29F9)))
            .put(Pattern.quote("|"), Matcher.quoteReplacement(Character.toString((char) 0x23D0)))
            .build();

    public FancyMode() {
        super("Fancy", characterReplacementMap);
    }

}
