using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace GHOSTBYTES
{
	// Token: 0x02000021 RID: 33
	internal class FlatComboBox : ComboBox
	{
		// Token: 0x060001AA RID: 426 RVA: 0x000037EA File Offset: 0x000019EA
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x060001AB RID: 427 RVA: 0x00003800 File Offset: 0x00001A00
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001AC RID: 428 RVA: 0x00003816 File Offset: 0x00001A16
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001AD RID: 429 RVA: 0x0000382C File Offset: 0x00001A2C
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x060001AE RID: 430 RVA: 0x00008D60 File Offset: 0x00006F60
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.x = e.Location.X;
			this.y = e.Location.Y;
			base.Invalidate();
			if (e.X < checked(base.Width - 41))
			{
				this.Cursor = Cursors.IBeam;
				return;
			}
			this.Cursor = Cursors.Hand;
		}

		// Token: 0x060001AF RID: 431 RVA: 0x00003842 File Offset: 0x00001A42
		protected override void OnDrawItem(DrawItemEventArgs e)
		{
			base.OnDrawItem(e);
			base.Invalidate();
			if ((e.State & DrawItemState.Selected) == DrawItemState.Selected)
			{
				base.Invalidate();
			}
		}

		// Token: 0x060001B0 RID: 432 RVA: 0x00003862 File Offset: 0x00001A62
		protected override void OnClick(EventArgs e)
		{
			base.OnClick(e);
			base.Invalidate();
		}

		// Token: 0x17000092 RID: 146
		// (get) Token: 0x060001B1 RID: 433 RVA: 0x00003871 File Offset: 0x00001A71
		// (set) Token: 0x060001B2 RID: 434 RVA: 0x00003879 File Offset: 0x00001A79
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

		// Token: 0x17000093 RID: 147
		// (get) Token: 0x060001B3 RID: 435 RVA: 0x00003882 File Offset: 0x00001A82
		// (set) Token: 0x060001B4 RID: 436 RVA: 0x00008DCC File Offset: 0x00006FCC
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

		// Token: 0x060001B5 RID: 437 RVA: 0x00008E0C File Offset: 0x0000700C
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
					e.Graphics.FillRectangle(new SolidBrush(Color.FromArgb(0, 170, 220)), e.Bounds);
				}
				else
				{
					e.Graphics.FillRectangle(new SolidBrush(this._BaseColor), e.Bounds);
				}
				e.Graphics.DrawString(base.GetItemText(RuntimeHelpers.GetObjectValue(base.Items[e.Index])), new Font("Segoe UI", 8f), Brushes.White, checked(new Rectangle(e.Bounds.X + 2, e.Bounds.Y + 2, e.Bounds.Width, e.Bounds.Height)));
				e.Graphics.Dispose();
			}
		}

		// Token: 0x060001B6 RID: 438 RVA: 0x0000388A File Offset: 0x00001A8A
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 18;
		}

		// Token: 0x060001B7 RID: 439 RVA: 0x00008F3C File Offset: 0x0000713C
		public FlatComboBox()
		{
			base.DrawItem += this.DrawItem_;
			this._StartIndex = 0;
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(60, 60, 60);
			this._BGColor = Color.FromArgb(60, 60, 60);
			this._HoverColor = Color.FromArgb(0, 170, 220);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.DrawMode = DrawMode.OwnerDrawFixed;
			this.BackColor = Color.FromArgb(50, 50, 50);
			this.ForeColor = Color.White;
			base.DropDownStyle = ComboBoxStyle.DropDownList;
			this.Cursor = Cursors.Hand;
			this.StartIndex = 0;
			base.ItemHeight = 18;
			this.Font = new Font("Segoe UI", 8f, FontStyle.Regular);
		}

		// Token: 0x060001B8 RID: 440 RVA: 0x00009014 File Offset: 0x00007214
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width;
			this.H = base.Height;
			Rectangle rect = new Rectangle(0, 0, this.W, this.H);
			checked
			{
				Rectangle rect2 = new Rectangle(this.W - 40, 0, this.W, this.H);
				GraphicsPath graphicsPath = new GraphicsPath();
				new GraphicsPath();
				Graphics g = Helpers.G;
				g.Clear(Color.FromArgb(0, 170, 220));
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
		}

		// Token: 0x04000092 RID: 146
		private int W;

		// Token: 0x04000093 RID: 147
		private int H;

		// Token: 0x04000094 RID: 148
		private int _StartIndex;

		// Token: 0x04000095 RID: 149
		private int x;

		// Token: 0x04000096 RID: 150
		private int y;

		// Token: 0x04000097 RID: 151
		private MouseState State;

		// Token: 0x04000098 RID: 152
		private Color _BaseColor;

		// Token: 0x04000099 RID: 153
		private Color _BGColor;

		// Token: 0x0400009A RID: 154
		private Color _HoverColor;
	}
}
