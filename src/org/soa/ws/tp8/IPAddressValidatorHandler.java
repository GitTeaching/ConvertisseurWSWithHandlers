package org.soa.ws.tp8;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

public class IPAddressValidatorHandler implements SOAPHandler<SOAPMessageContext> {

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		System.out.println("\nServer : handleMessage ...");
		
		boolean isRequest = (boolean)context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		
		if(!isRequest) {
				
			try {
				
				SOAPMessage soapMsg = context.getMessage();
				SOAPHeader soapHeader = soapMsg.getSOAPHeader();
				
				if(soapHeader == null) {
					//No Header Found
					handleFault(context);
					setFaultMessage(soapMsg, "No Header Found");
				}
				else {
					Iterator it = soapHeader.extractAllHeaderElements();
					
					if(it==null || !it.hasNext()) {
						//No ipAddress Block Found
						handleFault(context);
						setFaultMessage(soapMsg, "No ipAddress block found");
					}
					else {
						Node ipNode = (Node) it.next();
						
						String ipValue;
						if(ipNode == null) {
							//No IP Address Found
							ipValue=null;
							handleFault(context);
							setFaultMessage(soapMsg, "No IP Address Found");
						}
						else {
							ipValue = ipNode.getValue();
						}
						
						if(!ipValue.equals("127.0.0.1")) {
							//Access Denied! IP address invalid
							handleFault(context);
							setFaultMessage(soapMsg, "IP Address Invalid ! Access Denied");
						}
					}				
				}			
			} catch (SOAPException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public void setFaultMessage(SOAPMessage soapMsg, String faultMsg) {
		
		try {
			
			SOAPBody soapBody = soapMsg.getSOAPBody();
			SOAPFault soapFault = soapBody.addFault();
			soapFault.setFaultString(faultMsg);
			throw new SOAPFaultException(soapFault);
			
		} catch (SOAPException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		System.out.println("\nServer : handleFault ...");
		return true;
	}

	@Override
	public void close(MessageContext context) {
		System.out.println("\nServer : close ...");

	}

	@Override
	public Set<QName> getHeaders() {
		System.out.println("\nServer : getHeaders ...");
		return null;
	}

}
