using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using Guna.UI2.WinForms;
using Microsoft.CSharp.RuntimeBinder;
using ns16;
using ns27;
using ns3;
using ns5;
using Siticone.UI.WinForms;
using WindowsUI.Controls;
using _50Cent_Ware.Properties;

namespace beta-src
{
	// Token: 0x0200002D RID: 45
	public partial class beta : Form
	{
		// Token: 0x060000FE RID: 254
		[DllImport("user32.dll", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
		public static extern void mouse_event(uint uint_0, int int_17, int int_18, int int_19, int int_20);

		// Token: 0x060000FF RID: 255
		[DllImport("User32.dll")]
		private static extern short GetAsyncKeyState(Keys keys_0);

		// Token: 0x06000100 RID: 256
		[DllImport("user32.dll")]
		public static extern int SendMessage(IntPtr intptr_1, int int_17, int int_18, int int_19);

		// Token: 0x06000101 RID: 257
		[DllImport("user32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x06000102 RID: 258
		[DllImport("user32.dll", SetLastError = true)]
		private static extern IntPtr FindWindow(string string_0, string string_1);

		// Token: 0x06000103 RID: 259
		[DllImport("User32.dll")]
		private static extern bool SetCursorPos(int int_17, int int_18);

		// Token: 0x06000104 RID: 260
		[DllImport("kernel32")]
		private static extern long WritePrivateProfileString(string string_0, string string_1, string string_2, string string_3);

		// Token: 0x06000105 RID: 261
		[DllImport("kernel32")]
		private static extern int GetPrivateProfileString(string string_0, string string_1, string string_2, StringBuilder stringBuilder_0, int int_17, string string_3);

		// Token: 0x06000106 RID: 262
		[DllImport("User32.Dll")]
		private static extern bool PostMessageA(IntPtr intptr_1, uint uint_0, int int_17, int int_18);

		// Token: 0x06000107 RID: 263 RVA: 0x00006AC4 File Offset: 0x00004CC4
		public static int smethod_0(int int_17, int int_18)
		{
			return int_18 << 16 | (int_17 & 65535);
		}

		// Token: 0x06000108 RID: 264 RVA: 0x00006AE0 File Offset: 0x00004CE0
		public beta()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000109 RID: 265 RVA: 0x00006B44 File Offset: 0x00004D44
		private void beta_Load(object sender, EventArgs e)
		{
			this.timer_9.Start();
			this.timer_3.Start();
			this.guna2ShadowForm_0.SetShadowForm(this);
			this.timer_1.Start();
			this.timer_8.Start();
			this.outlineLabel31.Text = "licensed to - " + Settings.Default.username;
		}

		// Token: 0x0600010A RID: 266 RVA: 0x00006BA8 File Offset: 0x00004DA8
		private void leftcps_Paint(object sender, PaintEventArgs e)
		{
			float num = (float)this.leftcps.Value / 10f;
			Rectangle clipRectangle = e.ClipRectangle;
			new StringFormat();
			clipRectangle.Width = (int)((double)clipRectangle.Width * ((double)this.leftcps.Value / (double)this.leftcps.Maximum)) - 4;
			clipRectangle.Height -= 4;
			new Font("Segoe UI", 9f);
			object arg = (SiticoneMetroTrackBar)sender;
			if (beta.Class11.callSite_1 == null)
			{
				beta.Class11.callSite_1 = CallSite<Func<CallSite, Type, object, SolidBrush>>.Create(Binder.InvokeConstructor(CSharpBinderFlags.None, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			Func<CallSite, Type, object, SolidBrush> target = beta.Class11.callSite_1.Target;
			CallSite callSite_ = beta.Class11.callSite_1;
			Type typeFromHandle = typeof(SolidBrush);
			if (beta.Class11.callSite_0 == null)
			{
				beta.Class11.callSite_0 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "FillColor", typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			object obj = target(callSite_, typeFromHandle, beta.Class11.callSite_0.Target(beta.Class11.callSite_0, arg));
			if (beta.Class11.callSite_2 == null)
			{
				beta.Class11.callSite_2 = CallSite<Action<CallSite, Graphics, object, Rectangle>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "FillRectangle", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null)
				}));
			}
			beta.Class11.callSite_2.Target(beta.Class11.callSite_2, e.Graphics, obj, e.ClipRectangle);
			e.Graphics.FillRectangle(this.solidBrush_0, 2, 2, clipRectangle.Width, clipRectangle.Height);
			if (beta.Class11.callSite_3 == null)
			{
				beta.Class11.callSite_3 = CallSite<Action<CallSite, object>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "Dispose", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			beta.Class11.callSite_3.Target(beta.Class11.callSite_3, obj);
		}

		// Token: 0x0600010B RID: 267 RVA: 0x000021D4 File Offset: 0x000003D4
		private void skeetGroupBox1_Enter(object sender, EventArgs e)
		{
		}

		// Token: 0x0600010C RID: 268 RVA: 0x00006DB4 File Offset: 0x00004FB4
		private void siticoneCustomCheckBox1_CheckedChanged(object sender, EventArgs e)
		{
			if (this.siticoneCustomCheckBox1.Checked)
			{
				this.timer_0.Start();
			}
			else
			{
				this.timer_0.Stop();
			}
		}

		// Token: 0x0600010D RID: 269 RVA: 0x00006DE8 File Offset: 0x00004FE8
		private void leftcps_Scroll(object sender, ScrollEventArgs e)
		{
			this.outlineLabel7.Text = "average cps: " + (this.leftcps.Value / 4).ToString();
		}

		// Token: 0x0600010E RID: 270 RVA: 0x00006E20 File Offset: 0x00005020
		private void rightcps_Paint(object sender, PaintEventArgs e)
		{
			float num = (float)this.rightcps.Value / 10f;
			Rectangle clipRectangle = e.ClipRectangle;
			new StringFormat();
			clipRectangle.Width = (int)((double)clipRectangle.Width * ((double)this.rightcps.Value / (double)this.rightcps.Maximum)) - 4;
			clipRectangle.Height -= 4;
			new Font("Segoe UI", 9f);
			object arg = (SiticoneMetroTrackBar)sender;
			if (beta.Class12.callSite_1 == null)
			{
				beta.Class12.callSite_1 = CallSite<Func<CallSite, Type, object, SolidBrush>>.Create(Binder.InvokeConstructor(CSharpBinderFlags.None, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			Func<CallSite, Type, object, SolidBrush> target = beta.Class12.callSite_1.Target;
			CallSite callSite_ = beta.Class12.callSite_1;
			Type typeFromHandle = typeof(SolidBrush);
			if (beta.Class12.callSite_0 == null)
			{
				beta.Class12.callSite_0 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "FillColor", typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			object obj = target(callSite_, typeFromHandle, beta.Class12.callSite_0.Target(beta.Class12.callSite_0, arg));
			if (beta.Class12.callSite_2 == null)
			{
				beta.Class12.callSite_2 = CallSite<Action<CallSite, Graphics, object, Rectangle>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "FillRectangle", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null)
				}));
			}
			beta.Class12.callSite_2.Target(beta.Class12.callSite_2, e.Graphics, obj, e.ClipRectangle);
			e.Graphics.FillRectangle(this.solidBrush_0, 2, 2, clipRectangle.Width, clipRectangle.Height);
			if (beta.Class12.callSite_3 == null)
			{
				beta.Class12.callSite_3 = CallSite<Action<CallSite, object>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "Dispose", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			beta.Class12.callSite_3.Target(beta.Class12.callSite_3, obj);
		}

		// Token: 0x0600010F RID: 271 RVA: 0x000021D4 File Offset: 0x000003D4
		private void outlineLabel1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000110 RID: 272 RVA: 0x0000702C File Offset: 0x0000522C
		private void rightcps_Scroll(object sender, ScrollEventArgs e)
		{
			this.outlineLabel15.Text = "average cps: " + (this.rightcps.Value / 4).ToString();
		}

		// Token: 0x06000111 RID: 273 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_0(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x06000112 RID: 274 RVA: 0x00007064 File Offset: 0x00005264
		private void timer_0_Tick(object sender, EventArgs e)
		{
			Random random = new Random();
			Random random2 = new Random();
			try
			{
				if (this.siticoneCustomCheckBox1.Checked && this.random1.Checked)
				{
					this.timer_0.Interval = random.Next(4000, 4500) / this.siticoneHScrollBar3.Value;
				}
				if (this.siticoneCustomCheckBox1.Checked && !this.random1.Checked)
				{
					this.timer_0.Interval = 4000 / this.leftcps.Value;
				}
			}
			catch
			{
			}
			if (this.siticoneCustomCheckBox1.Checked && this.siticoneCustomCheckBox1.Checked)
			{
				Process[] processesByName = Process.GetProcessesByName("javaw");
				foreach (Process process in processesByName)
				{
					this.intptr_0 = beta.FindWindow(null, process.MainWindowTitle);
				}
				if (!this.intptr_0.Equals(IntPtr.Zero))
				{
					if (Control.MouseButtons == MouseButtons.Left && !this.siticoneCustomCheckBox2.Checked)
					{
						beta.PostMessageA(this.intptr_0, 513U, 0, 0);
						Thread.Sleep(random2.Next(4, 13));
						beta.PostMessageA(this.intptr_0, 514U, 0, 0);
					}
					Cursor cursor_ = Cursor.Current;
					if (Control.MouseButtons == MouseButtons.Left && this.siticoneCustomCheckBox6.Checked && cursor_.smethod_1())
					{
						this.int_11 = 1;
						int num = random.Next(0, 150);
						if (num <= this.siticoneMetroTrackBar4.Value)
						{
							beta.PostMessageA(this.intptr_0, 516U, 0, 0);
							Thread.Sleep(random.Next(1, 7));
							beta.PostMessageA(this.intptr_0, 517U, 0, 0);
						}
					}
					if (cursor_.smethod_1() && this.siticoneCustomCheckBox2.Checked && Control.MouseButtons == MouseButtons.Left)
					{
						beta.PostMessageA(this.intptr_0, 513U, 0, 0);
						Thread.Sleep(random2.Next(4, 13));
						beta.PostMessageA(this.intptr_0, 514U, 0, 0);
					}
				}
			}
		}

		// Token: 0x06000113 RID: 275 RVA: 0x000021D4 File Offset: 0x000003D4
		private void outlineLabel23_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000114 RID: 276 RVA: 0x000072C4 File Offset: 0x000054C4
		private void smart_CheckedChanged(object sender, EventArgs e)
		{
			if (this.smart.Checked)
			{
				this.custom.Checked = false;
			}
		}

		// Token: 0x06000115 RID: 277 RVA: 0x000072EC File Offset: 0x000054EC
		private void custom_CheckedChanged(object sender, EventArgs e)
		{
			if (this.custom.Checked)
			{
				this.smart.Checked = false;
			}
		}

		// Token: 0x06000116 RID: 278 RVA: 0x00007314 File Offset: 0x00005514
		private void siticoneMetroTrackBar1_Paint(object sender, PaintEventArgs e)
		{
			float num = (float)this.siticoneMetroTrackBar1.Value / 10f;
			Rectangle clipRectangle = e.ClipRectangle;
			new StringFormat();
			clipRectangle.Width = (int)((double)clipRectangle.Width * ((double)this.siticoneMetroTrackBar1.Value / (double)this.siticoneMetroTrackBar1.Maximum)) - 4;
			clipRectangle.Height -= 4;
			new Font("Segoe UI", 9f);
			object arg = (SiticoneMetroTrackBar)sender;
			if (beta.Class13.callSite_1 == null)
			{
				beta.Class13.callSite_1 = CallSite<Func<CallSite, Type, object, SolidBrush>>.Create(Binder.InvokeConstructor(CSharpBinderFlags.None, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			Func<CallSite, Type, object, SolidBrush> target = beta.Class13.callSite_1.Target;
			CallSite callSite_ = beta.Class13.callSite_1;
			Type typeFromHandle = typeof(SolidBrush);
			if (beta.Class13.callSite_0 == null)
			{
				beta.Class13.callSite_0 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "FillColor", typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			object obj = target(callSite_, typeFromHandle, beta.Class13.callSite_0.Target(beta.Class13.callSite_0, arg));
			if (beta.Class13.callSite_2 == null)
			{
				beta.Class13.callSite_2 = CallSite<Action<CallSite, Graphics, object, Rectangle>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "FillRectangle", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null)
				}));
			}
			beta.Class13.callSite_2.Target(beta.Class13.callSite_2, e.Graphics, obj, e.ClipRectangle);
			e.Graphics.FillRectangle(this.solidBrush_0, 2, 2, clipRectangle.Width, clipRectangle.Height);
			if (beta.Class13.callSite_3 == null)
			{
				beta.Class13.callSite_3 = CallSite<Action<CallSite, object>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "Dispose", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			beta.Class13.callSite_3.Target(beta.Class13.callSite_3, obj);
		}

		// Token: 0x06000117 RID: 279 RVA: 0x00007520 File Offset: 0x00005720
		private void siticoneMetroTrackBar2_Scroll(object sender, ScrollEventArgs e)
		{
			this.outlineLabel19.Text = "cps subtract: " + (this.siticoneMetroTrackBar2.Value / 4).ToString();
		}

		// Token: 0x06000118 RID: 280 RVA: 0x00007558 File Offset: 0x00005758
		private void siticoneMetroTrackBar2_Paint(object sender, PaintEventArgs e)
		{
			float num = (float)this.siticoneMetroTrackBar2.Value / 10f;
			Rectangle clipRectangle = e.ClipRectangle;
			new StringFormat();
			clipRectangle.Width = (int)((double)clipRectangle.Width * ((double)this.siticoneMetroTrackBar2.Value / (double)this.siticoneMetroTrackBar2.Maximum)) - 4;
			clipRectangle.Height -= 4;
			new Font("Segoe UI", 9f);
			object arg = (SiticoneMetroTrackBar)sender;
			if (beta.Class14.callSite_1 == null)
			{
				beta.Class14.callSite_1 = CallSite<Func<CallSite, Type, object, SolidBrush>>.Create(Binder.InvokeConstructor(CSharpBinderFlags.None, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			Func<CallSite, Type, object, SolidBrush> target = beta.Class14.callSite_1.Target;
			CallSite callSite_ = beta.Class14.callSite_1;
			Type typeFromHandle = typeof(SolidBrush);
			if (beta.Class14.callSite_0 == null)
			{
				beta.Class14.callSite_0 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "FillColor", typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			object obj = target(callSite_, typeFromHandle, beta.Class14.callSite_0.Target(beta.Class14.callSite_0, arg));
			if (beta.Class14.callSite_2 == null)
			{
				beta.Class14.callSite_2 = CallSite<Action<CallSite, Graphics, object, Rectangle>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "FillRectangle", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null)
				}));
			}
			beta.Class14.callSite_2.Target(beta.Class14.callSite_2, e.Graphics, obj, e.ClipRectangle);
			e.Graphics.FillRectangle(this.solidBrush_0, 2, 2, clipRectangle.Width, clipRectangle.Height);
			if (beta.Class14.callSite_3 == null)
			{
				beta.Class14.callSite_3 = CallSite<Action<CallSite, object>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "Dispose", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			beta.Class14.callSite_3.Target(beta.Class14.callSite_3, obj);
		}

		// Token: 0x06000119 RID: 281 RVA: 0x00007764 File Offset: 0x00005964
		private void siticoneMetroTrackBar3_Paint(object sender, PaintEventArgs e)
		{
			float num = (float)this.siticoneMetroTrackBar3.Value / 10f;
			Rectangle clipRectangle = e.ClipRectangle;
			new StringFormat();
			clipRectangle.Width = (int)((double)clipRectangle.Width * ((double)this.siticoneMetroTrackBar3.Value / (double)this.siticoneMetroTrackBar3.Maximum)) - 4;
			clipRectangle.Height -= 4;
			new Font("Segoe UI", 9f);
			object arg = (SiticoneMetroTrackBar)sender;
			if (beta.Class15.callSite_1 == null)
			{
				beta.Class15.callSite_1 = CallSite<Func<CallSite, Type, object, SolidBrush>>.Create(Binder.InvokeConstructor(CSharpBinderFlags.None, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			Func<CallSite, Type, object, SolidBrush> target = beta.Class15.callSite_1.Target;
			CallSite callSite_ = beta.Class15.callSite_1;
			Type typeFromHandle = typeof(SolidBrush);
			if (beta.Class15.callSite_0 == null)
			{
				beta.Class15.callSite_0 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "FillColor", typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			object obj = target(callSite_, typeFromHandle, beta.Class15.callSite_0.Target(beta.Class15.callSite_0, arg));
			if (beta.Class15.callSite_2 == null)
			{
				beta.Class15.callSite_2 = CallSite<Action<CallSite, Graphics, object, Rectangle>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "FillRectangle", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null)
				}));
			}
			beta.Class15.callSite_2.Target(beta.Class15.callSite_2, e.Graphics, obj, e.ClipRectangle);
			e.Graphics.FillRectangle(this.solidBrush_0, 2, 2, clipRectangle.Width, clipRectangle.Height);
			if (beta.Class15.callSite_3 == null)
			{
				beta.Class15.callSite_3 = CallSite<Action<CallSite, object>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "Dispose", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			beta.Class15.callSite_3.Target(beta.Class15.callSite_3, obj);
		}

		// Token: 0x0600011A RID: 282 RVA: 0x00007970 File Offset: 0x00005B70
		private void siticoneMetroTrackBar3_Scroll(object sender, ScrollEventArgs e)
		{
			this.outlineLabel20.Text = "cps add: " + (this.siticoneMetroTrackBar3.Value / 4).ToString();
		}

		// Token: 0x0600011B RID: 283 RVA: 0x000079A8 File Offset: 0x00005BA8
		private void delay1_Scroll(object sender, ScrollEventArgs e)
		{
			this.outlineLabel27.Text = "delay between clicks: " + this.delay1.Value.ToString() + "ms";
		}

		// Token: 0x0600011C RID: 284 RVA: 0x000079E4 File Offset: 0x00005BE4
		private void delay1_Paint(object sender, PaintEventArgs e)
		{
			float num = (float)this.delay1.Value / 10f;
			Rectangle clipRectangle = e.ClipRectangle;
			new StringFormat();
			clipRectangle.Width = (int)((double)clipRectangle.Width * ((double)this.delay1.Value / (double)this.delay1.Maximum)) - 4;
			clipRectangle.Height -= 4;
			new Font("Segoe UI", 9f);
			object arg = (SiticoneMetroTrackBar)sender;
			if (beta.Class16.callSite_1 == null)
			{
				beta.Class16.callSite_1 = CallSite<Func<CallSite, Type, object, SolidBrush>>.Create(Binder.InvokeConstructor(CSharpBinderFlags.None, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			Func<CallSite, Type, object, SolidBrush> target = beta.Class16.callSite_1.Target;
			CallSite callSite_ = beta.Class16.callSite_1;
			Type typeFromHandle = typeof(SolidBrush);
			if (beta.Class16.callSite_0 == null)
			{
				beta.Class16.callSite_0 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "FillColor", typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			object obj = target(callSite_, typeFromHandle, beta.Class16.callSite_0.Target(beta.Class16.callSite_0, arg));
			if (beta.Class16.callSite_2 == null)
			{
				beta.Class16.callSite_2 = CallSite<Action<CallSite, Graphics, object, Rectangle>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "FillRectangle", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null)
				}));
			}
			beta.Class16.callSite_2.Target(beta.Class16.callSite_2, e.Graphics, obj, e.ClipRectangle);
			e.Graphics.FillRectangle(this.solidBrush_0, 2, 2, clipRectangle.Width, clipRectangle.Height);
			if (beta.Class16.callSite_3 == null)
			{
				beta.Class16.callSite_3 = CallSite<Action<CallSite, object>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "Dispose", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			beta.Class16.callSite_3.Target(beta.Class16.callSite_3, obj);
		}

		// Token: 0x0600011D RID: 285 RVA: 0x00007BF0 File Offset: 0x00005DF0
		private void timer_1_Tick(object sender, EventArgs e)
		{
			Random random = new Random(Guid.NewGuid().GetHashCode());
			if (this.random1.Checked && !this.smart.Checked)
			{
				this.int_12 = this.leftcps.Value - 25;
				this.int_13 = this.leftcps.Value + 17;
				new Random();
				this.siticoneHScrollBar3.Value = random.Next(this.int_12, this.int_13);
			}
			if (this.random1.Checked && this.smart.Checked)
			{
				new Random();
				this.int_12 = this.leftcps.Value - 25;
				this.int_13 = this.leftcps.Value + 17;
				this.siticoneHScrollBar3.Value = random.Next(this.int_12, this.int_13);
			}
			if (this.siticoneCustomCheckBox7.Checked)
			{
				int num = random.Next(5, 3000);
				if (num <= 10)
				{
					this.int_12 = this.leftcps.Value - 50;
					this.siticoneHScrollBar3.Value = random.Next(this.int_12);
				}
			}
			if (this.random1.Checked && this.custom.Checked)
			{
				new Random();
				this.int_12 = this.leftcps.Value - this.siticoneMetroTrackBar2.Value;
				this.int_13 = this.leftcps.Value + this.siticoneMetroTrackBar3.Value;
				this.siticoneHScrollBar3.Value = random.Next(this.int_12, this.int_13);
			}
		}

		// Token: 0x0600011E RID: 286 RVA: 0x00002580 File Offset: 0x00000780
		private void siticoneMetroTrackBar1_Scroll(object sender, ScrollEventArgs e)
		{
			this.timer_1.Interval = this.siticoneMetroTrackBar1.Value;
		}

		// Token: 0x0600011F RID: 287 RVA: 0x000021D4 File Offset: 0x000003D4
		private void guna2GradientPanel1_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x06000120 RID: 288 RVA: 0x00002598 File Offset: 0x00000798
		private void method_1(object sender, EventArgs e)
		{
			this.timer_2.Start();
			this.bindBtn.Text = "...";
		}

		// Token: 0x06000121 RID: 289 RVA: 0x00007DB4 File Offset: 0x00005FB4
		private void timer_2_Tick(object sender, EventArgs e)
		{
			if (this.bindBtn.Text != "none" && this.bindBtn.Text != "...")
			{
				Keys keys_ = (Keys)this.keysConverter_0.ConvertFromString(this.bindBtn.Text.Replace("...", ""));
				if (beta.GetAsyncKeyState(keys_) < 0)
				{
					while (beta.GetAsyncKeyState(keys_) < 0)
					{
						Thread.Sleep(20);
					}
					if (!this.siticoneCustomCheckBox1.Checked)
					{
						this.siticoneCustomCheckBox1.Checked = true;
					}
					else if (this.siticoneCustomCheckBox1.Checked)
					{
						this.siticoneCustomCheckBox1.Checked = false;
					}
				}
			}
		}

		// Token: 0x06000122 RID: 290 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_2(object sender, PreviewKeyDownEventArgs e)
		{
		}

		// Token: 0x06000123 RID: 291 RVA: 0x00002598 File Offset: 0x00000798
		private void bindBtn_Click(object sender, EventArgs e)
		{
			this.timer_2.Start();
			this.bindBtn.Text = "...";
		}

		// Token: 0x06000124 RID: 292 RVA: 0x00007E78 File Offset: 0x00006078
		private void bindBtn_MouseClick(object sender, MouseEventArgs e)
		{
			string text = e.Button.ToString();
			if (!text.Contains("Left") && !text.Contains("Right"))
			{
				if (beta.GetAsyncKeyState(Keys.Escape) < 0)
				{
					this.bindBtn.Text = "none";
				}
				else
				{
					this.bindBtn.Text = text;
				}
			}
		}

		// Token: 0x06000125 RID: 293 RVA: 0x00007EE4 File Offset: 0x000060E4
		private void bindBtn_KeyDown(object sender, KeyEventArgs e)
		{
			string text = e.KeyData.ToString();
			if (!text.Contains("Alt"))
			{
				if (beta.GetAsyncKeyState(Keys.Escape) < 0)
				{
					this.bindBtn.Text = "none";
				}
				else
				{
					this.bindBtn.Text = text;
				}
			}
		}

		// Token: 0x06000126 RID: 294 RVA: 0x00007F40 File Offset: 0x00006140
		private void timer_3_Tick(object sender, EventArgs e)
		{
			new Random();
			Random random = new Random();
			try
			{
				if (this.siticoneCustomCheckBox4.Checked)
				{
					this.timer_3.Interval = 4000 / this.rightcps.Value;
				}
			}
			catch
			{
			}
			if (this.siticoneCustomCheckBox4.Checked && this.siticoneCustomCheckBox4.Checked)
			{
				Process[] processesByName = Process.GetProcessesByName("javaw");
				foreach (Process process in processesByName)
				{
					this.intptr_0 = beta.FindWindow(null, process.MainWindowTitle);
				}
				if (!this.intptr_0.Equals(IntPtr.Zero))
				{
					if (Control.MouseButtons == MouseButtons.Right && !this.siticoneCustomCheckBox3.Checked)
					{
						IntPtr foregroundWindow = beta.GetForegroundWindow();
						beta.SendMessage(foregroundWindow, 515, 1, beta.smethod_0(Control.MousePosition.X, Control.MousePosition.Y));
						Thread.Sleep(random.Next(1, 5));
						beta.SendMessage(foregroundWindow, 516, 1, beta.smethod_0(Control.MousePosition.X, Control.MousePosition.Y));
						Thread.Sleep(random.Next(1, 43));
					}
					Cursor cursor_ = Cursor.Current;
					if (cursor_.smethod_1() && this.siticoneCustomCheckBox3.Checked && Control.MouseButtons == MouseButtons.Right)
					{
						IntPtr foregroundWindow2 = beta.GetForegroundWindow();
						beta.SendMessage(foregroundWindow2, 515, 1, beta.smethod_0(Control.MousePosition.X, Control.MousePosition.Y));
						Thread.Sleep(random.Next(1, 5));
						beta.SendMessage(foregroundWindow2, 516, 1, beta.smethod_0(Control.MousePosition.X, Control.MousePosition.Y));
						Thread.Sleep(random.Next(1, 43));
					}
				}
			}
		}

		// Token: 0x06000127 RID: 295 RVA: 0x00008164 File Offset: 0x00006364
		private void method_3(object sender, EventArgs e)
		{
			if (this.siticoneCustomCheckBox4.Checked)
			{
				this.timer_3.Start();
			}
			else
			{
				this.timer_3.Stop();
			}
		}

		// Token: 0x06000128 RID: 296 RVA: 0x000021D4 File Offset: 0x000003D4
		private void skeetGroupBox2_Enter(object sender, EventArgs e)
		{
		}

		// Token: 0x06000129 RID: 297 RVA: 0x000021D4 File Offset: 0x000003D4
		private void skeetGroupBox4_Enter(object sender, EventArgs e)
		{
		}

		// Token: 0x0600012A RID: 298 RVA: 0x00008198 File Offset: 0x00006398
		private void timer_4_Tick(object sender, EventArgs e)
		{
			Random random = new Random();
			Process[] processesByName = Process.GetProcessesByName("javaw");
			foreach (Process process in processesByName)
			{
				this.intptr_0 = beta.FindWindow(null, process.MainWindowTitle);
			}
			Cursor cursor_ = Cursor.Current;
			if (beta.GetAsyncKeyState(Keys.LButton) < 0 && cursor_.smethod_1())
			{
				Random random2 = new Random();
				this.int_11 = 1;
				int num = random2.Next(0, 100);
				if (num <= this.siticoneMetroTrackBar6.Value)
				{
					beta.mouse_event(2U, 0, 0, 0, 0);
					Thread.Sleep(random.Next(1, 6));
					beta.mouse_event(4U, 0, 0, 0, 0);
					Thread.Sleep(this.delay1.Value);
				}
			}
		}

		// Token: 0x0600012B RID: 299 RVA: 0x0000825C File Offset: 0x0000645C
		private void siticoneCustomCheckBox9_CheckedChanged(object sender, EventArgs e)
		{
			if (this.siticoneCustomCheckBox9.Checked)
			{
				this.timer_4.Start();
			}
			else
			{
				this.timer_4.Stop();
			}
		}

		// Token: 0x0600012C RID: 300 RVA: 0x000021D4 File Offset: 0x000003D4
		private void skeetGroupBox3_Enter(object sender, EventArgs e)
		{
		}

		// Token: 0x0600012D RID: 301 RVA: 0x00008290 File Offset: 0x00006490
		private void outlineLabel4_Click(object sender, EventArgs e)
		{
			this.skeetGroupBox1.Visible = false;
			this.skeetGroupBox2.Visible = false;
			this.skeetGroupBox3.Visible = false;
			this.skeetGroupBox4.Visible = false;
			this.skeetGroupBox5.Visible = true;
			this.skeetGroupBox5.Location = new Point(8, 35);
		}

		// Token: 0x0600012E RID: 302 RVA: 0x000025B5 File Offset: 0x000007B5
		private void outlineLabel5_Click(object sender, EventArgs e)
		{
			MessageBox.Show("clicker tab is only working right now, other stuff will be added soon.");
		}

		// Token: 0x0600012F RID: 303 RVA: 0x000025B5 File Offset: 0x000007B5
		private void outlineLabel6_Click(object sender, EventArgs e)
		{
			MessageBox.Show("clicker tab is only working right now, other stuff will be added soon.");
		}

		// Token: 0x06000130 RID: 304 RVA: 0x000082EC File Offset: 0x000064EC
		private void timer_5_Tick(object sender, EventArgs e)
		{
			if (this.bindbtn2.Text != "none" && this.bindbtn2.Text != "...")
			{
				Keys keys_ = (Keys)this.keysConverter_0.ConvertFromString(this.bindbtn2.Text.Replace("...", ""));
				if (beta.GetAsyncKeyState(keys_) < 0)
				{
					while (beta.GetAsyncKeyState(keys_) < 0)
					{
						Thread.Sleep(20);
					}
					if (!this.siticoneCustomCheckBox4.Checked)
					{
						this.siticoneCustomCheckBox4.Checked = true;
					}
					else if (this.siticoneCustomCheckBox4.Checked)
					{
						this.siticoneCustomCheckBox4.Checked = false;
					}
				}
			}
		}

		// Token: 0x06000131 RID: 305 RVA: 0x000025C2 File Offset: 0x000007C2
		private void bindbtn3_Click(object sender, EventArgs e)
		{
			this.timer_6.Start();
			this.bindbtn3.Text = "...";
		}

		// Token: 0x06000132 RID: 306 RVA: 0x000025DF File Offset: 0x000007DF
		private void bindbtn2_Click(object sender, EventArgs e)
		{
			this.timer_5.Start();
			this.bindbtn2.Text = "...";
		}

		// Token: 0x06000133 RID: 307 RVA: 0x000083B0 File Offset: 0x000065B0
		private void bindbtn2_KeyDown(object sender, KeyEventArgs e)
		{
			string text = e.KeyData.ToString();
			if (!text.Contains("Alt"))
			{
				if (beta.GetAsyncKeyState(Keys.Escape) < 0)
				{
					this.bindbtn2.Text = "none";
				}
				else
				{
					this.bindbtn2.Text = text;
				}
			}
		}

		// Token: 0x06000134 RID: 308 RVA: 0x0000840C File Offset: 0x0000660C
		private void timer_6_Tick(object sender, EventArgs e)
		{
			if (this.bindbtn3.Text != "none" && this.bindbtn3.Text != "...")
			{
				Keys keys_ = (Keys)this.keysConverter_0.ConvertFromString(this.bindbtn3.Text.Replace("...", ""));
				if (beta.GetAsyncKeyState(keys_) < 0)
				{
					while (beta.GetAsyncKeyState(keys_) < 0)
					{
						Thread.Sleep(20);
					}
					if (!this.siticoneCustomCheckBox9.Checked)
					{
						this.siticoneCustomCheckBox9.Checked = true;
					}
					else if (this.siticoneCustomCheckBox9.Checked)
					{
						this.siticoneCustomCheckBox9.Checked = false;
					}
				}
			}
		}

		// Token: 0x06000135 RID: 309 RVA: 0x000084D0 File Offset: 0x000066D0
		private void bindbtn3_KeyDown(object sender, KeyEventArgs e)
		{
			string text = e.KeyData.ToString();
			if (!text.Contains("Alt"))
			{
				if (beta.GetAsyncKeyState(Keys.Escape) < 0)
				{
					this.bindbtn3.Text = "none";
				}
				else
				{
					this.bindbtn3.Text = text;
				}
			}
		}

		// Token: 0x06000136 RID: 310 RVA: 0x0000852C File Offset: 0x0000672C
		private void siticoneCustomCheckBox6_CheckedChanged(object sender, EventArgs e)
		{
			if (this.siticoneCustomCheckBox6.Checked)
			{
				this.siticoneMetroTrackBar4.Visible = true;
				this.outlineLabel11.Visible = true;
			}
			else
			{
				this.siticoneMetroTrackBar4.Visible = false;
				this.outlineLabel11.Visible = false;
			}
		}

		// Token: 0x06000137 RID: 311 RVA: 0x00008578 File Offset: 0x00006778
		private void siticoneMetroTrackBar4_Paint(object sender, PaintEventArgs e)
		{
			float num = (float)this.siticoneMetroTrackBar4.Value / 10f;
			Rectangle clipRectangle = e.ClipRectangle;
			new StringFormat();
			clipRectangle.Width = (int)((double)clipRectangle.Width * ((double)this.siticoneMetroTrackBar4.Value / (double)this.siticoneMetroTrackBar4.Maximum)) - 4;
			clipRectangle.Height -= 4;
			new Font("Segoe UI", 9f);
			object arg = (SiticoneMetroTrackBar)sender;
			if (beta.Class17.callSite_1 == null)
			{
				beta.Class17.callSite_1 = CallSite<Func<CallSite, Type, object, SolidBrush>>.Create(Binder.InvokeConstructor(CSharpBinderFlags.None, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			Func<CallSite, Type, object, SolidBrush> target = beta.Class17.callSite_1.Target;
			CallSite callSite_ = beta.Class17.callSite_1;
			Type typeFromHandle = typeof(SolidBrush);
			if (beta.Class17.callSite_0 == null)
			{
				beta.Class17.callSite_0 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "FillColor", typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			object obj = target(callSite_, typeFromHandle, beta.Class17.callSite_0.Target(beta.Class17.callSite_0, arg));
			if (beta.Class17.callSite_2 == null)
			{
				beta.Class17.callSite_2 = CallSite<Action<CallSite, Graphics, object, Rectangle>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "FillRectangle", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null)
				}));
			}
			beta.Class17.callSite_2.Target(beta.Class17.callSite_2, e.Graphics, obj, e.ClipRectangle);
			e.Graphics.FillRectangle(this.solidBrush_0, 2, 2, clipRectangle.Width, clipRectangle.Height);
			if (beta.Class17.callSite_3 == null)
			{
				beta.Class17.callSite_3 = CallSite<Action<CallSite, object>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "Dispose", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			beta.Class17.callSite_3.Target(beta.Class17.callSite_3, obj);
		}

		// Token: 0x06000138 RID: 312 RVA: 0x00008784 File Offset: 0x00006984
		private void siticoneMetroTrackBar4_Scroll(object sender, ScrollEventArgs e)
		{
			this.outlineLabel11.Text = "block hit chance: " + this.siticoneMetroTrackBar4.Value.ToString() + "%";
		}

		// Token: 0x06000139 RID: 313 RVA: 0x000087C0 File Offset: 0x000069C0
		private void siticoneCustomCheckBox5_CheckedChanged(object sender, EventArgs e)
		{
			if (this.siticoneCustomCheckBox5.Checked)
			{
				this.outlineLabel25.Visible = true;
				this.pictureBox1.Visible = true;
				this.siticoneMetroTrackBar5.Visible = true;
				this.timer_7.Start();
			}
			if (!this.siticoneCustomCheckBox5.Checked)
			{
				this.outlineLabel25.Visible = false;
				this.pictureBox1.Visible = false;
				this.siticoneMetroTrackBar5.Visible = false;
				this.timer_7.Stop();
			}
		}

		// Token: 0x0600013A RID: 314 RVA: 0x00008848 File Offset: 0x00006A48
		private void timer_7_Tick(object sender, EventArgs e)
		{
			Random random = new Random();
			Cursor cursor_ = Cursor.Current;
			if (cursor_.smethod_1() && this.siticoneCustomCheckBox5.Checked && Control.MouseButtons == MouseButtons.Left)
			{
				int x = Cursor.Position.X;
				int y = Cursor.Position.Y;
				if (this.siticoneMetroTrackBar5.Value == 1)
				{
					beta.SetCursorPos(x - 5 + random.Next(1, 10), y - 5 + random.Next(1, 10));
					this.timer_7.Interval = 10;
				}
				if (this.siticoneMetroTrackBar5.Value == 2)
				{
					beta.SetCursorPos(x - 10 + random.Next(5, 15), y - 10 + random.Next(5, 15));
					this.timer_7.Interval = 10;
				}
				if (this.siticoneMetroTrackBar5.Value == 3)
				{
					beta.SetCursorPos(x - 15 + random.Next(10, 20), y - 15 + random.Next(10, 20));
					this.timer_7.Interval = 10;
				}
				if (this.siticoneMetroTrackBar5.Value == 4)
				{
					this.timer_7.Interval = 10;
					beta.SetCursorPos(x - 20 + random.Next(15, 25), y - 20 + random.Next(15, 25));
				}
			}
		}

		// Token: 0x0600013B RID: 315 RVA: 0x000089AC File Offset: 0x00006BAC
		private void siticoneMetroTrackBar5_Scroll(object sender, ScrollEventArgs e)
		{
			if (this.siticoneMetroTrackBar5.Value == 1)
			{
				this.outlineLabel25.Text = "jitter size: 1";
				this.pictureBox1.Image = Class29.Bitmap_0;
			}
			if (this.siticoneMetroTrackBar5.Value == 2)
			{
				this.outlineLabel25.Text = "jitter size: 2";
				this.pictureBox1.Image = Class29.Bitmap_1;
			}
			if (this.siticoneMetroTrackBar5.Value == 3)
			{
				this.outlineLabel25.Text = "jitter size: 3";
				this.pictureBox1.Image = Class29.Bitmap_2;
			}
			if (this.siticoneMetroTrackBar5.Value == 4)
			{
				this.outlineLabel25.Text = "jitter size: 4";
				this.pictureBox1.Image = Class29.Bitmap_3;
			}
		}

		// Token: 0x0600013C RID: 316 RVA: 0x000021D4 File Offset: 0x000003D4
		private void pictureBox1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600013D RID: 317 RVA: 0x00008A7C File Offset: 0x00006C7C
		private void siticoneMetroTrackBar5_Paint(object sender, PaintEventArgs e)
		{
			float num = (float)this.siticoneMetroTrackBar5.Value / 10f;
			Rectangle clipRectangle = e.ClipRectangle;
			new StringFormat();
			clipRectangle.Width = (int)((double)clipRectangle.Width * ((double)this.siticoneMetroTrackBar5.Value / (double)this.siticoneMetroTrackBar5.Maximum)) - 4;
			clipRectangle.Height -= 4;
			new Font("Segoe UI", 9f);
			object arg = (SiticoneMetroTrackBar)sender;
			if (beta.Class18.callSite_1 == null)
			{
				beta.Class18.callSite_1 = CallSite<Func<CallSite, Type, object, SolidBrush>>.Create(Binder.InvokeConstructor(CSharpBinderFlags.None, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			Func<CallSite, Type, object, SolidBrush> target = beta.Class18.callSite_1.Target;
			CallSite callSite_ = beta.Class18.callSite_1;
			Type typeFromHandle = typeof(SolidBrush);
			if (beta.Class18.callSite_0 == null)
			{
				beta.Class18.callSite_0 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "FillColor", typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			object obj = target(callSite_, typeFromHandle, beta.Class18.callSite_0.Target(beta.Class18.callSite_0, arg));
			if (beta.Class18.callSite_2 == null)
			{
				beta.Class18.callSite_2 = CallSite<Action<CallSite, Graphics, object, Rectangle>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "FillRectangle", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null)
				}));
			}
			beta.Class18.callSite_2.Target(beta.Class18.callSite_2, e.Graphics, obj, e.ClipRectangle);
			e.Graphics.FillRectangle(this.solidBrush_0, 2, 2, clipRectangle.Width, clipRectangle.Height);
			if (beta.Class18.callSite_3 == null)
			{
				beta.Class18.callSite_3 = CallSite<Action<CallSite, object>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "Dispose", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			beta.Class18.callSite_3.Target(beta.Class18.callSite_3, obj);
		}

		// Token: 0x0600013E RID: 318 RVA: 0x00008C88 File Offset: 0x00006E88
		private void siticoneMetroTrackBar6_Scroll(object sender, ScrollEventArgs e)
		{
			this.outlineLabel28.Text = "chance: " + this.siticoneMetroTrackBar6.Value.ToString() + "%";
		}

		// Token: 0x0600013F RID: 319 RVA: 0x00008CC4 File Offset: 0x00006EC4
		private void siticoneMetroTrackBar6_Paint(object sender, PaintEventArgs e)
		{
			float num = (float)this.siticoneMetroTrackBar6.Value / 10f;
			Rectangle clipRectangle = e.ClipRectangle;
			new StringFormat();
			clipRectangle.Width = (int)((double)clipRectangle.Width * ((double)this.siticoneMetroTrackBar6.Value / (double)this.siticoneMetroTrackBar6.Maximum)) - 4;
			clipRectangle.Height -= 4;
			new Font("Segoe UI", 9f);
			object arg = (SiticoneMetroTrackBar)sender;
			if (beta.Class19.callSite_1 == null)
			{
				beta.Class19.callSite_1 = CallSite<Func<CallSite, Type, object, SolidBrush>>.Create(Binder.InvokeConstructor(CSharpBinderFlags.None, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.IsStaticType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			Func<CallSite, Type, object, SolidBrush> target = beta.Class19.callSite_1.Target;
			CallSite callSite_ = beta.Class19.callSite_1;
			Type typeFromHandle = typeof(SolidBrush);
			if (beta.Class19.callSite_0 == null)
			{
				beta.Class19.callSite_0 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "FillColor", typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			object obj = target(callSite_, typeFromHandle, beta.Class19.callSite_0.Target(beta.Class19.callSite_0, arg));
			if (beta.Class19.callSite_2 == null)
			{
				beta.Class19.callSite_2 = CallSite<Action<CallSite, Graphics, object, Rectangle>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "FillRectangle", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType, null)
				}));
			}
			beta.Class19.callSite_2.Target(beta.Class19.callSite_2, e.Graphics, obj, e.ClipRectangle);
			e.Graphics.FillRectangle(this.solidBrush_0, 2, 2, clipRectangle.Width, clipRectangle.Height);
			if (beta.Class19.callSite_3 == null)
			{
				beta.Class19.callSite_3 = CallSite<Action<CallSite, object>>.Create(Binder.InvokeMember(CSharpBinderFlags.ResultDiscarded, "Dispose", null, typeof(beta), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			beta.Class19.callSite_3.Target(beta.Class19.callSite_3, obj);
		}

		// Token: 0x06000140 RID: 320 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_4(object sender, EventArgs e)
		{
		}

		// Token: 0x06000141 RID: 321 RVA: 0x00008ED0 File Offset: 0x000070D0
		private void method_5(object sender, ColorChangedEventArgs e)
		{
			this.timer_8.Stop();
			new SolidBrush(this.winColorPicker1.SelectedColor);
			this.outlineLabel2.ForeColor = this.winColorPicker1.SelectedColor;
			this.guna2GradientPanel1.ShadowDecoration.Color = this.winColorPicker1.SelectedColor;
			this.solidBrush_0.Color = this.winColorPicker1.SelectedColor;
			this.siticoneCustomCheckBox1.CheckedState.FillColor = this.winColorPicker1.SelectedColor;
			this.siticoneCustomCheckBox2.CheckedState.FillColor = this.winColorPicker1.SelectedColor;
			this.siticoneCustomCheckBox3.CheckedState.FillColor = this.winColorPicker1.SelectedColor;
			this.siticoneCustomCheckBox4.CheckedState.FillColor = this.winColorPicker1.SelectedColor;
			this.siticoneCustomCheckBox5.CheckedState.FillColor = this.winColorPicker1.SelectedColor;
			this.siticoneCustomCheckBox6.CheckedState.FillColor = this.winColorPicker1.SelectedColor;
			this.random1.CheckedState.FillColor = this.winColorPicker1.SelectedColor;
			this.smart.CheckedState.FillColor = this.winColorPicker1.SelectedColor;
			this.custom.CheckedState.FillColor = this.winColorPicker1.SelectedColor;
			this.siticoneCustomCheckBox9.CheckedState.FillColor = this.winColorPicker1.SelectedColor;
		}

		// Token: 0x06000142 RID: 322 RVA: 0x000021D4 File Offset: 0x000003D4
		private void timer_8_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x06000143 RID: 323 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_6(object sender, EventArgs e)
		{
		}

		// Token: 0x06000144 RID: 324 RVA: 0x000025FC File Offset: 0x000007FC
		private void outlineLabel3_Click(object sender, EventArgs e)
		{
			this.skeetGroupBox1.Visible = true;
			this.skeetGroupBox2.Visible = true;
			this.skeetGroupBox3.Visible = true;
			this.skeetGroupBox4.Visible = true;
			this.skeetGroupBox5.Visible = false;
		}

		// Token: 0x06000145 RID: 325 RVA: 0x000021D4 File Offset: 0x000003D4
		private void outlineLabel31_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000146 RID: 326 RVA: 0x0000263A File Offset: 0x0000083A
		private void outlineLabel3_MouseEnter(object sender, EventArgs e)
		{
			this.outlineLabel3.ForeColor = this.winColorPicker1.SelectedColor;
		}

		// Token: 0x06000147 RID: 327 RVA: 0x00002652 File Offset: 0x00000852
		private void outlineLabel3_MouseLeave(object sender, EventArgs e)
		{
			this.outlineLabel3.ForeColor = Color.DarkGray;
		}

		// Token: 0x06000148 RID: 328 RVA: 0x00002664 File Offset: 0x00000864
		private void outlineLabel4_MouseEnter(object sender, EventArgs e)
		{
			this.outlineLabel4.ForeColor = this.winColorPicker1.SelectedColor;
		}

		// Token: 0x06000149 RID: 329 RVA: 0x0000267C File Offset: 0x0000087C
		private void outlineLabel4_MouseLeave(object sender, EventArgs e)
		{
			this.outlineLabel4.ForeColor = Color.DarkGray;
		}

		// Token: 0x0600014A RID: 330 RVA: 0x00009050 File Offset: 0x00007250
		private void timer_9_Tick(object sender, EventArgs e)
		{
			this.outlineLabel7.Text = "average cps: " + (this.leftcps.Value / 4).ToString();
			this.outlineLabel15.Text = "average cps: " + (this.rightcps.Value / 4).ToString();
			this.leftcps.Value += 3;
			this.rightcps.Value += 3;
			if (this.leftcps.Value == 50 && this.rightcps.Value == 50)
			{
				this.timer_9.Stop();
			}
		}

		// Token: 0x0600014B RID: 331 RVA: 0x0000268E File Offset: 0x0000088E
		private void beta_FormClosing(object sender, FormClosingEventArgs e)
		{
			Environment.Exit(0);
		}

		// Token: 0x0600014C RID: 332 RVA: 0x0000268E File Offset: 0x0000088E
		private void beta_FormClosed(object sender, FormClosedEventArgs e)
		{
			Environment.Exit(0);
		}

		// Token: 0x0600014D RID: 333 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_7(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x0600014E RID: 334 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_8(object sender, EventArgs e)
		{
		}

		// Token: 0x0600014F RID: 335 RVA: 0x00002696 File Offset: 0x00000896
		private void method_9(object sender, EventArgs e)
		{
			base.Hide();
			base.Show();
		}

		// Token: 0x06000150 RID: 336 RVA: 0x00009108 File Offset: 0x00007308
		protected virtual void Dispose(bool disposing)
		{
			if (disposing && this.icontainer_0 != null)
			{
				this.icontainer_0.Dispose();
			}
			base.Dispose(disposing);
		}

		// Token: 0x04000261 RID: 609
		private const int int_0 = 2;

		// Token: 0x04000262 RID: 610
		private const int int_1 = 4;

		// Token: 0x04000263 RID: 611
		private const int int_2 = 8;

		// Token: 0x04000264 RID: 612
		private const int int_3 = 16;

		// Token: 0x04000265 RID: 613
		private const int int_4 = 32768;

		// Token: 0x04000266 RID: 614
		public const int int_5 = 161;

		// Token: 0x04000267 RID: 615
		public const int int_6 = 2;

		// Token: 0x04000268 RID: 616
		public const int int_7 = 513;

		// Token: 0x04000269 RID: 617
		public const int int_8 = 514;

		// Token: 0x0400026A RID: 618
		public const int int_9 = 516;

		// Token: 0x0400026B RID: 619
		public const int int_10 = 517;

		// Token: 0x0400026C RID: 620
		private SolidBrush solidBrush_0 = new SolidBrush(Color.DodgerBlue);

		// Token: 0x0400026D RID: 621
		private SolidBrush solidBrush_1 = new SolidBrush(Color.White);

		// Token: 0x0400026E RID: 622
		private int int_11;

		// Token: 0x0400026F RID: 623
		private int int_12;

		// Token: 0x04000270 RID: 624
		private int int_13;

		// Token: 0x04000271 RID: 625
		private IntPtr intptr_0;

		// Token: 0x04000272 RID: 626
		private int int_14 = 255;

		// Token: 0x04000273 RID: 627
		private int int_15 = 0;

		// Token: 0x04000274 RID: 628
		private int int_16 = 0;

		// Token: 0x04000275 RID: 629
		private KeysConverter keysConverter_0 = new KeysConverter();

		// Token: 0x0200002E RID: 46
		[CompilerGenerated]
		private static class Class11
		{
			// Token: 0x040002C7 RID: 711
			public static CallSite<Func<CallSite, object, object>> callSite_0;

			// Token: 0x040002C8 RID: 712
			public static CallSite<Func<CallSite, Type, object, SolidBrush>> callSite_1;

			// Token: 0x040002C9 RID: 713
			public static CallSite<Action<CallSite, Graphics, object, Rectangle>> callSite_2;

			// Token: 0x040002CA RID: 714
			public static CallSite<Action<CallSite, object>> callSite_3;
		}

		// Token: 0x0200002F RID: 47
		[CompilerGenerated]
		private static class Class12
		{
			// Token: 0x040002CB RID: 715
			public static CallSite<Func<CallSite, object, object>> callSite_0;

			// Token: 0x040002CC RID: 716
			public static CallSite<Func<CallSite, Type, object, SolidBrush>> callSite_1;

			// Token: 0x040002CD RID: 717
			public static CallSite<Action<CallSite, Graphics, object, Rectangle>> callSite_2;

			// Token: 0x040002CE RID: 718
			public static CallSite<Action<CallSite, object>> callSite_3;
		}

		// Token: 0x02000030 RID: 48
		[CompilerGenerated]
		private static class Class13
		{
			// Token: 0x040002CF RID: 719
			public static CallSite<Func<CallSite, object, object>> callSite_0;

			// Token: 0x040002D0 RID: 720
			public static CallSite<Func<CallSite, Type, object, SolidBrush>> callSite_1;

			// Token: 0x040002D1 RID: 721
			public static CallSite<Action<CallSite, Graphics, object, Rectangle>> callSite_2;

			// Token: 0x040002D2 RID: 722
			public static CallSite<Action<CallSite, object>> callSite_3;
		}

		// Token: 0x02000031 RID: 49
		[CompilerGenerated]
		private static class Class14
		{
			// Token: 0x040002D3 RID: 723
			public static CallSite<Func<CallSite, object, object>> callSite_0;

			// Token: 0x040002D4 RID: 724
			public static CallSite<Func<CallSite, Type, object, SolidBrush>> callSite_1;

			// Token: 0x040002D5 RID: 725
			public static CallSite<Action<CallSite, Graphics, object, Rectangle>> callSite_2;

			// Token: 0x040002D6 RID: 726
			public static CallSite<Action<CallSite, object>> callSite_3;
		}

		// Token: 0x02000032 RID: 50
		[CompilerGenerated]
		private static class Class15
		{
			// Token: 0x040002D7 RID: 727
			public static CallSite<Func<CallSite, object, object>> callSite_0;

			// Token: 0x040002D8 RID: 728
			public static CallSite<Func<CallSite, Type, object, SolidBrush>> callSite_1;

			// Token: 0x040002D9 RID: 729
			public static CallSite<Action<CallSite, Graphics, object, Rectangle>> callSite_2;

			// Token: 0x040002DA RID: 730
			public static CallSite<Action<CallSite, object>> callSite_3;
		}

		// Token: 0x02000033 RID: 51
		[CompilerGenerated]
		private static class Class16
		{
			// Token: 0x040002DB RID: 731
			public static CallSite<Func<CallSite, object, object>> callSite_0;

			// Token: 0x040002DC RID: 732
			public static CallSite<Func<CallSite, Type, object, SolidBrush>> callSite_1;

			// Token: 0x040002DD RID: 733
			public static CallSite<Action<CallSite, Graphics, object, Rectangle>> callSite_2;

			// Token: 0x040002DE RID: 734
			public static CallSite<Action<CallSite, object>> callSite_3;
		}

		// Token: 0x02000034 RID: 52
		[CompilerGenerated]
		private static class Class17
		{
			// Token: 0x040002DF RID: 735
			public static CallSite<Func<CallSite, object, object>> callSite_0;

			// Token: 0x040002E0 RID: 736
			public static CallSite<Func<CallSite, Type, object, SolidBrush>> callSite_1;

			// Token: 0x040002E1 RID: 737
			public static CallSite<Action<CallSite, Graphics, object, Rectangle>> callSite_2;

			// Token: 0x040002E2 RID: 738
			public static CallSite<Action<CallSite, object>> callSite_3;
		}

		// Token: 0x02000035 RID: 53
		[CompilerGenerated]
		private static class Class18
		{
			// Token: 0x040002E3 RID: 739
			public static CallSite<Func<CallSite, object, object>> callSite_0;

			// Token: 0x040002E4 RID: 740
			public static CallSite<Func<CallSite, Type, object, SolidBrush>> callSite_1;

			// Token: 0x040002E5 RID: 741
			public static CallSite<Action<CallSite, Graphics, object, Rectangle>> callSite_2;

			// Token: 0x040002E6 RID: 742
			public static CallSite<Action<CallSite, object>> callSite_3;
		}

		// Token: 0x02000036 RID: 54
		[CompilerGenerated]
		private static class Class19
		{
			// Token: 0x040002E7 RID: 743
			public static CallSite<Func<CallSite, object, object>> callSite_0;

			// Token: 0x040002E8 RID: 744
			public static CallSite<Func<CallSite, Type, object, SolidBrush>> callSite_1;

			// Token: 0x040002E9 RID: 745
			public static CallSite<Action<CallSite, Graphics, object, Rectangle>> callSite_2;

			// Token: 0x040002EA RID: 746
			public static CallSite<Action<CallSite, object>> callSite_3;
		}
	}
}
