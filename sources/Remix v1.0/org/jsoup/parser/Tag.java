package org.jsoup.parser;

import org.jsoup.helper.*;
import java.util.*;

public class Tag
{
    private static final Map<String, Tag> tags;
    private String tagName;
    private boolean isBlock;
    private boolean formatAsBlock;
    private boolean canContainInline;
    private boolean empty;
    private boolean selfClosing;
    private boolean preserveWhitespace;
    private boolean formList;
    private boolean formSubmit;
    private static final String[] blockTags;
    private static final String[] inlineTags;
    private static final String[] emptyTags;
    private static final String[] formatAsInlineTags;
    private static final String[] preserveWhitespaceTags;
    private static final String[] formListedTags;
    private static final String[] formSubmitTags;
    
    private Tag(final String tagName) {
        this.isBlock = true;
        this.formatAsBlock = true;
        this.canContainInline = true;
        this.empty = false;
        this.selfClosing = false;
        this.preserveWhitespace = false;
        this.formList = false;
        this.formSubmit = false;
        this.tagName = tagName;
    }
    
    public String getName() {
        return this.tagName;
    }
    
    public static Tag valueOf(String tagName, final ParseSettings settings) {
        Validate.notNull(tagName);
        Tag tag = Tag.tags.get(tagName);
        if (tag == null) {
            tagName = settings.normalizeTag(tagName);
            Validate.notEmpty(tagName);
            tag = Tag.tags.get(tagName);
            if (tag == null) {
                tag = new Tag(tagName);
                tag.isBlock = false;
            }
        }
        return tag;
    }
    
    public static Tag valueOf(final String tagName) {
        return valueOf(tagName, ParseSettings.preserveCase);
    }
    
    public boolean isBlock() {
        return this.isBlock;
    }
    
    public boolean formatAsBlock() {
        return this.formatAsBlock;
    }
    
    @Deprecated
    public boolean canContainBlock() {
        return this.isBlock;
    }
    
    public boolean isInline() {
        return !this.isBlock;
    }
    
    public boolean isData() {
        return !this.canContainInline && !this.isEmpty();
    }
    
    public boolean isEmpty() {
        return this.empty;
    }
    
    public boolean isSelfClosing() {
        return this.empty || this.selfClosing;
    }
    
    public boolean isKnownTag() {
        return Tag.tags.containsKey(this.tagName);
    }
    
    public static boolean isKnownTag(final String tagName) {
        return Tag.tags.containsKey(tagName);
    }
    
    public boolean preserveWhitespace() {
        return this.preserveWhitespace;
    }
    
    public boolean isFormListed() {
        return this.formList;
    }
    
    public boolean isFormSubmittable() {
        return this.formSubmit;
    }
    
    Tag setSelfClosing() {
        this.selfClosing = true;
        return this;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        final Tag tag = (Tag)o;
        return this.tagName.equals(tag.tagName) && this.canContainInline == tag.canContainInline && this.empty == tag.empty && this.formatAsBlock == tag.formatAsBlock && this.isBlock == tag.isBlock && this.preserveWhitespace == tag.preserveWhitespace && this.selfClosing == tag.selfClosing && this.formList == tag.formList && this.formSubmit == tag.formSubmit;
    }
    
    @Override
    public int hashCode() {
        int result = this.tagName.hashCode();
        result = 31 * result + (this.isBlock ? 1 : 0);
        result = 31 * result + (this.formatAsBlock ? 1 : 0);
        result = 31 * result + (this.canContainInline ? 1 : 0);
        result = 31 * result + (this.empty ? 1 : 0);
        result = 31 * result + (this.selfClosing ? 1 : 0);
        result = 31 * result + (this.preserveWhitespace ? 1 : 0);
        result = 31 * result + (this.formList ? 1 : 0);
        result = 31 * result + (this.formSubmit ? 1 : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return this.tagName;
    }
    
    private static void register(final Tag tag) {
        Tag.tags.put(tag.tagName, tag);
    }
    
    static {
        tags = new HashMap<String, Tag>();
        blockTags = new String[] { "html", "head", "body", "frameset", "script", "noscript", "style", "meta", "link", "title", "frame", "noframes", "section", "nav", "aside", "hgroup", "header", "footer", "p", "h1", "h2", "h3", "h4", "h5", "h6", "ul", "ol", "pre", "div", "blockquote", "hr", "address", "figure", "figcaption", "form", "fieldset", "ins", "del", "dl", "dt", "dd", "li", "table", "caption", "thead", "tfoot", "tbody", "colgroup", "col", "tr", "th", "td", "video", "audio", "canvas", "details", "menu", "plaintext", "template", "article", "main", "svg", "math" };
        inlineTags = new String[] { "object", "base", "font", "tt", "i", "b", "u", "big", "small", "em", "strong", "dfn", "code", "samp", "kbd", "var", "cite", "abbr", "time", "acronym", "mark", "ruby", "rt", "rp", "a", "img", "br", "wbr", "map", "q", "sub", "sup", "bdo", "iframe", "embed", "span", "input", "select", "textarea", "label", "button", "optgroup", "option", "legend", "datalist", "keygen", "output", "progress", "meter", "area", "param", "source", "track", "summary", "command", "device", "area", "basefont", "bgsound", "menuitem", "param", "source", "track", "data", "bdi", "s" };
        emptyTags = new String[] { "meta", "link", "base", "frame", "img", "br", "wbr", "embed", "hr", "input", "keygen", "col", "command", "device", "area", "basefont", "bgsound", "menuitem", "param", "source", "track" };
        formatAsInlineTags = new String[] { "title", "a", "p", "h1", "h2", "h3", "h4", "h5", "h6", "pre", "address", "li", "th", "td", "script", "style", "ins", "del", "s" };
        preserveWhitespaceTags = new String[] { "pre", "plaintext", "title", "textarea" };
        formListedTags = new String[] { "button", "fieldset", "input", "keygen", "object", "output", "select", "textarea" };
        formSubmitTags = new String[] { "input", "keygen", "object", "select", "textarea" };
        for (final String tagName : Tag.blockTags) {
            final Tag tag = new Tag(tagName);
            register(tag);
        }
        for (final String tagName : Tag.inlineTags) {
            final Tag tag = new Tag(tagName);
            tag.isBlock = false;
            tag.formatAsBlock = false;
            register(tag);
        }
        for (final String tagName : Tag.emptyTags) {
            final Tag tag = Tag.tags.get(tagName);
            Validate.notNull(tag);
            tag.canContainInline = false;
            tag.empty = true;
        }
        for (final String tagName : Tag.formatAsInlineTags) {
            final Tag tag = Tag.tags.get(tagName);
            Validate.notNull(tag);
            tag.formatAsBlock = false;
        }
        for (final String tagName : Tag.preserveWhitespaceTags) {
            final Tag tag = Tag.tags.get(tagName);
            Validate.notNull(tag);
            tag.preserveWhitespace = true;
        }
        for (final String tagName : Tag.formListedTags) {
            final Tag tag = Tag.tags.get(tagName);
            Validate.notNull(tag);
            tag.formList = true;
        }
        for (final String tagName : Tag.formSubmitTags) {
            final Tag tag = Tag.tags.get(tagName);
            Validate.notNull(tag);
            tag.formSubmit = true;
        }
    }
}
