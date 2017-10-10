package br.inatel.dm107.entity;

import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeliveryEntity {

	public DeliveryEntity() {
		// TODO Auto-generated constructor stub
	}
	
	private Long id;
	private String idRequest;
	private String idCustomer;
	private String receiverName;
	private String receiverCPF;
	private Date deliveryDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getIdRequest() {
		return idRequest;
	}

	public void setIdRequest(String idRequest) {
		this.idRequest = idRequest;
	}

	public String getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(String idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverCPF() {
		return receiverCPF;
	}

	public void setReceiverCPF(String receiverCPF) {
		this.receiverCPF = receiverCPF;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}


	
}
