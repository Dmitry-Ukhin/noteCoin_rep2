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
	@SequenceGenerator(name="id_generator", sequenceName = "tran_id")
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

	@Column(name = "user_id")
	private Long user_id;

	public Transaction() { }

	public Transaction(String type, Integer sum, Date date, String descr){
		this(type, sum, date, descr, null);
	}

	public Transaction(String type, Integer sum, Date date, String descr, Long user_id){
		this.type = type;
		this.sum = sum;
		this.date = date;
		this.descr = descr;
		this.user_id = user_id;
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

	public Long getUser_id() {
		return user_id;
	}

	@Override
	public String toString() {
		return "Transaction[type-" + type + "; sum-" + sum + "; date-" + date + "; descr-" + descr + "]";
	}
}
