/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.MessagePattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MessagePatternUtil {
    private MessagePatternUtil() {
    }

    public static MessageNode buildMessageNode(String string) {
        return MessagePatternUtil.buildMessageNode(new MessagePattern(string));
    }

    public static MessageNode buildMessageNode(MessagePattern messagePattern) {
        int n = messagePattern.countParts() - 1;
        if (n < 0) {
            throw new IllegalArgumentException("The MessagePattern is empty");
        }
        if (messagePattern.getPartType(0) != MessagePattern.Part.Type.MSG_START) {
            throw new IllegalArgumentException("The MessagePattern does not represent a MessageFormat pattern");
        }
        return MessagePatternUtil.buildMessageNode(messagePattern, 0, n);
    }

    private static MessageNode buildMessageNode(MessagePattern messagePattern, int n, int n2) {
        int n3 = messagePattern.getPart(n).getLimit();
        MessageNode messageNode = new MessageNode(null);
        int n4 = n + 1;
        while (true) {
            MessagePattern.Part part;
            int n5;
            if (n3 < (n5 = (part = messagePattern.getPart(n4)).getIndex())) {
                MessageNode.access$500(messageNode, new TextNode(messagePattern.getPatternString().substring(n3, n5), null));
            }
            if (n4 == n2) break;
            MessagePattern.Part.Type type = part.getType();
            if (type == MessagePattern.Part.Type.ARG_START) {
                int n6 = messagePattern.getLimitPartIndex(n4);
                MessageNode.access$500(messageNode, MessagePatternUtil.buildArgNode(messagePattern, n4, n6));
                n4 = n6;
                part = messagePattern.getPart(n4);
            } else if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                MessageNode.access$500(messageNode, MessageContentsNode.access$600());
            }
            n3 = part.getLimit();
            ++n4;
        }
        return MessageNode.access$700(messageNode);
    }

    private static ArgNode buildArgNode(MessagePattern messagePattern, int n, int n2) {
        ArgNode argNode = ArgNode.access$800();
        MessagePattern.Part part = messagePattern.getPart(n);
        MessagePattern.ArgType argType = ArgNode.access$902(argNode, part.getArgType());
        part = messagePattern.getPart(++n);
        ArgNode.access$1002(argNode, messagePattern.getSubstring(part));
        if (part.getType() == MessagePattern.Part.Type.ARG_NUMBER) {
            ArgNode.access$1102(argNode, part.getValue());
        }
        ++n;
        switch (1.$SwitchMap$com$ibm$icu$text$MessagePattern$ArgType[argType.ordinal()]) {
            case 1: {
                ArgNode.access$1202(argNode, messagePattern.getSubstring(messagePattern.getPart(n++)));
                if (n >= n2) break;
                ArgNode.access$1302(argNode, messagePattern.getSubstring(messagePattern.getPart(n)));
                break;
            }
            case 2: {
                ArgNode.access$1202(argNode, "choice");
                ArgNode.access$1402(argNode, MessagePatternUtil.buildChoiceStyleNode(messagePattern, n, n2));
                break;
            }
            case 3: {
                ArgNode.access$1202(argNode, "plural");
                ArgNode.access$1402(argNode, MessagePatternUtil.buildPluralStyleNode(messagePattern, n, n2, argType));
                break;
            }
            case 4: {
                ArgNode.access$1202(argNode, "select");
                ArgNode.access$1402(argNode, MessagePatternUtil.buildSelectStyleNode(messagePattern, n, n2));
                break;
            }
            case 5: {
                ArgNode.access$1202(argNode, "selectordinal");
                ArgNode.access$1402(argNode, MessagePatternUtil.buildPluralStyleNode(messagePattern, n, n2, argType));
                break;
            }
        }
        return argNode;
    }

    private static ComplexArgStyleNode buildChoiceStyleNode(MessagePattern messagePattern, int n, int n2) {
        ComplexArgStyleNode complexArgStyleNode = new ComplexArgStyleNode(MessagePattern.ArgType.CHOICE, null);
        while (n < n2) {
            int n3 = n;
            MessagePattern.Part part = messagePattern.getPart(n);
            double d = messagePattern.getNumericValue(part);
            int n4 = messagePattern.getLimitPartIndex(n += 2);
            VariantNode variantNode = new VariantNode(null);
            VariantNode.access$1702(variantNode, messagePattern.getSubstring(messagePattern.getPart(n3 + 1)));
            VariantNode.access$1802(variantNode, d);
            VariantNode.access$1902(variantNode, MessagePatternUtil.buildMessageNode(messagePattern, n, n4));
            ComplexArgStyleNode.access$2000(complexArgStyleNode, variantNode);
            n = n4 + 1;
        }
        return ComplexArgStyleNode.access$2100(complexArgStyleNode);
    }

    private static ComplexArgStyleNode buildPluralStyleNode(MessagePattern messagePattern, int n, int n2, MessagePattern.ArgType argType) {
        ComplexArgStyleNode complexArgStyleNode = new ComplexArgStyleNode(argType, null);
        MessagePattern.Part part = messagePattern.getPart(n);
        if (part.getType().hasNumericValue()) {
            ComplexArgStyleNode.access$2202(complexArgStyleNode, true);
            ComplexArgStyleNode.access$2302(complexArgStyleNode, messagePattern.getNumericValue(part));
            ++n;
        }
        while (n < n2) {
            MessagePattern.Part part2 = messagePattern.getPart(n++);
            double d = -1.23456789E8;
            MessagePattern.Part part3 = messagePattern.getPart(n);
            if (part3.getType().hasNumericValue()) {
                d = messagePattern.getNumericValue(part3);
                ++n;
            }
            int n3 = messagePattern.getLimitPartIndex(n);
            VariantNode variantNode = new VariantNode(null);
            VariantNode.access$1702(variantNode, messagePattern.getSubstring(part2));
            VariantNode.access$1802(variantNode, d);
            VariantNode.access$1902(variantNode, MessagePatternUtil.buildMessageNode(messagePattern, n, n3));
            ComplexArgStyleNode.access$2000(complexArgStyleNode, variantNode);
            n = n3 + 1;
        }
        return ComplexArgStyleNode.access$2100(complexArgStyleNode);
    }

    private static ComplexArgStyleNode buildSelectStyleNode(MessagePattern messagePattern, int n, int n2) {
        ComplexArgStyleNode complexArgStyleNode = new ComplexArgStyleNode(MessagePattern.ArgType.SELECT, null);
        while (n < n2) {
            MessagePattern.Part part = messagePattern.getPart(n++);
            int n3 = messagePattern.getLimitPartIndex(n);
            VariantNode variantNode = new VariantNode(null);
            VariantNode.access$1702(variantNode, messagePattern.getSubstring(part));
            VariantNode.access$1902(variantNode, MessagePatternUtil.buildMessageNode(messagePattern, n, n3));
            ComplexArgStyleNode.access$2000(complexArgStyleNode, variantNode);
            n = n3 + 1;
        }
        return ComplexArgStyleNode.access$2100(complexArgStyleNode);
    }

    public static class VariantNode
    extends Node {
        private String selector;
        private double numericValue = -1.23456789E8;
        private MessageNode msgNode;

        public String getSelector() {
            return this.selector;
        }

        public boolean isSelectorNumeric() {
            return this.numericValue != -1.23456789E8;
        }

        public double getSelectorValue() {
            return this.numericValue;
        }

        public MessageNode getMessage() {
            return this.msgNode;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            if (this.isSelectorNumeric()) {
                stringBuilder.append(this.numericValue).append(" (").append(this.selector).append(") {");
            } else {
                stringBuilder.append(this.selector).append(" {");
            }
            return stringBuilder.append(this.msgNode.toString()).append('}').toString();
        }

        private VariantNode() {
            super(null);
        }

        VariantNode(1 var1_1) {
            this();
        }

        static String access$1702(VariantNode variantNode, String string) {
            variantNode.selector = string;
            return variantNode.selector;
        }

        static double access$1802(VariantNode variantNode, double d) {
            variantNode.numericValue = d;
            return variantNode.numericValue;
        }

        static MessageNode access$1902(VariantNode variantNode, MessageNode messageNode) {
            variantNode.msgNode = messageNode;
            return variantNode.msgNode;
        }
    }

    public static class ComplexArgStyleNode
    extends Node {
        private MessagePattern.ArgType argType;
        private double offset;
        private boolean explicitOffset;
        private volatile List<VariantNode> list = new ArrayList<VariantNode>();

        public MessagePattern.ArgType getArgType() {
            return this.argType;
        }

        public boolean hasExplicitOffset() {
            return this.explicitOffset;
        }

        public double getOffset() {
            return this.offset;
        }

        public List<VariantNode> getVariants() {
            return this.list;
        }

        public VariantNode getVariantsByType(List<VariantNode> list, List<VariantNode> list2) {
            if (list != null) {
                list.clear();
            }
            list2.clear();
            VariantNode variantNode = null;
            for (VariantNode variantNode2 : this.list) {
                if (variantNode2.isSelectorNumeric()) {
                    list.add(variantNode2);
                    continue;
                }
                if ("other".equals(variantNode2.getSelector())) {
                    if (variantNode != null) continue;
                    variantNode = variantNode2;
                    continue;
                }
                list2.add(variantNode2);
            }
            return variantNode;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('(').append(this.argType.toString()).append(" style) ");
            if (this.hasExplicitOffset()) {
                stringBuilder.append("offset:").append(this.offset).append(' ');
            }
            return stringBuilder.append(this.list.toString()).toString();
        }

        private ComplexArgStyleNode(MessagePattern.ArgType argType) {
            super(null);
            this.argType = argType;
        }

        private void addVariant(VariantNode variantNode) {
            this.list.add(variantNode);
        }

        private ComplexArgStyleNode freeze() {
            this.list = Collections.unmodifiableList(this.list);
            return this;
        }

        ComplexArgStyleNode(MessagePattern.ArgType argType, 1 var2_2) {
            this(argType);
        }

        static void access$2000(ComplexArgStyleNode complexArgStyleNode, VariantNode variantNode) {
            complexArgStyleNode.addVariant(variantNode);
        }

        static ComplexArgStyleNode access$2100(ComplexArgStyleNode complexArgStyleNode) {
            return complexArgStyleNode.freeze();
        }

        static boolean access$2202(ComplexArgStyleNode complexArgStyleNode, boolean bl) {
            complexArgStyleNode.explicitOffset = bl;
            return complexArgStyleNode.explicitOffset;
        }

        static double access$2302(ComplexArgStyleNode complexArgStyleNode, double d) {
            complexArgStyleNode.offset = d;
            return complexArgStyleNode.offset;
        }
    }

    public static class ArgNode
    extends MessageContentsNode {
        private MessagePattern.ArgType argType;
        private String name;
        private int number = -1;
        private String typeName;
        private String style;
        private ComplexArgStyleNode complexStyle;

        public MessagePattern.ArgType getArgType() {
            return this.argType;
        }

        public String getName() {
            return this.name;
        }

        public int getNumber() {
            return this.number;
        }

        public String getTypeName() {
            return this.typeName;
        }

        public String getSimpleStyle() {
            return this.style;
        }

        public ComplexArgStyleNode getComplexStyle() {
            return this.complexStyle;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('{').append(this.name);
            if (this.argType != MessagePattern.ArgType.NONE) {
                stringBuilder.append(',').append(this.typeName);
                if (this.argType == MessagePattern.ArgType.SIMPLE) {
                    if (this.style != null) {
                        stringBuilder.append(',').append(this.style);
                    }
                } else {
                    stringBuilder.append(',').append(this.complexStyle.toString());
                }
            }
            return stringBuilder.append('}').toString();
        }

        private ArgNode() {
            super(MessageContentsNode.Type.ARG, null);
        }

        private static ArgNode createArgNode() {
            return new ArgNode();
        }

        static ArgNode access$800() {
            return ArgNode.createArgNode();
        }

        static MessagePattern.ArgType access$902(ArgNode argNode, MessagePattern.ArgType argType) {
            argNode.argType = argType;
            return argNode.argType;
        }

        static String access$1002(ArgNode argNode, String string) {
            argNode.name = string;
            return argNode.name;
        }

        static int access$1102(ArgNode argNode, int n) {
            argNode.number = n;
            return argNode.number;
        }

        static String access$1202(ArgNode argNode, String string) {
            argNode.typeName = string;
            return argNode.typeName;
        }

        static String access$1302(ArgNode argNode, String string) {
            argNode.style = string;
            return argNode.style;
        }

        static ComplexArgStyleNode access$1402(ArgNode argNode, ComplexArgStyleNode complexArgStyleNode) {
            argNode.complexStyle = complexArgStyleNode;
            return argNode.complexStyle;
        }
    }

    public static class TextNode
    extends MessageContentsNode {
        private String text;

        public String getText() {
            return this.text;
        }

        @Override
        public String toString() {
            return "\u00ab" + this.text + "\u00bb";
        }

        private TextNode(String string) {
            super(MessageContentsNode.Type.TEXT, null);
            this.text = string;
        }

        static String access$102(TextNode textNode, String string) {
            textNode.text = string;
            return textNode.text;
        }

        static String access$100(TextNode textNode) {
            return textNode.text;
        }

        TextNode(String string, 1 var2_2) {
            this(string);
        }
    }

    public static class MessageContentsNode
    extends Node {
        private Type type;

        public Type getType() {
            return this.type;
        }

        public String toString() {
            return "{REPLACE_NUMBER}";
        }

        private MessageContentsNode(Type type) {
            super(null);
            this.type = type;
        }

        private static MessageContentsNode createReplaceNumberNode() {
            return new MessageContentsNode(Type.REPLACE_NUMBER);
        }

        MessageContentsNode(Type type, 1 var2_2) {
            this(type);
        }

        static MessageContentsNode access$600() {
            return MessageContentsNode.createReplaceNumberNode();
        }

        public static enum Type {
            TEXT,
            ARG,
            REPLACE_NUMBER;

        }
    }

    public static class MessageNode
    extends Node {
        private volatile List<MessageContentsNode> list = new ArrayList<MessageContentsNode>();

        public List<MessageContentsNode> getContents() {
            return this.list;
        }

        public String toString() {
            return this.list.toString();
        }

        private MessageNode() {
            super(null);
        }

        private void addContentsNode(MessageContentsNode messageContentsNode) {
            MessageContentsNode messageContentsNode2;
            if (messageContentsNode instanceof TextNode && !this.list.isEmpty() && (messageContentsNode2 = this.list.get(this.list.size() - 1)) instanceof TextNode) {
                TextNode textNode = (TextNode)messageContentsNode2;
                TextNode.access$102(textNode, TextNode.access$100(textNode) + TextNode.access$100((TextNode)messageContentsNode));
                return;
            }
            this.list.add(messageContentsNode);
        }

        private MessageNode freeze() {
            this.list = Collections.unmodifiableList(this.list);
            return this;
        }

        MessageNode(1 var1_1) {
            this();
        }

        static void access$500(MessageNode messageNode, MessageContentsNode messageContentsNode) {
            messageNode.addContentsNode(messageContentsNode);
        }

        static MessageNode access$700(MessageNode messageNode) {
            return messageNode.freeze();
        }
    }

    public static class Node {
        private Node() {
        }

        Node(1 var1_1) {
            this();
        }
    }
}

