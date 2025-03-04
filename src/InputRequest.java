import java.util.function.Function;

public class InputRequest {
    private final String prompt;  // Текст запроса
    private final Class<?> expectedType; // Ожидаемый тип данных
    private final Function<String, Object> parser; // Лямбда для преобразования строки в нужный тип
    private final String fieldName;
    public InputRequest(String prompt, Class<?> expectedType, Function<String, Object> parser, String fieldName) {
        this.prompt = prompt;
        this.expectedType = expectedType;
        this.parser = parser;
        this.fieldName = fieldName;
    }

    public String getPrompt() {
        return prompt;
    }

    public Class<?> getExpectedType() {
        return expectedType;
    }

    public Object parseInput(String input) {
        return parser.apply(input);
    }

    public String getFieldName() {
        return fieldName;
    }
}