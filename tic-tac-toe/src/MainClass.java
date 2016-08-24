import java.util.Random;
import java.util.Scanner;

public class MainClass {

    static final int MAP_SIZE = 3;              //размер игрового поля
    static final int VICTORY_CONDITIONS = 3;    //условие победы (для поля 5х5=4)
    static final char PUSTO = '*';
    static final char HUMAN_DOT = 'X';
    static final char AI_DOT = 'O';

    static char[][] map = new char[MAP_SIZE][MAP_SIZE];

    public static void main(String[] args) {
        createField();
        printField();
        while (true) {
            humanTurn();
            printField();
            if (checkWin(HUMAN_DOT)) {
                System.out.println("Победа человека!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("Ничья!");
                break;
            }
            aiTurn();
            printField();
            if (checkWin(AI_DOT)) {
                System.out.println("Победа компьютера!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("Ничья!");
                break;
            }
        }
    }

    //создание игрового поля
    public static void createField() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                map[i][j] = PUSTO;
            }
        }
    }

    //распечатка поля на экране
    public static void printField() {
        for (int i = 0; i < MAP_SIZE + 1; i++) {
            System.out.print((i) + " ");
        }

        System.out.println();
        for (int i = 0; i < MAP_SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < MAP_SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    //установка соответствующей точки в позицию [x,y]
    public static void setDot(int x, int y, char dot) {
        map[x][y] = dot;
    }

    //ход человека
    public static void humanTurn() {
        int x = -1;
        int y = -1;
        Scanner sn = new Scanner(System.in);
        do {
            System.out.println("Введите координаты x, y");
            x = sn.nextInt() - 1;
            y = sn.nextInt() - 1;
        }
        while (!isCellEmpty(y, x));
        setDot(y, x, HUMAN_DOT);
    }

    //проверка пуста ли ячейка и не выходит ли она за границу поля
    public static boolean isCellEmpty(int x, int y) {
        if (x < 0 || x > MAP_SIZE || y < 0 || y > MAP_SIZE) return false;
        if (map[x][y] == PUSTO) return true;
        return false;
    }

    //ход компьютера
    public static void aiTurn() {
        Random rn = new Random();
        int x = -1;
        int y = -1;
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (isCellEmpty(j, i)) {
                    setDot(j, i, AI_DOT);
                    if (checkWin(AI_DOT)) {
                        return;
                    }
                    setDot(j, i, PUSTO);
                }
            }
        }
        if (x == -1 && y == -1) {
            for (int i = 0; i < MAP_SIZE; i++) {
                for (int j = 0; j < MAP_SIZE; j++) {
                    if (isCellEmpty(j,i)) {
                        setDot(j, i, HUMAN_DOT);
                        if (checkWin(HUMAN_DOT)) {
                            x = j;
                            y = i;
                        }
                        setDot(j,i,PUSTO);
                    }
                }
            }
        }
        if (x == -1 && y == -1) {
            do {
                x = rn.nextInt(MAP_SIZE);
                y = rn.nextInt(MAP_SIZE);
            }
            while (!isCellEmpty(y, x));
        }
        setDot(y, x, AI_DOT);
    }

    //проверка осталось ли на поле хотя бы одна пустая ячейка
    public static boolean isFieldFull() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (map[i][j] == PUSTO) return false;
            }
        }
        return true;
    }

    //проверка победы
    public static boolean checkWin(char dot) {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (checkLine(i, j, 1, 0, VICTORY_CONDITIONS, dot)
                        || checkLine(i, j, 0, 1, VICTORY_CONDITIONS, dot)
                        || checkLine(i, j, 1, 1, VICTORY_CONDITIONS, dot)
                        || checkLine(i, j, 1, -1, VICTORY_CONDITIONS, dot)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkLine(int cx, int cy, int vx, int vy, int l, char dot) {
        if (cx + l * vx > MAP_SIZE || cy + l * vy > MAP_SIZE || cy + l * vy < -1) return false;
        for (int i = 0; i < l; i++) {
            if (map[cx + i * vx][cy + i * vy] != dot) return false;
        }
        return true;

    }

}
