/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NoArgumentOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSpec;
import joptsimple.OptionalArgumentOptionSpec;
import joptsimple.RequiredArgumentOptionSpec;
import joptsimple.UnconfiguredOptionException;

public class OptionSpecBuilder
extends NoArgumentOptionSpec {
    private final OptionParser parser;

    OptionSpecBuilder(OptionParser optionParser, List<String> list, String string) {
        super(list, string);
        this.parser = optionParser;
        this.attachToParser();
    }

    private void attachToParser() {
        this.parser.recognize(this);
    }

    public ArgumentAcceptingOptionSpec<String> withRequiredArg() {
        RequiredArgumentOptionSpec<String> requiredArgumentOptionSpec = new RequiredArgumentOptionSpec<String>(this.options(), this.description());
        this.parser.recognize(requiredArgumentOptionSpec);
        return requiredArgumentOptionSpec;
    }

    public ArgumentAcceptingOptionSpec<String> withOptionalArg() {
        OptionalArgumentOptionSpec<String> optionalArgumentOptionSpec = new OptionalArgumentOptionSpec<String>(this.options(), this.description());
        this.parser.recognize(optionalArgumentOptionSpec);
        return optionalArgumentOptionSpec;
    }

    public OptionSpecBuilder requiredIf(String string, String ... stringArray) {
        List<String> list = this.validatedDependents(string, stringArray);
        for (String string2 : list) {
            this.parser.requiredIf(this.options(), string2);
        }
        return this;
    }

    public OptionSpecBuilder requiredIf(OptionSpec<?> optionSpec, OptionSpec<?> ... optionSpecArray) {
        this.parser.requiredIf(this.options(), optionSpec);
        for (OptionSpec<?> optionSpec2 : optionSpecArray) {
            this.parser.requiredIf(this.options(), optionSpec2);
        }
        return this;
    }

    public OptionSpecBuilder requiredUnless(String string, String ... stringArray) {
        List<String> list = this.validatedDependents(string, stringArray);
        for (String string2 : list) {
            this.parser.requiredUnless(this.options(), string2);
        }
        return this;
    }

    public OptionSpecBuilder requiredUnless(OptionSpec<?> optionSpec, OptionSpec<?> ... optionSpecArray) {
        this.parser.requiredUnless(this.options(), optionSpec);
        for (OptionSpec<?> optionSpec2 : optionSpecArray) {
            this.parser.requiredUnless(this.options(), optionSpec2);
        }
        return this;
    }

    public OptionSpecBuilder availableIf(String string, String ... stringArray) {
        List<String> list = this.validatedDependents(string, stringArray);
        for (String string2 : list) {
            this.parser.availableIf(this.options(), string2);
        }
        return this;
    }

    public OptionSpecBuilder availableIf(OptionSpec<?> optionSpec, OptionSpec<?> ... optionSpecArray) {
        this.parser.availableIf(this.options(), optionSpec);
        for (OptionSpec<?> optionSpec2 : optionSpecArray) {
            this.parser.availableIf(this.options(), optionSpec2);
        }
        return this;
    }

    public OptionSpecBuilder availableUnless(String string, String ... stringArray) {
        List<String> list = this.validatedDependents(string, stringArray);
        for (String string2 : list) {
            this.parser.availableUnless(this.options(), string2);
        }
        return this;
    }

    public OptionSpecBuilder availableUnless(OptionSpec<?> optionSpec, OptionSpec<?> ... optionSpecArray) {
        this.parser.availableUnless(this.options(), optionSpec);
        for (OptionSpec<?> optionSpec2 : optionSpecArray) {
            this.parser.availableUnless(this.options(), optionSpec2);
        }
        return this;
    }

    private List<String> validatedDependents(String string, String ... stringArray) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(string);
        Collections.addAll(arrayList, stringArray);
        for (String string2 : arrayList) {
            if (this.parser.isRecognized(string2)) continue;
            throw new UnconfiguredOptionException(string2);
        }
        return arrayList;
    }

    public List defaultValues() {
        return super.defaultValues();
    }

    @Override
    public String argumentTypeIndicator() {
        return super.argumentTypeIndicator();
    }

    @Override
    public String argumentDescription() {
        return super.argumentDescription();
    }

    @Override
    public boolean isRequired() {
        return super.isRequired();
    }

    @Override
    public boolean requiresArgument() {
        return super.requiresArgument();
    }

    @Override
    public boolean acceptsArguments() {
        return super.acceptsArguments();
    }
}

