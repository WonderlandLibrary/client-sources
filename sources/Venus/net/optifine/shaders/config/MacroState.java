/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.Config;
import net.optifine.expr.ExpressionParser;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.IExpressionResolver;
import net.optifine.expr.ParseException;
import net.optifine.shaders.config.MacroExpressionResolver;

public class MacroState {
    private boolean active = true;
    private Deque<Boolean> dequeState = new ArrayDeque<Boolean>();
    private Deque<Boolean> dequeResolved = new ArrayDeque<Boolean>();
    private Map<String, String> mapMacroValues = new HashMap<String, String>();
    private static final Pattern PATTERN_DIRECTIVE = Pattern.compile("\\s*#\\s*(\\w+)\\s*(.*)");
    private static final Pattern PATTERN_DEFINED = Pattern.compile("defined\\s+(\\w+)");
    private static final Pattern PATTERN_DEFINED_FUNC = Pattern.compile("defined\\s*\\(\\s*(\\w+)\\s*\\)");
    private static final Pattern PATTERN_MACRO = Pattern.compile("(\\w+)");
    private static final String DEFINE = "define";
    private static final String UNDEF = "undef";
    private static final String IFDEF = "ifdef";
    private static final String IFNDEF = "ifndef";
    private static final String IF = "if";
    private static final String ELSE = "else";
    private static final String ELIF = "elif";
    private static final String ENDIF = "endif";
    private static final List<String> MACRO_NAMES = Arrays.asList("define", "undef", "ifdef", "ifndef", "if", "else", "elif", "endif");

    public boolean processLine(String string) {
        Matcher matcher = PATTERN_DIRECTIVE.matcher(string);
        if (!matcher.matches()) {
            return this.active;
        }
        String string2 = matcher.group(1);
        String string3 = matcher.group(2);
        int n = string3.indexOf("//");
        if (n >= 0) {
            string3 = string3.substring(0, n);
        }
        boolean bl = this.active;
        this.processMacro(string2, string3);
        this.active = !this.dequeState.contains(Boolean.FALSE);
        return this.active || bl;
    }

    public static boolean isMacroLine(String string) {
        Matcher matcher = PATTERN_DIRECTIVE.matcher(string);
        if (!matcher.matches()) {
            return true;
        }
        String string2 = matcher.group(1);
        return MACRO_NAMES.contains(string2);
    }

    private void processMacro(String string, String string2) {
        String string3;
        StringTokenizer stringTokenizer = new StringTokenizer(string2, " \t");
        String string4 = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : "";
        String string5 = string3 = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken("").trim() : "";
        if (string.equals(DEFINE)) {
            this.mapMacroValues.put(string4, string3);
        } else if (string.equals(UNDEF)) {
            this.mapMacroValues.remove(string4);
        } else if (string.equals(IFDEF)) {
            boolean bl = this.mapMacroValues.containsKey(string4);
            this.dequeState.add(bl);
            this.dequeResolved.add(bl);
        } else if (string.equals(IFNDEF)) {
            boolean bl = !this.mapMacroValues.containsKey(string4);
            this.dequeState.add(bl);
            this.dequeResolved.add(bl);
        } else if (string.equals(IF)) {
            boolean bl = this.eval(string2);
            this.dequeState.add(bl);
            this.dequeResolved.add(bl);
        } else if (!this.dequeState.isEmpty()) {
            if (string.equals(ELIF)) {
                boolean bl = this.dequeState.removeLast();
                boolean bl2 = this.dequeResolved.removeLast();
                if (bl2) {
                    this.dequeState.add(false);
                    this.dequeResolved.add(bl2);
                } else {
                    boolean bl3 = this.eval(string2);
                    this.dequeState.add(bl3);
                    this.dequeResolved.add(bl3);
                }
            } else if (string.equals(ELSE)) {
                boolean bl = this.dequeState.removeLast();
                boolean bl4 = this.dequeResolved.removeLast();
                boolean bl5 = !bl4;
                this.dequeState.add(bl5);
                this.dequeResolved.add(true);
            } else if (string.equals(ENDIF)) {
                this.dequeState.removeLast();
                this.dequeResolved.removeLast();
            }
        }
    }

    private boolean eval(String object) {
        Object object2;
        Object object3;
        Object object4;
        Matcher matcher = PATTERN_DEFINED.matcher((CharSequence)object);
        object = matcher.replaceAll("defined_$1");
        Matcher matcher2 = PATTERN_DEFINED_FUNC.matcher((CharSequence)object);
        object = matcher2.replaceAll("defined_$1");
        boolean bl = false;
        int n = 0;
        block2: do {
            bl = false;
            object4 = PATTERN_MACRO.matcher((CharSequence)object);
            while (((Matcher)object4).find()) {
                char c;
                object3 = ((Matcher)object4).group();
                if (((String)object3).length() <= 0 || !Character.isLetter(c = ((String)object3).charAt(0)) && c != '_' || !this.mapMacroValues.containsKey(object3)) continue;
                object2 = this.mapMacroValues.get(object3);
                if (object2 == null) {
                    object2 = "1";
                }
                int n2 = ((Matcher)object4).start();
                int n3 = ((Matcher)object4).end();
                object = ((String)object).substring(0, n2) + " " + (String)object2 + " " + ((String)object).substring(n3);
                bl = true;
                ++n;
                continue block2;
            }
        } while (bl && n < 100);
        if (n >= 100) {
            Config.warn("Too many iterations: " + n + ", when resolving: " + (String)object);
            return false;
        }
        try {
            object4 = new MacroExpressionResolver(this.mapMacroValues);
            object3 = new ExpressionParser((IExpressionResolver)object4);
            IExpression iExpression = ((ExpressionParser)object3).parse((String)object);
            if (iExpression.getExpressionType() == ExpressionType.BOOL) {
                object2 = (IExpressionBool)iExpression;
                return object2.eval();
            }
            if (iExpression.getExpressionType() == ExpressionType.FLOAT) {
                object2 = (IExpressionFloat)iExpression;
                float f = object2.eval();
                return f != 0.0f;
            }
            throw new ParseException("Not a boolean or float expression: " + iExpression.getExpressionType());
        } catch (ParseException parseException) {
            Config.warn("Invalid macro expression: " + (String)object);
            Config.warn("Error: " + parseException.getMessage());
            return true;
        }
    }
}

