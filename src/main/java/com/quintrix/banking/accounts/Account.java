package com.quintrix.banking.accounts;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.quintrix.banking.DataModel;
import com.quintrix.banking.company.Branch;
import com.quintrix.banking.transactions.Transaction;

@Entity
public class Account extends DataModel {
	
	
	public Set<Transaction> transactions;
	public AccountType type;
	public String ownerName;
	public Date opened;
	public Date closed;
	@ManyToOne
	public Branch branch;
	// Balance after most recent batch
	public double currentBalance;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long id;	

		
}
