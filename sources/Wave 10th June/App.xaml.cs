using System;
using System.CodeDom.Compiler;
using System.Diagnostics;
using System.Windows;

namespace WaveClient
{
	// Token: 0x02000006 RID: 6
	public partial class App : Application
	{
		// Token: 0x06000023 RID: 35 RVA: 0x000025D5 File Offset: 0x000007D5
		[STAThread]
		[DebuggerNonUserCode]
		[GeneratedCode("PresentationBuildTasks", "4.0.0.0")]
		public static void Main()
		{
			App app = new App();
			app.InitializeComponent();
			app.Run();
		}
	}
}
