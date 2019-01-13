class SubtractExpression extends BinaryExpression
{

    public SubtractExpression(final Expression lft, final Expression rht)
    {
        super(lft, rht, "-");
    }
    public double applyOperator(double leftresult, double rightresult)
    {
        return leftresult - rightresult;
    }
}

