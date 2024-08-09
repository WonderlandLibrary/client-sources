/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.expr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import net.optifine.expr.ExpressionParser;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.IExpressionFloat;

public class TestExpressions {
    public static void main(String[] stringArray) throws Exception {
        ExpressionParser expressionParser = new ExpressionParser(null);
        while (true) {
            try {
                while (true) {
                    IExpression iExpression;
                    InputStreamReader inputStreamReader;
                    BufferedReader bufferedReader;
                    String string;
                    if ((string = (bufferedReader = new BufferedReader(inputStreamReader = new InputStreamReader(System.in))).readLine()).length() <= 0) {
                        return;
                    }
                    IExpression iExpression2 = expressionParser.parse(string);
                    if (iExpression2 instanceof IExpressionFloat) {
                        iExpression = (IExpressionFloat)iExpression2;
                        float f = iExpression.eval();
                        System.out.println("" + f);
                    }
                    if (!(iExpression2 instanceof IExpressionBool)) continue;
                    iExpression = (IExpressionBool)iExpression2;
                    boolean bl = iExpression.eval();
                    System.out.println("" + bl);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                continue;
            }
            break;
        }
    }
}

