/*
 * Decompiled with CFR 0.152.
 */
package org.yaml.snakeyaml.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.resolver.ResolverTuple;

public class Resolver {
    public static final Pattern BOOL = Pattern.compile("^(?:yes|Yes|YES|no|No|NO|true|True|TRUE|false|False|FALSE|on|On|ON|off|Off|OFF)$");
    public static final Pattern FLOAT = Pattern.compile("^([-+]?(\\.[0-9]+|[0-9_]+(\\.[0-9_]*)?)([eE][-+]?[0-9]+)?|[-+]?[0-9][0-9_]*(?::[0-5]?[0-9])+\\.[0-9_]*|[-+]?\\.(?:inf|Inf|INF)|\\.(?:nan|NaN|NAN))$");
    public static final Pattern INT = Pattern.compile("^(?:[-+]?0b[0-1_]+|[-+]?0[0-7_]+|[-+]?(?:0|[1-9][0-9_]*)|[-+]?0x[0-9a-fA-F_]+|[-+]?[1-9][0-9_]*(?::[0-5]?[0-9])+)$");
    public static final Pattern MERGE = Pattern.compile("^(?:<<)$");
    public static final Pattern NULL = Pattern.compile("^(?:~|null|Null|NULL| )$");
    public static final Pattern EMPTY = Pattern.compile("^$");
    public static final Pattern TIMESTAMP = Pattern.compile("^(?:[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]|[0-9][0-9][0-9][0-9]-[0-9][0-9]?-[0-9][0-9]?(?:[Tt]|[ \t]+)[0-9][0-9]?:[0-9][0-9]:[0-9][0-9](?:\\.[0-9]*)?(?:[ \t]*(?:Z|[-+][0-9][0-9]?(?::[0-9][0-9])?))?)$");
    public static final Pattern VALUE = Pattern.compile("^(?:=)$");
    public static final Pattern YAML = Pattern.compile("^(?:!|&|\\*)$");
    protected Map<Character, List<ResolverTuple>> yamlImplicitResolvers = new HashMap<Character, List<ResolverTuple>>();

    protected void addImplicitResolvers() {
        this.addImplicitResolver(Tag.BOOL, BOOL, "yYnNtTfFoO");
        this.addImplicitResolver(Tag.INT, INT, "-+0123456789");
        this.addImplicitResolver(Tag.FLOAT, FLOAT, "-+0123456789.");
        this.addImplicitResolver(Tag.MERGE, MERGE, "<");
        this.addImplicitResolver(Tag.NULL, NULL, "~nN\u0000");
        this.addImplicitResolver(Tag.NULL, EMPTY, null);
        this.addImplicitResolver(Tag.TIMESTAMP, TIMESTAMP, "0123456789");
        this.addImplicitResolver(Tag.YAML, YAML, "!&*");
    }

    public Resolver() {
        this.addImplicitResolvers();
    }

    public void addImplicitResolver(Tag tag, Pattern regexp, String first) {
        if (first == null) {
            List<ResolverTuple> curr = this.yamlImplicitResolvers.get(null);
            if (curr == null) {
                curr = new ArrayList<ResolverTuple>();
                this.yamlImplicitResolvers.put(null, curr);
            }
            curr.add(new ResolverTuple(tag, regexp));
            return;
        }
        char[] chrs = first.toCharArray();
        int i = 0;
        int j = chrs.length;
        while (i < j) {
            List<ResolverTuple> curr;
            Character theC = Character.valueOf(chrs[i]);
            if (theC.charValue() == '\u0000') {
                theC = null;
            }
            if ((curr = this.yamlImplicitResolvers.get(theC)) == null) {
                curr = new ArrayList<ResolverTuple>();
                this.yamlImplicitResolvers.put(theC, curr);
            }
            curr.add(new ResolverTuple(tag, regexp));
            ++i;
        }
    }

    public Tag resolve(NodeId kind, String value, boolean implicit) {
        if (kind == NodeId.scalar && implicit) {
            Pattern regexp;
            Tag tag;
            List<ResolverTuple> resolvers = value.length() == 0 ? this.yamlImplicitResolvers.get(Character.valueOf('\u0000')) : this.yamlImplicitResolvers.get(Character.valueOf(value.charAt(0)));
            if (resolvers != null) {
                for (ResolverTuple v : resolvers) {
                    tag = v.getTag();
                    regexp = v.getRegexp();
                    if (!regexp.matcher(value).matches()) continue;
                    return tag;
                }
            }
            if (this.yamlImplicitResolvers.containsKey(null)) {
                for (ResolverTuple v : this.yamlImplicitResolvers.get(null)) {
                    tag = v.getTag();
                    regexp = v.getRegexp();
                    if (!regexp.matcher(value).matches()) continue;
                    return tag;
                }
            }
        }
        switch (1.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[kind.ordinal()]) {
            case 1: {
                return Tag.STR;
            }
            case 2: {
                return Tag.SEQ;
            }
        }
        return Tag.MAP;
    }

    static class 1 {
        static final /* synthetic */ int[] $SwitchMap$org$yaml$snakeyaml$nodes$NodeId;

        static {
            $SwitchMap$org$yaml$snakeyaml$nodes$NodeId = new int[NodeId.values().length];
            try {
                1.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[NodeId.scalar.ordinal()] = 1;
            }
            catch (NoSuchFieldError ex) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[NodeId.sequence.ordinal()] = 2;
                return;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }
}

