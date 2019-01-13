class AddExpression extends BinaryExpression
{
    public AddExpression(final Expression lft, final Expression rht)
    {
        super(lft, rht, "+");
    }
    public double applyOperator(double leftresult, double rightresult)
    {
    	return leftresult + rightresult;
    }

    
}
