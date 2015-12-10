package com.vehicletracking.api;

import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vehicletracking.model.User;

@Component
@Path("/web")
public class WebPage {
	protected final Log logger = LogFactory.getLog(WebPage.class);
	@GET
	@Transactional
	@Path("{tripId}")
	public void pageRedirect(@PathParam("tripId") String tripId) {
		logger.info("@@@ inside web api page "+tripId);
		try {
	        java.net.URI location = new java.net.URI("http://52.88.194.128:2020/vehicletracking-spring/Home.html?trip="+tripId);
	        throw new WebApplicationException(Response.temporaryRedirect(location).build());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	}
}
