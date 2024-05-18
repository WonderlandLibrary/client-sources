package HORIZON-6-0-SKIDPROTECTION;

import java.nio.ByteBuffer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class OggDecoder
{
    private int HorizonCode_Horizon_È;
    private byte[] Â;
    
    public OggDecoder() {
        this.HorizonCode_Horizon_È = 16384;
        this.Â = new byte[this.HorizonCode_Horizon_È];
    }
    
    public OggData HorizonCode_Horizon_È(final InputStream input) throws IOException {
        if (input == null) {
            throw new IOException("Failed to read OGG, source does not exist?");
        }
        final ByteArrayOutputStream dataout = new ByteArrayOutputStream();
        final OggInputStream oggInput = new OggInputStream(input);
        final boolean done = false;
        while (!oggInput.Ý()) {
            dataout.write(oggInput.read());
        }
        final OggData ogg = new OggData();
        ogg.Ý = oggInput.HorizonCode_Horizon_È();
        ogg.Â = oggInput.Â();
        final byte[] data = dataout.toByteArray();
        (ogg.HorizonCode_Horizon_È = ByteBuffer.allocateDirect(data.length)).put(data);
        ogg.HorizonCode_Horizon_È.rewind();
        return ogg;
    }
}
