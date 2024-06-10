using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x02000062 RID: 98
public class GControl2 : Control
{
	// Token: 0x17000178 RID: 376
	// (get) Token: 0x060004DB RID: 1243 RVA: 0x000179E8 File Offset: 0x00015BE8
	// (set) Token: 0x060004DC RID: 1244 RVA: 0x000179F0 File Offset: 0x00015BF0
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000179 RID: 377
	// (get) Token: 0x060004DD RID: 1245 RVA: 0x00017A00 File Offset: 0x00015C00
	// (set) Token: 0x060004DE RID: 1246 RVA: 0x00017A08 File Offset: 0x00015C08
	public bool Boolean_1
	{
		get
		{
			return this.bool_1;
		}
		set
		{
			this.bool_1 = value;
			base.Invalidate();
		}
	}

	// Token: 0x060004DF RID: 1247 RVA: 0x00017A18 File Offset: 0x00015C18
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		if (this.int_0 <= 5 || this.int_0 >= 29)
		{
			if (this.int_0 <= 32 || this.int_0 >= 56)
			{
				if (this.int_0 > 59 && this.int_0 < 83)
				{
					base.FindForm().Close();
				}
			}
			else if (!this.Boolean_1)
			{
				if (base.FindForm().WindowState != FormWindowState.Maximized)
				{
					base.FindForm().WindowState = FormWindowState.Minimized;
					base.FindForm().WindowState = FormWindowState.Maximized;
				}
				else
				{
					base.FindForm().WindowState = FormWindowState.Minimized;
					base.FindForm().WindowState = FormWindowState.Normal;
				}
			}
		}
		else if (!this.Boolean_0)
		{
			base.FindForm().WindowState = FormWindowState.Minimized;
		}
		this.enum8_0 = Enum8.Down;
		base.Invalidate();
	}

	// Token: 0x060004E0 RID: 1248 RVA: 0x00017AFC File Offset: 0x00015CFC
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum8_0 = Enum8.Over;
		base.Invalidate();
	}

	// Token: 0x060004E1 RID: 1249 RVA: 0x00017B14 File Offset: 0x00015D14
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum8_0 = Enum8.Over;
		base.Invalidate();
	}

	// Token: 0x060004E2 RID: 1250 RVA: 0x00017B2C File Offset: 0x00015D2C
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum8_0 = Enum8.None;
		base.Invalidate();
	}

	// Token: 0x060004E3 RID: 1251 RVA: 0x00017B44 File Offset: 0x00015D44
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		this.int_0 = e.Location.X;
		base.Invalidate();
	}

	// Token: 0x060004E4 RID: 1252 RVA: 0x00017B74 File Offset: 0x00015D74
	public GControl2()
	{
		this.enum8_0 = Enum8.None;
		this.rectangle_0 = new Rectangle(5, 2, 24, 24);
		this.rectangle_1 = new Rectangle(32, 2, 24, 24);
		this.rectangle_2 = new Rectangle(59, 2, 24, 24);
		this.rectangle_3 = new Rectangle(5, 4, 24, 24);
		this.rectangle_4 = new Rectangle(32, 4, 24, 24);
		this.rectangle_5 = new Rectangle(59, 4, 24, 24);
		this.bool_0 = false;
		this.bool_1 = false;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.Transparent;
		base.Width = 85;
		base.Height = 30;
		this.Anchor = (AnchorStyles.Top | AnchorStyles.Right);
	}

	// Token: 0x060004E5 RID: 1253 RVA: 0x00017C3C File Offset: 0x00015E3C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		base.OnPaint(e);
		graphics.SmoothingMode = SmoothingMode.HighQuality;
		graphics.FillRectangle(new SolidBrush(Color.FromArgb(161, 161, 161)), this.rectangle_0);
		graphics.DrawRectangle(Pens.Black, this.rectangle_0);
		graphics.FillRectangle(new SolidBrush(Color.FromArgb(161, 161, 161)), this.rectangle_1);
		graphics.DrawRectangle(Pens.Black, this.rectangle_1);
		graphics.FillRectangle(new SolidBrush(Color.FromArgb(161, 161, 161)), this.rectangle_2);
		graphics.DrawRectangle(Pens.Black, this.rectangle_2);
		graphics.DrawString("0", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(0, 0, 0)), this.rectangle_3, new StringFormat
		{
			Alignment = StringAlignment.Center,
			LineAlignment = StringAlignment.Center
		});
		if (base.FindForm().WindowState != FormWindowState.Maximized)
		{
			graphics.DrawString("1", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(0, 0, 0)), this.rectangle_4, new StringFormat
			{
				Alignment = StringAlignment.Center,
				LineAlignment = StringAlignment.Center
			});
		}
		else
		{
			graphics.DrawString("2", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(0, 0, 0)), this.rectangle_4, new StringFormat
			{
				Alignment = StringAlignment.Center,
				LineAlignment = StringAlignment.Center
			});
		}
		graphics.DrawString("r", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(0, 0, 0)), this.rectangle_5, new StringFormat
		{
			Alignment = StringAlignment.Center,
			LineAlignment = StringAlignment.Center
		});
		Enum8 @enum = this.enum8_0;
		if (@enum == Enum8.None)
		{
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(161, 161, 161)), this.rectangle_0);
			graphics.DrawRectangle(Pens.Black, this.rectangle_0);
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(161, 161, 161)), this.rectangle_1);
			graphics.DrawRectangle(Pens.Black, this.rectangle_1);
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(161, 161, 161)), this.rectangle_2);
			graphics.DrawRectangle(Pens.Black, this.rectangle_2);
			graphics.DrawString("0", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(0, 0, 0)), this.rectangle_3, new StringFormat
			{
				Alignment = StringAlignment.Center,
				LineAlignment = StringAlignment.Center
			});
			if (base.FindForm().WindowState != FormWindowState.Maximized)
			{
				graphics.DrawString("1", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(0, 0, 0)), this.rectangle_4, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
			else
			{
				graphics.DrawString("2", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(0, 0, 0)), this.rectangle_4, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
			graphics.DrawString("r", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(0, 0, 0)), this.rectangle_5, new StringFormat
			{
				Alignment = StringAlignment.Center,
				LineAlignment = StringAlignment.Center
			});
		}
		else if (@enum != Enum8.Over)
		{
			if (!this.Boolean_0)
			{
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(81, 81, 81)), this.rectangle_0);
				graphics.DrawRectangle(Pens.Black, this.rectangle_0);
			}
			if (!this.Boolean_1)
			{
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(81, 81, 81)), this.rectangle_1);
				graphics.DrawRectangle(Pens.Black, this.rectangle_1);
			}
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(81, 81, 81)), this.rectangle_2);
			graphics.DrawRectangle(Pens.Black, this.rectangle_2);
			graphics.DrawString("0", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(0, 0, 0)), this.rectangle_3, new StringFormat
			{
				Alignment = StringAlignment.Center,
				LineAlignment = StringAlignment.Center
			});
			if (base.FindForm().WindowState == FormWindowState.Maximized)
			{
				graphics.DrawString("2", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(0, 0, 0)), this.rectangle_4, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
			else
			{
				graphics.DrawString("1", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(0, 0, 0)), this.rectangle_4, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
			graphics.DrawString("r", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(0, 0, 0)), this.rectangle_5, new StringFormat
			{
				Alignment = StringAlignment.Center,
				LineAlignment = StringAlignment.Center
			});
		}
		else if (this.int_0 > 5 && this.int_0 < 29)
		{
			if (!this.Boolean_0)
			{
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(121, 121, 121)), this.rectangle_0);
				graphics.DrawRectangle(Pens.Black, this.rectangle_0);
				graphics.DrawString("0", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(255, 255, 255)), this.rectangle_3, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
		}
		else if (this.int_0 > 32 && this.int_0 < 56)
		{
			if (!this.Boolean_1)
			{
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(121, 121, 121)), this.rectangle_1);
				graphics.DrawRectangle(Pens.Black, this.rectangle_1);
			}
			if (base.FindForm().WindowState == FormWindowState.Maximized)
			{
				graphics.DrawString("2", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(255, 255, 255)), this.rectangle_4, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
			else
			{
				graphics.DrawString("1", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(255, 255, 255)), this.rectangle_4, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
		}
		else if (this.int_0 > 59 && this.int_0 < 83)
		{
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(121, 121, 121)), this.rectangle_2);
			graphics.DrawRectangle(Pens.Black, this.rectangle_2);
			graphics.DrawString("r", new Font("Marlett", 11.5f), new SolidBrush(Color.FromArgb(255, 255, 255)), this.rectangle_5, new StringFormat
			{
				Alignment = StringAlignment.Center,
				LineAlignment = StringAlignment.Center
			});
		}
		e.Graphics.DrawImage(bitmap, new Point(0, 0));
		graphics.Dispose();
		bitmap.Dispose();
	}

	// Token: 0x04000295 RID: 661
	private Enum8 enum8_0;

	// Token: 0x04000296 RID: 662
	private int int_0;

	// Token: 0x04000297 RID: 663
	private readonly Rectangle rectangle_0;

	// Token: 0x04000298 RID: 664
	private readonly Rectangle rectangle_1;

	// Token: 0x04000299 RID: 665
	private readonly Rectangle rectangle_2;

	// Token: 0x0400029A RID: 666
	private readonly Rectangle rectangle_3;

	// Token: 0x0400029B RID: 667
	private readonly Rectangle rectangle_4;

	// Token: 0x0400029C RID: 668
	private readonly Rectangle rectangle_5;

	// Token: 0x0400029D RID: 669
	private bool bool_0;

	// Token: 0x0400029E RID: 670
	private bool bool_1;
}
