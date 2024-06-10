using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;
using Bunifu.Framework.UI;
using Guna.UI.WinForms;

// Token: 0x02000016 RID: 22
public partial class Form2 : Form
{
	// Token: 0x06000051 RID: 81 RVA: 0x00009190 File Offset: 0x00007390
	public Form2()
	{
		this.InitializeComponent();
	}

	// Token: 0x06000052 RID: 82 RVA: 0x000091B0 File Offset: 0x000073B0
	private void gunaMetroTrackBar1_Scroll(object sender, ScrollEventArgs e)
	{
		double num = (300.0 + (double)this.gunaMetroTrackBar1.Value) / 100.0;
		this.double_0 = num;
		this.gunaLabel1.Text = (num.ToString() ?? "");
	}

	// Token: 0x06000053 RID: 83 RVA: 0x00006C50 File Offset: 0x00004E50
	private void gunaLabel4_Click(object sender, EventArgs e)
	{
	}

	// Token: 0x06000054 RID: 84 RVA: 0x00009200 File Offset: 0x00007400
	private void gunaMetroTrackBar2_Scroll(object sender, ScrollEventArgs e)
	{
		double num = (300.0 + (double)this.gunaMetroTrackBar2.Value) / 100.0;
		this.double_0 = num;
		this.gunaLabel2.Text = (num.ToString() ?? "");
	}

	// Token: 0x06000055 RID: 85 RVA: 0x00006C50 File Offset: 0x00004E50
	private void gunaLabel6_Click(object sender, EventArgs e)
	{
	}

	// Token: 0x06000056 RID: 86 RVA: 0x00006C50 File Offset: 0x00004E50
	private void method_0(object sender, EventArgs e)
	{
	}

	// Token: 0x06000057 RID: 87 RVA: 0x00009250 File Offset: 0x00007450
	protected virtual void Dispose(bool disposing)
	{
		if (disposing && this.icontainer_0 != null)
		{
			this.icontainer_0.Dispose();
		}
		base.Dispose(disposing);
	}

	// Token: 0x04000048 RID: 72
	private double double_0;

	// Token: 0x04000049 RID: 73
	private IContainer icontainer_0 = null;
}
