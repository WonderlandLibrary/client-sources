package net.minecraft.nbt;

import java.util.regex.*;
import org.apache.logging.log4j.*;
import java.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class JsonToNBT
{
    private static final Logger logger;
    private static final Pattern field_179273_b;
    private static final String[] I;
    
    static Any func_150316_a(final String s, String s2) throws NBTException {
        s2 = s2.trim();
        if (s2.startsWith(JsonToNBT.I[0x62 ^ 0x68])) {
            s2 = s2.substring(" ".length(), s2.length() - " ".length());
            final Compound compound = new Compound(s);
            "".length();
            if (0 >= 2) {
                throw null;
            }
            while (s2.length() > 0) {
                final String func_150314_a = func_150314_a(s2, " ".length() != 0);
                if (func_150314_a.length() > 0) {
                    compound.field_150491_b.add(func_179270_a(func_150314_a, (boolean)("".length() != 0)));
                }
                if (s2.length() < func_150314_a.length() + " ".length()) {
                    "".length();
                    if (0 == 4) {
                        throw null;
                    }
                    break;
                }
                else {
                    final char char1 = s2.charAt(func_150314_a.length());
                    if (char1 != (0xA4 ^ 0x88) && char1 != (0xC3 ^ 0xB8) && char1 != (0x37 ^ 0x4A) && char1 != (0x74 ^ 0x2F) && char1 != (0x2C ^ 0x71)) {
                        throw new NBTException(JsonToNBT.I[0x17 ^ 0x1C] + char1 + JsonToNBT.I[0x63 ^ 0x6F] + s2.substring(func_150314_a.length()));
                    }
                    s2 = s2.substring(func_150314_a.length() + " ".length());
                }
            }
            return compound;
        }
        else {
            if (!s2.startsWith(JsonToNBT.I[0x11 ^ 0x1C]) || JsonToNBT.field_179273_b.matcher(s2).matches()) {
                return new Primitive(s, s2);
            }
            s2 = s2.substring(" ".length(), s2.length() - " ".length());
            final List list = new List(s);
            "".length();
            if (true != true) {
                throw null;
            }
            while (s2.length() > 0) {
                final String func_150314_a2 = func_150314_a(s2, "".length() != 0);
                if (func_150314_a2.length() > 0) {
                    list.field_150492_b.add(func_179270_a(func_150314_a2, (boolean)(" ".length() != 0)));
                }
                if (s2.length() < func_150314_a2.length() + " ".length()) {
                    "".length();
                    if (1 <= -1) {
                        throw null;
                    }
                    break;
                }
                else {
                    final char char2 = s2.charAt(func_150314_a2.length());
                    if (char2 != (0x40 ^ 0x6C) && char2 != (0xFD ^ 0x86) && char2 != (0x2D ^ 0x50) && char2 != (0x42 ^ 0x19) && char2 != (0x1F ^ 0x42)) {
                        throw new NBTException(JsonToNBT.I[0x7 ^ 0x9] + char2 + JsonToNBT.I[0x2C ^ 0x23] + s2.substring(func_150314_a2.length()));
                    }
                    s2 = s2.substring(func_150314_a2.length() + " ".length());
                }
            }
            return list;
        }
    }
    
    public static NBTTagCompound getTagFromJson(String trim) throws NBTException {
        trim = trim.trim();
        if (!trim.startsWith(JsonToNBT.I[" ".length()])) {
            throw new NBTException(JsonToNBT.I["  ".length()]);
        }
        if (func_150310_b(trim) != " ".length()) {
            throw new NBTException(JsonToNBT.I["   ".length()]);
        }
        return (NBTTagCompound)func_150316_a(JsonToNBT.I[0xD ^ 0x9], trim).parse();
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static int func_150310_b(final String s) throws NBTException {
        int n = "".length();
        int length = "".length();
        final Stack<Character> stack = new Stack<Character>();
        int i = "".length();
        "".length();
        if (0 == 4) {
            throw null;
        }
        while (i < s.length()) {
            final char char1 = s.charAt(i);
            if (char1 == (0x65 ^ 0x47)) {
                if (func_179271_b(s, i)) {
                    if (length == 0) {
                        throw new NBTException(JsonToNBT.I[0x88 ^ 0x8D] + s);
                    }
                }
                else {
                    int n2;
                    if (length != 0) {
                        n2 = "".length();
                        "".length();
                        if (false) {
                            throw null;
                        }
                    }
                    else {
                        n2 = " ".length();
                    }
                    length = n2;
                    "".length();
                    if (1 <= -1) {
                        throw null;
                    }
                }
            }
            else if (length == 0) {
                if (char1 != (0xD4 ^ 0xAF) && char1 != (0xF1 ^ 0xAA)) {
                    if (char1 == (0xBE ^ 0xC3) && (stack.isEmpty() || stack.pop() != (0xEC ^ 0x97))) {
                        throw new NBTException(JsonToNBT.I[0xA9 ^ 0xAF] + s);
                    }
                    if (char1 == (0xF2 ^ 0xAF) && (stack.isEmpty() || stack.pop() != (0x43 ^ 0x18))) {
                        throw new NBTException(JsonToNBT.I[0x79 ^ 0x7E] + s);
                    }
                }
                else {
                    if (stack.isEmpty()) {
                        ++n;
                    }
                    stack.push(char1);
                }
            }
            ++i;
        }
        if (length != 0) {
            throw new NBTException(JsonToNBT.I[0x11 ^ 0x19] + s);
        }
        if (!stack.isEmpty()) {
            throw new NBTException(JsonToNBT.I[0x8B ^ 0x82] + s);
        }
        if (n == 0 && !s.isEmpty()) {
            n = " ".length();
        }
        return n;
    }
    
    private static String func_150311_c(String trim, final boolean b) throws NBTException {
        if (b) {
            trim = trim.trim();
            if (trim.startsWith(JsonToNBT.I[0xA3 ^ 0xB9]) || trim.startsWith(JsonToNBT.I[0x7C ^ 0x67])) {
                return trim;
            }
        }
        final int func_150312_a = func_150312_a(trim, (char)(0x31 ^ 0xB));
        if (func_150312_a != -" ".length()) {
            return trim.substring(func_150312_a + " ".length()).trim();
        }
        if (b) {
            return trim;
        }
        throw new NBTException(JsonToNBT.I[0x81 ^ 0x9D] + trim);
    }
    
    private static String func_150314_a(final String s, final boolean b) throws NBTException {
        int func_150312_a = func_150312_a(s, (char)(0x8B ^ 0xB1));
        final int func_150312_a2 = func_150312_a(s, (char)(0x19 ^ 0x35));
        if (b) {
            if (func_150312_a == -" ".length()) {
                throw new NBTException(JsonToNBT.I[0x4B ^ 0x5B] + s);
            }
            if (func_150312_a2 != -" ".length() && func_150312_a2 < func_150312_a) {
                throw new NBTException(JsonToNBT.I[0x61 ^ 0x70] + s);
            }
        }
        else if (func_150312_a == -" ".length() || func_150312_a > func_150312_a2) {
            func_150312_a = -" ".length();
        }
        return func_179269_a(s, func_150312_a);
    }
    
    private static String func_179269_a(final String s, final int n) throws NBTException {
        final Stack<Character> stack = new Stack<Character>();
        int i = n + " ".length();
        int length = "".length();
        int n2 = "".length();
        int n3 = "".length();
        int length2 = "".length();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (i < s.length()) {
            final char char1 = s.charAt(i);
            if (char1 == (0x3B ^ 0x19)) {
                if (func_179271_b(s, i)) {
                    if (length == 0) {
                        throw new NBTException(JsonToNBT.I[0x80 ^ 0x92] + s);
                    }
                }
                else {
                    int n4;
                    if (length != 0) {
                        n4 = "".length();
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                    }
                    else {
                        n4 = " ".length();
                    }
                    length = n4;
                    if (length != 0 && n3 == 0) {
                        n2 = " ".length();
                    }
                    if (length == 0) {
                        length2 = i;
                        "".length();
                        if (1 <= -1) {
                            throw null;
                        }
                    }
                }
            }
            else if (length == 0) {
                if (char1 != (0xE1 ^ 0x9A) && char1 != (0x68 ^ 0x33)) {
                    if (char1 == (0x3A ^ 0x47) && (stack.isEmpty() || stack.pop() != (0x19 ^ 0x62))) {
                        throw new NBTException(JsonToNBT.I[0xA7 ^ 0xB4] + s);
                    }
                    if (char1 == (0xF8 ^ 0xA5) && (stack.isEmpty() || stack.pop() != (0x54 ^ 0xF))) {
                        throw new NBTException(JsonToNBT.I[0x87 ^ 0x93] + s);
                    }
                    if (char1 == (0x8B ^ 0xA7) && stack.isEmpty()) {
                        return s.substring("".length(), i);
                    }
                }
                else {
                    stack.push(char1);
                }
            }
            if (!Character.isWhitespace(char1)) {
                if (length == 0 && n2 != 0 && length2 != i) {
                    return s.substring("".length(), length2 + " ".length());
                }
                n3 = " ".length();
            }
            ++i;
        }
        return s.substring("".length(), i);
    }
    
    private static boolean func_179271_b(final String s, final int n) {
        if (n > 0 && s.charAt(n - " ".length()) == (0x67 ^ 0x3B) && !func_179271_b(s, n - " ".length())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        field_179273_b = Pattern.compile(JsonToNBT.I["".length()]);
    }
    
    private static String func_150313_b(String trim, final boolean b) throws NBTException {
        if (b) {
            trim = trim.trim();
            if (trim.startsWith(JsonToNBT.I[0xA3 ^ 0xB6]) || trim.startsWith(JsonToNBT.I[0x63 ^ 0x75])) {
                return JsonToNBT.I[0x60 ^ 0x77];
            }
        }
        final int func_150312_a = func_150312_a(trim, (char)(0x32 ^ 0x8));
        if (func_150312_a != -" ".length()) {
            return trim.substring("".length(), func_150312_a).trim();
        }
        if (b) {
            return JsonToNBT.I[0x31 ^ 0x29];
        }
        throw new NBTException(JsonToNBT.I[0x5 ^ 0x1C] + trim);
    }
    
    private static Any func_179270_a(final String s, final boolean b) throws NBTException {
        final String func_150313_b = func_150313_b(s, b);
        final String func_150311_c = func_150311_c(s, b);
        final String[] array = new String["  ".length()];
        array["".length()] = func_150313_b;
        array[" ".length()] = func_150311_c;
        return func_179272_a(array);
    }
    
    private static int func_150312_a(final String s, final char c) {
        int i = "".length();
        int length = " ".length();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (i < s.length()) {
            final char char1 = s.charAt(i);
            if (char1 == (0x2D ^ 0xF)) {
                if (!func_179271_b(s, i)) {
                    int n;
                    if (length != 0) {
                        n = "".length();
                        "".length();
                        if (4 <= 0) {
                            throw null;
                        }
                    }
                    else {
                        n = " ".length();
                    }
                    length = n;
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                }
            }
            else if (length != 0) {
                if (char1 == c) {
                    return i;
                }
                if (char1 == (0xBD ^ 0xC6) || char1 == (0xDA ^ 0x81)) {
                    return -" ".length();
                }
            }
            ++i;
        }
        return -" ".length();
    }
    
    private static void I() {
        (I = new String[0x45 ^ 0x58])["".length()] = I("\b71Xc\b\b\u0016Y\u0014'1A)\u0015", "TljuH");
        JsonToNBT.I[" ".length()] = I("/", "TNwPK");
        JsonToNBT.I["  ".length()] = I("\u0004> \" $4v7-*p3-/\"%87)?52ol((&&/952ck6wv\"?m6?1?9p5+-?~", "MPVCL");
        JsonToNBT.I["   ".length()] = I("\u0000\u001c'\r;+\u0006!\u0010+!R)\u0017\"1\u001b4\u000e+e\u0006+\u0012n1\u0013#\u0011be\u001d*\u000e7e\u001d*\u0007n \n4\u0007-1\u0017 ", "ErDbN");
        JsonToNBT.I[0x6 ^ 0x2] = I("\u0010\"\u0001", "dCfYd");
        JsonToNBT.I[0x20 ^ 0x25] = I("\u0019\u001b\r7\u00011\u001bA'\u00155W\u000e4F\fU[r", "PwaRf");
        JsonToNBT.I[0xBD ^ 0xBB] = I("%\u001b\u0012\u0002*\u0011\u001b\u0013\u0006\"P\u0016\u0005\u0011*\tU\u0012\u0011'\u0013\u001e\u0015\u00175P\u000e\rYf", "pupcF");
        JsonToNBT.I[0x6B ^ 0x6C] = I("\u0004=\t*\r0=\b.\u0005q \u001a>\u0000#6K)\u001300\u0000.\u0015\"s0\u0016[q", "QSkKa");
        JsonToNBT.I[0x9B ^ 0x93] = I("\u0001\u001c3\u0010\u00055\u001c2\u0014\rt\u0003$\u001e\u001d5\u00068\u001e\u0007nR", "TrQqi");
        JsonToNBT.I[0x95 ^ 0x9C] = I("=\u0001\u0014)\n\t\u0001\u0015-\u0002H\r\u0004)\u0005\u0003\n\u0002;\\H", "hovHf");
        JsonToNBT.I[0xA0 ^ 0xAA] = I(",", "WiKXw");
        JsonToNBT.I[0x39 ^ 0x32] = I("\u0016=5\f(&0$\u0011<c'?\u001f=-sw", "CSPtX");
        JsonToNBT.I[0x4A ^ 0x46] = I("UZ \u0017oR", "rzAcU");
        JsonToNBT.I[0x75 ^ 0x78] = I("\u0017", "LPJGD");
        JsonToNBT.I[0x60 ^ 0x6E] = I("\u0011\r5\u00158!\u0000$\b,d\u0017?\u0006-*Cw", "DcPmH");
        JsonToNBT.I[0x9D ^ 0x92] = I("QM\f\u0018}V", "vmmlG");
        JsonToNBT.I[0x81 ^ 0x91] = I("\u001497.\u001d$w\"#Q-85-\u0005$w8-\u001c$x -\u001d42v?\u001416$-\u0005.%v*\u001e3w%8\u0003(91vQ", "AWVLq");
        JsonToNBT.I[0x14 ^ 0x5] = I("%\b\u001e6U\u000e\u001b\u0001<\u0007K\b\u0007iU", "kisSu");
        JsonToNBT.I[0x4 ^ 0x16] = I("\u0006\r\u0005\u000b$.\rI\u001b0*A\u0006\bc\u0013CSN", "OainC");
        JsonToNBT.I[0x43 ^ 0x50] = I("\u00046&\u0013806'\u00170q;1\u00008(x&\u0000523!\u0006'q#9Ht", "QXDrT");
        JsonToNBT.I[0x22 ^ 0x36] = I("\u0010&\u00045\u0001$&\u00051\te;\u0017!\f7-F6\u001f$+\r1\u00196h=\tWe", "EHfTm");
        JsonToNBT.I[0x8F ^ 0x9A] = I("+", "Psokq");
        JsonToNBT.I[0x78 ^ 0x6E] = I("\n", "QweEn");
        JsonToNBT.I[0x6E ^ 0x79] = I("", "MOYNL");
        JsonToNBT.I[0x61 ^ 0x79] = I("", "HRUiE");
        JsonToNBT.I[0x46 ^ 0x5F] = I("\u0019)\u00040\n)g\u0011=F (\u00063\u0012)g\u000b3\u000b)h\u00133\n9\"E!\u0003<&\u00173\u0012#5E4\t>g\u0016&\u0014%)\u0002hF", "LGeRf");
        JsonToNBT.I[0x68 ^ 0x72] = I("\r", "vUbUj");
        JsonToNBT.I[0x5D ^ 0x46] = I("\u0014", "OOJzQ");
        JsonToNBT.I[0x98 ^ 0x84] = I("-\u001d\u000f-\u0007\u001dS\u001a K\u0014\u001c\r.\u001f\u001dS\u0000.\u0006\u001d\\\u0018.\u0007\r\u0016N<\u000e\b\u0012\u001c.\u001f\u0017\u0001N)\u0004\nS\u001d;\u0019\u0011\u001d\tuK", "xsnOk");
    }
    
    static Any func_179272_a(final String... array) throws NBTException {
        return func_150316_a(array["".length()], array[" ".length()]);
    }
    
    static class List extends Any
    {
        protected java.util.List<Any> field_150492_b;
        
        public List(final String json) {
            this.field_150492_b = (java.util.List<Any>)Lists.newArrayList();
            this.json = json;
        }
        
        @Override
        public NBTBase parse() throws NBTException {
            final NBTTagList list = new NBTTagList();
            final Iterator<Any> iterator = this.field_150492_b.iterator();
            "".length();
            if (0 >= 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                list.appendTag(iterator.next().parse());
            }
            return list;
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
                if (-1 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    abstract static class Any
    {
        protected String json;
        
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
                if (0 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public abstract NBTBase parse() throws NBTException;
    }
    
    static class Compound extends Any
    {
        protected java.util.List<Any> field_150491_b;
        
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
                if (-1 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public NBTBase parse() throws NBTException {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            final Iterator<Any> iterator = this.field_150491_b.iterator();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Any any = iterator.next();
                nbtTagCompound.setTag(any.json, any.parse());
            }
            return nbtTagCompound;
        }
        
        public Compound(final String json) {
            this.field_150491_b = (java.util.List<Any>)Lists.newArrayList();
            this.json = json;
        }
    }
    
    static class Primitive extends Any
    {
        private static final Pattern DOUBLE;
        private static final Splitter SPLITTER;
        private static final Pattern LONG;
        private static final Pattern INTEGER;
        private static final Pattern SHORT;
        private static final Pattern BYTE;
        private static final String[] I;
        protected String jsonValue;
        private static final Pattern DOUBLE_UNTYPED;
        private static final Pattern FLOAT;
        
        private static void I() {
            (I = new String[0xD3 ^ 0xC2])["".length()] = I("?}Z\u0015H?`\\q*N\f_w,T}H\u0015\\?4\r\f*", "dPqHw");
            Primitive.I[" ".length()] = I("<O~\rx<Rxi\u001aM>{o\u001cWOl\rl<\u0004)\u0016\u001a", "gbUPG");
            Primitive.I["  ".length()] = I("\u0010|Y>z\u0010a_Z\u0018`\n\u0010\u001f\u0007\u0016", "KQrcE");
            Primitive.I["   ".length()] = I("\u0011}m\ne\u0011`kn\u0007a\u000b*+\u0016\u0017", "JPFWZ");
            Primitive.I[0x6B ^ 0x6F] = I("<zA>{<gGZ\u0019L\f\u0019\u001f\u0017:", "gWjcD");
            Primitive.I[0x11 ^ 0x14] = I("\u0014EB4s\u0014XDP\u0011d", "OhiiL");
            Primitive.I[0x1A ^ 0x1C] = I("?\u007fE<E?bCX'N\u000e@^!T\u007fW<Q", "dRnaz");
            Primitive.I[0x5B ^ 0x5C] = I("\u0006\u001e\u001c\u0013", "rlivu");
            Primitive.I[0x64 ^ 0x6C] = I("\"\u0015:;\u0003", "DtVHf");
            Primitive.I[0x86 ^ 0x8F] = I("\u0005/[", "YsySa");
            Primitive.I[0x46 ^ 0x4C] = I("l", "NAuVI");
            Primitive.I[0x61 ^ 0x6A] = I("\u000f", "TFefE");
            Primitive.I[0x62 ^ 0x6E] = I("\u0005", "XTftk");
            Primitive.I[0x75 ^ 0x78] = I("W", "udyJM");
            Primitive.I[0x8F ^ 0x81] = I("O", "mvsDY");
            Primitive.I[0x3F ^ 0x30] = I("\n:G", "VfecS");
            Primitive.I[0x13 ^ 0x3] = I("x", "ZjkRR");
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
                if (4 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Primitive(final String json, final String jsonValue) {
            this.json = json;
            this.jsonValue = jsonValue;
        }
        
        static {
            I();
            DOUBLE = Pattern.compile(Primitive.I["".length()]);
            FLOAT = Pattern.compile(Primitive.I[" ".length()]);
            BYTE = Pattern.compile(Primitive.I["  ".length()]);
            LONG = Pattern.compile(Primitive.I["   ".length()]);
            SHORT = Pattern.compile(Primitive.I[0x9C ^ 0x98]);
            INTEGER = Pattern.compile(Primitive.I[0x6D ^ 0x68]);
            DOUBLE_UNTYPED = Pattern.compile(Primitive.I[0x6E ^ 0x68]);
            SPLITTER = Splitter.on((char)(0x65 ^ 0x49)).omitEmptyStrings();
        }
        
        @Override
        public NBTBase parse() throws NBTException {
            try {
                if (Primitive.DOUBLE.matcher(this.jsonValue).matches()) {
                    return new NBTTagDouble(Double.parseDouble(this.jsonValue.substring("".length(), this.jsonValue.length() - " ".length())));
                }
                if (Primitive.FLOAT.matcher(this.jsonValue).matches()) {
                    return new NBTTagFloat(Float.parseFloat(this.jsonValue.substring("".length(), this.jsonValue.length() - " ".length())));
                }
                if (Primitive.BYTE.matcher(this.jsonValue).matches()) {
                    return new NBTTagByte(Byte.parseByte(this.jsonValue.substring("".length(), this.jsonValue.length() - " ".length())));
                }
                if (Primitive.LONG.matcher(this.jsonValue).matches()) {
                    return new NBTTagLong(Long.parseLong(this.jsonValue.substring("".length(), this.jsonValue.length() - " ".length())));
                }
                if (Primitive.SHORT.matcher(this.jsonValue).matches()) {
                    return new NBTTagShort(Short.parseShort(this.jsonValue.substring("".length(), this.jsonValue.length() - " ".length())));
                }
                if (Primitive.INTEGER.matcher(this.jsonValue).matches()) {
                    return new NBTTagInt(Integer.parseInt(this.jsonValue));
                }
                if (Primitive.DOUBLE_UNTYPED.matcher(this.jsonValue).matches()) {
                    return new NBTTagDouble(Double.parseDouble(this.jsonValue));
                }
                if (this.jsonValue.equalsIgnoreCase(Primitive.I[0xB9 ^ 0xBE]) || this.jsonValue.equalsIgnoreCase(Primitive.I[0x7D ^ 0x75])) {
                    int n;
                    if (Boolean.parseBoolean(this.jsonValue)) {
                        n = " ".length();
                        "".length();
                        if (3 == 0) {
                            throw null;
                        }
                    }
                    else {
                        n = "".length();
                    }
                    return new NBTTagByte((byte)n);
                }
            }
            catch (NumberFormatException ex) {
                this.jsonValue = this.jsonValue.replaceAll(Primitive.I[0xCE ^ 0xC7], Primitive.I[0x9C ^ 0x96]);
                return new NBTTagString(this.jsonValue);
            }
            if (this.jsonValue.startsWith(Primitive.I[0xBF ^ 0xB4]) && this.jsonValue.endsWith(Primitive.I[0x62 ^ 0x6E])) {
                final String[] array = (String[])Iterables.toArray(Primitive.SPLITTER.split((CharSequence)this.jsonValue.substring(" ".length(), this.jsonValue.length() - " ".length())), (Class)String.class);
                try {
                    final int[] array2 = new int[array.length];
                    int i = "".length();
                    "".length();
                    if (2 < 1) {
                        throw null;
                    }
                    while (i < array.length) {
                        array2[i] = Integer.parseInt(array[i].trim());
                        ++i;
                    }
                    return new NBTTagIntArray(array2);
                }
                catch (NumberFormatException ex2) {
                    return new NBTTagString(this.jsonValue);
                }
            }
            if (this.jsonValue.startsWith(Primitive.I[0xCC ^ 0xC1]) && this.jsonValue.endsWith(Primitive.I[0xAC ^ 0xA2])) {
                this.jsonValue = this.jsonValue.substring(" ".length(), this.jsonValue.length() - " ".length());
            }
            this.jsonValue = this.jsonValue.replaceAll(Primitive.I[0xAB ^ 0xA4], Primitive.I[0x2F ^ 0x3F]);
            final StringBuilder sb = new StringBuilder();
            int j = "".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
            while (j < this.jsonValue.length()) {
                if (j < this.jsonValue.length() - " ".length() && this.jsonValue.charAt(j) == (0x62 ^ 0x3E) && this.jsonValue.charAt(j + " ".length()) == (0x99 ^ 0xC5)) {
                    sb.append((char)(0xC3 ^ 0x9F));
                    ++j;
                    "".length();
                    if (4 < 3) {
                        throw null;
                    }
                }
                else {
                    sb.append(this.jsonValue.charAt(j));
                }
                ++j;
            }
            return new NBTTagString(sb.toString());
        }
    }
}
