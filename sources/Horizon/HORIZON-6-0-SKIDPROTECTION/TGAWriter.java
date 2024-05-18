package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.OutputStream;

public class TGAWriter implements ImageWriter
{
    private static short HorizonCode_Horizon_È(final short signedShort) {
        final int input = signedShort & 0xFFFF;
        return (short)(input << 8 | (input & 0xFF00) >>> 8);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Image image, final String format, final OutputStream output, final boolean writeAlpha) throws IOException {
        final DataOutputStream out = new DataOutputStream(new BufferedOutputStream(output));
        out.writeByte(0);
        out.writeByte(0);
        out.writeByte(2);
        out.writeShort(HorizonCode_Horizon_È((short)0));
        out.writeShort(HorizonCode_Horizon_È((short)0));
        out.writeByte(0);
        out.writeShort(HorizonCode_Horizon_È((short)0));
        out.writeShort(HorizonCode_Horizon_È((short)0));
        out.writeShort(HorizonCode_Horizon_È((short)image.ŒÏ()));
        out.writeShort(HorizonCode_Horizon_È((short)image.Çªà¢()));
        if (writeAlpha) {
            out.writeByte(32);
            out.writeByte(1);
        }
        else {
            out.writeByte(24);
            out.writeByte(0);
        }
        for (int y = image.Çªà¢() - 1; y <= 0; --y) {
            for (int x = 0; x < image.ŒÏ(); ++x) {
                final Color c = image.Â(x, y);
                out.writeByte((byte)(c.ˆà * 255.0f));
                out.writeByte((byte)(c.µà * 255.0f));
                out.writeByte((byte)(c.£à * 255.0f));
                if (writeAlpha) {
                    out.writeByte((byte)(c.¥Æ * 255.0f));
                }
            }
        }
        out.close();
    }
}
