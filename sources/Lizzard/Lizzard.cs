using System;
using System.Diagnostics;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using Lizzard.Helper;
using Lizzard.Locator;
using Lizzard.Math;

namespace Lizzard
{
	// Token: 0x02000002 RID: 2
	internal class Lizzard
	{
        private const int V = 9__4;

        // Token: 0x06000001 RID: 1 RVA: 0x00002048 File Offset: 0x00000248
        private static void Main(string[] args)
		{
			Console.Title = "";
			Console.CursorVisible = false;
			Console.BufferHeight = Console.WindowHeight;
			ConsoleHelper.DisableSelection();
			Pattern pattern = new Pattern();
			Console.Write("Minecraft Window Title : ", Console.ForegroundColor = ConsoleColor.Yellow);
			Console.CursorVisible = !Console.CursorVisible;
			InstanceLocator locator = new InstanceLocator(Console.ReadLine());
			Process process = locator.GetInstance();
			Process foregroundProcess = ForegroundWindowHelper.GetForegrondWindow();
			Task.Run(delegate()
			{
				for (;;)
				{
					process = locator.GetInstance();
					foregroundProcess = ForegroundWindowHelper.GetForegrondWindow();
					Thread.Sleep(100);
				}
			});
			Console.CursorVisible = !Console.CursorVisible;
			Console.Clear();
			bool flag = process == null;
			if (flag)
			{
				Console.WriteLine("No game instance found !", Console.ForegroundColor = ConsoleColor.Red);
			}
			else
			{
				Console.WriteLine("[+] Minecraft instance found : " + process.MainWindowTitle, Console.ForegroundColor = ConsoleColor.Green);
				Console.WriteLine("Hiding console, press INSERT to Self Destruct ...", Console.ForegroundColor = ConsoleColor.DarkGreen);
				Task.Run(delegate()
				{
					Thread.Sleep(2000);
					HiddenConsoleHelper.HideConsole();
				});
			}
			Task.Run(delegate()
			{
				for (;;)
				{
					bool flag2 = AsyncKeyHelper.isKeyDown(Keys.Insert);
					if (flag2)
					{
						Console.Beep();
						Environment.Exit(0);
					}
					bool flag3 = AsyncKeyHelper.isKeyDown(Keys.F4);
					if (flag3)
					{
						Lizzard.toggled = !Lizzard.toggled;
					}
					Thread.Sleep(100);
				}
			});
			int counter = 0;
            Task.Run(delegate()
			{
				for (;;)
				{
					bool flag2 = MouseHelper.GetMouseState() && Lizzard.toggled;
					if (flag2)
					{
						int num = counter + 1;
						counter = num;
						bool flag3 = num > 20 && foregroundProcess != null && foregroundProcess.Id == process.Id;
						if (flag3)
                        {
                            Action action;

                           


                                {
                                    MouseHelper.PostMessage(0, process.MainWindowHandle);
                                    Thread.Sleep(10);
                                    MouseHelper.PostMessage(1, process.MainWindowHandle);
                                };                            Thread.Sleep(pattern.Evaluate());
                        }
                    }
					else
					{
						counter = 0;
					}
                    Thread.Sleep(5);
				}
			});
			Thread.Sleep(-1);

        }

		// Token: 0x04000001 RID: 1
		private static bool toggled;
	}
}
