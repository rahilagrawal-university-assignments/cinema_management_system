/**
 * @author Rahil Agrawal
 * z5165505@student.unsw.edu.au
 * Assignment 1 - Cinema Booking System
 * Row - Each row is represented as having an array of seats which all share the same row name
 * @invarirant noOfSeats >= 0
 */

import java.util.ArrayList;

public class Row implements Cloneable {
	private String name;
	private int noOfseats;
	private ArrayList<Integer> rowSeats = new ArrayList<Integer>();
	/**
	 * Constructor for Row Class
	 * @param name
	 * @param seats
	 */
	public Row(String name, int seats) {
		this.name = name;
		this.noOfseats = seats;
		for(int i = 0; i< seats; i++) {
			rowSeats.add(i, 0);
		}
	}
	/**
	 * Setter Function for name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Setter function for noOfseats
	 * @param seats
	 */
	public void setNoOfSeats(int seats) {
		this.noOfseats = seats;
	}
	/**
	 * Getter function for noOfseats
	 * @return noOfseats - integer
	 */
	public int getNoOfSeats() {
		return noOfseats;
	}
	/**
	 * Getter function for Name
	 * @return name - String
	 */
	public String getName() {
		return name;
	}
	/**
	 * Getter function for array of seats
	 * @return seats - ArrayList<Integer>
	 */
	public ArrayList<Integer> getSeats(){
		return rowSeats;
	}
	/**
	 * @override the default toString function
	 * @return a string that contains all seats of the row
	 */
	@Override
	public String toString() {
		String row = "";
		int seat = 0;
		for(seat = 0; seat<noOfseats; seat++) {
			row = row + name + String.valueOf(seat+1) + " ";
		}
		return row;
	}
	/**
	 * @override the default clone function
	 * @return a new object of type Row with the same characteristics as current row
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Row clone() {
		//Row newRow = new Row(this.name, this.noOfseats);			
		//return newRow;
		try {
			Row newRow = (Row) super.clone();
			newRow.rowSeats = (ArrayList<Integer>) rowSeats.clone();	
			return newRow;
		}
		catch(CloneNotSupportedException e) {
			System.out.println(e);
			return null;
		}
		
	}
	
}
