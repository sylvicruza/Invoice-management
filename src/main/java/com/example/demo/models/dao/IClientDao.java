package com.example.demo.models.dao;


import com.example.demo.models.entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IClientDao extends PagingAndSortingRepository<Client, Long> {

	// c = Client
	// i = invoices
	@Query("select c from Client c left join fetch c.invoices i where c.id = ?1")
	public Client fetchByIdWithInvoices(Long id);

	public Client findByEmail(String name);
	
}
