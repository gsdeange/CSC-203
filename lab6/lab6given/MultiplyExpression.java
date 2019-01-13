class MultiplyExpression extends BinaryExpression
{
    public MultiplyExpression(final Expression lft, final Expression rht)
    {
        super(lft, rht, "*");
    }
    public double applyOperator(double leftresult, double rightresult)
    {
        return leftresult * rightresult;
    }
}

