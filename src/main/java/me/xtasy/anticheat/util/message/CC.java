package me.xtasy.anticheat.util.message;

public enum CC
{
    BLACK('0'),
    DARK_AQUA('3'),
    DARK_GREEN('2'),
    PINK('d'),
    YELLOW('e'),
    GOLD('6'),
    DARK_RED('4'),
    RED('c'),
    BLUE('9'),
    BOLD('l'),
    DARK_GRAY('8'),
    GREEN('a'),
    AQUA('b'),
    DARK_PURPLE('5'),
    DARK_BLUE('1'),
    GRAY('7'),
    WHITE('f');
    private static final CC[] $VALUES;



    private final char code;
    

    
    @Override
    public String toString() {
        return "�" + this.code;
    }
    
    static {
        $VALUES = new CC[] { CC.BLACK, CC.DARK_BLUE, CC.DARK_GREEN, CC.DARK_AQUA, CC.DARK_RED, CC.DARK_PURPLE, CC.GOLD, CC.GRAY, CC.DARK_GRAY, CC.BLUE, CC.GREEN, CC.AQUA, CC.RED, CC.PINK, CC.YELLOW, CC.WHITE, CC.BOLD };
    }
    
    private CC(final char code) {
        this.code = code;
    }
    
    public char getCode() {
        return this.code;
    }
}
