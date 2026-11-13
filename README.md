# Ballot Freedom

A simple Java console application for running a small election. Set an election name, add candidates, record votes by voter ID, open or close voting, and view vote totals and percentages.

## Requirements

- Java JDK 8 or later

## Run

From the project directory, compile and launch the app:

```bash
javac Main.java
java Main
```

## Menu options

1. Set the election name
2. Add candidates to the ballot
3. Cast a vote using a voter ID
4. View vote totals, percentages, and the current leader
5. Open or close voting
0. Exit

The app prevents a voter ID from voting more than once during a session. Election data is kept in memory, so it resets when the app exits.
