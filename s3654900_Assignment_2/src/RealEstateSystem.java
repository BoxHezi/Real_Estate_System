import java.util.*;
import java.io.*;

public class RealEstateSystem {
    // Using ArrayList instead of array to declare an unknown sized array
    private static ArrayList<Sale> salesProperty = new ArrayList<>();
    private static boolean isRunning = true;
    private static String fileName = "PropertyList.txt";
    private static String backupFile = "BackupList.txt";
    private static String sampleList = "SampleList.txt";

    public static void main(String[] args) throws OfferException {
        // read from text file
        readFromFile();
        while (isRunning) { // if isRunning is true
            realEstateMenu();// run RealEstateMenu method
            Scanner userInput = new Scanner(System.in);
            System.out.printf("%-26s", "Please select an option:");
            String option = userInput.nextLine();// read what user input

            if (option.equalsIgnoreCase("A")) { // A is input
                addNewSale();// run addNewSale method
            } else if (option.equalsIgnoreCase("B")) { // B is input
                submitOffer();// run submitOffer method
            } else if (option.equalsIgnoreCase("C")) { // C is input
                displaySalesSummary();// run displaySalesSummary method
            } else if (option.equalsIgnoreCase("D")) { // D is input
                addNewAuction();// run addNewAuction method
            } else if (option.equalsIgnoreCase("E")) { // E is input
                closeAuction();// run closeAuction method
            } else if (option.equalsIgnoreCase("X")) { // X is input
                System.out.println("Saving file...");
                System.out.println("File saved!");
                // write data to text file
                outputToFile();
                isRunning = false; // exit the loop
                System.exit(0);// exit the program
            } else {
                System.out.println("Invalid option");
            }
        }
    }

    public static void addNewSale() {
        // set scanner to scan user input
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter Sale ID for new PropertySale:");
        String saleIDInput = userInput.nextLine();
        // check if saleIDInput is empty of not
        if (saleIDInput.isEmpty()) {
            System.out.println("Error - Blank ID is not accepted!");
            return;
        }
        /*
         * Using a loop to check each element in the array to find out if saleID
         * is already exists or not
         */
        for (int i = 0; i < salesProperty.size(); i++) {
            if (saleIDInput.equals(salesProperty.get(i).getSaleID())) {
                System.out.println("Error - Sale ID \"" + saleIDInput
                        + "\" already exists in the system!");
                return;// exit addNewSale method
            }
        }
        System.out.print("Enter Property Address for new PropertySale:");
        String addressInput = userInput.nextLine();
        // check addressInput is valid
        if (addressInput.isEmpty()) {
            System.out.println("Address is invalid!");
            return;
        }
        System.out.print("Enter Reserve Price for new PropertySale:");
        int reservePriceInput = 0;
        String tempReservePrice = userInput.nextLine();
        // check reserve price is input
        if (tempReservePrice.isEmpty()) {
            System.out.println("Reserve price must be entered!");
            return;
        } else {
            try {
                /*
                 * tempReservePrice is a string, for int can not be check by
                 * using isEmpty() method. if user enter a valid number to set
                 * reserve price, parse it into reservePriceInput and set it
                 * reserve price for the property
                 */
                reservePriceInput = Integer.parseInt(tempReservePrice);
                if (reservePriceInput < 0) {
                    System.out.println("Reserve price can not be lower than 0");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Reserve price must be a number!");
                return;
            }

        }
        // create a new Sale object and add it to the array list
        Sale newSaleProperty = new Sale(saleIDInput, addressInput,
                reservePriceInput);
        salesProperty.add(newSaleProperty);// add the new property to the
                                           // ArrayList
        System.out
                .println("New Property Sale added sucessfully for property at "
                        + addressInput);
    }

    public static void submitOffer() throws OfferException {
        int newOffer;
        Scanner userInput = new Scanner(System.in);
        System.out.printf("%-26s", "Enter Sale ID: ");
        String saleIDSearch = userInput.nextLine();
        String checkID;

        for (int offerIndex = 0; offerIndex < salesProperty
                .size(); offerIndex++) {
            checkID = salesProperty.get(offerIndex).getSaleID();
            boolean checkAvailability = salesProperty.get(offerIndex)
                    .getAcceptingOffers();
            // if ID input is valid
            if (checkID.equals(saleIDSearch)) {
                // check if property still available
                if (checkAvailability == false) {
                    System.out.println("Property is no longer available!");
                    return;
                }

                System.out.printf("%-25s %s\n", "Enter Sale ID:", saleIDSearch);
                System.out.printf("%-25s %s\n", "Current offer:",
                        salesProperty.get(offerIndex).getCurrentOffer());
                System.out.printf("%-26s", "Enter new offer:");

                try {
                    // input offer for new property and call makeOffer method
                    newOffer = userInput.nextInt();
                    salesProperty.get(offerIndex).makeOffer(newOffer);
                    return;
                } catch (InputMismatchException e) {
                    System.out.println(
                            "Please enter a number to make a new offer!");
                    return;
                }
            }
        }
        // when Sale ID input is not found
        System.out.println(
                "Error - property sale ID \"" + saleIDSearch + "\" not found!");
    }

    public static void displaySalesSummary() {
        // print all property in the array list
        for (int i = 0; i < salesProperty.size(); i++) {
            System.out.print(salesProperty.get(i).getSaleDetails());
            System.out.println("-----------------------------------");
        }
    }

    public static void addNewAuction() {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter Sale ID for new AuctionSale:");
        String saleIDInput = userInput.nextLine();
        // check if saleIDInput is valid
        if (saleIDInput.isEmpty()) {
            System.out.println("Error - Blank ID is not accepted!");
            return;
        }
        /*
         * Using a loop to check each element in the array to find out if saleID
         * is already exists or not
         */
        for (int i = 0; i < salesProperty.size(); i++) {
            if (saleIDInput.equals(salesProperty.get(i).getSaleID())) {
                System.out.println("Error - Sale ID \"" + saleIDInput
                        + "\" already exists in the system!");
                return;// exit addNewSale method
            }
        }

        System.out.print("Enter Property Address for new Auction:");
        String addressInput = userInput.nextLine();
        // check if address input is valid
        if (addressInput.isEmpty()) {
            System.out.println("Address is invalid!");
            return;
        }
        System.out.print("Enter Reserve Price for new AuctionSale:");
        int reservePriceInput = 0;
        String tempReservePrice = userInput.nextLine();
        // check reserve price is input
        if (tempReservePrice.isEmpty()) {
            System.out.println("Reserve price must be entered!");
            return;
        } else {
            try {
                /*
                 * tempReservePrice is a string as int can not be check by using
                 * isEmpty() method. if user enter a valid number to set reserve
                 * price, parse it into an int reservePriceInput and set it
                 * reserve price for the property
                 */
                reservePriceInput = Integer.parseInt(tempReservePrice);
                if (reservePriceInput < 0) {
                    System.out.println("Reserve price can not be lower than 0");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Reserve price must be a number!");
                return;
            }
        }

        // add new auction object and add to the array list
        Auction newAuctionSale = new Auction(saleIDInput, addressInput,
                reservePriceInput);
        salesProperty.add(newAuctionSale);

        System.out
                .println("New Property Sale added sucessfully for property at "
                        + addressInput);
    }

    public static void closeAuction() {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter ID of Auction to be closed:");
        String closeID = userInput.nextLine();
        String checkID;

        for (int i = 0; i < salesProperty.size(); i++) {
            checkID = salesProperty.get(i).getSaleID();
            // check if ID input is exist in the array list
            if (checkID.equals(closeID)) {
                // if the property ID input is a auction property
                if (salesProperty.get(i) instanceof Auction) {
                    ((Auction) salesProperty.get(i)).closeAuction();
                    System.out.println("Auction \"" + closeID
                            + "\" has ended - property has been: "
                            + salesProperty.get(i).getPropertyStatus());
                    return;
                }
                // if a sale property is input
                else if (salesProperty.get(i) instanceof Sale) {
                    System.out.println("Error - property sale ID \"" + closeID
                            + "\" is not an auction!");
                    return;
                }
            }
        }
        System.out.println(
                "Error - property sale ID \"" + closeID + "\" not found!");
    }

    public static void readFromFile() {
        Scanner userInput = new Scanner(System.in);
        String loadSample = null;
        // create a scanner
        Scanner sourceFile = null;
        try {
            // scan file
            sourceFile = new Scanner(new File(fileName));
            System.out.println("File loaded!");
        } catch (Exception fileNotFound) {
            System.out.println("File \"" + fileName + "\" not found!");
            System.out.println("Searching backup file");
            try {
                // search for back up file if the main file is not found
                sourceFile = new Scanner(new File(backupFile));
                System.out.println("Reading data from backup file...");
            } catch (Exception backupNotFound) {
                /*
                 * tell user that no back up is found and ask if user want a
                 * sample list to be loaded
                 */
                System.out.println("No back up file found!");
                System.out.print("Load Sample List?(Y/N)");
                loadSample = userInput.nextLine();
                try {
                    // convert user input to upper case
                    switch (loadSample.toUpperCase()) {
                    case "Y":
                        // read from sample list file
                        sourceFile = new Scanner(new File(sampleList));
                        System.out.println("Sample file loaded");
                        break;
                    case "N":
                        System.out.println("Sample will be not loaded");
                        // exit from this method
                        return;
                    default:
                        System.out.println(
                                "No such an option. No file will be load!");
                        // exit from this method
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("No sample file found!");
                    // exit from this method
                    return;
                }
            }
        }
        // if sourceFile contain data
        if (sourceFile != null) {
            // initialize variables for both sale or auction object
            String tempID = null;
            String tempAddress = null;
            int tempCurrentOffer = 0;
            int tempReservePrice = 0;
            boolean tempAcceptingOffers;
            // initialize one variable to store highest bidder for auction
            // object
            String tempHighestBidder = null;
            while (sourceFile.hasNextLine()) {
                String propertyType = sourceFile.nextLine();
                // if propertyType is sale
                if (propertyType.equals("Sale")) {
                    /*
                     * read text line by line and parse those information to
                     * where they should be
                     */
                    tempID = sourceFile.nextLine();
                    tempAddress = sourceFile.nextLine();
                    tempCurrentOffer = sourceFile.nextInt();
                    tempReservePrice = sourceFile.nextInt();
                    tempAcceptingOffers = sourceFile.nextBoolean();
                    /*
                     * skip first line as nextBoolean will not goes to next line
                     * skip the second line in order to skip property status
                     */
                    sourceFile.nextLine();
                    sourceFile.nextLine();
                    // create a new Sale object due to what is read from file
                    Sale savedSale = new Sale(tempID, tempAddress,
                            tempReservePrice);
                    // set current offer and acceptingOffers
                    savedSale.setCurrentOffer(tempCurrentOffer);
                    savedSale.setAcceptingOffers(tempAcceptingOffers);
                    // add it to the array list
                    salesProperty.add(savedSale);
                }
                // if it is auction
                else if (propertyType.equals("Auction")) {
                    /*
                     * read text line by line and parse those information to
                     * where they should be
                     */
                    tempID = sourceFile.nextLine();
                    tempAddress = sourceFile.nextLine();
                    tempCurrentOffer = sourceFile.nextInt();
                    tempReservePrice = sourceFile.nextInt();
                    tempAcceptingOffers = sourceFile.nextBoolean();
                    /*
                     * skip first line as nextBoolean will not goes to next line
                     * skip the second line in order to skip property status
                     */
                    sourceFile.nextLine();
                    sourceFile.nextLine();
                    tempHighestBidder = sourceFile.nextLine();
                    // create a new Sale object but with Auction type
                    Sale savedAuction = new Auction(tempID, tempAddress,
                            tempReservePrice);
                    // set currentOffer, acceptingOffers and highestBidder
                    savedAuction.setCurrentOffer(tempCurrentOffer);
                    savedAuction.setAcceptingOffers(tempAcceptingOffers);
                    ((Auction) savedAuction)
                            .setHighestBidder(tempHighestBidder);
                    // add the object to the array list
                    salesProperty.add(savedAuction);
                }
            }
        }
        sourceFile.close();
    }

    public static void outputToFile() {
        // create new PrintWriter object to output data to file
        PrintWriter targetFile = null;
        PrintWriter backupTarget = null;
        try {
            // initialize targetFile
            targetFile = new PrintWriter(new FileOutputStream(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * create a for loop to scan each element in the salesProperty array
         * list
         */
        for (Sale saleInfo : salesProperty) {
            // when auction object is defined
            if (saleInfo instanceof Auction) {
                // print Auction to file
                targetFile.println("Auction");
            } else {
                // print Sale to file
                targetFile.println("Sale");
            }
            // output information to text file
            targetFile.println(saleInfo.getSaleID());
            targetFile.println(saleInfo.getPropertyAddress());
            targetFile.println(saleInfo.getCurrentOffer());
            targetFile.println(saleInfo.getReservePrice());
            targetFile.println(saleInfo.getAcceptingOffers());
            targetFile.println(saleInfo.getPropertyStatus());
            if (saleInfo instanceof Auction) {
                targetFile.println(((Auction) saleInfo).getHighestBidder());
            }
        }
        targetFile.println("End");
        targetFile.close();

        try {
            // initialize backupTarget
            backupTarget = new PrintWriter(new FileOutputStream(backupFile));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * write data again to a different file as backup with exactly the same
         * as what is written to the main file
         */
        for (Sale saleInfo : salesProperty) {
            if (saleInfo instanceof Auction) {
                backupTarget.println("Auction");
            } else {
                backupTarget.println("Sale");
            }
            // output information to text file
            backupTarget.println(saleInfo.getSaleID());
            backupTarget.println(saleInfo.getPropertyAddress());
            backupTarget.println(saleInfo.getCurrentOffer());
            backupTarget.println(saleInfo.getReservePrice());
            backupTarget.println(saleInfo.getAcceptingOffers());
            backupTarget.println(saleInfo.getPropertyStatus());
            if (saleInfo instanceof Auction) {
                backupTarget.println(((Auction) saleInfo).getHighestBidder());
            }
        }
        backupTarget.println("End");
        backupTarget.close();
    }

    public static void realEstateMenu() {
        // print out menu with vertical alignment
        System.out.println("*** Real Estate System Menu ***" + '\n');
        System.out.printf("%-25s %s\n", "Add New Sale", "A");
        System.out.printf("%-25s %s\n", "Submit Offer", "B");
        System.out.printf("%-25s %s\n", "Display Sales Summary", "C");
        System.out.printf("%-25s %s\n", "Add New Auction", "D");
        System.out.printf("%-25s %s\n", "Close Auction", "E");
        System.out.printf("%-25s %s\n", "Exit Program", "X");
    }
}