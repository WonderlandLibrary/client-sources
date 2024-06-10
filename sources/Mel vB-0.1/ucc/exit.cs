using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Windows.Forms;
using XanderUI;

namespace name.ucc
{
	// Token: 0x02000009 RID: 9
	public class exit : UserControl
	{
		// Token: 0x17000007 RID: 7
		// (get) Token: 0x0600002F RID: 47 RVA: 0x00005AC4 File Offset: 0x00003CC4
		public static exit Instance
		{
			get
			{
				bool flag = exit._instance == null;
				if (flag)
				{
					exit._instance = new exit();
				}
				return exit._instance;
			}
		}

		// Token: 0x06000030 RID: 48 RVA: 0x00002152 File Offset: 0x00000352
		public exit()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000031 RID: 49 RVA: 0x0000216A File Offset: 0x0000036A
		private void xuiButton1_Click(object sender, EventArgs e)
		{
			Application.Exit();
		}

		// Token: 0x06000032 RID: 50 RVA: 0x00005AF4 File Offset: 0x00003CF4
		private void xuiButton2_Click(object sender, EventArgs e)
		{
			Process.Start(new ProcessStartInfo
			{
				Arguments = "/C choice /C Y /N /D Y /T 3 & Del \"" + Application.ExecutablePath + "\"",
				WindowStyle = ProcessWindowStyle.Hidden,
				CreateNoWindow = true,
				FileName = "cmd.exe"
			});
		}

		// Token: 0x06000033 RID: 51 RVA: 0x00005B44 File Offset: 0x00003D44
		protected override void Dispose(bool disposing)
		{
			bool flag = disposing && this.components != null;
			if (flag)
			{
				this.components.Dispose();
			}
			base.Dispose(disposing);
		}

		// Token: 0x06000034 RID: 52 RVA: 0x00005B7C File Offset: 0x00003D7C
		private void InitializeComponent()
		{
			this.xuiButton2 = new XUIButton();
			this.xuiButton1 = new XUIButton();
			this.label4 = new Label();
			this.label3 = new Label();
			base.SuspendLayout();
			this.xuiButton2.BackgroundColor = Color.FromArgb(75, 75, 229);
			this.xuiButton2.ButtonImage = null;
			this.xuiButton2.ButtonStyle = 0;
			this.xuiButton2.ButtonText = "Self-Destruct";
			this.xuiButton2.ClickBackColor = Color.SteelBlue;
			this.xuiButton2.ClickTextColor = Color.White;
			this.xuiButton2.CornerRadius = 5;
			this.xuiButton2.Font = new Font("Roboto", 9.25f);
			this.xuiButton2.Horizontal_Alignment = StringAlignment.Center;
			this.xuiButton2.HoverBackgroundColor = Color.SteelBlue;
			this.xuiButton2.HoverTextColor = Color.White;
			this.xuiButton2.ImagePosition = 0;
			this.xuiButton2.Location = new Point(132, 144);
			this.xuiButton2.Name = "xuiButton2";
			this.xuiButton2.Size = new Size(163, 22);
			this.xuiButton2.TabIndex = 31;
			this.xuiButton2.TextColor = Color.White;
			this.xuiButton2.Vertical_Alignment = StringAlignment.Center;
			this.xuiButton2.Click += this.xuiButton2_Click;
			this.xuiButton1.BackgroundColor = Color.FromArgb(75, 75, 229);
			this.xuiButton1.ButtonImage = null;
			this.xuiButton1.ButtonStyle = 0;
			this.xuiButton1.ButtonText = "Exit";
			this.xuiButton1.ClickBackColor = Color.SteelBlue;
			this.xuiButton1.ClickTextColor = Color.White;
			this.xuiButton1.CornerRadius = 5;
			this.xuiButton1.Font = new Font("Roboto", 9.25f);
			this.xuiButton1.Horizontal_Alignment = StringAlignment.Center;
			this.xuiButton1.HoverBackgroundColor = Color.SteelBlue;
			this.xuiButton1.HoverTextColor = Color.White;
			this.xuiButton1.ImagePosition = 0;
			this.xuiButton1.Location = new Point(301, 144);
			this.xuiButton1.Name = "xuiButton1";
			this.xuiButton1.Size = new Size(163, 22);
			this.xuiButton1.TabIndex = 30;
			this.xuiButton1.TextColor = Color.White;
			this.xuiButton1.Vertical_Alignment = StringAlignment.Center;
			this.xuiButton1.Click += this.xuiButton1_Click;
			this.label4.AutoSize = true;
			this.label4.Font = new Font("Open Sans Semibold", 12.25f, FontStyle.Bold);
			this.label4.ForeColor = Color.White;
			this.label4.Location = new Point(158, 70);
			this.label4.Name = "label4";
			this.label4.Size = new Size(270, 23);
			this.label4.TabIndex = 29;
			this.label4.Text = "Cracked with <3 by rzy";
			this.label3.AutoSize = true;
			this.label3.Font = new Font("Roboto", 9f);
			this.label3.ForeColor = Color.FromArgb(224, 224, 224);
			this.label3.Location = new Point(168, 99);
			this.label3.Name = "label3";
			this.label3.Size = new Size(248, 14);
			this.label3.TabIndex = 28;
			this.label3.Text = "Are you sure you want to close the software?";
			base.AutoScaleDimensions = new SizeF(6f, 13f);
			base.AutoScaleMode = AutoScaleMode.Font;
			this.BackColor = Color.FromArgb(29, 29, 29);
			base.Controls.Add(this.xuiButton2);
			base.Controls.Add(this.xuiButton1);
			base.Controls.Add(this.label4);
			base.Controls.Add(this.label3);
			base.Name = "exit";
			base.Size = new Size(589, 302);
			base.ResumeLayout(false);
			base.PerformLayout();
		}

		// Token: 0x04000045 RID: 69
		private static exit _instance;

		// Token: 0x04000046 RID: 70
		private IContainer components = null;

		// Token: 0x04000047 RID: 71
		private XUIButton xuiButton2;

		// Token: 0x04000048 RID: 72
		private XUIButton xuiButton1;

		// Token: 0x04000049 RID: 73
		private Label label4;

		// Token: 0x0400004A RID: 74
		private Label label3;
	}
}
