package rabbitmq.client;

import java.sql.Timestamp;
import java.util.*;


public class Payment {
    public static List<String> clients = Arrays.asList(new String[]{"0901", "0902", "0903"});
    private int amount;
    private String sendTo;
    private Timestamp when;
    private String sender;
    private String purpose;


    public static Payment getRandomPayment(String sender, String purpose) {
        Payment p = new Payment();
        p.sender = sender;
        p.when = new Timestamp(System.currentTimeMillis());
        p.sendTo = getRandomSendTo(sender);
        p.amount =  (new Random()).nextInt(10) + 1;
        p.purpose = purpose;
        return p;
    }

    public static Payment getRandomCreditPayment(String sender) {
        Payment p = new Payment();
        p.sender = sender;
        p.when = new Timestamp(System.currentTimeMillis());
        p.sendTo = clients.get(new Random().nextInt(clients.size()));
        p.amount =  ((new Random()).nextInt(4) + 1) * 5; // credits 5, 10, 15, 20
        return p;
    }

    private static String getRandomSendTo(String sender){
        int idx;
        int senderIdx = clients.indexOf(sender);
        while((idx = new Random().nextInt(clients.size())) == senderIdx ){}
        return clients.get(idx);
    }

    public int getAmount() {
        return amount;
    }

    public String getSendTo() {
        return sendTo;
    }

    public Timestamp getWhen() {
        return when;
    }

    public String getSender() {
        return sender;
    }

    public String getPurpose(){ return purpose; }

    @Override
    public String toString() {
        return "Payment: " + sender + " -> " + sendTo + " ("  + amount + ", " + when + (purpose == null ?")" :(", purpose: " + purpose + ")"));
    }
}
