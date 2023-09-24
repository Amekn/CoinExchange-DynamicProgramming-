public class CoinExchange{
    private static int[] coinSet;
    public static void main(String[] args){
        if(args.length != 1){
            throw new IllegalArgumentException("Usage: java CoinExchange <amount>");
        }
        coinSet = new int[]{1, 2, 5, 10, 20, 25, 50, 75, 100};
        int change = Integer.parseInt(args[0]);
        int[] exchangedCoins = new int[change + 1];
        //int minCoins = makeChangeIterativeDynamic(coinSet, coinSet.length, change, new int[change + 1], exchangedCoins);
        int minCoins = makeChangeRecursiveDriver(coinSet, change, new int[change + 1], exchangedCoins);
        System.out.println("Exchange for " + args[0] + " require minimum of " + minCoins + " coins.");
        System.out.println("Those coins are: ");
        for(int i = 0, j = change; i < minCoins; i++){
            System.out.println("Coin " + (i + 1)+ ": " +
            exchangedCoins[j]
                               );
            j = j - exchangedCoins[j];
        }
    }
    
    public static int makeChange(int coins[], int amount){
        int minCoins = amount;
        for(int i = 0; i < coins.length; i++)
            if(coins[i] == amount)
                return 1;
        
        for(int j = 1; j <= amount/2; j++){
            int thisCoins = makeChange(coins, j)
                            + makeChange(coins, amount - j);
            if(thisCoins < minCoins)
                minCoins = thisCoins;
        }
        return minCoins;
    }
    
    public static int makeChangeIterativeDynamic(int[] coins, int maxChange, int[] coinsUsed, int[] lastCoin){
        coinsUsed[0] = 0; lastCoin[0] = 1;
        for(int cents = 1; cents <= maxChange; cents++){
            int minCoins = cents;
            int newCoin = 1;
            for(int j = 0; j < coins.length; j++){
                if(coins[j] > cents) break;//Cannot use coin j, as it is larger than the change we trying to make.
                if(coinsUsed[cents - coins[j]] + 1 < minCoins){
                    minCoins = coinsUsed[cents - coins[j]] + 1;
                    newCoin = coins[j];
                }
            }
            coinsUsed[cents] = minCoins;
            lastCoin[cents] = newCoin;
        }
        return coinsUsed[maxChange];
    }
    
    private static void makeChangeRecursiveDynamic(int[] coins, int current, int limit, int[] coinsUsed, int[] lastCoin){
        int minCoins = current;
        int newCoin = 1;
        for(int j = 0; j < coins.length; j++){
            if(coins[j] > current) break;
            if(coinsUsed[current - coins[j]] + 1 < minCoins){
                minCoins = coinsUsed[current - coins[j]] + 1;
                newCoin = coins[j];
            }
        }
        coinsUsed[current] = minCoins;
        lastCoin[current] = newCoin;
        //Check if further work has to be done.
        if(current==limit) return;
        makeChangeRecursiveDynamic(coins, current + 1, limit, coinsUsed, lastCoin);
    }
    
    public static int makeChangeRecursiveDriver(int[] coins, int maxChange, int[] coinsUsed, int[] lastCoin){
        coinsUsed[0] = 0; lastCoin[0] = 1;
        makeChangeRecursiveDynamic(coins, 1, maxChange, coinsUsed, lastCoin);
        return coinsUsed[maxChange];
    }
}
