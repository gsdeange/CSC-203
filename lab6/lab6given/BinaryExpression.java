public abstract class BinaryExpression implements Expression
{
	private final Expression lft;
	private final Expression rht;
	private String operator;

	public BinaryExpression(final Expression lft, final Expression rht, String operator)
	{
		this.lft = lft;
		this.rht = rht;
		this.operator = operator;
	}
	public String toString()
    {
        return "(" + lft + " " + operator + " " + rht + ")";
    }

    public double evaluate(final Bindings bindings)
    {
        double leftresult =  lft.evaluate(bindings);
        double rightresult = rht.evaluate(bindings);
        return applyOperator(leftresult, rightresult);
    }
    protected abstract double applyOperator(double leftresult, double rightresult);
}