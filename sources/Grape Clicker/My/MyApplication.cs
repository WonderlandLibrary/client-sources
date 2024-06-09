using Microsoft.VisualBasic.ApplicationServices;
using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace Xh0kO1ZCmA.My
{
	[EditorBrowsable(EditorBrowsableState.Never)]
	[GeneratedCode("MyTemplate", "11.0.0.0")]
	internal class MyApplication : WindowsFormsApplicationBase
	{
		[DebuggerStepThrough]
		public MyApplication() : base(AuthenticationMode.Windows)
		{
			base.IsSingleInstance = false;
			base.EnableVisualStyles = true;
			base.SaveMySettingsOnExit = true;
			base.ShutdownStyle = ShutdownMode.AfterAllFormsClose;
		}

		[DebuggerHidden]
		[EditorBrowsable(EditorBrowsableState.Advanced)]
		[MethodImpl(MethodImplOptions.NoInlining | MethodImplOptions.NoOptimization)]
		[STAThread]
		internal static void Main(string[] Args)
		{
			Application.SetCompatibleTextRenderingDefault(WindowsFormsApplicationBase.UseCompatibleTextRendering);
			MyProject.Application.Run(Args);
		}

		[DebuggerStepThrough]
		protected override void OnCreateMainForm()
		{
			base.MainForm = MyProject.Forms.Form1;
		}
	}
}