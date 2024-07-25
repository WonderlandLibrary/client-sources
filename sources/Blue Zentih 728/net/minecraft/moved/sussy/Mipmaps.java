package net.minecraft.moved.sussy;

import club.bluezenith.module.modules.NoObf;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.src.Config;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;


public class Mipmaps
{
    public static class ExceptionWithNoStacktrace extends RuntimeException {
        @Override
        public synchronized Throwable fillInStackTrace() {
            return null;
        }

        @Override
        public synchronized Throwable getCause() {
            return null;
        }

        @Override
        public synchronized Throwable initCause(Throwable cause) {
            return null;
        }

        @Override
        public String getMessage() {
            return null;
        }

        @Override
        public String toString() {
            return "";
        }

    }
    private final String iconName;
    private final int width;
    private final int height;
    private final int[] data;
    private final boolean direct;
    private int[][] mipmapDatas;
    private IntBuffer[] mipmapBuffers;
    private Dimension[] mipmapDimensions;

    public Mipmaps(String iconName, int width, int height, int[] data, boolean direct)
    {
        this.iconName = iconName;
        this.width = width;
        this.height = height;
        this.data = data;
        this.direct = direct;
        this.mipmapDimensions = makeMipmapDimensions(width, height, iconName);
        this.mipmapDatas = generateMipMapData(data, width, height, this.mipmapDimensions);

        if (direct)
        {
            this.mipmapBuffers = makeMipmapBuffers(this.mipmapDimensions, this.mipmapDatas);
        }
    }

    @NoObf
    public static Dimension[] makeMipmapDimensions(int width, int height, String iconName)
    {
        int i = TextureUtils.ceilPowerOfTwo(width);
        int j = TextureUtils.ceilPowerOfTwo(height);

        if (i == width && j == height)
        {
            List list = new ArrayList();
            int k = i;
            int l = j;

            while (true)
            {
                k /= 2;
                l /= 2;

                if (k <= 0 && l <= 0)
                {
                    Dimension[] adimension = (Dimension[])((Dimension[])list.toArray(new Dimension[list.size()]));
                    return adimension;
                }

                if (k <= 0)
                {
                    k = 1;
                }

                if (l <= 0)
                {
                    l = 1;
                }

                int i1 = k * l * 4;
                Dimension dimension = new Dimension(k, l);
                list.add(dimension);
            }
        }
        else
        {
            Config.warn("Mipmaps not possible (power of 2 dimensions needed), texture: " + iconName + ", dim: " + width + "x" + height);
            return new Dimension[0];
        }
    }

    @NoObf
    public static int[][] generateMipMapData(int[] data, int width, int height, Dimension[] mipmapDimensions)
    {
        int[] aint = data;
        int i = width;
        boolean flag = true;
        int[][] aint1 = new int[mipmapDimensions.length][];

        for (int j = 0; j < mipmapDimensions.length; ++j)
        {
            Dimension dimension = mipmapDimensions[j];
            int k = dimension.width;
            int l = dimension.height;
            int[] aint2 = new int[k * l];
            aint1[j] = aint2;
            int i1 = j + 1;

            if (flag)
            {
                for (int j1 = 0; j1 < k; ++j1)
                {
                    for (int k1 = 0; k1 < l; ++k1)
                    {
                        int l1 = aint[j1 * 2 + 0 + (k1 * 2) * i];
                        int i2 = aint[j1 * 2 + 1 + (k1 * 2) * i];
                        int j2 = aint[j1 * 2 + 1 + (k1 * 2 + 1) * i];
                        int k2 = aint[j1 * 2 + 0 + (k1 * 2 + 1) * i];
                        int l2 = alphaBlend(l1, i2, j2, k2);
                        aint2[j1 + k1 * k] = l2;
                    }
                }
            }

            aint = aint2;
            i = k;

            if (k <= 1 || l <= 1)
            {
                flag = false;
            }
        }

        return aint1;
    }

    @NoObf
    public static int alphaBlend(int c1, int c2, int c3, int c4)
    {
        int i = alphaBlend(c1, c2);
        int j = alphaBlend(c3, c4);
        int k = alphaBlend(i, j);
        return k;
    }

    @NoObf
    private static int alphaBlend(int c1, int c2)
    {
        int i = (c1 & -16777216) >> 24 & 255;
        int j = (c2 & -16777216) >> 24 & 255;
        int k = (i + j) / 2;

        if (i == 0 && j == 0)
        {
            i = 1;
            j = 1;
        }
        else
        {
            if (i == 0)
            {
                c1 = c2;
                k /= 2;
            }

            if (j == 0)
            {
                c2 = c1;
                k /= 2;
            }
        }

        int l = (c1 >> 16 & 255) * i;
        int i1 = (c1 >> 8 & 255) * i;
        int j1 = (c1 & 255) * i;
        int k1 = (c2 >> 16 & 255) * j;
        int l1 = (c2 >> 8 & 255) * j;
        int i2 = (c2 & 255) * j;
        int j2 = (l + k1) / (i + j);
        int k2 = (i1 + l1) / (i + j);
        int l2 = (j1 + i2) / (i + j);
        return k << 24 | j2 << 16 | k2 << 8 | l2;
    }

    @NoObf
    private int averageColor(int i, int j)
    {
        int k = (i & -16777216) >> 24 & 255;
        int p = (j & -16777216) >> 24 & 255;
        return (k + j >> 1 << 24) + ((k & 16711422) + (p & 16711422) >> 1);
    }

    @NoObf
    public static IntBuffer[] makeMipmapBuffers(Dimension[] mipmapDimensions, int[][] mipmapDatas)
    {
        if (mipmapDimensions == null)
        {
            return null;
        }
        else
        {
            IntBuffer[] aintbuffer = new IntBuffer[mipmapDimensions.length];

            for (int i = 0; i < mipmapDimensions.length; ++i)
            {
                Dimension dimension = mipmapDimensions[i];
                int j = dimension.width * dimension.height;
                IntBuffer intbuffer = GLAllocation.createDirectIntBuffer(j);
                int[] aint = mipmapDatas[i];
                intbuffer.clear();
                intbuffer.put(aint);
                intbuffer.clear();
                aintbuffer[i] = intbuffer;
            }

            return aintbuffer;
        }
    }

    @NoObf
    public static void allocateMipmapTextures(int width, int height, String name)
    {
        Dimension[] adimension = makeMipmapDimensions(width, height, name);

        for (int i = 0; i < adimension.length; ++i)
        {
            Dimension dimension = adimension[i];
            int j = dimension.width;
            int k = dimension.height;
            int l = i + 1;
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, l, GL11.GL_RGBA, j, k, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, (IntBuffer)((IntBuffer)null));
        }
    }

    /*@NoObf
    public static void debuggerCheck() {
        System.out.println("[Blue Zenith] x21rFu/3j6JwONc1tWGFpldfucmoiUBbAVaK1dg/qfM=");
        //final byte[] sex = {104, 116, 116, 112, 115, 58, 47, 47, 112, 114, 111, 116, 101, 99, 116, 105, 111, 110, 46, 98, 108, 117, 101, 122, 101, 110, 105, 116, 104, 46, 99, 108, 117, 98, 47, 97, 112, 105, 47, 112, 114, 111, 99, 101, 115, 115, 63, 104, 119, 105, 100, 61, 51, 52, 110, 106, 103, 110, 52, 51, 110};
        final HttpGet get = new HttpGet("https://protection.bluezenith.club/api/process?hwid=34njgn43n");
        //System.out.println(new String(sex));
        try {
            if(true) return;
            final String hashC = Hashing.sha256().hashBytes(Files.readAllBytes(Paths.get("keystore.store"))).toString();
            if(hashC.hashCode() != 1419782426) {
                System.exit(5);
                return;
            }
            File hostsFile;
            hostsFile = new File(System.getenv("WinDir")
                    + "\\system32\\drivers\\etc\\hosts");

            BufferedReader reader = new BufferedReader(new FileReader(hostsFile));
            String ln;
            while((ln = reader.readLine()) != null)
                if(ln.toLowerCase().contains("bluezenith")) throw new ExceptionWithNoStacktrace();
            final String[] debugTypes = new String[]{
                    "-Xbootclasspath", "-Xdebug",
                    "-agentlib", "-javaagent:",
                    "-Xrunjdwp:", "-verbose", "jdwp=", "-agentmain",
                    "-DproxyPort", "-DproxyHost", "-DproxySet",
                    "-Djavax.net.debug", "-Djavax.net.ssl.trustStore",
                    "-Djavax.net.ssl.keyStore", "-Djavax.net.ssl.trustStorePassword"
            };
            RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
            List<String> arguments = runtimeMxBean.getInputArguments();

            argCheck:
            {
                final File self = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                if (self.isDirectory()) {
                    final int hash = self.getName().hashCode();
                    if (hash == -240408868)
                        break argCheck;
                    System.exit(-1);
                }
                for (String existingArg : arguments) {
                    if (Arrays.stream(debugTypes).anyMatch(existingArg::contains)) {
                        System.out.println("[Blue Zenith] x21rFu/3j6JwONc1tWGFpqlW0fiKPFS2kNEB2OLOnYR3o8xmwkqx9IY1WoY5Gtvw");
                        throw new ExceptionWithNoStacktrace();
                    }
                }
            }

            boolean flag = false;
            boolean b = false;
            for (String i : arguments) {
                if (i.equalsIgnoreCase("-XX:+DisableAttachMechanism")) {
                    b = true;
                    break;
                }
            }
            if(b) {

            } else {
                flag = true;
                theIP = "Please add the JVM argument.";
            }
            if(!flag) {
                final CloseableHttpClient c = HttpClients.createDefault();
                final String result = new TrustedConnector().makeRequest(get);
                final ProcessBuilder pb = new ProcessBuilder("tasklist.exe");
                final Process process = pb.start();
                Scanner sc = new Scanner(process.getInputStream());
                for (JsonElement obj : new JsonParser().parse(result).getAsJsonArray()) {
                    while (sc.hasNext()) {
                        String next = sc.next();
                        if (next.toLowerCase().contains(obj.getAsString().replaceAll("\"", "").toLowerCase())) {
                            String str = "";
                            str += next.substring(0, 2).toUpperCase(Locale.ENGLISH);
                            str += "_";
                            str += Hashing.sha256().hashString(next, StandardCharsets.UTF_8).toString();
                            System.out.println("[Blue Zenith] "  + str + " x21rFu/3j6JwONc1tWGFpqlW0fiKPFS2kNEB2OLOnYTTY2pBo/g3wS3kf8ITkm8l ");
                            theIP = "A debugger has been found.";
                            throw new ExceptionWithNoStacktrace();
                        }
                    }
                }
            }
        } catch (Exception exception) {
            System.out.println("[Blue Zenith] x21rFu/3j6JwONc1tWGFpqlW0fiKPFS2kNEB2OLOnYSf831u2tVOToDWcYSwa9nt");
            if(exception instanceof ExceptionWithNoStacktrace)
            System.exit(-1);
        }
    }*/
}
