package r09.basic;

public class StringFactory implements BasicFactory<String> {
    private int current;
    private final String[] text;

    public StringFactory(int start, String[] text) {
        this.current = start;
        this.text = text;
    }

    @Override
    public String create() {
        String result = text[current];
        current = (current + 1) % text.length;
        return result;
    }
}