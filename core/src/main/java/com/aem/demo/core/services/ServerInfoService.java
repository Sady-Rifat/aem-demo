package com.aem.demo.core.services;

import org.osgi.service.component.annotations.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component(service = ServerInfoService.class)
public class ServerInfoService {
    public InetAddress getHostAddress() throws UnknownHostException {
        InetAddress ip;
        ip = InetAddress.getLocalHost();
        return ip;
    }
}
