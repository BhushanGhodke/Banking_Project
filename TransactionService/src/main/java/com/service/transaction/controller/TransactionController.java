package com.service.transaction.controller;

import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.transaction.binding.TransferFund;
import com.service.transaction.entity.Transaction;
import com.service.transaction.service.TransactionService;

@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/transfer")
	public ResponseEntity<Transaction> transferFund(@RequestBody TransferFund transferFund ) throws AccountNotFoundException{
	
		Transaction transaction = transactionService.transferAmount(transferFund);
	
		return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
	}

	@GetMapping("/{accountNumber}")
	public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String accountNumber) {
		return ResponseEntity.ok(transactionService.getTransaction(accountNumber));
	}
}
