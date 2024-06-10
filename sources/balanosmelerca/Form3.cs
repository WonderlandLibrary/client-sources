using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;
using Bunifu.Framework.UI;
using Guna.UI.WinForms;

// Token: 0x02000017 RID: 23
public partial class Form3 : Form
{
	// Token: 0x06000059 RID: 89 RVA: 0x00009B38 File Offset: 0x00007D38
	public Form3()
	{
		this.InitializeComponent();
	}

	// Token: 0x0600005A RID: 90 RVA: 0x00006C50 File Offset: 0x00004E50
	private void Form3_Load(object sender, EventArgs e)
	{
	}

	// Token: 0x0600005B RID: 91 RVA: 0x00006C50 File Offset: 0x00004E50
	private void method_0(object sender, EventArgs e)
	{
	}

	// Token: 0x0600005C RID: 92 RVA: 0x00006C50 File Offset: 0x00004E50
	private void method_1(object sender, EventArgs e)
	{
	}

	// Token: 0x0600005D RID: 93 RVA: 0x00006C50 File Offset: 0x00004E50
	private void gunaPanel1_Paint(object sender, PaintEventArgs e)
	{
	}

	// Token: 0x0600005E RID: 94 RVA: 0x00006C50 File Offset: 0x00004E50
	private void gunaNumeric1_Click(object sender, EventArgs e)
	{
	}

	// Token: 0x0600005F RID: 95 RVA: 0x00006C50 File Offset: 0x00004E50
	private void gunaLabel4_Click(object sender, EventArgs e)
	{
	}

	// Token: 0x06000060 RID: 96 RVA: 0x00006C50 File Offset: 0x00004E50
	private void gunaPanel3_Paint(object sender, PaintEventArgs e)
	{
	}

	// Token: 0x06000061 RID: 97 RVA: 0x00006C50 File Offset: 0x00004E50
	private void gunaLabel3_Click(object sender, EventArgs e)
	{
	}

	// Token: 0x06000062 RID: 98 RVA: 0x00009B58 File Offset: 0x00007D58
	private void gunaTrackBar1_Scroll(object sender, ScrollEventArgs e)
	{
		this.object_0 = Math.Round(8000.0 * Math.Round(100.0 / (double)this.gunaTrackBar1.Value));
		this.gunaLabel4.Text = (this.gunaTrackBar1.Value.ToString() ?? "");
	}

	// Token: 0x06000063 RID: 99 RVA: 0x00009BC4 File Offset: 0x00007DC4
	private void gunaTrackBar2_Scroll(object sender, ScrollEventArgs e)
	{
		double num = (300.0 + (double)this.gunaTrackBar2.Value) / 100.0;
		this.double_0 = num;
		this.gunaLabel5.Text = (num.ToString() ?? "");
	}

	// Token: 0x06000064 RID: 100 RVA: 0x00009C14 File Offset: 0x00007E14
	protected virtual void Dispose(bool disposing)
	{
		if (disposing && this.icontainer_0 != null)
		{
			this.icontainer_0.Dispose();
		}
		base.Dispose(disposing);
	}

	// Token: 0x04000055 RID: 85
	private object object_0;

	// Token: 0x04000056 RID: 86
	private double double_0;

	// Token: 0x04000057 RID: 87
	private IContainer icontainer_0 = null;
}
