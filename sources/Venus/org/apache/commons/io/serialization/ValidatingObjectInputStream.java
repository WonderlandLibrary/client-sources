/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.io.serialization.ClassNameMatcher;
import org.apache.commons.io.serialization.FullClassNameMatcher;
import org.apache.commons.io.serialization.RegexpClassNameMatcher;
import org.apache.commons.io.serialization.WildcardClassNameMatcher;

public class ValidatingObjectInputStream
extends ObjectInputStream {
    private final List<ClassNameMatcher> acceptMatchers = new ArrayList<ClassNameMatcher>();
    private final List<ClassNameMatcher> rejectMatchers = new ArrayList<ClassNameMatcher>();

    public ValidatingObjectInputStream(InputStream inputStream) throws IOException {
        super(inputStream);
    }

    private void validateClassName(String string) throws InvalidClassException {
        for (ClassNameMatcher object : this.rejectMatchers) {
            if (!object.matches(string)) continue;
            this.invalidClassNameFound(string);
        }
        boolean bl = false;
        for (ClassNameMatcher classNameMatcher : this.acceptMatchers) {
            if (!classNameMatcher.matches(string)) continue;
            bl = true;
            break;
        }
        if (!bl) {
            this.invalidClassNameFound(string);
        }
    }

    protected void invalidClassNameFound(String string) throws InvalidClassException {
        throw new InvalidClassException("Class name not accepted: " + string);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
        this.validateClassName(objectStreamClass.getName());
        return super.resolveClass(objectStreamClass);
    }

    public ValidatingObjectInputStream accept(Class<?> ... classArray) {
        for (Class<?> clazz : classArray) {
            this.acceptMatchers.add(new FullClassNameMatcher(clazz.getName()));
        }
        return this;
    }

    public ValidatingObjectInputStream reject(Class<?> ... classArray) {
        for (Class<?> clazz : classArray) {
            this.rejectMatchers.add(new FullClassNameMatcher(clazz.getName()));
        }
        return this;
    }

    public ValidatingObjectInputStream accept(String ... stringArray) {
        for (String string : stringArray) {
            this.acceptMatchers.add(new WildcardClassNameMatcher(string));
        }
        return this;
    }

    public ValidatingObjectInputStream reject(String ... stringArray) {
        for (String string : stringArray) {
            this.rejectMatchers.add(new WildcardClassNameMatcher(string));
        }
        return this;
    }

    public ValidatingObjectInputStream accept(Pattern pattern) {
        this.acceptMatchers.add(new RegexpClassNameMatcher(pattern));
        return this;
    }

    public ValidatingObjectInputStream reject(Pattern pattern) {
        this.rejectMatchers.add(new RegexpClassNameMatcher(pattern));
        return this;
    }

    public ValidatingObjectInputStream accept(ClassNameMatcher classNameMatcher) {
        this.acceptMatchers.add(classNameMatcher);
        return this;
    }

    public ValidatingObjectInputStream reject(ClassNameMatcher classNameMatcher) {
        this.rejectMatchers.add(classNameMatcher);
        return this;
    }
}

