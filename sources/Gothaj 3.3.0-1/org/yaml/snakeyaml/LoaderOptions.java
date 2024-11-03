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
   private int codePointLimit = 3145728;
   private TagInspector tagInspector = new UnTrustedTagInspector();

   public final boolean isAllowDuplicateKeys() {
      return this.allowDuplicateKeys;
   }

   public void setAllowDuplicateKeys(boolean allowDuplicateKeys) {
      this.allowDuplicateKeys = allowDuplicateKeys;
   }

   public final boolean isWrappedToRootException() {
      return this.wrappedToRootException;
   }

   public void setWrappedToRootException(boolean wrappedToRootException) {
      this.wrappedToRootException = wrappedToRootException;
   }

   public final int getMaxAliasesForCollections() {
      return this.maxAliasesForCollections;
   }

   public void setMaxAliasesForCollections(int maxAliasesForCollections) {
      this.maxAliasesForCollections = maxAliasesForCollections;
   }

   public final boolean getAllowRecursiveKeys() {
      return this.allowRecursiveKeys;
   }

   public void setAllowRecursiveKeys(boolean allowRecursiveKeys) {
      this.allowRecursiveKeys = allowRecursiveKeys;
   }

   public final boolean isProcessComments() {
      return this.processComments;
   }

   public LoaderOptions setProcessComments(boolean processComments) {
      this.processComments = processComments;
      return this;
   }

   public final boolean isEnumCaseSensitive() {
      return this.enumCaseSensitive;
   }

   public void setEnumCaseSensitive(boolean enumCaseSensitive) {
      this.enumCaseSensitive = enumCaseSensitive;
   }

   public final int getNestingDepthLimit() {
      return this.nestingDepthLimit;
   }

   public void setNestingDepthLimit(int nestingDepthLimit) {
      this.nestingDepthLimit = nestingDepthLimit;
   }

   public final int getCodePointLimit() {
      return this.codePointLimit;
   }

   public void setCodePointLimit(int codePointLimit) {
      this.codePointLimit = codePointLimit;
   }

   public TagInspector getTagInspector() {
      return this.tagInspector;
   }

   public void setTagInspector(TagInspector tagInspector) {
      this.tagInspector = tagInspector;
   }
}
