package com.chatley.examples.auction;


public class EnglishAuction implements Auction, Auctioneer {
	private int reservePrice;
	private int leadingBid = 0;
	private Participant leadingBidder;

	private final AuctionMouthpiece mouthpiece;

	public EnglishAuction(AuctionMouthpiece mouthpiece) {
		this.mouthpiece = mouthpiece;
	}
	
	public EnglishAuction() {
		this(new QueuedMouthpiece());
	}
	
	public EnglishAuction withReservePrice(int reservePrice) {
		this.reservePrice = reservePrice;
		return this;
	}

	public void accept(Participant participant) {
		mouthpiece.addListener(participant);
	}

	public void run() {
		System.out.println("\n* START of auction *\n");
		int roundCount = 1;
		int leadingBidAtStartOfRound;
		do {
			System.out.println("\n** START Round " + roundCount);
			leadingBidAtStartOfRound = leadingBid;
			mouthpiece.runARound(this);
			System.out.println("** END Round " + roundCount + ". Current leader: " + leadingBidder + ", with bid " + leadingBid);
			roundCount++;
		} while (leadingBid != leadingBidAtStartOfRound);
	}

	public void close(Ledger result) {
		if (leadingBid > reservePrice) {
			System.out.println("\n* END of auction: auction won by " + leadingBidder + ", with bid " + leadingBid);
			result.win(leadingBidder, leadingBid);
		} else {
			System.out.println("\n* END of auction: no winner: Reserve price of " + reservePrice + " not met\n---------------------------------------------------\n\n");
			result.noWinner();
		}
	}

	public synchronized void submitBid(Participant bidder, int bid) {
		if (bid > leadingBid) {
			System.out.println("**** Accepting new bid: " + bid + ", from " + bidder);
			updateLeadingBid(bidder, bid);
			notifyAllBidders();
			return;
		}
		System.out.println("**** Bid: " + bid + ", from " + bidder + ", does not beat current leading bid: " + leadingBid);
	}

	private void notifyAllBidders() {
		mouthpiece.onBidAccepted(leadingBid);
	}

	private synchronized void updateLeadingBid(Participant bidder, int bid) {
		leadingBidder = bidder;
		leadingBid = bid;
	}

}
