/**
 * last change 12.03.18 20:12
 */
package com.noteCoin.data;

import com.noteCoin.models.Transaction;

import java.util.List;

public interface WorkWithDB {

    public Integer saveToDB(Transaction transaction);

    public List<Transaction> loadFromDB(String requestToDB);
}
