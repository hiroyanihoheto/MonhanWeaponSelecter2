package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeaponController {
    private List<Weapon> weapons;
    private Random random;

    public WeaponController() {
        weapons = new ArrayList<>();
        weapons.add(new Weapon("太刀", 0.047));
        weapons.add(new Weapon("双剣", 0.069));
        weapons.add(new Weapon("ライト", 0.083));
        weapons.add(new Weapon("弓", 0.086));
        weapons.add(new Weapon("大剣", 0.090));
        weapons.add(new Weapon("ヘビィ", 0.106));
        weapons.add(new Weapon("スラアク", 0.110));
        weapons.add(new Weapon("片手剣", 0.116));
        weapons.add(new Weapon("狩猟笛", 0.130));
        weapons.add(new Weapon("ハンマー", 0.138));
        weapons.add(new Weapon("操虫棍", 0.158));
        weapons.add(new Weapon("チャアク", 0.242));
        weapons.add(new Weapon("ガンス", 0.250));
        weapons.add(new Weapon("ランス", 0.402));

        random = new Random();
        normalizeProbabilities(); // 初期化時にも正規化
    }

    @GetMapping({"/", "/startLotto"})
    public String startLotto() {
        return "startLotto";
    }

   
    
    @GetMapping("/weapon")
    public String getWeapon(Model model) {
        Weapon mainWeapon = selectRandomWeapon();
        Weapon subWeapon = selectRandomWeapon();

        model.addAttribute("mainWeapon", mainWeapon.getName());
        model.addAttribute("subWeapon", subWeapon.getName());

        // 抽選後、確率変動処理
        updateProbabilities(mainWeapon.getName(), subWeapon.getName());

        return "WeaponLotto";
    }
    
    @GetMapping("/probabilities")
    public String showProbabilities(Model model) {
        model.addAttribute("weapons", weapons);
        return "probabilities";
    }

    

    private Weapon selectRandomWeapon() {
        double rand = random.nextDouble();
        double cumulative = 0.0;

        for (Weapon weapon : weapons) {
            cumulative += weapon.getProbability();
            if (rand <= cumulative) {
                return weapon;
            }
        }

        return weapons.get(weapons.size() - 1); // 念のため
    }

    private void updateProbabilities(String mainName, String subName) {
        for (Weapon weapon : weapons) {
            if (weapon.getName().equals(mainName)) {
                weapon.setProbability(weapon.getProbability() - 0.008);
            } else if (weapon.getName().equals(subName)) {
                weapon.setProbability(weapon.getProbability() - 0.004);
            } else {
                weapon.setProbability(weapon.getProbability() + 0.001);
            }

            // 0未満防止
            if (weapon.getProbability() < 0) {
                weapon.setProbability(0);
            }
        }

        normalizeProbabilities();
    }

    private void normalizeProbabilities() {
        double total = weapons.stream().mapToDouble(Weapon::getProbability).sum();
        for (Weapon weapon : weapons) {
            weapon.setProbability(weapon.getProbability() / total);
        }
    }
}
