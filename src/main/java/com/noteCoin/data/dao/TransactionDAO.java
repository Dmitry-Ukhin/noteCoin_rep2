package com.noteCoin.data.dao;

import com.noteCoin.models.Transaction;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public interface TransactionDAO {
    Boolean save(Transaction transaction);
    Boolean remove(Transaction transaction);

    List<Transaction> getList(String strQuery);
    List<Transaction> getLimitedList(String strQuery, Integer maxResults);
}