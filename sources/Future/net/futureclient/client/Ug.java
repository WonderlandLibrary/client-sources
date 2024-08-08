package net.futureclient.client;

import java.io.DataInputStream;
import java.io.IOException;
import com.google.common.io.ByteArrayDataOutput;

public class Ug extends ai
{
    private String M;
    private String d;
    private String a;
    private String D;
    public boolean k;
    
    public Ug() {
        final boolean k = false;
        final String a = "";
        super();
        this.a = a;
        this.k = k;
    }
    
    public Ug(final String d, final String m, final String d2) {
        final boolean k = false;
        final String a = "";
        super();
        this.a = a;
        this.k = k;
        this.D = d;
        this.M = m;
        this.d = d2;
    }
    
    @Override
    public void M(final ByteArrayDataOutput byteArrayDataOutput) throws IOException {
        byteArrayDataOutput.writeUTF(this.D);
        byteArrayDataOutput.writeUTF(this.M);
        byteArrayDataOutput.writeUTF(this.d);
    }
    
    @Override
    public int M() {
        return 3;
    }
    
    public String M() {
        return this.a;
    }
    
    @Override
    public void M(final DataInputStream dataInputStream) throws IOException {
        this.k = dataInputStream.readBoolean();
        this.a = dataInputStream.readUTF();
    }
}
