package virtualpet;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.time.*;


public class VirtualPetSimulator {

    private static LocalDate lastCheckedDate = LocalDate.now();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();
        User user = new User(userName, 10000);

        while (true) {

            LocalDate today = LocalDate.now();
            if (!today.equals(lastCheckedDate)) {
                lastCheckedDate = today;
                user.resetDailyTaskFlags();
                user.dailyTask = user.generateRandomTask();
                System.out.println("\n=== A new day has begun! ===");
                System.out.println("New daily task available!");
            }

            System.out.println("\nChoose an action:");
            System.out.println("1. Pet shop");
            System.out.println("2. Feed Pet");
            System.out.println("3. Play with Pet");
            System.out.println("4. Sleep Pet");
            System.out.println("5. Try Evolve Pet");
            System.out.println("6. Show Status");
            System.out.println("7. Show daily task");
            System.out.println("8. Play Mini-Games");
            System.out.println("9. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("==== PET SHOP ====");
                    System.out.println("1- Pet \n2- Food \n3- Toys");
                    int shopchoice = scanner.nextInt();
                    if (shopchoice == 1) {


                        PetShop shop = new PetShop();
                        shop.displayPets(user);
                        System.out.print("Choose a pet (1-" + shop.availablePets.length + ") or 0 for Main Manu: ");
                        int petChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (petChoice > 0 && petChoice <= shop.availablePets.length) {
                            Pet selectedPet = shop.availablePets[petChoice - 1];
                            System.out.print("Name your pet: ");
                            String name = scanner.nextLine();
                            selectedPet.setName(name);

                            user.buyPet(selectedPet);


                        }
                        break;

                    }
                    else if(shopchoice==2){
                        PetShop petShop= new PetShop();
                        petShop.displayFood(user);
                        System.out.print("Choose a pet (1-" + petShop.availableFoods.length + ") or 0 for Main Menu: ");
                        int foodChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (foodChoice > 0 && foodChoice <= petShop.availableFoods.length) {
                            Food selectedFood = petShop.availableFoods[foodChoice - 1];


                            user.buyFood(selectedFood);


                        }
                        break;
                    } else if (shopchoice==3) {
                        PetShop petShop = new PetShop();
                        petShop.displayToys(user);
                        System.out.print("Choose a Toy (1-" + petShop.availableToys.length + ") or 0 for Main Manu: ");
                        int toyChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (toyChoice > 0 && toyChoice <= petShop.availableToys.length) {
                            Toy selectedToy = petShop.availableToys[toyChoice - 1];


                            user.buyToy(selectedToy);
                        }
                        break;
                    }

                case 2:
                    if (user.pets.length == 0) {
                        System.out.println("You have no pets!");
                        break;
                    }
                    if (user.getFoodInventory().isEmpty()) {
                        System.out.println("You have no food. Buy some first.");
                    } else {

                        for (int i = 0; i < user.foodInventory.size(); i++) {
                            Food food = user.foodInventory.get(i);
                            System.out.println((i + 1) + ". " + food.name + " (Hunger -" + food.hungerReduction +" Energy +"+ food.energyIncrease+ ", Cost: $" + food.price + ")");
                        }
                        System.out.print("Enter food number to use: ");
                        int foodChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (foodChoice >= 1 && foodChoice <= user.getFoodInventory().size()) {
                            Food selected = user.getFoodInventory().get(foodChoice - 1);

                            for(int i=0; i<user.pets.length;i++){
                                System.out.print(i+1 + ". " );
                                user.pets[i].displayStatus();

                            }
                            System.out.print("Choose pet to feed: ");
                            int petchoice= scanner.nextInt();
                            if (user.pets[petchoice-1].isAlive()) {
                                user.pets[petchoice - 1].feed(selected, user);
                                user.pets[petchoice - 1].checkDeath();
                                user.markFedPet();
                            }
                            user.removeFood(selected);
                        } else {
                            System.out.println("Invalid food selection.");
                        }
                    }
                    break;

                case 3:
                    if (user.getToyInventory().isEmpty()) {
                        System.out.println("You have no Toys. Buy some first.");
                    } else {

                        // Display all pets
                        for (int i = 0; i < user.pets.length; i++) {
                            System.out.print(i + 1 + ". ");
                            user.pets[i].displayStatus();
                        }

                        if (user.pets.length == 0) {
                            System.out.println("You have no pets.");
                        } else {

                            // Choose pet to play with
                            System.out.print("Choose pet: ");
                            int petchoice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            System.out.println("Selected Pet: " + user.pets[petchoice - 1].getName());

                            // Ensure valid pet choice
                            if (petchoice < 1 || petchoice > user.pets.length) {
                                System.out.println("Invalid pet selection. Try again.");
                                break; // Exit if the pet choice is invalid
                            }

                            while (true) {
                                // Display all toys
                                System.out.println("Toys available: " + user.toyInventory.size());
                                for (int i = 0; i < user.toyInventory.size(); i++) {
                                    Toy toy = user.toyInventory.get(i);
                                    System.out.println((i + 1) + ". " + toy.name + " (Mood +" + toy.moodIncrease + ", Cost: $" + toy.price + ", Uses left: " + toy.uses + ")");
                                }
                                System.out.print("Enter Toy number to use: ");
                                int toyChoice = scanner.nextInt();
                                scanner.nextLine(); // Consume newline

                                // Check if the toy choice is valid
                                if (toyChoice >= 1 && toyChoice <= user.getToyInventory().size()) {
                                    Toy selected = user.getToyInventory().get(toyChoice - 1);

                                    // Check if the toy is usable
                                    if (selected.uses > 0) {
                                        System.out.println("Using toy: " + selected.name);
                                        if (user.pets[petchoice - 1].isAlive()) {
                                            user.pets[petchoice - 1].play(selected, user);
                                            user.pets[petchoice - 1].checkDeath();
                                            user.markPlayedWithPet();
                                        }
                                        break; // Exit loop after valid toy is used
                                    } else {
                                        System.out.println("This Toy is Worn-Out :( Choose a different toy!");
                                    }
                                } else {
                                    System.out.println("INVALID TOY SELECTION. Choose again.");
                                }
                            }
                        }
                    }
                    break;



                case 4:
                    for (int i = 0; i < user.pets.length; i++) {
                        System.out.print(i + 1 + ". ");
                        user.pets[i].displayStatus();

                    }
                    System.out.print("Choose pet: ");
                    int petchoice = scanner.nextInt();

                    if (user.pets[petchoice - 1].isAlive() && user.pets[petchoice-1].energy<70) {
                        user.pets[petchoice - 1].sleep(user);
                        user.pets[petchoice - 1].checkDeath();
                        user.markPutPetToSleep();
                    }else{
                        System.out.println(user.pets[petchoice-1].name + " is not tired.");
                    }

                  break;

                case 5:
                    for(int i=0; i<user.pets.length;i++){
                        System.out.print(i+1 + ". " );
                        user.pets[i].displayStatus();

                    }
                    System.out.print("Choose pet: ");
                    petchoice= scanner.nextInt();

                    if (user.pets[petchoice-1].isAlive()) {
                        boolean wasEvolved = user.pets[petchoice-1].evolved;
                        user.pets[petchoice-1].tryEvolve(user);
                        if (!wasEvolved && user.pets[petchoice-1].evolved) {
                            user.markEvolvedPet(); // Add this line
                        }
                    }
                    break;

                case 6:
                    user.showStatus();
                    break;

                case 7:
                    DailyTask task = user.getDailyTask();
                    System.out.println("\n=== DAILY TASK ===");
                    System.out.println("Task: " + task.getTaskType().getDescription());
                    System.out.println("Reward: $" + task.getReward());
                    if (task.isCompleted()) {
                        System.out.println("Status: COMPLETED");
                    } else {
                        System.out.println("Status: Not completed yet");
                        // Show what's needed to complete
                        switch (task.getTaskType()) {
                            case FEED_PET:
                                System.out.println("Action needed: Feed any pet");
                                break;
                            case PLAY_WITH_PET:
                                System.out.println("Action needed: Play with any pet");
                                break;
                            case MAKE_PET_SLEEP:
                                System.out.println("Action needed: Put any pet to sleep");
                                break;
                            case EVOLVE_PET:
                                System.out.println("Action needed: Evolve any pet");
                                break;
                        }
                    }
                    break;

                case 8: // Mini-Games
                    System.out.println("\n=== Mini-Games ===");
                    System.out.println("1. Trivia Game (Win $25)");
                    System.out.println("2. Memory Game (Win $25)");
                    System.out.println("3. Arithmetics Game (Win $30)");


                    int gameChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (gameChoice) {
                        case 1:
                            MiniGames.playTriviaGame(user);
                            break;
                        case 2:
                            MiniGames.playMemoryGame(user);
                        case 3:
                            MiniGames.playArithmeticGame(user);

                        default:
                            System.out.println("Invalid choice!");
                    }
                  break;

                case 9:
                    System.out.println("Thanks for playing!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }


        }
    }

}
