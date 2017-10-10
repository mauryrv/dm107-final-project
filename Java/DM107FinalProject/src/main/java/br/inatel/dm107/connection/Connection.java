package br.inatel.dm107.connection;

//import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.Connection;
import java.sql.Date;
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
		//TERMINAR CLASSEEEE!!!!
		ArrayList<DeliveryEntity> deliveries = new ArrayList<>();
		DeliveryEntity item;

		String sql ="Select cliente.id as pf_id, nome, endereco, telefone,cpf,identidade,tipo_identidade"
				+ " from cliente join pessoa_fisica pf on cliente.id = pf.id order by cliente.id";
		
		try( 	Statement sttm = connection.createStatement();
				ResultSet  rs = sttm.executeQuery(sql);)
		{

			while(rs.next()){
				item = new DeliveryEntity();
				/*rs.getInt("pf_id"),rs.getString("nome"),rs.getString("endereco"),rs.getString("telefone")
				,rs.getString("cpf"),rs.getString("identidade"),rs.getString("tipo_identidade")*/
				
				deliveries.add(item);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return deliveries;
	}


	public DeliveryEntity getDelivery(long id)
	{
		
		DeliveryEntity delivery = null;

		String sql ="Select cliente.id as p_id, nome, endereco, telefone from cliente where id="+id;
				
		
		try( 	Statement sttm = connection.createStatement();
				ResultSet  rs = sttm.executeQuery(sql);)
		{

			while(rs.next()){
				
				delivery = new DeliveryEntity();
				
			/*rs.getInt("p_id"),
				rs.getString("nome"),
				rs.getString("endereco"),rs.getString("telefone"));*/
				
			

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

		String sql ="insert into atendimento(id,id_cliente,data,descricao) "
				+"values(?,?,?,?)";
		try(PreparedStatement psstm = connection.prepareStatement(sql))	{
			/*java.sql.Date newDt = new java.sql.Date (atendimento.getData().getTime());
			psstm.setInt(1, id);
			psstm.setInt(2, atendimento.getCliente().getId());
			psstm.setDate(3, newDt);
			psstm.setString(4, atendimento.getDescricao());*/
			
			result = psstm.executeUpdate();
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		
		return result;

	}
}
