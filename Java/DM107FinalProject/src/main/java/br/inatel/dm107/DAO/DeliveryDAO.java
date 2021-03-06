package br.inatel.dm107.DAO;

import java.util.ArrayList;
import java.util.List;

import br.inatel.dm107.connection.Connection;
import br.inatel.dm107.entity.DeliveryEntity;

public class DeliveryDAO {

	
	private static DeliveryDAO delivery = null;
	Connection con;
	
	public static DeliveryDAO getInstance() {
		if (delivery == null) {
			delivery = new DeliveryDAO();
		}
		return delivery;
	}
	
	public DeliveryDAO() {
		con = new Connection();
       	
	}
	
	public List<DeliveryEntity> getItems() {
		Connection con = new Connection();
		// pegar todas entregas cadastradas no banco de dados
		ArrayList<DeliveryEntity> items = con.listDeliveries();

		return items;
	}
	
	public DeliveryEntity getItemByRequestId(String requestNumber) {
		Connection con = new Connection();
		DeliveryEntity item = con.getDelivery(requestNumber);
		//pegar item no banco de dados pelo id da entrega!
		return item;
	}
	
	public DeliveryEntity createItem(DeliveryEntity entity) {
		
		//criar item no banco de dados
		long id = con.getNextDeliveryId();
		entity.setId(id);
		Connection con = new Connection();
		int add = con.createDelivery(entity);
		if(add==0)
			return null;
		
		return entity;
	}

}
