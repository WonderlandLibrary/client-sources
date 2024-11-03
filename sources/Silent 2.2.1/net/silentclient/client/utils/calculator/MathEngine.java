package net.silentclient.client.utils.calculator;

public class MathEngine {
    public static double eval(final String str) {
        try {
            Double.parseDouble(str);
            throw new RuntimeException("Input is already complete");
        } catch (NumberFormatException e) {
            return (new Object() {
                int pos = -1;

                int ch;

                void nextChar() {
                    this.ch = (++this.pos < str.length()) ? str.charAt(this.pos) : -1;
                }

                boolean eat(int charToEat) {
                    for (; this.ch == 32; nextChar());
                    if (this.ch == charToEat) {
                        nextChar();
                        return true;
                    }
                    return false;
                }

                double parse() {
                    nextChar();
                    double x = parseExpression();
                    if (this.pos < str.length())
                        throw new RuntimeException("Unexpected: " + (char)this.ch);
                    return x;
                }

                double parseExpression() {
                    double x = parseTerm();
                    while (true) {
                        for (; eat(43); x += parseTerm());
                        if (eat(45)) {
                            x -= parseTerm();
                            continue;
                        }
                        return x;
                    }
                }

                double parseTerm() {
                    double x = parseFactor();
                    while (true) {
                        for (; eat(42); x *= parseFactor());
                        if (eat(47)) {
                            x /= parseFactor();
                            continue;
                        }
                        return x;
                    }
                }

                double parseFactor() {
                    double x;
                    if (eat(43))
                        return parseFactor();
                    if (eat(45))
                        return -parseFactor();
                    int startPos = this.pos;
                    if (eat(40)) {
                        x = parseExpression();
                        eat(41);
                    } else {
                        if ((this.ch >= 48 && this.ch <= 57) || this.ch == 46 || this.ch == 44)
                            while (true) {
                                if ((this.ch >= 48 && this.ch <= 57) || this.ch == 46 || this.ch == 44) {
                                    nextChar();
                                    continue;
                                }
                                x = Double.parseDouble(str.substring(startPos, this.pos).replaceAll(",", ""));
                                if (eat(94))
                                    x = Math.pow(x, parseFactor());
                                return x;
                            }
                        if (this.ch >= 97 && this.ch <= 122) {
                            for (; this.ch >= 97 && this.ch <= 122; nextChar());
                            String func = str.substring(startPos, this.pos);
                            x = parseFactor();
                            if (func.equals("sqrt")) {
                                x = Math.sqrt(x);
                            } else if (func.equals("sin")) {
                                x = Math.sin(Math.toRadians(x));
                            } else if (func.equals("cos")) {
                                x = Math.cos(Math.toRadians(x));
                            } else if (func.equals("tan")) {
                                x = Math.tan(Math.toRadians(x));
                            } else {
                                throw new RuntimeException("Unknown function: " + func);
                            }
                        } else {
                            throw new RuntimeException("Unexpected: " + (char)this.ch);
                        }
                    }
                    if (eat(94))
                        x = Math.pow(x, parseFactor());
                    return x;
                }
            }).parse();
        }
    }
}
