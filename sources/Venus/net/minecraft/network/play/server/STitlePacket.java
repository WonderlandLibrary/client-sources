/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;

public class STitlePacket
implements IPacket<IClientPlayNetHandler> {
    private Type type;
    private ITextComponent message;
    private int fadeInTime;
    private int displayTime;
    private int fadeOutTime;

    public STitlePacket() {
    }

    public STitlePacket(Type type, ITextComponent iTextComponent) {
        this(type, iTextComponent, -1, -1, -1);
    }

    public STitlePacket(int n, int n2, int n3) {
        this(Type.TIMES, null, n, n2, n3);
    }

    public STitlePacket(Type type, @Nullable ITextComponent iTextComponent, int n, int n2, int n3) {
        this.type = type;
        this.message = iTextComponent;
        this.fadeInTime = n;
        this.displayTime = n2;
        this.fadeOutTime = n3;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.type = packetBuffer.readEnumValue(Type.class);
        if (this.type == Type.TITLE || this.type == Type.SUBTITLE || this.type == Type.ACTIONBAR) {
            this.message = packetBuffer.readTextComponent();
        }
        if (this.type == Type.TIMES) {
            this.fadeInTime = packetBuffer.readInt();
            this.displayTime = packetBuffer.readInt();
            this.fadeOutTime = packetBuffer.readInt();
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.type);
        if (this.type == Type.TITLE || this.type == Type.SUBTITLE || this.type == Type.ACTIONBAR) {
            packetBuffer.writeTextComponent(this.message);
        }
        if (this.type == Type.TIMES) {
            packetBuffer.writeInt(this.fadeInTime);
            packetBuffer.writeInt(this.displayTime);
            packetBuffer.writeInt(this.fadeOutTime);
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleTitle(this);
    }

    public Type getType() {
        return this.type;
    }

    public ITextComponent getMessage() {
        return this.message;
    }

    public int getFadeInTime() {
        return this.fadeInTime;
    }

    public int getDisplayTime() {
        return this.displayTime;
    }

    public int getFadeOutTime() {
        return this.fadeOutTime;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }

    public static enum Type {
        TITLE,
        SUBTITLE,
        ACTIONBAR,
        TIMES,
        CLEAR,
        RESET;

    }
}

