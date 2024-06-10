using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;

namespace GHOSTBYTES
{
	// Token: 0x02000024 RID: 36
	internal class FlatListBox : Control
	{
		// Token: 0x1700009D RID: 157
		// (get) Token: 0x060001DA RID: 474 RVA: 0x00003A49 File Offset: 0x00001C49
		// (set) Token: 0x060001DB RID: 475 RVA: 0x00009A68 File Offset: 0x00007C68
		    public virtual ListBox ListBx
		{
			[CompilerGenerated]
			get
			{
				return this.ListBx;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				DrawItemEventHandler value2 = new DrawItemEventHandler(this.Drawitem);
				ListBox listBx = this.ListBx;
				if (listBx != null)
				{
					listBx.DrawItem -= value2;
				}
				this.ListBx = value;
				listBx = this.ListBx;
				if (listBx != null)
				{
					listBx.DrawItem += value2;
				}
			}
		}

		// Token: 0x1700009E RID: 158
		// (get) Token: 0x060001DC RID: 476 RVA: 0x00003A51 File Offset: 0x00001C51
		// (set) Token: 0x060001DD RID: 477 RVA: 0x00003A59 File Offset: 0x00001C59
		[Category("Options")]
		public string[] items
		{
			get
			{
				return this._items;
			}
			set
			{
				this._items = value;
				this.ListBx.Items.Clear();
				this.ListBx.Items.AddRange(value);
				base.Invalidate();
			}
		}

		// Token: 0x1700009F RID: 159
		// (get) Token: 0x060001DE RID: 478 RVA: 0x00003A89 File Offset: 0x00001C89
		// (set) Token: 0x060001DF RID: 479 RVA: 0x00003A91 File Offset: 0x00001C91
		[Category("Colors")]
		public Color SelectedColor
		{
			get
			{
				return this._SelectedColor;
			}
			set
			{
				this._SelectedColor = value;
			}
		}

		// Token: 0x170000A0 RID: 160
		// (get) Token: 0x060001E0 RID: 480 RVA: 0x00003A9A File Offset: 0x00001C9A
		public string SelectedItem
		{
			get
			{
				return Conversions.ToString(this.ListBx.SelectedItem);
			}
		}

		// Token: 0x170000A1 RID: 161
		// (get) Token: 0x060001E1 RID: 481 RVA: 0x00003AAC File Offset: 0x00001CAC
		public int SelectedIndex
		{
			get
			{
				return this.ListBx.SelectedIndex;
			}
		}

		// Token: 0x060001E2 RID: 482 RVA: 0x00009AAC File Offset: 0x00007CAC
		public void Drawitem(object sender, DrawItemEventArgs e)
		{
			checked
			{
				if (e.Index >= 0)
				{
					e.DrawBackground();
					e.DrawFocusRectangle();
					e.Graphics.SmoothingMode = SmoothingMode.HighQuality;
					e.Graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
					e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
					e.Graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
					if (Strings.InStr(e.State.ToString(), "Selected,", CompareMethod.Binary) > 0)
					{
						e.Graphics.FillRectangle(new SolidBrush(this._SelectedColor), new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width, e.Bounds.Height));
						e.Graphics.DrawString(" " + this.ListBx.Items[e.Index].ToString(), new Font("Segoe UI", 8f), Brushes.White, (float)e.Bounds.X, (float)(e.Bounds.Y + 2));
					}
					else
					{
						e.Graphics.FillRectangle(new SolidBrush(Color.FromArgb(60, 60, 60)), new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width, e.Bounds.Height));
						e.Graphics.DrawString(" " + this.ListBx.Items[e.Index].ToString(), new Font("Segoe UI", 8f), Brushes.White, (float)e.Bounds.X, (float)(e.Bounds.Y + 2));
					}
					e.Graphics.Dispose();
				}
			}
		}

		// Token: 0x060001E3 RID: 483 RVA: 0x00003AB9 File Offset: 0x00001CB9
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			if (!base.Controls.Contains(this.ListBx))
			{
				base.Controls.Add(this.ListBx);
			}
		}

		// Token: 0x060001E4 RID: 484 RVA: 0x00003AE5 File Offset: 0x00001CE5
		public void AddRange(object[] items)
		{
			this.ListBx.Items.Remove("");
			this.ListBx.Items.AddRange(items);
		}

		// Token: 0x060001E5 RID: 485 RVA: 0x00003B0D File Offset: 0x00001D0D
		public void AddItem(object item)
		{
			this.ListBx.Items.Remove("");
			this.ListBx.Items.Add(RuntimeHelpers.GetObjectValue(item));
		}

		// Token: 0x060001E6 RID: 486 RVA: 0x00009CAC File Offset: 0x00007EAC
		public FlatListBox()
		{
			this.ListBx = new ListBox();
			this._items = new string[]
			{
				""
			};
			this.BaseColor = Color.FromArgb(60, 60, 60);
			this._SelectedColor = Color.FromArgb(0, 170, 220);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.ListBx.DrawMode = DrawMode.OwnerDrawFixed;
			this.ListBx.ScrollAlwaysVisible = false;
			this.ListBx.HorizontalScrollbar = false;
			this.ListBx.BorderStyle = BorderStyle.None;
			this.ListBx.BackColor = this.BaseColor;
			this.ListBx.ForeColor = Color.White;
			this.ListBx.Location = new Point(3, 3);
			this.ListBx.Font = new Font("Segoe UI", 8f);
			this.ListBx.ItemHeight = 20;
			this.ListBx.Items.Clear();
			this.ListBx.IntegralHeight = false;
			base.Size = new Size(131, 101);
			this.BackColor = this.BaseColor;
		}

		// Token: 0x060001E7 RID: 487 RVA: 0x00009DDC File Offset: 0x00007FDC
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			Rectangle rect = new Rectangle(0, 0, base.Width, base.Height);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			this.ListBx.Size = checked(new Size(base.Width - 6, base.Height - 2));
			g.FillRectangle(new SolidBrush(this.BaseColor), rect);
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x040000AD RID: 173
		private string[] _items;

		// Token: 0x040000AE RID: 174
		private Color BaseColor;

		// Token: 0x040000AF RID: 175
		private Color _SelectedColor;
	}
}
