package validator;

public class NameValueValidator {
    public static boolean isEmptyOrNull(String name) {
        return name == null || name.isEmpty();
    }
}
