using System;
using System.Runtime.InteropServices;
using System.Threading;
using Eternium_mcpe_client.Threads;

namespace Eternium_mcpe_client
{
	// Token: 0x02000006 RID: 6
	public static class Program
	{
		// Token: 0x0600000D RID: 13
		[DllImport("kernel32.dll")]
		private static extern IntPtr GetConsoleWindow();

		// Token: 0x0600000E RID: 14
		[DllImport("user32.dll")]
		private static extern bool ShowWindow(IntPtr hWnd, int nCmdShow);

		// Token: 0x0600000F RID: 15 RVA: 0x0000218C File Offset: 0x0000038C
		[STAThread]
		public static void Main(string[] args)
		{
			IntPtr consoleWindow = Program.GetConsoleWindow();
			Console.Title = "Dephlantis client";
			Thread.Sleep(200);
			Console.WriteLine("Loading Main Thread...");
			Thread.Sleep(500);
			Console.WriteLine("Loading GUI...");
			Thread.Sleep(500);
			Console.WriteLine("Loading Data...");
			Thread.Sleep(500);
			Console.WriteLine("Loading Memory...");
			Thread.Sleep(1000);
			Console.WriteLine("Setting up Settings...");
			Thread.Sleep(1000);
			Console.WriteLine("Starting up Launcher...");
			Thread.Sleep(200);
			Console.WriteLine("Starting up Minecraft...");
			Thread.Sleep(200);
			Program.ShowWindow(consoleWindow, 0);
			ClientThread.Thread();
			MinecraftThread.StartAllThreads();
			App app = new App();
			app.InitializeComponent();
			app.Run();
		}
	}
}
