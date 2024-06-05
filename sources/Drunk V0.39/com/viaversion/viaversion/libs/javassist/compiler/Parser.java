/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist.compiler;

import com.viaversion.viaversion.libs.javassist.compiler.CodeGen;
import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import com.viaversion.viaversion.libs.javassist.compiler.Lex;
import com.viaversion.viaversion.libs.javassist.compiler.SymbolTable;
import com.viaversion.viaversion.libs.javassist.compiler.SyntaxError;
import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import com.viaversion.viaversion.libs.javassist.compiler.ast.ASTList;
import com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree;
import com.viaversion.viaversion.libs.javassist.compiler.ast.ArrayInit;
import com.viaversion.viaversion.libs.javassist.compiler.ast.AssignExpr;
import com.viaversion.viaversion.libs.javassist.compiler.ast.BinExpr;
import com.viaversion.viaversion.libs.javassist.compiler.ast.CallExpr;
import com.viaversion.viaversion.libs.javassist.compiler.ast.CastExpr;
import com.viaversion.viaversion.libs.javassist.compiler.ast.CondExpr;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Declarator;
import com.viaversion.viaversion.libs.javassist.compiler.ast.DoubleConst;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Expr;
import com.viaversion.viaversion.libs.javassist.compiler.ast.FieldDecl;
import com.viaversion.viaversion.libs.javassist.compiler.ast.InstanceOfExpr;
import com.viaversion.viaversion.libs.javassist.compiler.ast.IntConst;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Keyword;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Member;
import com.viaversion.viaversion.libs.javassist.compiler.ast.MethodDecl;
import com.viaversion.viaversion.libs.javassist.compiler.ast.NewExpr;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Pair;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Stmnt;
import com.viaversion.viaversion.libs.javassist.compiler.ast.StringL;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Symbol;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Variable;

public final class Parser
implements TokenId {
    private Lex lex;
    private static final int[] binaryOpPrecedence = new int[]{0, 0, 0, 0, 1, 6, 0, 0, 0, 1, 2, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 4, 0};

    public Parser(Lex lex) {
        this.lex = lex;
    }

    public boolean hasMore() {
        if (this.lex.lookAhead() < 0) return false;
        return true;
    }

    public ASTList parseMember(SymbolTable tbl) throws CompileError {
        ASTList mem = this.parseMember1(tbl);
        if (!(mem instanceof MethodDecl)) return mem;
        return this.parseMethod2(tbl, (MethodDecl)mem);
    }

    public ASTList parseMember1(SymbolTable tbl) throws CompileError {
        Declarator d;
        ASTList mods = this.parseMemberMods();
        boolean isConstructor = false;
        if (this.lex.lookAhead() == 400 && this.lex.lookAhead(1) == 40) {
            d = new Declarator(344, 0);
            isConstructor = true;
        } else {
            d = this.parseFormalType(tbl);
        }
        if (this.lex.get() != 400) {
            throw new SyntaxError(this.lex);
        }
        String name = isConstructor ? "<init>" : this.lex.getString();
        d.setVariable(new Symbol(name));
        if (isConstructor) return this.parseMethod1(tbl, isConstructor, mods, d);
        if (this.lex.lookAhead() != 40) return this.parseField(tbl, mods, d);
        return this.parseMethod1(tbl, isConstructor, mods, d);
    }

    private FieldDecl parseField(SymbolTable tbl, ASTList mods, Declarator d) throws CompileError {
        int c;
        ASTree expr = null;
        if (this.lex.lookAhead() == 61) {
            this.lex.get();
            expr = this.parseExpression(tbl);
        }
        if ((c = this.lex.get()) == 59) {
            return new FieldDecl(mods, new ASTList(d, new ASTList(expr)));
        }
        if (c != 44) throw new SyntaxError(this.lex);
        throw new CompileError("only one field can be declared in one declaration", this.lex);
    }

    private MethodDecl parseMethod1(SymbolTable tbl, boolean isConstructor, ASTList mods, Declarator d) throws CompileError {
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        ASTList parms = null;
        if (this.lex.lookAhead() != 41) {
            while (true) {
                parms = ASTList.append(parms, this.parseFormalParam(tbl));
                int t = this.lex.lookAhead();
                if (t == 44) {
                    this.lex.get();
                    continue;
                }
                if (t == 41) break;
            }
        }
        this.lex.get();
        d.addArrayDim(this.parseArrayDimension());
        if (isConstructor && d.getArrayDim() > 0) {
            throw new SyntaxError(this.lex);
        }
        ASTList throwsList = null;
        if (this.lex.lookAhead() != 341) return new MethodDecl(mods, new ASTList(d, ASTList.make(parms, throwsList, null)));
        this.lex.get();
        while (true) {
            throwsList = ASTList.append(throwsList, this.parseClassType(tbl));
            if (this.lex.lookAhead() != 44) return new MethodDecl(mods, new ASTList(d, ASTList.make(parms, throwsList, null)));
            this.lex.get();
        }
    }

    public MethodDecl parseMethod2(SymbolTable tbl, MethodDecl md) throws CompileError {
        Stmnt body = null;
        if (this.lex.lookAhead() == 59) {
            this.lex.get();
        } else {
            body = this.parseBlock(tbl);
            if (body == null) {
                body = new Stmnt(66);
            }
        }
        md.sublist(4).setHead(body);
        return md;
    }

    private ASTList parseMemberMods() {
        ASTList list = null;
        while (true) {
            int t;
            if ((t = this.lex.lookAhead()) != 300 && t != 315 && t != 332 && t != 331 && t != 330 && t != 338 && t != 335 && t != 345 && t != 342) {
                if (t != 347) return list;
            }
            list = new ASTList(new Keyword(this.lex.get()), list);
        }
    }

    private Declarator parseFormalType(SymbolTable tbl) throws CompileError {
        int t = this.lex.lookAhead();
        if (!Parser.isBuiltinType(t) && t != 344) {
            ASTList name = this.parseClassType(tbl);
            int dim = this.parseArrayDimension();
            return new Declarator(name, dim);
        }
        this.lex.get();
        int dim = this.parseArrayDimension();
        return new Declarator(t, dim);
    }

    private static boolean isBuiltinType(int t) {
        if (t == 301) return true;
        if (t == 303) return true;
        if (t == 306) return true;
        if (t == 334) return true;
        if (t == 324) return true;
        if (t == 326) return true;
        if (t == 317) return true;
        if (t == 312) return true;
        return false;
    }

    private Declarator parseFormalParam(SymbolTable tbl) throws CompileError {
        Declarator d = this.parseFormalType(tbl);
        if (this.lex.get() != 400) {
            throw new SyntaxError(this.lex);
        }
        String name = this.lex.getString();
        d.setVariable(new Symbol(name));
        d.addArrayDim(this.parseArrayDimension());
        tbl.append(name, d);
        return d;
    }

    public Stmnt parseStatement(SymbolTable tbl) throws CompileError {
        int t = this.lex.lookAhead();
        if (t == 123) {
            return this.parseBlock(tbl);
        }
        if (t == 59) {
            this.lex.get();
            return new Stmnt(66);
        }
        if (t == 400 && this.lex.lookAhead(1) == 58) {
            this.lex.get();
            String label = this.lex.getString();
            this.lex.get();
            return Stmnt.make(76, (ASTree)new Symbol(label), (ASTree)this.parseStatement(tbl));
        }
        if (t == 320) {
            return this.parseIf(tbl);
        }
        if (t == 346) {
            return this.parseWhile(tbl);
        }
        if (t == 311) {
            return this.parseDo(tbl);
        }
        if (t == 318) {
            return this.parseFor(tbl);
        }
        if (t == 343) {
            return this.parseTry(tbl);
        }
        if (t == 337) {
            return this.parseSwitch(tbl);
        }
        if (t == 338) {
            return this.parseSynchronized(tbl);
        }
        if (t == 333) {
            return this.parseReturn(tbl);
        }
        if (t == 340) {
            return this.parseThrow(tbl);
        }
        if (t == 302) {
            return this.parseBreak(tbl);
        }
        if (t != 309) return this.parseDeclarationOrExpression(tbl, false);
        return this.parseContinue(tbl);
    }

    private Stmnt parseBlock(SymbolTable tbl) throws CompileError {
        if (this.lex.get() != 123) {
            throw new SyntaxError(this.lex);
        }
        Stmnt body = null;
        SymbolTable tbl2 = new SymbolTable(tbl);
        while (true) {
            if (this.lex.lookAhead() == 125) {
                this.lex.get();
                if (body != null) return body;
                return new Stmnt(66);
            }
            Stmnt s = this.parseStatement(tbl2);
            if (s == null) continue;
            body = (Stmnt)ASTList.concat(body, new Stmnt(66, (ASTree)s));
        }
    }

    private Stmnt parseIf(SymbolTable tbl) throws CompileError {
        Stmnt elsep;
        int t = this.lex.get();
        ASTree expr = this.parseParExpression(tbl);
        Stmnt thenp = this.parseStatement(tbl);
        if (this.lex.lookAhead() == 313) {
            this.lex.get();
            elsep = this.parseStatement(tbl);
            return new Stmnt(t, expr, new ASTList(thenp, new ASTList(elsep)));
        }
        elsep = null;
        return new Stmnt(t, expr, new ASTList(thenp, new ASTList(elsep)));
    }

    private Stmnt parseWhile(SymbolTable tbl) throws CompileError {
        int t = this.lex.get();
        ASTree expr = this.parseParExpression(tbl);
        Stmnt body = this.parseStatement(tbl);
        return new Stmnt(t, expr, body);
    }

    private Stmnt parseDo(SymbolTable tbl) throws CompileError {
        int t = this.lex.get();
        Stmnt body = this.parseStatement(tbl);
        if (this.lex.get() != 346) throw new SyntaxError(this.lex);
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        ASTree expr = this.parseExpression(tbl);
        if (this.lex.get() != 41) throw new SyntaxError(this.lex);
        if (this.lex.get() == 59) return new Stmnt(t, expr, body);
        throw new SyntaxError(this.lex);
    }

    private Stmnt parseFor(SymbolTable tbl) throws CompileError {
        Stmnt expr1;
        int t = this.lex.get();
        SymbolTable tbl2 = new SymbolTable(tbl);
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        if (this.lex.lookAhead() == 59) {
            this.lex.get();
            expr1 = null;
        } else {
            expr1 = this.parseDeclarationOrExpression(tbl2, true);
        }
        ASTree expr2 = this.lex.lookAhead() == 59 ? null : this.parseExpression(tbl2);
        if (this.lex.get() != 59) {
            throw new CompileError("; is missing", this.lex);
        }
        Stmnt expr3 = this.lex.lookAhead() == 41 ? null : this.parseExprList(tbl2);
        if (this.lex.get() != 41) {
            throw new CompileError(") is missing", this.lex);
        }
        Stmnt body = this.parseStatement(tbl2);
        return new Stmnt(t, expr1, new ASTList(expr2, new ASTList(expr3, body)));
    }

    private Stmnt parseSwitch(SymbolTable tbl) throws CompileError {
        int t = this.lex.get();
        ASTree expr = this.parseParExpression(tbl);
        Stmnt body = this.parseSwitchBlock(tbl);
        return new Stmnt(t, expr, body);
    }

    private Stmnt parseSwitchBlock(SymbolTable tbl) throws CompileError {
        if (this.lex.get() != 123) {
            throw new SyntaxError(this.lex);
        }
        SymbolTable tbl2 = new SymbolTable(tbl);
        Stmnt s = this.parseStmntOrCase(tbl2);
        if (s == null) {
            throw new CompileError("empty switch block", this.lex);
        }
        int op = s.getOperator();
        if (op != 304 && op != 310) {
            throw new CompileError("no case or default in a switch block", this.lex);
        }
        Stmnt body = new Stmnt(66, (ASTree)s);
        while (true) {
            if (this.lex.lookAhead() == 125) {
                this.lex.get();
                return body;
            }
            Stmnt s2 = this.parseStmntOrCase(tbl2);
            if (s2 == null) continue;
            int op2 = s2.getOperator();
            if (op2 == 304 || op2 == 310) {
                body = (Stmnt)ASTList.concat(body, new Stmnt(66, (ASTree)s2));
                s = s2;
                continue;
            }
            s = (Stmnt)ASTList.concat(s, new Stmnt(66, (ASTree)s2));
        }
    }

    private Stmnt parseStmntOrCase(SymbolTable tbl) throws CompileError {
        int t = this.lex.lookAhead();
        if (t != 304 && t != 310) {
            return this.parseStatement(tbl);
        }
        this.lex.get();
        Stmnt s = t == 304 ? new Stmnt(t, this.parseExpression(tbl)) : new Stmnt(310);
        if (this.lex.get() == 58) return s;
        throw new CompileError(": is missing", this.lex);
    }

    private Stmnt parseSynchronized(SymbolTable tbl) throws CompileError {
        int t = this.lex.get();
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        ASTree expr = this.parseExpression(tbl);
        if (this.lex.get() != 41) {
            throw new SyntaxError(this.lex);
        }
        Stmnt body = this.parseBlock(tbl);
        return new Stmnt(t, expr, body);
    }

    private Stmnt parseTry(SymbolTable tbl) throws CompileError {
        this.lex.get();
        Stmnt block = this.parseBlock(tbl);
        ASTList catchList = null;
        while (true) {
            if (this.lex.lookAhead() != 305) {
                Stmnt finallyBlock = null;
                if (this.lex.lookAhead() != 316) return Stmnt.make(343, block, catchList, finallyBlock);
                this.lex.get();
                finallyBlock = this.parseBlock(tbl);
                return Stmnt.make(343, block, catchList, finallyBlock);
            }
            this.lex.get();
            if (this.lex.get() != 40) {
                throw new SyntaxError(this.lex);
            }
            SymbolTable tbl2 = new SymbolTable(tbl);
            Declarator d = this.parseFormalParam(tbl2);
            if (d.getArrayDim() > 0) throw new SyntaxError(this.lex);
            if (d.getType() != 307) {
                throw new SyntaxError(this.lex);
            }
            if (this.lex.get() != 41) {
                throw new SyntaxError(this.lex);
            }
            Stmnt b = this.parseBlock(tbl2);
            catchList = ASTList.append(catchList, new Pair(d, b));
        }
    }

    private Stmnt parseReturn(SymbolTable tbl) throws CompileError {
        int t = this.lex.get();
        Stmnt s = new Stmnt(t);
        if (this.lex.lookAhead() != 59) {
            s.setLeft(this.parseExpression(tbl));
        }
        if (this.lex.get() == 59) return s;
        throw new CompileError("; is missing", this.lex);
    }

    private Stmnt parseThrow(SymbolTable tbl) throws CompileError {
        int t = this.lex.get();
        ASTree expr = this.parseExpression(tbl);
        if (this.lex.get() == 59) return new Stmnt(t, expr);
        throw new CompileError("; is missing", this.lex);
    }

    private Stmnt parseBreak(SymbolTable tbl) throws CompileError {
        return this.parseContinue(tbl);
    }

    private Stmnt parseContinue(SymbolTable tbl) throws CompileError {
        int t = this.lex.get();
        Stmnt s = new Stmnt(t);
        int t2 = this.lex.get();
        if (t2 == 400) {
            s.setLeft(new Symbol(this.lex.getString()));
            t2 = this.lex.get();
        }
        if (t2 == 59) return s;
        throw new CompileError("; is missing", this.lex);
    }

    private Stmnt parseDeclarationOrExpression(SymbolTable tbl, boolean exprList) throws CompileError {
        int i;
        int t = this.lex.lookAhead();
        while (t == 315) {
            this.lex.get();
            t = this.lex.lookAhead();
        }
        if (Parser.isBuiltinType(t)) {
            t = this.lex.get();
            int dim = this.parseArrayDimension();
            return this.parseDeclarators(tbl, new Declarator(t, dim));
        }
        if (t == 400 && (i = this.nextIsClassType(0)) >= 0 && this.lex.lookAhead(i) == 400) {
            ASTList name = this.parseClassType(tbl);
            int dim = this.parseArrayDimension();
            return this.parseDeclarators(tbl, new Declarator(name, dim));
        }
        Stmnt expr = exprList ? this.parseExprList(tbl) : new Stmnt(69, this.parseExpression(tbl));
        if (this.lex.get() == 59) return expr;
        throw new CompileError("; is missing", this.lex);
    }

    private Stmnt parseExprList(SymbolTable tbl) throws CompileError {
        Stmnt expr = null;
        while (true) {
            Stmnt e = new Stmnt(69, this.parseExpression(tbl));
            expr = (Stmnt)ASTList.concat(expr, new Stmnt(66, (ASTree)e));
            if (this.lex.lookAhead() != 44) return expr;
            this.lex.get();
        }
    }

    private Stmnt parseDeclarators(SymbolTable tbl, Declarator d) throws CompileError {
        int t;
        Stmnt decl = null;
        do {
            decl = (Stmnt)ASTList.concat(decl, new Stmnt(68, (ASTree)this.parseDeclarator(tbl, d)));
            t = this.lex.get();
            if (t != 59) continue;
            return decl;
        } while (t == 44);
        throw new CompileError("; is missing", this.lex);
    }

    private Declarator parseDeclarator(SymbolTable tbl, Declarator d) throws CompileError {
        if (this.lex.get() != 400) throw new SyntaxError(this.lex);
        if (d.getType() == 344) {
            throw new SyntaxError(this.lex);
        }
        String name = this.lex.getString();
        Symbol symbol = new Symbol(name);
        int dim = this.parseArrayDimension();
        ASTree init = null;
        if (this.lex.lookAhead() == 61) {
            this.lex.get();
            init = this.parseInitializer(tbl);
        }
        Declarator decl = d.make(symbol, dim, init);
        tbl.append(name, decl);
        return decl;
    }

    private ASTree parseInitializer(SymbolTable tbl) throws CompileError {
        if (this.lex.lookAhead() != 123) return this.parseExpression(tbl);
        return this.parseArrayInitializer(tbl);
    }

    private ArrayInit parseArrayInitializer(SymbolTable tbl) throws CompileError {
        this.lex.get();
        if (this.lex.lookAhead() == 125) {
            this.lex.get();
            return new ArrayInit(null);
        }
        ASTree expr = this.parseExpression(tbl);
        ArrayInit init = new ArrayInit(expr);
        while (true) {
            if (this.lex.lookAhead() != 44) {
                if (this.lex.get() == 125) return init;
                throw new SyntaxError(this.lex);
            }
            this.lex.get();
            expr = this.parseExpression(tbl);
            ASTList.append(init, expr);
        }
    }

    private ASTree parseParExpression(SymbolTable tbl) throws CompileError {
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        ASTree expr = this.parseExpression(tbl);
        if (this.lex.get() == 41) return expr;
        throw new SyntaxError(this.lex);
    }

    public ASTree parseExpression(SymbolTable tbl) throws CompileError {
        ASTree left = this.parseConditionalExpr(tbl);
        if (!Parser.isAssignOp(this.lex.lookAhead())) {
            return left;
        }
        int t = this.lex.get();
        ASTree right = this.parseExpression(tbl);
        return AssignExpr.makeAssign(t, left, right);
    }

    private static boolean isAssignOp(int t) {
        if (t == 61) return true;
        if (t == 351) return true;
        if (t == 352) return true;
        if (t == 353) return true;
        if (t == 354) return true;
        if (t == 355) return true;
        if (t == 356) return true;
        if (t == 360) return true;
        if (t == 361) return true;
        if (t == 365) return true;
        if (t == 367) return true;
        if (t == 371) return true;
        return false;
    }

    private ASTree parseConditionalExpr(SymbolTable tbl) throws CompileError {
        ASTree cond = this.parseBinaryExpr(tbl);
        if (this.lex.lookAhead() != 63) return cond;
        this.lex.get();
        ASTree thenExpr = this.parseExpression(tbl);
        if (this.lex.get() != 58) {
            throw new CompileError(": is missing", this.lex);
        }
        ASTree elseExpr = this.parseExpression(tbl);
        return new CondExpr(cond, thenExpr, elseExpr);
    }

    private ASTree parseBinaryExpr(SymbolTable tbl) throws CompileError {
        ASTree expr = this.parseUnaryExpr(tbl);
        int t;
        int p;
        while ((p = this.getOpPrecedence(t = this.lex.lookAhead())) != 0) {
            expr = this.binaryExpr2(tbl, expr, p);
        }
        return expr;
    }

    private ASTree parseInstanceOf(SymbolTable tbl, ASTree expr) throws CompileError {
        int t = this.lex.lookAhead();
        if (Parser.isBuiltinType(t)) {
            this.lex.get();
            int dim = this.parseArrayDimension();
            return new InstanceOfExpr(t, dim, expr);
        }
        ASTList name = this.parseClassType(tbl);
        int dim = this.parseArrayDimension();
        return new InstanceOfExpr(name, dim, expr);
    }

    private ASTree binaryExpr2(SymbolTable tbl, ASTree expr, int prec) throws CompileError {
        int t2;
        int p2;
        int t = this.lex.get();
        if (t == 323) {
            return this.parseInstanceOf(tbl, expr);
        }
        ASTree expr2 = this.parseUnaryExpr(tbl);
        while ((p2 = this.getOpPrecedence(t2 = this.lex.lookAhead())) != 0) {
            if (prec <= p2) return BinExpr.makeBin(t, expr, expr2);
            expr2 = this.binaryExpr2(tbl, expr2, p2);
        }
        return BinExpr.makeBin(t, expr, expr2);
    }

    private int getOpPrecedence(int c) {
        if (33 <= c && c <= 63) {
            return binaryOpPrecedence[c - 33];
        }
        if (c == 94) {
            return 7;
        }
        if (c == 124) {
            return 8;
        }
        if (c == 369) {
            return 9;
        }
        if (c == 368) {
            return 10;
        }
        if (c == 358) return 5;
        if (c == 350) {
            return 5;
        }
        if (c == 357) return 4;
        if (c == 359) return 4;
        if (c == 323) {
            return 4;
        }
        if (c == 364) return 3;
        if (c == 366) return 3;
        if (c != 370) return 0;
        return 3;
    }

    private ASTree parseUnaryExpr(SymbolTable tbl) throws CompileError {
        switch (this.lex.lookAhead()) {
            case 33: 
            case 43: 
            case 45: 
            case 126: 
            case 362: 
            case 363: {
                int t = this.lex.get();
                if (t != 45) return Expr.make(t, this.parseUnaryExpr(tbl));
                int t2 = this.lex.lookAhead();
                switch (t2) {
                    case 401: 
                    case 402: 
                    case 403: {
                        this.lex.get();
                        return new IntConst(-this.lex.getLong(), t2);
                    }
                    case 404: 
                    case 405: {
                        this.lex.get();
                        return new DoubleConst(-this.lex.getDouble(), t2);
                    }
                }
                return Expr.make(t, this.parseUnaryExpr(tbl));
            }
            case 40: {
                return this.parseCast(tbl);
            }
        }
        return this.parsePostfix(tbl);
    }

    private ASTree parseCast(SymbolTable tbl) throws CompileError {
        int t = this.lex.lookAhead(1);
        if (Parser.isBuiltinType(t) && this.nextIsBuiltinCast()) {
            this.lex.get();
            this.lex.get();
            int dim = this.parseArrayDimension();
            if (this.lex.get() == 41) return new CastExpr(t, dim, this.parseUnaryExpr(tbl));
            throw new CompileError(") is missing", this.lex);
        }
        if (t != 400) return this.parsePostfix(tbl);
        if (!this.nextIsClassCast()) return this.parsePostfix(tbl);
        this.lex.get();
        ASTList name = this.parseClassType(tbl);
        int dim = this.parseArrayDimension();
        if (this.lex.get() == 41) return new CastExpr(name, dim, this.parseUnaryExpr(tbl));
        throw new CompileError(") is missing", this.lex);
    }

    private boolean nextIsBuiltinCast() {
        int t;
        int i = 2;
        while ((t = this.lex.lookAhead(i++)) == 91) {
            if (this.lex.lookAhead(i++) == 93) continue;
            return false;
        }
        if (this.lex.lookAhead(i - 1) != 41) return false;
        return true;
    }

    private boolean nextIsClassCast() {
        int i = this.nextIsClassType(1);
        if (i < 0) {
            return false;
        }
        int t = this.lex.lookAhead(i);
        if (t != 41) {
            return false;
        }
        t = this.lex.lookAhead(i + 1);
        if (t == 40) return true;
        if (t == 412) return true;
        if (t == 406) return true;
        if (t == 400) return true;
        if (t == 339) return true;
        if (t == 336) return true;
        if (t == 328) return true;
        if (t == 410) return true;
        if (t == 411) return true;
        if (t == 403) return true;
        if (t == 402) return true;
        if (t == 401) return true;
        if (t == 405) return true;
        if (t == 404) return true;
        return false;
    }

    private int nextIsClassType(int i) {
        while (this.lex.lookAhead(++i) == 46) {
            if (this.lex.lookAhead(++i) == 400) continue;
            return -1;
        }
        do {
            int t;
            if ((t = this.lex.lookAhead(i++)) != 91) return i - 1;
        } while (this.lex.lookAhead(i++) == 93);
        return -1;
    }

    private int parseArrayDimension() throws CompileError {
        int arrayDim = 0;
        do {
            if (this.lex.lookAhead() != 91) return arrayDim;
            ++arrayDim;
            this.lex.get();
        } while (this.lex.get() == 93);
        throw new CompileError("] is missing", this.lex);
    }

    private ASTList parseClassType(SymbolTable tbl) throws CompileError {
        ASTList list = null;
        while (true) {
            if (this.lex.get() != 400) {
                throw new SyntaxError(this.lex);
            }
            list = ASTList.append(list, new Symbol(this.lex.getString()));
            if (this.lex.lookAhead() != 46) return list;
            this.lex.get();
        }
    }

    private ASTree parsePostfix(SymbolTable tbl) throws CompileError {
        int token = this.lex.lookAhead();
        switch (token) {
            case 401: 
            case 402: 
            case 403: {
                this.lex.get();
                return new IntConst(this.lex.getLong(), token);
            }
            case 404: 
            case 405: {
                this.lex.get();
                return new DoubleConst(this.lex.getDouble(), token);
            }
        }
        ASTree expr = this.parsePrimaryExpr(tbl);
        block11: while (true) {
            switch (this.lex.lookAhead()) {
                case 40: {
                    expr = this.parseMethodCall(tbl, expr);
                    continue block11;
                }
                case 91: {
                    if (this.lex.lookAhead(1) == 93) {
                        int dim = this.parseArrayDimension();
                        if (this.lex.get() != 46) throw new SyntaxError(this.lex);
                        if (this.lex.get() != 307) {
                            throw new SyntaxError(this.lex);
                        }
                        expr = this.parseDotClass(expr, dim);
                        continue block11;
                    }
                    ASTree index = this.parseArrayIndex(tbl);
                    if (index == null) {
                        throw new SyntaxError(this.lex);
                    }
                    expr = Expr.make(65, expr, index);
                    continue block11;
                }
                case 362: 
                case 363: {
                    int t = this.lex.get();
                    expr = Expr.make(t, null, expr);
                    continue block11;
                }
                case 46: {
                    this.lex.get();
                    int t = this.lex.get();
                    if (t == 307) {
                        expr = this.parseDotClass(expr, 0);
                        continue block11;
                    }
                    if (t == 336) {
                        expr = Expr.make(46, (ASTree)new Symbol(this.toClassName(expr)), (ASTree)new Keyword(t));
                        continue block11;
                    }
                    if (t != 400) throw new CompileError("missing member name", this.lex);
                    String str = this.lex.getString();
                    expr = Expr.make(46, expr, (ASTree)new Member(str));
                    continue block11;
                }
                case 35: {
                    this.lex.get();
                    int t = this.lex.get();
                    if (t != 400) {
                        throw new CompileError("missing static member name", this.lex);
                    }
                    String str = this.lex.getString();
                    expr = Expr.make(35, (ASTree)new Symbol(this.toClassName(expr)), (ASTree)new Member(str));
                    continue block11;
                }
            }
            break;
        }
        return expr;
    }

    private ASTree parseDotClass(ASTree className, int dim) throws CompileError {
        String cname = this.toClassName(className);
        if (dim <= 0) return Expr.make(46, (ASTree)new Symbol(cname), (ASTree)new Member("class"));
        StringBuffer sbuf = new StringBuffer();
        while (true) {
            if (dim-- <= 0) {
                sbuf.append('L').append(cname.replace('.', '/')).append(';');
                cname = sbuf.toString();
                return Expr.make(46, (ASTree)new Symbol(cname), (ASTree)new Member("class"));
            }
            sbuf.append('[');
        }
    }

    private ASTree parseDotClass(int builtinType, int dim) throws CompileError {
        if (dim > 0) {
            String cname = CodeGen.toJvmTypeName(builtinType, dim);
            return Expr.make(46, (ASTree)new Symbol(cname), (ASTree)new Member("class"));
        }
        switch (builtinType) {
            case 301: {
                String cname = "java.lang.Boolean";
                return Expr.make(35, (ASTree)new Symbol(cname), (ASTree)new Member("TYPE"));
            }
            case 303: {
                String cname = "java.lang.Byte";
                return Expr.make(35, (ASTree)new Symbol(cname), (ASTree)new Member("TYPE"));
            }
            case 306: {
                String cname = "java.lang.Character";
                return Expr.make(35, (ASTree)new Symbol(cname), (ASTree)new Member("TYPE"));
            }
            case 334: {
                String cname = "java.lang.Short";
                return Expr.make(35, (ASTree)new Symbol(cname), (ASTree)new Member("TYPE"));
            }
            case 324: {
                String cname = "java.lang.Integer";
                return Expr.make(35, (ASTree)new Symbol(cname), (ASTree)new Member("TYPE"));
            }
            case 326: {
                String cname = "java.lang.Long";
                return Expr.make(35, (ASTree)new Symbol(cname), (ASTree)new Member("TYPE"));
            }
            case 317: {
                String cname = "java.lang.Float";
                return Expr.make(35, (ASTree)new Symbol(cname), (ASTree)new Member("TYPE"));
            }
            case 312: {
                String cname = "java.lang.Double";
                return Expr.make(35, (ASTree)new Symbol(cname), (ASTree)new Member("TYPE"));
            }
            case 344: {
                String cname = "java.lang.Void";
                return Expr.make(35, (ASTree)new Symbol(cname), (ASTree)new Member("TYPE"));
            }
        }
        throw new CompileError("invalid builtin type: " + builtinType);
    }

    private ASTree parseMethodCall(SymbolTable tbl, ASTree expr) throws CompileError {
        if (expr instanceof Keyword) {
            int token = ((Keyword)expr).get();
            if (token == 339) return CallExpr.makeCall(expr, this.parseArgumentList(tbl));
            if (token == 336) return CallExpr.makeCall(expr, this.parseArgumentList(tbl));
            throw new SyntaxError(this.lex);
        }
        if (expr instanceof Symbol) {
            return CallExpr.makeCall(expr, this.parseArgumentList(tbl));
        }
        if (!(expr instanceof Expr)) return CallExpr.makeCall(expr, this.parseArgumentList(tbl));
        int op = ((Expr)expr).getOperator();
        if (op == 46) return CallExpr.makeCall(expr, this.parseArgumentList(tbl));
        if (op == 35) return CallExpr.makeCall(expr, this.parseArgumentList(tbl));
        throw new SyntaxError(this.lex);
    }

    private String toClassName(ASTree name) throws CompileError {
        StringBuffer sbuf = new StringBuffer();
        this.toClassName(name, sbuf);
        return sbuf.toString();
    }

    private void toClassName(ASTree name, StringBuffer sbuf) throws CompileError {
        if (name instanceof Symbol) {
            sbuf.append(((Symbol)name).get());
            return;
        }
        if (!(name instanceof Expr)) throw new CompileError("bad static member access", this.lex);
        Expr expr = (Expr)name;
        if (expr.getOperator() != 46) throw new CompileError("bad static member access", this.lex);
        this.toClassName(expr.oprand1(), sbuf);
        sbuf.append('.');
        this.toClassName(expr.oprand2(), sbuf);
    }

    private ASTree parsePrimaryExpr(SymbolTable tbl) throws CompileError {
        int t = this.lex.get();
        switch (t) {
            case 336: 
            case 339: 
            case 410: 
            case 411: 
            case 412: {
                return new Keyword(t);
            }
            case 400: {
                String name = this.lex.getString();
                Declarator decl = tbl.lookup(name);
                if (decl != null) return new Variable(name, decl);
                return new Member(name);
            }
            case 406: {
                return new StringL(this.lex.getString());
            }
            case 328: {
                return this.parseNew(tbl);
            }
            case 40: {
                ASTree expr = this.parseExpression(tbl);
                if (this.lex.get() != 41) throw new CompileError(") is missing", this.lex);
                return expr;
            }
        }
        if (!Parser.isBuiltinType(t)) {
            if (t != 344) throw new SyntaxError(this.lex);
        }
        int dim = this.parseArrayDimension();
        if (this.lex.get() != 46) throw new SyntaxError(this.lex);
        if (this.lex.get() != 307) throw new SyntaxError(this.lex);
        return this.parseDotClass(t, dim);
    }

    private NewExpr parseNew(SymbolTable tbl) throws CompileError {
        ArrayInit init = null;
        int t = this.lex.lookAhead();
        if (Parser.isBuiltinType(t)) {
            this.lex.get();
            ASTList size = this.parseArraySize(tbl);
            if (this.lex.lookAhead() != 123) return new NewExpr(t, size, init);
            init = this.parseArrayInitializer(tbl);
            return new NewExpr(t, size, init);
        }
        if (t != 400) throw new SyntaxError(this.lex);
        ASTList name = this.parseClassType(tbl);
        t = this.lex.lookAhead();
        if (t == 40) {
            ASTList args = this.parseArgumentList(tbl);
            return new NewExpr(name, args);
        }
        if (t != 91) throw new SyntaxError(this.lex);
        ASTList size = this.parseArraySize(tbl);
        if (this.lex.lookAhead() != 123) return NewExpr.makeObjectArray(name, size, init);
        init = this.parseArrayInitializer(tbl);
        return NewExpr.makeObjectArray(name, size, init);
    }

    private ASTList parseArraySize(SymbolTable tbl) throws CompileError {
        ASTList list = null;
        while (this.lex.lookAhead() == 91) {
            list = ASTList.append(list, this.parseArrayIndex(tbl));
        }
        return list;
    }

    private ASTree parseArrayIndex(SymbolTable tbl) throws CompileError {
        this.lex.get();
        if (this.lex.lookAhead() == 93) {
            this.lex.get();
            return null;
        }
        ASTree index = this.parseExpression(tbl);
        if (this.lex.get() == 93) return index;
        throw new CompileError("] is missing", this.lex);
    }

    private ASTList parseArgumentList(SymbolTable tbl) throws CompileError {
        if (this.lex.get() != 40) {
            throw new CompileError("( is missing", this.lex);
        }
        ASTList list = null;
        if (this.lex.lookAhead() != 41) {
            while (true) {
                list = ASTList.append(list, this.parseExpression(tbl));
                if (this.lex.lookAhead() != 44) break;
                this.lex.get();
            }
        }
        if (this.lex.get() == 41) return list;
        throw new CompileError(") is missing", this.lex);
    }
}

