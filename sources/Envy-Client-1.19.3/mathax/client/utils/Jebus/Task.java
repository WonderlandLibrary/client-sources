package mathax.client.utils.Jebus;

public class Task {
    private boolean called;

    public Task() {
        this.called = false;
    }

    public void run(Runnable task) {
        if (!this.called) {
            task.run();
            this.called = true;
        }
    }

    public boolean isCalled() {
        return this.called;
    }

    public void reset() {
        this.called = false;
    }
}
