
public class Sale {
    // declare variables
    private String saleID;
    private String propertyAddress;
    private int currentOffer;
    private int reservePrice;
    private boolean acceptingOffers;

    // constructor
    public Sale(String saleID, String propertyAddress, int reservePrice) {
        this.saleID = saleID;
        this.propertyAddress = propertyAddress;
        this.reservePrice = reservePrice;
        acceptingOffers = true;
        currentOffer = 0;
    }

    public String getSaleID() {
        return saleID;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public int getCurrentOffer() {
        return currentOffer;
    }

    public int getReservePrice() {
        return reservePrice;
    }

    public boolean getAcceptingOffers() {
        return acceptingOffers;
    }

    public void setCurrentOffer(int newOffer) {
        currentOffer = newOffer;
    }

    public void setAcceptingOffers(boolean newAcceptingOffers) {
        acceptingOffers = newAcceptingOffers;
    }

    // make offer method to check if buyers can make offers now
    public void makeOffer(int offerPrice) throws OfferException {
        /*
         * use try catch block to handle offer exception check availability of
         * properties will be done in the ReasEstateSystem class
         */
        try {
            // when new offer is less than current offer
            if (offerPrice <= currentOffer) {
                throw new OfferException(
                        "Error - New offer must be higher than current offer!");
            }
            // when offer is less than reserve price
            else if (offerPrice <= reservePrice) {
                setCurrentOffer(offerPrice);
                throw new OfferException(
                        "Offer accepted - but offer has NOT met reserve price!"
                                + "\nFuther offer are accepted");
            } else {
                // call setCurrentOffer method to set offer price
                setCurrentOffer(offerPrice);
                // set acceptingOffers to false
                setAcceptingOffers(!acceptingOffers);
                System.out.println(
                        "Offer Accepted! \nStop accepting further offer");
            }
        } catch (OfferException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    // getPropertyStatus method to check the status of the property
    public String getPropertyStatus() {
        if (acceptingOffers == true) {// if acceptingOffers is true
            return "ON SALE";
        } else { // if acceptingOffers is false
            return "SOLD";
        }
    }

    // method to format data in order to be readable for human
    public String getSaleDetails() {
        // print out sale details with vertical alignment
        String firstLine = String.format("%-20s %s\n", "Sale ID:", saleID);
        String secondLine = String.format("%-20s %s\n", "Property address:",
                propertyAddress);
        String thirdLine = String.format("%-20s %s\n", "Current offer:",
                currentOffer);
        String forthLine = String.format("%-20s %s\n", "Reserve price:",
                reservePrice);
        String fifthLine = String.format("%-20s %s\n", "Accepting offers:",
                acceptingOffers);
        String sixthLine = String.format("%-20s %s\n", "Sale status:",
                getPropertyStatus());
        return firstLine + secondLine + thirdLine + forthLine + fifthLine
                + sixthLine;
    }
}
