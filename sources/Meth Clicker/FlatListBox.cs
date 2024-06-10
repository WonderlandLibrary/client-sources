using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;

namespace Meth
{
	// Token: 0x02000028 RID: 40
	internal class FlatListBox : Control
	{
		// Token: 0x170000D4 RID: 212
		// (get) Token: 0x060002E0 RID: 736 RVA: 0x0001447B File Offset: 0x0001267B
		// (set) Token: 0x060002E1 RID: 737 RVA: 0x00014484 File Offset: 0x00012684
		private virtual ListBox ListBx
		{
			[CompilerGenerated]
			get
			{
				return this._ListBx;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				DrawItemEventHandler value2 = new DrawItemEventHandler(this.Drawitem);
				ListBox listBx = this._ListBx;
				if (listBx != null)
				{
					listBx.DrawItem -= value2;
				}
				this._ListBx = value;
				listBx = this._ListBx;
				if (listBx != null)
				{
					listBx.DrawItem += value2;
				}
			}
		}

		// Token: 0x170000D5 RID: 213
		// (get) Token: 0x060002E2 RID: 738 RVA: 0x000144C7 File Offset: 0x000126C7
		// (set) Token: 0x060002E3 RID: 739 RVA: 0x000144CF File Offset: 0x000126CF
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

		// Token: 0x170000D6 RID: 214
		// (get) Token: 0x060002E4 RID: 740 RVA: 0x000144FF File Offset: 0x000126FF
		// (set) Token: 0x060002E5 RID: 741 RVA: 0x00014507 File Offset: 0x00012707
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

		// Token: 0x170000D7 RID: 215
		// (get) Token: 0x060002E6 RID: 742 RVA: 0x00014510 File Offset: 0x00012710
		public string SelectedItem
		{
			get
			{
				return Conversions.ToString(this.ListBx.SelectedItem);
			}
		}

		// Token: 0x170000D8 RID: 216
		// (get) Token: 0x060002E7 RID: 743 RVA: 0x00014522 File Offset: 0x00012722
		public int SelectedIndex
		{
			get
			{
				return this.ListBx.SelectedIndex;
			}
		}

		// Token: 0x060002E8 RID: 744 RVA: 0x0001452F File Offset: 0x0001272F
		public void Clear()
		{
			this.ListBx.Items.Clear();
		}

		// Token: 0x060002E9 RID: 745 RVA: 0x00014544 File Offset: 0x00012744
		public void ClearSelected()
		{
			for (int i = this.ListBx.SelectedItems.Count - 1; i >= 0; i += -1)
			{
				this.ListBx.Items.Remove(RuntimeHelpers.GetObjectValue(this.ListBx.SelectedItems[i]));
			}
		}

		// Token: 0x060002EA RID: 746 RVA: 0x00014594 File Offset: 0x00012794
		public void Drawitem(object sender, DrawItemEventArgs e)
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
					e.Graphics.FillRectangle(new SolidBrush(Color.FromArgb(51, 53, 55)), new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width, e.Bounds.Height));
					e.Graphics.DrawString(" " + this.ListBx.Items[e.Index].ToString(), new Font("Segoe UI", 8f), Brushes.White, (float)e.Bounds.X, (float)(e.Bounds.Y + 2));
				}
				e.Graphics.Dispose();
			}
		}

		// Token: 0x060002EB RID: 747 RVA: 0x00014791 File Offset: 0x00012991
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			if (!base.Controls.Contains(this.ListBx))
			{
				base.Controls.Add(this.ListBx);
			}
		}

		// Token: 0x060002EC RID: 748 RVA: 0x000147BD File Offset: 0x000129BD
		public void AddRange(object[] items)
		{
			this.ListBx.Items.Remove("");
			this.ListBx.Items.AddRange(items);
		}

		// Token: 0x060002ED RID: 749 RVA: 0x000147E5 File Offset: 0x000129E5
		public void AddItem(object item)
		{
			this.ListBx.Items.Remove("");
			this.ListBx.Items.Add(RuntimeHelpers.GetObjectValue(item));
		}

		// Token: 0x060002EE RID: 750 RVA: 0x00014814 File Offset: 0x00012A14
		public FlatListBox()
		{
			this.ListBx = new ListBox();
			this._items = new string[]
			{
				""
			};
			this.BaseColor = Color.FromArgb(45, 47, 49);
			this._SelectedColor = Helpers._FlatColor;
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

		// Token: 0x060002EF RID: 751 RVA: 0x0001493C File Offset: 0x00012B3C
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
			this.ListBx.Size = new Size(base.Width - 6, base.Height - 2);
			g.FillRectangle(new SolidBrush(this.BaseColor), rect);
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x0400017F RID: 383
		private string[] _items;

		// Token: 0x04000180 RID: 384
		private Color BaseColor;

		// Token: 0x04000181 RID: 385
		private Color _SelectedColor;
	}
}
