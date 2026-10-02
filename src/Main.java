


// the while loop is erroring for some reason


import java.sql.Array;
import java.util.Scanner;
import java.util.ArrayList;

void main() throws IOException {

Scanner scanner = new Scanner(System.in); // console reader object
    // variable = scanner.nextLine(); to read console
    // scanner.close(); to kill it
    //  scanner.nextLine(); placing this alone will clear the line
boolean playing = true;

    while (playing) {

        int health = 15;
        stickStorage sticks = new stickStorage(); // there should only be one of theses.

        int level = 1;

        printSameLine("Your Goal?");
        pause(1000);
        print(" Get sticks.");
        pause(1500);
        printSameLine("How?");
        pause(1000);
        printSameLine(" Defeat People and take them for your self!");
        pause(1000);


        while (health > 0) { // game loop


            enemy currentEnemy = new enemy("randomName", level);

            print("");
            print("");
            print("you encountered " + currentEnemy.getName() + "!");
            pause(2000);
            print(currentEnemy.getName() + " has " + currentEnemy.getHealth() + " Health!");
            pause(1000);
            print(currentEnemy.getName() + "'s weapon has the name " + currentEnemy.getPrimaryWeapon().getName());
            pause(1000);

            boolean enemyAlive = true;
            while(enemyAlive || health > 0){

            int[] optionList = {1,2,3}; // create options for player action
            int option = requireInt(optionList, "Would you like to \n 1, attack " + currentEnemy.getName() + " \n 2, Swap primaries \n 3, Do nothing \n your current weapon is " + sticks.getPrimary().getName());

            // make game loop
            if (option == 1){
                currentEnemy.attack(sticks.getPrimary());
                pause(1000);
            } else if(option == 2){
                print("what would you like to swap to?");

                int[] weaponList = new int[sticks.getStorage().size()]; // create options for player action
                String weaponListContext = "You choises are: ";
                for(int i = 0; i < sticks.getStorage().size(); i++){
                    weaponListContext = weaponListContext + "\n" + i + ", " + sticks.getStorage().get(i).getName();
                }
                int weaponOption = requireInt(weaponList, weaponListContext );
                sticks.setPrimary(sticks.getStorage().get(weaponOption));

                print("you have picked " + sticks.getStorage().get(weaponOption).getName());
                pause(1000);
            } else{
                print("you have skipped");
                pause(1000);
            }

            if(currentEnemy.getHealth() == 0){
                enemyAlive = false;
                boolean youWant = requireBoolean("would you like " + currentEnemy.name + "'s weapon? \n The weapon is called "
                        + currentEnemy.getPrimaryWeapon().getName() +
                        ". \n Stiffness: " + currentEnemy.getPrimaryWeapon().getStiffness() +
                        "\n Strenght: " + currentEnemy.getPrimaryWeapon().getStrength() + ".");

                if (youWant){
                    sticks.getStorage().add(currentEnemy.getPrimaryWeapon());
                    print("you took " + currentEnemy.getPrimaryWeapon().getName() + "!");
                    pause(500);
                }else{
                    print("you did not take " + currentEnemy.getPrimaryWeapon().getName() + ".");
                    pause(1000);
                }

            } else{ // enemy turn

               health = health - currentEnemy.block(sticks.getPrimary(), sticks);

               if( health <= 0){
                   print("you have dead, :(");
               }else{
                   print("your health is " + health);
               }
               pause(1000);
            }

        }
            level++;
    }

        if(!requireBoolean(" Would you like to reset and continue?")){ // end game if they choose
            playing = false;
        }
    }
}


// functions______________________________________________________________________________________________________


// get console text functions

public int requireInt(int[] validNum, String context){
    // provide but leave list empty if you want any number
    // provide something like this   int[]list = {}; or this         int[]list = {3,2,5};

    Scanner scanner = new Scanner(System.in); // console reader object

    boolean running = true;
    int response = -1;

    String printableList = ""; // a string form of list values

    for(int i = 0; i < validNum.length; i++){ // makes printableList
        printableList = printableList + " - " + validNum[i];
    }
    print(context);
    print("enter number");

    while (running){// loop
        boolean valid = true;

        try{// attempt to put console into int

            response = Integer.parseInt(scanner.nextLine());

        } catch(Exception e){ // int wasn't provided

            print("invalid number" );
            valid = false; // do not allow progression


            pause(500);
            print("");

            print(context);
        }


        if (valid){ //good response

            if(validNum == null || validNum.length == 0 ) { // if list empty then allow any number
                running = false; // close loop

            }else{

            for(int i = 0; i < validNum.length; i++){
                if (response == validNum[i]){ // check if the response is a valid response from the list
                    running = false; // obliterate loop
                }
            }} // for loop and else close
        }

        if(running && valid){ // remind responses
            print("not an option");

            pause(500);
            print("");

            print(context);
            print("choose one of theses: " + printableList);
        }
    }

    return response;

}

public String requireString(String context){

    Scanner scanner = new Scanner(System.in); // console reader object

    boolean running = true;
    String response = "";

    print(context);

    while (running){
        boolean valid = true;


        try{
            response = scanner.nextLine();

        } catch(Exception e){
            print("invalid characters");
            valid = false;

            scanner.nextLine();

            pause(500);
            print("");

            print(context);

        }

        if (valid){
            running = false;
            //scanner.close(); no need this miskate will close the all scanner not just this one
        }
    }
    return response;

}

public boolean requireBoolean(String context){

    Scanner scanner = new Scanner(System.in); // console reader object

    String response = "";

    print(context);
    print("enter yes or no (a boolean)");

    while (true){

        response = scanner.nextLine();
        response = response.toLowerCase();

        if(isBoolean(response).equals("true")){

            return true;
        } else if (isBoolean(response).equals("false")){
            //scanner.close(); no need

            return false;
        } else{
            print("not a boolean");

            pause(500);
            print("");

            print(context);
            print("enter yes or no (a boolean)");
        }

    }

}

//other functions_____________________

// make printing easier
public void print(String text){System.out.println(text);}
public void printSameLine(String text){System.out.print(text);}
// briefly pause program
public void pause(int time) {
    try{
        Thread.sleep(time);
    } catch (Exception e) {
        print("pause function errored");
    }
}

// is this string a kind of boolean?
public String isBoolean(String text){
    // converts a word into a boolean but as a string, because theyre three outputs.
    // can return "true" "false", "invalid"
    text = text.toLowerCase();

    String[] goodWords = {"yes","true","fine","sure","ok","okay","yah","ya", "tung tung tung sahur", "pls","please", "why not"};
    String[] badWords = {"no","false", "nah","na","i dont like you", "i hate you","i dont wanna","meh"};

    for (String word : goodWords){
        if(text.equals(word)){
             return  "true";
        }
    }

    for (String word : badWords){
        if(text.equals(word)){
            return  "false";
        }
    }

    return "invalid";
}

//random name
public String randomName(){

    String[] nameList;
    nameList = new String[]{
            "gorden", "bob", "fobeo", "richard", "tim", "timmmy", "john", "joe",
             "mikey", "dutch", "andrew", "james","brady","jorge","ian","braxton",
            "collins", "Norah","dane","salem","evan","cesar","williams","reed","shiloh","corbin","peyton","colt",
            "hank","madden","tung tung tung", "garry","brendan","nola","clay","tyson","luka",
            "gould","kane","remy","landen","erik","gill","biggie","smalls","martin","jamir","sam",
            "donald", "slone","lu","wall","jett","norris","olson","ashton","reece","miller","tru","march",
            "carson","sahEvil", "sahur","randel","ben","benny","chase","bo","peep","sim","sam","ken","michel",
           "holt","bruce","lee","aden","paris","page","riley","cassidy","carter"
    };

    String name = nameList[randomNumber(0, nameList.length)];

    return name;
}


public String randomLastName(){

    String[] nameList;
    nameList = new String[]{
            "fobeo", "quavoius", "timmmy",  "joe","mr small",
            "bad guy", "meter", "james","brady","jorge","ian","braxton",
            "collins", "Norah","dane","salem","evan","cesar","williams","reed",
            "brendan","nola","clay","reader","tyson","luka","west",
           "cook","biggie","smalls","cruz","jamir","sam","wahsington",
            "donald","Mc", "sir","wall","miller","tru","march",
            "sahEvil", "bo","peep","sim","ken","grey",
            "little", "byrd","ross","lee","page","philips","carter"
    };

    String name = nameList[randomNumber(0, nameList.length)];

    return name;
}


public String randomStickName(){

    String[] nameList;
    nameList = new String[]{
            "Apple", "Ball", "Chair", "Door", "Engine", "Fork", "Glass", "Hat", "Ink", "Jacket",
            "Kettle", "Lamp", "Map", "Notebook", "Pen", "Quilt", "Ring", "Spoon", "Table", "Umbrella",
            "Vase", "Watch", "Xylophone", "Yarn", "Zipper", "Book", "Clock", "Desk", "Eraser", "Fan",
            "Glove", "Key", "Lock", "Mug", "Nail", "Pencil", "Ruler", "1Shoe", "Sock", "Tape",
            "Box", "Bag", "Bottle", "Bowl", "Brush", "Candle", "Coin", "Comb", "Couch", "Cup",
            "Dice", "Flag", "Frame", "Hammer", "Kite", "Mirror", "Pillow", "Radio", "Soap", "Towel",
            "tung tung", "bat", "cool"
    };

    String name = nameList[randomNumber(0, nameList.length)];

    return name + " stick";
}



//random Number
public int randomNumber(int min, int max){

    double number = -1;
    number = Math.random() * (max - min);
    return (int) number + min;

}

// flip a coin
public boolean flipACoin(){

    int bool = randomNumber(0,2);
    if( bool == 0){
        return true;
    }else{
        return false;
    }
}

// pause and play a countdown on this line
public void countDown (int countDown, boolean printStuff){
    int time = countDown;
    for(int i = 0; i < countDown; i++){

        if(printStuff){printSameLine(time + ", ");}
        time--;
        pause(1000);
    }

    if(printStuff){ // add an extra second,  if displaying ui
       printSameLine("0!");
        pause(1500); // make it a little for suspence
    }

}

// mini games---------

//notes
//System.in.available() will check if theres something in the current input stream
//System.in.read will pull a waiting byte out of input stream
// making a buffer will give system.in.read a place to put the input streram i dont want

// wack stuff mini game
public int wackSomething (int speed, int length) throws IOException {
    // returns a damage multiplier (which is 0,1,and 2)
    // the goal is to hit the X or near the X when cursor is on it

    print("");
    print("Type anything to stop the 'V' ");
    print("Try to stop so the 'V' lands on the 'X', landing on the small 'x' is fine to");
    print("(KEEP THE TYPING CURSOR AT THE BOTTOM OF THE CONSOLE OR IT WONT AUTO SCROLL)");
    requireString("Ready? ( type anything to start)");

    boolean didHit = false;
    int cursor = 0;
    String[] hitBar = new String[length]; // 8 characters long


    int target = (int) (Math.random() * hitBar.length); // get a random number between 0 and hitbar.length, always round down
    int[] closeTargets = new int[4]; // get all close indexs to target

    int arrayIndex =0;

    String targetRow = "";// the visible row

    for(int i = 0; i < hitBar.length; i++){

        if( i-target == 2 || i-target == 1 || i-target == -1 || i-target == -2 ) {// is it close to target
            targetRow = targetRow + "x";

            closeTargets[arrayIndex] = i;
            arrayIndex++;

        } else if (i == target){// is it on target
            targetRow = targetRow + "X";
        } else{// is it not close
            targetRow = targetRow  + "-";
        }
    }
    int damage = -1;// initializing damage (this is the thing getting returned)
    int gameI = 0; //how many times has the loop looped
    while(!didHit) {
    gameI++;

            //calculate cursor
        String cursorUI = "";
        for(int i = 0; i < hitBar.length; i++){

            if (i == cursor){
                cursorUI = cursorUI + "V";
            }else{
                cursorUI = cursorUI + " ";
            }
        }
            print("");
            print("");
            print("");
            print("(KEEP THE TYPING CURSOR AT THE BOTTOM OF THE CONSOLE OR IT WONT AUTO SCROLL)  (type anything to hit)");

            print("================================================================");
            print(cursorUI);
            print(targetRow);
            print("================================================================");

            pause(speed*10); // difficulty, 8 is average difficulty for big length

        // starting count down
        if(gameI <= 1){
            print("Starting: "); countDown(3, true);
        }


        if(System.in.available()>0){       // check if they typed something

            //calculate damage

                if( target-cursor == 2 || target-cursor == 1 || target-cursor == -1 || target-cursor == -2 ) {// is it close to target

                    damage = 1; //getting close to target does normal damage
                    print("Hit!");
                } else if (cursor == target){// is it on target
                    damage = 2; // double damage
                    print("DoubleDamage!!");
                } else{// is it not close
                    damage = 0; // missed
                    print("miss..");
                }


            //close loop
            didHit = true;

            //print("hit");
            byte[] buffer = new byte[System.in.available()]; // a array that holds bytes, thats the exact size of whats in the input stream
            System.in.read(buffer); // put bytes from input stream into buffer
        }

        cursor++; // move cursor
        if(cursor > hitBar.length-1){
            cursor = 0;
        }

    }

    pause(500);
    return damage;
}

// block stuff
public double blockSomething(int speed, int length) throws IOException {

    // ask when to start, give intructions too
    print("");
    print("Type anything to move the '<' to other lane.");
    print("Move '<' out of the way of incoming 'X'.");
    print("(KEEP THE TYPING CURSOR AT THE BOTTOM OF THE CONSOLE OR IT WONT AUTO SCROLL)");
    requireString("Ready? ( type anything to start)");

    //length should be over 30
    int hazardAmount = 0; // made when placing obsticles
    double damageMultiplier = 0;

    int cursorYPosition = 0; // cursor position on the y axis
    int cursorXPosition = length-1; // cursor position on the x axis

    char player = '<'; //model

    int gameI = 0; // how many times has the while loop looped

    char[] laneZero = new char[length]; // the top lane
    char[] laneOne = new char[length]; // the bottom lane

    //___________________________________ filling in the lane
    //generate empty lanes
    for(int i = 0; i < laneZero.length; i++){
        laneOne[i] = ' ';
        laneZero[i] = ' ';
    }

    int placement = 0; // should x be on lane 1 or 0
    //hazards on empty lanes
    for(int i = 0; i < laneZero.length; i++){

        int coolerI = i*6;

        if (coolerI + 12  > laneZero.length-1){ i = 2147483640;}else{
             if(placement == 0){    laneZero[coolerI] = 'X'; hazardAmount++; placement = 1;
             }else{  laneOne[coolerI] = 'X'; hazardAmount++; placement = 0; }
        }
    }//___________________________________

    while ( cursorXPosition >= 0){//_____________________ game loop logic
        gameI++;
        if (System.in.available() > 0){ // swap lanes

            if(cursorYPosition == 0){
                cursorYPosition = 1;
            }else{
                cursorYPosition = 0;
            }

            // clears input stream
            byte[] buffer = new byte[System.in.available()]; // a array that holds bytes, thats the exact size of whats in the input stream
            System.in.read(buffer); // put bytes from input stream into buffer
        }

        //make lane visuals
        String lane0 = "";
        String lane1 = "";

        if(cursorYPosition == 0){
            for(int i = 0; i < laneZero.length; i++){
                if(i == cursorXPosition){
                    lane0 = lane0 + player;
                } else{
                    lane0 = lane0 + laneZero[i];
                }
                lane1 = lane1 + laneOne[i];
            }
        }else{
            for(int i = 0; i < laneOne.length; i++){
                if(i == cursorXPosition){
                    lane1 = lane1 + player;
                } else{
                    lane1 = lane1 + laneOne[i];
                }
                lane0 = lane0 + laneZero[i];
            }
        }


        //ui
        print("Type anything to move the '<' to other lane.");
        print("Move '<' out of the way of incoming 'X'.");
        print("(if its not moving try scrolling to the bottom of the console)");
        print("===============================================================");
            print(lane0);
            print(lane1);
        print("===============================================================");


        //take damage logic
        if(cursorYPosition == 0){
            if(laneZero[cursorXPosition] == 'X'){
                damageMultiplier = damageMultiplier + ((double) 1 /hazardAmount);
                print("you got hit");}
        }else{
            if(laneOne[cursorXPosition] == 'X'){
                damageMultiplier = damageMultiplier + ((double) 1 /hazardAmount);
                print("you got hit");
            }
        }

        if (gameI <= 1){
            print("Starting: "); countDown(3,true);
        }

        pause(speed*10);
        cursorXPosition--;
    }//______________________________________

    return damageMultiplier;

}
//

// classes__________________________________________________________________________________

public class stick {

    //metadata-----------
    private String name; // cool name
    //private String wood; // elemental type from pokemon, unnecessary
    private int stiffness; // how many avalible blocks before it breaks // also unnecessary
    private int strength; // damage
    private String attackType; // what minigame plays when attacking, also unnecessary
    private double value; // sell price
    private int id; // creation id, should never change

    public static int totalSticks; // total num of stics made

    // constructor--------
    public stick ( String Name, int Stiff, int Str , String aT) {

        this.name = Name;
        //this.wood = Wood;
        this.stiffness = Stiff;
        this.strength = Str;
        this.attackType = aT;

        this.value = calculateValue();

        totalSticks++; // increase from 0 to 1
        this.id = totalSticks; // assign id
    }


    // functions and stuff-------------
    public double calculateValue () {return (stiffness * strength * 0.005);} // if your stick is trouble enough, it could be negative.

    public boolean isBroken() { if(stiffness <= 0 ){return true;} return false;}

    // gets and sets-------------

    //gets--
    public double getValue (){return value;}
    public int getId (){return id;}
    public String getName (){return name;}
    public int getStiffness(){return stiffness;}
    public int getStrength() {return strength;}

    //sets--
    public void setName (String newName){name = newName;}
    public boolean setStiffness (int newStif){stiffness = newStif; return isBroken();}
    public boolean damageStick() {stiffness--; return isBroken();}
    // crickets

}

public class enemy {

    //metadata------------------------------------

    private int health = 8;
    private stick primaryWeapon; // enemies only get one weapon
    private String name = "-1";

    // attacking
    private int currectAttackIndex = 0;
    private int attackAmount = 1;

    //set up-----------------------------------------

    public enemy (String n, int level){ // call a function to randomize the enemy
        this.name = n;
        this.health = this.health + level;
        if (n.equals("randomName")){
            this.name = randomName() + " " +randomLastName();
        }

        // inisialize primary weapon
        primaryWeapon = makeRandomWeapon(level);

    }

    // functions ---------------------------------------

    public int block(stick weapon, stickStorage storage) throws IOException { // calculate damage, minigames can be placed here
        print(name + " is attacking you!!");
        print("Hes using his " + primaryWeapon.getName() + "!");

        pause(1500);

        double damage = blockSomething(randomNumber(10,15),randomNumber(25,35)); // play mini game

        if (damage > 0) {
            if(weapon.damageStick()){
                storage.setPrimary(storage.getByName("fists").get(0)); // set primary to fists
                storage.removeStick(storage.getIndexByReference(weapon));// remove broken stick
                print(weapon.getName() + " has broken. setting weapons to fists");
            } else {
                if(weapon.getName().equals("fists")){   // make sure fists dont brake
                    weapon.setStiffness(999999);
                } else{
                    print(weapon.getName() + " lost stiffness."); // if not fists and broken
                }
            }


        }

        damage =  damage * (double)primaryWeapon.getStrength(); // strength multiplier
        damage = (int)Math.ceil(damage);// round up
        print(name + " did "+  damage + " damage.");
        pause(1000);
        return (int) damage;
    }

    public int attack(stick weapon) throws IOException { // you attack
        print("Your attacking " + name);
        print("with " + weapon.getName() +"!");

        pause(1500);

        double damage = wackSomething(randomNumber(10,15),randomNumber(15,25)); // play mini game
        damage =  damage * (double)weapon.getStrength(); // strength multiplier
        damage = (int)Math.ceil(damage);// round up
        print( "You did "+  damage + " damage with "+ weapon.getName());
        pause(1000);
        return (int) damage;
    }

    public stick makeRandomWeapon(int level){
       stick newWeapon = new stick(randomStickName(), randomNumber(2,level), randomNumber(1,level),"default" );
       return newWeapon;
    }

    //gets/sets----------------------------------------

    //gets
    public String getName (){return name;}
    public int getHealth (){return health;}
    public stick getPrimaryWeapon(){return primaryWeapon;}


    //sets
    public void setAttackAmount(int aA){attackAmount = aA;}
    public void setHealth(int h){health = h;}


}

public class stickStorage { // there should only be one of these and action function could be put here

    //metadata-----------
    private final ArrayList<stick> storage = new ArrayList<>() ; // stores reference to new arrary list
    //  .add(value)    .get(index)    .remove(index)    .size()

    private stick primary = null;


    // constructor--------
    public stickStorage ( ) {

        stick fists = new stick("fists", 9999 ,2,"default");
        primary = fists;
        storage.add(fists);
    }


    // functions and stuff-------------

    public void addStick (stick stick){storage.add(stick);}

    public void removeStick(int index){storage.remove(index);}

    //search functions

    public ArrayList<stick> getByName(String name){ // gets sticks by comparing name (can find multiple sticks)

         ArrayList<stick> collection = new ArrayList<>() ; //the list of sticks thats being returned

        name = name.toLowerCase();

         for (int i = 0; i < storage.size(); i++){ // traverse through all sticks in storage

             stick reference = storage.get(i); // current stick reference
             String stickName = reference.getName().toLowerCase(); // current stick name and make it lower case

             if(stickName.equals(name)){ // compare names
                 collection.add(reference); // add to collection
             }
         }
         return collection;
    }

    public int getIndexByReference(stick stick){ // gets the index of a stick

        for (int i = 0; i < storage.size(); i++){ // traverse through all sticks in storage

            stick currentIndex = storage.get(i); // current stick reference

            if(stick == currentIndex){ // compare references
            return i;
            }
        }
        return -1;
    }

    public ArrayList<stick> getStrongest(){ // gets sticks by comparing name (can find multiple sticks)

        ArrayList<stick> collection = new ArrayList<>() ; //the list of sticks thats being returned

        if (storage.isEmpty()){
            return collection;
        }else{
            collection.add(storage.getFirst());
        }

        for (int i = 1; i < storage.size(); i++){ // traverse through all sticks in storage

            stick reference = storage.get(i); // current stick reference

            if(reference.getStrength() > collection.getFirst().getStrength()){ // compare strength
                collection.clear(); //replace with stronger stick
                collection.add(reference);
            } else if (reference.getStrength() == collection.getFirst().getStrength()){
                collection.add(reference); // add equal sticks
            }
        }
        return collection;
    }

    public ArrayList<stick> getStiffest(){ // gets sticks by comparing name (can find multiple sticks)

        ArrayList<stick> collection = new ArrayList<>() ; //the list of sticks thats being returned

        if (storage.isEmpty()){
            return collection;
        }else{
            collection.add(storage.getFirst());
        }

        for (int i = 1; i < storage.size(); i++){ // traverse through all sticks in storage

            stick reference = storage.get(i); // current stick reference

            if(reference.getStiffness() > collection.getFirst().getStiffness()){ // compare strength
                collection.clear(); //replace with stiffer stick
                collection.add(reference);
            } else if (reference.getStiffness() == collection.getFirst().getStiffness()){
                collection.add(reference); // add equal sticks
            }
        }
        return collection;
    }

    //other functions

    public void printStats(stick stick){ //print the stats of a stick by its reference in a fancy way

    print("---"+ stick.getName() + " Stat Page---");
    print("Name: " + stick.getName());
    print("Stiffness: " + stick.getStiffness());
    print("Strength: " + stick.getStrength());
    print("Value: " + stick.getValue());
    print("stick creation id: " + stick.getId());
    print("--------------");
    }


    // gets and sets-------------
    //gets--

    public ArrayList<stick> getStorage(){return storage;} // returns the reference

    public stick getPrimary(){return primary;}

    //sets--

    public void setPrimary(stick stick){ primary = stick;}

}



//himm