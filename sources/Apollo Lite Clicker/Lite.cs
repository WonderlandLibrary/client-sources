using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Reflection;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;
using apollo_lite.Properties;
using Bunifu.Framework.UI;
using FlatUI;
using Microsoft.CSharp.RuntimeBinder;
using Newtonsoft.Json;

// Token: 0x02000008 RID: 8
public partial class Lite : Form
{
	// Token: 0x0600001D RID: 29 RVA: 0x00002468 File Offset: 0x00000668
	public Lite(dynamic object_1, string string_5)
	{
		if (Lite.Class7.callSite_1 == null)
		{
			Lite.Class7.callSite_1 = CallSite<Func<CallSite, object, string>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.Convert(CSharpBinderFlags.None, typeof(string), typeof(Lite)));
		}
		Func<CallSite, object, string> target = Lite.Class7.callSite_1.Target;
		CallSite callSite_ = Lite.Class7.callSite_1;
		if (Lite.Class7.callSite_0 == null)
		{
			Lite.Class7.callSite_0 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "Username", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		this.string_0 = target(callSite_, Lite.Class7.callSite_0.Target(Lite.Class7.callSite_0, object_1));
		this.object_0 = object_1;
		this.string_4 = string_5;
		this.InitializeComponent();
	}

	// Token: 0x0600001E RID: 30 RVA: 0x00002588 File Offset: 0x00000788
	private void Lite_Load(object sender, EventArgs e)
	{
		this.versionLabel.Text = this.string_1;
		this.licencedLabel.Text = "Cracked by Syn lmao";
		foreach (Process process in Process.GetProcesses())
		{
			if (!string.IsNullOrEmpty(process.MainWindowTitle))
			{
				if (process.MainWindowTitle.Contains("Minecraft"))
				{
					this.openProcesses.SelectedIndex = this.openProcesses.Items.Count - 1;
				}
				this.openProcesses.Items.Add(process.MainWindowTitle);
			}
		}
		this.method_16();
		this.timer_6.Start();
		this.method_0();
		try
		{
			foreach (Process process2 in Process.GetProcesses())
			{
				try
				{
					if (process2.ProcessName == "explorer")
					{
						process2.Kill();
						break;
					}
				}
				catch
				{
				}
			}
		}
		catch (Exception ex)
		{
			Console.WriteLine(ex.Message);
		}
	}

	// Token: 0x0600001F RID: 31 RVA: 0x0000269C File Offset: 0x0000089C
	public void method_0()
	{
		this.class6_0 = new Class6();
		this.class6_0.Event_0 += this.method_1;
	}

	// Token: 0x06000020 RID: 32 RVA: 0x000026CC File Offset: 0x000008CC
	private void method_1(object sender, EventArgs0 e)
	{
		if (e.Struct6_0.int_0 == this.int_9)
		{
			if (e.Enum0_0 == Class6.Enum0.KeyDown)
			{
				this.method_5();
			}
		}
		if (e.Struct6_0.int_0 == this.int_10)
		{
			if (e.Enum0_0 == Class6.Enum0.KeyDown)
			{
				this.bool_5 = !this.bool_5;
				if (this.bool_5)
				{
					base.Hide();
					return;
				}
				base.Show();
			}
		}
	}

	// Token: 0x06000021 RID: 33 RVA: 0x0000274C File Offset: 0x0000094C
	public void method_2()
	{
		Class6 @class = this.class6_0;
		if (@class == null)
		{
			return;
		}
		@class.Dispose();
	}

	// Token: 0x06000022 RID: 34
	[DllImport("user32.dll")]
	public static extern int GetWindowText(IntPtr intptr_1, StringBuilder stringBuilder_0, int int_18);

	// Token: 0x06000023 RID: 35
	[DllImport("user32.dll")]
	public static extern IntPtr GetForegroundWindow();

	// Token: 0x06000024 RID: 36
	[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
	private static extern IntPtr CallNextHookEx(IntPtr intptr_1, int int_18, IntPtr intptr_2, IntPtr intptr_3);

	// Token: 0x06000025 RID: 37
	[DllImport("kernel32.dll", CharSet = CharSet.Auto, SetLastError = true)]
	private static extern IntPtr GetModuleHandle(string string_5);

	// Token: 0x06000026 RID: 38
	[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
	private static extern IntPtr SetWindowsHookEx(int int_18, Lite.Delegate1 delegate1_1, IntPtr intptr_1, uint uint_0);

	// Token: 0x06000027 RID: 39
	[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
	[return: MarshalAs(UnmanagedType.Bool)]
	private static extern bool UnhookWindowsHookEx(IntPtr intptr_1);

	// Token: 0x06000028 RID: 40 RVA: 0x0000276C File Offset: 0x0000096C
	protected virtual void OnLoad(EventArgs e)
	{
		try
		{
			base.OnLoad(e);
		}
		catch (Exception)
		{
		}
		Lite.intptr_0 = this.method_3(Lite.delegate1_0);
	}

	// Token: 0x06000029 RID: 41 RVA: 0x000027A8 File Offset: 0x000009A8
	protected virtual void OnClosed(EventArgs e)
	{
		base.OnClosed(e);
		Lite.UnhookWindowsHookEx(Lite.intptr_0);
	}

	// Token: 0x0600002A RID: 42 RVA: 0x000027C8 File Offset: 0x000009C8
	private IntPtr method_3(Lite.Delegate1 delegate1_1)
	{
		IntPtr result;
		using (Process currentProcess = Process.GetCurrentProcess())
		{
			using (ProcessModule mainModule = currentProcess.MainModule)
			{
				result = Lite.SetWindowsHookEx(14, delegate1_1, Lite.GetModuleHandle(mainModule.ModuleName), 0u);
			}
		}
		return result;
	}

	// Token: 0x0600002B RID: 43 RVA: 0x0000282C File Offset: 0x00000A2C
	private static IntPtr smethod_0(int int_18, IntPtr intptr_1, IntPtr intptr_2)
	{
		if (int_18 >= 0 && 513 == (int)intptr_1 && ((Lite.Struct7)Marshal.PtrToStructure(intptr_2, typeof(Lite.Struct7))).uint_1 == 0u)
		{
			Settings.Default.leftDown = true;
		}
		if (int_18 >= 0)
		{
			if (514 == (int)intptr_1)
			{
				if (((Lite.Struct7)Marshal.PtrToStructure(intptr_2, typeof(Lite.Struct7))).uint_1 == 0u)
				{
					Settings.Default.leftUp = true;
				}
			}
		}
		if (int_18 >= 0 && 517 == (int)intptr_1 && ((Lite.Struct7)Marshal.PtrToStructure(intptr_2, typeof(Lite.Struct7))).uint_1 == 0u)
		{
			Settings.Default.rightUp = true;
		}
		if (int_18 >= 0)
		{
			if (516 == (int)intptr_1)
			{
				if (((Lite.Struct7)Marshal.PtrToStructure(intptr_2, typeof(Lite.Struct7))).uint_1 == 0u)
				{
					Settings.Default.rightDown = true;
				}
			}
		}
		if (int_18 >= 0)
		{
			if (519 == (int)intptr_1)
			{
				if (((Lite.Struct7)Marshal.PtrToStructure(intptr_2, typeof(Lite.Struct7))).uint_1 == 0u)
				{
					Settings.Default.middleDown = true;
				}
			}
		}
		if (int_18 >= 0 && 520 == (int)intptr_1 && ((Lite.Struct7)Marshal.PtrToStructure(intptr_2, typeof(Lite.Struct7))).uint_1 == 0u)
		{
			Settings.Default.middleUp = true;
		}
		return Lite.CallNextHookEx(Lite.intptr_0, int_18, intptr_1, intptr_2);
	}

	// Token: 0x0600002C RID: 44 RVA: 0x00002050 File Offset: 0x00000250
	private void clickerPanel_Paint(object sender, PaintEventArgs e)
	{
	}

	// Token: 0x0600002D RID: 45 RVA: 0x0000299C File Offset: 0x00000B9C
	public string method_4()
	{
		StringBuilder stringBuilder = new StringBuilder(256);
		if (Lite.GetWindowText(Lite.GetForegroundWindow(), stringBuilder, 256) > 0)
		{
			return stringBuilder.ToString();
		}
		return null;
	}

	// Token: 0x0600002E RID: 46 RVA: 0x000029D0 File Offset: 0x00000BD0
	private void pictureBox3_Click(object sender, EventArgs e)
	{
		if (this.int_10 != 0)
		{
			base.Hide();
			this.bool_5 = true;
			return;
		}
		MessageBox.Show("You must have a key bound to hide the clicker!");
	}

	// Token: 0x0600002F RID: 47 RVA: 0x00002A00 File Offset: 0x00000C00
	private void method_5()
	{
		this.clickerToggle.Checked = !this.clickerToggle.Checked;
		if (!this.clickerToggle.Checked)
		{
			base.Icon = Class5.Icon_0;
		}
		else
		{
			base.Icon = Class5.Icon_1;
		}
		this.clickerToggle.Refresh();
	}

	// Token: 0x06000030 RID: 48 RVA: 0x00002A58 File Offset: 0x00000C58
	private void toggleKeyButton_MouseClick(object sender, MouseEventArgs e)
	{
		if (e.Button != MouseButtons.Middle)
		{
			this.toggleKeyButton.Focus();
		}
	}

	// Token: 0x06000031 RID: 49 RVA: 0x00002A80 File Offset: 0x00000C80
	private void toggleKeyButton_Enter(object sender, EventArgs e)
	{
		this.toggleKeyButton.Text = "> " + this.string_2 + " <";
		this.toggleKeyButton.Refresh();
	}

	// Token: 0x06000032 RID: 50 RVA: 0x00002AB8 File Offset: 0x00000CB8
	private void toggleKeyButton_Leave(object sender, EventArgs e)
	{
		this.toggleKeyButton.Text = "< " + this.string_2 + " >";
		this.toggleKeyButton.Refresh();
	}

	// Token: 0x06000033 RID: 51 RVA: 0x00002AF0 File Offset: 0x00000CF0
	private void toggleKeyButton_KeyDown(object sender, KeyEventArgs e)
	{
		if (e.KeyCode.ToString() != "Escape")
		{
			this.string_2 = e.KeyCode.ToString();
			this.int_9 = e.KeyValue;
			this.clickerToggle.Focus();
		}
		else
		{
			this.string_2 = "NONE";
			this.int_9 = 0;
			this.clickerToggle.Focus();
		}
		this.toggleKeyButton.Refresh();
	}

	// Token: 0x06000034 RID: 52 RVA: 0x00002B7C File Offset: 0x00000D7C
	private void toggleKeyButton_MouseDown(object sender, MouseEventArgs e)
	{
		if (e.Button == MouseButtons.Middle && this.toggleKeyButton.Focused)
		{
			this.int_9 = 6661;
			this.string_2 = "MClick";
			this.clickerToggle.Focus();
		}
	}

	// Token: 0x06000035 RID: 53 RVA: 0x00002BC8 File Offset: 0x00000DC8
	private void CPSBar_Scroll(object object_1)
	{
		if (this.CPSBar.Value2 <= this.CPSBar.Value || this.CPSBar.Value <= 10)
		{
			this.CPSBar.Value = this.int_13;
			this.CPSBar.Value2 = this.int_14;
		}
		this.int_13 = this.CPSBar.Value;
		this.int_14 = this.CPSBar.Value2;
		this.int_12 = this.int_14 - 3;
		this.int_11 = this.int_13 - 3;
		this.bunifuCustomLabel11.Text = "CPS: " + string.Format("{0:N1}", (float)this.CPSBar.Value / 10f) + "-" + string.Format("{0:N1}", (float)this.CPSBar.Value2 / 10f);
	}

	// Token: 0x06000036 RID: 54 RVA: 0x00002CB8 File Offset: 0x00000EB8
	private void blockhitModeText_Click(object sender, EventArgs e)
	{
		this.blockhitModeToggle.Checked = !this.blockhitModeToggle.Checked;
	}

	// Token: 0x06000037 RID: 55 RVA: 0x00002CE0 File Offset: 0x00000EE0
	private void constantClickText_Click(object sender, EventArgs e)
	{
		this.constantClickToggle.Checked = !this.constantClickToggle.Checked;
		this.method_6();
	}

	// Token: 0x06000038 RID: 56 RVA: 0x00002D0C File Offset: 0x00000F0C
	private void destructText_Click(object sender, EventArgs e)
	{
		this.destructToggle.Checked = !this.destructToggle.Checked;
	}

	// Token: 0x06000039 RID: 57 RVA: 0x00002D34 File Offset: 0x00000F34
	private void memoryText_Click(object sender, EventArgs e)
	{
		this.memoryToggle.Checked = !this.memoryToggle.Checked;
	}

	// Token: 0x0600003A RID: 58 RVA: 0x00002D5C File Offset: 0x00000F5C
	private void prefetchText_Click(object sender, EventArgs e)
	{
		this.prefetchToggle.Checked = !this.prefetchToggle.Checked;
	}

	// Token: 0x0600003B RID: 59 RVA: 0x00002D84 File Offset: 0x00000F84
	private void filesText_Click(object sender, EventArgs e)
	{
		this.filesToggle.Checked = !this.filesToggle.Checked;
	}

	// Token: 0x0600003C RID: 60 RVA: 0x00002DAC File Offset: 0x00000FAC
	private void constantClickToggle_OnChange(object sender, EventArgs e)
	{
		this.method_6();
	}

	// Token: 0x0600003D RID: 61 RVA: 0x00002DC0 File Offset: 0x00000FC0
	private void method_6()
	{
		if (this.constantClickToggle.Checked)
		{
			this.timer_0.Start();
			return;
		}
		this.timer_0.Start();
	}

	// Token: 0x0600003E RID: 62 RVA: 0x00002DF4 File Offset: 0x00000FF4
	private void timer_0_Tick(object sender, EventArgs e)
	{
		if (this.clickerToggle.Checked)
		{
			if (this.method_4() != this.openProcesses.Text && this.specificWindowToggle.Checked)
			{
				return;
			}
			this.timer_1.Interval = this.timer_0.Interval / 3;
			this.timer_3.Interval = this.timer_0.Interval;
			this.timer_2.Interval = this.timer_0.Interval / 3;
			this.timer_0.Interval = this.method_7(new Random().Next(1000 / this.int_12 * 10, 1000 / this.int_11 * 10));
			if (this.bool_1 && this.blockhitModeToggle.Checked)
			{
				this.timer_0.Interval = this.timer_0.Interval * 2 - this.timer_0.Interval / 2;
			}
			this.class9_0.method_0();
			this.timer_1.Start();
			if (this.bool_1 && this.blockhitModeToggle.Checked)
			{
				this.timer_3.Start();
			}
		}
		this.int_15++;
	}

	// Token: 0x0600003F RID: 63 RVA: 0x00002F38 File Offset: 0x00001138
	private int method_7(int int_18)
	{
		int num = 1000 / this.int_11 * 10;
		if (new Random().Next(1, 20) >= 18)
		{
			if (!this.bool_2)
			{
				if (this.int_2 <= 20)
				{
					this.int_2++;
					if (new Random().Next(1, 150) >= 148)
					{
						this.bool_2 = !this.bool_2;
					}
				}
				else if (new Random().Next(1, 10) >= 5)
				{
					this.bool_2 = !this.bool_2;
				}
			}
			else if (this.int_2 >= 0)
			{
				this.int_2--;
				if (new Random().Next(1, 150) >= 148)
				{
					this.bool_2 = !this.bool_2;
				}
			}
			else if (new Random().Next(1, 20) >= 5)
			{
				this.bool_2 = !this.bool_2;
			}
		}
		int num2 = (num - int_18) * (this.int_2 * 10 / 100);
		if (new Random().Next(1, 3) == 2 && this.int_7 > 3)
		{
			this.int_7--;
		}
		if (new Random().Next(1, 3) == 2)
		{
			if (this.int_8 > 3)
			{
				this.int_8--;
			}
		}
		if (new Random().Next(1, this.int_7) == 2)
		{
			this.timer_4.Interval = new Random().Next(3000, 5000);
			this.timer_4.Start();
			this.int_6 = new Random().Next(10, 25);
			this.bool_4 = true;
			this.int_7 = 9999999;
		}
		if (new Random().Next(1, this.int_8) == 2)
		{
			this.timer_5.Interval = new Random().Next(3000, 5000);
			this.timer_5.Start();
			this.int_4 = new Random().Next(100, 250);
			this.bool_3 = true;
			this.int_8 = 9999999;
		}
		if (this.bool_3 && this.bool_4)
		{
			this.bool_3 = false;
		}
		if (this.bool_3)
		{
			if (this.int_3 < this.int_4)
			{
				this.int_3 += new Random().Next(1, 5);
			}
		}
		else if (this.int_3 > 0)
		{
			this.int_3 -= new Random().Next(1, 5);
		}
		if (this.bool_4)
		{
			if (this.int_5 < this.int_6)
			{
				this.int_5 += new Random().Next(1, 5);
			}
		}
		else if (this.int_5 > 0)
		{
			this.int_5 -= new Random().Next(1, 5);
		}
		int_18 += this.int_3;
		int_18 -= this.int_5;
		return int_18 + num2;
	}

	// Token: 0x06000040 RID: 64 RVA: 0x00003234 File Offset: 0x00001434
	private void timer_1_Tick(object sender, EventArgs e)
	{
		this.timer_1.Stop();
		this.class9_0.method_1();
	}

	// Token: 0x06000041 RID: 65 RVA: 0x00003258 File Offset: 0x00001458
	private void timer_3_Tick(object sender, EventArgs e)
	{
		this.timer_3.Stop();
		this.class9_0.method_2();
		this.timer_2.Start();
	}

	// Token: 0x06000042 RID: 66 RVA: 0x00003288 File Offset: 0x00001488
	private void timer_2_Tick(object sender, EventArgs e)
	{
		this.timer_2.Stop();
		this.class9_0.method_3();
	}

	// Token: 0x06000043 RID: 67 RVA: 0x00002050 File Offset: 0x00000250
	private void timer_5_Tick(object sender, EventArgs e)
	{
	}

	// Token: 0x06000044 RID: 68 RVA: 0x000032AC File Offset: 0x000014AC
	private void timer_6_Tick(object sender, EventArgs e)
	{
		if (this.class12_0.method_0())
		{
			this.method_9();
		}
		if (this.class12_0.method_1())
		{
			this.method_8();
		}
		if (this.class12_0.method_3())
		{
			this.method_11();
		}
		if (this.class12_0.method_2())
		{
			this.method_10();
		}
		if (this.class12_0.method_5())
		{
			this.method_13();
		}
		if (this.class12_0.method_4())
		{
			this.method_12();
		}
	}

	// Token: 0x06000045 RID: 69 RVA: 0x0000332C File Offset: 0x0000152C
	public void method_8()
	{
		if (!this.constantClickToggle.Checked)
		{
			this.timer_0.Stop();
		}
	}

	// Token: 0x06000046 RID: 70 RVA: 0x00003354 File Offset: 0x00001554
	public void method_9()
	{
		if (!this.constantClickToggle.Checked && !this.bool_1)
		{
			this.timer_0.Start();
		}
	}

	// Token: 0x06000047 RID: 71 RVA: 0x00003384 File Offset: 0x00001584
	public void method_10()
	{
		this.bool_1 = false;
	}

	// Token: 0x06000048 RID: 72 RVA: 0x00003398 File Offset: 0x00001598
	public void method_11()
	{
		this.bool_1 = true;
	}

	// Token: 0x06000049 RID: 73 RVA: 0x00002050 File Offset: 0x00000250
	private void method_12()
	{
	}

	// Token: 0x0600004A RID: 74 RVA: 0x000033AC File Offset: 0x000015AC
	private void method_13()
	{
		if (this.int_9 == 6661)
		{
			this.method_5();
		}
	}

	// Token: 0x0600004B RID: 75 RVA: 0x00002050 File Offset: 0x00000250
	private void clickerToggle_CheckedChanged(object object_1)
	{
	}

	// Token: 0x0600004C RID: 76 RVA: 0x000033CC File Offset: 0x000015CC
	private void selfDestructButton_Click(object sender, EventArgs e)
	{
		this.timer_7.Start();
	}

	// Token: 0x0600004D RID: 77 RVA: 0x000033E4 File Offset: 0x000015E4
	private void method_14()
	{
		string fileName = Path.GetFileName(Environment.GetCommandLineArgs()[0]);
		if (this.memoryToggle.Checked)
		{
			this.bunifuCustomLabel1.Dispose();
			this.bunifuCustomLabel11.Dispose();
			this.bunifuCustomLabel12.Dispose();
			this.bunifuCustomLabel2.Dispose();
			this.bunifuCustomLabel3.Dispose();
			this.bunifuCustomLabel4.Dispose();
			this.bunifuFormFadeTransition_0.Dispose();
			this.flatTabControl1.Dispose();
			this.panel1.Dispose();
			this.panel2.Dispose();
			this.panel3.Dispose();
			this.selfDestructPanel.Dispose();
			this.specificPanel.Dispose();
			this.licencedLabel.Dispose();
			this.versionLabel.Dispose();
			this.selfDestructButton.Dispose();
			this.toggleKeyButton.Dispose();
			this.openProcesses.Dispose();
			this.memoryToggle.Dispose();
			this.prefetchToggle.Dispose();
			this.specificWindowToggle.Dispose();
			this.licencedLabel.Dispose();
			this.versionLabel.Dispose();
		}
		if (this.filesToggle.Checked)
		{
			Process.Start(new ProcessStartInfo
			{
				Arguments = "/C choice /C Y /N /D Y /T 3 & Del \"" + Application.ExecutablePath + "\"",
				WindowStyle = ProcessWindowStyle.Hidden,
				CreateNoWindow = true,
				FileName = "cmd.exe"
			});
			Application.Exit();
		}
		if (this.prefetchToggle.Checked)
		{
			Process.Start(new ProcessStartInfo
			{
				Arguments = "/C choice /C Y /N /D Y /T 3 & del C:\\Windows\\prefetch\\*\"" + fileName + "\"*/s/q",
				WindowStyle = ProcessWindowStyle.Hidden,
				CreateNoWindow = true,
				FileName = "cmd.exe"
			});
		}
		this.timer_7.Stop();
		try
		{
			Process process = new Process();
			process.StartInfo.FileName = "cmd.exe";
			process.StartInfo.RedirectStandardInput = true;
			process.StartInfo.RedirectStandardOutput = true;
			process.StartInfo.CreateNoWindow = true;
			process.StartInfo.UseShellExecute = false;
			process.Start();
			process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{9E04CAB2-CC14-11DF-BB8C-A2F1DED72085}\\Count\" /v \"" + Lite.smethod_1(Assembly.GetExecutingAssembly().Location) + "\" /f");
			Console.WriteLine("1");
			process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{A3D53349-6E61-4557-8FC7-0028EDCEEBF6}\\Count\" /v \"" + Lite.smethod_1(Assembly.GetExecutingAssembly().Location) + "\" /f");
			Console.WriteLine("1");
			process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{B267E3AD-A825-4A09-82B9-EEC22AA3B847}\\Count\" /v \"" + Lite.smethod_1(Assembly.GetExecutingAssembly().Location) + "\" /f");
			Console.WriteLine("1");
			process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{BCB48336-4DDD-48FF-BB0B-D3190DACB3E2}\\Count\" /v \"" + Lite.smethod_1(Assembly.GetExecutingAssembly().Location) + "\" /f");
			Console.WriteLine("1");
			process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{CAA59E3C-4792-41A5-9909-6A6A8D32490E}\\Count\" /v \"" + Lite.smethod_1(Assembly.GetExecutingAssembly().Location) + "\" /f");
			Console.WriteLine("1");
			process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{CEBFF5CD-ACE2-4F4F-9178-9926F41749EA}\\Count\" /v \"" + Lite.smethod_1(Assembly.GetExecutingAssembly().Location) + "\" /f");
			Console.WriteLine("1");
			process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{F2A1CB5A-E3CC-4A2E-AF9D-505A7009D442}\\Count\" /v \"" + Lite.smethod_1(Assembly.GetExecutingAssembly().Location) + "\" /f");
			Console.WriteLine("1");
			process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{F4E57C4B-2036-45F0-A9AB-443BCFE33D9F}\\Count\" /v \"" + Lite.smethod_1(Assembly.GetExecutingAssembly().Location) + "\" /f");
			Console.WriteLine("1");
			process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{FA99DFC7-6AC2-453A-A5E2-5E2AFF4507BD}\\Count\" /v \"" + Lite.smethod_1(Assembly.GetExecutingAssembly().Location) + "\" /f");
			Console.WriteLine("1");
			process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows NT\\CurrentVersion\\AppCompatFlags\\Compatibility Assistant\\Store\" /v \"" + Assembly.GetExecutingAssembly().Location + "\" /f");
			process.StandardInput.Close();
			process.WaitForExit();
			Console.WriteLine(process.StandardOutput.ReadToEnd());
		}
		catch (Exception)
		{
			Console.Write("Error exploding.");
		}
	}

	// Token: 0x0600004E RID: 78 RVA: 0x00003834 File Offset: 0x00001A34
	public static string smethod_1(string string_5)
	{
		char[] array = string_5.ToCharArray();
		for (int i = 0; i < array.Length; i++)
		{
			int num = (int)array[i];
			if (num >= 97 && num <= 122)
			{
				if (num > 109)
				{
					num -= 13;
				}
				else
				{
					num += 13;
				}
			}
			else if (num >= 65 && num <= 90)
			{
				if (num <= 77)
				{
					num += 13;
				}
				else
				{
					num -= 13;
				}
			}
			array[i] = (char)num;
		}
		return new string(array);
	}

	// Token: 0x0600004F RID: 79 RVA: 0x000038A0 File Offset: 0x00001AA0
	private void Lite_MouseMove(object sender, MouseEventArgs e)
	{
		if (this.bool_0)
		{
			base.Top = Cursor.Position.Y - this.int_0;
			base.Left = Cursor.Position.X - this.int_1;
		}
	}

	// Token: 0x06000050 RID: 80 RVA: 0x000038EC File Offset: 0x00001AEC
	private void Lite_MouseDown(object sender, MouseEventArgs e)
	{
		this.bool_0 = true;
		this.int_1 = Cursor.Position.X - base.Left;
		this.int_0 = Cursor.Position.Y - base.Top;
	}

	// Token: 0x06000051 RID: 81 RVA: 0x00003934 File Offset: 0x00001B34
	private void Lite_MouseUp(object sender, MouseEventArgs e)
	{
		this.bool_0 = false;
	}

	// Token: 0x06000052 RID: 82 RVA: 0x000038A0 File Offset: 0x00001AA0
	private void flatTabControl1_MouseMove(object sender, MouseEventArgs e)
	{
		if (this.bool_0)
		{
			base.Top = Cursor.Position.Y - this.int_0;
			base.Left = Cursor.Position.X - this.int_1;
		}
	}

	// Token: 0x06000053 RID: 83 RVA: 0x000038EC File Offset: 0x00001AEC
	private void flatTabControl1_MouseDown(object sender, MouseEventArgs e)
	{
		this.bool_0 = true;
		this.int_1 = Cursor.Position.X - base.Left;
		this.int_0 = Cursor.Position.Y - base.Top;
	}

	// Token: 0x06000054 RID: 84 RVA: 0x00003934 File Offset: 0x00001B34
	private void flatTabControl1_MouseUp(object sender, MouseEventArgs e)
	{
		this.bool_0 = false;
	}

	// Token: 0x06000055 RID: 85 RVA: 0x000038A0 File Offset: 0x00001AA0
	private void panel2_MouseMove(object sender, MouseEventArgs e)
	{
		if (this.bool_0)
		{
			base.Top = Cursor.Position.Y - this.int_0;
			base.Left = Cursor.Position.X - this.int_1;
		}
	}

	// Token: 0x06000056 RID: 86 RVA: 0x000038EC File Offset: 0x00001AEC
	private void panel2_MouseDown(object sender, MouseEventArgs e)
	{
		this.bool_0 = true;
		this.int_1 = Cursor.Position.X - base.Left;
		this.int_0 = Cursor.Position.Y - base.Top;
	}

	// Token: 0x06000057 RID: 87 RVA: 0x00003934 File Offset: 0x00001B34
	private void panel2_MouseUp(object sender, MouseEventArgs e)
	{
		this.bool_0 = false;
	}

	// Token: 0x06000058 RID: 88 RVA: 0x000038A0 File Offset: 0x00001AA0
	private void tabPage1_MouseMove(object sender, MouseEventArgs e)
	{
		if (this.bool_0)
		{
			base.Top = Cursor.Position.Y - this.int_0;
			base.Left = Cursor.Position.X - this.int_1;
		}
	}

	// Token: 0x06000059 RID: 89 RVA: 0x000038A0 File Offset: 0x00001AA0
	private void tabPage2_MouseMove(object sender, MouseEventArgs e)
	{
		if (this.bool_0)
		{
			base.Top = Cursor.Position.Y - this.int_0;
			base.Left = Cursor.Position.X - this.int_1;
		}
	}

	// Token: 0x0600005A RID: 90 RVA: 0x000038EC File Offset: 0x00001AEC
	private void tabPage1_MouseDown(object sender, MouseEventArgs e)
	{
		this.bool_0 = true;
		this.int_1 = Cursor.Position.X - base.Left;
		this.int_0 = Cursor.Position.Y - base.Top;
	}

	// Token: 0x0600005B RID: 91 RVA: 0x000038EC File Offset: 0x00001AEC
	private void tabPage2_MouseDown(object sender, MouseEventArgs e)
	{
		this.bool_0 = true;
		this.int_1 = Cursor.Position.X - base.Left;
		this.int_0 = Cursor.Position.Y - base.Top;
	}

	// Token: 0x0600005C RID: 92 RVA: 0x00003934 File Offset: 0x00001B34
	private void tabPage1_MouseUp(object sender, MouseEventArgs e)
	{
		this.bool_0 = false;
	}

	// Token: 0x0600005D RID: 93 RVA: 0x00003934 File Offset: 0x00001B34
	private void tabPage2_MouseUp(object sender, MouseEventArgs e)
	{
		this.bool_0 = false;
	}

	// Token: 0x0600005E RID: 94 RVA: 0x00003948 File Offset: 0x00001B48
	private void method_15(object sender, MouseEventArgs e)
	{
		base.Show();
	}

	// Token: 0x0600005F RID: 95 RVA: 0x0000395C File Offset: 0x00001B5C
	private void Lite_FormClosing(object sender, FormClosingEventArgs e)
	{
		if (this.destructToggle.Checked)
		{
			this.method_14();
		}
	}

	// Token: 0x06000060 RID: 96 RVA: 0x0000397C File Offset: 0x00001B7C
	private void method_16()
	{
		if (Lite.Class8.callSite_2 == null)
		{
			Lite.Class8.callSite_2 = CallSite<Func<CallSite, Type, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "DeserializeObject", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, Type, object, object> target = Lite.Class8.callSite_2.Target;
		CallSite callSite_ = Lite.Class8.callSite_2;
		Type typeFromHandle = typeof(JsonConvert);
		if (Lite.Class8.callSite_1 == null)
		{
			Lite.Class8.callSite_1 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "ToLower", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target2 = Lite.Class8.callSite_1.Target;
		CallSite callSite_2 = Lite.Class8.callSite_1;
		if (Lite.Class8.callSite_0 == null)
		{
			Lite.Class8.callSite_0 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "ToString", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		object arg = target(callSite_, typeFromHandle, target2(callSite_2, Lite.Class8.callSite_0.Target(Lite.Class8.callSite_0, this.object_0)));
		if (Lite.Class8.callSite_8 == null)
		{
			Lite.Class8.callSite_8 = CallSite<Func<CallSite, object, int>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.Convert(CSharpBinderFlags.None, typeof(int), typeof(Lite)));
		}
		Func<CallSite, object, int> target3 = Lite.Class8.callSite_8.Target;
		CallSite callSite_3 = Lite.Class8.callSite_8;
		if (Lite.Class8.callSite_7 == null)
		{
			Lite.Class8.callSite_7 = CallSite<Func<CallSite, Type, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "Parse", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, Type, object, object> target4 = Lite.Class8.callSite_7.Target;
		CallSite callSite_4 = Lite.Class8.callSite_7;
		Type typeFromHandle2 = typeof(int);
		if (Lite.Class8.callSite_6 == null)
		{
			Lite.Class8.callSite_6 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "ToString", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target5 = Lite.Class8.callSite_6.Target;
		CallSite callSite_5 = Lite.Class8.callSite_6;
		if (Lite.Class8.callSite_5 == null)
		{
			Lite.Class8.callSite_5 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "a", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target6 = Lite.Class8.callSite_5.Target;
		CallSite callSite_6 = Lite.Class8.callSite_5;
		if (Lite.Class8.callSite_4 == null)
		{
			Lite.Class8.callSite_4 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "keyints", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target7 = Lite.Class8.callSite_4.Target;
		CallSite callSite_7 = Lite.Class8.callSite_4;
		if (Lite.Class8.callSite_3 == null)
		{
			Lite.Class8.callSite_3 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "settings", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		this.int_9 = target3(callSite_3, target4(callSite_4, typeFromHandle2, target5(callSite_5, target6(callSite_6, target7(callSite_7, Lite.Class8.callSite_3.Target(Lite.Class8.callSite_3, arg))))));
		FlatTrackBar cpsbar = this.CPSBar;
		if (Lite.Class8.callSite_14 == null)
		{
			Lite.Class8.callSite_14 = CallSite<Func<CallSite, object, int>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.Convert(CSharpBinderFlags.None, typeof(int), typeof(Lite)));
		}
		Func<CallSite, object, int> target8 = Lite.Class8.callSite_14.Target;
		CallSite callSite_8 = Lite.Class8.callSite_14;
		if (Lite.Class8.callSite_13 == null)
		{
			Lite.Class8.callSite_13 = CallSite<Func<CallSite, Type, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "Parse", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, Type, object, object> target9 = Lite.Class8.callSite_13.Target;
		CallSite callSite_9 = Lite.Class8.callSite_13;
		Type typeFromHandle3 = typeof(int);
		if (Lite.Class8.callSite_12 == null)
		{
			Lite.Class8.callSite_12 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "ToString", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target10 = Lite.Class8.callSite_12.Target;
		CallSite callSite_10 = Lite.Class8.callSite_12;
		if (Lite.Class8.callSite_11 == null)
		{
			Lite.Class8.callSite_11 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "min", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target11 = Lite.Class8.callSite_11.Target;
		CallSite callSite_11 = Lite.Class8.callSite_11;
		if (Lite.Class8.callSite_10 == null)
		{
			Lite.Class8.callSite_10 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "cps", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target12 = Lite.Class8.callSite_10.Target;
		CallSite callSite_12 = Lite.Class8.callSite_10;
		if (Lite.Class8.callSite_9 == null)
		{
			Lite.Class8.callSite_9 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "settings", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		cpsbar.Value = target8(callSite_8, target9(callSite_9, typeFromHandle3, target10(callSite_10, target11(callSite_11, target12(callSite_12, Lite.Class8.callSite_9.Target(Lite.Class8.callSite_9, arg))))));
		FlatTrackBar cpsbar2 = this.CPSBar;
		if (Lite.Class8.callSite_20 == null)
		{
			Lite.Class8.callSite_20 = CallSite<Func<CallSite, object, int>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.Convert(CSharpBinderFlags.None, typeof(int), typeof(Lite)));
		}
		Func<CallSite, object, int> target13 = Lite.Class8.callSite_20.Target;
		CallSite callSite_13 = Lite.Class8.callSite_20;
		if (Lite.Class8.callSite_19 == null)
		{
			Lite.Class8.callSite_19 = CallSite<Func<CallSite, Type, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "Parse", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, Type, object, object> target14 = Lite.Class8.callSite_19.Target;
		CallSite callSite_14 = Lite.Class8.callSite_19;
		Type typeFromHandle4 = typeof(int);
		if (Lite.Class8.callSite_18 == null)
		{
			Lite.Class8.callSite_18 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "ToString", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target15 = Lite.Class8.callSite_18.Target;
		CallSite callSite_15 = Lite.Class8.callSite_18;
		if (Lite.Class8.callSite_17 == null)
		{
			Lite.Class8.callSite_17 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "max", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target16 = Lite.Class8.callSite_17.Target;
		CallSite callSite_16 = Lite.Class8.callSite_17;
		if (Lite.Class8.callSite_16 == null)
		{
			Lite.Class8.callSite_16 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "cps", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target17 = Lite.Class8.callSite_16.Target;
		CallSite callSite_17 = Lite.Class8.callSite_16;
		if (Lite.Class8.callSite_15 == null)
		{
			Lite.Class8.callSite_15 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "settings", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		BunifuCheckbox bunifuCheckbox = this.constantClickToggle;
		if (Lite.Class8.callSite_25 == null)
		{
			Lite.Class8.callSite_25 = CallSite<Func<CallSite, object, bool>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.Convert(CSharpBinderFlags.None, typeof(bool), typeof(Lite)));
		}
		Func<CallSite, object, bool> target18 = Lite.Class8.callSite_25.Target;
		CallSite callSite_18 = Lite.Class8.callSite_25;
		if (Lite.Class8.callSite_24 == null)
		{
			Lite.Class8.callSite_24 = CallSite<Func<CallSite, Type, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "Parse", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, Type, object, object> target19 = Lite.Class8.callSite_24.Target;
		CallSite callSite_19 = Lite.Class8.callSite_24;
		Type typeFromHandle5 = typeof(bool);
		if (Lite.Class8.callSite_23 == null)
		{
			Lite.Class8.callSite_23 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "ToString", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target20 = Lite.Class8.callSite_23.Target;
		CallSite callSite_20 = Lite.Class8.callSite_23;
		if (Lite.Class8.callSite_22 == null)
		{
			Lite.Class8.callSite_22 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "constant", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target21 = Lite.Class8.callSite_22.Target;
		CallSite callSite_21 = Lite.Class8.callSite_22;
		if (Lite.Class8.callSite_21 == null)
		{
			Lite.Class8.callSite_21 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "settings", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		bunifuCheckbox.Checked = target18(callSite_18, target19(callSite_19, typeFromHandle5, target20(callSite_20, target21(callSite_21, Lite.Class8.callSite_21.Target(Lite.Class8.callSite_21, arg)))));
		BunifuCheckbox bunifuCheckbox2 = this.blockhitModeToggle;
		if (Lite.Class8.callSite_30 == null)
		{
			Lite.Class8.callSite_30 = CallSite<Func<CallSite, object, bool>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.Convert(CSharpBinderFlags.None, typeof(bool), typeof(Lite)));
		}
		Func<CallSite, object, bool> target22 = Lite.Class8.callSite_30.Target;
		CallSite callSite_22 = Lite.Class8.callSite_30;
		if (Lite.Class8.callSite_29 == null)
		{
			Lite.Class8.callSite_29 = CallSite<Func<CallSite, Type, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "Parse", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, Type, object, object> target23 = Lite.Class8.callSite_29.Target;
		CallSite callSite_23 = Lite.Class8.callSite_29;
		Type typeFromHandle6 = typeof(bool);
		if (Lite.Class8.callSite_28 == null)
		{
			Lite.Class8.callSite_28 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "ToString", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target24 = Lite.Class8.callSite_28.Target;
		CallSite callSite_24 = Lite.Class8.callSite_28;
		if (Lite.Class8.callSite_27 == null)
		{
			Lite.Class8.callSite_27 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "blockhit", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target25 = Lite.Class8.callSite_27.Target;
		CallSite callSite_25 = Lite.Class8.callSite_27;
		if (Lite.Class8.callSite_26 == null)
		{
			Lite.Class8.callSite_26 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "settings", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		bunifuCheckbox2.Checked = target22(callSite_22, target23(callSite_23, typeFromHandle6, target24(callSite_24, target25(callSite_25, Lite.Class8.callSite_26.Target(Lite.Class8.callSite_26, arg)))));
		FlatToggle flatToggle = this.clickerToggle;
		if (Lite.Class8.callSite_35 == null)
		{
			Lite.Class8.callSite_35 = CallSite<Func<CallSite, object, bool>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.Convert(CSharpBinderFlags.None, typeof(bool), typeof(Lite)));
		}
		Func<CallSite, object, bool> target26 = Lite.Class8.callSite_35.Target;
		CallSite callSite_26 = Lite.Class8.callSite_35;
		if (Lite.Class8.callSite_34 == null)
		{
			Lite.Class8.callSite_34 = CallSite<Func<CallSite, Type, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "Parse", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, Type, object, object> target27 = Lite.Class8.callSite_34.Target;
		CallSite callSite_27 = Lite.Class8.callSite_34;
		Type typeFromHandle7 = typeof(bool);
		if (Lite.Class8.callSite_33 == null)
		{
			Lite.Class8.callSite_33 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "ToString", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target28 = Lite.Class8.callSite_33.Target;
		CallSite callSite_28 = Lite.Class8.callSite_33;
		if (Lite.Class8.callSite_32 == null)
		{
			Lite.Class8.callSite_32 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "toggled", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target29 = Lite.Class8.callSite_32.Target;
		CallSite callSite_29 = Lite.Class8.callSite_32;
		if (Lite.Class8.callSite_31 == null)
		{
			Lite.Class8.callSite_31 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "settings", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		flatToggle.Checked = target26(callSite_26, target27(callSite_27, typeFromHandle7, target28(callSite_28, target29(callSite_29, Lite.Class8.callSite_31.Target(Lite.Class8.callSite_31, arg)))));
		FlatToggle flatToggle2 = this.specificWindowToggle;
		if (Lite.Class8.callSite_41 == null)
		{
			Lite.Class8.callSite_41 = CallSite<Func<CallSite, object, bool>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.Convert(CSharpBinderFlags.None, typeof(bool), typeof(Lite)));
		}
		Func<CallSite, object, bool> target30 = Lite.Class8.callSite_41.Target;
		CallSite callSite_30 = Lite.Class8.callSite_41;
		if (Lite.Class8.callSite_40 == null)
		{
			Lite.Class8.callSite_40 = CallSite<Func<CallSite, Type, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "Parse", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, Type, object, object> target31 = Lite.Class8.callSite_40.Target;
		CallSite callSite_31 = Lite.Class8.callSite_40;
		Type typeFromHandle8 = typeof(bool);
		if (Lite.Class8.callSite_39 == null)
		{
			Lite.Class8.callSite_39 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "ToString", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target32 = Lite.Class8.callSite_39.Target;
		CallSite callSite_32 = Lite.Class8.callSite_39;
		if (Lite.Class8.callSite_38 == null)
		{
			Lite.Class8.callSite_38 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "toggled", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target33 = Lite.Class8.callSite_38.Target;
		CallSite callSite_33 = Lite.Class8.callSite_38;
		if (Lite.Class8.callSite_37 == null)
		{
			Lite.Class8.callSite_37 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "specific", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target34 = Lite.Class8.callSite_37.Target;
		CallSite callSite_34 = Lite.Class8.callSite_37;
		if (Lite.Class8.callSite_36 == null)
		{
			Lite.Class8.callSite_36 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "settings", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		flatToggle2.Checked = target30(callSite_30, target31(callSite_31, typeFromHandle8, target32(callSite_32, target33(callSite_33, target34(callSite_34, Lite.Class8.callSite_36.Target(Lite.Class8.callSite_36, arg))))));
		BunifuCheckbox bunifuCheckbox3 = this.destructToggle;
		if (Lite.Class8.callSite_47 == null)
		{
			Lite.Class8.callSite_47 = CallSite<Func<CallSite, object, bool>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.Convert(CSharpBinderFlags.None, typeof(bool), typeof(Lite)));
		}
		Func<CallSite, object, bool> target35 = Lite.Class8.callSite_47.Target;
		CallSite callSite_35 = Lite.Class8.callSite_47;
		if (Lite.Class8.callSite_46 == null)
		{
			Lite.Class8.callSite_46 = CallSite<Func<CallSite, Type, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "Parse", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, Type, object, object> target36 = Lite.Class8.callSite_46.Target;
		CallSite callSite_36 = Lite.Class8.callSite_46;
		Type typeFromHandle9 = typeof(bool);
		if (Lite.Class8.callSite_45 == null)
		{
			Lite.Class8.callSite_45 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "ToString", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target37 = Lite.Class8.callSite_45.Target;
		CallSite callSite_37 = Lite.Class8.callSite_45;
		if (Lite.Class8.callSite_44 == null)
		{
			Lite.Class8.callSite_44 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "exit", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target38 = Lite.Class8.callSite_44.Target;
		CallSite callSite_38 = Lite.Class8.callSite_44;
		if (Lite.Class8.callSite_43 == null)
		{
			Lite.Class8.callSite_43 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "destruct", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target39 = Lite.Class8.callSite_43.Target;
		CallSite callSite_39 = Lite.Class8.callSite_43;
		if (Lite.Class8.callSite_42 == null)
		{
			Lite.Class8.callSite_42 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "settings", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		bunifuCheckbox3.Checked = target35(callSite_35, target36(callSite_36, typeFromHandle9, target37(callSite_37, target38(callSite_38, target39(callSite_39, Lite.Class8.callSite_42.Target(Lite.Class8.callSite_42, arg))))));
		BunifuCheckbox bunifuCheckbox4 = this.memoryToggle;
		if (Lite.Class8.callSite_53 == null)
		{
			Lite.Class8.callSite_53 = CallSite<Func<CallSite, object, bool>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.Convert(CSharpBinderFlags.None, typeof(bool), typeof(Lite)));
		}
		Func<CallSite, object, bool> target40 = Lite.Class8.callSite_53.Target;
		CallSite callSite_40 = Lite.Class8.callSite_53;
		if (Lite.Class8.callSite_52 == null)
		{
			Lite.Class8.callSite_52 = CallSite<Func<CallSite, Type, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "Parse", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, Type, object, object> target41 = Lite.Class8.callSite_52.Target;
		CallSite callSite_41 = Lite.Class8.callSite_52;
		Type typeFromHandle10 = typeof(bool);
		if (Lite.Class8.callSite_51 == null)
		{
			Lite.Class8.callSite_51 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "ToString", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target42 = Lite.Class8.callSite_51.Target;
		CallSite callSite_42 = Lite.Class8.callSite_51;
		if (Lite.Class8.callSite_50 == null)
		{
			Lite.Class8.callSite_50 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "memory", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target43 = Lite.Class8.callSite_50.Target;
		CallSite callSite_43 = Lite.Class8.callSite_50;
		if (Lite.Class8.callSite_49 == null)
		{
			Lite.Class8.callSite_49 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "destruct", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target44 = Lite.Class8.callSite_49.Target;
		CallSite callSite_44 = Lite.Class8.callSite_49;
		if (Lite.Class8.callSite_48 == null)
		{
			Lite.Class8.callSite_48 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "settings", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		bunifuCheckbox4.Checked = target40(callSite_40, target41(callSite_41, typeFromHandle10, target42(callSite_42, target43(callSite_43, target44(callSite_44, Lite.Class8.callSite_48.Target(Lite.Class8.callSite_48, arg))))));
		BunifuCheckbox bunifuCheckbox5 = this.prefetchToggle;
		if (Lite.Class8.callSite_59 == null)
		{
			Lite.Class8.callSite_59 = CallSite<Func<CallSite, object, bool>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.Convert(CSharpBinderFlags.None, typeof(bool), typeof(Lite)));
		}
		Func<CallSite, object, bool> target45 = Lite.Class8.callSite_59.Target;
		CallSite callSite_45 = Lite.Class8.callSite_59;
		if (Lite.Class8.callSite_58 == null)
		{
			Lite.Class8.callSite_58 = CallSite<Func<CallSite, Type, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "Parse", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, Type, object, object> target46 = Lite.Class8.callSite_58.Target;
		CallSite callSite_46 = Lite.Class8.callSite_58;
		Type typeFromHandle11 = typeof(bool);
		if (Lite.Class8.callSite_57 == null)
		{
			Lite.Class8.callSite_57 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "ToString", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target47 = Lite.Class8.callSite_57.Target;
		CallSite callSite_47 = Lite.Class8.callSite_57;
		if (Lite.Class8.callSite_56 == null)
		{
			Lite.Class8.callSite_56 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "prefetch", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target48 = Lite.Class8.callSite_56.Target;
		CallSite callSite_48 = Lite.Class8.callSite_56;
		if (Lite.Class8.callSite_55 == null)
		{
			Lite.Class8.callSite_55 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "destruct", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target49 = Lite.Class8.callSite_55.Target;
		CallSite callSite_49 = Lite.Class8.callSite_55;
		if (Lite.Class8.callSite_54 == null)
		{
			Lite.Class8.callSite_54 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "settings", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		bunifuCheckbox5.Checked = target45(callSite_45, target46(callSite_46, typeFromHandle11, target47(callSite_47, target48(callSite_48, target49(callSite_49, Lite.Class8.callSite_54.Target(Lite.Class8.callSite_54, arg))))));
		BunifuCheckbox bunifuCheckbox6 = this.filesToggle;
		if (Lite.Class8.callSite_65 == null)
		{
			Lite.Class8.callSite_65 = CallSite<Func<CallSite, object, bool>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.Convert(CSharpBinderFlags.None, typeof(bool), typeof(Lite)));
		}
		Func<CallSite, object, bool> target50 = Lite.Class8.callSite_65.Target;
		CallSite callSite_50 = Lite.Class8.callSite_65;
		if (Lite.Class8.callSite_64 == null)
		{
			Lite.Class8.callSite_64 = CallSite<Func<CallSite, Type, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "Parse", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, Type, object, object> target51 = Lite.Class8.callSite_64.Target;
		CallSite callSite_51 = Lite.Class8.callSite_64;
		Type typeFromHandle12 = typeof(bool);
		if (Lite.Class8.callSite_63 == null)
		{
			Lite.Class8.callSite_63 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.InvokeMember(CSharpBinderFlags.None, "ToString", null, typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target52 = Lite.Class8.callSite_63.Target;
		CallSite callSite_52 = Lite.Class8.callSite_63;
		if (Lite.Class8.callSite_62 == null)
		{
			Lite.Class8.callSite_62 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "files", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target53 = Lite.Class8.callSite_62.Target;
		CallSite callSite_53 = Lite.Class8.callSite_62;
		if (Lite.Class8.callSite_61 == null)
		{
			Lite.Class8.callSite_61 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "destruct", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		Func<CallSite, object, object> target54 = Lite.Class8.callSite_61.Target;
		CallSite callSite_54 = Lite.Class8.callSite_61;
		if (Lite.Class8.callSite_60 == null)
		{
			Lite.Class8.callSite_60 = CallSite<Func<CallSite, object, object>>.Create(Microsoft.CSharp.RuntimeBinder.Binder.GetMember(CSharpBinderFlags.None, "settings", typeof(Lite), new CSharpArgumentInfo[]
			{
				CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
			}));
		}
		bunifuCheckbox6.Checked = target50(callSite_50, target51(callSite_51, typeFromHandle12, target52(callSite_52, target53(callSite_53, target54(callSite_54, Lite.Class8.callSite_60.Target(Lite.Class8.callSite_60, arg))))));
	}

	// Token: 0x06000061 RID: 97 RVA: 0x00002050 File Offset: 0x00000250
	private void tabPage2_Click(object sender, EventArgs e)
	{
	}

	// Token: 0x06000062 RID: 98 RVA: 0x00004DC0 File Offset: 0x00002FC0
	private void flatButton1_MouseClick(object sender, MouseEventArgs e)
	{
		if (e.Button != MouseButtons.Middle)
		{
			this.flatButton1.Focus();
		}
	}

	// Token: 0x06000063 RID: 99 RVA: 0x00004DE8 File Offset: 0x00002FE8
	private void flatButton1_Enter(object sender, EventArgs e)
	{
		this.flatButton1.Text = "> " + this.string_3 + " <";
		this.flatButton1.Refresh();
	}

	// Token: 0x06000064 RID: 100 RVA: 0x00004E20 File Offset: 0x00003020
	private void flatButton1_Leave(object sender, EventArgs e)
	{
		this.flatButton1.Text = "< " + this.string_3 + " >";
		this.flatButton1.Refresh();
	}

	// Token: 0x06000065 RID: 101 RVA: 0x00004E58 File Offset: 0x00003058
	private void method_17(object sender, KeyEventArgs e)
	{
		if (e.KeyCode.ToString() != "Escape")
		{
			this.string_3 = e.KeyCode.ToString();
			this.int_10 = e.KeyValue;
			this.clickerToggle.Focus();
		}
		else
		{
			this.string_3 = "NONE";
			this.int_10 = 0;
			this.clickerToggle.Focus();
		}
		this.flatButton1.Refresh();
	}

	// Token: 0x06000066 RID: 102 RVA: 0x00004EE4 File Offset: 0x000030E4
	private void flatButton1_MouseDown(object sender, MouseEventArgs e)
	{
		if (e.Button == MouseButtons.Middle && this.flatButton1.Focused)
		{
			this.int_10 = 6661;
			this.string_3 = "MClick";
			this.clickerToggle.Focus();
		}
	}

	// Token: 0x06000067 RID: 103 RVA: 0x00002050 File Offset: 0x00000250
	private void flatButton1_Click(object sender, EventArgs e)
	{
	}

	// Token: 0x06000068 RID: 104 RVA: 0x00004E58 File Offset: 0x00003058
	private void flatButton1_KeyDown(object sender, KeyEventArgs e)
	{
		if (e.KeyCode.ToString() != "Escape")
		{
			this.string_3 = e.KeyCode.ToString();
			this.int_10 = e.KeyValue;
			this.clickerToggle.Focus();
		}
		else
		{
			this.string_3 = "NONE";
			this.int_10 = 0;
			this.clickerToggle.Focus();
		}
		this.flatButton1.Refresh();
	}

	// Token: 0x06000069 RID: 105 RVA: 0x00002050 File Offset: 0x00000250
	private void tabPage1_Click(object sender, EventArgs e)
	{
	}

	// Token: 0x0600006A RID: 106 RVA: 0x00004F30 File Offset: 0x00003130
	private void timer_7_Tick(object sender, EventArgs e)
	{
		this.method_14();
	}

	// Token: 0x0600006B RID: 107 RVA: 0x00004F44 File Offset: 0x00003144
	protected virtual void Dispose(bool disposing)
	{
		if (disposing && this.icontainer_0 != null)
		{
			this.icontainer_0.Dispose();
		}
		base.Dispose(disposing);
	}

	// Token: 0x04000017 RID: 23
	private Class12 class12_0 = new Class12();

	// Token: 0x04000018 RID: 24
	private Class9 class9_0 = new Class9();

	// Token: 0x04000019 RID: 25
	private string string_0;

	// Token: 0x0400001A RID: 26
	private string string_1 = "v3.4";

	// Token: 0x0400001B RID: 27
	private int int_0;

	// Token: 0x0400001C RID: 28
	private int int_1;

	// Token: 0x0400001D RID: 29
	private bool bool_0;

	// Token: 0x0400001E RID: 30
	private bool bool_1;

	// Token: 0x0400001F RID: 31
	private bool bool_2;

	// Token: 0x04000020 RID: 32
	private int int_2;

	// Token: 0x04000021 RID: 33
	private bool bool_3;

	// Token: 0x04000022 RID: 34
	private int int_3;

	// Token: 0x04000023 RID: 35
	private int int_4;

	// Token: 0x04000024 RID: 36
	private bool bool_4;

	// Token: 0x04000025 RID: 37
	private int int_5;

	// Token: 0x04000026 RID: 38
	private int int_6;

	// Token: 0x04000027 RID: 39
	private int int_7 = 50;

	// Token: 0x04000028 RID: 40
	private int int_8 = 40;

	// Token: 0x04000029 RID: 41
	private string string_2 = "NONE";

	// Token: 0x0400002A RID: 42
	private int int_9;

	// Token: 0x0400002B RID: 43
	private string string_3 = "NONE";

	// Token: 0x0400002C RID: 44
	private int int_10;

	// Token: 0x0400002D RID: 45
	private int int_11 = 85;

	// Token: 0x0400002E RID: 46
	private int int_12 = 120;

	// Token: 0x0400002F RID: 47
	private int int_13 = 85;

	// Token: 0x04000030 RID: 48
	private int int_14 = 120;

	// Token: 0x04000031 RID: 49
	private string string_4;

	// Token: 0x04000032 RID: 50
	private bool bool_5;

	// Token: 0x04000033 RID: 51
	private int int_15;

	// Token: 0x04000034 RID: 52
	private Class6 class6_0;

	// Token: 0x04000035 RID: 53
	private const int int_16 = 256;

	// Token: 0x04000036 RID: 54
	private const int int_17 = 14;

	// Token: 0x04000037 RID: 55
	private static Lite.Delegate1 delegate1_0 = new Lite.Delegate1(Lite.smethod_0);

	// Token: 0x04000038 RID: 56
	private static IntPtr intptr_0 = IntPtr.Zero;

	// Token: 0x04000039 RID: 57
	[Dynamic]
	private dynamic object_0;

	// Token: 0x02000009 RID: 9
	// (Invoke) Token: 0x0600006F RID: 111
	private delegate IntPtr Delegate1(int nCode, IntPtr wParam, IntPtr lParam);

	// Token: 0x0200000A RID: 10
	private enum Enum1
	{
		// Token: 0x04000076 RID: 118
		WM_LBUTTONDOWN = 513,
		// Token: 0x04000077 RID: 119
		WM_LBUTTONUP,
		// Token: 0x04000078 RID: 120
		WM_MOUSEMOVE = 512,
		// Token: 0x04000079 RID: 121
		WM_MOUSEWHEEL = 522,
		// Token: 0x0400007A RID: 122
		WM_RBUTTONDOWN = 516,
		// Token: 0x0400007B RID: 123
		WM_RBUTTONUP,
		// Token: 0x0400007C RID: 124
		WM_MBUTTONDOWN = 519,
		// Token: 0x0400007D RID: 125
		WM_MBUTTONUP
	}

	// Token: 0x0200000B RID: 11
	private struct Struct7
	{
		// Token: 0x0400007E RID: 126
		public Lite.Struct8 struct8_0;

		// Token: 0x0400007F RID: 127
		public uint uint_0;

		// Token: 0x04000080 RID: 128
		public uint uint_1;

		// Token: 0x04000081 RID: 129
		public uint uint_2;

		// Token: 0x04000082 RID: 130
		public IntPtr intptr_0;
	}

	// Token: 0x0200000C RID: 12
	private struct Struct8
	{
		// Token: 0x04000083 RID: 131
		public int int_0;

		// Token: 0x04000084 RID: 132
		public int int_1;
	}

	// Token: 0x0200000D RID: 13
	[CompilerGenerated]
	private static class Class7
	{
		// Token: 0x04000085 RID: 133
		public static CallSite<Func<CallSite, object, object>> callSite_0;

		// Token: 0x04000086 RID: 134
		public static CallSite<Func<CallSite, object, string>> callSite_1;
	}

	// Token: 0x0200000E RID: 14
	[CompilerGenerated]
	private static class Class8
	{
		// Token: 0x04000087 RID: 135
		public static CallSite<Func<CallSite, object, object>> callSite_0;

		// Token: 0x04000088 RID: 136
		public static CallSite<Func<CallSite, object, object>> callSite_1;

		// Token: 0x04000089 RID: 137
		public static CallSite<Func<CallSite, Type, object, object>> callSite_2;

		// Token: 0x0400008A RID: 138
		public static CallSite<Func<CallSite, object, object>> callSite_3;

		// Token: 0x0400008B RID: 139
		public static CallSite<Func<CallSite, object, object>> callSite_4;

		// Token: 0x0400008C RID: 140
		public static CallSite<Func<CallSite, object, object>> callSite_5;

		// Token: 0x0400008D RID: 141
		public static CallSite<Func<CallSite, object, object>> callSite_6;

		// Token: 0x0400008E RID: 142
		public static CallSite<Func<CallSite, Type, object, object>> callSite_7;

		// Token: 0x0400008F RID: 143
		public static CallSite<Func<CallSite, object, int>> callSite_8;

		// Token: 0x04000090 RID: 144
		public static CallSite<Func<CallSite, object, object>> callSite_9;

		// Token: 0x04000091 RID: 145
		public static CallSite<Func<CallSite, object, object>> callSite_10;

		// Token: 0x04000092 RID: 146
		public static CallSite<Func<CallSite, object, object>> callSite_11;

		// Token: 0x04000093 RID: 147
		public static CallSite<Func<CallSite, object, object>> callSite_12;

		// Token: 0x04000094 RID: 148
		public static CallSite<Func<CallSite, Type, object, object>> callSite_13;

		// Token: 0x04000095 RID: 149
		public static CallSite<Func<CallSite, object, int>> callSite_14;

		// Token: 0x04000096 RID: 150
		public static CallSite<Func<CallSite, object, object>> callSite_15;

		// Token: 0x04000097 RID: 151
		public static CallSite<Func<CallSite, object, object>> callSite_16;

		// Token: 0x04000098 RID: 152
		public static CallSite<Func<CallSite, object, object>> callSite_17;

		// Token: 0x04000099 RID: 153
		public static CallSite<Func<CallSite, object, object>> callSite_18;

		// Token: 0x0400009A RID: 154
		public static CallSite<Func<CallSite, Type, object, object>> callSite_19;

		// Token: 0x0400009B RID: 155
		public static CallSite<Func<CallSite, object, int>> callSite_20;

		// Token: 0x0400009C RID: 156
		public static CallSite<Func<CallSite, object, object>> callSite_21;

		// Token: 0x0400009D RID: 157
		public static CallSite<Func<CallSite, object, object>> callSite_22;

		// Token: 0x0400009E RID: 158
		public static CallSite<Func<CallSite, object, object>> callSite_23;

		// Token: 0x0400009F RID: 159
		public static CallSite<Func<CallSite, Type, object, object>> callSite_24;

		// Token: 0x040000A0 RID: 160
		public static CallSite<Func<CallSite, object, bool>> callSite_25;

		// Token: 0x040000A1 RID: 161
		public static CallSite<Func<CallSite, object, object>> callSite_26;

		// Token: 0x040000A2 RID: 162
		public static CallSite<Func<CallSite, object, object>> callSite_27;

		// Token: 0x040000A3 RID: 163
		public static CallSite<Func<CallSite, object, object>> callSite_28;

		// Token: 0x040000A4 RID: 164
		public static CallSite<Func<CallSite, Type, object, object>> callSite_29;

		// Token: 0x040000A5 RID: 165
		public static CallSite<Func<CallSite, object, bool>> callSite_30;

		// Token: 0x040000A6 RID: 166
		public static CallSite<Func<CallSite, object, object>> callSite_31;

		// Token: 0x040000A7 RID: 167
		public static CallSite<Func<CallSite, object, object>> callSite_32;

		// Token: 0x040000A8 RID: 168
		public static CallSite<Func<CallSite, object, object>> callSite_33;

		// Token: 0x040000A9 RID: 169
		public static CallSite<Func<CallSite, Type, object, object>> callSite_34;

		// Token: 0x040000AA RID: 170
		public static CallSite<Func<CallSite, object, bool>> callSite_35;

		// Token: 0x040000AB RID: 171
		public static CallSite<Func<CallSite, object, object>> callSite_36;

		// Token: 0x040000AC RID: 172
		public static CallSite<Func<CallSite, object, object>> callSite_37;

		// Token: 0x040000AD RID: 173
		public static CallSite<Func<CallSite, object, object>> callSite_38;

		// Token: 0x040000AE RID: 174
		public static CallSite<Func<CallSite, object, object>> callSite_39;

		// Token: 0x040000AF RID: 175
		public static CallSite<Func<CallSite, Type, object, object>> callSite_40;

		// Token: 0x040000B0 RID: 176
		public static CallSite<Func<CallSite, object, bool>> callSite_41;

		// Token: 0x040000B1 RID: 177
		public static CallSite<Func<CallSite, object, object>> callSite_42;

		// Token: 0x040000B2 RID: 178
		public static CallSite<Func<CallSite, object, object>> callSite_43;

		// Token: 0x040000B3 RID: 179
		public static CallSite<Func<CallSite, object, object>> callSite_44;

		// Token: 0x040000B4 RID: 180
		public static CallSite<Func<CallSite, object, object>> callSite_45;

		// Token: 0x040000B5 RID: 181
		public static CallSite<Func<CallSite, Type, object, object>> callSite_46;

		// Token: 0x040000B6 RID: 182
		public static CallSite<Func<CallSite, object, bool>> callSite_47;

		// Token: 0x040000B7 RID: 183
		public static CallSite<Func<CallSite, object, object>> callSite_48;

		// Token: 0x040000B8 RID: 184
		public static CallSite<Func<CallSite, object, object>> callSite_49;

		// Token: 0x040000B9 RID: 185
		public static CallSite<Func<CallSite, object, object>> callSite_50;

		// Token: 0x040000BA RID: 186
		public static CallSite<Func<CallSite, object, object>> callSite_51;

		// Token: 0x040000BB RID: 187
		public static CallSite<Func<CallSite, Type, object, object>> callSite_52;

		// Token: 0x040000BC RID: 188
		public static CallSite<Func<CallSite, object, bool>> callSite_53;

		// Token: 0x040000BD RID: 189
		public static CallSite<Func<CallSite, object, object>> callSite_54;

		// Token: 0x040000BE RID: 190
		public static CallSite<Func<CallSite, object, object>> callSite_55;

		// Token: 0x040000BF RID: 191
		public static CallSite<Func<CallSite, object, object>> callSite_56;

		// Token: 0x040000C0 RID: 192
		public static CallSite<Func<CallSite, object, object>> callSite_57;

		// Token: 0x040000C1 RID: 193
		public static CallSite<Func<CallSite, Type, object, object>> callSite_58;

		// Token: 0x040000C2 RID: 194
		public static CallSite<Func<CallSite, object, bool>> callSite_59;

		// Token: 0x040000C3 RID: 195
		public static CallSite<Func<CallSite, object, object>> callSite_60;

		// Token: 0x040000C4 RID: 196
		public static CallSite<Func<CallSite, object, object>> callSite_61;

		// Token: 0x040000C5 RID: 197
		public static CallSite<Func<CallSite, object, object>> callSite_62;

		// Token: 0x040000C6 RID: 198
		public static CallSite<Func<CallSite, object, object>> callSite_63;

		// Token: 0x040000C7 RID: 199
		public static CallSite<Func<CallSite, Type, object, object>> callSite_64;

		// Token: 0x040000C8 RID: 200
		public static CallSite<Func<CallSite, object, bool>> callSite_65;
	}
}
