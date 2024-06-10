using System;
using System.Net;
using System.Threading;

namespace Auth.GG_Winform_Example
{
	// Token: 0x0200003A RID: 58
	internal class InfoManager
	{
		// Token: 0x060001B4 RID: 436 RVA: 0x0000EFE2 File Offset: 0x0000CFE2
		public void StartListener()
		{
			/*
An exception occurred when decompiling this method (060001B4)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Void Auth.GG_Winform_Example.InfoManager::StartListener()

 ---> System.NullReferenceException: Object reference not set to an instance of an object.
   at ICSharpCode.Decompiler.ILAst.ILAstOptimizer.IntroducePropertyAccessInstructions(ILExpression expr, ILExpression parentExpr, Int32 posInParent) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\ILAstOptimizer.cs:line 1587
   at ICSharpCode.Decompiler.ILAst.ILAstOptimizer.IntroducePropertyAccessInstructions(ILNode node) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\ILAstOptimizer.cs:line 1579
   at ICSharpCode.Decompiler.ILAst.ILAstOptimizer.Optimize(DecompilerContext context, ILBlock method, AutoPropertyProvider autoPropertyProvider, StateMachineKind& stateMachineKind, MethodDef& inlinedMethod, AsyncMethodDebugInfo& asyncInfo, ILAstOptimizationStep abortBeforeStep) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\ILAstOptimizer.cs:line 244
   at ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(IEnumerable`1 parameters, MethodDebugInfoBuilder& builder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:line 123
   at ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(MethodDef methodDef, DecompilerContext context, AutoPropertyProvider autoPropertyProvider, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, StringBuilder sb, MethodDebugInfoBuilder& stmtsBuilder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:line 99
   --- End of inner exception stack trace ---
   at ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(MethodDef methodDef, DecompilerContext context, AutoPropertyProvider autoPropertyProvider, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, StringBuilder sb, MethodDebugInfoBuilder& stmtsBuilder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:line 99
   at ICSharpCode.Decompiler.Ast.AstBuilder.AddMethodBody(EntityDeclaration methodNode, EntityDeclaration& updatedNode, MethodDef method, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, MethodKind methodKind) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstBuilder.cs:line 1533
*/;
		}

		// Token: 0x060001B5 RID: 437 RVA: 0x0000F004 File Offset: 0x0000D004
		private void OnCallBack()
		{
		}

		// Token: 0x060001B6 RID: 438 RVA: 0x0000F088 File Offset: 0x0000D088
		public static IPAddress GetDefaultGateway()
		{
		}

		// Token: 0x060001B7 RID: 439 RVA: 0x0000F158 File Offset: 0x0000D158
		private string GetArpTable()
		{
		}

		// Token: 0x060001B8 RID: 440 RVA: 0x0000F1FC File Offset: 0x0000D1FC
		private string GetGatewayMAC()
		{
		}

		// Token: 0x0400013A RID: 314
		private Timer timer;

		// Token: 0x0400013B RID: 315
		private string lastGateway;
	}
}
