using System;
using System.Drawing;
using System.Windows.Forms;

namespace FlatUI
{
	// Token: 0x02000010 RID: 16
	public class FlatComboBox : ComboBox
	{
		// Token: 0x060000A8 RID: 168 RVA: 0x00004FE1 File Offset: 0x00002FE1
		protected override void OnMouseDown(MouseEventArgs e)
		{
		}

		// Token: 0x060000A9 RID: 169 RVA: 0x00004FFA File Offset: 0x00002FFA
		protected override void OnMouseUp(MouseEventArgs e)
		{
		}

		// Token: 0x060000AA RID: 170 RVA: 0x00005013 File Offset: 0x00003013
		protected override void OnMouseEnter(EventArgs e)
		{
			/*
An exception occurred when decompiling this method (060000AA)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Void FlatUI.FlatComboBox::OnMouseEnter(System.EventArgs)

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

		// Token: 0x060000AB RID: 171 RVA: 0x0000502C File Offset: 0x0000302C
		protected override void OnMouseLeave(EventArgs e)
		{
		}

		// Token: 0x060000AC RID: 172 RVA: 0x00005048 File Offset: 0x00003048
		protected override void OnMouseMove(MouseEventArgs e)
		{
		}

		// Token: 0x060000AD RID: 173 RVA: 0x000050C4 File Offset: 0x000030C4
		protected override void OnDrawItem(DrawItemEventArgs e)
		{
		}

		// Token: 0x060000AE RID: 174 RVA: 0x00002551 File Offset: 0x00000551
		protected override void OnClick(EventArgs e)
		{
		}

		// Token: 0x17000030 RID: 48
		// (get) Token: 0x060000AF RID: 175 RVA: 0x000050FC File Offset: 0x000030FC
		// (set) Token: 0x060000B0 RID: 176 RVA: 0x00005114 File Offset: 0x00003114
		public Color HoverColor
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x17000031 RID: 49
		// (get) Token: 0x060000B1 RID: 177 RVA: 0x00005120 File Offset: 0x00003120
		// (set) Token: 0x060000B2 RID: 178 RVA: 0x00005138 File Offset: 0x00003138
		private int StartIndex
		{
			get
			{
				/*
An exception occurred when decompiling this method (060000B1)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Int32 FlatUI.FlatComboBox::get_StartIndex()

 ---> System.ArgumentOutOfRangeException: Non-negative number required. (Parameter 'length')
   at System.Array.Copy(Array sourceArray, Int32 sourceIndex, Array destinationArray, Int32 destinationIndex, Int32 length, Boolean reliable)
   at System.Array.Copy(Array sourceArray, Array destinationArray, Int32 length)
   at ICSharpCode.Decompiler.ILAst.ILAstBuilder.StackSlot.ModifyStack(StackSlot[] stack, Int32 popCount, Int32 pushCount, ByteCode pushDefinition) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\ILAstBuilder.cs:line 48
   at ICSharpCode.Decompiler.ILAst.ILAstBuilder.StackAnalysis(MethodDef methodDef) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\ILAstBuilder.cs:line 387
   at ICSharpCode.Decompiler.ILAst.ILAstBuilder.Build(MethodDef methodDef, Boolean optimize, DecompilerContext context) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\ILAstBuilder.cs:line 271
   at ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(IEnumerable`1 parameters, MethodDebugInfoBuilder& builder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:line 112
   at ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(MethodDef methodDef, DecompilerContext context, AutoPropertyProvider autoPropertyProvider, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, StringBuilder sb, MethodDebugInfoBuilder& stmtsBuilder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:line 99
   --- End of inner exception stack trace ---
   at ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(MethodDef methodDef, DecompilerContext context, AutoPropertyProvider autoPropertyProvider, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, StringBuilder sb, MethodDebugInfoBuilder& stmtsBuilder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:line 99
   at ICSharpCode.Decompiler.Ast.AstBuilder.AddMethodBody(EntityDeclaration methodNode, EntityDeclaration& updatedNode, MethodDef method, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, MethodKind methodKind) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstBuilder.cs:line 1533
*/;
			}
			set
			{
			}
		}

		// Token: 0x060000B3 RID: 179 RVA: 0x00005178 File Offset: 0x00003178
		public void DrawItem_(object sender, DrawItemEventArgs e)
		{
		}

		// Token: 0x060000B4 RID: 180 RVA: 0x000026FB File Offset: 0x000006FB
		protected override void OnResize(EventArgs e)
		{
		}

		// Token: 0x060000B6 RID: 182 RVA: 0x00005398 File Offset: 0x00003398
		protected override void OnPaint(PaintEventArgs e)
		{
		}

		// Token: 0x04000057 RID: 87
		private int W;

		// Token: 0x04000058 RID: 88
		private int H;

		// Token: 0x04000059 RID: 89
		private int _StartIndex;

		// Token: 0x0400005A RID: 90
		private int x;

		// Token: 0x0400005B RID: 91
		private int y;

		// Token: 0x0400005C RID: 92
		private MouseState State;

		// Token: 0x0400005D RID: 93
		private Color _BaseColor;

		// Token: 0x0400005E RID: 94
		private Color _BGColor;

		// Token: 0x0400005F RID: 95
		private Color _HoverColor;
	}
}
