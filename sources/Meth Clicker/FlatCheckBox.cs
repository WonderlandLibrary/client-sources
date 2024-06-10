using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x02000020 RID: 32
	[DefaultEvent("CheckedChanged")]
	internal class FlatCheckBox : Control
	{
		// Token: 0x06000257 RID: 599 RVA: 0x0000D7C2 File Offset: 0x0000B9C2
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x170000B1 RID: 177
		// (get) Token: 0x06000258 RID: 600 RVA: 0x00011861 File Offset: 0x0000FA61
		// (set) Token: 0x06000259 RID: 601 RVA: 0x00011869 File Offset: 0x0000FA69
		public bool Checked
		{
			get
			{
				return this._Checked;
			}
			set
			{
				this._Checked = value;
				base.Invalidate();
			}
		}

		// Token: 0x14000016 RID: 22
		// (add) Token: 0x0600025A RID: 602 RVA: 0x00011878 File Offset: 0x0000FA78
		// (remove) Token: 0x0600025B RID: 603 RVA: 0x000118B0 File Offset: 0x0000FAB0
		public event FlatCheckBox.CheckedChangedEventHandler CheckedChanged;

		// Token: 0x0600025C RID: 604 RVA: 0x000118E8 File Offset: 0x0000FAE8
		protected override void OnClick(EventArgs e)
		{
			this._Checked = !this._Checked;
			FlatCheckBox.CheckedChangedEventHandler checkedChangedEvent = this.CheckedChangedEvent;
			if (checkedChangedEvent != null)
			{
				checkedChangedEvent(this);
			}
			base.OnClick(e);
		}

		// Token: 0x170000B2 RID: 178
		// (get) Token: 0x0600025D RID: 605 RVA: 0x0001191C File Offset: 0x0000FB1C
		// (set) Token: 0x0600025E RID: 606 RVA: 0x00011924 File Offset: 0x0000FB24
		[Category("Options")]
		public FlatCheckBox._Options Options
		{
			get
			{
				return this.O;
			}
			set
			{
				this.O = value;
			}
		}

		// Token: 0x0600025F RID: 607 RVA: 0x0001151B File Offset: 0x0000F71B
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 22;
		}

		// Token: 0x170000B3 RID: 179
		// (get) Token: 0x06000260 RID: 608 RVA: 0x0001192D File Offset: 0x0000FB2D
		// (set) Token: 0x06000261 RID: 609 RVA: 0x00011935 File Offset: 0x0000FB35
		[Category("Colors")]
		public Color BaseColor
		{
			get
			{
				return this._BaseColor;
			}
			set
			{
				this._BaseColor = value;
			}
		}

		// Token: 0x170000B4 RID: 180
		// (get) Token: 0x06000262 RID: 610 RVA: 0x0001193E File Offset: 0x0000FB3E
		// (set) Token: 0x06000263 RID: 611 RVA: 0x00011946 File Offset: 0x0000FB46
		[Category("Colors")]
		public Color BorderColor
		{
			get
			{
				return this._BorderColor;
			}
			set
			{
				this._BorderColor = value;
			}
		}

		// Token: 0x06000264 RID: 612 RVA: 0x0001194F File Offset: 0x0000FB4F
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000265 RID: 613 RVA: 0x00011965 File Offset: 0x0000FB65
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000266 RID: 614 RVA: 0x0001197B File Offset: 0x0000FB7B
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000267 RID: 615 RVA: 0x00011991 File Offset: 0x0000FB91
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000268 RID: 616 RVA: 0x000119A8 File Offset: 0x0000FBA8
		public FlatCheckBox()
		{
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(45, 47, 49);
			this._BorderColor = Helpers._FlatColor;
			this._TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.FromArgb(60, 70, 73);
			this.Cursor = Cursors.Hand;
			this.Font = new Font("Segoe UI", 10f);
			base.Size = new Size(112, 22);
		}

		// Token: 0x06000269 RID: 617 RVA: 0x00011A4C File Offset: 0x0000FC4C
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width - 1;
			this.H = base.Height - 1;
			Rectangle rect = new Rectangle(0, 2, base.Height - 5, base.Height - 5);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			FlatCheckBox._Options o = this.O;
			if (o != FlatCheckBox._Options.Style1)
			{
				if (o == FlatCheckBox._Options.Style2)
				{
					g.FillRectangle(new SolidBrush(this._BaseColor), rect);
					MouseState state = this.State;
					if (state != MouseState.Over)
					{
						if (state == MouseState.Down)
						{
							g.DrawRectangle(new Pen(this._BorderColor), rect);
							g.FillRectangle(new SolidBrush(Color.FromArgb(118, 213, 170)), rect);
						}
					}
					else
					{
						g.DrawRectangle(new Pen(this._BorderColor), rect);
						g.FillRectangle(new SolidBrush(Color.FromArgb(118, 213, 170)), rect);
					}
					if (this.Checked)
					{
						g.DrawString("ü", new Font("Wingdings", 18f), new SolidBrush(this._BorderColor), new Rectangle(5, 7, this.H - 9, this.H - 9), Helpers.CenterSF);
					}
					if (!base.Enabled)
					{
						g.FillRectangle(new SolidBrush(Color.FromArgb(54, 58, 61)), rect);
						g.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(48, 119, 91)), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
					}
					g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
				}
			}
			else
			{
				g.FillRectangle(new SolidBrush(this._BaseColor), rect);
				MouseState state2 = this.State;
				if (state2 != MouseState.Over)
				{
					if (state2 == MouseState.Down)
					{
						g.DrawRectangle(new Pen(this._BorderColor), rect);
					}
				}
				else
				{
					g.DrawRectangle(new Pen(this._BorderColor), rect);
				}
				if (this.Checked)
				{
					g.DrawString("ü", new Font("Wingdings", 18f), new SolidBrush(this._BorderColor), new Rectangle(5, 7, this.H - 9, this.H - 9), Helpers.CenterSF);
				}
				if (!base.Enabled)
				{
					g.FillRectangle(new SolidBrush(Color.FromArgb(54, 58, 61)), rect);
					g.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(140, 142, 143)), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
				}
				g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
			}
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x04000136 RID: 310
		private int W;

		// Token: 0x04000137 RID: 311
		private int H;

		// Token: 0x04000138 RID: 312
		private MouseState State;

		// Token: 0x04000139 RID: 313
		private FlatCheckBox._Options O;

		// Token: 0x0400013A RID: 314
		private bool _Checked;

		// Token: 0x0400013C RID: 316
		private Color _BaseColor;

		// Token: 0x0400013D RID: 317
		private Color _BorderColor;

		// Token: 0x0400013E RID: 318
		private Color _TextColor;

		// Token: 0x0200005D RID: 93
		// (Invoke) Token: 0x0600039B RID: 923
		public delegate void CheckedChangedEventHandler(object sender);

		// Token: 0x0200005E RID: 94
		[Flags]
		public enum _Options
		{
			// Token: 0x040001E1 RID: 481
			Style1 = 0,
			// Token: 0x040001E2 RID: 482
			Style2 = 1
		}
	}
}
