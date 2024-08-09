/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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
    public static final Pattern FLOAT = Pattern.compile("^([-+]?(?:[0-9][0-9_]*)\\.[0-9_]*(?:[eE][-+]?[0-9]+)?|[-+]?(?:[0-9][0-9_]*)(?:[eE][-+]?[0-9]+)|[-+]?\\.[0-9_]+(?:[eE][-+]?[0-9]+)?|[-+]?[0-9][0-9_]*(?::[0-5]?[0-9])+\\.[0-9_]*|[-+]?\\.(?:inf|Inf|INF)|\\.(?:nan|NaN|NAN))$");
    public static final Pattern INT = Pattern.compile("^(?:[-+]?0b_*[0-1][0-1_]*|[-+]?0_*[0-7][0-7_]*|[-+]?(?:0|[1-9][0-9_]*)|[-+]?0x_*[0-9a-fA-F][0-9a-fA-F_]*|[-+]?[1-9][0-9_]*(?::[0-5]?[0-9])+)$");
    public static final Pattern MERGE = Pattern.compile("^(?:<<)$");
    public static final Pattern NULL = Pattern.compile("^(?:~|null|Null|NULL| )$");
    public static final Pattern EMPTY = Pattern.compile("^$");
    public static final Pattern TIMESTAMP = Pattern.compile("^(?:[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]|[0-9][0-9][0-9][0-9]-[0-9][0-9]?-[0-9][0-9]?(?:[Tt]|[ \t]+)[0-9][0-9]?:[0-9][0-9]:[0-9][0-9](?:\\.[0-9]*)?(?:[ \t]*(?:Z|[-+][0-9][0-9]?(?::[0-9][0-9])?))?)$");
    public static final Pattern VALUE = Pattern.compile("^(?:=)$");
    public static final Pattern YAML = Pattern.compile("^(?:!|&|\\*)$");
    protected Map<Character, List<ResolverTuple>> yamlImplicitResolvers = new HashMap<Character, List<ResolverTuple>>();

    protected void addImplicitResolvers() {
        this.addImplicitResolver(Tag.BOOL, BOOL, "yYnNtTfFoO", 10);
        this.addImplicitResolver(Tag.INT, INT, "-+0123456789");
        this.addImplicitResolver(Tag.FLOAT, FLOAT, "-+0123456789.");
        this.addImplicitResolver(Tag.MERGE, MERGE, "<", 10);
        this.addImplicitResolver(Tag.NULL, NULL, "~nN\u0000", 10);
        this.addImplicitResolver(Tag.NULL, EMPTY, null, 10);
        this.addImplicitResolver(Tag.TIMESTAMP, TIMESTAMP, "0123456789", 50);
        this.addImplicitResolver(Tag.YAML, YAML, "!&*", 10);
    }

    public Resolver() {
        this.addImplicitResolvers();
    }

    public void addImplicitResolver(Tag tag, Pattern pattern, String string) {
        this.addImplicitResolver(tag, pattern, string, 1024);
    }

    public void addImplicitResolver(Tag tag, Pattern pattern, String string, int n) {
        if (string == null) {
            List<ResolverTuple> list = this.yamlImplicitResolvers.get(null);
            if (list == null) {
                list = new ArrayList<ResolverTuple>();
                this.yamlImplicitResolvers.put(null, list);
            }
            list.add(new ResolverTuple(tag, pattern, n));
        } else {
            char[] cArray = string.toCharArray();
            int n2 = cArray.length;
            for (int i = 0; i < n2; ++i) {
                List<ResolverTuple> list;
                Character c = Character.valueOf(cArray[i]);
                if (c.charValue() == '\u0000') {
                    c = null;
                }
                if ((list = this.yamlImplicitResolvers.get(c)) == null) {
                    list = new ArrayList<ResolverTuple>();
                    this.yamlImplicitResolvers.put(c, list);
                }
                list.add(new ResolverTuple(tag, pattern, n));
            }
        }
    }

    public Tag resolve(NodeId nodeId, String string, boolean bl) {
        if (nodeId == NodeId.scalar && bl) {
            Pattern pattern;
            Tag tag;
            List<ResolverTuple> list = string.length() == 0 ? this.yamlImplicitResolvers.get(Character.valueOf('\u0000')) : this.yamlImplicitResolvers.get(Character.valueOf(string.charAt(0)));
            if (list != null) {
                for (ResolverTuple resolverTuple : list) {
                    tag = resolverTuple.getTag();
                    pattern = resolverTuple.getRegexp();
                    if (string.length() > resolverTuple.getLimit() || !pattern.matcher(string).matches()) continue;
                    return tag;
                }
            }
            if (this.yamlImplicitResolvers.containsKey(null)) {
                for (ResolverTuple resolverTuple : this.yamlImplicitResolvers.get(null)) {
                    tag = resolverTuple.getTag();
                    pattern = resolverTuple.getRegexp();
                    if (string.length() > resolverTuple.getLimit() || !pattern.matcher(string).matches()) continue;
                    return tag;
                }
            }
        }
        switch (1.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[nodeId.ordinal()]) {
            case 1: {
                return Tag.STR;
            }
            case 2: {
                return Tag.SEQ;
            }
        }
        return Tag.MAP;
    }
}

