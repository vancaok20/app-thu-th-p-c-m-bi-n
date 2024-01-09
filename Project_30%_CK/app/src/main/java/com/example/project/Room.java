package com.example.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private String name;
    private List<Device> listDevice;


    public Room(String name, List<Device> listDevice) {
        this.name = name;
        this.listDevice = listDevice;
    }


    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void addDevice(Device dv){
        listDevice.add(dv);
    }



    public void removeDevice(int index){
        listDevice.remove(index);
    }

    public int getNumber(){
        return listDevice.size();
    }

    public List<Device> getListDevice() {
        return listDevice;
    }

    public void setListDevice(List<Device> listDevice) {

        this.listDevice = listDevice;
    }

    public static List<Room> allRooms = new ArrayList<>();

    public static List<Device> default_device()
    {
        List<Device> globalDevices = new ArrayList<>();
        globalDevices.add(0, new Device("LED 1",false));
        globalDevices.add(1, new Device("LED 2",false));
        globalDevices.add(2, new Device("LED 3",false));
        return globalDevices;
    }
}
