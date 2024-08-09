/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.ComponentFlattener;
import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.FlattenerListener;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextFormat;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyFormat;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.adventure.util.Services;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class LegacyComponentSerializerImpl
implements LegacyComponentSerializer {
    static final Pattern DEFAULT_URL_PATTERN = Pattern.compile("(?:(https?)://)?([-\\w_.]+\\.\\w{2,})(/\\S*)?");
    static final Pattern URL_SCHEME_PATTERN = Pattern.compile("^[a-z][a-z0-9+\\-.]*:");
    private static final TextDecoration[] DECORATIONS = TextDecoration.values();
    private static final char LEGACY_BUNGEE_HEX_CHAR = 'x';
    private static final List<TextFormat> FORMATS;
    private static final String LEGACY_CHARS;
    private static final Optional<LegacyComponentSerializer.Provider> SERVICE;
    static final Consumer<LegacyComponentSerializer.Builder> BUILDER;
    private final char character;
    private final char hexCharacter;
    @Nullable
    private final TextReplacementConfig urlReplacementConfig;
    private final boolean hexColours;
    private final boolean useTerriblyStupidHexFormat;
    private final ComponentFlattener flattener;

    LegacyComponentSerializerImpl(char c, char c2, @Nullable TextReplacementConfig textReplacementConfig, boolean bl, boolean bl2, ComponentFlattener componentFlattener) {
        this.character = c;
        this.hexCharacter = c2;
        this.urlReplacementConfig = textReplacementConfig;
        this.hexColours = bl;
        this.useTerriblyStupidHexFormat = bl2;
        this.flattener = componentFlattener;
    }

    @Nullable
    private FormatCodeType determineFormatType(char c, String string, int n) {
        if (n >= 14) {
            int n2 = n - 14;
            int n3 = n - 13;
            if (string.charAt(n2) == this.character && string.charAt(n3) == 'x') {
                return FormatCodeType.BUNGEECORD_UNUSUAL_HEX;
            }
        }
        if (c == this.hexCharacter && string.length() - n >= 6) {
            return FormatCodeType.KYORI_HEX;
        }
        if (LEGACY_CHARS.indexOf(c) != -1) {
            return FormatCodeType.MOJANG_LEGACY;
        }
        return null;
    }

    @Nullable
    static LegacyFormat legacyFormat(char c) {
        int n = LEGACY_CHARS.indexOf(c);
        if (n != -1) {
            TextFormat textFormat = FORMATS.get(n);
            if (textFormat instanceof NamedTextColor) {
                return new LegacyFormat((NamedTextColor)textFormat);
            }
            if (textFormat instanceof TextDecoration) {
                return new LegacyFormat((TextDecoration)textFormat);
            }
            if (textFormat instanceof Reset) {
                return LegacyFormat.RESET;
            }
        }
        return null;
    }

    @Nullable
    private DecodedFormat decodeTextFormat(char c, String string, int n) {
        FormatCodeType formatCodeType = this.determineFormatType(c, string, n);
        if (formatCodeType == null) {
            return null;
        }
        if (formatCodeType == FormatCodeType.KYORI_HEX) {
            @Nullable TextColor textColor = LegacyComponentSerializerImpl.tryParseHexColor(string.substring(n, n + 6));
            if (textColor != null) {
                return new DecodedFormat(formatCodeType, textColor, null);
            }
        } else {
            if (formatCodeType == FormatCodeType.MOJANG_LEGACY) {
                return new DecodedFormat(formatCodeType, FORMATS.get(LEGACY_CHARS.indexOf(c)), null);
            }
            if (formatCodeType == FormatCodeType.BUNGEECORD_UNUSUAL_HEX) {
                StringBuilder stringBuilder = new StringBuilder(6);
                for (int i = n - 1; i >= n - 11; i -= 2) {
                    stringBuilder.append(string.charAt(i));
                }
                @Nullable TextColor textColor = LegacyComponentSerializerImpl.tryParseHexColor(stringBuilder.reverse().toString());
                if (textColor != null) {
                    return new DecodedFormat(formatCodeType, textColor, null);
                }
            }
        }
        return null;
    }

    @Nullable
    private static TextColor tryParseHexColor(String string) {
        try {
            int n = Integer.parseInt(string, 16);
            return TextColor.color(n);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    private static boolean isHexTextColor(TextFormat textFormat) {
        return textFormat instanceof TextColor && !(textFormat instanceof NamedTextColor);
    }

    private String toLegacyCode(TextFormat textFormat) {
        if (LegacyComponentSerializerImpl.isHexTextColor(textFormat)) {
            TextColor textColor = (TextColor)textFormat;
            if (this.hexColours) {
                String string = String.format("%06x", textColor.value());
                if (this.useTerriblyStupidHexFormat) {
                    StringBuilder stringBuilder = new StringBuilder(String.valueOf('x'));
                    int n = string.length();
                    for (int i = 0; i < n; ++i) {
                        stringBuilder.append(this.character).append(string.charAt(i));
                    }
                    return stringBuilder.toString();
                }
                return this.hexCharacter + string;
            }
            textFormat = NamedTextColor.nearestTo(textColor);
        }
        int n = FORMATS.indexOf(textFormat);
        return Character.toString(LEGACY_CHARS.charAt(n));
    }

    private TextComponent extractUrl(TextComponent textComponent) {
        if (this.urlReplacementConfig == null) {
            return textComponent;
        }
        Component component = textComponent.replaceText(this.urlReplacementConfig);
        if (component instanceof TextComponent) {
            return (TextComponent)component;
        }
        return (TextComponent)((TextComponent.Builder)Component.text().append(component)).build();
    }

    @Override
    @NotNull
    public TextComponent deserialize(@NotNull String string) {
        Object object;
        int n = string.lastIndexOf(this.character, string.length() - 2);
        if (n == -1) {
            return this.extractUrl(Component.text(string));
        }
        ArrayList<TextComponent> arrayList = new ArrayList<TextComponent>();
        TextComponent.Builder builder = null;
        boolean bl = false;
        int n2 = string.length();
        do {
            if ((object = this.decodeTextFormat(string.charAt(n + 1), string, n + 2)) == null) continue;
            int n3 = n + (((DecodedFormat)object).encodedFormat == FormatCodeType.KYORI_HEX ? 8 : 2);
            if (n3 != n2) {
                if (builder != null) {
                    if (bl) {
                        arrayList.add((TextComponent)builder.build());
                        bl = false;
                        builder = Component.text();
                    } else {
                        builder = (TextComponent.Builder)Component.text().append((Component)builder.build());
                    }
                } else {
                    builder = Component.text();
                }
                builder.content(string.substring(n3, n2));
            } else if (builder == null) {
                builder = Component.text();
            }
            if (!bl) {
                bl = LegacyComponentSerializerImpl.applyFormat(builder, ((DecodedFormat)object).format);
            }
            if (((DecodedFormat)object).encodedFormat == FormatCodeType.BUNGEECORD_UNUSUAL_HEX) {
                n -= 12;
            }
            n2 = n;
        } while ((n = string.lastIndexOf(this.character, n - 1)) != -1);
        if (builder != null) {
            arrayList.add((TextComponent)builder.build());
        }
        Object object2 = object = n2 > 0 ? string.substring(0, n2) : "";
        if (arrayList.size() == 1 && ((String)object).isEmpty()) {
            return this.extractUrl((TextComponent)arrayList.get(0));
        }
        Collections.reverse(arrayList);
        return this.extractUrl((TextComponent)((TextComponent.Builder)Component.text().content((String)object).append(arrayList)).build());
    }

    @Override
    @NotNull
    public String serialize(@NotNull Component component) {
        Cereal cereal = new Cereal(this, null);
        this.flattener.flatten(component, cereal);
        return cereal.toString();
    }

    private static boolean applyFormat(@NotNull TextComponent.Builder builder, @NotNull TextFormat textFormat) {
        if (textFormat instanceof TextColor) {
            builder.colorIfAbsent((TextColor)textFormat);
            return false;
        }
        if (textFormat instanceof TextDecoration) {
            builder.decoration((TextDecoration)textFormat, TextDecoration.State.TRUE);
            return true;
        }
        if (textFormat instanceof Reset) {
            return false;
        }
        throw new IllegalArgumentException(String.format("unknown format '%s'", textFormat.getClass()));
    }

    @Override
    @NotNull
    public LegacyComponentSerializer.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    @NotNull
    public Object serialize(@NotNull Component component) {
        return this.serialize(component);
    }

    @Override
    @NotNull
    public Component deserialize(@NotNull Object object) {
        return this.deserialize((String)object);
    }

    @Override
    @NotNull
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }

    private static Consumer lambda$static$1() {
        return LegacyComponentSerializerImpl::lambda$static$0;
    }

    private static void lambda$static$0(LegacyComponentSerializer.Builder builder) {
    }

    static Optional access$000() {
        return SERVICE;
    }

    static String access$300(LegacyComponentSerializerImpl legacyComponentSerializerImpl, TextFormat textFormat) {
        return legacyComponentSerializerImpl.toLegacyCode(textFormat);
    }

    static char access$400(LegacyComponentSerializerImpl legacyComponentSerializerImpl) {
        return legacyComponentSerializerImpl.character;
    }

    static TextDecoration[] access$500() {
        return DECORATIONS;
    }

    static char access$800(LegacyComponentSerializerImpl legacyComponentSerializerImpl) {
        return legacyComponentSerializerImpl.hexCharacter;
    }

    static TextReplacementConfig access$900(LegacyComponentSerializerImpl legacyComponentSerializerImpl) {
        return legacyComponentSerializerImpl.urlReplacementConfig;
    }

    static boolean access$1000(LegacyComponentSerializerImpl legacyComponentSerializerImpl) {
        return legacyComponentSerializerImpl.hexColours;
    }

    static boolean access$1100(LegacyComponentSerializerImpl legacyComponentSerializerImpl) {
        return legacyComponentSerializerImpl.useTerriblyStupidHexFormat;
    }

    static ComponentFlattener access$1200(LegacyComponentSerializerImpl legacyComponentSerializerImpl) {
        return legacyComponentSerializerImpl.flattener;
    }

    static {
        LinkedHashMap<Object, String> linkedHashMap = new LinkedHashMap<Object, String>(22);
        linkedHashMap.put(NamedTextColor.BLACK, "0");
        linkedHashMap.put(NamedTextColor.DARK_BLUE, "1");
        linkedHashMap.put(NamedTextColor.DARK_GREEN, "2");
        linkedHashMap.put(NamedTextColor.DARK_AQUA, "3");
        linkedHashMap.put(NamedTextColor.DARK_RED, "4");
        linkedHashMap.put(NamedTextColor.DARK_PURPLE, "5");
        linkedHashMap.put(NamedTextColor.GOLD, "6");
        linkedHashMap.put(NamedTextColor.GRAY, "7");
        linkedHashMap.put(NamedTextColor.DARK_GRAY, "8");
        linkedHashMap.put(NamedTextColor.BLUE, "9");
        linkedHashMap.put(NamedTextColor.GREEN, "a");
        linkedHashMap.put(NamedTextColor.AQUA, "b");
        linkedHashMap.put(NamedTextColor.RED, "c");
        linkedHashMap.put(NamedTextColor.LIGHT_PURPLE, "d");
        linkedHashMap.put(NamedTextColor.YELLOW, "e");
        linkedHashMap.put(NamedTextColor.WHITE, "f");
        linkedHashMap.put(TextDecoration.OBFUSCATED, "k");
        linkedHashMap.put(TextDecoration.BOLD, "l");
        linkedHashMap.put(TextDecoration.STRIKETHROUGH, "m");
        linkedHashMap.put(TextDecoration.UNDERLINED, "n");
        linkedHashMap.put(TextDecoration.ITALIC, "o");
        linkedHashMap.put(Reset.INSTANCE, "r");
        FORMATS = Collections.unmodifiableList(new ArrayList(linkedHashMap.keySet()));
        LEGACY_CHARS = String.join((CharSequence)"", linkedHashMap.values());
        if (FORMATS.size() != LEGACY_CHARS.length()) {
            throw new IllegalStateException("FORMATS length differs from LEGACY_CHARS length");
        }
        SERVICE = Services.service(LegacyComponentSerializer.Provider.class);
        BUILDER = SERVICE.map(LegacyComponentSerializer.Provider::legacy).orElseGet(LegacyComponentSerializerImpl::lambda$static$1);
    }

    static final class DecodedFormat {
        final FormatCodeType encodedFormat;
        final TextFormat format;

        private DecodedFormat(FormatCodeType formatCodeType, TextFormat textFormat) {
            if (textFormat == null) {
                throw new IllegalStateException("No format found");
            }
            this.encodedFormat = formatCodeType;
            this.format = textFormat;
        }

        DecodedFormat(FormatCodeType formatCodeType, TextFormat textFormat, 1 var3_3) {
            this(formatCodeType, textFormat);
        }
    }

    static enum FormatCodeType {
        MOJANG_LEGACY,
        KYORI_HEX,
        BUNGEECORD_UNUSUAL_HEX;

    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class BuilderImpl
    implements LegacyComponentSerializer.Builder {
        private char character = (char)167;
        private char hexCharacter = (char)35;
        private TextReplacementConfig urlReplacementConfig = null;
        private boolean hexColours = false;
        private boolean useTerriblyStupidHexFormat = false;
        private ComponentFlattener flattener = ComponentFlattener.basic();

        BuilderImpl() {
            BUILDER.accept(this);
        }

        BuilderImpl(@NotNull LegacyComponentSerializerImpl legacyComponentSerializerImpl) {
            this();
            this.character = LegacyComponentSerializerImpl.access$400(legacyComponentSerializerImpl);
            this.hexCharacter = LegacyComponentSerializerImpl.access$800(legacyComponentSerializerImpl);
            this.urlReplacementConfig = LegacyComponentSerializerImpl.access$900(legacyComponentSerializerImpl);
            this.hexColours = LegacyComponentSerializerImpl.access$1000(legacyComponentSerializerImpl);
            this.useTerriblyStupidHexFormat = LegacyComponentSerializerImpl.access$1100(legacyComponentSerializerImpl);
            this.flattener = LegacyComponentSerializerImpl.access$1200(legacyComponentSerializerImpl);
        }

        @Override
        @NotNull
        public LegacyComponentSerializer.Builder character(char c) {
            this.character = c;
            return this;
        }

        @Override
        @NotNull
        public LegacyComponentSerializer.Builder hexCharacter(char c) {
            this.hexCharacter = c;
            return this;
        }

        @Override
        @NotNull
        public LegacyComponentSerializer.Builder extractUrls() {
            return this.extractUrls(DEFAULT_URL_PATTERN, null);
        }

        @Override
        @NotNull
        public LegacyComponentSerializer.Builder extractUrls(@NotNull Pattern pattern) {
            return this.extractUrls(pattern, null);
        }

        @Override
        @NotNull
        public LegacyComponentSerializer.Builder extractUrls(@Nullable Style style) {
            return this.extractUrls(DEFAULT_URL_PATTERN, style);
        }

        @Override
        @NotNull
        public LegacyComponentSerializer.Builder extractUrls(@NotNull Pattern pattern, @Nullable Style style) {
            Objects.requireNonNull(pattern, "pattern");
            this.urlReplacementConfig = (TextReplacementConfig)TextReplacementConfig.builder().match(pattern).replacement(arg_0 -> BuilderImpl.lambda$extractUrls$0(style, arg_0)).build();
            return this;
        }

        @Override
        @NotNull
        public LegacyComponentSerializer.Builder hexColors() {
            this.hexColours = true;
            return this;
        }

        @Override
        @NotNull
        public LegacyComponentSerializer.Builder useUnusualXRepeatedCharacterHexFormat() {
            this.useTerriblyStupidHexFormat = true;
            return this;
        }

        @Override
        @NotNull
        public LegacyComponentSerializer.Builder flattener(@NotNull ComponentFlattener componentFlattener) {
            this.flattener = Objects.requireNonNull(componentFlattener, "flattener");
            return this;
        }

        @Override
        @NotNull
        public LegacyComponentSerializer build() {
            return new LegacyComponentSerializerImpl(this.character, this.hexCharacter, this.urlReplacementConfig, this.hexColours, this.useTerriblyStupidHexFormat, this.flattener);
        }

        @Override
        @NotNull
        public Object build() {
            return this.build();
        }

        private static ComponentLike lambda$extractUrls$0(Style style, TextComponent.Builder builder) {
            String string = builder.content();
            if (!URL_SCHEME_PATTERN.matcher(string).find()) {
                string = "http://" + string;
            }
            return (style == null ? builder : (TextComponent.Builder)builder.style(style)).clickEvent(ClickEvent.openUrl(string));
        }
    }

    private final class Cereal
    implements FlattenerListener {
        private final StringBuilder sb;
        private final StyleState style;
        @Nullable
        private TextFormat lastWritten;
        private StyleState[] styles;
        private int head;
        final LegacyComponentSerializerImpl this$0;

        private Cereal(LegacyComponentSerializerImpl legacyComponentSerializerImpl) {
            this.this$0 = legacyComponentSerializerImpl;
            this.sb = new StringBuilder();
            this.style = new StyleState(this);
            this.styles = new StyleState[8];
            this.head = -1;
        }

        @Override
        public void pushStyle(@NotNull Style style) {
            StyleState styleState;
            int n;
            if ((n = ++this.head) >= this.styles.length) {
                this.styles = Arrays.copyOf(this.styles, this.styles.length * 2);
            }
            if ((styleState = this.styles[n]) == null) {
                this.styles[n] = styleState = new StyleState(this);
            }
            if (n > 0) {
                styleState.set(this.styles[n - 1]);
            } else {
                styleState.clear();
            }
            styleState.apply(style);
        }

        @Override
        public void component(@NotNull String string) {
            if (!string.isEmpty()) {
                if (this.head < 0) {
                    throw new IllegalStateException("No style has been pushed!");
                }
                this.styles[this.head].applyFormat();
                this.sb.append(string);
            }
        }

        @Override
        public void popStyle(@NotNull Style style) {
            if (this.head-- < 0) {
                throw new IllegalStateException("Tried to pop beyond what was pushed!");
            }
        }

        void append(@NotNull TextFormat textFormat) {
            if (this.lastWritten != textFormat) {
                this.sb.append(LegacyComponentSerializerImpl.access$400(this.this$0)).append(LegacyComponentSerializerImpl.access$300(this.this$0, textFormat));
            }
            this.lastWritten = textFormat;
        }

        public String toString() {
            return this.sb.toString();
        }

        Cereal(LegacyComponentSerializerImpl legacyComponentSerializerImpl, 1 var2_2) {
            this(legacyComponentSerializerImpl);
        }

        static StyleState access$600(Cereal cereal) {
            return cereal.style;
        }

        static TextFormat access$700(Cereal cereal) {
            return cereal.lastWritten;
        }

        private final class StyleState {
            @Nullable
            private TextColor color;
            private final Set<TextDecoration> decorations;
            private boolean needsReset;
            final Cereal this$1;

            StyleState(Cereal cereal) {
                this.this$1 = cereal;
                this.decorations = EnumSet.noneOf(TextDecoration.class);
            }

            void set(@NotNull StyleState styleState) {
                this.color = styleState.color;
                this.decorations.clear();
                this.decorations.addAll(styleState.decorations);
            }

            public void clear() {
                this.color = null;
                this.decorations.clear();
            }

            void apply(@NotNull Style style) {
                TextColor textColor = style.color();
                if (textColor != null) {
                    this.color = textColor;
                }
                int n = LegacyComponentSerializerImpl.access$500().length;
                block4: for (int i = 0; i < n; ++i) {
                    TextDecoration textDecoration = LegacyComponentSerializerImpl.access$500()[i];
                    switch (1.$SwitchMap$net$kyori$adventure$text$format$TextDecoration$State[style.decoration(textDecoration).ordinal()]) {
                        case 1: {
                            this.decorations.add(textDecoration);
                            continue block4;
                        }
                        case 2: {
                            if (!this.decorations.remove(textDecoration)) continue block4;
                            this.needsReset = true;
                            continue block4;
                        }
                    }
                }
            }

            void applyFormat() {
                boolean bl;
                boolean bl2 = bl = this.color != Cereal.access$600((Cereal)this.this$1).color;
                if (this.needsReset) {
                    if (!bl) {
                        this.this$1.append(Reset.INSTANCE);
                    }
                    this.needsReset = false;
                }
                if (bl || Cereal.access$700(this.this$1) == Reset.INSTANCE) {
                    this.applyFullFormat();
                    return;
                }
                if (!this.decorations.containsAll(Cereal.access$600((Cereal)this.this$1).decorations)) {
                    this.applyFullFormat();
                    return;
                }
                for (TextDecoration textDecoration : this.decorations) {
                    if (!Cereal.access$600((Cereal)this.this$1).decorations.add(textDecoration)) continue;
                    this.this$1.append(textDecoration);
                }
            }

            private void applyFullFormat() {
                if (this.color != null) {
                    this.this$1.append(this.color);
                } else {
                    this.this$1.append(Reset.INSTANCE);
                }
                Cereal.access$600((Cereal)this.this$1).color = this.color;
                for (TextDecoration textDecoration : this.decorations) {
                    this.this$1.append(textDecoration);
                }
                Cereal.access$600((Cereal)this.this$1).decorations.clear();
                Cereal.access$600((Cereal)this.this$1).decorations.addAll(this.decorations);
            }
        }
    }

    private static enum Reset implements TextFormat
    {
        INSTANCE;

    }

    static final class Instances {
        static final LegacyComponentSerializer SECTION = LegacyComponentSerializerImpl.access$000().map(LegacyComponentSerializer.Provider::legacySection).orElseGet(Instances::lambda$static$0);
        static final LegacyComponentSerializer AMPERSAND = LegacyComponentSerializerImpl.access$000().map(LegacyComponentSerializer.Provider::legacyAmpersand).orElseGet(Instances::lambda$static$1);

        Instances() {
        }

        private static LegacyComponentSerializer lambda$static$1() {
            return new LegacyComponentSerializerImpl('&', '#', null, false, false, ComponentFlattener.basic());
        }

        private static LegacyComponentSerializer lambda$static$0() {
            return new LegacyComponentSerializerImpl('\u00a7', '#', null, false, false, ComponentFlattener.basic());
        }
    }
}

