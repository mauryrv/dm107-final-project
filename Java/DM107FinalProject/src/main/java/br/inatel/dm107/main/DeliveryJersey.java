package br.inatel.dm107.main;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import br.inatel.dm107.DAO.DeliveryDAO;
import br.inatel.dm107.entity.DeliveryEntity;

import javax.ws.rs.core.UriInfo;

@Path("/delivery")
public class DeliveryJersey {

	@Context
	private UriInfo uriInfo;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response helloJersey(){
		List<DeliveryEntity>items = DeliveryDAO.getInstance().getItems();
		GenericEntity entity = new GenericEntity<List<DeliveryEntity>>(items){};
		
		return Response
				.ok()
				.entity(entity)
				.build();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("/{requestId}")
	public Response getItem(@PathParam("requestId") String requestId){
		DeliveryEntity item = DeliveryDAO.getInstance().getItemByRequestId(requestId);
		
		if(item == null)
		{
			return Response.status(Status.NOT_FOUND).build();
			
			
		}
		GenericEntity entity = new GenericEntity<DeliveryEntity>(item){};
		
		return Response
				.ok()
				.entity(entity)
				.build();
	}
	
	@POST
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response createItem(DeliveryEntity item)
	{
		DeliveryDAO.getInstance().createItem(item);
		GenericEntity entity = new GenericEntity<DeliveryEntity>(item){};
		
		try {
			return Response
					.created(new URI(String .format("%s/%s", uriInfo.getAbsolutePath(),item.getId())))
					.entity(entity)
					.build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
	}
}
