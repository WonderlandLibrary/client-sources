using System;
using System.ComponentModel;
using System.Drawing;
using System.Net;
using System.Runtime.InteropServices;
using System.Windows.Forms;

namespace dgbfdfuib
{
	public partial class Form1 : Form
	{
		public Form1()
		{
			this.InitializeComponent();
		}
        
		[DllImport("user32.dll")]
		public static extern bool mouse_event(int asd, int dsa, int gsd, int he, int agfh);
        
		private void TrackBar1_Scroll(object sender, EventArgs e)
		{
			this.Label2.Text = this.TrackBar1.Value.ToString();
			if (this.TrackBar1.Value > this.TrackBar2.Value)
			{
				this.TrackBar2.Value = this.TrackBar1.Value;
				this.Label3.Text = this.TrackBar2.Value.ToString();
			}
		}
        
		private void TrackBar2_Scroll(object sender, EventArgs e)
		{
			this.Label3.Text = this.TrackBar2.Value.ToString();
			if (this.TrackBar2.Value < this.TrackBar1.Value)
			{
				this.TrackBar1.Value = this.TrackBar2.Value;
				this.Label2.Text = this.TrackBar1.Value.ToString();
			}
		}
        
		private void Timer1_Tick(object sender, EventArgs e)
		{
			int num = (int)(1000.0 / (double)this.TrackBar1.Value);
			int num2 = (int)(1000.0 / (double)this.TrackBar2.Value);
			this.Timer1.Interval = (num + num2) / 2;
			if (Control.MouseButtons == MouseButtons.Left)
			{
				Form1.mouse_event(4, 0, 0, 0, 0);
				Form1.mouse_event(2, 0, 0, 0, 0);
			}
		}
        
		private void Button1_Click(object sender, EventArgs e)
		{
			this.HhHh++;
			if (this.HhHh == 1)
			{
				this.Button1.Text = "Toggle Off";
				return;
			}
			this.HhHh = 0;
			this.Button1.Text = "Toggle On";
		}
        
		private void Button1_MouseHover(object sender, EventArgs e)
		{
			if (this.Button1.Text == "Toggle Off")
			{
				this.Timer1.Stop();
			}
		}

		private void Button1_MouseLeave(object sender, EventArgs e)
		{
			if (this.Button1.Text == "Toggle Off")
			{
				this.Timer1.Start();
			}
		}
        
		private void TrackBar1_MouseDown(object sender, MouseEventArgs e)
		{
			if (this.Button1.Text == "Toggle Off")
			{
				this.Timer1.Stop();
			}
		}
        
		private void TrackBar1_MouseUp(object sender, MouseEventArgs e)
		{
			if (this.Button1.Text == "Toggle Off")
			{
				this.Timer1.Start();
			}
		}
        
		private void Form1_KeyDown(object sender, KeyEventArgs e)
		{
			if (e.KeyCode == Keys.F)
			{
				this.HhHh++;
				if (this.HhHh == 1)
				{
					this.Button1.Text = "Toggle Off";
					return;
				}
				this.HhHh = 0;
				this.Button1.Text = "Toggle On";
			}
		}

		private void Form1_Load(object sender, EventArgs e)
		{
			
		}
        
		private void Form1_FormClosed(object sender, FormClosedEventArgs e)
		{
		}
        
		private void Button1_TextChanged(object sender, EventArgs e)
		{
			if (this.Button1.Text == "Toggle Off")
			{
				this.Timer1.Start();
				return;
			}
			this.Timer1.Stop();
		}
        
		public const int AaAa = 2;
        
		public const int BbBb = 4;
        
		public const int CcCc = 32;
        
		public const int DdDd = 64;
        
		public const int EeEe = 8;
        
		public const int FfFf = 16;
        
		public const int GgGg = 1;
    
		private int HhHh;
	}
}
