/**
 * @author Rahil Agrawal
 * z5165505@student.unsw.edu.au
 * Assignment 1 - Cinema Booking System
 * Session - Creates movie session for a movie at a particular time in a particular cinema
 * Creates and cancels bookings by assigning and de-assigning seats
 */

import java.util.ArrayList;

public class Session {
	private Cinema cinema;
	private String time;
	private String movie;
	private int start;
	private String rowName;
	private ArrayList<Row> occupiedRows = new ArrayList<Row>();
	/**
	 * Constructor for Session Class
	 * @param cinema
	 * @param time
	 * @param movie
	 */
	public Session(Cinema cinema, String time, String movie) {
		this.cinema = cinema;
		this.time = time;
		this.movie = movie;
		occupiedRows = cinema.getRows();
	}
	/**
	 * Setter function for the name of the row that the booking will be in (if possible)
	 * @param rowName
	 */
	public void setRowName(String rowName) {
		this.rowName = rowName;
	}
	/**
	 * Setter function for the starting seat of a booking (if possible)
	 * @param start
	 */
	public void setStart(int start) {
		this.start = start;
	}
	/**
	 * Getter function for the name of the row that the booking will be in (if possible)
	 * @return rowName - String
	 */
	public String getRowName() {
		return rowName;
	}
	/**
	 * Getter function for the start seat of a booking (if possible)
	 * @return start - int
	 */
	public int getStart() {
		return start;
	}
	/**
	 * Getter function for name of movie
	 * @return movie - String
	 */
	public String getMovie() {
		return movie;
	}
	/**
	 * Getter function for time of movie
	 * @return time - string
	 */
	public String getTime() {
		return time;
	}
	/**
	 * Getter function for cinema that the session belongs to
	 * @return cinema - Cinema
	 */
	public Cinema getCinema() {
		return cinema;
	}
	/**
	 * Finds the number of maximum unoccupied adjacent seats in the session (row by row)
	 * Also gives the name and range of seats by setting the values of variables which can be acquired by using get
	 * @param tickets - number of tickets requested book
	 * @return the number of maximum adjacent seats in the session that are unoccupied
	 */
	public int adjacentFreeSeats(int tickets) {
		int max = 0;
		int adjacentFreeSeats = 0;
		int i = 0;
		int foundTickets = 0;
		for(Row row : occupiedRows) {
			i = 0;
			adjacentFreeSeats = 0;
			ArrayList<Integer> seats = row.getSeats();
			while(i < row.getNoOfSeats()) {
				if (seats.get(i) == 0) adjacentFreeSeats++;
				else if(max < adjacentFreeSeats){
					setRowName(row.getName());
					max = adjacentFreeSeats;
					setStart(i - adjacentFreeSeats);
					// if enough seats are found, do not continue looping
					if(tickets <= adjacentFreeSeats) {
						foundTickets = 1;
						break;
					}
					adjacentFreeSeats = 0;
				}
				i++;
			}
			// To make sure that seats at the end of the row are also considered
			if(tickets <= adjacentFreeSeats) {
				foundTickets = 1;
				setRowName(row.getName());
				setStart(i - adjacentFreeSeats);
				max = adjacentFreeSeats;
			}
			// Exit if tickets are found
			if(foundTickets == 1) {
				break;
			}
		}
		return max;
	}
	/**
	 * Books seats by setting the elements in seats array to 1
	 * @param name - name of the row
	 * @param startSeat - Index of starting seat of booking
	 * @param endSeat - Index of ending seat of booking
	 */
	public void bookSeats(String name, int startSeat, int endSeat) {
		int i = 0;
		for (Row row : occupiedRows) {
			i = 0;
			ArrayList<Integer> seats = row.getSeats();
			while(i < row.getNoOfSeats()) {
				if(i >= startSeat && i<= endSeat && row.getName().equals(name)) {
					seats.set(i, 1);
				}
				i++;
			}
		}
	}
	/**
	 * Checks if a booking is possible or not
	 * @param tickets - no of tickets requested to book
	 * @return true if there are enough adjacent seats for the booking to take place, false otherwise
	 */
	public boolean bookingPossible(int tickets) {
		return (adjacentFreeSeats(tickets) - tickets >= 0) ? true : false;
	}
	/**
	 * cancelBooking - method that cancels a booking by setting the seats of that booking to "unoccupied" (0)
	 * @pre booking with the given row name and seat range exists
	 * @post the given seat range in the given row is set to 0 = unoccupied
	 * @param name
	 * @param startSeat
	 * @param endSeat
	 */
	public void cancelBooking(String name, int startSeat, int endSeat) {
		int i = 0;
		for(Row row : occupiedRows) {
			i=0;
			ArrayList<Integer> seats = row.getSeats();
			while(i < row.getNoOfSeats()) {
				if(i >= startSeat && i<= endSeat && row.getName().equals(name)) {
					seats.set(i, 0);
				}
				i++;
			}
		}
	}
	/**
	 * printSeats - Prints the seat so of each row, one row at a time
	 * This method is not needed for MVP however, might be useful for debugging errors
	 */
	public void printSeats() {
		for(Row row: occupiedRows) {
			System.out.println(row.getName());
			for(int i=0; i< row.getNoOfSeats();i++) {
				System.out.println(row.getSeats().get(i));
			}
		}
	}
	/**
	 * @ovverride Default toString function
	 * @return a string that contains the name of the movie followed by the time of movie and details of the cinema
	 * This method is not needed for MVP however, might be useful for debugging errors
	 */
	public String toString() {
		return movie + '\n' + time + '\n' + cinema.toString();
	}
	
	
}
