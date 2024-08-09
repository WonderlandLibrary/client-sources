/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.io.Serializable;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.apache.logging.log4j.util.Strings;

public class StructuredDataId
implements Serializable,
StringBuilderFormattable {
    public static final StructuredDataId TIME_QUALITY = new StructuredDataId("timeQuality", null, new String[]{"tzKnown", "isSynced", "syncAccuracy"});
    public static final StructuredDataId ORIGIN = new StructuredDataId("origin", null, new String[]{"ip", "enterpriseId", "software", "swVersion"});
    public static final StructuredDataId META = new StructuredDataId("meta", null, new String[]{"sequenceId", "sysUpTime", "language"});
    public static final int RESERVED = -1;
    private static final long serialVersionUID = 9031746276396249990L;
    private static final int MAX_LENGTH = 32;
    private static final String AT_SIGN = "@";
    private final String name;
    private final int enterpriseNumber;
    private final String[] required;
    private final String[] optional;

    protected StructuredDataId(String string, String[] stringArray, String[] stringArray2) {
        int n = -1;
        if (string != null) {
            if (string.length() > 32) {
                throw new IllegalArgumentException(String.format("Length of id %s exceeds maximum of %d characters", string, 32));
            }
            n = string.indexOf(AT_SIGN);
        }
        if (n > 0) {
            this.name = string.substring(0, n);
            this.enterpriseNumber = Integer.parseInt(string.substring(n + 1));
        } else {
            this.name = string;
            this.enterpriseNumber = -1;
        }
        this.required = stringArray;
        this.optional = stringArray2;
    }

    public StructuredDataId(String string, int n, String[] stringArray, String[] stringArray2) {
        if (string == null) {
            throw new IllegalArgumentException("No structured id name was supplied");
        }
        if (string.contains(AT_SIGN)) {
            throw new IllegalArgumentException("Structured id name cannot contain an " + Strings.quote(AT_SIGN));
        }
        if (n <= 0) {
            throw new IllegalArgumentException("No enterprise number was supplied");
        }
        this.name = string;
        this.enterpriseNumber = n;
        String string2 = string + AT_SIGN + n;
        if (string2.length() > 32) {
            throw new IllegalArgumentException("Length of id exceeds maximum of 32 characters: " + string2);
        }
        this.required = stringArray;
        this.optional = stringArray2;
    }

    public StructuredDataId makeId(StructuredDataId structuredDataId) {
        if (structuredDataId == null) {
            return this;
        }
        return this.makeId(structuredDataId.getName(), structuredDataId.getEnterpriseNumber());
    }

    public StructuredDataId makeId(String string, int n) {
        String[] stringArray;
        String[] stringArray2;
        String string2;
        if (n <= 0) {
            return this;
        }
        if (this.name != null) {
            string2 = this.name;
            stringArray2 = this.required;
            stringArray = this.optional;
        } else {
            string2 = string;
            stringArray2 = null;
            stringArray = null;
        }
        return new StructuredDataId(string2, n, stringArray2, stringArray);
    }

    public String[] getRequired() {
        return this.required;
    }

    public String[] getOptional() {
        return this.optional;
    }

    public String getName() {
        return this.name;
    }

    public int getEnterpriseNumber() {
        return this.enterpriseNumber;
    }

    public boolean isReserved() {
        return this.enterpriseNumber <= 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.name.length() + 10);
        this.formatTo(stringBuilder);
        return stringBuilder.toString();
    }

    @Override
    public void formatTo(StringBuilder stringBuilder) {
        if (this.isReserved()) {
            stringBuilder.append(this.name);
        } else {
            stringBuilder.append(this.name).append(AT_SIGN).append(this.enterpriseNumber);
        }
    }
}

