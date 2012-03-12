/*
 * #%L
 * Service Locator Client for CXF
 * %%
 * Copyright (C) 2011 - 2012 Talend Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.talend.esb.servicelocator.cxf.internal;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;

import org.w3c.dom.Node;

import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.MetadataType;
import org.apache.cxf.wsdl.WSAEndpointReferenceUtils;
import org.talend.esb.servicelocator.client.BindingType;
import org.talend.esb.servicelocator.client.SLProperties;
import org.talend.esb.servicelocator.client.SLPropertiesImpl;
import org.talend.esb.servicelocator.client.ServiceLocatorException;
import org.talend.esb.servicelocator.client.SimpleEndpoint;
import org.talend.esb.servicelocator.client.TransportType;
import org.talend.esb.servicelocator.client.internal.SLPropertiesConverter;
import org.talend.esb.servicelocator.client.internal.endpoint.ServiceLocatorPropertiesType;

public class CXFEndpointProvider implements org.talend.esb.servicelocator.client.Endpoint {

    static final String SOAP11_BINDING_ID = "http://schemas.xmlsoap.org/wsdl/soap/";

    static final String SOAP12_BINDING_ID = "http://schemas.xmlsoap.org/wsdl/soap12/";

    static final String JAXRS_BINDING_ID = "http://apache.org/cxf/binding/jaxrs";

    static final String CXF_HTTP_TRANSPORT_ID = "http://cxf.apache.org/transports/http";

    static final String SOAP_HTTP_TRANSPORT_ID = "http://schemas.xmlsoap.org/soap/http";

    private static final Logger LOG = Logger.getLogger(CXFEndpointProvider.class.getName());

    private static final org.apache.cxf.ws.addressing.ObjectFactory WSA_OBJECT_FACTORY =
        new org.apache.cxf.ws.addressing.ObjectFactory();

    private static final org.talend.esb.servicelocator.client.internal.endpoint.ObjectFactory
    SL_OBJECT_FACTORY = new org.talend.esb.servicelocator.client.internal.endpoint.ObjectFactory();

    private static volatile JAXBContext jContext;

    private final QName sName;

    private final EndpointReferenceType epr;

    private final BindingType bindingType;

    private final TransportType transportType;

    private SLProperties props;

    public CXFEndpointProvider(QName serviceName, EndpointReferenceType endpointReference) {
        this(serviceName, null, null, endpointReference);
    }

    public CXFEndpointProvider(QName serviceName, String bindingId, String transportId,
            EndpointReferenceType endpointReference) {
        sName = serviceName;
        epr = endpointReference;
        bindingType = map2BindingType(bindingId);
        transportType = map2TransportType(transportId);
    }

    public CXFEndpointProvider(QName serviceName, String address, SLProperties properties) {
        this(serviceName, createEPR(address, properties));
        props = properties;
    }

    public CXFEndpointProvider(Server server, String address, SLProperties properties) {
        this(getServiceName(server),
                getBindingId(server),
                getTransportId(server),
                createEPR(server, address, properties));
        props = properties;
    }

    @Override
    public QName getServiceName() {
        return sName;
    }

    @Override
    public String getAddress() {
        return epr.getAddress().getValue();
    }

    @Override
    public BindingType getBinding() {
        return bindingType;
    }

    @Override
    public TransportType getTransport() {
        return transportType;
    }

    @Override
    public SLProperties getProperties() {
        return props != null ? props : SLPropertiesImpl.EMPTY_PROPERTIES;
    }

    @Override
    public void writeEndpointReferenceTo(Result result, PropertiesTransformer transformer)
        throws ServiceLocatorException {
        try {
            JAXBElement<EndpointReferenceType> ep =
                WSA_OBJECT_FACTORY.createEndpointReference(epr);
            createMarshaller().marshal(ep, result);
        } catch (JAXBException e) {
            if (LOG.isLoggable(Level.SEVERE)) {
                LOG.log(Level.SEVERE,
                        "Failed to serialize endpoint data", e);
            }
            throw new ServiceLocatorException("Failed to serialize endpoint data", e);
        }
    }

    @Override
    public void addEndpointReference(Node parent) throws ServiceLocatorException {
        serializeEPR(epr, parent);
    }

    private void serializeEPR(EndpointReferenceType wsAddr, Node parent) throws ServiceLocatorException {
        try {
            JAXBElement<EndpointReferenceType> ep =
                WSA_OBJECT_FACTORY.createEndpointReference(wsAddr);
            createMarshaller().marshal(ep, parent);
        } catch (JAXBException e) {
            if (LOG.isLoggable(Level.SEVERE)) {
                LOG.log(Level.SEVERE,
                        "Failed to serialize endpoint data", e);
            }
            throw new ServiceLocatorException("Failed to serialize endpoint data", e);
        }
    }

    private Marshaller createMarshaller() throws JAXBException {
        JAXBContext jc = getContext();
        return jc.createMarshaller();
    }

    private static JAXBContext getContext() throws JAXBException{
        if (jContext == null) {
            jContext = JAXBContext.newInstance(
                    "org.apache.cxf.ws.addressing:" +
                    "org.talend.esb.servicelocator.client.internal.endpoint",
                    SimpleEndpoint.class.getClassLoader());
        }
        return jContext;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof CXFEndpointProvider) {
            CXFEndpointProvider otherEndpoint = (CXFEndpointProvider) other;
            return getAddress().equals(otherEndpoint.getAddress())
                && getServiceName().equals(otherEndpoint.getServiceName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 13;
        int c = getAddress().hashCode();
        result = 23 * result + c;
        c = getServiceName().hashCode();
        result = 23 * result + c;
        return c;
    }

    @Override
    public String toString() {
        return "CXFEndpointProvider with address " + getAddress() + " for service " + getServiceName();
    }

    private static EndpointReferenceType createEPR(String address, SLProperties props) {
        EndpointReferenceType epr = WSAEndpointReferenceUtils.getEndpointReference(address);
        if (props != null) {
            addProperties(epr, props);
        }
        return epr;
    }

    private static EndpointReferenceType createEPR(Server server, String address, SLProperties props) {
        EndpointReferenceType sourceEPR = server.getEndpoint().getEndpointInfo().getTarget();
        EndpointReferenceType targetEPR = WSAEndpointReferenceUtils.duplicate(sourceEPR);
        WSAEndpointReferenceUtils.setAddress(targetEPR, address);

        if (props != null) {
            addProperties(targetEPR, props);
        }
        return targetEPR;
    }

    private static void addProperties(EndpointReferenceType epr, SLProperties props) {
        MetadataType metadata = WSAEndpointReferenceUtils.getSetMetadata(epr);
        ServiceLocatorPropertiesType jaxbProps = SLPropertiesConverter.toServiceLocatorPropertiesType(props);

        JAXBElement<ServiceLocatorPropertiesType>
            slp = SL_OBJECT_FACTORY.createServiceLocatorProperties(jaxbProps);
        metadata.getAny().add(slp);
    }

    private static QName getServiceName(Server server) {
        QName serviceName;
        String bindingId = getBindingId(server);
        EndpointInfo eInfo = server.getEndpoint().getEndpointInfo();
        
        if (JAXRS_BINDING_ID.equals(bindingId)) {
            serviceName = eInfo.getName();
        } else {
            ServiceInfo serviceInfo = eInfo.getService();
            serviceName =  serviceInfo.getName();
        }
        return serviceName;
    }

    private static String getBindingId(Server server) {
        Endpoint ep = server.getEndpoint();
        BindingInfo bi = ep.getBinding().getBindingInfo();
        return bi.getBindingId();
    }

    private static String getTransportId(Server server) {
        return server.getEndpoint().getEndpointInfo().getTransportId();
    }

    private static BindingType map2BindingType(String bindingId) {
        BindingType type;
        if (SOAP11_BINDING_ID.equals(bindingId)) {
            type = BindingType.SOAP11;
        } else if (SOAP12_BINDING_ID.equals(bindingId)) {
            type = BindingType.SOAP12;
        } else if (JAXRS_BINDING_ID.equals(bindingId)) {
            type = BindingType.JAXRS;
        } else {
            type = BindingType.OTHER;
        }
        return type;
    }

    private static TransportType map2TransportType(String transportId) {
        TransportType type;
        if (CXF_HTTP_TRANSPORT_ID.equals(transportId) || SOAP_HTTP_TRANSPORT_ID.equals(transportId)) {
            type = TransportType.HTTP;
        } else {
            type = TransportType.OTHER;
        }
        return type;
    }
}
