
package org.sdnhub.odl.learningswitch.northbound;

import org.sdnhub.odl.learningswitch.ILearningSwitch;
import org.codehaus.enunciate.jaxrs.StatusCodes;
import org.codehaus.enunciate.jaxrs.TypeHint;
import org.opendaylight.controller.sal.reader.IReadService;
import org.opendaylight.controller.sal.utils.ServiceHelper;
import org.opendaylight.controller.sal.utils.HexEncode;

import java.net.InetAddress;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.enunciate.jaxrs.ResponseCode;
import org.opendaylight.controller.northbound.commons.RestMessages;
import org.opendaylight.controller.northbound.commons.exception.ServiceUnavailableException;
import org.opendaylight.controller.northbound.commons.exception.UnauthorizedException;
import org.opendaylight.controller.northbound.commons.utils.NorthboundUtils;
import org.opendaylight.controller.sal.authorization.Privilege;




/**
 * Northbound REST API
 *
 * This entire web class can be accessed via /northbound prefix as specified in
 * web.xml
 *
 * <br>
 * <br>
 * Authentication scheme : <b>HTTP Basic</b><br>
 * Authentication realm : <b>opendaylight</b><br>
 * Transport : <b>HTTP and HTTPS</b><br>
 * <br>
 * HTTPS Authentication is disabled by default.
 */
@Path("/")
public class AppNorthbound {
    @Context
    private UriInfo _uriInfo;
    private String username;

    @Context
    public void setSecurityContext(SecurityContext context) {
        if (context != null && context.getUserPrincipal() != null) {
            username = context.getUserPrincipal().getName();
        }
    }

    protected String getUserName() {
        return username;
    }
    
    
    /**
    *
    * Get current function (hub or switch) - GET REST API call
    *
    * @return A response string
    *
    * <pre>
    * Example:
    *
    * Request URL:
    * http://localhost:8080/app/northbound/learningswitch/function/
    *
    * Response body in XML:
    * &lt;?xml version="1.0" encoding="UTF-8" standalone="yes"?&gt;
    * switch
    *
    * Response body in JSON:
    * { "function" : "hub"}
    * </pre>
    */
    @Path("/get/{req}")
    @GET
    @Produces("text/html")
    @StatusCodes()
    public String getData(@PathParam("req") String required) {
        if (!NorthboundUtils.isAuthorized(getUserName(), "default", Privilege.WRITE, this)) {
            throw new UnauthorizedException("User is not authorized to perform this operation");
        }
        ILearningSwitch firewall = (ILearningSwitch) ServiceHelper.getGlobalInstance(ILearningSwitch.class, this);
        if (firewall == null) {
            throw new ServiceUnavailableException("Simple Service " + RestMessages.SERVICEUNAVAILABLE.toString());
        }
        
        String returnData = "";
        int counter = 1;
        
        //Return the data required in String form
        if(required.equals("Protocols"))
        {
	        ArrayList<Byte> protocols = firewall.getBlockedProtocols();
	        
	        if(!protocols.isEmpty())
	        {
		        for(Byte proto : protocols)
		        {
		        	//For odd rows
		        	if((counter % 2) != 0)
		        		returnData += "<tr class=\"pure-table-odd\">\n";
		        	//For even rows
		        	else
		        		returnData += "<tr>\n";
		        	
		        	//Insert the serial number
		        	returnData += "<td>" + counter + "</td>";	        	
		        	
		        	returnData += "<td>" + Byte.toString(proto) + "</td>";
		        	
		        	returnData += "</tr>";
		        	counter++;
		        }
	        }
        }
        else if(required.equals("Ports"))
        {
	        ArrayList<Short> ports = firewall.getBlockedPorts();
	        
	        if(!ports.isEmpty())
	        {
		        for(Short port : ports)
		        {
		        	//For odd rows
		        	if((counter % 2) != 0)
		        		returnData += "<tr class=\"pure-table-odd\">\n";
		        	//For even rows
		        	else
		        		returnData += "<tr>\n";
		        	
		        	//Insert the serial number
		        	returnData += "<td>" + counter + "</td>";
		        	
		        	returnData += "<td>" + Short.toString(port) + "</td>";
		        	
		        	returnData += "</tr>";
		        	counter++;
		        }
	        }
        }
        else if(required.equals("IPs"))
        {
	        ArrayList<InetAddress> ip = firewall.getBlockedIPs();
	        
	        if(!ip.isEmpty())
	        {
		        for(InetAddress addr : ip)
		        {
		        	//For odd rows
		        	if((counter % 2) != 0)
		        		returnData += "<tr class=\"pure-table-odd\">\n";
		        	//For even rows
		        	else
		        		returnData += "<tr>\n";
		        	
		        	//Insert the serial number
		        	returnData += "<td>" + counter + "</td>";
		        	
		        	returnData += "<td>" + addr.getHostAddress() + "</td>";
		        	
		        	returnData += "</tr>";
		        	counter++;
		        }
	        }
        }
        else if(required.equals("MACs"))
        {
	        ArrayList<Long> macs = firewall.getBlockedMACs();
	        for(Long mac : macs)
	        {
	        	//For odd rows
	        	if((counter % 2) != 0)
	        		returnData += "<tr class=\"pure-table-odd\">\n";
	        	//For even rows
	        	else
	        		returnData += "<tr>\n";
	        	
	        	//Insert the serial number
	        	returnData += "<td>" + counter + "</td>";
	        	
	        	returnData += "<td>" + Long.toHexString(mac) + "</td>";
	        	
	        	returnData += "</tr>";
	        	counter++;
	        }
        }
        
        return returnData;
    }


    /**
    *
    * Sample PUT REST API call
    *
    * @return A response string
    *
    *         <pre>
    * Example:
    *
    * Request URL:
    * http://localhost:8080/app/northbound/learningswitch/{uuid}
    *
    * Response body in XML:
    * &lt;?xml version="1.0" encoding="UTF-8" standalone="yes"?&gt;
    * Sample Northbound API
    *
    * Response body in JSON:
    * Sample Northbound API
    * </pre>
    */
   @Path("/block/{req}/{data}")
   @PUT
   @StatusCodes({ @ResponseCode(code = 200, condition = "Data Updated successfully"),
       @ResponseCode(code = 401, condition = "User not authorized to perform this operation"),
       @ResponseCode(code = 500, condition = "Error updating data"),
       @ResponseCode(code = 503, condition = "One or more of service is unavailable")})
   @Consumes({ MediaType.APPLICATION_JSON})
   public Response updateData(@PathParam("req") String required, @PathParam("data") String data) {
       if (!NorthboundUtils.isAuthorized(getUserName(), "default", Privilege.WRITE, this)) {
           throw new UnauthorizedException("User is not authorized to perform this operation");
       }
       ILearningSwitch firewall = (ILearningSwitch) ServiceHelper.getGlobalInstance(ILearningSwitch.class, this);
       if (firewall == null) {
           throw new ServiceUnavailableException("Simple Service " + RestMessages.SERVICEUNAVAILABLE.toString());
       }
       
       //Block the required functionality
       if(required.equals("Protocol"))
       {
    	   byte protocol = Byte.parseByte(data);
    	   firewall.blockProtocol(protocol);
       }
       else if(required.equals("Port"))
       {
    	   short port = Short.parseShort(data);
    	   firewall.blockPort(port);
       }
       else if(required.equals("IP"))
       {
    	   firewall.blockIP(data);
       }
       else if(required.equals("MAC"))
       {
    	   firewall.blockMAC(data);
       }
       
       return Response.status(Response.Status.OK).build();
   }
   

   /**
   *
   * Sample Delete REST API call
   *
   * @return A response string
   *
   *         <pre>
   * Example:
   *
   * Request URL:
   * http://localhost:8080/app/northbound/learningswitch/{uuid}
   *
   * Response body in XML:
   * &lt;?xml version="1.0" encoding="UTF-8" standalone="yes"?&gt;
   * Sample Northbound API
   *
   * Response body in JSON:
   * Sample Northbound API
   * </pre>
   */
  @Path("/unblock/{req}/{data}")
  @DELETE
  @StatusCodes({ @ResponseCode(code = 200, condition = "Data Deleted successfully"),
                 @ResponseCode(code = 401, condition = "User not authorized to perform this operation"),
                 @ResponseCode(code = 500, condition = "Error deleting data"),
                 @ResponseCode(code = 503, condition = "One or more of service is unavailable")})
  @Consumes({ MediaType.APPLICATION_JSON})
  public Response removeData(@PathParam("req") String required, @PathParam("data") String data) {
      if (!NorthboundUtils.isAuthorized(getUserName(), "default", Privilege.WRITE, this)) {
          throw new UnauthorizedException("User is not authorized to perform this operation");
      }
      ILearningSwitch firewall = (ILearningSwitch) ServiceHelper.getGlobalInstance(ILearningSwitch.class, this);
      if (firewall == null) {
          throw new ServiceUnavailableException("Simple Service " + RestMessages.SERVICEUNAVAILABLE.toString());
      }
      
      //Unblock the required functionality
      if(required.equals("Protocol"))
      {
    	   byte protocol = Byte.parseByte(data);
    	   firewall.unblockProtocol(protocol);
      }
      else if(required.equals("Port"))
      {
		   short port = Short.parseShort(data);
		   firewall.unblockPort(port);
      }
      else if(required.equals("IP"))
      {
		   firewall.unblockIP(data);
      }
      else if(required.equals("MAC"))
      {
		   firewall.unblockMAC(data);
      }
      
      return Response.status(Response.Status.OK).build();
  }

}

