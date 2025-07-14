package com.example.demo;

import java.util.ArrayList;

/**
 * Class to represent a Sorting Office object.
 * Stores and manages various attributes for sorting offices.
 */
public class SortingOffice {
    public static int ID = 1;
    private int id;
    private final int x;
    private final int y;
    private final String location;
    private final String country;
    private final boolean international;
    private ArrayList<String> postcodes = new ArrayList<>();
    private ArrayList<SortingOffice> connections = new ArrayList<>();

    /**
     * Constructor to instantiate a new Sorting office.
     * Sets location based on x,y and country variable names.
     * Automatically sets the ID value.
     *
     * @param x the x coordinate value
     * @param y the y coordinate value
     * @param country country the office is based in
     * @param international if the office ships internationally
     * @param postcodes the postcodes covered by the post office
     */
    public SortingOffice(int x, int y, String country, boolean international, ArrayList<String> postcodes) {
        this.x = x;
        this.y = y;
        this.country = country;
        this.international = international;
        this.id = ID++;
        this.location = String.format("X%sY%sC%s", x, y, country);
        this.postcodes = postcodes;
    }

    /**
     * Adds a sorting office to the connections arrayList.
     *
     * @param office the office to add to the connections.
     */
    public void addConnection(SortingOffice office) {
        connections.add(office);
    }

    /**
     * Returns the postcodes covered by the office as a concatenated string.
     * @return a concatenated string with postcodes.
     */
    public String getAllPostcodes() {
        String allPostcodes = "";
        if (postcodes != null) {
            for (String elem : postcodes) {
                allPostcodes += elem;
            }
        }
        return allPostcodes;
    }

    /**
     * Getters and setters for x, y, international, connections, location, country postcodes and ID.
     */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isInternational() {
        return international;
    }

    public ArrayList<SortingOffice> getConnections() {
        return connections;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<String> getPostcodes() {
        return postcodes;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }



    /**
     * Setters for postcodes and connections.
     */
    public void setPostcodes(ArrayList<String> postcodes) {
        this.postcodes = postcodes;
    }

    public void setConnections(ArrayList<SortingOffice> connections) {
        this.connections = connections;
    }

    /**
     * Returns a string with the location and the international status, followed by postcodes.
     * @return a formatted string containing various attributes about an office.
     */
    @Override
    public String toString() {
        return getLocation() + isInternational() + getAllPostcodes();
    }

}
