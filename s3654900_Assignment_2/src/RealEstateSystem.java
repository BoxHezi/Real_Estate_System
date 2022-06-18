import java.util.*;
import java.io.*;

public class RealEstateSystem {
    // Using ArrayList instead of array to declare an unknown sized array
    private static final List<Sale> salesProperty = new ArrayList<>();
    private static boolean isRunning = true;
    private static final String FILENAME = "PropertyList.txt";
    private static final String BACKUP_FILE = "BackupList.txt";
    private static final String SAMPLE_LIST = "SampleList.txt";

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
        for (Sale sale : salesProperty) {
            if (saleIDInput.equals(sale.getSaleID())) {
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
        int reservePriceInput;
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
                .println("New Property Sale added successfully for property at "
                        + addressInput);
    }

    public static void submitOffer() throws OfferException {
        int newOffer;
        Scanner userInput = new Scanner(System.in);
        System.out.printf("%-26s", "Enter Sale ID: ");
        String saleIDSearch = userInput.nextLine();
        String checkID;

        for (Sale sale : salesProperty) {
            checkID = sale.getSaleID();
            boolean checkAvailability = sale
                    .getAcceptingOffers();
            // if ID input is valid
            if (checkID.equals(saleIDSearch)) {
                // check if property still available
                if (!checkAvailability) {
                    System.out.println("Property is no longer available!");
                    return;
                }

                System.out.printf("%-25s %s\n", "Enter Sale ID:", saleIDSearch);
                System.out.printf("%-25s %s\n", "Current offer:",
                        sale.getCurrentOffer());
                System.out.printf("%-26s", "Enter new offer:");

                try {
                    // input offer for new property and call makeOffer method
                    newOffer = userInput.nextInt();
                    sale.makeOffer(newOffer);
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
        for (Sale sale : salesProperty) {
            System.out.print(sale.getSaleDetails());
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
        for (Sale sale : salesProperty) {
            if (saleIDInput.equals(sale.getSaleID())) {
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
        int reservePriceInput;
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

        for (Sale sale : salesProperty) {
            checkID = sale.getSaleID();
            // check if ID input is exist in the array list
            if (checkID.equals(closeID)) {
                // if the property ID input is a auction property
                if (sale instanceof Auction) {
                    ((Auction) sale).closeAuction();
                    System.out.println("Auction \"" + closeID
                            + "\" has ended - property has been: "
                            + sale.getPropertyStatus());
                }
                // if a sale property is input
                else {
                    System.out.println("Error - property sale ID \"" + closeID
                            + "\" is not an auction!");
                }
                return;
            }
        }
        System.out.println(
                "Error - property sale ID \"" + closeID + "\" not found!");
    }

    public static void readFromFile() {
        String loadSample;
        Scanner sourceFile;
        try {
            // scan file
            sourceFile = new Scanner(new File(FILENAME));
            System.out.println("File loaded!");
        } catch (Exception fileNotFound) {
            System.out.println("File \"" + FILENAME + "\" not found!");
            System.out.println("Searching backup file");
            try {
                // search for back up file if the main file is not found
                sourceFile = new Scanner(new File(BACKUP_FILE));
                System.out.println("Reading data from backup file...");
            } catch (Exception backupNotFound) {
                /*
                 * tell user that no back up is found and ask if user want a
                 * sample list to be loaded
                 */
                Scanner userInput = new Scanner(System.in);
                System.out.println("No back up file found!");
                System.out.print("Load Sample List?(Y/N)");
                loadSample = userInput.nextLine();
                sourceFile = loadSampleFile(loadSample);
                if (sourceFile == null) {
                    return;
                } else {
                    System.out.println("Sample file loaded");
                }
            }
        }

        parseSourceFile(sourceFile);
        sourceFile.close();
    }

    public static void parseSourceFile(Scanner sourceFile) {
        while (sourceFile.hasNextLine()) {
            String propertyType = sourceFile.nextLine();
            // if propertyType is sale
            if (propertyType.equals("Sale")) {
                salesProperty.add(generateSaleItem(sourceFile));
            }
            // if it is auction
            else if (propertyType.equals("Auction")) {
                salesProperty.add(generateAuctionItem(sourceFile));
            }
        }
    }

    public static Scanner loadSampleFile(String loadSample) {
        try {
            // convert user input to upper case
            switch (loadSample.toUpperCase()) {
                case "Y" -> {
                    // read from sample list file
                    return new Scanner(new File(SAMPLE_LIST));
                }
                case "N" -> {
                    System.out.println("Sample will be not loaded");
                    return null;
                }
                default -> {
                    System.out.println(
                            "No such an option. No file will be load!");
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println("No sample file found!");
        }
        return null;
    }

    public static Sale generateSaleItem(Scanner sourceFile) {
        String tempID = sourceFile.nextLine();
        String tempAddress = sourceFile.nextLine();
        int tempCurrentOffer = sourceFile.nextInt();
        int tempReservePrice = sourceFile.nextInt();
        boolean tempAcceptingOffers = sourceFile.nextBoolean();
        /*
         * skip first line as nextBoolean will not go to next line
         * skip the second line in order to skip property status
         */
        sourceFile.nextLine();
        sourceFile.nextLine();

        Sale savedItem = new Auction(tempID, tempAddress, tempReservePrice);
        savedItem.setCurrentOffer(tempCurrentOffer);
        savedItem.setAcceptingOffers(tempAcceptingOffers);

        return savedItem;
    }

    public static Auction generateAuctionItem(Scanner sourceFile) {
        String tempID = sourceFile.nextLine();
        String tempAddress = sourceFile.nextLine();
        int tempCurrentOffer = sourceFile.nextInt();
        int tempReservePrice = sourceFile.nextInt();
        boolean tempAcceptingOffers = sourceFile.nextBoolean();
        /*
         * skip first line as nextBoolean will not go to next line
         * skip the second line in order to skip property status
         */
        sourceFile.nextLine();
        sourceFile.nextLine();

        Auction savedAuction = new Auction(tempID, tempAddress, tempReservePrice);
        savedAuction.setCurrentOffer(tempCurrentOffer);
        savedAuction.setAcceptingOffers(tempAcceptingOffers);
        savedAuction.setHighestBidder(sourceFile.nextLine());

        return savedAuction;
    }

    public static void outputToFile() {
        // create new PrintWriter object to output data to file
        PrintWriter targetFile = null;
        PrintWriter backupTarget = null;
        try {
            // initialize targetFile
            targetFile = new PrintWriter(new FileOutputStream(FILENAME));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * create a for loop to scan each element in the salesProperty array
         * list
         */
        for (Sale saleInfo : salesProperty) {
            // when auction object is defined
            assert targetFile != null;
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
        assert targetFile != null;
        targetFile.println("End");
        targetFile.close();

        try {
            // initialize backupTarget
            backupTarget = new PrintWriter(new FileOutputStream(BACKUP_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * write data again to a different file as backup with exactly the same
         * as what is written to the main file
         */
        for (Sale saleInfo : salesProperty) {
            assert backupTarget != null;
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
        assert backupTarget != null;
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