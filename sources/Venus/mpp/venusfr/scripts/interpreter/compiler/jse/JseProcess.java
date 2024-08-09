/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JseProcess {
    final Process process;
    final Thread input;
    final Thread output;
    final Thread error;

    public JseProcess(String[] stringArray, InputStream inputStream, OutputStream outputStream, OutputStream outputStream2) throws IOException {
        this(Runtime.getRuntime().exec(stringArray), inputStream, outputStream, outputStream2);
    }

    public JseProcess(String string, InputStream inputStream, OutputStream outputStream, OutputStream outputStream2) throws IOException {
        this(Runtime.getRuntime().exec(string), inputStream, outputStream, outputStream2);
    }

    private JseProcess(Process process, InputStream inputStream, OutputStream outputStream, OutputStream outputStream2) {
        this.process = process;
        this.input = inputStream == null ? null : this.copyBytes(inputStream, process.getOutputStream(), null, process.getOutputStream());
        this.output = outputStream == null ? null : this.copyBytes(process.getInputStream(), outputStream, process.getInputStream(), null);
        this.error = outputStream2 == null ? null : this.copyBytes(process.getErrorStream(), outputStream2, process.getErrorStream(), null);
    }

    public int exitValue() {
        return this.process.exitValue();
    }

    public int waitFor() throws InterruptedException {
        int n = this.process.waitFor();
        if (this.input != null) {
            this.input.join();
        }
        if (this.output != null) {
            this.output.join();
        }
        if (this.error != null) {
            this.error.join();
        }
        this.process.destroy();
        return n;
    }

    private Thread copyBytes(InputStream inputStream, OutputStream outputStream, InputStream inputStream2, OutputStream outputStream2) {
        CopyThread copyThread = new CopyThread(outputStream, outputStream2, inputStream2, inputStream);
        copyThread.start();
        return copyThread;
    }

    private static final class CopyThread
    extends Thread {
        private final OutputStream output;
        private final OutputStream ownedOutput;
        private final InputStream ownedInput;
        private final InputStream input;

        private CopyThread(OutputStream outputStream, OutputStream outputStream2, InputStream inputStream, InputStream inputStream2) {
            this.output = outputStream;
            this.ownedOutput = outputStream2;
            this.ownedInput = inputStream;
            this.input = inputStream2;
        }

        @Override
        public void run() {
            try {
                byte[] byArray = new byte[1024];
                try {
                    int n;
                    while ((n = this.input.read(byArray)) >= 0) {
                        this.output.write(byArray, 0, n);
                    }
                } finally {
                    if (this.ownedInput != null) {
                        this.ownedInput.close();
                    }
                    if (this.ownedOutput != null) {
                        this.ownedOutput.close();
                    }
                }
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
    }
}

