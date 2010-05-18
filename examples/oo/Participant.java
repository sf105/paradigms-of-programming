package com.chatley.examples.auction;

public interface Participant {
	void onBidAccepted(int price);	
	void onYourTurnToBid(BidReceiver receiver);

	String getName();
}
