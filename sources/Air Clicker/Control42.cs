using System;
using System.Drawing;
using System.Windows.Forms;

// Token: 0x020000B3 RID: 179
internal class Control42 : Control
{
	// Token: 0x17000268 RID: 616
	// (get) Token: 0x06000862 RID: 2146 RVA: 0x000278F8 File Offset: 0x00025AF8
	// (set) Token: 0x06000863 RID: 2147 RVA: 0x00027900 File Offset: 0x00025B00
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

	// Token: 0x17000269 RID: 617
	// (get) Token: 0x06000864 RID: 2148 RVA: 0x00027910 File Offset: 0x00025B10
	// (set) Token: 0x06000865 RID: 2149 RVA: 0x00027918 File Offset: 0x00025B18
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

	// Token: 0x1700026A RID: 618
	// (get) Token: 0x06000866 RID: 2150 RVA: 0x00027928 File Offset: 0x00025B28
	// (set) Token: 0x06000867 RID: 2151 RVA: 0x00027930 File Offset: 0x00025B30
	public bool Boolean_2
	{
		get
		{
			return this.bool_2;
		}
		set
		{
			this.bool_2 = value;
			base.Invalidate();
		}
	}

	// Token: 0x06000868 RID: 2152 RVA: 0x00027940 File Offset: 0x00025B40
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Size = new Size(100, 25);
	}

	// Token: 0x06000869 RID: 2153 RVA: 0x00027958 File Offset: 0x00025B58
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		int x = e.Location.X;
		int y = e.Location.Y;
		if (y > 0 && y < checked(base.Height - 2))
		{
			if (x > 0 && x < 34)
			{
				this.enum11_0 = Control42.Enum11.Minimize;
			}
			else if (x <= 33 || x >= 65)
			{
				if (x > 64 && x < base.Width)
				{
					this.enum11_0 = Control42.Enum11.Close;
				}
				else
				{
					this.enum11_0 = Control42.Enum11.None;
				}
			}
			else
			{
				this.enum11_0 = Control42.Enum11.Maximize;
			}
		}
		else
		{
			this.enum11_0 = Control42.Enum11.None;
		}
		base.Invalidate();
	}

	// Token: 0x0600086A RID: 2154 RVA: 0x00027A00 File Offset: 0x00025C00
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		switch (this.enum11_0)
		{
		case Control42.Enum11.Minimize:
			if (this.bool_1)
			{
				base.Parent.FindForm().WindowState = FormWindowState.Minimized;
			}
			break;
		case Control42.Enum11.Maximize:
			if (this.bool_0)
			{
				if (base.Parent.FindForm().WindowState == FormWindowState.Normal)
				{
					base.Parent.FindForm().WindowState = FormWindowState.Maximized;
				}
				else
				{
					base.Parent.FindForm().WindowState = FormWindowState.Normal;
				}
			}
			break;
		case Control42.Enum11.Close:
			base.Parent.FindForm().Close();
			break;
		}
	}

	// Token: 0x0600086B RID: 2155 RVA: 0x00027AA0 File Offset: 0x00025CA0
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum11_0 = Control42.Enum11.None;
		base.Invalidate();
	}

	// Token: 0x0600086C RID: 2156 RVA: 0x00027AB8 File Offset: 0x00025CB8
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		base.Focus();
	}

	// Token: 0x0600086D RID: 2157 RVA: 0x00027AC8 File Offset: 0x00025CC8
	public Control42()
	{
		this.enum11_0 = Control42.Enum11.None;
		this.bool_0 = true;
		this.bool_1 = true;
		this.bool_2 = false;
		this.DoubleBuffered = true;
		this.Anchor = (AnchorStyles.Top | AnchorStyles.Right);
	}

	// Token: 0x0600086E RID: 2158 RVA: 0x00027AFC File Offset: 0x00025CFC
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
		try
		{
			base.Location = new Point(checked(base.Parent.Width - 112), 15);
		}
		catch (Exception ex)
		{
		}
	}

	// Token: 0x0600086F RID: 2159 RVA: 0x00027B4C File Offset: 0x00025D4C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		base.OnPaint(e);
		Graphics graphics = e.Graphics;
		graphics.Clear(Color.FromArgb(181, 41, 42));
		if (this.bool_2)
		{
			switch (this.enum11_0)
			{
			case Control42.Enum11.Minimize:
				if (this.bool_1)
				{
					graphics.FillRectangle(new SolidBrush(Color.FromArgb(156, 35, 35)), new Rectangle(3, 0, 30, base.Height));
				}
				break;
			case Control42.Enum11.Maximize:
				if (this.bool_0)
				{
					graphics.FillRectangle(new SolidBrush(Color.FromArgb(156, 35, 35)), new Rectangle(35, 0, 30, base.Height));
				}
				break;
			case Control42.Enum11.Close:
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(156, 35, 35)), new Rectangle(66, 0, 35, base.Height));
				break;
			case Control42.Enum11.None:
				graphics.Clear(Color.FromArgb(181, 41, 42));
				break;
			}
		}
		graphics.DrawString("r", new Font("Marlett", 12f), new SolidBrush(Color.FromArgb(255, 254, 255)), new Point(checked(base.Width - 16), 8), new StringFormat
		{
			Alignment = StringAlignment.Center
		});
		FormWindowState windowState = base.Parent.FindForm().WindowState;
		if (windowState == FormWindowState.Normal)
		{
			if (this.bool_0)
			{
				graphics.DrawString("1", new Font("Marlett", 12f), new SolidBrush(Color.FromArgb(255, 254, 255)), new Point(51, 7), new StringFormat
				{
					Alignment = StringAlignment.Center
				});
			}
			else
			{
				graphics.DrawString("1", new Font("Marlett", 12f), new SolidBrush(Color.LightGray), new Point(51, 7), new StringFormat
				{
					Alignment = StringAlignment.Center
				});
			}
		}
		else if (windowState == FormWindowState.Maximized)
		{
			if (!this.bool_0)
			{
				graphics.DrawString("2", new Font("Marlett", 12f), new SolidBrush(Color.LightGray), new Point(51, 7), new StringFormat
				{
					Alignment = StringAlignment.Center
				});
			}
			else
			{
				graphics.DrawString("2", new Font("Marlett", 12f), new SolidBrush(Color.FromArgb(255, 254, 255)), new Point(51, 7), new StringFormat
				{
					Alignment = StringAlignment.Center
				});
			}
		}
		if (this.bool_1)
		{
			graphics.DrawString("0", new Font("Marlett", 12f), new SolidBrush(Color.FromArgb(255, 254, 255)), new Point(20, 7), new StringFormat
			{
				Alignment = StringAlignment.Center
			});
		}
		else
		{
			graphics.DrawString("0", new Font("Marlett", 12f), new SolidBrush(Color.LightGray), new Point(20, 7), new StringFormat
			{
				Alignment = StringAlignment.Center
			});
		}
	}

	// Token: 0x04000402 RID: 1026
	private Control42.Enum11 enum11_0;

	// Token: 0x04000403 RID: 1027
	private bool bool_0;

	// Token: 0x04000404 RID: 1028
	private bool bool_1;

	// Token: 0x04000405 RID: 1029
	private bool bool_2;

	// Token: 0x020000B4 RID: 180
	public enum Enum11
	{
		// Token: 0x04000407 RID: 1031
		Minimize,
		// Token: 0x04000408 RID: 1032
		Maximize,
		// Token: 0x04000409 RID: 1033
		Close,
		// Token: 0x0400040A RID: 1034
		None
	}
}
