/*
 * Decompiled with CFR 0.143.
 */
package javassist.bytecode;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.AnnotationsWriter;

public class ParameterAnnotationsAttribute
extends AttributeInfo {
    public static final String visibleTag = "RuntimeVisibleParameterAnnotations";
    public static final String invisibleTag = "RuntimeInvisibleParameterAnnotations";

    public ParameterAnnotationsAttribute(ConstPool cp, String attrname, byte[] info) {
        super(cp, attrname, info);
    }

    public ParameterAnnotationsAttribute(ConstPool cp, String attrname) {
        this(cp, attrname, new byte[]{0});
    }

    ParameterAnnotationsAttribute(ConstPool cp, int n, DataInputStream in) throws IOException {
        super(cp, n, in);
    }

    public int numParameters() {
        return this.info[0] & 255;
    }

    @Override
    public AttributeInfo copy(ConstPool newCp, Map classnames) {
        AnnotationsAttribute.Copier copier = new AnnotationsAttribute.Copier(this.info, this.constPool, newCp, classnames);
        try {
            copier.parameters();
            return new ParameterAnnotationsAttribute(newCp, this.getName(), copier.close());
        }
        catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    public Annotation[][] getAnnotations() {
        try {
            return new AnnotationsAttribute.Parser(this.info, this.constPool).parseParameters();
        }
        catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    public void setAnnotations(Annotation[][] params) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        AnnotationsWriter writer = new AnnotationsWriter(output, this.constPool);
        try {
            int n = params.length;
            writer.numParameters(n);
            for (int i = 0; i < n; ++i) {
                Annotation[] anno = params[i];
                writer.numAnnotations(anno.length);
                for (int j = 0; j < anno.length; ++j) {
                    anno[j].write(writer);
                }
            }
            writer.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.set(output.toByteArray());
    }

    @Override
    void renameClass(String oldname, String newname) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(oldname, newname);
        this.renameClass(map);
    }

    @Override
    void renameClass(Map classnames) {
        AnnotationsAttribute.Renamer renamer = new AnnotationsAttribute.Renamer(this.info, this.getConstPool(), classnames);
        try {
            renamer.parameters();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void getRefClasses(Map classnames) {
        this.renameClass(classnames);
    }

    public String toString() {
        Annotation[][] aa = this.getAnnotations();
        StringBuilder sbuf = new StringBuilder();
        int k = 0;
        while (k < aa.length) {
            Annotation[] a = aa[k++];
            int i = 0;
            while (i < a.length) {
                sbuf.append(a[i++].toString());
                if (i == a.length) continue;
                sbuf.append(" ");
            }
            if (k == aa.length) continue;
            sbuf.append(", ");
        }
        return sbuf.toString();
    }
}

