package com.gtasoft.godofwind.ressource;

import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

public class NetworkTools {

    public boolean connect = false;
    public boolean checking = false;

    public NetworkTools() {
        isNetworkConnected();

    }

    public boolean isNetworkConnected() {
        if (!checking) {
            checking = true;
            Timer validTimer = new Timer();

            validTimer.schedule(new TimerTask() {
                @Override
                public void run() {

                    try {
                        try {
                            InetAddress ipAddr = InetAddress.getByName("torr-penn.com");

                            String machine = null;
                            if (ipAddr != null) {
                                machine = ipAddr.getHostAddress();
                                //System.out.println(" machine : " + ipAddr);
                            }
                            if (machine != null && !machine.equals("")) {
                                //  System.out.println("TRUE  ipaddr : " + ipAddr);
                                connect = true;
                            } else {
                                //System.out.println("FALSE  ipaddr : " + ipAddr);
                                connect = false;
                            }
                        } catch (java.net.UnknownHostException ue) {
                            if (ue != null) {
                                System.out.println("///// ipaddr : unknown host exception ");

                            }
                            connect = false;
                        }

                    } catch (Exception e) {
                        System.out.println("///// ipaddr : exception + mess :" + e.toString());
                        connect = false;
                    }
                    checking = false;
                }
            }, 5);
        }
        //   System.out.println("XXXXXXX  ipaddr : " + connect);
        return connect;
    }


}
