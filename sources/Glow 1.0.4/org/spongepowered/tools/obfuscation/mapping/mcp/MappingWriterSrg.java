package org.spongepowered.tools.obfuscation.mapping.mcp;

import org.spongepowered.tools.obfuscation.mapping.common.*;
import javax.annotation.processing.*;
import org.spongepowered.tools.obfuscation.*;
import org.spongepowered.tools.obfuscation.mapping.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import java.io.*;
import java.util.*;

public class MappingWriterSrg extends MappingWriter
{
    public MappingWriterSrg(final Messager messager, final Filer filer) {
        super(messager, filer);
    }
    
    @Override
    public void write(final String s, final ObfuscationType obfuscationType, final IMappingConsumer.MappingSet<MappingField> set, final IMappingConsumer.MappingSet<MappingMethod> set2) {
        if (s == null) {
            return;
        }
        PrintWriter openFileWriter = null;
        try {
            openFileWriter = this.openFileWriter(s, obfuscationType + " output SRGs");
            this.writeFieldMappings(openFileWriter, set);
            this.writeMethodMappings(openFileWriter, set2);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (openFileWriter != null) {
                try {
                    openFileWriter.close();
                }
                catch (Exception ex2) {}
            }
        }
    }
    
    protected void writeFieldMappings(final PrintWriter printWriter, final IMappingConsumer.MappingSet<MappingField> set) {
        final Iterator<IMappingConsumer.MappingSet.Pair<MappingField>> iterator = set.iterator();
        while (iterator.hasNext()) {
            printWriter.println(this.formatFieldMapping(iterator.next()));
        }
    }
    
    protected void writeMethodMappings(final PrintWriter printWriter, final IMappingConsumer.MappingSet<MappingMethod> set) {
        final Iterator<IMappingConsumer.MappingSet.Pair<MappingMethod>> iterator = set.iterator();
        while (iterator.hasNext()) {
            printWriter.println(this.formatMethodMapping(iterator.next()));
        }
    }
    
    protected String formatFieldMapping(final IMappingConsumer.MappingSet.Pair<MappingField> pair) {
        return String.format("FD: %s/%s %s/%s", pair.from.getOwner(), pair.from.getName(), pair.to.getOwner(), pair.to.getName());
    }
    
    protected String formatMethodMapping(final IMappingConsumer.MappingSet.Pair<MappingMethod> pair) {
        return String.format("MD: %s %s %s %s", pair.from.getName(), pair.from.getDesc(), pair.to.getName(), pair.to.getDesc());
    }
}
