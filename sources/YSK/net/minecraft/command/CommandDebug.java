package net.minecraft.command;

import net.minecraft.server.*;
import java.text.*;
import java.io.*;
import net.minecraft.profiler.*;
import java.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;

public class CommandDebug extends CommandBase
{
    private long field_147206_b;
    private static final Logger logger;
    private int field_147207_c;
    private static final String[] I;
    
    @Override
    public int getRequiredPermissionLevel() {
        return "   ".length();
    }
    
    private static void I() {
        (I = new String[0x87 ^ 0xBD])["".length()] = I("(*\u001b\"\u0004", "LOyWc");
        CommandDebug.I[" ".length()] = I("\u0000\u001d5\u000f\u0018\r\u0016+L\u001d\u0006\u0010-\u0005W\u0016\u00019\u0005\u001c", "crXby");
        CommandDebug.I["  ".length()] = I("9&<;04-\"x5?+$1\u007f/:014", "ZIQVQ");
        CommandDebug.I["   ".length()] = I("\u001e0\u000f\u001f\u001b", "mDnmo");
        CommandDebug.I[0x12 ^ 0x16] = I("\u001b\f$>#\u0016\u0007:}&\u001d\u0001<4l\r\u0010(4'", "xcISB");
        CommandDebug.I[0x3B ^ 0x3E] = I("\u001b,\u000e9\u0016\u0016'\u0010z\u0013\u001d!\u00163Y\u000b7\u0002&\u0003", "xCcTw");
        CommandDebug.I[0xA8 ^ 0xAE] = I("\u0011=\u001f\u0011", "bIpaH");
        CommandDebug.I[0x1F ^ 0x18] = I("\u0013>\f8+\u001e5\u0012{.\u00153\u00142d\u0005\"\u00002/", "pQaUJ");
        CommandDebug.I[0xE ^ 0x6] = I("\u0017\u0004\u000e\u0018\r\u001a\u000f\u0010[\b\u0011\t\u0016\u0012B\u0001\u0018\u0002\u0012\t", "tkcul");
        CommandDebug.I[0x27 ^ 0x2E] = I(" \u001b\u0005,2-\u0010\u001bo7&\u0016\u001d&}-\u001b\u001c\u0012'\"\u0006\u001c$7", "CthAS");
        CommandDebug.I[0xA ^ 0x0] = I("*'\u0001\t8',\u001fJ=,*\u0019\u0003w:<\u0003\u0014", "IHldY");
        CommandDebug.I[0x7E ^ 0x75] = I("*</\u0017\b", "NYMbo");
        CommandDebug.I[0x7D ^ 0x71] = I("\u0003=:\u0000\u001b\u001f*x\u0014\u0017\u0000:9\u0012\u0001^", "sOUfr");
        CommandDebug.I[0x3D ^ 0x30] = I(".1\r\u0001a\u001a\u0005Y\u001c(\b\u0000<V!:f\u0007\u000b", "WHtxL");
        CommandDebug.I[0xB ^ 0x5] = I("F\u0012\f\u0011", "hfteM");
        CommandDebug.I[0x99 ^ 0x96] = I("\".\u0017\u001e\u001cA/\r\u0006X\u0012 \u0014\u0017X\u00113\r\u0014\u0011\r$\u0010R\n\u00042\u0017\u001e\f\u0012a\u0016\u001dX", "aAbrx");
        CommandDebug.I[0x5 ^ 0x15] = I("_O^\\i?\u000b\u001d\u0014*\u0000\u0003\u0015\u0005i\"\u0010\u001c\u0017 \u001e\u0007\u0001Q\u001b\u0017\u0011\u0006\u001d=\u0001B^\\d_h", "rbsqI");
        CommandDebug.I[0x84 ^ 0x95] = I("hYJ", "Gvjxu");
        CommandDebug.I[0x80 ^ 0x92] = I("h\\", "bVlmn");
        CommandDebug.I[0x72 ^ 0x61] = I("8/\t=t\u001f6\u00056nL", "lFdXT");
        CommandDebug.I[0x2D ^ 0x39] = I("J.0_", "jCCUV");
        CommandDebug.I[0xB2 ^ 0xA7] = I("\u0001,*\u0018s&5(\u001diu", "UEIsS");
        CommandDebug.I[0x63 ^ 0x75] = I("j\u0005\u0001\u000e\u001f9{", "Jqhmt");
        CommandDebug.I[0xD7 ^ 0xC0] = I("H}x&\t\u000e!x\u001b\u0012G3(\u0002\u0013\b*1\u001f\u0000\u001374\u000bA", "gRXra");
        CommandDebug.I[0x2E ^ 0x36] = I("nyh!", "KWZGm");
        CommandDebug.I[0x1 ^ 0x18] = I("I\u0004\"3\u0018\u001aP;5\u0001I\u0003.3\u001c\u0007\u0014ep:\u001dP88\u001c\u001c\u001c/p\u0011\fP", "ipKPs");
        CommandDebug.I[0x5 ^ 0x1F] = I("k8\u0005$;8l\u001c\"\"k?\t$?%(fM", "KLlGP");
        CommandDebug.I[0x5A ^ 0x41] = I("jGDb\u0018\u0002- \fz\u00178&\u0004\u0013\u000b/I\u0006\u000f\n:Iowj`c", "GjiBZ");
        CommandDebug.I[0x3 ^ 0x1F] = I("\u00036.\u0013", "qYAgA");
        CommandDebug.I[0x88 ^ 0x95] = I("fK~j\u001c\u0005\"s\u001a\u000b\u0004 \u001a\u0006\u001ck\"\u0006\u0007\tkK~gSA", "KfSJY");
        CommandDebug.I[0x0 ^ 0x1E] = I("\u0015`rc>\u0013e", "NEBQZ");
        CommandDebug.I[0x99 ^ 0x86] = I("C", "cOPfK");
        CommandDebug.I[0x7 ^ 0x27] = I("TTa", "tyAcD");
        CommandDebug.I[0x1B ^ 0x3A] = I("U~B\u001e", "pPpxN");
        CommandDebug.I[0x24 ^ 0x6] = I("Ui", "pFdlw");
        CommandDebug.I[0x84 ^ 0xA7] = I("uKf\u0007", "PeTaI");
        CommandDebug.I[0x3A ^ 0x1E] = I("`X", "ERJgw");
        CommandDebug.I[0x25 ^ 0x0] = I("7\u0000\u0015\u0004'!\u0007\u0000\u001d'&", "BnftB");
        CommandDebug.I[0x4A ^ 0x6C] = I("z", "TOzHS");
        CommandDebug.I[0x8C ^ 0xAB] = I("9\u001cP\b6!\u0002 \u0019'-\tP", "bGpMn");
        CommandDebug.I[0x57 ^ 0x7F] = I("T\u001b\u0012", "tFOUS");
        CommandDebug.I[0x38 ^ 0x11] = I("?&\u0002,2L \u001e/)\t<\u0018c", "lNkBK");
        CommandDebug.I[0x1D ^ 0x37] = I("#\u0019i$p\f\u001b=M\"\u0017\u001a'\u0004>\u0005T/\f#\u0016T,\u0003?\u0017\u0013!RpX\\", "btImP");
        CommandDebug.I[0x4D ^ 0x66] = I("\u0006R\u0006a8 \u0007\u0000(!(U\n2o'\u0014\u0019%o.\u0006K\bo,\u0014\u0005`", "OukAO");
        CommandDebug.I[0x95 ^ 0xB9] = I("\u0012$\u001c-H\fm\u00157\r7m\u0012$H\"\"\u001f%H #\u001f4\u000f-m\u0016.\u001ae4\u001f4WewX", "EMpAh");
        CommandDebug.I[0x39 ^ 0x14] = I("8# &\u000e\u0012}e\u0019\u0005\u0004<*,\u0005\u0006r", "kSECj");
        CommandDebug.I[0x25 ^ 0xB] = I("+\"\u0019\u0004\bC0\u001a\u001a\u000b\u0007", "cGuhg");
        CommandDebug.I[0x23 ^ 0xC] = I("zS|R +\u0017-\u00170n\u00171\u0013,n\u0002y\u00110/\u00101R0+\u00136\u00006`", "NcYrB");
        CommandDebug.I[0x10 ^ 0x20] = I("8\u000e\u0003g2\u001f\u0015\u001cg \u000e\u0015\u0006&e\u0018\u0014\u0019% \u0004\u0012", "vatGE");
        CommandDebug.I[0x25 ^ 0x14] = I(",(<o9\u000b3#o\"\u000748o \u0017*)*<\u0011", "bGKON");
        CommandDebug.I[0x39 ^ 0xB] = I("\n\n\u0019h\u0015-\u0011\u0006h\u0016,\u0000N;\u0003)\u0000N&\u0017)\u0007\u000b:\u0011", "DenHb");
        CommandDebug.I[0xF ^ 0x3C] = I("0\u0001$J4\u0001\u0001$\u0006#I\u000f5\u000eg\u000f\u00020\u0007\"\u001aN%\u0005g\u001d\u00068\u0004 \u001aBq\u00033I\u00030\u0001\"\u001aN%\u0002\"\u0004N6\u0005g\u000f\u000f\"\u001e\"\u001bO", "inQjG");
        CommandDebug.I[0x93 ^ 0xA7] = I("\u0013+J0\"\"d\f,(;d\u001e!(w*\u000f,)w\"\u0005;cyjJ&=#-\u0007 760\u0003&#h", "WDjIM");
        CommandDebug.I[0x6E ^ 0x5B] = I("h! \n!)1r\u0019'&1&\u0004,'b%\u0003+2h", "BBRkB");
        CommandDebug.I[0x64 ^ 0x52] = I("\u000b4\u0003:\u000ef<\u001cx\u0012) Z,\u0019#4\u000e=\u000ff<\u000ex\t#!\u000e=\u0019f!\u0012=\u0005f<\u000e\u007f\u0007*u\u00129\u001d#u\u00177\u0019#u\u00177\u001f/#\u001b,\u0002);Z,\u0004f\"\u0015*\u0000f3\u001b+\u001f#'[x;):\bx\u0018#'\f=\u0019h", "FUzXk");
        CommandDebug.I[0xA8 ^ 0x9F] = I("\u0007\f\u001e\u0002 p\u0006\u0005\u001b45\u000b\u001eV,>\u0004\u001c\u00170<\u0004\b\u001a<p_B", "PejvY");
        CommandDebug.I[0xD ^ 0x35] = I("<6\u0012&>", "OBsTJ");
        CommandDebug.I[0x22 ^ 0x1B] = I("6\u0002\u001d&", "EvrVM");
    }
    
    private void func_147205_a(final long n, final int n2) {
        final File file = new File(MinecraftServer.getServer().getFile(CommandDebug.I[0x3C ^ 0x37]), CommandDebug.I[0xCD ^ 0xC1] + new SimpleDateFormat(CommandDebug.I[0x3C ^ 0x31]).format(new Date()) + CommandDebug.I[0xA1 ^ 0xAF]);
        file.getParentFile().mkdirs();
        try {
            final FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(this.func_147204_b(n, n2));
            fileWriter.close();
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        catch (Throwable t) {
            CommandDebug.logger.error(CommandDebug.I[0x69 ^ 0x66] + file, t);
        }
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandDebug.I[" ".length()];
    }
    
    private void func_147202_a(final int n, final String s, final StringBuilder sb) {
        final List profilingData = MinecraftServer.getServer().theProfiler.getProfilingData(s);
        if (profilingData != null && profilingData.size() >= "   ".length()) {
            int i = " ".length();
            "".length();
            if (4 == 3) {
                throw null;
            }
            while (i < profilingData.size()) {
                final Profiler.Result result = profilingData.get(i);
                final String s2 = CommandDebug.I[0x96 ^ 0x88];
                final Object[] array = new Object[" ".length()];
                array["".length()] = n;
                sb.append(String.format(s2, array));
                int j = "".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
                while (j < n) {
                    sb.append(CommandDebug.I[0x4D ^ 0x52]);
                    ++j;
                }
                final StringBuilder append = sb.append(result.field_76331_c).append(CommandDebug.I[0x30 ^ 0x10]);
                final String s3 = CommandDebug.I[0x83 ^ 0xA2];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = result.field_76332_a;
                final StringBuilder append2 = append.append(String.format(s3, array2)).append(CommandDebug.I[0xD ^ 0x2F]);
                final String s4 = CommandDebug.I[0x91 ^ 0xB2];
                final Object[] array3 = new Object[" ".length()];
                array3["".length()] = result.field_76330_b;
                append2.append(String.format(s4, array3)).append(CommandDebug.I[0xBB ^ 0x9F]);
                if (!result.field_76331_c.equals(CommandDebug.I[0x56 ^ 0x73])) {
                    try {
                        this.func_147202_a(n + " ".length(), String.valueOf(s) + CommandDebug.I[0xBB ^ 0x9D] + result.field_76331_c, sb);
                        "".length();
                        if (3 <= 1) {
                            throw null;
                        }
                    }
                    catch (Exception ex) {
                        sb.append(CommandDebug.I[0x78 ^ 0x5F]).append(ex).append(CommandDebug.I[0x89 ^ 0xA1]);
                    }
                }
                ++i;
            }
        }
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    private String func_147204_b(final long n, final int n2) {
        final StringBuilder sb = new StringBuilder();
        sb.append(CommandDebug.I[0x5C ^ 0x4C]);
        sb.append(CommandDebug.I[0xAF ^ 0xBE]);
        sb.append(func_147203_d());
        sb.append(CommandDebug.I[0x6D ^ 0x7F]);
        sb.append(CommandDebug.I[0x3 ^ 0x10]).append(n).append(CommandDebug.I[0x9E ^ 0x8A]);
        sb.append(CommandDebug.I[0xBE ^ 0xAB]).append(n2).append(CommandDebug.I[0x95 ^ 0x83]);
        final StringBuilder append = sb.append(CommandDebug.I[0x56 ^ 0x41]);
        final String s = CommandDebug.I[0x37 ^ 0x2F];
        final Object[] array = new Object[" ".length()];
        array["".length()] = n2 / (n / 1000.0f);
        append.append(String.format(s, array)).append(CommandDebug.I[0xB7 ^ 0xAE]).append(0x8C ^ 0x98).append(CommandDebug.I[0x73 ^ 0x69]);
        sb.append(CommandDebug.I[0x5 ^ 0x1E]);
        this.func_147202_a("".length(), CommandDebug.I[0x37 ^ 0x2B], sb);
        sb.append(CommandDebug.I[0x1B ^ 0x6]);
        return sb.toString();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> listOfStringsMatchingLastWord;
        if (array.length == " ".length()) {
            final String[] array2 = new String["  ".length()];
            array2["".length()] = CommandDebug.I[0x3B ^ 0x3];
            array2[" ".length()] = CommandDebug.I[0x1E ^ 0x27];
            listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, array2);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            listOfStringsMatchingLastWord = null;
        }
        return listOfStringsMatchingLastWord;
    }
    
    private static String func_147203_d() {
        final String[] array = new String[0x53 ^ 0x5D];
        array["".length()] = CommandDebug.I[0x3B ^ 0x12];
        array[" ".length()] = CommandDebug.I[0x28 ^ 0x2];
        array["  ".length()] = CommandDebug.I[0x9E ^ 0xB5];
        array["   ".length()] = CommandDebug.I[0x20 ^ 0xC];
        array[0x28 ^ 0x2C] = CommandDebug.I[0x7B ^ 0x56];
        array[0x18 ^ 0x1D] = CommandDebug.I[0x26 ^ 0x8];
        array[0x9D ^ 0x9B] = CommandDebug.I[0xBA ^ 0x95];
        array[0x1E ^ 0x19] = CommandDebug.I[0x87 ^ 0xB7];
        array[0x33 ^ 0x3B] = CommandDebug.I[0x56 ^ 0x67];
        array[0x23 ^ 0x2A] = CommandDebug.I[0x3B ^ 0x9];
        array[0x74 ^ 0x7E] = CommandDebug.I[0xF6 ^ 0xC5];
        array[0x7E ^ 0x75] = CommandDebug.I[0x59 ^ 0x6D];
        array[0x67 ^ 0x6B] = CommandDebug.I[0x1E ^ 0x2B];
        array[0x34 ^ 0x39] = CommandDebug.I[0x16 ^ 0x20];
        final String[] array2 = array;
        try {
            return array2[(int)(System.nanoTime() % array2.length)];
        }
        catch (Throwable t) {
            return CommandDebug.I[0x68 ^ 0x5F];
        }
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < " ".length()) {
            throw new WrongUsageException(CommandDebug.I["  ".length()], new Object["".length()]);
        }
        if (array["".length()].equals(CommandDebug.I["   ".length()])) {
            if (array.length != " ".length()) {
                throw new WrongUsageException(CommandDebug.I[0x87 ^ 0x83], new Object["".length()]);
            }
            CommandBase.notifyOperators(commandSender, this, CommandDebug.I[0x55 ^ 0x50], new Object["".length()]);
            MinecraftServer.getServer().enableProfiling();
            this.field_147206_b = MinecraftServer.getCurrentTimeMillis();
            this.field_147207_c = MinecraftServer.getServer().getTickCounter();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            if (!array["".length()].equals(CommandDebug.I[0xBD ^ 0xBB])) {
                throw new WrongUsageException(CommandDebug.I[0x99 ^ 0x9E], new Object["".length()]);
            }
            if (array.length != " ".length()) {
                throw new WrongUsageException(CommandDebug.I[0x8F ^ 0x87], new Object["".length()]);
            }
            if (!MinecraftServer.getServer().theProfiler.profilingEnabled) {
                throw new CommandException(CommandDebug.I[0xB8 ^ 0xB1], new Object["".length()]);
            }
            final long currentTimeMillis = MinecraftServer.getCurrentTimeMillis();
            final int tickCounter = MinecraftServer.getServer().getTickCounter();
            final long n = currentTimeMillis - this.field_147206_b;
            final int n2 = tickCounter - this.field_147207_c;
            this.func_147205_a(n, n2);
            MinecraftServer.getServer().theProfiler.profilingEnabled = ("".length() != 0);
            final String s = CommandDebug.I[0x34 ^ 0x3E];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = n / 1000.0f;
            array2[" ".length()] = n2;
            CommandBase.notifyOperators(commandSender, this, s, array2);
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getCommandName() {
        return CommandDebug.I["".length()];
    }
}
