package com.quintrix.banking.tdd;

import java.util.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.quintrix.banking.accounts.Account;
import com.quintrix.banking.accounts.AccountsRepository;
import com.quintrix.banking.company.Branch;
import com.quintrix.banking.company.CompanyRepository;
import com.quintrix.banking.transactions.Transaction;
import com.quintrix.banking.transactions.TransactionType;
import com.quintrix.banking.transactions.TransactionsRepository;

import graphql.com.google.common.collect.Sets;

@SpringBootTest
public class BusinessLogicTestDrivenDevelopment {
	
	private static final String TEST_LOCATION = "test location";
	@Autowired
	CompanyRepository companyDb;
	@Autowired
	AccountsRepository accountsDb;
	@Autowired
	TransactionsRepository transactionsDb;
	
	@Test
	@Order(1)
	public void canAddBranch() {
		Branch testBranch = new Branch();
		testBranch.accounts = Sets.newHashSet();
		testBranch.location = TEST_LOCATION;
		assert(companyDb.addBranch(testBranch) != null);
	}

	@Test
	@Order(2)
	public void canQueryBranches() {
		Branch testBranch = companyDb.findBranchByLocation(TEST_LOCATION);
		assert(testBranch != null);
	}
	
	@Test
	@Order(3)
	public void canAddAccount() {
		Account testAccount = makeAccount("General Zod");
		//  ** The following line may clash with the auto ID generation found in DataModel.java
		testAccount.id = 1;
		accountsDb.addAccount(testAccount);
	}
	
	@Test
	@Order(4)
	public void canFindAccountById() {
		//  ** If auto ID generation created an issue with specifying an ID then this test will need to first locate a valid account ID before performing this search
		Account testAccount = accountsDb.findAccountById(1);
		assert(testAccount != null);
	}
	
	@Test
	@Order(5)
	public void canFindAccountByName() {
		Account testAccount = accountsDb.findAccountByOwnerName("Zod, General");
		assert(testAccount != null);
	}
	
	@Test
	@Order(6)
	public void canUpdateAccountInfo() {
		Account testAccount = accountsDb.findAccountByOwnerName("General Zod");
		testAccount.ownerName = "Batman";
		accountsDb.updateAccount(testAccount);
		testAccount = accountsDb.findAccountByOwnerName("Batman");
		assert(testAccount != null && testAccount.ownerName.equals("Batman"));
	}
	
	@Test
	@Order(7)
	public void canCloseAccount() {
		Account testAccount = accountsDb.findAccountByOwnerName("Batman");
		accountsDb.closeAccount(testAccount);
		testAccount = accountsDb.findAccountByOwnerName("Batman");
		assert(testAccount.closed != null);
	}
	
	@Test
	@Order(8)
	public void canStoreTwoNewAccounts() {
		Account newAccount1 = makeAccount("Thing 1");
		Account newAccount2 = makeAccount("Thing 2");
		newAccount1.currentBalance = 50.0;
		newAccount2.currentBalance = 0.0;
		accountsDb.addAccount(newAccount1);
		accountsDb.addAccount(newAccount2);
	}
	
	@Test
	@Order(9)
	public void canCreateValidTransaction() {
		Account sourceAccount = accountsDb.findAccountByOwnerName("Thing 1");
		Account destAccount = accountsDb.findAccountByOwnerName("Thing 2");
		Transaction newTransaction = new Transaction();
		newTransaction.amount = 50.0;
		newTransaction.date = new Date();
		newTransaction.sourceAccountId = sourceAccount.id;
		newTransaction.destinationAccountId = destAccount.id;
		newTransaction.type = TransactionType.Transfer;
		transactionsDb.submitNewTransaction(newTransaction);
	}
	
	@Test
	@Order(10)
	public void cannotCreateInvalidTransaction() {
		Account sourceAccount = accountsDb.findAccountByOwnerName("Thing 2");
		Account destAccount = accountsDb.findAccountByOwnerName("Thing 1");
		Transaction newTransaction = new Transaction();
		newTransaction.amount = 1.0;
		newTransaction.date = new Date();
		newTransaction.sourceAccountId = sourceAccount.id;
		newTransaction.destinationAccountId = destAccount.id;
		newTransaction.type = TransactionType.Transfer;
		assertThrows(Exception.class, () -> transactionsDb.submitNewTransaction(newTransaction));
	}
	
	@Test
	@Order(11)
	public void canBatchProcessTransactions() {
		transactionsDb.startBatchProcessing();
	}
	
	@Test
	@Order(12)
	public void batchProcessIsAccurate() {
		Account testAccount1 = accountsDb.findAccountByOwnerName("Thing 1");
		Account testAccount2 = accountsDb.findAccountByOwnerName("Thing 2");
		assert(testAccount1.currentBalance == 0.0 && testAccount2.currentBalance == 50.0);
	}
	
	private Account makeAccount(String owner) {
		Account testAccount = new Account();
		testAccount.currentBalance = 0.0;
		testAccount.opened = new Date();
		testAccount.ownerName = owner;
		return testAccount;
	}
	
}
