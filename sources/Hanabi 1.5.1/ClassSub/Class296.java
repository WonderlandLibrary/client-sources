package ClassSub;

public class Class296
{
    private long id;
    private String name;
    private String artists;
    private String picUrl;
    
    
    public Class296(final String name, final String artists, final long id, final String picUrl) {
        this.id = id;
        this.name = name;
        this.artists = artists;
        this.picUrl = picUrl;
    }
    
    public long getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getArtists() {
        return this.artists;
    }
    
    public String getPicUrl() {
        return this.picUrl;
    }
}
