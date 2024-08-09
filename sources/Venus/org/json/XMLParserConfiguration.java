/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.ParserConfiguration;
import org.json.XMLXsiTypeConverter;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class XMLParserConfiguration
extends ParserConfiguration {
    public static final XMLParserConfiguration ORIGINAL = new XMLParserConfiguration();
    public static final XMLParserConfiguration KEEP_STRINGS = new XMLParserConfiguration().withKeepStrings(false);
    private String cDataTagName;
    private boolean convertNilAttributeToNull;
    private Map<String, XMLXsiTypeConverter<?>> xsiTypeMap;
    private Set<String> forceList;

    public XMLParserConfiguration() {
        this.cDataTagName = "content";
        this.convertNilAttributeToNull = false;
        this.xsiTypeMap = Collections.emptyMap();
        this.forceList = Collections.emptySet();
    }

    @Deprecated
    public XMLParserConfiguration(boolean bl) {
        this(bl, "content", false);
    }

    @Deprecated
    public XMLParserConfiguration(String string) {
        this(false, string, false);
    }

    @Deprecated
    public XMLParserConfiguration(boolean bl, String string) {
        super(bl, 512);
        this.cDataTagName = string;
        this.convertNilAttributeToNull = false;
    }

    @Deprecated
    public XMLParserConfiguration(boolean bl, String string, boolean bl2) {
        super(bl, 512);
        this.cDataTagName = string;
        this.convertNilAttributeToNull = bl2;
    }

    private XMLParserConfiguration(boolean bl, String string, boolean bl2, Map<String, XMLXsiTypeConverter<?>> map, Set<String> set, int n) {
        super(bl, n);
        this.cDataTagName = string;
        this.convertNilAttributeToNull = bl2;
        this.xsiTypeMap = Collections.unmodifiableMap(map);
        this.forceList = Collections.unmodifiableSet(set);
    }

    @Override
    protected XMLParserConfiguration clone() {
        return new XMLParserConfiguration(this.keepStrings, this.cDataTagName, this.convertNilAttributeToNull, this.xsiTypeMap, this.forceList, this.maxNestingDepth);
    }

    public XMLParserConfiguration withKeepStrings(boolean bl) {
        return (XMLParserConfiguration)super.withKeepStrings(bl);
    }

    public String getcDataTagName() {
        return this.cDataTagName;
    }

    public XMLParserConfiguration withcDataTagName(String string) {
        XMLParserConfiguration xMLParserConfiguration = this.clone();
        xMLParserConfiguration.cDataTagName = string;
        return xMLParserConfiguration;
    }

    public boolean isConvertNilAttributeToNull() {
        return this.convertNilAttributeToNull;
    }

    public XMLParserConfiguration withConvertNilAttributeToNull(boolean bl) {
        XMLParserConfiguration xMLParserConfiguration = this.clone();
        xMLParserConfiguration.convertNilAttributeToNull = bl;
        return xMLParserConfiguration;
    }

    public Map<String, XMLXsiTypeConverter<?>> getXsiTypeMap() {
        return this.xsiTypeMap;
    }

    public XMLParserConfiguration withXsiTypeMap(Map<String, XMLXsiTypeConverter<?>> map) {
        XMLParserConfiguration xMLParserConfiguration = this.clone();
        HashMap hashMap = new HashMap(map);
        xMLParserConfiguration.xsiTypeMap = Collections.unmodifiableMap(hashMap);
        return xMLParserConfiguration;
    }

    public Set<String> getForceList() {
        return this.forceList;
    }

    public XMLParserConfiguration withForceList(Set<String> set) {
        XMLParserConfiguration xMLParserConfiguration = this.clone();
        HashSet<String> hashSet = new HashSet<String>(set);
        xMLParserConfiguration.forceList = Collections.unmodifiableSet(hashSet);
        return xMLParserConfiguration;
    }

    public XMLParserConfiguration withMaxNestingDepth(int n) {
        return (XMLParserConfiguration)super.withMaxNestingDepth(n);
    }

    public ParserConfiguration withMaxNestingDepth(int n) {
        return this.withMaxNestingDepth(n);
    }

    public ParserConfiguration withKeepStrings(boolean bl) {
        return this.withKeepStrings(bl);
    }

    @Override
    protected ParserConfiguration clone() {
        return this.clone();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

