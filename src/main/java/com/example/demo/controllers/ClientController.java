package com.example.demo.controllers;


import com.example.demo.models.entity.Client;
import com.example.demo.models.service.IClientService;
import com.example.demo.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

import java.util.Locale;

@Controller
@SessionAttributes("client")
public class ClientController {


	private static final String REDIRECT_LIST = "redirect:/list";
	private static final String ERROR = "error";
	private static final String CLIENT = "client";
	private static final String TITLE = "title";
	private final IClientService clientService;
	private final MessageSource messageSource;

	@Autowired
	public ClientController(IClientService clientService, MessageSource messageSource) {
		this.clientService = clientService;
		this.messageSource = messageSource;
	}


	/* ----- View Clients Details ----- */
	@GetMapping(value = "/view/{id}")
	public String view(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash, Locale locale) {

		Client client = clientService.fetchByIdWithInvoices(id);
		if (client == null) {
			flash.addFlashAttribute(ERROR, messageSource.getMessage("text.cliente.flash.db.error", null, locale));
			return REDIRECT_LIST;
		}

		model.addAttribute(CLIENT, client);
		model.addAttribute(TITLE, messageSource.getMessage("text.cliente.listar.titulo", null, locale) + ": "+ client.getFirstName());


		return "view";
	}

	/* ----- List Clients ----- */
	@GetMapping(value = { "/list", "/" })
	public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Locale locale) {

		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Client> clients = clientService.findAll(pageRequest);
		PageRender<Client> pageRender = new PageRender<>("/list", clients);

		model.addAttribute(TITLE, "Customer List");
		model.addAttribute("clients", clients);
		model.addAttribute("page", pageRender);
		return "list";
	}

	
	/* ----- Create Client ----- */

	@GetMapping(value = "/form")
	public String create(Model model, Locale locale) {
		Client client = new Client();
		model.addAttribute(CLIENT, client);

		model.addAttribute(TITLE,"New customer");
		return "form";
	}

	/* ----- Edit Client ----- */
	@GetMapping(value = "/form/{id}")
	public String update(@PathVariable(value = "id") Long id, RedirectAttributes flash, Model model, Locale locale) {

		Client client = null;
		
		// If Customer exist, find
		if (id > 0) {
			client = clientService.findOne(id);
			// If not exist, error
			if (client == null) {
				flash.addFlashAttribute(ERROR,"The customer doesn't exist in the database");
				return REDIRECT_LIST;
			}
		} else {
			flash.addFlashAttribute(ERROR, "The customer ID can't be zero.");
			return REDIRECT_LIST;
		}

		model.addAttribute(CLIENT, client);
		model.addAttribute(TITLE, "Edit customer");

		return "form";
	}

	/* ----- Save Client ----- */
	@PostMapping(value = "/form")
	public String save(@Valid Client client, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status, Locale locale) {

		if (result.hasErrors()) {
			model.addAttribute(TITLE, "Customer Form");
			return "form";
		}
		String flashMsg = (client.getId() != null)
				? "Customer edited successfully"
				: "Customer created successfully";

		clientService.save(client);
		status.setComplete();
		flash.addFlashAttribute("success", flashMsg);
		return REDIRECT_LIST;
	}

	/* ----- Delete Client ----- */
	@GetMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash, Locale locale) {

		if (id > 0) {
			clientService.delete(id);
			flash.addFlashAttribute("success", "Customer removed successfully");

		}
		return REDIRECT_LIST;
	}



}
