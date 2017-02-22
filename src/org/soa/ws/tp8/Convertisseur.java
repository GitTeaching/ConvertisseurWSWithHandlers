package org.soa.ws.tp8;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface Convertisseur {
	
	@WebMethod
	public double getDinarFromEuro(double euro); 
	
	@WebMethod
	public double getEuroFromDinar(double dinar);
	
}

