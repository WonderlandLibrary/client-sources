package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.transformers.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import java.util.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.service.*;
import java.io.*;
import org.spongepowered.asm.lib.commons.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.extensibility.*;

final class InnerClassGenerator implements IClassGenerator
{
    private static final Logger logger;
    private final Map<String, String> innerClassNames;
    private final Map<String, InnerClassInfo> innerClasses;
    
    InnerClassGenerator() {
        super();
        this.innerClassNames = new HashMap<String, String>();
        this.innerClasses = new HashMap<String, InnerClassInfo>();
    }
    
    public String registerInnerClass(final MixinInfo mixinInfo, final String s, final MixinTargetContext mixinTargetContext) {
        final String format = String.format("%s%s", s, mixinTargetContext);
        String uniqueReference = this.innerClassNames.get(format);
        if (uniqueReference == null) {
            uniqueReference = getUniqueReference(s, mixinTargetContext);
            this.innerClassNames.put(format, uniqueReference);
            this.innerClasses.put(uniqueReference, new InnerClassInfo(uniqueReference, s, mixinInfo, mixinTargetContext));
            InnerClassGenerator.logger.debug("Inner class {} in {} on {} gets unique name {}", new Object[] { s, mixinInfo.getClassRef(), mixinTargetContext.getTargetClassRef(), uniqueReference });
        }
        return uniqueReference;
    }
    
    @Override
    public byte[] generate(final String s) {
        final InnerClassInfo innerClassInfo = this.innerClasses.get(s.replace('.', '/'));
        if (innerClassInfo != null) {
            return this.generate(innerClassInfo);
        }
        return null;
    }
    
    private byte[] generate(final InnerClassInfo innerClassInfo) {
        try {
            InnerClassGenerator.logger.debug("Generating mapped inner class {} (originally {})", new Object[] { innerClassInfo.getName(), innerClassInfo.getOriginalName() });
            final ClassReader classReader = new ClassReader(innerClassInfo.getClassBytes());
            final MixinClassWriter mixinClassWriter = new MixinClassWriter(classReader, 0);
            classReader.accept(new InnerClassAdapter(mixinClassWriter, innerClassInfo), 8);
            return mixinClassWriter.toByteArray();
        }
        catch (InvalidMixinException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            InnerClassGenerator.logger.catching((Throwable)ex2);
            return null;
        }
    }
    
    private static String getUniqueReference(final String s, final MixinTargetContext mixinTargetContext) {
        String substring = s.substring(s.lastIndexOf(36) + 1);
        if (substring.matches("^[0-9]+$")) {
            substring = "Anonymous";
        }
        return String.format("%s$%s$%s", mixinTargetContext.getTargetClassRef(), substring, UUID.randomUUID().toString().replace("-", ""));
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
    
    static class InnerClassInfo extends Remapper
    {
        private final String name;
        private final String originalName;
        private final MixinInfo owner;
        private final MixinTargetContext target;
        private final String ownerName;
        private final String targetName;
        
        InnerClassInfo(final String name, final String originalName, final MixinInfo owner, final MixinTargetContext target) {
            super();
            this.name = name;
            this.originalName = originalName;
            this.owner = owner;
            this.ownerName = owner.getClassRef();
            this.target = target;
            this.targetName = target.getTargetClassRef();
        }
        
        String getName() {
            return this.name;
        }
        
        String getOriginalName() {
            return this.originalName;
        }
        
        MixinInfo getOwner() {
            return this.owner;
        }
        
        MixinTargetContext getTarget() {
            return this.target;
        }
        
        String getOwnerName() {
            return this.ownerName;
        }
        
        String getTargetName() {
            return this.targetName;
        }
        
        byte[] getClassBytes() throws ClassNotFoundException, IOException {
            return MixinService.getService().getBytecodeProvider().getClassBytes(this.originalName, true);
        }
        
        @Override
        public String mapMethodName(final String s, final String s2, final String s3) {
            if (this.ownerName.equalsIgnoreCase(s)) {
                final ClassInfo.Method method = this.owner.getClassInfo().findMethod(s2, s3, 10);
                if (method != null) {
                    return method.getName();
                }
            }
            return super.mapMethodName(s, s2, s3);
        }
        
        @Override
        public String map(final String s) {
            if (this.originalName.equals(s)) {
                return this.name;
            }
            if (this.ownerName.equals(s)) {
                return this.targetName;
            }
            return s;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }
    
    static class InnerClassAdapter extends ClassRemapper
    {
        private final InnerClassInfo info;
        
        public InnerClassAdapter(final ClassVisitor classVisitor, final InnerClassInfo info) {
            super(327680, classVisitor, info);
            this.info = info;
        }
        
        @Override
        public void visitSource(final String s, final String s2) {
            super.visitSource(s, s2);
            final AnnotationVisitor visitAnnotation = this.cv.visitAnnotation("Lorg/spongepowered/asm/mixin/transformer/meta/MixinInner;", false);
            visitAnnotation.visit("mixin", this.info.getOwner().toString());
            visitAnnotation.visit("name", this.info.getOriginalName().substring(this.info.getOriginalName().lastIndexOf(47) + 1));
            visitAnnotation.visitEnd();
        }
        
        @Override
        public void visitInnerClass(final String s, final String s2, final String s3, final int n) {
            if (s.startsWith(this.info.getOriginalName() + "$")) {
                throw new InvalidMixinException(this.info.getOwner(), "Found unsupported nested inner class " + s + " in " + this.info.getOriginalName());
            }
            super.visitInnerClass(s, s2, s3, n);
        }
    }
}
