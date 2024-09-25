/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.remapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Pair;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.TypeRemapper;
import us.myles.ViaVersion.api.remapper.ValueCreator;
import us.myles.ViaVersion.api.remapper.ValueReader;
import us.myles.ViaVersion.api.remapper.ValueTransformer;
import us.myles.ViaVersion.api.remapper.ValueWriter;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.exception.CancelException;
import us.myles.ViaVersion.exception.InformativeException;

public abstract class PacketRemapper {
    private final List<Pair<ValueReader, ValueWriter>> valueRemappers = new ArrayList<Pair<ValueReader, ValueWriter>>();

    public PacketRemapper() {
        this.registerMap();
    }

    public void map(Type type) {
        TypeRemapper remapper = new TypeRemapper(type);
        this.map(remapper, remapper);
    }

    public void map(Type oldType, Type newType) {
        this.map(new TypeRemapper(oldType), new TypeRemapper(newType));
    }

    public <T1, T2> void map(Type<T1> oldType, Type<T2> newType, final Function<T1, T2> transformer) {
        this.map(new TypeRemapper<T1>(oldType), new ValueTransformer<T1, T2>(newType){

            @Override
            public T2 transform(PacketWrapper wrapper, T1 inputValue) throws Exception {
                return transformer.apply(inputValue);
            }
        });
    }

    public <T1, T2> void map(ValueTransformer<T1, T2> transformer) {
        if (transformer.getInputType() == null) {
            throw new IllegalArgumentException("Use map(Type<T1>, ValueTransformer<T1, T2>) for value transformers without specified input type!");
        }
        this.map(transformer.getInputType(), transformer);
    }

    public <T1, T2> void map(Type<T1> oldType, ValueTransformer<T1, T2> transformer) {
        this.map(new TypeRemapper<T1>(oldType), transformer);
    }

    public <T> void map(ValueReader<T> inputReader, ValueWriter<T> outputWriter) {
        this.valueRemappers.add(new Pair<ValueReader<T>, ValueWriter<T>>(inputReader, outputWriter));
    }

    public void create(ValueCreator creator) {
        this.map(new TypeRemapper<Void>(Type.NOTHING), creator);
    }

    public void handler(PacketHandler handler) {
        this.map(new TypeRemapper<Void>(Type.NOTHING), handler);
    }

    public abstract void registerMap();

    public void remap(PacketWrapper packetWrapper) throws Exception {
        try {
            for (Pair<ValueReader, ValueWriter> valueRemapper : this.valueRemappers) {
                Object object = valueRemapper.getKey().read(packetWrapper);
                valueRemapper.getValue().write(packetWrapper, object);
            }
        }
        catch (InformativeException e) {
            e.addSource(this.getClass());
            throw e;
        }
        catch (CancelException e) {
            throw e;
        }
        catch (Exception e) {
            InformativeException ex = new InformativeException(e);
            ex.addSource(this.getClass());
            throw ex;
        }
    }
}

