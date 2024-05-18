package HORIZON-6-0-SKIDPROTECTION;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.io.ByteArrayInputStream;
import org.w3c.dom.Element;
import java.util.Properties;

public class Layer
{
    private static byte[] à;
    private final TiledMap Ø;
    public int HorizonCode_Horizon_È;
    public String Â;
    public int[][][] Ý;
    public int Ø­áŒŠá;
    public int Âµá€;
    public Properties Ó;
    
    static {
        Layer.à = new byte[256];
        for (int i = 0; i < 256; ++i) {
            Layer.à[i] = -1;
        }
        for (int i = 65; i <= 90; ++i) {
            Layer.à[i] = (byte)(i - 65);
        }
        for (int i = 97; i <= 122; ++i) {
            Layer.à[i] = (byte)(26 + i - 97);
        }
        for (int i = 48; i <= 57; ++i) {
            Layer.à[i] = (byte)(52 + i - 48);
        }
        Layer.à[43] = 62;
        Layer.à[47] = 63;
    }
    
    public Layer(final TiledMap map, final Element element) throws SlickException {
        this.Ø = map;
        this.Â = element.getAttribute("name");
        this.Ø­áŒŠá = Integer.parseInt(element.getAttribute("width"));
        this.Âµá€ = Integer.parseInt(element.getAttribute("height"));
        this.Ý = new int[this.Ø­áŒŠá][this.Âµá€][3];
        final Element propsElement = (Element)element.getElementsByTagName("properties").item(0);
        if (propsElement != null) {
            final NodeList properties = propsElement.getElementsByTagName("property");
            if (properties != null) {
                this.Ó = new Properties();
                for (int p = 0; p < properties.getLength(); ++p) {
                    final Element propElement = (Element)properties.item(p);
                    final String name = propElement.getAttribute("name");
                    final String value = propElement.getAttribute("value");
                    this.Ó.setProperty(name, value);
                }
            }
        }
        final Element dataNode = (Element)element.getElementsByTagName("data").item(0);
        final String encoding = dataNode.getAttribute("encoding");
        final String compression = dataNode.getAttribute("compression");
        if (encoding.equals("base64") && compression.equals("gzip")) {
            try {
                final Node cdata = dataNode.getFirstChild();
                final char[] enc = cdata.getNodeValue().trim().toCharArray();
                final byte[] dec = this.HorizonCode_Horizon_È(enc);
                final GZIPInputStream is = new GZIPInputStream(new ByteArrayInputStream(dec));
                for (int y = 0; y < this.Âµá€; ++y) {
                    for (int x = 0; x < this.Ø­áŒŠá; ++x) {
                        int tileId = 0;
                        tileId |= is.read();
                        tileId |= is.read() << 8;
                        tileId |= is.read() << 16;
                        tileId |= is.read() << 24;
                        if (tileId == 0) {
                            this.Ý[x][y][0] = -1;
                            this.Ý[x][y][1] = 0;
                            this.Ý[x][y][2] = 0;
                        }
                        else {
                            final TileSet set = map.Ý(tileId);
                            if (set != null) {
                                this.Ý[x][y][0] = set.HorizonCode_Horizon_È;
                                this.Ý[x][y][1] = tileId - set.Ý;
                            }
                            this.Ý[x][y][2] = tileId;
                        }
                    }
                }
                return;
            }
            catch (IOException e) {
                Log.HorizonCode_Horizon_È(e);
                throw new SlickException("Unable to decode base 64 block");
            }
            throw new SlickException("Unsupport tiled map type: " + encoding + "," + compression + " (only gzip base64 supported)");
        }
        throw new SlickException("Unsupport tiled map type: " + encoding + "," + compression + " (only gzip base64 supported)");
    }
    
    public int HorizonCode_Horizon_È(final int x, final int y) {
        return this.Ý[x][y][2];
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int tile) {
        if (tile == 0) {
            this.Ý[x][y][0] = -1;
            this.Ý[x][y][1] = 0;
            this.Ý[x][y][2] = 0;
        }
        else {
            final TileSet set = this.Ø.Ý(tile);
            this.Ý[x][y][0] = set.HorizonCode_Horizon_È;
            this.Ý[x][y][1] = tile - set.Ý;
            this.Ý[x][y][2] = tile;
        }
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int sx, final int sy, final int width, final int ty, final boolean lineByLine, final int mapTileWidth, final int mapTileHeight) {
        for (int tileset = 0; tileset < this.Ø.à(); ++tileset) {
            TileSet set = null;
            for (int tx = 0; tx < width; ++tx) {
                if (sx + tx >= 0) {
                    if (sy + ty >= 0) {
                        if (sx + tx < this.Ø­áŒŠá) {
                            if (sy + ty < this.Âµá€) {
                                if (this.Ý[sx + tx][sy + ty][0] == tileset) {
                                    if (set == null) {
                                        set = this.Ø.HorizonCode_Horizon_È(tileset);
                                        set.à.Ó();
                                    }
                                    final int sheetX = set.Â(this.Ý[sx + tx][sy + ty][1]);
                                    final int sheetY = set.Ý(this.Ý[sx + tx][sy + ty][1]);
                                    final int tileOffsetY = set.Ó - mapTileHeight;
                                    set.à.Â(x + tx * mapTileWidth, y + ty * mapTileHeight - tileOffsetY, sheetX, sheetY);
                                }
                            }
                        }
                    }
                }
            }
            if (lineByLine) {
                if (set != null) {
                    set.à.Âµá€();
                    set = null;
                }
                this.Ø.Ø­áŒŠá(ty, ty + sy, this.HorizonCode_Horizon_È);
            }
            if (set != null) {
                set.à.Âµá€();
            }
        }
    }
    
    private byte[] HorizonCode_Horizon_È(final char[] data) {
        int temp = data.length;
        for (int ix = 0; ix < data.length; ++ix) {
            if (data[ix] > 'ÿ' || Layer.à[data[ix]] < 0) {
                --temp;
            }
        }
        int len = temp / 4 * 3;
        if (temp % 4 == 3) {
            len += 2;
        }
        if (temp % 4 == 2) {
            ++len;
        }
        final byte[] out = new byte[len];
        int shift = 0;
        int accum = 0;
        int index = 0;
        for (int ix2 = 0; ix2 < data.length; ++ix2) {
            final int value = (data[ix2] > 'ÿ') ? -1 : Layer.à[data[ix2]];
            if (value >= 0) {
                accum <<= 6;
                shift += 6;
                accum |= value;
                if (shift >= 8) {
                    shift -= 8;
                    out[index++] = (byte)(accum >> shift & 0xFF);
                }
            }
        }
        if (index != out.length) {
            throw new RuntimeException("Data length appears to be wrong (wrote " + index + " should be " + out.length + ")");
        }
        return out;
    }
}
