using System;
using System.Collections;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;
using svchost.My;

namespace svchost
{
	// Token: 0x0200000C RID: 12
	[DesignerGenerated]
	public partial class Form1 : Form
	{
		// Token: 0x06000053 RID: 83 RVA: 0x0000424C File Offset: 0x0000244C
		public Form1()
		{
			base.Load += this.Form1_Load;
			base.FormClosing += this.Form1_FormClosing;
			base.Closing += this.Form1_Closing;
			this.randVal = 15;
			this.tempCps = 0;
			this.InitializeComponent();
		}

		// Token: 0x06000054 RID: 84
		[DllImport("user32.dll", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

		// Token: 0x06000055 RID: 85
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern short GetAsyncKeyState(int vkey);

		// Token: 0x06000056 RID: 86 RVA: 0x000042BC File Offset: 0x000024BC
		[MethodImpl(MethodImplOptions.NoInlining | MethodImplOptions.NoOptimization)]
		private object Err(object exception)
		{
			try
			{
				Interaction.MsgBox("Critical Error has occured! please send bellow to developer: " + exception.ToString(), MsgBoxStyle.Critical, "CRITICAL ERROR!");
			}
			catch (Exception ex)
			{
				ProjectData.EndApp();
			}
			object Err;
			return Err;
		}

		// Token: 0x06000057 RID: 87 RVA: 0x00004314 File Offset: 0x00002514
		private void Tick_Tick(object sender, EventArgs e)
		{
			try
			{
				this.mainTabcontrol.Refresh();
				MySettingsProperty.Settings.gaymode = this.LogInCheckBox2.Checked;
				this.LogInStatusBar1.Text = "RIVALS, CRACKED BY Elegant Team";
				Color color = Color.FromArgb(this.colorR.Value, this.colorG.Value, this.colorB.Value);
				this.Panel1.BackColor = color;
				this.LogInStatusBar1.RectangleColor = color;
				this.LogInStatusBar1.ForeColor = color;
				MySettingsProperty.Settings.mainColor = color;
				this.mainTabcontrol.HorizontalLineColour = color;
				this.mainTabcontrol.UpLineColour = color;
				this.secondaryTabControl.HorizontalLineColour = color;
				this.secondaryTabControl.UpLineColour = color;
				this.LogInTrackBar4.StripAmountColour = color;
				this.baseValue.StripAmountColour = color;
				this.jitterValue.StripAmountColour = color;
				MySettingsProperty.Settings.jitterValue = this.LogInTrackBar4.Value;
				this.multiplierVal.StripAmountColour = color;
				this.LogInStatusBar1.RectangleColor = color;
				MySettingsProperty.Settings.baseSpeed = (double)this.baseValue.Value;
				MySettingsProperty.Settings.jitterSpeed = (double)this.jitterValue.Value;
				MySettingsProperty.Settings.Overclock = (double)this.multiplierVal.Value;
				MySettingsProperty.Settings.bind = Conversions.ToString(this.LogInComboBox1.SelectedIndex);
				MySettingsProperty.Settings.bindb = checked((byte)this.LogInComboBox2.SelectedIndex);
				MySettingsProperty.Settings.Save();
			}
			catch (Exception ex)
			{
				this.Err(ex.Message);
			}
		}

		// Token: 0x06000058 RID: 88 RVA: 0x000044D4 File Offset: 0x000026D4
		private void Form1_Load(object sender, EventArgs e)
		{
			checked
			{
				try
				{
					Directory.CreateDirectory(Path.GetTempPath() + "/Rivals Click Sound");
					MyProject.Forms.cpshandler.Show();
					this.colorR.Value = (int)MySettingsProperty.Settings.mainColor.R;
					this.colorG.Value = (int)MySettingsProperty.Settings.mainColor.G;
					this.colorB.Value = (int)MySettingsProperty.Settings.mainColor.B;
					this.LogInCheckBox1.Checked = MySettingsProperty.Settings.EnableAutoclicker;
					this.LogInGroupBox2.Enabled = this.LogInCheckBox1.Checked;
					this.LogInGroupBox3.Enabled = this.LogInCheckBox1.Checked;
					this.LogInCheckBox3.Checked = MySettingsProperty.Settings.EnableClicksounds;
					this.LogInCheckBox8.Checked = MySettingsProperty.Settings.EnableJitter;
					this.LogInCheckBox6.Checked = MySettingsProperty.Settings.blockhit;
					this.LogInTrackBar4.Value = MySettingsProperty.Settings.jitterValue;
					this.LogInCheckBox2.Checked = MySettingsProperty.Settings.gaymode;
					bool gaymode = MySettingsProperty.Settings.gaymode;
					if (gaymode)
					{
						this.rainbow.Start();
					}
					else
					{
						this.rainbow.Stop();
					}
					MySettingsProperty.Settings.Save();
					this.baseValue.Value = (int)Math.Round(MySettingsProperty.Settings.baseSpeed);
					this.jitterValue.Value = (int)Math.Round(MySettingsProperty.Settings.jitterSpeed);
					this.multiplierVal.Value = (int)Math.Round(MySettingsProperty.Settings.Overclock);
					try
					{
					}
					catch (Exception ex2)
					{
					}
					this.LogInComboBox1.SelectedIndex = Conversions.ToInteger(MySettingsProperty.Settings.bind);
					this.LogInComboBox2.SelectedIndex = (int)MySettingsProperty.Settings.bindb;
				}
				catch (Exception ex)
				{
					this.Err(ex);
				}
			}
		}

		// Token: 0x06000059 RID: 89 RVA: 0x0000236F File Offset: 0x0000056F
		private void Form1_FormClosing(object sender, FormClosingEventArgs e)
		{
			Application.Exit();
		}

		// Token: 0x0600005A RID: 90 RVA: 0x00004730 File Offset: 0x00002930
		private void LogInCheckBox1_CheckedChanged(object @this)
		{
			MySettingsProperty.Settings.EnableAutoclicker = Conversions.ToBoolean(NewLateBinding.LateGet(@this, null, "Checked", new object[0], null, null, null));
			this.LogInGroupBox2.Enabled = Conversions.ToBoolean(NewLateBinding.LateGet(@this, null, "Checked", new object[0], null, null, null));
			this.LogInGroupBox3.Enabled = Conversions.ToBoolean(NewLateBinding.LateGet(@this, null, "Checked", new object[0], null, null, null));
		}

		// Token: 0x0600005B RID: 91 RVA: 0x000047B0 File Offset: 0x000029B0
		private void Autoclicker_Tick(object sender, EventArgs e)
		{
			checked
			{
				try
				{
					byte val2 = MySettingsProperty.Settings.bindb;
					MouseButtons key2 = MouseButtons.None;
					bool flag = val2 == 0;
					if (flag)
					{
						key2 = MouseButtons.Right;
					}
					else
					{
						bool flag2 = val2 == 1;
						if (flag2)
						{
							key2 = MouseButtons.Middle;
						}
						else
						{
							bool flag3 = val2 == 2;
							if (flag3)
							{
								key2 = MouseButtons.XButton1;
							}
							else
							{
								bool flag4 = val2 == 3;
								if (flag4)
								{
									key2 = MouseButtons.XButton2;
								}
								else
								{
									bool flag5 = val2 == 4;
									if (flag5)
									{
										key2 = MouseButtons.Left;
									}
								}
							}
						}
					}
					bool blockhit = MySettingsProperty.Settings.blockhit;
					if (blockhit)
					{
						bool flag6 = Operators.CompareString(Control.MouseButtons.HasFlag(key2).ToString(), "True", false) == 0;
						if (flag6)
						{
							Form1.mouse_event(8, 0, 0, 0, 1);
							Form1.mouse_event(16, 0, 0, 0, 1);
						}
					}
					Random rnd = new Random();
					this.autoclicker.Interval = this.baseValue.Value + 1 + rnd.Next(this.jitterValue.Value);
					bool @checked = this.LogInCheckBox1.Checked;
					if (@checked)
					{
						string val3 = MySettingsProperty.Settings.bind;
						MouseButtons key3 = MouseButtons.None;
						bool flag7 = Conversions.ToDouble(val3) == 0.0;
						if (flag7)
						{
							key3 = MouseButtons.Right;
						}
						else
						{
							bool flag8 = Conversions.ToDouble(val3) == 1.0;
							if (flag8)
							{
								key3 = MouseButtons.Middle;
							}
							else
							{
								bool flag9 = Conversions.ToDouble(val3) == 2.0;
								if (flag9)
								{
									key3 = MouseButtons.XButton1;
								}
								else
								{
									bool flag10 = Conversions.ToDouble(val3) == 3.0;
									if (flag10)
									{
										key3 = MouseButtons.XButton2;
									}
									else
									{
										bool flag11 = Conversions.ToDouble(val3) == 4.0;
										if (flag11)
										{
											key3 = MouseButtons.Left;
										}
									}
								}
							}
						}
						bool flag12 = Control.MouseButtons.HasFlag(key3);
						if (flag12)
						{
							bool checked2 = this.LogInCheckBox3.Checked;
							if (checked2)
							{
								try
								{
									this.PlayBackgroundSoundFile();
								}
								catch (Exception ex2)
								{
								}
							}
							bool checked3 = this.LogInCheckBox8.Checked;
							if (checked3)
							{
								int X = Cursor.Position.X;
								int Y = Cursor.Position.Y;
								Random rndX = new Random();
								Cursor.Position = new Point(X + rndX.Next(0 - this.LogInTrackBar4.Value, this.LogInTrackBar4.Value), Y + rndX.Next(0 - this.LogInTrackBar4.Value, this.LogInTrackBar4.Value));
							}
							int num = this.multiplierVal.Value + 1;
							for (int index = 1; index <= num; index++)
							{
								Form1.mouse_event(2, 0, 0, 0, 1);
								Form1.mouse_event(4, 0, 0, 0, 1);
							}
						}
					}
				}
				catch (Exception ex)
				{
					this.Err(ex.Message);
				}
			}
		}

		// Token: 0x0600005C RID: 92 RVA: 0x00002378 File Offset: 0x00000578
		private void LogInButton2_Click_1(object sender, EventArgs e)
		{
			Process.Start(Path.GetTempPath() + "/Rivals Click Sound");
		}

		// Token: 0x0600005D RID: 93 RVA: 0x0000236C File Offset: 0x0000056C
		private void OpenFileDialog1_FileOk(object sender, CancelEventArgs e)
		{
		}

		// Token: 0x0600005E RID: 94 RVA: 0x00004AF4 File Offset: 0x00002CF4
		private void Form1_Closing(object sender, CancelEventArgs e)
		{
			ArrayList a = new ArrayList();
			MySettingsProperty.Settings.clicksounds = a;
			MySettingsProperty.Settings.Save();
		}

		// Token: 0x0600005F RID: 95 RVA: 0x00004B20 File Offset: 0x00002D20
		public void PlayBackgroundSoundFile()
		{
			Random rnd = new Random();
		}

		// Token: 0x06000060 RID: 96 RVA: 0x00004B34 File Offset: 0x00002D34
		private void LogInButton4_Click(object sender, EventArgs e)
		{
			bool flag = Interaction.MsgBox("You are about to fully delete External from your computer, are you sure you want to proceed?", MsgBoxStyle.YesNo, "ATTENTION!") == MsgBoxResult.Yes;
			if (flag)
			{
				Process.Start("cmd.exe", "/c del " + Application.ExecutablePath);
				Application.Exit();
			}
		}

		// Token: 0x06000061 RID: 97 RVA: 0x00002390 File Offset: 0x00000590
		private void LogInCheckBox3_CheckedChanged(object sender)
		{
			MySettingsProperty.Settings.EnableClicksounds = this.LogInCheckBox3.Checked;
			MySettingsProperty.Settings.Save();
		}

		// Token: 0x06000062 RID: 98 RVA: 0x00004B7C File Offset: 0x00002D7C
		private void LogInCheckBox2_CheckedChanged(object sender)
		{
			bool flag = Conversions.ToBoolean(NewLateBinding.LateGet(sender, null, "Checked", new object[0], null, null, null));
			if (flag)
			{
				this.rainbow.Start();
			}
			else
			{
				this.rainbow.Stop();
			}
			MySettingsProperty.Settings.Save();
		}

		// Token: 0x06000063 RID: 99 RVA: 0x00004BD0 File Offset: 0x00002DD0
		private void Rainbow_Tick(object sender, EventArgs e)
		{
			Random rnd = new Random();
			this.colorR.Value = rnd.Next(255);
			this.colorG.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
			this.colorB.Value = rnd.Next(255);
		}

		// Token: 0x06000064 RID: 100 RVA: 0x000023B4 File Offset: 0x000005B4
		private void Track_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
		{
			NewLateBinding.LateCall(sender, null, "Refresh", new object[0], null, null, null, true);
		}

		// Token: 0x06000065 RID: 101 RVA: 0x000023B4 File Offset: 0x000005B4
		private void Track2_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
		{
			NewLateBinding.LateCall(sender, null, "Refresh", new object[0], null, null, null, true);
		}

		// Token: 0x06000066 RID: 102 RVA: 0x000023CE File Offset: 0x000005CE
		private void Resetclick_Tick(object sender, EventArgs e)
		{
			this.cpsAverage.Text = Conversions.ToString(this.clicks);
			this.clicks = 0;
		}

		// Token: 0x06000067 RID: 103 RVA: 0x000023EF File Offset: 0x000005EF
		private void ClickTestBtn_Click(object sender, EventArgs e)
		{
			checked
			{
				this.clicks++;
			}
		}

		// Token: 0x06000068 RID: 104 RVA: 0x00002400 File Offset: 0x00000600
		private void CspVal_Click(object sender, EventArgs e)
		{
			MyProject.Forms.cpshandler.Show();
		}

		// Token: 0x06000069 RID: 105 RVA: 0x00005230 File Offset: 0x00003430
		private void CspVal_TextChanged(object sender, EventArgs e)
		{
			try
			{
				NewLateBinding.LateSet(sender, null, "Tag", new object[]
				{
					Operators.AddObject(NewLateBinding.LateGet(sender, null, "Tag", new object[0], null, null, null), 1)
				}, null, null);
				this.tempCps = Operators.AddObject(this.tempCps, NewLateBinding.LateGet(sender, null, "Text", new object[0], null, null, null));
				bool flag = Operators.ConditionalCompareObjectGreater(NewLateBinding.LateGet(sender, null, "Tag", new object[0], null, null, null), 5, false);
				if (flag)
				{
					this.cpsAverage.Text = Conversions.ToString(Operators.DivideObject(this.tempCps, 5));
					this.tempCps = 0;
					NewLateBinding.LateSet(sender, null, "Tag", new object[]
					{
						0
					}, null, null);
				}
			}
			catch (Exception ex)
			{
			}
		}

		// Token: 0x0600006A RID: 106 RVA: 0x00002413 File Offset: 0x00000613
		private void LogInButton5_Click(object sender, EventArgs e)
		{
			this._C.Start();
			this.LogInButton5.Tag = Operators.AddObject(this.LogInButton5.Tag, 1);
		}

		// Token: 0x0600006B RID: 107 RVA: 0x00005334 File Offset: 0x00003534
		private void _C_Tick(object sender, EventArgs e)
		{
			this._C.Stop();
			this.LogInButton5.Text = Conversions.ToString(Operators.ConcatenateObject(Operators.DivideObject(this.LogInButton5.Tag, 5), " CPS"));
			this.LogInButton5.Tag = 0;
		}

		// Token: 0x0600006C RID: 108 RVA: 0x00002444 File Offset: 0x00000644
		private void LogInCheckBox4_CheckedChanged(object sender)
		{
			MySettingsProperty.Settings.autoLogin = Conversions.ToBoolean(NewLateBinding.LateGet(sender, null, "Checked", new object[0], null, null, null));
			MySettingsProperty.Settings.Save();
		}

		// Token: 0x0600006D RID: 109 RVA: 0x00002477 File Offset: 0x00000677
		private void LogInCheckBox6_CheckedChanged(object sender)
		{
			MySettingsProperty.Settings.blockhit = Conversions.ToBoolean(NewLateBinding.LateGet(sender, null, "Checked", new object[0], null, null, null));
			MySettingsProperty.Settings.Save();
		}

		// Token: 0x0600006E RID: 110 RVA: 0x0000236C File Offset: 0x0000056C
		private void LogInComboBox1_SelectedIndexChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x0600006F RID: 111 RVA: 0x0000236C File Offset: 0x0000056C
		private void LogInCheckBox8_CheckedChanged(object sender)
		{
		}

		// Token: 0x06000070 RID: 112 RVA: 0x0000236C File Offset: 0x0000056C
		private void LogInButton1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000071 RID: 113 RVA: 0x0000236C File Offset: 0x0000056C
		private void LogInThemeContainer1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000072 RID: 114 RVA: 0x0000236C File Offset: 0x0000056C
		private void LogInStatusBar1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000073 RID: 115 RVA: 0x0000236C File Offset: 0x0000056C
		private void LogInGroupBox8_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000074 RID: 116 RVA: 0x0000236C File Offset: 0x0000056C
		private void TabPage3_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000075 RID: 117 RVA: 0x0000236C File Offset: 0x0000056C
		private void Label1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000076 RID: 118 RVA: 0x0000236C File Offset: 0x0000056C
		private void LogInComboBox2_SelectedIndexChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x06000077 RID: 119 RVA: 0x0000236C File Offset: 0x0000056C
		private void LogInComboBox2_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000078 RID: 120 RVA: 0x0000236C File Offset: 0x0000056C
		private void autoclicker_Disposed(object sender, EventArgs e)
		{
		}

		// Token: 0x17000020 RID: 32
		// (get) Token: 0x0600007B RID: 123 RVA: 0x000024AA File Offset: 0x000006AA
		// (set) Token: 0x0600007C RID: 124 RVA: 0x00008D8C File Offset: 0x00006F8C
		internal virtual LogInThemeContainer LogInThemeContainer1
		{
			[CompilerGenerated]
			get
			{
				return this._LogInThemeContainer1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.LogInThemeContainer1_Click);
				LogInThemeContainer logInThemeContainer = this._LogInThemeContainer1;
				if (logInThemeContainer != null)
				{
					logInThemeContainer.Click -= value2;
				}
				this._LogInThemeContainer1 = value;
				logInThemeContainer = this._LogInThemeContainer1;
				if (logInThemeContainer != null)
				{
					logInThemeContainer.Click += value2;
				}
			}
		}

		// Token: 0x17000021 RID: 33
		// (get) Token: 0x0600007D RID: 125 RVA: 0x000024B4 File Offset: 0x000006B4
		// (set) Token: 0x0600007E RID: 126 RVA: 0x000024BE File Offset: 0x000006BE
		internal virtual LogInTabControl mainTabcontrol { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000022 RID: 34
		// (get) Token: 0x0600007F RID: 127 RVA: 0x000024C7 File Offset: 0x000006C7
		// (set) Token: 0x06000080 RID: 128 RVA: 0x000024D1 File Offset: 0x000006D1
		internal virtual TabPage TabPage1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000023 RID: 35
		// (get) Token: 0x06000081 RID: 129 RVA: 0x000024DA File Offset: 0x000006DA
		// (set) Token: 0x06000082 RID: 130 RVA: 0x000024E4 File Offset: 0x000006E4
		internal virtual LogInTabControl secondaryTabControl { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000024 RID: 36
		// (get) Token: 0x06000083 RID: 131 RVA: 0x000024ED File Offset: 0x000006ED
		// (set) Token: 0x06000084 RID: 132 RVA: 0x000024F7 File Offset: 0x000006F7
		internal virtual TabPage TabPage4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000025 RID: 37
		// (get) Token: 0x06000085 RID: 133 RVA: 0x00002500 File Offset: 0x00000700
		// (set) Token: 0x06000086 RID: 134 RVA: 0x00008DD0 File Offset: 0x00006FD0
		internal virtual LogInCheckBox LogInCheckBox1
		{
			[CompilerGenerated]
			get
			{
				return this._LogInCheckBox1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				LogInCheckBox.CheckedChangedEventHandler obj = new LogInCheckBox.CheckedChangedEventHandler(this.LogInCheckBox1_CheckedChanged);
				LogInCheckBox logInCheckBox = this._LogInCheckBox1;
				if (logInCheckBox != null)
				{
					logInCheckBox.CheckedChanged -= obj;
				}
				this._LogInCheckBox1 = value;
				logInCheckBox = this._LogInCheckBox1;
				if (logInCheckBox != null)
				{
					logInCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x17000026 RID: 38
		// (get) Token: 0x06000087 RID: 135 RVA: 0x0000250A File Offset: 0x0000070A
		// (set) Token: 0x06000088 RID: 136 RVA: 0x00002514 File Offset: 0x00000714
		internal virtual TabPage TabPage5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000027 RID: 39
		// (get) Token: 0x06000089 RID: 137 RVA: 0x0000251D File Offset: 0x0000071D
		// (set) Token: 0x0600008A RID: 138 RVA: 0x00002527 File Offset: 0x00000727
		internal virtual TabPage TabPage2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000028 RID: 40
		// (get) Token: 0x0600008B RID: 139 RVA: 0x00002530 File Offset: 0x00000730
		// (set) Token: 0x0600008C RID: 140 RVA: 0x0000253A File Offset: 0x0000073A
		internal virtual LogInGroupBox LogInGroupBox2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000029 RID: 41
		// (get) Token: 0x0600008D RID: 141 RVA: 0x00002543 File Offset: 0x00000743
		// (set) Token: 0x0600008E RID: 142 RVA: 0x00008E14 File Offset: 0x00007014
		internal virtual LogInComboBox LogInComboBox1
		{
			[CompilerGenerated]
			get
			{
				return this._LogInComboBox1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.LogInComboBox1_SelectedIndexChanged);
				LogInComboBox logInComboBox = this._LogInComboBox1;
				if (logInComboBox != null)
				{
					logInComboBox.SelectedIndexChanged -= value2;
				}
				this._LogInComboBox1 = value;
				logInComboBox = this._LogInComboBox1;
				if (logInComboBox != null)
				{
					logInComboBox.SelectedIndexChanged += value2;
				}
			}
		}

		// Token: 0x1700002A RID: 42
		// (get) Token: 0x0600008F RID: 143 RVA: 0x0000254D File Offset: 0x0000074D
		// (set) Token: 0x06000090 RID: 144 RVA: 0x00002557 File Offset: 0x00000757
		internal virtual LogInLabel LogInLabel1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700002B RID: 43
		// (get) Token: 0x06000091 RID: 145 RVA: 0x00002560 File Offset: 0x00000760
		// (set) Token: 0x06000092 RID: 146 RVA: 0x00008E58 File Offset: 0x00007058
		internal virtual TabPage TabPage3
		{
			[CompilerGenerated]
			get
			{
				return this._TabPage3;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TabPage3_Click);
				TabPage tabPage = this._TabPage3;
				if (tabPage != null)
				{
					tabPage.Click -= value2;
				}
				this._TabPage3 = value;
				tabPage = this._TabPage3;
				if (tabPage != null)
				{
					tabPage.Click += value2;
				}
			}
		}

		// Token: 0x1700002C RID: 44
		// (get) Token: 0x06000093 RID: 147 RVA: 0x0000256A File Offset: 0x0000076A
		// (set) Token: 0x06000094 RID: 148 RVA: 0x00002574 File Offset: 0x00000774
		internal virtual LogInGroupBox LogInGroupBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700002D RID: 45
		// (get) Token: 0x06000095 RID: 149 RVA: 0x0000257D File Offset: 0x0000077D
		// (set) Token: 0x06000096 RID: 150 RVA: 0x00002587 File Offset: 0x00000787
		internal virtual Panel Panel1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700002E RID: 46
		// (get) Token: 0x06000097 RID: 151 RVA: 0x00002590 File Offset: 0x00000790
		// (set) Token: 0x06000098 RID: 152 RVA: 0x0000259A File Offset: 0x0000079A
		internal virtual LogInTrackBar colorB { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700002F RID: 47
		// (get) Token: 0x06000099 RID: 153 RVA: 0x000025A3 File Offset: 0x000007A3
		// (set) Token: 0x0600009A RID: 154 RVA: 0x000025AD File Offset: 0x000007AD
		internal virtual LogInTrackBar colorG { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000030 RID: 48
		// (get) Token: 0x0600009B RID: 155 RVA: 0x000025B6 File Offset: 0x000007B6
		// (set) Token: 0x0600009C RID: 156 RVA: 0x000025C0 File Offset: 0x000007C0
		internal virtual LogInTrackBar colorR { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000031 RID: 49
		// (get) Token: 0x0600009D RID: 157 RVA: 0x000025C9 File Offset: 0x000007C9
		// (set) Token: 0x0600009E RID: 158 RVA: 0x000025D3 File Offset: 0x000007D3
		internal virtual LogInGroupBox LogInGroupBox4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000032 RID: 50
		// (get) Token: 0x0600009F RID: 159 RVA: 0x000025DC File Offset: 0x000007DC
		// (set) Token: 0x060000A0 RID: 160 RVA: 0x000025E6 File Offset: 0x000007E6
		internal virtual LogInTrackBar multiplierVal { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000033 RID: 51
		// (get) Token: 0x060000A1 RID: 161 RVA: 0x000025EF File Offset: 0x000007EF
		// (set) Token: 0x060000A2 RID: 162 RVA: 0x000025F9 File Offset: 0x000007F9
		internal virtual LogInGroupBox LogInGroupBox3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000034 RID: 52
		// (get) Token: 0x060000A3 RID: 163 RVA: 0x00002602 File Offset: 0x00000802
		// (set) Token: 0x060000A4 RID: 164 RVA: 0x0000260C File Offset: 0x0000080C
		internal virtual LogInLabel LogInLabel3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000035 RID: 53
		// (get) Token: 0x060000A5 RID: 165 RVA: 0x00002615 File Offset: 0x00000815
		// (set) Token: 0x060000A6 RID: 166 RVA: 0x0000261F File Offset: 0x0000081F
		internal virtual LogInLabel LogInLabel2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000036 RID: 54
		// (get) Token: 0x060000A7 RID: 167 RVA: 0x00002628 File Offset: 0x00000828
		// (set) Token: 0x060000A8 RID: 168 RVA: 0x00002632 File Offset: 0x00000832
		internal virtual LogInTrackBar baseValue { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000037 RID: 55
		// (get) Token: 0x060000A9 RID: 169 RVA: 0x0000263B File Offset: 0x0000083B
		// (set) Token: 0x060000AA RID: 170 RVA: 0x00002645 File Offset: 0x00000845
		internal virtual LogInTrackBar jitterValue { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000038 RID: 56
		// (get) Token: 0x060000AB RID: 171 RVA: 0x0000264E File Offset: 0x0000084E
		// (set) Token: 0x060000AC RID: 172 RVA: 0x00002658 File Offset: 0x00000858
		internal virtual LogInLabel LogInLabel4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000039 RID: 57
		// (get) Token: 0x060000AD RID: 173 RVA: 0x00002661 File Offset: 0x00000861
		// (set) Token: 0x060000AE RID: 174 RVA: 0x00008E9C File Offset: 0x0000709C
		internal virtual Timer tick
		{
			[CompilerGenerated]
			get
			{
				return this._tick;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Tick_Tick);
				Timer tick = this._tick;
				if (tick != null)
				{
					tick.Tick -= value2;
				}
				this._tick = value;
				tick = this._tick;
				if (tick != null)
				{
					tick.Tick += value2;
				}
			}
		}

		// Token: 0x1700003A RID: 58
		// (get) Token: 0x060000AF RID: 175 RVA: 0x0000266B File Offset: 0x0000086B
		// (set) Token: 0x060000B0 RID: 176 RVA: 0x00008EE0 File Offset: 0x000070E0
		internal virtual Timer autoclicker
		{
			[CompilerGenerated]
			get
			{
				return this._autoclicker;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Autoclicker_Tick);
				EventHandler value3 = new EventHandler(this.autoclicker_Disposed);
				Timer autoclicker = this._autoclicker;
				if (autoclicker != null)
				{
					autoclicker.Tick -= value2;
					autoclicker.Disposed -= value3;
				}
				this._autoclicker = value;
				autoclicker = this._autoclicker;
				if (autoclicker != null)
				{
					autoclicker.Tick += value2;
					autoclicker.Disposed += value3;
				}
			}
		}

		// Token: 0x1700003B RID: 59
		// (get) Token: 0x060000B1 RID: 177 RVA: 0x00002675 File Offset: 0x00000875
		// (set) Token: 0x060000B2 RID: 178 RVA: 0x00008F40 File Offset: 0x00007140
		internal virtual LogInButton LogInButton2
		{
			[CompilerGenerated]
			get
			{
				return this._LogInButton2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.LogInButton2_Click_1);
				LogInButton logInButton = this._LogInButton2;
				if (logInButton != null)
				{
					logInButton.Click -= value2;
				}
				this._LogInButton2 = value;
				logInButton = this._LogInButton2;
				if (logInButton != null)
				{
					logInButton.Click += value2;
				}
			}
		}

		// Token: 0x1700003C RID: 60
		// (get) Token: 0x060000B3 RID: 179 RVA: 0x0000267F File Offset: 0x0000087F
		// (set) Token: 0x060000B4 RID: 180 RVA: 0x00008F84 File Offset: 0x00007184
		internal virtual LogInCheckBox LogInCheckBox3
		{
			[CompilerGenerated]
			get
			{
				return this._LogInCheckBox3;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				LogInCheckBox.CheckedChangedEventHandler obj = new LogInCheckBox.CheckedChangedEventHandler(this.LogInCheckBox3_CheckedChanged);
				LogInCheckBox logInCheckBox = this._LogInCheckBox3;
				if (logInCheckBox != null)
				{
					logInCheckBox.CheckedChanged -= obj;
				}
				this._LogInCheckBox3 = value;
				logInCheckBox = this._LogInCheckBox3;
				if (logInCheckBox != null)
				{
					logInCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x1700003D RID: 61
		// (get) Token: 0x060000B5 RID: 181 RVA: 0x00002689 File Offset: 0x00000889
		// (set) Token: 0x060000B6 RID: 182 RVA: 0x00008FC8 File Offset: 0x000071C8
		internal virtual LogInButton LogInButton4
		{
			[CompilerGenerated]
			get
			{
				return this._LogInButton4;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.LogInButton4_Click);
				LogInButton logInButton = this._LogInButton4;
				if (logInButton != null)
				{
					logInButton.Click -= value2;
				}
				this._LogInButton4 = value;
				logInButton = this._LogInButton4;
				if (logInButton != null)
				{
					logInButton.Click += value2;
				}
			}
		}

		// Token: 0x1700003E RID: 62
		// (get) Token: 0x060000B7 RID: 183 RVA: 0x00002693 File Offset: 0x00000893
		// (set) Token: 0x060000B8 RID: 184 RVA: 0x0000900C File Offset: 0x0000720C
		internal virtual LogInStatusBar LogInStatusBar1
		{
			[CompilerGenerated]
			get
			{
				return this._LogInStatusBar1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.LogInStatusBar1_Click);
				LogInStatusBar logInStatusBar = this._LogInStatusBar1;
				if (logInStatusBar != null)
				{
					logInStatusBar.Click -= value2;
				}
				this._LogInStatusBar1 = value;
				logInStatusBar = this._LogInStatusBar1;
				if (logInStatusBar != null)
				{
					logInStatusBar.Click += value2;
				}
			}
		}

		// Token: 0x1700003F RID: 63
		// (get) Token: 0x060000B9 RID: 185 RVA: 0x0000269D File Offset: 0x0000089D
		// (set) Token: 0x060000BA RID: 186 RVA: 0x00009050 File Offset: 0x00007250
		internal virtual OpenFileDialog OpenFileDialog1
		{
			[CompilerGenerated]
			get
			{
				return this._OpenFileDialog1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				CancelEventHandler value2 = new CancelEventHandler(this.OpenFileDialog1_FileOk);
				OpenFileDialog openFileDialog = this._OpenFileDialog1;
				if (openFileDialog != null)
				{
					openFileDialog.FileOk -= value2;
				}
				this._OpenFileDialog1 = value;
				openFileDialog = this._OpenFileDialog1;
				if (openFileDialog != null)
				{
					openFileDialog.FileOk += value2;
				}
			}
		}

		// Token: 0x17000040 RID: 64
		// (get) Token: 0x060000BB RID: 187 RVA: 0x000026A7 File Offset: 0x000008A7
		// (set) Token: 0x060000BC RID: 188 RVA: 0x00009094 File Offset: 0x00007294
		internal virtual LogInCheckBox LogInCheckBox2
		{
			[CompilerGenerated]
			get
			{
				return this._LogInCheckBox2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				LogInCheckBox.CheckedChangedEventHandler obj = new LogInCheckBox.CheckedChangedEventHandler(this.LogInCheckBox2_CheckedChanged);
				LogInCheckBox logInCheckBox = this._LogInCheckBox2;
				if (logInCheckBox != null)
				{
					logInCheckBox.CheckedChanged -= obj;
				}
				this._LogInCheckBox2 = value;
				logInCheckBox = this._LogInCheckBox2;
				if (logInCheckBox != null)
				{
					logInCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x17000041 RID: 65
		// (get) Token: 0x060000BD RID: 189 RVA: 0x000026B1 File Offset: 0x000008B1
		// (set) Token: 0x060000BE RID: 190 RVA: 0x000090D8 File Offset: 0x000072D8
		internal virtual Timer rainbow
		{
			[CompilerGenerated]
			get
			{
				return this._rainbow;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Rainbow_Tick);
				Timer rainbow = this._rainbow;
				if (rainbow != null)
				{
					rainbow.Tick -= value2;
				}
				this._rainbow = value;
				rainbow = this._rainbow;
				if (rainbow != null)
				{
					rainbow.Tick += value2;
				}
			}
		}

		// Token: 0x17000042 RID: 66
		// (get) Token: 0x060000BF RID: 191 RVA: 0x000026BB File Offset: 0x000008BB
		// (set) Token: 0x060000C0 RID: 192 RVA: 0x0000911C File Offset: 0x0000731C
		internal virtual LogInLabel cpsAverage
		{
			[CompilerGenerated]
			get
			{
				return this._cpsAverage;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.CspVal_Click);
				LogInLabel cpsAverage = this._cpsAverage;
				if (cpsAverage != null)
				{
					cpsAverage.Click -= value2;
				}
				this._cpsAverage = value;
				cpsAverage = this._cpsAverage;
				if (cpsAverage != null)
				{
					cpsAverage.Click += value2;
				}
			}
		}

		// Token: 0x17000043 RID: 67
		// (get) Token: 0x060000C1 RID: 193 RVA: 0x000026C5 File Offset: 0x000008C5
		// (set) Token: 0x060000C2 RID: 194 RVA: 0x000026CF File Offset: 0x000008CF
		internal virtual LogInLabel LogInLabel5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000044 RID: 68
		// (get) Token: 0x060000C3 RID: 195 RVA: 0x000026D8 File Offset: 0x000008D8
		// (set) Token: 0x060000C4 RID: 196 RVA: 0x00009160 File Offset: 0x00007360
		internal virtual LogInButton LogInButton5
		{
			[CompilerGenerated]
			get
			{
				return this._LogInButton5;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.LogInButton5_Click);
				LogInButton logInButton = this._LogInButton5;
				if (logInButton != null)
				{
					logInButton.Click -= value2;
				}
				this._LogInButton5 = value;
				logInButton = this._LogInButton5;
				if (logInButton != null)
				{
					logInButton.Click += value2;
				}
			}
		}

		// Token: 0x17000045 RID: 69
		// (get) Token: 0x060000C5 RID: 197 RVA: 0x000026E2 File Offset: 0x000008E2
		// (set) Token: 0x060000C6 RID: 198 RVA: 0x000091A4 File Offset: 0x000073A4
		internal virtual Timer _C
		{
			[CompilerGenerated]
			get
			{
				return this.__C;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this._C_Tick);
				Timer _C = this.__C;
				if (_C != null)
				{
					_C.Tick -= value2;
				}
				this.__C = value;
				_C = this.__C;
				if (_C != null)
				{
					_C.Tick += value2;
				}
			}
		}

		// Token: 0x17000046 RID: 70
		// (get) Token: 0x060000C7 RID: 199 RVA: 0x000026EC File Offset: 0x000008EC
		// (set) Token: 0x060000C8 RID: 200 RVA: 0x000026F6 File Offset: 0x000008F6
		internal virtual BackgroundWorker BackgroundWorker1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000047 RID: 71
		// (get) Token: 0x060000C9 RID: 201 RVA: 0x000026FF File Offset: 0x000008FF
		// (set) Token: 0x060000CA RID: 202 RVA: 0x00002709 File Offset: 0x00000909
		internal virtual Panel Panel3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000048 RID: 72
		// (get) Token: 0x060000CB RID: 203 RVA: 0x00002712 File Offset: 0x00000912
		// (set) Token: 0x060000CC RID: 204 RVA: 0x000091E8 File Offset: 0x000073E8
		internal virtual LogInCheckBox LogInCheckBox6
		{
			[CompilerGenerated]
			get
			{
				return this._LogInCheckBox6;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				LogInCheckBox.CheckedChangedEventHandler obj = new LogInCheckBox.CheckedChangedEventHandler(this.LogInCheckBox6_CheckedChanged);
				LogInCheckBox logInCheckBox = this._LogInCheckBox6;
				if (logInCheckBox != null)
				{
					logInCheckBox.CheckedChanged -= obj;
				}
				this._LogInCheckBox6 = value;
				logInCheckBox = this._LogInCheckBox6;
				if (logInCheckBox != null)
				{
					logInCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x17000049 RID: 73
		// (get) Token: 0x060000CD RID: 205 RVA: 0x0000271C File Offset: 0x0000091C
		// (set) Token: 0x060000CE RID: 206 RVA: 0x0000922C File Offset: 0x0000742C
		internal virtual LogInComboBox LogInComboBox2
		{
			[CompilerGenerated]
			get
			{
				return this._LogInComboBox2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.LogInComboBox2_SelectedIndexChanged);
				EventHandler value3 = new EventHandler(this.LogInComboBox2_Click);
				LogInComboBox logInComboBox = this._LogInComboBox2;
				if (logInComboBox != null)
				{
					logInComboBox.SelectedIndexChanged -= value2;
					logInComboBox.Click -= value3;
				}
				this._LogInComboBox2 = value;
				logInComboBox = this._LogInComboBox2;
				if (logInComboBox != null)
				{
					logInComboBox.SelectedIndexChanged += value2;
					logInComboBox.Click += value3;
				}
			}
		}

		// Token: 0x1700004A RID: 74
		// (get) Token: 0x060000CF RID: 207 RVA: 0x00002726 File Offset: 0x00000926
		// (set) Token: 0x060000D0 RID: 208 RVA: 0x00002730 File Offset: 0x00000930
		internal virtual LogInLabel LogInLabel12 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700004B RID: 75
		// (get) Token: 0x060000D1 RID: 209 RVA: 0x00002739 File Offset: 0x00000939
		// (set) Token: 0x060000D2 RID: 210 RVA: 0x00002743 File Offset: 0x00000943
		internal virtual LogInGroupBox LogInGroupBox7 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700004C RID: 76
		// (get) Token: 0x060000D3 RID: 211 RVA: 0x0000274C File Offset: 0x0000094C
		// (set) Token: 0x060000D4 RID: 212 RVA: 0x00002756 File Offset: 0x00000956
		internal virtual LogInLabel LogInLabel13 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700004D RID: 77
		// (get) Token: 0x060000D5 RID: 213 RVA: 0x0000275F File Offset: 0x0000095F
		// (set) Token: 0x060000D6 RID: 214 RVA: 0x0000928C File Offset: 0x0000748C
		internal virtual LogInCheckBox LogInCheckBox8
		{
			[CompilerGenerated]
			get
			{
				return this._LogInCheckBox8;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				LogInCheckBox.CheckedChangedEventHandler obj = new LogInCheckBox.CheckedChangedEventHandler(this.LogInCheckBox8_CheckedChanged);
				LogInCheckBox logInCheckBox = this._LogInCheckBox8;
				if (logInCheckBox != null)
				{
					logInCheckBox.CheckedChanged -= obj;
				}
				this._LogInCheckBox8 = value;
				logInCheckBox = this._LogInCheckBox8;
				if (logInCheckBox != null)
				{
					logInCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x1700004E RID: 78
		// (get) Token: 0x060000D7 RID: 215 RVA: 0x00002769 File Offset: 0x00000969
		// (set) Token: 0x060000D8 RID: 216 RVA: 0x00002773 File Offset: 0x00000973
		internal virtual LogInTrackBar LogInTrackBar4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700004F RID: 79
		// (get) Token: 0x060000D9 RID: 217 RVA: 0x0000277C File Offset: 0x0000097C
		// (set) Token: 0x060000DA RID: 218 RVA: 0x000092D0 File Offset: 0x000074D0
		internal virtual LogInGroupBox LogInGroupBox8
		{
			[CompilerGenerated]
			get
			{
				return this._LogInGroupBox8;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.LogInGroupBox8_Click);
				LogInGroupBox logInGroupBox = this._LogInGroupBox8;
				if (logInGroupBox != null)
				{
					logInGroupBox.Click -= value2;
				}
				this._LogInGroupBox8 = value;
				logInGroupBox = this._LogInGroupBox8;
				if (logInGroupBox != null)
				{
					logInGroupBox.Click += value2;
				}
			}
		}

		// Token: 0x17000050 RID: 80
		// (get) Token: 0x060000DB RID: 219 RVA: 0x00002786 File Offset: 0x00000986
		// (set) Token: 0x060000DC RID: 220 RVA: 0x00002790 File Offset: 0x00000990
		internal virtual Label Label2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000051 RID: 81
		// (get) Token: 0x060000DD RID: 221 RVA: 0x00002799 File Offset: 0x00000999
		// (set) Token: 0x060000DE RID: 222 RVA: 0x000027A3 File Offset: 0x000009A3
		internal virtual Panel Panel2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000052 RID: 82
		// (get) Token: 0x060000DF RID: 223 RVA: 0x000027AC File Offset: 0x000009AC
		// (set) Token: 0x060000E0 RID: 224 RVA: 0x000027B6 File Offset: 0x000009B6
		internal virtual Label Label8 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000053 RID: 83
		// (get) Token: 0x060000E1 RID: 225 RVA: 0x000027BF File Offset: 0x000009BF
		// (set) Token: 0x060000E2 RID: 226 RVA: 0x000027C9 File Offset: 0x000009C9
		internal virtual Label Label7 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000054 RID: 84
		// (get) Token: 0x060000E3 RID: 227 RVA: 0x000027D2 File Offset: 0x000009D2
		// (set) Token: 0x060000E4 RID: 228 RVA: 0x000027DC File Offset: 0x000009DC
		internal virtual Label Label6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000055 RID: 85
		// (get) Token: 0x060000E5 RID: 229 RVA: 0x000027E5 File Offset: 0x000009E5
		// (set) Token: 0x060000E6 RID: 230 RVA: 0x000027EF File Offset: 0x000009EF
		internal virtual Label Label5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000056 RID: 86
		// (get) Token: 0x060000E7 RID: 231 RVA: 0x000027F8 File Offset: 0x000009F8
		// (set) Token: 0x060000E8 RID: 232 RVA: 0x00002802 File Offset: 0x00000A02
		internal virtual Label Label4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000057 RID: 87
		// (get) Token: 0x060000E9 RID: 233 RVA: 0x0000280B File Offset: 0x00000A0B
		// (set) Token: 0x060000EA RID: 234 RVA: 0x00002815 File Offset: 0x00000A15
		internal virtual Label Label3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000058 RID: 88
		// (get) Token: 0x060000EB RID: 235 RVA: 0x0000281E File Offset: 0x00000A1E
		// (set) Token: 0x060000EC RID: 236 RVA: 0x00002828 File Offset: 0x00000A28
		internal virtual Label Label1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000059 RID: 89
		// (get) Token: 0x060000ED RID: 237 RVA: 0x00002831 File Offset: 0x00000A31
		// (set) Token: 0x060000EE RID: 238 RVA: 0x0000283B File Offset: 0x00000A3B
		internal virtual Label Label13 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700005A RID: 90
		// (get) Token: 0x060000EF RID: 239 RVA: 0x00002844 File Offset: 0x00000A44
		// (set) Token: 0x060000F0 RID: 240 RVA: 0x0000284E File Offset: 0x00000A4E
		internal virtual Panel Panel5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700005B RID: 91
		// (get) Token: 0x060000F1 RID: 241 RVA: 0x00002857 File Offset: 0x00000A57
		// (set) Token: 0x060000F2 RID: 242 RVA: 0x00002861 File Offset: 0x00000A61
		internal virtual Label Label12 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700005C RID: 92
		// (get) Token: 0x060000F3 RID: 243 RVA: 0x0000286A File Offset: 0x00000A6A
		// (set) Token: 0x060000F4 RID: 244 RVA: 0x00002874 File Offset: 0x00000A74
		internal virtual Label Label11 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700005D RID: 93
		// (get) Token: 0x060000F5 RID: 245 RVA: 0x0000287D File Offset: 0x00000A7D
		// (set) Token: 0x060000F6 RID: 246 RVA: 0x00002887 File Offset: 0x00000A87
		internal virtual Label Label9 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700005E RID: 94
		// (get) Token: 0x060000F7 RID: 247 RVA: 0x00002890 File Offset: 0x00000A90
		// (set) Token: 0x060000F8 RID: 248 RVA: 0x0000289A File Offset: 0x00000A9A
		internal virtual Panel Panel4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700005F RID: 95
		// (get) Token: 0x060000F9 RID: 249 RVA: 0x000028A3 File Offset: 0x00000AA3
		// (set) Token: 0x060000FA RID: 250 RVA: 0x000028AD File Offset: 0x00000AAD
		internal virtual Panel Panel6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000060 RID: 96
		// (get) Token: 0x060000FB RID: 251 RVA: 0x000028B6 File Offset: 0x00000AB6
		// (set) Token: 0x060000FC RID: 252 RVA: 0x000028C0 File Offset: 0x00000AC0
		internal virtual Label Label10 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x04000014 RID: 20
		private int ClickAmount;

		// Token: 0x04000015 RID: 21
		private int SomeValue;

		// Token: 0x04000016 RID: 22
		private int clicks;

		// Token: 0x04000017 RID: 23
		private object randVal;

		// Token: 0x04000018 RID: 24
		private object tempCps;
	}
}
