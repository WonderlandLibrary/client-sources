using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000031 RID: 49
	public class LogInComboBox : ComboBox
	{
		// Token: 0x1700010B RID: 267
		// (get) Token: 0x060002F3 RID: 755 RVA: 0x00010998 File Offset: 0x0000EB98
		// (set) Token: 0x060002F4 RID: 756 RVA: 0x000034B1 File Offset: 0x000016B1
		[Category("Colours")]
		public Color LineColour
		{
			get
			{
				return this._LineColour;
			}
			set
			{
				this._LineColour = value;
			}
		}

		// Token: 0x1700010C RID: 268
		// (get) Token: 0x060002F5 RID: 757 RVA: 0x000109B0 File Offset: 0x0000EBB0
		// (set) Token: 0x060002F6 RID: 758 RVA: 0x000034BB File Offset: 0x000016BB
		[Category("Colours")]
		public Color SqaureColour
		{
			get
			{
				return this._SqaureColour;
			}
			set
			{
				this._SqaureColour = value;
			}
		}

		// Token: 0x1700010D RID: 269
		// (get) Token: 0x060002F7 RID: 759 RVA: 0x000109C8 File Offset: 0x0000EBC8
		// (set) Token: 0x060002F8 RID: 760 RVA: 0x000034C5 File Offset: 0x000016C5
		[Category("Colours")]
		public Color ArrowColour
		{
			get
			{
				return this._ArrowColour;
			}
			set
			{
				this._ArrowColour = value;
			}
		}

		// Token: 0x1700010E RID: 270
		// (get) Token: 0x060002F9 RID: 761 RVA: 0x000109E0 File Offset: 0x0000EBE0
		// (set) Token: 0x060002FA RID: 762 RVA: 0x000034CF File Offset: 0x000016CF
		[Category("Colours")]
		public Color SqaureHoverColour
		{
			get
			{
				return this._SqaureHoverColour;
			}
			set
			{
				this._SqaureHoverColour = value;
			}
		}

		// Token: 0x060002FB RID: 763 RVA: 0x000034D9 File Offset: 0x000016D9
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = DrawHelpers.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060002FC RID: 764 RVA: 0x000034F2 File Offset: 0x000016F2
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = DrawHelpers.MouseState.None;
			base.Invalidate();
		}

		// Token: 0x1700010F RID: 271
		// (get) Token: 0x060002FD RID: 765 RVA: 0x000109F8 File Offset: 0x0000EBF8
		// (set) Token: 0x060002FE RID: 766 RVA: 0x0000350B File Offset: 0x0000170B
		[Category("Colours")]
		public Color BorderColour
		{
			get
			{
				return this._BorderColour;
			}
			set
			{
				this._BorderColour = value;
			}
		}

		// Token: 0x17000110 RID: 272
		// (get) Token: 0x060002FF RID: 767 RVA: 0x00010A10 File Offset: 0x0000EC10
		// (set) Token: 0x06000300 RID: 768 RVA: 0x00003515 File Offset: 0x00001715
		[Category("Colours")]
		public Color BaseColour
		{
			get
			{
				return this._BaseColour;
			}
			set
			{
				this._BaseColour = value;
			}
		}

		// Token: 0x17000111 RID: 273
		// (get) Token: 0x06000301 RID: 769 RVA: 0x00010A28 File Offset: 0x0000EC28
		// (set) Token: 0x06000302 RID: 770 RVA: 0x0000351F File Offset: 0x0000171F
		[Category("Colours")]
		public Color FontColour
		{
			get
			{
				return this._FontColour;
			}
			set
			{
				this._FontColour = value;
			}
		}

		// Token: 0x17000112 RID: 274
		// (get) Token: 0x06000303 RID: 771 RVA: 0x00010A40 File Offset: 0x0000EC40
		// (set) Token: 0x06000304 RID: 772 RVA: 0x00010A58 File Offset: 0x0000EC58
		public int StartIndex
		{
			get
			{
				return this._StartIndex;
			}
			set
			{
				this._StartIndex = value;
				try
				{
					base.SelectedIndex = value;
				}
				catch (Exception ex)
				{
				}
				base.Invalidate();
			}
		}

		// Token: 0x06000305 RID: 773 RVA: 0x00003529 File Offset: 0x00001729
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x06000306 RID: 774 RVA: 0x0000353B File Offset: 0x0000173B
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.Invalidate();
			this.OnMouseClick(e);
		}

		// Token: 0x06000307 RID: 775 RVA: 0x0000354D File Offset: 0x0000174D
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.Invalidate();
			base.OnMouseUp(e);
		}

		// Token: 0x06000308 RID: 776 RVA: 0x00010AA0 File Offset: 0x0000ECA0
		public void ReplaceItem(object sender, DrawItemEventArgs e)
		{
			e.DrawBackground();
			e.Graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			checked
			{
				Rectangle Rect = new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width + 1, e.Bounds.Height + 1);
				try
				{
					Graphics graphics = e.Graphics;
					bool flag = (e.State & DrawItemState.Selected) == DrawItemState.Selected;
					if (flag)
					{
						graphics.FillRectangle(new SolidBrush(this._SqaureColour), Rect);
						graphics.DrawString(base.GetItemText(RuntimeHelpers.GetObjectValue(base.Items[e.Index])), this.Font, new SolidBrush(this._FontColour), 1f, (float)(e.Bounds.Top + 2));
					}
					else
					{
						graphics.FillRectangle(new SolidBrush(this._BaseColour), Rect);
						graphics.DrawString(base.GetItemText(RuntimeHelpers.GetObjectValue(base.Items[e.Index])), this.Font, new SolidBrush(this._FontColour), 1f, (float)(e.Bounds.Top + 2));
					}
				}
				catch (Exception ex)
				{
				}
				e.DrawFocusRectangle();
				base.Invalidate();
			}
		}

		// Token: 0x06000309 RID: 777 RVA: 0x00010C10 File Offset: 0x0000EE10
		public LogInComboBox()
		{
			base.DrawItem += this.ReplaceItem;
			this._StartIndex = 0;
			this._BorderColour = Color.FromArgb(35, 35, 35);
			this._BaseColour = Color.FromArgb(42, 42, 42);
			this._FontColour = Color.FromArgb(255, 255, 255);
			this._LineColour = Color.FromArgb(255, 0, 0);
			this._SqaureColour = Color.FromArgb(47, 47, 47);
			this._ArrowColour = Color.FromArgb(30, 30, 30);
			this._SqaureHoverColour = Color.FromArgb(52, 52, 52);
			this.State = DrawHelpers.MouseState.None;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.Transparent;
			base.DrawMode = DrawMode.OwnerDrawFixed;
			base.DropDownStyle = ComboBoxStyle.DropDownList;
			base.Width = 163;
			this.Font = new Font("Segoe UI", 10f);
		}

		// Token: 0x0600030A RID: 778 RVA: 0x00010D1C File Offset: 0x0000EF1C
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics g = e.Graphics;
			Graphics graphics = g;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.Clear(this.BackColor);
			checked
			{
				try
				{
					Rectangle Square = new Rectangle(base.Width - 25, 0, base.Width, base.Height);
					graphics.FillRectangle(new SolidBrush(this._BaseColour), new Rectangle(0, 0, base.Width - 25, base.Height));
					DrawHelpers.MouseState state = this.State;
					if (state != DrawHelpers.MouseState.None)
					{
						if (state == DrawHelpers.MouseState.Over)
						{
							graphics.FillRectangle(new SolidBrush(this._SqaureHoverColour), Square);
						}
					}
					else
					{
						graphics.FillRectangle(new SolidBrush(this._SqaureColour), Square);
					}
					graphics.DrawLine(new Pen(this._LineColour, 2f), new Point(base.Width - 26, 1), new Point(base.Width - 26, base.Height - 1));
					try
					{
						graphics.DrawString(this.Text, this.Font, new SolidBrush(this._FontColour), new Rectangle(3, 0, base.Width - 20, base.Height), new StringFormat
						{
							LineAlignment = StringAlignment.Center,
							Alignment = StringAlignment.Near
						});
					}
					catch (Exception ex)
					{
					}
					graphics.DrawRectangle(new Pen(this._BorderColour, 2f), new Rectangle(0, 0, base.Width, base.Height));
					Point[] P = new Point[]
					{
						new Point(base.Width - 17, 11),
						new Point(base.Width - 13, 5),
						new Point(base.Width - 9, 11)
					};
					graphics.FillPolygon(new SolidBrush(this._BorderColour), P);
					graphics.DrawPolygon(new Pen(this._ArrowColour), P);
					Point[] P2 = new Point[]
					{
						new Point(base.Width - 17, 15),
						new Point(base.Width - 13, 21),
						new Point(base.Width - 9, 15)
					};
					graphics.FillPolygon(new SolidBrush(this._BorderColour), P2);
					graphics.DrawPolygon(new Pen(this._ArrowColour), P2);
				}
				catch (Exception ex2)
				{
				}
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
				graphics = null;
			}
		}

		// Token: 0x0400012D RID: 301
		private int _StartIndex;

		// Token: 0x0400012E RID: 302
		private Color _BorderColour;

		// Token: 0x0400012F RID: 303
		private Color _BaseColour;

		// Token: 0x04000130 RID: 304
		private Color _FontColour;

		// Token: 0x04000131 RID: 305
		private Color _LineColour;

		// Token: 0x04000132 RID: 306
		private Color _SqaureColour;

		// Token: 0x04000133 RID: 307
		private Color _ArrowColour;

		// Token: 0x04000134 RID: 308
		private Color _SqaureHoverColour;

		// Token: 0x04000135 RID: 309
		private DrawHelpers.MouseState State;
	}
}
