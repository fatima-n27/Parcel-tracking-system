package com.example.demo;

/**
 * DO NOT EDIT
 * Interface to represent a Deliverable object.
 * You will not need to edit this class at all.
 */
public interface Deliverable{
    public void process(SortingOffice sortingOffice);
    public SortingOffice getSender();
    public SortingOffice getRecipient();
    public String getReceipt();
}
