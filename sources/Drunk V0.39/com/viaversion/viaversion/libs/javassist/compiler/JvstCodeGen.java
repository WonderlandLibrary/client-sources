/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist.compiler;

import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.CtPrimitiveType;
import com.viaversion.viaversion.libs.javassist.NotFoundException;
import com.viaversion.viaversion.libs.javassist.bytecode.Bytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.Descriptor;
import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import com.viaversion.viaversion.libs.javassist.compiler.JvstTypeChecker;
import com.viaversion.viaversion.libs.javassist.compiler.MemberCodeGen;
import com.viaversion.viaversion.libs.javassist.compiler.MemberResolver;
import com.viaversion.viaversion.libs.javassist.compiler.ProceedHandler;
import com.viaversion.viaversion.libs.javassist.compiler.SymbolTable;
import com.viaversion.viaversion.libs.javassist.compiler.ast.ASTList;
import com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree;
import com.viaversion.viaversion.libs.javassist.compiler.ast.CallExpr;
import com.viaversion.viaversion.libs.javassist.compiler.ast.CastExpr;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Declarator;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Expr;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Member;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Stmnt;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Symbol;

public class JvstCodeGen
extends MemberCodeGen {
    String paramArrayName = null;
    String paramListName = null;
    CtClass[] paramTypeList = null;
    private int paramVarBase = 0;
    private boolean useParam0 = false;
    private String param0Type = null;
    public static final String sigName = "$sig";
    public static final String dollarTypeName = "$type";
    public static final String clazzName = "$class";
    private CtClass dollarType = null;
    CtClass returnType = null;
    String returnCastName = null;
    private String returnVarName = null;
    public static final String wrapperCastName = "$w";
    String proceedName = null;
    public static final String cflowName = "$cflow";
    ProceedHandler procHandler = null;

    public JvstCodeGen(Bytecode b, CtClass cc, ClassPool cp) {
        super(b, cc, cp);
        this.setTypeChecker(new JvstTypeChecker(cc, cp, this));
    }

    private int indexOfParam1() {
        int n;
        if (this.useParam0) {
            n = 1;
            return this.paramVarBase + n;
        }
        n = 0;
        return this.paramVarBase + n;
    }

    public void setProceedHandler(ProceedHandler h, String name) {
        this.proceedName = name;
        this.procHandler = h;
    }

    public void addNullIfVoid() {
        if (this.exprType != 344) return;
        this.bytecode.addOpcode(1);
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/Object";
    }

    @Override
    public void atMember(Member mem) throws CompileError {
        String name = mem.get();
        if (name.equals(this.paramArrayName)) {
            JvstCodeGen.compileParameterList(this.bytecode, this.paramTypeList, this.indexOfParam1());
            this.exprType = 307;
            this.arrayDim = 1;
            this.className = "java/lang/Object";
            return;
        }
        if (name.equals(sigName)) {
            this.bytecode.addLdc(Descriptor.ofMethod(this.returnType, this.paramTypeList));
            this.bytecode.addInvokestatic("com/viaversion/viaversion/libs/javassist/runtime/Desc", "getParams", "(Ljava/lang/String;)[Ljava/lang/Class;");
            this.exprType = 307;
            this.arrayDim = 1;
            this.className = "java/lang/Class";
            return;
        }
        if (name.equals(dollarTypeName)) {
            if (this.dollarType == null) {
                throw new CompileError("$type is not available");
            }
            this.bytecode.addLdc(Descriptor.of(this.dollarType));
            this.callGetType("getType");
            return;
        }
        if (!name.equals(clazzName)) {
            super.atMember(mem);
            return;
        }
        if (this.param0Type == null) {
            throw new CompileError("$class is not available");
        }
        this.bytecode.addLdc(this.param0Type);
        this.callGetType("getClazz");
    }

    private void callGetType(String method) {
        this.bytecode.addInvokestatic("com/viaversion/viaversion/libs/javassist/runtime/Desc", method, "(Ljava/lang/String;)Ljava/lang/Class;");
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/Class";
    }

    @Override
    protected void atFieldAssign(Expr expr, int op, ASTree left, ASTree right, boolean doDup) throws CompileError {
        if (left instanceof Member && ((Member)left).get().equals(this.paramArrayName)) {
            if (op != 61) {
                throw new CompileError("bad operator for " + this.paramArrayName);
            }
            right.accept(this);
            if (this.arrayDim != 1) throw new CompileError("invalid type for " + this.paramArrayName);
            if (this.exprType != 307) {
                throw new CompileError("invalid type for " + this.paramArrayName);
            }
            this.atAssignParamList(this.paramTypeList, this.bytecode);
            if (doDup) return;
            this.bytecode.addOpcode(87);
            return;
        }
        super.atFieldAssign(expr, op, left, right, doDup);
    }

    protected void atAssignParamList(CtClass[] params, Bytecode code) throws CompileError {
        if (params == null) {
            return;
        }
        int varNo = this.indexOfParam1();
        int n = params.length;
        int i = 0;
        while (i < n) {
            code.addOpcode(89);
            code.addIconst(i);
            code.addOpcode(50);
            this.compileUnwrapValue(params[i], code);
            code.addStore(varNo, params[i]);
            varNo += JvstCodeGen.is2word(this.exprType, this.arrayDim) ? 2 : 1;
            ++i;
        }
    }

    @Override
    public void atCastExpr(CastExpr expr) throws CompileError {
        ASTree p;
        ASTList classname = expr.getClassName();
        if (classname != null && expr.getArrayDim() == 0 && (p = classname.head()) instanceof Symbol && classname.tail() == null) {
            String typename = ((Symbol)p).get();
            if (typename.equals(this.returnCastName)) {
                this.atCastToRtype(expr);
                return;
            }
            if (typename.equals(wrapperCastName)) {
                this.atCastToWrapper(expr);
                return;
            }
        }
        super.atCastExpr(expr);
    }

    protected void atCastToRtype(CastExpr expr) throws CompileError {
        expr.getOprand().accept(this);
        if (this.exprType != 344 && !JvstCodeGen.isRefType(this.exprType) && this.arrayDim <= 0) {
            if (!(this.returnType instanceof CtPrimitiveType)) throw new CompileError("invalid cast");
            CtPrimitiveType pt = (CtPrimitiveType)this.returnType;
            int destType = MemberResolver.descToType(pt.getDescriptor());
            this.atNumCastExpr(this.exprType, destType);
            this.exprType = destType;
            this.arrayDim = 0;
            this.className = null;
            return;
        }
        this.compileUnwrapValue(this.returnType, this.bytecode);
    }

    protected void atCastToWrapper(CastExpr expr) throws CompileError {
        expr.getOprand().accept(this);
        if (JvstCodeGen.isRefType(this.exprType)) return;
        if (this.arrayDim > 0) {
            return;
        }
        CtClass clazz = this.resolver.lookupClass(this.exprType, this.arrayDim, this.className);
        if (!(clazz instanceof CtPrimitiveType)) return;
        CtPrimitiveType pt = (CtPrimitiveType)clazz;
        String wrapper = pt.getWrapperName();
        this.bytecode.addNew(wrapper);
        this.bytecode.addOpcode(89);
        if (pt.getDataSize() > 1) {
            this.bytecode.addOpcode(94);
        } else {
            this.bytecode.addOpcode(93);
        }
        this.bytecode.addOpcode(88);
        this.bytecode.addInvokespecial(wrapper, "<init>", "(" + pt.getDescriptor() + ")V");
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/Object";
    }

    @Override
    public void atCallExpr(CallExpr expr) throws CompileError {
        ASTree method = expr.oprand1();
        if (method instanceof Member) {
            String name = ((Member)method).get();
            if (this.procHandler != null && name.equals(this.proceedName)) {
                this.procHandler.doit(this, this.bytecode, (ASTList)expr.oprand2());
                return;
            }
            if (name.equals(cflowName)) {
                this.atCflow((ASTList)expr.oprand2());
                return;
            }
        }
        super.atCallExpr(expr);
    }

    protected void atCflow(ASTList cname) throws CompileError {
        StringBuffer sbuf = new StringBuffer();
        if (cname == null) throw new CompileError("bad $cflow");
        if (cname.tail() != null) {
            throw new CompileError("bad $cflow");
        }
        JvstCodeGen.makeCflowName(sbuf, cname.head());
        String name = sbuf.toString();
        Object[] names = this.resolver.getClassPool().lookupCflow(name);
        if (names == null) {
            throw new CompileError("no such $cflow: " + name);
        }
        this.bytecode.addGetstatic((String)names[0], (String)names[1], "Lcom/viaversion/viaversion/libs/javassist/runtime/Cflow;");
        this.bytecode.addInvokevirtual("com.viaversion.viaversion.libs.javassist.runtime.Cflow", "value", "()I");
        this.exprType = 324;
        this.arrayDim = 0;
        this.className = null;
    }

    private static void makeCflowName(StringBuffer sbuf, ASTree name) throws CompileError {
        if (name instanceof Symbol) {
            sbuf.append(((Symbol)name).get());
            return;
        }
        if (!(name instanceof Expr)) throw new CompileError("bad $cflow");
        Expr expr = (Expr)name;
        if (expr.getOperator() != 46) throw new CompileError("bad $cflow");
        JvstCodeGen.makeCflowName(sbuf, expr.oprand1());
        sbuf.append('.');
        JvstCodeGen.makeCflowName(sbuf, expr.oprand2());
    }

    public boolean isParamListName(ASTList args) {
        if (this.paramTypeList == null) return false;
        if (args == null) return false;
        if (args.tail() != null) return false;
        ASTree left = args.head();
        if (!(left instanceof Member)) return false;
        if (!((Member)left).get().equals(this.paramListName)) return false;
        return true;
    }

    @Override
    public int getMethodArgsLength(ASTList args) {
        String pname = this.paramListName;
        int n = 0;
        while (args != null) {
            ASTree a = args.head();
            if (a instanceof Member && ((Member)a).get().equals(pname)) {
                if (this.paramTypeList != null) {
                    n += this.paramTypeList.length;
                }
            } else {
                ++n;
            }
            args = args.tail();
        }
        return n;
    }

    @Override
    public void atMethodArgs(ASTList args, int[] types, int[] dims, String[] cnames) throws CompileError {
        CtClass[] params = this.paramTypeList;
        String pname = this.paramListName;
        int i = 0;
        while (args != null) {
            ASTree a = args.head();
            if (a instanceof Member && ((Member)a).get().equals(pname)) {
                if (params != null) {
                    CtClass p;
                    int n = params.length;
                    int regno = this.indexOfParam1();
                    for (int k = 0; k < n; regno += this.bytecode.addLoad(regno, p), ++i, ++k) {
                        p = params[k];
                        this.setType(p);
                        types[i] = this.exprType;
                        dims[i] = this.arrayDim;
                        cnames[i] = this.className;
                    }
                }
            } else {
                a.accept(this);
                types[i] = this.exprType;
                dims[i] = this.arrayDim;
                cnames[i] = this.className;
                ++i;
            }
            args = args.tail();
        }
    }

    void compileInvokeSpecial(ASTree target, int methodIndex, String descriptor, ASTList args) throws CompileError {
        target.accept(this);
        int nargs = this.getMethodArgsLength(args);
        this.atMethodArgs(args, new int[nargs], new int[nargs], new String[nargs]);
        this.bytecode.addInvokespecial(methodIndex, descriptor);
        this.setReturnType(descriptor, false, false);
        this.addNullIfVoid();
    }

    @Override
    protected void atReturnStmnt(Stmnt st) throws CompileError {
        ASTree result = st.getLeft();
        if (result != null && this.returnType == CtClass.voidType) {
            this.compileExpr(result);
            if (JvstCodeGen.is2word(this.exprType, this.arrayDim)) {
                this.bytecode.addOpcode(88);
            } else if (this.exprType != 344) {
                this.bytecode.addOpcode(87);
            }
            result = null;
        }
        this.atReturnStmnt2(result);
    }

    public int recordReturnType(CtClass type, String castName, String resultName, SymbolTable tbl) throws CompileError {
        this.returnType = type;
        this.returnCastName = castName;
        this.returnVarName = resultName;
        if (resultName == null) {
            return -1;
        }
        int varNo = this.getMaxLocals();
        int locals = varNo + this.recordVar(type, resultName, varNo, tbl);
        this.setMaxLocals(locals);
        return varNo;
    }

    public void recordType(CtClass t) {
        this.dollarType = t;
    }

    public int recordParams(CtClass[] params, boolean isStatic, String prefix, String paramVarName, String paramsName, SymbolTable tbl) throws CompileError {
        boolean bl;
        if (!isStatic) {
            bl = true;
            return this.recordParams(params, isStatic, prefix, paramVarName, paramsName, bl, 0, this.getThisName(), tbl);
        }
        bl = false;
        return this.recordParams(params, isStatic, prefix, paramVarName, paramsName, bl, 0, this.getThisName(), tbl);
    }

    public int recordParams(CtClass[] params, boolean isStatic, String prefix, String paramVarName, String paramsName, boolean use0, int paramBase, String target, SymbolTable tbl) throws CompileError {
        this.paramTypeList = params;
        this.paramArrayName = paramVarName;
        this.paramListName = paramsName;
        this.paramVarBase = paramBase;
        this.useParam0 = use0;
        if (target != null) {
            this.param0Type = MemberResolver.jvmToJavaName(target);
        }
        this.inStaticMethod = isStatic;
        int varNo = paramBase;
        if (use0) {
            String varName = prefix + "0";
            Declarator decl = new Declarator(307, MemberResolver.javaToJvmName(target), 0, varNo++, new Symbol(varName));
            tbl.append(varName, decl);
        }
        int i = 0;
        while (true) {
            if (i >= params.length) {
                if (this.getMaxLocals() >= varNo) return varNo;
                this.setMaxLocals(varNo);
                return varNo;
            }
            varNo += this.recordVar(params[i], prefix + (i + 1), varNo, tbl);
            ++i;
        }
    }

    public int recordVariable(CtClass type, String varName, SymbolTable tbl) throws CompileError {
        if (varName == null) {
            return -1;
        }
        int varNo = this.getMaxLocals();
        int locals = varNo + this.recordVar(type, varName, varNo, tbl);
        this.setMaxLocals(locals);
        return varNo;
    }

    private int recordVar(CtClass cc, String varName, int varNo, SymbolTable tbl) throws CompileError {
        if (cc == CtClass.voidType) {
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/Object";
        } else {
            this.setType(cc);
        }
        Declarator decl = new Declarator(this.exprType, this.className, this.arrayDim, varNo, new Symbol(varName));
        tbl.append(varName, decl);
        if (!JvstCodeGen.is2word(this.exprType, this.arrayDim)) return 1;
        return 2;
    }

    public void recordVariable(String typeDesc, String varName, int varNo, SymbolTable tbl) throws CompileError {
        char c;
        int dim = 0;
        while ((c = typeDesc.charAt(dim)) == '[') {
            ++dim;
        }
        int type = MemberResolver.descToType(c);
        String cname = null;
        if (type == 307) {
            cname = dim == 0 ? typeDesc.substring(1, typeDesc.length() - 1) : typeDesc.substring(dim + 1, typeDesc.length() - 1);
        }
        Declarator decl = new Declarator(type, cname, dim, varNo, new Symbol(varName));
        tbl.append(varName, decl);
    }

    public static int compileParameterList(Bytecode code, CtClass[] params, int regno) {
        if (params == null) {
            code.addIconst(0);
            code.addAnewarray("java.lang.Object");
            return 1;
        }
        CtClass[] args = new CtClass[1];
        int n = params.length;
        code.addIconst(n);
        code.addAnewarray("java.lang.Object");
        int i = 0;
        while (i < n) {
            code.addOpcode(89);
            code.addIconst(i);
            if (params[i].isPrimitive()) {
                CtPrimitiveType pt = (CtPrimitiveType)params[i];
                String wrapper = pt.getWrapperName();
                code.addNew(wrapper);
                code.addOpcode(89);
                int s = code.addLoad(regno, pt);
                regno += s;
                args[0] = pt;
                code.addInvokespecial(wrapper, "<init>", Descriptor.ofMethod(CtClass.voidType, args));
            } else {
                code.addAload(regno);
                ++regno;
            }
            code.addOpcode(83);
            ++i;
        }
        return 8;
    }

    protected void compileUnwrapValue(CtClass type, Bytecode code) throws CompileError {
        if (type == CtClass.voidType) {
            this.addNullIfVoid();
            return;
        }
        if (this.exprType == 344) {
            throw new CompileError("invalid type for " + this.returnCastName);
        }
        if (type instanceof CtPrimitiveType) {
            CtPrimitiveType pt = (CtPrimitiveType)type;
            String wrapper = pt.getWrapperName();
            code.addCheckcast(wrapper);
            code.addInvokevirtual(wrapper, pt.getGetMethodName(), pt.getGetMethodDescriptor());
            this.setType(type);
            return;
        }
        code.addCheckcast(type);
        this.setType(type);
    }

    public void setType(CtClass type) throws CompileError {
        this.setType(type, 0);
    }

    private void setType(CtClass type, int dim) throws CompileError {
        if (type.isPrimitive()) {
            CtPrimitiveType pt = (CtPrimitiveType)type;
            this.exprType = MemberResolver.descToType(pt.getDescriptor());
            this.arrayDim = dim;
            this.className = null;
            return;
        }
        if (!type.isArray()) {
            this.exprType = 307;
            this.arrayDim = dim;
            this.className = MemberResolver.javaToJvmName(type.getName());
            return;
        }
        try {
            this.setType(type.getComponentType(), dim + 1);
            return;
        }
        catch (NotFoundException e) {
            throw new CompileError("undefined type: " + type.getName());
        }
    }

    public void doNumCast(CtClass type) throws CompileError {
        if (this.arrayDim != 0) return;
        if (JvstCodeGen.isRefType(this.exprType)) return;
        if (!(type instanceof CtPrimitiveType)) throw new CompileError("type mismatch");
        CtPrimitiveType pt = (CtPrimitiveType)type;
        this.atNumCastExpr(this.exprType, MemberResolver.descToType(pt.getDescriptor()));
    }
}

