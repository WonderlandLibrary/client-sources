package org.jsoup.safety;

import org.jsoup.helper.*;
import java.util.*;
import org.jsoup.nodes.*;
import org.jsoup.internal.*;

public class Whitelist
{
    private Set<TagName> tagNames;
    private Map<TagName, Set<AttributeKey>> attributes;
    private Map<TagName, Map<AttributeKey, AttributeValue>> enforcedAttributes;
    private Map<TagName, Map<AttributeKey, Set<Protocol>>> protocols;
    private boolean preserveRelativeLinks;
    
    public static Whitelist none() {
        return new Whitelist();
    }
    
    public static Whitelist simpleText() {
        return new Whitelist().addTags("b", "em", "i", "strong", "u");
    }
    
    public static Whitelist basic() {
        return new Whitelist().addTags("a", "b", "blockquote", "br", "cite", "code", "dd", "dl", "dt", "em", "i", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong", "sub", "sup", "u", "ul").addAttributes("a", "href").addAttributes("blockquote", "cite").addAttributes("q", "cite").addProtocols("a", "href", "ftp", "http", "https", "mailto").addProtocols("blockquote", "cite", "http", "https").addProtocols("cite", "cite", "http", "https").addEnforcedAttribute("a", "rel", "nofollow");
    }
    
    public static Whitelist basicWithImages() {
        return basic().addTags("img").addAttributes("img", "align", "alt", "height", "src", "title", "width").addProtocols("img", "src", "http", "https");
    }
    
    public static Whitelist relaxed() {
        return new Whitelist().addTags("a", "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong", "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u", "ul").addAttributes("a", "href", "title").addAttributes("blockquote", "cite").addAttributes("col", "span", "width").addAttributes("colgroup", "span", "width").addAttributes("img", "align", "alt", "height", "src", "title", "width").addAttributes("ol", "start", "type").addAttributes("q", "cite").addAttributes("table", "summary", "width").addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width").addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope", "width").addAttributes("ul", "type").addProtocols("a", "href", "ftp", "http", "https", "mailto").addProtocols("blockquote", "cite", "http", "https").addProtocols("cite", "cite", "http", "https").addProtocols("img", "src", "http", "https").addProtocols("q", "cite", "http", "https");
    }
    
    public Whitelist() {
        this.tagNames = new HashSet<TagName>();
        this.attributes = new HashMap<TagName, Set<AttributeKey>>();
        this.enforcedAttributes = new HashMap<TagName, Map<AttributeKey, AttributeValue>>();
        this.protocols = new HashMap<TagName, Map<AttributeKey, Set<Protocol>>>();
        this.preserveRelativeLinks = false;
    }
    
    public Whitelist addTags(final String... tags) {
        Validate.notNull(tags);
        for (final String tagName : tags) {
            Validate.notEmpty(tagName);
            this.tagNames.add(TagName.valueOf(tagName));
        }
        return this;
    }
    
    public Whitelist removeTags(final String... tags) {
        Validate.notNull(tags);
        for (final String tag : tags) {
            Validate.notEmpty(tag);
            final TagName tagName = TagName.valueOf(tag);
            if (this.tagNames.remove(tagName)) {
                this.attributes.remove(tagName);
                this.enforcedAttributes.remove(tagName);
                this.protocols.remove(tagName);
            }
        }
        return this;
    }
    
    public Whitelist addAttributes(final String tag, final String... attributes) {
        Validate.notEmpty(tag);
        Validate.notNull(attributes);
        Validate.isTrue(attributes.length > 0, "No attribute names supplied.");
        final TagName tagName = TagName.valueOf(tag);
        if (!this.tagNames.contains(tagName)) {
            this.tagNames.add(tagName);
        }
        final Set<AttributeKey> attributeSet = new HashSet<AttributeKey>();
        for (final String key : attributes) {
            Validate.notEmpty(key);
            attributeSet.add(AttributeKey.valueOf(key));
        }
        if (this.attributes.containsKey(tagName)) {
            final Set<AttributeKey> currentSet = this.attributes.get(tagName);
            currentSet.addAll(attributeSet);
        }
        else {
            this.attributes.put(tagName, attributeSet);
        }
        return this;
    }
    
    public Whitelist removeAttributes(final String tag, final String... attributes) {
        Validate.notEmpty(tag);
        Validate.notNull(attributes);
        Validate.isTrue(attributes.length > 0, "No attribute names supplied.");
        final TagName tagName = TagName.valueOf(tag);
        final Set<AttributeKey> attributeSet = new HashSet<AttributeKey>();
        for (final String key : attributes) {
            Validate.notEmpty(key);
            attributeSet.add(AttributeKey.valueOf(key));
        }
        if (this.tagNames.contains(tagName) && this.attributes.containsKey(tagName)) {
            final Set<AttributeKey> currentSet = this.attributes.get(tagName);
            currentSet.removeAll(attributeSet);
            if (currentSet.isEmpty()) {
                this.attributes.remove(tagName);
            }
        }
        if (tag.equals(":all")) {
            for (final TagName name : this.attributes.keySet()) {
                final Set<AttributeKey> currentSet2 = this.attributes.get(name);
                currentSet2.removeAll(attributeSet);
                if (currentSet2.isEmpty()) {
                    this.attributes.remove(name);
                }
            }
        }
        return this;
    }
    
    public Whitelist addEnforcedAttribute(final String tag, final String attribute, final String value) {
        Validate.notEmpty(tag);
        Validate.notEmpty(attribute);
        Validate.notEmpty(value);
        final TagName tagName = TagName.valueOf(tag);
        if (!this.tagNames.contains(tagName)) {
            this.tagNames.add(tagName);
        }
        final AttributeKey attrKey = AttributeKey.valueOf(attribute);
        final AttributeValue attrVal = AttributeValue.valueOf(value);
        if (this.enforcedAttributes.containsKey(tagName)) {
            this.enforcedAttributes.get(tagName).put(attrKey, attrVal);
        }
        else {
            final Map<AttributeKey, AttributeValue> attrMap = new HashMap<AttributeKey, AttributeValue>();
            attrMap.put(attrKey, attrVal);
            this.enforcedAttributes.put(tagName, attrMap);
        }
        return this;
    }
    
    public Whitelist removeEnforcedAttribute(final String tag, final String attribute) {
        Validate.notEmpty(tag);
        Validate.notEmpty(attribute);
        final TagName tagName = TagName.valueOf(tag);
        if (this.tagNames.contains(tagName) && this.enforcedAttributes.containsKey(tagName)) {
            final AttributeKey attrKey = AttributeKey.valueOf(attribute);
            final Map<AttributeKey, AttributeValue> attrMap = this.enforcedAttributes.get(tagName);
            attrMap.remove(attrKey);
            if (attrMap.isEmpty()) {
                this.enforcedAttributes.remove(tagName);
            }
        }
        return this;
    }
    
    public Whitelist preserveRelativeLinks(final boolean preserve) {
        this.preserveRelativeLinks = preserve;
        return this;
    }
    
    public Whitelist addProtocols(final String tag, final String attribute, final String... protocols) {
        Validate.notEmpty(tag);
        Validate.notEmpty(attribute);
        Validate.notNull(protocols);
        final TagName tagName = TagName.valueOf(tag);
        final AttributeKey attrKey = AttributeKey.valueOf(attribute);
        Map<AttributeKey, Set<Protocol>> attrMap;
        if (this.protocols.containsKey(tagName)) {
            attrMap = this.protocols.get(tagName);
        }
        else {
            attrMap = new HashMap<AttributeKey, Set<Protocol>>();
            this.protocols.put(tagName, attrMap);
        }
        Set<Protocol> protSet;
        if (attrMap.containsKey(attrKey)) {
            protSet = attrMap.get(attrKey);
        }
        else {
            protSet = new HashSet<Protocol>();
            attrMap.put(attrKey, protSet);
        }
        for (final String protocol : protocols) {
            Validate.notEmpty(protocol);
            final Protocol prot = Protocol.valueOf(protocol);
            protSet.add(prot);
        }
        return this;
    }
    
    public Whitelist removeProtocols(final String tag, final String attribute, final String... removeProtocols) {
        Validate.notEmpty(tag);
        Validate.notEmpty(attribute);
        Validate.notNull(removeProtocols);
        final TagName tagName = TagName.valueOf(tag);
        final AttributeKey attr = AttributeKey.valueOf(attribute);
        Validate.isTrue(this.protocols.containsKey(tagName), "Cannot remove a protocol that is not set.");
        final Map<AttributeKey, Set<Protocol>> tagProtocols = this.protocols.get(tagName);
        Validate.isTrue(tagProtocols.containsKey(attr), "Cannot remove a protocol that is not set.");
        final Set<Protocol> attrProtocols = tagProtocols.get(attr);
        for (final String protocol : removeProtocols) {
            Validate.notEmpty(protocol);
            attrProtocols.remove(Protocol.valueOf(protocol));
        }
        if (attrProtocols.isEmpty()) {
            tagProtocols.remove(attr);
            if (tagProtocols.isEmpty()) {
                this.protocols.remove(tagName);
            }
        }
        return this;
    }
    
    protected boolean isSafeTag(final String tag) {
        return this.tagNames.contains(TagName.valueOf(tag));
    }
    
    protected boolean isSafeAttribute(final String tagName, final Element el, final Attribute attr) {
        final TagName tag = TagName.valueOf(tagName);
        final AttributeKey key = AttributeKey.valueOf(attr.getKey());
        final Set<AttributeKey> okSet = this.attributes.get(tag);
        if (okSet == null || !okSet.contains(key)) {
            final Map<AttributeKey, AttributeValue> enforcedSet = this.enforcedAttributes.get(tag);
            if (enforcedSet != null) {
                final Attributes expect = this.getEnforcedAttributes(tagName);
                final String attrKey = attr.getKey();
                if (expect.hasKeyIgnoreCase(attrKey)) {
                    return expect.getIgnoreCase(attrKey).equals(attr.getValue());
                }
            }
            return !tagName.equals(":all") && this.isSafeAttribute(":all", el, attr);
        }
        if (this.protocols.containsKey(tag)) {
            final Map<AttributeKey, Set<Protocol>> attrProts = this.protocols.get(tag);
            return !attrProts.containsKey(key) || this.testValidProtocol(el, attr, attrProts.get(key));
        }
        return true;
    }
    
    private boolean testValidProtocol(final Element el, final Attribute attr, final Set<Protocol> protocols) {
        String value = el.absUrl(attr.getKey());
        if (value.length() == 0) {
            value = attr.getValue();
        }
        if (!this.preserveRelativeLinks) {
            attr.setValue(value);
        }
        for (final Protocol protocol : protocols) {
            String prot = protocol.toString();
            if (prot.equals("#")) {
                if (this.isValidAnchor(value)) {
                    return true;
                }
                continue;
            }
            else {
                prot += ":";
                if (Normalizer.lowerCase(value).startsWith(prot)) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    private boolean isValidAnchor(final String value) {
        return value.startsWith("#") && !value.matches(".*\\s.*");
    }
    
    Attributes getEnforcedAttributes(final String tagName) {
        final Attributes attrs = new Attributes();
        final TagName tag = TagName.valueOf(tagName);
        if (this.enforcedAttributes.containsKey(tag)) {
            final Map<AttributeKey, AttributeValue> keyVals = this.enforcedAttributes.get(tag);
            for (final Map.Entry<AttributeKey, AttributeValue> entry : keyVals.entrySet()) {
                attrs.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return attrs;
    }
    
    static class TagName extends TypedValue
    {
        TagName(final String value) {
            super(value);
        }
        
        static TagName valueOf(final String value) {
            return new TagName(value);
        }
    }
    
    static class AttributeKey extends TypedValue
    {
        AttributeKey(final String value) {
            super(value);
        }
        
        static AttributeKey valueOf(final String value) {
            return new AttributeKey(value);
        }
    }
    
    static class AttributeValue extends TypedValue
    {
        AttributeValue(final String value) {
            super(value);
        }
        
        static AttributeValue valueOf(final String value) {
            return new AttributeValue(value);
        }
    }
    
    static class Protocol extends TypedValue
    {
        Protocol(final String value) {
            super(value);
        }
        
        static Protocol valueOf(final String value) {
            return new Protocol(value);
        }
    }
    
    abstract static class TypedValue
    {
        private String value;
        
        TypedValue(final String value) {
            Validate.notNull(value);
            this.value = value;
        }
        
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = 31 * result + ((this.value == null) ? 0 : this.value.hashCode());
            return result;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            final TypedValue other = (TypedValue)obj;
            if (this.value == null) {
                if (other.value != null) {
                    return false;
                }
            }
            else if (!this.value.equals(other.value)) {
                return false;
            }
            return true;
        }
        
        @Override
        public String toString() {
            return this.value;
        }
    }
}
