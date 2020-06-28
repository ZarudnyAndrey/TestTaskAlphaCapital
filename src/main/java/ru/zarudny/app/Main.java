package ru.zarudny.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.zarudny.app.Order.TransactionName;

public class Main {

  public static void main(String[] args) {
    File balanceInput = new File("input/balance.csv");
    File orderInput = new File("input/order.csv");

    File balanceOutput = new File("output/balance.csv");
    File reportOutput = new File("output/report.csv");

    Set<Client> clients = new LinkedSet<>();
    Set<Order> buyers = new LinkedSet<>();
    Set<Order> sellers = new LinkedSet<>();
    Set<Report> reports = new LinkedSet<>();

    readFiles(balanceInput, orderInput, clients, buyers, sellers);

    executionApplications(clients, buyers, sellers, reports);

    createReport(balanceOutput, reportOutput, clients, reports);
  }

  public static void readFiles(File balanceInput, File orderInput, Set<Client> clients,
      Set<Order> buyers, Set<Order> sellers) {
    Client currentClient;
    int userId;
    String assetCode;
    int assetCount;

    try (InputStreamReader inputStreamReader = new InputStreamReader(
        new FileInputStream(balanceInput))) {
      CSVParser csvParser = CSVFormat.DEFAULT.parse(inputStreamReader);

      for (CSVRecord csvRecord : csvParser) {
        userId = Integer.parseInt(csvRecord.get(0));
        assetCode = csvRecord.get(1);
        assetCount = Integer.parseInt(csvRecord.get(2));

        currentClient = new Client(userId, assetCode, assetCount);
        clients.add(currentClient);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    String uniqueNumberOrder;

    try (InputStreamReader inputStreamReader = new InputStreamReader(
        new FileInputStream(orderInput))) {
      CSVParser csvParser = CSVFormat.DEFAULT.parse(inputStreamReader);

      for (CSVRecord csvRecord : csvParser) {
        uniqueNumberOrder = csvRecord.get(0);
        userId = Integer.parseInt(csvRecord.get(1));
        assetCode = csvRecord.get(3);
        assetCount = Integer.parseInt(csvRecord.get(4));

        if (csvRecord.get(2).equals("BUY")) {
          buyers.add(
              new Order(uniqueNumberOrder, userId, TransactionName.BUY, assetCode, assetCount));
        } else if (csvRecord.get(2).equals("SELL")) {
          sellers.add(
              new Order(uniqueNumberOrder, userId, TransactionName.SELL, assetCode, assetCount));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void executionApplications(Set<Client> clients, Set<Order> buyers,
      Set<Order> sellers, Set<Report> reports) {
    int sellerId;
    int buyerId;
    int count;

    for (Order seller : sellers) {
      for (Order buyer : buyers) {

        if (seller.getAsset().equals(buyer.getAsset())) {
          Report report = null;
          sellerId = seller.getClientId();
          buyerId = buyer.getClientId();


          if (seller.getAssetCount() >= buyer.getAssetCount()) {
            report = new Report(RandomStringGenerator.randomString(10),
                seller.getUniqueNumberOrder(), buyer.getUniqueNumberOrder(), buyer.getAssetCount());

            count = buyer.getAssetCount();
            for (Client client : clients) {
              if (client.getId() == buyerId) {
                client.setAssetCount(client.getAssetCount() + count);
                sellers.remove(seller);
              }

              if (client.getId() == sellerId) {
                client.setAssetCount(client.getAssetCount() - count);
                buyers.remove(buyer);
              }
            }

          } else if (seller.getAssetCount() < buyer.getAssetCount()) {
            report = new Report(RandomStringGenerator.randomString(10),
                seller.getUniqueNumberOrder(), buyer.getUniqueNumberOrder(),
                seller.getAssetCount());

            count = seller.getAssetCount();
            for (Client client : clients) {
              if (client.getId() == buyerId) {
                client.setAssetCount(client.getAssetCount() + count);
                sellers.remove(seller);
              }

              if (client.getId() == sellerId) {
                client.setAssetCount(client.getAssetCount() - count);
                buyers.remove(buyer);
              }
            }

          }

          reports.add(report);
        }

      }
    }
  }

  public static void createReport(File balanceOutput, File reportOutput, Set<Client> clients,
      Set<Report> reports) {
    try (FileOutputStream writer = new FileOutputStream(balanceOutput)) {
      StringBuilder builder = new StringBuilder();
      for (Client client : clients) {
        builder.append(client.toString());
      }
      writer.write(builder.toString().getBytes());
      writer.flush();
    } catch (IOException e) {
      e.getStackTrace();
    }

    try (FileOutputStream writer = new FileOutputStream(reportOutput)) {
      StringBuilder builder = new StringBuilder();
      for (Report report : reports) {
        builder.append(report.toString());
      }
      writer.write(builder.toString().getBytes());
      writer.flush();
    } catch (IOException e) {
      e.getStackTrace();
    }
  }
}
