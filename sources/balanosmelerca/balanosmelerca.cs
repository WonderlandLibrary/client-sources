using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;
using Bunifu.Framework.UI;
using Guna.UI.WinForms;

// Token: 0x0200000D RID: 13
public partial class balanosmelerca : Form
{
	// Token: 0x06000036 RID: 54 RVA: 0x00007DC0 File Offset: 0x00005FC0
	public balanosmelerca()
	{
		this.InitializeComponent();
		base.MouseDown += this.balanosmelerca_MouseDown;
		base.MouseMove += this.balanosmelerca_MouseMove;
	}

	// Token: 0x06000037 RID: 55 RVA: 0x00006C50 File Offset: 0x00004E50
	private void balanosmelerca_Load(object sender, EventArgs e)
	{
	}

	// Token: 0x06000038 RID: 56 RVA: 0x00007E14 File Offset: 0x00006014
	private void balanosmelerca_MouseDown(object sender, MouseEventArgs e)
	{
		if (e.Button == MouseButtons.Left)
		{
			this.int_0 = base.Left - Control.MousePosition.X;
			this.int_1 = base.Top - Control.MousePosition.Y;
		}
	}

	// Token: 0x06000039 RID: 57 RVA: 0x00007E68 File Offset: 0x00006068
	private void balanosmelerca_MouseMove(object sender, MouseEventArgs e)
	{
		if (e.Button == MouseButtons.Left)
		{
			base.Left = this.int_0 + Control.MousePosition.X;
			base.Top = this.int_1 + Control.MousePosition.Y;
		}
	}

	// Token: 0x0600003A RID: 58 RVA: 0x00006C50 File Offset: 0x00004E50
	private void method_0(object sender, EventArgs e)
	{
	}

	// Token: 0x0600003B RID: 59 RVA: 0x00007EBC File Offset: 0x000060BC
	private void button4_Click(object sender, EventArgs e)
	{
		this.gunaElipsePanel5.Controls.Clear();
		Form1 form = new Form1();
		form.TopLevel = false;
		this.gunaElipsePanel5.Controls.Add(form);
		form.FormBorderStyle = FormBorderStyle.None;
		form.Dock = DockStyle.Fill;
		form.Show();
	}

	// Token: 0x0600003C RID: 60 RVA: 0x00007F0C File Offset: 0x0000610C
	private void button1_Click(object sender, EventArgs e)
	{
		this.gunaElipsePanel5.Controls.Clear();
		Form2 form = new Form2();
		form.TopLevel = false;
		this.gunaElipsePanel5.Controls.Add(form);
		form.FormBorderStyle = FormBorderStyle.None;
		form.Dock = DockStyle.Fill;
		form.Show();
	}

	// Token: 0x0600003D RID: 61 RVA: 0x00007F5C File Offset: 0x0000615C
	private void button2_Click(object sender, EventArgs e)
	{
		this.gunaElipsePanel5.Controls.Clear();
		Form3 form = new Form3();
		form.TopLevel = false;
		this.gunaElipsePanel5.Controls.Add(form);
		form.FormBorderStyle = FormBorderStyle.None;
		form.Dock = DockStyle.Fill;
		form.Show();
	}

	// Token: 0x0600003E RID: 62 RVA: 0x00007FAC File Offset: 0x000061AC
	private void button3_Click(object sender, EventArgs e)
	{
		this.gunaElipsePanel5.Controls.Clear();
		Form4 form = new Form4();
		form.TopLevel = false;
		this.gunaElipsePanel5.Controls.Add(form);
		form.FormBorderStyle = FormBorderStyle.None;
		form.Dock = DockStyle.Fill;
		form.Show();
	}

	// Token: 0x0600003F RID: 63 RVA: 0x00006C50 File Offset: 0x00004E50
	private void gunaElipsePanel5_Paint(object sender, PaintEventArgs e)
	{
	}

	// Token: 0x06000040 RID: 64 RVA: 0x00007FFC File Offset: 0x000061FC
	protected virtual void Dispose(bool disposing)
	{
		if (disposing && this.icontainer_0 != null)
		{
			this.icontainer_0.Dispose();
		}
		base.Dispose(disposing);
	}

	// Token: 0x0400002E RID: 46
	private int int_0 = 0;

	// Token: 0x0400002F RID: 47
	private int int_1 = 0;
}
