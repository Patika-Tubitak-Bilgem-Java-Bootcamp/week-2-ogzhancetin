package HW2.locations;

import HW2.obstacles.Obstacle;
import HW2.Player;

import java.util.Random;

public abstract class BattleLoc extends Location {
    protected Obstacle obstacle;
    protected String award;

    public BattleLoc(Player player, String name, Obstacle obstacle, String award) {
        super(player);
        this.obstacle = obstacle;
        this.name = name;
        this.award = award;
    }

    public boolean getLocation() {
        int obsCount = obstacle.count();
        System.out.println("Şuan buradasınız : " + this.getName());
        System.out.println("Dikkatli ol! Burada " + obsCount + " tane " + obstacle.getName() + " yaşıyor !");
        System.out.print("<S>avaş veya <K>aç :");
        String selCase = scan.nextLine();
        selCase = selCase.toUpperCase();
        if (selCase.equals("S")) {
            if (combat(obsCount)) {
                System.out.println(this.getName() + " bölgesindeki tüm düşmanları temizlediniz !");
                if (this.award.equals("Food") && !player.getInventory().isFood()) {
                    System.out.println(this.award + " Kazandınız! ");
                    player.getInventory().setFood(true);
                } else if (this.award.equals("Water") && !player.getInventory().isWater()) {
                    System.out.println(this.award + " Kazandınız! ");
                    player.getInventory().setWater(true);
                } else if (this.award.equals("Firewood") && !player.getInventory().isFirewood()) {
                    System.out.println(this.award + " Kazandınız! ");
                    player.getInventory().setFirewood(true);
                }
                return true;
            }

            if (player.getHealthy() <= 0) {
                System.out.println("öldünüz !");
                return false;
            }

        }
        return true;
    }

    public boolean combat(int obsCount) {
        Random random = new Random();
        for (int i = 0; i < obsCount; i++) {
            int defObsHealth = obstacle.getHealth();
            playerStats();
            enemyStats();
            while (player.getHealthy() > 0 && obstacle.getHealth() > 0) {
                System.out.print("<V>ur veya <K>aç :");
                String selCase = scan.nextLine();
                selCase = selCase.toUpperCase();
                if (selCase.equals("V")) {
                    if (random.nextBoolean()) {
                        System.out.println("Siz vurdunuz !");
                        obstacle.setHealth(obstacle.getHealth() - player.getTotalDamage());
                        afterHit();
                        if (obstacle.getHealth() > 0) {
                            System.out.println();
                            System.out.println("Canavar size vurdu !");
                            player.setHealthy(player.getHealthy() - (obstacle.getDamage() - player.getInventory().getArmor()));
                            afterHit();
                        }
                    } else {
                        System.out.println();
                        System.out.println("Canavar size vurdu !");
                        player.setHealthy(player.getHealthy() - (obstacle.getDamage() - player.getInventory().getArmor()));
                        afterHit();

                        if (this.getPlayer().getHealthy() > 0) {
                            System.out.println();
                            System.out.println("Siz vurdunuz!");
                            obstacle.setHealth(this.obstacle.getHealth() - this.getPlayer().getTotalDamage());
                            afterHit();
                        }
                    }
                } else {
                    return false;
                }
            }

            if (obstacle.getHealth() < player.getHealthy()) {
                System.out.println("Düşmanı yendiniz !");
                player.setMoney(player.getMoney() + obstacle.getAward());
                System.out.println("Güncel Paranız : " + player.getMoney());
                obstacle.setHealth(defObsHealth);
            } else {
                return false;
            }
            System.out.println("-------------------");
        }
        return true;
    }

    public void playerStats() {
        System.out.println("Oyuncu Degerleri\n--------------");
        System.out.println("Can:" + player.getHealthy());
        System.out.println("Hasar:" + player.getTotalDamage());
        System.out.println("Para:" + player.getMoney());
        if (player.getInventory().getDamage() > 0) {
            System.out.println("Silah:" + player.getInventory().getWeapon());
        }
        if (player.getInventory().getArmor() > 0) {
            System.out.println("Zırh:" + player.getInventory().getaName());
        }
    }

    public void enemyStats() {
        System.out.println("\n" + obstacle.getName() + " Değerleri\n--------------");
        System.out.println("Can:" + obstacle.getHealth());
        System.out.println("Hasar:" + obstacle.getDamage());
        System.out.println("ödül:" + obstacle.getAward());
    }

    public void afterHit() {
        System.out.println("Oyuncu Canı:" + player.getHealthy());
        System.out.println(obstacle.getName() + " Canı:" + obstacle.getHealth());
        System.out.println();
    }

}
