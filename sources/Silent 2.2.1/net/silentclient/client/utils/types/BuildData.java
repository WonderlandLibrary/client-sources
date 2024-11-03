package net.silentclient.client.utils.types;

public class BuildData {
    public String commit;
    public String branch;

    public BuildData() {
        this.branch = "unknown";
        this.commit = "unknown";
    }

    public String getBranch() {
        return branch;
    }

    public String getCommit() {
        return commit;
    }
}
