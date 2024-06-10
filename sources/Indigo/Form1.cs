using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Text;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Guna.UI2.WinForms;
using Memory;

namespace WindowsFormsApp10
{
	// Token: 0x02000002 RID: 2
	public partial class Form1 : Form
	{
		// Token: 0x06000001 RID: 1
		[DllImport("user32.dll")]
		private static extern short GetAsyncKeyState(Keys vKey);

		// Token: 0x06000002 RID: 2 RVA: 0x00002048 File Offset: 0x00000248
		public Form1()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000003 RID: 3 RVA: 0x00002130 File Offset: 0x00000330
		private void Form1_Load(object sender, EventArgs e)
		{
			int procIdFromName = this.MemLib.GetProcIdFromName("Minecraft.Windows");
			bool flag = procIdFromName < 1;
			if (flag)
			{
				MessageBox.Show("Minecraft is not running");
				Environment.Exit(0);
			}
			base.TopMost = true;
			this.Minecraft.Inject();
		}

		// Token: 0x06000004 RID: 4 RVA: 0x0000217F File Offset: 0x0000037F
		private void consoleKeys()
		{
		}

		// Token: 0x06000005 RID: 5 RVA: 0x00002182 File Offset: 0x00000382
		private void guna2Panel1_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x06000006 RID: 6 RVA: 0x00002185 File Offset: 0x00000385
		private void panel1_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x06000007 RID: 7 RVA: 0x00002188 File Offset: 0x00000388
		private void Form1_KeyDown(object sender, KeyEventArgs e)
		{
		}

		// Token: 0x06000008 RID: 8 RVA: 0x0000218B File Offset: 0x0000038B
		private void label1_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000009 RID: 9 RVA: 0x0000218E File Offset: 0x0000038E
		private void guna2Panel2_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x0600000A RID: 10 RVA: 0x00002191 File Offset: 0x00000391
		private void label3_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600000B RID: 11 RVA: 0x00002194 File Offset: 0x00000394
		private void label3_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x0600000C RID: 12 RVA: 0x00002197 File Offset: 0x00000397
		private void label6_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600000D RID: 13 RVA: 0x0000219A File Offset: 0x0000039A
		private void label4_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600000E RID: 14 RVA: 0x000021A0 File Offset: 0x000003A0
		private void timer2_Tick(object sender, EventArgs e)
		{
			bool flag = Form1.GetAsyncKeyState(Keys.W) < 0;
			if (flag)
			{
				this.guna2Button16.CustomBorderColor = Color.FromArgb(213, 0, 0);
			}
			else
			{
				this.guna2Button16.CustomBorderColor = Color.FromArgb(103, 58, 183);
			}
			bool flag2 = Form1.GetAsyncKeyState(Keys.S) < 0;
			if (flag2)
			{
				this.guna2Button14.CustomBorderColor = Color.FromArgb(213, 0, 0);
			}
			else
			{
				this.guna2Button14.CustomBorderColor = Color.FromArgb(103, 58, 183);
			}
			bool flag3 = Form1.GetAsyncKeyState(Keys.A) < 0;
			if (flag3)
			{
				this.guna2Button13.CustomBorderColor = Color.FromArgb(213, 0, 0);
			}
			else
			{
				this.guna2Button13.CustomBorderColor = Color.FromArgb(103, 58, 183);
			}
			bool flag4 = Form1.GetAsyncKeyState(Keys.D) < 0;
			if (flag4)
			{
				this.guna2Button15.CustomBorderColor = Color.FromArgb(213, 0, 0);
			}
			else
			{
				this.guna2Button15.CustomBorderColor = Color.FromArgb(103, 58, 183);
			}
			bool flag5 = Form1.GetAsyncKeyState(Keys.Space) < 0;
			if (flag5)
			{
				this.guna2Button17.CustomBorderColor = Color.FromArgb(213, 0, 0);
			}
			else
			{
				this.guna2Button17.CustomBorderColor = Color.FromArgb(103, 58, 183);
			}
			bool @checked = this.guna2Button28.Checked;
			if (@checked)
			{
				this.Minecraft.noSwing();
			}
			else
			{
				this.Minecraft.enableSwing();
			}
			bool checked2 = this.guna2Button9.Checked;
			if (checked2)
			{
				bool flag6 = Form1.GetAsyncKeyState(Keys.W) < 0;
				if (flag6)
				{
					this.Minecraft.autoSprint();
				}
				else
				{
					this.Minecraft.normalWalk();
				}
			}
			bool checked3 = this.guna2Button20.Checked;
			if (checked3)
			{
				this.Minecraft.noWeb();
			}
			else
			{
				this.Minecraft.cobWeb();
			}
			bool checked4 = this.guna2Button6.Checked;
			if (checked4)
			{
				this.Minecraft.speedHack();
			}
			bool checked5 = this.guna2Button2.Checked;
			if (checked5)
			{
				this.Minecraft.instabreak = 1;
				this.Minecraft.instaBreak();
			}
			else
			{
				this.Minecraft.instabreak = 0;
			}
			bool checked6 = this.guna2Button1.Checked;
			if (checked6)
			{
				bool flag7 = Form1.GetAsyncKeyState(Keys.C) < 0;
				if (flag7)
				{
					this.Minecraft.hideHand();
					this.Minecraft.Zoom();
				}
				else
				{
					this.Minecraft.showHand();
					this.Minecraft.unZoom();
				}
			}
			bool checked7 = this.guna2Button7.Checked;
			if (checked7)
			{
				this.Minecraft.brightness();
			}
			else
			{
				this.Minecraft.normalBright();
			}
			bool checked8 = this.guna2Button4.Checked;
			if (checked8)
			{
				this.Minecraft.clearWater();
			}
			else
			{
				this.Minecraft.onoff = 0;
			}
			bool flag8 = Control.IsKeyLocked(Keys.Insert);
			if (flag8)
			{
				this.guna2Panel2.Visible = true;
				this.guna2Panel3.Visible = true;
				this.guna2Panel4.Visible = true;
				this.guna2Panel5.Visible = true;
			}
			else
			{
				this.guna2Panel2.Visible = false;
				this.guna2Panel3.Visible = false;
				this.guna2Panel4.Visible = false;
				this.guna2Panel5.Visible = false;
			}
		}

		// Token: 0x0600000F RID: 15 RVA: 0x00002541 File Offset: 0x00000741
		private void consoleCommands()
		{
		}

		// Token: 0x06000010 RID: 16 RVA: 0x00002544 File Offset: 0x00000744
		private void timer3_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x06000011 RID: 17 RVA: 0x00002547 File Offset: 0x00000747
		private void label7_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000012 RID: 18 RVA: 0x0000254A File Offset: 0x0000074A
		private void label7_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000013 RID: 19 RVA: 0x0000254D File Offset: 0x0000074D
		private void guna2TextBox1_TextChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x06000014 RID: 20 RVA: 0x00002550 File Offset: 0x00000750
		private void label8_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000015 RID: 21 RVA: 0x00002553 File Offset: 0x00000753
		private void label9_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000016 RID: 22 RVA: 0x00002556 File Offset: 0x00000756
		private void guna2TextBox2_TextChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x06000017 RID: 23 RVA: 0x00002559 File Offset: 0x00000759
		private void guna2TextBox3_TextChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x06000018 RID: 24 RVA: 0x0000255C File Offset: 0x0000075C
		private void timer3_Tick_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000019 RID: 25 RVA: 0x0000255F File Offset: 0x0000075F
		private void guna2TextBox2_KeyDown(object sender, KeyEventArgs e)
		{
		}

		// Token: 0x0600001A RID: 26 RVA: 0x00002562 File Offset: 0x00000762
		private void timer3_Tick_2(object sender, EventArgs e)
		{
		}

		// Token: 0x0600001B RID: 27 RVA: 0x00002565 File Offset: 0x00000765
		private void guna2Panel1_Paint_1(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x0600001C RID: 28 RVA: 0x00002568 File Offset: 0x00000768
		private void guna2HtmlLabel1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600001D RID: 29 RVA: 0x0000256B File Offset: 0x0000076B
		private void guna2TextBox4_TextChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x0600001E RID: 30 RVA: 0x0000256E File Offset: 0x0000076E
		private void guna2Panel2_Paint_1(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x0600001F RID: 31 RVA: 0x00002574 File Offset: 0x00000774
		private void guna2Panel1_MouseDown(object sender, MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Left;
			if (flag)
			{
				this.MovePanel = e.Location;
			}
		}

		// Token: 0x06000020 RID: 32 RVA: 0x000025A4 File Offset: 0x000007A4
		private void guna2Panel1_MouseMove(object sender, MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Left;
			if (flag)
			{
			}
		}

		// Token: 0x06000021 RID: 33 RVA: 0x000025C5 File Offset: 0x000007C5
		private void label5_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000022 RID: 34 RVA: 0x000025C8 File Offset: 0x000007C8
		private void label2_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000023 RID: 35 RVA: 0x000025CB File Offset: 0x000007CB
		private void guna2Button3_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000024 RID: 36 RVA: 0x000025CE File Offset: 0x000007CE
		private void guna2Panel2_Paint_2(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x06000025 RID: 37 RVA: 0x000025D1 File Offset: 0x000007D1
		private void guna2Button2_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000026 RID: 38 RVA: 0x000025D4 File Offset: 0x000007D4
		private void guna2Panel2_MouseDown(object sender, MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Left;
			if (flag)
			{
				this.MovePanel = e.Location;
			}
		}

		// Token: 0x06000027 RID: 39 RVA: 0x00002604 File Offset: 0x00000804
		private void guna2Panel2_MouseMove(object sender, MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Left;
			if (flag)
			{
				this.guna2Panel2.Left = e.X + this.guna2Panel2.Left - this.MovePanel.X;
				this.guna2Panel2.Top = e.Y + this.guna2Panel2.Top - this.MovePanel.Y;
			}
		}

		// Token: 0x06000028 RID: 40 RVA: 0x00002679 File Offset: 0x00000879
		private void guna2Panel3_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x06000029 RID: 41 RVA: 0x0000267C File Offset: 0x0000087C
		private void guna2Button1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600002A RID: 42 RVA: 0x0000267F File Offset: 0x0000087F
		private void Zoom_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x0600002B RID: 43 RVA: 0x00002684 File Offset: 0x00000884
		private void guna2Panel3_MouseMove(object sender, MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Left;
			if (flag)
			{
				this.guna2Panel3.Left = e.X + this.guna2Panel3.Left - this.MovePanel.X;
				this.guna2Panel3.Top = e.Y + this.guna2Panel3.Top - this.MovePanel.Y;
			}
		}

		// Token: 0x0600002C RID: 44 RVA: 0x000026FC File Offset: 0x000008FC
		private void guna2Panel3_MouseDown(object sender, MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Left;
			if (flag)
			{
				this.MovePanel = e.Location;
			}
		}

		// Token: 0x0600002D RID: 45 RVA: 0x00002729 File Offset: 0x00000929
		private void guna2Button4_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600002E RID: 46 RVA: 0x0000272C File Offset: 0x0000092C
		private void guna2Button5_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600002F RID: 47 RVA: 0x0000272F File Offset: 0x0000092F
		private void guna2Panel4_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x06000030 RID: 48 RVA: 0x00002732 File Offset: 0x00000932
		private void guna2Button6_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000031 RID: 49 RVA: 0x00002735 File Offset: 0x00000935
		private void guna2Button6_MouseDown(object sender, MouseEventArgs e)
		{
		}

		// Token: 0x06000032 RID: 50 RVA: 0x00002738 File Offset: 0x00000938
		private void guna2Button7_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000033 RID: 51 RVA: 0x0000273B File Offset: 0x0000093B
		private void guna2HtmlToolTip2_Popup(object sender, PopupEventArgs e)
		{
		}

		// Token: 0x06000034 RID: 52 RVA: 0x0000273E File Offset: 0x0000093E
		private void guna2Button1_MouseLeave(object sender, EventArgs e)
		{
		}

		// Token: 0x06000035 RID: 53 RVA: 0x00002741 File Offset: 0x00000941
		private void guna2Button1_MouseHover(object sender, EventArgs e)
		{
			this.guna2HtmlToolTip1.SetToolTip(this.guna2Button1, "Zoom: Allows your player to zoom");
		}

		// Token: 0x06000036 RID: 54 RVA: 0x0000275B File Offset: 0x0000095B
		private void guna2Button6_MouseHover(object sender, EventArgs e)
		{
		}

		// Token: 0x06000037 RID: 55 RVA: 0x0000275E File Offset: 0x0000095E
		private void guna2Button7_MouseHover(object sender, EventArgs e)
		{
		}

		// Token: 0x06000038 RID: 56 RVA: 0x00002761 File Offset: 0x00000961
		private void guna2HtmlToolTip3_Popup(object sender, PopupEventArgs e)
		{
		}

		// Token: 0x06000039 RID: 57 RVA: 0x00002764 File Offset: 0x00000964
		private void guna2Panel4_MouseDown(object sender, MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Left;
			if (flag)
			{
				this.MovePanel = e.Location;
			}
		}

		// Token: 0x0600003A RID: 58 RVA: 0x00002794 File Offset: 0x00000994
		private void guna2Panel4_MouseMove(object sender, MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Left;
			if (flag)
			{
				this.guna2Panel4.Left = e.X + this.guna2Panel4.Left - this.MovePanel.X;
				this.guna2Panel4.Top = e.Y + this.guna2Panel4.Top - this.MovePanel.Y;
			}
		}

		// Token: 0x0600003B RID: 59 RVA: 0x0000280C File Offset: 0x00000A0C
		private void guna2Panel5_MouseDown(object sender, MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Left;
			if (flag)
			{
				this.MovePanel = e.Location;
			}
		}

		// Token: 0x0600003C RID: 60 RVA: 0x0000283C File Offset: 0x00000A3C
		private void guna2Panel5_MouseMove(object sender, MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Left;
			if (flag)
			{
				this.guna2Panel5.Left = e.X + this.guna2Panel5.Left - this.MovePanel.X;
				this.guna2Panel5.Top = e.Y + this.guna2Panel5.Top - this.MovePanel.Y;
			}
		}

		// Token: 0x0600003D RID: 61 RVA: 0x000028B1 File Offset: 0x00000AB1
		private void guna2Button6_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x0600003E RID: 62 RVA: 0x000028B4 File Offset: 0x00000AB4
		private void guna2Button7_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x0600003F RID: 63 RVA: 0x000028B7 File Offset: 0x00000AB7
		private void guna2Button8_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000040 RID: 64 RVA: 0x000028BA File Offset: 0x00000ABA
		private void guna2Button8_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000041 RID: 65 RVA: 0x000028BD File Offset: 0x00000ABD
		private void guna2Button2_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000042 RID: 66 RVA: 0x000028C0 File Offset: 0x00000AC0
		private void guna2Button6_Click_2(object sender, EventArgs e)
		{
		}

		// Token: 0x06000043 RID: 67 RVA: 0x000028C3 File Offset: 0x00000AC3
		private void guna2TrackBar2_Scroll(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x06000044 RID: 68 RVA: 0x000028C6 File Offset: 0x00000AC6
		private void guna2TrackBar1_Scroll(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x06000045 RID: 69 RVA: 0x000028C9 File Offset: 0x00000AC9
		private void label12_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000046 RID: 70 RVA: 0x000028CC File Offset: 0x00000ACC
		private void guna2TrackBar4_Scroll(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x06000047 RID: 71 RVA: 0x000028CF File Offset: 0x00000ACF
		private void label13_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000048 RID: 72 RVA: 0x000028D2 File Offset: 0x00000AD2
		private void guna2HtmlToolTip1_Popup(object sender, PopupEventArgs e)
		{
		}

		// Token: 0x06000049 RID: 73 RVA: 0x000028D5 File Offset: 0x00000AD5
		private void guna2TrackBar1_MouseHover(object sender, EventArgs e)
		{
		}

		// Token: 0x0600004A RID: 74 RVA: 0x000028D8 File Offset: 0x00000AD8
		private void label3_Click_2(object sender, EventArgs e)
		{
		}

		// Token: 0x0600004B RID: 75 RVA: 0x000028DB File Offset: 0x00000ADB
		private void label3_MouseHover(object sender, EventArgs e)
		{
		}

		// Token: 0x0600004C RID: 76 RVA: 0x000028DE File Offset: 0x00000ADE
		private void label13_MouseHover(object sender, EventArgs e)
		{
		}

		// Token: 0x0600004D RID: 77 RVA: 0x000028E1 File Offset: 0x00000AE1
		private void guna2Button9_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600004E RID: 78 RVA: 0x000028E4 File Offset: 0x00000AE4
		private void label11_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600004F RID: 79 RVA: 0x000028E7 File Offset: 0x00000AE7
		private void label12_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000050 RID: 80 RVA: 0x000028EA File Offset: 0x00000AEA
		private void guna2Button10_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000051 RID: 81 RVA: 0x000028ED File Offset: 0x00000AED
		private void guna2TextBox2_TextChanged_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000052 RID: 82 RVA: 0x000028F0 File Offset: 0x00000AF0
		private void guna2Button16_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000053 RID: 83 RVA: 0x000028F3 File Offset: 0x00000AF3
		private void guna2Button12_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000054 RID: 84 RVA: 0x000028F6 File Offset: 0x00000AF6
		private void timer3_Tick_3(object sender, EventArgs e)
		{
			this.Minecraft.spinbot += 5f;
		}

		// Token: 0x06000055 RID: 85 RVA: 0x00002910 File Offset: 0x00000B10
		private void guna2ResizeBox1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000056 RID: 86 RVA: 0x00002913 File Offset: 0x00000B13
		private void guna2Button11_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000057 RID: 87 RVA: 0x00002916 File Offset: 0x00000B16
		private void guna2TextBox2_KeyDown_1(object sender, KeyEventArgs e)
		{
		}

		// Token: 0x06000058 RID: 88 RVA: 0x00002919 File Offset: 0x00000B19
		private void guna2Button20_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000059 RID: 89 RVA: 0x0000291C File Offset: 0x00000B1C
		private void guna2Button19_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600005A RID: 90 RVA: 0x00002920 File Offset: 0x00000B20
		private void timer3_Tick_4(object sender, EventArgs e)
		{
			bool @checked = this.guna2Button26.Checked;
			if (@checked)
			{
				this.Minecraft.spinBot();
				this.Minecraft.spinbot += (float)this.spinSpeed;
			}
			else
			{
				this.Minecraft.spinDisable();
			}
		}

		// Token: 0x0600005B RID: 91 RVA: 0x00002974 File Offset: 0x00000B74
		private void guna2TextBox3_TextChanged_1(object sender, EventArgs e)
		{
		}

		// Token: 0x0600005C RID: 92 RVA: 0x00002977 File Offset: 0x00000B77
		private void guna2Panel6_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x0600005D RID: 93 RVA: 0x0000297C File Offset: 0x00000B7C
		private void guna2Button18_Click(object sender, EventArgs e)
		{
			bool @checked = this.guna2Button18.Checked;
			if (@checked)
			{
				this.guna2Button16.Visible = false;
				this.guna2Button14.Visible = false;
				this.guna2Button13.Visible = false;
				this.guna2Button17.Visible = false;
				this.guna2Button15.Visible = false;
			}
			else
			{
				this.guna2Button16.Visible = true;
				this.guna2Button14.Visible = true;
				this.guna2Button13.Visible = true;
				this.guna2Button17.Visible = true;
				this.guna2Button15.Visible = true;
			}
		}

		// Token: 0x0600005E RID: 94 RVA: 0x00002A21 File Offset: 0x00000C21
		private void guna2Button24_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600005F RID: 95 RVA: 0x00002A24 File Offset: 0x00000C24
		private void guna2Button26_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000060 RID: 96 RVA: 0x00002A28 File Offset: 0x00000C28
		private void guna2TextBox4_TextChanged_1(object sender, EventArgs e)
		{
			try
			{
			}
			catch
			{
			}
		}

		// Token: 0x06000061 RID: 97 RVA: 0x00002A50 File Offset: 0x00000C50
		private void guna2Button27_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000062 RID: 98 RVA: 0x00002A53 File Offset: 0x00000C53
		private void guna2Button28_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000063 RID: 99 RVA: 0x00002A56 File Offset: 0x00000C56
		private void guna2Button27_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000064 RID: 100 RVA: 0x00002A59 File Offset: 0x00000C59
		private void guna2Panel5_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x06000065 RID: 101 RVA: 0x00002A5C File Offset: 0x00000C5C
		private void guna2Button4_MouseHover(object sender, EventArgs e)
		{
			this.guna2HtmlToolTip2.SetToolTip(this.guna2Button4, "Allows your player to walk in water");
		}

		// Token: 0x06000066 RID: 102 RVA: 0x00002A76 File Offset: 0x00000C76
		private void guna2Button7_MouseLeave(object sender, EventArgs e)
		{
		}

		// Token: 0x06000067 RID: 103 RVA: 0x00002A79 File Offset: 0x00000C79
		private void guna2Button4_MouseLeave(object sender, EventArgs e)
		{
		}

		// Token: 0x06000068 RID: 104 RVA: 0x00002A7C File Offset: 0x00000C7C
		private void guna2Button7_MouseHover_1(object sender, EventArgs e)
		{
			this.guna2HtmlToolTip3.SetToolTip(this.guna2Button7, "Changes how lighting works");
		}

		// Token: 0x06000069 RID: 105 RVA: 0x00002A96 File Offset: 0x00000C96
		private void guna2Button26_MouseLeave(object sender, EventArgs e)
		{
		}

		// Token: 0x0600006A RID: 106 RVA: 0x00002A99 File Offset: 0x00000C99
		private void guna2Button26_MouseHover(object sender, EventArgs e)
		{
			this.guna2HtmlToolTip6.SetToolTip(this.guna2Button26, "Makes your head move around");
		}

		// Token: 0x0600006B RID: 107 RVA: 0x00002AB3 File Offset: 0x00000CB3
		private void guna2Button2_MouseHover(object sender, EventArgs e)
		{
			this.guna2HtmlToolTip4.SetToolTip(this.guna2Button2, "Reduce the delay between breaking blocks");
		}

		// Token: 0x0600006C RID: 108 RVA: 0x00002ACD File Offset: 0x00000CCD
		private void guna2Button20_MouseHover(object sender, EventArgs e)
		{
			this.guna2HtmlToolTip5.SetToolTip(this.guna2Button20, "Don't slow down when you're in cobweb");
		}

		// Token: 0x0600006D RID: 109 RVA: 0x00002AE7 File Offset: 0x00000CE7
		private void guna2Button9_MouseLeave(object sender, EventArgs e)
		{
		}

		// Token: 0x0600006E RID: 110 RVA: 0x00002AEA File Offset: 0x00000CEA
		private void guna2Button9_MouseHover(object sender, EventArgs e)
		{
			this.guna2HtmlToolTip9.SetToolTip(this.guna2Button9, "Improves Sprinting");
		}

		// Token: 0x0600006F RID: 111 RVA: 0x00002B04 File Offset: 0x00000D04
		private void guna2Button10_MouseHover(object sender, EventArgs e)
		{
		}

		// Token: 0x06000070 RID: 112 RVA: 0x00002B07 File Offset: 0x00000D07
		private void guna2Button20_MouseLeave(object sender, EventArgs e)
		{
		}

		// Token: 0x06000071 RID: 113 RVA: 0x00002B0A File Offset: 0x00000D0A
		private void guna2Button28_MouseHover(object sender, EventArgs e)
		{
			this.guna2HtmlToolTip7.SetToolTip(this.guna2Button28, "NoSwing: Disables your hand from swinging(Client - Sided)");
		}

		// Token: 0x06000072 RID: 114 RVA: 0x00002B24 File Offset: 0x00000D24
		private void guna2Button28_MouseLeave(object sender, EventArgs e)
		{
		}

		// Token: 0x06000073 RID: 115 RVA: 0x00002B27 File Offset: 0x00000D27
		private void guna2Button2_MouseMove(object sender, MouseEventArgs e)
		{
		}

		// Token: 0x06000074 RID: 116 RVA: 0x00002B2A File Offset: 0x00000D2A
		private void guna2Button2_MouseLeave(object sender, EventArgs e)
		{
		}

		// Token: 0x06000075 RID: 117 RVA: 0x00002B2D File Offset: 0x00000D2D
		private void guna2Button6_MouseHover_1(object sender, EventArgs e)
		{
			this.guna2HtmlToolTip8.SetToolTip(this.guna2Button6, "Makes your player move faster | Speed: " + this.Minecraft.speed.ToString());
		}

		// Token: 0x06000076 RID: 118 RVA: 0x00002B5C File Offset: 0x00000D5C
		private void guna2Button6_MouseLeave(object sender, EventArgs e)
		{
		}

		// Token: 0x06000077 RID: 119 RVA: 0x00002B5F File Offset: 0x00000D5F
		private void guna2Button10_MouseLeave(object sender, EventArgs e)
		{
		}

		// Token: 0x06000078 RID: 120 RVA: 0x00002B64 File Offset: 0x00000D64
		private void guna2Button6_MouseDown_1(object sender, MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Right;
			if (flag)
			{
			}
		}

		// Token: 0x06000079 RID: 121 RVA: 0x00002B85 File Offset: 0x00000D85
		private void guna2Button3_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x0600007A RID: 122 RVA: 0x00002B88 File Offset: 0x00000D88
		private void label2_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x0600007B RID: 123 RVA: 0x00002B8B File Offset: 0x00000D8B
		private void guna2Panel6_Paint_1(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x0600007C RID: 124 RVA: 0x00002B8E File Offset: 0x00000D8E
		private void guna2Button3_Click_2(object sender, EventArgs e)
		{
		}

		// Token: 0x0600007D RID: 125 RVA: 0x00002B91 File Offset: 0x00000D91
		private void guna2Button26_MouseDown(object sender, MouseEventArgs e)
		{
		}

		// Token: 0x0600007E RID: 126 RVA: 0x00002B94 File Offset: 0x00000D94
		private void guna2TextBox3_TextChanged_2(object sender, EventArgs e)
		{
		}

		// Token: 0x0600007F RID: 127 RVA: 0x00002B97 File Offset: 0x00000D97
		private void guna2TextBox2_TextChanged_2(object sender, EventArgs e)
		{
		}

		// Token: 0x06000080 RID: 128 RVA: 0x00002B9C File Offset: 0x00000D9C
		private void guna2TextBox2_KeyDown_2(object sender, KeyEventArgs e)
		{
			bool flag = e.KeyCode == Keys.Return;
			if (flag)
			{
				try
				{
					bool flag2 = this.guna2TextBox2.Text == "inject";
					if (flag2)
					{
						this.Minecraft.Inject();
					}
					bool flag3 = this.guna2TextBox2.Text == "close";
					if (flag3)
					{
						Application.Exit();
					}
					bool flag4 = this.guna2TextBox2.Text == "antiaim speed " + this.guna2TextBox2.Text.Split(new char[]
					{
						' '
					})[2];
					if (flag4)
					{
						this.spinSpeed = Convert.ToInt32(this.guna2TextBox2.Text.Split(new char[]
						{
							' '
						})[2]);
					}
					bool flag5 = this.guna2TextBox2.Text == "speed value " + this.guna2TextBox2.Text.Split(new char[]
					{
						' '
					})[2];
					if (flag5)
					{
						this.Minecraft.speed = (float)Convert.ToDouble(this.guna2TextBox2.Text.Split(new char[]
						{
							' '
						})[2]);
					}
					bool flag6 = this.guna2TextBox2.Text == "rainbow gui true";
					if (flag6)
					{
						this.timer4.Enabled = true;
					}
					bool flag7 = this.guna2TextBox2.Text == "rainbow gui false";
					if (flag7)
					{
						this.timer4.Enabled = false;
					}
					bool flag8 = this.guna2TextBox2.Text == "change name " + this.guna2TextBox2.Text.Split(new char[]
					{
						' '
					})[2];
					if (flag8)
					{
						this.Minecraft.userInput = this.guna2TextBox2.Text.Split(new char[]
						{
							' '
						})[2];
						this.Minecraft.userName();
					}
					bool flag9 = this.guna2TextBox2.Text == "volume music " + this.guna2TextBox2.Text.Split(new char[]
					{
						' '
					})[2];
					if (flag9)
					{
						this.Minecraft.musicVolume = (float)Convert.ToDouble(this.guna2TextBox2.Text.Split(new char[]
						{
							' '
						})[2]);
						this.Minecraft.highMusic();
					}
				}
				catch
				{
				}
			}
		}

		// Token: 0x06000081 RID: 129 RVA: 0x00002E3C File Offset: 0x0000103C
		private void guna2Button6_KeyDown(object sender, KeyEventArgs e)
		{
		}

		// Token: 0x06000082 RID: 130 RVA: 0x00002E3F File Offset: 0x0000103F
		private void label2_Click_2(object sender, EventArgs e)
		{
		}

		// Token: 0x06000083 RID: 131 RVA: 0x00002E42 File Offset: 0x00001042
		private void label4_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000084 RID: 132 RVA: 0x00002E45 File Offset: 0x00001045
		private void label5_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000085 RID: 133 RVA: 0x00002E48 File Offset: 0x00001048
		private void label6_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000086 RID: 134 RVA: 0x00002E4B File Offset: 0x0000104B
		private void guna2Button3_Click_3(object sender, EventArgs e)
		{
		}

		// Token: 0x06000087 RID: 135 RVA: 0x00002E4E File Offset: 0x0000104E
		private void guna2Panel6_MouseDown(object sender, MouseEventArgs e)
		{
		}

		// Token: 0x06000088 RID: 136 RVA: 0x00002E51 File Offset: 0x00001051
		private void guna2Panel6_MouseMove(object sender, MouseEventArgs e)
		{
		}

		// Token: 0x06000089 RID: 137 RVA: 0x00002E54 File Offset: 0x00001054
		private void guna2Button1_MouseMove(object sender, MouseEventArgs e)
		{
		}

		// Token: 0x0600008A RID: 138 RVA: 0x00002E57 File Offset: 0x00001057
		private void guna2HtmlToolTip5_Popup(object sender, PopupEventArgs e)
		{
		}

		// Token: 0x0600008B RID: 139 RVA: 0x00002E5C File Offset: 0x0000105C
		private void timer4_Tick(object sender, EventArgs e)
		{
			this.guna2Panel2.CustomBorderColor = Color.FromArgb(this.r, this.gr, this.b);
			this.guna2Panel3.CustomBorderColor = Color.FromArgb(this.r, this.gr, this.b);
			this.guna2Panel4.CustomBorderColor = Color.FromArgb(this.r, this.gr, this.b);
			this.guna2Panel5.CustomBorderColor = Color.FromArgb(this.r, this.gr, this.b);
			bool flag = this.r > 0 && this.b == 0;
			if (flag)
			{
				this.r--;
				this.gr++;
			}
			bool flag2 = this.gr > 0 && this.r == 0;
			if (flag2)
			{
				this.gr--;
				this.b++;
			}
			bool flag3 = this.b > 0 && this.gr == 0;
			if (flag3)
			{
				this.b--;
				this.r++;
			}
		}

		// Token: 0x0600008C RID: 140 RVA: 0x00002F9B File Offset: 0x0000119B
		private void guna2Button3_Click_4(object sender, EventArgs e)
		{
		}

		// Token: 0x0600008D RID: 141 RVA: 0x00002F9E File Offset: 0x0000119E
		private void label1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600008E RID: 142 RVA: 0x00002FA4 File Offset: 0x000011A4
		private void timer1_Tick(object sender, EventArgs e)
		{
			this.label6.Text = (DateTime.Now.ToLongTimeString() ?? "");
			this.label5.Text = "Pitch: " + this.Minecraft.getPitch().ToString();
			this.label4.Text = "Yaw: " + this.Minecraft.getYaw().ToString();
			this.label2.Text = string.Concat(new string[]
			{
				"X: ",
				this.Minecraft.getPosition("X").ToString(),
				", Y: ",
				this.Minecraft.getPosition("Y").ToString(),
				", Z: ",
				this.Minecraft.getPosition("Z").ToString()
			});
		}

		// Token: 0x04000001 RID: 1
		private Minecraft Minecraft = new Minecraft();

		// Token: 0x04000002 RID: 2
		public Mem MemLib = new Mem();

		// Token: 0x04000003 RID: 3
		private int r = 255;

		// Token: 0x04000004 RID: 4
		private int gr = 0;

		// Token: 0x04000005 RID: 5
		private int b = 0;

		// Token: 0x04000006 RID: 6
		private Pen myPen = new Pen(Color.Red, 3f);

		// Token: 0x04000007 RID: 7
		private Point MovePanel;

		// Token: 0x04000008 RID: 8
		private Random random = new Random();

		// Token: 0x04000009 RID: 9
		private int spinSpeed;

		// Token: 0x0400000A RID: 10
		public static SolidBrush white = new SolidBrush(Color.FromArgb(120, 120, 126));

		// Token: 0x0400000B RID: 11
		private bool rainbowGui = false;

		// Token: 0x0400000C RID: 12
		private string Zoom = "C";

		// Token: 0x0400000D RID: 13
		private string Speed = "";

		// Token: 0x0400000E RID: 14
		private string AntiAim = "";

		// Token: 0x0400000F RID: 15
		private string ClickGui = "";

		// Token: 0x04000010 RID: 16
		private string NoWater = "";

		// Token: 0x04000011 RID: 17
		private string FastBreak = "";

		// Token: 0x04000012 RID: 18
		private string NoWeb = "";

		// Token: 0x04000013 RID: 19
		private string Sprint = "";

		// Token: 0x04000014 RID: 20
		private string NoSwing = "";

		// Token: 0x04000015 RID: 21
		private string FullBright = "";
	}
}
