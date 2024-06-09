package org.spongepowered.asm.lib.signature;

public class SignatureReader
{
    private final String signature;
    
    public SignatureReader(final String signature) {
        super();
        this.signature = signature;
    }
    
    public void accept(final SignatureVisitor signatureVisitor) {
        final String signature = this.signature;
        final int length = signature.length();
        int n;
        if (signature.charAt(0) == '<') {
            n = 2;
            char char1;
            do {
                final int index = signature.indexOf(58, n);
                signatureVisitor.visitFormalTypeParameter(signature.substring(n - 1, index));
                n = index + 1;
                final char char2 = signature.charAt(n);
                if (char2 == 'L' || char2 == '[' || char2 == 'T') {
                    n = parseType(signature, n, signatureVisitor.visitClassBound());
                }
                while ((char1 = signature.charAt(n++)) == ':') {
                    n = parseType(signature, n, signatureVisitor.visitInterfaceBound());
                }
            } while (char1 != '>');
        }
        else {
            n = 0;
        }
        if (signature.charAt(n) == '(') {
            ++n;
            while (signature.charAt(n) != ')') {
                n = parseType(signature, n, signatureVisitor.visitParameterType());
            }
            for (int i = parseType(signature, n + 1, signatureVisitor.visitReturnType()); i < length; i = parseType(signature, i + 1, signatureVisitor.visitExceptionType())) {}
        }
        else {
            for (int j = parseType(signature, n, signatureVisitor.visitSuperclass()); j < length; j = parseType(signature, j, signatureVisitor.visitInterface())) {}
        }
    }
    
    public void acceptType(final SignatureVisitor signatureVisitor) {
        parseType(this.signature, 0, signatureVisitor);
    }
    
    private static int parseType(final String s, int n, final SignatureVisitor signatureVisitor) {
        final char char1;
        switch (char1 = s.charAt(n++)) {
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'V':
            case 'Z': {
                signatureVisitor.visitBaseType(char1);
                return n;
            }
            case '[': {
                return parseType(s, n, signatureVisitor.visitArrayType());
            }
            case 'T': {
                final int index = s.indexOf(59, n);
                signatureVisitor.visitTypeVariable(s.substring(n, index));
                return index + 1;
            }
            default: {
                int n2 = n;
                int n3 = 0;
                int n4 = 0;
            Block_3:
                while (true) {
                    final char char2;
                    switch (char2 = s.charAt(n++)) {
                        case '.':
                        case ';': {
                            if (n3 == 0) {
                                final String substring = s.substring(n2, n - 1);
                                if (n4 != 0) {
                                    signatureVisitor.visitInnerClassType(substring);
                                }
                                else {
                                    signatureVisitor.visitClassType(substring);
                                }
                            }
                            if (char2 == ';') {
                                break Block_3;
                            }
                            n2 = n;
                            n3 = 0;
                            n4 = 1;
                            continue;
                        }
                        case '<': {
                            final String substring2 = s.substring(n2, n - 1);
                            if (n4 != 0) {
                                signatureVisitor.visitInnerClassType(substring2);
                            }
                            else {
                                signatureVisitor.visitClassType(substring2);
                            }
                            n3 = 1;
                        Label_0368:
                            while (true) {
                                final char char3;
                                switch (char3 = s.charAt(n)) {
                                    case '>': {
                                        break Label_0368;
                                    }
                                    case '*': {
                                        ++n;
                                        signatureVisitor.visitTypeArgument();
                                        continue;
                                    }
                                    case '+':
                                    case '-': {
                                        n = parseType(s, n + 1, signatureVisitor.visitTypeArgument(char3));
                                        continue;
                                    }
                                    default: {
                                        n = parseType(s, n, signatureVisitor.visitTypeArgument('='));
                                        continue;
                                    }
                                }
                            }
                            continue;
                        }
                    }
                }
                signatureVisitor.visitEnd();
                return n;
            }
        }
    }
}
