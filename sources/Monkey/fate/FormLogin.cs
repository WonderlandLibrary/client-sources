using System;
using System.ComponentModel;
using System.Windows.Forms;
using Siticone.UI.WinForms;

namespace fate
{
	// Token: 0x02000016 RID: 22
	public partial class FormLogin : Form
	{
		// Token: 0x060000E9 RID: 233 RVA: 0x000037EE File Offset: 0x000017EE
		private void FormLogin_Load(object sender, EventArgs e)
		{
		}

		// Token: 0x060000EA RID: 234 RVA: 0x00006590 File Offset: 0x00004590
		private void Register_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060000EB RID: 235 RVA: 0x000065B2 File Offset: 0x000045B2
		private void btnClose_Click(object sender, EventArgs e)
		{
			/*
An exception occurred when decompiling this method (060000EB)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Void fate.FormLogin::btnClose_Click(System.Object,System.EventArgs)

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

		// Token: 0x060000EC RID: 236 RVA: 0x000065BC File Offset: 0x000045BC
		private void login_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x04000082 RID: 130
		private IContainer components;

		// Token: 0x04000083 RID: 131
		private Label label12;

		// Token: 0x04000084 RID: 132
		private Label MONKEY;

		// Token: 0x04000085 RID: 133
		private Label lbRightCPS;

		// Token: 0x04000086 RID: 134
		private SiticoneTextBox username;

		// Token: 0x04000087 RID: 135
		private SiticonePanel side;

		// Token: 0x04000088 RID: 136
		private SiticoneTextBox password;

		// Token: 0x04000089 RID: 137
		private SiticoneButton login;

		// Token: 0x0400008A RID: 138
		private SiticoneButton Register;

		// Token: 0x0400008B RID: 139
		private SiticoneButton btnClose;

		// Token: 0x0400008C RID: 140
		private SiticoneDragControl siticoneDragControl1;
	}
}
