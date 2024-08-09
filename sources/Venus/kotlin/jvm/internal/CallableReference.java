/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import kotlin.SinceKotlin;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KCallable;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KParameter;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;

public abstract class CallableReference
implements KCallable,
Serializable {
    private transient KCallable reflected;
    @SinceKotlin(version="1.1")
    protected final Object receiver;
    @SinceKotlin(version="1.4")
    private final Class owner;
    @SinceKotlin(version="1.4")
    private final String name;
    @SinceKotlin(version="1.4")
    private final String signature;
    @SinceKotlin(version="1.4")
    private final boolean isTopLevel;
    @SinceKotlin(version="1.1")
    public static final Object NO_RECEIVER = NoReceiver.access$000();

    public CallableReference() {
        this(NO_RECEIVER);
    }

    @SinceKotlin(version="1.1")
    protected CallableReference(Object object) {
        this(object, null, null, null, false);
    }

    @SinceKotlin(version="1.4")
    protected CallableReference(Object object, Class clazz, String string, String string2, boolean bl) {
        this.receiver = object;
        this.owner = clazz;
        this.name = string;
        this.signature = string2;
        this.isTopLevel = bl;
    }

    protected abstract KCallable computeReflected();

    @SinceKotlin(version="1.1")
    public Object getBoundReceiver() {
        return this.receiver;
    }

    @SinceKotlin(version="1.1")
    public KCallable compute() {
        KCallable kCallable = this.reflected;
        if (kCallable == null) {
            this.reflected = kCallable = this.computeReflected();
        }
        return kCallable;
    }

    @SinceKotlin(version="1.1")
    protected KCallable getReflected() {
        KCallable kCallable = this.compute();
        if (kCallable == this) {
            throw new KotlinReflectionNotSupportedError();
        }
        return kCallable;
    }

    public KDeclarationContainer getOwner() {
        return this.owner == null ? null : (this.isTopLevel ? Reflection.getOrCreateKotlinPackage(this.owner) : Reflection.getOrCreateKotlinClass(this.owner));
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getSignature() {
        return this.signature;
    }

    @Override
    public List<KParameter> getParameters() {
        return this.getReflected().getParameters();
    }

    @Override
    public KType getReturnType() {
        return this.getReflected().getReturnType();
    }

    @Override
    public List<Annotation> getAnnotations() {
        return this.getReflected().getAnnotations();
    }

    @Override
    @SinceKotlin(version="1.1")
    public List<KTypeParameter> getTypeParameters() {
        return this.getReflected().getTypeParameters();
    }

    public Object call(Object ... objectArray) {
        return this.getReflected().call(objectArray);
    }

    public Object callBy(Map map) {
        return this.getReflected().callBy(map);
    }

    @Override
    @SinceKotlin(version="1.1")
    public KVisibility getVisibility() {
        return this.getReflected().getVisibility();
    }

    @Override
    @SinceKotlin(version="1.1")
    public boolean isFinal() {
        return this.getReflected().isFinal();
    }

    @Override
    @SinceKotlin(version="1.1")
    public boolean isOpen() {
        return this.getReflected().isOpen();
    }

    @Override
    @SinceKotlin(version="1.1")
    public boolean isAbstract() {
        return this.getReflected().isAbstract();
    }

    @Override
    @SinceKotlin(version="1.3")
    public boolean isSuspend() {
        return this.getReflected().isSuspend();
    }

    @SinceKotlin(version="1.2")
    private static class NoReceiver
    implements Serializable {
        private static final NoReceiver INSTANCE = new NoReceiver();

        private NoReceiver() {
        }

        private Object readResolve() throws ObjectStreamException {
            return INSTANCE;
        }

        static NoReceiver access$000() {
            return INSTANCE;
        }
    }
}

