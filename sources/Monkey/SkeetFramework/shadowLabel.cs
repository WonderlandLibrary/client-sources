using System;
using System.Drawing;
using System.Windows.Forms;

namespace SkeetFramework
{
	// Token: 0x02000009 RID: 9
	internal class shadowLabel : Label
	{
		// Token: 0x17000017 RID: 23
		// (get) Token: 0x06000058 RID: 88 RVA: 0x00003F93 File Offset: 0x00001F93
		// (set) Token: 0x06000059 RID: 89 RVA: 0x00003F9B File Offset: 0x00001F9B
		public Color ShadowColor
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x17000018 RID: 24
		// (get) Token: 0x0600005A RID: 90 RVA: 0x00003FAC File Offset: 0x00001FAC
		// (set) Token: 0x0600005B RID: 91 RVA: 0x00003FB4 File Offset: 0x00001FB4
		public int ShadowOffset
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x17000019 RID: 25
		// (get) Token: 0x0600005C RID: 92 RVA: 0x00003FC5 File Offset: 0x00001FC5
		// (set) Token: 0x0600005D RID: 93 RVA: 0x00003FCD File Offset: 0x00001FCD
		public bool EnableShadow
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x0600005E RID: 94 RVA: 0x00003FE0 File Offset: 0x00001FE0
		protected override void OnPaint(PaintEventArgs e)
		{
			/*
An exception occurred when decompiling this method (0600005E)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Void SkeetFramework.shadowLabel::OnPaint(System.Windows.Forms.PaintEventArgs)

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

		// Token: 0x0600005F RID: 95 RVA: 0x000042F4 File Offset: 0x000022F4
		private static StringFormat ContentAlignmentToStringAlignment(ContentAlignment ca)
		{
		}

		// Token: 0x0400002E RID: 46
		private shadowLabel.Angles _angle;

		// Token: 0x0400002F RID: 47
		private bool _enableShadow;

		// Token: 0x04000030 RID: 48
		private Color _shadowColor;

		// Token: 0x04000031 RID: 49
		private int _shadowOffset;

		// Token: 0x0200000A RID: 10
		public enum Angles
		{
			// Token: 0x04000033 RID: 51
			LeftToRight,
			// Token: 0x04000034 RID: 52
			TopToBottom = 90,
			// Token: 0x04000035 RID: 53
			RightToLeft = 180,
			// Token: 0x04000036 RID: 54
			BottomToTop = 270
		}
	}
}
