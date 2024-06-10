using System;
using System.Runtime.CompilerServices;

namespace Auth.GG_Winform_Example
{
	// Token: 0x0200002F RID: 47
	internal class Constants
	{
		// Token: 0x17000042 RID: 66
		// (get) Token: 0x06000154 RID: 340 RVA: 0x0000CA06 File Offset: 0x0000AA06
		// (set) Token: 0x06000155 RID: 341 RVA: 0x0000CA0D File Offset: 0x0000AA0D
		public static string Token
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

		// Token: 0x17000043 RID: 67
		// (get) Token: 0x06000156 RID: 342 RVA: 0x0000CA15 File Offset: 0x0000AA15
		// (set) Token: 0x06000157 RID: 343 RVA: 0x0000CA1C File Offset: 0x0000AA1C
		public static string Date
		{
			[CompilerGenerated]
			get
			{
				/*
An exception occurred when decompiling this method (06000156)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.String Auth.GG_Winform_Example.Constants::get_Date()

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
			[CompilerGenerated]
			set
			{
			}
		}

		// Token: 0x17000044 RID: 68
		// (get) Token: 0x06000158 RID: 344 RVA: 0x0000CA24 File Offset: 0x0000AA24
		// (set) Token: 0x06000159 RID: 345 RVA: 0x0000CA2B File Offset: 0x0000AA2B
		public static string APIENCRYPTKEY
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

		// Token: 0x17000045 RID: 69
		// (get) Token: 0x0600015A RID: 346 RVA: 0x0000CA33 File Offset: 0x0000AA33
		// (set) Token: 0x0600015B RID: 347 RVA: 0x0000CA3A File Offset: 0x0000AA3A
		public static string APIENCRYPTSALT
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

		// Token: 0x0600015C RID: 348 RVA: 0x0000CA44 File Offset: 0x0000AA44
		public static string RandomString(int length)
		{
		}

		// Token: 0x0600015D RID: 349 RVA: 0x0000CA90 File Offset: 0x0000AA90
		public static string HWID()
		{
		}

		// Token: 0x0600015F RID: 351 RVA: 0x0000CAB1 File Offset: 0x0000AAB1
		// Note: this type is marked as 'beforefieldinit'.
		static Constants()
		{
			/*
An exception occurred when decompiling this method (0600015F)

ICSharpCode.Decompiler.DecompilerException: Error decompiling System.Void Auth.GG_Winform_Example.Constants::.cctor()

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

		// Token: 0x0400010D RID: 269
		private static string <Token>k__BackingField;

		// Token: 0x04000111 RID: 273
		public static bool Breached;

		// Token: 0x04000112 RID: 274
		public static bool Started;

		// Token: 0x04000113 RID: 275
		public static string IV;

		// Token: 0x04000114 RID: 276
		public static string Key;

		// Token: 0x04000115 RID: 277
		public static string ApiUrl;

		// Token: 0x04000116 RID: 278
		public static bool Initialized;

		// Token: 0x04000117 RID: 279
		public static Random random;
	}
}
