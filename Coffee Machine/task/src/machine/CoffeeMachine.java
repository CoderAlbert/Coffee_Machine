package machine;

import java.util.Scanner;

public class CoffeeMachine {
    static int water;
    static int milk;
    static int coffeeBeans;
    static int cash;
    public static int disposableCup;
    public static CoffeeMachine machine;
    final String ENOUGH_RESSOURCES = "ENOUGH_RESSOURCES";

    private CoffeeMachine()
    {
        water = 400;
        milk = 540;
        coffeeBeans = 120;
        cash = 550;
        disposableCup = 9;
    }

    public static CoffeeMachine getInstance()
    {
        if(machine == null) {
            machine = new CoffeeMachine();
        }

        return machine;
    }

    public static void main(String[] args) {
        CoffeeMachine machine = CoffeeMachine.getInstance();
        machine.openMenu();
    }

    public void printMachineStatus()
    {
        System.out.println("\nThe coffee machine has:");
        System.out.format("%d ml of water\n", water);
        System.out.format("%d ml of milk\n", milk);
        System.out.format("%d g of coffee beans\n", coffeeBeans);
        System.out.format("%d disposable cups\n", disposableCup);
        System.out.format("$%d of money\n\n", cash);
    }

    public void openMenu()
    {
        String menuInput = machine.considerAction();
        switch (menuInput)
        {
            case "buy":
                openBuyMenu();
                openMenu();
                break;
            case "fill":
                openFillMenu();
                openMenu();
                break;
            case "take":
                openTakeMenu();
                openMenu();
                break;
            case "remaining":
                printMachineStatus();
                openMenu();
                break;
            case "exit":
                return;
            default:
                System.err.println("Invalid input");
        }

    }

    public void openBuyMenu()
    {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        Scanner scanner = new Scanner(System.in);
        String buyInput = scanner.nextLine();
        Coffee coffee = null;

        switch(buyInput)
        {
            case "1":
                coffee = new Espresso();
                break;
            case "2":
                coffee = new Latte();
                break;
            case "3":
                coffee = new Cappuccino();
                break;
            case "back":
                return;
        }

        buyCoffee(coffee);
    }

    private static int calculateMaxAmountOfCoffee(Coffee coffee)
    {
        int currentWater = water;
        int currentMilk = milk;
        int currentCoffeeBeans = coffeeBeans;
        int currentDisposableCup = disposableCup;

        int maxAmount = 0;
        while(true)
        {
            if(currentWater < coffee.water || currentMilk < coffee.milk || currentCoffeeBeans < coffee.coffeeBeans || currentDisposableCup == 0)  return maxAmount;

            currentWater -= 200;
            currentMilk -= 50;
            currentCoffeeBeans -= 15;
            currentDisposableCup--;
            maxAmount++;
        }
    }

    private void buyCoffee(Coffee coffee)
    {
        int maxPossibleCoffee = calculateMaxAmountOfCoffee(coffee);

        String missingRessource = missingRessourceForCoffee(coffee);

        if(!missingRessource.equals(ENOUGH_RESSOURCES)) {
            System.out.format("Sorry, not enough %s!\n", missingRessource);
            return;
        }

        System.out.println("I have enough resources, making you a coffee!");

        milk -= coffee.milk;
        water -= coffee.water;
        coffeeBeans -= coffee.coffeeBeans;;
        cash += coffee.costs;
        disposableCup -= 1;

    }

    private String missingRessourceForCoffee(Coffee coffee)
    {
        if(coffee.water > water )
        {
            return "water";
        }
        else if(coffee.milk > milk) {
            return "milk";
        }
        else if(coffee.coffeeBeans > coffeeBeans)
        {
            return "coffee beans";
        }
        else if(disposableCup < 1)
        {
            return "disposable cup";
        }
        return ENOUGH_RESSOURCES;
    }

    private void openFillMenu()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWrite how many ml of water you want to add: ");
        int waterToFill = scanner.nextInt();

        System.out.println("Write how many ml of milk you want to add: ");
        int milkToFill = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add: ");
        int coffeeBeansToFill = scanner.nextInt();

        System.out.println("Write how many disposable cups of coffee you want to add: ");
        int disposableCupsToFill = scanner.nextInt();

        fillUpMachine(waterToFill, milkToFill, coffeeBeansToFill, disposableCupsToFill);
    }
    private void fillUpMachine(int waterToFill, int milkToFill, int coffeeBeansToFill, int disposableCupsToFill)
    {
        water += waterToFill;
        milk += milkToFill;
        coffeeBeans += coffeeBeansToFill;
        disposableCup += disposableCupsToFill;
    }

    private void openTakeMenu()
    {
        System.out.format("I gave you $%d\n\n", cash);
        cash = 0;
    }

    public String considerAction()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
        return scanner.nextLine();
    }
}
