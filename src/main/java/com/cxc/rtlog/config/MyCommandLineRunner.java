package com.cxc.rtlog.config;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Resource
    private Environment env;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("服务启动成功！,运行地址：");
        String runningPort = "8080";
        if (env.getProperty("server.port") != null) {
            runningPort = env.getProperty("server.port");
        }
        // 获取本机ip地址
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
                        System.out.println("网卡："+networkInterface.getName()+ " http://" + inetAddress.getHostAddress()+":"+runningPort+"/rtLog");
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
