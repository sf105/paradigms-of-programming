package com.chatley.examples.auction;

public interface AuctionMouthpiece {
	void addListener(Participant participant);
	void onBidAccepted(int leadingBid);
	void runARound(Auctioneer auctioneer);

}
