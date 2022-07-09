import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Polling {

    public static final Scanner SCANNER = new Scanner(System.in);

    private final Set<User> users;

    public Polling() {
        users = new LinkedHashSet<>();

        while (true) {
            try {
                sendChoose();
            } catch (PollingException e) {
                System.out.println(e.getContext());
            }
        }

    }

    private void sendChoose() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Ввести данные");
        System.out.println("2 - Вывести данные");
        String type = SCANNER.nextLine();
        if (type.equalsIgnoreCase("1")) {
            sendForm();
        } else if (type.equalsIgnoreCase("2")) {
            sendSorting();
        } else {
            throw new PollingException("Неизвестное действие!");
        }
    }

    private void sendSorting() {
        System.out.println("Выберите тип сортировки:");
        System.out.println("1 - Мужчины старше 40 с высшим образованием ответили ДА");
        System.out.println("2 - Женщины моложне 30 с средним образованием ответили НЕТ");
        System.out.println("3 - Мужчины моложе 25 с начальным образованием ответили ДА");
        String type = SCANNER.nextLine();

        Set<User> users = sortUsers(type);
        if (users.isEmpty()) {
            throw new PollingException("Сортировка выдала пустой список!");
        }
        System.out.println("==============");
        users.forEach(user -> System.out.println(user.getLabelName()));
        System.out.println("==============");
    }

    private void sendForm() {
        System.out.println("Введите данные опрошенного в формате: <имя> <возраст> <пол 1|2> <образование 1|2|3> <ответ 1|2>");
        System.out.println("* Пол: 1 - Мужской | 2 - Женский");
        System.out.println("* Образование: 1 - " + Education.STARTING.getLang() + " | 2 - " + Education.MIDDLE.getLang() + " | 3 - " + Education.HIGH.getLang());
        System.out.println("* Ответ: 1 - Да | 2 - Нет");

        String data = SCANNER.nextLine();
        String[] dataArray = data.split(" ");


        String nameString;
        String ageString;
        String genderString;
        String educationString;
        String answerString;

        try {
            nameString = dataArray[0];
            ageString = dataArray[1];
            genderString = dataArray[2];
            educationString = dataArray[3];
            answerString = dataArray[4];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new PollingException("Введите полное количество аргументов!");
        }

        String name = nameString;
        boolean gender = genderString.equals("1");
        int age;
        Education education;
        boolean answer = answerString.equalsIgnoreCase("1");

        try {
            age = Integer.parseInt(ageString);
        } catch (NumberFormatException e) {
            throw new PollingException("Введите возраст в цифровом формате");
        }

        education = getEducation(educationString);
        if (education == null) {
            throw new PollingException("Введите верный тип образования");
        }

        User user = new User(name, age, gender, education, answer);
        System.out.println("Добавлен ответ: " + user.getLabelName());
        users.add(user);
    }

    private Set<User> sortUsers(String type) {
        return switch (type) {
            case "1" -> users.stream()
                    .filter(User::isMale)
                    .filter(user -> user.getAge() > 40)
                    .filter(user -> user.getEducation() == Education.HIGH)
                    .filter(User::getAnswer)
                    .collect(Collectors.toSet());
            case "2" -> users.stream()
                    .filter(User::isFeemale)
                    .filter(user -> user.getAge() < 30)
                    .filter(user -> user.getEducation() == Education.MIDDLE)
                    .filter(user -> !user.getAnswer())
                    .collect(Collectors.toSet());
            case "3" -> users.stream()
                    .filter(User::isMale)
                    .filter(user -> user.getAge() < 25)
                    .filter(user -> user.getEducation() == Education.STARTING)
                    .filter(User::getAnswer)
                    .collect(Collectors.toSet());
            default -> new HashSet<>();
        };
    }

    private Education getEducation(String number) {
        return switch (number) {
            case "1" -> Education.STARTING;
            case "2" -> Education.MIDDLE;
            case "3" -> Education.HIGH;
            default -> null;
        };
    }

}
