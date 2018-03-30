/**
 * last change 20.03.18 01:28
 */
package com.noteCoin.data;

import com.noteCoin.models.Transaction;

import java.util.List;

public interface WorkWith_DB {

    Integer saveToDB(Transaction transaction);

    List<Transaction> loadFromDB(String requestToDB);
    List<Transaction> loadFromDB(String requestToDB, Integer maxResults);

    Integer removeTransaction(Transaction transaction);

    void reloadConnectWithDB();

    Integer getDebugStatus();
    String getDebugString();
}
