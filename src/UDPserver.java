//import java.io.*;
//import java.net.*;
//
//class UDPServer {
//    public static void main(String args[]) throws Exception {
//        DatagramSocket serverSocket = new DatagramSocket(9876);
//        byte[] receiveData = new byte[1024];
//        byte[] sendData = new byte[1024];
//        while(true) {
//            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//            serverSocket.receive(receivePacket);
//            String sentence = new String(receivePacket.getData());
//            InetAddress IPAddress = receivePacket.getAddress();
//            int port = receivePacket.getPort();
//
//            // Разделите предложение на числа и операцию
//            String[] parts = sentence.split(" ");
//            double num1 = Double.parseDouble(parts[0]);
//            double num2 = Double.parseDouble(parts[1]);
//            String operation = parts[2];
//
//            // Выполните операцию и преобразуйте результат в строку
//            double result = MathOperation.performOperation(num1, num2, operation);
//            String resultStr = Double.toString(result);
//            sendData = resultStr.getBytes();
//
//            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
//            serverSocket.send(sendPacket);
//        }
//    }
//}
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPserver {

    public static void main(String[] args) throws IOException, SocketException {
        int port = 4444;

        DatagramSocket socket = new DatagramSocket(port);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Check if the port is available
            if (socket.isBound()) {
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (true) {
            System.out.print("Enter two numbers and an operation (e.g., 10 2 +): ");
            String input = scanner.nextLine();

            // Check if the input string is empty
            if (input.isEmpty()) {
                continue;
            }

            String[] numbersAndOperation = input.split(" ");
            double number1 = Double.parseDouble(numbersAndOperation[0]);
            double number2 = Double.parseDouble(numbersAndOperation[1]);
            char operation = numbersAndOperation[2].charAt(0);

            double result = 0;
            switch (operation) {
                case '*':
                    result = number1 * number2;
                    break;
                case '/':
                    if (number2 == 0) {
                        throw new ArithmeticException("Cannot divide by zero");
                    }
                    result = number1 / number2;
                    break;
                case '+':
                    result = number1 + number2;
                    break;
                case '-':
                    result = number1 - number2;
                    break;
                default:
                    throw new RuntimeException("Unknown operation: " + operation);
            }

            byte[] buffer = String.valueOf(result).getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, socket.getLocalAddress(), socket.getLocalPort());
            socket.send(packet);
        }

    }
}
