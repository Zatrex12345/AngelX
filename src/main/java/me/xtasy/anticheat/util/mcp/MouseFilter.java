package me.xtasy.anticheat.util.mcp;

public class MouseFilter
{
    private float field_76334_b;
    private float field_76335_c;
    private float field_76336_a;
    
    public float smooth(float field_76335_c, final float n) {
        this.field_76336_a += field_76335_c;
        field_76335_c = (this.field_76336_a - this.field_76334_b) * n;
        this.field_76335_c += (field_76335_c - this.field_76335_c) * 0.5f;
        if ((field_76335_c > 0.0f && field_76335_c > this.field_76335_c) || (field_76335_c < 0.0f && field_76335_c < this.field_76335_c)) {
            field_76335_c = this.field_76335_c;
        }
        this.field_76334_b += field_76335_c;
        return field_76335_c;
    }
}
