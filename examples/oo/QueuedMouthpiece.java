package com.chatley.examples.auction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class QueuedMouthpiece implements AuctionMouthpiece {

	final Collection<Participant> listeners = new ArrayList<Participant>();
	final ExecutorService executor = Executors.newSingleThreadExecutor();
	private Participant currentTurn;

	public void addListener(Participant participant) {
		listeners.add(participant);
	}

	public void onBidAccepted(final int leadingBid) {
		announce(new Announcement() {
			@Override
			void tellEach(Participant participant) {
				if (participant != currentTurn) {
					System.out.println("**** Notifying: " + participant
							+ " of new leading bid: " + leadingBid);
					participant.onBidAccepted(leadingBid);
				}
			}
		});
	}

	private void announce(Announcement announcement) {
		executor.submit(announcement);
	}

	private abstract class Announcement implements Runnable {
		public void run() {
			for (Participant participant : listeners) {
				tellEach(participant);
			}
		}

		abstract void tellEach(Participant participant);
	}

	public void runARound(Auctioneer auctioneer) {
		for (Participant participant : listeners) {
			currentTurn = participant;
			askForBids(auctioneer, participant);
		}
	}

	private void askForBids(final Auctioneer auctioneer,
			final Participant participant) {
		try {
			final Turn turn = new Turn(auctioneer, participant);
			executor.execute(new Runnable() {
				public void run() {
					System.out.println("*** Start of turn: " + participant);
					participant.onYourTurnToBid(turn);
				}
			});
			executor.awaitTermination(250, TimeUnit.MILLISECONDS);
			turn.endOfTurn();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
