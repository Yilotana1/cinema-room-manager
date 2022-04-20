package cinema;

import java.util.*;

public class Cinema {

    public static final int MAX_PRICE = 10;
    public static final int SEATS_LIMIT = 60;
    public static final int MIN_PRICE = 8;
    public static final int PURCHASED = 1;
    public static final int PRINT_SEATS = 1;
    public static final int BUT_TICKET = 2;
    public static final int SHOW_STATISTICS = 3;
    public static final int EXIT = 0;
    public static final int HUNDRED_PERCENT = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();

        int[][] seatsTable = new int[rows][seats];

        System.out.println();

        boolean finished = false;
        while (!finished) {
            int item = chooseFromMenu();
            switch (item) {
                case PRINT_SEATS:
                    printSeats(seatsTable);
                    break;

                case BUT_TICKET:
                    buyTicket(seatsTable);
                    break;

                case SHOW_STATISTICS:
                    showStatistics(seatsTable);
                    break;
                case EXIT:
                    System.out.println(0);
                    finished = true;
                    break;
            }
        }

    }

    private static void showStatistics(int[][] seatsTable) {
        int purchasedTickets = countPurchasedTickets(seatsTable);
        System.out.printf("Number of purchased tickets: %d%n", purchasedTickets);
        int allSeats = seatsTable.length * seatsTable[0].length;
        System.out.printf("Percentage: %.2f%%%n", (float) purchasedTickets * HUNDRED_PERCENT / allSeats);
        System.out.printf("Current income: $%d%n", getCurrentIncome(seatsTable));
        System.out.printf("Total income: $%d%n", getTotalIncome(seatsTable));
    }

    private static int getTotalIncome(int[][] seatsTable) {
        int rows = seatsTable.length;
        int columns = seatsTable[0].length;
        int allSeats = rows * columns;
        if (allSeats < SEATS_LIMIT) {
            return allSeats * MAX_PRICE;
        }
        int frontHalf = rows / 2;
        int backHalf = rows - frontHalf;
        return (frontHalf * MAX_PRICE + backHalf * MIN_PRICE) * columns;
    }

    private static int getCurrentIncome(int[][] seatsTable) {
        int income = 0;
        for (int i = 0; i < seatsTable.length; i++) {
            for (int j = 0; j < seatsTable[0].length; j++) {

                int seat = seatsTable[i][j];
                if (seat == PURCHASED) {
                    income += getTicketPrice(seatsTable.length, seatsTable[0].length, i + 1);
                }
            }
        }
        return income;
    }


    private static int countPurchasedTickets(int[][] seatsTable) {
        int count = 0;
        for (int[] row : seatsTable) {
            for (int seat : row) {
                if (seat == PURCHASED) {
                    count++;
                }
            }
        }
        return count;
    }

    private static void buyTicket(int[][] seatsTable) {
        int rows = seatsTable.length;
        int columns = seatsTable[0].length;
        int chosenRow;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a row number:");
            chosenRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int chosenColumn = scanner.nextInt();
            System.out.println();

            if (wrongInput(rows, columns, chosenRow, chosenColumn)
                    || purchased(chosenRow, chosenColumn, seatsTable)) continue;

            seatsTable[chosenRow - 1][chosenColumn - 1] = PURCHASED;
            scanner.close();
            System.out.printf("Ticket price: $%d%n%n", getTicketPrice(rows, columns, chosenRow));
            break;
        }


    }

    private static boolean purchased(int chosenRow, int chosenColumn, int[][] seatsTable) {
        if (seatsTable[chosenRow - 1][chosenColumn - 1] == PURCHASED) {
            System.out.println("That ticket has already been purchased!\n");
            return true;
        }
        return false;
    }

    private static boolean wrongInput(int rows, int columns, int chosenRow, int chosenColumn) {
        if (chosenRow > rows || chosenRow < 0 || chosenColumn > columns || chosenColumn < 0) {
            System.out.println("Wrong input!\n");
            return true;
        }
        return false;
    }


    private static int getTicketPrice(int rows, int seats, int row) {
        int allSeatsNumber = rows * seats;
        if (allSeatsNumber < SEATS_LIMIT) {
            return MAX_PRICE;
        }
        int frontHalf = rows / 2;
        return row <= frontHalf ? MAX_PRICE : MIN_PRICE;
    }

    private static int chooseFromMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        Scanner scanner = new Scanner(System.in);
        int item = scanner.nextInt();
        scanner.close();
        return item;
    }


    private static void printSeats(int[][] seatsTable) {

        System.out.println("Cinema:");
        for (int i = 0; i < seatsTable.length; i++) {

            if (i == 0) {
                System.out.println(" " + getSequenceView(seatsTable[0].length));
            }

            for (int j = 0; j < seatsTable[i].length; j++) {

                if (j == 0) {
                    System.out.print(i + 1 + " ");
                }

                if (j == seatsTable[i].length - 1) {
                    System.out.print(convertIntoString(seatsTable[i][j]));
                    break;
                }
                System.out.print(convertIntoString(seatsTable[i][j]) + " ");
            }
            System.out.println();
        }

        System.out.println();
    }

    private static String convertIntoString(int i) {
        return i == 0 ? "S" : "B";
    }

    private static String getSequenceView(int n) {
        StringBuilder sBuild = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sBuild.append(i);
            if (i != n) {
                sBuild.append(" ");
            }
        }

        return sBuild.toString();
    }


}