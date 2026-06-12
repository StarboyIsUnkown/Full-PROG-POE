/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.github.starboyisunkown.fullchatapp;

/**
 *
 * @author makho
 */
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Random;

public class FullchatApp {

    
    private String firstName;
    private String lastName;
    private String storedPhone;

    //Declare parallel arrays
    private static final int MAX_MESSAGES = 100;
    
    private static String[] storedMessages = new String[MAX_MESSAGES];
    private static String[] messageHashes = new String[MAX_MESSAGES];
    private static String[] messageIds = new String[MAX_MESSAGES];
    private static String[] recipients = new String[MAX_MESSAGES];
    private static String[] senders = new String[MAX_MESSAGES];
    private static String[] statuses = new String[MAX_MESSAGES];

  //USER DETAILS CHECK
    
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        String regex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\\\",.<>/?]).{8,}$";
        return password.matches(regex);
    }

    public boolean checkCellPhoneNumber(String phoneNumber) {
        return Pattern.matches("^\\+27\\d{9}$", phoneNumber);
    }

    public boolean loginUser(String enteredUser, String enteredPass, String storedUser, String storedPass) {
        return enteredUser.equals(storedUser) && enteredPass.equals(storedPass);
    }

    public String returnLoginStatus(boolean loggedIn) {
        return loggedIn ? "Welcome " + firstName + " " + lastName : "Username or password incorrect.";
    }

  //CHATAPP OUR MAIN APP
    
    static class QuickChatApp {
        private int totalMessagesSent = 0;
        private String messagesLog = "";

        public boolean checkMessageID(String id) {
            return id != null && id.length() <= 10;
        }

        public String checkRecipientCell(String recipient) {
            return recipient.matches("^\\+27\\d{9}$") ? "Valid"
                    : "Invalid recipient number. Must start with +27.";
        }

        public String createMessageHash(String id, int num, String body) {
            String[] words = body.trim().split("\\s+");
            String firstWord = words.length > 0 ? words[0] : "";
            String lastWord = words.length > 0 ? words[words.length - 1] : "";
            
            String idPrefix = (id.length() >= 2) ? id.substring(0, 2) : id;
            return (idPrefix + ":" + num + ":" + firstWord + lastWord).toUpperCase();
        }

        public String sentMessage(Scanner in) {
            System.out.println("Select option: [Send] / [Store] / [0] Disregard");
            String choice = in.nextLine().trim();

            if (choice.equalsIgnoreCase("Send")) {
                totalMessagesSent++;
                return "Sent";
            }
            if (choice.equalsIgnoreCase("Store")) {
                totalMessagesSent++;
                return "Stored";
            }
            return "Disregarded";
        }

        public int returnTotalMessages() {
            return totalMessagesSent;
        }

        public void compileAndDisplayMessage(String id, String hash, String recipient,
                                             String body, String status) {
            String receipt =
                    "\nMessage ID: " + id +
                    "\nHash: " + hash +
                    "\nRecipient: " + recipient +
                    "\nMessage: " + body +
                    "\nStatus: " + status + "\n";

            System.out.println(receipt);
            messagesLog += receipt;
        }

        public String printMessages() {
            return messagesLog.isEmpty() ? "No active session logs recorded." : messagesLog;
        }
    }

    
    public static void handleStoredMessagesMenu(Scanner scanner) {
        char choice = ' ';
        while (choice != 'g') {
            System.out.println("\n--------------------------------------------------");
            System.out.println("             STORED MESSAGES SUB-MENU            ");
            System.out.println("--------------------------------------------------");
            System.out.println("a. Display sender and recipient of all messages");
            System.out.println("b. Display the longest stored message");
            System.out.println("c. Search for a message ID");
            System.out.println("d. Search for all messages for a recipient");
            System.out.println("e. Delete a message using the message hash");
            System.out.println("f. Display a report of full details of all messages");
            System.out.println("g. Return to Main Menu");
            System.out.println("--------------------------------------------------");
            System.out.print("Select choice (a-g): ");

            String input = scanner.nextLine().trim().toLowerCase();
            if (input.isEmpty()) continue;
            choice = input.charAt(0);

            switch (choice) {
                case 'a': displaySendersAndRecipients(); break;
                case 'b': displayLongestMessage(); break;
                case 'c': searchByMessageId(scanner); break;
                case 'd': searchByRecipient(scanner); break;
                case 'e': deleteMessageByHash(scanner); break;
                case 'f': displayFullReport(); break;
                case 'g': System.out.println("Exiting sub-menu..."); break;
                default: System.out.println("Invalid entry. Try again.");
            }
        }
    }

    //Sender display with receipt
    static void displaySendersAndRecipients() {
        System.out.println("\n--- Senders and Recipients ---");
        boolean elementsExist = false;
        for (int i = 0; i < MAX_MESSAGES; i++) {
            if (storedMessages[i] != null) {
                System.out.println("Sender: " + senders[i] + " -> Recipient: " + recipients[i]);
                elementsExist = true;
            }
        }
        if (!elementsExist) System.out.println("No stored messages to display.");
    }

    //Show longest message sent
    static void displayLongestMessage() {
        System.out.println("\n--- Longest Stored Message ---");
        int index = -1;
        int max = -1;

        for (int i = 0; i < MAX_MESSAGES; i++) {
            if (storedMessages[i] != null && storedMessages[i].length() > max) {
                max = storedMessages[i].length();
                index = i;
            }
        }

        if (index >= 0) {
            System.out.println("Message ID: " + messageIds[index]);
            System.out.println("Sender: " + senders[index]);
            System.out.println("Recipient: " + recipients[index]);
            System.out.println("Length: " + max + " characters");
            System.out.println("Content: \"" + storedMessages[index] + "\"");
        } else {
            System.out.println("No messages are currently stored.");
        }
    }

    //Message ID search
    static void searchByMessageId(Scanner scanner) {
        System.out.print("Enter Message ID to Search: ");
        String id = scanner.nextLine().trim();

        for (int i = 0; i < MAX_MESSAGES; i++) {
            if (messageIds[i] != null && id.equalsIgnoreCase(messageIds[i])) {
                System.out.println("\nMatch Located!");
                System.out.println("Recipient: " + recipients[i]);
                System.out.println("Message: " + storedMessages[i]);
                return;
            }
        }
        System.out.println("Message ID '" + id + "' not found.");
    }

    //Search all messages
    static void searchByRecipient(Scanner scanner) {
        System.out.print("Enter Recipient Phone (+27...): ");
        String recipient = scanner.nextLine().trim();
        boolean found = false;

        System.out.println("\n--- Search Results for Recipient: " + recipient + " ---");
        for (int i = 0; i < MAX_MESSAGES; i++) {
            if (recipients[i] != null && recipient.equalsIgnoreCase(recipients[i])) {
                System.out.println("[" + messageIds[i] + "] From: " + senders[i] + " -> \"" + storedMessages[i] + "\"");
                found = true;
            }
        }
        if (!found) System.out.println("No records found that match that recipient.");
    }

    //Delete message using hash
    static void deleteMessageByHash(Scanner scanner) {
        System.out.print("Enter Message Hash to Delete: ");
        String hash = scanner.nextLine().trim();

        for (int i = 0; i < MAX_MESSAGES; i++) {
            if (messageHashes[i] != null && hash.equalsIgnoreCase(messageHashes[i])) {
            
                messageIds[i] = null;
                messageHashes[i] = null;
                recipients[i] = null;
                storedMessages[i] = null;
                senders[i] = null;
                statuses[i] = null;
                System.out.println("Message successfully cleared from system arrays.");
                return;
            }
        }
        System.out.println("No active records found matching hash: " + hash);
    }

    //All stored messages and their details
    static void displayFullReport() {
        System.out.println("\n-------------------------------------------------------------------------");
        System.out.printf("%-12s %-18s %-15s %-15s %-10s\n", "ID", "HASH", "SENDER", "RECIPIENT", "STATUS");
        System.out.println("---------------------------------------------------------------------------");
        boolean recordsExist = false;
        
        for (int i = 0; i < MAX_MESSAGES; i++) {
            if (storedMessages[i] != null) {
                System.out.printf("%-12s %-18s %-15s %-15s %-10s\n", 
                        messageIds[i], messageHashes[i], senders[i], recipients[i], statuses[i]);
                recordsExist = true;
            }
        }
        if (!recordsExist) {
            System.out.println("No records stored in data matrices.");
        }
        System.out.println("Record search done");
    }

    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        FullchatApp auth = new FullchatApp();

        System.out.println("---------------------------------");
        System.out.println("    REGISTRATION   ");
        System.out.println("---------------------------------");
        System.out.print("First Name: ");
        auth.firstName = input.nextLine();

        System.out.print("Last Name: ");
        auth.lastName = input.nextLine();

        while (true) {
            System.out.print("Phone (+27): ");
            String phone = input.nextLine().trim();
            if (auth.checkCellPhoneNumber(phone)) {
                auth.storedPhone = phone;
                break;
            }
            System.out.println("Invalid format. Must be +27 followed by 9 digits.");
        }

        String username;
        while (true) {
            System.out.print("Username (Must contain '_' and max 5 chars): ");
            username = input.nextLine().trim();
            if (auth.checkUserName(username)) break;
            System.out.println("Criteria failed. Ensure it contains '_' and is 5 characters or fewer.");
        }

        String password;
        while (true) {
            System.out.print("Password (Min 8 chars, 1 Upper, 1 Digit, 1 Special): ");
            password = input.nextLine().trim();
            if (auth.checkPasswordComplexity(password)) break;
            System.out.println("Password complexity rules not met.");
        }

        System.out.println("\n-------------------------------");
        System.out.println("    LOGIN   ");
        System.out.println("---------------------------------");
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("Enter Login Username: ");
            String u = input.nextLine().trim();

            System.out.print("Enter Login Password: ");
            String p = input.nextLine().trim();

            loggedIn = auth.loginUser(u, p, username, password);
            System.out.println(auth.returnLoginStatus(loggedIn));
        }

        QuickChatApp chat = new QuickChatApp();

        System.out.print("\nConfiguration Setup - Max messages allowed in session: ");
        int maxAllowed = Integer.parseInt(input.nextLine().trim());

        boolean running = true;
        while (running) {
            System.out.println("\n----------------------");
            System.out.println("       MAIN MENU        ");
            System.out.println("------------------------");
            System.out.println("1. Send Message Workspace");
            System.out.println("2. Display Session Logs");
            System.out.println("3. Display Activity Metrics");
            System.out.println("4. Stored Messages");
            System.out.println("5. Quit Application");
            System.out.print("Select Menu Option (1-5): ");

            String choice = input.nextLine().trim();

            switch (choice) {
                case "1":
                    if (chat.returnTotalMessages() >= maxAllowed) {
                        System.out.println("Session transmission threshold reached.");
                        break;
                    }

                    Random random = new Random();
                    StringBuilder messageIDBuilder = new StringBuilder();
                    for (int i = 0; i < 10; i++) {
                        messageIDBuilder.append(random.nextInt(10));
                    }
                    String messageID = messageIDBuilder.toString();

                    String recipient;
                    while (true) {
                        System.out.print("Recipient Mobile Number (+27): ");
                        recipient = input.nextLine().trim();
                        if (chat.checkRecipientCell(recipient).equals("Valid")) {
                            break;
                        }
                        System.out.println("Invalid formatting rules. Must start with +27.");
                    }

                    System.out.print("Message Content: ");
                    String body = input.nextLine();

                    String hash = chat.createMessageHash(messageID, chat.returnTotalMessages() + 1, body);
                    String status = chat.sentMessage(input);

                    if (!status.equals("Disregarded")) {
                        chat.compileAndDisplayMessage(messageID, hash, recipient, body, status);

                        //Scanner for Prev messages
                        int targetIdx = -1;
                        for (int i = 0; i < MAX_MESSAGES; i++) {
                            if (storedMessages[i] == null) {
                                targetIdx = i;
                                break;
                            }
                        }

                        if (targetIdx != -1) {
                            messageIds[targetIdx] = messageID;
                            messageHashes[targetIdx] = hash;
                            recipients[targetIdx] = recipient;
                            storedMessages[targetIdx] = body;
                            senders[targetIdx] = auth.firstName + " " + auth.lastName;
                            statuses[targetIdx] = status;
                        } else {
                            System.out.println("System storage array buffer completely full.");
                        }
                    } else {
                        System.out.println("Message marked as 'Disregarded'. It will not be saved.");
                    }
                    break;

                case "2":
                    System.out.println(chat.printMessages());
                    break;

                case "3":
                    System.out.println("Total Messages Processed This Session: " + chat.returnTotalMessages());
                    break;

                case "4":
                    handleStoredMessagesMenu(input);
                    break;

                case "5":
                    System.out.println("Terminating execution sequence. Safe disconnect initialized.");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option index identifier selected.");
            }
        }
        input.close();
    }
}
