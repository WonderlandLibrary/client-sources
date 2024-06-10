using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Reflection;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using Microsoft.VisualBasic;

namespace Client
{
	// Token: 0x02000003 RID: 3
	public partial class Form1 : Form
	{
		// Token: 0x06000004 RID: 4
		[DllImport("user32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x06000005 RID: 5
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowThreadProcessId(IntPtr intptr_2, out int int_12);

		// Token: 0x06000006 RID: 6
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowTextLength(IntPtr intptr_2);

		// Token: 0x06000007 RID: 7
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowText(IntPtr intptr_2, StringBuilder stringBuilder_0, int int_12);

		// Token: 0x06000008 RID: 8
		[DllImport("user32", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
		public static extern void mouse_event(int int_12, int int_13, int int_14, int int_15, int int_16);

		// Token: 0x06000009 RID: 9
		[DllImport("user32.dll")]
		private static extern bool PostMessage(IntPtr intptr_2, uint uint_0, int int_12, int int_13);

		// Token: 0x0600000A RID: 10
		[DllImport("user32.dll")]
		public static extern int GetAsyncKeyState(Keys keys_0);

		// Token: 0x0600000B RID: 11
		[DllImport("user32.dll")]
		public static extern bool ReleaseCapture();

		// Token: 0x0600000C RID: 12
		[DllImport("user32.dll")]
		public static extern int SendMessage(IntPtr intptr_2, int int_12, int int_13, int int_14);

		// Token: 0x0600000D RID: 13 RVA: 0x00002438 File Offset: 0x00000638
		public Form1()
		{
			<Module>.Class0.smethod_0();
			this.random_0 = new Random();
			this.dotNetScanMemory_SmoLL_0 = new DotNetScanMemory_SmoLL();
			this.icontainer_0 = null;
			base..ctor();
			if (!Form2.bool_0)
			{
				this.InitializeComponent();
			}
			if (new WebClient().DownloadString("https://pastebin.com/raw/LiTRJnTs").Contains("v_enabled = true"))
			{
				this.skeetGroupBox7.Visible = true;
			}
			this.settingsTab.SendToBack();
			this.otherTab.SendToBack();
		}

		// Token: 0x0600000E RID: 14 RVA: 0x000024BC File Offset: 0x000006BC
		public void method_0()
		{
			int num = 0;
			for (;;)
			{
				if (num == 8)
				{
					goto IL_D8;
				}
				IL_117:
				if (num == 2)
				{
					this.int_10++;
					num = 3;
				}
				if (num == 9)
				{
					this.int_10 = 0;
					num = 10;
				}
				if (num == 5)
				{
					this.timer_0.Start();
					num = 6;
				}
				bool flag;
				if (num == 3)
				{
					flag = (this.int_10 == 1);
					num = 4;
				}
				if (num == 11)
				{
					goto IL_9F;
				}
				IL_C9:
				if (num == 4)
				{
					if (!flag)
					{
						goto IL_D8;
					}
					num = 5;
				}
				if (num != 7)
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 6)
					{
						this.skeetButton4.Text = "Toggle off";
						num = 7;
					}
					if (num == 10)
					{
						this.skeetButton4.Text = "Toggle on";
						num = 11;
					}
					if (num == 0)
					{
						num = 1;
					}
					if (num != 12)
					{
						continue;
					}
					break;
				}
				IL_9F:
				this.skeetButton4.Refresh();
				num = 12;
				goto IL_C9;
				IL_D8:
				this.timer_0.Stop();
				num = 9;
				goto IL_117;
			}
		}

		// Token: 0x0600000F RID: 15 RVA: 0x00002698 File Offset: 0x00000898
		public void method_1()
		{
			int num = 0;
			for (;;)
			{
				if (num == 10)
				{
					this.skeetButton7.Text = "Toggle on";
					num = 11;
				}
				if (num == 11)
				{
					goto IL_9A;
				}
				IL_C6:
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				bool flag;
				if (num == 3)
				{
					flag = (this.int_11 == 1);
					num = 4;
				}
				if (num != 4)
				{
					goto IL_69;
				}
				if (flag)
				{
					num = 5;
					goto IL_69;
				}
				goto IL_113;
				IL_12A:
				if (num == 9)
				{
					this.int_11 = 0;
					num = 10;
				}
				if (num == 6)
				{
					this.skeetButton7.Text = "Toggle off";
					num = 7;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num == 12)
				{
					break;
				}
				continue;
				IL_69:
				if (num == 5)
				{
					this.timer_1.Start();
					num = 6;
				}
				if (num == 7)
				{
					goto IL_9A;
				}
				if (num == 2)
				{
					this.int_11++;
					num = 3;
				}
				if (num != 8)
				{
					goto IL_12A;
				}
				IL_113:
				this.timer_1.Stop();
				num = 9;
				goto IL_12A;
				IL_9A:
				this.skeetButton7.Refresh();
				num = 12;
				goto IL_C6;
			}
		}

		// Token: 0x06000010 RID: 16 RVA: 0x00002874 File Offset: 0x00000A74
		private string method_2()
		{
			int num = 0;
			string result;
			for (;;)
			{
				IntPtr foregroundWindow;
				if (num == 3)
				{
					foregroundWindow = Form1.GetForegroundWindow();
					num = 4;
				}
				if (num == 9)
				{
					goto IL_6E;
				}
				IL_95:
				string text;
				if (num == 2)
				{
					text = string.Empty;
					num = 3;
				}
				StringBuilder stringBuilder;
				if (num == 8)
				{
					text = stringBuilder.ToString();
					num = 9;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 7)
				{
					bool flag;
					if (!flag)
					{
						goto IL_6E;
					}
					num = 8;
				}
				int num2;
				if (num == 6)
				{
					bool flag = Form1.GetWindowText(foregroundWindow, stringBuilder, num2) > 0;
					num = 7;
				}
				if (num == 4)
				{
					num2 = Form1.GetWindowTextLength(foregroundWindow) + 1;
					num = 5;
				}
				if (num == 5)
				{
					stringBuilder = new StringBuilder(num2);
					num = 6;
				}
				if (num == 10)
				{
					break;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num == 11)
				{
					break;
				}
				continue;
				IL_6E:
				result = text;
				num = 10;
				goto IL_95;
			}
			return result;
		}

		// Token: 0x06000011 RID: 17 RVA: 0x00002A10 File Offset: 0x00000C10
		public static bool smethod_0()
		{
			int num = 0;
			bool result;
			for (;;)
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num != 11)
				{
					IntPtr foregroundWindow;
					int num2;
					if (num == 8)
					{
						Form1.GetWindowThreadProcessId(foregroundWindow, out num2);
						num = 9;
					}
					if (num == 2)
					{
						foregroundWindow = Form1.GetForegroundWindow();
						num = 3;
					}
					bool flag2;
					if (num != 6)
					{
						if (num != 4)
						{
							goto IL_90;
						}
						bool flag;
						if (flag)
						{
							num = 5;
							goto IL_90;
						}
						goto IL_108;
						IL_11F:
						if (num == 10)
						{
							goto IL_12C;
						}
						goto IL_13D;
						IL_90:
						if (num == 5)
						{
							flag2 = false;
							num = 6;
						}
						if (num == 3)
						{
							flag = (foregroundWindow == IntPtr.Zero);
							num = 4;
						}
						int id;
						if (num == 9)
						{
							flag2 = (num2 == id);
							num = 10;
						}
						if (num != 7)
						{
							goto IL_11F;
						}
						IL_108:
						id = Process.GetCurrentProcess().Id;
						num = 8;
						goto IL_11F;
					}
					goto IL_12C;
					IL_13D:
					if (num == 0)
					{
						num = 1;
					}
					if (num == 12)
					{
						break;
					}
					continue;
					IL_12C:
					result = flag2;
					num = 11;
					goto IL_13D;
				}
				break;
			}
			return result;
		}

		// Token: 0x06000012 RID: 18 RVA: 0x00002BA8 File Offset: 0x00000DA8
		private void timer_0_Tick(object sender, EventArgs e)
		{
			int num = 0;
			int minValue;
			int maxValue;
			do
			{
				bool flag;
				if (num == 4)
				{
					flag = (Convert.ToInt32(this.skeetSlider1.Double_0) > Convert.ToInt32(this.skeetSlider1.Double_0));
					num = 5;
				}
				if (num == 3)
				{
					minValue = 1000 / Convert.ToInt32(this.skeetSlider2.Double_0);
					num = 4;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 6)
				{
					MessageBox.Show("minimum value cannot exceed maximum");
					num = 7;
				}
				if (num == 5)
				{
					if (!flag)
					{
						break;
					}
					num = 6;
				}
				if (num == 7)
				{
					goto IL_1AF;
				}
				if (num == 2)
				{
					maxValue = 1000 / Convert.ToInt32(this.skeetSlider1.Double_0);
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 8);
			try
			{
				this.timer_0.Interval = new Random().Next(minValue, maxValue);
			}
			catch
			{
				this.method_0();
				this.skeetSlider1.Double_0 = 1.0;
				MessageBox.Show("minimum value cannot exceed maximum");
				this.skeetSlider1.Refresh();
			}
			IL_1AF:
			IntPtr foregroundWindow = Form1.GetForegroundWindow();
			if (Control.MouseButtons == MouseButtons.Left && (this.method_2().Contains("Minecraft") || this.method_2().Contains("Badlion") || this.method_2().Contains("Labymod") || this.method_2().Contains("OCMC") || this.method_2().Contains("Cheatbreaker") || this.method_2().Contains("J3Ultimate") || this.method_2().Contains("Kihar") || this.method_2().Contains("Lunar") || this.method_2().Contains("PvPLounge")) && !Form1.smethod_0())
			{
				int num2 = Convert.ToInt32(this.skeetSlider7.Double_0);
				int x = Cursor.Position.X;
				int y = Cursor.Position.Y;
				int x2 = this.random_0.Next(x - num2 * 2, x + num2 * 2);
				int y2 = this.random_0.Next(y - num2 * 2, y + num2 * 2);
				this.method_3(foregroundWindow, "leftdown");
				Point position = new Point(x2, y2);
				Point position2 = Cursor.Position;
				if (this.skeetToggle3.Boolean_0)
				{
					Cursor.Position = position;
				}
				Thread.Sleep(this.random_0.Next(40, 50));
				this.method_3(foregroundWindow, "leftup");
			}
		}

		// Token: 0x06000013 RID: 19 RVA: 0x00002F80 File Offset: 0x00001180
		public void method_3(IntPtr intptr_2, string string_0)
		{
			int num = 0;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					num = 3;
				}
				if (num == 3)
				{
					@class.string_0 = string_;
					num = 4;
				}
				if (num == 4)
				{
					@class.intptr_0 = intptr_2;
					num = 5;
				}
				if (num == 5)
				{
					new Thread(new ThreadStart(@class.method_0)).Start();
					num = 6;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 6);
		}

		// Token: 0x06000014 RID: 20 RVA: 0x0000307C File Offset: 0x0000127C
		private void skeetButton4_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 2)
				{
					this.method_0();
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
		}

		// Token: 0x06000015 RID: 21 RVA: 0x000030F8 File Offset: 0x000012F8
		public static int smethod_1(int int_12, int int_13)
		{
			int num = 0;
			int result;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					result = (int_13 << 16 | (int_12 & 65535));
					num = 3;
				}
				if (num == 3)
				{
					break;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 4);
			return result;
		}

		// Token: 0x06000016 RID: 22 RVA: 0x00003194 File Offset: 0x00001394
		private void timer_1_Tick(object sender, EventArgs e)
		{
			int num = 0;
			int maxValue;
			int minValue;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				bool flag;
				if (num == 4)
				{
					flag = (Convert.ToInt32(this.skeetSlider4.Double_0) > Convert.ToInt32(this.skeetSlider3.Double_0));
					num = 5;
				}
				if (num == 9)
				{
					MessageBox.Show("minimum value cannot exceed maximum");
					num = 10;
				}
				if (num == 8)
				{
					this.skeetSlider4.Refresh();
					num = 9;
				}
				if (num == 6)
				{
					this.method_1();
					num = 7;
				}
				if (num == 7)
				{
					this.skeetSlider4.Double_0 = 1.0;
					num = 8;
				}
				if (num == 10)
				{
					goto IL_21C;
				}
				if (num == 2)
				{
					maxValue = 1000 / Convert.ToInt32(this.skeetSlider4.Double_0);
					num = 3;
				}
				if (num == 3)
				{
					minValue = 1000 / Convert.ToInt32(this.skeetSlider3.Double_0);
					num = 4;
				}
				if (num == 5)
				{
					if (!flag)
					{
						break;
					}
					num = 6;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 11);
			try
			{
				this.timer_1.Interval = new Random().Next(minValue, maxValue);
			}
			catch
			{
				this.method_1();
				this.skeetSlider4.Double_0 = 1.0;
				MessageBox.Show("minimum value cannot exceed maximum");
				this.skeetSlider4.Refresh();
			}
			IL_21C:
			IntPtr foregroundWindow = Form1.GetForegroundWindow();
			if (Control.MouseButtons == MouseButtons.Right && (this.method_2().Contains("Minecraft") || this.method_2().Contains("Badlion") || this.method_2().Contains("Labymod") || this.method_2().Contains("OCMC") || this.method_2().Contains("Cheatbreaker") || this.method_2().Contains("J3Ultimate") || this.method_2().Contains("Kihar") || this.method_2().Contains("Lunar") || this.method_2().Contains("PvPLounge")) && !Form1.smethod_0())
			{
				int num2 = Convert.ToInt32(this.skeetSlider8.Double_0);
				int x = Cursor.Position.X;
				int y = Cursor.Position.Y;
				int x2 = this.random_0.Next(x - num2 * 2, x + num2 * 2);
				int y2 = this.random_0.Next(y - num2 * 2, y + num2 * 2);
				this.method_3(foregroundWindow, "rightdown");
				Point position = new Point(x2, y2);
				Point position2 = Cursor.Position;
				if (this.skeetToggle6.Boolean_0)
				{
					Cursor.Position = position;
				}
				Thread.Sleep(this.random_0.Next(40, 50));
				this.method_3(foregroundWindow, "rightup");
			}
		}

		// Token: 0x06000017 RID: 23 RVA: 0x000035DC File Offset: 0x000017DC
		private void skeetButton7_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					this.method_1();
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
		}

		// Token: 0x06000018 RID: 24 RVA: 0x00003658 File Offset: 0x00001858
		private void timer_6_Tick(object sender, EventArgs e)
		{
			int num = 0;
			for (;;)
			{
				if (num != 53)
				{
					goto IL_0E;
				}
				bool flag;
				if (flag)
				{
					num = 54;
					goto IL_0E;
				}
				goto IL_A1E;
				IL_13FD:
				int num2;
				if (num == 171)
				{
					num2 = 1;
					num = 172;
				}
				bool flag2;
				if (num == 17)
				{
					if (!flag2)
					{
						goto IL_1435;
					}
					num = 18;
				}
				if (num == 146)
				{
					this.LeftBindBTN.Text = "Bound: N7";
					num = 147;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num == 176)
				{
					break;
				}
				continue;
				IL_1323:
				if (num == 147)
				{
					num2 = 1;
					num = 148;
				}
				if (num == 154)
				{
					this.LeftBindBTN.Text = "Bound: N9";
					num = 155;
				}
				if (num == 14)
				{
					this.LeftBindBTN.Text = "Bound: B";
					num = 15;
				}
				bool flag3;
				if (num == 117)
				{
					if (!flag3)
					{
						goto IL_17DD;
					}
					num = 118;
				}
				if (num == 135)
				{
					num2 = 1;
					num = 136;
				}
				if (num == 140)
				{
					goto IL_13DF;
				}
				goto IL_13FD;
				IL_11E0:
				if (num == 4)
				{
					bool flag4;
					if (!flag4)
					{
						goto IL_148A;
					}
					num = 5;
				}
				if (num == 55)
				{
					num2 = 1;
					num = 56;
				}
				if (num == 3)
				{
					bool flag4 = Form1.GetAsyncKeyState(Keys.Escape) != 0;
					num = 4;
				}
				if (num == 139)
				{
					num2 = 1;
					num = 140;
				}
				if (num == 74)
				{
					this.LeftBindBTN.Text = "Bound: Q";
					num = 75;
				}
				bool flag5;
				if (num == 157)
				{
					if (!flag5)
					{
						goto IL_14D3;
					}
					num = 158;
				}
				if (num == 71)
				{
					num2 = 1;
					num = 72;
				}
				if (num == 38)
				{
					this.LeftBindBTN.Text = "Bound: H";
					num = 39;
				}
				if (num == 168)
				{
					goto IL_1305;
				}
				goto IL_1323;
				IL_10B6:
				bool flag6;
				if (num == 45)
				{
					if (!flag6)
					{
						goto IL_151C;
					}
					num = 46;
				}
				if (num == 26)
				{
					this.LeftBindBTN.Text = "Bound: E";
					num = 27;
				}
				if (num == 163)
				{
					num2 = 1;
					num = 164;
				}
				bool flag7;
				if (num == 69)
				{
					if (!flag7)
					{
						goto IL_1130;
					}
					num = 70;
				}
				if (num == 66)
				{
					this.LeftBindBTN.Text = "Bound: O";
					num = 67;
				}
				if (num == 136)
				{
					goto IL_11C2;
				}
				goto IL_11E0;
				IL_1171:
				if (num == 54)
				{
					this.LeftBindBTN.Text = "Bound: L";
					num = 55;
				}
				if (num == 32)
				{
					goto IL_1098;
				}
				goto IL_10B6;
				IL_1076:
				if (num == 72)
				{
					goto IL_1130;
				}
				goto IL_1171;
				IL_1455:
				bool flag8 = Form1.GetAsyncKeyState(Keys.R) != 0;
				num = 77;
				goto IL_1076;
				IL_1814:
				if (num == 91)
				{
					num2 = 1;
					num = 92;
				}
				bool flag9;
				if (num == 73)
				{
					if (!flag9)
					{
						goto IL_1455;
					}
					num = 74;
				}
				if (num == 64)
				{
					goto IL_1794;
				}
				goto IL_17CB;
				IL_17DD:
				bool flag10 = Form1.GetAsyncKeyState(Keys.NumPad1) != 0;
				num = 121;
				goto IL_1814;
				IL_13DF:
				bool flag11 = Form1.GetAsyncKeyState(Keys.NumPad6) != 0;
				num = 141;
				goto IL_13FD;
				IL_1894:
				if (num == 150)
				{
					this.LeftBindBTN.Text = "Bound: N8";
					num = 151;
				}
				if (num == 77)
				{
					if (!flag8)
					{
						goto IL_1582;
					}
					num = 78;
				}
				if (num == 166)
				{
					this.LeftBindBTN.Text = "Bound: +";
					num = 167;
				}
				if (num == 42)
				{
					this.LeftBindBTN.Text = "Bound: I";
					num = 43;
				}
				bool flag12;
				if (num == 173)
				{
					if (!flag12)
					{
						break;
					}
					num = 174;
				}
				if (num == 127)
				{
					num2 = 1;
					num = 128;
				}
				bool flag13;
				if (num == 49)
				{
					if (!flag13)
					{
						goto IL_650;
					}
					num = 50;
				}
				bool flag14;
				if (num == 137)
				{
					if (!flag14)
					{
						goto IL_13DF;
					}
					num = 138;
				}
				if (num == 107)
				{
					num2 = 1;
					num = 108;
				}
				if (num == 82)
				{
					this.LeftBindBTN.Text = "Bound: S";
					num = 83;
				}
				if (num == 134)
				{
					this.LeftBindBTN.Text = "Bound: N4";
					num = 135;
				}
				if (num == 51)
				{
					num2 = 1;
					num = 52;
				}
				if (num == 15)
				{
					num2 = 1;
					num = 16;
				}
				bool flag15;
				if (num == 89)
				{
					if (!flag15)
					{
						goto IL_CDD;
					}
					num = 90;
				}
				if (num == 27)
				{
					num2 = 1;
					num = 28;
				}
				if (num == 90)
				{
					this.LeftBindBTN.Text = "Bound: U";
					num = 91;
				}
				if (num == 70)
				{
					this.LeftBindBTN.Text = "Bound: P";
					num = 71;
				}
				if (num == 164)
				{
					goto IL_540;
				}
				IL_57E:
				if (num == 114)
				{
					this.LeftBindBTN.Text = "Bound: INS";
					num = 115;
				}
				if (num == 175)
				{
					this.timer_7.Start();
					num = 176;
				}
				bool flag16;
				if (num == 161)
				{
					if (!flag16)
					{
						goto IL_540;
					}
					num = 162;
				}
				if (num == 2)
				{
					num2 = 0;
					num = 3;
				}
				if (num == 50)
				{
					this.LeftBindBTN.Text = "Bound: K";
					num = 51;
				}
				if (num == 47)
				{
					num2 = 1;
					num = 48;
				}
				if (num == 6)
				{
					this.timer_6.Stop();
					num = 7;
				}
				bool flag17;
				if (num == 133)
				{
					if (!flag17)
					{
						goto IL_11C2;
					}
					num = 134;
				}
				if (num == 52)
				{
					goto IL_650;
				}
				goto IL_66E;
				IL_540:
				bool flag18 = Form1.GetAsyncKeyState(Keys.Add) != 0;
				num = 165;
				goto IL_57E;
				IL_66E:
				bool flag19;
				if (num == 85)
				{
					if (!flag19)
					{
						goto IL_1669;
					}
					num = 86;
				}
				if (num == 10)
				{
					this.LeftBindBTN.Text = "Bound: A";
					num = 11;
				}
				if (num == 106)
				{
					this.LeftBindBTN.Text = "Bound: Y";
					num = 107;
				}
				if (num == 18)
				{
					this.LeftBindBTN.Text = "Bound: C";
					num = 19;
				}
				if (num == 34)
				{
					this.LeftBindBTN.Text = "Bound: G";
					num = 35;
				}
				if (num == 96)
				{
					goto IL_1826;
				}
				goto IL_1844;
				IL_650:
				flag = (Form1.GetAsyncKeyState(Keys.L) != 0);
				num = 53;
				goto IL_66E;
				IL_1853:
				flag17 = (Form1.GetAsyncKeyState(Keys.NumPad4) != 0);
				num = 133;
				goto IL_1894;
				IL_1782:
				bool flag20;
				if (num == 129)
				{
					if (!flag20)
					{
						goto IL_1853;
					}
					num = 130;
				}
				if (num == 44)
				{
					goto IL_84B;
				}
				goto IL_869;
				IL_174B:
				flag7 = (Form1.GetAsyncKeyState(Keys.P) != 0);
				num = 69;
				goto IL_1782;
				IL_14C1:
				if (num == 141)
				{
					if (!flag11)
					{
						goto IL_15B7;
					}
					num = 142;
				}
				bool flag21;
				if (num == 101)
				{
					if (!flag21)
					{
						goto IL_1649;
					}
					num = 102;
				}
				if (num == 123)
				{
					num2 = 1;
					num = 124;
				}
				bool flag22;
				if (num == 65)
				{
					if (!flag22)
					{
						goto IL_174B;
					}
					num = 66;
				}
				if (num == 36)
				{
					goto IL_FDC;
				}
				goto IL_FFA;
				IL_CFB:
				if (num == 79)
				{
					num2 = 1;
					num = 80;
				}
				if (num == 115)
				{
					num2 = 1;
					num = 116;
				}
				bool flag23;
				if (num == 149)
				{
					if (!flag23)
					{
						goto IL_D4C;
					}
					num = 150;
				}
				if (num == 62)
				{
					this.LeftBindBTN.Text = "Bound: N";
					num = 63;
				}
				bool flag24;
				if (num == 57)
				{
					if (!flag24)
					{
						goto IL_1600;
					}
					num = 58;
				}
				if (num == 138)
				{
					this.LeftBindBTN.Text = "Bound: N5";
					num = 139;
				}
				if (num == 174)
				{
					this.timer_6.Stop();
					num = 175;
				}
				if (num == 142)
				{
					this.LeftBindBTN.Text = "Bound: N6";
					num = 143;
				}
				bool flag25;
				if (num == 169)
				{
					if (!flag25)
					{
						goto IL_18EF;
					}
					num = 170;
				}
				if (num == 162)
				{
					this.LeftBindBTN.Text = "Bound: -";
					num = 163;
				}
				if (num == 126)
				{
					this.LeftBindBTN.Text = "Bound: N2";
					num = 127;
				}
				if (num == 19)
				{
					num2 = 1;
					num = 20;
				}
				if (num == 158)
				{
					this.LeftBindBTN.Text = "Bound: *";
					num = 159;
				}
				if (num == 67)
				{
					num2 = 1;
					num = 68;
				}
				bool flag26;
				if (num == 37)
				{
					if (!flag26)
					{
						goto IL_16F1;
					}
					num = 38;
				}
				if (num == 8)
				{
					goto IL_148A;
				}
				goto IL_14C1;
				IL_CB1:
				bool flag27;
				if (num == 145)
				{
					if (!flag27)
					{
						goto IL_195F;
					}
					num = 146;
				}
				if (num == 92)
				{
					goto IL_CDD;
				}
				goto IL_CFB;
				IL_D8D:
				if (num == 110)
				{
					this.LeftBindBTN.Text = "Bound: Z";
					num = 111;
				}
				if (num == 143)
				{
					num2 = 1;
					num = 144;
				}
				bool flag28;
				if (num == 33)
				{
					if (!flag28)
					{
						goto IL_FDC;
					}
					num = 34;
				}
				if (num == 112)
				{
					goto IL_C93;
				}
				goto IL_CB1;
				IL_150A:
				if (num == 95)
				{
					num2 = 1;
					num = 96;
				}
				if (num == 152)
				{
					goto IL_D4C;
				}
				goto IL_D8D;
				IL_17CB:
				if (num == 87)
				{
					num2 = 1;
					num = 88;
				}
				if (num == 103)
				{
					num2 = 1;
					num = 104;
				}
				if (num == 122)
				{
					this.LeftBindBTN.Text = "Bound: N1";
					num = 123;
				}
				if (num == 22)
				{
					this.LeftBindBTN.Text = "Bound: D";
					num = 23;
				}
				if (num == 11)
				{
					num2 = 1;
					num = 12;
				}
				bool flag29;
				if (num == 125)
				{
					if (!flag29)
					{
						goto IL_19A8;
					}
					num = 126;
				}
				if (num == 68)
				{
					goto IL_174B;
				}
				goto IL_1782;
				IL_1794:
				flag22 = (Form1.GetAsyncKeyState(Keys.O) != 0);
				num = 65;
				goto IL_17CB;
				IL_15EE:
				if (num == 31)
				{
					num2 = 1;
					num = 32;
				}
				bool flag30;
				if (num == 61)
				{
					if (!flag30)
					{
						goto IL_1794;
					}
					num = 62;
				}
				if (num == 167)
				{
					num2 = 1;
					num = 168;
				}
				if (num == 28)
				{
					goto IL_1562;
				}
				goto IL_15A5;
				IL_15B7:
				flag27 = (Form1.GetAsyncKeyState(Keys.NumPad7) != 0);
				num = 145;
				goto IL_15EE;
				IL_1637:
				if (num == 121)
				{
					if (!flag10)
					{
						goto IL_171E;
					}
					num = 122;
				}
				if (num == 78)
				{
					this.LeftBindBTN.Text = "Bound: R";
					num = 79;
				}
				if (num == 130)
				{
					this.LeftBindBTN.Text = "Bound: N3";
					num = 131;
				}
				if (num == 83)
				{
					num2 = 1;
					num = 84;
				}
				bool flag31;
				if (num == 113)
				{
					if (!flag31)
					{
						goto IL_169E;
					}
					num = 114;
				}
				if (num == 144)
				{
					goto IL_15B7;
				}
				goto IL_15EE;
				IL_904:
				if (num == 60)
				{
					goto IL_1600;
				}
				goto IL_1637;
				IL_1669:
				flag15 = (Form1.GetAsyncKeyState(Keys.U) != 0);
				num = 89;
				goto IL_904;
				IL_18DD:
				bool flag32;
				if (num == 153)
				{
					if (!flag32)
					{
						goto IL_AD1;
					}
					num = 154;
				}
				if (num == 43)
				{
					num2 = 1;
					num = 44;
				}
				bool flag33;
				if (num == 109)
				{
					if (!flag33)
					{
						goto IL_C93;
					}
					num = 110;
				}
				bool flag34;
				if (num == 29)
				{
					if (!flag34)
					{
						goto IL_1098;
					}
					num = 30;
				}
				if (num == 63)
				{
					num2 = 1;
					num = 64;
				}
				if (num == 7)
				{
					this.timer_7.Stop();
					num = 8;
				}
				if (num == 30)
				{
					this.LeftBindBTN.Text = "Bound: F";
					num = 31;
				}
				if (num == 86)
				{
					this.LeftBindBTN.Text = "Bound: T";
					num = 87;
				}
				if (num == 118)
				{
					this.LeftBindBTN.Text = "Bound: N0";
					num = 119;
				}
				if (num == 132)
				{
					goto IL_1853;
				}
				goto IL_1894;
				IL_18A6:
				flag33 = (Form1.GetAsyncKeyState(Keys.Z) != 0);
				num = 109;
				goto IL_18DD;
				IL_A3C:
				if (num == 75)
				{
					num2 = 1;
					num = 76;
				}
				if (num == 39)
				{
					num2 = 1;
					num = 40;
				}
				bool flag35;
				if (num == 105)
				{
					if (!flag35)
					{
						goto IL_18A6;
					}
					num = 106;
				}
				if (num == 100)
				{
					goto IL_AA6;
				}
				goto IL_AC4;
				IL_A1E:
				flag24 = (Form1.GetAsyncKeyState(Keys.M) != 0);
				num = 57;
				goto IL_A3C;
				IL_A11:
				if (num == 56)
				{
					goto IL_A1E;
				}
				goto IL_A3C;
				IL_15A5:
				if (num == 80)
				{
					goto IL_1582;
				}
				goto IL_A11;
				IL_1562:
				flag34 = (Form1.GetAsyncKeyState(Keys.F) != 0);
				num = 29;
				goto IL_15A5;
				IL_1582:
				bool flag36 = Form1.GetAsyncKeyState(Keys.S) != 0;
				num = 81;
				goto IL_A11;
				IL_1920:
				if (num == 35)
				{
					num2 = 1;
					num = 36;
				}
				if (num == 108)
				{
					goto IL_18A6;
				}
				goto IL_18DD;
				IL_1950:
				if (num == 172)
				{
					goto IL_18EF;
				}
				goto IL_1920;
				IL_1932:
				flag19 = (Form1.GetAsyncKeyState(Keys.T) != 0);
				num = 85;
				goto IL_1950;
				IL_AEF:
				if (num == 131)
				{
					num2 = 1;
					num = 132;
				}
				if (num == 81)
				{
					if (!flag36)
					{
						goto IL_1932;
					}
					num = 82;
				}
				bool flag37;
				if (num == 21)
				{
					if (!flag37)
					{
						goto IL_19F1;
					}
					num = 22;
				}
				if (num == 12)
				{
					goto IL_B59;
				}
				goto IL_B77;
				IL_AD1:
				flag5 = (Form1.GetAsyncKeyState(Keys.Multiply) != 0);
				num = 157;
				goto IL_AEF;
				IL_AC4:
				if (num == 156)
				{
					goto IL_AD1;
				}
				goto IL_AEF;
				IL_AA6:
				flag21 = (Form1.GetAsyncKeyState(Keys.X) != 0);
				num = 101;
				goto IL_AC4;
				IL_18EF:
				flag12 = (num2 == 1);
				num = 173;
				goto IL_1920;
				IL_1305:
				flag25 = (Form1.GetAsyncKeyState(Keys.Divide) != 0);
				num = 169;
				goto IL_1323;
				IL_11C2:
				flag14 = (Form1.GetAsyncKeyState(Keys.NumPad5) != 0);
				num = 137;
				goto IL_11E0;
				IL_1098:
				flag28 = (Form1.GetAsyncKeyState(Keys.G) != 0);
				num = 33;
				goto IL_10B6;
				IL_1130:
				flag9 = (Form1.GetAsyncKeyState(Keys.Q) != 0);
				num = 73;
				goto IL_1171;
				IL_1478:
				if (num == 76)
				{
					goto IL_1455;
				}
				goto IL_1076;
				IL_1435:
				flag37 = (Form1.GetAsyncKeyState(Keys.D) != 0);
				num = 21;
				goto IL_1478;
				IL_FFA:
				if (num == 151)
				{
					num2 = 1;
					num = 152;
				}
				if (num == 99)
				{
					num2 = 1;
					num = 100;
				}
				if (num == 98)
				{
					this.LeftBindBTN.Text = "Bound: W";
					num = 99;
				}
				if (num == 20)
				{
					goto IL_1435;
				}
				goto IL_1478;
				IL_FDC:
				flag26 = (Form1.GetAsyncKeyState(Keys.H) != 0);
				num = 37;
				goto IL_FFA;
				IL_1844:
				if (num == 120)
				{
					goto IL_17DD;
				}
				goto IL_1814;
				IL_1826:
				bool flag38 = Form1.GetAsyncKeyState(Keys.W) != 0;
				num = 97;
				goto IL_1844;
				IL_B77:
				bool flag39;
				if (num == 93)
				{
					if (!flag39)
					{
						goto IL_1826;
					}
					num = 94;
				}
				if (num == 16)
				{
					goto IL_BA3;
				}
				goto IL_BC1;
				IL_B59:
				bool flag40 = Form1.GetAsyncKeyState(Keys.B) != 0;
				num = 13;
				goto IL_B77;
				IL_16DF:
				if (num == 58)
				{
					this.LeftBindBTN.Text = "Bound: M";
					num = 59;
				}
				if (num == 5)
				{
					this.LeftBindBTN.Text = "Bind";
					num = 6;
				}
				bool flag41;
				if (num == 9)
				{
					if (!flag41)
					{
						goto IL_B59;
					}
					num = 10;
				}
				if (num == 104)
				{
					goto IL_1649;
				}
				goto IL_168C;
				IL_170F:
				if (num == 116)
				{
					goto IL_169E;
				}
				goto IL_16DF;
				IL_173C:
				if (num == 40)
				{
					goto IL_16F1;
				}
				goto IL_170F;
				IL_171E:
				flag29 = (Form1.GetAsyncKeyState(Keys.NumPad2) != 0);
				num = 125;
				goto IL_173C;
				IL_869:
				if (num == 94)
				{
					this.LeftBindBTN.Text = "Bound: V";
					num = 95;
				}
				if (num == 124)
				{
					goto IL_171E;
				}
				goto IL_173C;
				IL_84B:
				flag6 = (Form1.GetAsyncKeyState(Keys.J) != 0);
				num = 45;
				goto IL_869;
				IL_19DF:
				bool flag42;
				if (num == 41)
				{
					if (!flag42)
					{
						goto IL_84B;
					}
					num = 42;
				}
				if (num == 148)
				{
					goto IL_195F;
				}
				goto IL_1996;
				IL_19A8:
				flag20 = (Form1.GetAsyncKeyState(Keys.NumPad3) != 0);
				num = 129;
				goto IL_19DF;
				IL_1A28:
				if (num == 23)
				{
					num2 = 1;
					num = 24;
				}
				if (num == 111)
				{
					num2 = 1;
					num = 112;
				}
				if (num == 128)
				{
					goto IL_19A8;
				}
				goto IL_19DF;
				IL_19F1:
				bool flag43 = Form1.GetAsyncKeyState(Keys.E) != 0;
				num = 25;
				goto IL_1A28;
				IL_0E:
				if (num == 46)
				{
					this.LeftBindBTN.Text = "Bound: J";
					num = 47;
				}
				if (num == 159)
				{
					num2 = 1;
					num = 160;
				}
				if (num == 59)
				{
					num2 = 1;
					num = 60;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 24)
				{
					goto IL_19F1;
				}
				goto IL_1A28;
				IL_16F1:
				flag42 = (Form1.GetAsyncKeyState(Keys.I) != 0);
				num = 41;
				goto IL_170F;
				IL_169E:
				flag3 = (Form1.GetAsyncKeyState(Keys.NumPad0) != 0);
				num = 117;
				goto IL_16DF;
				IL_148A:
				flag41 = (Form1.GetAsyncKeyState(Keys.A) != 0);
				num = 9;
				goto IL_14C1;
				IL_CDD:
				flag39 = (Form1.GetAsyncKeyState(Keys.V) != 0);
				num = 93;
				goto IL_CFB;
				IL_C93:
				flag31 = (Form1.GetAsyncKeyState(Keys.Insert) != 0);
				num = 113;
				goto IL_CB1;
				IL_D4C:
				flag32 = (Form1.GetAsyncKeyState(Keys.NumPad9) != 0);
				num = 153;
				goto IL_D8D;
				IL_14D3:
				flag16 = (Form1.GetAsyncKeyState(Keys.Subtract) != 0);
				num = 161;
				goto IL_150A;
				IL_1550:
				if (num == 25)
				{
					if (!flag43)
					{
						goto IL_1562;
					}
					num = 26;
				}
				if (num == 102)
				{
					this.LeftBindBTN.Text = "Bound: X";
					num = 103;
				}
				if (num == 155)
				{
					num2 = 1;
					num = 156;
				}
				if (num == 160)
				{
					goto IL_14D3;
				}
				goto IL_150A;
				IL_151C:
				flag13 = (Form1.GetAsyncKeyState(Keys.K) != 0);
				num = 49;
				goto IL_1550;
				IL_BC1:
				if (num == 48)
				{
					goto IL_151C;
				}
				goto IL_1550;
				IL_BA3:
				flag2 = (Form1.GetAsyncKeyState(Keys.C) != 0);
				num = 17;
				goto IL_BC1;
				IL_1600:
				flag30 = (Form1.GetAsyncKeyState(Keys.N) != 0);
				num = 61;
				goto IL_1637;
				IL_168C:
				if (num == 88)
				{
					goto IL_1669;
				}
				goto IL_904;
				IL_1649:
				flag35 = (Form1.GetAsyncKeyState(Keys.Y) != 0);
				num = 105;
				goto IL_168C;
				IL_1996:
				if (num == 165)
				{
					if (!flag18)
					{
						goto IL_1305;
					}
					num = 166;
				}
				if (num == 97)
				{
					if (!flag38)
					{
						goto IL_AA6;
					}
					num = 98;
				}
				if (num == 119)
				{
					num2 = 1;
					num = 120;
				}
				if (num == 13)
				{
					if (!flag40)
					{
						goto IL_BA3;
					}
					num = 14;
				}
				if (num == 170)
				{
					this.LeftBindBTN.Text = "Bound: /";
					num = 171;
				}
				if (num == 84)
				{
					goto IL_1932;
				}
				goto IL_1950;
				IL_195F:
				flag23 = (Form1.GetAsyncKeyState(Keys.NumPad8) != 0);
				num = 149;
				goto IL_1996;
			}
		}

		// Token: 0x06000019 RID: 25 RVA: 0x00005120 File Offset: 0x00003320
		private void timer_7_Tick(object sender, EventArgs e)
		{
			int num = 0;
			for (;;)
			{
				bool flag;
				if (num == 9)
				{
					flag = (this.LeftBindBTN.Text == "Bound: B");
					num = 10;
				}
				if (num != 15)
				{
					goto IL_2D;
				}
				bool flag2;
				if (flag2)
				{
					num = 16;
					goto IL_2D;
				}
				goto IL_1DCC;
				IL_138C:
				bool flag3;
				if (num == 18)
				{
					if (!flag3)
					{
						goto IL_1945;
					}
					num = 19;
				}
				bool flag4;
				if (num == 118)
				{
					if (!flag4)
					{
						goto IL_1AC6;
					}
					num = 119;
				}
				if (num == 65)
				{
					bool flag5;
					if (!flag5)
					{
						goto IL_19BC;
					}
					num = 66;
				}
				if (num == 180)
				{
					bool flag6;
					if (!flag6)
					{
						goto IL_1DEF;
					}
					num = 181;
				}
				if (num == 111)
				{
					this.method_0();
					num = 112;
				}
				bool flag7;
				if (num == 173)
				{
					if (!flag7)
					{
						goto IL_18DC;
					}
					num = 174;
				}
				if (num == 191)
				{
					this.method_0();
					num = 192;
				}
				bool flag8;
				if (num == 113)
				{
					if (!flag8)
					{
						goto IL_18B9;
					}
					num = 114;
				}
				if (num == 26)
				{
					this.method_0();
					num = 27;
				}
				bool flag9;
				if (num == 183)
				{
					if (!flag9)
					{
						goto IL_480;
					}
					num = 184;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num == 208)
				{
					break;
				}
				continue;
				IL_12F9:
				if (num == 150)
				{
					bool flag10;
					if (!flag10)
					{
						goto IL_D18;
					}
					num = 151;
				}
				if (num == 84)
				{
					bool flag11 = this.LeftBindBTN.Text == "Bound: Q";
					num = 85;
				}
				if (num == 64)
				{
					bool flag5 = this.LeftBindBTN.Text == "Bound: M";
					num = 65;
				}
				if (num != 7)
				{
					goto IL_138C;
				}
				goto IL_187F;
				IL_11A0:
				if (num == 156)
				{
					this.method_0();
					num = 157;
				}
				bool flag12;
				if (num == 48)
				{
					if (!flag12)
					{
						goto IL_1523;
					}
					num = 49;
				}
				if (num == 121)
				{
					this.method_0();
					num = 122;
				}
				if (num == 131)
				{
					this.method_0();
					num = 132;
				}
				bool flag13;
				if (num == 63)
				{
					if (!flag13)
					{
						goto IL_19BC;
					}
					num = 64;
				}
				if (num == 146)
				{
					this.method_0();
					num = 147;
				}
				if (num == 185)
				{
					bool flag14;
					if (!flag14)
					{
						goto IL_480;
					}
					num = 186;
				}
				if (num == 205)
				{
					bool flag15;
					if (!flag15)
					{
						goto IL_FB3;
					}
					num = 206;
				}
				if (num == 130)
				{
					bool flag16;
					if (!flag16)
					{
						goto IL_1A73;
					}
					num = 131;
				}
				if (num == 99)
				{
					bool flag17 = this.LeftBindBTN.Text == "Bound: T";
					num = 100;
				}
				if (num != 12)
				{
					goto IL_12F9;
				}
				goto IL_1C5A;
				IL_1133:
				bool flag18;
				if (num == 148)
				{
					if (!flag18)
					{
						goto IL_D18;
					}
					num = 149;
				}
				if (num == 175)
				{
					bool flag19;
					if (!flag19)
					{
						goto IL_18DC;
					}
					num = 176;
				}
				if (num == 136)
				{
					this.method_0();
					num = 137;
				}
				if (num != 177)
				{
					goto IL_11A0;
				}
				goto IL_18DC;
				IL_1094:
				bool flag20;
				if (num == 34)
				{
					flag20 = (this.LeftBindBTN.Text == "Bound: G");
					num = 35;
				}
				if (num == 106)
				{
					this.method_0();
					num = 107;
				}
				if (num == 5)
				{
					bool flag21;
					if (!flag21)
					{
						goto IL_187F;
					}
					num = 6;
				}
				bool flag22;
				if (num == 153)
				{
					if (!flag22)
					{
						goto IL_9A7;
					}
					num = 154;
				}
				if (num != 92)
				{
					goto IL_1133;
				}
				goto IL_18FF;
				IL_FDA:
				if (num == 184)
				{
					bool flag14 = this.LeftBindBTN.Text == "Bound: N9";
					num = 185;
				}
				if (num == 145)
				{
					bool flag23;
					if (!flag23)
					{
						goto IL_1BCE;
					}
					num = 146;
				}
				if (num == 11)
				{
					this.method_0();
					num = 12;
				}
				if (num == 165)
				{
					bool flag24;
					if (!flag24)
					{
						goto IL_1BAB;
					}
					num = 166;
				}
				if (num == 142)
				{
					goto IL_1076;
				}
				goto IL_1094;
				IL_FCA:
				if (num != 57)
				{
					goto IL_FDA;
				}
				goto IL_1922;
				IL_F55:
				if (num == 101)
				{
					this.method_0();
					num = 102;
				}
				if (num == 199)
				{
					bool flag25 = this.LeftBindBTN.Text == "Bound: +";
					num = 200;
				}
				if (num == 207)
				{
					goto IL_FB3;
				}
				goto IL_FCA;
				IL_F26:
				if (num == 50)
				{
					bool flag26;
					if (!flag26)
					{
						goto IL_1523;
					}
					num = 51;
				}
				if (num != 192)
				{
					goto IL_F55;
				}
				goto IL_1B23;
				IL_EFB:
				if (num == 107)
				{
					goto IL_F08;
				}
				goto IL_F26;
				IL_EAD:
				if (num == 46)
				{
					this.method_0();
					num = 47;
				}
				if (num == 155)
				{
					bool flag27;
					if (!flag27)
					{
						goto IL_9A7;
					}
					num = 156;
				}
				if (num != 27)
				{
					goto IL_EFB;
				}
				goto IL_1CF7;
				IL_DD2:
				bool flag28;
				if (num == 98)
				{
					if (!flag28)
					{
						goto IL_548;
					}
					num = 99;
				}
				if (num == 85)
				{
					bool flag11;
					if (!flag11)
					{
						goto IL_E04;
					}
					num = 86;
				}
				if (num == 140)
				{
					bool flag29;
					if (!flag29)
					{
						goto IL_1076;
					}
					num = 141;
				}
				bool flag30;
				if (num == 74)
				{
					flag30 = (this.LeftBindBTN.Text == "Bound: O");
					num = 75;
				}
				if (num == 115)
				{
					bool flag31;
					if (!flag31)
					{
						goto IL_18B9;
					}
					num = 116;
				}
				if (num != 22)
				{
					goto IL_EAD;
				}
				goto IL_1945;
				IL_D36:
				if (num == 61)
				{
					this.method_0();
					num = 62;
				}
				if (num == 14)
				{
					flag2 = (this.LeftBindBTN.Text == "Bound: C");
					num = 15;
				}
				if (num == 75)
				{
					if (!flag30)
					{
						goto IL_1D83;
					}
					num = 76;
				}
				bool flag32;
				if (num == 13)
				{
					if (!flag32)
					{
						goto IL_1DCC;
					}
					num = 14;
				}
				if (num != 87)
				{
					goto IL_DD2;
				}
				goto IL_E04;
				IL_BE9:
				bool flag33;
				if (num == 103)
				{
					if (!flag33)
					{
						goto IL_F08;
					}
					num = 104;
				}
				if (num == 149)
				{
					bool flag10 = this.LeftBindBTN.Text == "Bound: N2";
					num = 150;
				}
				if (num == 100)
				{
					bool flag17;
					if (!flag17)
					{
						goto IL_548;
					}
					num = 101;
				}
				bool flag34;
				if (num == 128)
				{
					if (!flag34)
					{
						goto IL_1A73;
					}
					num = 129;
				}
				if (num == 105)
				{
					bool flag35;
					if (!flag35)
					{
						goto IL_F08;
					}
					num = 106;
				}
				bool flag36;
				if (num == 119)
				{
					flag36 = (this.LeftBindBTN.Text == "Bound: X");
					num = 120;
				}
				bool flag37;
				if (num == 93)
				{
					if (!flag37)
					{
						goto IL_180A;
					}
					num = 94;
				}
				if (num == 129)
				{
					bool flag16 = this.LeftBindBTN.Text == "Bound: Z";
					num = 130;
				}
				if (num == 152)
				{
					goto IL_D18;
				}
				goto IL_D36;
				IL_BD9:
				if (num != 42)
				{
					goto IL_BE9;
				}
				goto IL_1AE9;
				IL_B70:
				if (num == 30)
				{
					bool flag38;
					if (!flag38)
					{
						goto IL_1E12;
					}
					num = 31;
				}
				bool flag39;
				if (num == 83)
				{
					if (!flag39)
					{
						goto IL_E04;
					}
					num = 84;
				}
				if (num == 72)
				{
					goto IL_BBB;
				}
				goto IL_BD9;
				IL_B60:
				if (num != 167)
				{
					goto IL_B70;
				}
				goto IL_1BAB;
				IL_AB5:
				bool flag40;
				if (num == 28)
				{
					if (!flag40)
					{
						goto IL_1E12;
					}
					num = 29;
				}
				if (num == 35)
				{
					if (!flag20)
					{
						goto IL_176F;
					}
					num = 36;
				}
				bool flag41;
				if (num == 188)
				{
					if (!flag41)
					{
						goto IL_1B23;
					}
					num = 189;
				}
				bool flag42;
				if (num == 138)
				{
					if (!flag42)
					{
						goto IL_1076;
					}
					num = 139;
				}
				if (num == 6)
				{
					this.method_0();
					num = 7;
				}
				if (num != 202)
				{
					goto IL_B60;
				}
				goto IL_1BF1;
				IL_A41:
				if (num == 114)
				{
					bool flag31 = this.LeftBindBTN.Text == "Bound: W";
					num = 115;
				}
				if (num == 69)
				{
					bool flag43 = this.LeftBindBTN.Text == "Bound: N";
					num = 70;
				}
				if (num != 147)
				{
					goto IL_AB5;
				}
				goto IL_1BCE;
				IL_9C5:
				if (num == 139)
				{
					bool flag29 = this.LeftBindBTN.Text == "Bound: N0";
					num = 140;
				}
				bool flag44;
				if (num == 198)
				{
					if (!flag44)
					{
						goto IL_1BF1;
					}
					num = 199;
				}
				if (num == 62)
				{
					goto IL_A23;
				}
				goto IL_A41;
				IL_76A:
				if (num == 110)
				{
					bool flag45;
					if (!flag45)
					{
						goto IL_573;
					}
					num = 111;
				}
				if (num == 20)
				{
					bool flag46;
					if (!flag46)
					{
						goto IL_1945;
					}
					num = 21;
				}
				if (num == 91)
				{
					this.method_0();
					num = 92;
				}
				bool flag47;
				if (num == 143)
				{
					if (!flag47)
					{
						goto IL_1BCE;
					}
					num = 144;
				}
				if (num == 36)
				{
					this.method_0();
					num = 37;
				}
				if (num == 174)
				{
					bool flag19 = this.LeftBindBTN.Text == "Bound: N7";
					num = 175;
				}
				if (num == 95)
				{
					bool flag48;
					if (!flag48)
					{
						goto IL_180A;
					}
					num = 96;
				}
				bool flag49;
				if (num == 43)
				{
					if (!flag49)
					{
						goto IL_1EB7;
					}
					num = 44;
				}
				if (num == 141)
				{
					this.method_0();
					num = 142;
				}
				if (num == 154)
				{
					bool flag27 = this.LeftBindBTN.Text == "Bound: N3";
					num = 155;
				}
				if (num == 39)
				{
					bool flag50 = this.LeftBindBTN.Text == "Bound: H";
					num = 40;
				}
				if (num == 71)
				{
					this.method_0();
					num = 72;
				}
				bool flag51;
				if (num == 53)
				{
					if (!flag51)
					{
						goto IL_1922;
					}
					num = 54;
				}
				if (num == 124)
				{
					bool flag52 = this.LeftBindBTN.Text == "Bound: Y";
					num = 125;
				}
				if (num == 194)
				{
					bool flag53 = this.LeftBindBTN.Text == "Bound: -";
					num = 195;
				}
				if (num == 157)
				{
					goto IL_9A7;
				}
				goto IL_9C5;
				IL_6D7:
				if (num == 164)
				{
					bool flag24 = this.LeftBindBTN.Text == "Bound: N5";
					num = 165;
				}
				if (num == 19)
				{
					bool flag46 = this.LeftBindBTN.Text == "Bound: D";
					num = 20;
				}
				if (num == 116)
				{
					this.method_0();
					num = 117;
				}
				if (num != 127)
				{
					goto IL_76A;
				}
				goto IL_1C14;
				IL_6A8:
				if (num == 41)
				{
					this.method_0();
					num = 42;
				}
				if (num != 137)
				{
					goto IL_6D7;
				}
				goto IL_1C37;
				IL_591:
				if (num == 144)
				{
					bool flag23 = this.LeftBindBTN.Text == "Bound: N1";
					num = 145;
				}
				if (num == 126)
				{
					this.method_0();
					num = 127;
				}
				if (num == 81)
				{
					this.method_0();
					num = 82;
				}
				if (num == 16)
				{
					this.method_0();
					num = 17;
				}
				bool flag54;
				if (num == 133)
				{
					if (!flag54)
					{
						goto IL_1C37;
					}
					num = 134;
				}
				if (num == 51)
				{
					this.method_0();
					num = 52;
				}
				if (num == 80)
				{
					bool flag55;
					if (!flag55)
					{
						goto IL_1E5B;
					}
					num = 81;
				}
				if (num == 162)
				{
					goto IL_68A;
				}
				goto IL_6A8;
				IL_566:
				if (num == 112)
				{
					goto IL_573;
				}
				goto IL_591;
				IL_52B:
				if (num == 54)
				{
					bool flag56 = this.LeftBindBTN.Text == "Bound: K";
					num = 55;
				}
				if (num == 102)
				{
					goto IL_548;
				}
				goto IL_566;
				IL_50D:
				flag44 = (Form1.GetAsyncKeyState(Keys.Add) != 0);
				num = 198;
				goto IL_52B;
				IL_49E:
				bool flag57;
				if (num == 193)
				{
					if (!flag57)
					{
						goto IL_50D;
					}
					num = 194;
				}
				bool flag58;
				if (num == 23)
				{
					if (!flag58)
					{
						goto IL_1CF7;
					}
					num = 24;
				}
				bool flag59;
				if (num == 8)
				{
					if (!flag59)
					{
						goto IL_1C5A;
					}
					num = 9;
				}
				if (num != 117)
				{
					goto IL_1474;
				}
				goto IL_18B9;
				IL_187F:
				flag59 = (Form1.GetAsyncKeyState(Keys.B) != 0);
				num = 8;
				goto IL_138C;
				IL_1828:
				if (num == 195)
				{
					bool flag53;
					if (!flag53)
					{
						goto IL_50D;
					}
					num = 196;
				}
				bool flag60;
				if (num == 88)
				{
					if (!flag60)
					{
						goto IL_18FF;
					}
					num = 89;
				}
				if (num == 3)
				{
					bool flag61;
					if (!flag61)
					{
						goto IL_187F;
					}
					num = 4;
				}
				if (num == 60)
				{
					bool flag62;
					if (!flag62)
					{
						goto IL_A23;
					}
					num = 61;
				}
				if (num == 76)
				{
					this.method_0();
					num = 77;
				}
				if (num == 67)
				{
					goto IL_19BC;
				}
				goto IL_19DA;
				IL_180A:
				flag28 = (Form1.GetAsyncKeyState(Keys.T) != 0);
				num = 98;
				goto IL_1828;
				IL_178D:
				bool flag63;
				if (num == 108)
				{
					if (!flag63)
					{
						goto IL_573;
					}
					num = 109;
				}
				bool flag64;
				if (num == 158)
				{
					if (!flag64)
					{
						goto IL_68A;
					}
					num = 159;
				}
				if (num == 204)
				{
					bool flag15 = this.LeftBindBTN.Text == "Bound: /";
					num = 205;
				}
				if (num == 97)
				{
					goto IL_180A;
				}
				goto IL_1828;
				IL_176F:
				bool flag65 = Form1.GetAsyncKeyState(Keys.H) != 0;
				num = 38;
				goto IL_178D;
				IL_1541:
				if (num == 38)
				{
					if (!flag65)
					{
						goto IL_1AE9;
					}
					num = 39;
				}
				bool flag66;
				if (num == 58)
				{
					if (!flag66)
					{
						goto IL_A23;
					}
					num = 59;
				}
				bool flag67;
				if (num == 134)
				{
					flag67 = (this.LeftBindBTN.Text == "Bound: INS");
					num = 135;
				}
				if (num == 94)
				{
					bool flag48 = this.LeftBindBTN.Text == "Bound: S";
					num = 95;
				}
				if (num == 79)
				{
					bool flag55 = this.LeftBindBTN.Text == "Bound: P";
					num = 80;
				}
				bool flag68;
				if (num == 33)
				{
					if (!flag68)
					{
						goto IL_176F;
					}
					num = 34;
				}
				bool flag69;
				if (num == 24)
				{
					flag69 = (this.LeftBindBTN.Text == "Bound: E");
					num = 25;
				}
				bool flag70;
				if (num == 123)
				{
					if (!flag70)
					{
						goto IL_1C14;
					}
					num = 124;
				}
				bool flag71;
				if (num == 44)
				{
					flag71 = (this.LeftBindBTN.Text == "Bound: I");
					num = 45;
				}
				if (num == 45)
				{
					if (!flag71)
					{
						goto IL_1EB7;
					}
					num = 46;
				}
				if (num == 49)
				{
					bool flag26 = this.LeftBindBTN.Text == "Bound: J";
					num = 50;
				}
				if (num == 56)
				{
					this.method_0();
					num = 57;
				}
				if (num == 2)
				{
					bool flag61 = Form1.GetAsyncKeyState(Keys.A) != 0;
					num = 3;
				}
				if (num != 172)
				{
					goto IL_1762;
				}
				goto IL_1D60;
				IL_1523:
				flag51 = (Form1.GetAsyncKeyState(Keys.K) != 0);
				num = 53;
				goto IL_1541;
				IL_1474:
				bool flag72;
				if (num == 203)
				{
					if (!flag72)
					{
						goto IL_FB3;
					}
					num = 204;
				}
				if (num == 104)
				{
					bool flag35 = this.LeftBindBTN.Text == "Bound: U";
					num = 105;
				}
				bool flag73;
				if (num == 68)
				{
					if (!flag73)
					{
						goto IL_BBB;
					}
					num = 69;
				}
				if (num == 189)
				{
					bool flag74 = this.LeftBindBTN.Text == "Bound: *";
					num = 190;
				}
				if (num == 52)
				{
					goto IL_1523;
				}
				goto IL_1541;
				IL_18B9:
				flag4 = (Form1.GetAsyncKeyState(Keys.X) != 0);
				num = 118;
				goto IL_1474;
				IL_1762:
				if (num == 37)
				{
					goto IL_176F;
				}
				goto IL_178D;
				IL_1D60:
				flag7 = (Form1.GetAsyncKeyState(Keys.NumPad7) != 0);
				num = 173;
				goto IL_1762;
				IL_1EEE:
				bool flag75;
				if (num == 168)
				{
					if (!flag75)
					{
						goto IL_1D60;
					}
					num = 169;
				}
				if (num == 82)
				{
					goto IL_1E5B;
				}
				goto IL_1EA5;
				IL_1EB7:
				flag12 = (Form1.GetAsyncKeyState(Keys.J) != 0);
				num = 48;
				goto IL_1EEE;
				IL_2D:
				if (num == 21)
				{
					this.method_0();
					num = 22;
				}
				if (num == 125)
				{
					bool flag52;
					if (!flag52)
					{
						goto IL_1C14;
					}
					num = 126;
				}
				if (num == 186)
				{
					this.method_0();
					num = 187;
				}
				if (num == 169)
				{
					bool flag76 = this.LeftBindBTN.Text == "Bound: N6";
					num = 170;
				}
				bool flag77;
				if (num == 163)
				{
					if (!flag77)
					{
						goto IL_1BAB;
					}
					num = 164;
				}
				if (num == 176)
				{
					this.method_0();
					num = 177;
				}
				if (num == 55)
				{
					bool flag56;
					if (!flag56)
					{
						goto IL_1922;
					}
					num = 56;
				}
				if (num == 47)
				{
					goto IL_1EB7;
				}
				goto IL_1EEE;
				IL_1C5A:
				flag32 = (Form1.GetAsyncKeyState(Keys.C) != 0);
				num = 13;
				goto IL_12F9;
				IL_1EA5:
				if (num == 159)
				{
					bool flag78 = this.LeftBindBTN.Text == "Bound: N4";
					num = 160;
				}
				if (num == 200)
				{
					bool flag25;
					if (!flag25)
					{
						goto IL_1BF1;
					}
					num = 201;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 151)
				{
					this.method_0();
					num = 152;
				}
				if (num == 86)
				{
					this.method_0();
					num = 87;
				}
				if (num == 10)
				{
					if (!flag)
					{
						goto IL_1C5A;
					}
					num = 11;
				}
				if (num == 196)
				{
					this.method_0();
					num = 197;
				}
				if (num == 32)
				{
					goto IL_1E12;
				}
				goto IL_1E49;
				IL_1E5B:
				flag39 = (Form1.GetAsyncKeyState(Keys.Q) != 0);
				num = 83;
				goto IL_1EA5;
				IL_1A91:
				if (num == 40)
				{
					bool flag50;
					if (!flag50)
					{
						goto IL_1AE9;
					}
					num = 41;
				}
				if (num == 120)
				{
					if (!flag36)
					{
						goto IL_1AC6;
					}
					num = 121;
				}
				bool flag79;
				if (num == 78)
				{
					if (!flag79)
					{
						goto IL_1E5B;
					}
					num = 79;
				}
				if (num == 206)
				{
					this.method_0();
					num = 207;
				}
				if (num == 70)
				{
					bool flag43;
					if (!flag43)
					{
						goto IL_BBB;
					}
					num = 71;
				}
				if (num == 59)
				{
					bool flag62 = this.LeftBindBTN.Text == "Bound: L";
					num = 60;
				}
				if (num != 197)
				{
					goto IL_52B;
				}
				goto IL_50D;
				IL_1A73:
				flag54 = (Form1.GetAsyncKeyState(Keys.Insert) != 0);
				num = 133;
				goto IL_1A91;
				IL_1A28:
				if (num == 160)
				{
					bool flag78;
					if (!flag78)
					{
						goto IL_68A;
					}
					num = 161;
				}
				if (num == 31)
				{
					this.method_0();
					num = 32;
				}
				if (num == 132)
				{
					goto IL_1A73;
				}
				goto IL_1A91;
				IL_1AC6:
				flag70 = (Form1.GetAsyncKeyState(Keys.Y) != 0);
				num = 123;
				goto IL_1A28;
				IL_19DA:
				bool flag80;
				if (num == 73)
				{
					if (!flag80)
					{
						goto IL_1D83;
					}
					num = 74;
				}
				if (num == 181)
				{
					this.method_0();
					num = 182;
				}
				if (num != 122)
				{
					goto IL_1A28;
				}
				goto IL_1AC6;
				IL_19BC:
				flag73 = (Form1.GetAsyncKeyState(Keys.N) != 0);
				num = 68;
				goto IL_19DA;
				IL_18DC:
				bool flag81 = Form1.GetAsyncKeyState(Keys.NumPad8) != 0;
				num = 178;
				goto IL_11A0;
				IL_18FF:
				flag37 = (Form1.GetAsyncKeyState(Keys.S) != 0);
				num = 93;
				goto IL_1133;
				IL_1076:
				flag47 = (Form1.GetAsyncKeyState(Keys.NumPad1) != 0);
				num = 143;
				goto IL_1094;
				IL_1922:
				flag66 = (Form1.GetAsyncKeyState(Keys.L) != 0);
				num = 58;
				goto IL_FDA;
				IL_FB3:
				this.LeftBindBTN.Refresh();
				num = 208;
				goto IL_FCA;
				IL_1B23:
				flag57 = (Form1.GetAsyncKeyState(Keys.Subtract) != 0);
				num = 193;
				goto IL_F55;
				IL_F08:
				flag63 = (Form1.GetAsyncKeyState(Keys.V) != 0);
				num = 108;
				goto IL_F26;
				IL_1CF7:
				flag40 = (Form1.GetAsyncKeyState(Keys.F) != 0);
				num = 28;
				goto IL_EFB;
				IL_1945:
				flag58 = (Form1.GetAsyncKeyState(Keys.E) != 0);
				num = 23;
				goto IL_EAD;
				IL_E04:
				flag60 = (Form1.GetAsyncKeyState(Keys.R) != 0);
				num = 88;
				goto IL_DD2;
				IL_D18:
				flag22 = (Form1.GetAsyncKeyState(Keys.NumPad3) != 0);
				num = 153;
				goto IL_D36;
				IL_1AE9:
				flag49 = (Form1.GetAsyncKeyState(Keys.I) != 0);
				num = 43;
				goto IL_BE9;
				IL_BBB:
				flag80 = (Form1.GetAsyncKeyState(Keys.O) != 0);
				num = 73;
				goto IL_BD9;
				IL_1BAB:
				flag75 = (Form1.GetAsyncKeyState(Keys.NumPad6) != 0);
				num = 168;
				goto IL_B70;
				IL_1BF1:
				flag72 = (Form1.GetAsyncKeyState(Keys.Divide) != 0);
				num = 203;
				goto IL_B60;
				IL_1BCE:
				flag18 = (Form1.GetAsyncKeyState(Keys.NumPad2) != 0);
				num = 148;
				goto IL_AB5;
				IL_A23:
				flag13 = (Form1.GetAsyncKeyState(Keys.M) != 0);
				num = 63;
				goto IL_A41;
				IL_9A7:
				flag64 = (Form1.GetAsyncKeyState(Keys.NumPad4) != 0);
				num = 158;
				goto IL_9C5;
				IL_1C14:
				flag34 = (Form1.GetAsyncKeyState(Keys.Z) != 0);
				num = 128;
				goto IL_76A;
				IL_1C37:
				flag42 = (Form1.GetAsyncKeyState(Keys.NumPad0) != 0);
				num = 138;
				goto IL_6D7;
				IL_68A:
				flag77 = (Form1.GetAsyncKeyState(Keys.NumPad5) != 0);
				num = 163;
				goto IL_6A8;
				IL_573:
				flag8 = (Form1.GetAsyncKeyState(Keys.W) != 0);
				num = 113;
				goto IL_591;
				IL_548:
				flag33 = (Form1.GetAsyncKeyState(Keys.U) != 0);
				num = 103;
				goto IL_566;
				IL_480:
				flag41 = (Form1.GetAsyncKeyState(Keys.Multiply) != 0);
				num = 188;
				goto IL_49E;
				IL_1DBA:
				if (num == 190)
				{
					bool flag74;
					if (!flag74)
					{
						goto IL_1B23;
					}
					num = 191;
				}
				if (num == 170)
				{
					bool flag76;
					if (!flag76)
					{
						goto IL_1D60;
					}
					num = 171;
				}
				if (num == 96)
				{
					this.method_0();
					num = 97;
				}
				if (num == 25)
				{
					if (!flag69)
					{
						goto IL_1CF7;
					}
					num = 26;
				}
				if (num == 90)
				{
					bool flag82;
					if (!flag82)
					{
						goto IL_18FF;
					}
					num = 91;
				}
				if (num == 4)
				{
					bool flag21 = this.LeftBindBTN.Text == "Bound: A";
					num = 5;
				}
				if (num == 135)
				{
					if (!flag67)
					{
						goto IL_1C37;
					}
					num = 136;
				}
				if (num == 29)
				{
					bool flag38 = this.LeftBindBTN.Text == "Bound: F";
					num = 30;
				}
				if (num == 201)
				{
					this.method_0();
					num = 202;
				}
				if (num == 187)
				{
					goto IL_480;
				}
				goto IL_49E;
				IL_1D83:
				flag79 = (Form1.GetAsyncKeyState(Keys.P) != 0);
				num = 78;
				goto IL_1DBA;
				IL_321:
				if (num == 66)
				{
					this.method_0();
					num = 67;
				}
				if (num == 77)
				{
					goto IL_1D83;
				}
				goto IL_1DBA;
				IL_1DCC:
				flag3 = (Form1.GetAsyncKeyState(Keys.D) != 0);
				num = 18;
				goto IL_321;
				IL_26F:
				if (num == 161)
				{
					this.method_0();
					num = 162;
				}
				if (num == 171)
				{
					this.method_0();
					num = 172;
				}
				if (num == 89)
				{
					bool flag82 = this.LeftBindBTN.Text == "Bound: R";
					num = 90;
				}
				if (num == 109)
				{
					bool flag45 = this.LeftBindBTN.Text == "Bound: V";
					num = 110;
				}
				if (num != 17)
				{
					goto IL_321;
				}
				goto IL_1DCC;
				IL_1DEF:
				flag9 = (Form1.GetAsyncKeyState(Keys.NumPad9) != 0);
				num = 183;
				goto IL_26F;
				IL_1E49:
				if (num == 166)
				{
					this.method_0();
					num = 167;
				}
				if (num == 179)
				{
					bool flag6 = this.LeftBindBTN.Text == "Bound: N8";
					num = 180;
				}
				if (num == 178)
				{
					if (!flag81)
					{
						goto IL_1DEF;
					}
					num = 179;
				}
				if (num != 182)
				{
					goto IL_26F;
				}
				goto IL_1DEF;
				IL_1E12:
				flag68 = (Form1.GetAsyncKeyState(Keys.G) != 0);
				num = 33;
				goto IL_1E49;
			}
		}

		// Token: 0x0600001A RID: 26 RVA: 0x00007098 File Offset: 0x00005298
		private void LeftBindBTN_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 2)
				{
					this.timer_6.Enabled = !this.timer_6.Enabled;
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
			this.LeftBindBTN.Text = (this.timer_6.Enabled ? "?" : "Bind");
		}

		// Token: 0x0600001B RID: 27 RVA: 0x00007158 File Offset: 0x00005358
		private void timer_8_Tick(object sender, EventArgs e)
		{
			int num = 0;
			for (;;)
			{
				if (num != 113)
				{
					goto IL_0E;
				}
				bool flag;
				if (flag)
				{
					num = 114;
					goto IL_0E;
				}
				goto IL_64;
				IL_19E5:
				if (num == 0)
				{
					num = 1;
				}
				if (num == 176)
				{
					break;
				}
				continue;
				IL_19C7:
				bool flag2 = Form1.GetAsyncKeyState(Keys.NumPad2) != 0;
				num = 125;
				goto IL_19E5;
				IL_177E:
				bool flag3;
				if (num == 121)
				{
					if (!flag3)
					{
						goto IL_19C7;
					}
					num = 122;
				}
				int num2;
				if (num == 15)
				{
					num2 = 1;
					num = 16;
				}
				if (num == 116)
				{
					goto IL_64;
				}
				goto IL_82;
				IL_1747:
				bool flag4 = Form1.GetAsyncKeyState(Keys.C) != 0;
				num = 17;
				goto IL_177E;
				IL_17C7:
				bool flag5;
				if (num == 109)
				{
					if (!flag5)
					{
						goto IL_A1;
					}
					num = 110;
				}
				if (num == 16)
				{
					goto IL_1747;
				}
				goto IL_177E;
				IL_1790:
				bool flag6 = Form1.GetAsyncKeyState(Keys.K) != 0;
				num = 49;
				goto IL_17C7;
				IL_0E:
				if (num == 48)
				{
					goto IL_1790;
				}
				goto IL_17C7;
				IL_BF:
				if (num != 125)
				{
					goto IL_DE;
				}
				if (flag2)
				{
					num = 126;
					goto IL_DE;
				}
				goto IL_1300;
				IL_19BA:
				if (num == 124)
				{
					goto IL_19C7;
				}
				goto IL_19E5;
				IL_1914:
				if (num == 102)
				{
					this.RightBindBTN.Text = "Bound: X";
					num = 103;
				}
				if (num == 78)
				{
					this.RightBindBTN.Text = "Bound: R";
					num = 79;
				}
				if (num == 5)
				{
					this.RightBindBTN.Text = "Bind";
					num = 6;
				}
				if (num == 148)
				{
					goto IL_199C;
				}
				goto IL_19BA;
				IL_18E9:
				if (num == 136)
				{
					goto IL_18F6;
				}
				goto IL_1914;
				IL_1876:
				if (num == 94)
				{
					this.RightBindBTN.Text = "Bound: V";
					num = 95;
				}
				if (num == 147)
				{
					num2 = 1;
					num = 148;
				}
				if (num == 52)
				{
					goto IL_18CB;
				}
				goto IL_18E9;
				IL_180D:
				if (num == 163)
				{
					num2 = 1;
					num = 164;
				}
				bool flag7;
				if (num == 173)
				{
					if (!flag7)
					{
						break;
					}
					num = 174;
				}
				if (num == 160)
				{
					goto IL_1858;
				}
				goto IL_1876;
				IL_1374:
				if (num == 151)
				{
					num2 = 1;
					num = 152;
				}
				bool flag8;
				if (num == 65)
				{
					if (!flag8)
					{
						goto IL_148F;
					}
					num = 66;
				}
				if (num == 130)
				{
					this.RightBindBTN.Text = "Bound: N3";
					num = 131;
				}
				if (num == 154)
				{
					this.RightBindBTN.Text = "Bound: N9";
					num = 155;
				}
				if (num == 118)
				{
					this.RightBindBTN.Text = "Bound: N0";
					num = 119;
				}
				bool flag9;
				if (num == 57)
				{
					if (!flag9)
					{
						goto IL_1446;
					}
					num = 58;
				}
				if (num == 56)
				{
					goto IL_17EF;
				}
				goto IL_180D;
				IL_1349:
				if (num == 140)
				{
					goto IL_1356;
				}
				goto IL_1374;
				IL_131E:
				if (num == 84)
				{
					goto IL_132B;
				}
				goto IL_1349;
				IL_12F3:
				if (num == 128)
				{
					goto IL_1300;
				}
				goto IL_131E;
				IL_1009:
				bool flag10;
				if (num == 29)
				{
					if (!flag10)
					{
						goto IL_14E4;
					}
					num = 30;
				}
				bool flag11;
				if (num == 13)
				{
					if (!flag11)
					{
						goto IL_1747;
					}
					num = 14;
				}
				if (num == 14)
				{
					this.RightBindBTN.Text = "Bound: B";
					num = 15;
				}
				if (num == 39)
				{
					num2 = 1;
					num = 40;
				}
				if (num == 88)
				{
					goto IL_11C7;
				}
				IL_11FE:
				if (num == 27)
				{
					num2 = 1;
					num = 28;
				}
				if (num == 50)
				{
					this.RightBindBTN.Text = "Bound: K";
					num = 51;
				}
				bool flag12;
				if (num == 93)
				{
					if (!flag12)
					{
						goto IL_15EC;
					}
					num = 94;
				}
				if (num == 122)
				{
					this.RightBindBTN.Text = "Bound: N1";
					num = 123;
				}
				if (num == 6)
				{
					this.timer_8.Stop();
					num = 7;
				}
				if (num == 106)
				{
					this.RightBindBTN.Text = "Bound: Y";
					num = 107;
				}
				if (num == 54)
				{
					this.RightBindBTN.Text = "Bound: L";
					num = 55;
				}
				if (num == 166)
				{
					this.RightBindBTN.Text = "Bound: +";
					num = 167;
				}
				bool flag13;
				if (num == 85)
				{
					if (!flag13)
					{
						goto IL_11C7;
					}
					num = 86;
				}
				if (num == 67)
				{
					num2 = 1;
					num = 68;
				}
				if (num == 79)
				{
					num2 = 1;
					num = 80;
				}
				if (num == 58)
				{
					this.RightBindBTN.Text = "Bound: M";
					num = 59;
				}
				if (num == 47)
				{
					num2 = 1;
					num = 48;
				}
				if (num == 82)
				{
					this.RightBindBTN.Text = "Bound: S";
					num = 83;
				}
				if (num == 164)
				{
					goto IL_12D5;
				}
				goto IL_12F3;
				IL_11C7:
				bool flag14 = Form1.GetAsyncKeyState(Keys.U) != 0;
				num = 89;
				goto IL_11FE;
				IL_E8F:
				if (num == 7)
				{
					this.timer_9.Stop();
					num = 8;
				}
				if (num == 150)
				{
					this.RightBindBTN.Text = "Bound: N8";
					num = 151;
				}
				bool flag15;
				if (num == 153)
				{
					if (!flag15)
					{
						goto IL_152D;
					}
					num = 154;
				}
				if (num == 23)
				{
					num2 = 1;
					num = 24;
				}
				if (num == 146)
				{
					this.RightBindBTN.Text = "Bound: N7";
					num = 147;
				}
				if (num == 111)
				{
					num2 = 1;
					num = 112;
				}
				bool flag16;
				if (num == 145)
				{
					if (!flag16)
					{
						goto IL_199C;
					}
					num = 146;
				}
				if (num == 127)
				{
					num2 = 1;
					num = 128;
				}
				if (num == 43)
				{
					num2 = 1;
					num = 44;
				}
				if (num == 167)
				{
					num2 = 1;
					num = 168;
				}
				if (num == 72)
				{
					goto IL_FEB;
				}
				goto IL_1009;
				IL_E1C:
				if (num == 135)
				{
					num2 = 1;
					num = 136;
				}
				if (num == 90)
				{
					this.RightBindBTN.Text = "Bound: U";
					num = 91;
				}
				if (num == 80)
				{
					goto IL_E71;
				}
				goto IL_E8F;
				IL_DF7:
				if (num == 172)
				{
					goto IL_E04;
				}
				goto IL_E1C;
				IL_D8E:
				bool flag17;
				if (num == 137)
				{
					if (!flag17)
					{
						goto IL_1356;
					}
					num = 138;
				}
				bool flag18;
				if (num == 53)
				{
					if (!flag18)
					{
						goto IL_17EF;
					}
					num = 54;
				}
				if (num == 40)
				{
					goto IL_DD9;
				}
				goto IL_DF7;
				IL_147D:
				if (num == 11)
				{
					num2 = 1;
					num = 12;
				}
				if (num == 139)
				{
					num2 = 1;
					num = 140;
				}
				if (num == 31)
				{
					num2 = 1;
					num = 32;
				}
				if (num == 59)
				{
					num2 = 1;
					num = 60;
				}
				if (num == 46)
				{
					this.RightBindBTN.Text = "Bound: J";
					num = 47;
				}
				if (num == 10)
				{
					this.RightBindBTN.Text = "Bound: A";
					num = 11;
				}
				bool flag19;
				if (num == 33)
				{
					if (!flag19)
					{
						goto IL_1576;
					}
					num = 34;
				}
				if (num == 20)
				{
					goto IL_D70;
				}
				goto IL_D8E;
				IL_C42:
				bool flag20;
				if (num == 133)
				{
					if (!flag20)
					{
						goto IL_18F6;
					}
					num = 134;
				}
				bool flag21;
				if (num == 157)
				{
					if (!flag21)
					{
						goto IL_1858;
					}
					num = 158;
				}
				if (num == 60)
				{
					goto IL_1446;
				}
				goto IL_147D;
				IL_BCF:
				if (num == 158)
				{
					this.RightBindBTN.Text = "Bound: *";
					num = 159;
				}
				if (num == 17)
				{
					if (!flag4)
					{
						goto IL_D70;
					}
					num = 18;
				}
				if (num == 104)
				{
					goto IL_C24;
				}
				goto IL_C42;
				IL_AE5:
				if (num == 155)
				{
					num2 = 1;
					num = 156;
				}
				bool flag22;
				if (num == 37)
				{
					if (!flag22)
					{
						goto IL_DD9;
					}
					num = 38;
				}
				if (num == 175)
				{
					this.timer_9.Start();
					num = 176;
				}
				bool flag23;
				if (num == 105)
				{
					if (!flag23)
					{
						goto IL_167E;
					}
					num = 106;
				}
				if (num == 143)
				{
					num2 = 1;
					num = 144;
				}
				bool flag24;
				if (num == 73)
				{
					if (!flag24)
					{
						goto IL_1635;
					}
					num = 74;
				}
				if (num == 44)
				{
					goto IL_BB1;
				}
				goto IL_BCF;
				IL_A77:
				if (num == 159)
				{
					num2 = 1;
					num = 160;
				}
				if (num == 174)
				{
					this.timer_8.Stop();
					num = 175;
				}
				if (num == 64)
				{
					goto IL_AC7;
				}
				goto IL_AE5;
				IL_14AF:
				flag15 = (Form1.GetAsyncKeyState(Keys.NumPad9) != 0);
				num = 153;
				goto IL_A77;
				IL_199C:
				bool flag25 = Form1.GetAsyncKeyState(Keys.NumPad8) != 0;
				num = 149;
				goto IL_19BA;
				IL_18F6:
				flag17 = (Form1.GetAsyncKeyState(Keys.NumPad5) != 0);
				num = 137;
				goto IL_1914;
				IL_18CB:
				flag18 = (Form1.GetAsyncKeyState(Keys.L) != 0);
				num = 53;
				goto IL_18E9;
				IL_517:
				if (num == 12)
				{
					goto IL_775;
				}
				IL_7AC:
				bool flag26;
				if (num == 169)
				{
					if (!flag26)
					{
						goto IL_E04;
					}
					num = 170;
				}
				if (num == 114)
				{
					this.RightBindBTN.Text = "Bound: INS";
					num = 115;
				}
				bool flag27;
				if (num == 117)
				{
					if (!flag27)
					{
						goto IL_90B;
					}
					num = 118;
				}
				if (num == 38)
				{
					this.RightBindBTN.Text = "Bound: H";
					num = 39;
				}
				if (num == 49)
				{
					if (!flag6)
					{
						goto IL_18CB;
					}
					num = 50;
				}
				if (num == 26)
				{
					this.RightBindBTN.Text = "Bound: E";
					num = 27;
				}
				if (num == 138)
				{
					this.RightBindBTN.Text = "Bound: N5";
					num = 139;
				}
				if (num == 71)
				{
					num2 = 1;
					num = 72;
				}
				if (num == 142)
				{
					this.RightBindBTN.Text = "Bound: N6";
					num = 143;
				}
				if (num == 123)
				{
					num2 = 1;
					num = 124;
				}
				if (num == 66)
				{
					this.RightBindBTN.Text = "Bound: O";
					num = 67;
				}
				if (num == 70)
				{
					this.RightBindBTN.Text = "Bound: P";
					num = 71;
				}
				if (num == 91)
				{
					num2 = 1;
					num = 92;
				}
				if (num == 131)
				{
					num2 = 1;
					num = 132;
				}
				bool flag28;
				if (num == 129)
				{
					if (!flag28)
					{
						goto IL_16C7;
					}
					num = 130;
				}
				bool flag29;
				if (num == 161)
				{
					if (!flag29)
					{
						goto IL_12D5;
					}
					num = 162;
				}
				if (num == 35)
				{
					num2 = 1;
					num = 36;
				}
				bool flag30;
				if (num == 9)
				{
					if (!flag30)
					{
						goto IL_775;
					}
					num = 10;
				}
				if (num == 126)
				{
					this.RightBindBTN.Text = "Bound: N2";
					num = 127;
				}
				if (num == 75)
				{
					num2 = 1;
					num = 76;
				}
				if (num == 51)
				{
					num2 = 1;
					num = 52;
				}
				if (num == 134)
				{
					this.RightBindBTN.Text = "Bound: N4";
					num = 135;
				}
				if (num == 110)
				{
					this.RightBindBTN.Text = "Bound: Z";
					num = 111;
				}
				if (num == 28)
				{
					goto IL_88D;
				}
				goto IL_8AB;
				IL_775:
				flag11 = (Form1.GetAsyncKeyState(Keys.B) != 0);
				num = 13;
				goto IL_7AC;
				IL_4F9:
				flag30 = (Form1.GetAsyncKeyState(Keys.A) != 0);
				num = 9;
				goto IL_517;
				IL_DE:
				if (num == 4)
				{
					bool flag31;
					if (!flag31)
					{
						goto IL_4F9;
					}
					num = 5;
				}
				bool flag32;
				if (num == 141)
				{
					if (!flag32)
					{
						goto IL_110;
					}
					num = 142;
				}
				bool flag33;
				if (num == 81)
				{
					if (!flag33)
					{
						goto IL_132B;
					}
					num = 82;
				}
				bool flag34;
				if (num == 21)
				{
					if (!flag34)
					{
						goto IL_9FC;
					}
					num = 22;
				}
				if (num == 98)
				{
					this.RightBindBTN.Text = "Bound: W";
					num = 99;
				}
				if (num == 107)
				{
					num2 = 1;
					num = 108;
				}
				if (num == 92)
				{
					goto IL_171A;
				}
				goto IL_1738;
				IL_15DA:
				if (num == 2)
				{
					num2 = 0;
					num = 3;
				}
				if (num == 18)
				{
					this.RightBindBTN.Text = "Bound: C";
					num = 19;
				}
				if (num == 8)
				{
					goto IL_4F9;
				}
				goto IL_517;
				IL_15A3:
				bool flag35 = Form1.GetAsyncKeyState(Keys.X) != 0;
				num = 101;
				goto IL_15DA;
				IL_1564:
				bool flag36;
				if (num == 69)
				{
					if (!flag36)
					{
						goto IL_FEB;
					}
					num = 70;
				}
				bool flag37;
				if (num == 97)
				{
					if (!flag37)
					{
						goto IL_15A3;
					}
					num = 98;
				}
				bool flag38;
				if (num == 41)
				{
					if (!flag38)
					{
						goto IL_BB1;
					}
					num = 42;
				}
				if (num == 120)
				{
					goto IL_90B;
				}
				goto IL_929;
				IL_1594:
				if (num == 156)
				{
					goto IL_152D;
				}
				goto IL_1564;
				IL_1576:
				flag22 = (Form1.GetAsyncKeyState(Keys.H) != 0);
				num = 37;
				goto IL_1594;
				IL_8AB:
				if (num == 36)
				{
					goto IL_1576;
				}
				goto IL_1594;
				IL_88D:
				flag10 = (Form1.GetAsyncKeyState(Keys.F) != 0);
				num = 29;
				goto IL_8AB;
				IL_152D:
				flag21 = (Form1.GetAsyncKeyState(Keys.Multiply) != 0);
				num = 157;
				goto IL_1564;
				IL_1858:
				flag29 = (Form1.GetAsyncKeyState(Keys.Subtract) != 0);
				num = 161;
				goto IL_1876;
				IL_17EF:
				flag9 = (Form1.GetAsyncKeyState(Keys.M) != 0);
				num = 57;
				goto IL_180D;
				IL_1356:
				flag32 = (Form1.GetAsyncKeyState(Keys.NumPad6) != 0);
				num = 141;
				goto IL_1374;
				IL_132B:
				flag13 = (Form1.GetAsyncKeyState(Keys.T) != 0);
				num = 85;
				goto IL_1349;
				IL_1300:
				flag28 = (Form1.GetAsyncKeyState(Keys.NumPad3) != 0);
				num = 129;
				goto IL_131E;
				IL_12D5:
				bool flag39 = Form1.GetAsyncKeyState(Keys.Add) != 0;
				num = 165;
				goto IL_12F3;
				IL_FEB:
				flag24 = (Form1.GetAsyncKeyState(Keys.Q) != 0);
				num = 73;
				goto IL_1009;
				IL_E71:
				flag33 = (Form1.GetAsyncKeyState(Keys.S) != 0);
				num = 81;
				goto IL_E8F;
				IL_16B5:
				bool flag40;
				if (num == 77)
				{
					if (!flag40)
					{
						goto IL_E71;
					}
					num = 78;
				}
				if (num == 86)
				{
					this.RightBindBTN.Text = "Bound: T";
					num = 87;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 19)
				{
					num2 = 1;
					num = 20;
				}
				if (num == 22)
				{
					this.RightBindBTN.Text = "Bound: D";
					num = 23;
				}
				if (num == 99)
				{
					num2 = 1;
					num = 100;
				}
				if (num == 62)
				{
					this.RightBindBTN.Text = "Bound: N";
					num = 63;
				}
				if (num == 76)
				{
					goto IL_1635;
				}
				goto IL_166C;
				IL_167E:
				flag5 = (Form1.GetAsyncKeyState(Keys.Z) != 0);
				num = 109;
				goto IL_16B5;
				IL_1708:
				if (num == 162)
				{
					this.RightBindBTN.Text = "Bound: -";
					num = 163;
				}
				if (num == 108)
				{
					goto IL_167E;
				}
				goto IL_16B5;
				IL_1738:
				if (num == 132)
				{
					goto IL_16C7;
				}
				goto IL_1708;
				IL_171A:
				flag12 = (Form1.GetAsyncKeyState(Keys.V) != 0);
				num = 93;
				goto IL_1738;
				IL_16C7:
				flag20 = (Form1.GetAsyncKeyState(Keys.NumPad4) != 0);
				num = 133;
				goto IL_1708;
				IL_E04:
				flag7 = (num2 == 1);
				num = 173;
				goto IL_E1C;
				IL_DD9:
				flag38 = (Form1.GetAsyncKeyState(Keys.I) != 0);
				num = 41;
				goto IL_DF7;
				IL_D70:
				flag34 = (Form1.GetAsyncKeyState(Keys.D) != 0);
				num = 21;
				goto IL_D8E;
				IL_1446:
				bool flag41 = Form1.GetAsyncKeyState(Keys.N) != 0;
				num = 61;
				goto IL_147D;
				IL_C24:
				flag23 = (Form1.GetAsyncKeyState(Keys.Y) != 0);
				num = 105;
				goto IL_C42;
				IL_166C:
				if (num == 101)
				{
					if (!flag35)
					{
						goto IL_C24;
					}
					num = 102;
				}
				if (num == 103)
				{
					num2 = 1;
					num = 104;
				}
				if (num == 61)
				{
					if (!flag41)
					{
						goto IL_AC7;
					}
					num = 62;
				}
				if (num == 119)
				{
					num2 = 1;
					num = 120;
				}
				if (num == 55)
				{
					num2 = 1;
					num = 56;
				}
				if (num == 165)
				{
					if (!flag39)
					{
						goto IL_936;
					}
					num = 166;
				}
				if (num == 96)
				{
					goto IL_15EC;
				}
				goto IL_1623;
				IL_1635:
				flag40 = (Form1.GetAsyncKeyState(Keys.R) != 0);
				num = 77;
				goto IL_166C;
				IL_BB1:
				bool flag42 = Form1.GetAsyncKeyState(Keys.J) != 0;
				num = 45;
				goto IL_BCF;
				IL_AC7:
				flag8 = (Form1.GetAsyncKeyState(Keys.O) != 0);
				num = 65;
				goto IL_AE5;
				IL_14D2:
				if (num == 152)
				{
					goto IL_14AF;
				}
				goto IL_A77;
				IL_148F:
				flag36 = (Form1.GetAsyncKeyState(Keys.P) != 0);
				num = 69;
				goto IL_14D2;
				IL_A1A:
				if (num == 42)
				{
					this.RightBindBTN.Text = "Bound: I";
					num = 43;
				}
				if (num == 95)
				{
					num2 = 1;
					num = 96;
				}
				if (num == 68)
				{
					goto IL_148F;
				}
				goto IL_14D2;
				IL_9FC:
				bool flag43 = Form1.GetAsyncKeyState(Keys.E) != 0;
				num = 25;
				goto IL_A1A;
				IL_151B:
				if (num == 171)
				{
					num2 = 1;
					num = 172;
				}
				if (num == 87)
				{
					num2 = 1;
					num = 88;
				}
				if (num == 63)
				{
					num2 = 1;
					num = 64;
				}
				if (num == 115)
				{
					num2 = 1;
					num = 116;
				}
				if (num == 24)
				{
					goto IL_9FC;
				}
				goto IL_A1A;
				IL_14E4:
				flag19 = (Form1.GetAsyncKeyState(Keys.G) != 0);
				num = 33;
				goto IL_151B;
				IL_954:
				if (num == 30)
				{
					this.RightBindBTN.Text = "Bound: F";
					num = 31;
				}
				if (num == 32)
				{
					goto IL_14E4;
				}
				goto IL_151B;
				IL_936:
				flag26 = (Form1.GetAsyncKeyState(Keys.Divide) != 0);
				num = 169;
				goto IL_954;
				IL_929:
				if (num == 168)
				{
					goto IL_936;
				}
				goto IL_954;
				IL_90B:
				flag3 = (Form1.GetAsyncKeyState(Keys.NumPad1) != 0);
				num = 121;
				goto IL_929;
				IL_1623:
				if (num == 45)
				{
					if (!flag42)
					{
						goto IL_1790;
					}
					num = 46;
				}
				if (num == 25)
				{
					if (!flag43)
					{
						goto IL_88D;
					}
					num = 26;
				}
				if (num == 149)
				{
					if (!flag25)
					{
						goto IL_14AF;
					}
					num = 150;
				}
				if (num == 170)
				{
					this.RightBindBTN.Text = "Bound: /";
					num = 171;
				}
				if (num == 3)
				{
					bool flag31 = Form1.GetAsyncKeyState(Keys.Escape) != 0;
					num = 4;
				}
				if (num == 34)
				{
					this.RightBindBTN.Text = "Bound: G";
					num = 35;
				}
				if (num == 83)
				{
					num2 = 1;
					num = 84;
				}
				if (num == 89)
				{
					if (!flag14)
					{
						goto IL_171A;
					}
					num = 90;
				}
				if (num == 100)
				{
					goto IL_15A3;
				}
				goto IL_15DA;
				IL_15EC:
				flag37 = (Form1.GetAsyncKeyState(Keys.W) != 0);
				num = 97;
				goto IL_1623;
				IL_A1:
				flag = (Form1.GetAsyncKeyState(Keys.Insert) != 0);
				num = 113;
				goto IL_BF;
				IL_151:
				if (num == 74)
				{
					this.RightBindBTN.Text = "Bound: Q";
					num = 75;
				}
				if (num == 112)
				{
					goto IL_A1;
				}
				goto IL_BF;
				IL_110:
				flag16 = (Form1.GetAsyncKeyState(Keys.NumPad7) != 0);
				num = 145;
				goto IL_151;
				IL_82:
				if (num == 144)
				{
					goto IL_110;
				}
				goto IL_151;
				IL_64:
				flag27 = (Form1.GetAsyncKeyState(Keys.NumPad0) != 0);
				num = 117;
				goto IL_82;
			}
		}

		// Token: 0x0600001C RID: 28 RVA: 0x00008B98 File Offset: 0x00006D98
		private void RightBindBTN_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 2)
				{
					this.timer_8.Enabled = !this.timer_8.Enabled;
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
			this.RightBindBTN.Text = (this.timer_8.Enabled ? "?" : "Bind");
		}

		// Token: 0x0600001D RID: 29 RVA: 0x00008C58 File Offset: 0x00006E58
		private void timer_9_Tick(object sender, EventArgs e)
		{
			int num = 0;
			for (;;)
			{
				if (num == 42)
				{
					goto IL_1F36;
				}
				IL_1F6D:
				if (num != 190)
				{
					goto IL_0E;
				}
				bool flag;
				if (flag)
				{
					num = 191;
					goto IL_0E;
				}
				goto IL_1C72;
				IL_AF1:
				bool flag2;
				if (num == 134)
				{
					flag2 = (this.RightBindBTN.Text == "Bound: INS");
					num = 135;
				}
				if (num == 75)
				{
					bool flag3;
					if (!flag3)
					{
						goto IL_1B83;
					}
					num = 76;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num != 208)
				{
					continue;
				}
				break;
				IL_A71:
				bool flag4;
				if (num == 154)
				{
					flag4 = (this.RightBindBTN.Text == "Bound: N3");
					num = 155;
				}
				if (num == 65)
				{
					bool flag5;
					if (!flag5)
					{
						goto IL_1A20;
					}
					num = 66;
				}
				if (num == 81)
				{
					this.method_1();
					num = 82;
				}
				if (num != 97)
				{
					goto IL_AF1;
				}
				goto IL_1C95;
				IL_A61:
				if (num != 192)
				{
					goto IL_A71;
				}
				goto IL_1C72;
				IL_A43:
				bool flag6 = Form1.GetAsyncKeyState(Keys.V) != 0;
				num = 108;
				goto IL_A61;
				IL_95A:
				if (num == 105)
				{
					bool flag7;
					if (!flag7)
					{
						goto IL_A43;
					}
					num = 106;
				}
				bool flag8;
				if (num == 34)
				{
					flag8 = (this.RightBindBTN.Text == "Bound: G");
					num = 35;
				}
				bool flag9;
				if (num == 44)
				{
					flag9 = (this.RightBindBTN.Text == "Bound: I");
					num = 45;
				}
				bool flag10;
				if (num == 129)
				{
					flag10 = (this.RightBindBTN.Text == "Bound: Z");
					num = 130;
				}
				bool flag11;
				if (num == 113)
				{
					if (!flag11)
					{
						goto IL_1925;
					}
					num = 114;
				}
				if (num != 177)
				{
					goto IL_18D3;
				}
				goto IL_18B5;
				IL_70B:
				bool flag12;
				if (num == 159)
				{
					flag12 = (this.RightBindBTN.Text == "Bound: N4");
					num = 160;
				}
				bool flag13;
				if (num == 38)
				{
					if (!flag13)
					{
						goto IL_1F36;
					}
					num = 39;
				}
				if (num == 170)
				{
					bool flag14;
					if (!flag14)
					{
						goto IL_196B;
					}
					num = 171;
				}
				bool flag15;
				if (num == 119)
				{
					flag15 = (this.RightBindBTN.Text == "Bound: X");
					num = 120;
				}
				bool flag16;
				if (num == 139)
				{
					flag16 = (this.RightBindBTN.Text == "Bound: N0");
					num = 140;
				}
				if (num == 200)
				{
					bool flag17;
					if (!flag17)
					{
						goto IL_1F06;
					}
					num = 201;
				}
				bool flag18;
				if (num == 99)
				{
					flag18 = (this.RightBindBTN.Text == "Bound: T");
					num = 100;
				}
				if (num == 10)
				{
					bool flag19;
					if (!flag19)
					{
						goto IL_1DBC;
					}
					num = 11;
				}
				if (num == 80)
				{
					bool flag20;
					if (!flag20)
					{
						goto IL_1B47;
					}
					num = 81;
				}
				bool flag21;
				if (num == 188)
				{
					if (!flag21)
					{
						goto IL_1C72;
					}
					num = 189;
				}
				if (num == 151)
				{
					this.method_1();
					num = 152;
				}
				bool flag22;
				if (num == 39)
				{
					flag22 = (this.RightBindBTN.Text == "Bound: H");
					num = 40;
				}
				if (num == 35)
				{
					if (!flag8)
					{
						goto IL_1CB8;
					}
					num = 36;
				}
				if (num == 89)
				{
					bool flag23 = this.RightBindBTN.Text == "Bound: R";
					num = 90;
				}
				if (num == 167)
				{
					goto IL_93C;
				}
				goto IL_95A;
				IL_4D9:
				if (num == 40)
				{
					if (!flag22)
					{
						goto IL_1F36;
					}
					num = 41;
				}
				if (num == 161)
				{
					this.method_1();
					num = 162;
				}
				if (num == 116)
				{
					this.method_1();
					num = 117;
				}
				if (num == 136)
				{
					this.method_1();
					num = 137;
				}
				if (num == 156)
				{
					this.method_1();
					num = 157;
				}
				if (num == 150)
				{
					bool flag24;
					if (!flag24)
					{
						goto IL_17BE;
					}
					num = 151;
				}
				if (num == 5)
				{
					bool flag25;
					if (!flag25)
					{
						goto IL_1833;
					}
					num = 6;
				}
				bool flag26;
				if (num == 174)
				{
					flag26 = (this.RightBindBTN.Text == "Bound: N7");
					num = 175;
				}
				bool flag27;
				if (num == 93)
				{
					if (!flag27)
					{
						goto IL_1C95;
					}
					num = 94;
				}
				if (num == 100)
				{
					if (!flag18)
					{
						goto IL_17E9;
					}
					num = 101;
				}
				bool flag28;
				if (num == 68)
				{
					if (!flag28)
					{
						goto IL_1892;
					}
					num = 69;
				}
				if (num == 126)
				{
					this.method_1();
					num = 127;
				}
				if (num == 140)
				{
					if (!flag16)
					{
						goto IL_126D;
					}
					num = 141;
				}
				bool flag29;
				if (num == 14)
				{
					flag29 = (this.RightBindBTN.Text == "Bound: C");
					num = 15;
				}
				bool flag30;
				if (num == 178)
				{
					if (!flag30)
					{
						goto IL_1A7D;
					}
					num = 179;
				}
				if (num == 121)
				{
					this.method_1();
					num = 122;
				}
				if (num == 162)
				{
					goto IL_6ED;
				}
				goto IL_70B;
				IL_1356:
				if (num == 195)
				{
					bool flag31;
					if (!flag31)
					{
						goto IL_12E9;
					}
					num = 196;
				}
				bool flag32;
				if (num == 143)
				{
					if (!flag32)
					{
						goto IL_1948;
					}
					num = 144;
				}
				bool flag33;
				if (num == 203)
				{
					if (!flag33)
					{
						goto IL_1612;
					}
					num = 204;
				}
				if (num == 90)
				{
					bool flag23;
					if (!flag23)
					{
						goto IL_1E61;
					}
					num = 91;
				}
				if (num == 66)
				{
					this.method_1();
					num = 67;
				}
				if (num == 189)
				{
					flag = (this.RightBindBTN.Text == "Bound: *");
					num = 190;
				}
				bool flag34;
				if (num == 63)
				{
					if (!flag34)
					{
						goto IL_1A20;
					}
					num = 64;
				}
				if (num != 122)
				{
					goto IL_4D9;
				}
				goto IL_1D76;
				IL_1338:
				bool flag35 = Form1.GetAsyncKeyState(Keys.K) != 0;
				num = 53;
				goto IL_1356;
				IL_1307:
				bool flag36;
				if (num == 48)
				{
					if (!flag36)
					{
						goto IL_1338;
					}
					num = 49;
				}
				if (num != 132)
				{
					goto IL_168F;
				}
				goto IL_1671;
				IL_128B:
				bool flag37;
				if (num == 58)
				{
					if (!flag37)
					{
						goto IL_1EAA;
					}
					num = 59;
				}
				if (num == 144)
				{
					bool flag38 = this.RightBindBTN.Text == "Bound: N1";
					num = 145;
				}
				if (num == 197)
				{
					goto IL_12E9;
				}
				goto IL_1307;
				IL_11A6:
				if (num == 41)
				{
					this.method_1();
					num = 42;
				}
				if (num == 165)
				{
					bool flag39;
					if (!flag39)
					{
						goto IL_93C;
					}
					num = 166;
				}
				if (num == 171)
				{
					this.method_1();
					num = 172;
				}
				if (num == 205)
				{
					bool flag40;
					if (!flag40)
					{
						goto IL_1612;
					}
					num = 206;
				}
				bool flag41;
				if (num == 148)
				{
					if (!flag41)
					{
						goto IL_17BE;
					}
					num = 149;
				}
				bool flag42;
				if (num == 168)
				{
					if (!flag42)
					{
						goto IL_196B;
					}
					num = 169;
				}
				if (num == 142)
				{
					goto IL_126D;
				}
				goto IL_128B;
				IL_1196:
				if (num != 82)
				{
					goto IL_11A6;
				}
				goto IL_1B47;
				IL_10EB:
				if (num == 30)
				{
					bool flag43;
					if (!flag43)
					{
						goto IL_1519;
					}
					num = 31;
				}
				if (num == 3)
				{
					bool flag44;
					if (!flag44)
					{
						goto IL_1833;
					}
					num = 4;
				}
				bool flag45;
				if (num == 133)
				{
					if (!flag45)
					{
						goto IL_1E18;
					}
					num = 134;
				}
				if (num == 175)
				{
					if (!flag26)
					{
						goto IL_18B5;
					}
					num = 176;
				}
				bool flag46;
				if (num == 198)
				{
					if (!flag46)
					{
						goto IL_1F06;
					}
					num = 199;
				}
				if (num != 27)
				{
					goto IL_1196;
				}
				goto IL_1CFE;
				IL_B41:
				if (num == 20)
				{
					bool flag47;
					if (!flag47)
					{
						goto IL_B72;
					}
					num = 21;
				}
				if (num != 182)
				{
					goto IL_10EB;
				}
				goto IL_1A7D;
				IL_F03:
				if (num == 180)
				{
					bool flag48;
					if (!flag48)
					{
						goto IL_1A7D;
					}
					num = 181;
				}
				if (num == 11)
				{
					this.method_1();
					num = 12;
				}
				if (num == 135)
				{
					if (!flag2)
					{
						goto IL_1E18;
					}
					num = 136;
				}
				bool flag49;
				if (num == 83)
				{
					if (!flag49)
					{
						goto IL_1D99;
					}
					num = 84;
				}
				if (num == 61)
				{
					this.method_1();
					num = 62;
				}
				if (num == 206)
				{
					this.method_1();
					num = 207;
				}
				bool flag50;
				if (num == 173)
				{
					if (!flag50)
					{
						goto IL_18B5;
					}
					num = 174;
				}
				if (num == 21)
				{
					this.method_1();
					num = 22;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				bool flag51;
				if (num == 13)
				{
					if (!flag51)
					{
						goto IL_1CDB;
					}
					num = 14;
				}
				bool flag52;
				if (num == 124)
				{
					flag52 = (this.RightBindBTN.Text == "Bound: Y");
					num = 125;
				}
				bool flag53;
				if (num == 18)
				{
					if (!flag53)
					{
						goto IL_B72;
					}
					num = 19;
				}
				if (num == 77)
				{
					goto IL_1B83;
				}
				goto IL_B41;
				IL_DC9:
				if (num == 104)
				{
					bool flag7 = this.RightBindBTN.Text == "Bound: U";
					num = 105;
				}
				if (num == 64)
				{
					bool flag5 = this.RightBindBTN.Text == "Bound: M";
					num = 65;
				}
				bool flag54;
				if (num == 138)
				{
					if (!flag54)
					{
						goto IL_126D;
					}
					num = 139;
				}
				bool flag55;
				if (num == 128)
				{
					if (!flag55)
					{
						goto IL_1671;
					}
					num = 129;
				}
				if (num == 141)
				{
					this.method_1();
					num = 142;
				}
				if (num == 176)
				{
					this.method_1();
					num = 177;
				}
				if (num == 160)
				{
					if (!flag12)
					{
						goto IL_6ED;
					}
					num = 161;
				}
				if (num == 2)
				{
					bool flag44 = Form1.GetAsyncKeyState(Keys.A) != 0;
					num = 3;
				}
				if (num != 72)
				{
					goto IL_F03;
				}
				goto IL_1892;
				IL_18D3:
				if (num == 201)
				{
					this.method_1();
					num = 202;
				}
				if (num == 157)
				{
					goto IL_1902;
				}
				goto IL_DC9;
				IL_1C95:
				bool flag56 = Form1.GetAsyncKeyState(Keys.T) != 0;
				num = 98;
				goto IL_AF1;
				IL_1E4F:
				if (num == 145)
				{
					bool flag38;
					if (!flag38)
					{
						goto IL_1948;
					}
					num = 146;
				}
				if (num == 56)
				{
					this.method_1();
					num = 57;
				}
				if (num == 6)
				{
					this.method_1();
					num = 7;
				}
				if (num == 95)
				{
					bool flag57;
					if (!flag57)
					{
						goto IL_1C95;
					}
					num = 96;
				}
				bool flag58;
				if (num == 33)
				{
					if (!flag58)
					{
						goto IL_1CB8;
					}
					num = 34;
				}
				if (num == 55)
				{
					bool flag59;
					if (!flag59)
					{
						goto IL_1A43;
					}
					num = 56;
				}
				if (num == 85)
				{
					bool flag60;
					if (!flag60)
					{
						goto IL_1D99;
					}
					num = 86;
				}
				if (num == 45)
				{
					if (!flag9)
					{
						goto IL_156F;
					}
					num = 46;
				}
				if (num == 111)
				{
					this.method_1();
					num = 112;
				}
				if (num == 179)
				{
					bool flag48 = this.RightBindBTN.Text == "Bound: N8";
					num = 180;
				}
				if (num == 12)
				{
					goto IL_1DBC;
				}
				goto IL_1E06;
				IL_1E18:
				flag54 = (Form1.GetAsyncKeyState(Keys.NumPad0) != 0);
				num = 138;
				goto IL_1E4F;
				IL_1E98:
				bool flag61;
				if (num == 28)
				{
					if (!flag61)
					{
						goto IL_1519;
					}
					num = 29;
				}
				if (num == 137)
				{
					goto IL_1E18;
				}
				goto IL_1E4F;
				IL_1E61:
				flag27 = (Form1.GetAsyncKeyState(Keys.S) != 0);
				num = 93;
				goto IL_1E98;
				IL_1EF4:
				if (num == 84)
				{
					bool flag60 = this.RightBindBTN.Text == "Bound: Q";
					num = 85;
				}
				if (num == 108)
				{
					if (!flag6)
					{
						goto IL_14BF;
					}
					num = 109;
				}
				bool flag62;
				if (num == 73)
				{
					if (!flag62)
					{
						goto IL_1B83;
					}
					num = 74;
				}
				if (num == 185)
				{
					bool flag63;
					if (!flag63)
					{
						goto IL_186F;
					}
					num = 186;
				}
				if (num == 4)
				{
					bool flag25 = this.RightBindBTN.Text == "Bound: A";
					num = 5;
				}
				if (num == 15)
				{
					if (!flag29)
					{
						goto IL_1CDB;
					}
					num = 16;
				}
				if (num == 16)
				{
					this.method_1();
					num = 17;
				}
				if (num == 25)
				{
					bool flag64;
					if (!flag64)
					{
						goto IL_1CFE;
					}
					num = 26;
				}
				if (num == 92)
				{
					goto IL_1E61;
				}
				goto IL_1E98;
				IL_1F24:
				if (num == 62)
				{
					goto IL_1EAA;
				}
				goto IL_1EF4;
				IL_1F06:
				flag33 = (Form1.GetAsyncKeyState(Keys.Divide) != 0);
				num = 203;
				goto IL_1F24;
				IL_0E:
				if (num == 202)
				{
					goto IL_1F06;
				}
				goto IL_1F24;
				IL_1EAA:
				flag34 = (Form1.GetAsyncKeyState(Keys.M) != 0);
				num = 63;
				goto IL_1EF4;
				IL_B90:
				if (num == 60)
				{
					bool flag65;
					if (!flag65)
					{
						goto IL_1EAA;
					}
					num = 61;
				}
				if (num == 29)
				{
					bool flag43 = this.RightBindBTN.Text == "Bound: F";
					num = 30;
				}
				if (num == 59)
				{
					bool flag65 = this.RightBindBTN.Text == "Bound: L";
					num = 60;
				}
				if (num != 57)
				{
					goto IL_C14;
				}
				goto IL_1A43;
				IL_1851:
				bool flag66;
				if (num == 193)
				{
					if (!flag66)
					{
						goto IL_12E9;
					}
					num = 194;
				}
				if (num == 131)
				{
					this.method_1();
					num = 132;
				}
				if (num != 22)
				{
					goto IL_B90;
				}
				goto IL_B72;
				IL_1807:
				if (num == 51)
				{
					this.method_1();
					num = 52;
				}
				if (num == 7)
				{
					goto IL_1833;
				}
				goto IL_1851;
				IL_17DC:
				if (num == 102)
				{
					goto IL_17E9;
				}
				goto IL_1807;
				IL_169F:
				if (num == 146)
				{
					this.method_1();
					num = 147;
				}
				if (num == 24)
				{
					bool flag64 = this.RightBindBTN.Text == "Bound: E";
					num = 25;
				}
				if (num == 199)
				{
					bool flag17 = this.RightBindBTN.Text == "Bound: +";
					num = 200;
				}
				if (num == 53)
				{
					if (!flag35)
					{
						goto IL_1A43;
					}
					num = 54;
				}
				if (num == 79)
				{
					bool flag20 = this.RightBindBTN.Text == "Bound: P";
					num = 80;
				}
				if (num == 91)
				{
					this.method_1();
					num = 92;
				}
				if (num == 70)
				{
					bool flag67;
					if (!flag67)
					{
						goto IL_1892;
					}
					num = 71;
				}
				if (num == 152)
				{
					goto IL_17BE;
				}
				goto IL_17DC;
				IL_168F:
				if (num != 37)
				{
					goto IL_169F;
				}
				goto IL_1CB8;
				IL_B72:
				bool flag68 = Form1.GetAsyncKeyState(Keys.E) != 0;
				num = 23;
				goto IL_B90;
				IL_1C72:
				flag66 = (Form1.GetAsyncKeyState(Keys.Subtract) != 0);
				num = 193;
				goto IL_A71;
				IL_1537:
				if (num == 98)
				{
					if (!flag56)
					{
						goto IL_17E9;
					}
					num = 99;
				}
				bool flag69;
				if (num == 43)
				{
					if (!flag69)
					{
						goto IL_156F;
					}
					num = 44;
				}
				if (num == 130)
				{
					if (!flag10)
					{
						goto IL_1671;
					}
					num = 131;
				}
				if (num == 50)
				{
					bool flag70;
					if (!flag70)
					{
						goto IL_1338;
					}
					num = 51;
				}
				if (num == 191)
				{
					this.method_1();
					num = 192;
				}
				if (num == 36)
				{
					this.method_1();
					num = 37;
				}
				if (num == 194)
				{
					bool flag31 = this.RightBindBTN.Text == "Bound: -";
					num = 195;
				}
				if (num == 186)
				{
					this.method_1();
					num = 187;
				}
				bool flag71;
				if (num == 88)
				{
					if (!flag71)
					{
						goto IL_1E61;
					}
					num = 89;
				}
				if (num == 107)
				{
					goto IL_A43;
				}
				goto IL_A61;
				IL_1519:
				flag58 = (Form1.GetAsyncKeyState(Keys.G) != 0);
				num = 33;
				goto IL_1537;
				IL_14ED:
				bool flag72;
				if (num == 103)
				{
					if (!flag72)
					{
						goto IL_A43;
					}
					num = 104;
				}
				if (num == 32)
				{
					goto IL_1519;
				}
				goto IL_1537;
				IL_186F:
				flag21 = (Form1.GetAsyncKeyState(Keys.Multiply) != 0);
				num = 188;
				goto IL_14ED;
				IL_1034:
				if (num == 184)
				{
					bool flag63 = this.RightBindBTN.Text == "Bound: N9";
					num = 185;
				}
				bool flag73;
				if (num == 158)
				{
					if (!flag73)
					{
						goto IL_6ED;
					}
					num = 159;
				}
				bool flag74;
				if (num == 183)
				{
					if (!flag74)
					{
						goto IL_186F;
					}
					num = 184;
				}
				if (num == 169)
				{
					bool flag14 = this.RightBindBTN.Text == "Bound: N6";
					num = 170;
				}
				if (num != 47)
				{
					goto IL_158D;
				}
				goto IL_156F;
				IL_1CDB:
				flag53 = (Form1.GetAsyncKeyState(Keys.D) != 0);
				num = 18;
				goto IL_1034;
				IL_374:
				bool flag75;
				if (num == 118)
				{
					if (!flag75)
					{
						goto IL_1D76;
					}
					num = 119;
				}
				if (num == 120)
				{
					if (!flag15)
					{
						goto IL_1D76;
					}
					num = 121;
				}
				if (num == 74)
				{
					bool flag3 = this.RightBindBTN.Text == "Bound: O";
					num = 75;
				}
				if (num == 94)
				{
					bool flag57 = this.RightBindBTN.Text == "Bound: S";
					num = 95;
				}
				if (num == 101)
				{
					this.method_1();
					num = 102;
				}
				if (num == 19)
				{
					bool flag47 = this.RightBindBTN.Text == "Bound: D";
					num = 20;
				}
				if (num == 31)
				{
					this.method_1();
					num = 32;
				}
				if (num == 125)
				{
					if (!flag52)
					{
						goto IL_1D53;
					}
					num = 126;
				}
				if (num == 26)
				{
					this.method_1();
					num = 27;
				}
				if (num != 17)
				{
					goto IL_1034;
				}
				goto IL_1CDB;
				IL_1D99:
				flag71 = (Form1.GetAsyncKeyState(Keys.R) != 0);
				num = 88;
				goto IL_374;
				IL_1E06:
				if (num == 54)
				{
					bool flag59 = this.RightBindBTN.Text == "Bound: K";
					num = 55;
				}
				if (num == 49)
				{
					bool flag70 = this.RightBindBTN.Text == "Bound: J";
					num = 50;
				}
				if (num == 76)
				{
					this.method_1();
					num = 77;
				}
				bool flag76;
				if (num == 78)
				{
					if (!flag76)
					{
						goto IL_1B47;
					}
					num = 79;
				}
				bool flag77;
				if (num == 153)
				{
					if (!flag77)
					{
						goto IL_1902;
					}
					num = 154;
				}
				bool flag78;
				if (num == 8)
				{
					if (!flag78)
					{
						goto IL_1DBC;
					}
					num = 9;
				}
				if (num == 23)
				{
					if (!flag68)
					{
						goto IL_1CFE;
					}
					num = 24;
				}
				bool flag79;
				if (num == 123)
				{
					if (!flag79)
					{
						goto IL_1D53;
					}
					num = 124;
				}
				if (num != 87)
				{
					goto IL_374;
				}
				goto IL_1D99;
				IL_1DBC:
				flag51 = (Form1.GetAsyncKeyState(Keys.C) != 0);
				num = 13;
				goto IL_1E06;
				IL_14DD:
				if (num != 187)
				{
					goto IL_14ED;
				}
				goto IL_186F;
				IL_14BF:
				flag11 = (Form1.GetAsyncKeyState(Keys.W) != 0);
				num = 113;
				goto IL_14DD;
				IL_13AF:
				bool flag80;
				if (num == 114)
				{
					flag80 = (this.RightBindBTN.Text == "Bound: W");
					num = 115;
				}
				if (num == 110)
				{
					bool flag81;
					if (!flag81)
					{
						goto IL_14BF;
					}
					num = 111;
				}
				if (num == 204)
				{
					bool flag40 = this.RightBindBTN.Text == "Bound: /";
					num = 205;
				}
				if (num == 71)
				{
					this.method_1();
					num = 72;
				}
				if (num == 115)
				{
					if (!flag80)
					{
						goto IL_1925;
					}
					num = 116;
				}
				if (num == 149)
				{
					bool flag24 = this.RightBindBTN.Text == "Bound: N2";
					num = 150;
				}
				if (num != 127)
				{
					goto IL_14B2;
				}
				goto IL_1D53;
				IL_1612:
				this.RightBindBTN.Refresh();
				num = 208;
				goto IL_13AF;
				IL_D44:
				if (num == 155)
				{
					if (!flag4)
					{
						goto IL_1902;
					}
					num = 156;
				}
				if (num == 109)
				{
					bool flag81 = this.RightBindBTN.Text == "Bound: V";
					num = 110;
				}
				if (num == 181)
				{
					this.method_1();
					num = 182;
				}
				if (num != 207)
				{
					goto IL_13AF;
				}
				goto IL_1612;
				IL_1925:
				flag75 = (Form1.GetAsyncKeyState(Keys.X) != 0);
				num = 118;
				goto IL_D44;
				IL_D34:
				if (num != 117)
				{
					goto IL_D44;
				}
				goto IL_1925;
				IL_1948:
				flag41 = (Form1.GetAsyncKeyState(Keys.NumPad2) != 0);
				num = 148;
				goto IL_D34;
				IL_CD3:
				if (num == 69)
				{
					bool flag67 = this.RightBindBTN.Text == "Bound: N";
					num = 70;
				}
				bool flag82;
				if (num == 163)
				{
					if (!flag82)
					{
						goto IL_93C;
					}
					num = 164;
				}
				if (num != 147)
				{
					goto IL_D34;
				}
				goto IL_1948;
				IL_196B:
				flag50 = (Form1.GetAsyncKeyState(Keys.NumPad7) != 0);
				num = 173;
				goto IL_CD3;
				IL_C53:
				if (num == 96)
				{
					this.method_1();
					num = 97;
				}
				if (num == 196)
				{
					this.method_1();
					num = 197;
				}
				if (num == 9)
				{
					bool flag19 = this.RightBindBTN.Text == "Bound: B";
					num = 10;
				}
				if (num != 172)
				{
					goto IL_CD3;
				}
				goto IL_196B;
				IL_1A20:
				flag28 = (Form1.GetAsyncKeyState(Keys.N) != 0);
				num = 68;
				goto IL_C53;
				IL_C14:
				if (num == 166)
				{
					this.method_1();
					num = 167;
				}
				if (num == 106)
				{
					this.method_1();
					num = 107;
				}
				if (num != 67)
				{
					goto IL_C53;
				}
				goto IL_1A20;
				IL_1A43:
				flag37 = (Form1.GetAsyncKeyState(Keys.L) != 0);
				num = 58;
				goto IL_C14;
				IL_14B2:
				if (num == 112)
				{
					goto IL_14BF;
				}
				goto IL_14DD;
				IL_1D53:
				flag55 = (Form1.GetAsyncKeyState(Keys.Z) != 0);
				num = 128;
				goto IL_14B2;
				IL_93C:
				flag42 = (Form1.GetAsyncKeyState(Keys.NumPad6) != 0);
				num = 168;
				goto IL_95A;
				IL_6ED:
				flag82 = (Form1.GetAsyncKeyState(Keys.NumPad5) != 0);
				num = 163;
				goto IL_70B;
				IL_1D76:
				flag79 = (Form1.GetAsyncKeyState(Keys.Y) != 0);
				num = 123;
				goto IL_4D9;
				IL_158D:
				if (num == 46)
				{
					this.method_1();
					num = 47;
				}
				if (num == 164)
				{
					bool flag39 = this.RightBindBTN.Text == "Bound: N5";
					num = 165;
				}
				if (num == 86)
				{
					this.method_1();
					num = 87;
				}
				if (num != 52)
				{
					goto IL_1356;
				}
				goto IL_1338;
				IL_156F:
				flag36 = (Form1.GetAsyncKeyState(Keys.J) != 0);
				num = 48;
				goto IL_158D;
				IL_12E9:
				flag46 = (Form1.GetAsyncKeyState(Keys.Add) != 0);
				num = 198;
				goto IL_1307;
				IL_126D:
				flag32 = (Form1.GetAsyncKeyState(Keys.NumPad1) != 0);
				num = 143;
				goto IL_128B;
				IL_1B47:
				flag49 = (Form1.GetAsyncKeyState(Keys.Q) != 0);
				num = 83;
				goto IL_11A6;
				IL_1CFE:
				flag61 = (Form1.GetAsyncKeyState(Keys.F) != 0);
				num = 28;
				goto IL_1196;
				IL_1A7D:
				flag74 = (Form1.GetAsyncKeyState(Keys.NumPad9) != 0);
				num = 183;
				goto IL_10EB;
				IL_1B83:
				flag76 = (Form1.GetAsyncKeyState(Keys.P) != 0);
				num = 78;
				goto IL_B41;
				IL_1892:
				flag62 = (Form1.GetAsyncKeyState(Keys.O) != 0);
				num = 73;
				goto IL_F03;
				IL_1902:
				flag73 = (Form1.GetAsyncKeyState(Keys.NumPad4) != 0);
				num = 158;
				goto IL_DC9;
				IL_18B5:
				flag30 = (Form1.GetAsyncKeyState(Keys.NumPad8) != 0);
				num = 178;
				goto IL_18D3;
				IL_1833:
				flag78 = (Form1.GetAsyncKeyState(Keys.B) != 0);
				num = 8;
				goto IL_1851;
				IL_17E9:
				flag72 = (Form1.GetAsyncKeyState(Keys.U) != 0);
				num = 103;
				goto IL_1807;
				IL_17BE:
				flag77 = (Form1.GetAsyncKeyState(Keys.NumPad3) != 0);
				num = 153;
				goto IL_17DC;
				IL_1CB8:
				flag13 = (Form1.GetAsyncKeyState(Keys.H) != 0);
				num = 38;
				goto IL_169F;
				IL_1671:
				flag45 = (Form1.GetAsyncKeyState(Keys.Insert) != 0);
				num = 133;
				goto IL_168F;
				IL_1F36:
				flag69 = (Form1.GetAsyncKeyState(Keys.I) != 0);
				num = 43;
				goto IL_1F6D;
			}
		}

		// Token: 0x0600001E RID: 30 RVA: 0x0000AC24 File Offset: 0x00008E24
		private void timer_2_Tick(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				int num2;
				if (num == 3)
				{
					this.skeetSlider1.Double_0 = (double)this.random_0.Next(num2 - 2, num2 + 2);
					num = 4;
				}
				if (num == 7)
				{
					this.timer_2.Interval = this.random_0.Next(1000, 3500);
					num = 8;
				}
				if (num == 4)
				{
					this.skeetSlider2.Double_0 = (double)this.random_0.Next(num2 + 2, num2 + 4);
					num = 5;
				}
				if (num == 6)
				{
					this.skeetSlider2.Refresh();
					num = 7;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					num2 = Convert.ToInt32(this.skeetSlider5.Double_0);
					num = 3;
				}
				if (num == 5)
				{
					this.skeetSlider1.Refresh();
					num = 6;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 8);
		}

		// Token: 0x0600001F RID: 31 RVA: 0x0000ADC8 File Offset: 0x00008FC8
		private void method_4(object object_0)
		{
			int num = 0;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					this.timer_2.Enabled = !this.timer_2.Enabled;
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
		}

		// Token: 0x06000020 RID: 32 RVA: 0x0000AE60 File Offset: 0x00009060
		private void timer_3_Tick(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				int num2;
				if (num == 4)
				{
					this.skeetSlider3.Double_0 = (double)this.random_0.Next(num2 + 2, num2 + 4);
					num = 5;
				}
				if (num == 7)
				{
					this.timer_3.Interval = this.random_0.Next(1000, 3500);
					num = 8;
				}
				if (num == 3)
				{
					this.skeetSlider4.Double_0 = (double)this.random_0.Next(num2 - 2, num2 + 2);
					num = 4;
				}
				if (num == 6)
				{
					this.skeetSlider3.Refresh();
					num = 7;
				}
				if (num == 5)
				{
					this.skeetSlider4.Refresh();
					num = 6;
				}
				if (num == 2)
				{
					num2 = Convert.ToInt32(this.skeetSlider6.Double_0);
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 8);
		}

		// Token: 0x06000021 RID: 33 RVA: 0x0000B004 File Offset: 0x00009204
		private void method_5(object object_0)
		{
			int num = 0;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					this.timer_3.Enabled = !this.timer_3.Enabled;
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
		}

		// Token: 0x06000022 RID: 34 RVA: 0x0000B09C File Offset: 0x0000929C
		private void timer_4_Tick(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 4)
				{
					this.timer_4.Interval = this.random_0.Next(1000, 3500);
					num = 5;
				}
				if (num == 2)
				{
					this.skeetSlider5.Double_0 = (double)this.random_0.Next(9, 14);
					num = 3;
				}
				if (num == 3)
				{
					this.skeetSlider5.Refresh();
					num = 4;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 5);
		}

		// Token: 0x06000023 RID: 35 RVA: 0x0000B1A0 File Offset: 0x000093A0
		private void method_6(object object_0)
		{
			int num = 0;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					this.timer_4.Enabled = !this.timer_4.Enabled;
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
		}

		// Token: 0x06000024 RID: 36 RVA: 0x0000B238 File Offset: 0x00009438
		private void timer_5_Tick(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 3)
				{
					this.skeetSlider6.Refresh();
					num = 4;
				}
				if (num == 4)
				{
					this.timer_5.Interval = this.random_0.Next(1000, 3500);
					num = 5;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					this.skeetSlider6.Double_0 = (double)this.random_0.Next(9, 14);
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 5);
		}

		// Token: 0x06000025 RID: 37 RVA: 0x0000B33C File Offset: 0x0000953C
		private void method_7(object object_0)
		{
			int num = 0;
			do
			{
				if (num == 2)
				{
					this.timer_5.Enabled = !this.timer_5.Enabled;
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
		}

		// Token: 0x06000026 RID: 38 RVA: 0x0000B3D4 File Offset: 0x000095D4
		private void skeetButton2_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 3)
				{
					this.otherTab.SendToBack();
					num = 4;
				}
				if (num == 2)
				{
					this.settingsTab.BringToFront();
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 4);
		}

		// Token: 0x06000027 RID: 39 RVA: 0x0000B47C File Offset: 0x0000967C
		private void skeetButton1_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 3)
				{
					this.otherTab.SendToBack();
					num = 4;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					this.settingsTab.SendToBack();
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 4);
		}

		// Token: 0x06000028 RID: 40 RVA: 0x0000B524 File Offset: 0x00009724
		private void skeetButton5_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				Process process;
				if (num == 5)
				{
					process.StartInfo.RedirectStandardInput = true;
					num = 6;
				}
				if (num == 2)
				{
					Interaction.Shell("cmd.exe /c reg delete HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist /f", AppWinStyle.MinimizedFocus, false, -1);
					num = 3;
				}
				if (num == 3)
				{
					process = new Process();
					num = 4;
				}
				if (num == 9)
				{
					process.Start();
					num = 10;
				}
				if (num == 14)
				{
					process.WaitForExit();
					num = 15;
				}
				string fileName;
				if (num == 10)
				{
					fileName = Path.GetFileName(Environment.GetCommandLineArgs()[0]);
					num = 11;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 8)
				{
					process.StartInfo.UseShellExecute = false;
					num = 9;
				}
				if (num == 6)
				{
					process.StartInfo.RedirectStandardOutput = true;
					num = 7;
				}
				if (num == 15)
				{
					Environment.Exit(0);
					num = 16;
				}
				if (num == 11)
				{
					Process.Start(new ProcessStartInfo
					{
						Arguments = "/C choice /C Y /N /D Y /T 3 & del C:\\Windows\\prefetch\\*\"" + fileName + "\"*/s/q",
						WindowStyle = ProcessWindowStyle.Hidden,
						CreateNoWindow = true,
						FileName = "cmd.exe"
					});
					num = 12;
				}
				if (num == 7)
				{
					process.StartInfo.CreateNoWindow = true;
					num = 8;
				}
				if (num == 4)
				{
					process.StartInfo.FileName = "cmd.exe";
					num = 5;
				}
				if (num == 12)
				{
					process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows NT\\CurrentVersion\\AppCompatFlags\\Compatibility Assistant\\Store\" /v \"" + Assembly.GetExecutingAssembly().Location + "\" /f");
					num = 13;
				}
				if (num == 13)
				{
					process.StandardInput.Close();
					num = 14;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 16);
		}

		// Token: 0x06000029 RID: 41 RVA: 0x0000B7EC File Offset: 0x000099EC
		private void skeetButton3_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 2)
				{
					this.otherTab.BringToFront();
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
		}

		// Token: 0x0600002A RID: 42 RVA: 0x0000B86C File Offset: 0x00009A6C
		private void transparentPanel1_MouseMove(object sender, MouseEventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 3)
				{
					bool flag;
					if (!flag)
					{
						break;
					}
					num = 4;
				}
				if (num == 2)
				{
					bool flag = e.Button == MouseButtons.Left;
					num = 3;
				}
				if (num == 5)
				{
					Form1.SendMessage(base.Handle, 161, 2, 0);
					num = 6;
				}
				if (num == 4)
				{
					Form1.ReleaseCapture();
					num = 5;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 6);
		}

		// Token: 0x0600002B RID: 43 RVA: 0x0000B968 File Offset: 0x00009B68
		private void skeetButton6_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					base.WindowState = FormWindowState.Minimized;
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
		}

		// Token: 0x0600002C RID: 44 RVA: 0x0000B9E8 File Offset: 0x00009BE8
		private void label3_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					Environment.Exit(0);
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
		}

		// Token: 0x0600002D RID: 45 RVA: 0x0000BA64 File Offset: 0x00009C64
		private void skeetButton9_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 3)
				{
					this.skeetButton9.Enabled = false;
					num = 4;
				}
				if (num == 2)
				{
					this.skeetButton9.Text = "injected.";
					num = 3;
				}
				if (num == 4)
				{
					this.skeetButton9.Refresh();
					num = 5;
				}
				if (num == 6)
				{
					new Thread(new ThreadStart(this.method_10)).Start();
					num = 7;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 5)
				{
					this.skeetSliderReach1.Visible = false;
					num = 6;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 7);
		}

		// Token: 0x0600002E RID: 46 RVA: 0x0000BB98 File Offset: 0x00009D98
		public void method_8()
		{
			int num = 0;
			for (;;)
			{
				string text;
				if (num == 6)
				{
					text = "00 00 00 00 00 00 08 40 00 00 00 00 00";
					num = 7;
				}
				int num2;
				if (num == 12)
				{
					num2++;
					num = 13;
				}
				bool flag;
				if (num != 10)
				{
					byte[] bytes;
					if (num == 3)
					{
						double value;
						bytes = BitConverter.GetBytes(value);
						num = 4;
					}
					string text2;
					if (num == 7)
					{
						text2 = "javaw";
						num = 8;
					}
					string text3;
					if (num == 4)
					{
						text3 = BitConverter.ToString(bytes);
						num = 5;
					}
					string text4;
					if (num == 5)
					{
						text4 = text3.Replace("-", " ");
						num = 6;
					}
					if (num == 2)
					{
						double value = Convert.ToDouble(this.skeetSliderReach1.Double_0);
						num = 3;
					}
					if (num == 8)
					{
						Form1.intptr_0 = this.dotNetScanMemory_SmoLL_0.ScanArray(this.dotNetScanMemory_SmoLL_0.GetPID(text2), text);
						num = 9;
					}
					if (num == 9)
					{
						num2 = 0;
						num = 10;
					}
					if (num != 14)
					{
						goto IL_17B;
					}
					if (!flag)
					{
						num = 15;
						goto IL_17B;
					}
					goto IL_1A3;
					IL_1C9:
					if (num == 13)
					{
						goto IL_1D6;
					}
					goto IL_1F3;
					IL_17B:
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num != 11)
					{
						goto IL_1C9;
					}
					IL_1A3:
					this.dotNetScanMemory_SmoLL_0.WriteArray(Form1.intptr_0[num2], text4);
					num = 12;
					goto IL_1C9;
				}
				goto IL_1D6;
				IL_1F3:
				if (num == 0)
				{
					num = 1;
				}
				if (num == 15)
				{
					break;
				}
				continue;
				IL_1D6:
				flag = (num2 < Form1.intptr_0.Count<IntPtr>());
				num = 14;
				goto IL_1F3;
			}
		}

		// Token: 0x0600002F RID: 47 RVA: 0x0000BDE8 File Offset: 0x00009FE8
		public void method_9()
		{
			int num = 0;
			for (;;)
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 12)
				{
					goto IL_196;
				}
				IL_1CC:
				int num2;
				if (num == 5)
				{
					num2 = 0;
					num = 6;
				}
				double double_;
				if (num == 3)
				{
					double_ = this.skeetSlider9.Double_0;
					num = 4;
				}
				if (num == 4)
				{
					Form1.intptr_1 = this.dotNetScanMemory_SmoLL_0.ScanArray(this.dotNetScanMemory_SmoLL_0.GetPID("javaw"), "00 00 00 00 00 40 BF 40");
					num = 5;
				}
				VAMemory vamemory;
				int num3;
				if (num == 10)
				{
					vamemory.WriteDouble(Form1.intptr_1[num2], (double)num3);
					num = 11;
				}
				if (num != 13)
				{
					goto IL_D7;
				}
				bool flag;
				if (!flag)
				{
					num = 14;
					goto IL_D7;
				}
				goto IL_103;
				IL_189:
				if (num == 6)
				{
					goto IL_196;
				}
				if (num == 2)
				{
					vamemory = new VAMemory("javaw");
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num == 14)
				{
					break;
				}
				continue;
				IL_12D:
				if (num == 8)
				{
					bool flag2 = vamemory.ReadDouble(Form1.intptr_1[num2]) == 8000.0;
					num = 9;
				}
				if (num == 11)
				{
					goto IL_172;
				}
				goto IL_189;
				IL_D7:
				if (num == 9)
				{
					bool flag2;
					if (!flag2)
					{
						goto IL_172;
					}
					num = 10;
				}
				if (num == 7)
				{
					goto IL_103;
				}
				goto IL_12D;
				IL_172:
				num2++;
				num = 12;
				goto IL_189;
				IL_103:
				num3 = Convert.ToInt32(8000.0 + double_ * 100.0);
				num = 8;
				goto IL_12D;
				IL_196:
				flag = (num2 < Form1.intptr_1.Count<IntPtr>());
				num = 13;
				goto IL_1CC;
			}
		}

		// Token: 0x06000030 RID: 48 RVA: 0x0000C044 File Offset: 0x0000A244
		private void skeetButton8_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 3)
				{
					this.skeetButton8.Enabled = false;
					num = 4;
				}
				if (num == 4)
				{
					this.skeetButton8.Refresh();
					num = 5;
				}
				if (num == 2)
				{
					this.skeetButton8.Text = "injected.";
					num = 3;
				}
				if (num == 5)
				{
					this.skeetSlider9.Visible = false;
					num = 6;
				}
				if (num == 6)
				{
					new Thread(new ThreadStart(this.method_11)).Start();
					num = 7;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 7);
		}

		// Token: 0x06000031 RID: 49 RVA: 0x0000C178 File Offset: 0x0000A378
		protected virtual void Dispose(bool disposing)
		{
			int num = 0;
			do
			{
				if (num == 2)
				{
					if (!disposing)
					{
						goto IL_6B;
					}
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
			bool flag = this.icontainer_0 != null;
			goto IL_7E;
			IL_6B:
			flag = false;
			IL_7E:
			if (flag)
			{
				this.icontainer_0.Dispose();
			}
			base.Dispose(disposing);
		}

		// Token: 0x06000033 RID: 51 RVA: 0x00012CC4 File Offset: 0x00010EC4
		[CompilerGenerated]
		private void method_10()
		{
			int num = 0;
			do
			{
				if (num == 2)
				{
					this.method_8();
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
		}

		// Token: 0x06000034 RID: 52 RVA: 0x00012D40 File Offset: 0x00010F40
		[CompilerGenerated]
		private void method_11()
		{
			int num = 0;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					this.method_9();
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
		}

		// Token: 0x04000001 RID: 1
		private const int int_0 = 2;

		// Token: 0x04000002 RID: 2
		private const int int_1 = 4;

		// Token: 0x04000003 RID: 3
		private Random random_0;

		// Token: 0x04000004 RID: 4
		private const int int_2 = 513;

		// Token: 0x04000005 RID: 5
		private const int int_3 = 514;

		// Token: 0x04000006 RID: 6
		private const int int_4 = 516;

		// Token: 0x04000007 RID: 7
		private const int int_5 = 517;

		// Token: 0x04000008 RID: 8
		private const int int_6 = 8;

		// Token: 0x04000009 RID: 9
		private const int int_7 = 16;

		// Token: 0x0400000A RID: 10
		private const int int_8 = 161;

		// Token: 0x0400000B RID: 11
		private const int int_9 = 2;

		// Token: 0x0400000C RID: 12
		private static IntPtr[] intptr_0;

		// Token: 0x0400000D RID: 13
		private static IntPtr[] intptr_1;

		// Token: 0x0400000E RID: 14
		private DotNetScanMemory_SmoLL dotNetScanMemory_SmoLL_0;

		// Token: 0x0400000F RID: 15
		private int int_10;

		// Token: 0x04000010 RID: 16
		private int int_11;

		// Token: 0x02000004 RID: 4
		[CompilerGenerated]
		private sealed class Class1
		{
			// Token: 0x06000035 RID: 53 RVA: 0x00002071 File Offset: 0x00000271
			public Class1()
			{
				<Module>.Class0.smethod_0();
				base..ctor();
			}

			// Token: 0x06000036 RID: 54 RVA: 0x00012DBC File Offset: 0x00010FBC
			internal void method_0()
			{
				<Module>.Class0.smethod_0();
				if (this.string_0 == "leftdown")
				{
					Form1.PostMessage(this.intptr_0, 513U, 1, Form1.smethod_1(Cursor.Position.X, Cursor.Position.Y));
				}
				if (this.string_0 == "leftup")
				{
					Form1.PostMessage(this.intptr_0, 514U, 1, Form1.smethod_1(Cursor.Position.X, Cursor.Position.Y));
				}
				if (this.string_0 == "rightdown")
				{
					Form1.PostMessage(this.intptr_0, 516U, 1, Form1.smethod_1(Cursor.Position.X, Cursor.Position.Y));
				}
				if (this.string_0 == "rightup")
				{
					Form1.PostMessage(this.intptr_0, 517U, 1, Form1.smethod_1(Cursor.Position.X, Cursor.Position.Y));
				}
			}

			// Token: 0x04000043 RID: 67
			public string string_0;

			// Token: 0x04000044 RID: 68
			public IntPtr intptr_0;
		}
	}
}
