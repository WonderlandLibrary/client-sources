/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.List;

public interface OptionDescriptor {
    public List<String> options();

    public String description();

    public List<?> defaultValues();

    public boolean isRequired();

    public boolean acceptsArguments();

    public boolean requiresArgument();

    public String argumentDescription();

    public String argumentTypeIndicator();

    public boolean representsNonOptions();
}

