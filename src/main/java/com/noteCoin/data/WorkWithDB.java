/**
 * last change 20.03.18 01:28
 */
package com.noteCoin.data;

import com.noteCoin.models.Transaction;

import java.util.List;

public interface WorkWithDB {

    public Integer saveToDB(Transaction transaction);

    public List<Transaction> loadFromDB(String requestToDB);

    public Integer removeTransaction(Transaction transaction);

    public void reloadConnectWithDB();
}
