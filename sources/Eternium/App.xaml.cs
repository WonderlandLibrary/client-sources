using System;
using System.CodeDom.Compiler;
using System.Diagnostics;
using System.Windows;

namespace Eternium_mcpe_client
{
	// Token: 0x02000004 RID: 4
	public partial class App : Application
	{
		// Token: 0x06000008 RID: 8 RVA: 0x00002108 File Offset: 0x00000308
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
