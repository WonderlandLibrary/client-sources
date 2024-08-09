/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.TypeRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueReader;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.protocol.remapper.ValueWriter;
import com.viaversion.viaversion.api.type.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class PacketHandlers
implements PacketHandler {
    private final List<PacketHandler> packetHandlers = new ArrayList<PacketHandler>();

    protected PacketHandlers() {
        this.register();
    }

    static PacketHandler fromRemapper(List<PacketHandler> list) {
        PacketHandlers packetHandlers = new PacketHandlers(){

            @Override
            public void register() {
            }
        };
        packetHandlers.packetHandlers.addAll(list);
        return packetHandlers;
    }

    public <T> void map(Type<T> type) {
        this.handler(arg_0 -> PacketHandlers.lambda$map$0(type, arg_0));
    }

    public void map(Type type, Type type2) {
        this.handler(arg_0 -> PacketHandlers.lambda$map$1(type2, type, arg_0));
    }

    public <T1, T2> void map(Type<T1> type, Type<T2> type2, Function<T1, T2> function) {
        this.map(type, new ValueTransformer<T1, T2>(this, type2, function){
            final Function val$transformer;
            final PacketHandlers this$0;
            {
                this.this$0 = packetHandlers;
                this.val$transformer = function;
                super(type);
            }

            @Override
            public T2 transform(PacketWrapper packetWrapper, T1 T1) {
                return this.val$transformer.apply(T1);
            }
        });
    }

    public <T1, T2> void map(ValueTransformer<T1, T2> valueTransformer) {
        if (valueTransformer.getInputType() == null) {
            throw new IllegalArgumentException("Use map(Type<T1>, ValueTransformer<T1, T2>) for value transformers without specified input type!");
        }
        this.map(valueTransformer.getInputType(), valueTransformer);
    }

    public <T1, T2> void map(Type<T1> type, ValueTransformer<T1, T2> valueTransformer) {
        this.map(new TypeRemapper<T1>(type), valueTransformer);
    }

    public <T> void map(ValueReader<T> valueReader, ValueWriter<T> valueWriter) {
        this.handler(arg_0 -> PacketHandlers.lambda$map$2(valueWriter, valueReader, arg_0));
    }

    public void handler(PacketHandler packetHandler) {
        this.packetHandlers.add(packetHandler);
    }

    public <T> void create(Type<T> type, T t) {
        this.handler(arg_0 -> PacketHandlers.lambda$create$3(type, t, arg_0));
    }

    public void read(Type<?> type) {
        this.handler(arg_0 -> PacketHandlers.lambda$read$4(type, arg_0));
    }

    protected abstract void register();

    @Override
    public final void handle(PacketWrapper packetWrapper) throws Exception {
        for (PacketHandler packetHandler : this.packetHandlers) {
            packetHandler.handle(packetWrapper);
        }
    }

    public int handlersSize() {
        return this.packetHandlers.size();
    }

    private static void lambda$read$4(Type type, PacketWrapper packetWrapper) throws Exception {
        packetWrapper.read(type);
    }

    private static void lambda$create$3(Type type, Object object, PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(type, object);
    }

    private static void lambda$map$2(ValueWriter valueWriter, ValueReader valueReader, PacketWrapper packetWrapper) throws Exception {
        valueWriter.write(packetWrapper, valueReader.read(packetWrapper));
    }

    private static void lambda$map$1(Type type, Type type2, PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(type, packetWrapper.read(type2));
    }

    private static void lambda$map$0(Type type, PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(type, packetWrapper.read(type));
    }
}

