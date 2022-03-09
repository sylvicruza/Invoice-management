package com.example.demo.view.xml;



import com.example.demo.models.entity.Client;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "clients")
public class ClientList {

	@XmlElement(name = "client")
	public List<Client> clients;

	public ClientList() {}
	
	public ClientList(List<Client> clients) {
		this.clients = clients;
	}

	public List<Client> getClients() {
		return clients;
	}
	
}
