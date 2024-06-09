package org.spongepowered.asm.lib;

class MethodWriter extends MethodVisitor
{
    static final int ACC_CONSTRUCTOR = 524288;
    static final int SAME_FRAME = 0;
    static final int SAME_LOCALS_1_STACK_ITEM_FRAME = 64;
    static final int RESERVED = 128;
    static final int SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED = 247;
    static final int CHOP_FRAME = 248;
    static final int SAME_FRAME_EXTENDED = 251;
    static final int APPEND_FRAME = 252;
    static final int FULL_FRAME = 255;
    static final int FRAMES = 0;
    static final int INSERTED_FRAMES = 1;
    static final int MAXS = 2;
    static final int NOTHING = 3;
    final ClassWriter cw;
    private int access;
    private final int name;
    private final int desc;
    private final String descriptor;
    String signature;
    int classReaderOffset;
    int classReaderLength;
    int exceptionCount;
    int[] exceptions;
    private ByteVector annd;
    private AnnotationWriter anns;
    private AnnotationWriter ianns;
    private AnnotationWriter tanns;
    private AnnotationWriter itanns;
    private AnnotationWriter[] panns;
    private AnnotationWriter[] ipanns;
    private int synthetics;
    private Attribute attrs;
    private ByteVector code;
    private int maxStack;
    private int maxLocals;
    private int currentLocals;
    private int frameCount;
    private ByteVector stackMap;
    private int previousFrameOffset;
    private int[] previousFrame;
    private int[] frame;
    private int handlerCount;
    private Handler firstHandler;
    private Handler lastHandler;
    private int methodParametersCount;
    private ByteVector methodParameters;
    private int localVarCount;
    private ByteVector localVar;
    private int localVarTypeCount;
    private ByteVector localVarType;
    private int lineNumberCount;
    private ByteVector lineNumber;
    private int lastCodeOffset;
    private AnnotationWriter ctanns;
    private AnnotationWriter ictanns;
    private Attribute cattrs;
    private int subroutines;
    private final int compute;
    private Label labels;
    private Label previousBlock;
    private Label currentBlock;
    private int stackSize;
    private int maxStackSize;
    
    MethodWriter(final ClassWriter cw, final int access, final String s, final String descriptor, final String signature, final String[] array, final int compute) {
        super(327680);
        this.code = new ByteVector();
        if (cw.firstMethod == null) {
            cw.firstMethod = this;
        }
        else {
            cw.lastMethod.mv = this;
        }
        cw.lastMethod = this;
        this.cw = cw;
        this.access = access;
        if ("<init>".equals(s)) {
            this.access |= 0x80000;
        }
        this.name = cw.newUTF8(s);
        this.desc = cw.newUTF8(descriptor);
        this.descriptor = descriptor;
        this.signature = signature;
        if (array != null && array.length > 0) {
            this.exceptionCount = array.length;
            this.exceptions = new int[this.exceptionCount];
            for (int i = 0; i < this.exceptionCount; ++i) {
                this.exceptions[i] = cw.newClass(array[i]);
            }
        }
        if ((this.compute = compute) != 3) {
            int n = Type.getArgumentsAndReturnSizes(this.descriptor) >> 2;
            if ((access & 0x8) != 0x0) {
                --n;
            }
            this.maxLocals = n;
            this.currentLocals = n;
            this.labels = new Label();
            final Label labels = this.labels;
            labels.status |= 0x8;
            this.visitLabel(this.labels);
        }
    }
    
    @Override
    public void visitParameter(final String s, final int n) {
        if (this.methodParameters == null) {
            this.methodParameters = new ByteVector();
        }
        ++this.methodParametersCount;
        this.methodParameters.putShort((s == null) ? 0 : this.cw.newUTF8(s)).putShort(n);
    }
    
    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        this.annd = new ByteVector();
        return new AnnotationWriter(this.cw, false, this.annd, null, 0);
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String s, final boolean b) {
        final ByteVector byteVector = new ByteVector();
        byteVector.putShort(this.cw.newUTF8(s)).putShort(0);
        final AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, 2);
        if (b) {
            annotationWriter.next = this.anns;
            this.anns = annotationWriter;
        }
        else {
            annotationWriter.next = this.ianns;
            this.ianns = annotationWriter;
        }
        return annotationWriter;
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        final ByteVector byteVector = new ByteVector();
        AnnotationWriter.putTarget(n, typePath, byteVector);
        byteVector.putShort(this.cw.newUTF8(s)).putShort(0);
        final AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, byteVector.length - 2);
        if (b) {
            annotationWriter.next = this.tanns;
            this.tanns = annotationWriter;
        }
        else {
            annotationWriter.next = this.itanns;
            this.itanns = annotationWriter;
        }
        return annotationWriter;
    }
    
    @Override
    public AnnotationVisitor visitParameterAnnotation(final int n, final String s, final boolean b) {
        final ByteVector byteVector = new ByteVector();
        if ("Ljava/lang/Synthetic;".equals(s)) {
            this.synthetics = Math.max(this.synthetics, n + 1);
            return new AnnotationWriter(this.cw, false, byteVector, null, 0);
        }
        byteVector.putShort(this.cw.newUTF8(s)).putShort(0);
        final AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, 2);
        if (b) {
            if (this.panns == null) {
                this.panns = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
            }
            annotationWriter.next = this.panns[n];
            this.panns[n] = annotationWriter;
        }
        else {
            if (this.ipanns == null) {
                this.ipanns = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
            }
            annotationWriter.next = this.ipanns[n];
            this.ipanns[n] = annotationWriter;
        }
        return annotationWriter;
    }
    
    @Override
    public void visitAttribute(final Attribute attribute) {
        if (attribute.isCodeAttribute()) {
            attribute.next = this.cattrs;
            this.cattrs = attribute;
        }
        else {
            attribute.next = this.attrs;
            this.attrs = attribute;
        }
    }
    
    @Override
    public void visitCode() {
    }
    
    @Override
    public void visitFrame(final int n, final int n2, final Object[] array, final int n3, final Object[] array2) {
        if (this.compute == 0) {
            return;
        }
        if (this.compute == 1) {
            if (this.currentBlock.frame == null) {
                this.currentBlock.frame = new CurrentFrame();
                this.currentBlock.frame.owner = this.currentBlock;
                this.currentBlock.frame.initInputFrame(this.cw, this.access, Type.getArgumentTypes(this.descriptor), n2);
                this.visitImplicitFirstFrame();
            }
            else {
                if (n == -1) {
                    this.currentBlock.frame.set(this.cw, n2, array, n3, array2);
                }
                this.visitFrame(this.currentBlock.frame);
            }
        }
        else if (n == -1) {
            if (this.previousFrame == null) {
                this.visitImplicitFirstFrame();
            }
            this.currentLocals = n2;
            int startFrame = this.startFrame(this.code.length, n2, n3);
            for (int i = 0; i < n2; ++i) {
                if (array[i] instanceof String) {
                    this.frame[startFrame++] = (0x1700000 | this.cw.addType((String)array[i]));
                }
                else if (array[i] instanceof Integer) {
                    this.frame[startFrame++] = (int)array[i];
                }
                else {
                    this.frame[startFrame++] = (0x1800000 | this.cw.addUninitializedType("", ((Label)array[i]).position));
                }
            }
            for (int j = 0; j < n3; ++j) {
                if (array2[j] instanceof String) {
                    this.frame[startFrame++] = (0x1700000 | this.cw.addType((String)array2[j]));
                }
                else if (array2[j] instanceof Integer) {
                    this.frame[startFrame++] = (int)array2[j];
                }
                else {
                    this.frame[startFrame++] = (0x1800000 | this.cw.addUninitializedType("", ((Label)array2[j]).position));
                }
            }
            this.endFrame();
        }
        else {
            int length;
            if (this.stackMap == null) {
                this.stackMap = new ByteVector();
                length = this.code.length;
            }
            else {
                length = this.code.length - this.previousFrameOffset - 1;
                if (length < 0) {
                    if (n == 3) {
                        return;
                    }
                    throw new IllegalStateException();
                }
            }
            switch (n) {
                case 0: {
                    this.currentLocals = n2;
                    this.stackMap.putByte(255).putShort(length).putShort(n2);
                    for (int k = 0; k < n2; ++k) {
                        this.writeFrameType(array[k]);
                    }
                    this.stackMap.putShort(n3);
                    for (int l = 0; l < n3; ++l) {
                        this.writeFrameType(array2[l]);
                    }
                    break;
                }
                case 1: {
                    this.currentLocals += n2;
                    this.stackMap.putByte(251 + n2).putShort(length);
                    for (int n4 = 0; n4 < n2; ++n4) {
                        this.writeFrameType(array[n4]);
                    }
                    break;
                }
                case 2: {
                    this.currentLocals -= n2;
                    this.stackMap.putByte(251 - n2).putShort(length);
                    break;
                }
                case 3: {
                    if (length < 64) {
                        this.stackMap.putByte(length);
                        break;
                    }
                    this.stackMap.putByte(251).putShort(length);
                    break;
                }
                case 4: {
                    if (length < 64) {
                        this.stackMap.putByte(64 + length);
                    }
                    else {
                        this.stackMap.putByte(247).putShort(length);
                    }
                    this.writeFrameType(array2[0]);
                    break;
                }
            }
            this.previousFrameOffset = this.code.length;
            ++this.frameCount;
        }
        this.maxStack = Math.max(this.maxStack, n3);
        this.maxLocals = Math.max(this.maxLocals, this.currentLocals);
    }
    
    @Override
    public void visitInsn(final int n) {
        this.lastCodeOffset = this.code.length;
        this.code.putByte(n);
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(n, 0, null, null);
            }
            else {
                final int n2 = this.stackSize + Frame.SIZE[n];
                if (n2 > this.maxStackSize) {
                    this.maxStackSize = n2;
                }
                this.stackSize = n2;
            }
            if ((n >= 172 && n <= 177) || n == 191) {
                this.noSuccessor();
            }
        }
    }
    
    @Override
    public void visitIntInsn(final int n, final int n2) {
        this.lastCodeOffset = this.code.length;
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(n, n2, null, null);
            }
            else if (n != 188) {
                final int n3 = this.stackSize + 1;
                if (n3 > this.maxStackSize) {
                    this.maxStackSize = n3;
                }
                this.stackSize = n3;
            }
        }
        if (n == 17) {
            this.code.put12(n, n2);
        }
        else {
            this.code.put11(n, n2);
        }
    }
    
    @Override
    public void visitVarInsn(final int n, final int n2) {
        this.lastCodeOffset = this.code.length;
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(n, n2, null, null);
            }
            else if (n == 169) {
                final Label currentBlock = this.currentBlock;
                currentBlock.status |= 0x100;
                this.currentBlock.inputStackTop = this.stackSize;
                this.noSuccessor();
            }
            else {
                final int n3 = this.stackSize + Frame.SIZE[n];
                if (n3 > this.maxStackSize) {
                    this.maxStackSize = n3;
                }
                this.stackSize = n3;
            }
        }
        if (this.compute != 3) {
            int maxLocals;
            if (n == 22 || n == 24 || n == 55 || n == 57) {
                maxLocals = n2 + 2;
            }
            else {
                maxLocals = n2 + 1;
            }
            if (maxLocals > this.maxLocals) {
                this.maxLocals = maxLocals;
            }
        }
        if (n2 < 4 && n != 169) {
            int n4;
            if (n < 54) {
                n4 = 26 + (n - 21 << 2) + n2;
            }
            else {
                n4 = 59 + (n - 54 << 2) + n2;
            }
            this.code.putByte(n4);
        }
        else if (n2 >= 256) {
            this.code.putByte(196).put12(n, n2);
        }
        else {
            this.code.put11(n, n2);
        }
        if (n >= 54 && this.compute == 0 && this.handlerCount > 0) {
            this.visitLabel(new Label());
        }
    }
    
    @Override
    public void visitTypeInsn(final int n, final String s) {
        this.lastCodeOffset = this.code.length;
        final Item classItem = this.cw.newClassItem(s);
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(n, this.code.length, this.cw, classItem);
            }
            else if (n == 187) {
                final int n2 = this.stackSize + 1;
                if (n2 > this.maxStackSize) {
                    this.maxStackSize = n2;
                }
                this.stackSize = n2;
            }
        }
        this.code.put12(n, classItem.index);
    }
    
    @Override
    public void visitFieldInsn(final int n, final String s, final String s2, final String s3) {
        this.lastCodeOffset = this.code.length;
        final Item fieldItem = this.cw.newFieldItem(s, s2, s3);
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(n, 0, this.cw, fieldItem);
            }
            else {
                final char char1 = s3.charAt(0);
                int n2 = 0;
                switch (n) {
                    case 178: {
                        n2 = this.stackSize + ((char1 == 'D' || char1 == 'J') ? 2 : 1);
                        break;
                    }
                    case 179: {
                        n2 = this.stackSize + ((char1 == 'D' || char1 == 'J') ? -2 : -1);
                        break;
                    }
                    case 180: {
                        n2 = this.stackSize + ((char1 == 'D' || char1 == 'J') ? 1 : 0);
                        break;
                    }
                    default: {
                        n2 = this.stackSize + ((char1 == 'D' || char1 == 'J') ? -3 : -2);
                        break;
                    }
                }
                if (n2 > this.maxStackSize) {
                    this.maxStackSize = n2;
                }
                this.stackSize = n2;
            }
        }
        this.code.put12(n, fieldItem.index);
    }
    
    @Override
    public void visitMethodInsn(final int n, final String s, final String s2, final String s3, final boolean b) {
        this.lastCodeOffset = this.code.length;
        final Item methodItem = this.cw.newMethodItem(s, s2, s3, b);
        int n2 = methodItem.intVal;
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(n, 0, this.cw, methodItem);
            }
            else {
                if (n2 == 0) {
                    n2 = Type.getArgumentsAndReturnSizes(s3);
                    methodItem.intVal = n2;
                }
                int n3;
                if (n == 184) {
                    n3 = this.stackSize - (n2 >> 2) + (n2 & 0x3) + 1;
                }
                else {
                    n3 = this.stackSize - (n2 >> 2) + (n2 & 0x3);
                }
                if (n3 > this.maxStackSize) {
                    this.maxStackSize = n3;
                }
                this.stackSize = n3;
            }
        }
        if (n == 185) {
            if (n2 == 0) {
                n2 = Type.getArgumentsAndReturnSizes(s3);
                methodItem.intVal = n2;
            }
            this.code.put12(185, methodItem.index).put11(n2 >> 2, 0);
        }
        else {
            this.code.put12(n, methodItem.index);
        }
    }
    
    @Override
    public void visitInvokeDynamicInsn(final String s, final String s2, final Handle handle, final Object... array) {
        this.lastCodeOffset = this.code.length;
        final Item invokeDynamicItem = this.cw.newInvokeDynamicItem(s, s2, handle, array);
        int intVal = invokeDynamicItem.intVal;
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(186, 0, this.cw, invokeDynamicItem);
            }
            else {
                if (intVal == 0) {
                    intVal = Type.getArgumentsAndReturnSizes(s2);
                    invokeDynamicItem.intVal = intVal;
                }
                final int n = this.stackSize - (intVal >> 2) + (intVal & 0x3) + 1;
                if (n > this.maxStackSize) {
                    this.maxStackSize = n;
                }
                this.stackSize = n;
            }
        }
        this.code.put12(186, invokeDynamicItem.index);
        this.code.putShort(0);
    }
    
    @Override
    public void visitJumpInsn(int n, final Label label) {
        final boolean b = n >= 200;
        n = (b ? (n - 33) : n);
        this.lastCodeOffset = this.code.length;
        Label label2 = null;
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(n, 0, null, null);
                final Label first = label.getFirst();
                first.status |= 0x10;
                this.addSuccessor(0, label);
                if (n != 167) {
                    label2 = new Label();
                }
            }
            else if (this.compute == 1) {
                this.currentBlock.frame.execute(n, 0, null, null);
            }
            else if (n == 168) {
                if ((label.status & 0x200) == 0x0) {
                    label.status |= 0x200;
                    ++this.subroutines;
                }
                final Label currentBlock = this.currentBlock;
                currentBlock.status |= 0x80;
                this.addSuccessor(this.stackSize + 1, label);
                label2 = new Label();
            }
            else {
                this.addSuccessor(this.stackSize += Frame.SIZE[n], label);
            }
        }
        if ((label.status & 0x2) != 0x0 && label.position - this.code.length < -32768) {
            if (n == 167) {
                this.code.putByte(200);
            }
            else if (n == 168) {
                this.code.putByte(201);
            }
            else {
                if (label2 != null) {
                    final Label label3 = label2;
                    label3.status |= 0x10;
                }
                this.code.putByte((n <= 166) ? ((n + 1 ^ 0x1) - 1) : (n ^ 0x1));
                this.code.putShort(8);
                this.code.putByte(200);
            }
            label.put(this, this.code, this.code.length - 1, true);
        }
        else if (b) {
            this.code.putByte(n + 33);
            label.put(this, this.code, this.code.length - 1, true);
        }
        else {
            this.code.putByte(n);
            label.put(this, this.code, this.code.length - 1, false);
        }
        if (this.currentBlock != null) {
            if (label2 != null) {
                this.visitLabel(label2);
            }
            if (n == 167) {
                this.noSuccessor();
            }
        }
    }
    
    @Override
    public void visitLabel(final Label previousBlock) {
        final ClassWriter cw = this.cw;
        cw.hasAsmInsns |= previousBlock.resolve(this, this.code.length, this.code.data);
        if ((previousBlock.status & 0x1) != 0x0) {
            return;
        }
        if (this.compute == 0) {
            if (this.currentBlock != null) {
                if (previousBlock.position == this.currentBlock.position) {
                    final Label currentBlock = this.currentBlock;
                    currentBlock.status |= (previousBlock.status & 0x10);
                    previousBlock.frame = this.currentBlock.frame;
                    return;
                }
                this.addSuccessor(0, previousBlock);
            }
            this.currentBlock = previousBlock;
            if (previousBlock.frame == null) {
                previousBlock.frame = new Frame();
                previousBlock.frame.owner = previousBlock;
            }
            if (this.previousBlock != null) {
                if (previousBlock.position == this.previousBlock.position) {
                    final Label previousBlock2 = this.previousBlock;
                    previousBlock2.status |= (previousBlock.status & 0x10);
                    previousBlock.frame = this.previousBlock.frame;
                    this.currentBlock = this.previousBlock;
                    return;
                }
                this.previousBlock.successor = previousBlock;
            }
            this.previousBlock = previousBlock;
        }
        else if (this.compute == 1) {
            if (this.currentBlock == null) {
                this.currentBlock = previousBlock;
            }
            else {
                this.currentBlock.frame.owner = previousBlock;
            }
        }
        else if (this.compute == 2) {
            if (this.currentBlock != null) {
                this.currentBlock.outputStackMax = this.maxStackSize;
                this.addSuccessor(this.stackSize, previousBlock);
            }
            this.currentBlock = previousBlock;
            this.stackSize = 0;
            this.maxStackSize = 0;
            if (this.previousBlock != null) {
                this.previousBlock.successor = previousBlock;
            }
            this.previousBlock = previousBlock;
        }
    }
    
    @Override
    public void visitLdcInsn(final Object o) {
        this.lastCodeOffset = this.code.length;
        final Item constItem = this.cw.newConstItem(o);
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(18, 0, this.cw, constItem);
            }
            else {
                int n;
                if (constItem.type == 5 || constItem.type == 6) {
                    n = this.stackSize + 2;
                }
                else {
                    n = this.stackSize + 1;
                }
                if (n > this.maxStackSize) {
                    this.maxStackSize = n;
                }
                this.stackSize = n;
            }
        }
        final int index = constItem.index;
        if (constItem.type == 5 || constItem.type == 6) {
            this.code.put12(20, index);
        }
        else if (index >= 256) {
            this.code.put12(19, index);
        }
        else {
            this.code.put11(18, index);
        }
    }
    
    @Override
    public void visitIincInsn(final int n, final int n2) {
        this.lastCodeOffset = this.code.length;
        if (this.currentBlock != null && (this.compute == 0 || this.compute == 1)) {
            this.currentBlock.frame.execute(132, n, null, null);
        }
        if (this.compute != 3) {
            final int maxLocals = n + 1;
            if (maxLocals > this.maxLocals) {
                this.maxLocals = maxLocals;
            }
        }
        if (n > 255 || n2 > 127 || n2 < -128) {
            this.code.putByte(196).put12(132, n).putShort(n2);
        }
        else {
            this.code.putByte(132).put11(n, n2);
        }
    }
    
    @Override
    public void visitTableSwitchInsn(final int n, final int n2, final Label label, final Label... array) {
        this.lastCodeOffset = this.code.length;
        final int length = this.code.length;
        this.code.putByte(170);
        this.code.putByteArray(null, 0, (4 - this.code.length % 4) % 4);
        label.put(this, this.code, length, true);
        this.code.putInt(n).putInt(n2);
        for (int i = 0; i < array.length; ++i) {
            array[i].put(this, this.code, length, true);
        }
        this.visitSwitchInsn(label, array);
    }
    
    @Override
    public void visitLookupSwitchInsn(final Label label, final int[] array, final Label[] array2) {
        this.lastCodeOffset = this.code.length;
        final int length = this.code.length;
        this.code.putByte(171);
        this.code.putByteArray(null, 0, (4 - this.code.length % 4) % 4);
        label.put(this, this.code, length, true);
        this.code.putInt(array2.length);
        for (int i = 0; i < array2.length; ++i) {
            this.code.putInt(array[i]);
            array2[i].put(this, this.code, length, true);
        }
        this.visitSwitchInsn(label, array2);
    }
    
    private void visitSwitchInsn(final Label label, final Label[] array) {
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(171, 0, null, null);
                this.addSuccessor(0, label);
                final Label first = label.getFirst();
                first.status |= 0x10;
                for (int i = 0; i < array.length; ++i) {
                    this.addSuccessor(0, array[i]);
                    final Label first2 = array[i].getFirst();
                    first2.status |= 0x10;
                }
            }
            else {
                this.addSuccessor(--this.stackSize, label);
                for (int j = 0; j < array.length; ++j) {
                    this.addSuccessor(this.stackSize, array[j]);
                }
            }
            this.noSuccessor();
        }
    }
    
    @Override
    public void visitMultiANewArrayInsn(final String s, final int n) {
        this.lastCodeOffset = this.code.length;
        final Item classItem = this.cw.newClassItem(s);
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(197, n, this.cw, classItem);
            }
            else {
                this.stackSize += 1 - n;
            }
        }
        this.code.put12(197, classItem.index).putByte(n);
    }
    
    @Override
    public AnnotationVisitor visitInsnAnnotation(int n, final TypePath typePath, final String s, final boolean b) {
        final ByteVector byteVector = new ByteVector();
        n = ((n & 0xFF0000FF) | this.lastCodeOffset << 8);
        AnnotationWriter.putTarget(n, typePath, byteVector);
        byteVector.putShort(this.cw.newUTF8(s)).putShort(0);
        final AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, byteVector.length - 2);
        if (b) {
            annotationWriter.next = this.ctanns;
            this.ctanns = annotationWriter;
        }
        else {
            annotationWriter.next = this.ictanns;
            this.ictanns = annotationWriter;
        }
        return annotationWriter;
    }
    
    @Override
    public void visitTryCatchBlock(final Label start, final Label end, final Label handler, final String desc) {
        ++this.handlerCount;
        final Handler lastHandler = new Handler();
        lastHandler.start = start;
        lastHandler.end = end;
        lastHandler.handler = handler;
        lastHandler.desc = desc;
        lastHandler.type = ((desc != null) ? this.cw.newClass(desc) : 0);
        if (this.lastHandler == null) {
            this.firstHandler = lastHandler;
        }
        else {
            this.lastHandler.next = lastHandler;
        }
        this.lastHandler = lastHandler;
    }
    
    @Override
    public AnnotationVisitor visitTryCatchAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        final ByteVector byteVector = new ByteVector();
        AnnotationWriter.putTarget(n, typePath, byteVector);
        byteVector.putShort(this.cw.newUTF8(s)).putShort(0);
        final AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, byteVector.length - 2);
        if (b) {
            annotationWriter.next = this.ctanns;
            this.ctanns = annotationWriter;
        }
        else {
            annotationWriter.next = this.ictanns;
            this.ictanns = annotationWriter;
        }
        return annotationWriter;
    }
    
    @Override
    public void visitLocalVariable(final String s, final String s2, final String s3, final Label label, final Label label2, final int n) {
        if (s3 != null) {
            if (this.localVarType == null) {
                this.localVarType = new ByteVector();
            }
            ++this.localVarTypeCount;
            this.localVarType.putShort(label.position).putShort(label2.position - label.position).putShort(this.cw.newUTF8(s)).putShort(this.cw.newUTF8(s3)).putShort(n);
        }
        if (this.localVar == null) {
            this.localVar = new ByteVector();
        }
        ++this.localVarCount;
        this.localVar.putShort(label.position).putShort(label2.position - label.position).putShort(this.cw.newUTF8(s)).putShort(this.cw.newUTF8(s2)).putShort(n);
        if (this.compute != 3) {
            final char char1 = s2.charAt(0);
            final int maxLocals = n + ((char1 == 'J' || char1 == 'D') ? 2 : 1);
            if (maxLocals > this.maxLocals) {
                this.maxLocals = maxLocals;
            }
        }
    }
    
    @Override
    public AnnotationVisitor visitLocalVariableAnnotation(final int n, final TypePath typePath, final Label[] array, final Label[] array2, final int[] array3, final String s, final boolean b) {
        final ByteVector byteVector = new ByteVector();
        byteVector.putByte(n >>> 24).putShort(array.length);
        for (int i = 0; i < array.length; ++i) {
            byteVector.putShort(array[i].position).putShort(array2[i].position - array[i].position).putShort(array3[i]);
        }
        if (typePath == null) {
            byteVector.putByte(0);
        }
        else {
            byteVector.putByteArray(typePath.b, typePath.offset, typePath.b[typePath.offset] * 2 + 1);
        }
        byteVector.putShort(this.cw.newUTF8(s)).putShort(0);
        final AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, byteVector.length - 2);
        if (b) {
            annotationWriter.next = this.ctanns;
            this.ctanns = annotationWriter;
        }
        else {
            annotationWriter.next = this.ictanns;
            this.ictanns = annotationWriter;
        }
        return annotationWriter;
    }
    
    @Override
    public void visitLineNumber(final int n, final Label label) {
        if (this.lineNumber == null) {
            this.lineNumber = new ByteVector();
        }
        ++this.lineNumberCount;
        this.lineNumber.putShort(label.position);
        this.lineNumber.putShort(n);
    }
    
    @Override
    public void visitMaxs(final int maxStack, final int maxLocals) {
        if (this.compute == 0) {
            for (Handler handler = this.firstHandler; handler != null; handler = handler.next) {
                Label label = handler.start.getFirst();
                final Label first = handler.handler.getFirst();
                final Label first2 = handler.end.getFirst();
                final int info = 0x1700000 | this.cw.addType((handler.desc == null) ? "java/lang/Throwable" : handler.desc);
                final Label label2 = first;
                label2.status |= 0x10;
                while (label != first2) {
                    final Edge successors = new Edge();
                    successors.info = info;
                    successors.successor = first;
                    successors.next = label.successors;
                    label.successors = successors;
                    label = label.successor;
                }
            }
            final Frame frame = this.labels.frame;
            frame.initInputFrame(this.cw, this.access, Type.getArgumentTypes(this.descriptor), this.maxLocals);
            this.visitFrame(frame);
            int max = 0;
            Label next = this.labels;
            while (next != null) {
                final Label label3 = next;
                next = next.next;
                label3.next = null;
                final Frame frame2 = label3.frame;
                if ((label3.status & 0x10) != 0x0) {
                    final Label label4 = label3;
                    label4.status |= 0x20;
                }
                final Label label5 = label3;
                label5.status |= 0x40;
                final int n = frame2.inputStack.length + label3.outputStackMax;
                if (n > max) {
                    max = n;
                }
                for (Edge edge = label3.successors; edge != null; edge = edge.next) {
                    final Label first3 = edge.successor.getFirst();
                    if (frame2.merge(this.cw, first3.frame, edge.info) && first3.next == null) {
                        first3.next = next;
                        next = first3;
                    }
                }
            }
            for (Label label6 = this.labels; label6 != null; label6 = label6.successor) {
                final Frame frame3 = label6.frame;
                if ((label6.status & 0x20) != 0x0) {
                    this.visitFrame(frame3);
                }
                if ((label6.status & 0x40) == 0x0) {
                    final Label successor = label6.successor;
                    final int position = label6.position;
                    final int n2 = ((successor == null) ? this.code.length : successor.position) - 1;
                    if (n2 >= position) {
                        max = Math.max(max, 1);
                        for (int i = position; i < n2; ++i) {
                            this.code.data[i] = 0;
                        }
                        this.code.data[n2] = -65;
                        this.frame[this.startFrame(position, 0, 1)] = (0x1700000 | this.cw.addType("java/lang/Throwable"));
                        this.endFrame();
                        this.firstHandler = Handler.remove(this.firstHandler, label6, successor);
                    }
                }
            }
            Handler handler2 = this.firstHandler;
            this.handlerCount = 0;
            while (handler2 != null) {
                ++this.handlerCount;
                handler2 = handler2.next;
            }
            this.maxStack = max;
        }
        else if (this.compute == 2) {
            for (Handler handler3 = this.firstHandler; handler3 != null; handler3 = handler3.next) {
                Label label7 = handler3.start;
                final Label handler4 = handler3.handler;
                while (label7 != handler3.end) {
                    final Edge edge2 = new Edge();
                    edge2.info = 0;
                    edge2.successor = handler4;
                    if ((label7.status & 0x80) == 0x0) {
                        edge2.next = label7.successors;
                        label7.successors = edge2;
                    }
                    else {
                        edge2.next = label7.successors.next.next;
                        label7.successors.next.next = edge2;
                    }
                    label7 = label7.successor;
                }
            }
            if (this.subroutines > 0) {
                int n3 = 0;
                this.labels.visitSubroutine(null, 1L, this.subroutines);
                for (Label label8 = this.labels; label8 != null; label8 = label8.successor) {
                    if ((label8.status & 0x80) != 0x0) {
                        final Label successor2 = label8.successors.next.successor;
                        if ((successor2.status & 0x400) == 0x0) {
                            ++n3;
                            successor2.visitSubroutine(null, n3 / 32L << 32 | 1L << n3 % 32, this.subroutines);
                        }
                    }
                }
                for (Label label9 = this.labels; label9 != null; label9 = label9.successor) {
                    if ((label9.status & 0x80) != 0x0) {
                        for (Label label10 = this.labels; label10 != null; label10 = label10.successor) {
                            final Label label11 = label10;
                            label11.status &= 0xFFFFF7FF;
                        }
                        label9.successors.next.successor.visitSubroutine(label9, 0L, this.subroutines);
                    }
                }
            }
            int n4 = 0;
            Label next2 = this.labels;
            while (next2 != null) {
                final Label label12 = next2;
                next2 = next2.next;
                final int inputStackTop = label12.inputStackTop;
                final int n5 = inputStackTop + label12.outputStackMax;
                if (n5 > n4) {
                    n4 = n5;
                }
                Edge edge3 = label12.successors;
                if ((label12.status & 0x80) != 0x0) {
                    edge3 = edge3.next;
                }
                while (edge3 != null) {
                    final Label successor3 = edge3.successor;
                    if ((successor3.status & 0x8) == 0x0) {
                        successor3.inputStackTop = ((edge3.info == 0) ? 1 : (inputStackTop + edge3.info));
                        final Label label13 = successor3;
                        label13.status |= 0x8;
                        successor3.next = next2;
                        next2 = successor3;
                    }
                    edge3 = edge3.next;
                }
            }
            this.maxStack = Math.max(maxStack, n4);
        }
        else {
            this.maxStack = maxStack;
            this.maxLocals = maxLocals;
        }
    }
    
    @Override
    public void visitEnd() {
    }
    
    private void addSuccessor(final int info, final Label successor) {
        final Edge successors = new Edge();
        successors.info = info;
        successors.successor = successor;
        successors.next = this.currentBlock.successors;
        this.currentBlock.successors = successors;
    }
    
    private void noSuccessor() {
        if (this.compute == 0) {
            final Label previousBlock = new Label();
            previousBlock.frame = new Frame();
            (previousBlock.frame.owner = previousBlock).resolve(this, this.code.length, this.code.data);
            this.previousBlock.successor = previousBlock;
            this.previousBlock = previousBlock;
        }
        else {
            this.currentBlock.outputStackMax = this.maxStackSize;
        }
        if (this.compute != 1) {
            this.currentBlock = null;
        }
    }
    
    private void visitFrame(final Frame frame) {
        int n = 0;
        int i = 0;
        int n2 = 0;
        final int[] inputLocals = frame.inputLocals;
        final int[] inputStack = frame.inputStack;
        for (int j = 0; j < inputLocals.length; ++j) {
            final int n3 = inputLocals[j];
            if (n3 == 16777216) {
                ++n;
            }
            else {
                i += n + 1;
                n = 0;
            }
            if (n3 == 16777220 || n3 == 16777219) {
                ++j;
            }
        }
        for (int k = 0; k < inputStack.length; ++k) {
            final int n4 = inputStack[k];
            ++n2;
            if (n4 == 16777220 || n4 == 16777219) {
                ++k;
            }
        }
        int startFrame = this.startFrame(frame.owner.position, i, n2);
        int n5 = 0;
        while (i > 0) {
            final int n6 = inputLocals[n5];
            this.frame[startFrame++] = n6;
            if (n6 == 16777220 || n6 == 16777219) {
                ++n5;
            }
            ++n5;
            --i;
        }
        for (int l = 0; l < inputStack.length; ++l) {
            final int n7 = inputStack[l];
            this.frame[startFrame++] = n7;
            if (n7 == 16777220 || n7 == 16777219) {
                ++l;
            }
        }
        this.endFrame();
    }
    
    private void visitImplicitFirstFrame() {
        int startFrame = this.startFrame(0, this.descriptor.length() + 1, 0);
        if ((this.access & 0x8) == 0x0) {
            if ((this.access & 0x80000) == 0x0) {
                this.frame[startFrame++] = (0x1700000 | this.cw.addType(this.cw.thisName));
            }
            else {
                this.frame[startFrame++] = 6;
            }
        }
        int n = 1;
        while (true) {
            final int n2 = n;
            switch (this.descriptor.charAt(n++)) {
                case 'B':
                case 'C':
                case 'I':
                case 'S':
                case 'Z': {
                    this.frame[startFrame++] = 1;
                    continue;
                }
                case 'F': {
                    this.frame[startFrame++] = 2;
                    continue;
                }
                case 'J': {
                    this.frame[startFrame++] = 4;
                    continue;
                }
                case 'D': {
                    this.frame[startFrame++] = 3;
                    continue;
                }
                case '[': {
                    while (this.descriptor.charAt(n) == '[') {
                        ++n;
                    }
                    if (this.descriptor.charAt(n) == 'L') {
                        ++n;
                        while (this.descriptor.charAt(n) != ';') {
                            ++n;
                        }
                    }
                    this.frame[startFrame++] = (0x1700000 | this.cw.addType(this.descriptor.substring(n2, ++n)));
                    continue;
                }
                case 'L': {
                    while (this.descriptor.charAt(n) != ';') {
                        ++n;
                    }
                    this.frame[startFrame++] = (0x1700000 | this.cw.addType(this.descriptor.substring(n2 + 1, n++)));
                    continue;
                }
                default: {
                    this.frame[1] = startFrame - 3;
                    this.endFrame();
                }
            }
        }
    }
    
    private int startFrame(final int n, final int n2, final int n3) {
        final int n4 = 3 + n2 + n3;
        if (this.frame == null || this.frame.length < n4) {
            this.frame = new int[n4];
        }
        this.frame[0] = n;
        this.frame[1] = n2;
        this.frame[2] = n3;
        return 3;
    }
    
    private void endFrame() {
        if (this.previousFrame != null) {
            if (this.stackMap == null) {
                this.stackMap = new ByteVector();
            }
            this.writeFrame();
            ++this.frameCount;
        }
        this.previousFrame = this.frame;
        this.frame = null;
    }
    
    private void writeFrame() {
        final int n = this.frame[1];
        final int n2 = this.frame[2];
        if ((this.cw.version & 0xFFFF) < 50) {
            this.stackMap.putShort(this.frame[0]).putShort(n);
            this.writeFrameTypes(3, 3 + n);
            this.stackMap.putShort(n2);
            this.writeFrameTypes(3 + n, 3 + n + n2);
            return;
        }
        int n3 = this.previousFrame[1];
        int n4 = 255;
        int n5 = 0;
        int n6;
        if (this.frameCount == 0) {
            n6 = this.frame[0];
        }
        else {
            n6 = this.frame[0] - this.previousFrame[0] - 1;
        }
        if (n2 == 0) {
            n5 = n - n3;
            switch (n5) {
                case -3:
                case -2:
                case -1: {
                    n4 = 248;
                    n3 = n;
                    break;
                }
                case 0: {
                    n4 = ((n6 < 64) ? 0 : 251);
                    break;
                }
                case 1:
                case 2:
                case 3: {
                    n4 = 252;
                    break;
                }
            }
        }
        else if (n == n3 && n2 == 1) {
            n4 = ((n6 < 63) ? 64 : 247);
        }
        if (n4 != 255) {
            int n7 = 3;
            for (int i = 0; i < n3; ++i) {
                if (this.frame[n7] != this.previousFrame[n7]) {
                    n4 = 255;
                    break;
                }
                ++n7;
            }
        }
        switch (n4) {
            case 0: {
                this.stackMap.putByte(n6);
                break;
            }
            case 64: {
                this.stackMap.putByte(64 + n6);
                this.writeFrameTypes(3 + n, 4 + n);
                break;
            }
            case 247: {
                this.stackMap.putByte(247).putShort(n6);
                this.writeFrameTypes(3 + n, 4 + n);
                break;
            }
            case 251: {
                this.stackMap.putByte(251).putShort(n6);
                break;
            }
            case 248: {
                this.stackMap.putByte(251 + n5).putShort(n6);
                break;
            }
            case 252: {
                this.stackMap.putByte(251 + n5).putShort(n6);
                this.writeFrameTypes(3 + n3, 3 + n);
                break;
            }
            default: {
                this.stackMap.putByte(255).putShort(n6).putShort(n);
                this.writeFrameTypes(3, 3 + n);
                this.stackMap.putShort(n2);
                this.writeFrameTypes(3 + n, 3 + n + n2);
                break;
            }
        }
    }
    
    private void writeFrameTypes(final int n, final int n2) {
        for (int i = n; i < n2; ++i) {
            final int n3 = this.frame[i];
            final int n4 = n3 & 0xF0000000;
            if (n4 == 0) {
                final int n5 = n3 & 0xFFFFF;
                switch (n3 & 0xFF00000) {
                    case 24117248: {
                        this.stackMap.putByte(7).putShort(this.cw.newClass(this.cw.typeTable[n5].strVal1));
                        break;
                    }
                    case 25165824: {
                        this.stackMap.putByte(8).putShort(this.cw.typeTable[n5].intVal);
                        break;
                    }
                    default: {
                        this.stackMap.putByte(n5);
                        break;
                    }
                }
            }
            else {
                final StringBuilder sb = new StringBuilder();
                int n6 = n4 >> 28;
                while (n6-- > 0) {
                    sb.append('[');
                }
                if ((n3 & 0xFF00000) == 0x1700000) {
                    sb.append('L');
                    sb.append(this.cw.typeTable[n3 & 0xFFFFF].strVal1);
                    sb.append(';');
                }
                else {
                    switch (n3 & 0xF) {
                        case 1: {
                            sb.append('I');
                            break;
                        }
                        case 2: {
                            sb.append('F');
                            break;
                        }
                        case 3: {
                            sb.append('D');
                            break;
                        }
                        case 9: {
                            sb.append('Z');
                            break;
                        }
                        case 10: {
                            sb.append('B');
                            break;
                        }
                        case 11: {
                            sb.append('C');
                            break;
                        }
                        case 12: {
                            sb.append('S');
                            break;
                        }
                        default: {
                            sb.append('J');
                            break;
                        }
                    }
                }
                this.stackMap.putByte(7).putShort(this.cw.newClass(sb.toString()));
            }
        }
    }
    
    private void writeFrameType(final Object o) {
        if (o instanceof String) {
            this.stackMap.putByte(7).putShort(this.cw.newClass((String)o));
        }
        else if (o instanceof Integer) {
            this.stackMap.putByte((int)o);
        }
        else {
            this.stackMap.putByte(8).putShort(((Label)o).position);
        }
    }
    
    final int getSize() {
        if (this.classReaderOffset != 0) {
            return 6 + this.classReaderLength;
        }
        int n = 8;
        if (this.code.length > 0) {
            if (this.code.length > 65535) {
                throw new RuntimeException("Method code too large!");
            }
            this.cw.newUTF8("Code");
            n += 18 + this.code.length + 8 * this.handlerCount;
            if (this.localVar != null) {
                this.cw.newUTF8("LocalVariableTable");
                n += 8 + this.localVar.length;
            }
            if (this.localVarType != null) {
                this.cw.newUTF8("LocalVariableTypeTable");
                n += 8 + this.localVarType.length;
            }
            if (this.lineNumber != null) {
                this.cw.newUTF8("LineNumberTable");
                n += 8 + this.lineNumber.length;
            }
            if (this.stackMap != null) {
                this.cw.newUTF8(((this.cw.version & 0xFFFF) >= 50) ? "StackMapTable" : "StackMap");
                n += 8 + this.stackMap.length;
            }
            if (this.ctanns != null) {
                this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
                n += 8 + this.ctanns.getSize();
            }
            if (this.ictanns != null) {
                this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
                n += 8 + this.ictanns.getSize();
            }
            if (this.cattrs != null) {
                n += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
            }
        }
        if (this.exceptionCount > 0) {
            this.cw.newUTF8("Exceptions");
            n += 8 + 2 * this.exceptionCount;
        }
        if ((this.access & 0x1000) != 0x0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0x0)) {
            this.cw.newUTF8("Synthetic");
            n += 6;
        }
        if ((this.access & 0x20000) != 0x0) {
            this.cw.newUTF8("Deprecated");
            n += 6;
        }
        if (this.signature != null) {
            this.cw.newUTF8("Signature");
            this.cw.newUTF8(this.signature);
            n += 8;
        }
        if (this.methodParameters != null) {
            this.cw.newUTF8("MethodParameters");
            n += 7 + this.methodParameters.length;
        }
        if (this.annd != null) {
            this.cw.newUTF8("AnnotationDefault");
            n += 6 + this.annd.length;
        }
        if (this.anns != null) {
            this.cw.newUTF8("RuntimeVisibleAnnotations");
            n += 8 + this.anns.getSize();
        }
        if (this.ianns != null) {
            this.cw.newUTF8("RuntimeInvisibleAnnotations");
            n += 8 + this.ianns.getSize();
        }
        if (this.tanns != null) {
            this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
            n += 8 + this.tanns.getSize();
        }
        if (this.itanns != null) {
            this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
            n += 8 + this.itanns.getSize();
        }
        if (this.panns != null) {
            this.cw.newUTF8("RuntimeVisibleParameterAnnotations");
            n += 7 + 2 * (this.panns.length - this.synthetics);
            for (int i = this.panns.length - 1; i >= this.synthetics; --i) {
                n += ((this.panns[i] == null) ? 0 : this.panns[i].getSize());
            }
        }
        if (this.ipanns != null) {
            this.cw.newUTF8("RuntimeInvisibleParameterAnnotations");
            n += 7 + 2 * (this.ipanns.length - this.synthetics);
            for (int j = this.ipanns.length - 1; j >= this.synthetics; --j) {
                n += ((this.ipanns[j] == null) ? 0 : this.ipanns[j].getSize());
            }
        }
        if (this.attrs != null) {
            n += this.attrs.getSize(this.cw, null, 0, -1, -1);
        }
        return n;
    }
    
    final void put(final ByteVector byteVector) {
        byteVector.putShort(this.access & ~(0xE0000 | (this.access & 0x40000) / 64)).putShort(this.name).putShort(this.desc);
        if (this.classReaderOffset != 0) {
            byteVector.putByteArray(this.cw.cr.b, this.classReaderOffset, this.classReaderLength);
            return;
        }
        int n = 0;
        if (this.code.length > 0) {
            ++n;
        }
        if (this.exceptionCount > 0) {
            ++n;
        }
        if ((this.access & 0x1000) != 0x0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0x0)) {
            ++n;
        }
        if ((this.access & 0x20000) != 0x0) {
            ++n;
        }
        if (this.signature != null) {
            ++n;
        }
        if (this.methodParameters != null) {
            ++n;
        }
        if (this.annd != null) {
            ++n;
        }
        if (this.anns != null) {
            ++n;
        }
        if (this.ianns != null) {
            ++n;
        }
        if (this.tanns != null) {
            ++n;
        }
        if (this.itanns != null) {
            ++n;
        }
        if (this.panns != null) {
            ++n;
        }
        if (this.ipanns != null) {
            ++n;
        }
        if (this.attrs != null) {
            n += this.attrs.getCount();
        }
        byteVector.putShort(n);
        if (this.code.length > 0) {
            int n2 = 12 + this.code.length + 8 * this.handlerCount;
            if (this.localVar != null) {
                n2 += 8 + this.localVar.length;
            }
            if (this.localVarType != null) {
                n2 += 8 + this.localVarType.length;
            }
            if (this.lineNumber != null) {
                n2 += 8 + this.lineNumber.length;
            }
            if (this.stackMap != null) {
                n2 += 8 + this.stackMap.length;
            }
            if (this.ctanns != null) {
                n2 += 8 + this.ctanns.getSize();
            }
            if (this.ictanns != null) {
                n2 += 8 + this.ictanns.getSize();
            }
            if (this.cattrs != null) {
                n2 += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
            }
            byteVector.putShort(this.cw.newUTF8("Code")).putInt(n2);
            byteVector.putShort(this.maxStack).putShort(this.maxLocals);
            byteVector.putInt(this.code.length).putByteArray(this.code.data, 0, this.code.length);
            byteVector.putShort(this.handlerCount);
            if (this.handlerCount > 0) {
                for (Handler handler = this.firstHandler; handler != null; handler = handler.next) {
                    byteVector.putShort(handler.start.position).putShort(handler.end.position).putShort(handler.handler.position).putShort(handler.type);
                }
            }
            int n3 = 0;
            if (this.localVar != null) {
                ++n3;
            }
            if (this.localVarType != null) {
                ++n3;
            }
            if (this.lineNumber != null) {
                ++n3;
            }
            if (this.stackMap != null) {
                ++n3;
            }
            if (this.ctanns != null) {
                ++n3;
            }
            if (this.ictanns != null) {
                ++n3;
            }
            if (this.cattrs != null) {
                n3 += this.cattrs.getCount();
            }
            byteVector.putShort(n3);
            if (this.localVar != null) {
                byteVector.putShort(this.cw.newUTF8("LocalVariableTable"));
                byteVector.putInt(this.localVar.length + 2).putShort(this.localVarCount);
                byteVector.putByteArray(this.localVar.data, 0, this.localVar.length);
            }
            if (this.localVarType != null) {
                byteVector.putShort(this.cw.newUTF8("LocalVariableTypeTable"));
                byteVector.putInt(this.localVarType.length + 2).putShort(this.localVarTypeCount);
                byteVector.putByteArray(this.localVarType.data, 0, this.localVarType.length);
            }
            if (this.lineNumber != null) {
                byteVector.putShort(this.cw.newUTF8("LineNumberTable"));
                byteVector.putInt(this.lineNumber.length + 2).putShort(this.lineNumberCount);
                byteVector.putByteArray(this.lineNumber.data, 0, this.lineNumber.length);
            }
            if (this.stackMap != null) {
                byteVector.putShort(this.cw.newUTF8(((this.cw.version & 0xFFFF) >= 50) ? "StackMapTable" : "StackMap"));
                byteVector.putInt(this.stackMap.length + 2).putShort(this.frameCount);
                byteVector.putByteArray(this.stackMap.data, 0, this.stackMap.length);
            }
            if (this.ctanns != null) {
                byteVector.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
                this.ctanns.put(byteVector);
            }
            if (this.ictanns != null) {
                byteVector.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
                this.ictanns.put(byteVector);
            }
            if (this.cattrs != null) {
                this.cattrs.put(this.cw, this.code.data, this.code.length, this.maxLocals, this.maxStack, byteVector);
            }
        }
        if (this.exceptionCount > 0) {
            byteVector.putShort(this.cw.newUTF8("Exceptions")).putInt(2 * this.exceptionCount + 2);
            byteVector.putShort(this.exceptionCount);
            for (int i = 0; i < this.exceptionCount; ++i) {
                byteVector.putShort(this.exceptions[i]);
            }
        }
        if ((this.access & 0x1000) != 0x0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0x0)) {
            byteVector.putShort(this.cw.newUTF8("Synthetic")).putInt(0);
        }
        if ((this.access & 0x20000) != 0x0) {
            byteVector.putShort(this.cw.newUTF8("Deprecated")).putInt(0);
        }
        if (this.signature != null) {
            byteVector.putShort(this.cw.newUTF8("Signature")).putInt(2).putShort(this.cw.newUTF8(this.signature));
        }
        if (this.methodParameters != null) {
            byteVector.putShort(this.cw.newUTF8("MethodParameters"));
            byteVector.putInt(this.methodParameters.length + 1).putByte(this.methodParametersCount);
            byteVector.putByteArray(this.methodParameters.data, 0, this.methodParameters.length);
        }
        if (this.annd != null) {
            byteVector.putShort(this.cw.newUTF8("AnnotationDefault"));
            byteVector.putInt(this.annd.length);
            byteVector.putByteArray(this.annd.data, 0, this.annd.length);
        }
        if (this.anns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeVisibleAnnotations"));
            this.anns.put(byteVector);
        }
        if (this.ianns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeInvisibleAnnotations"));
            this.ianns.put(byteVector);
        }
        if (this.tanns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.tanns.put(byteVector);
        }
        if (this.itanns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.itanns.put(byteVector);
        }
        if (this.panns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeVisibleParameterAnnotations"));
            AnnotationWriter.put(this.panns, this.synthetics, byteVector);
        }
        if (this.ipanns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeInvisibleParameterAnnotations"));
            AnnotationWriter.put(this.ipanns, this.synthetics, byteVector);
        }
        if (this.attrs != null) {
            this.attrs.put(this.cw, null, 0, -1, -1, byteVector);
        }
    }
}
