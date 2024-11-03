package net.silentclient.client.utils.types;

public class NewsResponse {
    public NewsItem news;

    public NewsItem getItem() {
        return news;
    }

    public class NewsItem {
        public int id;
        public String cover;

        public String getCover() {
            return "https://cdn.silentclient.net/assets" + cover;
        }
    }

}
