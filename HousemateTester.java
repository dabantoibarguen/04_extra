public class HousemateTester {
    public static void main(String[] args) {
        Housemate test = new Housemate("Dexter", 201);
        test.makePayment(new Payment("January", 1500));

        System.out.println(test);
    }
}