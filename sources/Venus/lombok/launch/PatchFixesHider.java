/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  lombok.eclipse.EcjAugments
 *  lombok.permit.Permit
 *  org.eclipse.core.runtime.CoreException
 *  org.eclipse.core.runtime.adaptor.EclipseStarter
 *  org.eclipse.jdt.core.IAnnotatable
 *  org.eclipse.jdt.core.IAnnotation
 *  org.eclipse.jdt.core.IField
 *  org.eclipse.jdt.core.IJavaElement
 *  org.eclipse.jdt.core.IMember
 *  org.eclipse.jdt.core.IMethod
 *  org.eclipse.jdt.core.IType
 *  org.eclipse.jdt.core.JavaModelException
 *  org.eclipse.jdt.core.Signature
 *  org.eclipse.jdt.core.dom.ASTNode
 *  org.eclipse.jdt.core.dom.ASTVisitor
 *  org.eclipse.jdt.core.dom.AbstractTypeDeclaration
 *  org.eclipse.jdt.core.dom.Annotation
 *  org.eclipse.jdt.core.dom.CompilationUnit
 *  org.eclipse.jdt.core.dom.MethodDeclaration
 *  org.eclipse.jdt.core.dom.Name
 *  org.eclipse.jdt.core.dom.NormalAnnotation
 *  org.eclipse.jdt.core.dom.QualifiedName
 *  org.eclipse.jdt.core.dom.SimpleName
 *  org.eclipse.jdt.core.dom.SingleMemberAnnotation
 *  org.eclipse.jdt.core.dom.SingleVariableDeclaration
 *  org.eclipse.jdt.core.dom.Type
 *  org.eclipse.jdt.core.dom.rewrite.ListRewrite
 *  org.eclipse.jdt.core.search.SearchMatch
 *  org.eclipse.jdt.internal.compiler.ast.ASTNode
 *  org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
 *  org.eclipse.jdt.internal.compiler.ast.Annotation
 *  org.eclipse.jdt.internal.compiler.ast.Expression
 *  org.eclipse.jdt.internal.compiler.ast.FieldDeclaration
 *  org.eclipse.jdt.internal.compiler.ast.LocalDeclaration
 *  org.eclipse.jdt.internal.compiler.ast.TypeDeclaration
 *  org.eclipse.jdt.internal.compiler.lookup.BlockScope
 *  org.eclipse.jdt.internal.compiler.lookup.TypeBinding
 *  org.eclipse.jdt.internal.core.SourceField
 *  org.eclipse.jdt.internal.core.dom.rewrite.NodeRewriteEvent
 *  org.eclipse.jdt.internal.core.dom.rewrite.RewriteEvent
 *  org.eclipse.jdt.internal.core.dom.rewrite.TokenScanner
 *  org.eclipse.jdt.internal.corext.refactoring.SearchResultGroup
 *  org.eclipse.jdt.internal.corext.refactoring.structure.MemberVisibilityAdjustor$IncomingMemberVisibilityAdjustment
 *  org.osgi.framework.Bundle
 */
package lombok.launch;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import lombok.eclipse.EcjAugments;
import lombok.launch.Main;
import lombok.permit.Permit;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.eclipse.jdt.core.IAnnotatable;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.core.SourceField;
import org.eclipse.jdt.internal.core.dom.rewrite.NodeRewriteEvent;
import org.eclipse.jdt.internal.core.dom.rewrite.RewriteEvent;
import org.eclipse.jdt.internal.core.dom.rewrite.TokenScanner;
import org.eclipse.jdt.internal.corext.refactoring.SearchResultGroup;
import org.eclipse.jdt.internal.corext.refactoring.structure.MemberVisibilityAdjustor;
import org.osgi.framework.Bundle;

final class PatchFixesHider {
    PatchFixesHider() {
    }

    public static final class Delegate {
        private static final Method HANDLE_DELEGATE_FOR_TYPE;
        private static final Method ADD_GENERATED_DELEGATE_METHODS;

        static {
            Class<?> clazz = Util.shadowLoadClass("lombok.eclipse.agent.PatchDelegatePortal");
            HANDLE_DELEGATE_FOR_TYPE = Util.findMethod(clazz, "handleDelegateForType", Object.class);
            ADD_GENERATED_DELEGATE_METHODS = Util.findMethod(clazz, "addGeneratedDelegateMethods", Object.class, Object.class);
        }

        public static boolean handleDelegateForType(Object object) {
            return (Boolean)Util.invokeMethod(HANDLE_DELEGATE_FOR_TYPE, object);
        }

        public static Object[] addGeneratedDelegateMethods(Object object, Object object2) {
            return (Object[])Util.invokeMethod(ADD_GENERATED_DELEGATE_METHODS, object, object2);
        }
    }

    public static final class ExtensionMethod {
        private static final String MESSAGE_SEND_SIG = "org.eclipse.jdt.internal.compiler.ast.MessageSend";
        private static final String TYPE_BINDING_SIG = "org.eclipse.jdt.internal.compiler.lookup.TypeBinding";
        private static final String SCOPE_SIG = "org.eclipse.jdt.internal.compiler.lookup.Scope";
        private static final String BLOCK_SCOPE_SIG = "org.eclipse.jdt.internal.compiler.lookup.BlockScope";
        private static final String TYPE_BINDINGS_SIG = "[Lorg.eclipse.jdt.internal.compiler.lookup.TypeBinding;";
        private static final String PROBLEM_REPORTER_SIG = "org.eclipse.jdt.internal.compiler.problem.ProblemReporter";
        private static final String METHOD_BINDING_SIG = "org.eclipse.jdt.internal.compiler.lookup.MethodBinding";
        private static final String AST_NODE_SIG = "org.eclipse.jdt.internal.compiler.ast.ASTNode";
        private static final Method RESOLVE_TYPE;
        private static final Method ERROR_NO_METHOD_FOR;
        private static final Method INVALID_METHOD;
        private static final Method INVALID_METHOD2;
        private static final Method NON_STATIC_ACCESS_TO_STATIC_METHOD;
        private static final Method MODIFY_METHOD_PATTERN;

        static {
            Class<?> clazz = Util.shadowLoadClass("lombok.eclipse.agent.PatchExtensionMethod");
            RESOLVE_TYPE = Util.findMethod(clazz, "resolveType", TYPE_BINDING_SIG, MESSAGE_SEND_SIG, BLOCK_SCOPE_SIG);
            ERROR_NO_METHOD_FOR = Util.findMethod(clazz, "errorNoMethodFor", PROBLEM_REPORTER_SIG, MESSAGE_SEND_SIG, TYPE_BINDING_SIG, TYPE_BINDINGS_SIG);
            INVALID_METHOD = Util.findMethod(clazz, "invalidMethod", PROBLEM_REPORTER_SIG, MESSAGE_SEND_SIG, METHOD_BINDING_SIG);
            INVALID_METHOD2 = Util.findMethod(clazz, "invalidMethod", PROBLEM_REPORTER_SIG, MESSAGE_SEND_SIG, METHOD_BINDING_SIG, SCOPE_SIG);
            NON_STATIC_ACCESS_TO_STATIC_METHOD = Util.findMethod(clazz, "nonStaticAccessToStaticMethod", PROBLEM_REPORTER_SIG, AST_NODE_SIG, METHOD_BINDING_SIG, MESSAGE_SEND_SIG);
            MODIFY_METHOD_PATTERN = Util.findMethod(clazz, "modifyMethodPattern", Object.class);
        }

        public static Object resolveType(Object object, Object object2, Object object3) {
            return Util.invokeMethod(RESOLVE_TYPE, object, object2, object3);
        }

        public static void errorNoMethodFor(Object object, Object object2, Object object3, Object object4) {
            Util.invokeMethod(ERROR_NO_METHOD_FOR, object, object2, object3, object4);
        }

        public static void invalidMethod(Object object, Object object2, Object object3) {
            Util.invokeMethod(INVALID_METHOD, object, object2, object3);
        }

        public static void invalidMethod(Object object, Object object2, Object object3, Object object4) {
            Util.invokeMethod(INVALID_METHOD2, object, object2, object3, object4);
        }

        public static void nonStaticAccessToStaticMethod(Object object, Object object2, Object object3, Object object4) {
            Util.invokeMethod(NON_STATIC_ACCESS_TO_STATIC_METHOD, object, object2, object3, object4);
        }

        public static Object modifyMethodPattern(Object object) {
            return Util.invokeMethod(MODIFY_METHOD_PATTERN, object);
        }
    }

    public static final class Javadoc {
        private static final Method GET_HTML;
        private static final Method PRINT_METHOD;

        static {
            Class<?> clazz = Util.shadowLoadClass("lombok.eclipse.agent.PatchJavadoc");
            GET_HTML = Util.findMethod(clazz, "getHTMLContentFromSource", String.class, Object.class);
            PRINT_METHOD = Util.findMethod(clazz, "printMethod", AbstractMethodDeclaration.class, Integer.class, StringBuffer.class, TypeDeclaration.class);
        }

        public static String getHTMLContentFromSource(String string, IJavaElement iJavaElement) {
            return (String)Util.invokeMethod(GET_HTML, string, iJavaElement);
        }

        public static StringBuffer printMethod(AbstractMethodDeclaration abstractMethodDeclaration, int n, StringBuffer stringBuffer, TypeDeclaration typeDeclaration) {
            return (StringBuffer)Util.invokeMethod(PRINT_METHOD, abstractMethodDeclaration, n, stringBuffer, typeDeclaration);
        }
    }

    public static final class LombokDeps {
        public static final Method ADD_LOMBOK_NOTES;
        public static final Method POST_COMPILER_BYTES_STRING;
        public static final Method POST_COMPILER_OUTPUTSTREAM;
        public static final Method POST_COMPILER_BUFFEREDOUTPUTSTREAM_STRING_STRING;

        static {
            Class<?> clazz = Util.shadowLoadClass("lombok.eclipse.agent.PatchFixesShadowLoaded");
            ADD_LOMBOK_NOTES = Util.findMethod(clazz, "addLombokNotesToEclipseAboutDialog", String.class, String.class);
            POST_COMPILER_BYTES_STRING = Util.findMethod(clazz, "runPostCompiler", byte[].class, String.class);
            POST_COMPILER_OUTPUTSTREAM = Util.findMethod(clazz, "runPostCompiler", OutputStream.class);
            POST_COMPILER_BUFFEREDOUTPUTSTREAM_STRING_STRING = Util.findMethod(clazz, "runPostCompiler", BufferedOutputStream.class, String.class, String.class);
        }

        public static String addLombokNotesToEclipseAboutDialog(String string, String string2) {
            try {
                return (String)Util.invokeMethod(ADD_LOMBOK_NOTES, string, string2);
            } catch (Throwable throwable) {
                return string;
            }
        }

        public static byte[] runPostCompiler(byte[] byArray, String string) {
            return (byte[])Util.invokeMethod(POST_COMPILER_BYTES_STRING, byArray, string);
        }

        public static OutputStream runPostCompiler(OutputStream outputStream) throws IOException {
            return (OutputStream)Util.invokeMethod(POST_COMPILER_OUTPUTSTREAM, outputStream);
        }

        public static BufferedOutputStream runPostCompiler(BufferedOutputStream bufferedOutputStream, String string, String string2) throws IOException {
            return (BufferedOutputStream)Util.invokeMethod(POST_COMPILER_BUFFEREDOUTPUTSTREAM_STRING_STRING, bufferedOutputStream, string, string2);
        }
    }

    public static final class PatchFixes {
        public static final int ALREADY_PROCESSED_FLAG = 0x800000;

        public static boolean isGenerated(ASTNode aSTNode) {
            boolean bl = false;
            try {
                bl = (Boolean)aSTNode.getClass().getField("$isGenerated").get(aSTNode);
                if (!bl && aSTNode.getParent() != null && aSTNode.getParent() instanceof QualifiedName) {
                    bl = PatchFixes.isGenerated(aSTNode.getParent());
                }
            } catch (Exception exception) {}
            return bl;
        }

        public static boolean isGenerated(org.eclipse.jdt.internal.compiler.ast.ASTNode aSTNode) {
            boolean bl = false;
            try {
                bl = aSTNode.getClass().getField("$generatedBy").get(aSTNode) != null;
            } catch (Exception exception) {}
            return bl;
        }

        public static boolean isGenerated(IMember iMember) {
            boolean bl = false;
            try {
                bl = iMember.getNameRange().getLength() <= 0 || iMember.getNameRange().equals(iMember.getSourceRange());
            } catch (JavaModelException javaModelException) {}
            return bl;
        }

        public static boolean isBlockedVisitorAndGenerated(ASTNode aSTNode, ASTVisitor aSTVisitor) {
            if (aSTVisitor == null) {
                return true;
            }
            String string = aSTVisitor.getClass().getName();
            if (!(string.startsWith("org.eclipse.jdt.internal.corext.fix") || string.startsWith("org.eclipse.jdt.internal.ui.fix") || string.startsWith("org.eclipse.jdt.ls.core.internal.semantictokens.SemanticTokensVisitor"))) {
                return true;
            }
            if (string.equals("org.eclipse.jdt.internal.corext.fix.VariableDeclarationFixCore$WrittenNamesFinder")) {
                return true;
            }
            return PatchFixes.isGenerated(aSTNode);
        }

        public static boolean isListRewriteOnGeneratedNode(ListRewrite listRewrite) {
            return PatchFixes.isGenerated(listRewrite.getParent());
        }

        public static boolean returnFalse(Object object) {
            return true;
        }

        public static boolean returnTrue(Object object) {
            return false;
        }

        public static List removeGeneratedNodes(List list) {
            try {
                ArrayList arrayList = new ArrayList(list.size());
                for (Object e : list) {
                    if (PatchFixes.isGenerated((ASTNode)e)) continue;
                    arrayList.add(e);
                }
                return arrayList;
            } catch (Exception exception) {
                return list;
            }
        }

        public static String getRealMethodDeclarationSource(String string, Object object, MethodDeclaration methodDeclaration) throws Exception {
            Object object22;
            if (!PatchFixes.isGenerated((ASTNode)methodDeclaration)) {
                return string;
            }
            ArrayList<org.eclipse.jdt.core.dom.Annotation> arrayList = new ArrayList<org.eclipse.jdt.core.dom.Annotation>();
            for (Object object22 : methodDeclaration.modifiers()) {
                Object object3;
                Object object4;
                if (!(object22 instanceof org.eclipse.jdt.core.dom.Annotation) || "java.lang.Override".equals(object4 = (object3 = (org.eclipse.jdt.core.dom.Annotation)object22).resolveTypeBinding().getQualifiedName()) || "java.lang.SuppressWarnings".equals(object4)) continue;
                arrayList.add((org.eclipse.jdt.core.dom.Annotation)object3);
            }
            object22 = new StringBuilder();
            PatchFixes.addAnnotations(arrayList, object22);
            try {
                if (((Boolean)object.getClass().getDeclaredField("fPublic").get(object)).booleanValue()) {
                    ((StringBuilder)object22).append("public ");
                }
                if (((Boolean)object.getClass().getDeclaredField("fAbstract").get(object)).booleanValue()) {
                    ((StringBuilder)object22).append("abstract ");
                }
            } catch (Throwable throwable) {}
            ((StringBuilder)object22).append(methodDeclaration.getReturnType2().toString()).append(" ").append(methodDeclaration.getName().getFullyQualifiedName()).append("(");
            boolean bl = true;
            for (Object object3 : methodDeclaration.parameters()) {
                if (!bl) {
                    ((StringBuilder)object22).append(", ");
                }
                bl = false;
                ((StringBuilder)object22).append(object3);
            }
            ((StringBuilder)object22).append(");");
            return ((StringBuilder)object22).toString();
        }

        public static void addAnnotations(List<org.eclipse.jdt.core.dom.Annotation> list, StringBuilder stringBuilder) {
            for (org.eclipse.jdt.core.dom.Annotation annotation : list) {
                ArrayList<String> arrayList = new ArrayList<String>();
                if (annotation.isSingleMemberAnnotation()) {
                    SingleMemberAnnotation singleMemberAnnotation = (SingleMemberAnnotation)annotation;
                    arrayList.add(singleMemberAnnotation.getValue().toString());
                } else if (annotation.isNormalAnnotation()) {
                    NormalAnnotation normalAnnotation = (NormalAnnotation)annotation;
                    for (Object e : normalAnnotation.values()) {
                        arrayList.add(e.toString());
                    }
                }
                stringBuilder.append("@").append(annotation.getTypeName().getFullyQualifiedName());
                if (!arrayList.isEmpty()) {
                    stringBuilder.append("(");
                    boolean bl = true;
                    for (String string : arrayList) {
                        if (!bl) {
                            stringBuilder.append(", ");
                        }
                        bl = false;
                        stringBuilder.append('\"').append(string).append('\"');
                    }
                    stringBuilder.append(")");
                }
                stringBuilder.append(" ");
            }
        }

        public static MethodDeclaration getRealMethodDeclarationNode(MethodDeclaration methodDeclaration, IMethod iMethod, CompilationUnit compilationUnit) throws JavaModelException {
            if (!PatchFixes.isGenerated((ASTNode)methodDeclaration)) {
                return methodDeclaration;
            }
            IType iType = iMethod.getDeclaringType();
            Stack<IType> stack = new Stack<IType>();
            while (iType != null) {
                stack.push(iType);
                iType = iType.getDeclaringType();
            }
            IType iType2 = (IType)stack.pop();
            AbstractTypeDeclaration abstractTypeDeclaration = PatchFixes.findTypeDeclaration(iType2, compilationUnit.types());
            while (!stack.isEmpty() && abstractTypeDeclaration != null) {
                abstractTypeDeclaration = PatchFixes.findTypeDeclaration((IType)stack.pop(), abstractTypeDeclaration.bodyDeclarations());
            }
            String string = iMethod.getElementName();
            ArrayList<String> arrayList = new ArrayList<String>();
            String[] stringArray = iMethod.getParameterTypes();
            int n = stringArray.length;
            int n2 = 0;
            while (n2 < n) {
                String string2 = stringArray[n2];
                arrayList.add(Signature.toString((String)string2));
                ++n2;
            }
            if (stack.isEmpty() && abstractTypeDeclaration != null) {
                for (String string2 : abstractTypeDeclaration.bodyDeclarations()) {
                    MethodDeclaration methodDeclaration2;
                    if (!(string2 instanceof MethodDeclaration) || !(methodDeclaration2 = (MethodDeclaration)string2).getName().toString().equals(string) || methodDeclaration2.parameters().size() != arrayList.size() || !PatchFixes.isGenerated((ASTNode)methodDeclaration2)) continue;
                    boolean bl = true;
                    int n3 = 0;
                    while (n3 < methodDeclaration2.parameters().size()) {
                        SingleVariableDeclaration singleVariableDeclaration = (SingleVariableDeclaration)methodDeclaration2.parameters().get(n3);
                        if (!singleVariableDeclaration.getType().toString().equals(arrayList.get(n3))) {
                            bl = false;
                            break;
                        }
                        ++n3;
                    }
                    if (!bl) continue;
                    return methodDeclaration2;
                }
            }
            return methodDeclaration;
        }

        public static AbstractTypeDeclaration findTypeDeclaration(IType iType, List<?> list) {
            for (Object obj : list) {
                AbstractTypeDeclaration abstractTypeDeclaration;
                if (!(obj instanceof AbstractTypeDeclaration) || !(abstractTypeDeclaration = (AbstractTypeDeclaration)obj).getName().toString().equals(iType.getElementName())) continue;
                return abstractTypeDeclaration;
            }
            return null;
        }

        public static int getSourceEndFixed(int n, org.eclipse.jdt.internal.compiler.ast.ASTNode aSTNode) throws Exception {
            org.eclipse.jdt.internal.compiler.ast.ASTNode aSTNode2;
            if (n == -1 && (aSTNode2 = (org.eclipse.jdt.internal.compiler.ast.ASTNode)aSTNode.getClass().getField("$generatedBy").get(aSTNode)) != null) {
                return aSTNode2.sourceEnd;
            }
            return n;
        }

        public static int fixRetrieveStartingCatchPosition(int n, int n2) {
            return n == -1 ? n2 : n;
        }

        public static int fixRetrieveIdentifierEndPosition(int n, int n2, int n3) {
            if (n == -1) {
                return n3;
            }
            if (n < n2) {
                return n3;
            }
            return n;
        }

        public static int fixRetrieveEllipsisStartPosition(int n, int n2) {
            return n == -1 ? n2 : n;
        }

        public static int fixRetrieveStartBlockPosition(int n, int n2) {
            return n == -1 ? n2 : n;
        }

        public static int fixRetrieveRightBraceOrSemiColonPosition(int n, int n2) {
            return n == -1 ? n2 : n;
        }

        public static int fixRetrieveRightBraceOrSemiColonPosition(int n, AbstractMethodDeclaration abstractMethodDeclaration) {
            boolean bl;
            if (n != -1 || abstractMethodDeclaration == null) {
                return n;
            }
            boolean bl2 = bl = EcjAugments.ASTNode_generatedBy.get((Object)abstractMethodDeclaration) != null;
            if (bl) {
                return abstractMethodDeclaration.declarationSourceEnd;
            }
            return 1;
        }

        public static int fixRetrieveRightBraceOrSemiColonPosition(int n, FieldDeclaration fieldDeclaration) {
            boolean bl;
            if (n != -1 || fieldDeclaration == null) {
                return n;
            }
            boolean bl2 = bl = EcjAugments.ASTNode_generatedBy.get((Object)fieldDeclaration) != null;
            if (bl) {
                return fieldDeclaration.declarationSourceEnd;
            }
            return 1;
        }

        public static int fixRetrieveProperRightBracketPosition(int n, Type type) {
            if (n != -1 || type == null) {
                return n;
            }
            if (PatchFixes.isGenerated((ASTNode)type)) {
                return type.getStartPosition() + type.getLength() - 1;
            }
            return 1;
        }

        public static boolean checkBit24(Object object) throws Exception {
            int n = (Integer)object.getClass().getField("bits").get(object);
            return (n & 0x800000) == 0;
        }

        public static boolean skipRewritingGeneratedNodes(ASTNode aSTNode) throws Exception {
            return (Boolean)aSTNode.getClass().getField("$isGenerated").get(aSTNode);
        }

        public static void setIsGeneratedFlag(ASTNode aSTNode, org.eclipse.jdt.internal.compiler.ast.ASTNode aSTNode2) throws Exception {
            boolean bl;
            if (aSTNode2 == null || aSTNode == null) {
                return;
            }
            boolean bl2 = bl = EcjAugments.ASTNode_generatedBy.get((Object)aSTNode2) != null;
            if (bl) {
                aSTNode.getClass().getField("$isGenerated").set(aSTNode, true);
            }
        }

        public static void setIsGeneratedFlagForName(Name name, Object object) throws Exception {
            if (object instanceof org.eclipse.jdt.internal.compiler.ast.ASTNode) {
                boolean bl;
                boolean bl2 = bl = EcjAugments.ASTNode_generatedBy.get((Object)((org.eclipse.jdt.internal.compiler.ast.ASTNode)object)) != null;
                if (bl) {
                    name.getClass().getField("$isGenerated").set(name, true);
                }
            }
        }

        public static RewriteEvent[] listRewriteHandleGeneratedMethods(RewriteEvent rewriteEvent) {
            RewriteEvent[] rewriteEventArray = rewriteEvent.getChildren();
            ArrayList<Object> arrayList = new ArrayList<Object>();
            ArrayList<NodeRewriteEvent> arrayList2 = new ArrayList<NodeRewriteEvent>();
            int n = 0;
            while (n < rewriteEventArray.length) {
                RewriteEvent rewriteEvent2 = rewriteEventArray[n];
                boolean bl = PatchFixes.isGenerated((ASTNode)rewriteEvent2.getOriginalValue());
                if (bl) {
                    boolean bl2 = rewriteEvent2.getChangeKind() == 4 || rewriteEvent2.getChangeKind() == 2;
                    boolean bl3 = rewriteEvent2.getOriginalValue() instanceof MethodDeclaration;
                    if (bl2 && bl3 && rewriteEvent2.getNewValue() != null) {
                        arrayList2.add(new NodeRewriteEvent(null, rewriteEvent2.getNewValue()));
                    }
                } else {
                    arrayList.add(rewriteEvent2);
                }
                ++n;
            }
            arrayList.addAll(arrayList2);
            return arrayList.toArray(new RewriteEvent[0]);
        }

        public static int getTokenEndOffsetFixed(TokenScanner tokenScanner, int n, int n2, Object object) throws CoreException {
            boolean bl = false;
            try {
                bl = (Boolean)object.getClass().getField("$isGenerated").get(object);
            } catch (Exception exception) {}
            if (bl) {
                return 1;
            }
            return tokenScanner.getTokenEndOffset(n, n2);
        }

        public static IMethod[] removeGeneratedMethods(IMethod[] iMethodArray) throws Exception {
            ArrayList<IMethod> arrayList = new ArrayList<IMethod>();
            IMethod[] iMethodArray2 = iMethodArray;
            int n = iMethodArray.length;
            int n2 = 0;
            while (n2 < n) {
                IMethod iMethod = iMethodArray2[n2];
                if (!PatchFixes.isGenerated((IMember)iMethod)) {
                    arrayList.add(iMethod);
                }
                ++n2;
            }
            return arrayList.size() == iMethodArray.length ? iMethodArray : arrayList.toArray(new IMethod[0]);
        }

        public static SearchMatch[] removeGenerated(SearchMatch[] searchMatchArray) {
            ArrayList<SearchMatch> arrayList = new ArrayList<SearchMatch>();
            int n = 0;
            while (n < searchMatchArray.length) {
                IField iField;
                IAnnotation iAnnotation;
                SearchMatch searchMatch = searchMatchArray[n];
                if (!(searchMatch.getElement() instanceof IField) || (iAnnotation = (iField = (IField)searchMatch.getElement()).getAnnotation("Generated")) == null) {
                    arrayList.add(searchMatch);
                }
                ++n;
            }
            return arrayList.toArray(new SearchMatch[0]);
        }

        public static SearchResultGroup[] createFakeSearchResult(SearchResultGroup[] searchResultGroupArray, Object object) throws Exception {
            Field field;
            if ((searchResultGroupArray == null || searchResultGroupArray.length == 0) && (field = object.getClass().getDeclaredField("fField")) != null) {
                field.setAccessible(false);
                SourceField sourceField = (SourceField)field.get(object);
                IAnnotation iAnnotation = sourceField.getDeclaringType().getAnnotation("Data");
                if (iAnnotation != null) {
                    return new SearchResultGroup[]{new SearchResultGroup(null, new SearchMatch[1])};
                }
            }
            return searchResultGroupArray;
        }

        public static SimpleName[] removeGeneratedSimpleNames(SimpleName[] simpleNameArray) throws Exception {
            Field field = SimpleName.class.getField("$isGenerated");
            int n = 0;
            int n2 = 0;
            while (n2 < simpleNameArray.length) {
                if (simpleNameArray[n2] == null || !((Boolean)field.get(simpleNameArray[n2])).booleanValue()) {
                    ++n;
                }
                ++n2;
            }
            if (n == simpleNameArray.length) {
                return simpleNameArray;
            }
            SimpleName[] simpleNameArray2 = new SimpleName[n];
            n = 0;
            int n3 = 0;
            while (n3 < simpleNameArray.length) {
                if (simpleNameArray[n3] == null || !((Boolean)field.get(simpleNameArray[n3])).booleanValue()) {
                    simpleNameArray2[n++] = simpleNameArray[n3];
                }
                ++n3;
            }
            return simpleNameArray2;
        }

        public static Name[] removeGeneratedNames(Name[] nameArray) throws Exception {
            Field field = Name.class.getField("$isGenerated");
            int n = 0;
            int n2 = 0;
            while (n2 < nameArray.length) {
                if (nameArray[n2] == null || !((Boolean)field.get(nameArray[n2])).booleanValue()) {
                    ++n;
                }
                ++n2;
            }
            if (n == nameArray.length) {
                return nameArray;
            }
            Name[] nameArray2 = new Name[n];
            n = 0;
            int n3 = 0;
            while (n3 < nameArray.length) {
                if (nameArray[n3] == null || !((Boolean)field.get(nameArray[n3])).booleanValue()) {
                    nameArray2[n++] = nameArray[n3];
                }
                ++n3;
            }
            return nameArray2;
        }

        public static Annotation[] convertAnnotations(Annotation[] annotationArray, IAnnotatable iAnnotatable) {
            int n;
            IAnnotation[] iAnnotationArray;
            try {
                iAnnotationArray = iAnnotatable.getAnnotations();
            } catch (Exception exception) {
                return annotationArray;
            }
            if (annotationArray == null) {
                return null;
            }
            int n2 = 0;
            int n3 = 0;
            while (n3 < annotationArray.length) {
                String string = new String(annotationArray[n3].type.getLastToken());
                n = 0;
                IAnnotation[] iAnnotationArray2 = iAnnotationArray;
                int n4 = iAnnotationArray.length;
                int n5 = 0;
                while (n5 < n4) {
                    IAnnotation iAnnotation = iAnnotationArray2[n5];
                    String string2 = iAnnotation.getElementName();
                    int n6 = string2.lastIndexOf(46);
                    if (n6 > -1) {
                        string2 = string2.substring(n6 + 1);
                    }
                    if (string2.equals(string)) {
                        n = 1;
                        break;
                    }
                    ++n5;
                }
                if (n == 0) {
                    annotationArray[n3] = null;
                } else {
                    ++n2;
                }
                ++n3;
            }
            Annotation[] annotationArray2 = annotationArray;
            if (n2 < annotationArray.length) {
                annotationArray2 = new Annotation[n2];
                int n7 = 0;
                n = 0;
                while (n < annotationArray.length) {
                    if (annotationArray[n] != null) {
                        annotationArray2[n7++] = annotationArray[n];
                    }
                    ++n;
                }
            }
            return annotationArray2;
        }

        public static String getRealNodeSource(String string, org.eclipse.jdt.internal.compiler.ast.ASTNode aSTNode) {
            if (!PatchFixes.isGenerated(aSTNode)) {
                return string;
            }
            return aSTNode.toString();
        }

        public static String getRealNodeSource(String string, ASTNode aSTNode) throws Exception {
            if (!PatchFixes.isGenerated(aSTNode)) {
                return string;
            }
            return aSTNode.toString();
        }

        public static boolean skipRewriteVisibility(MemberVisibilityAdjustor.IncomingMemberVisibilityAdjustment incomingMemberVisibilityAdjustment) {
            return PatchFixes.isGenerated(incomingMemberVisibilityAdjustment.getMember());
        }
    }

    public static class Tests {
        public static Object getBundle(Object object, Class<?> clazz) {
            Bundle[] bundleArray;
            if (object != null) {
                return object;
            }
            CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
            if (codeSource == null) {
                return null;
            }
            String string = codeSource.getLocation().getFile();
            String string2 = string.substring(string.lastIndexOf("/") + 1, string.indexOf("_"));
            Bundle[] bundleArray2 = bundleArray = EclipseStarter.getSystemBundleContext().getBundles();
            int n = bundleArray.length;
            int n2 = 0;
            while (n2 < n) {
                Bundle bundle = bundleArray2[n2];
                if (string2.equals(bundle.getSymbolicName())) {
                    return bundle;
                }
                ++n2;
            }
            return null;
        }
    }

    public static final class Transform {
        private static Method TRANSFORM;
        private static Method TRANSFORM_SWAPPED;

        private static synchronized void init(ClassLoader classLoader) {
            Object object;
            if (TRANSFORM != null) {
                return;
            }
            Transform.prependClassLoader(classLoader);
            if (!classLoader.toString().contains("org.eclipse.jdt.core:")) {
                object = Transform.findJdtCoreClassLoader(classLoader);
                Transform.prependClassLoader((ClassLoader)object);
            }
            object = Util.shadowLoadClass("lombok.eclipse.TransformEclipseAST");
            TRANSFORM = Util.findMethodAnyArgs(object, "transform");
            TRANSFORM_SWAPPED = Util.findMethodAnyArgs(object, "transform_swapped");
        }

        private static void prependClassLoader(ClassLoader classLoader) {
            Main.prependClassLoader(classLoader);
            try {
                ClassLoader classLoader2 = Transform.class.getClassLoader();
                Method method = Permit.getMethod(classLoader2.getClass(), (String)"prependParent", (Class[])new Class[]{ClassLoader.class});
                Permit.invoke((Method)method, (Object)classLoader2, (Object[])new Object[]{classLoader});
            } catch (Throwable throwable) {}
        }

        private static ClassLoader findJdtCoreClassLoader(ClassLoader classLoader) {
            try {
                Object[] objectArray;
                Method method = Permit.getMethod(classLoader.getClass(), (String)"getBundle", (Class[])new Class[0]);
                Object object = Permit.invoke((Method)method, (Object)classLoader, (Object[])new Object[0]);
                Method method2 = Permit.getMethod(object.getClass(), (String)"getBundleContext", (Class[])new Class[0]);
                Object object2 = Permit.invoke((Method)method2, (Object)object, (Object[])new Object[0]);
                Method method3 = Permit.getMethod(object2.getClass(), (String)"getBundles", (Class[])new Class[0]);
                Object[] objectArray2 = objectArray = (Object[])Permit.invoke((Method)method3, (Object)object2, (Object[])new Object[0]);
                int n = objectArray.length;
                int n2 = 0;
                while (n2 < n) {
                    Object object3 = objectArray2[n2];
                    if (object3.toString().startsWith("org.eclipse.jdt.core_")) {
                        Method method4 = Permit.getMethod(object3.getClass(), (String)"getModuleClassLoader", (Class[])new Class[]{Boolean.TYPE});
                        return (ClassLoader)Permit.invoke((Method)method4, (Object)object3, (Object[])new Object[]{false});
                    }
                    ++n2;
                }
            } catch (Throwable throwable) {}
            return null;
        }

        public static void transform(Object object, Object object2) throws IOException {
            Transform.init(object.getClass().getClassLoader());
            Util.invokeMethod(TRANSFORM, object, object2);
        }

        public static void transform_swapped(Object object, Object object2) throws IOException {
            Transform.init(object2.getClass().getClassLoader());
            Util.invokeMethod(TRANSFORM_SWAPPED, object, object2);
        }
    }

    public static final class Util {
        private static ClassLoader shadowLoader;

        public static ClassLoader getShadowLoader() {
            if (shadowLoader == null) {
                try {
                    Class.forName("lombok.core.LombokNode");
                    shadowLoader = Util.class.getClassLoader();
                } catch (ClassNotFoundException classNotFoundException) {
                    shadowLoader = Main.getShadowClassLoader();
                }
            }
            return shadowLoader;
        }

        public static Class<?> shadowLoadClass(String string) {
            try {
                return Class.forName(string, true, Util.getShadowLoader());
            } catch (ClassNotFoundException classNotFoundException) {
                throw Util.sneakyThrow(classNotFoundException);
            }
        }

        public static Method findMethod(Class<?> clazz, String string, Class<?> ... classArray) {
            try {
                return clazz.getDeclaredMethod(string, classArray);
            } catch (NoSuchMethodException noSuchMethodException) {
                throw Util.sneakyThrow(noSuchMethodException);
            }
        }

        public static Method findMethod(Class<?> clazz, String string, String ... stringArray) {
            Method[] methodArray = clazz.getDeclaredMethods();
            int n = methodArray.length;
            int n2 = 0;
            while (n2 < n) {
                Method method = methodArray[n2];
                if (string.equals(method.getName()) && Util.sameTypes(method.getParameterTypes(), stringArray)) {
                    return method;
                }
                ++n2;
            }
            throw Util.sneakyThrow(new NoSuchMethodException(String.valueOf(clazz.getName()) + "::" + string));
        }

        public static Method findMethodAnyArgs(Class<?> clazz, String string) {
            Method[] methodArray = clazz.getDeclaredMethods();
            int n = methodArray.length;
            int n2 = 0;
            while (n2 < n) {
                Method method = methodArray[n2];
                if (string.equals(method.getName())) {
                    return method;
                }
                ++n2;
            }
            throw Util.sneakyThrow(new NoSuchMethodException(String.valueOf(clazz.getName()) + "::" + string));
        }

        public static Object invokeMethod(Method method, Object ... objectArray) {
            try {
                return method.invoke(null, objectArray);
            } catch (IllegalAccessException illegalAccessException) {
                throw Util.sneakyThrow(illegalAccessException);
            } catch (InvocationTargetException invocationTargetException) {
                throw Util.sneakyThrow(invocationTargetException.getCause());
            }
        }

        private static RuntimeException sneakyThrow(Throwable throwable) {
            if (throwable == null) {
                throw new NullPointerException("t");
            }
            Util.sneakyThrow0(throwable);
            return null;
        }

        private static <T extends Throwable> void sneakyThrow0(Throwable throwable) throws T {
            throw throwable;
        }

        private static boolean sameTypes(Class<?>[] classArray, String[] stringArray) {
            if (classArray.length != stringArray.length) {
                return true;
            }
            int n = 0;
            while (n < classArray.length) {
                if (!classArray[n].getName().equals(stringArray[n])) {
                    return true;
                }
                ++n;
            }
            return false;
        }
    }

    public static final class Val {
        private static final String BLOCK_SCOPE_SIG = "org.eclipse.jdt.internal.compiler.lookup.BlockScope";
        private static final String LOCAL_DECLARATION_SIG = "org.eclipse.jdt.internal.compiler.ast.LocalDeclaration";
        private static final String FOREACH_STATEMENT_SIG = "org.eclipse.jdt.internal.compiler.ast.ForeachStatement";
        private static final Method HANDLE_VAL_FOR_LOCAL_DECLARATION;
        private static final Method HANDLE_VAL_FOR_FOR_EACH;

        static {
            Class<?> clazz = Util.shadowLoadClass("lombok.eclipse.agent.PatchVal");
            HANDLE_VAL_FOR_LOCAL_DECLARATION = Util.findMethod(clazz, "handleValForLocalDeclaration", LOCAL_DECLARATION_SIG, BLOCK_SCOPE_SIG);
            HANDLE_VAL_FOR_FOR_EACH = Util.findMethod(clazz, "handleValForForEach", FOREACH_STATEMENT_SIG, BLOCK_SCOPE_SIG);
        }

        public static boolean handleValForLocalDeclaration(Object object, Object object2) {
            return (Boolean)Util.invokeMethod(HANDLE_VAL_FOR_LOCAL_DECLARATION, object, object2);
        }

        public static boolean handleValForForEach(Object object, Object object2) {
            return (Boolean)Util.invokeMethod(HANDLE_VAL_FOR_FOR_EACH, object, object2);
        }

        public static TypeBinding skipResolveInitializerIfAlreadyCalled(Expression expression, BlockScope blockScope) {
            if (expression.resolvedType != null) {
                return expression.resolvedType;
            }
            try {
                return expression.resolveType(blockScope);
            } catch (NullPointerException nullPointerException) {
                return null;
            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                return null;
            }
        }

        public static TypeBinding skipResolveInitializerIfAlreadyCalled2(Expression expression, BlockScope blockScope, LocalDeclaration localDeclaration) {
            if (localDeclaration != null && LocalDeclaration.class.equals(localDeclaration.getClass()) && expression.resolvedType != null) {
                return expression.resolvedType;
            }
            try {
                return expression.resolveType(blockScope);
            } catch (NullPointerException nullPointerException) {
                return null;
            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                return null;
            }
        }
    }

    public static final class ValPortal {
        private static final Method COPY_INITIALIZATION_OF_FOR_EACH_ITERABLE;
        private static final Method COPY_INITIALIZATION_OF_LOCAL_DECLARATION;
        private static final Method ADD_FINAL_AND_VAL_ANNOTATION_TO_VARIABLE_DECLARATION_STATEMENT;
        private static final Method ADD_FINAL_AND_VAL_ANNOTATION_TO_SINGLE_VARIABLE_DECLARATION;

        static {
            Class<?> clazz = Util.shadowLoadClass("lombok.eclipse.agent.PatchValEclipsePortal");
            COPY_INITIALIZATION_OF_FOR_EACH_ITERABLE = Util.findMethod(clazz, "copyInitializationOfForEachIterable", Object.class);
            COPY_INITIALIZATION_OF_LOCAL_DECLARATION = Util.findMethod(clazz, "copyInitializationOfLocalDeclaration", Object.class);
            ADD_FINAL_AND_VAL_ANNOTATION_TO_VARIABLE_DECLARATION_STATEMENT = Util.findMethod(clazz, "addFinalAndValAnnotationToVariableDeclarationStatement", Object.class, Object.class, Object.class);
            ADD_FINAL_AND_VAL_ANNOTATION_TO_SINGLE_VARIABLE_DECLARATION = Util.findMethod(clazz, "addFinalAndValAnnotationToSingleVariableDeclaration", Object.class, Object.class, Object.class);
        }

        public static void copyInitializationOfForEachIterable(Object object) {
            Util.invokeMethod(COPY_INITIALIZATION_OF_FOR_EACH_ITERABLE, object);
        }

        public static void copyInitializationOfLocalDeclaration(Object object) {
            Util.invokeMethod(COPY_INITIALIZATION_OF_LOCAL_DECLARATION, object);
        }

        public static void addFinalAndValAnnotationToVariableDeclarationStatement(Object object, Object object2, Object object3) {
            Util.invokeMethod(ADD_FINAL_AND_VAL_ANNOTATION_TO_VARIABLE_DECLARATION_STATEMENT, object, object2, object3);
        }

        public static void addFinalAndValAnnotationToSingleVariableDeclaration(Object object, Object object2, Object object3) {
            Util.invokeMethod(ADD_FINAL_AND_VAL_ANNOTATION_TO_SINGLE_VARIABLE_DECLARATION, object, object2, object3);
        }
    }
}

