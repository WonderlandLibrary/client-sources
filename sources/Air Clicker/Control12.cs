using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x02000023 RID: 35
internal class Control12 : Control
{
	// Token: 0x170000DF RID: 223
	// (get) Token: 0x06000277 RID: 631 RVA: 0x0000B6B4 File Offset: 0x000098B4
	// (set) Token: 0x06000278 RID: 632 RVA: 0x0000B6CC File Offset: 0x000098CC
	public Control12.Enum1 Enum1_0
	{
		get
		{
			return this.enum1_0;
		}
		set
		{
			this.enum1_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x06000279 RID: 633 RVA: 0x0000B6DC File Offset: 0x000098DC
	public Control12()
	{
		this.enum1_0 = Control12.Enum1.Close;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		this.Anchor = (AnchorStyles.Top | AnchorStyles.Right);
		base.Width = 18;
		base.Height = 20;
		this.MinimumSize = base.Size;
		this.MaximumSize = base.Size;
		base.Margin = new Padding(0);
	}

	// Token: 0x0600027A RID: 634 RVA: 0x0000B74C File Offset: 0x0000994C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.Clear(this.BackColor);
		switch (this.enum1_0)
		{
		case Control12.Enum1.Minimize:
			this.method_0(3, 10);
			break;
		case Control12.Enum1.MaximizeRestore:
			if (base.FindForm().WindowState == FormWindowState.Normal)
			{
				this.method_1(3, 5);
			}
			else
			{
				this.method_2(3, 4);
			}
			break;
		case Control12.Enum1.Close:
			this.method_3(4, 5);
			break;
		}
	}

	// Token: 0x0600027B RID: 635 RVA: 0x0000B7C8 File Offset: 0x000099C8
	private void method_0(int int_0, int int_1)
	{
		Class9.graphics_0.FillRectangle(Brushes.WhiteSmoke, int_0, int_1, 12, 5);
		Class9.graphics_0.DrawRectangle(Pens.Black, int_0, int_1, 11, 4);
	}

	// Token: 0x0600027C RID: 636 RVA: 0x0000B7F4 File Offset: 0x000099F4
	private void method_1(int int_0, int int_1)
	{
		checked
		{
			Class9.graphics_0.DrawRectangle(new Pen(Color.FromArgb(235, 235, 235), 2f), int_0 + 2, int_1 + 2, 8, 6);
			Class9.graphics_0.DrawRectangle(Pens.Black, int_0, int_1, 11, 9);
			Class9.graphics_0.DrawRectangle(Pens.Black, int_0 + 3, int_1 + 3, 5, 3);
		}
	}

	// Token: 0x0600027D RID: 637 RVA: 0x0000B860 File Offset: 0x00009A60
	private void method_2(int int_0, int int_1)
	{
		checked
		{
			Class9.graphics_0.FillRectangle(Brushes.WhiteSmoke, int_0 + 3, int_1 + 1, 8, 4);
			Class9.graphics_0.FillRectangle(Brushes.WhiteSmoke, int_0 + 7, int_1 + 5, 4, 4);
			Class9.graphics_0.DrawRectangle(Pens.Black, int_0 + 2, int_1 + 0, 9, 9);
			Class9.graphics_0.FillRectangle(Brushes.WhiteSmoke, int_0 + 1, int_1 + 3, 2, 6);
			Class9.graphics_0.FillRectangle(Brushes.WhiteSmoke, int_0 + 1, int_1 + 9, 8, 2);
			Class9.graphics_0.DrawRectangle(Pens.Black, int_0, int_1 + 2, 9, 9);
			Class9.graphics_0.DrawRectangle(Pens.Black, int_0 + 3, int_1 + 5, 3, 3);
		}
	}

	// Token: 0x0600027E RID: 638 RVA: 0x0000B914 File Offset: 0x00009B14
	private void method_3(int int_0, int int_1)
	{
		checked
		{
			if (this.graphicsPath_0 == null)
			{
				this.graphicsPath_0 = new GraphicsPath();
				this.graphicsPath_0.AddLine(int_0 + 1, int_1, int_0 + 3, int_1);
				this.graphicsPath_0.AddLine(int_0 + 5, int_1 + 2, int_0 + 7, int_1);
				this.graphicsPath_0.AddLine(int_0 + 9, int_1, int_0 + 10, int_1 + 1);
				this.graphicsPath_0.AddLine(int_0 + 7, int_1 + 4, int_0 + 7, int_1 + 5);
				this.graphicsPath_0.AddLine(int_0 + 10, int_1 + 8, int_0 + 9, int_1 + 9);
				this.graphicsPath_0.AddLine(int_0 + 7, int_1 + 9, int_0 + 5, int_1 + 7);
				this.graphicsPath_0.AddLine(int_0 + 3, int_1 + 9, int_0 + 1, int_1 + 9);
				this.graphicsPath_0.AddLine(int_0 + 0, int_1 + 8, int_0 + 3, int_1 + 5);
				this.graphicsPath_0.AddLine(int_0 + 3, int_1 + 4, int_0 + 0, int_1 + 1);
			}
			Class9.graphics_0.FillPath(Brushes.WhiteSmoke, this.graphicsPath_0);
			Class9.graphics_0.DrawPath(Pens.Black, this.graphicsPath_0);
		}
	}

	// Token: 0x0600027F RID: 639 RVA: 0x0000BA34 File Offset: 0x00009C34
	protected virtual void OnMouseClick(MouseEventArgs e)
	{
		if (e.Button == MouseButtons.Left)
		{
			Form form = base.FindForm();
			switch (this.enum1_0)
			{
			case Control12.Enum1.Minimize:
				form.WindowState = FormWindowState.Minimized;
				break;
			case Control12.Enum1.MaximizeRestore:
				if (form.WindowState != FormWindowState.Normal)
				{
					form.WindowState = FormWindowState.Normal;
				}
				else
				{
					form.WindowState = FormWindowState.Maximized;
				}
				break;
			case Control12.Enum1.Close:
				form.Close();
				break;
			}
		}
		base.Invalidate();
		base.OnMouseClick(e);
	}

	// Token: 0x04000102 RID: 258
	private Control12.Enum1 enum1_0;

	// Token: 0x04000103 RID: 259
	private GraphicsPath graphicsPath_0;

	// Token: 0x02000024 RID: 36
	public enum Enum1 : byte
	{
		// Token: 0x04000105 RID: 261
		None,
		// Token: 0x04000106 RID: 262
		Minimize,
		// Token: 0x04000107 RID: 263
		MaximizeRestore,
		// Token: 0x04000108 RID: 264
		Close
	}
}
