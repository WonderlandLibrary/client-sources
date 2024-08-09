/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

import java.io.IOException;
import java.io.Serializable;
import org.apache.commons.io.IOExceptionWithCause;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TaggedIOException
extends IOExceptionWithCause {
    private static final long serialVersionUID = -6994123481142850163L;
    private final Serializable tag;

    public static boolean isTaggedWith(Throwable throwable, Object object) {
        return object != null && throwable instanceof TaggedIOException && object.equals(((TaggedIOException)throwable).tag);
    }

    public static void throwCauseIfTaggedWith(Throwable throwable, Object object) throws IOException {
        if (TaggedIOException.isTaggedWith(throwable, object)) {
            throw ((TaggedIOException)throwable).getCause();
        }
    }

    public TaggedIOException(IOException iOException, Serializable serializable) {
        super(iOException.getMessage(), iOException);
        this.tag = serializable;
    }

    public Serializable getTag() {
        return this.tag;
    }

    @Override
    public IOException getCause() {
        return (IOException)super.getCause();
    }

    @Override
    public Throwable getCause() {
        return this.getCause();
    }
}

