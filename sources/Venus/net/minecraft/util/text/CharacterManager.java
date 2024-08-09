/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.ICharacterConsumer;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextProcessing;
import net.minecraft.util.text.TextPropertiesManager;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;

public class CharacterManager {
    private final ICharWidthProvider field_238347_a_;

    public CharacterManager(ICharWidthProvider iCharWidthProvider) {
        this.field_238347_a_ = iCharWidthProvider;
    }

    public float func_238350_a_(@Nullable String string) {
        if (string == null) {
            return 0.0f;
        }
        MutableFloat mutableFloat = new MutableFloat();
        TextProcessing.func_238346_c_(string, Style.EMPTY, (arg_0, arg_1, arg_2) -> this.lambda$func_238350_a_$0(mutableFloat, arg_0, arg_1, arg_2));
        return mutableFloat.floatValue();
    }

    public float func_238356_a_(ITextProperties iTextProperties) {
        MutableFloat mutableFloat = new MutableFloat();
        TextProcessing.func_238343_a_(iTextProperties, Style.EMPTY, (arg_0, arg_1, arg_2) -> this.lambda$func_238356_a_$1(mutableFloat, arg_0, arg_1, arg_2));
        return mutableFloat.floatValue();
    }

    public float func_243238_a(IReorderingProcessor iReorderingProcessor) {
        MutableFloat mutableFloat = new MutableFloat();
        iReorderingProcessor.accept((arg_0, arg_1, arg_2) -> this.lambda$func_243238_a$2(mutableFloat, arg_0, arg_1, arg_2));
        return mutableFloat.floatValue();
    }

    public int func_238352_a_(String string, int n, Style style) {
        StringWidthProcessor stringWidthProcessor = new StringWidthProcessor(this, n);
        TextProcessing.func_238341_a_(string, style, stringWidthProcessor);
        return stringWidthProcessor.func_238398_a_();
    }

    public String func_238361_b_(String string, int n, Style style) {
        return string.substring(0, this.func_238352_a_(string, n, style));
    }

    public String func_238364_c_(String string, int n, Style style) {
        MutableFloat mutableFloat = new MutableFloat();
        MutableInt mutableInt = new MutableInt(string.length());
        TextProcessing.func_238345_b_(string, style, (arg_0, arg_1, arg_2) -> this.lambda$func_238364_c_$3(mutableFloat, n, mutableInt, arg_0, arg_1, arg_2));
        return string.substring(mutableInt.intValue());
    }

    @Nullable
    public Style func_238357_a_(ITextProperties iTextProperties, int n) {
        StringWidthProcessor stringWidthProcessor = new StringWidthProcessor(this, n);
        return iTextProperties.getComponentWithStyle((arg_0, arg_1) -> CharacterManager.lambda$func_238357_a_$4(stringWidthProcessor, arg_0, arg_1), Style.EMPTY).orElse(null);
    }

    @Nullable
    public Style func_243239_a(IReorderingProcessor iReorderingProcessor, int n) {
        StringWidthProcessor stringWidthProcessor = new StringWidthProcessor(this, n);
        MutableObject mutableObject = new MutableObject();
        iReorderingProcessor.accept((arg_0, arg_1, arg_2) -> CharacterManager.lambda$func_243239_a$5(stringWidthProcessor, mutableObject, arg_0, arg_1, arg_2));
        return (Style)mutableObject.getValue();
    }

    public ITextProperties func_238358_a_(ITextProperties iTextProperties, int n, Style style) {
        StringWidthProcessor stringWidthProcessor = new StringWidthProcessor(this, n);
        return iTextProperties.getComponentWithStyle(new ITextProperties.IStyledTextAcceptor<ITextProperties>(){
            private final TextPropertiesManager field_238368_c_;
            final StringWidthProcessor val$charactermanager$stringwidthprocessor;
            final CharacterManager this$0;
            {
                this.this$0 = characterManager;
                this.val$charactermanager$stringwidthprocessor = stringWidthProcessor;
                this.field_238368_c_ = new TextPropertiesManager();
            }

            @Override
            public Optional<ITextProperties> accept(Style style, String string) {
                this.val$charactermanager$stringwidthprocessor.func_238399_b_();
                if (!TextProcessing.func_238346_c_(string, style, this.val$charactermanager$stringwidthprocessor)) {
                    String string2 = string.substring(0, this.val$charactermanager$stringwidthprocessor.func_238398_a_());
                    if (!string2.isEmpty()) {
                        this.field_238368_c_.func_238155_a_(ITextProperties.func_240653_a_(string2, style));
                    }
                    return Optional.of(this.field_238368_c_.func_238156_b_());
                }
                if (!string.isEmpty()) {
                    this.field_238368_c_.func_238155_a_(ITextProperties.func_240653_a_(string, style));
                }
                return Optional.empty();
            }
        }, style).orElse(iTextProperties);
    }

    public static int func_238351_a_(String string, int n, int n2, boolean bl) {
        int n3 = n2;
        boolean bl2 = n < 0;
        int n4 = Math.abs(n);
        for (int i = 0; i < n4; ++i) {
            if (bl2) {
                while (bl && n3 > 0 && (string.charAt(n3 - 1) == ' ' || string.charAt(n3 - 1) == '\n')) {
                    --n3;
                }
                while (n3 > 0 && string.charAt(n3 - 1) != ' ' && string.charAt(n3 - 1) != '\n') {
                    --n3;
                }
                continue;
            }
            int n5 = string.length();
            int n6 = string.indexOf(32, n3);
            int n7 = string.indexOf(10, n3);
            n3 = n6 == -1 && n7 == -1 ? -1 : (n6 != -1 && n7 != -1 ? Math.min(n6, n7) : (n6 != -1 ? n6 : n7));
            if (n3 == -1) {
                n3 = n5;
                continue;
            }
            while (bl && n3 < n5 && (string.charAt(n3) == ' ' || string.charAt(n3) == '\n')) {
                ++n3;
            }
        }
        return n3;
    }

    public void func_238353_a_(String string, int n, Style style, boolean bl, ISliceAcceptor iSliceAcceptor) {
        int n2 = 0;
        int n3 = string.length();
        Style style2 = style;
        while (n2 < n3) {
            MultilineProcessor multilineProcessor = new MultilineProcessor(this, n);
            boolean bl2 = TextProcessing.func_238340_a_(string, n2, style2, style, multilineProcessor);
            if (bl2) {
                iSliceAcceptor.accept(style2, n2, n3);
                break;
            }
            int n4 = multilineProcessor.func_238386_a_();
            char c = string.charAt(n4);
            int n5 = c != '\n' && c != ' ' ? n4 : n4 + 1;
            iSliceAcceptor.accept(style2, n2, bl ? n5 : n4);
            n2 = n5;
            style2 = multilineProcessor.func_238389_b_();
        }
    }

    public List<ITextProperties> func_238365_g_(String string, int n, Style style) {
        ArrayList<ITextProperties> arrayList = Lists.newArrayList();
        this.func_238353_a_(string, n, style, false, (arg_0, arg_1, arg_2) -> CharacterManager.lambda$func_238365_g_$6(arrayList, string, arg_0, arg_1, arg_2));
        return arrayList;
    }

    public List<ITextProperties> func_238362_b_(ITextProperties iTextProperties, int n, Style style) {
        ArrayList<ITextProperties> arrayList = Lists.newArrayList();
        this.func_243242_a(iTextProperties, n, style, (arg_0, arg_1) -> CharacterManager.lambda$func_238362_b_$7(arrayList, arg_0, arg_1));
        return arrayList;
    }

    public void func_243242_a(ITextProperties iTextProperties, int n, Style style, BiConsumer<ITextProperties, Boolean> biConsumer) {
        Object object;
        ArrayList<StyleOverridingTextComponent> arrayList = Lists.newArrayList();
        iTextProperties.getComponentWithStyle((arg_0, arg_1) -> CharacterManager.lambda$func_243242_a$8(arrayList, arg_0, arg_1), style);
        SubstyledText substyledText = new SubstyledText(arrayList);
        boolean bl = true;
        boolean bl2 = false;
        boolean bl3 = false;
        block0: while (bl) {
            bl = false;
            object = new MultilineProcessor(this, n);
            for (StyleOverridingTextComponent styleOverridingTextComponent : substyledText.field_238369_a_) {
                boolean bl4 = TextProcessing.func_238340_a_(styleOverridingTextComponent.field_238391_a_, 0, styleOverridingTextComponent.field_238392_d_, style, (ICharacterConsumer)object);
                if (!bl4) {
                    int n2 = ((MultilineProcessor)object).func_238386_a_();
                    Style style2 = ((MultilineProcessor)object).func_238389_b_();
                    char c = substyledText.func_238372_a_(n2);
                    boolean bl5 = c == '\n';
                    boolean bl6 = bl5 || c == ' ';
                    bl2 = bl5;
                    ITextProperties iTextProperties2 = substyledText.func_238373_a_(n2, bl6 ? 1 : 0, style2);
                    biConsumer.accept(iTextProperties2, bl3);
                    bl3 = !bl5;
                    bl = true;
                    continue block0;
                }
                ((MultilineProcessor)object).func_238387_a_(styleOverridingTextComponent.field_238391_a_.length());
            }
        }
        object = substyledText.func_238371_a_();
        if (object != null) {
            biConsumer.accept((ITextProperties)object, bl3);
        } else if (bl2) {
            biConsumer.accept(ITextProperties.field_240651_c_, false);
        }
    }

    private static Optional lambda$func_243242_a$8(List list, Style style, String string) {
        if (!string.isEmpty()) {
            list.add(new StyleOverridingTextComponent(string, style));
        }
        return Optional.empty();
    }

    private static void lambda$func_238362_b_$7(List list, ITextProperties iTextProperties, Boolean bl) {
        list.add(iTextProperties);
    }

    private static void lambda$func_238365_g_$6(List list, String string, Style style, int n, int n2) {
        list.add(ITextProperties.func_240653_a_(string.substring(n, n2), style));
    }

    private static boolean lambda$func_243239_a$5(StringWidthProcessor stringWidthProcessor, MutableObject mutableObject, int n, Style style, int n2) {
        if (!stringWidthProcessor.accept(n, style, n2)) {
            mutableObject.setValue(style);
            return true;
        }
        return false;
    }

    private static Optional lambda$func_238357_a_$4(StringWidthProcessor stringWidthProcessor, Style style, String string) {
        return TextProcessing.func_238346_c_(string, style, stringWidthProcessor) ? Optional.empty() : Optional.of(style);
    }

    private boolean lambda$func_238364_c_$3(MutableFloat mutableFloat, int n, MutableInt mutableInt, int n2, Style style, int n3) {
        float f = mutableFloat.addAndGet(this.field_238347_a_.getWidth(n3, style));
        if (f > (float)n) {
            return true;
        }
        mutableInt.setValue(n2);
        return false;
    }

    private boolean lambda$func_243238_a$2(MutableFloat mutableFloat, int n, Style style, int n2) {
        mutableFloat.add(this.field_238347_a_.getWidth(n2, style));
        return false;
    }

    private boolean lambda$func_238356_a_$1(MutableFloat mutableFloat, int n, Style style, int n2) {
        mutableFloat.add(this.field_238347_a_.getWidth(n2, style));
        return false;
    }

    private boolean lambda$func_238350_a_$0(MutableFloat mutableFloat, int n, Style style, int n2) {
        mutableFloat.add(this.field_238347_a_.getWidth(n2, style));
        return false;
    }

    @FunctionalInterface
    public static interface ICharWidthProvider {
        public float getWidth(int var1, Style var2);
    }

    class StringWidthProcessor
    implements ICharacterConsumer {
        private float field_238396_b_;
        private int field_238397_c_;
        final CharacterManager this$0;

        public StringWidthProcessor(CharacterManager characterManager, float f) {
            this.this$0 = characterManager;
            this.field_238396_b_ = f;
        }

        @Override
        public boolean accept(int n, Style style, int n2) {
            this.field_238396_b_ -= this.this$0.field_238347_a_.getWidth(n2, style);
            if (this.field_238396_b_ >= 0.0f) {
                this.field_238397_c_ = n + Character.charCount(n2);
                return false;
            }
            return true;
        }

        public int func_238398_a_() {
            return this.field_238397_c_;
        }

        public void func_238399_b_() {
            this.field_238397_c_ = 0;
        }
    }

    class MultilineProcessor
    implements ICharacterConsumer {
        private final float field_238377_b_;
        private int field_238378_c_;
        private Style field_238379_d_;
        private boolean field_238380_e_;
        private float field_238381_f_;
        private int field_238382_g_;
        private Style field_238383_h_;
        private int field_238384_i_;
        private int field_238385_j_;
        final CharacterManager this$0;

        public MultilineProcessor(CharacterManager characterManager, float f) {
            this.this$0 = characterManager;
            this.field_238378_c_ = -1;
            this.field_238379_d_ = Style.EMPTY;
            this.field_238382_g_ = -1;
            this.field_238383_h_ = Style.EMPTY;
            this.field_238377_b_ = Math.max(f, 1.0f);
        }

        @Override
        public boolean accept(int n, Style style, int n2) {
            int n3 = n + this.field_238385_j_;
            switch (n2) {
                case 10: {
                    return this.func_238388_a_(n3, style);
                }
                case 32: {
                    this.field_238382_g_ = n3;
                    this.field_238383_h_ = style;
                }
            }
            float f = this.this$0.field_238347_a_.getWidth(n2, style);
            this.field_238381_f_ += f;
            if (this.field_238380_e_ && this.field_238381_f_ > this.field_238377_b_) {
                return this.field_238382_g_ != -1 ? this.func_238388_a_(this.field_238382_g_, this.field_238383_h_) : this.func_238388_a_(n3, style);
            }
            this.field_238380_e_ |= f != 0.0f;
            this.field_238384_i_ = n3 + Character.charCount(n2);
            return false;
        }

        private boolean func_238388_a_(int n, Style style) {
            this.field_238378_c_ = n;
            this.field_238379_d_ = style;
            return true;
        }

        private boolean func_238390_c_() {
            return this.field_238378_c_ != -1;
        }

        public int func_238386_a_() {
            return this.func_238390_c_() ? this.field_238378_c_ : this.field_238384_i_;
        }

        public Style func_238389_b_() {
            return this.field_238379_d_;
        }

        public void func_238387_a_(int n) {
            this.field_238385_j_ += n;
        }
    }

    @FunctionalInterface
    public static interface ISliceAcceptor {
        public void accept(Style var1, int var2, int var3);
    }

    static class SubstyledText {
        private final List<StyleOverridingTextComponent> field_238369_a_;
        private String field_238370_b_;

        public SubstyledText(List<StyleOverridingTextComponent> list) {
            this.field_238369_a_ = list;
            this.field_238370_b_ = list.stream().map(SubstyledText::lambda$new$0).collect(Collectors.joining());
        }

        public char func_238372_a_(int n) {
            return this.field_238370_b_.charAt(n);
        }

        public ITextProperties func_238373_a_(int n, int n2, Style style) {
            TextPropertiesManager textPropertiesManager = new TextPropertiesManager();
            ListIterator<StyleOverridingTextComponent> listIterator2 = this.field_238369_a_.listIterator();
            int n3 = n;
            boolean bl = false;
            while (listIterator2.hasNext()) {
                String string;
                StyleOverridingTextComponent styleOverridingTextComponent = listIterator2.next();
                String string2 = styleOverridingTextComponent.field_238391_a_;
                int n4 = string2.length();
                if (!bl) {
                    if (n3 > n4) {
                        textPropertiesManager.func_238155_a_(styleOverridingTextComponent);
                        listIterator2.remove();
                        n3 -= n4;
                    } else {
                        string = string2.substring(0, n3);
                        if (!string.isEmpty()) {
                            textPropertiesManager.func_238155_a_(ITextProperties.func_240653_a_(string, styleOverridingTextComponent.field_238392_d_));
                        }
                        n3 += n2;
                        bl = true;
                    }
                }
                if (!bl) continue;
                if (n3 <= n4) {
                    string = string2.substring(n3);
                    if (string.isEmpty()) {
                        listIterator2.remove();
                        break;
                    }
                    listIterator2.set(new StyleOverridingTextComponent(string, style));
                    break;
                }
                listIterator2.remove();
                n3 -= n4;
            }
            this.field_238370_b_ = this.field_238370_b_.substring(n + n2);
            return textPropertiesManager.func_238156_b_();
        }

        @Nullable
        public ITextProperties func_238371_a_() {
            TextPropertiesManager textPropertiesManager = new TextPropertiesManager();
            this.field_238369_a_.forEach(textPropertiesManager::func_238155_a_);
            this.field_238369_a_.clear();
            return textPropertiesManager.func_238154_a_();
        }

        private static String lambda$new$0(StyleOverridingTextComponent styleOverridingTextComponent) {
            return styleOverridingTextComponent.field_238391_a_;
        }
    }

    static class StyleOverridingTextComponent
    implements ITextProperties {
        private final String field_238391_a_;
        private final Style field_238392_d_;

        public StyleOverridingTextComponent(String string, Style style) {
            this.field_238391_a_ = string;
            this.field_238392_d_ = style;
        }

        @Override
        public <T> Optional<T> getComponent(ITextProperties.ITextAcceptor<T> iTextAcceptor) {
            return iTextAcceptor.accept(this.field_238391_a_);
        }

        @Override
        public <T> Optional<T> getComponentWithStyle(ITextProperties.IStyledTextAcceptor<T> iStyledTextAcceptor, Style style) {
            return iStyledTextAcceptor.accept(this.field_238392_d_.mergeStyle(style), this.field_238391_a_);
        }
    }
}

