public class HousemateListTester {

    public static void main(String[] args) {
        char choice;
        int size;
        HousemateList list; // declare PaymentList object to test
    
        // get size of list
        System.out.print("Size of list? ");
        size = EasyScanner.nextInt();
        while(size < 0){
            System.out.println("Please enter a valid number");
            System.out.print("Size of list? ");
            size = EasyScanner.nextInt();
        }
        list = new HousemateList(size); // create object to test
        // menu
        do {
            // display options
            System.out.println();
            System.out.println("[1] Add housemate");
            System.out.println("[2] Remove housemate");
            System.out.println("[3] Display housemate");
            System.out.println("[4] Is full");
            System.out.println("[5] Get housemate");
            System.out.println("[6] Add housemate payment");
            System.out.println("[7] Get total");
            System.out.println("[8] Get total owed");
            System.out.println("[9] Quit");
            System.out.println();
            System.out.print("Enter a choice [1-9]: ");
            // get choice
            choice = EasyScanner.nextChar();
            System.out.println();
            // process choice
            switch(choice) {
                case '1': addH(list); break;
                case '2': removeH(list); break;
                case '3': display(list); break;
                case '4': isfull(list); break;
                case '5': getH(list); break;
                case '6': addPaymentH(list); break;
                case '7': total(list); break;
                case '8': totalPayments(list); break;
                case '9': System.out.println("Testing complete"); break;
                default: System.out.print("1-9 only");
            }
        } while (choice != '9');
    }

    // Option 1: Add
    private static void addH(HousemateList listIn) {  
        System.out.print("Enter Name: ");
        String name = EasyScanner.nextString();
        System.out.print("Enter Room Number: ");
        int roomN = EasyScanner.nextInt();
        Housemate h = new Housemate(name, roomN);
        boolean ok = listIn.addHousemate(h); // value of false sent back if unable to add
        if (!ok) { // check if item was not added
            System.out.println("ERROR: List is full");
        }
    }

    // Option 1: Add
    private static void removeH(HousemateList listIn) {  
        System.out.print("Enter Room Number: ");
        int roomN = EasyScanner.nextInt();
        Housemate h = listIn.search(roomN);
        boolean ok = listIn.removeHousemate(roomN);
        if (!ok) { // check if item was not added
            System.out.println("ERROR: No housemate found in that room");
        }
        else{
            System.out.println("Roomate " + h.getName() + " removed");
        }
    }

    // Option 3: Display
    private static void display(HousemateList listIn) {  
        System.out.println("HOUSEMATES ENTERED");
        System.out.println(listIn); // calls toString method of PaymentList
    }

    // Option 4: Full
    private static void isfull(HousemateList listIn) {  
        if (listIn.isFull()) {
            System.out.println("List is full");
        }
        else {
            System.out.println("List is NOT full");
        }
    }  


    // Option 5: Get housemate
    private static void getH(HousemateList listIn){
        System.out.println("1. Find by room number");
        System.out.println("2. Find by place in list");
        System.out.print("Enter a choice: ");
        char choice = EasyScanner.nextChar();
        System.out.println();
        if(choice == '1'){
            System.out.print("Enter room number: ");
            int roomN = EasyScanner.nextInt();
            System.out.println(roomN);
            Housemate h = listIn.search(roomN);
            if(h == null){
                System.out.println("INVALID ROOM NUMBER");
                return;
            }
            System.out.println(h);
        }
        else if(choice == '2'){
            System.out.print("Enter housemate number to retrieve: ");
            int listN = EasyScanner.nextInt();
            System.out.println(listN);
            Housemate h = listIn.getHousemate(listN);
            if(h == null){
                System.out.println("INVALID HOUSEMATE NUMBER");
                return;
            }
            System.out.println(h);
        }
        else{
            System.out.println("1-2 only");
            getH(listIn);
        }
    }

    // Option 6; Add housemate payment
    public static void addPaymentH(HousemateList listN){
        System.out.print("Enter Room Number: ");
        int roomN = EasyScanner.nextInt();
        Housemate h = listN.search(roomN);
        if(h == null){
            System.out.println("Invalid room number");
            addPaymentH(listN);
            return;
        }
        System.out.print("Enter Month: ");
        String month = EasyScanner.nextString();
        System.out.print("Enter Amount: ");
        double amount = EasyScanner.nextDouble();
        Payment p = new Payment(month, amount);
        h.makePayment(p);
    }

    // Option 7: Get Total
    public static void total(HousemateList listN){
        System.out.print("TOTAL HOUSEMATES SO FAR: ");
        System.out.println(listN.getTotal()); 
    }

    // Option 7: Get Total
    public static void totalPayments(HousemateList listN){
        int total = 0;
        for (int i = 0; i < listN.getTotal(); i++) {
            Housemate curH = listN.getHousemate(i+1);
            for(int j = 0; j < curH.getPayments().getTotal(); j++){
                Payment curP = curH.getPayments().getPayment(j+1);
                total += curP.getAmount();
            }
        }
        System.out.print("TOTAL AMOUNT OWED IN PAYMENTS: ");
        System.out.println(total);
    }

}
