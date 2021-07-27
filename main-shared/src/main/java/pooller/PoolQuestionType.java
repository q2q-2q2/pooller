package pooller;

public enum PoolQuestionType {
    SINGLE, MANY,
    SINGLE_PROVIDED(true), MANY_PROVIDED(false),
    SINGLE_IMAGE(true), MANY_IMAGE(false),
    ;
    private final boolean single;
    PoolQuestionType(boolean isSingle) {
        this.single = isSingle;
    }
    PoolQuestionType() {
        this.single = false;
    }
    public boolean isSingle() {
        return single;
    }
}
