package ru.zarudny.app;

public class Client {

  private int id;
  private String assetCode;
  private int assetCount;

  public Client(int id,
      String assetCode,
      int assetCount) {
    this.id = id;
    this.assetCode = assetCode;
    this.assetCount = assetCount;
  }

  public int getId() {
    return id;
  }

  public int getAssetCount() {
    return assetCount;
  }

  public void setAssetCount(int assetCount) {
    this.assetCount = assetCount;
  }

  @Override
  public String toString() {
    return id + "," + assetCode + "," + assetCount + "\n";
  }
}
