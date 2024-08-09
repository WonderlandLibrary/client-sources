/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SChangeGameStatePacket
implements IPacket<IClientPlayNetHandler> {
    public static final State field_241764_a_ = new State(0);
    public static final State field_241765_b_ = new State(1);
    public static final State field_241766_c_ = new State(2);
    public static final State field_241767_d_ = new State(3);
    public static final State field_241768_e_ = new State(4);
    public static final State field_241769_f_ = new State(5);
    public static final State field_241770_g_ = new State(6);
    public static final State field_241771_h_ = new State(7);
    public static final State field_241772_i_ = new State(8);
    public static final State field_241773_j_ = new State(9);
    public static final State field_241774_k_ = new State(10);
    public static final State field_241775_l_ = new State(11);
    private State state;
    private float value;

    public SChangeGameStatePacket() {
    }

    public SChangeGameStatePacket(State state, float f) {
        this.state = state;
        this.value = f;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.state = (State)State.field_241777_a_.get(packetBuffer.readUnsignedByte());
        this.value = packetBuffer.readFloat();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.state.field_241778_b_);
        packetBuffer.writeFloat(this.value);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleChangeGameState(this);
    }

    public State func_241776_b_() {
        return this.state;
    }

    public float getValue() {
        return this.value;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }

    public static class State {
        private static final Int2ObjectMap<State> field_241777_a_ = new Int2ObjectOpenHashMap<State>();
        private final int field_241778_b_;

        public State(int n) {
            this.field_241778_b_ = n;
            field_241777_a_.put(n, this);
        }
    }
}

