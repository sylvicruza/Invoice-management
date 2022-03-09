package com.example.demo.models.service;


import com.example.demo.models.dao.IClientDao;
import com.example.demo.models.dao.IInvoiceDao;
import com.example.demo.models.dao.IProductDao;
import com.example.demo.models.entity.Client;
import com.example.demo.models.entity.Invoice;
import com.example.demo.models.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	private IClientDao clientDao;

	@Autowired
	private IProductDao productDao;

	@Autowired
	private IInvoiceDao invoiceDao;

	/*----- Method List -----*/
	@Override
	@Transactional(readOnly = true)
	public List<Client> findAll() {
		return (List<Client>) clientDao.findAll();
	}

	/*----- Paginator -----*/
	@Override
	@Transactional(readOnly = true)
	public Page<Client> findAll(Pageable pageable) {
		return clientDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Client findByEmail(String email) {
		return clientDao.findByEmail(email);
	}

	/*----- Method Find By ID -----*/
	@Override
	@Transactional(readOnly = true)
	public Client findOne(Long id) {
		return clientDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Client fetchByIdWithInvoices(Long id) {
		return clientDao.fetchByIdWithInvoices(id);
	}

	/*----- Method Save -----*/
	@Override
	@Transactional
	public void save(Client client) {
		clientDao.save(client);
	}

	/*----- Method Delete -----*/
	@Override
	@Transactional
	public void delete(Long id) {
		clientDao.deleteById(id);
	}

	/*----- Method Find by Name (Product) -----*/
	@Override
	@Transactional(readOnly = true)
	public List<Product> findByName(String term) {
		return productDao.findByName(term);
	}

	/*----- Method Save (Invoice) -----*/
	@Override
	@Transactional
	public void saveInvoice(Invoice invoice) {
		invoiceDao.save(invoice);
	}
	
	/*----- Method Find Product by ID -----*/
	@Override
	@Transactional(readOnly = true)
	public Product findProductById(Long id) {
		return productDao.findById(id).orElse(null);
	}

	/*----- Method Find by ID (Invoice) -----*/
	@Override
	@Transactional(readOnly = true)
	public Invoice findInvoiceById(Long id) {
		return invoiceDao.findById(id).orElse(null);
	}

	/*----- Method Delete (Invoice) -----*/
	@Override
	@Transactional
	public void deleteInvoice(Long id) {
		invoiceDao.deleteById(id);	
	}

	/*----- Method Fetch Invoice with Client with Invoice -----*/
	@Override
	@Transactional(readOnly = true)
	public Invoice fetchInvoiceByIdWithClientWithInvoiceItemWithProduct(Long id) {
		return invoiceDao.fetchByIdWithClientWithInvoiceItemWithProduct(id);
	}

}
