package org.spongepowered.asm.lib;

class Frame
{
    static final int DIM = -268435456;
    static final int ARRAY_OF = 268435456;
    static final int ELEMENT_OF = -268435456;
    static final int KIND = 251658240;
    static final int TOP_IF_LONG_OR_DOUBLE = 8388608;
    static final int VALUE = 8388607;
    static final int BASE_KIND = 267386880;
    static final int BASE_VALUE = 1048575;
    static final int BASE = 16777216;
    static final int OBJECT = 24117248;
    static final int UNINITIALIZED = 25165824;
    private static final int LOCAL = 33554432;
    private static final int STACK = 50331648;
    static final int TOP = 16777216;
    static final int BOOLEAN = 16777225;
    static final int BYTE = 16777226;
    static final int CHAR = 16777227;
    static final int SHORT = 16777228;
    static final int INTEGER = 16777217;
    static final int FLOAT = 16777218;
    static final int DOUBLE = 16777219;
    static final int LONG = 16777220;
    static final int NULL = 16777221;
    static final int UNINITIALIZED_THIS = 16777222;
    static final int[] SIZE;
    Label owner;
    int[] inputLocals;
    int[] inputStack;
    private int[] outputLocals;
    private int[] outputStack;
    int outputStackTop;
    private int initializationCount;
    private int[] initializations;
    
    Frame() {
        super();
    }
    
    final void set(final ClassWriter classWriter, final int n, final Object[] array, final int n2, final Object[] array2) {
        for (int i = convert(classWriter, n, array, this.inputLocals); i < array.length; this.inputLocals[i++] = 16777216) {}
        int n3 = 0;
        for (int j = 0; j < n2; ++j) {
            if (array2[j] == Opcodes.LONG || array2[j] == Opcodes.DOUBLE) {
                ++n3;
            }
        }
        convert(classWriter, n2, array2, this.inputStack = new int[n2 + n3]);
        this.outputStackTop = 0;
        this.initializationCount = 0;
    }
    
    private static int convert(final ClassWriter classWriter, final int n, final Object[] array, final int[] array2) {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            if (array[i] instanceof Integer) {
                array2[n2++] = (0x1000000 | (int)array[i]);
                if (array[i] == Opcodes.LONG || array[i] == Opcodes.DOUBLE) {
                    array2[n2++] = 16777216;
                }
            }
            else if (array[i] instanceof String) {
                array2[n2++] = type(classWriter, Type.getObjectType((String)array[i]).getDescriptor());
            }
            else {
                array2[n2++] = (0x1800000 | classWriter.addUninitializedType("", ((Label)array[i]).position));
            }
        }
        return n2;
    }
    
    final void set(final Frame frame) {
        this.inputLocals = frame.inputLocals;
        this.inputStack = frame.inputStack;
        this.outputLocals = frame.outputLocals;
        this.outputStack = frame.outputStack;
        this.outputStackTop = frame.outputStackTop;
        this.initializationCount = frame.initializationCount;
        this.initializations = frame.initializations;
    }
    
    private int get(final int n) {
        if (this.outputLocals == null || n >= this.outputLocals.length) {
            return 0x2000000 | n;
        }
        int n2 = this.outputLocals[n];
        if (n2 == 0) {
            final int[] outputLocals = this.outputLocals;
            final int n3 = 0x2000000 | n;
            outputLocals[n] = n3;
            n2 = n3;
        }
        return n2;
    }
    
    private void set(final int n, final int n2) {
        if (this.outputLocals == null) {
            this.outputLocals = new int[10];
        }
        final int length = this.outputLocals.length;
        if (n >= length) {
            final int[] outputLocals = new int[Math.max(n + 1, 2 * length)];
            System.arraycopy(this.outputLocals, 0, outputLocals, 0, length);
            this.outputLocals = outputLocals;
        }
        this.outputLocals[n] = n2;
    }
    
    private void push(final int n) {
        if (this.outputStack == null) {
            this.outputStack = new int[10];
        }
        final int length = this.outputStack.length;
        if (this.outputStackTop >= length) {
            final int[] outputStack = new int[Math.max(this.outputStackTop + 1, 2 * length)];
            System.arraycopy(this.outputStack, 0, outputStack, 0, length);
            this.outputStack = outputStack;
        }
        this.outputStack[this.outputStackTop++] = n;
        final int outputStackMax = this.owner.inputStackTop + this.outputStackTop;
        if (outputStackMax > this.owner.outputStackMax) {
            this.owner.outputStackMax = outputStackMax;
        }
    }
    
    private void push(final ClassWriter classWriter, final String s) {
        final int type = type(classWriter, s);
        if (type != 0) {
            this.push(type);
            if (type == 16777220 || type == 16777219) {
                this.push(16777216);
            }
        }
    }
    
    private static int type(final ClassWriter classWriter, final String s) {
        final int n = (s.charAt(0) == '(') ? (s.indexOf(41) + 1) : 0;
        switch (s.charAt(n)) {
            case 'V': {
                return 0;
            }
            case 'B':
            case 'C':
            case 'I':
            case 'S':
            case 'Z': {
                return 16777217;
            }
            case 'F': {
                return 16777218;
            }
            case 'J': {
                return 16777220;
            }
            case 'D': {
                return 16777219;
            }
            case 'L': {
                return 0x1700000 | classWriter.addType(s.substring(n + 1, s.length() - 1));
            }
            default: {
                int n2;
                for (n2 = n + 1; s.charAt(n2) == '['; ++n2) {}
                int n3 = 0;
                switch (s.charAt(n2)) {
                    case 'Z': {
                        n3 = 16777225;
                        break;
                    }
                    case 'C': {
                        n3 = 16777227;
                        break;
                    }
                    case 'B': {
                        n3 = 16777226;
                        break;
                    }
                    case 'S': {
                        n3 = 16777228;
                        break;
                    }
                    case 'I': {
                        n3 = 16777217;
                        break;
                    }
                    case 'F': {
                        n3 = 16777218;
                        break;
                    }
                    case 'J': {
                        n3 = 16777220;
                        break;
                    }
                    case 'D': {
                        n3 = 16777219;
                        break;
                    }
                    default: {
                        n3 = (0x1700000 | classWriter.addType(s.substring(n2 + 1, s.length() - 1)));
                        break;
                    }
                }
                return n2 - n << 28 | n3;
            }
        }
    }
    
    private int pop() {
        if (this.outputStackTop > 0) {
            final int[] outputStack = this.outputStack;
            final int outputStackTop = this.outputStackTop - 1;
            this.outputStackTop = outputStackTop;
            return outputStack[outputStackTop];
        }
        final int n = 50331648;
        final Label owner = this.owner;
        return n | -(--owner.inputStackTop);
    }
    
    private void pop(final int n) {
        if (this.outputStackTop >= n) {
            this.outputStackTop -= n;
        }
        else {
            final Label owner = this.owner;
            owner.inputStackTop -= n - this.outputStackTop;
            this.outputStackTop = 0;
        }
    }
    
    private void pop(final String s) {
        final char char1 = s.charAt(0);
        if (char1 == '(') {
            this.pop((Type.getArgumentsAndReturnSizes(s) >> 2) - 1);
        }
        else if (char1 == 'J' || char1 == 'D') {
            this.pop(2);
        }
        else {
            this.pop(1);
        }
    }
    
    private void init(final int n) {
        if (this.initializations == null) {
            this.initializations = new int[2];
        }
        final int length = this.initializations.length;
        if (this.initializationCount >= length) {
            final int[] initializations = new int[Math.max(this.initializationCount + 1, 2 * length)];
            System.arraycopy(this.initializations, 0, initializations, 0, length);
            this.initializations = initializations;
        }
        this.initializations[this.initializationCount++] = n;
    }
    
    private int init(final ClassWriter classWriter, final int n) {
        int n2;
        if (n == 16777222) {
            n2 = (0x1700000 | classWriter.addType(classWriter.thisName));
        }
        else {
            if ((n & 0xFFF00000) != 0x1800000) {
                return n;
            }
            n2 = (0x1700000 | classWriter.addType(classWriter.typeTable[n & 0xFFFFF].strVal1));
        }
        for (int i = 0; i < this.initializationCount; ++i) {
            int n3 = this.initializations[i];
            final int n4 = n3 & 0xF0000000;
            final int n5 = n3 & 0xF000000;
            if (n5 == 33554432) {
                n3 = n4 + this.inputLocals[n3 & 0x7FFFFF];
            }
            else if (n5 == 50331648) {
                n3 = n4 + this.inputStack[this.inputStack.length - (n3 & 0x7FFFFF)];
            }
            if (n == n3) {
                return n2;
            }
        }
        return n;
    }
    
    final void initInputFrame(final ClassWriter classWriter, final int n, final Type[] array, final int n2) {
        this.inputLocals = new int[n2];
        this.inputStack = new int[0];
        int i = 0;
        if ((n & 0x8) == 0x0) {
            if ((n & 0x80000) == 0x0) {
                this.inputLocals[i++] = (0x1700000 | classWriter.addType(classWriter.thisName));
            }
            else {
                this.inputLocals[i++] = 16777222;
            }
        }
        for (int j = 0; j < array.length; ++j) {
            final int type = type(classWriter, array[j].getDescriptor());
            this.inputLocals[i++] = type;
            if (type == 16777220 || type == 16777219) {
                this.inputLocals[i++] = 16777216;
            }
        }
        while (i < n2) {
            this.inputLocals[i++] = 16777216;
        }
    }
    
    void execute(final int n, final int n2, final ClassWriter classWriter, final Item item) {
        Label_2252: {
            switch (n) {
                case 0:
                case 116:
                case 117:
                case 118:
                case 119:
                case 145:
                case 146:
                case 147:
                case 167:
                case 177: {
                    break;
                }
                case 1: {
                    this.push(16777221);
                    break;
                }
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 16:
                case 17:
                case 21: {
                    this.push(16777217);
                    break;
                }
                case 9:
                case 10:
                case 22: {
                    this.push(16777220);
                    this.push(16777216);
                    break;
                }
                case 11:
                case 12:
                case 13:
                case 23: {
                    this.push(16777218);
                    break;
                }
                case 14:
                case 15:
                case 24: {
                    this.push(16777219);
                    this.push(16777216);
                    break;
                }
                case 18: {
                    switch (item.type) {
                        case 3: {
                            this.push(16777217);
                            break Label_2252;
                        }
                        case 5: {
                            this.push(16777220);
                            this.push(16777216);
                            break Label_2252;
                        }
                        case 4: {
                            this.push(16777218);
                            break Label_2252;
                        }
                        case 6: {
                            this.push(16777219);
                            this.push(16777216);
                            break Label_2252;
                        }
                        case 7: {
                            this.push(0x1700000 | classWriter.addType("java/lang/Class"));
                            break Label_2252;
                        }
                        case 8: {
                            this.push(0x1700000 | classWriter.addType("java/lang/String"));
                            break Label_2252;
                        }
                        case 16: {
                            this.push(0x1700000 | classWriter.addType("java/lang/invoke/MethodType"));
                            break Label_2252;
                        }
                        default: {
                            this.push(0x1700000 | classWriter.addType("java/lang/invoke/MethodHandle"));
                            break Label_2252;
                        }
                    }
                    break;
                }
                case 25: {
                    this.push(this.get(n2));
                    break;
                }
                case 46:
                case 51:
                case 52:
                case 53: {
                    this.pop(2);
                    this.push(16777217);
                    break;
                }
                case 47:
                case 143: {
                    this.pop(2);
                    this.push(16777220);
                    this.push(16777216);
                    break;
                }
                case 48: {
                    this.pop(2);
                    this.push(16777218);
                    break;
                }
                case 49:
                case 138: {
                    this.pop(2);
                    this.push(16777219);
                    this.push(16777216);
                    break;
                }
                case 50: {
                    this.pop(1);
                    this.push(-268435456 + this.pop());
                    break;
                }
                case 54:
                case 56:
                case 58: {
                    this.set(n2, this.pop());
                    if (n2 <= 0) {
                        break;
                    }
                    final int value = this.get(n2 - 1);
                    if (value == 16777220 || value == 16777219) {
                        this.set(n2 - 1, 16777216);
                        break;
                    }
                    if ((value & 0xF000000) != 0x1000000) {
                        this.set(n2 - 1, value | 0x800000);
                        break;
                    }
                    break;
                }
                case 55:
                case 57: {
                    this.pop(1);
                    this.set(n2, this.pop());
                    this.set(n2 + 1, 16777216);
                    if (n2 <= 0) {
                        break;
                    }
                    final int value2 = this.get(n2 - 1);
                    if (value2 == 16777220 || value2 == 16777219) {
                        this.set(n2 - 1, 16777216);
                        break;
                    }
                    if ((value2 & 0xF000000) != 0x1000000) {
                        this.set(n2 - 1, value2 | 0x800000);
                        break;
                    }
                    break;
                }
                case 79:
                case 81:
                case 83:
                case 84:
                case 85:
                case 86: {
                    this.pop(3);
                    break;
                }
                case 80:
                case 82: {
                    this.pop(4);
                    break;
                }
                case 87:
                case 153:
                case 154:
                case 155:
                case 156:
                case 157:
                case 158:
                case 170:
                case 171:
                case 172:
                case 174:
                case 176:
                case 191:
                case 194:
                case 195:
                case 198:
                case 199: {
                    this.pop(1);
                    break;
                }
                case 88:
                case 159:
                case 160:
                case 161:
                case 162:
                case 163:
                case 164:
                case 165:
                case 166:
                case 173:
                case 175: {
                    this.pop(2);
                    break;
                }
                case 89: {
                    final int pop = this.pop();
                    this.push(pop);
                    this.push(pop);
                    break;
                }
                case 90: {
                    final int pop2 = this.pop();
                    final int pop3 = this.pop();
                    this.push(pop2);
                    this.push(pop3);
                    this.push(pop2);
                    break;
                }
                case 91: {
                    final int pop4 = this.pop();
                    final int pop5 = this.pop();
                    final int pop6 = this.pop();
                    this.push(pop4);
                    this.push(pop6);
                    this.push(pop5);
                    this.push(pop4);
                    break;
                }
                case 92: {
                    final int pop7 = this.pop();
                    final int pop8 = this.pop();
                    this.push(pop8);
                    this.push(pop7);
                    this.push(pop8);
                    this.push(pop7);
                    break;
                }
                case 93: {
                    final int pop9 = this.pop();
                    final int pop10 = this.pop();
                    final int pop11 = this.pop();
                    this.push(pop10);
                    this.push(pop9);
                    this.push(pop11);
                    this.push(pop10);
                    this.push(pop9);
                    break;
                }
                case 94: {
                    final int pop12 = this.pop();
                    final int pop13 = this.pop();
                    final int pop14 = this.pop();
                    final int pop15 = this.pop();
                    this.push(pop13);
                    this.push(pop12);
                    this.push(pop15);
                    this.push(pop14);
                    this.push(pop13);
                    this.push(pop12);
                    break;
                }
                case 95: {
                    final int pop16 = this.pop();
                    final int pop17 = this.pop();
                    this.push(pop16);
                    this.push(pop17);
                    break;
                }
                case 96:
                case 100:
                case 104:
                case 108:
                case 112:
                case 120:
                case 122:
                case 124:
                case 126:
                case 128:
                case 130:
                case 136:
                case 142:
                case 149:
                case 150: {
                    this.pop(2);
                    this.push(16777217);
                    break;
                }
                case 97:
                case 101:
                case 105:
                case 109:
                case 113:
                case 127:
                case 129:
                case 131: {
                    this.pop(4);
                    this.push(16777220);
                    this.push(16777216);
                    break;
                }
                case 98:
                case 102:
                case 106:
                case 110:
                case 114:
                case 137:
                case 144: {
                    this.pop(2);
                    this.push(16777218);
                    break;
                }
                case 99:
                case 103:
                case 107:
                case 111:
                case 115: {
                    this.pop(4);
                    this.push(16777219);
                    this.push(16777216);
                    break;
                }
                case 121:
                case 123:
                case 125: {
                    this.pop(3);
                    this.push(16777220);
                    this.push(16777216);
                    break;
                }
                case 132: {
                    this.set(n2, 16777217);
                    break;
                }
                case 133:
                case 140: {
                    this.pop(1);
                    this.push(16777220);
                    this.push(16777216);
                    break;
                }
                case 134: {
                    this.pop(1);
                    this.push(16777218);
                    break;
                }
                case 135:
                case 141: {
                    this.pop(1);
                    this.push(16777219);
                    this.push(16777216);
                    break;
                }
                case 139:
                case 190:
                case 193: {
                    this.pop(1);
                    this.push(16777217);
                    break;
                }
                case 148:
                case 151:
                case 152: {
                    this.pop(4);
                    this.push(16777217);
                    break;
                }
                case 168:
                case 169: {
                    throw new RuntimeException("JSR/RET are not supported with computeFrames option");
                }
                case 178: {
                    this.push(classWriter, item.strVal3);
                    break;
                }
                case 179: {
                    this.pop(item.strVal3);
                    break;
                }
                case 180: {
                    this.pop(1);
                    this.push(classWriter, item.strVal3);
                    break;
                }
                case 181: {
                    this.pop(item.strVal3);
                    this.pop();
                    break;
                }
                case 182:
                case 183:
                case 184:
                case 185: {
                    this.pop(item.strVal3);
                    if (n != 184) {
                        final int pop18 = this.pop();
                        if (n == 183 && item.strVal2.charAt(0) == '<') {
                            this.init(pop18);
                        }
                    }
                    this.push(classWriter, item.strVal3);
                    break;
                }
                case 186: {
                    this.pop(item.strVal2);
                    this.push(classWriter, item.strVal2);
                    break;
                }
                case 187: {
                    this.push(0x1800000 | classWriter.addUninitializedType(item.strVal1, n2));
                    break;
                }
                case 188: {
                    this.pop();
                    switch (n2) {
                        case 4: {
                            this.push(285212681);
                            break Label_2252;
                        }
                        case 5: {
                            this.push(285212683);
                            break Label_2252;
                        }
                        case 8: {
                            this.push(285212682);
                            break Label_2252;
                        }
                        case 9: {
                            this.push(285212684);
                            break Label_2252;
                        }
                        case 10: {
                            this.push(285212673);
                            break Label_2252;
                        }
                        case 6: {
                            this.push(285212674);
                            break Label_2252;
                        }
                        case 7: {
                            this.push(285212675);
                            break Label_2252;
                        }
                        default: {
                            this.push(285212676);
                            break Label_2252;
                        }
                    }
                    break;
                }
                case 189: {
                    final String strVal1 = item.strVal1;
                    this.pop();
                    if (strVal1.charAt(0) == '[') {
                        this.push(classWriter, '[' + strVal1);
                        break;
                    }
                    this.push(0x11700000 | classWriter.addType(strVal1));
                    break;
                }
                case 192: {
                    final String strVal2 = item.strVal1;
                    this.pop();
                    if (strVal2.charAt(0) == '[') {
                        this.push(classWriter, strVal2);
                        break;
                    }
                    this.push(0x1700000 | classWriter.addType(strVal2));
                    break;
                }
                default: {
                    this.pop(n2);
                    this.push(classWriter, item.strVal1);
                    break;
                }
            }
        }
    }
    
    final boolean merge(final ClassWriter classWriter, final Frame frame, final int n) {
        boolean b = false;
        final int length = this.inputLocals.length;
        final int length2 = this.inputStack.length;
        if (frame.inputLocals == null) {
            frame.inputLocals = new int[length];
            b = true;
        }
        for (int i = 0; i < length; ++i) {
            int init;
            if (this.outputLocals != null && i < this.outputLocals.length) {
                final int n2 = this.outputLocals[i];
                if (n2 == 0) {
                    init = this.inputLocals[i];
                }
                else {
                    final int n3 = n2 & 0xF0000000;
                    final int n4 = n2 & 0xF000000;
                    if (n4 == 16777216) {
                        init = n2;
                    }
                    else {
                        if (n4 == 33554432) {
                            init = n3 + this.inputLocals[n2 & 0x7FFFFF];
                        }
                        else {
                            init = n3 + this.inputStack[length2 - (n2 & 0x7FFFFF)];
                        }
                        if ((n2 & 0x800000) != 0x0 && (init == 16777220 || init == 16777219)) {
                            init = 16777216;
                        }
                    }
                }
            }
            else {
                init = this.inputLocals[i];
            }
            if (this.initializations != null) {
                init = this.init(classWriter, init);
            }
            b |= merge(classWriter, init, frame.inputLocals, i);
        }
        if (n > 0) {
            for (int j = 0; j < length; ++j) {
                b |= merge(classWriter, this.inputLocals[j], frame.inputLocals, j);
            }
            if (frame.inputStack == null) {
                frame.inputStack = new int[1];
                b = true;
            }
            return b | merge(classWriter, n, frame.inputStack, 0);
        }
        final int n5 = this.inputStack.length + this.owner.inputStackTop;
        if (frame.inputStack == null) {
            frame.inputStack = new int[n5 + this.outputStackTop];
            b = true;
        }
        for (int k = 0; k < n5; ++k) {
            int init2 = this.inputStack[k];
            if (this.initializations != null) {
                init2 = this.init(classWriter, init2);
            }
            b |= merge(classWriter, init2, frame.inputStack, k);
        }
        for (int l = 0; l < this.outputStackTop; ++l) {
            final int n6 = this.outputStack[l];
            final int n7 = n6 & 0xF0000000;
            final int n8 = n6 & 0xF000000;
            int init3;
            if (n8 == 16777216) {
                init3 = n6;
            }
            else {
                if (n8 == 33554432) {
                    init3 = n7 + this.inputLocals[n6 & 0x7FFFFF];
                }
                else {
                    init3 = n7 + this.inputStack[length2 - (n6 & 0x7FFFFF)];
                }
                if ((n6 & 0x800000) != 0x0 && (init3 == 16777220 || init3 == 16777219)) {
                    init3 = 16777216;
                }
            }
            if (this.initializations != null) {
                init3 = this.init(classWriter, init3);
            }
            b |= merge(classWriter, init3, frame.inputStack, n5 + l);
        }
        return b;
    }
    
    private static boolean merge(final ClassWriter classWriter, int n, final int[] array, final int n2) {
        final int n3 = array[n2];
        if (n3 == n) {
            return false;
        }
        if ((n & 0xFFFFFFF) == 0x1000005) {
            if (n3 == 16777221) {
                return false;
            }
            n = 16777221;
        }
        if (n3 == 0) {
            array[n2] = n;
            return true;
        }
        int n4;
        if ((n3 & 0xFF00000) == 0x1700000 || (n3 & 0xF0000000) != 0x0) {
            if (n == 16777221) {
                return false;
            }
            if ((n & 0xFFF00000) == (n3 & 0xFFF00000)) {
                if ((n3 & 0xFF00000) == 0x1700000) {
                    n4 = ((n & 0xF0000000) | 0x1700000 | classWriter.getMergedType(n & 0xFFFFF, n3 & 0xFFFFF));
                }
                else {
                    n4 = (-268435456 + (n3 & 0xF0000000) | 0x1700000 | classWriter.addType("java/lang/Object"));
                }
            }
            else if ((n & 0xFF00000) == 0x1700000 || (n & 0xF0000000) != 0x0) {
                n4 = (Math.min((((n & 0xF0000000) == 0x0 || (n & 0xFF00000) == 0x1700000) ? 0 : -268435456) + (n & 0xF0000000), (((n3 & 0xF0000000) == 0x0 || (n3 & 0xFF00000) == 0x1700000) ? 0 : -268435456) + (n3 & 0xF0000000)) | 0x1700000 | classWriter.addType("java/lang/Object"));
            }
            else {
                n4 = 16777216;
            }
        }
        else if (n3 == 16777221) {
            n4 = (((n & 0xFF00000) == 0x1700000 || (n & 0xF0000000) != 0x0) ? n : 16777216);
        }
        else {
            n4 = 16777216;
        }
        if (n3 != n4) {
            array[n2] = n4;
            return true;
        }
        return false;
    }
    
    static {
        final int[] size = new int[202];
        final String s = "EFFFFFFFFGGFFFGGFFFEEFGFGFEEEEEEEEEEEEEEEEEEEEDEDEDDDDDCDCDEEEEEEEEEEEEEEEEEEEEBABABBBBDCFFFGGGEDCDCDCDCDCDCDCDCDCDCEEEEDDDDDDDCDCDCEFEFDDEEFFDEDEEEBDDBBDDDDDDCCCCCCCCEFEDDDCDCDEEEEEEEEEEFEEEEEEDDEEDDEE";
        for (int i = 0; i < size.length; ++i) {
            size[i] = s.charAt(i) - 'E';
        }
        SIZE = size;
    }
}
