using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace FlatUI
{
	// Token: 0x02000012 RID: 18
	[DefaultEvent("Scroll")]
	public class FlatTrackBar : Control
	{
		// Token: 0x060000BE RID: 190 RVA: 0x00005728 File Offset: 0x00003728
		protected override void OnMouseDown(MouseEventArgs e)
		{
		}

		// Token: 0x060000BF RID: 191 RVA: 0x00005878 File Offset: 0x00003878
		protected override void OnMouseMove(MouseEventArgs e)
		{
			/*
An exception occurred when decompiling this method (060000BF)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Void FlatUI.FlatTrackBar::OnMouseMove(System.Windows.Forms.MouseEventArgs)

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

		// Token: 0x060000C0 RID: 192 RVA: 0x000059E0 File Offset: 0x000039E0
		protected override void OnMouseUp(MouseEventArgs e)
		{
		}

		// Token: 0x17000034 RID: 52
		// (get) Token: 0x060000C1 RID: 193 RVA: 0x000059F4 File Offset: 0x000039F4
		// (set) Token: 0x060000C2 RID: 194 RVA: 0x00005A0C File Offset: 0x00003A0C
		[Category("Colors")]
		public Color TrackColor
		{
			get
			{
				/*
An exception occurred when decompiling this method (060000C1)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Drawing.Color FlatUI.FlatTrackBar::get_TrackColor()

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

		// Token: 0x17000035 RID: 53
		// (get) Token: 0x060000C3 RID: 195 RVA: 0x00005A18 File Offset: 0x00003A18
		// (set) Token: 0x060000C4 RID: 196 RVA: 0x00005A30 File Offset: 0x00003A30
		[Category("Colors")]
		public Color HatchColor
		{
			get
			{
			}
			set
			{
				/*
An exception occurred when decompiling this method (060000C4)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Void FlatUI.FlatTrackBar::set_HatchColor(System.Drawing.Color)

 ---> System.ArgumentNullException: Value cannot be null. (Parameter 'key')
   at System.Collections.Generic.Dictionary`2.FindValue(TKey key)
   at System.Collections.Generic.Dictionary`2.get_Item(TKey key)
   at ICSharpCode.Decompiler.ILAst.SimpleControlFlow.JoinBasicBlocks(List`1 body, ILBasicBlock head, Int32 pos) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\SimpleControlFlow.cs:line 432
   at ICSharpCode.Decompiler.ILAst.ILAstOptimizerExtensionMethods.RunOptimization(ILBlock block, Func`4 optimization) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\ILAstOptimizer.cs:line 2130
   at ICSharpCode.Decompiler.ILAst.ILAstOptimizer.Optimize(DecompilerContext context, ILBlock method, AutoPropertyProvider autoPropertyProvider, StateMachineKind& stateMachineKind, MethodDef& inlinedMethod, AsyncMethodDebugInfo& asyncInfo, ILAstOptimizationStep abortBeforeStep) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\ILAst\ILAstOptimizer.cs:line 202
   at ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(IEnumerable`1 parameters, MethodDebugInfoBuilder& builder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:line 123
   at ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(MethodDef methodDef, DecompilerContext context, AutoPropertyProvider autoPropertyProvider, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, StringBuilder sb, MethodDebugInfoBuilder& stmtsBuilder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:line 99
   --- End of inner exception stack trace ---
   at ICSharpCode.Decompiler.Ast.AstMethodBodyBuilder.CreateMethodBody(MethodDef methodDef, DecompilerContext context, AutoPropertyProvider autoPropertyProvider, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, StringBuilder sb, MethodDebugInfoBuilder& stmtsBuilder) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstMethodBodyBuilder.cs:line 99
   at ICSharpCode.Decompiler.Ast.AstBuilder.AddMethodBody(EntityDeclaration methodNode, EntityDeclaration& updatedNode, MethodDef method, IEnumerable`1 parameters, Boolean valueParameterIsKeyword, MethodKind methodKind) in D:\a\dnSpy\dnSpy\Extensions\ILSpy.Decompiler\ICSharpCode.Decompiler\ICSharpCode.Decompiler\Ast\AstBuilder.cs:line 1533
*/;
			}
		}

		// Token: 0x17000036 RID: 54
		// (get) Token: 0x060000C5 RID: 197 RVA: 0x00005A3C File Offset: 0x00003A3C
		// (set) Token: 0x060000C6 RID: 198 RVA: 0x00005A53 File Offset: 0x00003A53
		[Category("Colors")]
		public Color ColorScheme1
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x17000037 RID: 55
		// (get) Token: 0x060000C7 RID: 199 RVA: 0x00005A5C File Offset: 0x00003A5C
		// (set) Token: 0x060000C8 RID: 200 RVA: 0x00005A73 File Offset: 0x00003A73
		[Category("Colors")]
		public Color ColorScheme2
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x17000038 RID: 56
		// (get) Token: 0x060000C9 RID: 201 RVA: 0x00005A7C File Offset: 0x00003A7C
		// (set) Token: 0x060000CA RID: 202 RVA: 0x00005A94 File Offset: 0x00003A94
		[Category("Misc")]
		public int Minimum
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x17000039 RID: 57
		// (get) Token: 0x060000CB RID: 203 RVA: 0x00005AA0 File Offset: 0x00003AA0
		// (set) Token: 0x060000CC RID: 204 RVA: 0x00005AB8 File Offset: 0x00003AB8
		[Category("Misc")]
		public bool Full
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x1700003A RID: 58
		// (get) Token: 0x060000CD RID: 205 RVA: 0x00005AC4 File Offset: 0x00003AC4
		// (set) Token: 0x060000CE RID: 206 RVA: 0x00005ADC File Offset: 0x00003ADC
		[Category("Misc")]
		public bool Decimal
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x1700003B RID: 59
		// (get) Token: 0x060000CF RID: 207 RVA: 0x00005AE8 File Offset: 0x00003AE8
		// (set) Token: 0x060000D0 RID: 208 RVA: 0x00005B00 File Offset: 0x00003B00
		[Category("Misc")]
		public double FloatValue
		{
			get
			{
			}
			set
			{
				/*
An exception occurred when decompiling this method (060000D0)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Void FlatUI.FlatTrackBar::set_FloatValue(System.Double)

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
		}

		// Token: 0x14000002 RID: 2
		// (add) Token: 0x060000D1 RID: 209 RVA: 0x00005B0C File Offset: 0x00003B0C
		// (remove) Token: 0x060000D2 RID: 210 RVA: 0x00005B44 File Offset: 0x00003B44
		public event FlatTrackBar.ScrollEventHandler Scroll
		{
			[CompilerGenerated]
			add
			{
			}
			[CompilerGenerated]
			remove
			{
			}
		}

		// Token: 0x1700003C RID: 60
		// (get) Token: 0x060000D3 RID: 211 RVA: 0x00005B7C File Offset: 0x00003B7C
		// (set) Token: 0x060000D4 RID: 212 RVA: 0x00005B94 File Offset: 0x00003B94
		public int Maximum
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x1700003D RID: 61
		// (get) Token: 0x060000D5 RID: 213 RVA: 0x00005BE0 File Offset: 0x00003BE0
		// (set) Token: 0x060000D6 RID: 214 RVA: 0x00005BF8 File Offset: 0x00003BF8
		public int Value
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x1700003E RID: 62
		// (get) Token: 0x060000D7 RID: 215 RVA: 0x00005C6C File Offset: 0x00003C6C
		// (set) Token: 0x060000D8 RID: 216 RVA: 0x00005C84 File Offset: 0x00003C84
		public bool ShowValue
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x060000D9 RID: 217 RVA: 0x00005C90 File Offset: 0x00003C90
		protected override void OnKeyDown(KeyEventArgs e)
		{
			/*
An exception occurred when decompiling this method (060000D9)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Void FlatUI.FlatTrackBar::OnKeyDown(System.Windows.Forms.KeyEventArgs)

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

		// Token: 0x060000DA RID: 218 RVA: 0x00004B4A File Offset: 0x00002B4A
		protected override void OnTextChanged(EventArgs e)
		{
		}

		// Token: 0x060000DB RID: 219 RVA: 0x00005D2C File Offset: 0x00003D2C
		protected override void OnResize(EventArgs e)
		{
		}

		// Token: 0x060000DD RID: 221 RVA: 0x00005DF0 File Offset: 0x00003DF0
		protected override void OnPaint(PaintEventArgs e)
		{
			/*
An exception occurred when decompiling this method (060000DD)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Void FlatUI.FlatTrackBar::OnPaint(System.Windows.Forms.PaintEventArgs)

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

		// Token: 0x04000066 RID: 102
		private int W;

		// Token: 0x04000067 RID: 103
		private int H;

		// Token: 0x04000068 RID: 104
		private int Val;

		// Token: 0x04000069 RID: 105
		private bool Bool;

		// Token: 0x0400006A RID: 106
		private bool filled;

		// Token: 0x0400006B RID: 107
		private bool floatText;

		// Token: 0x0400006C RID: 108
		private double FloatVal;

		// Token: 0x0400006D RID: 109
		private Rectangle Track;

		// Token: 0x0400006E RID: 110
		private int minvalue;

		// Token: 0x0400006F RID: 111
		private int _Maximum;

		// Token: 0x04000070 RID: 112
		private int _Value;

		// Token: 0x04000071 RID: 113
		private bool _ShowValue;

		// Token: 0x04000072 RID: 114
		private HatchBrush HBB2;

		// Token: 0x04000073 RID: 115
		public static Color scheme1;

		// Token: 0x04000074 RID: 116
		public static Color scheme2;

		// Token: 0x04000075 RID: 117
		public static Pen outline_color;

		// Token: 0x04000076 RID: 118
		private Color SliderColor;

		// Token: 0x04000077 RID: 119
		private Color BaseColor;

		// Token: 0x04000078 RID: 120
		private Color _TrackColor;

		// Token: 0x04000079 RID: 121
		private Color _HatchColor;

		// Token: 0x02000013 RID: 19
		// (Invoke) Token: 0x060000E0 RID: 224
		public delegate void ScrollEventHandler(object sender);
	}
}
