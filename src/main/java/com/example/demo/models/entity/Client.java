package com.example.demo.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "clients")
public class Client implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotEmpty
	@Email
	private String email;

	@NotNull
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createAt;

	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Invoice> invoices;

	public Client() {
		invoices = new ArrayList<Invoice>();
	}


	private static final long serialVersionUID = 1L;

	public static long getSerialVersionuid() {
		return serialVersionUID;
	}

	/*----- toString -----*/
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
	
	/*----- Getters & Setters -----*/
	public Long getId() { return id; }
	
	public void setId(Long id) { this.id = id; }
	
	public String getFirstName() { return firstName; }
	
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	public String getLastName() { return lastName; }
	
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	public String getEmail() { return email; }
	
	public void setEmail(String email) { this.email = email; }
	
	public Date getCreateAt() { return createAt; }
	
	public void setCreateAt(Date createAt) { this.createAt = createAt; }
	
	public List<Invoice> getInvoices() { return invoices; }
	
	public void setInvoices(List<Invoice> invoices) { this.invoices = invoices; }
	
	public void addInvoice(Invoice invoice) { invoices.add(invoice); }

}
