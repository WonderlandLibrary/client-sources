/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.exception;

import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.exception.DefaultExceptionContext;
import org.apache.commons.lang3.exception.ExceptionContext;
import org.apache.commons.lang3.tuple.Pair;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ContextedRuntimeException
extends RuntimeException
implements ExceptionContext {
    private static final long serialVersionUID = 20110706L;
    private final ExceptionContext exceptionContext;

    public ContextedRuntimeException() {
        this.exceptionContext = new DefaultExceptionContext();
    }

    public ContextedRuntimeException(String string) {
        super(string);
        this.exceptionContext = new DefaultExceptionContext();
    }

    public ContextedRuntimeException(Throwable throwable) {
        super(throwable);
        this.exceptionContext = new DefaultExceptionContext();
    }

    public ContextedRuntimeException(String string, Throwable throwable) {
        super(string, throwable);
        this.exceptionContext = new DefaultExceptionContext();
    }

    public ContextedRuntimeException(String string, Throwable throwable, ExceptionContext exceptionContext) {
        super(string, throwable);
        if (exceptionContext == null) {
            exceptionContext = new DefaultExceptionContext();
        }
        this.exceptionContext = exceptionContext;
    }

    @Override
    public ContextedRuntimeException addContextValue(String string, Object object) {
        this.exceptionContext.addContextValue(string, object);
        return this;
    }

    @Override
    public ContextedRuntimeException setContextValue(String string, Object object) {
        this.exceptionContext.setContextValue(string, object);
        return this;
    }

    @Override
    public List<Object> getContextValues(String string) {
        return this.exceptionContext.getContextValues(string);
    }

    @Override
    public Object getFirstContextValue(String string) {
        return this.exceptionContext.getFirstContextValue(string);
    }

    @Override
    public List<Pair<String, Object>> getContextEntries() {
        return this.exceptionContext.getContextEntries();
    }

    @Override
    public Set<String> getContextLabels() {
        return this.exceptionContext.getContextLabels();
    }

    @Override
    public String getMessage() {
        return this.getFormattedExceptionMessage(super.getMessage());
    }

    public String getRawMessage() {
        return super.getMessage();
    }

    @Override
    public String getFormattedExceptionMessage(String string) {
        return this.exceptionContext.getFormattedExceptionMessage(string);
    }

    @Override
    public ExceptionContext setContextValue(String string, Object object) {
        return this.setContextValue(string, object);
    }

    @Override
    public ExceptionContext addContextValue(String string, Object object) {
        return this.addContextValue(string, object);
    }
}

