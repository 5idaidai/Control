package cn.edu.zzu.utopiar.bean;
import java.io.*;
import java.util.*;

import javax.comm.*;
import javax.swing.JOptionPane;

import cn.edu.zzu.entity.MutilByte;
import cn.edu.zzu.utopiar.editor.EditorPanel;

public class CommWrite{
    static String messageString = "Hello, world!\n";
    static int test = 0x02;
    static OutputStream outputStream;
    static InputStream inputStream;
    static Thread readThread;
    
    public static SerialPort open(String commName){
    	Enumeration portList = CommPortIdentifier.getPortIdentifiers();
    	CommPortIdentifier portId;
    	SerialPort serialPort = null;

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                // if (portId.getName().equals("COM1")) {
                if (portId.getName().equals(commName)) {
                    try {
                        serialPort = (SerialPort)portId.open("SimpleWriteApp", 2000);
                    } catch (PortInUseException e) {
                    	System.out.println("端口："+portId.getName()+"正在使用！");
                    	JOptionPane.showMessageDialog(null, "端口："+portId.getName()+"正在使用！", "警告", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
        return serialPort;
    }
    
    public static void close(SerialPort serialPort){
    	serialPort.close();
    }

    public static void write(MutilByte bytes, SerialPort serialPort){
		try {
			outputStream = serialPort.getOutputStream();
		} catch (IOException e) {
		}
		try {
			serialPort.setSerialPortParams(19200, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {
		}
		try {
			outputStream.write(bytes.getHead());
			outputStream.write(bytes.getChannel());
			outputStream.write(bytes.getGrad());
			outputStream.write(bytes.getChufang());
			outputStream.write(bytes.getCs());
		} catch (IOException e) {
		}
    }
    
    public static void write(int[] bytes, SerialPort serialPort){
		try {
			outputStream = serialPort.getOutputStream();
		} catch (IOException e) {
		}
		try {
			serialPort.setSerialPortParams(19200, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {
		}
		try {
			for (int i : bytes) {
				outputStream.write(i);
			}
		} catch (IOException e) {
		}
    }
    
    public static void write(int singleByte, SerialPort serialPort){
		try {
			outputStream = serialPort.getOutputStream();
		} catch (IOException e) {
		}
		try {
			serialPort.setSerialPortParams(19200, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {
		}
		try {
			outputStream.write(singleByte);
		} catch (IOException e) {
		}
    }
    
    public static void read(SerialPort serialPort) {
        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {}
		try {
			serialPort.addEventListener(new SerialPortEventListener() {				
				@Override
				public void serialEvent(SerialPortEvent event) {
					switch(event.getEventType()) {
			        case SerialPortEvent.BI:
			        case SerialPortEvent.OE:
			        case SerialPortEvent.FE:
			        case SerialPortEvent.PE:
			        case SerialPortEvent.CD:
			        case SerialPortEvent.CTS:
			        case SerialPortEvent.DSR:
			        case SerialPortEvent.RI:
			        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			            break;
			        case SerialPortEvent.DATA_AVAILABLE:
			            byte[] readBuffer = new byte[20];

			            try {
			                while (inputStream.available() > 0) {
			                    int numBytes = inputStream.read(readBuffer);
			                }
			                ByteArrayInputStream in = new ByteArrayInputStream(readBuffer);                 
			                int result = in.read();
			                EditorPanel.RVO = result;
			                EditorPanel.Arrived = true;
			                readThread.interrupt();
			                System.out.print(result);
			            } catch (IOException e) {}
			            break;
			        }
					
				}
			});
		} catch (TooManyListenersException e) {
		}
        serialPort.notifyOnDataAvailable(true);
        try {
            serialPort.setSerialPortParams(19200,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {}
        readThread = new Thread(){
        	@Override
        	public void run() {
        		// TODO Auto-generated method stub
        		try {
                    Thread.sleep(10000);            
                } catch (InterruptedException e) {}
        	}
        };
        readThread.start();
        if(!readThread.isAlive()){
        	serialPort.close();
        }
        if(readThread.isInterrupted()){
        	serialPort.close();
        }
    }
    
}