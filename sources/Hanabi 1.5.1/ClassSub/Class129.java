package ClassSub;

import java.io.*;
import java.nio.*;
import org.lwjgl.*;

public class Class129 implements Class96
{
    private int texWidth;
    private int texHeight;
    private int width;
    private int height;
    private short pixelDepth;
    
    
    private short flipEndian(final short n) {
        final int n2 = n & 0xFFFF;
        return (short)(n2 << 8 | (n2 & 0xFF00) >>> 8);
    }
    
    @Override
    public int getDepth() {
        return this.pixelDepth;
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public int getTexWidth() {
        return this.texWidth;
    }
    
    @Override
    public int getTexHeight() {
        return this.texHeight;
    }
    
    @Override
    public ByteBuffer loadImage(final InputStream inputStream) throws IOException {
        return this.loadImage(inputStream, true, null);
    }
    
    @Override
    public ByteBuffer loadImage(final InputStream inputStream, final boolean b, final int[] array) throws IOException {
        return this.loadImage(inputStream, b, false, array);
    }
    
    @Override
    public ByteBuffer loadImage(final InputStream inputStream, boolean b, boolean b2, final int[] array) throws IOException {
        if (array != null) {
            b2 = true;
        }
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 100000);
        final DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
        final short n = (short)dataInputStream.read();
        final short n2 = (short)dataInputStream.read();
        final short n3 = (short)dataInputStream.read();
        this.flipEndian(dataInputStream.readShort());
        this.flipEndian(dataInputStream.readShort());
        final short n4 = (short)dataInputStream.read();
        this.flipEndian(dataInputStream.readShort());
        this.flipEndian(dataInputStream.readShort());
        if (n3 != 2) {
            throw new IOException("Slick only supports uncompressed RGB(A) TGA images");
        }
        this.width = this.flipEndian(dataInputStream.readShort());
        this.height = this.flipEndian(dataInputStream.readShort());
        this.pixelDepth = (short)dataInputStream.read();
        if (this.pixelDepth == 32) {
            b2 = false;
        }
        this.texWidth = this.get2Fold(this.width);
        this.texHeight = this.get2Fold(this.height);
        if (((short)dataInputStream.read() & 0x20) == 0x0) {
            b = !b;
        }
        if (n > 0) {
            bufferedInputStream.skip(n);
        }
        byte[] array2;
        if (this.pixelDepth == 32 || b2) {
            this.pixelDepth = 32;
            array2 = new byte[this.texWidth * this.texHeight * 4];
        }
        else {
            if (this.pixelDepth != 24) {
                throw new RuntimeException("Only 24 and 32 bit TGAs are supported");
            }
            array2 = new byte[this.texWidth * this.texHeight * 3];
        }
        if (this.pixelDepth == 24) {
            if (b) {
                for (int i = this.height - 1; i >= 0; --i) {
                    for (int j = 0; j < this.width; ++j) {
                        final byte byte1 = dataInputStream.readByte();
                        final byte byte2 = dataInputStream.readByte();
                        final byte byte3 = dataInputStream.readByte();
                        final int n5 = (j + i * this.texWidth) * 3;
                        array2[n5] = byte3;
                        array2[n5 + 1] = byte2;
                        array2[n5 + 2] = byte1;
                    }
                }
            }
            else {
                for (int k = 0; k < this.height; ++k) {
                    for (int l = 0; l < this.width; ++l) {
                        final byte byte4 = dataInputStream.readByte();
                        final byte byte5 = dataInputStream.readByte();
                        final byte byte6 = dataInputStream.readByte();
                        final int n6 = (l + k * this.texWidth) * 3;
                        array2[n6] = byte6;
                        array2[n6 + 1] = byte5;
                        array2[n6 + 2] = byte4;
                    }
                }
            }
        }
        else if (this.pixelDepth == 32) {
            if (b) {
                for (int n7 = this.height - 1; n7 >= 0; --n7) {
                    for (int n8 = 0; n8 < this.width; ++n8) {
                        final byte byte7 = dataInputStream.readByte();
                        final byte byte8 = dataInputStream.readByte();
                        final byte byte9 = dataInputStream.readByte();
                        byte byte10;
                        if (b2) {
                            byte10 = -1;
                        }
                        else {
                            byte10 = dataInputStream.readByte();
                        }
                        final int n9 = (n8 + n7 * this.texWidth) * 4;
                        array2[n9] = byte9;
                        array2[n9 + 1] = byte8;
                        array2[n9 + 2] = byte7;
                        array2[n9 + 3] = byte10;
                        if (byte10 == 0) {
                            array2[n9 + 2] = 0;
                            array2[n9] = (array2[n9 + 1] = 0);
                        }
                    }
                }
            }
            else {
                for (int n10 = 0; n10 < this.height; ++n10) {
                    for (int n11 = 0; n11 < this.width; ++n11) {
                        final byte byte11 = dataInputStream.readByte();
                        final byte byte12 = dataInputStream.readByte();
                        final byte byte13 = dataInputStream.readByte();
                        byte byte14;
                        if (b2) {
                            byte14 = -1;
                        }
                        else {
                            byte14 = dataInputStream.readByte();
                        }
                        final int n12 = (n11 + n10 * this.texWidth) * 4;
                        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
                            array2[n12] = byte13;
                            array2[n12 + 1] = byte12;
                            array2[n12 + 2] = byte11;
                            array2[n12 + 3] = byte14;
                        }
                        else {
                            array2[n12] = byte13;
                            array2[n12 + 1] = byte12;
                            array2[n12 + 2] = byte11;
                            array2[n12 + 3] = byte14;
                        }
                        if (byte14 == 0) {
                            array2[n12 + 2] = 0;
                            array2[n12] = (array2[n12 + 1] = 0);
                        }
                    }
                }
            }
        }
        inputStream.close();
        if (array != null) {
            for (int n13 = 0; n13 < array2.length; n13 += 4) {
                boolean b3 = true;
                for (int n14 = 0; n14 < 3; ++n14) {
                    if (array2[n13 + n14] != array[n14]) {
                        b3 = false;
                    }
                }
                if (b3) {
                    array2[n13 + 3] = 0;
                }
            }
        }
        final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(array2.length);
        byteBuffer.put(array2);
        final short n15 = (short)(this.pixelDepth / 8);
        if (this.height < this.texHeight - 1) {
            final int n16 = (this.texHeight - 1) * (this.texWidth * n15);
            final int n17 = (this.height - 1) * (this.texWidth * n15);
            for (int n18 = 0; n18 < this.texWidth * n15; ++n18) {
                byteBuffer.put(n16 + n18, byteBuffer.get(n18));
                byteBuffer.put(n17 + this.texWidth * n15 + n18, byteBuffer.get(this.texWidth * n15 + n18));
            }
        }
        if (this.width < this.texWidth - 1) {
            for (int n19 = 0; n19 < this.texHeight; ++n19) {
                for (short n20 = 0; n20 < n15; ++n20) {
                    byteBuffer.put((n19 + 1) * (this.texWidth * n15) - n15 + n20, byteBuffer.get(n19 * (this.texWidth * n15) + n20));
                    byteBuffer.put(n19 * (this.texWidth * n15) + this.width * n15 + n20, byteBuffer.get(n19 * (this.texWidth * n15) + (this.width - 1) * n15 + n20));
                }
            }
        }
        byteBuffer.flip();
        return byteBuffer;
    }
    
    private int get2Fold(final int n) {
        int i;
        for (i = 2; i < n; i *= 2) {}
        return i;
    }
    
    @Override
    public ByteBuffer getImageBufferData() {
        throw new RuntimeException("TGAImageData doesn't store it's image.");
    }
    
    @Override
    public void configureEdging(final boolean b) {
    }
}
