package com.quintrix.banking.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/***
 * Please read about JPA repositories and utilize them here
 * @author drhin
 *
 */
public interface CompanyRepository {

	public Branch addBranch(Branch newBranch);
	public Branch findBranchByLocation(String location);
	public Branch findBranchById(long id);
	
}
