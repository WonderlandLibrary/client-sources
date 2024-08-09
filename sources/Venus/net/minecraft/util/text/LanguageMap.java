/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import net.minecraft.util.ICharacterConsumer;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextProcessing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class LanguageMap {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson field_240591_b_ = new Gson();
    private static final Pattern NUMERIC_VARIABLE_PATTERN = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
    private static volatile LanguageMap field_240592_d_ = LanguageMap.func_240595_c_();

    private static LanguageMap func_240595_c_() {
        Object object;
        ImmutableMap.Builder builder = ImmutableMap.builder();
        BiConsumer<String, String> biConsumer = builder::put;
        try {
            object = LanguageMap.class.getResourceAsStream("/assets/minecraft/lang/en_us.json");
            try {
                LanguageMap.func_240593_a_((InputStream)object, biConsumer);
            } finally {
                if (object != null) {
                    ((InputStream)object).close();
                }
            }
        } catch (JsonParseException | IOException exception) {
            LOGGER.error("Couldn't read strings from /assets/minecraft/lang/en_us.json", (Throwable)exception);
        }
        object = builder.build();
        return new LanguageMap((Map)object){
            final Map val$map;
            {
                this.val$map = map;
            }

            @Override
            public String func_230503_a_(String string) {
                return this.val$map.getOrDefault(string, string);
            }

            @Override
            public boolean func_230506_b_(String string) {
                return this.val$map.containsKey(string);
            }

            @Override
            public boolean func_230505_b_() {
                return true;
            }

            @Override
            public IReorderingProcessor func_241870_a(ITextProperties iTextProperties) {
                return arg_0 -> 1.lambda$func_241870_a$1(iTextProperties, arg_0);
            }

            private static boolean lambda$func_241870_a$1(ITextProperties iTextProperties, ICharacterConsumer iCharacterConsumer) {
                return iTextProperties.getComponentWithStyle((arg_0, arg_1) -> 1.lambda$func_241870_a$0(iCharacterConsumer, arg_0, arg_1), Style.EMPTY).isPresent();
            }

            private static Optional lambda$func_241870_a$0(ICharacterConsumer iCharacterConsumer, Style style, String string) {
                return TextProcessing.func_238346_c_(string, style, iCharacterConsumer) ? Optional.empty() : ITextProperties.field_240650_b_;
            }
        };
    }

    public static void func_240593_a_(InputStream inputStream, BiConsumer<String, String> biConsumer) {
        JsonObject jsonObject = field_240591_b_.fromJson((Reader)new InputStreamReader(inputStream, StandardCharsets.UTF_8), JsonObject.class);
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String string = NUMERIC_VARIABLE_PATTERN.matcher(JSONUtils.getString(entry.getValue(), entry.getKey())).replaceAll("%$1s");
            biConsumer.accept(entry.getKey(), string);
        }
    }

    public static LanguageMap getInstance() {
        return field_240592_d_;
    }

    public static void func_240594_a_(LanguageMap languageMap) {
        field_240592_d_ = languageMap;
    }

    public abstract String func_230503_a_(String var1);

    public abstract boolean func_230506_b_(String var1);

    public abstract boolean func_230505_b_();

    public abstract IReorderingProcessor func_241870_a(ITextProperties var1);

    public List<IReorderingProcessor> func_244260_a(List<ITextProperties> list) {
        return list.stream().map(LanguageMap.getInstance()::func_241870_a).collect(ImmutableList.toImmutableList());
    }
}

