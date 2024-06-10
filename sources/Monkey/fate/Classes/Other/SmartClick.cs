using System;
using System.Runtime.InteropServices;

namespace fate.Classes.Other
{
	// Token: 0x02000022 RID: 34
	public static class SmartClick
	{
		// Token: 0x06000130 RID: 304
		[DllImport("user32.dll")]
		public static extern bool GetCursorInfo(ref SmartClick.CursorInfoStruct pci);

		// Token: 0x06000131 RID: 305
		[DllImport("user32.dll")]
		public static extern IntPtr GetForegroundWindow();

		// Token: 0x06000132 RID: 306
		[DllImport("user32.dll")]
		public static extern IntPtr FindWindow(string lpWindowClass, string lpWindowCaption);

		// Token: 0x06000133 RID: 307 RVA: 0x0000C55C File Offset: 0x0000A55C
		public static bool InMenu()
		{
			/*
An exception occurred when decompiling this method (06000133)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Boolean fate.Classes.Other.SmartClick::InMenu()

 ---> System.NullReferenceException: Object reference not set to an instance of an object.
   at ICSharpCode.Decompiler.ILAst.TypeAnalysis.DoInferTypeForExpression(ILExpression expr, TypeSig expectedType, Boolean forceInferChildren) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\TypeAnalysis.cs:line 343
   at ICSharpCode.Decompiler.ILAst.TypeAnalysis.InferTypeForExpression(ILExpression expr, TypeSig expectedType, Boolean forceInferChildren) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\TypeAnalysis.cs:line 302
   at ICSharpCode.Decompiler.ILAst.TypeAnalysis.RunInference(ILExpression expr) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\TypeAnalysis.cs:line 276
   at ICSharpCode.Decompiler.ILAst.TypeAnalysis.RunInference() in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\TypeAnalysis.cs:line 219
   at ICSharpCode.Decompiler.ILAst.TypeAnalysis.Run(DecompilerContext context, ILBlock method) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\TypeAnalysis.cs:line 51
   at ICSharpCode.Decompiler.ILAst.ILAstOptimizer.Optimize(DecompilerContext context, ILBlock method, AutoPropertyProvider autoPropertyProvider, StateMachineKind& stateMachineKind, MethodDef& inlinedMethod, AsyncMethodDebugInfo& asyncInfo, ILAstOptimizationStep abortBeforeStep) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\ILAstOptimizer.cs:line 253
   at ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(IEnumerable`1 parameters, MethodDebugInfoBuilder& builder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:line 123
   at ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(MethodDef methodDef, DecompilerContext context, AutoPropertyProvider autoPropertyProvider, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, StringBuilder sb, MethodDebugInfoBuilder& stmtsBuilder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:line 99
   --- End of inner exception stack trace ---
   at ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(MethodDef methodDef, DecompilerContext context, AutoPropertyProvider autoPropertyProvider, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, StringBuilder sb, MethodDebugInfoBuilder& stmtsBuilder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:line 99
   at ICSharpCode.Decompiler.Ast.AstBuilder.AddMethodBody(EntityDeclaration methodNode, EntityDeclaration& updatedNode, MethodDef method, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, MethodKind methodKind) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstBuilder.cs:line 1533
*/;
		}

		// Token: 0x06000134 RID: 308 RVA: 0x0000C57C File Offset: 0x0000A57C
		public static bool IsCursorVisible()
		{
		}

		// Token: 0x02000023 RID: 35
		public struct PointStruct
		{
			// Token: 0x040000E7 RID: 231
			public int x;

			// Token: 0x040000E8 RID: 232
			public int y;
		}

		// Token: 0x02000024 RID: 36
		public struct CursorInfoStruct
		{
			// Token: 0x040000E9 RID: 233
			public int cbSize;

			// Token: 0x040000EA RID: 234
			public int flags;

			// Token: 0x040000EB RID: 235
			public IntPtr hCursor;

			// Token: 0x040000EC RID: 236
			public SmartClick.PointStruct pt;
		}
	}
}
