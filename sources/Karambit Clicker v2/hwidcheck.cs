using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Karambit
{
    public partial class hwidcheck : Form
    {
        public hwidcheck()
        {
            InitializeComponent();
        }

        private void accessbtn_Click(object sender, EventArgs e)
        {
			Cursor = DefaultCursor;
			skidderino.hwid.clsComputerInfo clsComputerInfo = new skidderino.hwid.clsComputerInfo();
			string processorId = clsComputerInfo.GetProcessorId();
			string volumeSerial = clsComputerInfo.GetVolumeSerial("C");
			string motherBoardID = clsComputerInfo.GetMotherBoardID();
			string macaddress = clsComputerInfo.GetMACAddress();

			string hwid = macaddress + motherBoardID + volumeSerial + processorId;
			string whitelist = new System.Net.WebClient() { Proxy = null }.DownloadString("https://pastebin.com/Ee4LBtxC");
			if (whitelist.Contains(hwid))
			{
				Form mainform = new mainform();
				this.Hide();
				mainform.ShowDialog();
				this.Close();
			}
			else
			{
				MessageBox.Show("You aren't whitelisted!");
				this.Close();
			}
		}

        private void copyhwidbtn_Click(object sender, EventArgs e)
        {
			Cursor = DefaultCursor;
			skidderino.hwid.clsComputerInfo clsComputerInfo = new skidderino.hwid.clsComputerInfo();
			string processorId = clsComputerInfo.GetProcessorId();
			string volumeSerial = clsComputerInfo.GetVolumeSerial("C");
			string motherBoardID = clsComputerInfo.GetMotherBoardID();
			string macaddress = clsComputerInfo.GetMACAddress();
			string hwid = macaddress + motherBoardID + volumeSerial + processorId;
			Clipboard.SetText(hwid);
		}
    }
}
