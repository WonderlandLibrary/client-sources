/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.FieldNamingStrategy;
import java.lang.reflect.Field;
import java.util.Locale;

public enum FieldNamingPolicy implements FieldNamingStrategy
{
    IDENTITY{

        @Override
        public String translateName(Field f) {
            return f.getName();
        }
    }
    ,
    UPPER_CAMEL_CASE{

        @Override
        public String translateName(Field f) {
            return 2.upperCaseFirstLetter(f.getName());
        }
    }
    ,
    UPPER_CAMEL_CASE_WITH_SPACES{

        @Override
        public String translateName(Field f) {
            return 3.upperCaseFirstLetter(3.separateCamelCase(f.getName(), ' '));
        }
    }
    ,
    UPPER_CASE_WITH_UNDERSCORES{

        @Override
        public String translateName(Field f) {
            return 4.separateCamelCase(f.getName(), '_').toUpperCase(Locale.ENGLISH);
        }
    }
    ,
    LOWER_CASE_WITH_UNDERSCORES{

        @Override
        public String translateName(Field f) {
            return 5.separateCamelCase(f.getName(), '_').toLowerCase(Locale.ENGLISH);
        }
    }
    ,
    LOWER_CASE_WITH_DASHES{

        @Override
        public String translateName(Field f) {
            return 6.separateCamelCase(f.getName(), '-').toLowerCase(Locale.ENGLISH);
        }
    }
    ,
    LOWER_CASE_WITH_DOTS{

        @Override
        public String translateName(Field f) {
            return 7.separateCamelCase(f.getName(), '.').toLowerCase(Locale.ENGLISH);
        }
    };


    static String separateCamelCase(String name, char separator) {
        StringBuilder translation = new StringBuilder();
        int length = name.length();
        for (int i = 0; i < length; ++i) {
            char character = name.charAt(i);
            if (Character.isUpperCase(character) && translation.length() != 0) {
                translation.append(separator);
            }
            translation.append(character);
        }
        return translation.toString();
    }

    static String upperCaseFirstLetter(String s) {
        int length = s.length();
        for (int i = 0; i < length; ++i) {
            char c = s.charAt(i);
            if (!Character.isLetter(c)) continue;
            if (Character.isUpperCase(c)) {
                return s;
            }
            char uppercased = Character.toUpperCase(c);
            if (i == 0) {
                return uppercased + s.substring(1);
            }
            return s.substring(0, i) + uppercased + s.substring(i + 1);
        }
        return s;
    }
}

