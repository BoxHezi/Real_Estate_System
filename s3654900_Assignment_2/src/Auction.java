import java.util.*;

public class Auction extends Sale {
    private String highestBidder;

    public Auction(String saleID, String propertyAddress, int reservePrice) {
        super(saleID, propertyAddress, reservePrice);
        highestBidder = "NO BIDS PLACED";
    }

    public String getPropertyStatus() {
        if (super.getAcceptingOffers()) {
            return "ACCEPTING BIDS";
        } else {
            if (super.getCurrentOffer() >= super.getReservePrice()) {
                return "SOLD";
            } else {
                return "PASSED IN";
            }
        }
    }

    public void closeAuction() {
        if (super.getAcceptingOffers()) {
            super.setAcceptingOffers(false);
        }
    }

//    public boolean closeAuction() {
//        if (!super.getAcceptingOffers()) {
//            return false;
//        } else {
//            super.setAcceptingOffers(false);
//            return true;
//        }
//    }

    public void setHighestBidder(String newhighestBidder) {
        highestBidder = newhighestBidder;
    }

    public String getHighestBidder() {
        return highestBidder;
    }

    public void makeOffer(int offerPrice) throws OfferException {
        Scanner userInput = new Scanner(System.in);
        /*
         * try catch block to catch exception property availability will be
         * checked in the RealEstateSystem class
         */
        try {
            if (offerPrice <= super.getCurrentOffer()) {
                throw new OfferException(
                        "Error - New offer must be higher than current offer!");
            } else {
                super.setCurrentOffer(offerPrice);
                System.out.println("Offer Accepted!");
                System.out.printf("%-26s", "Enter your name:");
                highestBidder = userInput.nextLine();
            }
        } catch (OfferException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getSaleDetails() {
        String seventhLine = String.format("%-20s %s\n", "Highest bidder:",
                highestBidder);
        return super.getSaleDetails() + seventhLine;
    }
}
