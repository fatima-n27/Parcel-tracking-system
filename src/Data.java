package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Class to hold data that is added to the "database".
 * You may add methods to this class.
 */
public class Data {

    /**
     * Attributes to save our data to the "database"
     */
    private static ArrayList<SortingOffice> sortingOffices = new ArrayList<>();
    private static Stack<Deliverable> deliverables = new Stack<>();
    private static Stack<Deliverable> processedDeliverables = new Stack<>();

    /**
     * Method to return the Stack of deliverables
     *
     * @return stack of deliverables
     */
    public static Stack<Deliverable> getDeliverables() {
        return deliverables;
    }

    /**
     * Method to return the sorting offices
     *
     * @return array list of sorting offices
     */
    public static ArrayList<SortingOffice> getSortingOffices() {
        return sortingOffices;
    }

    /**
     * Method to return the completed deliverables.
     *
     * @return stack of completed deliverables.
     */
    public static Stack<Deliverable> getProcessedDeliverables() {
        return processedDeliverables;
    }

    /**
     * DO NOT EDIT ANY CODE ABOVE THIS COMMENT. You may need to write additional methods below.
     **/

    /**
     * Adds a sorting office to the arrayList.
     * Used only in stage 9 task (Testing).
     *
     * @param office the sorting office to add to the list.
     */
    public static void addSortingOffice(SortingOffice office) {
        sortingOffices.add(office);
    }

    /**
     * Searches through the sortingOffice arrayList and finds one based on its ID.
     *
     * @param id the id to search for
     * @return if sortingOffice is found, the office. Null otherwise.
     */
    public static SortingOffice findSortingOffice(int id) {
        for (SortingOffice elem : sortingOffices) {
            if (elem.getId() == id) {
                return elem;
            }
        }
        return null;
    }

    /**
     * Searches through the sortingOffice arrayList and finds one
       based on if it covers a certain postcode.
     * @param postcode the postcode to search for.
     * @return if sortingOffice is found, the office. Null otherwise.
     */
    public static SortingOffice findSortingOffice(String postcode) {
        for (SortingOffice elem : sortingOffices) {
            for (int i = 0; i < elem.getPostcodes().size(); i++) {
                if (elem.getPostcodes().get(i).equals(postcode)) {
                    return elem;
                }
            }
        }
        return null;
    }

    /**
     * Reads the content of sortingOffices.txt and creates new sortingOffices.
     * Adds them to the arrayList.
     *
     * @throws FileNotFoundException if sortingOffices.txt doesn't exist.
     */
    public static void readSortingOffices() throws FileNotFoundException {
        File input = new File("sortingOffices.txt");
        Scanner in = new Scanner(input);

        String string;
        String[] content;   //Array to store the contents of one line before processing.

        while (in.hasNext()) {
            string = in.nextLine();
            content = string.split(" ");    //Document uses a whitespace as a separator.

            //File has formatting 'x y country international postcodes'
            int x = Integer.parseInt(content[0]);
            int y = Integer.parseInt(content[1]);
            String country = content[2];
            boolean international = Boolean.parseBoolean(content[3]);

            ArrayList<String> postcodes = new ArrayList<>();

            String posts = content[4];
            String[] splitPostCodes = posts.split(",");     //postcodes are separated by a comma

            for (String elem : splitPostCodes) {
                postcodes.add(elem);    //Adds all the postcodes read in to the arrayList
            }

            /* Creates a new sorting office based on the attributes read in,
               and adds it to the arrayList. */
            SortingOffice office = new SortingOffice(x, y, country, international, postcodes);
            sortingOffices.add(office);
        }
    }

    /**
     * Reads the content of deliverables.csv and creates suitable
       new products of plant/produce.non-perishable type.
     * Adds them to the arrayList.
     *
     * @throws FileNotFoundException if sortingOffices.txt doesn't exist.
     */
    public static void readDeliverables() throws FileNotFoundException {
        Scanner in = new Scanner(new File("deliverables.csv"));

        String string;
        String[] content;

        while (in.hasNext()) {
            string = in.nextLine();
            content = string.split(","); //Document uses a comma as a separator.

            SortingOffice sender = findSortingOffice(content[0]);
            SortingOffice receiver = findSortingOffice(content[1]);

            String itemType = content[2];

            //Based on the itemType read in, suitable objects are created
            if (itemType.equals("NonPerishable")) {
                //Only non-perishable items have weight and fragile attributes.
                double weight = Double.parseDouble(content[3]);
                boolean fragile = Boolean.parseBoolean(content[4]);

                NonPerishable np = new NonPerishable(sender, receiver, weight, fragile);
                deliverables.add(np);

            } else if (itemType.equals("Plant")) {
                Plant plant = new Plant(sender, receiver);
                deliverables.add(plant);
            } else if (itemType.equals("Produce")) {
                Produce prod = new Produce(sender, receiver);
                deliverables.add(prod);
            }
        }
        in.close();
    }

    /**
     * Creates connections between sorting offices to enable transport of products.
     * Connections are created based on the country and international status of the office.
     * All connections created are bidirectional.
     */
    public static void addConnections() {
        for (int i = 0; i < sortingOffices.size(); i++) {
            for (int j = 0; j < sortingOffices.size(); j++) {

                //Don't add itself to its own connection list.
                if (sortingOffices.get(i) != sortingOffices.get(j)) {
                    //If the countries match, add it to the connections list
                    if (sortingOffices.get(i).getCountry().equals(sortingOffices.get(j).getCountry())) {
                        sortingOffices.get(i).addConnection(sortingOffices.get(j));
                    }

                    //If the both are international, add it to the connections list
                    if (sortingOffices.get(i).isInternational() && sortingOffices.get(j).isInternational()) {
                        sortingOffices.get(i).addConnection(sortingOffices.get(j));
                    }
                }
            }
        }
    }

    /**
     * For all the items that are processed, prints their receipt to receipts.txt file.
     * Invokes getReceipt() method from the suitable class.
     * Catches any FileNotFound errors.
     */
    public static void printReceipts() {
        try {
            PrintWriter output = new PrintWriter("receipts.txt");
            for (Deliverable item : processedDeliverables) {
                output.println(item.getReceipt());
            }
            output.close();
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find the suitable file.");
        }
    }

}
