package org.spongepowered.asm.util;

import java.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.signature.*;

public class ClassSignature
{
    protected static final String OBJECT = "java/lang/Object";
    private final Map<TypeVar, TokenHandle> types;
    private Token superClass;
    private final List<Token> interfaces;
    private final Deque<String> rawInterfaces;
    
    ClassSignature() {
        super();
        this.types = new LinkedHashMap<TypeVar, TokenHandle>();
        this.superClass = new Token("java/lang/Object");
        this.interfaces = new ArrayList<Token>();
        this.rawInterfaces = new LinkedList<String>();
    }
    
    private ClassSignature read(final String s) {
        if (s != null) {
            try {
                new SignatureReader(s).accept(new SignatureParser());
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return this;
    }
    
    protected TypeVar getTypeVar(final String s) {
        for (final TypeVar typeVar : this.types.keySet()) {
            if (typeVar.matches(s)) {
                return typeVar;
            }
        }
        return null;
    }
    
    protected TokenHandle getType(final String s) {
        for (final TypeVar typeVar : this.types.keySet()) {
            if (typeVar.matches(s)) {
                return this.types.get(typeVar);
            }
        }
        final TokenHandle tokenHandle = new TokenHandle();
        this.types.put(new TypeVar(s), tokenHandle);
        return tokenHandle;
    }
    
    protected String getTypeVar(final TokenHandle tokenHandle) {
        for (final Map.Entry<TypeVar, TokenHandle> entry : this.types.entrySet()) {
            final TypeVar typeVar = entry.getKey();
            final TokenHandle tokenHandle2 = entry.getValue();
            if (tokenHandle == tokenHandle2 || tokenHandle.asToken() == tokenHandle2.asToken()) {
                return "T" + typeVar + ";";
            }
        }
        return tokenHandle.token.asType();
    }
    
    protected void addTypeVar(final TypeVar typeVar, final TokenHandle tokenHandle) throws IllegalArgumentException {
        if (this.types.containsKey(typeVar)) {
            throw new IllegalArgumentException("TypeVar " + typeVar + " is already present on " + this);
        }
        this.types.put(typeVar, tokenHandle);
    }
    
    protected void setSuperClass(final Token superClass) {
        this.superClass = superClass;
    }
    
    public String getSuperClass() {
        return this.superClass.asType(true);
    }
    
    protected void addInterface(final Token token) {
        if (!token.isRaw()) {
            final String type = token.asType(true);
            final ListIterator<Token> listIterator = this.interfaces.listIterator();
            while (listIterator.hasNext()) {
                final Token token2 = listIterator.next();
                if (token2.isRaw() && token2.asType(true).equals(type)) {
                    listIterator.set(token);
                    return;
                }
            }
        }
        this.interfaces.add(token);
    }
    
    public void addInterface(final String s) {
        this.rawInterfaces.add(s);
    }
    
    protected void addRawInterface(final String s) {
        final Token token = new Token(s);
        final String type = token.asType(true);
        final Iterator<Token> iterator = this.interfaces.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().asType(true).equals(type)) {
                return;
            }
        }
        this.interfaces.add(token);
    }
    
    public void merge(final ClassSignature classSignature) {
        try {
            final HashSet<String> set = new HashSet<String>();
            final Iterator<TypeVar> iterator = this.types.keySet().iterator();
            while (iterator.hasNext()) {
                set.add(iterator.next().toString());
            }
            classSignature.conform(set);
        }
        catch (IllegalStateException ex) {
            ex.printStackTrace();
            return;
        }
        for (final Map.Entry<TypeVar, TokenHandle> entry : classSignature.types.entrySet()) {
            this.addTypeVar(entry.getKey(), entry.getValue());
        }
        final Iterator<Token> iterator3 = classSignature.interfaces.iterator();
        while (iterator3.hasNext()) {
            this.addInterface(iterator3.next());
        }
    }
    
    private void conform(final Set<String> set) {
        for (final TypeVar typeVar : this.types.keySet()) {
            final String uniqueName = this.findUniqueName(typeVar.getOriginalName(), set);
            typeVar.rename(uniqueName);
            set.add(uniqueName);
        }
    }
    
    private String findUniqueName(final String s, final Set<String> set) {
        if (!set.contains(s)) {
            return s;
        }
        if (s.length() == 1) {
            final String offsetName = this.findOffsetName(s.charAt(0), set);
            if (offsetName != null) {
                return offsetName;
            }
        }
        final String offsetName2 = this.findOffsetName('T', set, "", s);
        if (offsetName2 != null) {
            return offsetName2;
        }
        final String offsetName3 = this.findOffsetName('T', set, s, "");
        if (offsetName3 != null) {
            return offsetName3;
        }
        final String offsetName4 = this.findOffsetName('T', set, "T", s);
        if (offsetName4 != null) {
            return offsetName4;
        }
        final String offsetName5 = this.findOffsetName('T', set, "", s + "Type");
        if (offsetName5 != null) {
            return offsetName5;
        }
        throw new IllegalStateException("Failed to conform type var: " + s);
    }
    
    private String findOffsetName(final char c, final Set<String> set) {
        return this.findOffsetName(c, set, "", "");
    }
    
    private String findOffsetName(final char c, final Set<String> set, final String s, final String s2) {
        final String format = String.format("%s%s%s", s, c, s2);
        if (!set.contains(format)) {
            return format;
        }
        if (c > '@' && c < '[') {
            for (int n = c - '@'; n + 65 != c; n = ++n % 26) {
                final String format2 = String.format("%s%s%s", s, (char)(n + 65), s2);
                if (!set.contains(format2)) {
                    return format2;
                }
            }
        }
        return null;
    }
    
    public SignatureVisitor getRemapper() {
        return new SignatureRemapper();
    }
    
    @Override
    public String toString() {
        while (this.rawInterfaces.size() > 0) {
            this.addRawInterface(this.rawInterfaces.remove());
        }
        final StringBuilder sb = new StringBuilder();
        if (this.types.size() > 0) {
            boolean b = false;
            final StringBuilder sb2 = new StringBuilder();
            for (final Map.Entry<TypeVar, TokenHandle> entry : this.types.entrySet()) {
                final String bound = entry.getValue().asBound();
                if (!bound.isEmpty()) {
                    sb2.append(entry.getKey()).append(':').append(bound);
                    b = true;
                }
            }
            if (b) {
                sb.append('<').append((CharSequence)sb2).append('>');
            }
        }
        sb.append(this.superClass.asType());
        final Iterator<Token> iterator2 = this.interfaces.iterator();
        while (iterator2.hasNext()) {
            sb.append(iterator2.next().asType());
        }
        return sb.toString();
    }
    
    public ClassSignature wake() {
        return this;
    }
    
    public static ClassSignature of(final String s) {
        return new ClassSignature().read(s);
    }
    
    public static ClassSignature of(final ClassNode classNode) {
        if (classNode.signature != null) {
            return of(classNode.signature);
        }
        return generate(classNode);
    }
    
    public static ClassSignature ofLazy(final ClassNode classNode) {
        if (classNode.signature != null) {
            return new Lazy(classNode.signature);
        }
        return generate(classNode);
    }
    
    private static ClassSignature generate(final ClassNode classNode) {
        final ClassSignature classSignature = new ClassSignature();
        classSignature.setSuperClass(new Token((classNode.superName != null) ? classNode.superName : "java/lang/Object"));
        final Iterator<String> iterator = classNode.interfaces.iterator();
        while (iterator.hasNext()) {
            classSignature.addInterface(new Token(iterator.next()));
        }
        return classSignature;
    }
    
    static class Lazy extends ClassSignature
    {
        private final String sig;
        private ClassSignature generated;
        
        Lazy(final String sig) {
            super();
            this.sig = sig;
        }
        
        @Override
        public ClassSignature wake() {
            if (this.generated == null) {
                this.generated = ClassSignature.of(this.sig);
            }
            return this.generated;
        }
    }
    
    static class TypeVar implements Comparable<TypeVar>
    {
        private final String originalName;
        private String currentName;
        
        TypeVar(final String s) {
            super();
            this.originalName = s;
            this.currentName = s;
        }
        
        @Override
        public int compareTo(final TypeVar typeVar) {
            return this.currentName.compareTo(typeVar.currentName);
        }
        
        @Override
        public String toString() {
            return this.currentName;
        }
        
        String getOriginalName() {
            return this.originalName;
        }
        
        void rename(final String currentName) {
            this.currentName = currentName;
        }
        
        public boolean matches(final String s) {
            return this.originalName.equals(s);
        }
        
        @Override
        public boolean equals(final Object o) {
            return this.currentName.equals(o);
        }
        
        @Override
        public int hashCode() {
            return this.currentName.hashCode();
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((TypeVar)o);
        }
    }
    
    static class Token implements IToken
    {
        static final String SYMBOLS = "+-*";
        private final boolean inner;
        private boolean array;
        private char symbol;
        private String type;
        private List<Token> classBound;
        private List<Token> ifaceBound;
        private List<IToken> signature;
        private List<IToken> suffix;
        private Token tail;
        
        Token() {
            this(false);
        }
        
        Token(final String s) {
            this(s, false);
        }
        
        Token(final char symbol) {
            this();
            this.symbol = symbol;
        }
        
        Token(final boolean b) {
            this(null, b);
        }
        
        Token(final String type, final boolean inner) {
            super();
            this.symbol = '\0';
            this.inner = inner;
            this.type = type;
        }
        
        Token setSymbol(final char symbol) {
            if (this.symbol == '\0' && "+-*".indexOf(symbol) > -1) {
                this.symbol = symbol;
            }
            return this;
        }
        
        Token setType(final String type) {
            if (this.type == null) {
                this.type = type;
            }
            return this;
        }
        
        boolean hasClassBound() {
            return this.classBound != null;
        }
        
        boolean hasInterfaceBound() {
            return this.ifaceBound != null;
        }
        
        @Override
        public IToken setArray(final boolean b) {
            this.array |= b;
            return this;
        }
        
        @Override
        public IToken setWildcard(final char symbol) {
            if ("+-".indexOf(symbol) == -1) {
                return this;
            }
            return this.setSymbol(symbol);
        }
        
        private List<Token> getClassBound() {
            if (this.classBound == null) {
                this.classBound = new ArrayList<Token>();
            }
            return this.classBound;
        }
        
        private List<Token> getIfaceBound() {
            if (this.ifaceBound == null) {
                this.ifaceBound = new ArrayList<Token>();
            }
            return this.ifaceBound;
        }
        
        private List<IToken> getSignature() {
            if (this.signature == null) {
                this.signature = new ArrayList<IToken>();
            }
            return this.signature;
        }
        
        private List<IToken> getSuffix() {
            if (this.suffix == null) {
                this.suffix = new ArrayList<IToken>();
            }
            return this.suffix;
        }
        
        IToken addTypeArgument(final char c) {
            if (this.tail != null) {
                return this.tail.addTypeArgument(c);
            }
            final Token token = new Token(c);
            this.getSignature().add(token);
            return token;
        }
        
        IToken addTypeArgument(final String s) {
            if (this.tail != null) {
                return this.tail.addTypeArgument(s);
            }
            final Token token = new Token(s);
            this.getSignature().add(token);
            return token;
        }
        
        IToken addTypeArgument(final Token token) {
            if (this.tail != null) {
                return this.tail.addTypeArgument(token);
            }
            this.getSignature().add(token);
            return token;
        }
        
        IToken addTypeArgument(final TokenHandle tokenHandle) {
            if (this.tail != null) {
                return this.tail.addTypeArgument(tokenHandle);
            }
            final TokenHandle clone = tokenHandle.clone();
            this.getSignature().add(clone);
            return clone;
        }
        
        Token addBound(final String s, final boolean b) {
            if (b) {
                return this.addClassBound(s);
            }
            return this.addInterfaceBound(s);
        }
        
        Token addClassBound(final String s) {
            final Token token = new Token(s);
            this.getClassBound().add(token);
            return token;
        }
        
        Token addInterfaceBound(final String s) {
            final Token token = new Token(s);
            this.getIfaceBound().add(token);
            return token;
        }
        
        Token addInnerClass(final String s) {
            this.tail = new Token(s, true);
            this.getSuffix().add(this.tail);
            return this.tail;
        }
        
        @Override
        public String toString() {
            return this.asType();
        }
        
        @Override
        public String asBound() {
            final StringBuilder sb = new StringBuilder();
            if (this.type != null) {
                sb.append(this.type);
            }
            if (this.classBound != null) {
                final Iterator<Token> iterator = this.classBound.iterator();
                while (iterator.hasNext()) {
                    sb.append(iterator.next().asType());
                }
            }
            if (this.ifaceBound != null) {
                final Iterator<Token> iterator2 = this.ifaceBound.iterator();
                while (iterator2.hasNext()) {
                    sb.append(':').append(iterator2.next().asType());
                }
            }
            return sb.toString();
        }
        
        @Override
        public String asType() {
            return this.asType(false);
        }
        
        public String asType(final boolean b) {
            final StringBuilder sb = new StringBuilder();
            if (this.array) {
                sb.append('[');
            }
            if (this.symbol != '\0') {
                sb.append(this.symbol);
            }
            if (this.type == null) {
                return sb.toString();
            }
            if (!this.inner) {
                sb.append('L');
            }
            sb.append(this.type);
            if (!b) {
                if (this.signature != null) {
                    sb.append('<');
                    final Iterator<IToken> iterator = this.signature.iterator();
                    while (iterator.hasNext()) {
                        sb.append(iterator.next().asType());
                    }
                    sb.append('>');
                }
                if (this.suffix != null) {
                    final Iterator<IToken> iterator2 = this.suffix.iterator();
                    while (iterator2.hasNext()) {
                        sb.append('.').append(iterator2.next().asType());
                    }
                }
            }
            if (!this.inner) {
                sb.append(';');
            }
            return sb.toString();
        }
        
        boolean isRaw() {
            return this.signature == null;
        }
        
        String getClassType() {
            return (this.type != null) ? this.type : "java/lang/Object";
        }
        
        @Override
        public Token asToken() {
            return this;
        }
    }
    
    class TokenHandle implements IToken
    {
        final Token token;
        boolean array;
        char wildcard;
        final ClassSignature this$0;
        
        TokenHandle(final ClassSignature classSignature) {
            this(classSignature, new Token());
        }
        
        TokenHandle(final ClassSignature this$0, final Token token) {
            this.this$0 = this$0;
            super();
            this.token = token;
        }
        
        @Override
        public IToken setArray(final boolean b) {
            this.array |= b;
            return this;
        }
        
        @Override
        public IToken setWildcard(final char wildcard) {
            if ("+-".indexOf(wildcard) > -1) {
                this.wildcard = wildcard;
            }
            return this;
        }
        
        @Override
        public String asBound() {
            return this.token.asBound();
        }
        
        @Override
        public String asType() {
            final StringBuilder sb = new StringBuilder();
            if (this.wildcard > '\0') {
                sb.append(this.wildcard);
            }
            if (this.array) {
                sb.append('[');
            }
            return sb.append(this.this$0.getTypeVar(this)).toString();
        }
        
        @Override
        public Token asToken() {
            return this.token;
        }
        
        @Override
        public String toString() {
            return this.token.toString();
        }
        
        public TokenHandle clone() {
            return this.this$0.new TokenHandle(this.token);
        }
        
        public Object clone() throws CloneNotSupportedException {
            return this.clone();
        }
    }
    
    class SignatureParser extends SignatureVisitor
    {
        private FormalParamElement param;
        final ClassSignature this$0;
        
        SignatureParser(final ClassSignature this$0) {
            this.this$0 = this$0;
            super(327680);
        }
        
        @Override
        public void visitFormalTypeParameter(final String s) {
            this.param = new FormalParamElement(s);
        }
        
        @Override
        public SignatureVisitor visitClassBound() {
            return this.param.visitClassBound();
        }
        
        @Override
        public SignatureVisitor visitInterfaceBound() {
            return this.param.visitInterfaceBound();
        }
        
        @Override
        public SignatureVisitor visitSuperclass() {
            return new SuperClassElement();
        }
        
        @Override
        public SignatureVisitor visitInterface() {
            return new InterfaceElement();
        }
        
        abstract class SignatureElement extends SignatureVisitor
        {
            final SignatureParser this$1;
            
            public SignatureElement(final SignatureParser this$1) {
                this.this$1 = this$1;
                super(327680);
            }
        }
        
        abstract class TokenElement extends SignatureElement
        {
            protected Token token;
            private boolean array;
            final SignatureParser this$1;
            
            TokenElement(final SignatureParser this$1) {
                this.this$1 = this$1.super();
            }
            
            public Token getToken() {
                if (this.token == null) {
                    this.token = new Token();
                }
                return this.token;
            }
            
            protected void setArray() {
                this.array = true;
            }
            
            private boolean getArray() {
                final boolean array = this.array;
                this.array = false;
                return array;
            }
            
            @Override
            public void visitClassType(final String type) {
                this.getToken().setType(type);
            }
            
            @Override
            public SignatureVisitor visitClassBound() {
                this.getToken();
                return this.this$1.new BoundElement(this, true);
            }
            
            @Override
            public SignatureVisitor visitInterfaceBound() {
                this.getToken();
                return this.this$1.new BoundElement(this, false);
            }
            
            @Override
            public void visitInnerClassType(final String s) {
                this.token.addInnerClass(s);
            }
            
            @Override
            public SignatureVisitor visitArrayType() {
                this.setArray();
                return this;
            }
            
            @Override
            public SignatureVisitor visitTypeArgument(final char c) {
                return this.this$1.new TypeArgElement(this, c);
            }
            
            Token addTypeArgument() {
                return this.token.addTypeArgument('*').asToken();
            }
            
            IToken addTypeArgument(final char c) {
                return this.token.addTypeArgument(c).setArray(this.getArray());
            }
            
            IToken addTypeArgument(final String s) {
                return this.token.addTypeArgument(s).setArray(this.getArray());
            }
            
            IToken addTypeArgument(final Token token) {
                return this.token.addTypeArgument(token).setArray(this.getArray());
            }
            
            IToken addTypeArgument(final TokenHandle tokenHandle) {
                return this.token.addTypeArgument(tokenHandle).setArray(this.getArray());
            }
        }
        
        class FormalParamElement extends TokenElement
        {
            private final TokenHandle handle;
            final SignatureParser this$1;
            
            FormalParamElement(final SignatureParser this$1, final String s) {
                this.this$1 = this$1.super();
                this.handle = this$1.this$0.getType(s);
                this.token = this.handle.asToken();
            }
        }
        
        class TypeArgElement extends TokenElement
        {
            private final TokenElement type;
            private final char wildcard;
            final SignatureParser this$1;
            
            TypeArgElement(final SignatureParser this$1, final TokenElement type, final char wildcard) {
                this.this$1 = this$1.super();
                this.type = type;
                this.wildcard = wildcard;
            }
            
            @Override
            public SignatureVisitor visitArrayType() {
                this.type.setArray();
                return this;
            }
            
            @Override
            public void visitBaseType(final char c) {
                this.token = this.type.addTypeArgument(c).asToken();
            }
            
            @Override
            public void visitTypeVariable(final String s) {
                this.token = this.type.addTypeArgument(this.this$1.this$0.getType(s)).setWildcard(this.wildcard).asToken();
            }
            
            @Override
            public void visitClassType(final String s) {
                this.token = this.type.addTypeArgument(s).setWildcard(this.wildcard).asToken();
            }
            
            @Override
            public void visitTypeArgument() {
                this.token.addTypeArgument('*');
            }
            
            @Override
            public SignatureVisitor visitTypeArgument(final char c) {
                return this.this$1.new TypeArgElement(this, c);
            }
            
            @Override
            public void visitEnd() {
            }
        }
        
        class BoundElement extends TokenElement
        {
            private final TokenElement type;
            private final boolean classBound;
            final SignatureParser this$1;
            
            BoundElement(final SignatureParser this$1, final TokenElement type, final boolean classBound) {
                this.this$1 = this$1.super();
                this.type = type;
                this.classBound = classBound;
            }
            
            @Override
            public void visitClassType(final String s) {
                this.token = this.type.token.addBound(s, this.classBound);
            }
            
            @Override
            public void visitTypeArgument() {
                this.token.addTypeArgument('*');
            }
            
            @Override
            public SignatureVisitor visitTypeArgument(final char c) {
                return this.this$1.new TypeArgElement(this, c);
            }
        }
        
        class SuperClassElement extends TokenElement
        {
            final SignatureParser this$1;
            
            SuperClassElement(final SignatureParser this$1) {
                this.this$1 = this$1.super();
            }
            
            @Override
            public void visitEnd() {
                this.this$1.this$0.setSuperClass(this.token);
            }
        }
        
        class InterfaceElement extends TokenElement
        {
            final SignatureParser this$1;
            
            InterfaceElement(final SignatureParser this$1) {
                this.this$1 = this$1.super();
            }
            
            @Override
            public void visitEnd() {
                this.this$1.this$0.addInterface(this.token);
            }
        }
    }
    
    class SignatureRemapper extends SignatureWriter
    {
        private final Set<String> localTypeVars;
        final ClassSignature this$0;
        
        SignatureRemapper(final ClassSignature this$0) {
            this.this$0 = this$0;
            super();
            this.localTypeVars = new HashSet<String>();
        }
        
        @Override
        public void visitFormalTypeParameter(final String s) {
            this.localTypeVars.add(s);
            super.visitFormalTypeParameter(s);
        }
        
        @Override
        public void visitTypeVariable(final String s) {
            if (!this.localTypeVars.contains(s)) {
                final TypeVar typeVar = this.this$0.getTypeVar(s);
                if (typeVar != null) {
                    super.visitTypeVariable(typeVar.toString());
                    return;
                }
            }
            super.visitTypeVariable(s);
        }
    }
    
    interface IToken
    {
        public static final String WILDCARDS = "+-";
        
        String asType();
        
        String asBound();
        
        Token asToken();
        
        IToken setArray(final boolean p0);
        
        IToken setWildcard(final char p0);
    }
}
