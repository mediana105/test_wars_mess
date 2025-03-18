package org.itmo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

class SpyMessenger {
    private HashMap<String, LinkedList<Message>> messages;
    void sendMessage(String sender, String receiver, String message, String passcode) {
        Message msg = new Message(sender, receiver, message, passcode);
        LinkedList<Message> receiver_msgs = getMessages(receiver);
        if (receiver_msgs.size() >= 5) {
            receiver_msgs.poll();
        }
        receiver_msgs.add(msg);
        messages.put(receiver, receiver_msgs);
    }

    String readMessage(String user, String passcode) {
        LinkedList<Message> receiver_msgs = getMessages(user);
        Message receiver_msg = null;
        for (Message msg: receiver_msgs) {
            if (Objects.equals(msg.getPasscode(), passcode)) {
                receiver_msg = msg;
            }
        }
        return receiver_msg.getMessage();
    }
    private class Message{
        private String sender;
        private String receiver;
        private String message;
        private String passcode;
        public Message(String sender, String receiver, String message, String passcode) {
            this.sender = sender;
            this.receiver = receiver;
            this.message = message;
            this.passcode = passcode;
        }
        public String getMessage() {
            return message;
        }
        public String getPasscode() {
            return passcode;
        }
    }

    private LinkedList<Message> getMessages(String user) {
        return messages.computeIfAbsent(user, _ -> new LinkedList<>());
    }

}