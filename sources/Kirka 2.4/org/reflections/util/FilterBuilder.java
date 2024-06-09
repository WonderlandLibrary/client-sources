/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.util;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.reflections.ReflectionsException;
import org.reflections.util.Utils;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class FilterBuilder
implements Predicate<String> {
    private final List<Predicate<String>> chain;

    public FilterBuilder() {
        this.chain = Lists.newArrayList();
    }

    private FilterBuilder(Iterable<Predicate<String>> filters) {
        this.chain = Lists.newArrayList(filters);
    }

    public FilterBuilder include(String regex) {
        return this.add(new Include(regex));
    }

    public FilterBuilder exclude(String regex) {
        this.add(new Exclude(regex));
        return this;
    }

    public FilterBuilder add(Predicate<String> filter) {
        this.chain.add(filter);
        return this;
    }

    public FilterBuilder includePackage(Class<?> aClass) {
        return this.add(new Include(FilterBuilder.packageNameRegex(aClass)));
    }

    public FilterBuilder excludePackage(Class<?> aClass) {
        return this.add(new Exclude(FilterBuilder.packageNameRegex(aClass)));
    }

    public FilterBuilder includePackage(String ... prefixes) {
        for (String prefix : prefixes) {
            this.add(new Include(FilterBuilder.prefix(prefix)));
        }
        return this;
    }

    public FilterBuilder excludePackage(String prefix) {
        return this.add(new Exclude(FilterBuilder.prefix(prefix)));
    }

    private static String packageNameRegex(Class<?> aClass) {
        return FilterBuilder.prefix(aClass.getPackage().getName() + ".");
    }

    public static String prefix(String qualifiedName) {
        return qualifiedName.replace(".", "\\.") + ".*";
    }

    public String toString() {
        return Joiner.on((String)", ").join(this.chain);
    }

    public boolean apply(String regex) {
        boolean accept;
        boolean bl = accept = this.chain == null || this.chain.isEmpty() || this.chain.get(0) instanceof Exclude;
        if (this.chain != null) {
            for (Predicate<String> filter : this.chain) {
                if (accept && filter instanceof Include || !accept && filter instanceof Exclude || (accept = filter.apply((Object)regex)) || !(filter instanceof Exclude)) continue;
                break;
            }
        }
        return accept;
    }

    public static FilterBuilder parse(String includeExcludeString) {
        ArrayList<Predicate<String>> filters = new ArrayList<Predicate<String>>();
        if (!Utils.isEmpty(includeExcludeString)) {
            for (String string : includeExcludeString.split(",")) {
                Matcher filter;
                String trimmed = string.trim();
                char prefix = trimmed.charAt(0);
                String pattern = trimmed.substring(1);
                switch (prefix) {
                    case '+': {
                        filter = new Include(pattern);
                        break;
                    }
                    case '-': {
                        filter = new Exclude(pattern);
                        break;
                    }
                    default: {
                        throw new ReflectionsException("includeExclude should start with either + or -");
                    }
                }
                filters.add(filter);
            }
            return new FilterBuilder(filters);
        }
        return new FilterBuilder();
    }

    public static FilterBuilder parsePackages(String includeExcludeString) {
        ArrayList<Predicate<String>> filters = new ArrayList<Predicate<String>>();
        if (!Utils.isEmpty(includeExcludeString)) {
            for (String string : includeExcludeString.split(",")) {
                Matcher filter;
                String trimmed = string.trim();
                char prefix = trimmed.charAt(0);
                String pattern = trimmed.substring(1);
                if (!pattern.endsWith(".")) {
                    pattern = pattern + ".";
                }
                pattern = FilterBuilder.prefix(pattern);
                switch (prefix) {
                    case '+': {
                        filter = new Include(pattern);
                        break;
                    }
                    case '-': {
                        filter = new Exclude(pattern);
                        break;
                    }
                    default: {
                        throw new ReflectionsException("includeExclude should start with either + or -");
                    }
                }
                filters.add(filter);
            }
            return new FilterBuilder(filters);
        }
        return new FilterBuilder();
    }

    public static class Exclude
    extends Matcher {
        public Exclude(String patternString) {
            super(patternString);
        }

        public boolean apply(String regex) {
            return !this.pattern.matcher(regex).matches();
        }

        public String toString() {
            return "-" + super.toString();
        }
    }

    public static class Include
    extends Matcher {
        public Include(String patternString) {
            super(patternString);
        }

        public boolean apply(String regex) {
            return this.pattern.matcher(regex).matches();
        }

        public String toString() {
            return "+" + super.toString();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static abstract class Matcher
    implements Predicate<String> {
        final Pattern pattern;

        public Matcher(String regex) {
            this.pattern = Pattern.compile(regex);
        }

        public abstract boolean apply(String var1);

        public String toString() {
            return this.pattern.pattern();
        }
    }

}

