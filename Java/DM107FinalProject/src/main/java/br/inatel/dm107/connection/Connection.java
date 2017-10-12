package br.inatel.dm107.connection;


import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.inatel.dm107.entity.DeliveryEntity;


public class Connection {
	
	java.sql.Connection connection;
	static{
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public Connection()
	{
		String url = "jdbc:postgresql://localhost:5432/TrabalhoDM107";
		String username="postgres";
		String password="admin";
		
		try {
			connection = DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void closeConnection()

	{
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public long getNextDeliveryId()
	{
		int id=0;
		String sql ="select nextval('sqce_delivery') as nextid";
		
		try( 	Statement sttm = connection.createStatement();
				ResultSet  rs = sttm.executeQuery(sql);)
		{

			while(rs.next()){
				id = rs.getInt("nextid");

			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
		
	}

	public ArrayList<DeliveryEntity> listDeliveries()
	{
		
		ArrayList<DeliveryEntity> deliveries = new ArrayList<>();
		DeliveryEntity item;

		String sql ="Select id,request_number,customer_id,receiver_name,receiver_cpf,delivery_date"
				+ " from delivery order by id";
		
		try( 	Statement sttm = connection.createStatement();
				ResultSet  rs = sttm.executeQuery(sql);)
		{

			while(rs.next()){
				item = new DeliveryEntity();
				item.setId(rs.getLong("id"));
				item.setIdRequest(rs.getString("request_number"));
				item.setIdCustomer(rs.getString("customer_id"));
				item.setReceiverName(rs.getString("receiver_name"));
				item.setReceiverCPF(rs.getString("receiver_cpf"));
				item.setDeliveryDate(rs.getDate("delivery_date"));
				deliveries.add(item);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return deliveries;
	}

	public DeliveryEntity getDelivery(String request_number)
	{
		
		DeliveryEntity delivery = null;

		String sql ="Select id,request_number,customer_id,receiver_name,receiver_cpf,delivery_date"
				+ " from delivery where request_number='"+request_number+"'";

		
		try( 	Statement sttm = connection.createStatement();
				ResultSet  rs = sttm.executeQuery(sql);)
		{

			while(rs.next()){
				
				delivery = new DeliveryEntity();
				delivery.setId(rs.getLong("id"));
				delivery.setIdRequest(rs.getString("request_number"));
				delivery.setIdCustomer(rs.getString("customer_id"));
				delivery.setReceiverName(rs.getString("receiver_name"));
				delivery.setReceiverCPF(rs.getString("receiver_cpf"));
				delivery.setDeliveryDate(rs.getDate("delivery_date"));

			}
			
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return delivery;
		
	}

	public int createDelivery(DeliveryEntity entity)
	{
		
		int result =0;

		String sql ="insert into delivery(id,request_number,customer_id) "
				+"values(?,?,?)";
		try(PreparedStatement psstm = connection.prepareStatement(sql))	{
			
			psstm.setLong(1, entity.getId());
			psstm.setString(2, entity.getIdRequest());
			psstm.setString(3, entity.getIdCustomer());

			result = psstm.executeUpdate();
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return result;

	}
}
