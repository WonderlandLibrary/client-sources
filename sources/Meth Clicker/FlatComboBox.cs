using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x02000025 RID: 37
	internal class FlatComboBox : ComboBox
	{
		// Token: 0x060002B0 RID: 688 RVA: 0x00013533 File Offset: 0x00011733
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x060002B1 RID: 689 RVA: 0x00013549 File Offset: 0x00011749
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060002B2 RID: 690 RVA: 0x0001355F File Offset: 0x0001175F
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060002B3 RID: 691 RVA: 0x00013575 File Offset: 0x00011775
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x060002B4 RID: 692 RVA: 0x0001358C File Offset: 0x0001178C
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.x = e.Location.X;
			this.y = e.Location.Y;
			base.Invalidate();
			if (e.X < base.Width - 41)
			{
				this.Cursor = Cursors.IBeam;
				return;
			}
			this.Cursor = Cursors.Hand;
		}

		// Token: 0x060002B5 RID: 693 RVA: 0x000135F6 File Offset: 0x000117F6
		protected override void OnDrawItem(DrawItemEventArgs e)
		{
			base.OnDrawItem(e);
			base.Invalidate();
			if ((e.State & DrawItemState.Selected) == DrawItemState.Selected)
			{
				base.Invalidate();
			}
		}

		// Token: 0x060002B6 RID: 694 RVA: 0x00013616 File Offset: 0x00011816
		protected override void OnClick(EventArgs e)
		{
			base.OnClick(e);
			base.Invalidate();
		}

		// Token: 0x170000C9 RID: 201
		// (get) Token: 0x060002B7 RID: 695 RVA: 0x00013625 File Offset: 0x00011825
		// (set) Token: 0x060002B8 RID: 696 RVA: 0x0001362D File Offset: 0x0001182D
		[Category("Colors")]
		public Color HoverColor
		{
			get
			{
				return this._HoverColor;
			}
			set
			{
				this._HoverColor = value;
			}
		}

		// Token: 0x170000CA RID: 202
		// (get) Token: 0x060002B9 RID: 697 RVA: 0x00013636 File Offset: 0x00011836
		// (set) Token: 0x060002BA RID: 698 RVA: 0x00013640 File Offset: 0x00011840
		private int StartIndex
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

		// Token: 0x060002BB RID: 699 RVA: 0x00013680 File Offset: 0x00011880
		public void DrawItem_(object sender, DrawItemEventArgs e)
		{
			if (e.Index >= 0)
			{
				e.DrawBackground();
				e.DrawFocusRectangle();
				e.Graphics.SmoothingMode = SmoothingMode.HighQuality;
				e.Graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
				e.Graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
				e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
				if ((e.State & DrawItemState.Selected) == DrawItemState.Selected)
				{
					e.Graphics.FillRectangle(new SolidBrush(this._HoverColor), e.Bounds);
				}
				else
				{
					e.Graphics.FillRectangle(new SolidBrush(this._BaseColor), e.Bounds);
				}
				e.Graphics.DrawString(base.GetItemText(RuntimeHelpers.GetObjectValue(base.Items[e.Index])), new Font("Segoe UI", 8f), Brushes.White, new Rectangle(e.Bounds.X + 2, e.Bounds.Y + 2, e.Bounds.Width, e.Bounds.Height));
				e.Graphics.Dispose();
			}
		}

		// Token: 0x060002BC RID: 700 RVA: 0x000137A6 File Offset: 0x000119A6
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 18;
		}

		// Token: 0x060002BD RID: 701 RVA: 0x000137B8 File Offset: 0x000119B8
		public FlatComboBox()
		{
			base.DrawItem += this.DrawItem_;
			this._StartIndex = 0;
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(25, 27, 29);
			this._BGColor = Color.FromArgb(45, 47, 49);
			this._HoverColor = Color.FromArgb(35, 168, 109);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.DrawMode = DrawMode.OwnerDrawFixed;
			this.BackColor = Color.FromArgb(45, 45, 48);
			this.ForeColor = Color.White;
			base.DropDownStyle = ComboBoxStyle.DropDownList;
			this.Cursor = Cursors.Hand;
			this.StartIndex = 0;
			base.ItemHeight = 18;
			this.Font = new Font("Segoe UI", 8f, FontStyle.Regular);
		}

		// Token: 0x060002BE RID: 702 RVA: 0x00013890 File Offset: 0x00011A90
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width;
			this.H = base.Height;
			Rectangle rect = new Rectangle(0, 0, this.W, this.H);
			Rectangle rect2 = new Rectangle(this.W - 40, 0, this.W, this.H);
			GraphicsPath graphicsPath = new GraphicsPath();
			new GraphicsPath();
			Graphics g = Helpers.G;
			g.Clear(Color.FromArgb(45, 45, 48));
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.FillRectangle(new SolidBrush(this._BGColor), rect);
			graphicsPath.Reset();
			graphicsPath.AddRectangle(rect2);
			g.SetClip(graphicsPath);
			g.FillRectangle(new SolidBrush(this._BaseColor), rect2);
			g.ResetClip();
			g.DrawLine(Pens.White, this.W - 10, 6, this.W - 30, 6);
			g.DrawLine(Pens.White, this.W - 10, 12, this.W - 30, 12);
			g.DrawLine(Pens.White, this.W - 10, 18, this.W - 30, 18);
			g.DrawString(this.Text, this.Font, Brushes.White, new Point(4, 6), Helpers.NearSF);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x04000164 RID: 356
		private int W;

		// Token: 0x04000165 RID: 357
		private int H;

		// Token: 0x04000166 RID: 358
		private int _StartIndex;

		// Token: 0x04000167 RID: 359
		private int x;

		// Token: 0x04000168 RID: 360
		private int y;

		// Token: 0x04000169 RID: 361
		private MouseState State;

		// Token: 0x0400016A RID: 362
		private Color _BaseColor;

		// Token: 0x0400016B RID: 363
		private Color _BGColor;

		// Token: 0x0400016C RID: 364
		private Color _HoverColor;
	}
}
