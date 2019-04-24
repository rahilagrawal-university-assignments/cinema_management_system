/**
 * @author Rahil Agrawal
 * z5165505@student.unsw.edu.au
 * Assignment 1 - Cinema Booking System
 * Main Class for a Cinema Booking System
 * Takes input from a file
 * Processes input and executes methods of respective objects
 * Writes output to standard output
 * @invariant cinemas.length()>=0 && sessions.length()>=0 && bookings.length()>=0
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class CinemaBookingSystem {
	ArrayList<Cinema> cinemas = new ArrayList<Cinema>();
	ArrayList<Session> sessions = new ArrayList<Session>();
	ArrayList<Booking> bookings = new ArrayList<Booking>();
	
	/**
	 * Main Function for the project
	 * Accepts input from a file and sends line-wise input
	 * to another function
	 * @param args
	 */
	public static void main(String args[]){
		Scanner sc = null;
		CinemaBookingSystem system = new CinemaBookingSystem();
	    
		try
		{
	        sc = new Scanner(new File(args[0])); 
	        while(sc.hasNextLine()){
				system.execute(sc.nextLine(), system);
			}
	        
	    }
	    catch (FileNotFoundException e)
	    {
	        System.out.println(e.getMessage());
	    }
	    finally
	    {
	        if (sc != null) sc.close();
	    }
	}
    /**
     * Function takes in a string input
     * Processes the input and calls appropriate functions
     * to create, change and delete objects for correct functioning
     * Cinema Booking System
     * @param line - Consists a line of input from the input file
     */
    public void execute(String line, CinemaBookingSystem system) {
    	String[] input;
    	//Split the line into an array of strings where each element is one word.
    	input = line.split(" ");
    	// Call function based on the input provided
    	if(input[0].equals("Cinema")) {
        	system.addCinema(input, system);
    	}
    	else if(input[0].equals("Session")) {
    		system.addSession(input, system);
    	}
    	else if(input[0].equals("Request")) {
    		system.addBooking(input, system);
    	}
    	else if(input[0].equals("Cancel")) {
    		system.cancelBooking(input, system);
    	}
    	else if(input[0].equals("Change")) {
    		system.changeBooking(input,system);
    	}
    	else if(input[0].equals("Print")) {
    		system.print(input,system);
    	}
    	else {
    		return;
    	}
    }
    
    /**
     * addCinema - Adds a cinema if it does not already exist, adds a row otherwise.
     * @pre input[0] = "Cinema" and cinams.length() >=0
     * @post cinemas.length() >= 1
     * @param input - Array of strings that has words of one line of input
     * @param system - Object containing lists of Cinemas, Sessions and Bookings.
     */
    public void addCinema(String[] input,CinemaBookingSystem system) {
    	Cinema newCinema = null;
		int id = Integer.valueOf(input[1]);
		boolean cinemaExists = false;
		for(Cinema cinema : system.cinemas) {
			if(cinema.getCinemaId() == id) {
				cinemaExists = true;
				newCinema = cinema;
    			break;
			}
		}
		if(!cinemaExists) {
			Cinema cinema = new Cinema(id);
			newCinema = cinema;
			system.cinemas.add(newCinema);
		}
		newCinema.addRow(input[2], Integer.valueOf(input[3]));
		//System.out.println(newCinema);
    }
    
    /**
     * addSession - Adds a session with certain time and movie to a particular cinema
     * @pre input[0] = "Session" and cinams.length() >=1
     * @post cinemas.length() >= 1
     * @param input - Array of strings that has words of one line of input
     * @param system - Object containing lists of Cinemas, Sessions and Bookings.
     */
    
    public void addSession(String[] input, CinemaBookingSystem system) {
    	Cinema whichCinema = null;
    	// Check if cinema exists
		for(Cinema cinema : system.cinemas) {
			// If cinema is found, copy the reference and exit loop
			if(cinema.getCinemaId() == Integer.valueOf(input[1])) {
				whichCinema = cinema;
				break;
			}
		}
		//Retrieve the name of movie
		String movieName = "";
		int i = 3;
		while(i < input.length && !(input[i].startsWith("#"))) {
			movieName += input[i++] + ((i != input.length) ? " " : "");
		}
		movieName = movieName.trim();
		// If cinema does not exist, display error message and return to caller function
		if(whichCinema == null) {
			return;
		}
		// Create a new session and add it to the list of sessions
		Session newSession = new Session(whichCinema, input[2], movieName);
		system.sessions.add(newSession);
    }
    
    /**
     * addBooking - Adds a booking for a particular session (Time, movie name and cinema) if seats are available,
     * 				Rejects booking otherwise
     * @pre input[0] = "Booking" and cineams.length() > 0 and sessions.length() > 0 && bookings.length() >=0 
     * @post cineams.length() > 0 and sessions.length() > 0 && bookings.length() >=0 
     * @param input - Array of strings that has words of one line of input
     * @param system - Object containing lists of Cinemas, Sessions and Bookings.
     */
    
    public void addBooking(String input[], CinemaBookingSystem system) {
    	Session whichSession = null;
    	int tickets = Integer.valueOf(input[4]);
    	String time = input[3];
    	int bookingId = Integer.valueOf(input[1]);
    	int cinemaId = Integer.valueOf(input[2]);
    	for(Session session : system.sessions) {
    		if(session.getTime().equals(time) && session.getCinema().getCinemaId()==cinemaId) {
    			whichSession = session;
    		}
    	}
    	if(whichSession.bookingPossible(tickets)) {
    		Booking newBooking = new Booking(bookingId, whichSession, tickets);
    		newBooking.setRowName(whichSession.getRowName());
    		newBooking.setStart(whichSession.getStart());
    		newBooking.setEnd();
    		bookings.add(bookingId-1, newBooking);
    		whichSession.bookSeats(newBooking.getRowName(), newBooking.getRowStart(), newBooking.getRowEnd());
    		System.out.println(system.bookings.get(bookingId-1));
    	}
    	else {
    		bookings.add(bookingId-1, null);
    		System.out.println("Booking rejected");
    	}
    }
    
    /**
     * cancelBooking - Cancels a booking if a booking with the given Id exists,
     * 				 - Rejects cancellation and returns to calling function otherwise
     * Set the arrayList element corresponding to given bookingId = null
     * @pre input[0] = "Cancel" and bookings.length() >= 1
     * @post bookings.length() >= 1
     * @param input - Array of strings that has words of one line of input
     * @param system - Object containing lists of Cinemas, Sessions and Bookings.
     */
    
    public void cancelBooking(String input[], CinemaBookingSystem system) {
    	// Get the booking to be cancelled
    	int bookingId = Integer.valueOf(input[1]);
    	Booking whichBooking = system.bookings.get(bookingId-1);
    	// Display error message and exit if the booking does not exist
    	if(whichBooking == null) {
    		System.out.println("Cancel rejected");
    		return;
    	}
    	Session whichSession = null;
    	// Find session of the booking
    	for(Session session : system.sessions) {
    		if(session == whichBooking.getSession()) {
    			whichSession = session;
    		}
    	}
    	// De-assign the seats that were booked
    	whichSession.cancelBooking(whichBooking.getRowName(), whichBooking.getRowStart(), whichBooking.getRowEnd());
    	// Set the arrayList element for booking to null
    	system.bookings.set(bookingId-1, null);
    	System.out.println("Cancel " + bookingId);
    }
    
    /**
     * changeBooking - Assigns new seats to the booking if seats are available
     * 				 - Does not change anything otherwise
     * @pre input[0] = "Change" and bookings.length() >= 1
     * @post bookings.length() >= 1
     * @param input - Array of strings that has words of one line of input
     * @param system - Object containing lists of Cinemas, Sessions and Bookings.
     */
    
	public void changeBooking(String input[], CinemaBookingSystem system) {
		Session whichOldSession = null;
		Session whichNewSession = null;
    	int tickets = Integer.valueOf(input[4]);
    	String time = input[3];
    	int bookingId = Integer.valueOf(input[1]);
    	int cinemaId = Integer.valueOf(input[2]);
    	Booking whichBooking = bookings.get(bookingId-1);
    	// Get objects of old session - Session that the booking already lies in 
    	//				  new session - Session that the booking will now be in (if there are enough seats)
    	for(Session session : system.sessions) {
    		if(session.getTime().equals(time) && session.getCinema().getCinemaId()==cinemaId) {
    			whichNewSession = session;
    		}
    		if(session==whichBooking.getSession()) {
    			whichOldSession = session;
    		}
    	}
    	// Remove booking from the old session
    	whichOldSession.cancelBooking(whichBooking.getRowName(), whichBooking.getRowStart(), whichBooking.getRowEnd());
    	// Create booking in the new session if there are enough seats
    	if(whichNewSession.bookingPossible(tickets)) {
    		Booking newBooking = new Booking(bookingId, whichNewSession, tickets);
    		newBooking.setRowName(whichNewSession.getRowName());
    		newBooking.setStart(whichNewSession.getStart());
    		newBooking.setEnd();
    		system.bookings.set(bookingId-1, newBooking);
    		whichNewSession.bookSeats(newBooking.getRowName(), newBooking.getRowStart(), newBooking.getRowEnd());
    		String changeOutput = "";
    		changeOutput = "Change " + bookingId + " " + newBooking.getRowName();
    		if(tickets == 1)
    			changeOutput = changeOutput + String.valueOf(newBooking.getRowStart()+1);
    		else
    			changeOutput = changeOutput + String.valueOf(newBooking.getRowStart()+1) + "-" + newBooking.getRowName() + String.valueOf(newBooking.getRowEnd()+1);
    		System.out.println(changeOutput);
    	}
    	// Retrieve the old booking if there are not enough seats in the new session
    	else {
    		System.out.println("Change rejected");
    		whichOldSession.bookSeats(whichBooking.getRowName(), whichBooking.getRowStart(), whichBooking.getRowEnd());
    	} 
	}
	public int sortBookings(Booking b1, Booking b2) {
		return (b1.getRowStart() < b2.getRowStart()) ? 1 : 0;
	}
	/**
	 * print - Prints the name of the movie followed by the bookings
	 * 		 - In order of rows as inserted and ascending order of seats
	 * @pre input[0] = "Print" and cineams.length() > 0 and sessions.length() > 0 && bookings.length() >=0 
     * @param input - Array of strings that has words of one line of input
     * @param system - Object containing lists of Cinemas, Sessions and Bookings.
	 */
    public void print(String input[], CinemaBookingSystem system) {
    	int counter = 0;
    	int cinemaId = Integer.valueOf(input[1]);
    	String time = input[2];
    	Cinema whichCinema = null;
    	Session whichSession = null;
    	// Find Cinema that the session belongs to. Used later to get Rows later
    	for(Cinema cinema : system.cinemas) {
    		if(cinema.getCinemaId() == cinemaId) {
    			whichCinema = cinema;
    		}
    	}
    	// Find Session, needed for printing the bookings and movie details
    	for(Session session : system.sessions) {
    		if(session.getCinema().getCinemaId() == cinemaId && session.getTime().equals(time)) {
    			whichSession = session;
    		}
    	}
    	System.out.println(whichSession.getMovie());
    	// For each row, find all bookings that belong to that row
    	// Arrange the bookings in ascending order of starting seat
    	// Print the bookings 
    	// Go to a new line and repeat the procedure for each row
    	for(Row row : whichCinema.getRows()) {
    		String outputString = "";
    		counter = 0;
    		ArrayList<Booking> whichBookings = new ArrayList<Booking>();
        	for(Booking booking : system.bookings) {
        		if(booking == null) continue;
        		if(booking.getSession() == whichSession && booking.getRowName().equals(row.getName())) {
        			whichBookings.add(booking);
        			counter++;    			
        		}
        	}
        	if(whichBookings.size() == 0) continue;
        	whichBookings.sort(Comparator.comparing(Booking::getRowStart));
       		System.out.print(row.getName() + ": ");
        	for(Booking booking : whichBookings) {
        		if(booking == null) continue;
        		outputString = String.valueOf(booking.getRowStart()+1);
        		if(booking.getRowStart() != booking.getRowEnd()) {
        			outputString += "-" + String.valueOf(booking.getRowEnd() +1);
        		}
        		if(counter-- > 1)
        			outputString += ",";
        		System.out.print(outputString);
        	}
        	System.out.print('\n');
    	}
    }
}
