/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.nio.charset.Charset;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Beta
@GwtCompatible
@Immutable
public final class MediaType {
    private static final String CHARSET_ATTRIBUTE = "charset";
    private static final ImmutableListMultimap<String, String> UTF_8_CONSTANT_PARAMETERS = ImmutableListMultimap.of("charset", Ascii.toLowerCase(Charsets.UTF_8.name()));
    private static final CharMatcher TOKEN_MATCHER = CharMatcher.ascii().and(CharMatcher.javaIsoControl().negate()).and(CharMatcher.isNot(' ')).and(CharMatcher.noneOf("()<>@,;:\\\"/[]?="));
    private static final CharMatcher QUOTED_TEXT_MATCHER = CharMatcher.ascii().and(CharMatcher.noneOf("\"\\\r"));
    private static final CharMatcher LINEAR_WHITE_SPACE = CharMatcher.anyOf(" \t\r\n");
    private static final String APPLICATION_TYPE = "application";
    private static final String AUDIO_TYPE = "audio";
    private static final String IMAGE_TYPE = "image";
    private static final String TEXT_TYPE = "text";
    private static final String VIDEO_TYPE = "video";
    private static final String WILDCARD = "*";
    private static final Map<MediaType, MediaType> KNOWN_TYPES = Maps.newHashMap();
    public static final MediaType ANY_TYPE = MediaType.createConstant("*", "*");
    public static final MediaType ANY_TEXT_TYPE = MediaType.createConstant("text", "*");
    public static final MediaType ANY_IMAGE_TYPE = MediaType.createConstant("image", "*");
    public static final MediaType ANY_AUDIO_TYPE = MediaType.createConstant("audio", "*");
    public static final MediaType ANY_VIDEO_TYPE = MediaType.createConstant("video", "*");
    public static final MediaType ANY_APPLICATION_TYPE = MediaType.createConstant("application", "*");
    public static final MediaType CACHE_MANIFEST_UTF_8 = MediaType.createConstantUtf8("text", "cache-manifest");
    public static final MediaType CSS_UTF_8 = MediaType.createConstantUtf8("text", "css");
    public static final MediaType CSV_UTF_8 = MediaType.createConstantUtf8("text", "csv");
    public static final MediaType HTML_UTF_8 = MediaType.createConstantUtf8("text", "html");
    public static final MediaType I_CALENDAR_UTF_8 = MediaType.createConstantUtf8("text", "calendar");
    public static final MediaType PLAIN_TEXT_UTF_8 = MediaType.createConstantUtf8("text", "plain");
    public static final MediaType TEXT_JAVASCRIPT_UTF_8 = MediaType.createConstantUtf8("text", "javascript");
    public static final MediaType TSV_UTF_8 = MediaType.createConstantUtf8("text", "tab-separated-values");
    public static final MediaType VCARD_UTF_8 = MediaType.createConstantUtf8("text", "vcard");
    public static final MediaType WML_UTF_8 = MediaType.createConstantUtf8("text", "vnd.wap.wml");
    public static final MediaType XML_UTF_8 = MediaType.createConstantUtf8("text", "xml");
    public static final MediaType VTT_UTF_8 = MediaType.createConstantUtf8("text", "vtt");
    public static final MediaType BMP = MediaType.createConstant("image", "bmp");
    public static final MediaType CRW = MediaType.createConstant("image", "x-canon-crw");
    public static final MediaType GIF = MediaType.createConstant("image", "gif");
    public static final MediaType ICO = MediaType.createConstant("image", "vnd.microsoft.icon");
    public static final MediaType JPEG = MediaType.createConstant("image", "jpeg");
    public static final MediaType PNG = MediaType.createConstant("image", "png");
    public static final MediaType PSD = MediaType.createConstant("image", "vnd.adobe.photoshop");
    public static final MediaType SVG_UTF_8 = MediaType.createConstantUtf8("image", "svg+xml");
    public static final MediaType TIFF = MediaType.createConstant("image", "tiff");
    public static final MediaType WEBP = MediaType.createConstant("image", "webp");
    public static final MediaType MP4_AUDIO = MediaType.createConstant("audio", "mp4");
    public static final MediaType MPEG_AUDIO = MediaType.createConstant("audio", "mpeg");
    public static final MediaType OGG_AUDIO = MediaType.createConstant("audio", "ogg");
    public static final MediaType WEBM_AUDIO = MediaType.createConstant("audio", "webm");
    public static final MediaType L24_AUDIO = MediaType.createConstant("audio", "l24");
    public static final MediaType BASIC_AUDIO = MediaType.createConstant("audio", "basic");
    public static final MediaType AAC_AUDIO = MediaType.createConstant("audio", "aac");
    public static final MediaType VORBIS_AUDIO = MediaType.createConstant("audio", "vorbis");
    public static final MediaType WMA_AUDIO = MediaType.createConstant("audio", "x-ms-wma");
    public static final MediaType WAX_AUDIO = MediaType.createConstant("audio", "x-ms-wax");
    public static final MediaType VND_REAL_AUDIO = MediaType.createConstant("audio", "vnd.rn-realaudio");
    public static final MediaType VND_WAVE_AUDIO = MediaType.createConstant("audio", "vnd.wave");
    public static final MediaType MP4_VIDEO = MediaType.createConstant("video", "mp4");
    public static final MediaType MPEG_VIDEO = MediaType.createConstant("video", "mpeg");
    public static final MediaType OGG_VIDEO = MediaType.createConstant("video", "ogg");
    public static final MediaType QUICKTIME = MediaType.createConstant("video", "quicktime");
    public static final MediaType WEBM_VIDEO = MediaType.createConstant("video", "webm");
    public static final MediaType WMV = MediaType.createConstant("video", "x-ms-wmv");
    public static final MediaType FLV_VIDEO = MediaType.createConstant("video", "x-flv");
    public static final MediaType THREE_GPP_VIDEO = MediaType.createConstant("video", "3gpp");
    public static final MediaType THREE_GPP2_VIDEO = MediaType.createConstant("video", "3gpp2");
    public static final MediaType APPLICATION_XML_UTF_8 = MediaType.createConstantUtf8("application", "xml");
    public static final MediaType ATOM_UTF_8 = MediaType.createConstantUtf8("application", "atom+xml");
    public static final MediaType BZIP2 = MediaType.createConstant("application", "x-bzip2");
    public static final MediaType DART_UTF_8 = MediaType.createConstantUtf8("application", "dart");
    public static final MediaType APPLE_PASSBOOK = MediaType.createConstant("application", "vnd.apple.pkpass");
    public static final MediaType EOT = MediaType.createConstant("application", "vnd.ms-fontobject");
    public static final MediaType EPUB = MediaType.createConstant("application", "epub+zip");
    public static final MediaType FORM_DATA = MediaType.createConstant("application", "x-www-form-urlencoded");
    public static final MediaType KEY_ARCHIVE = MediaType.createConstant("application", "pkcs12");
    public static final MediaType APPLICATION_BINARY = MediaType.createConstant("application", "binary");
    public static final MediaType GZIP = MediaType.createConstant("application", "x-gzip");
    public static final MediaType JAVASCRIPT_UTF_8 = MediaType.createConstantUtf8("application", "javascript");
    public static final MediaType JSON_UTF_8 = MediaType.createConstantUtf8("application", "json");
    public static final MediaType MANIFEST_JSON_UTF_8 = MediaType.createConstantUtf8("application", "manifest+json");
    public static final MediaType KML = MediaType.createConstant("application", "vnd.google-earth.kml+xml");
    public static final MediaType KMZ = MediaType.createConstant("application", "vnd.google-earth.kmz");
    public static final MediaType MBOX = MediaType.createConstant("application", "mbox");
    public static final MediaType APPLE_MOBILE_CONFIG = MediaType.createConstant("application", "x-apple-aspen-config");
    public static final MediaType MICROSOFT_EXCEL = MediaType.createConstant("application", "vnd.ms-excel");
    public static final MediaType MICROSOFT_POWERPOINT = MediaType.createConstant("application", "vnd.ms-powerpoint");
    public static final MediaType MICROSOFT_WORD = MediaType.createConstant("application", "msword");
    public static final MediaType NACL_APPLICATION = MediaType.createConstant("application", "x-nacl");
    public static final MediaType NACL_PORTABLE_APPLICATION = MediaType.createConstant("application", "x-pnacl");
    public static final MediaType OCTET_STREAM = MediaType.createConstant("application", "octet-stream");
    public static final MediaType OGG_CONTAINER = MediaType.createConstant("application", "ogg");
    public static final MediaType OOXML_DOCUMENT = MediaType.createConstant("application", "vnd.openxmlformats-officedocument.wordprocessingml.document");
    public static final MediaType OOXML_PRESENTATION = MediaType.createConstant("application", "vnd.openxmlformats-officedocument.presentationml.presentation");
    public static final MediaType OOXML_SHEET = MediaType.createConstant("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    public static final MediaType OPENDOCUMENT_GRAPHICS = MediaType.createConstant("application", "vnd.oasis.opendocument.graphics");
    public static final MediaType OPENDOCUMENT_PRESENTATION = MediaType.createConstant("application", "vnd.oasis.opendocument.presentation");
    public static final MediaType OPENDOCUMENT_SPREADSHEET = MediaType.createConstant("application", "vnd.oasis.opendocument.spreadsheet");
    public static final MediaType OPENDOCUMENT_TEXT = MediaType.createConstant("application", "vnd.oasis.opendocument.text");
    public static final MediaType PDF = MediaType.createConstant("application", "pdf");
    public static final MediaType POSTSCRIPT = MediaType.createConstant("application", "postscript");
    public static final MediaType PROTOBUF = MediaType.createConstant("application", "protobuf");
    public static final MediaType RDF_XML_UTF_8 = MediaType.createConstantUtf8("application", "rdf+xml");
    public static final MediaType RTF_UTF_8 = MediaType.createConstantUtf8("application", "rtf");
    public static final MediaType SFNT = MediaType.createConstant("application", "font-sfnt");
    public static final MediaType SHOCKWAVE_FLASH = MediaType.createConstant("application", "x-shockwave-flash");
    public static final MediaType SKETCHUP = MediaType.createConstant("application", "vnd.sketchup.skp");
    public static final MediaType SOAP_XML_UTF_8 = MediaType.createConstantUtf8("application", "soap+xml");
    public static final MediaType TAR = MediaType.createConstant("application", "x-tar");
    public static final MediaType WOFF = MediaType.createConstant("application", "font-woff");
    public static final MediaType WOFF2 = MediaType.createConstant("application", "font-woff2");
    public static final MediaType XHTML_UTF_8 = MediaType.createConstantUtf8("application", "xhtml+xml");
    public static final MediaType XRD_UTF_8 = MediaType.createConstantUtf8("application", "xrd+xml");
    public static final MediaType ZIP = MediaType.createConstant("application", "zip");
    private final String type;
    private final String subtype;
    private final ImmutableListMultimap<String, String> parameters;
    @LazyInit
    private String toString;
    @LazyInit
    private int hashCode;
    private static final Joiner.MapJoiner PARAMETER_JOINER = Joiner.on("; ").withKeyValueSeparator("=");

    private static MediaType createConstant(String string, String string2) {
        return MediaType.addKnownType(new MediaType(string, string2, ImmutableListMultimap.of()));
    }

    private static MediaType createConstantUtf8(String string, String string2) {
        return MediaType.addKnownType(new MediaType(string, string2, UTF_8_CONSTANT_PARAMETERS));
    }

    private static MediaType addKnownType(MediaType mediaType) {
        KNOWN_TYPES.put(mediaType, mediaType);
        return mediaType;
    }

    private MediaType(String string, String string2, ImmutableListMultimap<String, String> immutableListMultimap) {
        this.type = string;
        this.subtype = string2;
        this.parameters = immutableListMultimap;
    }

    public String type() {
        return this.type;
    }

    public String subtype() {
        return this.subtype;
    }

    public ImmutableListMultimap<String, String> parameters() {
        return this.parameters;
    }

    private Map<String, ImmutableMultiset<String>> parametersAsMap() {
        return Maps.transformValues(this.parameters.asMap(), new Function<Collection<String>, ImmutableMultiset<String>>(this){
            final MediaType this$0;
            {
                this.this$0 = mediaType;
            }

            @Override
            public ImmutableMultiset<String> apply(Collection<String> collection) {
                return ImmutableMultiset.copyOf(collection);
            }

            @Override
            public Object apply(Object object) {
                return this.apply((Collection)object);
            }
        });
    }

    public Optional<Charset> charset() {
        ImmutableSet immutableSet = ImmutableSet.copyOf(this.parameters.get((Object)CHARSET_ATTRIBUTE));
        switch (immutableSet.size()) {
            case 0: {
                return Optional.absent();
            }
            case 1: {
                return Optional.of(Charset.forName((String)Iterables.getOnlyElement(immutableSet)));
            }
        }
        throw new IllegalStateException("Multiple charset values defined: " + immutableSet);
    }

    public MediaType withoutParameters() {
        return this.parameters.isEmpty() ? this : MediaType.create(this.type, this.subtype);
    }

    public MediaType withParameters(Multimap<String, String> multimap) {
        return MediaType.create(this.type, this.subtype, multimap);
    }

    public MediaType withParameter(String string, String string2) {
        Preconditions.checkNotNull(string);
        Preconditions.checkNotNull(string2);
        String string3 = MediaType.normalizeToken(string);
        ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        for (Map.Entry entry : this.parameters.entries()) {
            String string4 = (String)entry.getKey();
            if (string3.equals(string4)) continue;
            builder.put(string4, entry.getValue());
        }
        builder.put(string3, MediaType.normalizeParameterValue(string3, string2));
        MediaType mediaType = new MediaType(this.type, this.subtype, (ImmutableListMultimap<String, String>)builder.build());
        return (MediaType)MoreObjects.firstNonNull(KNOWN_TYPES.get(mediaType), mediaType);
    }

    public MediaType withCharset(Charset charset) {
        Preconditions.checkNotNull(charset);
        return this.withParameter(CHARSET_ATTRIBUTE, charset.name());
    }

    public boolean hasWildcard() {
        return WILDCARD.equals(this.type) || WILDCARD.equals(this.subtype);
    }

    public boolean is(MediaType mediaType) {
        return !(!mediaType.type.equals(WILDCARD) && !mediaType.type.equals(this.type) || !mediaType.subtype.equals(WILDCARD) && !mediaType.subtype.equals(this.subtype) || !((AbstractCollection)this.parameters.entries()).containsAll(mediaType.parameters.entries()));
    }

    public static MediaType create(String string, String string2) {
        return MediaType.create(string, string2, ImmutableListMultimap.of());
    }

    static MediaType createApplicationType(String string) {
        return MediaType.create(APPLICATION_TYPE, string);
    }

    static MediaType createAudioType(String string) {
        return MediaType.create(AUDIO_TYPE, string);
    }

    static MediaType createImageType(String string) {
        return MediaType.create(IMAGE_TYPE, string);
    }

    static MediaType createTextType(String string) {
        return MediaType.create(TEXT_TYPE, string);
    }

    static MediaType createVideoType(String string) {
        return MediaType.create(VIDEO_TYPE, string);
    }

    private static MediaType create(String string, String string2, Multimap<String, String> multimap) {
        Preconditions.checkNotNull(string);
        Preconditions.checkNotNull(string2);
        Preconditions.checkNotNull(multimap);
        String string3 = MediaType.normalizeToken(string);
        String string4 = MediaType.normalizeToken(string2);
        Preconditions.checkArgument(!WILDCARD.equals(string3) || WILDCARD.equals(string4), "A wildcard type cannot be used with a non-wildcard subtype");
        ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        for (Map.Entry<String, String> entry : multimap.entries()) {
            String string5 = MediaType.normalizeToken(entry.getKey());
            builder.put(string5, MediaType.normalizeParameterValue(string5, entry.getValue()));
        }
        MediaType mediaType = new MediaType(string3, string4, (ImmutableListMultimap<String, String>)builder.build());
        return (MediaType)MoreObjects.firstNonNull(KNOWN_TYPES.get(mediaType), mediaType);
    }

    private static String normalizeToken(String string) {
        Preconditions.checkArgument(TOKEN_MATCHER.matchesAllOf(string));
        return Ascii.toLowerCase(string);
    }

    private static String normalizeParameterValue(String string, String string2) {
        return CHARSET_ATTRIBUTE.equals(string) ? Ascii.toLowerCase(string2) : string2;
    }

    public static MediaType parse(String string) {
        Preconditions.checkNotNull(string);
        Tokenizer tokenizer = new Tokenizer(string);
        try {
            String string2 = tokenizer.consumeToken(TOKEN_MATCHER);
            tokenizer.consumeCharacter('/');
            String string3 = tokenizer.consumeToken(TOKEN_MATCHER);
            ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
            while (tokenizer.hasMore()) {
                String string4;
                tokenizer.consumeTokenIfPresent(LINEAR_WHITE_SPACE);
                tokenizer.consumeCharacter(';');
                tokenizer.consumeTokenIfPresent(LINEAR_WHITE_SPACE);
                String string5 = tokenizer.consumeToken(TOKEN_MATCHER);
                tokenizer.consumeCharacter('=');
                if ('\"' == tokenizer.previewChar()) {
                    tokenizer.consumeCharacter('\"');
                    StringBuilder stringBuilder = new StringBuilder();
                    while ('\"' != tokenizer.previewChar()) {
                        if ('\\' == tokenizer.previewChar()) {
                            tokenizer.consumeCharacter('\\');
                            stringBuilder.append(tokenizer.consumeCharacter(CharMatcher.ascii()));
                            continue;
                        }
                        stringBuilder.append(tokenizer.consumeToken(QUOTED_TEXT_MATCHER));
                    }
                    string4 = stringBuilder.toString();
                    tokenizer.consumeCharacter('\"');
                } else {
                    string4 = tokenizer.consumeToken(TOKEN_MATCHER);
                }
                builder.put(string5, string4);
            }
            return MediaType.create(string2, string3, builder.build());
        } catch (IllegalStateException illegalStateException) {
            throw new IllegalArgumentException("Could not parse '" + string + "'", illegalStateException);
        }
    }

    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof MediaType) {
            MediaType mediaType = (MediaType)object;
            return this.type.equals(mediaType.type) && this.subtype.equals(mediaType.subtype) && this.parametersAsMap().equals(mediaType.parametersAsMap());
        }
        return true;
    }

    public int hashCode() {
        int n = this.hashCode;
        if (n == 0) {
            this.hashCode = n = Objects.hashCode(this.type, this.subtype, this.parametersAsMap());
        }
        return n;
    }

    public String toString() {
        String string = this.toString;
        if (string == null) {
            this.toString = string = this.computeToString();
        }
        return string;
    }

    private String computeToString() {
        StringBuilder stringBuilder = new StringBuilder().append(this.type).append('/').append(this.subtype);
        if (!this.parameters.isEmpty()) {
            stringBuilder.append("; ");
            ListMultimap<String, String> listMultimap = Multimaps.transformValues(this.parameters, new Function<String, String>(this){
                final MediaType this$0;
                {
                    this.this$0 = mediaType;
                }

                @Override
                public String apply(String string) {
                    return MediaType.access$000().matchesAllOf(string) ? string : MediaType.access$100(string);
                }

                @Override
                public Object apply(Object object) {
                    return this.apply((String)object);
                }
            });
            PARAMETER_JOINER.appendTo(stringBuilder, (Iterable<? extends Map.Entry<?, ?>>)listMultimap.entries());
        }
        return stringBuilder.toString();
    }

    private static String escapeAndQuote(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length() + 16).append('\"');
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '\r' || c == '\\' || c == '\"') {
                stringBuilder.append('\\');
            }
            stringBuilder.append(c);
        }
        return stringBuilder.append('\"').toString();
    }

    static CharMatcher access$000() {
        return TOKEN_MATCHER;
    }

    static String access$100(String string) {
        return MediaType.escapeAndQuote(string);
    }

    private static final class Tokenizer {
        final String input;
        int position = 0;

        Tokenizer(String string) {
            this.input = string;
        }

        String consumeTokenIfPresent(CharMatcher charMatcher) {
            Preconditions.checkState(this.hasMore());
            int n = this.position;
            this.position = charMatcher.negate().indexIn(this.input, n);
            return this.hasMore() ? this.input.substring(n, this.position) : this.input.substring(n);
        }

        String consumeToken(CharMatcher charMatcher) {
            int n = this.position;
            String string = this.consumeTokenIfPresent(charMatcher);
            Preconditions.checkState(this.position != n);
            return string;
        }

        char consumeCharacter(CharMatcher charMatcher) {
            Preconditions.checkState(this.hasMore());
            char c = this.previewChar();
            Preconditions.checkState(charMatcher.matches(c));
            ++this.position;
            return c;
        }

        char consumeCharacter(char c) {
            Preconditions.checkState(this.hasMore());
            Preconditions.checkState(this.previewChar() == c);
            ++this.position;
            return c;
        }

        char previewChar() {
            Preconditions.checkState(this.hasMore());
            return this.input.charAt(this.position);
        }

        boolean hasMore() {
            return this.position >= 0 && this.position < this.input.length();
        }
    }
}

