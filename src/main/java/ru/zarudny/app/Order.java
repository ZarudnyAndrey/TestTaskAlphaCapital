package ru.zarudny.app;

public class Order {

  private String uniqueNumberOrder;
  private int clientId;
  private Enum transaction;
  private String asset;
  private int assetCount;

  public Order(String uniqueNumberOrder, int clientId, Enum transaction, String asset,
      int assetCount) {
    this.uniqueNumberOrder = uniqueNumberOrder;
    this.clientId = clientId;
    this.transaction = transaction;
    this.asset = asset;
    this.assetCount = assetCount;
  }

  public String getAsset() {
    return asset;
  }

  public int getAssetCount() {
    return assetCount;
  }

  public int getClientId() {
    return clientId;
  }

  public String getUniqueNumberOrder() {
    return uniqueNumberOrder;
  }

  public enum TransactionName {
    BUY,
    SELL
  }
}
