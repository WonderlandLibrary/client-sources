using System;
using System.ComponentModel;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Threading;
using System.Windows.Forms;
using Memory;
using Octo_Client.Properties;

namespace Octo_Client
{
	// Token: 0x02000003 RID: 3
	public partial class OctoMain : Form
	{
		// Token: 0x0600000D RID: 13 RVA: 0x00002F90 File Offset: 0x00001190
		public OctoMain()
		{
			this.InitializeComponent();
			this.InitializeWaitTimes(this.minTimeDefault, this.maxTimeDefault, this.minClickBeforeMouseMoveDefault, this.maxClickBeforeMouseMoveDefault);
			new Thread(delegate()
			{
				this.ContinuallyUpdateMouseCoordLabel(60);
			})
			{
				IsBackground = true
			}.Start();
		}

		// Token: 0x0600000E RID: 14 RVA: 0x00003040 File Offset: 0x00001240
		private void Form1_Load(object sender, EventArgs e)
		{
			OctoOverlay octoOverlay = new OctoOverlay();
			octoOverlay.Show();
			int procIdFromName = this.m.GetProcIdFromName("Minecraft.Windows");
			this.displaypid.Text = "PID: " + procIdFromName.ToString();
			bool flag = procIdFromName > 0;
			if (flag)
			{
				bool flag2 = this.m.OpenProcess(procIdFromName);
				this.Status.Text = "Minecraft Is connected!";
				this.IsOpen.Text = "Is Open: true";
				this.backgroundWorker1.RunWorkerAsync();
			}
			else
			{
				this.Status.Text = "Minecraft Is not open!";
				this.IsOpen.Text = "Is Open: false";
			}
		}

		// Token: 0x0600000F RID: 15 RVA: 0x000030F8 File Offset: 0x000012F8
		private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
		{
			this.Status.Text = "Started Background operations";
			for (;;)
			{
				bool superJumpEnabled = OctoMain.SuperJumpEnabled;
				if (superJumpEnabled)
				{
					float num = this.m.ReadFloat("Minecraft.Windows.exe+0x036DD9F8,0x140,0x578,0x18,0x0,0x10,0x158,0x10,0x498", "", true);
					bool flag = num > 0f;
					if (flag)
					{
						this.m.WriteMemory("Minecraft.Windows.exe+0x036DD9F8,0x140,0x578,0x18,0x0,0x10,0x158,0x10,0x498", "float", this.JumpValue.Text, "", null);
						Thread.Sleep(1500);
						this.m.WriteMemory("Minecraft.Windows.exe+0x036DD9F8,0x140,0x578,0x18,0x0,0x10,0x158,0x10,0x498", "float", "0", "", null);
					}
				}
				bool slowFallEnabled = OctoMain.SlowFallEnabled;
				if (slowFallEnabled)
				{
					bool flag2 = this.m.ReadFloat("Minecraft.Windows.exe+0x036DD9F8,0x140,0x578,0x18,0x0,0x10,0x158,0x10,0x498", "", true) < -0.0784f;
					if (flag2)
					{
						this.m.FreezeValue("Minecraft.Windows.exe+0x036DD9F8,0x140,0x578,0x18,0x0,0x10,0x158,0x10,0x498", "float", "-0.07840000093", "");
						this.m.FreezeValue("Minecraft.Windows.exe+0x036E7688,0x0,0x18,0x88,0x5B0,0xD0,0x8,0x1178,0x19C", "float", "0", "");
					}
					else
					{
						this.m.UnfreezeValue("Minecraft.Windows.exe+0x036DD9F8,0x140,0x578,0x18,0x0,0x10,0x158,0x10,0x498");
						this.m.UnfreezeValue("Minecraft.Windows.exe+0x036E7688,0x0,0x18,0x88,0x5B0,0xD0,0x8,0x1178,0x19C");
					}
				}
				else
				{
					bool flag3 = !this.Hover.Checked;
					if (flag3)
					{
						this.m.UnfreezeValue("Minecraft.Windows.exe+0x036DD9F8,0x140,0x578,0x18,0x0,0x10,0x158,0x10,0x498");
					}
				}
				bool triggerBotEnabled = OctoMain.TriggerBotEnabled;
				if (triggerBotEnabled)
				{
					bool flag4 = this.m.ReadInt("Minecraft.Windows.exe+0x036DE720,38,148,330,900", "") != 0;
					if (flag4)
					{
						uint x = (uint)Cursor.Position.X;
						uint y = (uint)Cursor.Position.Y;
						OctoMain.Win32.mouse_event(6U, x, y, 0U, 0U);
						Thread.Sleep(50);
					}
				}
			}
		}

		// Token: 0x06000010 RID: 16 RVA: 0x000032D0 File Offset: 0x000014D0
		private void Speed_CheckedChanged(object sender, EventArgs e)
		{
			bool @checked = this.Speed.Checked;
			if (@checked)
			{
				OctoMain.<>c__DisplayClass17_0 CS$<>8__locals1;
				CS$<>8__locals1.s = this.SpeedValue.Text;
				bool flag = OctoMain.<Speed_CheckedChanged>g__IsAllDigits|17_0(ref CS$<>8__locals1);
				if (flag)
				{
					this.m.WriteMemory("Minecraft.Windows.exe+0x036DD9F8,0x80,0x140,0x580,0x100,0x438,0x18,0x1F0,0x9C", "float", this.SpeedValue.Text, "", null);
					bool flag2 = this.m.WriteMemory("Minecraft.Windows.exe+0x036DD9F8,0x80,0x140,0x580,0x100,0x438,0x18,0x1F0,0x9C", "float", this.SpeedValue.Text, "", null);
					if (flag2)
					{
						OctoMain.speedEnabled = true;
						this.Status.Text = "Speed was enabled and was set to" + this.SpeedValue.Text + "!";
						this.m.FreezeValue("Minecraft.Windows.exe+0x036DD9F8,0x80,0x140,0x580,0x100,0x438,0x18,0x1F0,0x9C", "float", this.SpeedValue.Text, "");
					}
					else
					{
						this.Status.Text = "Failed to enable speed";
					}
				}
				else
				{
					this.Status.Text = this.SpeedValue.Text + " Is not a number!";
				}
			}
			else
			{
				bool flag3 = !this.Speed.Checked;
				if (flag3)
				{
					this.m.WriteMemory("Minecraft.Windows.exe+0x036DD9F8,0x80,0x140,0x580,0x100,0x438,0x18,0x1F0,0x9C", "float", "0.1", "", null);
					bool flag4 = this.m.WriteMemory("Minecraft.Windows.exe+0x036DD9F8,0x80,0x140,0x580,0x100,0x438,0x18,0x1F0,0x9C", "float", "0.1", "", null);
					if (flag4)
					{
						this.m.UnfreezeValue("Minecraft.Windows.exe+0x036DD9F8,0x80,0x140,0x580,0x100,0x438,0x18,0x1F0,0x9C");
						this.Status.Text = "Speed was disabled!";
						OctoMain.speedEnabled = false;
					}
					else
					{
						this.Status.Text = "Failed to disable speed";
					}
				}
			}
		}

		// Token: 0x06000011 RID: 17 RVA: 0x00003498 File Offset: 0x00001698
		private void AirJump_CheckedChanged_1(object sender, EventArgs e)
		{
			bool @checked = this.AirJump.Checked;
			if (@checked)
			{
				this.m.FreezeValue("Minecraft.Windows.exe+0x036DD9F8,0x140,0x580,0x18,0x0,0x10,0x158,0x10,0x1A0", "int", "16777473", "");
				this.Status.Text = "Air jump was enabled!";
				OctoMain.AirJumpEnabled = true;
			}
			else
			{
				bool flag = !this.AirJump.Checked;
				if (flag)
				{
					this.m.UnfreezeValue("Minecraft.Windows.exe+0x036DD9F8,0x140,0x580,0x18,0x0,0x10,0x158,0x10,0x1A0");
					this.Status.Text = "Air jump was disabled!";
					OctoMain.AirJumpEnabled = false;
				}
			}
		}

		// Token: 0x06000012 RID: 18 RVA: 0x0000352C File Offset: 0x0000172C
		private void NoFall_CheckedChanged(object sender, EventArgs e)
		{
			bool @checked = this.NoFall.Checked;
			if (@checked)
			{
				this.m.FreezeValue("Minecraft.Windows.exe+0x036E7688,0x0,0x18,0x88,0x5B0,0xD0,0x8,0x1178,0x19C", "float", "0", "");
				this.Status.Text = "No Fall was enabled!";
				OctoMain.NoFallEnabled = true;
			}
			else
			{
				bool flag = !this.NoFall.Checked;
				if (flag)
				{
					this.m.UnfreezeValue("Minecraft.Windows.exe+0x036E7688,0x0,0x18,0x88,0x5B0,0xD0,0x8,0x1178,0x19C");
					this.Status.Text = "No Fall was disabled!";
					OctoMain.NoFallEnabled = false;
				}
			}
		}

		// Token: 0x06000013 RID: 19 RVA: 0x000035C0 File Offset: 0x000017C0
		private void SlowFall_CheckedChanged(object sender, EventArgs e)
		{
			bool @checked = this.SlowFall.Checked;
			if (@checked)
			{
				OctoMain.SlowFallEnabled = true;
				this.Status.Text = "SlowFall was enabled";
			}
			else
			{
				bool flag = !this.SlowFall.Checked;
				if (flag)
				{
					OctoMain.SlowFallEnabled = false;
					this.Status.Text = "SlowFall was disabled!";
				}
			}
		}

		// Token: 0x06000014 RID: 20 RVA: 0x00003624 File Offset: 0x00001824
		private void checkBox1_CheckedChanged(object sender, EventArgs e)
		{
			bool @checked = this.Fly.Checked;
			if (@checked)
			{
				this.m.WriteMemory("Minecraft.Windows.exe+0x369A320,0x8c4", "int", "1", "", null);
				this.m.FreezeValue("Minecraft.Windows.exe+0x369A320,0x8c4", "int", "1", "");
				bool flag = this.m.WriteMemory("Minecraft.Windows.exe+0x369A320,0x8c4", "int", "1", "", null);
				if (flag)
				{
					this.Status.Text = "Fly enabled";
					OctoMain.FlyEnabled = true;
				}
				else
				{
					this.Status.Text = "Failed to enable fly";
				}
			}
			else
			{
				bool flag2 = !this.Fly.Checked;
				if (flag2)
				{
					this.m.WriteMemory("Minecraft.Windows.exe+0x369A320,0x8c4", "int", "0", "", null);
					this.m.FreezeValue("Minecraft.Windows.exe+0x369A320,0x8c4", "int", "0", "");
					bool flag3 = this.m.WriteMemory("Minecraft.Windows.exe+0x369A320,0x8c4", "int", "0", "", null);
					if (flag3)
					{
						this.Status.Text = "Fly was disabled!";
						OctoMain.FlyEnabled = false;
					}
					else
					{
						this.Status.Text = "Failed to disable fly";
					}
				}
			}
		}

		// Token: 0x06000015 RID: 21 RVA: 0x00003788 File Offset: 0x00001988
		private void Attach_Click(object sender, EventArgs e)
		{
			int procIdFromName = this.m.GetProcIdFromName("Minecraft.Windows");
			this.displaypid.Text = "PID: " + procIdFromName.ToString();
			bool flag = procIdFromName > 0;
			if (flag)
			{
				bool flag2 = this.m.OpenProcess(procIdFromName);
				this.Status.Text = "Minecraft Is connected!";
				this.IsOpen.Text = "Is Open: true";
			}
			else
			{
				this.Status.Text = "Minecraft Is not open!";
				this.IsOpen.Text = "Is Open: false";
			}
		}

		// Token: 0x06000016 RID: 22 RVA: 0x00003828 File Offset: 0x00001A28
		private void checkBox1_CheckedChanged_1(object sender, EventArgs e)
		{
			bool @checked = this.SuperJump.Checked;
			if (@checked)
			{
				OctoMain.<>c__DisplayClass23_0 CS$<>8__locals1;
				CS$<>8__locals1.s = this.JumpValue.Text;
				bool flag = OctoMain.<checkBox1_CheckedChanged_1>g__IsAllDigits|23_0(ref CS$<>8__locals1);
				if (flag)
				{
					this.Status.Text = "Super Jump was enabled and was set to" + this.JumpValue.Text + "!";
					OctoMain.SuperJumpEnabled = true;
				}
				else
				{
					this.Status.Text = this.JumpValue.Text + " Is not a number!";
				}
			}
			else
			{
				bool flag2 = !this.SuperJump.Checked;
				if (flag2)
				{
					this.Status.Text = "Super Jump was disabled";
					OctoMain.SuperJumpEnabled = false;
				}
			}
		}

		// Token: 0x06000017 RID: 23 RVA: 0x000038E8 File Offset: 0x00001AE8
		private void Hover_CheckedChanged(object sender, EventArgs e)
		{
			bool @checked = this.Hover.Checked;
			if (@checked)
			{
				this.m.FreezeValue("Minecraft.Windows.exe+0x036DD9F8,0x140,0x578,0x18,0x0,0x10,0x158,0x10,0x498", "float", "0", "");
				this.Status.Text = "Hover was enabled";
				OctoMain.HoverENabled = true;
			}
			else
			{
				bool flag = !this.Hover.Checked;
				if (flag)
				{
					this.m.UnfreezeValue("Minecraft.Windows.exe+0x036DD9F8,0x140,0x578,0x18,0x0,0x10,0x158,0x10,0x498");
					this.Status.Text = "Hover was disabled!";
					OctoMain.HoverENabled = false;
				}
			}
		}

		// Token: 0x06000018 RID: 24 RVA: 0x0000397C File Offset: 0x00001B7C
		private void Phase_CheckedChanged(object sender, EventArgs e)
		{
			bool @checked = this.Phase.Checked;
			if (@checked)
			{
				float num = this.m.ReadFloat("Minecraft.Windows.exe+0x036DE720,0x38,0x148,0x468", "", true);
				OctoMain.PhaseEnabled = true;
				num -= 2f;
				this.m.WriteMemory("Minecraft.Windows.exe+0x036DE720,0x38,0x148,0x468", "float", num.ToString(), "", null);
				this.Status.Text = "Keep toggling phase until your arm turns black!";
			}
			else
			{
				bool flag = !this.Phase.Checked;
				if (flag)
				{
					float num2 = this.m.ReadFloat("Minecraft.Windows.exe+0x036DE720,0x38,0x148,0x468", "", true);
					OctoMain.PhaseEnabled = true;
					num2 += 2f;
					this.m.WriteMemory("Minecraft.Windows.exe+0x036DE720,0x38,0x148,0x468", "float", num2.ToString(), "", null);
					this.Status.Text = "Keep toggling phase until your arm turns black!";
					this.Status.Text = "disabled phase";
					OctoMain.PhaseEnabled = false;
				}
			}
		}

		// Token: 0x06000019 RID: 25 RVA: 0x00003A7D File Offset: 0x00001C7D
		private void dllinject_Click(object sender, EventArgs e)
		{
			this.m.InjectDll(this.dllpath.Text);
		}

		// Token: 0x0600001A RID: 26 RVA: 0x00003A98 File Offset: 0x00001C98
		private void ContinuallyUpdateMouseCoordLabel(int precisionMilliseconds = 60)
		{
			for (;;)
			{
				Thread.Sleep(precisionMilliseconds);
				OctoMain.UpdateMouseCoordLabelsCallback method = new OctoMain.UpdateMouseCoordLabelsCallback(this.SetMouseCoordLabels);
				string text = Cursor.Position.X.ToString();
				string text2 = Cursor.Position.Y.ToString();
				try
				{
					base.Invoke(method, new object[]
					{
						text,
						text2
					});
				}
				catch (Exception)
				{
				}
			}
		}

		// Token: 0x0600001B RID: 27 RVA: 0x00003B20 File Offset: 0x00001D20
		private void InitializeWaitTimes(int minTime, int maxTime, int minClicksBeforeMouseMove, int maxClicksBeforeMouseMove)
		{
			this.minWait.Text = (this.minTimeDefault.ToString() ?? "");
			this.maxWait.Text = (this.maxTimeDefault.ToString() ?? "");
			this.minClicksBetweenMovement.Text = (this.minClickBeforeMouseMoveDefault.ToString() ?? "");
			this.maxClicksBetweenMovement.Text = (this.maxClickBeforeMouseMoveDefault.ToString() ?? "");
		}

		// Token: 0x0600001C RID: 28 RVA: 0x00003BB0 File Offset: 0x00001DB0
		private void StartAutoClicker()
		{
			string a;
			bool flag = this.ValidFieldData(out a);
			if (flag)
			{
				bool flag2 = !this.run;
				if (flag2)
				{
					this.AutoClickOnNewThread();
				}
				this.run = true;
				this.DisableSettingFields();
			}
			else
			{
				bool flag3 = a == "NON-INT";
				string text;
				string caption;
				MessageBoxButtons buttons;
				if (flag3)
				{
					text = "Wait Time and Mouse Clicks need to be non-decimal numbers!";
					caption = "Invalid input(s)";
					buttons = MessageBoxButtons.OK;
				}
				else
				{
					text = "Max clicks/Max time cannot be less than Min clicks/Min time!";
					caption = "Max < Min Error";
					buttons = MessageBoxButtons.OK;
				}
				MessageBox.Show(text, caption, buttons);
			}
		}

		// Token: 0x0600001D RID: 29 RVA: 0x00003C38 File Offset: 0x00001E38
		private bool ValidFieldData(out string typeError)
		{
			typeError = "NONE";
			int num;
			int num2;
			int num3;
			int num4;
			try
			{
				num = int.Parse(this.minClicksBetweenMovement.Text.ToString());
				num2 = int.Parse(this.maxClicksBetweenMovement.Text.ToString());
				num3 = int.Parse(this.minWait.Text.ToString());
				num4 = int.Parse(this.maxWait.Text.ToString());
			}
			catch (Exception)
			{
				typeError = "NON-INT";
				return false;
			}
			bool flag = num2 < num || num4 < num3;
			bool result;
			if (flag)
			{
				typeError = "MAX LESS THAN MIN";
				result = false;
			}
			else
			{
				result = true;
			}
			return result;
		}

		// Token: 0x0600001E RID: 30 RVA: 0x00003CEC File Offset: 0x00001EEC
		private void AutoClickOnNewThread()
		{
			this.centerMouseX = Cursor.Position.X;
			this.centerMouseY = Cursor.Position.Y;
			new Thread(new ThreadStart(this.AutoClick))
			{
				IsBackground = true
			}.Start();
		}

		// Token: 0x0600001F RID: 31 RVA: 0x00003D44 File Offset: 0x00001F44
		private void AutoClick()
		{
			int minValue = int.Parse(this.minWait.Text.ToString());
			int maxValue = int.Parse(this.maxWait.Text.ToString());
			int minValue2 = int.Parse(this.minClicksBetweenMovement.Text.ToString());
			int maxValue2 = int.Parse(this.maxClicksBetweenMovement.Text.ToString());
			while (this.run)
			{
				Random random = new Random();
				int num = random.Next(minValue2, maxValue2);
				int num2 = 0;
				while (num2 < num && this.run)
				{
					int millisecondsTimeout = random.Next(minValue, maxValue);
					this.DoMouseClick();
					this.mouseClicksThisRun++;
					MethodInvoker method = delegate()
					{
						this.SetMouseClickLabel(this.mouseClicksThisRun);
					};
					base.Invoke(method);
					Thread.Sleep(millisecondsTimeout);
					num2++;
				}
				MethodInvoker method2 = new MethodInvoker(this.RandomlyUpdateMouseCoordinates);
				base.Invoke(method2);
				Thread.Sleep(1000);
			}
		}

		// Token: 0x06000020 RID: 32 RVA: 0x00003E58 File Offset: 0x00002058
		private void RandomlyUpdateMouseCoordinates()
		{
			bool flag = this.run;
			if (flag)
			{
				this.UpdateMouseCenterIfUserMovedMouse();
				Random random = new Random();
				int x = this.centerMouseX + random.Next(1, this.mousePixelsToMoveFromCenter);
				int y = this.centerMouseY + random.Next(1, this.mousePixelsToMoveFromCenter);
				OctoMain.Win32.POINT point = default(OctoMain.Win32.POINT);
				int millisecondsTimeout = random.Next(1000, 4500);
				OctoMain.Win32.ClientToScreen(base.Handle, ref point);
				OctoMain.Win32.SetCursorPos(x, y);
				Thread.Sleep(millisecondsTimeout);
				this.SetMouseCoordLabels(Cursor.Position.X.ToString(), Cursor.Position.Y.ToString());
			}
		}

		// Token: 0x06000021 RID: 33 RVA: 0x00003F19 File Offset: 0x00002119
		private void SetMouseCoordLabels(string xPos, string yPos)
		{
			this.MouseXLabel.Text = "Mouse X Position: " + xPos;
			this.MouseYLabel.Text = "Mouse Y position: " + yPos;
		}

		// Token: 0x06000022 RID: 34 RVA: 0x00003F4C File Offset: 0x0000214C
		private void DoMouseClick()
		{
			bool flag = this.run;
			if (flag)
			{
				bool flag2 = !this.rightclickenabled;
				if (flag2)
				{
					uint x = (uint)Cursor.Position.X;
					uint y = (uint)Cursor.Position.Y;
					OctoMain.Win32.mouse_event(6U, x, y, 0U, 0U);
				}
				else
				{
					uint x2 = (uint)Cursor.Position.X;
					uint y2 = (uint)Cursor.Position.Y;
					OctoMain.Win32.mouse_event(24U, x2, y2, 0U, 0U);
				}
			}
		}

		// Token: 0x06000023 RID: 35 RVA: 0x00003FD0 File Offset: 0x000021D0
		private void UpdateMouseCenterIfUserMovedMouse()
		{
			bool flag = Math.Abs(Cursor.Position.X - this.centerMouseX) > this.mousePixelsToMoveFromCenter || Math.Abs(Cursor.Position.Y - this.centerMouseY) > this.mousePixelsToMoveFromCenter;
			if (flag)
			{
				this.centerMouseX = Cursor.Position.X;
				this.centerMouseY = Cursor.Position.Y;
			}
		}

		// Token: 0x06000024 RID: 36 RVA: 0x0000404F File Offset: 0x0000224F
		private void SetMouseClickLabel(int numClicks)
		{
			this.numMouseClicks.Text = numClicks.ToString() + " Total Mouse Clicks";
		}

		// Token: 0x06000025 RID: 37 RVA: 0x00004070 File Offset: 0x00002270
		private void EnableSettingFields()
		{
			this.startButton.Enabled = true;
			this.maxClicksBetweenMovement.Enabled = true;
			this.minClicksBetweenMovement.Enabled = true;
			this.minWait.Enabled = true;
			this.maxWait.Enabled = true;
		}

		// Token: 0x06000026 RID: 38 RVA: 0x000040C0 File Offset: 0x000022C0
		private void DisableSettingFields()
		{
			this.startButton.Enabled = false;
			this.maxClicksBetweenMovement.Enabled = false;
			this.minClicksBetweenMovement.Enabled = false;
			this.minWait.Enabled = false;
			this.maxWait.Enabled = false;
		}

		// Token: 0x06000027 RID: 39 RVA: 0x0000410F File Offset: 0x0000230F
		private void startButton_Click_1(object sender, EventArgs e)
		{
			this.StartAutoClicker();
		}

		// Token: 0x06000028 RID: 40 RVA: 0x00004119 File Offset: 0x00002319
		private void stopButton_Click_1(object sender, EventArgs e)
		{
			this.run = false;
			this.mouseClicksThisRun = 0;
			this.SetMouseClickLabel(this.mouseClicksThisRun);
			this.EnableSettingFields();
		}

		// Token: 0x06000029 RID: 41 RVA: 0x0000413E File Offset: 0x0000233E
		private void panel1_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x0600002A RID: 42 RVA: 0x00004144 File Offset: 0x00002344
		private void InstaMine_CheckedChanged(object sender, EventArgs e)
		{
			bool @checked = this.InstaMine.Checked;
			if (@checked)
			{
				this.m.WriteMemory("Minecraft.Windows.exe+036E7648,0x18,0x18,0x88,0xAD0,0x8,0x10,0x150,0xC0C", "int", "1", "", null);
				this.m.FreezeValue("Minecraft.Windows.exe+036E7648,0x18,0x18,0x88,0xAD0,0x8,0x10,0x150,0xC0C", "int", "1", "");
				bool flag = this.m.WriteMemory("Minecraft.Windows.exe+036E7648,0x18,0x18,0x88,0xAD0,0x8,0x10,0x150,0xC0C", "int", "1", "", null);
				if (flag)
				{
					this.Status.Text = "Insta Mine enabled";
					OctoMain.InstaMineEnabled = true;
				}
				else
				{
					this.Status.Text = "Failed to enable Insta Mine";
				}
			}
			else
			{
				bool flag2 = !this.InstaMine.Checked;
				if (flag2)
				{
					this.m.WriteMemory("Minecraft.Windows.exe+036E7648,0x18,0x18,0x88,0xAD0,0x8,0x10,0x150,0xC0C", "int", "0", "", null);
					this.m.FreezeValue("Minecraft.Windows.exe+036E7648,0x18,0x18,0x88,0xAD0,0x8,0x10,0x150,0xC0C", "int", "0", "");
					bool flag3 = this.m.WriteMemory("Minecraft.Windows.exe+036E7648,0x18,0x18,0x88,0xAD0,0x8,0x10,0x150,0xC0C", "int", "0", "", null);
					if (flag3)
					{
						this.Status.Text = "Insta Mine was disabled!";
						OctoMain.InstaMineEnabled = false;
					}
					else
					{
						this.Status.Text = "Failed to disable Insta Mine";
					}
				}
			}
		}

		// Token: 0x0600002B RID: 43 RVA: 0x000042A8 File Offset: 0x000024A8
		private void rightclick_CheckedChanged(object sender, EventArgs e)
		{
			bool @checked = this.rightclick.Checked;
			if (@checked)
			{
				this.rightclickenabled = true;
			}
			else
			{
				this.rightclickenabled = false;
			}
		}

		// Token: 0x0600002C RID: 44 RVA: 0x000042DC File Offset: 0x000024DC
		private void FullBright_CheckedChanged(object sender, EventArgs e)
		{
			bool @checked = this.FullBright.Checked;
			if (@checked)
			{
				this.m.FreezeValue("Minecraft.Windows.exe+0x036A1C38,0xC8,0x120,0xB0,0xF18,0xB0,0x138,0xF0", "float", "16", "");
				this.Status.Text = "Full bright was enabled!";
				OctoMain.FullbrightEnabled = true;
			}
			else
			{
				bool flag = !this.FullBright.Checked;
				if (flag)
				{
					this.m.UnfreezeValue("Minecraft.Windows.exe+0x036A1C38,0xC8,0x120,0xB0,0xF18,0xB0,0x138,0xF0");
					this.m.WriteMemory("Minecraft.Windows.exe+0x036A1C38,0xC8,0x120,0xB0,0xF18,0xB0,0x138,0xF0", "float", "16", "", null);
					this.Status.Text = "Full bright was disabled!";
					OctoMain.FullbrightEnabled = false;
				}
			}
		}

		// Token: 0x0600002D RID: 45 RVA: 0x00004394 File Offset: 0x00002594
		private void NoWeb_CheckedChanged(object sender, EventArgs e)
		{
			bool @checked = this.NoWeb.Checked;
			if (@checked)
			{
				this.m.FreezeValue("Minecraft.Windows.exe+0x036E7698,0x10,0x18,0xB0,0x214", "float", "-1", "");
				this.Status.Text = "NoWeb was enabled!";
				OctoMain.NoWebEnabled = true;
			}
			else
			{
				bool flag = !this.NoWeb.Checked;
				if (flag)
				{
					this.m.UnfreezeValue("Minecraft.Windows.exe+0x036E7698,0x10,0x18,0xB0,0x214");
					this.m.WriteMemory("Minecraft.Windows.exe+0x036E7698,0x10,0x18,0xB0,0x214", "float", "0", "", null);
					this.Status.Text = "NoWeb was disabled!";
					OctoMain.NoWebEnabled = false;
				}
			}
		}

		// Token: 0x0600002E RID: 46 RVA: 0x0000444C File Offset: 0x0000264C
		private void ItemDuper_CheckedChanged(object sender, EventArgs e)
		{
			bool @checked = this.ItemDuper.Checked;
			if (@checked)
			{
				OctoMain.TriggerBotEnabled = true;
			}
			else
			{
				bool flag = !this.ItemDuper.Checked;
				if (flag)
				{
					OctoMain.TriggerBotEnabled = false;
				}
			}
		}

		// Token: 0x06000033 RID: 51 RVA: 0x000065DC File Offset: 0x000047DC
		[CompilerGenerated]
		internal static bool <Speed_CheckedChanged>g__IsAllDigits|17_0(ref OctoMain.<>c__DisplayClass17_0 A_0)
		{
			foreach (char c in A_0.s)
			{
				bool flag = !char.IsDigit(c);
				if (flag)
				{
					return false;
				}
			}
			return true;
		}

		// Token: 0x06000034 RID: 52 RVA: 0x00006628 File Offset: 0x00004828
		[CompilerGenerated]
		internal static bool <checkBox1_CheckedChanged_1>g__IsAllDigits|23_0(ref OctoMain.<>c__DisplayClass23_0 A_0)
		{
			foreach (char c in A_0.s)
			{
				bool flag = !char.IsDigit(c);
				if (flag)
				{
					return false;
				}
			}
			return true;
		}

		// Token: 0x04000013 RID: 19
		public bool rightclickenabled = false;

		// Token: 0x04000014 RID: 20
		public Mem m = new Mem();

		// Token: 0x04000015 RID: 21
		public static bool speedEnabled;

		// Token: 0x04000016 RID: 22
		public static bool AirJumpEnabled;

		// Token: 0x04000017 RID: 23
		public static bool NoFallEnabled;

		// Token: 0x04000018 RID: 24
		public static bool SlowFallEnabled;

		// Token: 0x04000019 RID: 25
		public static bool FlyEnabled;

		// Token: 0x0400001A RID: 26
		public static bool SuperJumpEnabled;

		// Token: 0x0400001B RID: 27
		public static bool HoverENabled;

		// Token: 0x0400001C RID: 28
		public static bool PhaseEnabled;

		// Token: 0x0400001D RID: 29
		public static bool InstaMineEnabled;

		// Token: 0x0400001E RID: 30
		public static bool FullbrightEnabled;

		// Token: 0x0400001F RID: 31
		public static bool NoWebEnabled;

		// Token: 0x04000020 RID: 32
		public static bool TriggerBotEnabled;

		// Token: 0x04000021 RID: 33
		private int centerMouseX;

		// Token: 0x04000022 RID: 34
		private int centerMouseY;

		// Token: 0x04000023 RID: 35
		private bool run = false;

		// Token: 0x04000024 RID: 36
		private int minTimeDefault = 1600;

		// Token: 0x04000025 RID: 37
		private int maxTimeDefault = 2600;

		// Token: 0x04000026 RID: 38
		private int minClickBeforeMouseMoveDefault = 1;

		// Token: 0x04000027 RID: 39
		private int maxClickBeforeMouseMoveDefault = 30;

		// Token: 0x04000028 RID: 40
		private int mousePixelsToMoveFromCenter = 3;

		// Token: 0x04000029 RID: 41
		private int mouseClicksThisRun = 0;

		// Token: 0x0400002A RID: 42
		private static object threadLocker = new object();

		// Token: 0x02000008 RID: 8
		// (Invoke) Token: 0x06000043 RID: 67
		private delegate void UpdateMouseCoordLabelsCallback(string xCoord, string yCoord);

		// Token: 0x02000009 RID: 9
		public class Win32
		{
			// Token: 0x06000046 RID: 70
			[DllImport("User32.Dll")]
			public static extern long SetCursorPos(int x, int y);

			// Token: 0x06000047 RID: 71
			[DllImport("User32.Dll")]
			public static extern bool ClientToScreen(IntPtr hWnd, ref OctoMain.Win32.POINT point);

			// Token: 0x06000048 RID: 72
			[DllImport("user32.dll", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
			public static extern void mouse_event(uint dwFlags, uint dx, uint dy, uint cButtons, uint dwExtraInfo);

			// Token: 0x04000058 RID: 88
			public const int MOUSEEVENTF_LEFTDOWN = 2;

			// Token: 0x04000059 RID: 89
			public const int MOUSEEVENTF_LEFTUP = 4;

			// Token: 0x0400005A RID: 90
			public const int MOUSEEVENTF_RIGHTDOWN = 8;

			// Token: 0x0400005B RID: 91
			public const int MOUSEEVENTF_RIGHTUP = 16;

			// Token: 0x0200000C RID: 12
			public struct POINT
			{
				// Token: 0x0400005E RID: 94
				public int x;

				// Token: 0x0400005F RID: 95
				public int y;
			}
		}
	}
}
