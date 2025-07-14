package com.example.demo;

/**
 * Class stores attributes and manages non-perishable products.
 * Implements deliverable interface.
 */
public class NonPerishable implements Deliverable {
    public static final String BROKEN_OFFICE = "X560Y320CScotland";
    private SortingOffice sender;
    private SortingOffice recipient;
    private final double weight;
    private boolean fragile;
    private boolean broken = false;

    /**
     * Constructor to instantiate a non-perishable product.
     *
     * @param sender the sender sorting office.
     * @param recipient the receiving sorting office.
     * @param weight weight of the package
     * @param fragile fragile status of the package
     */
    public NonPerishable(SortingOffice sender, SortingOffice recipient,
                         double weight, boolean fragile) {
        this.sender = sender;
        this.recipient = recipient;
        this.weight = weight;
        this.fragile = fragile;
    }

    /**
     * Processes the package through sorting offices.
     * If the package is processed through a specific office, broken is set to true.
     *
     * @param sortingOffice the sorting office to process
     */
    @Override
    public void process(SortingOffice sortingOffice) {
        if (sortingOffice == null) {
            broken = false; //default set broken to false.
        } else if (fragile && sortingOffice.getLocation().contains(BROKEN_OFFICE)) {
            broken = true; //If the office location is of a specific one, set broken to true.
        }
    }

    /**
     * Returns a formatted string with the recipient location, broken and fragile status.
     *
     * @return a formatted string with information about the package being handled.
     */
    @Override
    public String getReceipt() {
        if (isFragile()) {
            if (isBroken()) {
                return String.format("Fragile Non-Perishable delivered to %s. Item is broken.",
                        getRecipient().getLocation());
            } else {
                return String.format("Fragile Non-Perishable delivered to %s.",
                        getRecipient().getLocation());
            }
        } else {
            return String.format("Non-Perishable delivered to %s.", getRecipient().getLocation());
        }
    }

    /**
     * Getters for sender, recipient, fragile, broken and weight attributes.
     */
    @Override
    public SortingOffice getSender() {
        return sender;
    }

    @Override
    public SortingOffice getRecipient() {
        return recipient;
    }

    public boolean isFragile() {
        return fragile;
    }

    public boolean isBroken() {
        return broken;
    }

    public double getWeight() {
        return weight;
    }

    /**
     * Setters for sender, recipient, fragile and broken attributes.
     */
    public void setRecipient(SortingOffice recipient) {
        this.recipient = recipient;
    }

    public void setSender(SortingOffice sender) {
        this.sender = sender;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }
}
