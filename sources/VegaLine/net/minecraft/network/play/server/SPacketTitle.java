/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ITextComponent;

public class SPacketTitle
implements Packet<INetHandlerPlayClient> {
    private Type type;
    private ITextComponent message;
    private int fadeInTime;
    private int displayTime;
    private int fadeOutTime;

    public SPacketTitle() {
    }

    public SPacketTitle(Type typeIn, ITextComponent messageIn) {
        this(typeIn, messageIn, -1, -1, -1);
    }

    public SPacketTitle(int fadeInTimeIn, int displayTimeIn, int fadeOutTimeIn) {
        this(Type.TIMES, null, fadeInTimeIn, displayTimeIn, fadeOutTimeIn);
    }

    public SPacketTitle(Type typeIn, @Nullable ITextComponent messageIn, int fadeInTimeIn, int displayTimeIn, int fadeOutTimeIn) {
        this.type = typeIn;
        this.message = messageIn;
        this.fadeInTime = fadeInTimeIn;
        this.displayTime = displayTimeIn;
        this.fadeOutTime = fadeOutTimeIn;
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.type = buf.readEnumValue(Type.class);
        if (this.type == Type.TITLE || this.type == Type.SUBTITLE || this.type == Type.ACTIONBAR) {
            this.message = buf.readTextComponent();
        }
        if (this.type == Type.TIMES) {
            this.fadeInTime = buf.readInt();
            this.displayTime = buf.readInt();
            this.fadeOutTime = buf.readInt();
        }
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeEnumValue(this.type);
        if (this.type == Type.TITLE || this.type == Type.SUBTITLE || this.type == Type.ACTIONBAR) {
            buf.writeTextComponent(this.message);
        }
        if (this.type == Type.TIMES) {
            buf.writeInt(this.fadeInTime);
            buf.writeInt(this.displayTime);
            buf.writeInt(this.fadeOutTime);
        }
    }

    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleTitle(this);
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

    public static enum Type {
        TITLE,
        SUBTITLE,
        ACTIONBAR,
        TIMES,
        CLEAR,
        RESET;


        public static Type byName(String name) {
            for (Type spackettitle$type : Type.values()) {
                if (!spackettitle$type.name().equalsIgnoreCase(name)) continue;
                return spackettitle$type;
            }
            return TITLE;
        }

        public static String[] getNames() {
            String[] astring = new String[Type.values().length];
            int i = 0;
            for (Type spackettitle$type : Type.values()) {
                astring[i++] = spackettitle$type.name().toLowerCase(Locale.ROOT);
            }
            return astring;
        }
    }
}

