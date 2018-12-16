package com.example.umairbscs.saveget.Model;

public class Computer


{

    private String computerName;
    private  int computerPower;
    private  int computerSpeed;
    private  int computerRam;
    private String computerScreen;
    private String computerKeyboard;
    private String computerCpu;
    public Computer(){}

    public Computer(String computerName, int computerPower, int computerSpeed, int computerRam, String computerScreen, String computerKeyboard, String computerCpu) {
        this.computerName = computerName;
        this.computerPower = computerPower;
        this.computerSpeed = computerSpeed;
        this.computerRam = computerRam;
        this.computerScreen = computerScreen;
        this.computerKeyboard = computerKeyboard;
        this.computerCpu = computerCpu;
    }

    public String getComputerName() {
        return computerName;
    }

    public int getComputerPower() {
        return computerPower;
    }

    public int getComputerSpeed() {
        return computerSpeed;
    }

    public int getComputerRam() {
        return computerRam;
    }

    public String getComputerScreen() {
        return computerScreen;
    }

    public String getComputerKeyboard() {
        return computerKeyboard;
    }

    public String getComputerCpu() {
        return computerCpu;
    }
}
