/**
 * @author Rahil Agrawal
 * z5165505@student.unsw.edu.au
 * Assignment 1 - Cinema Booking System
 * Booking - Creates a booking object to keep track of details of a booking such as cinema, time and information about seats
 * @invariant cinema >= 1 
 */

public class Booking {
	private Session session;
	private int tickets;
	private int id;
	private String rowName;
	private int rowStart;
	private int rowEnd;
	/**
	 * Constructor for Booking
	 * @param id
	 * @param cinema
	 * @param time
	 * @param tickets
	 */
	public Booking(int id,Session session, int tickets) {
		this.session = session;
		this.id = id;
		this.tickets = tickets;
	}
	/**
	 * Getter function for Session that the booking belongs to
	 * @return session - Session
	 */
	public Session getSession() {
		return session;
	}
	/**
	 * Setter function for session that the booking belongs to
	 * @param session
	 */
	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * Getter method for tickets
	 * @return tickets - Number of tickets of the booking
	 */
	public int getTickets() {
		return tickets;
	}
	/**
	 * Getter method for Id
	 * @return id - Booking Id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Setter Method for Tickets
	 * @param tickets = No of tickets given
	 */
	public void setTickets(int tickets) {
		this.tickets = tickets;
	}
	/**
	 * Setter method for booking Id
	 * @param id = Booking id provided
	 */
	public void setId(int id) {
		this.id = id;
	}
	public void setRowName(String rowName) {
		this.rowName = rowName;
	}
	/**
	 * Setter method for rowStart
	 * @param rowStart = index of the starting seat of the booking
	 */
	public void setStart(int rowStart) {
		this.rowStart = rowStart;
	}
	/**
	 * Setter Method for rowEnd
	 * rowEnd = index of the ending seat = index of starting seat + no of tickets - 1
	 * (Both the starting and ending seat are included in the booking so need to subtract 1)
	 */
	public void setEnd() {
		rowEnd = getRowStart() + getTickets() - 1;
	}
	/**
	 * Getter method for rowName
	 * @return the name of the row
	 */
	public String getRowName() {
		return rowName;
	}
	/**
	 * Getter method for rowStart
	 * @return index of the first seat
	 */
	public int getRowStart() {
		return rowStart;
	}
	/**
	 * Getter method for rowEnd
	 * @return index of the last seat
	 */
	public int getRowEnd() {
		return rowEnd;
	}
	/**
	 * @override the default toString function
	 * @return string that contains Booking Id followed by row name and range of seats sepearted by a '-'
	 */
	@Override
	public String toString() {
		String returnString = "Booking " + id + " " + rowName + String.valueOf(rowStart+1);
		if(tickets != 1)
			returnString = returnString  +  "-" + rowName + String.valueOf(rowEnd+1); 
		else
			returnString = returnString + "";
		return returnString;
	}
}
