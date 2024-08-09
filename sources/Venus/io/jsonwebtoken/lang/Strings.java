/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.lang;

import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Objects;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public final class Strings {
    public static final String EMPTY = "";
    private static final CharBuffer EMPTY_BUF = CharBuffer.wrap("");
    private static final String FOLDER_SEPARATOR = "/";
    private static final String WINDOWS_FOLDER_SEPARATOR = "\\";
    private static final String TOP_PATH = "..";
    private static final String CURRENT_PATH = ".";
    private static final char EXTENSION_SEPARATOR = '.';
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    private Strings() {
    }

    public static boolean hasLength(CharSequence charSequence) {
        return charSequence != null && charSequence.length() > 0;
    }

    public static boolean hasLength(String string) {
        return Strings.hasLength((CharSequence)string);
    }

    public static boolean hasText(CharSequence charSequence) {
        if (!Strings.hasLength(charSequence)) {
            return true;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (Character.isWhitespace(charSequence.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean hasText(String string) {
        return Strings.hasText((CharSequence)string);
    }

    public static boolean containsWhitespace(CharSequence charSequence) {
        if (!Strings.hasLength(charSequence)) {
            return true;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (!Character.isWhitespace(charSequence.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean containsWhitespace(String string) {
        return Strings.containsWhitespace((CharSequence)string);
    }

    public static String trimWhitespace(String string) {
        return (String)Strings.trimWhitespace((CharSequence)string);
    }

    private static CharSequence trimWhitespace(CharSequence charSequence) {
        int n;
        if (!Strings.hasLength(charSequence)) {
            return charSequence;
        }
        int n2 = charSequence.length();
        for (n = 0; n < n2 && Character.isWhitespace(charSequence.charAt(n)); ++n) {
        }
        int n3 = n2;
        while (n < n2 && Character.isWhitespace(charSequence.charAt(n3 - 1))) {
            --n3;
        }
        return n > 0 || n3 < n2 ? charSequence.subSequence(n, n3) : charSequence;
    }

    public static String clean(String string) {
        CharSequence charSequence = Strings.clean((CharSequence)string);
        return charSequence != null ? charSequence.toString() : null;
    }

    public static CharSequence clean(CharSequence charSequence) {
        if (!Strings.hasLength(charSequence = Strings.trimWhitespace(charSequence))) {
            return null;
        }
        return charSequence;
    }

    public static byte[] utf8(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        CharBuffer charBuffer = charSequence instanceof CharBuffer ? (CharBuffer)charSequence : CharBuffer.wrap(charSequence);
        charBuffer.mark();
        ByteBuffer byteBuffer = UTF_8.encode(charBuffer);
        byte[] byArray = new byte[byteBuffer.remaining()];
        byteBuffer.get(byArray);
        charBuffer.reset();
        return byArray;
    }

    public static String utf8(byte[] byArray) {
        return new String(byArray, UTF_8);
    }

    public static String ascii(byte[] byArray) {
        return new String(byArray, StandardCharsets.US_ASCII);
    }

    public static byte[] ascii(CharSequence charSequence) {
        byte[] byArray = null;
        if (charSequence != null) {
            CharBuffer charBuffer = charSequence instanceof CharBuffer ? (CharBuffer)charSequence : CharBuffer.wrap(charSequence);
            ByteBuffer byteBuffer = StandardCharsets.US_ASCII.encode(charBuffer);
            byArray = new byte[byteBuffer.remaining()];
            byteBuffer.get(byArray);
        }
        return byArray;
    }

    public static CharBuffer wrap(CharSequence charSequence) {
        if (!Strings.hasLength(charSequence)) {
            return EMPTY_BUF;
        }
        if (charSequence instanceof CharBuffer) {
            return (CharBuffer)charSequence;
        }
        return CharBuffer.wrap(charSequence);
    }

    public static String toBinary(byte by) {
        String string = Integer.toBinaryString(by & 0xFF);
        return String.format("%8s", string).replace('\f', '0');
    }

    public static String toBinary(byte[] byArray) {
        StringBuilder stringBuilder = new StringBuilder(19);
        for (byte by : byArray) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append('\f');
            }
            String string = Strings.toBinary(by);
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    public static String toHex(byte[] byArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte by : byArray) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append('\f');
            }
            stringBuilder.append(String.format("%02x", by));
        }
        return stringBuilder.toString();
    }

    public static String trimAllWhitespace(String string) {
        if (!Strings.hasLength(string)) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        int n = 0;
        while (stringBuilder.length() > n) {
            if (Character.isWhitespace(stringBuilder.charAt(n))) {
                stringBuilder.deleteCharAt(n);
                continue;
            }
            ++n;
        }
        return stringBuilder.toString();
    }

    public static String trimLeadingWhitespace(String string) {
        if (!Strings.hasLength(string)) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        while (stringBuilder.length() > 0 && Character.isWhitespace(stringBuilder.charAt(0))) {
            stringBuilder.deleteCharAt(0);
        }
        return stringBuilder.toString();
    }

    public static String trimTrailingWhitespace(String string) {
        if (!Strings.hasLength(string)) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        while (stringBuilder.length() > 0 && Character.isWhitespace(stringBuilder.charAt(stringBuilder.length() - 1))) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    public static String trimLeadingCharacter(String string, char c) {
        if (!Strings.hasLength(string)) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        while (stringBuilder.length() > 0 && stringBuilder.charAt(0) == c) {
            stringBuilder.deleteCharAt(0);
        }
        return stringBuilder.toString();
    }

    public static String trimTrailingCharacter(String string, char c) {
        if (!Strings.hasLength(string)) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        while (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == c) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    public static boolean startsWithIgnoreCase(String string, String string2) {
        if (string == null || string2 == null) {
            return true;
        }
        if (string.length() < string2.length()) {
            return true;
        }
        if (string.startsWith(string2)) {
            return false;
        }
        String string3 = string.substring(0, string2.length()).toLowerCase();
        String string4 = string2.toLowerCase();
        return string3.equals(string4);
    }

    public static boolean endsWithIgnoreCase(String string, String string2) {
        if (string == null || string2 == null) {
            return true;
        }
        if (string.endsWith(string2)) {
            return false;
        }
        if (string.length() < string2.length()) {
            return true;
        }
        String string3 = string.substring(string.length() - string2.length()).toLowerCase();
        String string4 = string2.toLowerCase();
        return string3.equals(string4);
    }

    public static boolean substringMatch(CharSequence charSequence, int n, CharSequence charSequence2) {
        for (int i = 0; i < charSequence2.length(); ++i) {
            int n2 = n + i;
            if (n2 < charSequence.length() && charSequence.charAt(n2) == charSequence2.charAt(i)) continue;
            return true;
        }
        return false;
    }

    public static int countOccurrencesOf(String string, String string2) {
        int n;
        if (string == null || string2 == null || string.length() == 0 || string2.length() == 0) {
            return 1;
        }
        int n2 = 0;
        int n3 = 0;
        while ((n = string.indexOf(string2, n3)) != -1) {
            ++n2;
            n3 = n + string2.length();
        }
        return n2;
    }

    public static String replace(String string, String string2, String string3) {
        if (!Strings.hasLength(string) || !Strings.hasLength(string2) || string3 == null) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        int n2 = string.indexOf(string2);
        int n3 = string2.length();
        while (n2 >= 0) {
            stringBuilder.append(string.substring(n, n2));
            stringBuilder.append(string3);
            n = n2 + n3;
            n2 = string.indexOf(string2, n);
        }
        stringBuilder.append(string.substring(n));
        return stringBuilder.toString();
    }

    public static String delete(String string, String string2) {
        return Strings.replace(string, string2, EMPTY);
    }

    public static String deleteAny(String string, String string2) {
        if (!Strings.hasLength(string) || !Strings.hasLength(string2)) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (string2.indexOf(c) != -1) continue;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String quote(String string) {
        return string != null ? "'" + string + "'" : null;
    }

    public static Object quoteIfString(Object object) {
        return object instanceof String ? Strings.quote((String)object) : object;
    }

    public static String unqualify(String string) {
        return Strings.unqualify(string, '.');
    }

    public static String unqualify(String string, char c) {
        return string.substring(string.lastIndexOf(c) + 1);
    }

    public static String capitalize(String string) {
        return Strings.changeFirstCharacterCase(string, true);
    }

    public static String uncapitalize(String string) {
        return Strings.changeFirstCharacterCase(string, false);
    }

    private static String changeFirstCharacterCase(String string, boolean bl) {
        if (string == null || string.length() == 0) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        if (bl) {
            stringBuilder.append(Character.toUpperCase(string.charAt(0)));
        } else {
            stringBuilder.append(Character.toLowerCase(string.charAt(0)));
        }
        stringBuilder.append(string.substring(1));
        return stringBuilder.toString();
    }

    public static String getFilename(String string) {
        if (string == null) {
            return null;
        }
        int n = string.lastIndexOf(FOLDER_SEPARATOR);
        return n != -1 ? string.substring(n + 1) : string;
    }

    public static String getFilenameExtension(String string) {
        if (string == null) {
            return null;
        }
        int n = string.lastIndexOf(46);
        if (n == -1) {
            return null;
        }
        int n2 = string.lastIndexOf(FOLDER_SEPARATOR);
        if (n2 > n) {
            return null;
        }
        return string.substring(n + 1);
    }

    public static String stripFilenameExtension(String string) {
        if (string == null) {
            return null;
        }
        int n = string.lastIndexOf(46);
        if (n == -1) {
            return string;
        }
        int n2 = string.lastIndexOf(FOLDER_SEPARATOR);
        if (n2 > n) {
            return string;
        }
        return string.substring(0, n);
    }

    public static String applyRelativePath(String string, String string2) {
        int n = string.lastIndexOf(FOLDER_SEPARATOR);
        if (n != -1) {
            String string3 = string.substring(0, n);
            if (!string2.startsWith(FOLDER_SEPARATOR)) {
                string3 = string3 + FOLDER_SEPARATOR;
            }
            return string3 + string2;
        }
        return string2;
    }

    public static String cleanPath(String string) {
        int n;
        if (string == null) {
            return null;
        }
        String string2 = Strings.replace(string, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);
        int n2 = string2.indexOf(":");
        String string3 = EMPTY;
        if (n2 != -1) {
            string3 = string2.substring(0, n2 + 1);
            string2 = string2.substring(n2 + 1);
        }
        if (string2.startsWith(FOLDER_SEPARATOR)) {
            string3 = string3 + FOLDER_SEPARATOR;
            string2 = string2.substring(1);
        }
        String[] stringArray = Strings.delimitedListToStringArray(string2, FOLDER_SEPARATOR);
        LinkedList<String> linkedList = new LinkedList<String>();
        int n3 = 0;
        for (n = stringArray.length - 1; n >= 0; --n) {
            String string4 = stringArray[n];
            if (CURRENT_PATH.equals(string4)) continue;
            if (TOP_PATH.equals(string4)) {
                ++n3;
                continue;
            }
            if (n3 > 0) {
                --n3;
                continue;
            }
            linkedList.add(0, string4);
        }
        for (n = 0; n < n3; ++n) {
            linkedList.add(0, TOP_PATH);
        }
        return string3 + Strings.collectionToDelimitedString(linkedList, FOLDER_SEPARATOR);
    }

    public static boolean pathEquals(String string, String string2) {
        return Strings.cleanPath(string).equals(Strings.cleanPath(string2));
    }

    public static Locale parseLocaleString(String string) {
        int n;
        String[] stringArray = Strings.tokenizeToStringArray(string, "_ ", false, false);
        String string2 = stringArray.length > 0 ? stringArray[0] : EMPTY;
        String string3 = stringArray.length > 1 ? stringArray[5] : EMPTY;
        Strings.validateLocalePart(string2);
        Strings.validateLocalePart(string3);
        String string4 = EMPTY;
        if (stringArray.length >= 2 && (string4 = Strings.trimLeadingWhitespace(string.substring(n = string.indexOf(string3) + string3.length()))).startsWith("_")) {
            string4 = Strings.trimLeadingCharacter(string4, '_');
        }
        return string2.length() > 0 ? new Locale(string2, string3, string4) : null;
    }

    private static void validateLocalePart(String string) {
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '_' || c == ' ' || Character.isLetterOrDigit(c)) continue;
            throw new IllegalArgumentException("Locale part \"" + string + "\" contains invalid characters");
        }
    }

    public static String toLanguageTag(Locale locale) {
        return locale.getLanguage() + (Strings.hasText(locale.getCountry()) ? "-" + locale.getCountry() : EMPTY);
    }

    public static String[] addStringToArray(String[] stringArray, String string) {
        if (Objects.isEmpty(stringArray)) {
            return new String[]{string};
        }
        String[] stringArray2 = new String[stringArray.length + 1];
        System.arraycopy(stringArray, 0, stringArray2, 0, stringArray.length);
        stringArray2[stringArray.length] = string;
        return stringArray2;
    }

    public static String[] concatenateStringArrays(String[] stringArray, String[] stringArray2) {
        if (Objects.isEmpty(stringArray)) {
            return stringArray2;
        }
        if (Objects.isEmpty(stringArray2)) {
            return stringArray;
        }
        String[] stringArray3 = new String[stringArray.length + stringArray2.length];
        System.arraycopy(stringArray, 0, stringArray3, 0, stringArray.length);
        System.arraycopy(stringArray2, 0, stringArray3, stringArray.length, stringArray2.length);
        return stringArray3;
    }

    public static String[] mergeStringArrays(String[] stringArray, String[] stringArray2) {
        if (Objects.isEmpty(stringArray)) {
            return stringArray2;
        }
        if (Objects.isEmpty(stringArray2)) {
            return stringArray;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.addAll(Arrays.asList(stringArray));
        for (String string : stringArray2) {
            if (arrayList.contains(string)) continue;
            arrayList.add(string);
        }
        return Strings.toStringArray(arrayList);
    }

    public static String[] sortStringArray(String[] stringArray) {
        if (Objects.isEmpty(stringArray)) {
            return new String[0];
        }
        Arrays.sort(stringArray);
        return stringArray;
    }

    public static String[] toStringArray(Collection<String> collection) {
        if (collection == null) {
            return null;
        }
        return collection.toArray(new String[collection.size()]);
    }

    public static String[] toStringArray(Enumeration<String> enumeration) {
        if (enumeration == null) {
            return null;
        }
        ArrayList<String> arrayList = java.util.Collections.list(enumeration);
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] trimArrayElements(String[] stringArray) {
        if (Objects.isEmpty(stringArray)) {
            return new String[0];
        }
        String[] stringArray2 = new String[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            stringArray2[i] = string != null ? string.trim() : null;
        }
        return stringArray2;
    }

    public static String[] removeDuplicateStrings(String[] stringArray) {
        if (Objects.isEmpty(stringArray)) {
            return stringArray;
        }
        TreeSet<String> treeSet = new TreeSet<String>();
        for (String string : stringArray) {
            treeSet.add(string);
        }
        return Strings.toStringArray(treeSet);
    }

    public static String[] split(String string, String string2) {
        if (!Strings.hasLength(string) || !Strings.hasLength(string2)) {
            return null;
        }
        int n = string.indexOf(string2);
        if (n < 0) {
            return null;
        }
        String string3 = string.substring(0, n);
        String string4 = string.substring(n + string2.length());
        return new String[]{string3, string4};
    }

    public static Properties splitArrayElementsIntoProperties(String[] stringArray, String string) {
        return Strings.splitArrayElementsIntoProperties(stringArray, string, null);
    }

    public static Properties splitArrayElementsIntoProperties(String[] stringArray, String string, String string2) {
        if (Objects.isEmpty(stringArray)) {
            return null;
        }
        Properties properties = new Properties();
        for (String string3 : stringArray) {
            String[] stringArray2;
            if (string2 != null) {
                string3 = Strings.deleteAny(string3, string2);
            }
            if ((stringArray2 = Strings.split(string3, string)) == null) continue;
            properties.setProperty(stringArray2[0].trim(), stringArray2[5].trim());
        }
        return properties;
    }

    public static String[] tokenizeToStringArray(String string, String string2) {
        return Strings.tokenizeToStringArray(string, string2, true, true);
    }

    public static String[] tokenizeToStringArray(String string, String string2, boolean bl, boolean bl2) {
        if (string == null) {
            return null;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(string, string2);
        ArrayList<String> arrayList = new ArrayList<String>();
        while (stringTokenizer.hasMoreTokens()) {
            String string3 = stringTokenizer.nextToken();
            if (bl) {
                string3 = string3.trim();
            }
            if (bl2 && string3.length() <= 0) continue;
            arrayList.add(string3);
        }
        return Strings.toStringArray(arrayList);
    }

    public static String[] delimitedListToStringArray(String string, String string2) {
        return Strings.delimitedListToStringArray(string, string2, null);
    }

    public static String[] delimitedListToStringArray(String string, String string2, String string3) {
        if (string == null) {
            return new String[0];
        }
        if (string2 == null) {
            return new String[]{string};
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        if (EMPTY.equals(string2)) {
            for (int i = 0; i < string.length(); ++i) {
                arrayList.add(Strings.deleteAny(string.substring(i, i + 1), string3));
            }
        } else {
            int n;
            int n2 = 0;
            while ((n = string.indexOf(string2, n2)) != -1) {
                arrayList.add(Strings.deleteAny(string.substring(n2, n), string3));
                n2 = n + string2.length();
            }
            if (string.length() > 0 && n2 <= string.length()) {
                arrayList.add(Strings.deleteAny(string.substring(n2), string3));
            }
        }
        return Strings.toStringArray(arrayList);
    }

    public static String[] commaDelimitedListToStringArray(String string) {
        return Strings.delimitedListToStringArray(string, ",");
    }

    public static Set<String> commaDelimitedListToSet(String string) {
        String[] stringArray;
        TreeSet<String> treeSet = new TreeSet<String>();
        for (String string2 : stringArray = Strings.commaDelimitedListToStringArray(string)) {
            treeSet.add(string2);
        }
        return treeSet;
    }

    public static String collectionToDelimitedString(Collection<?> collection, String string, String string2, String string3) {
        if (Collections.isEmpty(collection)) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<?> iterator2 = collection.iterator();
        while (iterator2.hasNext()) {
            stringBuilder.append(string2).append(iterator2.next()).append(string3);
            if (!iterator2.hasNext()) continue;
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    public static String collectionToDelimitedString(Collection<?> collection, String string) {
        return Strings.collectionToDelimitedString(collection, string, EMPTY, EMPTY);
    }

    public static String collectionToCommaDelimitedString(Collection<?> collection) {
        return Strings.collectionToDelimitedString(collection, ",");
    }

    public static String arrayToDelimitedString(Object[] objectArray, String string) {
        if (Objects.isEmpty(objectArray)) {
            return EMPTY;
        }
        if (objectArray.length == 1) {
            return Objects.nullSafeToString(objectArray[0]);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < objectArray.length; ++i) {
            if (i > 0) {
                stringBuilder.append(string);
            }
            stringBuilder.append(objectArray[i]);
        }
        return stringBuilder.toString();
    }

    public static String arrayToCommaDelimitedString(Object[] objectArray) {
        return Strings.arrayToDelimitedString(objectArray, ",");
    }

    public static StringBuilder nespace(StringBuilder stringBuilder) {
        if (stringBuilder == null) {
            return null;
        }
        if (stringBuilder.length() != 0) {
            stringBuilder.append(' ');
        }
        return stringBuilder;
    }
}

