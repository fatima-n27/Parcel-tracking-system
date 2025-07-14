package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing class for the SortingOffices project.
 * Tests stages 1 through 8.
 */

/*
 * Additional methods added to the project were:
 * addSortingOffices() method in Data. Only used in testing for task 5.
 */

public class TestAndImpress {
    /**
     * 2 different postCode arrayLists for testing is stage 5
     * 5 different SortingOffices for testing in all stages.
     */
    public ArrayList<String> postCodes;
    public ArrayList<String> diffPostCodes;
    public SortingOffice office1;
    public SortingOffice office2;
    public SortingOffice office3;
    public SortingOffice office4;
    public SortingOffice broken;

    /**
     * Initializes the 2 arrayLists and 5 sortingOffices.
     * Ran before every test suite executes.
     */
    @BeforeEach
    public void setUp() {
        postCodes = new ArrayList<>();
        postCodes.add("AB");
        postCodes.add("CD");

        diffPostCodes = new ArrayList<>();
        diffPostCodes.add("EF");

        office1 = new SortingOffice(100,100,"Wales", true, postCodes);
        office2 = new SortingOffice(200,200,"England", true, postCodes);
        office3 = new SortingOffice(300,300,"Scotland", true, diffPostCodes);
        office4 = new SortingOffice(400,400,"Scotland", true, postCodes);

        broken = new SortingOffice(560,320,"Scotland", true, postCodes);
    }

    /**
     * Test suite for task 1.
     * Tests 5 methods from the SortingOffice class including:
       getters for x, y, international
       if the location is being set as expected
       toString method
     */
    @Test
    public void testStage1() {
        assertEquals(100, office1.getX());
        assertEquals(100, office1.getY());
        assertEquals(true, office1.isInternational());
        assertEquals("X100Y100CWales", office1.getLocation());
        assertEquals("X100Y100CWalestrueABCD", office1.toString());
    }

    /**
     * Test suite for task 2.
     * Tests 3 methods from the NonPerishable class including:
       Getters for sender and recipient attributes.
       process() method is working as expected.
     */
    @Test
    public void testStage2() {
        NonPerishable nonPerishable = new NonPerishable(office1, office2, 10.0, true);

        assertEquals("X100Y100CWalestrueABCD", nonPerishable.getSender().toString());
        assertEquals("X200Y200CEnglandtrueABCD", nonPerishable.getRecipient().toString());

        assertFalse(nonPerishable.isBroken());  //Confirms item is not broken before.
        nonPerishable.process(broken);
        assertTrue(nonPerishable.isBroken()); //Item should be broken after processing from problem office.

    }

    /**
     * Test suite for task 3.
     * Tests getter methods from the perishable class including for expired, sender and recipient.
     */
    @Test
    public void testStage3() {
        Perishable perishable = new Perishable(office1, office2);
        assertEquals("X100Y100CWalestrueABCD", perishable.getSender().toString());
        assertEquals("X200Y200CEnglandtrueABCD", perishable.getRecipient().toString());
        assertFalse(perishable.isExpired());
    }

    /**
     * Test suite for task 4.
     * Tests 4 methods from the produce class including:
       Getters for tracking location, current location, sender and recipient.
       process() method is working as expected.
     * Tests 3 methods from the Plant class including:
     */
    @Test
    public void testStage4() {
        Produce produce = new Produce(office1, office3);

        //If process method works, trackingLocation test will pass
        produce.process(office1);
        produce.process(office2);
        produce.process(office3);

        assertEquals("X100Y100CWales,X200Y200CEngland,X300Y300CScotland", produce.getTrackingLocation());
        assertEquals("X300Y300CScotland", produce.getCurrentLocation());
        assertEquals("X100Y100CWalestrueABCD", produce.getSender().toString());
        assertEquals("X300Y300CScotlandtrueEF", produce.getRecipient().toString());


        Plant plant = new Plant(office1, office3);

        assertEquals("X100Y100CWalestrueABCD", produce.getSender().toString());
        assertEquals("X300Y300CScotlandtrueEF", produce.getRecipient().toString());

        assertFalse(plant.isExpired());     //Plant is not expired before processing
        plant.process(office1);
        plant.process(office2);
        plant.process(office3);
        plant.process(office4);     //Plant gets processed through 4 offices
        assertTrue(plant.isExpired());  //expired should be set to true now.
    }

    /**
     * Test suite for task 5.
     * Tests both findSortingOffices methods from the Data class.
     * If tested alone, it will fail the 2nd test since the ID will be too big. Change the ID parameter to 2 to pass.
     */
    @Test
    public void testStage5() {
        Data.addSortingOffice(office1);
        Data.addSortingOffice(office2);
        Data.addSortingOffice(office3);
        Data.addSortingOffice(office4);

        assertEquals("X300Y300CScotland", Data.findSortingOffice("EF").getLocation());
        assertEquals("X200Y200CEngland", Data.findSortingOffice(22).getLocation());
    }

    /**
     * Test suite for task 6.
     * Tests if the readDeliverables and readSortingOffices methods from previous stages are working properly.
     *
     * @throws FileNotFoundException if sortingOffices.txt or deliverables.txt doesn't exist.
     */
    @Test
    public void testStage6() throws FileNotFoundException {
        assertTrue(Data.getDeliverables().isEmpty());   //Ensure the deliverables stack is empty before
        Data.readDeliverables();
        assertEquals(15, Data.getDeliverables().size());    //15 items in the deliverables.csv file.

        Data.getSortingOffices().clear();   //From previous tasks
        Data.readSortingOffices();
        assertEquals(8, Data.getSortingOffices().size()); //8 items in the sortingOffices.txt file.
    }


    /**
     * Test suite for task 7.
     * Tests if the addConnections method is working properly from Data class.
     * If ran alone, will fail due to the ID parameter being passed in.
     * If ran alone, pass in 7 instead of 45 and 10 instead of 48 to pass.
     *
     * @throws FileNotFoundException if sortingOffices.txt doesn't exist
     */
    @Test
    public void testStage7() throws FileNotFoundException {  //NullPointer for when the id is too big - maybe handle
        Data.getSortingOffices().clear();   //Clear the sortingOffices list from previous tasks
        Data.readSortingOffices();

        //Confirms these random 2 offices exist
        assertEquals("X560Y320CScotlandtrueEHG",Data.findSortingOffice(45).toString());
        assertTrue(Data.findSortingOffice(45).getConnections().isEmpty());

        assertEquals("X480Y630CWalesfalseCHLD",Data.findSortingOffice(48).toString());
        assertTrue(Data.findSortingOffice(48).getConnections().isEmpty());

        Data.addConnections();
        assertEquals(4, Data.findSortingOffice(45).getConnections().size());

        assertEquals(1, Data.findSortingOffice(48).getConnections().size());
    }

    /**
     * Test suite for task 8.
     * Tests the getReceipt() method for non-perishable, plant and produce objects.
     * Tests all the possible messages that can be output.
     */
    @Test
    public void testStage8() {
        Plant plant = new Plant(office1, office2);
        plant.setExpired(true);
        assertEquals("WARNING! Expired Plant delivered to X200Y200CEngland.", plant.getReceipt());

        Plant plant2 = new Plant(office1, office2);
        assertEquals("Plant delivered to X200Y200CEngland.", plant2.getReceipt());

        NonPerishable nonPerishable = new NonPerishable(office1, office2, 10.0, true);
        assertEquals("Fragile Non-Perishable delivered to X200Y200CEngland.", nonPerishable.getReceipt());

        NonPerishable nonPerishable1 = new NonPerishable(office1, office2, 10, true);
        nonPerishable1.setBroken(true);

        assertEquals("Fragile Non-Perishable delivered to X200Y200CEngland. Item is broken.", nonPerishable1.getReceipt());

        NonPerishable nonPerishable2 = new NonPerishable(office1, office2, 10, false);

        assertEquals("Non-Perishable delivered to X200Y200CEngland.", nonPerishable2.getReceipt());

        Produce produce = new Produce(office1, office2);
        produce.process(office2);
        assertEquals("Produce delivered to X200Y200CEngland. Produce route: X200Y200CEngland.", produce.getReceipt());

        Produce produce2 = new Produce(office1, office2);
        produce2.setExpired(true);
        produce2.process(office2);

        assertEquals("WARNING! Expired Produce delivered to X200Y200CEngland. Produce route: X200Y200CEngland.", produce2.getReceipt());
    }
}
