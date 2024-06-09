package org.spongepowered.tools.obfuscation.mapping.common;

import org.spongepowered.tools.obfuscation.mapping.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.tools.*;
import java.io.*;

public abstract class MappingWriter implements IMappingWriter
{
    private final Messager messager;
    private final Filer filer;
    
    public MappingWriter(final Messager messager, final Filer filer) {
        super();
        this.messager = messager;
        this.filer = filer;
    }
    
    protected PrintWriter openFileWriter(final String s, final String s2) throws IOException {
        if (s.matches("^.*[\\\\/:].*$")) {
            final File file = new File(s);
            file.getParentFile().mkdirs();
            this.messager.printMessage(Diagnostic.Kind.NOTE, "Writing " + s2 + " to " + file.getAbsolutePath());
            return new PrintWriter(file);
        }
        final FileObject resource = this.filer.createResource(StandardLocation.CLASS_OUTPUT, "", s, new Element[0]);
        this.messager.printMessage(Diagnostic.Kind.NOTE, "Writing " + s2 + " to " + new File(resource.toUri()).getAbsolutePath());
        return new PrintWriter(resource.openWriter());
    }
}
