package com.example.demo.models.service;


import com.example.demo.models.entity.Client;
import com.example.demo.models.entity.Invoice;
import com.example.demo.models.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IClientService {
	
	public List<Client> findAll();

	public Page<Client> findAll(Pageable pageable);

	@Transactional(readOnly = true)
	Client findByEmail(String email);

	public Client findOne(Long id);
	
	public Client fetchByIdWithInvoices(Long id);
	
	public void save(Client client);
	
	public void delete(Long id);
	
	public List<Product> findByName(String term);
	
	public void saveInvoice(Invoice invoice);
	
	public Product findProductById(Long id);
	
	public Invoice findInvoiceById(Long id);
	
	public void deleteInvoice(Long id);
	
	public Invoice fetchInvoiceByIdWithClientWithInvoiceItemWithProduct(Long id);
}
