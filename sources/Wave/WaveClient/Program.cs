using System;
using Wave.Cmr;

namespace WaveClient
{
	// Token: 0x02000007 RID: 7
	public class Program
	{
		// Token: 0x06000025 RID: 37 RVA: 0x000025F0 File Offset: 0x000007F0
		[STAThread]
		public static void Main(string[] args)
		{
			cmr.EnableVirtualTerminalProcessing();
			Console.Title = "WaveClient";
			WaveClientConsole.Application.InitializeThread();
			App app = new App();
			app.InitializeComponent();
			app.Run();
		}
	}
}
