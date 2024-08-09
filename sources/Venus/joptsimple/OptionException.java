/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import joptsimple.OptionSpec;
import joptsimple.UnrecognizedOptionException;
import joptsimple.internal.Messages;
import joptsimple.internal.Strings;

public abstract class OptionException
extends RuntimeException {
    private static final long serialVersionUID = -1L;
    private final List<String> options = new ArrayList<String>();

    protected OptionException(List<String> list) {
        this.options.addAll(list);
    }

    protected OptionException(Collection<? extends OptionSpec<?>> collection) {
        this.options.addAll(this.specsToStrings(collection));
    }

    protected OptionException(Collection<? extends OptionSpec<?>> collection, Throwable throwable) {
        super(throwable);
        this.options.addAll(this.specsToStrings(collection));
    }

    private List<String> specsToStrings(Collection<? extends OptionSpec<?>> collection) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (OptionSpec<?> optionSpec : collection) {
            arrayList.add(this.specToString(optionSpec));
        }
        return arrayList;
    }

    private String specToString(OptionSpec<?> optionSpec) {
        return Strings.join(new ArrayList<String>(optionSpec.options()), "/");
    }

    public List<String> options() {
        return Collections.unmodifiableList(this.options);
    }

    protected final String singleOptionString() {
        return this.singleOptionString(this.options.get(0));
    }

    protected final String singleOptionString(String string) {
        return string;
    }

    protected final String multipleOptionString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>(this.options);
        Iterator iterator2 = linkedHashSet.iterator();
        while (iterator2.hasNext()) {
            stringBuilder.append(this.singleOptionString((String)iterator2.next()));
            if (!iterator2.hasNext()) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    static OptionException unrecognizedOption(String string) {
        return new UnrecognizedOptionException(string);
    }

    @Override
    public final String getMessage() {
        return this.localizedMessage(Locale.getDefault());
    }

    final String localizedMessage(Locale locale) {
        return this.formattedMessage(locale);
    }

    private String formattedMessage(Locale locale) {
        return Messages.message(locale, "joptsimple.ExceptionMessages", this.getClass(), "message", this.messageArguments());
    }

    abstract Object[] messageArguments();
}

