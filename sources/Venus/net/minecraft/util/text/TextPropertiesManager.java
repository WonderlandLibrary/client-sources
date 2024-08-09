/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.text.ITextProperties;

public class TextPropertiesManager {
    private final List<ITextProperties> field_238153_a_ = Lists.newArrayList();

    public void func_238155_a_(ITextProperties iTextProperties) {
        this.field_238153_a_.add(iTextProperties);
    }

    @Nullable
    public ITextProperties func_238154_a_() {
        if (this.field_238153_a_.isEmpty()) {
            return null;
        }
        return this.field_238153_a_.size() == 1 ? this.field_238153_a_.get(0) : ITextProperties.func_240654_a_(this.field_238153_a_);
    }

    public ITextProperties func_238156_b_() {
        ITextProperties iTextProperties = this.func_238154_a_();
        return iTextProperties != null ? iTextProperties : ITextProperties.field_240651_c_;
    }
}

