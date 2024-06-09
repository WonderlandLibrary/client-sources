package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import org.lwjgl.BufferUtils;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Comment;
import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Info;
import java.io.InputStream;

public class OggInputStream extends InputStream implements AudioInputStream
{
    private int Ó;
    private byte[] à;
    private InputStream Ø;
    private Info áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private SyncState ÂµÈ;
    private StreamState á;
    private Page ˆÏ­;
    private Packet £á;
    private Comment Å;
    private DspState £à;
    private Block µà;
    byte[] HorizonCode_Horizon_È;
    int Â;
    boolean Ý;
    boolean Ø­áŒŠá;
    boolean Âµá€;
    private int ˆà;
    private ByteBuffer ¥Æ;
    private int Ø­à;
    
    public OggInputStream(final InputStream input) throws IOException {
        this.Ó = 16384;
        this.à = new byte[this.Ó];
        this.áŒŠÆ = new Info();
        this.ÂµÈ = new SyncState();
        this.á = new StreamState();
        this.ˆÏ­ = new Page();
        this.£á = new Packet();
        this.Å = new Comment();
        this.£à = new DspState();
        this.µà = new Block(this.£à);
        this.Â = 0;
        this.Ý = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN);
        this.Ø­áŒŠá = true;
        this.Âµá€ = false;
        this.¥Æ = BufferUtils.createByteBuffer(2048000);
        this.Ø = input;
        this.Ø­à = input.available();
        this.Âµá€();
    }
    
    public int Ø­áŒŠá() {
        return this.Ø­à;
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return this.áŒŠÆ.channels;
    }
    
    @Override
    public int Â() {
        return this.áŒŠÆ.rate;
    }
    
    private void Âµá€() throws IOException {
        this.Ó();
        this.Ø();
    }
    
    @Override
    public int available() {
        return this.áˆºÑ¢Õ ? 0 : 1;
    }
    
    private void Ó() {
        this.ÂµÈ.init();
    }
    
    private boolean à() {
        int index = this.ÂµÈ.buffer(4096);
        this.HorizonCode_Horizon_È = this.ÂµÈ.data;
        if (this.HorizonCode_Horizon_È == null) {
            this.áˆºÑ¢Õ = true;
            return false;
        }
        try {
            this.Â = this.Ø.read(this.HorizonCode_Horizon_È, index, 4096);
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È("Failure reading in vorbis");
            Log.HorizonCode_Horizon_È(e);
            this.áˆºÑ¢Õ = true;
            return false;
        }
        this.ÂµÈ.wrote(this.Â);
        if (this.ÂµÈ.pageout(this.ˆÏ­) != 1) {
            if (this.Â < 4096) {
                return false;
            }
            Log.HorizonCode_Horizon_È("Input does not appear to be an Ogg bitstream.");
            this.áˆºÑ¢Õ = true;
            return false;
        }
        else {
            this.á.init(this.ˆÏ­.serialno());
            this.áŒŠÆ.init();
            this.Å.init();
            if (this.á.pagein(this.ˆÏ­) < 0) {
                Log.HorizonCode_Horizon_È("Error reading first page of Ogg bitstream data.");
                this.áˆºÑ¢Õ = true;
                return false;
            }
            if (this.á.packetout(this.£á) != 1) {
                Log.HorizonCode_Horizon_È("Error reading initial header packet.");
                this.áˆºÑ¢Õ = true;
                return false;
            }
            if (this.áŒŠÆ.synthesis_headerin(this.Å, this.£á) < 0) {
                Log.HorizonCode_Horizon_È("This Ogg bitstream does not contain Vorbis audio data.");
                this.áˆºÑ¢Õ = true;
                return false;
            }
            int i = 0;
            while (i < 2) {
                while (i < 2) {
                    int result = this.ÂµÈ.pageout(this.ˆÏ­);
                    if (result == 0) {
                        break;
                    }
                    if (result != 1) {
                        continue;
                    }
                    this.á.pagein(this.ˆÏ­);
                    while (i < 2) {
                        result = this.á.packetout(this.£á);
                        if (result == 0) {
                            break;
                        }
                        if (result == -1) {
                            Log.HorizonCode_Horizon_È("Corrupt secondary header.  Exiting.");
                            this.áˆºÑ¢Õ = true;
                            return false;
                        }
                        this.áŒŠÆ.synthesis_headerin(this.Å, this.£á);
                        ++i;
                    }
                }
                index = this.ÂµÈ.buffer(4096);
                this.HorizonCode_Horizon_È = this.ÂµÈ.data;
                try {
                    this.Â = this.Ø.read(this.HorizonCode_Horizon_È, index, 4096);
                }
                catch (Exception e2) {
                    Log.HorizonCode_Horizon_È("Failed to read Vorbis: ");
                    Log.HorizonCode_Horizon_È(e2);
                    this.áˆºÑ¢Õ = true;
                    return false;
                }
                if (this.Â == 0 && i < 2) {
                    Log.HorizonCode_Horizon_È("End of file before finding all Vorbis headers!");
                    this.áˆºÑ¢Õ = true;
                    return false;
                }
                this.ÂµÈ.wrote(this.Â);
            }
            this.Ó = 4096 / this.áŒŠÆ.channels;
            this.£à.synthesis_init(this.áŒŠÆ);
            this.µà.init(this.£à);
            return true;
        }
    }
    
    private void Ø() throws IOException {
        boolean wrote = false;
        while (true) {
            if (this.Ø­áŒŠá) {
                if (!this.à()) {
                    this.ÂµÈ.clear();
                    this.áˆºÑ¢Õ = true;
                    return;
                }
                this.Ø­áŒŠá = false;
            }
            if (!this.Âµá€) {
                this.Âµá€ = true;
                return;
            }
            final float[][][] _pcm = { null };
            final int[] _index = new int[this.áŒŠÆ.channels];
            while (!this.Ø­áŒŠá) {
                while (!this.Ø­áŒŠá) {
                    int result = this.ÂµÈ.pageout(this.ˆÏ­);
                    if (result == 0) {
                        break;
                    }
                    if (result == -1) {
                        Log.HorizonCode_Horizon_È("Corrupt or missing data in bitstream; continuing...");
                    }
                    else {
                        this.á.pagein(this.ˆÏ­);
                        while (true) {
                            result = this.á.packetout(this.£á);
                            if (result == 0) {
                                break;
                            }
                            if (result == -1) {
                                continue;
                            }
                            if (this.µà.synthesis(this.£á) == 0) {
                                this.£à.synthesis_blockin(this.µà);
                            }
                            int samples;
                            while ((samples = this.£à.synthesis_pcmout(_pcm, _index)) > 0) {
                                final float[][] pcm = _pcm[0];
                                final int bout = (samples < this.Ó) ? samples : this.Ó;
                                for (int i = 0; i < this.áŒŠÆ.channels; ++i) {
                                    int ptr = i * 2;
                                    final int mono = _index[i];
                                    for (int j = 0; j < bout; ++j) {
                                        int val = (int)(pcm[i][mono + j] * 32767.0);
                                        if (val > 32767) {
                                            val = 32767;
                                        }
                                        if (val < -32768) {
                                            val = -32768;
                                        }
                                        if (val < 0) {
                                            val |= 0x8000;
                                        }
                                        if (this.Ý) {
                                            this.à[ptr] = (byte)(val >>> 8);
                                            this.à[ptr + 1] = (byte)val;
                                        }
                                        else {
                                            this.à[ptr] = (byte)val;
                                            this.à[ptr + 1] = (byte)(val >>> 8);
                                        }
                                        ptr += 2 * this.áŒŠÆ.channels;
                                    }
                                }
                                final int bytesToWrite = 2 * this.áŒŠÆ.channels * bout;
                                if (bytesToWrite >= this.¥Æ.remaining()) {
                                    Log.Â("Read block from OGG that was too big to be buffered: " + bytesToWrite);
                                }
                                else {
                                    this.¥Æ.put(this.à, 0, bytesToWrite);
                                }
                                wrote = true;
                                this.£à.synthesis_read(bout);
                            }
                        }
                        if (this.ˆÏ­.eos() != 0) {
                            this.Ø­áŒŠá = true;
                        }
                        if (!this.Ø­áŒŠá && wrote) {
                            return;
                        }
                        continue;
                    }
                }
                if (!this.Ø­áŒŠá) {
                    this.Â = 0;
                    final int index = this.ÂµÈ.buffer(4096);
                    Label_0579: {
                        if (index >= 0) {
                            this.HorizonCode_Horizon_È = this.ÂµÈ.data;
                            try {
                                this.Â = this.Ø.read(this.HorizonCode_Horizon_È, index, 4096);
                                break Label_0579;
                            }
                            catch (Exception e) {
                                Log.HorizonCode_Horizon_È("Failure during vorbis decoding");
                                Log.HorizonCode_Horizon_È(e);
                                this.áˆºÑ¢Õ = true;
                                return;
                            }
                        }
                        this.Â = 0;
                    }
                    this.ÂµÈ.wrote(this.Â);
                    if (this.Â != 0) {
                        continue;
                    }
                    this.Ø­áŒŠá = true;
                }
            }
            this.á.clear();
            this.µà.clear();
            this.£à.clear();
            this.áŒŠÆ.clear();
        }
    }
    
    @Override
    public int read() throws IOException {
        if (this.ˆà >= this.¥Æ.position()) {
            this.¥Æ.clear();
            this.Ø();
            this.ˆà = 0;
        }
        if (this.ˆà >= this.¥Æ.position()) {
            return -1;
        }
        int value = this.¥Æ.get(this.ˆà);
        if (value < 0) {
            value += 256;
        }
        ++this.ˆà;
        return value;
    }
    
    @Override
    public boolean Ý() {
        return this.áˆºÑ¢Õ && this.ˆà >= this.¥Æ.position();
    }
    
    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        for (int i = 0; i < len; ++i) {
            try {
                final int value = this.read();
                if (value >= 0) {
                    b[i] = (byte)value;
                }
                else {
                    if (i == 0) {
                        return -1;
                    }
                    return i;
                }
            }
            catch (IOException e) {
                Log.HorizonCode_Horizon_È(e);
                return i;
            }
        }
        return len;
    }
    
    @Override
    public int read(final byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }
    
    @Override
    public void close() throws IOException {
    }
}
