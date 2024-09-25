/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package us.myles.ViaVersion.api.remapper;

import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.remapper.ValueWriter;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.exception.InformativeException;

public abstract class ValueTransformer<T1, T2>
implements ValueWriter<T1> {
    private final Type<T1> inputType;
    private final Type<T2> outputType;

    public ValueTransformer(@Nullable Type<T1> inputType, Type<T2> outputType) {
        this.inputType = inputType;
        this.outputType = outputType;
    }

    public ValueTransformer(Type<T2> outputType) {
        this(null, outputType);
    }

    public abstract T2 transform(PacketWrapper var1, T1 var2) throws Exception;

    @Override
    public void write(PacketWrapper writer, T1 inputValue) throws Exception {
        try {
            writer.write(this.outputType, this.transform(writer, inputValue));
        }
        catch (InformativeException e) {
            e.addSource(this.getClass());
            throw e;
        }
    }

    @Nullable
    public Type<T1> getInputType() {
        return this.inputType;
    }

    public Type<T2> getOutputType() {
        return this.outputType;
    }
}

