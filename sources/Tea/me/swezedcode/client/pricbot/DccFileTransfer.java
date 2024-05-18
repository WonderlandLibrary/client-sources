package me.swezedcode.client.pricbot;

import java.io.*;
import java.net.*;

public class DccFileTransfer {
	public static final int BUFFER_SIZE = 1024;
	private PircBot _bot;
	private DccManager _manager;
	private String _nick;
	private String _login;
	private String _hostname;
	private String _type;
	private long _address;
	private int _port;
	private long _size;
	private boolean _received;
	private Socket _socket;
	private long _progress;
	private File _file;
	private int _timeout;
	private boolean _incoming;
	private long _packetDelay;
	private long _startTime;

	DccFileTransfer(final PircBot bot, final DccManager manager, final String nick, final String login,
			final String hostname, final String type, final String filename, final long address, final int port,
			final long size) {
		this._login = null;
		this._hostname = null;
		this._socket = null;
		this._progress = 0L;
		this._file = null;
		this._timeout = 0;
		this._packetDelay = 0L;
		this._startTime = 0L;
		this._bot = bot;
		this._manager = manager;
		this._nick = nick;
		this._login = login;
		this._hostname = hostname;
		this._type = type;
		this._file = new File(filename);
		this._address = address;
		this._port = port;
		this._size = size;
		this._received = false;
		this._incoming = true;
	}

	DccFileTransfer(final PircBot bot, final DccManager manager, final File file, final String nick,
			final int timeout) {
		this._login = null;
		this._hostname = null;
		this._socket = null;
		this._progress = 0L;
		this._file = null;
		this._timeout = 0;
		this._packetDelay = 0L;
		this._startTime = 0L;
		this._bot = bot;
		this._manager = manager;
		this._nick = nick;
		this._file = file;
		this._size = file.length();
		this._timeout = timeout;
		this._received = true;
		this._incoming = false;
	}

	public synchronized void receive(final File file, final boolean resume) {
		if (!this._received) {
			this._received = true;
			this._file = file;
			if (this._type.equals("SEND") && resume) {
				this._progress = file.length();
				if (this._progress == 0L) {
					this.doReceive(file, false);
				} else {
					this._bot.sendCTCPCommand(this._nick, "DCC RESUME file.ext " + this._port + " " + this._progress);
					this._manager.addAwaitingResume(this);
				}
			} else {
				this._progress = file.length();
				this.doReceive(file, resume);
			}
		}
	}

	void doReceive(final File file, final boolean resume) {
		new Thread() {
			@Override
			public void run() {
				BufferedOutputStream foutput = null;
				Exception exception = null;
				try {
					final int[] ip = DccFileTransfer.this._bot.longToIp(DccFileTransfer.this._address);
					final String ipStr = String.valueOf(ip[0]) + "." + ip[1] + "." + ip[2] + "." + ip[3];
					DccFileTransfer.access$4(DccFileTransfer.this, new Socket(ipStr, DccFileTransfer.this._port));
					DccFileTransfer.this._socket.setSoTimeout(30000);
					DccFileTransfer.access$5(DccFileTransfer.this, System.currentTimeMillis());
					DccFileTransfer.this._manager.removeAwaitingResume(DccFileTransfer.this);
					final BufferedInputStream input = new BufferedInputStream(
							DccFileTransfer.this._socket.getInputStream());
					final BufferedOutputStream output = new BufferedOutputStream(
							DccFileTransfer.this._socket.getOutputStream());
					foutput = new BufferedOutputStream(new FileOutputStream(file.getCanonicalPath(), resume));
					final byte[] inBuffer = new byte[1024];
					final byte[] outBuffer = new byte[4];
					int bytesRead = 0;
					while ((bytesRead = input.read(inBuffer, 0, inBuffer.length)) != -1) {
						foutput.write(inBuffer, 0, bytesRead);
						final DccFileTransfer this$0 = DccFileTransfer.this;
						DccFileTransfer.access$8(this$0, this$0._progress + bytesRead);
						outBuffer[0] = (byte) (DccFileTransfer.this._progress >> 24 & 0xFFL);
						outBuffer[1] = (byte) (DccFileTransfer.this._progress >> 16 & 0xFFL);
						outBuffer[2] = (byte) (DccFileTransfer.this._progress >> 8 & 0xFFL);
						outBuffer[3] = (byte) (DccFileTransfer.this._progress >> 0 & 0xFFL);
						output.write(outBuffer);
						output.flush();
						DccFileTransfer.this.delay();
					}
					foutput.flush();
				} catch (Exception e) {
					exception = e;
				} finally {
					try {
						foutput.close();
						DccFileTransfer.this._socket.close();
					} catch (Exception ex) {
					}
				}
				try {
					foutput.close();
					DccFileTransfer.this._socket.close();
				} catch (Exception ex2) {
				}
				DccFileTransfer.this._bot.onFileTransferFinished(DccFileTransfer.this, exception);
			}
		}.start();
	}

	void doSend(final boolean allowResume) {
		new Thread() {
			@Override
			public void run() {
				BufferedInputStream finput = null;
				Exception exception = null;
				try {
					ServerSocket ss = null;
					final int[] ports = DccFileTransfer.this._bot.getDccPorts();
					if (ports == null) {
						ss = new ServerSocket(0);
					} else {
						int i = 0;
						while (i < ports.length) {
							try {
								ss = new ServerSocket(ports[i]);
								break;
							} catch (Exception ex) {
								++i;
							}
						}
						if (ss == null) {
							throw new IOException("All ports returned by getDccPorts() are in use.");
						}
					}
					ss.setSoTimeout(DccFileTransfer.this._timeout);
					DccFileTransfer.access$11(DccFileTransfer.this, ss.getLocalPort());
					InetAddress inetAddress = DccFileTransfer.this._bot.getDccInetAddress();
					if (inetAddress == null) {
						inetAddress = DccFileTransfer.this._bot.getInetAddress();
					}
					final byte[] ip = inetAddress.getAddress();
					final long ipNum = DccFileTransfer.this._bot.ipToLong(ip);
					String safeFilename = DccFileTransfer.this._file.getName().replace(' ', '_');
					safeFilename = safeFilename.replace('\t', '_');
					if (allowResume) {
						DccFileTransfer.this._manager.addAwaitingResume(DccFileTransfer.this);
					}
					DccFileTransfer.this._bot.sendCTCPCommand(DccFileTransfer.this._nick,
							"DCC SEND " + safeFilename + " " + ipNum + " " + DccFileTransfer.this._port + " "
									+ DccFileTransfer.this._file.length());
					DccFileTransfer.access$4(DccFileTransfer.this, ss.accept());
					DccFileTransfer.this._socket.setSoTimeout(30000);
					DccFileTransfer.access$5(DccFileTransfer.this, System.currentTimeMillis());
					if (allowResume) {
						DccFileTransfer.this._manager.removeAwaitingResume(DccFileTransfer.this);
					}
					ss.close();
					final BufferedOutputStream output = new BufferedOutputStream(
							DccFileTransfer.this._socket.getOutputStream());
					final BufferedInputStream input = new BufferedInputStream(
							DccFileTransfer.this._socket.getInputStream());
					finput = new BufferedInputStream(new FileInputStream(DccFileTransfer.this._file));
					if (DccFileTransfer.this._progress > 0L) {
						for (long bytesSkipped = 0L; bytesSkipped < DccFileTransfer.this._progress; bytesSkipped += finput
								.skip(DccFileTransfer.this._progress - bytesSkipped)) {
						}
					}
					final byte[] outBuffer = new byte[1024];
					final byte[] inBuffer = new byte[4];
					int bytesRead = 0;
					while ((bytesRead = finput.read(outBuffer, 0, outBuffer.length)) != -1) {
						output.write(outBuffer, 0, bytesRead);
						output.flush();
						input.read(inBuffer, 0, inBuffer.length);
						final DccFileTransfer this$0 = DccFileTransfer.this;
						DccFileTransfer.access$8(this$0, this$0._progress + bytesRead);
						DccFileTransfer.this.delay();
					}
				} catch (Exception e) {
					exception = e;
				} finally {
					try {
						finput.close();
						DccFileTransfer.this._socket.close();
					} catch (Exception ex2) {
					}
				}
				try {
					finput.close();
					DccFileTransfer.this._socket.close();
				} catch (Exception ex3) {
				}
				DccFileTransfer.this._bot.onFileTransferFinished(DccFileTransfer.this, exception);
			}
		}.start();
	}

	void setProgress(final long progress) {
		this._progress = progress;
	}

	private void delay() {
		if (this._packetDelay > 0L) {
			try {
				Thread.sleep(this._packetDelay);
			} catch (InterruptedException ex) {
			}
		}
	}

	public String getNick() {
		return this._nick;
	}

	public String getLogin() {
		return this._login;
	}

	public String getHostname() {
		return this._hostname;
	}

	public File getFile() {
		return this._file;
	}

	public int getPort() {
		return this._port;
	}

	public boolean isIncoming() {
		return this._incoming;
	}

	public boolean isOutgoing() {
		return !this.isIncoming();
	}

	public void setPacketDelay(final long millis) {
		this._packetDelay = millis;
	}

	public long getPacketDelay() {
		return this._packetDelay;
	}

	public long getSize() {
		return this._size;
	}

	public long getProgress() {
		return this._progress;
	}

	public double getProgressPercentage() {
		return 100.0 * (this.getProgress() / this.getSize());
	}

	public void close() {
		try {
			this._socket.close();
		} catch (Exception ex) {
		}
	}

	public long getTransferRate() {
		final long time = (System.currentTimeMillis() - this._startTime) / 1000L;
		if (time <= 0L) {
			return 0L;
		}
		return this.getProgress() / time;
	}

	public long getNumericalAddress() {
		return this._address;
	}

	static /* synthetic */ void access$4(final DccFileTransfer dccFileTransfer, final Socket socket) {
		dccFileTransfer._socket = socket;
	}

	static /* synthetic */ void access$5(final DccFileTransfer dccFileTransfer, final long startTime) {
		dccFileTransfer._startTime = startTime;
	}

	static /* synthetic */ void access$8(final DccFileTransfer dccFileTransfer, final long progress) {
		dccFileTransfer._progress = progress;
	}

	static /* synthetic */ void access$11(final DccFileTransfer dccFileTransfer, final int port) {
		dccFileTransfer._port = port;
	}
}
