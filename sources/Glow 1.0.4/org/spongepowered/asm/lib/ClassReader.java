package org.spongepowered.asm.lib;

import java.io.*;

public class ClassReader
{
    static final boolean SIGNATURES = true;
    static final boolean ANNOTATIONS = true;
    static final boolean FRAMES = true;
    static final boolean WRITER = true;
    static final boolean RESIZE = true;
    public static final int SKIP_CODE = 1;
    public static final int SKIP_DEBUG = 2;
    public static final int SKIP_FRAMES = 4;
    public static final int EXPAND_FRAMES = 8;
    static final int EXPAND_ASM_INSNS = 256;
    public final byte[] b;
    private final int[] items;
    private final String[] strings;
    private final int maxStringLength;
    public final int header;
    
    public ClassReader(final byte[] array) {
        this(array, 0, array.length);
    }
    
    public ClassReader(final byte[] b, final int n, final int n2) {
        super();
        this.b = b;
        if (this.readShort(n + 6) > 52) {
            throw new IllegalArgumentException();
        }
        this.items = new int[this.readUnsignedShort(n + 8)];
        final int length = this.items.length;
        this.strings = new String[length];
        int maxStringLength = 0;
        int header = n + 10;
        for (int i = 1; i < length; ++i) {
            this.items[i] = header + 1;
            int n3 = 0;
            switch (b[header]) {
                case 3:
                case 4:
                case 9:
                case 10:
                case 11:
                case 12:
                case 18: {
                    n3 = 5;
                    break;
                }
                case 5:
                case 6: {
                    n3 = 9;
                    ++i;
                    break;
                }
                case 1: {
                    n3 = 3 + this.readUnsignedShort(header + 1);
                    if (n3 > maxStringLength) {
                        maxStringLength = n3;
                        break;
                    }
                    break;
                }
                case 15: {
                    n3 = 4;
                    break;
                }
                default: {
                    n3 = 3;
                    break;
                }
            }
            header += n3;
        }
        this.maxStringLength = maxStringLength;
        this.header = header;
    }
    
    public int getAccess() {
        return this.readUnsignedShort(this.header);
    }
    
    public String getClassName() {
        return this.readClass(this.header + 2, new char[this.maxStringLength]);
    }
    
    public String getSuperName() {
        return this.readClass(this.header + 4, new char[this.maxStringLength]);
    }
    
    public String[] getInterfaces() {
        int n = this.header + 6;
        final int unsignedShort = this.readUnsignedShort(n);
        final String[] array = new String[unsignedShort];
        if (unsignedShort > 0) {
            final char[] array2 = new char[this.maxStringLength];
            for (int i = 0; i < unsignedShort; ++i) {
                n += 2;
                array[i] = this.readClass(n, array2);
            }
        }
        return array;
    }
    
    void copyPool(final ClassWriter classWriter) {
        final char[] array = new char[this.maxStringLength];
        final int length = this.items.length;
        final Item[] items = new Item[length];
        for (int i = 1; i < length; ++i) {
            final int n = this.items[i];
            final byte b = this.b[n - 1];
            final Item item = new Item(i);
            switch (b) {
                case 9:
                case 10:
                case 11: {
                    final int n2 = this.items[this.readUnsignedShort(n + 2)];
                    item.set(b, this.readClass(n, array), this.readUTF8(n2, array), this.readUTF8(n2 + 2, array));
                    break;
                }
                case 3: {
                    item.set(this.readInt(n));
                    break;
                }
                case 4: {
                    item.set(Float.intBitsToFloat(this.readInt(n)));
                    break;
                }
                case 12: {
                    item.set(b, this.readUTF8(n, array), this.readUTF8(n + 2, array), null);
                    break;
                }
                case 5: {
                    item.set(this.readLong(n));
                    ++i;
                    break;
                }
                case 6: {
                    item.set(Double.longBitsToDouble(this.readLong(n)));
                    ++i;
                    break;
                }
                case 1: {
                    String s = this.strings[i];
                    if (s == null) {
                        final int n3 = this.items[i];
                        final String[] strings = this.strings;
                        final int n4 = i;
                        final String utf = this.readUTF(n3 + 2, this.readUnsignedShort(n3), array);
                        strings[n4] = utf;
                        s = utf;
                    }
                    item.set(b, s, null, null);
                    break;
                }
                case 15: {
                    final int n5 = this.items[this.readUnsignedShort(n + 1)];
                    final int n6 = this.items[this.readUnsignedShort(n5 + 2)];
                    item.set(20 + this.readByte(n), this.readClass(n5, array), this.readUTF8(n6, array), this.readUTF8(n6 + 2, array));
                    break;
                }
                case 18: {
                    if (classWriter.bootstrapMethods == null) {
                        this.copyBootstrapMethods(classWriter, items, array);
                    }
                    final int n7 = this.items[this.readUnsignedShort(n + 2)];
                    item.set(this.readUTF8(n7, array), this.readUTF8(n7 + 2, array), this.readUnsignedShort(n));
                    break;
                }
                default: {
                    item.set(b, this.readUTF8(n, array), null, null);
                    break;
                }
            }
            final int n8 = item.hashCode % items.length;
            item.next = items[n8];
            items[n8] = item;
        }
        final int n9 = this.items[1] - 1;
        classWriter.pool.putByteArray(this.b, n9, this.header - n9);
        classWriter.items = items;
        classWriter.threshold = (int)(0.75 * length);
        classWriter.index = length;
    }
    
    private void copyBootstrapMethods(final ClassWriter classWriter, final Item[] array, final char[] array2) {
        int attributes = this.getAttributes();
        boolean b = false;
        for (int i = this.readUnsignedShort(attributes); i > 0; --i) {
            if ("BootstrapMethods".equals(this.readUTF8(attributes + 2, array2))) {
                b = true;
                break;
            }
            attributes += 6 + this.readInt(attributes + 4);
        }
        if (!b) {
            return;
        }
        final int unsignedShort = this.readUnsignedShort(attributes + 8);
        int j = 0;
        int n = attributes + 10;
        while (j < unsignedShort) {
            final int n2 = n - attributes - 10;
            int hashCode = this.readConst(this.readUnsignedShort(n), array2).hashCode();
            for (int k = this.readUnsignedShort(n + 2); k > 0; --k) {
                hashCode ^= this.readConst(this.readUnsignedShort(n + 4), array2).hashCode();
                n += 2;
            }
            n += 4;
            final Item item = new Item(j);
            item.set(n2, hashCode & 0x0);
            final int n3 = item.hashCode % array.length;
            item.next = array[n3];
            array[n3] = item;
            ++j;
        }
        final int int1 = this.readInt(attributes + 4);
        final ByteVector bootstrapMethods = new ByteVector(int1 + 62);
        bootstrapMethods.putByteArray(this.b, attributes + 10, int1 - 2);
        classWriter.bootstrapMethodsCount = unsignedShort;
        classWriter.bootstrapMethods = bootstrapMethods;
    }
    
    public ClassReader(final InputStream inputStream) throws IOException {
        this(readClass(inputStream, false));
    }
    
    public ClassReader(final String s) throws IOException {
        this(readClass(ClassLoader.getSystemResourceAsStream(s.replace('.', '/') + ".class"), true));
    }
    
    private static byte[] readClass(final InputStream inputStream, final boolean b) throws IOException {
        if (inputStream == null) {
            throw new IOException("Class not found");
        }
        try {
            byte[] array = new byte[inputStream.available()];
            int n = 0;
            while (true) {
                final int read = inputStream.read(array, n, array.length - n);
                if (read == -1) {
                    if (n < array.length) {
                        final byte[] array2 = new byte[n];
                        System.arraycopy(array, 0, array2, 0, n);
                        array = array2;
                    }
                    return array;
                }
                n += read;
                if (n != array.length) {
                    continue;
                }
                final int read2 = inputStream.read();
                if (read2 < 0) {
                    return array;
                }
                final byte[] array3 = new byte[array.length + 1000];
                System.arraycopy(array, 0, array3, 0, n);
                array3[n++] = (byte)read2;
                array = array3;
            }
        }
        finally {
            if (b) {
                inputStream.close();
            }
        }
    }
    
    public void accept(final ClassVisitor classVisitor, final int n) {
        this.accept(classVisitor, new Attribute[0], n);
    }
    
    public void accept(final ClassVisitor classVisitor, final Attribute[] attrs, final int flags) {
        int header = this.header;
        final char[] buffer = new char[this.maxStringLength];
        final Context context = new Context();
        context.attrs = attrs;
        context.flags = flags;
        context.buffer = buffer;
        int unsignedShort = this.readUnsignedShort(header);
        final String class1 = this.readClass(header + 2, buffer);
        final String class2 = this.readClass(header + 4, buffer);
        final String[] array = new String[this.readUnsignedShort(header + 6)];
        header += 8;
        for (int i = 0; i < array.length; ++i) {
            array[i] = this.readClass(header, buffer);
            header += 2;
        }
        String utf8 = null;
        String utf9 = null;
        String utf10 = null;
        String class3 = null;
        String utf11 = null;
        String utf12 = null;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        Attribute next = null;
        int attributes = this.getAttributes();
        for (int j = this.readUnsignedShort(attributes); j > 0; --j) {
            final String utf13 = this.readUTF8(attributes + 2, buffer);
            if ("SourceFile".equals(utf13)) {
                utf9 = this.readUTF8(attributes + 8, buffer);
            }
            else if ("InnerClasses".equals(utf13)) {
                n5 = attributes + 8;
            }
            else if ("EnclosingMethod".equals(utf13)) {
                class3 = this.readClass(attributes + 8, buffer);
                final int unsignedShort2 = this.readUnsignedShort(attributes + 10);
                if (unsignedShort2 != 0) {
                    utf11 = this.readUTF8(this.items[unsignedShort2], buffer);
                    utf12 = this.readUTF8(this.items[unsignedShort2] + 2, buffer);
                }
            }
            else if ("Signature".equals(utf13)) {
                utf8 = this.readUTF8(attributes + 8, buffer);
            }
            else if ("RuntimeVisibleAnnotations".equals(utf13)) {
                n = attributes + 8;
            }
            else if ("RuntimeVisibleTypeAnnotations".equals(utf13)) {
                n3 = attributes + 8;
            }
            else if ("Deprecated".equals(utf13)) {
                unsignedShort |= 0x20000;
            }
            else if ("Synthetic".equals(utf13)) {
                unsignedShort |= 0x41000;
            }
            else if ("SourceDebugExtension".equals(utf13)) {
                final int int1 = this.readInt(attributes + 4);
                utf10 = this.readUTF(attributes + 8, int1, new char[int1]);
            }
            else if ("RuntimeInvisibleAnnotations".equals(utf13)) {
                n2 = attributes + 8;
            }
            else if ("RuntimeInvisibleTypeAnnotations".equals(utf13)) {
                n4 = attributes + 8;
            }
            else if ("BootstrapMethods".equals(utf13)) {
                final int[] bootstrapMethods = new int[this.readUnsignedShort(attributes + 8)];
                int k = 0;
                int n6 = attributes + 10;
                while (k < bootstrapMethods.length) {
                    bootstrapMethods[k] = n6;
                    n6 += 2 + this.readUnsignedShort(n6 + 2) << 1;
                    ++k;
                }
                context.bootstrapMethods = bootstrapMethods;
            }
            else {
                final Attribute attribute = this.readAttribute(attrs, utf13, attributes + 8, this.readInt(attributes + 4), buffer, -1, null);
                if (attribute != null) {
                    attribute.next = next;
                    next = attribute;
                }
            }
            attributes += 6 + this.readInt(attributes + 4);
        }
        classVisitor.visit(this.readInt(this.items[1] - 7), unsignedShort, class1, utf8, class2, array);
        if ((flags & 0x2) == 0x0 && (utf9 != null || utf10 != null)) {
            classVisitor.visitSource(utf9, utf10);
        }
        if (class3 != null) {
            classVisitor.visitOuterClass(class3, utf11, utf12);
        }
        if (n != 0) {
            int l = this.readUnsignedShort(n);
            int annotationValues = n + 2;
            while (l > 0) {
                annotationValues = this.readAnnotationValues(annotationValues + 2, buffer, true, classVisitor.visitAnnotation(this.readUTF8(annotationValues, buffer), true));
                --l;
            }
        }
        if (n2 != 0) {
            int unsignedShort3 = this.readUnsignedShort(n2);
            int annotationValues2 = n2 + 2;
            while (unsignedShort3 > 0) {
                annotationValues2 = this.readAnnotationValues(annotationValues2 + 2, buffer, true, classVisitor.visitAnnotation(this.readUTF8(annotationValues2, buffer), false));
                --unsignedShort3;
            }
        }
        if (n3 != 0) {
            int unsignedShort4 = this.readUnsignedShort(n3);
            int annotationValues3 = n3 + 2;
            while (unsignedShort4 > 0) {
                final int annotationTarget = this.readAnnotationTarget(context, annotationValues3);
                annotationValues3 = this.readAnnotationValues(annotationTarget + 2, buffer, true, classVisitor.visitTypeAnnotation(context.typeRef, context.typePath, this.readUTF8(annotationTarget, buffer), true));
                --unsignedShort4;
            }
        }
        if (n4 != 0) {
            int unsignedShort5 = this.readUnsignedShort(n4);
            int annotationValues4 = n4 + 2;
            while (unsignedShort5 > 0) {
                final int annotationTarget2 = this.readAnnotationTarget(context, annotationValues4);
                annotationValues4 = this.readAnnotationValues(annotationTarget2 + 2, buffer, true, classVisitor.visitTypeAnnotation(context.typeRef, context.typePath, this.readUTF8(annotationTarget2, buffer), false));
                --unsignedShort5;
            }
        }
        while (next != null) {
            final Attribute next2 = next.next;
            next.next = null;
            classVisitor.visitAttribute(next);
            next = next2;
        }
        if (n5 != 0) {
            int n7 = n5 + 2;
            for (int unsignedShort6 = this.readUnsignedShort(n5); unsignedShort6 > 0; --unsignedShort6) {
                classVisitor.visitInnerClass(this.readClass(n7, buffer), this.readClass(n7 + 2, buffer), this.readUTF8(n7 + 4, buffer), this.readUnsignedShort(n7 + 6));
                n7 += 8;
            }
        }
        int n8 = this.header + 10 + 2 * array.length;
        for (int unsignedShort7 = this.readUnsignedShort(n8 - 2); unsignedShort7 > 0; --unsignedShort7) {
            n8 = this.readField(classVisitor, context, n8);
        }
        n8 += 2;
        for (int unsignedShort8 = this.readUnsignedShort(n8 - 2); unsignedShort8 > 0; --unsignedShort8) {
            n8 = this.readMethod(classVisitor, context, n8);
        }
        classVisitor.visitEnd();
    }
    
    private int readField(final ClassVisitor classVisitor, final Context context, int n) {
        final char[] buffer = context.buffer;
        int unsignedShort = this.readUnsignedShort(n);
        final String utf8 = this.readUTF8(n + 2, buffer);
        final String utf9 = this.readUTF8(n + 4, buffer);
        n += 6;
        String utf10 = null;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        Object o = null;
        Attribute next = null;
        for (int i = this.readUnsignedShort(n); i > 0; --i) {
            final String utf11 = this.readUTF8(n + 2, buffer);
            if ("ConstantValue".equals(utf11)) {
                final int unsignedShort2 = this.readUnsignedShort(n + 8);
                o = ((unsignedShort2 == 0) ? null : this.readConst(unsignedShort2, buffer));
            }
            else if ("Signature".equals(utf11)) {
                utf10 = this.readUTF8(n + 8, buffer);
            }
            else if ("Deprecated".equals(utf11)) {
                unsignedShort |= 0x20000;
            }
            else if ("Synthetic".equals(utf11)) {
                unsignedShort |= 0x41000;
            }
            else if ("RuntimeVisibleAnnotations".equals(utf11)) {
                n2 = n + 8;
            }
            else if ("RuntimeVisibleTypeAnnotations".equals(utf11)) {
                n4 = n + 8;
            }
            else if ("RuntimeInvisibleAnnotations".equals(utf11)) {
                n3 = n + 8;
            }
            else if ("RuntimeInvisibleTypeAnnotations".equals(utf11)) {
                n5 = n + 8;
            }
            else {
                final Attribute attribute = this.readAttribute(context.attrs, utf11, n + 8, this.readInt(n + 4), buffer, -1, null);
                if (attribute != null) {
                    attribute.next = next;
                    next = attribute;
                }
            }
            n += 6 + this.readInt(n + 4);
        }
        n += 2;
        final FieldVisitor visitField = classVisitor.visitField(unsignedShort, utf8, utf9, utf10, o);
        if (visitField == null) {
            return n;
        }
        if (n2 != 0) {
            int j = this.readUnsignedShort(n2);
            int annotationValues = n2 + 2;
            while (j > 0) {
                annotationValues = this.readAnnotationValues(annotationValues + 2, buffer, true, visitField.visitAnnotation(this.readUTF8(annotationValues, buffer), true));
                --j;
            }
        }
        if (n3 != 0) {
            int k = this.readUnsignedShort(n3);
            int annotationValues2 = n3 + 2;
            while (k > 0) {
                annotationValues2 = this.readAnnotationValues(annotationValues2 + 2, buffer, true, visitField.visitAnnotation(this.readUTF8(annotationValues2, buffer), false));
                --k;
            }
        }
        if (n4 != 0) {
            int l = this.readUnsignedShort(n4);
            int annotationValues3 = n4 + 2;
            while (l > 0) {
                final int annotationTarget = this.readAnnotationTarget(context, annotationValues3);
                annotationValues3 = this.readAnnotationValues(annotationTarget + 2, buffer, true, visitField.visitTypeAnnotation(context.typeRef, context.typePath, this.readUTF8(annotationTarget, buffer), true));
                --l;
            }
        }
        if (n5 != 0) {
            int unsignedShort3 = this.readUnsignedShort(n5);
            int annotationValues4 = n5 + 2;
            while (unsignedShort3 > 0) {
                final int annotationTarget2 = this.readAnnotationTarget(context, annotationValues4);
                annotationValues4 = this.readAnnotationValues(annotationTarget2 + 2, buffer, true, visitField.visitTypeAnnotation(context.typeRef, context.typePath, this.readUTF8(annotationTarget2, buffer), false));
                --unsignedShort3;
            }
        }
        while (next != null) {
            final Attribute next2 = next.next;
            next.next = null;
            visitField.visitAttribute(next);
            next = next2;
        }
        visitField.visitEnd();
        return n;
    }
    
    private int readMethod(final ClassVisitor classVisitor, final Context context, int n) {
        final char[] buffer = context.buffer;
        context.access = this.readUnsignedShort(n);
        context.name = this.readUTF8(n + 2, buffer);
        context.desc = this.readUTF8(n + 4, buffer);
        n += 6;
        int n2 = 0;
        int n3 = 0;
        String[] array = null;
        String utf8 = null;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        final int classReaderOffset = n;
        Attribute next = null;
        for (int i = this.readUnsignedShort(n); i > 0; --i) {
            final String utf9 = this.readUTF8(n + 2, buffer);
            if ("Code".equals(utf9)) {
                if ((context.flags & 0x1) == 0x0) {
                    n2 = n + 8;
                }
            }
            else if ("Exceptions".equals(utf9)) {
                array = new String[this.readUnsignedShort(n + 8)];
                n3 = n + 10;
                for (int j = 0; j < array.length; ++j) {
                    array[j] = this.readClass(n3, buffer);
                    n3 += 2;
                }
            }
            else if ("Signature".equals(utf9)) {
                utf8 = this.readUTF8(n + 8, buffer);
            }
            else if ("Deprecated".equals(utf9)) {
                context.access |= 0x20000;
            }
            else if ("RuntimeVisibleAnnotations".equals(utf9)) {
                n5 = n + 8;
            }
            else if ("RuntimeVisibleTypeAnnotations".equals(utf9)) {
                n7 = n + 8;
            }
            else if ("AnnotationDefault".equals(utf9)) {
                n9 = n + 8;
            }
            else if ("Synthetic".equals(utf9)) {
                context.access |= 0x41000;
            }
            else if ("RuntimeInvisibleAnnotations".equals(utf9)) {
                n6 = n + 8;
            }
            else if ("RuntimeInvisibleTypeAnnotations".equals(utf9)) {
                n8 = n + 8;
            }
            else if ("RuntimeVisibleParameterAnnotations".equals(utf9)) {
                n10 = n + 8;
            }
            else if ("RuntimeInvisibleParameterAnnotations".equals(utf9)) {
                n11 = n + 8;
            }
            else if ("MethodParameters".equals(utf9)) {
                n4 = n + 8;
            }
            else {
                final Attribute attribute = this.readAttribute(context.attrs, utf9, n + 8, this.readInt(n + 4), buffer, -1, null);
                if (attribute != null) {
                    attribute.next = next;
                    next = attribute;
                }
            }
            n += 6 + this.readInt(n + 4);
        }
        n += 2;
        final MethodVisitor visitMethod = classVisitor.visitMethod(context.access, context.name, context.desc, utf8, array);
        if (visitMethod == null) {
            return n;
        }
        if (visitMethod instanceof MethodWriter) {
            final MethodWriter methodWriter = (MethodWriter)visitMethod;
            if (methodWriter.cw.cr == this && utf8 == methodWriter.signature) {
                int n12 = 0;
                if (array == null) {
                    n12 = ((methodWriter.exceptionCount == 0) ? 1 : 0);
                }
                else if (array.length == methodWriter.exceptionCount) {
                    n12 = 1;
                    for (int k = array.length - 1; k >= 0; --k) {
                        n3 -= 2;
                        if (methodWriter.exceptions[k] != this.readUnsignedShort(n3)) {
                            n12 = 0;
                            break;
                        }
                    }
                }
                if (n12 != 0) {
                    methodWriter.classReaderOffset = classReaderOffset;
                    methodWriter.classReaderLength = n - classReaderOffset;
                    return n;
                }
            }
        }
        if (n4 != 0) {
            for (int l = this.b[n4] & 0xFF, n13 = n4 + 1; l > 0; --l, n13 += 4) {
                visitMethod.visitParameter(this.readUTF8(n13, buffer), this.readUnsignedShort(n13 + 2));
            }
        }
        if (n9 != 0) {
            final AnnotationVisitor visitAnnotationDefault = visitMethod.visitAnnotationDefault();
            this.readAnnotationValue(n9, buffer, null, visitAnnotationDefault);
            if (visitAnnotationDefault != null) {
                visitAnnotationDefault.visitEnd();
            }
        }
        if (n5 != 0) {
            int unsignedShort = this.readUnsignedShort(n5);
            int annotationValues = n5 + 2;
            while (unsignedShort > 0) {
                annotationValues = this.readAnnotationValues(annotationValues + 2, buffer, true, visitMethod.visitAnnotation(this.readUTF8(annotationValues, buffer), true));
                --unsignedShort;
            }
        }
        if (n6 != 0) {
            int unsignedShort2 = this.readUnsignedShort(n6);
            int annotationValues2 = n6 + 2;
            while (unsignedShort2 > 0) {
                annotationValues2 = this.readAnnotationValues(annotationValues2 + 2, buffer, true, visitMethod.visitAnnotation(this.readUTF8(annotationValues2, buffer), false));
                --unsignedShort2;
            }
        }
        if (n7 != 0) {
            int unsignedShort3 = this.readUnsignedShort(n7);
            int annotationValues3 = n7 + 2;
            while (unsignedShort3 > 0) {
                final int annotationTarget = this.readAnnotationTarget(context, annotationValues3);
                annotationValues3 = this.readAnnotationValues(annotationTarget + 2, buffer, true, visitMethod.visitTypeAnnotation(context.typeRef, context.typePath, this.readUTF8(annotationTarget, buffer), true));
                --unsignedShort3;
            }
        }
        if (n8 != 0) {
            int unsignedShort4 = this.readUnsignedShort(n8);
            int annotationValues4 = n8 + 2;
            while (unsignedShort4 > 0) {
                final int annotationTarget2 = this.readAnnotationTarget(context, annotationValues4);
                annotationValues4 = this.readAnnotationValues(annotationTarget2 + 2, buffer, true, visitMethod.visitTypeAnnotation(context.typeRef, context.typePath, this.readUTF8(annotationTarget2, buffer), false));
                --unsignedShort4;
            }
        }
        if (n10 != 0) {
            this.readParameterAnnotations(visitMethod, context, n10, true);
        }
        if (n11 != 0) {
            this.readParameterAnnotations(visitMethod, context, n11, false);
        }
        while (next != null) {
            final Attribute next2 = next.next;
            next.next = null;
            visitMethod.visitAttribute(next);
            next = next2;
        }
        if (n2 != 0) {
            visitMethod.visitCode();
            this.readCode(visitMethod, context, n2);
        }
        visitMethod.visitEnd();
        return n;
    }
    
    private void readCode(final MethodVisitor methodVisitor, final Context context, int i) {
        final byte[] b = this.b;
        final char[] buffer = context.buffer;
        final int unsignedShort = this.readUnsignedShort(i);
        final int unsignedShort2 = this.readUnsignedShort(i + 2);
        final int int1 = this.readInt(i + 4);
        i += 8;
        final int n = i;
        final int n2 = i + int1;
        final Label[] labels = new Label[int1 + 2];
        context.labels = labels;
        final Label[] array = labels;
        this.readLabel(int1 + 1, array);
        while (i < n2) {
            final int n3 = i - n;
            switch (ClassWriter.TYPE[b[i] & 0xFF]) {
                case 0:
                case 4: {
                    ++i;
                    continue;
                }
                case 9: {
                    this.readLabel(n3 + this.readShort(i + 1), array);
                    i += 3;
                    continue;
                }
                case 18: {
                    this.readLabel(n3 + this.readUnsignedShort(i + 1), array);
                    i += 3;
                    continue;
                }
                case 10: {
                    this.readLabel(n3 + this.readInt(i + 1), array);
                    i += 5;
                    continue;
                }
                case 17: {
                    if ((b[i + 1] & 0xFF) == 0x84) {
                        i += 6;
                        continue;
                    }
                    i += 4;
                    continue;
                }
                case 14: {
                    i = i + 4 - (n3 & 0x3);
                    this.readLabel(n3 + this.readInt(i), array);
                    for (int j = this.readInt(i + 8) - this.readInt(i + 4) + 1; j > 0; --j) {
                        this.readLabel(n3 + this.readInt(i + 12), array);
                        i += 4;
                    }
                    i += 12;
                    continue;
                }
                case 15: {
                    i = i + 4 - (n3 & 0x3);
                    this.readLabel(n3 + this.readInt(i), array);
                    for (int k = this.readInt(i + 4); k > 0; --k) {
                        this.readLabel(n3 + this.readInt(i + 12), array);
                        i += 8;
                    }
                    i += 8;
                    continue;
                }
                case 1:
                case 3:
                case 11: {
                    i += 2;
                    continue;
                }
                case 2:
                case 5:
                case 6:
                case 12:
                case 13: {
                    i += 3;
                    continue;
                }
                case 7:
                case 8: {
                    i += 5;
                    continue;
                }
                default: {
                    i += 4;
                    continue;
                }
            }
        }
        for (int l = this.readUnsignedShort(i); l > 0; --l) {
            methodVisitor.visitTryCatchBlock(this.readLabel(this.readUnsignedShort(i + 2), array), this.readLabel(this.readUnsignedShort(i + 4), array), this.readLabel(this.readUnsignedShort(i + 6), array), this.readUTF8(this.items[this.readUnsignedShort(i + 8)], buffer));
            i += 8;
        }
        i += 2;
        int[] typeAnnotations = null;
        int[] typeAnnotations2 = null;
        int n4 = 0;
        int n5 = 0;
        int n6 = -1;
        int n7 = -1;
        int n8 = 0;
        int n9 = 0;
        boolean b2 = true;
        final boolean b3 = (context.flags & 0x8) != 0x0;
        int frame = 0;
        int n10 = 0;
        int n11 = 0;
        Context context2 = null;
        Attribute next = null;
        for (int unsignedShort3 = this.readUnsignedShort(i); unsignedShort3 > 0; --unsignedShort3) {
            final String utf8 = this.readUTF8(i + 2, buffer);
            if ("LocalVariableTable".equals(utf8)) {
                if ((context.flags & 0x2) == 0x0) {
                    n8 = i + 8;
                    int unsignedShort4 = this.readUnsignedShort(i + 8);
                    int n12 = i;
                    while (unsignedShort4 > 0) {
                        final int unsignedShort5 = this.readUnsignedShort(n12 + 10);
                        if (array[unsignedShort5] == null) {
                            final Label label = this.readLabel(unsignedShort5, array);
                            label.status |= 0x1;
                        }
                        final int n13 = unsignedShort5 + this.readUnsignedShort(n12 + 12);
                        if (array[n13] == null) {
                            final Label label2 = this.readLabel(n13, array);
                            label2.status |= 0x1;
                        }
                        n12 += 10;
                        --unsignedShort4;
                    }
                }
            }
            else if ("LocalVariableTypeTable".equals(utf8)) {
                n9 = i + 8;
            }
            else if ("LineNumberTable".equals(utf8)) {
                if ((context.flags & 0x2) == 0x0) {
                    int unsignedShort6 = this.readUnsignedShort(i + 8);
                    int n14 = i;
                    while (unsignedShort6 > 0) {
                        final int unsignedShort7 = this.readUnsignedShort(n14 + 10);
                        if (array[unsignedShort7] == null) {
                            final Label label3 = this.readLabel(unsignedShort7, array);
                            label3.status |= 0x1;
                        }
                        Label next2;
                        for (next2 = array[unsignedShort7]; next2.line > 0; next2 = next2.next) {
                            if (next2.next == null) {
                                next2.next = new Label();
                            }
                        }
                        next2.line = this.readUnsignedShort(n14 + 12);
                        n14 += 4;
                        --unsignedShort6;
                    }
                }
            }
            else if ("RuntimeVisibleTypeAnnotations".equals(utf8)) {
                typeAnnotations = this.readTypeAnnotations(methodVisitor, context, i + 8, true);
                n6 = ((typeAnnotations.length == 0 || this.readByte(typeAnnotations[0]) < 67) ? -1 : this.readUnsignedShort(typeAnnotations[0] + 1));
            }
            else if ("RuntimeInvisibleTypeAnnotations".equals(utf8)) {
                typeAnnotations2 = this.readTypeAnnotations(methodVisitor, context, i + 8, false);
                n7 = ((typeAnnotations2.length == 0 || this.readByte(typeAnnotations2[0]) < 67) ? -1 : this.readUnsignedShort(typeAnnotations2[0] + 1));
            }
            else if ("StackMapTable".equals(utf8)) {
                if ((context.flags & 0x4) == 0x0) {
                    frame = i + 10;
                    n10 = this.readInt(i + 4);
                    n11 = this.readUnsignedShort(i + 8);
                }
            }
            else if ("StackMap".equals(utf8)) {
                if ((context.flags & 0x4) == 0x0) {
                    b2 = false;
                    frame = i + 10;
                    n10 = this.readInt(i + 4);
                    n11 = this.readUnsignedShort(i + 8);
                }
            }
            else {
                for (int n15 = 0; n15 < context.attrs.length; ++n15) {
                    if (context.attrs[n15].type.equals(utf8)) {
                        final Attribute read = context.attrs[n15].read(this, i + 8, this.readInt(i + 4), buffer, n - 8, array);
                        if (read != null) {
                            read.next = next;
                            next = read;
                        }
                    }
                }
            }
            i += 6 + this.readInt(i + 4);
        }
        i += 2;
        if (frame != 0) {
            context2 = context;
            context2.offset = -1;
            context2.mode = 0;
            context2.localCount = 0;
            context2.localDiff = 0;
            context2.stackCount = 0;
            context2.local = new Object[unsignedShort2];
            context2.stack = new Object[unsignedShort];
            if (b3) {
                this.getImplicitFrame(context);
            }
            for (int n16 = frame; n16 < frame + n10 - 2; ++n16) {
                if (b[n16] == 8) {
                    final int unsignedShort8 = this.readUnsignedShort(n16 + 1);
                    if (unsignedShort8 >= 0 && unsignedShort8 < int1 && (b[n + unsignedShort8] & 0xFF) == 0xBB) {
                        this.readLabel(unsignedShort8, array);
                    }
                }
            }
        }
        if ((context.flags & 0x100) != 0x0) {
            methodVisitor.visitFrame(-1, unsignedShort2, null, 0, null);
        }
        final int n17 = ((context.flags & 0x100) == 0x0) ? -33 : 0;
        i = n;
        while (i < n2) {
            final int n18 = i - n;
            final Label label4 = array[n18];
            if (label4 != null) {
                Label label5 = label4.next;
                label4.next = null;
                methodVisitor.visitLabel(label4);
                if ((context.flags & 0x2) == 0x0 && label4.line > 0) {
                    methodVisitor.visitLineNumber(label4.line, label4);
                    while (label5 != null) {
                        methodVisitor.visitLineNumber(label5.line, label4);
                        label5 = label5.next;
                    }
                }
            }
            while (context2 != null && (context2.offset == n18 || context2.offset == -1)) {
                if (context2.offset != -1) {
                    if (!b2 || b3) {
                        methodVisitor.visitFrame(-1, context2.localCount, context2.local, context2.stackCount, context2.stack);
                    }
                    else {
                        methodVisitor.visitFrame(context2.mode, context2.localDiff, context2.local, context2.stackCount, context2.stack);
                    }
                }
                if (n11 > 0) {
                    frame = this.readFrame(frame, b2, b3, context2);
                    --n11;
                }
                else {
                    context2 = null;
                }
            }
            int n19 = b[i] & 0xFF;
            switch (ClassWriter.TYPE[n19]) {
                case 0: {
                    methodVisitor.visitInsn(n19);
                    ++i;
                    break;
                }
                case 4: {
                    if (n19 > 54) {
                        n19 -= 59;
                        methodVisitor.visitVarInsn(54 + (n19 >> 2), n19 & 0x3);
                    }
                    else {
                        n19 -= 26;
                        methodVisitor.visitVarInsn(21 + (n19 >> 2), n19 & 0x3);
                    }
                    ++i;
                    break;
                }
                case 9: {
                    methodVisitor.visitJumpInsn(n19, array[n18 + this.readShort(i + 1)]);
                    i += 3;
                    break;
                }
                case 10: {
                    methodVisitor.visitJumpInsn(n19 + n17, array[n18 + this.readInt(i + 1)]);
                    i += 5;
                    break;
                }
                case 18: {
                    final int n20 = (n19 < 218) ? (n19 - 49) : (n19 - 20);
                    final Label label6 = array[n18 + this.readUnsignedShort(i + 1)];
                    if (n20 == 167 || n20 == 168) {
                        methodVisitor.visitJumpInsn(n20 + 33, label6);
                    }
                    else {
                        final int n21 = (n20 <= 166) ? ((n20 + 1 ^ 0x1) - 1) : (n20 ^ 0x1);
                        final Label label7 = new Label();
                        methodVisitor.visitJumpInsn(n21, label7);
                        methodVisitor.visitJumpInsn(200, label6);
                        methodVisitor.visitLabel(label7);
                        if (frame != 0 && (context2 == null || context2.offset != n18 + 3)) {
                            methodVisitor.visitFrame(256, 0, null, 0, null);
                        }
                    }
                    i += 3;
                    break;
                }
                case 17: {
                    final int n22 = b[i + 1] & 0xFF;
                    if (n22 == 132) {
                        methodVisitor.visitIincInsn(this.readUnsignedShort(i + 2), this.readShort(i + 4));
                        i += 6;
                        break;
                    }
                    methodVisitor.visitVarInsn(n22, this.readUnsignedShort(i + 2));
                    i += 4;
                    break;
                }
                case 14: {
                    i = i + 4 - (n18 & 0x3);
                    final int n23 = n18 + this.readInt(i);
                    final int int2 = this.readInt(i + 4);
                    final int int3 = this.readInt(i + 8);
                    final Label[] array2 = new Label[int3 - int2 + 1];
                    i += 12;
                    for (int n24 = 0; n24 < array2.length; ++n24) {
                        array2[n24] = array[n18 + this.readInt(i)];
                        i += 4;
                    }
                    methodVisitor.visitTableSwitchInsn(int2, int3, array[n23], array2);
                    break;
                }
                case 15: {
                    i = i + 4 - (n18 & 0x3);
                    final int n25 = n18 + this.readInt(i);
                    final int int4 = this.readInt(i + 4);
                    final int[] array3 = new int[int4];
                    final Label[] array4 = new Label[int4];
                    i += 8;
                    for (int n26 = 0; n26 < int4; ++n26) {
                        array3[n26] = this.readInt(i);
                        array4[n26] = array[n18 + this.readInt(i + 4)];
                        i += 8;
                    }
                    methodVisitor.visitLookupSwitchInsn(array[n25], array3, array4);
                    break;
                }
                case 3: {
                    methodVisitor.visitVarInsn(n19, b[i + 1] & 0xFF);
                    i += 2;
                    break;
                }
                case 1: {
                    methodVisitor.visitIntInsn(n19, b[i + 1]);
                    i += 2;
                    break;
                }
                case 2: {
                    methodVisitor.visitIntInsn(n19, this.readShort(i + 1));
                    i += 3;
                    break;
                }
                case 11: {
                    methodVisitor.visitLdcInsn(this.readConst(b[i + 1] & 0xFF, buffer));
                    i += 2;
                    break;
                }
                case 12: {
                    methodVisitor.visitLdcInsn(this.readConst(this.readUnsignedShort(i + 1), buffer));
                    i += 3;
                    break;
                }
                case 6:
                case 7: {
                    final int n27 = this.items[this.readUnsignedShort(i + 1)];
                    final boolean b4 = b[n27 - 1] == 11;
                    final String class1 = this.readClass(n27, buffer);
                    final int n28 = this.items[this.readUnsignedShort(n27 + 2)];
                    final String utf9 = this.readUTF8(n28, buffer);
                    final String utf10 = this.readUTF8(n28 + 2, buffer);
                    if (n19 < 182) {
                        methodVisitor.visitFieldInsn(n19, class1, utf9, utf10);
                    }
                    else {
                        methodVisitor.visitMethodInsn(n19, class1, utf9, utf10, b4);
                    }
                    if (n19 == 185) {
                        i += 5;
                        break;
                    }
                    i += 3;
                    break;
                }
                case 8: {
                    final int n29 = this.items[this.readUnsignedShort(i + 1)];
                    int n30 = context.bootstrapMethods[this.readUnsignedShort(n29)];
                    final Handle handle = (Handle)this.readConst(this.readUnsignedShort(n30), buffer);
                    final int unsignedShort9 = this.readUnsignedShort(n30 + 2);
                    final Object[] array5 = new Object[unsignedShort9];
                    n30 += 4;
                    for (int n31 = 0; n31 < unsignedShort9; ++n31) {
                        array5[n31] = this.readConst(this.readUnsignedShort(n30), buffer);
                        n30 += 2;
                    }
                    final int n32 = this.items[this.readUnsignedShort(n29 + 2)];
                    methodVisitor.visitInvokeDynamicInsn(this.readUTF8(n32, buffer), this.readUTF8(n32 + 2, buffer), handle, array5);
                    i += 5;
                    break;
                }
                case 5: {
                    methodVisitor.visitTypeInsn(n19, this.readClass(i + 1, buffer));
                    i += 3;
                    break;
                }
                case 13: {
                    methodVisitor.visitIincInsn(b[i + 1] & 0xFF, b[i + 2]);
                    i += 3;
                    break;
                }
                default: {
                    methodVisitor.visitMultiANewArrayInsn(this.readClass(i + 1, buffer), b[i + 3] & 0xFF);
                    i += 4;
                    break;
                }
            }
            while (typeAnnotations != null && n4 < typeAnnotations.length && n6 <= n18) {
                if (n6 == n18) {
                    final int annotationTarget = this.readAnnotationTarget(context, typeAnnotations[n4]);
                    this.readAnnotationValues(annotationTarget + 2, buffer, true, methodVisitor.visitInsnAnnotation(context.typeRef, context.typePath, this.readUTF8(annotationTarget, buffer), true));
                }
                n6 = ((++n4 >= typeAnnotations.length || this.readByte(typeAnnotations[n4]) < 67) ? -1 : this.readUnsignedShort(typeAnnotations[n4] + 1));
            }
            while (typeAnnotations2 != null && n5 < typeAnnotations2.length && n7 <= n18) {
                if (n7 == n18) {
                    final int annotationTarget2 = this.readAnnotationTarget(context, typeAnnotations2[n5]);
                    this.readAnnotationValues(annotationTarget2 + 2, buffer, true, methodVisitor.visitInsnAnnotation(context.typeRef, context.typePath, this.readUTF8(annotationTarget2, buffer), false));
                }
                n7 = ((++n5 >= typeAnnotations2.length || this.readByte(typeAnnotations2[n5]) < 67) ? -1 : this.readUnsignedShort(typeAnnotations2[n5] + 1));
            }
        }
        if (array[int1] != null) {
            methodVisitor.visitLabel(array[int1]);
        }
        if ((context.flags & 0x2) == 0x0 && n8 != 0) {
            int[] array6 = null;
            if (n9 != 0) {
                i = n9 + 2;
                array6 = new int[this.readUnsignedShort(n9) * 3];
                for (int length = array6.length; length > 0; array6[--length] = i + 6, array6[--length] = this.readUnsignedShort(i + 8), array6[--length] = this.readUnsignedShort(i), i += 10) {}
            }
            i = n8 + 2;
            for (int unsignedShort10 = this.readUnsignedShort(n8); unsignedShort10 > 0; --unsignedShort10) {
                final int unsignedShort11 = this.readUnsignedShort(i);
                final int unsignedShort12 = this.readUnsignedShort(i + 2);
                final int unsignedShort13 = this.readUnsignedShort(i + 8);
                String utf11 = null;
                if (array6 != null) {
                    for (int n33 = 0; n33 < array6.length; n33 += 3) {
                        if (array6[n33] == unsignedShort11 && array6[n33 + 1] == unsignedShort13) {
                            utf11 = this.readUTF8(array6[n33 + 2], buffer);
                            break;
                        }
                    }
                }
                methodVisitor.visitLocalVariable(this.readUTF8(i + 4, buffer), this.readUTF8(i + 6, buffer), utf11, array[unsignedShort11], array[unsignedShort11 + unsignedShort12], unsignedShort13);
                i += 10;
            }
        }
        if (typeAnnotations != null) {
            for (int n34 = 0; n34 < typeAnnotations.length; ++n34) {
                if (this.readByte(typeAnnotations[n34]) >> 1 == 32) {
                    final int annotationTarget3 = this.readAnnotationTarget(context, typeAnnotations[n34]);
                    this.readAnnotationValues(annotationTarget3 + 2, buffer, true, methodVisitor.visitLocalVariableAnnotation(context.typeRef, context.typePath, context.start, context.end, context.index, this.readUTF8(annotationTarget3, buffer), true));
                }
            }
        }
        if (typeAnnotations2 != null) {
            for (int n35 = 0; n35 < typeAnnotations2.length; ++n35) {
                if (this.readByte(typeAnnotations2[n35]) >> 1 == 32) {
                    final int annotationTarget4 = this.readAnnotationTarget(context, typeAnnotations2[n35]);
                    this.readAnnotationValues(annotationTarget4 + 2, buffer, true, methodVisitor.visitLocalVariableAnnotation(context.typeRef, context.typePath, context.start, context.end, context.index, this.readUTF8(annotationTarget4, buffer), false));
                }
            }
        }
        while (next != null) {
            final Attribute next3 = next.next;
            next.next = null;
            methodVisitor.visitAttribute(next);
            next = next3;
        }
        methodVisitor.visitMaxs(unsignedShort, unsignedShort2);
    }
    
    private int[] readTypeAnnotations(final MethodVisitor methodVisitor, final Context context, int n, final boolean b) {
        final char[] buffer = context.buffer;
        final int[] array = new int[this.readUnsignedShort(n)];
        n += 2;
        for (int i = 0; i < array.length; ++i) {
            array[i] = n;
            final int int1 = this.readInt(n);
            switch (int1 >>> 24) {
                case 0:
                case 1:
                case 22: {
                    n += 2;
                    break;
                }
                case 19:
                case 20:
                case 21: {
                    ++n;
                    break;
                }
                case 64:
                case 65: {
                    for (int j = this.readUnsignedShort(n + 1); j > 0; --j) {
                        final int unsignedShort = this.readUnsignedShort(n + 3);
                        final int unsignedShort2 = this.readUnsignedShort(n + 5);
                        this.readLabel(unsignedShort, context.labels);
                        this.readLabel(unsignedShort + unsignedShort2, context.labels);
                        n += 6;
                    }
                    n += 3;
                    break;
                }
                case 71:
                case 72:
                case 73:
                case 74:
                case 75: {
                    n += 4;
                    break;
                }
                default: {
                    n += 3;
                    break;
                }
            }
            final int byte1 = this.readByte(n);
            if (int1 >>> 24 == 66) {
                final TypePath typePath = (byte1 == 0) ? null : new TypePath(this.b, n);
                n += 1 + 2 * byte1;
                n = this.readAnnotationValues(n + 2, buffer, true, methodVisitor.visitTryCatchAnnotation(int1, typePath, this.readUTF8(n, buffer), b));
            }
            else {
                n = this.readAnnotationValues(n + 3 + 2 * byte1, buffer, true, null);
            }
        }
        return array;
    }
    
    private int readAnnotationTarget(final Context context, int n) {
        final int int1 = this.readInt(n);
        int typeRef = 0;
        switch (int1 >>> 24) {
            case 0:
            case 1:
            case 22: {
                typeRef = (int1 & 0xFFFF0000);
                n += 2;
                break;
            }
            case 19:
            case 20:
            case 21: {
                typeRef = (int1 & 0xFF000000);
                ++n;
                break;
            }
            case 64:
            case 65: {
                typeRef = (int1 & 0xFF000000);
                final int unsignedShort = this.readUnsignedShort(n + 1);
                context.start = new Label[unsignedShort];
                context.end = new Label[unsignedShort];
                context.index = new int[unsignedShort];
                n += 3;
                for (int i = 0; i < unsignedShort; ++i) {
                    final int unsignedShort2 = this.readUnsignedShort(n);
                    final int unsignedShort3 = this.readUnsignedShort(n + 2);
                    context.start[i] = this.readLabel(unsignedShort2, context.labels);
                    context.end[i] = this.readLabel(unsignedShort2 + unsignedShort3, context.labels);
                    context.index[i] = this.readUnsignedShort(n + 4);
                    n += 6;
                }
                break;
            }
            case 71:
            case 72:
            case 73:
            case 74:
            case 75: {
                typeRef = (int1 & 0xFF0000FF);
                n += 4;
                break;
            }
            default: {
                typeRef = (int1 & ((int1 >>> 24 < 67) ? -256 : -16777216));
                n += 3;
                break;
            }
        }
        final int byte1 = this.readByte(n);
        context.typeRef = typeRef;
        context.typePath = ((byte1 == 0) ? null : new TypePath(this.b, n));
        return n + 1 + 2 * byte1;
    }
    
    private void readParameterAnnotations(final MethodVisitor methodVisitor, final Context context, int annotationValues, final boolean b) {
        final int n = this.b[annotationValues++] & 0xFF;
        int n2;
        int i;
        for (n2 = Type.getArgumentTypes(context.desc).length - n, i = 0; i < n2; ++i) {
            final AnnotationVisitor visitParameterAnnotation = methodVisitor.visitParameterAnnotation(i, "Ljava/lang/Synthetic;", false);
            if (visitParameterAnnotation != null) {
                visitParameterAnnotation.visitEnd();
            }
        }
        final char[] buffer = context.buffer;
        while (i < n + n2) {
            int j = this.readUnsignedShort(annotationValues);
            annotationValues += 2;
            while (j > 0) {
                annotationValues = this.readAnnotationValues(annotationValues + 2, buffer, true, methodVisitor.visitParameterAnnotation(i, this.readUTF8(annotationValues, buffer), b));
                --j;
            }
            ++i;
        }
    }
    
    private int readAnnotationValues(int n, final char[] array, final boolean b, final AnnotationVisitor annotationVisitor) {
        int i = this.readUnsignedShort(n);
        n += 2;
        if (b) {
            while (i > 0) {
                n = this.readAnnotationValue(n + 2, array, this.readUTF8(n, array), annotationVisitor);
                --i;
            }
        }
        else {
            while (i > 0) {
                n = this.readAnnotationValue(n, array, null, annotationVisitor);
                --i;
            }
        }
        if (annotationVisitor != null) {
            annotationVisitor.visitEnd();
        }
        return n;
    }
    
    private int readAnnotationValue(int n, final char[] array, final String s, final AnnotationVisitor annotationVisitor) {
        if (annotationVisitor != null) {
            Label_1209: {
                switch (this.b[n++] & 0xFF) {
                    case 68:
                    case 70:
                    case 73:
                    case 74: {
                        annotationVisitor.visit(s, this.readConst(this.readUnsignedShort(n), array));
                        n += 2;
                        break;
                    }
                    case 66: {
                        annotationVisitor.visit(s, (byte)this.readInt(this.items[this.readUnsignedShort(n)]));
                        n += 2;
                        break;
                    }
                    case 90: {
                        annotationVisitor.visit(s, (this.readInt(this.items[this.readUnsignedShort(n)]) == 0) ? Boolean.FALSE : Boolean.TRUE);
                        n += 2;
                        break;
                    }
                    case 83: {
                        annotationVisitor.visit(s, (short)this.readInt(this.items[this.readUnsignedShort(n)]));
                        n += 2;
                        break;
                    }
                    case 67: {
                        annotationVisitor.visit(s, (char)this.readInt(this.items[this.readUnsignedShort(n)]));
                        n += 2;
                        break;
                    }
                    case 115: {
                        annotationVisitor.visit(s, this.readUTF8(n, array));
                        n += 2;
                        break;
                    }
                    case 101: {
                        annotationVisitor.visitEnum(s, this.readUTF8(n, array), this.readUTF8(n + 2, array));
                        n += 4;
                        break;
                    }
                    case 99: {
                        annotationVisitor.visit(s, Type.getType(this.readUTF8(n, array)));
                        n += 2;
                        break;
                    }
                    case 64: {
                        n = this.readAnnotationValues(n + 2, array, true, annotationVisitor.visitAnnotation(s, this.readUTF8(n, array)));
                        break;
                    }
                    case 91: {
                        final int unsignedShort = this.readUnsignedShort(n);
                        n += 2;
                        if (unsignedShort == 0) {
                            return this.readAnnotationValues(n - 2, array, false, annotationVisitor.visitArray(s));
                        }
                        switch (this.b[n++] & 0xFF) {
                            case 66: {
                                final byte[] array2 = new byte[unsignedShort];
                                for (int i = 0; i < unsignedShort; ++i) {
                                    array2[i] = (byte)this.readInt(this.items[this.readUnsignedShort(n)]);
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array2);
                                --n;
                                break Label_1209;
                            }
                            case 90: {
                                final boolean[] array3 = new boolean[unsignedShort];
                                for (int j = 0; j < unsignedShort; ++j) {
                                    array3[j] = (this.readInt(this.items[this.readUnsignedShort(n)]) != 0);
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array3);
                                --n;
                                break Label_1209;
                            }
                            case 83: {
                                final short[] array4 = new short[unsignedShort];
                                for (int k = 0; k < unsignedShort; ++k) {
                                    array4[k] = (short)this.readInt(this.items[this.readUnsignedShort(n)]);
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array4);
                                --n;
                                break Label_1209;
                            }
                            case 67: {
                                final char[] array5 = new char[unsignedShort];
                                for (int l = 0; l < unsignedShort; ++l) {
                                    array5[l] = (char)this.readInt(this.items[this.readUnsignedShort(n)]);
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array5);
                                --n;
                                break Label_1209;
                            }
                            case 73: {
                                final int[] array6 = new int[unsignedShort];
                                for (int n2 = 0; n2 < unsignedShort; ++n2) {
                                    array6[n2] = this.readInt(this.items[this.readUnsignedShort(n)]);
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array6);
                                --n;
                                break Label_1209;
                            }
                            case 74: {
                                final long[] array7 = new long[unsignedShort];
                                for (int n3 = 0; n3 < unsignedShort; ++n3) {
                                    array7[n3] = this.readLong(this.items[this.readUnsignedShort(n)]);
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array7);
                                --n;
                                break Label_1209;
                            }
                            case 70: {
                                final float[] array8 = new float[unsignedShort];
                                for (int n4 = 0; n4 < unsignedShort; ++n4) {
                                    array8[n4] = Float.intBitsToFloat(this.readInt(this.items[this.readUnsignedShort(n)]));
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array8);
                                --n;
                                break Label_1209;
                            }
                            case 68: {
                                final double[] array9 = new double[unsignedShort];
                                for (int n5 = 0; n5 < unsignedShort; ++n5) {
                                    array9[n5] = Double.longBitsToDouble(this.readLong(this.items[this.readUnsignedShort(n)]));
                                    n += 3;
                                }
                                annotationVisitor.visit(s, array9);
                                --n;
                                break Label_1209;
                            }
                            default: {
                                n = this.readAnnotationValues(n - 3, array, false, annotationVisitor.visitArray(s));
                                break Label_1209;
                            }
                        }
                        break;
                    }
                }
            }
            return n;
        }
        switch (this.b[n] & 0xFF) {
            case 101: {
                return n + 5;
            }
            case 64: {
                return this.readAnnotationValues(n + 3, array, true, null);
            }
            case 91: {
                return this.readAnnotationValues(n + 1, array, false, null);
            }
            default: {
                return n + 3;
            }
        }
    }
    
    private void getImplicitFrame(final Context context) {
        final String desc = context.desc;
        final Object[] local = context.local;
        int localCount = 0;
        if ((context.access & 0x8) == 0x0) {
            if ("<init>".equals(context.name)) {
                local[localCount++] = Opcodes.UNINITIALIZED_THIS;
            }
            else {
                local[localCount++] = this.readClass(this.header + 2, context.buffer);
            }
        }
        int n = 1;
        while (true) {
            final int n2 = n;
            switch (desc.charAt(n++)) {
                case 'B':
                case 'C':
                case 'I':
                case 'S':
                case 'Z': {
                    local[localCount++] = Opcodes.INTEGER;
                    continue;
                }
                case 'F': {
                    local[localCount++] = Opcodes.FLOAT;
                    continue;
                }
                case 'J': {
                    local[localCount++] = Opcodes.LONG;
                    continue;
                }
                case 'D': {
                    local[localCount++] = Opcodes.DOUBLE;
                    continue;
                }
                case '[': {
                    while (desc.charAt(n) == '[') {
                        ++n;
                    }
                    if (desc.charAt(n) == 'L') {
                        ++n;
                        while (desc.charAt(n) != ';') {
                            ++n;
                        }
                    }
                    local[localCount++] = desc.substring(n2, ++n);
                    continue;
                }
                case 'L': {
                    while (desc.charAt(n) != ';') {
                        ++n;
                    }
                    local[localCount++] = desc.substring(n2 + 1, n++);
                    continue;
                }
                default: {
                    context.localCount = localCount;
                }
            }
        }
    }
    
    private int readFrame(int n, final boolean b, final boolean b2, final Context context) {
        final char[] buffer = context.buffer;
        final Label[] labels = context.labels;
        int n2;
        if (b) {
            n2 = (this.b[n++] & 0xFF);
        }
        else {
            n2 = 255;
            context.offset = -1;
        }
        context.localDiff = 0;
        int unsignedShort;
        if (n2 < 64) {
            unsignedShort = n2;
            context.mode = 3;
            context.stackCount = 0;
        }
        else if (n2 < 128) {
            unsignedShort = n2 - 64;
            n = this.readFrameType(context.stack, 0, n, buffer, labels);
            context.mode = 4;
            context.stackCount = 1;
        }
        else {
            unsignedShort = this.readUnsignedShort(n);
            n += 2;
            if (n2 == 247) {
                n = this.readFrameType(context.stack, 0, n, buffer, labels);
                context.mode = 4;
                context.stackCount = 1;
            }
            else if (n2 >= 248 && n2 < 251) {
                context.mode = 2;
                context.localDiff = 251 - n2;
                context.localCount -= context.localDiff;
                context.stackCount = 0;
            }
            else if (n2 == 251) {
                context.mode = 3;
                context.stackCount = 0;
            }
            else if (n2 < 255) {
                int n3 = b2 ? context.localCount : 0;
                for (int i = n2 - 251; i > 0; --i) {
                    n = this.readFrameType(context.local, n3++, n, buffer, labels);
                }
                context.mode = 1;
                context.localDiff = n2 - 251;
                context.localCount += context.localDiff;
                context.stackCount = 0;
            }
            else {
                context.mode = 0;
                int j = this.readUnsignedShort(n);
                n += 2;
                context.localDiff = j;
                context.localCount = j;
                int n4 = 0;
                while (j > 0) {
                    n = this.readFrameType(context.local, n4++, n, buffer, labels);
                    --j;
                }
                int k = this.readUnsignedShort(n);
                n += 2;
                context.stackCount = k;
                int n5 = 0;
                while (k > 0) {
                    n = this.readFrameType(context.stack, n5++, n, buffer, labels);
                    --k;
                }
            }
        }
        this.readLabel(context.offset += unsignedShort + 1, labels);
        return n;
    }
    
    private int readFrameType(final Object[] array, final int n, int n2, final char[] array2, final Label[] array3) {
        switch (this.b[n2++] & 0xFF) {
            case 0: {
                array[n] = Opcodes.TOP;
                break;
            }
            case 1: {
                array[n] = Opcodes.INTEGER;
                break;
            }
            case 2: {
                array[n] = Opcodes.FLOAT;
                break;
            }
            case 3: {
                array[n] = Opcodes.DOUBLE;
                break;
            }
            case 4: {
                array[n] = Opcodes.LONG;
                break;
            }
            case 5: {
                array[n] = Opcodes.NULL;
                break;
            }
            case 6: {
                array[n] = Opcodes.UNINITIALIZED_THIS;
                break;
            }
            case 7: {
                array[n] = this.readClass(n2, array2);
                n2 += 2;
                break;
            }
            default: {
                array[n] = this.readLabel(this.readUnsignedShort(n2), array3);
                n2 += 2;
                break;
            }
        }
        return n2;
    }
    
    protected Label readLabel(final int n, final Label[] array) {
        if (array[n] == null) {
            array[n] = new Label();
        }
        return array[n];
    }
    
    private int getAttributes() {
        int n = this.header + 8 + this.readUnsignedShort(this.header + 6) * 2;
        for (int i = this.readUnsignedShort(n); i > 0; --i) {
            for (int j = this.readUnsignedShort(n + 8); j > 0; --j) {
                n += 6 + this.readInt(n + 12);
            }
            n += 8;
        }
        n += 2;
        for (int k = this.readUnsignedShort(n); k > 0; --k) {
            for (int l = this.readUnsignedShort(n + 8); l > 0; --l) {
                n += 6 + this.readInt(n + 12);
            }
            n += 8;
        }
        return n + 2;
    }
    
    private Attribute readAttribute(final Attribute[] array, final String s, final int n, final int n2, final char[] array2, final int n3, final Label[] array3) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i].type.equals(s)) {
                return array[i].read(this, n, n2, array2, n3, array3);
            }
        }
        return new Attribute(s).read(this, n, n2, null, -1, null);
    }
    
    public int getItemCount() {
        return this.items.length;
    }
    
    public int getItem(final int n) {
        return this.items[n];
    }
    
    public int getMaxStringLength() {
        return this.maxStringLength;
    }
    
    public int readByte(final int n) {
        return this.b[n] & 0xFF;
    }
    
    public int readUnsignedShort(final int n) {
        final byte[] b = this.b;
        return (b[n] & 0xFF) << 8 | (b[n + 1] & 0xFF);
    }
    
    public short readShort(final int n) {
        final byte[] b = this.b;
        return (short)((b[n] & 0xFF) << 8 | (b[n + 1] & 0xFF));
    }
    
    public int readInt(final int n) {
        final byte[] b = this.b;
        return (b[n] & 0xFF) << 24 | (b[n + 1] & 0xFF) << 16 | (b[n + 2] & 0xFF) << 8 | (b[n + 3] & 0xFF);
    }
    
    public long readLong(final int n) {
        return (long)this.readInt(n) << 32 | ((long)this.readInt(n + 4) & 0xFFFFFFFFL);
    }
    
    public String readUTF8(int n, final char[] array) {
        final int unsignedShort = this.readUnsignedShort(n);
        if (n == 0 || unsignedShort == 0) {
            return null;
        }
        final String s = this.strings[unsignedShort];
        if (s != null) {
            return s;
        }
        n = this.items[unsignedShort];
        return this.strings[unsignedShort] = this.readUTF(n + 2, this.readUnsignedShort(n), array);
    }
    
    private String readUTF(int i, final int n, final char[] array) {
        final int n2 = i + n;
        final byte[] b = this.b;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        while (i < n2) {
            final byte b2 = b[i++];
            switch (n4) {
                case 0: {
                    final int n6 = b2 & 0xFF;
                    if (n6 < 128) {
                        array[n3++] = (char)n6;
                        continue;
                    }
                    if (n6 < 224 && n6 > 191) {
                        n5 = (char)(n6 & 0x1F);
                        n4 = 1;
                        continue;
                    }
                    n5 = (char)(n6 & 0xF);
                    n4 = 2;
                    continue;
                }
                case 1: {
                    array[n3++] = (char)(n5 << 6 | (b2 & 0x3F));
                    n4 = 0;
                    continue;
                }
                case 2: {
                    n5 = (char)(n5 << 6 | (b2 & 0x3F));
                    n4 = 1;
                    continue;
                }
            }
        }
        return new String(array, 0, n3);
    }
    
    public String readClass(final int n, final char[] array) {
        return this.readUTF8(this.items[this.readUnsignedShort(n)], array);
    }
    
    public Object readConst(final int n, final char[] array) {
        final int n2 = this.items[n];
        switch (this.b[n2 - 1]) {
            case 3: {
                return this.readInt(n2);
            }
            case 4: {
                return Float.intBitsToFloat(this.readInt(n2));
            }
            case 5: {
                return this.readLong(n2);
            }
            case 6: {
                return Double.longBitsToDouble(this.readLong(n2));
            }
            case 7: {
                return Type.getObjectType(this.readUTF8(n2, array));
            }
            case 8: {
                return this.readUTF8(n2, array);
            }
            case 16: {
                return Type.getMethodType(this.readUTF8(n2, array));
            }
            default: {
                final int byte1 = this.readByte(n2);
                final int[] items = this.items;
                final int n3 = items[this.readUnsignedShort(n2 + 1)];
                final boolean b = this.b[n3 - 1] == 11;
                final String class1 = this.readClass(n3, array);
                final int n4 = items[this.readUnsignedShort(n3 + 2)];
                return new Handle(byte1, class1, this.readUTF8(n4, array), this.readUTF8(n4 + 2, array), b);
            }
        }
    }
}
