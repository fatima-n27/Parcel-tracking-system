package com.example.demo;

import java.util.ArrayList;

/**
 * Generic perishable class
 * Stores attributes for perishable products.
 * Implements deliverable interface.
 */
public class Perishable implements Deliverable {
    protected SortingOffice sender;
    protected SortingOffice recipient;
    protected boolean expired = false;
    protected ArrayList<SortingOffice> locations = new ArrayList<SortingOffice>();

    /**
     * Constructor to instantiate a new perishable object.
     *
     *  @param sender the sender sorting office.
     *  @param recipient the receiving sorting office.
     */
    public Perishable(SortingOffice sender, SortingOffice recipient) {
        this.sender = sender;
        this.recipient =  recipient;
    }

    /**
     * Empty process method.
     * Overrides in subclasses.
     *
     * @param sortingOffice the sorting office to process.
     */
    @Override
    public void process(SortingOffice sortingOffice) {
    }

    /**
     * Returns a formatted string with the recipient's location.
     * String wording depends on the instance.
     *
     * @return a formatted string with class name and recipient's location.
     */
    @Override
    public String getReceipt() {
        if (this instanceof Plant) {
            return String.format("Plant delivered to %s.", getRecipient().getLocation());
        } else if (this instanceof Produce) {
            return String.format("Produce delivered to %s.", getRecipient().getLocation());
        }
        return "";
    }

    /**
     * Getters for expired, locations, sender and recipient attribute.
     */
    @Override
    public SortingOffice getSender() {
        return sender;
    }

    @Override
    public SortingOffice getRecipient() {
        return recipient;
    }

    public boolean isExpired() {
        return expired;
    }

    public ArrayList<SortingOffice> getLocations() {
        return locations;
    }

    /**
     * Setters for expired, locations, sender and recipient attribute.
     */
    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public void setRecipient(SortingOffice recipient) {
        this.recipient = recipient;
    }

    public void setLocations(ArrayList<SortingOffice> locations) {
        this.locations = locations;
    }

    public void setSender(SortingOffice sender) {
        this.sender = sender;
    }
}
