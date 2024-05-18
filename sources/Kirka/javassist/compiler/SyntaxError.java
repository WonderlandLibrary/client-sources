/*
 * Decompiled with CFR 0.143.
 */
package javassist.compiler;

import javassist.compiler.CompileError;
import javassist.compiler.Lex;

public class SyntaxError
extends CompileError {
    public SyntaxError(Lex lexer) {
        super("syntax error near \"" + lexer.getTextAround() + "\"", lexer);
    }
}

