package Project;
import java.util.Random;
import java.util.Scanner;

class CustomHashMap<K, V> {

    private Entry<K,V>[] table;
    private int capacity= 16;

    static class Entry<K, V> {
        K key;
        V value;
        Entry<K,V> next;

        public Entry(K key, V value, Entry<K,V> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }


    @SuppressWarnings("unchecked")
    public CustomHashMap(){
        table = new Entry[capacity];
    }

    public void put(K newKey, V data){
        if(newKey==null)
            return;
        int hash=hash(newKey);

        Entry<K,V> newEntry = new Entry<K,V>(newKey, data, null);


        if(table[hash] == null){
            table[hash] = newEntry;
        }else{
            Entry<K,V> previous = null;
            Entry<K,V> current = table[hash];

            while(current != null){
                if(current.key.equals(newKey)){
                    if(previous==null){
                        newEntry.next=current.next;
                        table[hash]=newEntry;
                        return;
                    }
                    else{
                        newEntry.next=current.next;
                        previous.next=newEntry;
                        return;
                    }
                }
                previous=current;
                current = current.next;
            }
            previous.next = newEntry;
        }
    }

    public V get(K key){
        int hash = hash(key);
        if(table[hash] == null){
            return null;
        }else{
            Entry<K,V> temp = table[hash];
            while(temp!= null){
                if(temp.key.equals(key))
                    return temp.value;
                temp = temp.next;
            }
            return null;
        }
    }

    public void display(){

        for(int i=0;i<capacity;i++){
            if(table[i]!=null){
                Entry<K, V> entry=table[i];
                while(entry!=null){
                    System.out.print("{"+entry.key+"="+entry.value+"}" +" ");
                    entry=entry.next;
                }
            }
        }

    }

    private int hash(K key){
        return Math.abs(key.hashCode()) % capacity;
    }

}

class Board
{

    final static int WINPOINT = 100;


    static CustomHashMap< Integer , Integer > snake = new CustomHashMap< Integer , Integer >();
    static CustomHashMap< Integer , Integer > ladder = new CustomHashMap< Integer , Integer >();

    {
        snake.put(99,54);
        snake.put(70,55);
        snake.put(52,42);
        snake.put(25,2);
        snake.put(95,72);

        ladder.put(6,25);
        ladder.put(11,40);
        ladder.put(60,85);
        ladder.put(46,90);
        ladder.put(17,69);
    }

    public int rollDice()
    {
        int n = 0;
        Random r = new Random();
        n=r.nextInt(7);
        return (n==0?1:n);
    }

    public boolean isWin(int player)
    {
        return WINPOINT == player;
    }

    public int calculatePlayerValue(int player, int diceValue)
    {
        player = player + diceValue;

        if(player > WINPOINT)
        {
            player = player - diceValue;
            return player;
        }

        if(null!=snake.get(player))
        {
            System.out.println("You got swallowed by snake");
            player= snake.get(player);
        }

        if(null!=ladder.get(player))
        {
            System.out.println("You climbed up the ladder");
            player= ladder.get(player);
        }
        return player;
    }

    public void startGame()
    {

        int player1 =1, player2=1;
        int currentPlayer=-1;
        System.out.println("WELCOME TO SNAKE N LADDERS!");
        System.out.println("There are snakes at :: ");
        snake.display();
        System.out.println("\nBut don't you worry there're also ladders :: ");
        ladder.display();
        Scanner s = new Scanner(System.in);
        String str;
        int diceValue =0;
        do
        {
            System.out.println(currentPlayer==-1?"\n\nFIRST PLAYER TURN":"\n\nSECOND PLAYER TURN");
            System.out.println("Press r to roll Dice");
            str = s.next();
            diceValue = rollDice();


            if(currentPlayer == -1)
            {
                player1 = calculatePlayerValue(player1,diceValue);
                System.out.println("Dice :: " + diceValue);
                System.out.println("First Player :: " + player1);
                System.out.println("Second Player :: " + player2);
                System.out.println("------------------");
                if(isWin(player1))
                {
                    System.out.println("First player wins");
                    return;
                }
            }
            else
            {
                player2 = calculatePlayerValue(player2,diceValue);
                System.out.println("Dice :: " + diceValue);
                System.out.println("First Player :: " + player1);
                System.out.println("Second Player :: " + player2);
                System.out.println("------------------");
                if(isWin(player2))
                {
                    System.out.println("Second player wins");
                    return;
                }
            }

            currentPlayer= -currentPlayer;
        }while("r".equals(str));
    }

}

public class SnakeNLadder {

    public static void main(String[] args) {
        Board s = new Board();
        s.startGame();

    }

}




