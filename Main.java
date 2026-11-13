import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/** A small, in-memory console voting application. */
public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final List<Candidate> candidates = new ArrayList<>();
    private static final Set<String> voters = new HashSet<>();
    private static String electionName = "Community Election";
    private static boolean votingOpen = true;

    private static class Candidate {
        final String name;
        int votes;
        Candidate(String name) { this.name = name; }
    }

    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("       BALLOT FREEDOM CONSOLE");
        System.out.println("====================================");

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1: setupElection(); break;
                case 2: addCandidate(); break;
                case 3: castVote(); break;
                case 4: showResults(); break;
                case 5: votingOpen = !votingOpen; System.out.println("Voting is now " + (votingOpen ? "OPEN." : "CLOSED.")); break;
                case 0: running = false; System.out.println("Thanks for using Ballot Freedom. Goodbye!"); break;
                default: System.out.println("Please choose a valid menu option.");
            }
        }
        input.close();
    }

    private static void showMenu() {
        System.out.println("\nElection: " + electionName + " | Voting: " + (votingOpen ? "OPEN" : "CLOSED"));
        System.out.println("1. Set election name");
        System.out.println("2. Add candidate");
        System.out.println("3. Cast vote");
        System.out.println("4. View results");
        System.out.println("5. Open / close voting");
        System.out.println("0. Exit");
    }

    private static void setupElection() {
        System.out.print("Enter election name: ");
        String name = input.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Election name cannot be empty."); return; }
        electionName = name;
        System.out.println("Election name updated.");
    }

    private static void addCandidate() {
        if (!voters.isEmpty()) {
            System.out.println("Candidates cannot be changed after voting starts. Close and reset the app to start a new ballot.");
            return;
        }
        System.out.print("Candidate name: ");
        String name = input.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Candidate name cannot be empty."); return; }
        for (Candidate c : candidates) {
            if (c.name.equalsIgnoreCase(name)) { System.out.println("That candidate is already on the ballot."); return; }
        }
        candidates.add(new Candidate(name));
        System.out.println(name + " added to the ballot.");
    }

    private static void castVote() {
        if (!votingOpen) { System.out.println("Voting is currently closed."); return; }
        if (candidates.isEmpty()) { System.out.println("No candidates yet. Add candidates first."); return; }
        System.out.print("Enter your voter ID: ");
        String voterId = input.nextLine().trim();
        if (voterId.isEmpty()) { System.out.println("Voter ID cannot be empty."); return; }
        if (voters.contains(voterId)) { System.out.println("This voter ID has already voted."); return; }

        System.out.println("\n--- " + electionName + " ---");
        for (int i = 0; i < candidates.size(); i++) System.out.println((i + 1) + ". " + candidates.get(i).name);
        int selection = readInt("Select candidate number: ");
        if (selection < 1 || selection > candidates.size()) { System.out.println("Invalid candidate number. No vote was recorded."); return; }

        candidates.get(selection - 1).votes++;
        voters.add(voterId);
        System.out.println("Your vote has been recorded. Thank you!");
    }

    private static void showResults() {
        System.out.println("\n=== Results: " + electionName + " ===");
        if (candidates.isEmpty()) { System.out.println("No candidates have been added."); return; }
        int total = 0, highest = -1;
        for (Candidate c : candidates) { total += c.votes; highest = Math.max(highest, c.votes); }
        for (Candidate c : candidates) {
            double percent = total == 0 ? 0 : c.votes * 100.0 / total;
            System.out.printf("%-22s %3d votes  (%5.1f%%)%n", c.name, c.votes, percent);
        }
        System.out.println("Total votes: " + total);
        if (total > 0) {
            List<String> leaders = new ArrayList<>();
            for (Candidate c : candidates) if (c.votes == highest) leaders.add(c.name);
            System.out.println(leaders.size() == 1 ? "Leading: " + leaders.get(0) : "Tie between: " + String.join(", ", leaders));
        }
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        String value = input.nextLine().trim();
        try { return Integer.parseInt(value); }
        catch (NumberFormatException e) { return -1; }
    }
}
