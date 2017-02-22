package org.soa.ws.tp8;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "org.soa.ws.tp8.Convertisseur")
@HandlerChain(file="handler-chain.xml")
public class ConvertisseurImpl implements Convertisseur {
	
	@Override
	@WebMethod
	public double getDinarFromEuro(double euro) {
		return euro * 180;
	}

	@Override
	@WebMethod
	public double getEuroFromDinar(double dinar) {
		return dinar / 180;
	}

}
