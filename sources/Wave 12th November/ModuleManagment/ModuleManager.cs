using System;
using System.Threading;
using WaveClient.Module;

namespace WaveClient.ModuleManagment
{
	// Token: 0x0200000B RID: 11
	public static class ModuleManager
	{
		// Token: 0x0200002D RID: 45
		public static class MemoryUpdate
		{
			// Token: 0x06000139 RID: 313 RVA: 0x00004C74 File Offset: 0x00002E74
			public static void Tick10()
			{
				while (!ModuleManager.MemoryUpdate.ExitTickThread)
				{
					if (AirJump.ToggleState)
					{
						AirJump.Tick10();
					}
					if (NoFall.ToggleState)
					{
						NoFall.Tick10();
					}
					if (Instabreak.ToggleState)
					{
						Instabreak.Tick10();
					}
					if (Reach.ToggleState)
					{
						Reach.Tick10();
					}
					if (NoSwing.ToggleState)
					{
						NoSwing.Tick10();
					}
					if (Coords.ToggleState)
					{
						Coords.Tick10();
					}
					if (Speed.ToggleState)
					{
						Speed.Tick10();
					}
					if (AutoSprint.ToggleState)
					{
						AutoSprint.Tick10();
					}
					if (KillYourSelf.ToggleState)
					{
						KillYourSelf.Enable();
					}
					if (Phase.ToggleState)
					{
						Phase.Tick10();
					}
					if (Noclip.ToggleState)
					{
						Noclip.Tick10();
					}
					if (AnvilCost.ToggleState)
					{
						AnvilCost.Tick100();
					}
					if (HighJump.ToggleState)
					{
						HighJump.Tick10();
					}
					if (StickyGround.ToggleState)
					{
						StickyGround.Tick10();
					}
					bool toggleState = AirJump.ToggleState;
					bool toggleState2 = NoFall.ToggleState;
					if (!Instabreak.ToggleState)
					{
						Instabreak.Disable();
					}
					if (!Reach.ToggleState)
					{
						Reach.Disable();
					}
					if (!NoSwing.ToggleState)
					{
						NoSwing.Disable();
					}
					if (!Coords.ToggleState)
					{
						Coords.Disable();
					}
					bool toggleState3 = Speed.ToggleState;
					bool toggleState4 = AutoSprint.ToggleState;
					if (!Phase.ToggleState)
					{
						Phase.Disable();
					}
					if (!Noclip.ToggleState)
					{
						Noclip.Disable();
					}
					bool toggleState5 = Autoclicker.ToggleState;
					bool toggleState6 = KillYourSelf.ToggleState;
					bool toggleState7 = Noclip.ToggleState;
					bool toggleState8 = AnvilCost.ToggleState;
					bool toggleState9 = HighJump.ToggleState;
					bool toggleState10 = StickyGround.ToggleState;
				}
			}

			// Token: 0x0600013A RID: 314 RVA: 0x00004DBC File Offset: 0x00002FBC
			public static void Tick100()
			{
				while (!ModuleManager.MemoryUpdate.ExitTickThread)
				{
					Thread.Sleep(10);
				}
			}

			// Token: 0x0600013B RID: 315 RVA: 0x00004DCE File Offset: 0x00002FCE
			public static void Tick1000()
			{
				while (!ModuleManager.MemoryUpdate.ExitTickThread)
				{
					Thread.Sleep(10);
				}
			}

			// Token: 0x0600013C RID: 316 RVA: 0x00004DE0 File Offset: 0x00002FE0
			public static void StartTickThread()
			{
				ModuleManager.MemoryUpdate.ThreadTimerTick10 = new Thread(new ThreadStart(ModuleManager.MemoryUpdate.Tick10));
				ModuleManager.MemoryUpdate.ThreadTimerTick100 = new Thread(new ThreadStart(ModuleManager.MemoryUpdate.Tick100));
				ModuleManager.MemoryUpdate.ThreadTimerTick1000 = new Thread(new ThreadStart(ModuleManager.MemoryUpdate.Tick1000));
				ModuleManager.MemoryUpdate.ThreadTimerTick10.Priority = ThreadPriority.Normal;
				ModuleManager.MemoryUpdate.ThreadTimerTick100.Priority = ThreadPriority.Normal;
				ModuleManager.MemoryUpdate.ThreadTimerTick1000.Priority = ThreadPriority.BelowNormal;
				ModuleManager.MemoryUpdate.ThreadTimerTick10.Start();
				ModuleManager.MemoryUpdate.ThreadTimerTick100.Start();
				ModuleManager.MemoryUpdate.ThreadTimerTick1000.Start();
			}

			// Token: 0x0600013D RID: 317 RVA: 0x00004E6E File Offset: 0x0000306E
			public static void RestartTickThread()
			{
				ModuleManager.MemoryUpdate.ThreadTimerTick10.Abort();
				ModuleManager.MemoryUpdate.ThreadTimerTick100.Abort();
				ModuleManager.MemoryUpdate.ThreadTimerTick1000.Abort();
				ModuleManager.MemoryUpdate.StartTickThread();
			}

			// Token: 0x040000BE RID: 190
			public static bool ExitTickThread;

			// Token: 0x040000BF RID: 191
			private static Thread ThreadTimerTick10;

			// Token: 0x040000C0 RID: 192
			private static Thread ThreadTimerTick100;

			// Token: 0x040000C1 RID: 193
			private static Thread ThreadTimerTick1000;
		}
	}
}
