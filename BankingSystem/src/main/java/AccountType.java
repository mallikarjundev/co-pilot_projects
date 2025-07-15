public enum AccountType {
    SAVINGS(0.04), CURRENT(0.01);

    private final double annualInterestRate;

    AccountType(double rate){
        this.annualInterestRate=rate;
    }

    public double getAnnualInterestRate(){
        return annualInterestRate;
    }

}
