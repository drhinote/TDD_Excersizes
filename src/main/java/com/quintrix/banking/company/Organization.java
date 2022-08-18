package com.quintrix.banking.company;

import java.util.Set;

import javax.persistence.Entity;

import com.quintrix.banking.DataModel;

public class Organization extends DataModel  {

	public Set<Branch> branches;
	
}
