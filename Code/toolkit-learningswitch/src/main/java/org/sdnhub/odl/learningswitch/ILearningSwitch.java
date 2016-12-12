
package org.sdnhub.odl.learningswitch;

import java.net.InetAddress;
import java.util.ArrayList;

public interface ILearningSwitch {
    public void blockProtocol(byte protocol);
    public void unblockProtocol(byte protocol);
    public void blockPort(short port);
    public void unblockPort(short port);
    public void blockIP(String ip);
    public void unblockIP(String ip);
    public void blockMAC(String mac);
    public void unblockMAC(String mac);
    
    public ArrayList<Byte> getBlockedProtocols();
    public ArrayList<Short> getBlockedPorts();
    public ArrayList<InetAddress> getBlockedIPs();
    public ArrayList<Long> getBlockedMACs();
}
