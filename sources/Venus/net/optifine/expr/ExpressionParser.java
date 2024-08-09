/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.expr;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.optifine.Config;
import net.optifine.expr.ConstantFloat;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.FunctionBool;
import net.optifine.expr.FunctionFloat;
import net.optifine.expr.FunctionFloatArray;
import net.optifine.expr.FunctionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.IExpressionResolver;
import net.optifine.expr.ParseException;
import net.optifine.expr.Token;
import net.optifine.expr.TokenParser;
import net.optifine.expr.TokenType;

public class ExpressionParser {
    private IExpressionResolver expressionResolver;

    public ExpressionParser(IExpressionResolver iExpressionResolver) {
        this.expressionResolver = iExpressionResolver;
    }

    public IExpressionFloat parseFloat(String string) throws ParseException {
        IExpression iExpression = this.parse(string);
        if (!(iExpression instanceof IExpressionFloat)) {
            throw new ParseException("Not a float expression: " + iExpression.getExpressionType());
        }
        return (IExpressionFloat)iExpression;
    }

    public IExpressionBool parseBool(String string) throws ParseException {
        IExpression iExpression = this.parse(string);
        if (!(iExpression instanceof IExpressionBool)) {
            throw new ParseException("Not a boolean expression: " + iExpression.getExpressionType());
        }
        return (IExpressionBool)iExpression;
    }

    public IExpression parse(String string) throws ParseException {
        try {
            Token[] tokenArray = TokenParser.parse(string);
            if (tokenArray == null) {
                return null;
            }
            ArrayDeque<Token> arrayDeque = new ArrayDeque<Token>(Arrays.asList(tokenArray));
            return this.parseInfix(arrayDeque);
        } catch (IOException iOException) {
            throw new ParseException(iOException.getMessage(), iOException);
        }
    }

    private IExpression parseInfix(Deque<Token> deque) throws ParseException {
        if (deque.isEmpty()) {
            return null;
        }
        LinkedList<IExpression> linkedList = new LinkedList<IExpression>();
        LinkedList<Token> linkedList2 = new LinkedList<Token>();
        IExpression iExpression = this.parseExpression(deque);
        ExpressionParser.checkNull(iExpression, "Missing expression");
        linkedList.add(iExpression);
        Token token;
        while ((token = deque.poll()) != null) {
            if (token.getType() != TokenType.OPERATOR) {
                throw new ParseException("Invalid operator: " + token);
            }
            IExpression iExpression2 = this.parseExpression(deque);
            ExpressionParser.checkNull(iExpression2, "Missing expression");
            linkedList2.add(token);
            linkedList.add(iExpression2);
        }
        return this.makeInfix(linkedList, linkedList2);
    }

    private IExpression makeInfix(List<IExpression> list, List<Token> list2) throws ParseException {
        LinkedList<FunctionType> linkedList = new LinkedList<FunctionType>();
        for (Token token : list2) {
            FunctionType functionType = FunctionType.parse(token.getText());
            ExpressionParser.checkNull((Object)functionType, "Invalid operator: " + token);
            linkedList.add(functionType);
        }
        return this.makeInfixFunc(list, linkedList);
    }

    private IExpression makeInfixFunc(List<IExpression> list, List<FunctionType> list2) throws ParseException {
        if (list.size() != list2.size() + 1) {
            throw new ParseException("Invalid infix expression, expressions: " + list.size() + ", operators: " + list2.size());
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        int n = Integer.MAX_VALUE;
        int n2 = Integer.MIN_VALUE;
        for (FunctionType functionType : list2) {
            n = Math.min(functionType.getPrecedence(), n);
            n2 = Math.max(functionType.getPrecedence(), n2);
        }
        if (n2 >= n && n2 - n <= 10) {
            for (int i = n2; i >= n; --i) {
                this.mergeOperators(list, list2, i);
            }
            if (list.size() == 1 && list2.size() == 0) {
                return list.get(0);
            }
            throw new ParseException("Error merging operators, expressions: " + list.size() + ", operators: " + list2.size());
        }
        throw new ParseException("Invalid infix precedence, min: " + n + ", max: " + n2);
    }

    private void mergeOperators(List<IExpression> list, List<FunctionType> list2, int n) throws ParseException {
        for (int i = 0; i < list2.size(); ++i) {
            FunctionType functionType = list2.get(i);
            if (functionType.getPrecedence() != n) continue;
            list2.remove(i);
            IExpression iExpression = list.remove(i);
            IExpression iExpression2 = list.remove(i);
            IExpression iExpression3 = ExpressionParser.makeFunction(functionType, new IExpression[]{iExpression, iExpression2});
            list.add(i, iExpression3);
            --i;
        }
    }

    private IExpression parseExpression(Deque<Token> deque) throws ParseException {
        Token token = deque.poll();
        ExpressionParser.checkNull(token, "Missing expression");
        switch (1.$SwitchMap$net$optifine$expr$TokenType[token.getType().ordinal()]) {
            case 1: {
                return ExpressionParser.makeConstantFloat(token);
            }
            case 2: {
                FunctionType functionType = this.getFunctionType(token, deque);
                if (functionType != null) {
                    return this.makeFunction(functionType, deque);
                }
                return this.makeVariable(token);
            }
            case 3: {
                return this.makeBracketed(token, deque);
            }
            case 4: {
                FunctionType functionType = FunctionType.parse(token.getText());
                ExpressionParser.checkNull((Object)functionType, "Invalid operator: " + token);
                if (functionType == FunctionType.PLUS) {
                    return this.parseExpression(deque);
                }
                if (functionType == FunctionType.MINUS) {
                    IExpression iExpression = this.parseExpression(deque);
                    return ExpressionParser.makeFunction(FunctionType.NEG, new IExpression[]{iExpression});
                }
                if (functionType != FunctionType.NOT) break;
                IExpression iExpression = this.parseExpression(deque);
                return ExpressionParser.makeFunction(FunctionType.NOT, new IExpression[]{iExpression});
            }
        }
        throw new ParseException("Invalid expression: " + token);
    }

    private static IExpression makeConstantFloat(Token token) throws ParseException {
        float f = Config.parseFloat(token.getText(), Float.NaN);
        if (f == Float.NaN) {
            throw new ParseException("Invalid float value: " + token);
        }
        return new ConstantFloat(f);
    }

    private FunctionType getFunctionType(Token token, Deque<Token> deque) throws ParseException {
        Token token2 = deque.peek();
        if (token2 != null && token2.getType() == TokenType.BRACKET_OPEN) {
            FunctionType functionType = FunctionType.parse(token.getText());
            ExpressionParser.checkNull((Object)functionType, "Unknown function: " + token);
            return functionType;
        }
        FunctionType functionType = FunctionType.parse(token.getText());
        if (functionType == null) {
            return null;
        }
        if (functionType.getParameterCount(new IExpression[0]) > 0) {
            throw new ParseException("Missing arguments: " + functionType);
        }
        return functionType;
    }

    private IExpression makeFunction(FunctionType functionType, Deque<Token> deque) throws ParseException {
        Token token;
        if (functionType.getParameterCount(new IExpression[0]) == 0 && ((token = deque.peek()) == null || token.getType() != TokenType.BRACKET_OPEN)) {
            return ExpressionParser.makeFunction(functionType, new IExpression[0]);
        }
        token = deque.poll();
        Deque<Token> deque2 = ExpressionParser.getGroup(deque, TokenType.BRACKET_CLOSE, true);
        IExpression[] iExpressionArray = this.parseExpressions(deque2);
        return ExpressionParser.makeFunction(functionType, iExpressionArray);
    }

    private IExpression[] parseExpressions(Deque<Token> deque) throws ParseException {
        ArrayList<IExpression> arrayList = new ArrayList<IExpression>();
        Deque<Token> deque2;
        IExpression iExpression;
        while ((iExpression = this.parseInfix(deque2 = ExpressionParser.getGroup(deque, TokenType.COMMA, false))) != null) {
            arrayList.add(iExpression);
        }
        return arrayList.toArray(new IExpression[arrayList.size()]);
    }

    private static IExpression makeFunction(FunctionType functionType, IExpression[] iExpressionArray) throws ParseException {
        ExpressionType[] expressionTypeArray = functionType.getParameterTypes(iExpressionArray);
        if (iExpressionArray.length != expressionTypeArray.length) {
            throw new ParseException("Invalid number of arguments, function: \"" + functionType.getName() + "\", count arguments: " + iExpressionArray.length + ", should be: " + expressionTypeArray.length);
        }
        for (int i = 0; i < iExpressionArray.length; ++i) {
            ExpressionType expressionType;
            IExpression iExpression = iExpressionArray[i];
            ExpressionType expressionType2 = iExpression.getExpressionType();
            if (expressionType2 == (expressionType = expressionTypeArray[i])) continue;
            throw new ParseException("Invalid argument type, function: \"" + functionType.getName() + "\", index: " + i + ", type: " + expressionType2 + ", should be: " + expressionType);
        }
        if (functionType.getExpressionType() == ExpressionType.FLOAT) {
            return new FunctionFloat(functionType, iExpressionArray);
        }
        if (functionType.getExpressionType() == ExpressionType.BOOL) {
            return new FunctionBool(functionType, iExpressionArray);
        }
        if (functionType.getExpressionType() == ExpressionType.FLOAT_ARRAY) {
            return new FunctionFloatArray(functionType, iExpressionArray);
        }
        throw new ParseException("Unknown function type: " + functionType.getExpressionType() + ", function: " + functionType.getName());
    }

    private IExpression makeVariable(Token token) throws ParseException {
        if (this.expressionResolver == null) {
            throw new ParseException("Model variable not found: " + token);
        }
        IExpression iExpression = this.expressionResolver.getExpression(token.getText());
        if (iExpression == null) {
            throw new ParseException("Model variable not found: " + token);
        }
        return iExpression;
    }

    private IExpression makeBracketed(Token token, Deque<Token> deque) throws ParseException {
        Deque<Token> deque2 = ExpressionParser.getGroup(deque, TokenType.BRACKET_CLOSE, true);
        return this.parseInfix(deque2);
    }

    private static Deque<Token> getGroup(Deque<Token> deque, TokenType tokenType, boolean bl) throws ParseException {
        ArrayDeque<Token> arrayDeque = new ArrayDeque<Token>();
        int n = 0;
        Iterator iterator2 = arrayDeque.iterator();
        while (iterator2.hasNext()) {
            Token token = (Token)iterator2.next();
            iterator2.remove();
            if (n == 0 && token.getType() == tokenType) {
                return arrayDeque;
            }
            arrayDeque.add(token);
            if (token.getType() == TokenType.BRACKET_OPEN) {
                ++n;
            }
            if (token.getType() != TokenType.BRACKET_CLOSE) continue;
            --n;
        }
        if (bl) {
            throw new ParseException("Missing end token: " + tokenType);
        }
        return arrayDeque;
    }

    private static void checkNull(Object object, String string) throws ParseException {
        if (object == null) {
            throw new ParseException(string);
        }
    }
}

