using System;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace SkeetFramework
{
	// Token: 0x02000003 RID: 3
	public class SkeetButton : Control
	{
		// Token: 0x17000001 RID: 1
		// (get) Token: 0x06000006 RID: 6 RVA: 0x000022A0 File Offset: 0x000002A0
		// (set) Token: 0x06000007 RID: 7 RVA: 0x000022A8 File Offset: 0x000002A8
		public Color ColorTop
		{
			[CompilerGenerated]
			get
			{
			}
			[CompilerGenerated]
			set
			{
			}
		}

		// Token: 0x17000002 RID: 2
		// (get) Token: 0x06000008 RID: 8 RVA: 0x000022B1 File Offset: 0x000002B1
		// (set) Token: 0x06000009 RID: 9 RVA: 0x000022B9 File Offset: 0x000002B9
		public Color ColorBottom
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x17000003 RID: 3
		// (get) Token: 0x0600000A RID: 10 RVA: 0x000022C2 File Offset: 0x000002C2
		// (set) Token: 0x0600000B RID: 11 RVA: 0x000022CA File Offset: 0x000002CA
		public Pen FakeWhite
		{
			[CompilerGenerated]
			get
			{
			}
			[CompilerGenerated]
			set
			{
			}
		}

		// Token: 0x0600000D RID: 13 RVA: 0x00002378 File Offset: 0x00000378
		private void SkeetButton_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x0600000E RID: 14 RVA: 0x0000243B File Offset: 0x0000043B
		protected override void OnMouseDown(MouseEventArgs e)
		{
			/*
An exception occurred when decompiling this method (0600000E)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Void SkeetFramework.SkeetButton::OnMouseDown(System.Windows.Forms.MouseEventArgs)

 ---> System.OverflowException: Arithmetic operation resulted in an overflow.
   at ICSharpCode.Decompiler.ILAst.ILAstBuilder.StackSlot.ModifyStack(StackSlot[] stack, Int32 popCount, Int32 pushCount, ByteCode pushDefinition) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\ILAstBuilder.cs:line 47
   at ICSharpCode.Decompiler.ILAst.ILAstBuilder.StackAnalysis(MethodDef methodDef) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\ILAstBuilder.cs:line 387
   at ICSharpCode.Decompiler.ILAst.ILAstBuilder.Build(MethodDef methodDef, Boolean optimize, DecompilerContext context) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\ILAstBuilder.cs:line 271
   at ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(IEnumerable`1 parameters, MethodDebugInfoBuilder& builder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:line 112
   at ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(MethodDef methodDef, DecompilerContext context, AutoPropertyProvider autoPropertyProvider, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, StringBuilder sb, MethodDebugInfoBuilder& stmtsBuilder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:line 99
   --- End of inner exception stack trace ---
   at ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(MethodDef methodDef, DecompilerContext context, AutoPropertyProvider autoPropertyProvider, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, StringBuilder sb, MethodDebugInfoBuilder& stmtsBuilder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:line 99
   at ICSharpCode.Decompiler.Ast.AstBuilder.AddMethodBody(EntityDeclaration methodNode, EntityDeclaration& updatedNode, MethodDef method, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, MethodKind methodKind) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstBuilder.cs:line 1533
*/;
		}

		// Token: 0x0600000F RID: 15 RVA: 0x0000244D File Offset: 0x0000044D
		protected override void OnMouseUp(MouseEventArgs e)
		{
		}

		// Token: 0x04000009 RID: 9
		private bool active;

		// Token: 0x0400000A RID: 10
		private StringFormat strFormat;
	}
}
