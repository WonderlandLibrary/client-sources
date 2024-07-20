/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.List;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionSpecBuilder;

public interface OptionDeclarer {
    public OptionSpecBuilder accepts(String var1);

    public OptionSpecBuilder accepts(String var1, String var2);

    public OptionSpecBuilder acceptsAll(List<String> var1);

    public OptionSpecBuilder acceptsAll(List<String> var1, String var2);

    public NonOptionArgumentSpec<String> nonOptions();

    public NonOptionArgumentSpec<String> nonOptions(String var1);

    public void posixlyCorrect(boolean var1);

    public void allowsUnrecognizedOptions();

    public void recognizeAlternativeLongOptions(boolean var1);
}

