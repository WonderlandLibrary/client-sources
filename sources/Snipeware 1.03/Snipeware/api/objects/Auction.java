package Snipeware.api.objects;

import java.util.List;

public class Auction{
    public String uuid;
    public String auctioneer;
    public String profile_id;
    public List<String> coop;
    public Object start;
    public Object end;
    public String item_name;
    public String item_lore;
    public String extra;
    public String category;
    public String tier;
    public int starting_bid;
    public String item_bytes;
    public boolean claimed;
    public List<Object> claimed_bidders;
    public int highest_bid_amount;
    public boolean bin;
    public List<Bid> bids;
}