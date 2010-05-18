package com.chatley.examples.auction;

public interface Auction {
	public abstract void accept(Participant bidder);
	public abstract void run();
	public abstract void close(Ledger auctionResult);

}