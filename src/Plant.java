package com.example.demo;

import java.util.ArrayList;

/**
 * Plant class stores attributes and manages plant products.
 * Extends Perishable class.
 */
public class Plant extends Perishable {
    /**
     * Minimum offices for expired to be true.
     */
    public static final int SET_EXPIRED = 3;

    /**
     * Constructor implemented from the parent class.
     *
     * @param sender the sender sorting office.
     * @param recipient the receiving sorting office.
     */
    public Plant(SortingOffice sender, SortingOffice recipient) {
        super(sender, recipient);
    }

    /**
     * Process method adds a sorting office to the locations arrayList.
     * If the product is processed through more than 3 offices, expired is set to true.
     *
     * @param sortingOffice the sorting office to process.
     */
    @Override
    public void process(SortingOffice sortingOffice) {
        locations.add(sortingOffice);
        if (locations.size() > SET_EXPIRED) {
            expired = true;
        }
    }

    /**
     * Getters for sender, recipient and locations attribute.
     */
    @Override
    public SortingOffice getSender() {
        return sender;
    }

    @Override
    public SortingOffice getRecipient() {
        return recipient;
    }

    @Override
    public ArrayList<SortingOffice> getLocations() {
        return super.getLocations();
    }

    /**
     * Depending on the state of the expired attribute, a different message is returned.
     * @return a formatted string, based on expired attribute
     */
    @Override
    public String getReceipt() {
        if (!isExpired()) {
            return super.getReceipt();
        } else {
            return "WARNING! Expired " + super.getReceipt();
        }
    }
}
