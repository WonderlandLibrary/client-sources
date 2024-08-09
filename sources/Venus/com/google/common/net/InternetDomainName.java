/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.thirdparty.publicsuffix.PublicSuffixPatterns;
import java.util.List;
import javax.annotation.Nullable;

@Beta
@GwtCompatible
public final class InternetDomainName {
    private static final CharMatcher DOTS_MATCHER = CharMatcher.anyOf(".\u3002\uff0e\uff61");
    private static final Splitter DOT_SPLITTER = Splitter.on('.');
    private static final Joiner DOT_JOINER = Joiner.on('.');
    private static final int NO_PUBLIC_SUFFIX_FOUND = -1;
    private static final String DOT_REGEX = "\\.";
    private static final int MAX_PARTS = 127;
    private static final int MAX_LENGTH = 253;
    private static final int MAX_DOMAIN_PART_LENGTH = 63;
    private final String name;
    private final ImmutableList<String> parts;
    private final int publicSuffixIndex;
    private static final CharMatcher DASH_MATCHER = CharMatcher.anyOf("-_");
    private static final CharMatcher PART_CHAR_MATCHER = CharMatcher.javaLetterOrDigit().or(DASH_MATCHER);

    InternetDomainName(String string) {
        string = Ascii.toLowerCase(DOTS_MATCHER.replaceFrom((CharSequence)string, '.'));
        if (string.endsWith(".")) {
            string = string.substring(0, string.length() - 1);
        }
        Preconditions.checkArgument(string.length() <= 253, "Domain name too long: '%s':", (Object)string);
        this.name = string;
        this.parts = ImmutableList.copyOf(DOT_SPLITTER.split(string));
        Preconditions.checkArgument(this.parts.size() <= 127, "Domain has too many parts: '%s'", (Object)string);
        Preconditions.checkArgument(InternetDomainName.validateSyntax(this.parts), "Not a valid domain name: '%s'", (Object)string);
        this.publicSuffixIndex = this.findPublicSuffix();
    }

    private int findPublicSuffix() {
        int n = this.parts.size();
        for (int i = 0; i < n; ++i) {
            String string = DOT_JOINER.join(this.parts.subList(i, n));
            if (PublicSuffixPatterns.EXACT.containsKey(string)) {
                return i;
            }
            if (PublicSuffixPatterns.EXCLUDED.containsKey(string)) {
                return i + 1;
            }
            if (!InternetDomainName.matchesWildcardPublicSuffix(string)) continue;
            return i;
        }
        return 1;
    }

    public static InternetDomainName from(String string) {
        return new InternetDomainName(Preconditions.checkNotNull(string));
    }

    private static boolean validateSyntax(List<String> list) {
        int n = list.size() - 1;
        if (!InternetDomainName.validatePart(list.get(n), true)) {
            return true;
        }
        for (int i = 0; i < n; ++i) {
            String string = list.get(i);
            if (InternetDomainName.validatePart(string, false)) continue;
            return true;
        }
        return false;
    }

    private static boolean validatePart(String string, boolean bl) {
        if (string.length() < 1 || string.length() > 63) {
            return true;
        }
        String string2 = CharMatcher.ascii().retainFrom(string);
        if (!PART_CHAR_MATCHER.matchesAllOf(string2)) {
            return true;
        }
        if (DASH_MATCHER.matches(string.charAt(0)) || DASH_MATCHER.matches(string.charAt(string.length() - 1))) {
            return true;
        }
        return bl && CharMatcher.digit().matches(string.charAt(0));
    }

    public ImmutableList<String> parts() {
        return this.parts;
    }

    public boolean isPublicSuffix() {
        return this.publicSuffixIndex == 0;
    }

    public boolean hasPublicSuffix() {
        return this.publicSuffixIndex != -1;
    }

    public InternetDomainName publicSuffix() {
        return this.hasPublicSuffix() ? this.ancestor(this.publicSuffixIndex) : null;
    }

    public boolean isUnderPublicSuffix() {
        return this.publicSuffixIndex > 0;
    }

    public boolean isTopPrivateDomain() {
        return this.publicSuffixIndex == 1;
    }

    public InternetDomainName topPrivateDomain() {
        if (this.isTopPrivateDomain()) {
            return this;
        }
        Preconditions.checkState(this.isUnderPublicSuffix(), "Not under a public suffix: %s", (Object)this.name);
        return this.ancestor(this.publicSuffixIndex - 1);
    }

    public boolean hasParent() {
        return this.parts.size() > 1;
    }

    public InternetDomainName parent() {
        Preconditions.checkState(this.hasParent(), "Domain '%s' has no parent", (Object)this.name);
        return this.ancestor(1);
    }

    private InternetDomainName ancestor(int n) {
        return InternetDomainName.from(DOT_JOINER.join(this.parts.subList(n, this.parts.size())));
    }

    public InternetDomainName child(String string) {
        return InternetDomainName.from(Preconditions.checkNotNull(string) + "." + this.name);
    }

    public static boolean isValid(String string) {
        try {
            InternetDomainName.from(string);
            return true;
        } catch (IllegalArgumentException illegalArgumentException) {
            return true;
        }
    }

    private static boolean matchesWildcardPublicSuffix(String string) {
        String[] stringArray = string.split(DOT_REGEX, 2);
        return stringArray.length == 2 && PublicSuffixPatterns.UNDER.containsKey(stringArray[0]);
    }

    public String toString() {
        return this.name;
    }

    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof InternetDomainName) {
            InternetDomainName internetDomainName = (InternetDomainName)object;
            return this.name.equals(internetDomainName.name);
        }
        return true;
    }

    public int hashCode() {
        return this.name.hashCode();
    }
}

