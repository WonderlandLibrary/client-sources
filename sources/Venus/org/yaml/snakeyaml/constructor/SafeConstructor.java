/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.constructor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

public class SafeConstructor
extends BaseConstructor {
    public static final ConstructUndefined undefinedConstructor;
    private static final Map<String, Boolean> BOOL_VALUES;
    private static final int[][] RADIX_MAX;
    private static final Pattern TIMESTAMP_REGEXP;
    private static final Pattern YMD_REGEXP;

    public SafeConstructor(LoaderOptions loaderOptions) {
        super(loaderOptions);
        this.yamlConstructors.put(Tag.NULL, new ConstructYamlNull(this));
        this.yamlConstructors.put(Tag.BOOL, new ConstructYamlBool(this));
        this.yamlConstructors.put(Tag.INT, new ConstructYamlInt(this));
        this.yamlConstructors.put(Tag.FLOAT, new ConstructYamlFloat(this));
        this.yamlConstructors.put(Tag.BINARY, new ConstructYamlBinary(this));
        this.yamlConstructors.put(Tag.TIMESTAMP, new ConstructYamlTimestamp());
        this.yamlConstructors.put(Tag.OMAP, new ConstructYamlOmap(this));
        this.yamlConstructors.put(Tag.PAIRS, new ConstructYamlPairs(this));
        this.yamlConstructors.put(Tag.SET, new ConstructYamlSet(this));
        this.yamlConstructors.put(Tag.STR, new ConstructYamlStr(this));
        this.yamlConstructors.put(Tag.SEQ, new ConstructYamlSeq(this));
        this.yamlConstructors.put(Tag.MAP, new ConstructYamlMap(this));
        this.yamlConstructors.put(null, undefinedConstructor);
        this.yamlClassConstructors.put(NodeId.scalar, undefinedConstructor);
        this.yamlClassConstructors.put(NodeId.sequence, undefinedConstructor);
        this.yamlClassConstructors.put(NodeId.mapping, undefinedConstructor);
    }

    protected void flattenMapping(MappingNode mappingNode) {
        this.flattenMapping(mappingNode, true);
    }

    protected void flattenMapping(MappingNode mappingNode, boolean bl) {
        this.processDuplicateKeys(mappingNode, bl);
        if (mappingNode.isMerged()) {
            mappingNode.setValue(this.mergeNode(mappingNode, true, new HashMap<Object, Integer>(), new ArrayList<NodeTuple>(), bl));
        }
    }

    protected void processDuplicateKeys(MappingNode mappingNode) {
        this.processDuplicateKeys(mappingNode, true);
    }

    protected void processDuplicateKeys(MappingNode mappingNode, boolean bl) {
        List<NodeTuple> list = mappingNode.getValue();
        HashMap<Object, Integer> hashMap = new HashMap<Object, Integer>(list.size());
        TreeSet<Integer> treeSet = new TreeSet<Integer>();
        int n = 0;
        for (NodeTuple nodeTuple : list) {
            Node node = nodeTuple.getKeyNode();
            if (!node.getTag().equals(Tag.MERGE)) {
                Integer n2;
                Object object;
                if (bl) {
                    if (node instanceof ScalarNode) {
                        node.setType(String.class);
                        node.setTag(Tag.STR);
                    } else {
                        throw new YAMLException("Keys must be scalars but found: " + node);
                    }
                }
                if ((object = this.constructObject(node)) != null && !bl && node.isTwoStepsConstruction()) {
                    if (!this.loadingConfig.getAllowRecursiveKeys()) {
                        throw new YAMLException("Recursive key for mapping is detected but it is not configured to be allowed.");
                    }
                    try {
                        object.hashCode();
                    } catch (Exception exception) {
                        throw new ConstructorException("while constructing a mapping", mappingNode.getStartMark(), "found unacceptable key " + object, nodeTuple.getKeyNode().getStartMark(), exception);
                    }
                }
                if ((n2 = hashMap.put(object, n)) != null) {
                    if (!this.isAllowDuplicateKeys()) {
                        throw new DuplicateKeyException(mappingNode.getStartMark(), object, nodeTuple.getKeyNode().getStartMark());
                    }
                    treeSet.add(n2);
                }
            }
            ++n;
        }
        Iterator<NodeTuple> iterator2 = treeSet.descendingIterator();
        while (iterator2.hasNext()) {
            list.remove((Integer)((Object)iterator2.next()));
        }
    }

    private List<NodeTuple> mergeNode(MappingNode mappingNode, boolean bl, Map<Object, Integer> map, List<NodeTuple> list, boolean bl2) {
        Iterator<NodeTuple> iterator2 = mappingNode.getValue().iterator();
        block4: while (iterator2.hasNext()) {
            Object object;
            NodeTuple nodeTuple = iterator2.next();
            Node node = nodeTuple.getKeyNode();
            Node node2 = nodeTuple.getValueNode();
            if (node.getTag().equals(Tag.MERGE)) {
                iterator2.remove();
                switch (1.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[node2.getNodeId().ordinal()]) {
                    case 1: {
                        object = (MappingNode)node2;
                        this.mergeNode((MappingNode)object, false, map, list, bl2);
                        continue block4;
                    }
                    case 2: {
                        SequenceNode sequenceNode = (SequenceNode)node2;
                        List<Node> list2 = sequenceNode.getValue();
                        for (Node node3 : list2) {
                            if (!(node3 instanceof MappingNode)) {
                                throw new ConstructorException("while constructing a mapping", mappingNode.getStartMark(), "expected a mapping for merging, but found " + (Object)((Object)node3.getNodeId()), node3.getStartMark());
                            }
                            MappingNode mappingNode2 = (MappingNode)node3;
                            this.mergeNode(mappingNode2, false, map, list, bl2);
                        }
                        continue block4;
                    }
                    default: {
                        throw new ConstructorException("while constructing a mapping", mappingNode.getStartMark(), "expected a mapping or list of mappings for merging, but found " + (Object)((Object)node2.getNodeId()), node2.getStartMark());
                    }
                }
            }
            if (bl2) {
                if (node instanceof ScalarNode) {
                    node.setType(String.class);
                    node.setTag(Tag.STR);
                } else {
                    throw new YAMLException("Keys must be scalars but found: " + node);
                }
            }
            if (!map.containsKey(object = this.constructObject(node))) {
                list.add(nodeTuple);
                map.put(object, list.size() - 1);
                continue;
            }
            if (!bl) continue;
            list.set(map.get(object), nodeTuple);
        }
        return list;
    }

    @Override
    protected void constructMapping2ndStep(MappingNode mappingNode, Map<Object, Object> map) {
        this.flattenMapping(mappingNode);
        super.constructMapping2ndStep(mappingNode, map);
    }

    @Override
    protected void constructSet2ndStep(MappingNode mappingNode, Set<Object> set) {
        this.flattenMapping(mappingNode);
        super.constructSet2ndStep(mappingNode, set);
    }

    private static int maxLen(int n, int n2) {
        return Integer.toString(n, n2).length();
    }

    private static int maxLen(long l, int n) {
        return Long.toString(l, n).length();
    }

    private Number createNumber(int n, String string, int n2) {
        Number number;
        int[] nArray;
        int n3;
        int n4 = n3 = string != null ? string.length() : 0;
        if (n < 0) {
            string = "-" + string;
        }
        int[] nArray2 = nArray = n2 < RADIX_MAX.length ? RADIX_MAX[n2] : null;
        if (nArray != null) {
            boolean bl;
            boolean bl2 = bl = n3 > nArray[0];
            if (bl) {
                if (n3 > nArray[1]) {
                    return new BigInteger(string, n2);
                }
                return SafeConstructor.createLongOrBigInteger(string, n2);
            }
        }
        try {
            number = Integer.valueOf(string, n2);
        } catch (NumberFormatException numberFormatException) {
            number = SafeConstructor.createLongOrBigInteger(string, n2);
        }
        return number;
    }

    protected static Number createLongOrBigInteger(String string, int n) {
        try {
            return Long.valueOf(string, n);
        } catch (NumberFormatException numberFormatException) {
            return new BigInteger(string, n);
        }
    }

    static Map access$000() {
        return BOOL_VALUES;
    }

    static Number access$100(SafeConstructor safeConstructor, int n, String string, int n2) {
        return safeConstructor.createNumber(n, string, n2);
    }

    static Pattern access$200() {
        return YMD_REGEXP;
    }

    static Pattern access$300() {
        return TIMESTAMP_REGEXP;
    }

    static {
        int[] nArray;
        undefinedConstructor = new ConstructUndefined();
        BOOL_VALUES = new HashMap<String, Boolean>();
        BOOL_VALUES.put("yes", Boolean.TRUE);
        BOOL_VALUES.put("no", Boolean.FALSE);
        BOOL_VALUES.put("true", Boolean.TRUE);
        BOOL_VALUES.put("false", Boolean.FALSE);
        BOOL_VALUES.put("on", Boolean.TRUE);
        BOOL_VALUES.put("off", Boolean.FALSE);
        RADIX_MAX = new int[17][2];
        for (int n : nArray = new int[]{2, 8, 10, 16}) {
            SafeConstructor.RADIX_MAX[n] = new int[]{SafeConstructor.maxLen(Integer.MAX_VALUE, n), SafeConstructor.maxLen(Long.MAX_VALUE, n)};
        }
        TIMESTAMP_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)(?:(?:[Tt]|[ \t]+)([0-9][0-9]?):([0-9][0-9]):([0-9][0-9])(?:\\.([0-9]*))?(?:[ \t]*(?:Z|([-+][0-9][0-9]?)(?::([0-9][0-9])?)?))?)?$");
        YMD_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)$");
    }

    public static final class ConstructUndefined
    extends AbstractConstruct {
        @Override
        public Object construct(Node node) {
            throw new ConstructorException(null, null, "could not determine a constructor for the tag " + node.getTag(), node.getStartMark());
        }
    }

    public class ConstructYamlMap
    implements Construct {
        final SafeConstructor this$0;

        public ConstructYamlMap(SafeConstructor safeConstructor) {
            this.this$0 = safeConstructor;
        }

        @Override
        public Object construct(Node node) {
            MappingNode mappingNode = (MappingNode)node;
            if (node.isTwoStepsConstruction()) {
                return this.this$0.createDefaultMap(mappingNode.getValue().size());
            }
            return this.this$0.constructMapping(mappingNode);
        }

        @Override
        public void construct2ndStep(Node node, Object object) {
            if (!node.isTwoStepsConstruction()) {
                throw new YAMLException("Unexpected recursive mapping structure. Node: " + node);
            }
            this.this$0.constructMapping2ndStep((MappingNode)node, (Map)object);
        }
    }

    public class ConstructYamlSeq
    implements Construct {
        final SafeConstructor this$0;

        public ConstructYamlSeq(SafeConstructor safeConstructor) {
            this.this$0 = safeConstructor;
        }

        @Override
        public Object construct(Node node) {
            SequenceNode sequenceNode = (SequenceNode)node;
            if (node.isTwoStepsConstruction()) {
                return this.this$0.newList(sequenceNode);
            }
            return this.this$0.constructSequence(sequenceNode);
        }

        @Override
        public void construct2ndStep(Node node, Object object) {
            if (!node.isTwoStepsConstruction()) {
                throw new YAMLException("Unexpected recursive sequence structure. Node: " + node);
            }
            this.this$0.constructSequenceStep2((SequenceNode)node, (List)object);
        }
    }

    public class ConstructYamlStr
    extends AbstractConstruct {
        final SafeConstructor this$0;

        public ConstructYamlStr(SafeConstructor safeConstructor) {
            this.this$0 = safeConstructor;
        }

        @Override
        public Object construct(Node node) {
            return this.this$0.constructScalar((ScalarNode)node);
        }
    }

    public class ConstructYamlSet
    implements Construct {
        final SafeConstructor this$0;

        public ConstructYamlSet(SafeConstructor safeConstructor) {
            this.this$0 = safeConstructor;
        }

        @Override
        public Object construct(Node node) {
            if (node.isTwoStepsConstruction()) {
                return this.this$0.constructedObjects.containsKey(node) ? this.this$0.constructedObjects.get(node) : this.this$0.createDefaultSet(((MappingNode)node).getValue().size());
            }
            return this.this$0.constructSet((MappingNode)node);
        }

        @Override
        public void construct2ndStep(Node node, Object object) {
            if (!node.isTwoStepsConstruction()) {
                throw new YAMLException("Unexpected recursive set structure. Node: " + node);
            }
            this.this$0.constructSet2ndStep((MappingNode)node, (Set)object);
        }
    }

    public class ConstructYamlPairs
    extends AbstractConstruct {
        final SafeConstructor this$0;

        public ConstructYamlPairs(SafeConstructor safeConstructor) {
            this.this$0 = safeConstructor;
        }

        @Override
        public Object construct(Node node) {
            if (!(node instanceof SequenceNode)) {
                throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a sequence, but found " + (Object)((Object)node.getNodeId()), node.getStartMark());
            }
            SequenceNode sequenceNode = (SequenceNode)node;
            ArrayList<Object[]> arrayList = new ArrayList<Object[]>(sequenceNode.getValue().size());
            for (Node node2 : sequenceNode.getValue()) {
                if (!(node2 instanceof MappingNode)) {
                    throw new ConstructorException("while constructingpairs", node.getStartMark(), "expected a mapping of length 1, but found " + (Object)((Object)node2.getNodeId()), node2.getStartMark());
                }
                MappingNode mappingNode = (MappingNode)node2;
                if (mappingNode.getValue().size() != 1) {
                    throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a single mapping item, but found " + mappingNode.getValue().size() + " items", mappingNode.getStartMark());
                }
                Node node3 = mappingNode.getValue().get(0).getKeyNode();
                Node node4 = mappingNode.getValue().get(0).getValueNode();
                Object object = this.this$0.constructObject(node3);
                Object object2 = this.this$0.constructObject(node4);
                arrayList.add(new Object[]{object, object2});
            }
            return arrayList;
        }
    }

    public class ConstructYamlOmap
    extends AbstractConstruct {
        final SafeConstructor this$0;

        public ConstructYamlOmap(SafeConstructor safeConstructor) {
            this.this$0 = safeConstructor;
        }

        @Override
        public Object construct(Node node) {
            LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
            if (!(node instanceof SequenceNode)) {
                throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a sequence, but found " + (Object)((Object)node.getNodeId()), node.getStartMark());
            }
            SequenceNode sequenceNode = (SequenceNode)node;
            for (Node node2 : sequenceNode.getValue()) {
                if (!(node2 instanceof MappingNode)) {
                    throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a mapping of length 1, but found " + (Object)((Object)node2.getNodeId()), node2.getStartMark());
                }
                MappingNode mappingNode = (MappingNode)node2;
                if (mappingNode.getValue().size() != 1) {
                    throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a single mapping item, but found " + mappingNode.getValue().size() + " items", mappingNode.getStartMark());
                }
                Node node3 = mappingNode.getValue().get(0).getKeyNode();
                Node node4 = mappingNode.getValue().get(0).getValueNode();
                Object object = this.this$0.constructObject(node3);
                Object object2 = this.this$0.constructObject(node4);
                linkedHashMap.put(object, object2);
            }
            return linkedHashMap;
        }
    }

    public static class ConstructYamlTimestamp
    extends AbstractConstruct {
        private Calendar calendar;

        public Calendar getCalendar() {
            return this.calendar;
        }

        @Override
        public Object construct(Node node) {
            TimeZone timeZone;
            ScalarNode scalarNode = (ScalarNode)node;
            String string = scalarNode.getValue();
            Matcher matcher = SafeConstructor.access$200().matcher(string);
            if (matcher.matches()) {
                String string2 = matcher.group(1);
                String string3 = matcher.group(2);
                String string4 = matcher.group(3);
                this.calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                this.calendar.clear();
                this.calendar.set(1, Integer.parseInt(string2));
                this.calendar.set(2, Integer.parseInt(string3) - 1);
                this.calendar.set(5, Integer.parseInt(string4));
                return this.calendar.getTime();
            }
            matcher = SafeConstructor.access$300().matcher(string);
            if (!matcher.matches()) {
                throw new YAMLException("Unexpected timestamp: " + string);
            }
            String string5 = matcher.group(1);
            String string6 = matcher.group(2);
            String string7 = matcher.group(3);
            String string8 = matcher.group(4);
            String string9 = matcher.group(5);
            String string10 = matcher.group(6);
            String string11 = matcher.group(7);
            if (string11 != null) {
                string10 = string10 + "." + string11;
            }
            double d = Double.parseDouble(string10);
            int n = (int)Math.round(Math.floor(d));
            int n2 = (int)Math.round((d - (double)n) * 1000.0);
            String string12 = matcher.group(8);
            String string13 = matcher.group(9);
            if (string12 != null) {
                String string14 = string13 != null ? ":" + string13 : "00";
                timeZone = TimeZone.getTimeZone("GMT" + string12 + string14);
            } else {
                timeZone = TimeZone.getTimeZone("UTC");
            }
            this.calendar = Calendar.getInstance(timeZone);
            this.calendar.set(1, Integer.parseInt(string5));
            this.calendar.set(2, Integer.parseInt(string6) - 1);
            this.calendar.set(5, Integer.parseInt(string7));
            this.calendar.set(11, Integer.parseInt(string8));
            this.calendar.set(12, Integer.parseInt(string9));
            this.calendar.set(13, n);
            this.calendar.set(14, n2);
            return this.calendar.getTime();
        }
    }

    public class ConstructYamlBinary
    extends AbstractConstruct {
        final SafeConstructor this$0;

        public ConstructYamlBinary(SafeConstructor safeConstructor) {
            this.this$0 = safeConstructor;
        }

        @Override
        public Object construct(Node node) {
            String string = this.this$0.constructScalar((ScalarNode)node).replaceAll("\\s", "");
            byte[] byArray = Base64Coder.decode(string.toCharArray());
            return byArray;
        }
    }

    public class ConstructYamlFloat
    extends AbstractConstruct {
        final SafeConstructor this$0;

        public ConstructYamlFloat(SafeConstructor safeConstructor) {
            this.this$0 = safeConstructor;
        }

        @Override
        public Object construct(Node node) {
            String string = this.this$0.constructScalar((ScalarNode)node).replaceAll("_", "");
            if (string.isEmpty()) {
                throw new ConstructorException("while constructing a float", node.getStartMark(), "found empty value", node.getStartMark());
            }
            int n = 1;
            char c = string.charAt(0);
            if (c == '-') {
                n = -1;
                string = string.substring(1);
            } else if (c == '+') {
                string = string.substring(1);
            }
            String string2 = string.toLowerCase();
            if (".inf".equals(string2)) {
                return n == -1 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            }
            if (".nan".equals(string2)) {
                return Double.NaN;
            }
            if (string.indexOf(58) != -1) {
                String[] stringArray = string.split(":");
                int n2 = 1;
                double d = 0.0;
                int n3 = stringArray.length;
                for (int i = 0; i < n3; ++i) {
                    d += Double.parseDouble(stringArray[n3 - i - 1]) * (double)n2;
                    n2 *= 60;
                }
                return (double)n * d;
            }
            Double d = Double.valueOf(string);
            return d * (double)n;
        }
    }

    public class ConstructYamlInt
    extends AbstractConstruct {
        final SafeConstructor this$0;

        public ConstructYamlInt(SafeConstructor safeConstructor) {
            this.this$0 = safeConstructor;
        }

        @Override
        public Object construct(Node node) {
            String string = this.this$0.constructScalar((ScalarNode)node).replaceAll("_", "");
            if (string.isEmpty()) {
                throw new ConstructorException("while constructing an int", node.getStartMark(), "found empty value", node.getStartMark());
            }
            int n = 1;
            char c = string.charAt(0);
            if (c == '-') {
                n = -1;
                string = string.substring(1);
            } else if (c == '+') {
                string = string.substring(1);
            }
            int n2 = 10;
            if ("0".equals(string)) {
                return 0;
            }
            if (string.startsWith("0b")) {
                string = string.substring(2);
                n2 = 2;
            } else if (string.startsWith("0x")) {
                string = string.substring(2);
                n2 = 16;
            } else if (string.startsWith("0")) {
                string = string.substring(1);
                n2 = 8;
            } else {
                if (string.indexOf(58) != -1) {
                    String[] stringArray = string.split(":");
                    int n3 = 1;
                    int n4 = 0;
                    int n5 = stringArray.length;
                    for (int i = 0; i < n5; ++i) {
                        n4 = (int)((long)n4 + Long.parseLong(stringArray[n5 - i - 1]) * (long)n3);
                        n3 *= 60;
                    }
                    return SafeConstructor.access$100(this.this$0, n, String.valueOf(n4), 10);
                }
                return SafeConstructor.access$100(this.this$0, n, string, 10);
            }
            return SafeConstructor.access$100(this.this$0, n, string, n2);
        }
    }

    public class ConstructYamlBool
    extends AbstractConstruct {
        final SafeConstructor this$0;

        public ConstructYamlBool(SafeConstructor safeConstructor) {
            this.this$0 = safeConstructor;
        }

        @Override
        public Object construct(Node node) {
            String string = this.this$0.constructScalar((ScalarNode)node);
            return SafeConstructor.access$000().get(string.toLowerCase());
        }
    }

    public class ConstructYamlNull
    extends AbstractConstruct {
        final SafeConstructor this$0;

        public ConstructYamlNull(SafeConstructor safeConstructor) {
            this.this$0 = safeConstructor;
        }

        @Override
        public Object construct(Node node) {
            if (node != null) {
                this.this$0.constructScalar((ScalarNode)node);
            }
            return null;
        }
    }
}

