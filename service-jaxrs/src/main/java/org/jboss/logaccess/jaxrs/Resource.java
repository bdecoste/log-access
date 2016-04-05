package org.jboss.logaccess.jaxrs;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("/")
public class Resource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("logs")
	public String getPublic(@Context HttpHeaders header, @Context HttpServletResponse response){
		Client client = new Client("http://127.0.0.1:9990");
		
		String data = "{\"operation\":\"read-log-file\",\"address\":[\"subsystem\",\"logging\"],\"name\":\"server.log\",\"lines\":\"-1\"}";
		String result = client.post("management", data);
		return result;
	}

}
