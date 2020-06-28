package ru.zarudny.app;

public class Report {

  private String uniqueTransactionNumber;
  private String sellerRequestNumber;
  private String buyerRequestNumber;
  private int assetCount;

  public Report(String uniqueTransactionNumber, String sellerRequestNumber,
      String buyerRequestNumber, int assetCount) {
    this.uniqueTransactionNumber = uniqueTransactionNumber;
    this.sellerRequestNumber = sellerRequestNumber;
    this.buyerRequestNumber = buyerRequestNumber;
    this.assetCount = assetCount;
  }

  @Override
  public String toString() {
    return uniqueTransactionNumber + "," + sellerRequestNumber + "," +
        buyerRequestNumber + "," + assetCount + "\n";
  }
}
