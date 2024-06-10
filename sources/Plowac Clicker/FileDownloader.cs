using System;
using System.ComponentModel;
using System.IO;
using System.Net;
using System.Threading;

// Token: 0x02000137 RID: 311
internal class FileDownloader
{
	// Token: 0x06000CC4 RID: 3268 RVA: 0x000360A8 File Offset: 0x000342A8
	public FileDownloader(string url, string fullPathWhereToSave)
	{
		if (string.IsNullOrEmpty(url))
		{
			throw new ArgumentNullException("url");
		}
		if (string.IsNullOrEmpty(fullPathWhereToSave))
		{
			throw new ArgumentNullException("fullPathWhereToSave");
		}
		this._url = url;
		this._fullPathWhereToSave = fullPathWhereToSave;
	}

	// Token: 0x06000CC5 RID: 3269 RVA: 0x00036104 File Offset: 0x00034304
	public bool StartDownload(int timeout)
	{
		bool result;
		try
		{
			Directory.CreateDirectory(Path.GetDirectoryName(this._fullPathWhereToSave));
			if (File.Exists(this._fullPathWhereToSave))
			{
				File.Delete(this._fullPathWhereToSave);
			}
			using (WebClient webClient = new WebClient())
			{
				Uri address = new Uri(this._url);
				webClient.DownloadProgressChanged += this.WebClientDownloadProgressChanged;
				webClient.DownloadFileCompleted += this.WebClientDownloadCompleted;
				Console.WriteLine("Downloading file:");
				webClient.DownloadFileAsync(address, this._fullPathWhereToSave);
				this._semaphore.Wait(timeout);
				result = (this._result && File.Exists(this._fullPathWhereToSave));
			}
		}
		catch (Exception value)
		{
			Console.WriteLine("Was not able to download file!");
			Console.Write(value);
			result = false;
		}
		finally
		{
			this._semaphore.Dispose();
		}
		return result;
	}

	// Token: 0x06000CC6 RID: 3270 RVA: 0x00009A21 File Offset: 0x00007C21
	private void WebClientDownloadProgressChanged(object sender, DownloadProgressChangedEventArgs e)
	{
		Console.Write("\r     -->    {0}%.", e.ProgressPercentage);
		this.ProgressPassentage = e.ProgressPercentage;
	}

	// Token: 0x06000CC7 RID: 3271 RVA: 0x00036214 File Offset: 0x00034414
	private void WebClientDownloadCompleted(object sender, AsyncCompletedEventArgs args)
	{
		this.ProgressPassentage = 0;
		this._result = !args.Cancelled;
		if (!this._result)
		{
			Console.Write(args.Error.ToString());
		}
		Console.WriteLine(Environment.NewLine + "Download completato!");
		this._semaphore.Release();
	}

	// Token: 0x06000CC8 RID: 3272 RVA: 0x00009A44 File Offset: 0x00007C44
	public static bool DownloadFile(string url, string fullPathWhereToSave, int timeoutInMilliSec)
	{
		return new FileDownloader(url, fullPathWhereToSave).StartDownload(timeoutInMilliSec);
	}

	// Token: 0x04000991 RID: 2449
	private readonly string _url;

	// Token: 0x04000992 RID: 2450
	private readonly string _fullPathWhereToSave;

	// Token: 0x04000993 RID: 2451
	private bool _result;

	// Token: 0x04000994 RID: 2452
	private readonly SemaphoreSlim _semaphore = new SemaphoreSlim(0);

	// Token: 0x04000995 RID: 2453
	public int ProgressPassentage;
}
