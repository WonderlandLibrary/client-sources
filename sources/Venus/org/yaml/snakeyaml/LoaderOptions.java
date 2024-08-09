/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml;

import org.yaml.snakeyaml.inspector.TagInspector;
import org.yaml.snakeyaml.inspector.UnTrustedTagInspector;

public class LoaderOptions {
    private boolean allowDuplicateKeys = true;
    private boolean wrappedToRootException = false;
    private int maxAliasesForCollections = 50;
    private boolean allowRecursiveKeys = false;
    private boolean processComments = false;
    private boolean enumCaseSensitive = true;
    private int nestingDepthLimit = 50;
    private int codePointLimit = 0x300000;
    private TagInspector tagInspector = new UnTrustedTagInspector();

    public final boolean isAllowDuplicateKeys() {
        return this.allowDuplicateKeys;
    }

    public void setAllowDuplicateKeys(boolean bl) {
        this.allowDuplicateKeys = bl;
    }

    public final boolean isWrappedToRootException() {
        return this.wrappedToRootException;
    }

    public void setWrappedToRootException(boolean bl) {
        this.wrappedToRootException = bl;
    }

    public final int getMaxAliasesForCollections() {
        return this.maxAliasesForCollections;
    }

    public void setMaxAliasesForCollections(int n) {
        this.maxAliasesForCollections = n;
    }

    public final boolean getAllowRecursiveKeys() {
        return this.allowRecursiveKeys;
    }

    public void setAllowRecursiveKeys(boolean bl) {
        this.allowRecursiveKeys = bl;
    }

    public final boolean isProcessComments() {
        return this.processComments;
    }

    public LoaderOptions setProcessComments(boolean bl) {
        this.processComments = bl;
        return this;
    }

    public final boolean isEnumCaseSensitive() {
        return this.enumCaseSensitive;
    }

    public void setEnumCaseSensitive(boolean bl) {
        this.enumCaseSensitive = bl;
    }

    public final int getNestingDepthLimit() {
        return this.nestingDepthLimit;
    }

    public void setNestingDepthLimit(int n) {
        this.nestingDepthLimit = n;
    }

    public final int getCodePointLimit() {
        return this.codePointLimit;
    }

    public void setCodePointLimit(int n) {
        this.codePointLimit = n;
    }

    public TagInspector getTagInspector() {
        return this.tagInspector;
    }

    public void setTagInspector(TagInspector tagInspector) {
        this.tagInspector = tagInspector;
    }
}

