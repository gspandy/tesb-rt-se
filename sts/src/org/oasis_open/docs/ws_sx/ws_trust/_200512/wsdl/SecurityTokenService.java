package org.oasis_open.docs.ws_sx.ws_trust._200512.wsdl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;

/**
 * This class was generated by Apache CXF 2.4.0-SNAPSHOT
 * 2011-01-06T10:25:02.238+02:00
 * Generated source version: 2.4.0-SNAPSHOT
 * 
 */
 
@WebService(targetNamespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/wsdl", name = "SecurityTokenService")
@XmlSeeAlso({org.xmlsoap.schemas.ws._2004._09.policy.ObjectFactory.class, org.oasis_open.docs.ws_sx.ws_trust._200512.ObjectFactory.class, org.w3._2000._09.xmldsig.ObjectFactory.class, org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0.ObjectFactory.class, org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SecurityTokenService {

    @WebResult(name = "RequestSecurityTokenResponse", targetNamespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/", partName = "response")
    @Action(input = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/KET", output = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/KETFinal")
    @WebMethod(operationName = "KeyExchangeToken", action = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/wsdl/KeyExchangeToken")
    public org.oasis_open.docs.ws_sx.ws_trust._200512.RequestSecurityTokenResponseType keyExchangeToken(
        @WebParam(partName = "request", name = "RequestSecurityToken", targetNamespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/")
        org.oasis_open.docs.ws_sx.ws_trust._200512.RequestSecurityTokenType request
    );

    @WebResult(name = "RequestSecurityTokenResponseCollection", targetNamespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/", partName = "responseCollection")
    @Action(input = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Issue", output = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTRC/IssueFinal")
    @WebMethod(operationName = "Issue", action = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/wsdl/Issue")
    public org.oasis_open.docs.ws_sx.ws_trust._200512.RequestSecurityTokenResponseCollectionType issue(
        @WebParam(partName = "request", name = "RequestSecurityToken", targetNamespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/")
        org.oasis_open.docs.ws_sx.ws_trust._200512.RequestSecurityTokenType request
    );

    @WebResult(name = "RequestSecurityTokenResponse", targetNamespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/", partName = "response")
    @Action(input = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Cancel", output = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/CancelFinal")
    @WebMethod(operationName = "Cancel", action = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/wsdl/Cancel")
    public org.oasis_open.docs.ws_sx.ws_trust._200512.RequestSecurityTokenResponseType cancel(
        @WebParam(partName = "request", name = "RequestSecurityToken", targetNamespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/")
        org.oasis_open.docs.ws_sx.ws_trust._200512.RequestSecurityTokenType request
    );

    @WebResult(name = "RequestSecurityTokenResponse", targetNamespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/", partName = "response")
    @Action(input = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Validate", output = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/ValidateFinal")
    @WebMethod(operationName = "Validate", action = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/wsdl/Validate")
    public org.oasis_open.docs.ws_sx.ws_trust._200512.RequestSecurityTokenResponseType validate(
        @WebParam(partName = "request", name = "RequestSecurityToken", targetNamespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/")
        org.oasis_open.docs.ws_sx.ws_trust._200512.RequestSecurityTokenType request
    );

    @WebResult(name = "RequestSecurityTokenResponseCollection", targetNamespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/", partName = "responseCollection")
    @WebMethod(operationName = "RequestCollection", action = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/wsdl/RequestCollection")
    public org.oasis_open.docs.ws_sx.ws_trust._200512.RequestSecurityTokenResponseCollectionType requestCollection(
        @WebParam(partName = "requestCollection", name = "RequestSecurityTokenCollection", targetNamespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/")
        org.oasis_open.docs.ws_sx.ws_trust._200512.RequestSecurityTokenCollectionType requestCollection
    );

    @WebResult(name = "RequestSecurityTokenResponse", targetNamespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/", partName = "response")
    @Action(input = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Renew", output = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/RenewFinal")
    @WebMethod(operationName = "Renew", action = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/wsdl/Renew")
    public org.oasis_open.docs.ws_sx.ws_trust._200512.RequestSecurityTokenResponseType renew(
        @WebParam(partName = "request", name = "RequestSecurityToken", targetNamespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/")
        org.oasis_open.docs.ws_sx.ws_trust._200512.RequestSecurityTokenType request
    );
}