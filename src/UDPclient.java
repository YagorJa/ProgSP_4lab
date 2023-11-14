//import java.io.*;
//import java.net.*;
//
//class UDPClient {
//    public static void main(String args[]) throws Exception {
//        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
//        DatagramSocket clientSocket = new DatagramSocket();
//        InetAddress IPAddress = InetAddress.getByName("hostname");
//        byte[] sendData = new byte[1024];
//        byte[] receiveData = new byte[1024];
//
//        System.out.println("Введите два числа и операцию:");
//        String sentence = inFromUser.readLine();
//        sendData = sentence.getBytes();
//
//        // Отправка UDP-пакета серверу
//        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
//        clientSocket.send(sendPacket);
//
//        // Получение UDP-пакета от сервера
//        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//        clientSocket.receive(receivePacket);
//        String modifiedSentence = new String(receivePacket.getData());
//        System.out.println("Ответ сервера:" + modifiedSentence);
//        clientSocket.close();
//    }
//}
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPclient {

    public static void main(String[] args) throws IOException, SocketException {
        Scanner scanner = new Scanner(System.in);

        DatagramSocket socket = new DatagramSocket();
        System.out.print("Enter the server's address: ");
        String address = scanner.nextLine();
        System.out.print("Enter the server's port: ");
        int port = scanner.nextInt();

        while (true) {
            System.out.print("Enter two numbers and an operation (e.g., 10 2 +): ");
            String input = scanner.nextLine();

            String[] numbersAndOperation = input.split(" ");
            double number1 = Double.parseDouble(numbersAndOperation[0]);
            double number2 = Double.parseDouble(numbersAndOperation[1]);
            char operation = numbersAndOperation[2].charAt(0);

            byte[] buffer = input.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(address), port);
            socket.send(packet);

            byte[] responseBuffer = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
            socket.receive(responsePacket);

            String response = new String(responsePacket.getData());
            System.out.println("Result: " + response);
        }
    }
}
