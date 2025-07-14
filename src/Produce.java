package com.example.demo;

/**
 * Produce class stores attributes and manages produce products.
 * Extends Perishable class.
 */
public class Produce extends Perishable {
    private String currentLocation;
    private String trackingLocation;

    /**
     * Constructor to instantiate a new produce object.
     * Sets the tracking and current location to blank.
     *
     * @param sender the sender sorting office.
     * @param recipient the receiving sorting office.
     */
    public Produce(SortingOffice sender, SortingOffice recipient) {
        super(sender, recipient);
        this.currentLocation = "";
        this.trackingLocation = "";
    }

    /**
     * Adds the target sorting office to locations arrayList.
     * Sets the currentLocation to latest office.
     * Concatenates a string with all the sorting offices' location produce was processed through
     * and sets it as the tracking location.
     *
     * @param sortingOffice the sorting office to process
     */
    @Override
    public void process(SortingOffice sortingOffice) {
        if (sortingOffice != null) {
            locations.add(sortingOffice);
            currentLocation = sortingOffice.getLocation();
            //If statement for formatting the string
            if (trackingLocation.isEmpty()) {
                //If it's the first office in the string, add it to the start.
                trackingLocation = sortingOffice.getLocation();
            } else {
                //Otherwise, add it preceded with a comma.
                trackingLocation += ","  + sortingOffice.getLocation();
            }
        }
    }

    /**
     * Returns a formatted string with the tracking location and expired status of produce.
     * Uses inheritance from perishable class.
     * @return formatted string.
     */
    @Override
    public String getReceipt() {
        if (!isExpired()) {
            return super.getReceipt() + String.format(" Produce route: %s.", getTrackingLocation());
        } else {
            return "WARNING! Expired " + super.getReceipt()
                    + String.format(" Produce route: %s.", getTrackingLocation());
        }
    }

    /**
     * Getters for tracking and current location, and for sender and recipient.
     */
    public String getTrackingLocation() {
        return trackingLocation;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public SortingOffice getSender() {
        return sender;
    }

    @Override
    public SortingOffice getRecipient() {
        return recipient;
    }

    /**
     * Setters for tracking location, current location and expired status.
     */

    public void setTrackingLocation(String trackingLocation) {
        this.trackingLocation = trackingLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public void setExpired(boolean expired) {
        super.setExpired(expired);
    }
}
