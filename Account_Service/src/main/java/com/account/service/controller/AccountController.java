package com.account.service.controller;

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

import com.account.service.binding.AccountBinding;
import com.account.service.binding.KycBinding;
import com.account.service.binding.TransactionRequest;
import com.account.service.entity.Account;
import com.account.service.entity.AccountTransaction;
import com.account.service.entity.KycDetails;
import com.account.service.entity.User;
import com.account.service.service.AccountService;
import com.account.service.service.KycDetailsService;
import com.account.service.service.TransactionService;
import com.account.service.util.APiResponse;

@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private KycDetailsService kycDetailsService;

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/createAccount")
	public ResponseEntity<APiResponse<Account>> createAccount(@RequestBody AccountBinding accountBinding) {

		APiResponse<Account> account = accountService.createAccount(accountBinding);

		System.out.println(account);
		return new ResponseEntity<APiResponse<Account>>(account, HttpStatus.CREATED);

	}

	@PostMapping("/kycUpdate")
	public ResponseEntity<APiResponse<KycDetails>> startKycUpdationProcess(@RequestBody KycBinding kycBinding) {

		APiResponse<KycDetails> kycStatus = kycDetailsService.startKycUpdationProcess(kycBinding);

		return new ResponseEntity<APiResponse<KycDetails>>(kycStatus, HttpStatus.CREATED);

	}

	@GetMapping("/getAccountInfo/{userId}")
	public ResponseEntity<APiResponse<Account>> getAccountInfoByUserId(@PathVariable Integer userId) {

		APiResponse<Account> response = accountService.getAccountInfoByUserId(userId);

		return new ResponseEntity<APiResponse<Account>>(response, HttpStatus.OK);
	}

	@GetMapping("/getUserInfo/{id}")
	public ResponseEntity<APiResponse<User>> getUserInfoById(@PathVariable Integer id) {

		APiResponse<User> userInfo = accountService.getUserInfoById(id);

		return new ResponseEntity<APiResponse<User>>(userInfo, HttpStatus.OK);
	}

	@GetMapping("/kycStatus/{accountId}")
	public ResponseEntity<APiResponse<KycDetails>> getKycDetailsByAccountId(@PathVariable Integer accountId) {

		APiResponse<KycDetails> response = kycDetailsService.getKycDetailsByAccountId(accountId);

		return new ResponseEntity<APiResponse<KycDetails>>(response, HttpStatus.OK);
	}

	@GetMapping("/kycDetails/{userId}")
	public ResponseEntity<APiResponse<KycDetails>> getKycDetailsByUserId(@PathVariable Integer userId) {

		APiResponse<KycDetails> response = kycDetailsService.getKycDetailsByUserId(userId);

		return new ResponseEntity<APiResponse<KycDetails>>(response, HttpStatus.OK);
	}

	@GetMapping("/getAccountStatus/{userId}")
	public ResponseEntity<APiResponse<Boolean>> getAccountOpenedOrNotByUserId(@PathVariable Integer userId) {

		APiResponse<Boolean> response = accountService.getAccountOpenedOrNotByUserId(userId);

		return new ResponseEntity<APiResponse<Boolean>>(response, HttpStatus.OK);
	}

	@GetMapping("/getAccountTransactions/{accountNumber}")
	public ResponseEntity<APiResponse<List<AccountTransaction>>> getAccountTransactionByAccountId(
			@PathVariable String  accountNumber) {

		APiResponse<List<AccountTransaction>> response = transactionService.getTransactionDetailsByAccountNumber(accountNumber);

		return new ResponseEntity<APiResponse<List<AccountTransaction>>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getBalance/{accountNumber}")
	public ResponseEntity<APiResponse<Double>> getBalance(@PathVariable String accountNumber) throws AccountNotFoundException {

		APiResponse<Double> response = accountService.getBalanceByAccountNumber(accountNumber);

		return new ResponseEntity<APiResponse<Double>>(response, HttpStatus.OK);
	}
	

	@PostMapping("/credit")
	public ResponseEntity<APiResponse<Boolean>> credit(@RequestBody TransactionRequest txRequest) throws AccountNotFoundException {

		APiResponse<Boolean> response = accountService.credit(txRequest);

		return new ResponseEntity<APiResponse<Boolean>>(response, HttpStatus.OK);
	}

	@PostMapping("/debit")
	public ResponseEntity<APiResponse<Boolean>> debit(@RequestBody TransactionRequest txRequest) throws AccountNotFoundException {

		APiResponse<Boolean> response = accountService.debit(txRequest);

		return new ResponseEntity<APiResponse<Boolean>>(response, HttpStatus.OK);
	}
}
