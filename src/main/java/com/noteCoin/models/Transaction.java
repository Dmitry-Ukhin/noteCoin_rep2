/**
 * last change 12.03.18 20:10
 */
package com.noteCoin.models;

import javax.persistence.*;
import java.lang.Integer;
import java.lang.Long;
import java.util.Date;

@Entity
@Table(name="Transaction")
public class Transaction{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="id_generator")
	@SequenceGenerator(name="id_generator", sequenceName = "tran_id", allocationSize = 10000)
	@Column(name = "id")
	private Long id;

	@Column(name = "type")
	private String type;

	@Column(name = "sum")
	private Integer sum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	private Date date;

	@Column(name = "description")
	private String descr;

	public Transaction() { }

	public Transaction(String type, Integer sum, Date date, String descr){
		this.type = type;
		this.sum = sum;
		this.date = date;
		this.descr = descr;
	}

	public Long getId(){
		return id;
	}

	public String getType() {
		return type;
	}

	public Integer getSum() {
		return sum;
	}

	public Date getDate() {
		return date;
	}

	public String getDescr() {
		return descr;
	}

	@Override
	public String toString() {
		return "Transaction[type-" + type + "; sum-" + sum + "; date-" + date + "; descr-" + descr + "]";
	}
}
