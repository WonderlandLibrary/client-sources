/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.command.CommandSource;
import net.minecraft.nbt.CollectionNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.mutable.MutableBoolean;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class NBTPathArgument
implements ArgumentType<NBTPath> {
    private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo.bar", "foo[0]", "[0]", "[]", "{foo=bar}");
    public static final SimpleCommandExceptionType PATH_MALFORMED = new SimpleCommandExceptionType(new TranslationTextComponent("arguments.nbtpath.node.invalid"));
    public static final DynamicCommandExceptionType NOTHING_FOUND = new DynamicCommandExceptionType(NBTPathArgument::lambda$static$0);

    public static NBTPathArgument nbtPath() {
        return new NBTPathArgument();
    }

    public static NBTPath getNBTPath(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, NBTPath.class);
    }

    @Override
    public NBTPath parse(StringReader stringReader) throws CommandSyntaxException {
        ArrayList<INode> arrayList = Lists.newArrayList();
        int n = stringReader.getCursor();
        Object2IntOpenHashMap<INode> object2IntOpenHashMap = new Object2IntOpenHashMap<INode>();
        boolean bl = true;
        while (stringReader.canRead() && stringReader.peek() != ' ') {
            char c;
            INode iNode = NBTPathArgument.func_218079_a(stringReader, bl);
            arrayList.add(iNode);
            object2IntOpenHashMap.put(iNode, stringReader.getCursor() - n);
            bl = false;
            if (!stringReader.canRead() || (c = stringReader.peek()) == ' ' || c == '[' || c == '{') continue;
            stringReader.expect('.');
        }
        return new NBTPath(stringReader.getString().substring(n, stringReader.getCursor()), arrayList.toArray(new INode[0]), object2IntOpenHashMap);
    }

    private static INode func_218079_a(StringReader stringReader, boolean bl) throws CommandSyntaxException {
        switch (stringReader.peek()) {
            case '\"': {
                String string = stringReader.readString();
                return NBTPathArgument.func_218083_a(stringReader, string);
            }
            case '[': {
                stringReader.skip();
                char c = stringReader.peek();
                if (c == '{') {
                    CompoundNBT compoundNBT = new JsonToNBT(stringReader).readStruct();
                    stringReader.expect(']');
                    return new ListNode(compoundNBT);
                }
                if (c == ']') {
                    stringReader.skip();
                    return EmptyListNode.field_218067_a;
                }
                int n = stringReader.readInt();
                stringReader.expect(']');
                return new CollectionNode(n);
            }
            case '{': {
                if (!bl) {
                    throw PATH_MALFORMED.createWithContext(stringReader);
                }
                CompoundNBT compoundNBT = new JsonToNBT(stringReader).readStruct();
                return new CompoundNode(compoundNBT);
            }
        }
        String string = NBTPathArgument.readTagName(stringReader);
        return NBTPathArgument.func_218083_a(stringReader, string);
    }

    private static INode func_218083_a(StringReader stringReader, String string) throws CommandSyntaxException {
        if (stringReader.canRead() && stringReader.peek() == '{') {
            CompoundNBT compoundNBT = new JsonToNBT(stringReader).readStruct();
            return new JsonNode(string, compoundNBT);
        }
        return new StringNode(string);
    }

    private static String readTagName(StringReader stringReader) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        while (stringReader.canRead() && NBTPathArgument.isSimpleNameChar(stringReader.peek())) {
            stringReader.skip();
        }
        if (stringReader.getCursor() == n) {
            throw PATH_MALFORMED.createWithContext(stringReader);
        }
        return stringReader.getString().substring(n, stringReader.getCursor());
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    private static boolean isSimpleNameChar(char c) {
        return c != ' ' && c != '\"' && c != '[' && c != ']' && c != '.' && c != '{' && c != '}';
    }

    private static Predicate<INBT> equalToCompoundPredicate(CompoundNBT compoundNBT) {
        return arg_0 -> NBTPathArgument.lambda$equalToCompoundPredicate$1(compoundNBT, arg_0);
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static boolean lambda$equalToCompoundPredicate$1(CompoundNBT compoundNBT, INBT iNBT) {
        return NBTUtil.areNBTEquals(compoundNBT, iNBT, true);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("arguments.nbtpath.nothing_found", object);
    }

    public static class NBTPath {
        private final String rawText;
        private final Object2IntMap<INode> field_218078_b;
        private final INode[] nodes;

        public NBTPath(String string, INode[] iNodeArray, Object2IntMap<INode> object2IntMap) {
            this.rawText = string;
            this.nodes = iNodeArray;
            this.field_218078_b = object2IntMap;
        }

        public List<INBT> func_218071_a(INBT iNBT) throws CommandSyntaxException {
            List<INBT> list = Collections.singletonList(iNBT);
            for (INode iNode : this.nodes) {
                if (!(list = iNode.func_218056_a(list)).isEmpty()) continue;
                throw this.createNothingFoundException(iNode);
            }
            return list;
        }

        public int func_218069_b(INBT iNBT) {
            List<INBT> list = Collections.singletonList(iNBT);
            for (INode iNode : this.nodes) {
                if (!(list = iNode.func_218056_a(list)).isEmpty()) continue;
                return 1;
            }
            return list.size();
        }

        private List<INBT> func_218072_d(INBT iNBT) throws CommandSyntaxException {
            List<INBT> list = Collections.singletonList(iNBT);
            for (int i = 0; i < this.nodes.length - 1; ++i) {
                INode iNode = this.nodes[i];
                int n = i + 1;
                if (!(list = iNode.func_218052_a(list, this.nodes[n]::createEmptyElement)).isEmpty()) continue;
                throw this.createNothingFoundException(iNode);
            }
            return list;
        }

        public List<INBT> func_218073_a(INBT iNBT, Supplier<INBT> supplier) throws CommandSyntaxException {
            List<INBT> list = this.func_218072_d(iNBT);
            INode iNode = this.nodes[this.nodes.length - 1];
            return iNode.func_218052_a(list, supplier);
        }

        private static int reduceToInt(List<INBT> list, Function<INBT, Integer> function) {
            return list.stream().map(function).reduce(0, NBTPath::lambda$reduceToInt$0);
        }

        public int func_218076_b(INBT iNBT, Supplier<INBT> supplier) throws CommandSyntaxException {
            List<INBT> list = this.func_218072_d(iNBT);
            INode iNode = this.nodes[this.nodes.length - 1];
            return NBTPath.reduceToInt(list, arg_0 -> NBTPath.lambda$func_218076_b$1(iNode, supplier, arg_0));
        }

        public int func_218068_c(INBT iNBT) {
            List<INBT> list = Collections.singletonList(iNBT);
            for (int i = 0; i < this.nodes.length - 1; ++i) {
                list = this.nodes[i].func_218056_a(list);
            }
            INode iNode = this.nodes[this.nodes.length - 1];
            return NBTPath.reduceToInt(list, iNode::func_218053_a);
        }

        private CommandSyntaxException createNothingFoundException(INode iNode) {
            int n = this.field_218078_b.getInt(iNode);
            return NOTHING_FOUND.create(this.rawText.substring(0, n));
        }

        public String toString() {
            return this.rawText;
        }

        private static Integer lambda$func_218076_b$1(INode iNode, Supplier supplier, INBT iNBT) {
            return iNode.func_218051_a(iNBT, supplier);
        }

        private static Integer lambda$reduceToInt$0(Integer n, Integer n2) {
            return n + n2;
        }
    }

    static interface INode {
        public void addMatchingElements(INBT var1, List<INBT> var2);

        public void func_218054_a(INBT var1, Supplier<INBT> var2, List<INBT> var3);

        public INBT createEmptyElement();

        public int func_218051_a(INBT var1, Supplier<INBT> var2);

        public int func_218053_a(INBT var1);

        default public List<INBT> func_218056_a(List<INBT> list) {
            return this.func_218057_a(list, this::addMatchingElements);
        }

        default public List<INBT> func_218052_a(List<INBT> list, Supplier<INBT> supplier) {
            return this.func_218057_a(list, (arg_0, arg_1) -> this.lambda$func_218052_a$0(supplier, arg_0, arg_1));
        }

        default public List<INBT> func_218057_a(List<INBT> list, BiConsumer<INBT, List<INBT>> biConsumer) {
            ArrayList<INBT> arrayList = Lists.newArrayList();
            for (INBT iNBT : list) {
                biConsumer.accept(iNBT, arrayList);
            }
            return arrayList;
        }

        private void lambda$func_218052_a$0(Supplier supplier, INBT iNBT, List list) {
            this.func_218054_a(iNBT, supplier, list);
        }
    }

    static class ListNode
    implements INode {
        private final CompoundNBT compound;
        private final Predicate<INBT> equalToPredicate;

        public ListNode(CompoundNBT compoundNBT) {
            this.compound = compoundNBT;
            this.equalToPredicate = NBTPathArgument.equalToCompoundPredicate(compoundNBT);
        }

        @Override
        public void addMatchingElements(INBT iNBT, List<INBT> list) {
            if (iNBT instanceof ListNBT) {
                ListNBT listNBT = (ListNBT)iNBT;
                listNBT.stream().filter(this.equalToPredicate).forEach(list::add);
            }
        }

        @Override
        public void func_218054_a(INBT iNBT, Supplier<INBT> supplier, List<INBT> list) {
            MutableBoolean mutableBoolean = new MutableBoolean();
            if (iNBT instanceof ListNBT) {
                ListNBT listNBT = (ListNBT)iNBT;
                listNBT.stream().filter(this.equalToPredicate).forEach(arg_0 -> ListNode.lambda$func_218054_a$0(list, mutableBoolean, arg_0));
                if (mutableBoolean.isFalse()) {
                    CompoundNBT compoundNBT = this.compound.copy();
                    listNBT.add(compoundNBT);
                    list.add(compoundNBT);
                }
            }
        }

        @Override
        public INBT createEmptyElement() {
            return new ListNBT();
        }

        @Override
        public int func_218051_a(INBT iNBT, Supplier<INBT> supplier) {
            int n = 0;
            if (iNBT instanceof ListNBT) {
                ListNBT listNBT = (ListNBT)iNBT;
                int n2 = listNBT.size();
                if (n2 == 0) {
                    listNBT.add(supplier.get());
                    ++n;
                } else {
                    for (int i = 0; i < n2; ++i) {
                        INBT iNBT2;
                        INBT iNBT3 = listNBT.get(i);
                        if (!this.equalToPredicate.test(iNBT3) || (iNBT2 = supplier.get()).equals(iNBT3) || !listNBT.setNBTByIndex(i, iNBT2)) continue;
                        ++n;
                    }
                }
            }
            return n;
        }

        @Override
        public int func_218053_a(INBT iNBT) {
            int n = 0;
            if (iNBT instanceof ListNBT) {
                ListNBT listNBT = (ListNBT)iNBT;
                for (int i = listNBT.size() - 1; i >= 0; --i) {
                    if (!this.equalToPredicate.test(listNBT.get(i))) continue;
                    listNBT.remove(i);
                    ++n;
                }
            }
            return n;
        }

        private static void lambda$func_218054_a$0(List list, MutableBoolean mutableBoolean, INBT iNBT) {
            list.add(iNBT);
            mutableBoolean.setTrue();
        }
    }

    static class EmptyListNode
    implements INode {
        public static final EmptyListNode field_218067_a = new EmptyListNode();

        private EmptyListNode() {
        }

        @Override
        public void addMatchingElements(INBT iNBT, List<INBT> list) {
            if (iNBT instanceof CollectionNBT) {
                list.addAll((CollectionNBT)iNBT);
            }
        }

        @Override
        public void func_218054_a(INBT iNBT, Supplier<INBT> supplier, List<INBT> list) {
            if (iNBT instanceof CollectionNBT) {
                CollectionNBT collectionNBT = (CollectionNBT)iNBT;
                if (collectionNBT.isEmpty()) {
                    INBT iNBT2 = supplier.get();
                    if (collectionNBT.addNBTByIndex(0, iNBT2)) {
                        list.add(iNBT2);
                    }
                } else {
                    list.addAll(collectionNBT);
                }
            }
        }

        @Override
        public INBT createEmptyElement() {
            return new ListNBT();
        }

        @Override
        public int func_218051_a(INBT iNBT, Supplier<INBT> supplier) {
            if (!(iNBT instanceof CollectionNBT)) {
                return 1;
            }
            CollectionNBT collectionNBT = (CollectionNBT)iNBT;
            int n = collectionNBT.size();
            if (n == 0) {
                collectionNBT.addNBTByIndex(0, supplier.get());
                return 0;
            }
            INBT iNBT2 = supplier.get();
            int n2 = n - (int)collectionNBT.stream().filter(iNBT2::equals).count();
            if (n2 == 0) {
                return 1;
            }
            collectionNBT.clear();
            if (!collectionNBT.addNBTByIndex(0, iNBT2)) {
                return 1;
            }
            for (int i = 1; i < n; ++i) {
                collectionNBT.addNBTByIndex(i, supplier.get());
            }
            return n2;
        }

        @Override
        public int func_218053_a(INBT iNBT) {
            CollectionNBT collectionNBT;
            int n;
            if (iNBT instanceof CollectionNBT && (n = (collectionNBT = (CollectionNBT)iNBT).size()) > 0) {
                collectionNBT.clear();
                return n;
            }
            return 1;
        }
    }

    static class CollectionNode
    implements INode {
        private final int field_218059_a;

        public CollectionNode(int n) {
            this.field_218059_a = n;
        }

        @Override
        public void addMatchingElements(INBT iNBT, List<INBT> list) {
            if (iNBT instanceof CollectionNBT) {
                int n;
                CollectionNBT collectionNBT = (CollectionNBT)iNBT;
                int n2 = collectionNBT.size();
                int n3 = n = this.field_218059_a < 0 ? n2 + this.field_218059_a : this.field_218059_a;
                if (0 <= n && n < n2) {
                    list.add((INBT)collectionNBT.get(n));
                }
            }
        }

        @Override
        public void func_218054_a(INBT iNBT, Supplier<INBT> supplier, List<INBT> list) {
            this.addMatchingElements(iNBT, list);
        }

        @Override
        public INBT createEmptyElement() {
            return new ListNBT();
        }

        @Override
        public int func_218051_a(INBT iNBT, Supplier<INBT> supplier) {
            if (iNBT instanceof CollectionNBT) {
                int n;
                CollectionNBT collectionNBT = (CollectionNBT)iNBT;
                int n2 = collectionNBT.size();
                int n3 = n = this.field_218059_a < 0 ? n2 + this.field_218059_a : this.field_218059_a;
                if (0 <= n && n < n2) {
                    INBT iNBT2 = (INBT)collectionNBT.get(n);
                    INBT iNBT3 = supplier.get();
                    if (!iNBT3.equals(iNBT2) && collectionNBT.setNBTByIndex(n, iNBT3)) {
                        return 0;
                    }
                }
            }
            return 1;
        }

        @Override
        public int func_218053_a(INBT iNBT) {
            if (iNBT instanceof CollectionNBT) {
                int n;
                CollectionNBT collectionNBT = (CollectionNBT)iNBT;
                int n2 = collectionNBT.size();
                int n3 = n = this.field_218059_a < 0 ? n2 + this.field_218059_a : this.field_218059_a;
                if (0 <= n && n < n2) {
                    collectionNBT.remove(n);
                    return 0;
                }
            }
            return 1;
        }
    }

    static class CompoundNode
    implements INode {
        private final Predicate<INBT> field_218066_a;

        public CompoundNode(CompoundNBT compoundNBT) {
            this.field_218066_a = NBTPathArgument.equalToCompoundPredicate(compoundNBT);
        }

        @Override
        public void addMatchingElements(INBT iNBT, List<INBT> list) {
            if (iNBT instanceof CompoundNBT && this.field_218066_a.test(iNBT)) {
                list.add(iNBT);
            }
        }

        @Override
        public void func_218054_a(INBT iNBT, Supplier<INBT> supplier, List<INBT> list) {
            this.addMatchingElements(iNBT, list);
        }

        @Override
        public INBT createEmptyElement() {
            return new CompoundNBT();
        }

        @Override
        public int func_218051_a(INBT iNBT, Supplier<INBT> supplier) {
            return 1;
        }

        @Override
        public int func_218053_a(INBT iNBT) {
            return 1;
        }
    }

    static class JsonNode
    implements INode {
        private final String field_218063_a;
        private final CompoundNBT field_218064_b;
        private final Predicate<INBT> field_218065_c;

        public JsonNode(String string, CompoundNBT compoundNBT) {
            this.field_218063_a = string;
            this.field_218064_b = compoundNBT;
            this.field_218065_c = NBTPathArgument.equalToCompoundPredicate(compoundNBT);
        }

        @Override
        public void addMatchingElements(INBT iNBT, List<INBT> list) {
            INBT iNBT2;
            if (iNBT instanceof CompoundNBT && this.field_218065_c.test(iNBT2 = ((CompoundNBT)iNBT).get(this.field_218063_a))) {
                list.add(iNBT2);
            }
        }

        @Override
        public void func_218054_a(INBT iNBT, Supplier<INBT> supplier, List<INBT> list) {
            if (iNBT instanceof CompoundNBT) {
                CompoundNBT compoundNBT = (CompoundNBT)iNBT;
                INBT iNBT2 = compoundNBT.get(this.field_218063_a);
                if (iNBT2 == null) {
                    CompoundNBT compoundNBT2 = this.field_218064_b.copy();
                    compoundNBT.put(this.field_218063_a, compoundNBT2);
                    list.add(compoundNBT2);
                } else if (this.field_218065_c.test(iNBT2)) {
                    list.add(iNBT2);
                }
            }
        }

        @Override
        public INBT createEmptyElement() {
            return new CompoundNBT();
        }

        @Override
        public int func_218051_a(INBT iNBT, Supplier<INBT> supplier) {
            INBT iNBT2;
            CompoundNBT compoundNBT;
            INBT iNBT3;
            if (iNBT instanceof CompoundNBT && this.field_218065_c.test(iNBT3 = (compoundNBT = (CompoundNBT)iNBT).get(this.field_218063_a)) && !(iNBT2 = supplier.get()).equals(iNBT3)) {
                compoundNBT.put(this.field_218063_a, iNBT2);
                return 0;
            }
            return 1;
        }

        @Override
        public int func_218053_a(INBT iNBT) {
            CompoundNBT compoundNBT;
            INBT iNBT2;
            if (iNBT instanceof CompoundNBT && this.field_218065_c.test(iNBT2 = (compoundNBT = (CompoundNBT)iNBT).get(this.field_218063_a))) {
                compoundNBT.remove(this.field_218063_a);
                return 0;
            }
            return 1;
        }
    }

    static class StringNode
    implements INode {
        private final String field_218058_a;

        public StringNode(String string) {
            this.field_218058_a = string;
        }

        @Override
        public void addMatchingElements(INBT iNBT, List<INBT> list) {
            INBT iNBT2;
            if (iNBT instanceof CompoundNBT && (iNBT2 = ((CompoundNBT)iNBT).get(this.field_218058_a)) != null) {
                list.add(iNBT2);
            }
        }

        @Override
        public void func_218054_a(INBT iNBT, Supplier<INBT> supplier, List<INBT> list) {
            if (iNBT instanceof CompoundNBT) {
                INBT iNBT2;
                CompoundNBT compoundNBT = (CompoundNBT)iNBT;
                if (compoundNBT.contains(this.field_218058_a)) {
                    iNBT2 = compoundNBT.get(this.field_218058_a);
                } else {
                    iNBT2 = supplier.get();
                    compoundNBT.put(this.field_218058_a, iNBT2);
                }
                list.add(iNBT2);
            }
        }

        @Override
        public INBT createEmptyElement() {
            return new CompoundNBT();
        }

        @Override
        public int func_218051_a(INBT iNBT, Supplier<INBT> supplier) {
            if (iNBT instanceof CompoundNBT) {
                INBT iNBT2;
                CompoundNBT compoundNBT = (CompoundNBT)iNBT;
                INBT iNBT3 = supplier.get();
                if (!iNBT3.equals(iNBT2 = compoundNBT.put(this.field_218058_a, iNBT3))) {
                    return 0;
                }
            }
            return 1;
        }

        @Override
        public int func_218053_a(INBT iNBT) {
            CompoundNBT compoundNBT;
            if (iNBT instanceof CompoundNBT && (compoundNBT = (CompoundNBT)iNBT).contains(this.field_218058_a)) {
                compoundNBT.remove(this.field_218058_a);
                return 0;
            }
            return 1;
        }
    }
}

