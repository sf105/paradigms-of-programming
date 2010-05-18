/**
 * 
 */
package com.chatley.examples.auction;

public class Turn implements BidReceiver {
	private boolean acceptingBids;
	private final Auctioneer auctioneer;
	private final Participant bidder;

	public Turn(Auctioneer auctioneer, Participant bidder) {
		this.auctioneer = auctioneer;
		this.bidder = bidder;
		this.acceptingBids = true;
	}

	public void bid(int price) {
		if (acceptingBids) {
			auctioneer.submitBid(bidder, price);
		} else {
			System.out.println("**** Bidder: " + bidder + " attempted to bid " + price + ", but their turn is over");
		}
	}
	
	public void endOfTurn() {
		acceptingBids = false;
		System.out.println("*** End of turn: " + bidder);
	}
}