/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.internal.IntrinsicConstEvaluation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.SequencesKt;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__AppendableKt;
import kotlin.text.StringsKt__IndentKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000b\u001a!\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0002\b\u0004\u001a\u0011\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002\u00a2\u0006\u0002\b\u0007\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u001aJ\u0010\t\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001H\u0082\b\u00a2\u0006\u0002\b\u000e\u001a\u0014\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u0002\u001a\u001e\u0010\u0011\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002\u001a\f\u0010\u0013\u001a\u00020\u0002*\u00020\u0002H\u0007\u001a\u0016\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002H\u0007\u00a8\u0006\u0015"}, d2={"getIndentFunction", "Lkotlin/Function1;", "", "indent", "getIndentFunction$StringsKt__IndentKt", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "prependIndent", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "replaceIndent", "newIndent", "replaceIndentByMargin", "marginPrefix", "trimIndent", "trimMargin", "kotlin-stdlib"}, xs="kotlin/text/StringsKt")
@SourceDebugExtension(value={"SMAP\nIndent.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Indent.kt\nkotlin/text/StringsKt__IndentKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,123:1\n113#1,2:125\n115#1,4:140\n120#1,2:153\n113#1,2:162\n115#1,4:177\n120#1,2:184\n1#2:124\n1#2:150\n1#2:181\n1#2:205\n1569#3,11:127\n1864#3,2:138\n1866#3:151\n1580#3:152\n766#3:155\n857#3,2:156\n1549#3:158\n1620#3,3:159\n1569#3,11:164\n1864#3,2:175\n1866#3:182\n1580#3:183\n1569#3,11:192\n1864#3,2:203\n1866#3:206\n1580#3:207\n151#4,6:144\n151#4,6:186\n*S KotlinDebug\n*F\n+ 1 Indent.kt\nkotlin/text/StringsKt__IndentKt\n*L\n38#1:125,2\n38#1:140,4\n38#1:153,2\n78#1:162,2\n78#1:177,4\n78#1:184,2\n38#1:150\n78#1:181\n114#1:205\n38#1:127,11\n38#1:138,2\n38#1:151\n38#1:152\n74#1:155\n74#1:156,2\n75#1:158\n75#1:159,3\n78#1:164,11\n78#1:175,2\n78#1:182\n78#1:183\n114#1:192,11\n114#1:203,2\n114#1:206\n114#1:207\n39#1:144,6\n101#1:186,6\n*E\n"})
class StringsKt__IndentKt
extends StringsKt__AppendableKt {
    @IntrinsicConstEvaluation
    @NotNull
    public static final String trimMargin(@NotNull String string, @NotNull String string2) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "marginPrefix");
        return StringsKt.replaceIndentByMargin(string, "", string2);
    }

    public static String trimMargin$default(String string, String string2, int n, Object object) {
        if ((n & 1) != 0) {
            string2 = "|";
        }
        return StringsKt.trimMargin(string, string2);
    }

    @NotNull
    public static final String replaceIndentByMargin(@NotNull String string, @NotNull String string2, @NotNull String string3) {
        List<String> list;
        boolean bl;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "newIndent");
        Intrinsics.checkNotNullParameter(string3, "marginPrefix");
        boolean bl2 = bl = !StringsKt.isBlank(string3);
        if (!bl) {
            boolean bl3 = false;
            String string4 = "marginPrefix must be non-blank string.";
            throw new IllegalArgumentException(string4.toString());
        }
        List<String> list2 = list = StringsKt.lines(string);
        int n = string.length() + string2.length() * list.size();
        Function1<String, String> function1 = StringsKt__IndentKt.getIndentFunction$StringsKt__IndentKt(string2);
        boolean bl4 = false;
        int n2 = CollectionsKt.getLastIndex(list2);
        Iterable iterable = list2;
        boolean bl5 = false;
        Iterable iterable2 = iterable;
        Collection collection = new ArrayList();
        boolean bl6 = false;
        Iterable iterable3 = iterable2;
        boolean bl7 = false;
        int n3 = 0;
        for (Object t : iterable3) {
            String string5;
            String string6;
            int n4;
            if ((n4 = n3++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Object t2 = t;
            int n5 = n4;
            boolean bl8 = false;
            String string7 = (String)t2;
            int n6 = n5;
            boolean bl9 = false;
            if ((n6 == 0 || n6 == n2) && StringsKt.isBlank(string7)) {
                string6 = null;
            } else {
                String string8;
                String string9;
                int n7;
                int n8;
                CharSequence charSequence;
                String string10;
                block11: {
                    string10 = string7;
                    boolean bl10 = false;
                    charSequence = string10;
                    n8 = 0;
                    int n9 = charSequence.length();
                    for (int i = 0; i < n9; ++i) {
                        char c = charSequence.charAt(i);
                        boolean bl11 = false;
                        if (!(!CharsKt.isWhitespace(c))) continue;
                        n7 = i;
                        break block11;
                    }
                    n7 = -1;
                }
                int n10 = n7;
                if (n10 == -1) {
                    string9 = null;
                } else if (StringsKt.startsWith$default(string10, string3, n10, false, 4, null)) {
                    charSequence = string10;
                    n8 = n10 + string3.length();
                    Intrinsics.checkNotNull(charSequence, "null cannot be cast to non-null type java.lang.String");
                    String string11 = ((String)charSequence).substring(n8);
                    string9 = string11;
                    Intrinsics.checkNotNullExpressionValue(string11, "this as java.lang.String).substring(startIndex)");
                } else {
                    string9 = null;
                }
                string6 = string9;
                if (string9 == null || (string6 = function1.invoke(string8 = string6)) == null) {
                    string6 = string7;
                }
            }
            if (string6 == null) continue;
            String string12 = string5 = string6;
            boolean bl12 = false;
            collection.add(string12);
        }
        String string13 = ((StringBuilder)CollectionsKt.joinTo$default((List)collection, new StringBuilder(n), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkNotNullExpressionValue(string13, "mapIndexedNotNull { inde\u2026\"\\n\")\n        .toString()");
        return string13;
    }

    public static String replaceIndentByMargin$default(String string, String string2, String string3, int n, Object object) {
        if ((n & 1) != 0) {
            string2 = "";
        }
        if ((n & 2) != 0) {
            string3 = "|";
        }
        return StringsKt.replaceIndentByMargin(string, string2, string3);
    }

    @IntrinsicConstEvaluation
    @NotNull
    public static final String trimIndent(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return StringsKt.replaceIndent(string, "");
    }

    @NotNull
    public static final String replaceIndent(@NotNull String string, @NotNull String string2) {
        boolean bl;
        Object object;
        Object object22;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "newIndent");
        List<String> list = StringsKt.lines(string);
        Iterable iterable = list;
        boolean bl2 = false;
        Iterable iterable2 = iterable;
        Collection collection = new ArrayList();
        boolean bl3 = false;
        for (Object object22 : iterable2) {
            object = (String)object22;
            bl = false;
            if (!(!StringsKt.isBlank((CharSequence)object))) continue;
            collection.add(object22);
        }
        iterable = (List)collection;
        bl2 = false;
        iterable2 = iterable;
        collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        bl3 = false;
        for (Object object22 : iterable2) {
            object = (String)object22;
            Collection collection2 = collection;
            bl = false;
            collection2.add(StringsKt__IndentKt.indentWidth$StringsKt__IndentKt((String)object));
        }
        Integer n = (Integer)CollectionsKt.minOrNull((List)collection);
        int n2 = n != null ? n : 0;
        List<String> list2 = list;
        int n3 = string.length() + string2.length() * list.size();
        Function1<String, String> function1 = StringsKt__IndentKt.getIndentFunction$StringsKt__IndentKt(string2);
        boolean bl4 = false;
        int n4 = CollectionsKt.getLastIndex(list2);
        Iterable iterable3 = list2;
        boolean bl5 = false;
        object22 = iterable3;
        object = new ArrayList();
        bl = false;
        Object object3 = object22;
        boolean bl6 = false;
        int n5 = 0;
        Iterator iterator2 = object3.iterator();
        while (iterator2.hasNext()) {
            String string3;
            String string4;
            int n6;
            Object t = iterator2.next();
            if ((n6 = n5++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Object t2 = t;
            int n7 = n6;
            boolean bl7 = false;
            String string5 = (String)t2;
            int n8 = n7;
            boolean bl8 = false;
            if ((n8 == 0 || n8 == n4) && StringsKt.isBlank(string5)) {
                string4 = null;
            } else {
                String string6;
                String string7 = string5;
                boolean bl9 = false;
                string4 = StringsKt.drop(string7, n2);
                if (string4 == null || (string4 = function1.invoke(string6 = string4)) == null) {
                    string4 = string5;
                }
            }
            if (string4 == null) continue;
            String string8 = string3 = string4;
            boolean bl10 = false;
            object.add(string8);
        }
        String string9 = ((StringBuilder)CollectionsKt.joinTo$default((List)object, new StringBuilder(n3), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkNotNullExpressionValue(string9, "mapIndexedNotNull { inde\u2026\"\\n\")\n        .toString()");
        return string9;
    }

    public static String replaceIndent$default(String string, String string2, int n, Object object) {
        if ((n & 1) != 0) {
            string2 = "";
        }
        return StringsKt.replaceIndent(string, string2);
    }

    @NotNull
    public static final String prependIndent(@NotNull String string, @NotNull String string2) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "indent");
        return SequencesKt.joinToString$default(SequencesKt.map(StringsKt.lineSequence(string), (Function1)new Function1<String, String>(string2){
            final String $indent;
            {
                this.$indent = string;
                super(1);
            }

            @NotNull
            public final String invoke(@NotNull String string) {
                Intrinsics.checkNotNullParameter(string, "it");
                return StringsKt.isBlank(string) ? (string.length() < this.$indent.length() ? this.$indent : string) : this.$indent + string;
            }

            public Object invoke(Object object) {
                return this.invoke((String)object);
            }
        }), "\n", null, null, 0, null, null, 62, null);
    }

    public static String prependIndent$default(String string, String string2, int n, Object object) {
        if ((n & 1) != 0) {
            string2 = "    ";
        }
        return StringsKt.prependIndent(string, string2);
    }

    private static final int indentWidth$StringsKt__IndentKt(String string) {
        int n;
        int n2;
        int n3;
        int n4;
        block1: {
            CharSequence charSequence = string;
            n4 = 0;
            int n5 = charSequence.length();
            for (n3 = 0; n3 < n5; ++n3) {
                char c = charSequence.charAt(n3);
                boolean bl = false;
                if (!(!CharsKt.isWhitespace(c))) continue;
                n2 = n3;
                break block1;
            }
            n2 = -1;
        }
        n4 = n = n2;
        n3 = 0;
        return n4 == -1 ? string.length() : n4;
    }

    private static final Function1<String, String> getIndentFunction$StringsKt__IndentKt(String string) {
        return ((CharSequence)string).length() == 0 ? (Function1)getIndentFunction.1.INSTANCE : (Function1)new Function1<String, String>(string){
            final String $indent;
            {
                this.$indent = string;
                super(1);
            }

            @NotNull
            public final String invoke(@NotNull String string) {
                Intrinsics.checkNotNullParameter(string, "line");
                return this.$indent + string;
            }

            public Object invoke(Object object) {
                return this.invoke((String)object);
            }
        };
    }

    private static final String reindent$StringsKt__IndentKt(List<String> list, int n, Function1<? super String, String> function1, Function1<? super String, String> function12) {
        boolean bl = false;
        int n2 = CollectionsKt.getLastIndex(list);
        Iterable iterable = list;
        boolean bl2 = false;
        Iterable iterable2 = iterable;
        Collection collection = new ArrayList();
        boolean bl3 = false;
        Iterable iterable3 = iterable2;
        boolean bl4 = false;
        int n3 = 0;
        for (Object t : iterable3) {
            String string;
            String string2;
            int n4;
            if ((n4 = n3++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Object t2 = t;
            int n5 = n4;
            boolean bl5 = false;
            String string3 = (String)t2;
            int n6 = n5;
            boolean bl6 = false;
            if ((n6 == 0 || n6 == n2) && StringsKt.isBlank(string3)) {
                string2 = null;
            } else {
                String string4;
                string2 = function12.invoke(string3);
                if (string2 == null || (string2 = function1.invoke(string4 = string2)) == null) {
                    string2 = string3;
                }
            }
            if (string2 == null) continue;
            String string5 = string = string2;
            boolean bl7 = false;
            collection.add(string5);
        }
        String string = ((StringBuilder)CollectionsKt.joinTo$default((List)collection, new StringBuilder(n), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkNotNullExpressionValue(string, "mapIndexedNotNull { inde\u2026\"\\n\")\n        .toString()");
        return string;
    }
}

