using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.ApplicationServices;

// Token: 0x02000002 RID: 2
[EditorBrowsable(EditorBrowsableState.Never)]
[GeneratedCode("MyTemplate", "11.0.0.0")]
internal class Form0 : WindowsFormsApplicationBase
{
	// Token: 0x06000008 RID: 8 RVA: 0x00002AE8 File Offset: 0x00000CE8
	[STAThread]
	[EditorBrowsable(EditorBrowsableState.Advanced)]
	[DebuggerHidden]
	[MethodImpl(MethodImplOptions.NoOptimization)]
	internal static void Main(string[] args)
	{
		try
		{
			Application.SetCompatibleTextRenderingDefault(WindowsFormsApplicationBase.UseCompatibleTextRendering);
		}
		finally
		{
		}
		Class1.Form0_0.Run(args);
	}

	// Token: 0x06000009 RID: 9 RVA: 0x00002B20 File Offset: 0x00000D20
	[DebuggerStepThrough]
	public Form0() : base(AuthenticationMode.ApplicationDefined)
	{
		base.IsSingleInstance = false;
		base.EnableVisualStyles = true;
		base.SaveMySettingsOnExit = true;
		base.ShutdownStyle = ShutdownMode.AfterAllFormsClose;
	}

	// Token: 0x0600000A RID: 10 RVA: 0x00002B48 File Offset: 0x00000D48
	[DebuggerStepThrough]
	protected virtual void OnCreateMainForm()
	{
		base.MainForm = Class1.Class2_0.Loader_0;
	}
}
