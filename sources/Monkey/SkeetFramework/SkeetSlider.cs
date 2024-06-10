using System;
using System.Drawing;
using System.Windows.Forms;

namespace SkeetFramework
{
	// Token: 0x02000006 RID: 6
	public class SkeetSlider : UserControl
	{
		// Token: 0x17000008 RID: 8
		// (get) Token: 0x06000026 RID: 38 RVA: 0x00002D34 File Offset: 0x00000D34
		// (set) Token: 0x06000027 RID: 39 RVA: 0x00002D4C File Offset: 0x00000D4C
		public bool InvertTitleColor
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x17000009 RID: 9
		// (get) Token: 0x06000028 RID: 40 RVA: 0x00002DBC File Offset: 0x00000DBC
		// (set) Token: 0x06000029 RID: 41 RVA: 0x00002DD4 File Offset: 0x00000DD4
		public bool ShowTitle
		{
			get
			{
			}
			set
			{
				/*
An exception occurred when decompiling this method (06000029)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Void SkeetFramework.SkeetSlider::set_ShowTitle(System.Boolean)

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
		}

		// Token: 0x1700000A RID: 10
		// (get) Token: 0x0600002A RID: 42 RVA: 0x00002DEC File Offset: 0x00000DEC
		// (set) Token: 0x0600002B RID: 43 RVA: 0x00002E04 File Offset: 0x00000E04
		public string Title
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x1700000B RID: 11
		// (get) Token: 0x0600002C RID: 44 RVA: 0x00002E38 File Offset: 0x00000E38
		// (set) Token: 0x0600002D RID: 45 RVA: 0x00002E50 File Offset: 0x00000E50
		public bool ShowValue
		{
			get
			{
				/*
An exception occurred when decompiling this method (0600002C)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Boolean SkeetFramework.SkeetSlider::get_ShowValue()

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

		// Token: 0x1700000C RID: 12
		// (get) Token: 0x0600002E RID: 46 RVA: 0x00002E68 File Offset: 0x00000E68
		// (set) Token: 0x0600002F RID: 47 RVA: 0x00002E80 File Offset: 0x00000E80
		public string ValueSuffix
		{
			get
			{
			}
			set
			{
				/*
An exception occurred when decompiling this method (0600002F)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Void SkeetFramework.SkeetSlider::set_ValueSuffix(System.String)

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

		// Token: 0x1700000D RID: 13
		// (get) Token: 0x06000030 RID: 48 RVA: 0x00002E94 File Offset: 0x00000E94
		// (set) Token: 0x06000031 RID: 49 RVA: 0x00002EAC File Offset: 0x00000EAC
		public string ValuePrefix
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x1700000E RID: 14
		// (get) Token: 0x06000032 RID: 50 RVA: 0x00002EC0 File Offset: 0x00000EC0
		// (set) Token: 0x06000033 RID: 51 RVA: 0x00002ED8 File Offset: 0x00000ED8
		public Color SliderColor
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x1700000F RID: 15
		// (get) Token: 0x06000034 RID: 52 RVA: 0x00002EEC File Offset: 0x00000EEC
		// (set) Token: 0x06000035 RID: 53 RVA: 0x00002F04 File Offset: 0x00000F04
		public Color SliderBackgroundColor
		{
			get
			{
				/*
An exception occurred when decompiling this method (06000034)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Drawing.Color SkeetFramework.SkeetSlider::get_SliderBackgroundColor()

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
			set
			{
			}
		}

		// Token: 0x17000010 RID: 16
		// (get) Token: 0x06000036 RID: 54 RVA: 0x00002F18 File Offset: 0x00000F18
		// (set) Token: 0x06000037 RID: 55 RVA: 0x00002F30 File Offset: 0x00000F30
		public int FormatDecimal
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x17000011 RID: 17
		// (get) Token: 0x06000038 RID: 56 RVA: 0x00002F58 File Offset: 0x00000F58
		// (set) Token: 0x06000039 RID: 57 RVA: 0x00002F70 File Offset: 0x00000F70
		public double Value
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x17000012 RID: 18
		// (get) Token: 0x0600003A RID: 58 RVA: 0x00002FBC File Offset: 0x00000FBC
		// (set) Token: 0x0600003B RID: 59 RVA: 0x00002FD4 File Offset: 0x00000FD4
		public double MaxValue
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x17000013 RID: 19
		// (get) Token: 0x0600003C RID: 60 RVA: 0x000030B8 File Offset: 0x000010B8
		// (set) Token: 0x0600003D RID: 61 RVA: 0x000030D0 File Offset: 0x000010D0
		public double MinValue
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x0600003E RID: 62 RVA: 0x0000319C File Offset: 0x0000119C
		public static Color changeColorBrightness(Color color, float correctionFactor)
		{
		}

		// Token: 0x0600003F RID: 63 RVA: 0x00003228 File Offset: 0x00001228
		private void updateColor()
		{
		}

		// Token: 0x06000040 RID: 64 RVA: 0x000032C8 File Offset: 0x000012C8
		private void updateValue()
		{
		}

		// Token: 0x06000041 RID: 65 RVA: 0x00003448 File Offset: 0x00001448
		public SkeetSlider()
		{
			/*
An exception occurred when decompiling this method (06000041)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Void SkeetFramework.SkeetSlider::.ctor()

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

		// Token: 0x06000042 RID: 66 RVA: 0x00003544 File Offset: 0x00001544
		private void updateSlider()
		{
		}

		// Token: 0x06000043 RID: 67 RVA: 0x000035E0 File Offset: 0x000015E0
		public static void SetDoubleBuffered(Control c)
		{
		}

		// Token: 0x06000044 RID: 68 RVA: 0x00003620 File Offset: 0x00001620
		private int offsetValueSwitch(int i)
		{
		}

		// Token: 0x06000045 RID: 69 RVA: 0x0000365C File Offset: 0x0000165C
		private Bitmap drawSlider(Control panel, int x)
		{
		}

		// Token: 0x06000046 RID: 70 RVA: 0x000037B8 File Offset: 0x000017B8
		protected override void SetBoundsCore(int x, int y, int width, int height, BoundsSpecified specified)
		{
		}

		// Token: 0x06000047 RID: 71 RVA: 0x000037EE File Offset: 0x000017EE
		private void skeetSlider_Load(object sender, EventArgs e)
		{
		}

		// Token: 0x06000048 RID: 72 RVA: 0x000037F4 File Offset: 0x000017F4
		private void pnlClick()
		{
		}

		// Token: 0x06000049 RID: 73 RVA: 0x00003932 File Offset: 0x00001932
		private void skeetSlider_Resize(object sender, EventArgs e)
		{
		}

		// Token: 0x0600004A RID: 74 RVA: 0x0000396C File Offset: 0x0000196C
		private void pnlSliderBox_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600004B RID: 75 RVA: 0x000039AC File Offset: 0x000019AC
		private void pnlSliderBox_MouseMove(object sender, MouseEventArgs e)
		{
		}

		// Token: 0x0600004C RID: 76 RVA: 0x00003A00 File Offset: 0x00001A00
		private void InitializeComponent()
		{
		}

		// Token: 0x0600004D RID: 77 RVA: 0x000037EE File Offset: 0x000017EE
		private void Slider_Load(object sender, EventArgs e)
		{
		}

		// Token: 0x04000018 RID: 24
		private bool skeetShowTitle;

		// Token: 0x04000019 RID: 25
		private bool invertTitleColor;

		// Token: 0x0400001A RID: 26
		private string skeetTitle;

		// Token: 0x0400001B RID: 27
		private bool skeetShowValue;

		// Token: 0x0400001C RID: 28
		private string skeetValueSuffix;

		// Token: 0x0400001D RID: 29
		private string skeetValuePrefix;

		// Token: 0x0400001E RID: 30
		private Color sliderColor;

		// Token: 0x0400001F RID: 31
		private Color sliderBack;

		// Token: 0x04000020 RID: 32
		private double skeetMax;

		// Token: 0x04000021 RID: 33
		private double skeetMin;

		// Token: 0x04000022 RID: 34
		private double skeetValue;

		// Token: 0x04000023 RID: 35
		private int skeetFormatLen;

		// Token: 0x04000024 RID: 36
		private Bitmap sliderback;

		// Token: 0x04000025 RID: 37
		private Bitmap slidercolor;

		// Token: 0x04000026 RID: 38
		private int offset;

		// Token: 0x04000027 RID: 39
		private Panel panel1;

		// Token: 0x04000028 RID: 40
		private Panel pnlSlider;

		// Token: 0x04000029 RID: 41
		private outlineLabel lbSliderValue;

		// Token: 0x0400002A RID: 42
		private transparentPanel pnlSliderBox;

		// Token: 0x0400002B RID: 43
		private shadowLabel lbTitle;
	}
}
