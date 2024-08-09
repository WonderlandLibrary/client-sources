/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson;

import com.google.gson.FieldNamingStrategy;
import java.lang.reflect.Field;
import java.util.Locale;

public enum FieldNamingPolicy implements FieldNamingStrategy
{
    IDENTITY{

        @Override
        public String translateName(Field field) {
            return field.getName();
        }
    }
    ,
    UPPER_CAMEL_CASE{

        @Override
        public String translateName(Field field) {
            return 2.upperCaseFirstLetter(field.getName());
        }
    }
    ,
    UPPER_CAMEL_CASE_WITH_SPACES{

        @Override
        public String translateName(Field field) {
            return 3.upperCaseFirstLetter(3.separateCamelCase(field.getName(), ' '));
        }
    }
    ,
    UPPER_CASE_WITH_UNDERSCORES{

        @Override
        public String translateName(Field field) {
            return 4.separateCamelCase(field.getName(), '_').toUpperCase(Locale.ENGLISH);
        }
    }
    ,
    LOWER_CASE_WITH_UNDERSCORES{

        @Override
        public String translateName(Field field) {
            return 5.separateCamelCase(field.getName(), '_').toLowerCase(Locale.ENGLISH);
        }
    }
    ,
    LOWER_CASE_WITH_DASHES{

        @Override
        public String translateName(Field field) {
            return 6.separateCamelCase(field.getName(), '-').toLowerCase(Locale.ENGLISH);
        }
    }
    ,
    LOWER_CASE_WITH_DOTS{

        @Override
        public String translateName(Field field) {
            return 7.separateCamelCase(field.getName(), '.').toLowerCase(Locale.ENGLISH);
        }
    };


    private FieldNamingPolicy() {
    }

    static String separateCamelCase(String string, char c) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c2 = string.charAt(i);
            if (Character.isUpperCase(c2) && stringBuilder.length() != 0) {
                stringBuilder.append(c);
            }
            stringBuilder.append(c2);
        }
        return stringBuilder.toString();
    }

    static String upperCaseFirstLetter(String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (!Character.isLetter(c)) continue;
            if (Character.isUpperCase(c)) {
                return string;
            }
            char c2 = Character.toUpperCase(c);
            if (i == 0) {
                return c2 + string.substring(1);
            }
            return string.substring(0, i) + c2 + string.substring(i + 1);
        }
        return string;
    }

    FieldNamingPolicy(1 var3_3) {
        this();
    }
}

