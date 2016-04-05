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
	@Path("filelogs")
	public String getLogs(@Context HttpHeaders header, @Context HttpServletResponse response){
		Client client = new Client("http://127.0.0.1:9990");
		
		String data = "{\"operation\":\"read-log-file\",\"address\":[\"subsystem\",\"logging\"],\"name\":\"server.log\",\"lines\":\"-1\"}";
		String result = client.post("management", data);
		return result;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("logfiles")
	public String getLogFiles(@Context HttpHeaders header, @Context HttpServletResponse response){
		Client client = new Client("http://127.0.0.1:9990");
		
		String data = "{\"operation\":\"list-log-files\",\"address\":[\"subsystem\",\"logging\"]}";
		String result = client.post("management", data);
		return result;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("podlog")
	public String getPodLogFiles(@Context HttpHeaders header, @Context HttpServletResponse response){
		String host = System.getenv("KUBERNETES_SERVICE_HOST");
		String port = System.getenv("KUBERNETES_SERVICE_PORT");
		String namespace = System.getenv("OPENSHIFT_KUBE_PING_NAMESPACE");
		String pod = System.getenv("HOSTNAME");
		
		Client client = new Client("https://" + host + ":" + port);
		
		String result = client.get("/api/v1/namespaces/" + namespace + "/pods/" + pod + "/log");
		return result;
	}

}
