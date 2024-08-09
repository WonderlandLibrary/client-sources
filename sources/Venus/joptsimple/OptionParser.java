/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import joptsimple.AbstractOptionSpec;
import joptsimple.AlternativeLongOptionSpec;
import joptsimple.ArgumentList;
import joptsimple.BuiltinHelpFormatter;
import joptsimple.HelpFormatter;
import joptsimple.MissingRequiredOptionsException;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionDeclarer;
import joptsimple.OptionException;
import joptsimple.OptionParserState;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;
import joptsimple.OptionSpecTokenizer;
import joptsimple.ParserRules;
import joptsimple.UnavailableOptionException;
import joptsimple.UnconfiguredOptionException;
import joptsimple.internal.AbbreviationMap;
import joptsimple.internal.OptionNameMap;
import joptsimple.internal.SimpleOptionNameMap;
import joptsimple.util.KeyValuePair;

public class OptionParser
implements OptionDeclarer {
    private final OptionNameMap<AbstractOptionSpec<?>> recognizedOptions;
    private final ArrayList<AbstractOptionSpec<?>> trainingOrder;
    private final Map<List<String>, Set<OptionSpec<?>>> requiredIf;
    private final Map<List<String>, Set<OptionSpec<?>>> requiredUnless;
    private final Map<List<String>, Set<OptionSpec<?>>> availableIf;
    private final Map<List<String>, Set<OptionSpec<?>>> availableUnless;
    private OptionParserState state;
    private boolean posixlyCorrect;
    private boolean allowsUnrecognizedOptions;
    private HelpFormatter helpFormatter = new BuiltinHelpFormatter();

    public OptionParser() {
        this(true);
    }

    public OptionParser(boolean bl) {
        this.trainingOrder = new ArrayList();
        this.requiredIf = new HashMap();
        this.requiredUnless = new HashMap();
        this.availableIf = new HashMap();
        this.availableUnless = new HashMap();
        this.state = OptionParserState.moreOptions(false);
        this.recognizedOptions = bl ? new AbbreviationMap() : new SimpleOptionNameMap();
        this.recognize(new NonOptionArgumentSpec());
    }

    public OptionParser(String string) {
        this();
        new OptionSpecTokenizer(string).configure(this);
    }

    @Override
    public OptionSpecBuilder accepts(String string) {
        return this.acceptsAll(Collections.singletonList(string));
    }

    @Override
    public OptionSpecBuilder accepts(String string, String string2) {
        return this.acceptsAll(Collections.singletonList(string), string2);
    }

    @Override
    public OptionSpecBuilder acceptsAll(List<String> list) {
        return this.acceptsAll(list, "");
    }

    @Override
    public OptionSpecBuilder acceptsAll(List<String> list, String string) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("need at least one option");
        }
        ParserRules.ensureLegalOptions(list);
        return new OptionSpecBuilder(this, list, string);
    }

    @Override
    public NonOptionArgumentSpec<String> nonOptions() {
        NonOptionArgumentSpec<String> nonOptionArgumentSpec = new NonOptionArgumentSpec<String>();
        this.recognize(nonOptionArgumentSpec);
        return nonOptionArgumentSpec;
    }

    @Override
    public NonOptionArgumentSpec<String> nonOptions(String string) {
        NonOptionArgumentSpec<String> nonOptionArgumentSpec = new NonOptionArgumentSpec<String>(string);
        this.recognize(nonOptionArgumentSpec);
        return nonOptionArgumentSpec;
    }

    @Override
    public void posixlyCorrect(boolean bl) {
        this.posixlyCorrect = bl;
        this.state = OptionParserState.moreOptions(bl);
    }

    boolean posixlyCorrect() {
        return this.posixlyCorrect;
    }

    @Override
    public void allowsUnrecognizedOptions() {
        this.allowsUnrecognizedOptions = true;
    }

    boolean doesAllowsUnrecognizedOptions() {
        return this.allowsUnrecognizedOptions;
    }

    @Override
    public void recognizeAlternativeLongOptions(boolean bl) {
        if (bl) {
            this.recognize(new AlternativeLongOptionSpec());
        } else {
            this.recognizedOptions.remove(String.valueOf("W"));
        }
    }

    void recognize(AbstractOptionSpec<?> abstractOptionSpec) {
        this.recognizedOptions.putAll(abstractOptionSpec.options(), abstractOptionSpec);
        this.trainingOrder.add(abstractOptionSpec);
    }

    public void printHelpOn(OutputStream outputStream) throws IOException {
        this.printHelpOn(new OutputStreamWriter(outputStream));
    }

    public void printHelpOn(Writer writer) throws IOException {
        writer.write(this.helpFormatter.format(this._recognizedOptions()));
        writer.flush();
    }

    public void formatHelpWith(HelpFormatter helpFormatter) {
        if (helpFormatter == null) {
            throw new NullPointerException();
        }
        this.helpFormatter = helpFormatter;
    }

    public Map<String, OptionSpec<?>> recognizedOptions() {
        return new LinkedHashMap(this._recognizedOptions());
    }

    private Map<String, AbstractOptionSpec<?>> _recognizedOptions() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (AbstractOptionSpec<?> abstractOptionSpec : this.trainingOrder) {
            for (String string : abstractOptionSpec.options()) {
                linkedHashMap.put(string, abstractOptionSpec);
            }
        }
        return linkedHashMap;
    }

    public OptionSet parse(String ... stringArray) {
        ArgumentList argumentList = new ArgumentList(stringArray);
        OptionSet optionSet = new OptionSet(this.recognizedOptions.toJavaUtilMap());
        optionSet.add(this.recognizedOptions.get("[arguments]"));
        while (argumentList.hasMore()) {
            this.state.handleArgument(this, argumentList, optionSet);
        }
        this.reset();
        this.ensureRequiredOptions(optionSet);
        this.ensureAllowedOptions(optionSet);
        return optionSet;
    }

    public void mutuallyExclusive(OptionSpecBuilder ... optionSpecBuilderArray) {
        for (int i = 0; i < optionSpecBuilderArray.length; ++i) {
            for (int j = 0; j < optionSpecBuilderArray.length; ++j) {
                if (i == j) continue;
                optionSpecBuilderArray[i].availableUnless(optionSpecBuilderArray[j], new OptionSpec[0]);
            }
        }
    }

    private void ensureRequiredOptions(OptionSet optionSet) {
        List<AbstractOptionSpec<?>> list = this.missingRequiredOptions(optionSet);
        boolean bl = this.isHelpOptionPresent(optionSet);
        if (!list.isEmpty() && !bl) {
            throw new MissingRequiredOptionsException((List<? extends OptionSpec<?>>)list);
        }
    }

    private void ensureAllowedOptions(OptionSet optionSet) {
        List<AbstractOptionSpec<?>> list = this.unavailableOptions(optionSet);
        boolean bl = this.isHelpOptionPresent(optionSet);
        if (!list.isEmpty() && !bl) {
            throw new UnavailableOptionException((List<? extends OptionSpec<?>>)list);
        }
    }

    private List<AbstractOptionSpec<?>> missingRequiredOptions(OptionSet optionSet) {
        AbstractOptionSpec<?> abstractOptionSpec;
        ArrayList arrayList = new ArrayList();
        for (AbstractOptionSpec<?> abstractOptionSpec2 : this.recognizedOptions.toJavaUtilMap().values()) {
            if (!abstractOptionSpec2.isRequired() || optionSet.has(abstractOptionSpec2)) continue;
            arrayList.add(abstractOptionSpec2);
        }
        for (Map.Entry entry : this.requiredIf.entrySet()) {
            abstractOptionSpec = this.specFor((String)((List)entry.getKey()).iterator().next());
            if (!this.optionsHasAnyOf(optionSet, (Collection)entry.getValue()) || optionSet.has(abstractOptionSpec)) continue;
            arrayList.add(abstractOptionSpec);
        }
        for (Map.Entry entry : this.requiredUnless.entrySet()) {
            abstractOptionSpec = this.specFor((String)((List)entry.getKey()).iterator().next());
            if (this.optionsHasAnyOf(optionSet, (Collection)entry.getValue()) || optionSet.has(abstractOptionSpec)) continue;
            arrayList.add(abstractOptionSpec);
        }
        return arrayList;
    }

    private List<AbstractOptionSpec<?>> unavailableOptions(OptionSet optionSet) {
        AbstractOptionSpec<?> abstractOptionSpec;
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<List<String>, Set<OptionSpec<?>>> entry : this.availableIf.entrySet()) {
            abstractOptionSpec = this.specFor(entry.getKey().iterator().next());
            if (this.optionsHasAnyOf(optionSet, (Collection)entry.getValue()) || !optionSet.has(abstractOptionSpec)) continue;
            arrayList.add(abstractOptionSpec);
        }
        for (Map.Entry<List<String>, Set<OptionSpec<?>>> entry : this.availableUnless.entrySet()) {
            abstractOptionSpec = this.specFor(entry.getKey().iterator().next());
            if (!this.optionsHasAnyOf(optionSet, (Collection)entry.getValue()) || !optionSet.has(abstractOptionSpec)) continue;
            arrayList.add(abstractOptionSpec);
        }
        return arrayList;
    }

    private boolean optionsHasAnyOf(OptionSet optionSet, Collection<OptionSpec<?>> collection) {
        for (OptionSpec<?> optionSpec : collection) {
            if (!optionSet.has(optionSpec)) continue;
            return false;
        }
        return true;
    }

    private boolean isHelpOptionPresent(OptionSet optionSet) {
        boolean bl = false;
        for (AbstractOptionSpec<?> abstractOptionSpec : this.recognizedOptions.toJavaUtilMap().values()) {
            if (!abstractOptionSpec.isForHelp() || !optionSet.has(abstractOptionSpec)) continue;
            bl = true;
            break;
        }
        return bl;
    }

    void handleLongOptionToken(String string, ArgumentList argumentList, OptionSet optionSet) {
        KeyValuePair keyValuePair = OptionParser.parseLongOptionWithArgument(string);
        if (!this.isRecognized(keyValuePair.key)) {
            throw OptionException.unrecognizedOption(keyValuePair.key);
        }
        AbstractOptionSpec<?> abstractOptionSpec = this.specFor(keyValuePair.key);
        abstractOptionSpec.handleOption(this, argumentList, optionSet, keyValuePair.value);
    }

    void handleShortOptionToken(String string, ArgumentList argumentList, OptionSet optionSet) {
        KeyValuePair keyValuePair = OptionParser.parseShortOptionWithArgument(string);
        if (this.isRecognized(keyValuePair.key)) {
            this.specFor(keyValuePair.key).handleOption(this, argumentList, optionSet, keyValuePair.value);
        } else {
            this.handleShortOptionCluster(string, argumentList, optionSet);
        }
    }

    private void handleShortOptionCluster(String string, ArgumentList argumentList, OptionSet optionSet) {
        char[] cArray = OptionParser.extractShortOptionsFrom(string);
        this.validateOptionCharacters(cArray);
        for (int i = 0; i < cArray.length; ++i) {
            AbstractOptionSpec<?> abstractOptionSpec = this.specFor(cArray[i]);
            if (abstractOptionSpec.acceptsArguments() && cArray.length > i + 1) {
                String string2 = String.valueOf(cArray, i + 1, cArray.length - 1 - i);
                abstractOptionSpec.handleOption(this, argumentList, optionSet, string2);
                break;
            }
            abstractOptionSpec.handleOption(this, argumentList, optionSet, null);
        }
    }

    void handleNonOptionArgument(String string, ArgumentList argumentList, OptionSet optionSet) {
        this.specFor("[arguments]").handleOption(this, argumentList, optionSet, string);
    }

    void noMoreOptions() {
        this.state = OptionParserState.noMoreOptions();
    }

    boolean looksLikeAnOption(String string) {
        return ParserRules.isShortOptionToken(string) || ParserRules.isLongOptionToken(string);
    }

    boolean isRecognized(String string) {
        return this.recognizedOptions.contains(string);
    }

    void requiredIf(List<String> list, String string) {
        this.requiredIf(list, this.specFor(string));
    }

    void requiredIf(List<String> list, OptionSpec<?> optionSpec) {
        this.putDependentOption(list, optionSpec, this.requiredIf);
    }

    void requiredUnless(List<String> list, String string) {
        this.requiredUnless(list, this.specFor(string));
    }

    void requiredUnless(List<String> list, OptionSpec<?> optionSpec) {
        this.putDependentOption(list, optionSpec, this.requiredUnless);
    }

    void availableIf(List<String> list, String string) {
        this.availableIf(list, this.specFor(string));
    }

    void availableIf(List<String> list, OptionSpec<?> optionSpec) {
        this.putDependentOption(list, optionSpec, this.availableIf);
    }

    void availableUnless(List<String> list, String string) {
        this.availableUnless(list, this.specFor(string));
    }

    void availableUnless(List<String> list, OptionSpec<?> optionSpec) {
        this.putDependentOption(list, optionSpec, this.availableUnless);
    }

    private void putDependentOption(List<String> list, OptionSpec<?> optionSpec, Map<List<String>, Set<OptionSpec<?>>> map) {
        for (String string : list) {
            AbstractOptionSpec<?> abstractOptionSpec = this.specFor(string);
            if (abstractOptionSpec != null) continue;
            throw new UnconfiguredOptionException(list);
        }
        Set<OptionSpec<?>> set = map.get(list);
        if (set == null) {
            set = new HashSet();
            map.put(list, set);
        }
        set.add(optionSpec);
    }

    private AbstractOptionSpec<?> specFor(char c) {
        return this.specFor(String.valueOf(c));
    }

    private AbstractOptionSpec<?> specFor(String string) {
        return this.recognizedOptions.get(string);
    }

    private void reset() {
        this.state = OptionParserState.moreOptions(this.posixlyCorrect);
    }

    private static char[] extractShortOptionsFrom(String string) {
        char[] cArray = new char[string.length() - 1];
        string.getChars(1, string.length(), cArray, 0);
        return cArray;
    }

    private void validateOptionCharacters(char[] cArray) {
        for (char c : cArray) {
            String string = String.valueOf(c);
            if (!this.isRecognized(string)) {
                throw OptionException.unrecognizedOption(string);
            }
            if (!this.specFor(string).acceptsArguments()) continue;
            return;
        }
    }

    private static KeyValuePair parseLongOptionWithArgument(String string) {
        return KeyValuePair.valueOf(string.substring(2));
    }

    private static KeyValuePair parseShortOptionWithArgument(String string) {
        return KeyValuePair.valueOf(string.substring(1));
    }
}

