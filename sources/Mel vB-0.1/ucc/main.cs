using System;
using System.ComponentModel;
using System.Drawing;
using System.Globalization;
using System.Linq;
using System.Runtime.InteropServices;
using System.Threading;
using System.Windows.Forms;
using XanderUI;

namespace name.ucc
{
	// Token: 0x02000008 RID: 8
	public class main : UserControl
	{
		// Token: 0x06000022 RID: 34
		[DllImport("user32.dll", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern bool mouse_event(int dwFlags, int dX, int dY, int cButtons, int dwExtraInfo);

		// Token: 0x17000006 RID: 6
		// (get) Token: 0x06000023 RID: 35 RVA: 0x00004858 File Offset: 0x00002A58
		public static main Instance
		{
			get
			{
				bool flag = main._instance == null;
				if (flag)
				{
					main._instance = new main();
				}
				return main._instance;
			}
		}

		// Token: 0x06000024 RID: 36 RVA: 0x000020F4 File Offset: 0x000002F4
		public main()
		{
			this.InitializeComponent();
			this.up.Start();
		}

		// Token: 0x06000025 RID: 37 RVA: 0x00004888 File Offset: 0x00002A88
		private void up_Tick(object sender, EventArgs e)
		{
			bool flag = this.textBox1.Text != "";
			if (flag)
			{
				this.xuiButton3.ButtonText = ">" + this.textBox1.Text + "<";
			}
			else
			{
				this.xuiButton3.ButtonText = ">...<";
			}
			this.label4.Text = ((this.xuiSlider1.Percentage / 5).ToString() ?? "");
			this.label5.Text = "3." + (this.xuiSlider2.Percentage / 11).ToString();
		}

		// Token: 0x06000026 RID: 38 RVA: 0x00002131 File Offset: 0x00000331
		private void xuiButton3_Click(object sender, EventArgs e)
		{
			this.textBox1.Focus();
		}

		// Token: 0x06000027 RID: 39 RVA: 0x00002140 File Offset: 0x00000340
		private void textBox1_TextChanged(object sender, EventArgs e)
		{
			this.xuiButton3.Focus();
		}

		// Token: 0x06000028 RID: 40 RVA: 0x00004944 File Offset: 0x00002B44
		public void performClick(int delay)
		{
			int num = Convert.ToInt32(this.label4.Text);
			int minValue = delay / num;
			int maxValue = delay / num;
			this.crdn.Interval = this.rnd.Next(minValue, maxValue);
			Thread.Sleep(this.rnd.Next(2, 4));
			main.mouse_event(4, 0, 0, 0, 0);
			Thread.Sleep(this.rnd.Next(3, 7));
			main.mouse_event(2, 0, 0, 0, 0);
			this.clicks++;
		}

		// Token: 0x06000029 RID: 41 RVA: 0x000049D0 File Offset: 0x00002BD0
		private void crdn_Tick(object sender, EventArgs e)
		{
			bool flag = this.clicks == 35 * this.rnd.Next(3, 6);
			if (flag)
			{
				this.clicks = 0;
			}
			bool flag2 = Control.MouseButtons == MouseButtons.Left;
			if (flag2)
			{
				int num = 35;
				bool flag3 = this.clicks > num && (double)this.clicks < (double)num * 1.1;
				if (flag3)
				{
					this.performClick(this.rnd.Next(1500, 1750));
				}
				else
				{
					bool flag4 = (double)this.clicks > (double)num * 1.2 && (double)this.clicks < (double)num * 1.25;
					if (flag4)
					{
						this.performClick(this.rnd.Next(1250, 1350));
					}
					else
					{
						bool flag5 = (double)this.clicks > (double)num * 1.4 && (double)this.clicks < (double)num * 1.5;
						if (flag5)
						{
							this.performClick(this.rnd.Next(1350, 1500));
						}
						else
						{
							bool flag6 = (double)this.clicks > (double)num * 1.7 && (double)this.clicks < (double)num * 1.8;
							if (flag6)
							{
								this.performClick(this.rnd.Next(1400, 1550));
							}
							else
							{
								int num2 = this.rnd.Next(1, 100);
								bool flag7 = num2 < 25;
								if (flag7)
								{
									this.performClick(700);
								}
								else
								{
									bool flag8 = num2 < 50;
									if (flag8)
									{
										this.performClick(750);
									}
									else
									{
										bool flag9 = num2 < 75;
										if (flag9)
										{
											this.performClick(800);
										}
										else
										{
											bool flag10 = num2 <= 100;
											if (flag10)
											{
												this.performClick(950);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		// Token: 0x0600002A RID: 42 RVA: 0x00004BDC File Offset: 0x00002DDC
		private void xuiButton2_Click(object sender, EventArgs e)
		{
			bool flag = this.xuiButton2.ButtonText == "Toggle ON";
			if (flag)
			{
				this.crdn.Start();
				this.xuiButton2.ButtonText = "Toggle OFF";
			}
			else
			{
				this.crdn.Stop();
				this.xuiButton2.ButtonText = "Toggle ON";
			}
		}

		// Token: 0x0600002B RID: 43 RVA: 0x00004C44 File Offset: 0x00002E44
		private void xuiButton4_Click(object sender, EventArgs e)
		{
			this.dot = new DotNetScanMemory_SmoLL();
			string text = this.label5.Text;
			double value = Convert.ToDouble(text, CultureInfo.InvariantCulture);
			byte[] bytes = BitConverter.GetBytes(value);
			this.text1 = BitConverter.ToString(bytes).Replace("-", " ");
			IntPtr[] array = this.dot.ScanArray(this.dot.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
			int num = array.Count<IntPtr>() - 1;
			for (int i = 0; i <= num; i++)
			{
				this.dot.WriteArray(array[i], this.text1);
			}
			MessageBox.Show("Reach Injected");
		}

		// Token: 0x0600002C RID: 44 RVA: 0x0000214F File Offset: 0x0000034F
		private void main_Load(object sender, EventArgs e)
		{
		}

		// Token: 0x0600002D RID: 45 RVA: 0x00004D00 File Offset: 0x00002F00
		protected override void Dispose(bool disposing)
		{
			bool flag = disposing && this.components != null;
			if (flag)
			{
				this.components.Dispose();
			}
			base.Dispose(disposing);
		}

		// Token: 0x0600002E RID: 46 RVA: 0x00004D38 File Offset: 0x00002F38
		private void InitializeComponent()
		{
			this.components = new Container();
			this.xuiButton2 = new XUIButton();
			this.label3 = new Label();
			this.xuiSlider1 = new XUISlider();
			this.xuiButton3 = new XUIButton();
			this.textBox1 = new TextBox();
			this.up = new System.Windows.Forms.Timer(this.components);
			this.label1 = new Label();
			this.label4 = new Label();
			this.label5 = new Label();
			this.label6 = new Label();
			this.xuiSlider2 = new XUISlider();
			this.xuiButton4 = new XUIButton();
			this.label7 = new Label();
			this.crdn = new System.Windows.Forms.Timer(this.components);
			this.messagetext = new Label();
			base.SuspendLayout();
			this.xuiButton2.BackgroundColor = Color.FromArgb(55, 55, 55);
			this.xuiButton2.ButtonImage = null;
			this.xuiButton2.ButtonStyle = 0;
			this.xuiButton2.ButtonText = "Toggle ON";
			this.xuiButton2.ClickBackColor = Color.SteelBlue;
			this.xuiButton2.ClickTextColor = Color.White;
			this.xuiButton2.CornerRadius = 5;
			this.xuiButton2.Font = new Font("Roboto", 8.25f);
			this.xuiButton2.Horizontal_Alignment = StringAlignment.Center;
			this.xuiButton2.HoverBackgroundColor = Color.SteelBlue;
			this.xuiButton2.HoverTextColor = Color.White;
			this.xuiButton2.ImagePosition = 0;
			this.xuiButton2.Location = new Point(15, 54);
			this.xuiButton2.Name = "xuiButton2";
			this.xuiButton2.Size = new Size(94, 19);
			this.xuiButton2.TabIndex = 31;
			this.xuiButton2.TextColor = Color.White;
			this.xuiButton2.Vertical_Alignment = StringAlignment.Center;
			this.xuiButton2.Click += this.xuiButton2_Click;
			this.label3.AutoSize = true;
			this.label3.FlatStyle = FlatStyle.Popup;
			this.label3.Font = new Font("Roboto", 10f);
			this.label3.ForeColor = Color.FromArgb(224, 224, 224);
			this.label3.Location = new Point(12, 5);
			this.label3.Name = "label3";
			this.label3.Size = new Size(88, 17);
			this.label3.TabIndex = 28;
			this.label3.Text = "Random Cps";
			this.xuiSlider1.BarThickness = 4;
			this.xuiSlider1.BigStepIncrement = 10;
			this.xuiSlider1.FilledColor = Color.FromArgb(1, 119, 215);
			this.xuiSlider1.KnobColor = Color.Gray;
			this.xuiSlider1.KnobImage = null;
			this.xuiSlider1.Location = new Point(6, 28);
			this.xuiSlider1.Name = "xuiSlider1";
			this.xuiSlider1.Percentage = 50;
			this.xuiSlider1.QuickHopping = false;
			this.xuiSlider1.Size = new Size(254, 20);
			this.xuiSlider1.SliderStyle = 4;
			this.xuiSlider1.TabIndex = 32;
			this.xuiSlider1.Text = "xuiSlider1";
			this.xuiSlider1.UnfilledColor = Color.FromArgb(26, 169, 219);
			this.xuiButton3.BackgroundColor = Color.FromArgb(55, 55, 55);
			this.xuiButton3.ButtonImage = null;
			this.xuiButton3.ButtonStyle = 0;
			this.xuiButton3.ButtonText = ">...<";
			this.xuiButton3.ClickBackColor = Color.SteelBlue;
			this.xuiButton3.ClickTextColor = Color.White;
			this.xuiButton3.CornerRadius = 5;
			this.xuiButton3.Font = new Font("Roboto", 8.25f);
			this.xuiButton3.Horizontal_Alignment = StringAlignment.Center;
			this.xuiButton3.HoverBackgroundColor = Color.SteelBlue;
			this.xuiButton3.HoverTextColor = Color.White;
			this.xuiButton3.ImagePosition = 0;
			this.xuiButton3.Location = new Point(115, 54);
			this.xuiButton3.Name = "xuiButton3";
			this.xuiButton3.Size = new Size(54, 19);
			this.xuiButton3.TabIndex = 33;
			this.xuiButton3.TextColor = Color.White;
			this.xuiButton3.Vertical_Alignment = StringAlignment.Center;
			this.xuiButton3.Visible = false;
			this.xuiButton3.Click += this.xuiButton3_Click;
			this.textBox1.BackColor = Color.FromArgb(25, 25, 25);
			this.textBox1.ForeColor = Color.White;
			this.textBox1.Location = new Point(0, 311);
			this.textBox1.MaxLength = 1;
			this.textBox1.Name = "textBox1";
			this.textBox1.Size = new Size(100, 20);
			this.textBox1.TabIndex = 35;
			this.textBox1.TextChanged += this.textBox1_TextChanged;
			this.up.Tick += this.up_Tick;
			this.label1.AutoSize = true;
			this.label1.FlatStyle = FlatStyle.Popup;
			this.label1.Font = new Font("Roboto", 10f);
			this.label1.ForeColor = Color.FromArgb(224, 224, 224);
			this.label1.Location = new Point(256, 30);
			this.label1.Name = "label1";
			this.label1.Size = new Size(41, 17);
			this.label1.TabIndex = 36;
			this.label1.Text = "Cps : ";
			this.label4.AutoSize = true;
			this.label4.FlatStyle = FlatStyle.Popup;
			this.label4.Font = new Font("Roboto", 10f);
			this.label4.ForeColor = Color.FromArgb(224, 224, 224);
			this.label4.Location = new Point(290, 30);
			this.label4.Name = "label4";
			this.label4.Size = new Size(24, 17);
			this.label4.TabIndex = 37;
			this.label4.Text = "10";
			this.label5.AutoSize = true;
			this.label5.FlatStyle = FlatStyle.Popup;
			this.label5.Font = new Font("Roboto", 10f);
			this.label5.ForeColor = Color.FromArgb(224, 224, 224);
			this.label5.Location = new Point(316, 158);
			this.label5.Name = "label5";
			this.label5.Size = new Size(28, 17);
			this.label5.TabIndex = 43;
			this.label5.Text = "3.1";
			this.label6.AutoSize = true;
			this.label6.FlatStyle = FlatStyle.Popup;
			this.label6.Font = new Font("Roboto", 10f);
			this.label6.ForeColor = Color.FromArgb(224, 224, 224);
			this.label6.Location = new Point(256, 158);
			this.label6.Name = "label6";
			this.label6.Size = new Size(67, 17);
			this.label6.TabIndex = 42;
			this.label6.Text = "Amount : ";
			this.xuiSlider2.BarThickness = 4;
			this.xuiSlider2.BigStepIncrement = 10;
			this.xuiSlider2.FilledColor = Color.FromArgb(1, 119, 215);
			this.xuiSlider2.KnobColor = Color.Gray;
			this.xuiSlider2.KnobImage = null;
			this.xuiSlider2.Location = new Point(6, 156);
			this.xuiSlider2.Name = "xuiSlider2";
			this.xuiSlider2.Percentage = 20;
			this.xuiSlider2.QuickHopping = false;
			this.xuiSlider2.Size = new Size(254, 20);
			this.xuiSlider2.SliderStyle = 4;
			this.xuiSlider2.TabIndex = 40;
			this.xuiSlider2.Text = "xuiSlider2";
			this.xuiSlider2.UnfilledColor = Color.FromArgb(26, 169, 219);
			this.xuiButton4.BackgroundColor = Color.FromArgb(55, 55, 55);
			this.xuiButton4.ButtonImage = null;
			this.xuiButton4.ButtonStyle = 0;
			this.xuiButton4.ButtonText = "Inject";
			this.xuiButton4.ClickBackColor = Color.SteelBlue;
			this.xuiButton4.ClickTextColor = Color.White;
			this.xuiButton4.CornerRadius = 5;
			this.xuiButton4.Font = new Font("Roboto", 8.25f);
			this.xuiButton4.Horizontal_Alignment = StringAlignment.Center;
			this.xuiButton4.HoverBackgroundColor = Color.SteelBlue;
			this.xuiButton4.HoverTextColor = Color.White;
			this.xuiButton4.ImagePosition = 0;
			this.xuiButton4.Location = new Point(15, 182);
			this.xuiButton4.Name = "xuiButton4";
			this.xuiButton4.Size = new Size(94, 19);
			this.xuiButton4.TabIndex = 39;
			this.xuiButton4.TextColor = Color.White;
			this.xuiButton4.Vertical_Alignment = StringAlignment.Center;
			this.xuiButton4.Click += this.xuiButton4_Click;
			this.label7.AutoSize = true;
			this.label7.FlatStyle = FlatStyle.Popup;
			this.label7.Font = new Font("Roboto", 10f);
			this.label7.ForeColor = Color.FromArgb(224, 224, 224);
			this.label7.Location = new Point(12, 133);
			this.label7.Name = "label7";
			this.label7.Size = new Size(47, 17);
			this.label7.TabIndex = 38;
			this.label7.Text = "Reach";
			this.crdn.Tick += this.crdn_Tick;
			this.messagetext.AutoSize = true;
			this.messagetext.Font = new Font("Open Sans", 6.75f);
			this.messagetext.ForeColor = Color.Brown;
			this.messagetext.Location = new Point(65, 135);
			this.messagetext.Name = "messagetext";
			this.messagetext.Size = new Size(120, 13);
			this.messagetext.TabIndex = 44;
			this.messagetext.Text = "*Can only change one time";
			base.AutoScaleDimensions = new SizeF(6f, 13f);
			base.AutoScaleMode = AutoScaleMode.Font;
			this.BackColor = Color.FromArgb(29, 29, 29);
			base.Controls.Add(this.messagetext);
			base.Controls.Add(this.label5);
			base.Controls.Add(this.label6);
			base.Controls.Add(this.xuiSlider2);
			base.Controls.Add(this.xuiButton4);
			base.Controls.Add(this.label7);
			base.Controls.Add(this.label4);
			base.Controls.Add(this.label1);
			base.Controls.Add(this.textBox1);
			base.Controls.Add(this.xuiButton3);
			base.Controls.Add(this.xuiSlider1);
			base.Controls.Add(this.xuiButton2);
			base.Controls.Add(this.label3);
			base.Name = "main";
			base.Size = new Size(589, 353);
			base.Load += this.main_Load;
			base.ResumeLayout(false);
			base.PerformLayout();
		}

		// Token: 0x0400002C RID: 44
		private const int MOUSEEVENTF_LEFTDOWN = 2;

		// Token: 0x0400002D RID: 45
		private const int MOUSEEVENTF_LEFTUP = 4;

		// Token: 0x0400002E RID: 46
		private static main _instance;

		// Token: 0x0400002F RID: 47
		private DotNetScanMemory_SmoLL dot;

		// Token: 0x04000030 RID: 48
		private string text1;

		// Token: 0x04000031 RID: 49
		private m m2;

		// Token: 0x04000032 RID: 50
		public bool autoclickuse = false;

		// Token: 0x04000033 RID: 51
		private int clicks = 0;

		// Token: 0x04000034 RID: 52
		private Random rnd = new Random();

		// Token: 0x04000035 RID: 53
		private IContainer components = null;

		// Token: 0x04000036 RID: 54
		private XUIButton xuiButton2;

		// Token: 0x04000037 RID: 55
		private Label label3;

		// Token: 0x04000038 RID: 56
		private XUISlider xuiSlider1;

		// Token: 0x04000039 RID: 57
		private XUIButton xuiButton3;

		// Token: 0x0400003A RID: 58
		private TextBox textBox1;

		// Token: 0x0400003B RID: 59
		private System.Windows.Forms.Timer up;

		// Token: 0x0400003C RID: 60
		private Label label1;

		// Token: 0x0400003D RID: 61
		private Label label4;

		// Token: 0x0400003E RID: 62
		private Label label5;

		// Token: 0x0400003F RID: 63
		private Label label6;

		// Token: 0x04000040 RID: 64
		private XUISlider xuiSlider2;

		// Token: 0x04000041 RID: 65
		private XUIButton xuiButton4;

		// Token: 0x04000042 RID: 66
		private Label label7;

		// Token: 0x04000043 RID: 67
		private System.Windows.Forms.Timer crdn;

		// Token: 0x04000044 RID: 68
		private Label messagetext;
	}
}
