/**
 * last change 12.03.18 20:13
 */
package com.noteCoin.data;

import java.util.Date;
import java.util.List;
import java.lang.Integer;

import com.noteCoin.models.Transaction;

public class FactoryTransaction{
/**
*method createTransaction gets sorted List 
*List[0] = type of transaction
*List[1] = sum of transaction
*List[2] = date of transaction in milliseconds 
*List[3] = description of transaction
*/
	public Transaction createTransaction(List<Object> args) {
		String type = (String)args.get(0);//get type of transaction
		Integer sum = (Integer)args.get(1);//get sum of transaction
		Date date = (Date)args.get(2);//get date of transaction
		String descr = (String)args.get(3);//get description of transaction
		Transaction tran = new Transaction(type, sum, date, descr);
		return tran;
	}
}
