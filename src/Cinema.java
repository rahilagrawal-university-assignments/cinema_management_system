/**
 * @author Rahil Agrawal
 * z5165505@student.unsw.edu.au
 * Assignment 1 - Cinema Booking System
 * Cinema - Creates Cinemas that can hold movies shows in various times
 * Each cinema has few rows of seats
 * @invarirant id >= 1
 */

import java.util.ArrayList;

public class Cinema {
	private int id;
	private ArrayList<Row> rows = new ArrayList<Row>();
	/**
	 * Constructor for Cinema
	 * @param id
	 */
	public Cinema(int id) {
		this.id = id;
	}
	/**
	 * Getter function for cinemaId
	 * @return id - int
	 */
	public int getCinemaId() {
		return id;
	}
	/**
	 * addRow - Adds a row with given name and number of seats
	 * @param name - Name of the row 
	 * @param seats - Number of seats in the row
	 */
	public void addRow(String name, int seats) {
			Row row = new Row(name, seats);
			rows.add(row);
	}
	/**
	 * getRows - Returns the list of rows
	 * @return a copy of the list of rows where each object is created using clone - ArrayList<Row>
	 */
	public ArrayList<Row> getRows(){
		ArrayList<Row> newRows = new ArrayList<Row>();
		for(Row row :rows) {
			newRows.add(row.clone());
		}
		return newRows;
	}
	/**
	 * toString - Overrides the default toString function
	 * 			- Returns a string with CinemaId and rows
	 * @Override toString()
	 */
	@Override
	public String toString() {
		String cinema = "Cinema " + String.valueOf(id) + "\n";
		for(Row row : rows) {
			cinema = cinema + row.toString();
		}
		return cinema;
	}
}
