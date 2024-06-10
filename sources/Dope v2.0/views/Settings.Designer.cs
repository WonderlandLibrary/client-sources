
namespace Dope.views
{
    partial class Settings
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.switch1 = new Dope.Theme.Switch();
            this.bind1 = new Dope.Theme.Bind();
            this.checkbox1 = new Dope.Theme.Checkbox();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Century Gothic", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(12, 12);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(81, 23);
            this.label1.TabIndex = 0;
            this.label1.Text = "Settings";
            // 
            // switch1
            // 
            this.switch1.Checked = false;
            this.switch1.Location = new System.Drawing.Point(12, 57);
            this.switch1.Name = "switch1";
            this.switch1.Size = new System.Drawing.Size(122, 33);
            this.switch1.TabIndex = 1;
            this.switch1.Text = "Hide";
            this.switch1.Click += new System.EventHandler(this.switch1_Click);
            // 
            // bind1
            // 
            this.bind1.AutoSize = true;
            this.bind1.Key = 0;
            this.bind1.Location = new System.Drawing.Point(140, 63);
            this.bind1.Name = "bind1";
            this.bind1.Size = new System.Drawing.Size(55, 21);
            this.bind1.TabIndex = 2;
            this.bind1.Text = "[Bind]";
            this.bind1.TextChanged += new System.EventHandler(this.bind1_TextChanged);
            // 
            // checkbox1
            // 
            this.checkbox1.Checked = false;
            this.checkbox1.Location = new System.Drawing.Point(16, 96);
            this.checkbox1.Name = "checkbox1";
            this.checkbox1.Size = new System.Drawing.Size(149, 23);
            this.checkbox1.TabIndex = 3;
            this.checkbox1.Text = "Always on Top";
            this.checkbox1.Click += new System.EventHandler(this.checkbox1_Click);
            // 
            // Settings
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(20)))), ((int)(((byte)(20)))), ((int)(((byte)(20)))));
            this.ClientSize = new System.Drawing.Size(464, 411);
            this.Controls.Add(this.checkbox1);
            this.Controls.Add(this.bind1);
            this.Controls.Add(this.switch1);
            this.Controls.Add(this.label1);
            this.Font = new System.Drawing.Font("Century Gothic", 12F);
            this.ForeColor = System.Drawing.Color.Gainsboro;
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "Settings";
            this.Text = "Settings";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private Theme.Switch switch1;
        private Theme.Bind bind1;
        private Theme.Checkbox checkbox1;
    }
}