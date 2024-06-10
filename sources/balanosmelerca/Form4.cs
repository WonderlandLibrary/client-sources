using System;
using System.ComponentModel;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Guna.UI.WinForms;

// Token: 0x02000018 RID: 24
public partial class Form4 : Form
{
	// Token: 0x06000066 RID: 102
	[DllImport("user32", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
	public static extern void mouse_event(int int_5, int int_6, int int_7, int int_8, int int_9);

	// Token: 0x06000067 RID: 103 RVA: 0x0000A5F0 File Offset: 0x000087F0
	public Form4()
	{
		this.InitializeComponent();
	}

	// Token: 0x06000068 RID: 104 RVA: 0x00006C50 File Offset: 0x00004E50
	private void Form4_Load(object sender, EventArgs e)
	{
	}

	// Token: 0x06000069 RID: 105 RVA: 0x0000A610 File Offset: 0x00008810
	private void method_0(object sender, ScrollEventArgs e)
	{
		this.timer_0.Start();
	}

	// Token: 0x0600006A RID: 106 RVA: 0x0000A628 File Offset: 0x00008828
	private void method_1(object sender, EventArgs e)
	{
		this.timer_0.Stop();
	}

	// Token: 0x0600006B RID: 107 RVA: 0x0000A640 File Offset: 0x00008840
	private void timer_0_Tick(object sender, EventArgs e)
	{
		if (Control.MouseButtons == MouseButtons.Left)
		{
			Form4.mouse_event(4, 12, 10, 5, 0);
			Form4.mouse_event(2, 0, 0, 0, 0);
		}
	}

	// Token: 0x0600006C RID: 108 RVA: 0x00006C50 File Offset: 0x00004E50
	private void method_2(object sender, EventArgs e)
	{
	}

	// Token: 0x0600006D RID: 109 RVA: 0x0000A610 File Offset: 0x00008810
	private void method_3(object sender, EventArgs e)
	{
		this.timer_0.Start();
	}

	// Token: 0x0600006E RID: 110 RVA: 0x0000A628 File Offset: 0x00008828
	private void method_4(object sender, EventArgs e)
	{
		this.timer_0.Stop();
	}

	// Token: 0x0600006F RID: 111 RVA: 0x00006C50 File Offset: 0x00004E50
	private void method_5(object sender, ScrollEventArgs e)
	{
	}

	// Token: 0x06000070 RID: 112 RVA: 0x0000A674 File Offset: 0x00008874
	private void gunaMetroTrackBar1_Scroll(object sender, ScrollEventArgs e)
	{
		this.gunaMetroTrackBar1.Minimum = 0;
		this.gunaMetroTrackBar1.Maximum = 20;
		this.gunaLabel6.Text = this.gunaMetroTrackBar1.Value.ToString();
	}

	// Token: 0x06000071 RID: 113 RVA: 0x00006C50 File Offset: 0x00004E50
	private void gunaPanel1_Paint(object sender, PaintEventArgs e)
	{
	}

	// Token: 0x06000072 RID: 114 RVA: 0x00006C50 File Offset: 0x00004E50
	private void gunaLabel8_Click(object sender, EventArgs e)
	{
	}

	// Token: 0x06000073 RID: 115 RVA: 0x0000A6B8 File Offset: 0x000088B8
	private void gunaMetroTrackBar2_Scroll(object sender, ScrollEventArgs e)
	{
		this.gunaMetroTrackBar2.Minimum = 0;
		this.gunaMetroTrackBar2.Maximum = 20;
		this.gunaLabel8.Text = this.gunaMetroTrackBar2.Value.ToString();
	}

	// Token: 0x06000074 RID: 116 RVA: 0x0000A610 File Offset: 0x00008810
	private void button2_Click(object sender, EventArgs e)
	{
		this.timer_0.Start();
	}

	// Token: 0x06000075 RID: 117 RVA: 0x0000A628 File Offset: 0x00008828
	private void button1_Click(object sender, EventArgs e)
	{
		this.timer_0.Stop();
	}

	// Token: 0x06000076 RID: 118 RVA: 0x00006C50 File Offset: 0x00004E50
	private void gunaLabel7_Click(object sender, EventArgs e)
	{
	}

	// Token: 0x06000077 RID: 119 RVA: 0x00006C50 File Offset: 0x00004E50
	private void gunaLabel5_Click(object sender, EventArgs e)
	{
	}

	// Token: 0x06000078 RID: 120 RVA: 0x0000A6FC File Offset: 0x000088FC
	protected virtual void Dispose(bool disposing)
	{
		if (disposing && this.icontainer_0 != null)
		{
			this.icontainer_0.Dispose();
		}
		base.Dispose(disposing);
	}

	// Token: 0x04000064 RID: 100
	private const int int_0 = 1;

	// Token: 0x04000065 RID: 101
	private const int int_1 = 2;

	// Token: 0x04000066 RID: 102
	private const int int_2 = 4;

	// Token: 0x04000067 RID: 103
	private const int int_3 = 8;

	// Token: 0x04000068 RID: 104
	private const int int_4 = 22;
}
