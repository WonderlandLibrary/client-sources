/*
 * Decompiled with CFR 0.152.
 */
package de.gerrygames.viarewind.protocol.protocol1_7_0_5to1_7_6_10;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ServerboundPackets1_7;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Protocol1_7_0_5to1_7_6_10
extends AbstractProtocol<ClientboundPackets1_7, ClientboundPackets1_7, ServerboundPackets1_7, ServerboundPackets1_7> {
    public static final ValueTransformer<String, String> REMOVE_DASHES = new ValueTransformer<String, String>(Type.STRING){

        @Override
        public String transform(PacketWrapper packetWrapper, String s) {
            return s.replace("-", "");
        }
    };

    public Protocol1_7_0_5to1_7_6_10() {
        super(ClientboundPackets1_7.class, ClientboundPackets1_7.class, ServerboundPackets1_7.class, ServerboundPackets1_7.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(State.LOGIN, 2, 2, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING, REMOVE_DASHES);
                this.map(Type.STRING);
            }
        });
        this.registerClientbound(ClientboundPackets1_7.SPAWN_PLAYER, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.STRING, REMOVE_DASHES);
                this.map(Type.STRING);
                this.handler(packetWrapper -> {
                    int size = packetWrapper.read(Type.VAR_INT);
                    int i = 0;
                    while (i < size * 3) {
                        packetWrapper.read(Type.STRING);
                        ++i;
                    }
                });
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Types1_7_6_10.METADATA_LIST);
            }
        });
        this.registerClientbound(ClientboundPackets1_7.TEAMS, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(packetWrapper -> {
                    byte mode = packetWrapper.get(Type.BYTE, 0);
                    if (mode == 0 || mode == 2) {
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.BYTE);
                    }
                    if (mode != 0 && mode != 3) {
                        if (mode != 4) return;
                    }
                    List<Object> entryList = new ArrayList<String>();
                    int size = packetWrapper.read(Type.SHORT).shortValue();
                    for (int i = 0; i < size; ++i) {
                        entryList.add(packetWrapper.read(Type.STRING));
                    }
                    entryList = entryList.stream().map((? super T it) -> {
                        String string;
                        if (it.length() > 16) {
                            string = it.substring(0, 16);
                            return string;
                        }
                        string = it;
                        return string;
                    }).distinct().collect(Collectors.toList());
                    packetWrapper.write(Type.SHORT, (short)entryList.size());
                    Iterator<Object> iterator = entryList.iterator();
                    while (iterator.hasNext()) {
                        String entry = (String)iterator.next();
                        packetWrapper.write(Type.STRING, entry);
                    }
                });
            }
        });
    }

    @Override
    public void init(UserConnection userConnection) {
    }
}

