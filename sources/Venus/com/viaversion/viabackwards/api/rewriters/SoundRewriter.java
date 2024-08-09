/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

public class SoundRewriter<C extends ClientboundPacketType>
extends com.viaversion.viaversion.rewriter.SoundRewriter<C> {
    private final BackwardsProtocol<C, ?, ?, ?> protocol;

    public SoundRewriter(BackwardsProtocol<C, ?, ?, ?> backwardsProtocol) {
        super(backwardsProtocol);
        this.protocol = backwardsProtocol;
    }

    public void registerNamedSound(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final SoundRewriter this$0;
            {
                this.this$0 = soundRewriter;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(this.this$0.getNamedSoundHandler());
            }
        });
    }

    public void registerStopSound(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final SoundRewriter this$0;
            {
                this.this$0 = soundRewriter;
            }

            @Override
            public void register() {
                this.handler(this.this$0.getStopSoundHandler());
            }
        });
    }

    public PacketHandler getNamedSoundHandler() {
        return this::lambda$getNamedSoundHandler$0;
    }

    public PacketHandler getStopSoundHandler() {
        return this::lambda$getStopSoundHandler$1;
    }

    @Override
    public void register1_19_3Sound(C c) {
        this.protocol.registerClientbound(c, this.get1_19_3SoundHandler());
    }

    public PacketHandler get1_19_3SoundHandler() {
        return this::lambda$get1_19_3SoundHandler$2;
    }

    private void lambda$get1_19_3SoundHandler$2(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        if (n != 0) {
            int n2 = this.idRewriter.rewrite(n - 1);
            if (n2 == -1) {
                packetWrapper.cancel();
                return;
            }
            packetWrapper.write(Type.VAR_INT, n2 + 1);
            return;
        }
        packetWrapper.write(Type.VAR_INT, 0);
        String string = packetWrapper.read(Type.STRING);
        String string2 = this.protocol.getMappingData().getMappedNamedSound(string);
        if (string2 != null) {
            if (string2.isEmpty()) {
                packetWrapper.cancel();
                return;
            }
            string = string2;
        }
        packetWrapper.write(Type.STRING, string);
    }

    private void lambda$getStopSoundHandler$1(PacketWrapper packetWrapper) throws Exception {
        byte by = packetWrapper.passthrough(Type.BYTE);
        if ((by & 2) == 0) {
            return;
        }
        if ((by & 1) != 0) {
            packetWrapper.passthrough(Type.VAR_INT);
        }
        String string = packetWrapper.read(Type.STRING);
        String string2 = this.protocol.getMappingData().getMappedNamedSound(string);
        if (string2 == null) {
            packetWrapper.write(Type.STRING, string);
            return;
        }
        if (!string2.isEmpty()) {
            packetWrapper.write(Type.STRING, string2);
        } else {
            packetWrapper.cancel();
        }
    }

    private void lambda$getNamedSoundHandler$0(PacketWrapper packetWrapper) throws Exception {
        String string = packetWrapper.get(Type.STRING, 0);
        String string2 = this.protocol.getMappingData().getMappedNamedSound(string);
        if (string2 == null) {
            return;
        }
        if (!string2.isEmpty()) {
            packetWrapper.set(Type.STRING, 0, string2);
        } else {
            packetWrapper.cancel();
        }
    }
}

