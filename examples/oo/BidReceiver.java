/**
 * 
 */
package com.chatley.examples.auction;

public interface BidReceiver {
	// true: bid accepted
	// false: bid ignored
	
	// was boolean return MCF
	void bid(int price);
}