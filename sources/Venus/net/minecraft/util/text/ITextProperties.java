/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.Unit;
import net.minecraft.util.text.Style;

public interface ITextProperties {
    public static final Optional<Unit> field_240650_b_ = Optional.of(Unit.INSTANCE);
    public static final ITextProperties field_240651_c_ = new ITextProperties(){

        @Override
        public <T> Optional<T> getComponent(ITextAcceptor<T> iTextAcceptor) {
            return Optional.empty();
        }

        @Override
        public <T> Optional<T> getComponentWithStyle(IStyledTextAcceptor<T> iStyledTextAcceptor, Style style) {
            return Optional.empty();
        }
    };

    public <T> Optional<T> getComponent(ITextAcceptor<T> var1);

    public <T> Optional<T> getComponentWithStyle(IStyledTextAcceptor<T> var1, Style var2);

    public static ITextProperties func_240652_a_(String string) {
        return new ITextProperties(string){
            final String val$p_240652_0_;
            {
                this.val$p_240652_0_ = string;
            }

            @Override
            public <T> Optional<T> getComponent(ITextAcceptor<T> iTextAcceptor) {
                return iTextAcceptor.accept(this.val$p_240652_0_);
            }

            @Override
            public <T> Optional<T> getComponentWithStyle(IStyledTextAcceptor<T> iStyledTextAcceptor, Style style) {
                return iStyledTextAcceptor.accept(style, this.val$p_240652_0_);
            }
        };
    }

    public static ITextProperties func_240653_a_(String string, Style style) {
        return new ITextProperties(){
            final String val$p_240653_0_;
            final Style val$p_240653_1_;
            {
                this.val$p_240653_0_ = string;
                this.val$p_240653_1_ = style;
            }

            @Override
            public <T> Optional<T> getComponent(ITextAcceptor<T> iTextAcceptor) {
                return iTextAcceptor.accept(this.val$p_240653_0_);
            }

            @Override
            public <T> Optional<T> getComponentWithStyle(IStyledTextAcceptor<T> iStyledTextAcceptor, Style style) {
                return iStyledTextAcceptor.accept(this.val$p_240653_1_.mergeStyle(style), this.val$p_240653_0_);
            }
        };
    }

    public static ITextProperties func_240655_a_(ITextProperties ... iTextPropertiesArray) {
        return ITextProperties.func_240654_a_(ImmutableList.copyOf(iTextPropertiesArray));
    }

    public static ITextProperties func_240654_a_(List<ITextProperties> list) {
        return new ITextProperties(list){
            final List val$p_240654_0_;
            {
                this.val$p_240654_0_ = list;
            }

            @Override
            public <T> Optional<T> getComponent(ITextAcceptor<T> iTextAcceptor) {
                for (ITextProperties iTextProperties : this.val$p_240654_0_) {
                    Optional<T> optional = iTextProperties.getComponent(iTextAcceptor);
                    if (!optional.isPresent()) continue;
                    return optional;
                }
                return Optional.empty();
            }

            @Override
            public <T> Optional<T> getComponentWithStyle(IStyledTextAcceptor<T> iStyledTextAcceptor, Style style) {
                for (ITextProperties iTextProperties : this.val$p_240654_0_) {
                    Optional<T> optional = iTextProperties.getComponentWithStyle(iStyledTextAcceptor, style);
                    if (!optional.isPresent()) continue;
                    return optional;
                }
                return Optional.empty();
            }
        };
    }

    default public String getString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.getComponent(arg_0 -> ITextProperties.lambda$getString$0(stringBuilder, arg_0));
        return stringBuilder.toString();
    }

    private static Optional lambda$getString$0(StringBuilder stringBuilder, String string) {
        stringBuilder.append(string);
        return Optional.empty();
    }

    public static interface ITextAcceptor<T> {
        public Optional<T> accept(String var1);
    }

    public static interface IStyledTextAcceptor<T> {
        public Optional<T> accept(Style var1, String var2);
    }
}

