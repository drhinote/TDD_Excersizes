package com.quintrix.banking.company;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.quintrix.banking.accounts.Account;

import graphql.com.google.common.collect.Sets;

@Entity
public class Branch {

	public String location;
	
	@OneToMany(mappedBy="branch", targetEntity=Account.class)
	public Set<Account> accounts;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long id;	
		
}