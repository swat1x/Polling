import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class User {

    @Getter
    String name;
    @Getter
    int age;
    boolean male;

    @Getter
    Education education;

    boolean answer;

    public boolean isMale() {
        return male;
    }

    public boolean isFeemale() {
        return !male;
    }

    public boolean getAnswer() {
        return answer;
    }

    public String getGender() {
        return male ? "Мужчина" : "Женщина";
    }

    public String getLabelName() {
        return String.format("Имя: %s, пол: %s, возраст: %s, ответ: %s", name, getGender(), age, answer ? "ДА" : "НЕТ");
    }
}
