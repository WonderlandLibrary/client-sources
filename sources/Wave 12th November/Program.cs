using System;
using Wave.Cmr;

namespace WaveClient
{
	// Token: 0x02000004 RID: 4
	public class Program
	{
		// Token: 0x06000005 RID: 5 RVA: 0x00002160 File Offset: 0x00000360
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
