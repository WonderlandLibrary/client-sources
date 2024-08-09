/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
import joptsimple.HelpFormatter;
import joptsimple.OptionDescriptor;
import joptsimple.ParserRules;
import joptsimple.internal.Classes;
import joptsimple.internal.Messages;
import joptsimple.internal.Rows;
import joptsimple.internal.Strings;

public class BuiltinHelpFormatter
implements HelpFormatter {
    private final Rows nonOptionRows;
    private final Rows optionRows;

    BuiltinHelpFormatter() {
        this(80, 2);
    }

    public BuiltinHelpFormatter(int n, int n2) {
        this.nonOptionRows = new Rows(n * 2, 0);
        this.optionRows = new Rows(n, n2);
    }

    @Override
    public String format(Map<String, ? extends OptionDescriptor> map) {
        this.optionRows.reset();
        this.nonOptionRows.reset();
        Comparator<OptionDescriptor> comparator = new Comparator<OptionDescriptor>(this){
            final BuiltinHelpFormatter this$0;
            {
                this.this$0 = builtinHelpFormatter;
            }

            @Override
            public int compare(OptionDescriptor optionDescriptor, OptionDescriptor optionDescriptor2) {
                return optionDescriptor.options().iterator().next().compareTo(optionDescriptor2.options().iterator().next());
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((OptionDescriptor)object, (OptionDescriptor)object2);
            }
        };
        TreeSet<OptionDescriptor> treeSet = new TreeSet<OptionDescriptor>(comparator);
        treeSet.addAll(map.values());
        this.addRows(treeSet);
        return this.formattedHelpOutput();
    }

    protected void addOptionRow(String string) {
        this.addOptionRow(string, "");
    }

    protected void addOptionRow(String string, String string2) {
        this.optionRows.add(string, string2);
    }

    protected void addNonOptionRow(String string) {
        this.nonOptionRows.add(string, "");
    }

    protected void fitRowsToWidth() {
        this.nonOptionRows.fitToWidth();
        this.optionRows.fitToWidth();
    }

    protected String nonOptionOutput() {
        return this.nonOptionRows.render();
    }

    protected String optionOutput() {
        return this.optionRows.render();
    }

    protected String formattedHelpOutput() {
        StringBuilder stringBuilder = new StringBuilder();
        String string = this.nonOptionOutput();
        if (!Strings.isNullOrEmpty(string)) {
            stringBuilder.append(string).append(Strings.LINE_SEPARATOR);
        }
        stringBuilder.append(this.optionOutput());
        return stringBuilder.toString();
    }

    protected void addRows(Collection<? extends OptionDescriptor> collection) {
        this.addNonOptionsDescription(collection);
        if (collection.isEmpty()) {
            this.addOptionRow(this.message("no.options.specified", new Object[0]));
        } else {
            this.addHeaders(collection);
            this.addOptions(collection);
        }
        this.fitRowsToWidth();
    }

    protected void addNonOptionsDescription(Collection<? extends OptionDescriptor> collection) {
        OptionDescriptor optionDescriptor = this.findAndRemoveNonOptionsSpec(collection);
        if (this.shouldShowNonOptionArgumentDisplay(optionDescriptor)) {
            this.addNonOptionRow(this.message("non.option.arguments.header", new Object[0]));
            this.addNonOptionRow(this.createNonOptionArgumentsDisplay(optionDescriptor));
        }
    }

    protected boolean shouldShowNonOptionArgumentDisplay(OptionDescriptor optionDescriptor) {
        return !Strings.isNullOrEmpty(optionDescriptor.description()) || !Strings.isNullOrEmpty(optionDescriptor.argumentTypeIndicator()) || !Strings.isNullOrEmpty(optionDescriptor.argumentDescription());
    }

    protected String createNonOptionArgumentsDisplay(OptionDescriptor optionDescriptor) {
        StringBuilder stringBuilder = new StringBuilder();
        this.maybeAppendOptionInfo(stringBuilder, optionDescriptor);
        this.maybeAppendNonOptionsDescription(stringBuilder, optionDescriptor);
        return stringBuilder.toString();
    }

    protected void maybeAppendNonOptionsDescription(StringBuilder stringBuilder, OptionDescriptor optionDescriptor) {
        stringBuilder.append(stringBuilder.length() > 0 && !Strings.isNullOrEmpty(optionDescriptor.description()) ? " -- " : "").append(optionDescriptor.description());
    }

    protected OptionDescriptor findAndRemoveNonOptionsSpec(Collection<? extends OptionDescriptor> collection) {
        Iterator<? extends OptionDescriptor> iterator2 = collection.iterator();
        while (iterator2.hasNext()) {
            OptionDescriptor optionDescriptor = iterator2.next();
            if (!optionDescriptor.representsNonOptions()) continue;
            iterator2.remove();
            return optionDescriptor;
        }
        throw new AssertionError((Object)"no non-options argument spec");
    }

    protected void addHeaders(Collection<? extends OptionDescriptor> collection) {
        if (this.hasRequiredOption(collection)) {
            this.addOptionRow(this.message("option.header.with.required.indicator", new Object[0]), this.message("description.header", new Object[0]));
            this.addOptionRow(this.message("option.divider.with.required.indicator", new Object[0]), this.message("description.divider", new Object[0]));
        } else {
            this.addOptionRow(this.message("option.header", new Object[0]), this.message("description.header", new Object[0]));
            this.addOptionRow(this.message("option.divider", new Object[0]), this.message("description.divider", new Object[0]));
        }
    }

    protected final boolean hasRequiredOption(Collection<? extends OptionDescriptor> collection) {
        for (OptionDescriptor optionDescriptor : collection) {
            if (!optionDescriptor.isRequired()) continue;
            return false;
        }
        return true;
    }

    protected void addOptions(Collection<? extends OptionDescriptor> collection) {
        for (OptionDescriptor optionDescriptor : collection) {
            if (optionDescriptor.representsNonOptions()) continue;
            this.addOptionRow(this.createOptionDisplay(optionDescriptor), this.createDescriptionDisplay(optionDescriptor));
        }
    }

    protected String createOptionDisplay(OptionDescriptor optionDescriptor) {
        StringBuilder stringBuilder = new StringBuilder(optionDescriptor.isRequired() ? "* " : "");
        Iterator<String> iterator2 = optionDescriptor.options().iterator();
        while (iterator2.hasNext()) {
            String string = iterator2.next();
            stringBuilder.append(this.optionLeader(string));
            stringBuilder.append(string);
            if (!iterator2.hasNext()) continue;
            stringBuilder.append(", ");
        }
        this.maybeAppendOptionInfo(stringBuilder, optionDescriptor);
        return stringBuilder.toString();
    }

    protected String optionLeader(String string) {
        return string.length() > 1 ? "--" : ParserRules.HYPHEN;
    }

    protected void maybeAppendOptionInfo(StringBuilder stringBuilder, OptionDescriptor optionDescriptor) {
        String string = this.extractTypeIndicator(optionDescriptor);
        String string2 = optionDescriptor.argumentDescription();
        if (optionDescriptor.acceptsArguments() || !Strings.isNullOrEmpty(string2) || optionDescriptor.representsNonOptions()) {
            this.appendOptionHelp(stringBuilder, string, string2, optionDescriptor.requiresArgument());
        }
    }

    protected String extractTypeIndicator(OptionDescriptor optionDescriptor) {
        String string = optionDescriptor.argumentTypeIndicator();
        if (!Strings.isNullOrEmpty(string) && !String.class.getName().equals(string)) {
            return Classes.shortNameOf(string);
        }
        return "String";
    }

    protected void appendOptionHelp(StringBuilder stringBuilder, String string, String string2, boolean bl) {
        if (bl) {
            this.appendTypeIndicator(stringBuilder, string, string2, '<', '>');
        } else {
            this.appendTypeIndicator(stringBuilder, string, string2, '[', ']');
        }
    }

    protected void appendTypeIndicator(StringBuilder stringBuilder, String string, String string2, char c, char c2) {
        stringBuilder.append(' ').append(c);
        if (string != null) {
            stringBuilder.append(string);
        }
        if (!Strings.isNullOrEmpty(string2)) {
            if (string != null) {
                stringBuilder.append(": ");
            }
            stringBuilder.append(string2);
        }
        stringBuilder.append(c2);
    }

    protected String createDescriptionDisplay(OptionDescriptor optionDescriptor) {
        List<?> list = optionDescriptor.defaultValues();
        if (list.isEmpty()) {
            return optionDescriptor.description();
        }
        String string = this.createDefaultValuesDisplay(list);
        return (optionDescriptor.description() + ' ' + Strings.surround(this.message("default.value.header", new Object[0]) + ' ' + string, '(', ')')).trim();
    }

    protected String createDefaultValuesDisplay(List<?> list) {
        return list.size() == 1 ? list.get(0).toString() : list.toString();
    }

    protected String message(String string, Object ... objectArray) {
        return Messages.message(Locale.getDefault(), "joptsimple.HelpFormatterMessages", BuiltinHelpFormatter.class, string, objectArray);
    }
}

