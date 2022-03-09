package com.example.demo.controllers;

import com.example.demo.models.entity.Client;
import com.example.demo.models.entity.Invoice;
import com.example.demo.models.entity.ItemInvoice;
import com.example.demo.models.entity.Product;
import com.example.demo.models.service.IClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoice")
public class InvoiceController {

	private static final String ERROR = "error";
	private static final String TITLE = "title";
	private static final String INVOICE_FORM = "invoice/form";
	private final IClientService clientService;
	private final MessageSource messageSource;
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	public InvoiceController(IClientService clientService, MessageSource messageSource) {
		this.clientService = clientService;
		this.messageSource = messageSource;
	}


	/* ----- View Invoice[id] ----- */
	@GetMapping("/view/{id}")
	public String view(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash, Locale locale) {

		Invoice invoice = clientService.fetchInvoiceByIdWithClientWithInvoiceItemWithProduct(id);
		
		if (invoice == null) {
			flash.addAttribute(ERROR, messageSource.getMessage("text.factura.flash.db.error", null, locale));
			return "redirect:/list";
		}

		model.addAttribute("invoice", invoice);
		model.addAttribute(TITLE, String.format(messageSource.getMessage("text.factura.ver.titulo", null, locale), invoice.getDescription()));

		return "invoice/view";
	}

	/* ----- Create Invoice for Client[id] ----- */
	@GetMapping("/form/{clientId}")
	public String create(@PathVariable(value = "clientId") Long clientId, Map<String, Object> model,
                         RedirectAttributes flash, Locale locale) {

		Client client = clientService.findOne(clientId);
		
		if (client == null) {
			flash.addAttribute(ERROR, messageSource.getMessage("text.cliente.flash.db.error", null, locale));
			return "redirect:/list";
		}

		Invoice invoice = new Invoice();
		invoice.setClient(client);

		model.put("invoice", invoice);
		model.put(TITLE, messageSource.getMessage("text.factura.form.titulo", null, locale));

		return INVOICE_FORM;
	}

	/* ----- Autocomplete for Finding Products (autocomplete-products.js)----- */
	@GetMapping(value = "/load-products/{term}", produces = { "application/json" })
	public @ResponseBody
    List<Product> loadProducts(@PathVariable String term) {
		return clientService.findByName(term);
	}

	/* ----- Save Invoice ----- */
	@PostMapping("/form")
	public String save(@Valid Invoice invoice, BindingResult result, Model model,
                       @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                       @RequestParam(name = "amount[]", required = false) Integer[] amount, RedirectAttributes flash,
                       SessionStatus status, Locale locale) {

		if (result.hasErrors()) {
			model.addAttribute(TITLE, messageSource.getMessage("text.factura.form.titulo", null, locale));
			return INVOICE_FORM;
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute(TITLE, messageSource.getMessage("text.factura.form.titulo", null, locale));
			model.addAttribute(ERROR, messageSource.getMessage("text.factura.flash.lineas.error", null, locale));
			return INVOICE_FORM;
		}

		for (int i = 0; i < itemId.length; i++) {
			Product product = clientService.findProductById(itemId[i]);

			ItemInvoice line = new ItemInvoice();
			line.setAmount(amount[i]);
			line.setProduct(product);
			invoice.addItemInvoice(line);

			log.info("ID: {}  amount: {}", itemId[i].toString() , amount[i].toString());
		}

		clientService.saveInvoice(invoice);
		status.setComplete();

		flash.addFlashAttribute("success", messageSource.getMessage("text.factura.flash.crear.success", null, locale));

		return "redirect:/view/" + invoice.getClient().getId();
	}
	
	/* ----- Delete Invoice ----- */
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash, Locale locale) {
		Invoice invoice = clientService.findInvoiceById(id);

		if (invoice != null) {
			clientService.deleteInvoice(id);
			flash.addAttribute("success", messageSource.getMessage("text.factura.flash.eliminar.success", null, locale));
			return "redirect:/view/" + invoice.getClient().getId();
		}

		flash.addFlashAttribute(ERROR, messageSource.getMessage("text.factura.flash.db.error", null, locale));
		return "redirect:/list/";
	}

}
