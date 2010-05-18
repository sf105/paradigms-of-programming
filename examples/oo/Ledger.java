package com.chatley.examples.auction;

public interface Ledger {

	void win(Participant bidder, int i);

	void noWinner();

}
