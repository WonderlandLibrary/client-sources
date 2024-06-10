namespace Eternium_mcpe_client.Threads.GUI
{
	// Token: 0x0200000B RID: 11
	public partial class Eternium : global::System.Windows.Forms.Form
	{
		// Token: 0x0600002A RID: 42 RVA: 0x00002F30 File Offset: 0x00001130
		protected override void Dispose(bool disposing)
		{
			bool flag = disposing && this.components != null;
			if (flag)
			{
				this.components.Dispose();
			}
			base.Dispose(disposing);
		}

		// Token: 0x0600002B RID: 43 RVA: 0x00002F68 File Offset: 0x00001168
		private void InitializeComponent()
		{
			this.components = new global::System.ComponentModel.Container();
			this.panel1 = new global::System.Windows.Forms.Panel();
			this.Togglebutton = new global::Guna.UI.WinForms.GunaAdvenceButton();
			this.cpsdrop = new global::System.Windows.Forms.CheckBox();
			this.left = new global::System.Windows.Forms.CheckBox();
			this.right = new global::System.Windows.Forms.CheckBox();
			this.Text2 = new global::Guna.UI.WinForms.GunaLabel();
			this.Text1 = new global::Guna.UI.WinForms.GunaLabel();
			this.Trackbar2 = new global::Guna.UI2.WinForms.Guna2TrackBar();
			this.Trackbar1 = new global::Guna.UI2.WinForms.Guna2TrackBar();
			this.Autoclickertext = new global::Guna.UI.WinForms.GunaLabel();
			this.panel4 = new global::System.Windows.Forms.Panel();
			this.Colorpickertextbox = new global::System.Windows.Forms.CheckBox();
			this.Trackbar2green = new global::Guna.UI2.WinForms.Guna2TrackBar();
			this.Trackbar3blue = new global::Guna.UI2.WinForms.Guna2TrackBar();
			this.Trackbar1red = new global::Guna.UI2.WinForms.Guna2TrackBar();
			this.Bluetext = new global::Guna.UI.WinForms.GunaLabel();
			this.Greentext = new global::Guna.UI.WinForms.GunaLabel();
			this.Redtext = new global::Guna.UI.WinForms.GunaLabel();
			this.Colortext = new global::Guna.UI.WinForms.GunaLabel();
			this.panel3 = new global::System.Windows.Forms.Panel();
			this.Vtext = new global::Guna.UI.WinForms.GunaLabel();
			this.Stoptext = new global::Guna.UI.WinForms.GunaLabel();
			this.Starttext = new global::Guna.UI.WinForms.GunaLabel();
			this.information = new global::Guna.UI.WinForms.GunaLabel();
			this.panel2 = new global::System.Windows.Forms.Panel();
			this.gunaAdvenceButton1 = new global::Guna.UI.WinForms.GunaAdvenceButton();
			this.Text3 = new global::Guna.UI.WinForms.GunaLabel();
			this.Trackbar3 = new global::Guna.UI2.WinForms.Guna2TrackBar();
			this.Reachtext = new global::Guna.UI.WinForms.GunaLabel();
			this.Autoclickertimer = new global::System.Windows.Forms.Timer(this.components);
			this.Colortimer = new global::System.Windows.Forms.Timer(this.components);
			this.panel1.SuspendLayout();
			this.panel4.SuspendLayout();
			this.panel3.SuspendLayout();
			this.panel2.SuspendLayout();
			base.SuspendLayout();
			this.panel1.BackColor = global::System.Drawing.Color.FromArgb(33, 32, 40);
			this.panel1.Controls.Add(this.Togglebutton);
			this.panel1.Controls.Add(this.cpsdrop);
			this.panel1.Controls.Add(this.left);
			this.panel1.Controls.Add(this.right);
			this.panel1.Controls.Add(this.Text2);
			this.panel1.Controls.Add(this.Text1);
			this.panel1.Controls.Add(this.Trackbar2);
			this.panel1.Controls.Add(this.Trackbar1);
			this.panel1.Controls.Add(this.Autoclickertext);
			this.panel1.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.panel1.Location = new global::System.Drawing.Point(31, 36);
			this.panel1.Name = "panel1";
			this.panel1.Size = new global::System.Drawing.Size(362, 172);
			this.panel1.TabIndex = 9;
			this.Togglebutton.Animated = true;
			this.Togglebutton.AnimationHoverSpeed = 0.07f;
			this.Togglebutton.AnimationSpeed = 0.03f;
			this.Togglebutton.BaseColor = global::System.Drawing.Color.FromArgb(22, 20, 26);
			this.Togglebutton.BorderColor = global::System.Drawing.Color.FromArgb(22, 20, 26);
			this.Togglebutton.BorderSize = 1;
			this.Togglebutton.ButtonType = 1;
			this.Togglebutton.CheckedBaseColor = global::System.Drawing.Color.FromArgb(22, 20, 26);
			this.Togglebutton.CheckedBorderColor = global::System.Drawing.Color.FromArgb(22, 20, 26);
			this.Togglebutton.CheckedForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Togglebutton.CheckedImage = null;
			this.Togglebutton.CheckedLineColor = global::System.Drawing.Color.FromArgb(22, 20, 26);
			this.Togglebutton.DialogResult = global::System.Windows.Forms.DialogResult.None;
			this.Togglebutton.FocusedColor = global::System.Drawing.Color.Empty;
			this.Togglebutton.Font = new global::System.Drawing.Font("Segoe UI", 9f);
			this.Togglebutton.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Togglebutton.Image = null;
			this.Togglebutton.ImageSize = new global::System.Drawing.Size(20, 20);
			this.Togglebutton.LineColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Togglebutton.Location = new global::System.Drawing.Point(31, 118);
			this.Togglebutton.Name = "Togglebutton";
			this.Togglebutton.OnHoverBaseColor = global::System.Drawing.Color.FromArgb(162, 160, 168);
			this.Togglebutton.OnHoverBorderColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Togglebutton.OnHoverForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Togglebutton.OnHoverImage = null;
			this.Togglebutton.OnHoverLineColor = global::System.Drawing.Color.Empty;
			this.Togglebutton.OnPressedColor = global::System.Drawing.Color.Black;
			this.Togglebutton.Size = new global::System.Drawing.Size(140, 38);
			this.Togglebutton.TabIndex = 53;
			this.Togglebutton.Text = "Toggle ON";
			this.Togglebutton.TextAlign = global::System.Windows.Forms.HorizontalAlignment.Center;
			this.Togglebutton.Click += new global::System.EventHandler(this.Togglebutton_Click);
			this.cpsdrop.AutoSize = true;
			this.cpsdrop.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.cpsdrop.Font = new global::System.Drawing.Font("Segoe UI", 9f);
			this.cpsdrop.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.cpsdrop.Location = new global::System.Drawing.Point(280, 118);
			this.cpsdrop.Name = "cpsdrop";
			this.cpsdrop.Size = new global::System.Drawing.Size(73, 19);
			this.cpsdrop.TabIndex = 41;
			this.cpsdrop.Text = "CPS Drop";
			this.cpsdrop.UseVisualStyleBackColor = true;
			this.left.AutoSize = true;
			this.left.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.left.Font = new global::System.Drawing.Font("Segoe UI", 9f);
			this.left.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.left.Location = new global::System.Drawing.Point(223, 137);
			this.left.Name = "left";
			this.left.Size = new global::System.Drawing.Size(43, 19);
			this.left.TabIndex = 40;
			this.left.Text = "Left";
			this.left.UseVisualStyleBackColor = true;
			this.right.AutoSize = true;
			this.right.BackColor = global::System.Drawing.Color.FromArgb(33, 32, 40);
			this.right.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.right.Font = new global::System.Drawing.Font("Segoe UI", 9f);
			this.right.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.right.Location = new global::System.Drawing.Point(223, 118);
			this.right.Name = "right";
			this.right.Size = new global::System.Drawing.Size(51, 19);
			this.right.TabIndex = 14;
			this.right.Text = "Right";
			this.right.UseMnemonic = false;
			this.right.UseVisualStyleBackColor = false;
			this.Text2.AutoSize = true;
			this.Text2.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Text2.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.Text2.Font = new global::System.Drawing.Font("Segoe UI", 10f);
			this.Text2.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Text2.Location = new global::System.Drawing.Point(343, 74);
			this.Text2.Name = "Text2";
			this.Text2.Size = new global::System.Drawing.Size(17, 19);
			this.Text2.TabIndex = 20;
			this.Text2.Text = "0";
			this.Text1.AutoSize = true;
			this.Text1.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Text1.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.Text1.Font = new global::System.Drawing.Font("Segoe UI", 10f);
			this.Text1.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Text1.Location = new global::System.Drawing.Point(343, 41);
			this.Text1.Name = "Text1";
			this.Text1.Size = new global::System.Drawing.Size(17, 19);
			this.Text1.TabIndex = 19;
			this.Text1.Text = "0";
			this.Trackbar2.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Trackbar2.FillColor = global::System.Drawing.Color.FromArgb(76, 74, 82);
			this.Trackbar2.HoverState.Parent = this.Trackbar2;
			this.Trackbar2.IndicateFocus = false;
			this.Trackbar2.Location = new global::System.Drawing.Point(25, 70);
			this.Trackbar2.Maximum = 20;
			this.Trackbar2.Name = "Trackbar2";
			this.Trackbar2.Size = new global::System.Drawing.Size(312, 23);
			this.Trackbar2.Style = 1;
			this.Trackbar2.TabIndex = 15;
			this.Trackbar2.ThumbColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Trackbar2.Value = 14;
			this.Trackbar2.Scroll += new global::System.Windows.Forms.ScrollEventHandler(this.Trackbar2_Scroll);
			this.Trackbar1.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Trackbar1.FillColor = global::System.Drawing.Color.FromArgb(76, 74, 82);
			this.Trackbar1.HoverState.Parent = this.Trackbar1;
			this.Trackbar1.IndicateFocus = false;
			this.Trackbar1.Location = new global::System.Drawing.Point(25, 41);
			this.Trackbar1.Maximum = 20;
			this.Trackbar1.Name = "Trackbar1";
			this.Trackbar1.Size = new global::System.Drawing.Size(312, 23);
			this.Trackbar1.Style = 1;
			this.Trackbar1.TabIndex = 14;
			this.Trackbar1.ThumbColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Trackbar1.Value = 7;
			this.Trackbar1.Scroll += new global::System.Windows.Forms.ScrollEventHandler(this.Trackbar1_Scroll);
			this.Autoclickertext.AutoSize = true;
			this.Autoclickertext.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Autoclickertext.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.Autoclickertext.Font = new global::System.Drawing.Font("Segoe UI", 9f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.Autoclickertext.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Autoclickertext.Location = new global::System.Drawing.Point(12, 9);
			this.Autoclickertext.Name = "Autoclickertext";
			this.Autoclickertext.Size = new global::System.Drawing.Size(67, 15);
			this.Autoclickertext.TabIndex = 11;
			this.Autoclickertext.Text = "Autoclicker";
			this.panel4.BackColor = global::System.Drawing.Color.FromArgb(33, 32, 40);
			this.panel4.Controls.Add(this.Colorpickertextbox);
			this.panel4.Controls.Add(this.Trackbar2green);
			this.panel4.Controls.Add(this.Trackbar3blue);
			this.panel4.Controls.Add(this.Trackbar1red);
			this.panel4.Controls.Add(this.Bluetext);
			this.panel4.Controls.Add(this.Greentext);
			this.panel4.Controls.Add(this.Redtext);
			this.panel4.Controls.Add(this.Colortext);
			this.panel4.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.panel4.Location = new global::System.Drawing.Point(413, 220);
			this.panel4.Name = "panel4";
			this.panel4.Size = new global::System.Drawing.Size(362, 172);
			this.panel4.TabIndex = 10;
			this.Colorpickertextbox.AutoSize = true;
			this.Colorpickertextbox.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.Colorpickertextbox.Font = new global::System.Drawing.Font("Segoe UI", 9f);
			this.Colorpickertextbox.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Colorpickertextbox.Location = new global::System.Drawing.Point(71, 25);
			this.Colorpickertextbox.Name = "Colorpickertextbox";
			this.Colorpickertextbox.Size = new global::System.Drawing.Size(87, 19);
			this.Colorpickertextbox.TabIndex = 42;
			this.Colorpickertextbox.Text = "Color Picker";
			this.Colorpickertextbox.UseVisualStyleBackColor = true;
			this.Trackbar2green.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Trackbar2green.FillColor = global::System.Drawing.Color.FromArgb(76, 74, 82);
			this.Trackbar2green.HoverState.Parent = this.Trackbar2green;
			this.Trackbar2green.IndicateFocus = false;
			this.Trackbar2green.Location = new global::System.Drawing.Point(71, 98);
			this.Trackbar2green.Maximum = 255;
			this.Trackbar2green.Name = "Trackbar2green";
			this.Trackbar2green.Size = new global::System.Drawing.Size(269, 23);
			this.Trackbar2green.Style = 1;
			this.Trackbar2green.TabIndex = 38;
			this.Trackbar2green.ThumbColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Trackbar2green.Value = 200;
			this.Trackbar3blue.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Trackbar3blue.FillColor = global::System.Drawing.Color.FromArgb(76, 74, 82);
			this.Trackbar3blue.HoverState.Parent = this.Trackbar3blue;
			this.Trackbar3blue.IndicateFocus = false;
			this.Trackbar3blue.Location = new global::System.Drawing.Point(68, 134);
			this.Trackbar3blue.Maximum = 255;
			this.Trackbar3blue.Name = "Trackbar3blue";
			this.Trackbar3blue.Size = new global::System.Drawing.Size(269, 23);
			this.Trackbar3blue.Style = 1;
			this.Trackbar3blue.TabIndex = 37;
			this.Trackbar3blue.ThumbColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Trackbar3blue.Value = 100;
			this.Trackbar1red.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Trackbar1red.FillColor = global::System.Drawing.Color.FromArgb(76, 74, 82);
			this.Trackbar1red.HoverState.Parent = this.Trackbar1red;
			this.Trackbar1red.IndicateFocus = false;
			this.Trackbar1red.Location = new global::System.Drawing.Point(71, 62);
			this.Trackbar1red.Maximum = 255;
			this.Trackbar1red.Name = "Trackbar1red";
			this.Trackbar1red.Size = new global::System.Drawing.Size(269, 23);
			this.Trackbar1red.Style = 1;
			this.Trackbar1red.TabIndex = 30;
			this.Trackbar1red.ThumbColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Bluetext.AutoSize = true;
			this.Bluetext.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Bluetext.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.Bluetext.Font = new global::System.Drawing.Font("Segoe UI", 9f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.Bluetext.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Bluetext.Location = new global::System.Drawing.Point(30, 134);
			this.Bluetext.Name = "Bluetext";
			this.Bluetext.Size = new global::System.Drawing.Size(30, 15);
			this.Bluetext.TabIndex = 35;
			this.Bluetext.Text = "Blue";
			this.Greentext.AutoSize = true;
			this.Greentext.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Greentext.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.Greentext.Font = new global::System.Drawing.Font("Segoe UI", 9f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.Greentext.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Greentext.Location = new global::System.Drawing.Point(22, 98);
			this.Greentext.Name = "Greentext";
			this.Greentext.Size = new global::System.Drawing.Size(38, 15);
			this.Greentext.TabIndex = 34;
			this.Greentext.Text = "Green";
			this.Redtext.AutoSize = true;
			this.Redtext.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Redtext.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.Redtext.Font = new global::System.Drawing.Font("Segoe UI", 9f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.Redtext.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Redtext.Location = new global::System.Drawing.Point(30, 62);
			this.Redtext.Name = "Redtext";
			this.Redtext.Size = new global::System.Drawing.Size(27, 15);
			this.Redtext.TabIndex = 33;
			this.Redtext.Text = "Red";
			this.Colortext.AutoSize = true;
			this.Colortext.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Colortext.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.Colortext.Font = new global::System.Drawing.Font("Segoe UI", 9f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.Colortext.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Colortext.Location = new global::System.Drawing.Point(18, 15);
			this.Colortext.Name = "Colortext";
			this.Colortext.Size = new global::System.Drawing.Size(36, 15);
			this.Colortext.TabIndex = 31;
			this.Colortext.Text = "Color";
			this.panel3.BackColor = global::System.Drawing.Color.FromArgb(33, 32, 40);
			this.panel3.Controls.Add(this.Vtext);
			this.panel3.Controls.Add(this.Stoptext);
			this.panel3.Controls.Add(this.Starttext);
			this.panel3.Controls.Add(this.information);
			this.panel3.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.panel3.Location = new global::System.Drawing.Point(31, 220);
			this.panel3.Name = "panel3";
			this.panel3.Size = new global::System.Drawing.Size(362, 172);
			this.panel3.TabIndex = 11;
			this.Vtext.AutoSize = true;
			this.Vtext.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Vtext.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.Vtext.Font = new global::System.Drawing.Font("Segoe UI", 9f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.Vtext.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Vtext.Location = new global::System.Drawing.Point(6, 120);
			this.Vtext.Name = "Vtext";
			this.Vtext.Size = new global::System.Drawing.Size(349, 15);
			this.Vtext.TabIndex = 17;
			this.Vtext.Text = "V = Self dustruct (only if the program is opened as administrator)";
			this.Stoptext.AutoSize = true;
			this.Stoptext.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Stoptext.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.Stoptext.Font = new global::System.Drawing.Font("Segoe UI", 9f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.Stoptext.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Stoptext.Location = new global::System.Drawing.Point(211, 82);
			this.Stoptext.Name = "Stoptext";
			this.Stoptext.Size = new global::System.Drawing.Size(115, 15);
			this.Stoptext.TabIndex = 16;
			this.Stoptext.Text = "R = Stop Autoclicker";
			this.Starttext.AutoSize = true;
			this.Starttext.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Starttext.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.Starttext.Font = new global::System.Drawing.Font("Segoe UI", 9f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.Starttext.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Starttext.Location = new global::System.Drawing.Point(57, 82);
			this.Starttext.Name = "Starttext";
			this.Starttext.Size = new global::System.Drawing.Size(114, 15);
			this.Starttext.TabIndex = 15;
			this.Starttext.Text = "F = Start Autoclicker";
			this.information.AutoSize = true;
			this.information.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.information.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.information.Font = new global::System.Drawing.Font("Segoe UI", 9f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.information.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.information.Location = new global::System.Drawing.Point(156, 15);
			this.information.Name = "information";
			this.information.Size = new global::System.Drawing.Size(49, 15);
			this.information.TabIndex = 12;
			this.information.Text = "Settings";
			this.panel2.BackColor = global::System.Drawing.Color.FromArgb(33, 32, 40);
			this.panel2.Controls.Add(this.gunaAdvenceButton1);
			this.panel2.Controls.Add(this.Text3);
			this.panel2.Controls.Add(this.Trackbar3);
			this.panel2.Controls.Add(this.Reachtext);
			this.panel2.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.panel2.Location = new global::System.Drawing.Point(413, 36);
			this.panel2.Name = "panel2";
			this.panel2.Size = new global::System.Drawing.Size(362, 172);
			this.panel2.TabIndex = 12;
			this.gunaAdvenceButton1.Animated = true;
			this.gunaAdvenceButton1.AnimationHoverSpeed = 0.07f;
			this.gunaAdvenceButton1.AnimationSpeed = 0.03f;
			this.gunaAdvenceButton1.BaseColor = global::System.Drawing.Color.FromArgb(22, 20, 26);
			this.gunaAdvenceButton1.BorderColor = global::System.Drawing.Color.FromArgb(22, 20, 26);
			this.gunaAdvenceButton1.BorderSize = 1;
			this.gunaAdvenceButton1.ButtonType = 1;
			this.gunaAdvenceButton1.CheckedBaseColor = global::System.Drawing.Color.FromArgb(22, 20, 26);
			this.gunaAdvenceButton1.CheckedBorderColor = global::System.Drawing.Color.FromArgb(22, 20, 26);
			this.gunaAdvenceButton1.CheckedForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.gunaAdvenceButton1.CheckedImage = null;
			this.gunaAdvenceButton1.CheckedLineColor = global::System.Drawing.Color.FromArgb(22, 20, 26);
			this.gunaAdvenceButton1.DialogResult = global::System.Windows.Forms.DialogResult.None;
			this.gunaAdvenceButton1.FocusedColor = global::System.Drawing.Color.Empty;
			this.gunaAdvenceButton1.Font = new global::System.Drawing.Font("Segoe UI", 9f);
			this.gunaAdvenceButton1.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.gunaAdvenceButton1.Image = null;
			this.gunaAdvenceButton1.ImageSize = new global::System.Drawing.Size(20, 20);
			this.gunaAdvenceButton1.LineColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.gunaAdvenceButton1.Location = new global::System.Drawing.Point(119, 118);
			this.gunaAdvenceButton1.Name = "gunaAdvenceButton1";
			this.gunaAdvenceButton1.OnHoverBaseColor = global::System.Drawing.Color.FromArgb(162, 160, 168);
			this.gunaAdvenceButton1.OnHoverBorderColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.gunaAdvenceButton1.OnHoverForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.gunaAdvenceButton1.OnHoverImage = null;
			this.gunaAdvenceButton1.OnHoverLineColor = global::System.Drawing.Color.Empty;
			this.gunaAdvenceButton1.OnPressedColor = global::System.Drawing.Color.Black;
			this.gunaAdvenceButton1.Size = new global::System.Drawing.Size(140, 38);
			this.gunaAdvenceButton1.TabIndex = 54;
			this.gunaAdvenceButton1.Text = "Reach ON";
			this.gunaAdvenceButton1.TextAlign = global::System.Windows.Forms.HorizontalAlignment.Center;
			this.gunaAdvenceButton1.Click += new global::System.EventHandler(this.gunaAdvenceButton1_Click);
			this.Text3.AutoSize = true;
			this.Text3.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.Text3.Font = new global::System.Drawing.Font("Segoe UI", 10f);
			this.Text3.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Text3.Location = new global::System.Drawing.Point(342, 67);
			this.Text3.Name = "Text3";
			this.Text3.Size = new global::System.Drawing.Size(17, 19);
			this.Text3.TabIndex = 22;
			this.Text3.Text = "0";
			this.Trackbar3.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Trackbar3.FillColor = global::System.Drawing.Color.FromArgb(76, 74, 82);
			this.Trackbar3.HoverState.Parent = this.Trackbar3;
			this.Trackbar3.IndicateFocus = false;
			this.Trackbar3.Location = new global::System.Drawing.Point(21, 63);
			this.Trackbar3.Maximum = 9;
			this.Trackbar3.Name = "Trackbar3";
			this.Trackbar3.Size = new global::System.Drawing.Size(312, 30);
			this.Trackbar3.Style = 1;
			this.Trackbar3.TabIndex = 19;
			this.Trackbar3.ThumbColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Trackbar3.Value = 7;
			this.Reachtext.AutoSize = true;
			this.Reachtext.Cursor = global::System.Windows.Forms.Cursors.Arrow;
			this.Reachtext.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.Reachtext.Font = new global::System.Drawing.Font("Segoe UI", 9f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.Reachtext.ForeColor = global::System.Drawing.Color.FromArgb(181, 179, 188);
			this.Reachtext.Location = new global::System.Drawing.Point(18, 9);
			this.Reachtext.Name = "Reachtext";
			this.Reachtext.Size = new global::System.Drawing.Size(39, 15);
			this.Reachtext.TabIndex = 18;
			this.Reachtext.Text = "Reach";
			this.Autoclickertimer.Tick += new global::System.EventHandler(this.Autoclickertimer_Tick);
			this.Colortimer.Tick += new global::System.EventHandler(this.colortimer_Tick);
			base.AutoScaleDimensions = new global::System.Drawing.SizeF(6f, 13f);
			base.AutoScaleMode = global::System.Windows.Forms.AutoScaleMode.Font;
			base.AutoSizeMode = global::System.Windows.Forms.AutoSizeMode.GrowAndShrink;
			this.AutoValidate = global::System.Windows.Forms.AutoValidate.EnablePreventFocusChange;
			this.BackColor = global::System.Drawing.Color.FromArgb(22, 20, 26);
			base.ClientSize = new global::System.Drawing.Size(800, 415);
			base.Controls.Add(this.panel2);
			base.Controls.Add(this.panel3);
			base.Controls.Add(this.panel4);
			base.Controls.Add(this.panel1);
			base.MaximizeBox = false;
			base.Name = "Eternium";
			base.ShowIcon = false;
			base.StartPosition = global::System.Windows.Forms.FormStartPosition.CenterScreen;
			this.Text = "Eternium";
			base.Load += new global::System.EventHandler(this.Eternium_Load);
			this.panel1.ResumeLayout(false);
			this.panel1.PerformLayout();
			this.panel4.ResumeLayout(false);
			this.panel4.PerformLayout();
			this.panel3.ResumeLayout(false);
			this.panel3.PerformLayout();
			this.panel2.ResumeLayout(false);
			this.panel2.PerformLayout();
			base.ResumeLayout(false);
		}

		// Token: 0x0400000A RID: 10
		private global::System.ComponentModel.IContainer components = null;

		// Token: 0x0400000B RID: 11
		private global::System.Windows.Forms.Panel panel1;

		// Token: 0x0400000C RID: 12
		private global::System.Windows.Forms.CheckBox cpsdrop;

		// Token: 0x0400000D RID: 13
		private global::System.Windows.Forms.CheckBox left;

		// Token: 0x0400000E RID: 14
		private global::System.Windows.Forms.CheckBox right;

		// Token: 0x0400000F RID: 15
		private global::Guna.UI.WinForms.GunaLabel Text2;

		// Token: 0x04000010 RID: 16
		private global::Guna.UI.WinForms.GunaLabel Text1;

		// Token: 0x04000011 RID: 17
		private global::Guna.UI2.WinForms.Guna2TrackBar Trackbar2;

		// Token: 0x04000012 RID: 18
		private global::Guna.UI2.WinForms.Guna2TrackBar Trackbar1;

		// Token: 0x04000013 RID: 19
		private global::Guna.UI.WinForms.GunaLabel Autoclickertext;

		// Token: 0x04000014 RID: 20
		private global::System.Windows.Forms.Panel panel4;

		// Token: 0x04000015 RID: 21
		private global::System.Windows.Forms.CheckBox Colorpickertextbox;

		// Token: 0x04000016 RID: 22
		private global::Guna.UI2.WinForms.Guna2TrackBar Trackbar2green;

		// Token: 0x04000017 RID: 23
		private global::Guna.UI2.WinForms.Guna2TrackBar Trackbar3blue;

		// Token: 0x04000018 RID: 24
		private global::Guna.UI2.WinForms.Guna2TrackBar Trackbar1red;

		// Token: 0x04000019 RID: 25
		private global::Guna.UI.WinForms.GunaLabel Bluetext;

		// Token: 0x0400001A RID: 26
		private global::Guna.UI.WinForms.GunaLabel Greentext;

		// Token: 0x0400001B RID: 27
		private global::Guna.UI.WinForms.GunaLabel Redtext;

		// Token: 0x0400001C RID: 28
		private global::Guna.UI.WinForms.GunaLabel Colortext;

		// Token: 0x0400001D RID: 29
		private global::System.Windows.Forms.Panel panel3;

		// Token: 0x0400001E RID: 30
		private global::Guna.UI.WinForms.GunaLabel Stoptext;

		// Token: 0x0400001F RID: 31
		private global::Guna.UI.WinForms.GunaLabel Starttext;

		// Token: 0x04000020 RID: 32
		private global::Guna.UI.WinForms.GunaLabel information;

		// Token: 0x04000021 RID: 33
		private global::System.Windows.Forms.Panel panel2;

		// Token: 0x04000022 RID: 34
		private global::Guna.UI.WinForms.GunaLabel Text3;

		// Token: 0x04000023 RID: 35
		private global::Guna.UI2.WinForms.Guna2TrackBar Trackbar3;

		// Token: 0x04000024 RID: 36
		private global::Guna.UI.WinForms.GunaLabel Reachtext;

		// Token: 0x04000025 RID: 37
		private global::Guna.UI.WinForms.GunaAdvenceButton Togglebutton;

		// Token: 0x04000026 RID: 38
		private global::System.Windows.Forms.Timer Autoclickertimer;

		// Token: 0x04000027 RID: 39
		private global::Guna.UI.WinForms.GunaAdvenceButton gunaAdvenceButton1;

		// Token: 0x04000028 RID: 40
		private global::System.Windows.Forms.Timer Colortimer;

		// Token: 0x04000029 RID: 41
		private global::Guna.UI.WinForms.GunaLabel Vtext;
	}
}
