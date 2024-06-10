using System;
using System.Drawing;
using System.Windows.Forms;

// Token: 0x02000060 RID: 96
public class GControl0 : ContainerControl
{
	// Token: 0x17000174 RID: 372
	// (get) Token: 0x060004C7 RID: 1223 RVA: 0x00017134 File Offset: 0x00015334
	// (set) Token: 0x060004C8 RID: 1224 RVA: 0x0001714C File Offset: 0x0001534C
	public Icon Icon_0
	{
		get
		{
			return this.icon_0;
		}
		set
		{
			this.icon_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000175 RID: 373
	// (get) Token: 0x060004C9 RID: 1225 RVA: 0x0001715C File Offset: 0x0001535C
	// (set) Token: 0x060004CA RID: 1226 RVA: 0x00017164 File Offset: 0x00015364
	public bool Boolean_0
	{
		get
		{
			return this.bool_1;
		}
		set
		{
			this.bool_1 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000176 RID: 374
	// (get) Token: 0x060004CB RID: 1227 RVA: 0x00017174 File Offset: 0x00015374
	// (set) Token: 0x060004CC RID: 1228 RVA: 0x0001717C File Offset: 0x0001537C
	public bool Boolean_1
	{
		get
		{
			return this.bool_2;
		}
		set
		{
			this.bool_2 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000177 RID: 375
	// (get) Token: 0x060004CD RID: 1229 RVA: 0x0001718C File Offset: 0x0001538C
	// (set) Token: 0x060004CE RID: 1230 RVA: 0x000171A4 File Offset: 0x000153A4
	public GEnum0 GEnum0_0
	{
		get
		{
			return this.genum0_0;
		}
		set
		{
			this.genum0_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x060004CF RID: 1231 RVA: 0x000171B4 File Offset: 0x000153B4
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		if (e.Button == MouseButtons.Left & new Rectangle(15, 10, checked(base.Width - 31), 45).Contains(e.Location))
		{
			this.bool_0 = true;
			this.point_0 = e.Location;
		}
	}

	// Token: 0x060004D0 RID: 1232 RVA: 0x00017210 File Offset: 0x00015410
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.bool_0 = false;
	}

	// Token: 0x060004D1 RID: 1233 RVA: 0x00017220 File Offset: 0x00015420
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		if (this.bool_0)
		{
			base.Parent.Location = checked(new Point(Control.MousePosition.X - this.point_0.X, Control.MousePosition.Y - this.point_0.Y));
		}
	}

	// Token: 0x060004D2 RID: 1234 RVA: 0x00017280 File Offset: 0x00015480
	public GControl0()
	{
		this.point_0 = new Point(0, 0);
		this.bool_0 = false;
		this.bool_1 = true;
		this.bool_2 = true;
		this.genum0_0 = GEnum0.Center;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
	}

	// Token: 0x060004D3 RID: 1235 RVA: 0x000172D0 File Offset: 0x000154D0
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
		base.ParentForm.FormBorderStyle = FormBorderStyle.None;
		base.ParentForm.TransparencyKey = Color.Fuchsia;
		this.Dock = DockStyle.Fill;
	}

	// Token: 0x060004D4 RID: 1236 RVA: 0x000172FC File Offset: 0x000154FC
	protected virtual void OnPaint(PaintEventArgs e)
	{
		/*
An exception occurred when decompiling this method (060004D4)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Void GControl0::System.Windows.Forms.Control.OnPaint(System.Windows.Forms.PaintEventArgs)
 ---> System.Exception: Inconsistent stack size at IL_219
   in ICSharpCode.Decompiler.ILAst.ILAstBuilder.StackAnalysis(MethodDef methodDef) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\ILAstBuilder.cs:riga 279
   in ICSharpCode.Decompiler.ILAst.ILAstBuilder.Build(MethodDef methodDef, Boolean optimize, DecompilerContext context) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\ILAstBuilder.cs:riga 269
   in ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(IEnumerable`1 parameters, MethodDebugInfoBuilder& builder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:riga 112
   in ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(MethodDef methodDef, DecompilerContext context, AutoPropertyProvider autoPropertyProvider, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, StringBuilder sb, MethodDebugInfoBuilder& stmtsBuilder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:riga 88
   --- Fine della traccia dello stack dell'eccezione interna ---
   in ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(MethodDef methodDef, DecompilerContext context, AutoPropertyProvider autoPropertyProvider, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, StringBuilder sb, MethodDebugInfoBuilder& stmtsBuilder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:riga 92
   in ICSharpCode.Decompiler.Ast.AstBuilder.AddMethodBody(EntityDeclaration methodNode, EntityDeclaration& updatedNode, MethodDef method, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, MethodKind methodKind) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstBuilder.cs:riga 1533
*/;
	}

	// Token: 0x0400028D RID: 653
	private Point point_0;

	// Token: 0x0400028E RID: 654
	private bool bool_0;

	// Token: 0x0400028F RID: 655
	private Icon icon_0;

	// Token: 0x04000290 RID: 656
	private bool bool_1;

	// Token: 0x04000291 RID: 657
	private bool bool_2;

	// Token: 0x04000292 RID: 658
	private GEnum0 genum0_0;
}
