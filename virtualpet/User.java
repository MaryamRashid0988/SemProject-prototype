package virtualpet;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.*;
import java.time.*;

public class User {
    private String username;
    protected Pet[] pets;
    private int money;
    List<Food> foodInventory;
    List<Toy> toyInventory;
    private TaskType currentTask;
    private boolean taskCompleted;
    private LocalDateTime lastTaskTime;
    DailyTask dailyTask;
    private boolean fedPetToday;
    private boolean playedWithPetToday;
    private boolean putPetToSleepToday;
    private boolean evolvedPetToday;


    public User(String username, int initialMoney) {
        this.username = username;
        this.money = initialMoney;
        this.pets = new Pet[0];
        this.foodInventory = new ArrayList<>();
        this.toyInventory = new ArrayList<>();
        this.lastTaskTime = LocalDateTime.now().minusHours(25); // ensure task is available immediately
        this.currentTask = getCurrentTask();
        this.taskCompleted=false;
        this.dailyTask= generateRandomTask();
        this.resetDailyTaskFlags();
    }

    public boolean buyPet(Pet pet) {
        if (money >= pet.price) {
            money -= pet.price;
            pets = Arrays.copyOf(pets, pets.length + 1);
            pets[pets.length - 1] = pet;
            System.out.println(username + " has bought a " + pet.name + "!");
            return true;
        } else {
            System.out.println(username + " doesn't have enough money to buy this pet.");
            return false;
        }
    }

    public void buyFood(Food food) {
        if (money >= food.price) {
            money -= food.price;
            foodInventory.add(food);
            System.out.println("Bought " + food.name + " for $" + food.price);
        } else {
            System.out.println("Not enough money to buy " + food.name);
        }
    }

    public void buyToy(Toy toy) {
        if (money >= toy.price) {
            money -= toy.price;
            toyInventory.add(toy);
            System.out.println("Bought " + toy.name + " for $" + toy.price);
        } else {
            System.out.println("Not enough money to buy " + toy.name);
        }
    }

    public List<Food> getFoodInventory() {
        return foodInventory;
    }
    public List<Toy> getToyInventory() { return toyInventory; }

    public void removeFood(Food food) {
        foodInventory.remove(food);
    }

    public void earnMoney(int amount) {
        money += amount;
        System.out.println(username + " earned $" + amount + "!");
    }

    public void spendMoney(int amount) {
        money -= amount;
        System.out.println(username + " spent $" + amount + ".");
    }

    public int getMoney() {
        return money;
    }

    public void showStatus() {
        System.out.println("\n== " + username + "'s STATUS ==");
        System.out.println("MONEY: $" + money);
        if (pets.length > 0) {
            for (Pet pet : pets) {
                if (pet.isAlive()) pet.displayStatus();
            }
        } else {
            System.out.println("No pets yet.");
        }
        System.out.println("FOOD INVENTORY:");
        for (int i = 0; i < foodInventory.size(); i++) {
            Food food = foodInventory.get(i);
            System.out.println((i + 1) + ". " + food.name + " [Hunger -" + food.hungerReduction + " Energy +"+ food.energyIncrease+", Cost: $" + food.price + "]");
        }
        System.out.println("TOY INVENTORY:");
        for (int i = 0; i < toyInventory.size(); i++) {
            Toy toy = toyInventory.get(i);
            System.out.println((i + 1) + ". " + toy.name + " [Mood +" + toy.moodIncrease + " Energy -"+ toy.energyDecrease+ ", Cost: $" + toy.price + "]");
        }
    }

    public TaskType getCurrentTask() {
        return currentTask;
    }

    public boolean isTaskCompleted() {
        return taskCompleted;
    }
    public void completeTask(TaskType actionType) {
        if (!taskCompleted && actionType == currentTask) {
            taskCompleted = true;
            earnMoney(30); // fixed reward, or adjust per task
            System.out.println("🎉 Daily task completed! Earned $30.");
        }
    }

    public DailyTask generateRandomTask(){
        TaskType[] values = TaskType.values();
        int index = (int) (Math.random() * values.length);


        TaskType type = values[index];
        int reward = switch (type) {
            case FEED_PET -> 20;
            case PLAY_WITH_PET -> 30;
            case MAKE_PET_SLEEP -> 15;
            case EVOLVE_PET -> 50;
        };
        return new DailyTask(type,reward);

    }

    public DailyTask getDailyTask() {
        return dailyTask;
    }

    public void markFedPet() {
        this.fedPetToday = true;
        checkAndCompleteTask(TaskType.FEED_PET);
    }

    public void markPlayedWithPet() {
        this.playedWithPetToday = true;
        checkAndCompleteTask(TaskType.PLAY_WITH_PET);
    }

    public void markPutPetToSleep() {
        this.putPetToSleepToday = true;
        checkAndCompleteTask(TaskType.MAKE_PET_SLEEP);
    }

    public void markEvolvedPet() {
        this.evolvedPetToday = true;
        checkAndCompleteTask(TaskType.EVOLVE_PET);
    }

    public void checkAndCompleteTask(TaskType type) {
        if (dailyTask.isCompleted()) {
            return;
        }

        if (dailyTask.getTaskType() != type) {
            return;
        }

        boolean conditionsMet = false;
        switch (type) {
            case FEED_PET:
                conditionsMet = fedPetToday;
                break;
            case PLAY_WITH_PET:
                conditionsMet = playedWithPetToday;
                break;
            case MAKE_PET_SLEEP:
                conditionsMet = putPetToSleepToday;
                break;
            case EVOLVE_PET:
                conditionsMet = evolvedPetToday;
                break;
        }

        if (conditionsMet) {
            dailyTask.complete(this);
        }
    }

    public void resetDailyTaskFlags() {
        fedPetToday = false;
        playedWithPetToday = false;
        putPetToSleepToday = false;
        evolvedPetToday = false;
    }


}
