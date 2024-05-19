package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
										ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return (args) -> {
			Client client1 = new Client("Mane", "prim", "prim@mail.cl", passwordEncoder.encode("test1"));
			Account acc1 = new Account("VIN0001", LocalDate.now(),5000);
			Account acc2 = new Account("VIN0002", LocalDate.now().plusDays(1),7500);


			Client client2 = new Client("James", "Hetfield", "JH@mail.cl",passwordEncoder.encode("test2"));
			Account acc3 = new Account("VIN0003", LocalDate.now(),10000);

			Client userAdmin = new Client("Admin","ADM","admin@admin.cl",passwordEncoder.encode("admin"));

			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(userAdmin);


			client1.addClient(acc1);
			client1.addClient(acc2);
			accountRepository.save(acc1);
			accountRepository.save(acc2);
			client2.addClient(acc3);
			accountRepository.save(acc3);

			//Transactions
			Transaction Transaction1A1 = new Transaction(TransactionType.CREDIT,100,"compra 1",LocalDate.now());
			Transaction Transaction2A1 = new Transaction(TransactionType.DEBIT,200,"pago 1",LocalDate.now());
			acc1.addAccount(Transaction1A1);
			acc1.addAccount(Transaction2A1);
			transactionRepository.save(Transaction1A1);
			transactionRepository.save(Transaction2A1);

			Transaction Transaction1A2 = new Transaction(TransactionType.CREDIT,200,"compra 5",LocalDate.now());
			Transaction Transaction2A2 = new Transaction(TransactionType.DEBIT,100,"pago 5",LocalDate.now());
			acc2.addAccount(Transaction1A2);
			acc2.addAccount(Transaction2A2);
			transactionRepository.save(Transaction1A2);
			transactionRepository.save(Transaction2A2);

			Transaction Transaction1A3 = new Transaction(TransactionType.CREDIT,300,"compra 9",LocalDate.now());
			Transaction Transaction2A3 = new Transaction(TransactionType.DEBIT,500,"pago 15",LocalDate.now());
			acc3.addAccount(Transaction1A3);
			acc3.addAccount(Transaction2A3);
			transactionRepository.save(Transaction1A3);
			transactionRepository.save(Transaction2A3);

			List<Integer> payment1 = asList(12,24,36,48,60);
			Loan loanHipotecario = new Loan("Hipotecario",500000,payment1);

			List<Integer> payment2 = asList(6,12,24);
			Loan loanPersonal = new Loan("Personal",100000,payment2);

			List<Integer> payment3 = asList(6,12,24,36);
			Loan loanAutomotriz = new Loan("Automotriz",300000,payment3);

			loanRepository.save(loanHipotecario);
			loanRepository.save(loanPersonal);
			loanRepository.save(loanAutomotriz);

			ClientLoan clientLoan1 = new ClientLoan(400000,60,client1,loanHipotecario);
			ClientLoan clientLoan2 = new ClientLoan(50000,12,client1,loanPersonal);
			ClientLoan clientLoan3 = new ClientLoan(100000,24,client2,loanPersonal);
			ClientLoan clientLoan4 = new ClientLoan(200000,36,client2,loanAutomotriz);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			Card card1 = new Card(client1.getFirstName()+" "+client1.getLastName(),CardType.DEBIT,
					CardColor.GOLD,"1234-5678",364,LocalDate.now(),LocalDate.now().plusYears(5),client1);
			Card card2 = new Card(client1.getFirstName()+" "+client1.getLastName(),CardType.CREDIT,
					CardColor.TITANIUM,"8765-4321",155,LocalDate.now(),LocalDate.now().plusYears(5),client1);
			Card card3 = new Card(client2.getFirstName()+" "+client2.getLastName(),CardType.CREDIT,
					CardColor.SILVER,"1472-8521",164,LocalDate.now(),LocalDate.now().plusYears(5),client2);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);




			//clientRepository.save(new Client("Mane", "prim", "prim@mail.cl"));
		};
	}

}
